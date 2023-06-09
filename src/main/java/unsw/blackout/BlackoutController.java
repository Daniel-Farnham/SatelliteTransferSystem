package unsw.blackout;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import unsw.components.Devices.Device;
import unsw.components.Devices.HandheldDevice;
import unsw.components.Devices.LaptopDevice;
import unsw.blackout.FileTransferException.VirtualFileNotFoundException;
import unsw.components.Devices.DesktopDevice;
import unsw.components.Satellites.Satellite;
import unsw.components.Satellites.StandardSatellite;
import unsw.components.Satellites.TeleportingSatellite;
import unsw.components.Satellites.RelaySatellite;
import unsw.components.Files.File;
import unsw.components.Files.FileCarrier;
import unsw.components.GPS.GPS;
import unsw.response.models.EntityInfoResponse;
import unsw.response.models.FileInfoResponse;

import static unsw.utils.MathsHelper.RADIUS_OF_JUPITER;
import unsw.utils.Angle;


public class BlackoutController {
    private List<Device> devices;
    private List<Satellite> satellites; 
    private List<File> files; 

    public BlackoutController() {
        devices = new ArrayList<>();
        satellites = new ArrayList<>(); 
    }

    /*
     * Returns list of Devices
     */
    public List<Device> getDevices() {
        return devices;
    }

    /*
     * Returns list of Satellites
     */
    public List<Satellite> getSatellites() {
        return satellites; 
    }

    public List<File> getFiles() {
        return files;
    }

    /**
     * Creates a new device with the given ID, type, and position.
     * @param deviceId The ID of the new device.
     * @param type The type of the new device.
     * @param position The position of the new device.
     */
    public void createDevice(String deviceId, String type, Angle position) {
        if (type == "HandheldDevice") {
            HandheldDevice newHandheldDevice = new HandheldDevice(deviceId, position);
            devices.add(newHandheldDevice);    
        }
        
        if (type == "LaptopDevice") {
            LaptopDevice newLaptopDevice = new LaptopDevice(deviceId, position);
            devices.add(newLaptopDevice);   
        }

        if (type == "DesktopDevice") {
            DesktopDevice newDesktopDevice = new DesktopDevice(deviceId, position);
            devices.add(newDesktopDevice);   
        }
        /* 
        Device newDevice = new Device(deviceId, type, position); 
        devices.add(newDevice); 
        */
    }

    /**
     * Removes a device with the specified ID.
     * @param deviceId The ID of the device to be removed.
     */
    public void removeDevice(String deviceId) {
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            if (device.getDeviceId().equals(deviceId)) {
                devices.remove(i);
                return;
            }
        }
    }

    /**
     * Creates a new satellite with the given ID, type, height, and position.
     * @param satelliteId The ID of the new satellite.
     * @param type The type of the new satellite.
     * @param height The height of the new satellite.
     * @param position The position of the new satellite.
     */
    public void createSatellite(String satelliteId, String type, double height, Angle position) {
        if (type == "StandardSatellite") {
            StandardSatellite newStandardSatellite = new StandardSatellite(satelliteId, height, position);
            satellites.add(newStandardSatellite); 
        }

        if (type == "TeleportingSatellite") {
            TeleportingSatellite newTeleportingSatellite = new TeleportingSatellite(satelliteId, height, position);
            satellites.add(newTeleportingSatellite);
        }

        if (type == "RelaySatellite") {
            RelaySatellite newRelaySatellite = new RelaySatellite(satelliteId, height, position);
            satellites.add(newRelaySatellite);
        }
        /* 
        Satellite newSatellite = new Satellite(satelliteId, type, height, position);
        satellites.add(newSatellite); 
        */
    }

    /**
     * Removes a satellite with the specified ID.
     * @param satelliteId The ID of the satellite to be removed.
     */
    public void removeSatellite(String satelliteId) {
        for (int i = 0; i < satellites.size(); i++) {
            Satellite satellite = satellites.get(i);
            if (satellite.getSatelliteId().equals(satelliteId)) {
                satellites.remove(i);
                return;
            }
        }
    }

    /**
     * Returns a list of all device IDs.
     * @return A list of all device IDs.
     */
    public List<String> listDeviceIds() {
        List<String> deviceIds = new ArrayList<>();
        for (Device device : devices) {
            deviceIds.add(device.getDeviceId());
        }
        return deviceIds;
    }

    /**
     * Returns a list of all satellite IDs.
     * @return A list of all satellite IDs.
     */
    public List<String> listSatelliteIds() {
        List<String> satelliteIds = new ArrayList<>();
        for (Satellite satellite : satellites) {
            satelliteIds.add(satellite.getSatelliteId());
        }
        return satelliteIds;
    }

    /**
    * Adds a file to the device with the specified ID.
    * 
    * @param deviceId the ID of the device to add the file to
    * @param filename the name of the file to add
    * @param content the contents of the file to add
    */
    public void addFileToDevice(String deviceId, String filename, String content) {
        Device targetDevice = null; 

        for (Device device : devices) {
            if (device.getDeviceId().equals(deviceId)) {
                targetDevice = device; 
                break; 
            }
        }

        targetDevice.addFile(filename, content); 
        
        
    }

    /**
    * Retrieves information about either a device or a satellite based on the input ID.
    *
    * @param id the ID of the device or satellite to retrieve information for
    * @return an EntityInfoResponse object containing the relevant information, or null if the ID is not found
 */
    public EntityInfoResponse getInfo(String id) {
        EntityInfoResponse response = null;

        for (Device device : devices) {
            if (device.getDeviceId().equals(id)) {
                
                // If a matching device is found, create a list of FileInfoResponse objects for its files
                List<File> files = device.getDeviceFiles();
                List<FileInfoResponse> fileResponses = new ArrayList<>();

                for (File file : files) {
                    // Create a FileInfoResponse object for each file
                    FileInfoResponse fileResponse = new FileInfoResponse(
                    file.getFilename(),
                    file.getContent(), 
                    file.getFilesize(),
                    file.transferComplete()
                );
                fileResponses.add(fileResponse);
                }
                
                // Create a map from the list of FileInfoResponse objects
                Map<String, FileInfoResponse> fileMap = new HashMap<>();
                for (FileInfoResponse fileResponse : fileResponses) {
                    fileMap.put(fileResponse.getFilename(), fileResponse);
                }

                response = new EntityInfoResponse(
                device.getDeviceId(),
                device.getPosition(),
                RADIUS_OF_JUPITER, 
                device.getDeviceType(),
                fileMap
                );
                break;
            }
        }
        
        for (Satellite satellite : satellites) {
            if (satellite.getSatelliteId().equals(id)) {
                response = new EntityInfoResponse(
                satellite.getSatelliteId(),
                satellite.getPosition(),
                satellite.getHeight(),
                satellite.getSatelliteType()
                );
                break;
            }
        }
            
        return response; 
    }

    public void simulate() {
        String entityId = null; 

        for (Satellite satellite : satellites) {
            satellite.updatePosition();
        }
        communicableEntitiesInRange(entityId);
        // Need to call method that progresses the files in transit. 
    }

    /**
     * Simulate for the specified number of minutes.
     * You shouldn't need to modify this function.
     */
    public void simulate(int numberOfMinutes) {
        for (int i = 0; i < numberOfMinutes; i++) {
            simulate();
        }
    }

    /**
     * 
     * Takes in an id. 
     */
    public List<String> communicableEntitiesInRange(String id) {
        List <String> listCommunicableEntities = new ArrayList<>(); 
        GPS gps = new GPS(devices, satellites);
        for (Satellite satellite : satellites) {
            if (satellite.getSatelliteId() == id) {
                listCommunicableEntities.addAll(gps.connectableEntities(satellite));
            }
            
        }
        for (Device device : devices) {
            if (device.getDeviceId() == id) {
                listCommunicableEntities.addAll(gps.connectableEntitiesToDevice(device));
            }
        }
        
        return listCommunicableEntities;
    }

    /**
     * This method is incomplete and buggy. 
     * Creates a FileCarrier class which communicates with the relevant entity and associated subclasses
     * FileCarrier organises a file to begin sending and checks for bandwith/storage parameters. 
     */
    public void sendFile(String fileName, String fromId, String toId) throws FileTransferException {
        String returnedExceptionString;

        FileCarrier fileCarrier = new FileCarrier(fromId, toId, fileName, devices, satellites, files);
        returnedExceptionString = fileCarrier.sendFile(fromId, toId, fileName);

        if (returnedExceptionString == "NoRoomOnSatellite") {

        }
        
        // TODO: Task 2 c)
    }

    public void createDevice(String deviceId, String type, Angle position, boolean isMoving) {
        createDevice(deviceId, type, position);
        // TODO: Task 3
    }

    public void createSlope(int startAngle, int endAngle, int gradient) {
        // TODO: Task 3
        // If you are not completing Task 3 you can leave this method blank :)
    }
}

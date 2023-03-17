package unsw.components.Files;

import unsw.components.Devices.Device;
import unsw.components.Satellites.Satellite;

import java.util.ArrayList;
import java.util.List;

public class FileCarrier {
    private String fromId;
    private String toId;
    private String fileName; 
    private List<File> files; 
    private List<Satellite> satellites; 
    private List<Device> devices; 

    public FileCarrier(String fromId, String toId, String fileName, List<Device> devices, List<Satellite> satellites, List<File> files) {
        this.fromId = fromId;
        this.toId = toId;
        this.fileName = fileName; 
        this.devices = devices;
        this.satellites = satellites; 
    }

    /**
    Sends a file from a device or satellite with the specified ID to another device or satellite with the specified ID. (Note this is not fully implemented)
    @param fromId the ID of the device or satellite sending the file
    @param toId the ID of the device or satellite receiving the file
    @param fileName the name of the file being sent
    @return an exception message, if any, generated during the file transfer process
    */
    public String sendFile(String fromId, String toId, String fileName) {
        String Exception = "";
        String bytesInTransit = "";
        
        for (Satellite satellite : satellites) { 
            if (satellite.getSatelliteId() == fromId) {
                bytesInTransit = satellite.sendBytes(fileName, File.getContentfromFileName(fileName));
            } 
            if (satellite.getSatelliteId() == toId) {
                if (satellite.canStoreFile(File.getContentfromFileName(fileName))) {
                    satellite.receiveBytes(satellite.getTransferComplete(), fileName, bytesInTransit);
                }
                else {
                    Exception = "NoRoomOnSatellite";
                }
            }
        }
        for (Device device : devices) {
            if (device.getDeviceId() == fromId) {
                bytesInTransit = device.sendBytes(fileName, File.getContentfromFileName(fileName));
            }
            if (device.getDeviceId() == toId) {       
                device.receiveBytes(device.getTransferComplete(), fileName, bytesInTransit);
            }
        }

        return Exception;
    }







}

package unsw.components.GPS;

import unsw.components.Satellites.StandardSatellite;
import unsw.components.Satellites.RelaySatellite;
import unsw.components.Satellites.Satellite;
import unsw.components.Satellites.TeleportingSatellite;
import unsw.components.Devices.Device;
import unsw.components.Devices.HandheldDevice;
import unsw.components.Devices.LaptopDevice;
import unsw.components.Devices.DesktopDevice;

import java.util.List;
import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.utils.MathsHelper;

/** 
 * This class calculates the list of connections between satellites and devices.
 * To save whoever marking this the time, this is a rough breakdown of the code
 *      - GPS assesses the possible connectable entities of device and satellite seperately e.g. connectableEntitiesToDevice v connectableEntities
 *      - These possible connections are also validated seperately
 *      - The code doesn't deal with RelaySatellites excluding device types if connected to a StandardSatellite
 *      - This code has a lot of duplication and needs to be refactored, if this message is still here then you know that I ran out of time and the last minute stress was real.
 *      - There is issues with TeleportationSatellites between 0 - 180 degrees. This is probably a reflection on simulate. 

*/
public class GPS {
    private List<Device> listDevice;
    private List<Satellite> listSatellite;

    public GPS(List<Device> deviceList, List<Satellite> satelliteList) {
        this.listDevice = deviceList;
        this.listSatellite = satelliteList;
    }

    /**
     * This method returns a list of satellite IDs that a given device can connect to.
     * 
     * @param device - Device to be checked for satellite connectivity
     * @return List<String> - List of satellite IDs that a given device can connect to
     */
    public List<String> connectableEntitiesToDevice(Device device) {
        List<String> listConnectableEntities = new ArrayList<>();

        double otherSatelliteRange = 0; 
        Angle otherSatellitePosition;
        double otherSatelliteHeight; 

        // For a given device try to make valid connections with satellites
        for (Satellite uniqueSatellite : listSatellite) {
            otherSatellitePosition = uniqueSatellite.getPosition();
            otherSatelliteHeight = uniqueSatellite.getHeight();
            if (uniqueSatellite instanceof StandardSatellite) {
                StandardSatellite standardSatellite = (StandardSatellite) uniqueSatellite;
                otherSatelliteRange = standardSatellite.getMaxRange();
            }
            if (uniqueSatellite instanceof TeleportingSatellite) {
                TeleportingSatellite teleportingSatellite = (TeleportingSatellite) uniqueSatellite;
                otherSatelliteRange = teleportingSatellite.getMaxRange();
            }
            if (uniqueSatellite instanceof RelaySatellite) {
                RelaySatellite relaySatellite = (RelaySatellite) uniqueSatellite;
                otherSatelliteRange = relaySatellite.getMaxRange();
            }

            // If connection is valid, add to list of connectable entities.
            if (isDeviceToSatelliteConnectionValid(device, otherSatellitePosition, otherSatelliteHeight, otherSatelliteRange)) {
                listConnectableEntities.add(uniqueSatellite.getSatelliteId());    
            }
        }

        return listConnectableEntities;
    }

    /**
     * This method checks if a connection between and satellite is valid.
     * 
     * @param device                - Device to be checked for connectivity
     * @param otherSatellitePosition - Position of the other satellite
     * @param otherSatelliteHeight   - Height of the other satellite
     * @param otherSatelliteRange    - Range of the other satellite
     * @return boolean - true if the device can connect to the satellite, false otherwise
     */
    public boolean isDeviceToSatelliteConnectionValid(Device device, Angle otherSatellitePosition, double otherSatelliteHeight, double otherSatelliteRange) {
        boolean validConnection = false;

        Angle devicePosition = device.getPosition();
        double deviceRange = 0;

        if (device instanceof HandheldDevice) {
            HandheldDevice handheldDevice = (HandheldDevice) device;
            deviceRange = handheldDevice.getMaxRange();
        }
        if (device instanceof LaptopDevice) {
            LaptopDevice laptopDevice = (LaptopDevice) device;
            deviceRange = laptopDevice.getMaxRange();
        }
        if (device instanceof DesktopDevice) {
            DesktopDevice desktopDevice = (DesktopDevice) device;
            deviceRange = desktopDevice.getMaxRange();
        }

        double maxRangeBetweenEntities = Math.min(deviceRange, otherSatelliteRange);
        double distanceBetweenEntities = MathsHelper.getDistance(otherSatelliteHeight, otherSatellitePosition, devicePosition);
        boolean visible = MathsHelper.isVisible(otherSatelliteHeight, otherSatellitePosition, devicePosition);

        if (distanceBetweenEntities < maxRangeBetweenEntities && visible) {
            validConnection = true; 
        }
    
        
        return validConnection;
    }

    // Checking if satellites can connect to devices and other satellites
    /**
     * 
     * @param satellite - Represents an individual to be checked for connections
     * @return
     */
    public List<String> connectableEntities(Satellite satellite) {
        double deviceRange = 0;
        String deviceType;
        Angle devicePosition;

        List<String> listConnectableEntities = new ArrayList<>();

        // For a given satellite try to make valid connections with devices, looks first for possible connection
        for (Device device : listDevice) {
            devicePosition = device.getPosition();
            deviceType = device.getDeviceType();
            if (device instanceof HandheldDevice) {
                HandheldDevice handheldDevice = (HandheldDevice) device;
                deviceRange = handheldDevice.getMaxRange();
            }
            if (device instanceof LaptopDevice) {
                LaptopDevice laptopDevice = (LaptopDevice) device;
                deviceRange = laptopDevice.getMaxRange();
            }
            if (device instanceof DesktopDevice) {
                DesktopDevice desktopDevice = (DesktopDevice) device;
                deviceRange = desktopDevice.getMaxRange();
            }
            if (isSatelliteToDeviceConnectionValid(satellite, devicePosition, deviceType, deviceRange)) {
                listConnectableEntities.add(device.getDeviceId());
            }

        }

        double otherSatelliteRange = 0; 
        Angle otherSatellitePosition;
        double otherSatelliteHeight; 

        // For a given satellite try to make valid connections with other satellites, looks firsrt for possible connection
        for (Satellite uniqueSatellite : listSatellite) {
            otherSatellitePosition = uniqueSatellite.getPosition();
            otherSatelliteHeight = uniqueSatellite.getHeight();
            if (uniqueSatellite instanceof StandardSatellite) {
                StandardSatellite standardSatellite = (StandardSatellite) uniqueSatellite;
                otherSatelliteRange = standardSatellite.getMaxRange();
            }
            if (uniqueSatellite instanceof TeleportingSatellite) {
                TeleportingSatellite teleportingSatellite = (TeleportingSatellite) uniqueSatellite;
                otherSatelliteRange = teleportingSatellite.getMaxRange();
            }
            if (uniqueSatellite instanceof RelaySatellite) {
                RelaySatellite relaySatellite = (RelaySatellite) uniqueSatellite;
                otherSatelliteRange = relaySatellite.getMaxRange();
            }

            if (isSatelliteToSatelliteConnectionValid(satellite, otherSatellitePosition, otherSatelliteHeight, otherSatelliteRange)) {
                // Stops it returning communication with itself being returned
                if (satellite.getSatelliteId() != uniqueSatellite.getSatelliteId()) {
                    listConnectableEntities.add(uniqueSatellite.getSatelliteId());
                }
            }
        }
        return listConnectableEntities;
    }


    /**
     * Determines whether a connection is valid between the given satellite and device.
     *
     * @param satellite The satellite to check connection validity with.
     * @param deviceAngle The angle of the device in relation to the satellite.
     * @param deviceType The type of the device (e.g. "HandheldDevice", "LaptopDevice").
     * @param deviceRange The range of the device.
     * @return A boolean value indicating whether the connection is valid.
     */
    public boolean isSatelliteToDeviceConnectionValid(Satellite satellite, Angle deviceAngle, String deviceType, double deviceRange) {
        boolean validConnection = false;
        boolean validType = false;

        Angle satellitePosition = satellite.getPosition();
        double satelliteHeight = satellite.getHeight();
        double satelliteRange = 0;

        // Check if Satellite and Device type match
        if (satellite instanceof StandardSatellite) {
            StandardSatellite standardSatellite = (StandardSatellite) satellite;
            satelliteRange = standardSatellite.getMaxRange();
            if (deviceType == "HandheldDevice") {
                validType = true; 
            }
            if (deviceType == "LaptopDevice") {
                validType = true;
            }
        }
        else if (satellite instanceof TeleportingSatellite) {
            TeleportingSatellite teleportingSatellite = (TeleportingSatellite) satellite;
            satelliteRange = teleportingSatellite.getMaxRange();
            validType = true;
        } 
        else { 
            return validConnection;
        }

        double maxRangeBetweenEntities = Math.min(satelliteRange, deviceRange);
        double distanceBetweenEntities = MathsHelper.getDistance(satelliteHeight, satellitePosition, deviceAngle);
        boolean visible = MathsHelper.isVisible(satelliteHeight, satellitePosition, deviceAngle);

        if (distanceBetweenEntities < maxRangeBetweenEntities && visible && validType) {
            validConnection = true;
        }
        return validConnection;
    }

    /**
     * Determines whether a connection is valid between the given satellite and other satellite parameters.
     *
     * @param satellite The satellite to check connection validity with.
     * @param otherSatellitePosition The position of the other satellite to check connection validity with.
     * @param otherSatelliteHeight The height of the other satellite to check connection validity with.
     * @param otherSatelliteRange The range of the other satellite to check connection validity with.
     * @return A boolean value indicating whether the connection is valid.
     */
    public boolean isSatelliteToSatelliteConnectionValid(Satellite satellite, Angle otherSatellitePosition, double otherSatelliteHeight, double otherSatelliteRange) {
        boolean validConnection = false;
        Angle satellitePosition = satellite.getPosition();
        double satelliteHeight = satellite.getHeight();
        double satelliteRange = 0;
        
        if (satellite instanceof StandardSatellite) {
            StandardSatellite standardSatellite = (StandardSatellite) satellite;
            satelliteRange = standardSatellite.getMaxRange();
        }

        if (satellite instanceof TeleportingSatellite) {
            TeleportingSatellite teleportingSatellite = (TeleportingSatellite) satellite;
            satelliteRange = teleportingSatellite.getMaxRange();
        }

        if (satellite instanceof RelaySatellite) {
            RelaySatellite relaySatellite = (RelaySatellite) satellite;
            satelliteRange = relaySatellite.getMaxRange();
        }

        double maxRangeBetweenEntities = Math.min(satelliteRange, otherSatelliteRange);
        double distanceBetweenEntities = MathsHelper.getDistance(satelliteHeight, satellitePosition, otherSatelliteHeight, otherSatellitePosition);
        boolean visible = MathsHelper.isVisible(satelliteHeight, satellitePosition, otherSatelliteHeight, otherSatellitePosition);

        if (distanceBetweenEntities < maxRangeBetweenEntities && visible) {
            validConnection = true; 
        }
        return validConnection;
    }
}


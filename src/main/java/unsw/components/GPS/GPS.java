package unsw.components.GPS;

import unsw.components.Satellites.StandardSatellite;
import unsw.components.Satellites.RelaySatellite;
import unsw.components.Satellites.Satellite;
import unsw.components.Satellites.TeleportingSatellite;
import unsw.components.Devices.Device;
import unsw.components.Devices.HandheldDevice;
import unsw.components.Devices.LaptopDevice;
import unsw.components.Devices.DesktopDevice;
import unsw.components.Satellites.Satellite;

import java.util.List;
import java.util.ArrayList;

import unsw.utils.Angle;
import unsw.utils.MathsHelper;

public class GPS {
    private List<Device> listDevice;
    private List<Satellite> listSatellite;

    public GPS(List<Device> deviceList, List<Satellite> satelliteList) {
        this.listDevice = deviceList;
        this.listSatellite = satelliteList;
    }

    // BREAKTHROUGH YES. So we will need to record the device type. the device
    // range. the device position

    // Two step process
    // 1. Get list of all entities in existence (although I may already have that)
    // 2. Create method that goes through each of these entities and checks which
    // ones it can connect with.

    // ALTERNATIVELY !!!!
    // 1. Maybe create a function that checks if the connection isValid

    // This could also be improved a lot by using HashMaps!!!

    // Write a test that creates a bunch of entities and see which ones are connected? Print the results

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

        // For a given satellite try to make valid connections with devices
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

            // If connection is valid, add to list of connectable entities.
            if (isDeviceConnectionValid(satellite, devicePosition, deviceType, deviceRange)) {
                listConnectableEntities.add(device.getDeviceId());
            }

        }

        double otherSatelliteRange = 0; 
        String otherSatelliteType;
        Angle otherSatellitePosition;
        double otherSatelliteHeight; 

        // For a given satellite try to make valid connections with other satellites
        for (Satellite uniqueSatellite : listSatellite) {
            otherSatellitePosition = uniqueSatellite.getPosition();
            otherSatelliteHeight = uniqueSatellite.getHeight();
            otherSatelliteType = uniqueSatellite.getSatelliteType();
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
            if (isSatelliteConnectionValid(satellite, otherSatellitePosition, otherSatelliteHeight, otherSatelliteRange)) {
                listConnectableEntities.add(uniqueSatellite.getSatelliteId());
            }

        }

        return listConnectableEntities;
    }

    // Add another for loop for Satellites.

    public boolean isDeviceConnectionValid(Satellite satellite, Angle deviceAngle, String deviceType,
            double deviceRange) {
        boolean validConnection = false;
        boolean validType = false;

        Angle satellitePosition = satellite.getPosition();
        double satelliteHeight = satellite.getHeight();
        double satelliteRange = 0;

        // Check maxRange of given satellite and check if the satellite can support the
        // type. May be able to repackage this information to be used later on.
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
        if (satellite instanceof TeleportingSatellite) {
            TeleportingSatellite teleportingSatellite = (TeleportingSatellite) satellite;
            satelliteRange = teleportingSatellite.getMaxRange();
            validType = true;
        } else { // This means that it is either a RelaySatellite or invalid Satellite. Which
                 // cannot connect directly to devices.
            return validConnection;
        }

        // Check whether a relay satellite can connect to a device

        double maxRangeBetweenEntities = Math.min(satelliteRange, deviceRange);
        double distanceBetweenEntities = MathsHelper.getDistance(satelliteHeight, satellitePosition, deviceAngle);
        boolean visible = MathsHelper.isVisible(satelliteHeight, satellitePosition, deviceAngle);
        // May also need to check the type of device it is being sent from.
        if (distanceBetweenEntities < maxRangeBetweenEntities && visible && validType) {
            validConnection = true;
        }

        return validConnection;
    }



    public boolean isSatelliteConnectionValid(Satellite satellite, Angle otherSatellitePosition, double otherSatelliteHeight, double otherSatelliteRange) {
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

// List of Priorities
// 1. Get Math Helper functions working

/*
 * // Distance between satellite and other satellite
 * getDistance(satelliteHeight, satelliteAngle, double otherHeight,Angle
 * otherAngle) // Distance between satellite and device getDistance(double
 * satelliteHeight, Angle satelliteAngle, Angle deviceAngle); // Determine if
 * satellite is visible to other satellite isVisible(double satelliteHeight,
 * Angle satelliteAngle, double otherHeight,Angle otherAngle) // Determine is
 * satellite is visible to device isVisible(double satelliteHeight, Angle
 * satelliteAngle, Angle deviceAngle);
 * 
 * 
 * Satellite.getSatelliteType(); StandardSatellite.getMaxRange();
 * 
 * if (satellite_type == "StandardSatellite") { double height =
 * Satellite.getSatelliteHeight(); }
 * 
 * double SatelliteRange = getMaxRange(); if (satellite_type ==
 * "StandardSatellite") { for (Device devices : device) {
 * getDistance(satelliteHeight, satelliteAngle, deviceAngle) double minRange =
 * Math.min(/*SatelliteRange, device.getDeviceRange)
 * 
 * if (device.getDeviceType() == "HandheldDevice" || device.getDeviceType()
 * =="LaptopDevice") } }
 */

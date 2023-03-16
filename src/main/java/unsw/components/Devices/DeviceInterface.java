package unsw.components.Devices;

import unsw.components.Files.File;

import unsw.utils.Angle;

import java.util.List; 

public interface DeviceInterface {
    String getDeviceId();
    String getDeviceType();
    Angle getPosition();
    void setDeviceId(String deviceId);
    void setDeviceType(String deviceType);
    void setPosition(Angle position);
    void addFile(String filename, String content);
    List<File> getDeviceFiles();
    int getMaxRange(); // specific to HandheldDevice and LaptopDevice subclasses
}

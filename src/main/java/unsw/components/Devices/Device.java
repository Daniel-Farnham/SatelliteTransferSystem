package unsw.components.Devices;

import java.util.ArrayList;
import java.util.List;


import unsw.components.Files.File;
import unsw.utils.Angle;

public class Device {
    private String device_id; 
    private String device_type; 
    private Angle position; 
    private List<File> files;

    public Device(String id, String type, Angle position) {
        this.device_id = id; 
        this.device_type = type; 
        this.position = position;  
        this.files = new ArrayList<>();
    }

    public String getDeviceId() {
        return device_id; 
    }

    public String getDeviceType() {
        return device_type; 
    }

    public Angle getPosition() {
        return position; 
    }

    public void setDeviceId(String device_id) {
        this.device_id = device_id; 
    }

    public void setDeviceType(String device_type) {
        this.device_type = device_type; 
    }

    public void setPosition(Angle position) {
        this.position = position; 
    }

    public void addFile(String filename, String content) {
        files.add(new unsw.components.Files.File(filename, content));

    }

    
    public List<File> getDeviceFiles() {
        return files;
    }
    

}

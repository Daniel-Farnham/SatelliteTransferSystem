package unsw.components.Devices;

import java.util.ArrayList;
import java.util.List;

import unsw.components.Files.File;
import unsw.utils.Angle;

public abstract class Device {
    private String device_id; 
    private String device_type; 
    private Angle position; 
    private List<File> files;
    private int byteIndex;
    private boolean transferComplete = false; 

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


    public String sendBytes(String filename, String content) {
        char[] fileContent = content.toCharArray();
        String bytesSent = "";

        if (byteIndex < content.length()) {
            bytesSent += fileContent[byteIndex]; 
            byteIndex++; 
            transferComplete = false;
            
        }
        else if (byteIndex == content.length()) {
            transferComplete = true; 
        }
        
        return bytesSent; 
    
    }
    

    public void receiveBytes(boolean transferComplete, String filename, String bytesReceived) {
        String fileContent = "";
        
        if (transferComplete == false) {
            fileContent += bytesReceived; 
        }
        else if (transferComplete == true) {
            addFile(filename, fileContent); // This also may not be necessary 
        }
    }
    
    public boolean getTransferComplete() {
        return transferComplete; 
    }
}

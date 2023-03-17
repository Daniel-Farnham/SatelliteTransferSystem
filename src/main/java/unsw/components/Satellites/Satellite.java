package unsw.components.Satellites;

import java.util.ArrayList;
import java.util.List;

import unsw.components.Files.File;
import unsw.utils.Angle;

public abstract class Satellite {
    private String satellite_id; 
    private String satellite_type; 
    private Double height; 
    private Angle position;
    private List<File> files;

    public Satellite(String id, String type, Double height, Angle position) {
        this.satellite_id = id;
        this.satellite_type = type; 
        this.height = height;
        this.position = position;
        this.files = new ArrayList<>();
    }

    public String getSatelliteId() {
        return satellite_id;
    }

    public String getSatelliteType() {
        return satellite_type;
    }

    public Double getHeight() {
        return height;
    }

    public Angle getPosition() {
        return position;
    }

    public void setSatelliteId(String satellite_id) {
        this.satellite_id = satellite_id;
    }

    public void setSatelliteType(String satellite_type) {
        this.satellite_type = satellite_type;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setPosition(Angle position) {
        this.position = position;
    }

    public List<File> getSatelliteFiles() {
        return files;
    }

    public abstract void updatePosition();

    public abstract boolean getTransferComplete(); 

    public abstract boolean canStoreFile(String content);
    
    public abstract String sendBytes(String filename, String content);

    public abstract void receiveBytes(boolean transferComplete, String filename, String bytesReceived);
    
    public void addSatelliteFile(String filename, String content) {
        files.add(new unsw.components.Files.File(filename, content));
    }

}

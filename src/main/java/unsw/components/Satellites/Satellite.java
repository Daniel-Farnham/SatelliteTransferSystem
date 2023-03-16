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

    // The abstract method updatePosition() that will be implemented by each subtype of Satellite
    public abstract void updatePosition();

    //public abstract List<String> connectableEntities(String satellite_type);

    public void addSatelliteFile(String filename, String content) {
        files.add(new unsw.components.Files.File(filename, content));
    }

    public void removeSatelliteFile(String filename) {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getFilename().equals(filename)) {
                files.remove(i);
                return;
            }
        }
    }

    public List<File> getSatelliteFiles() {
        return files;
    }


}

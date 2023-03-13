package unsw.components.Satellites;

import unsw.utils.Angle;

public class Satellite {
    private String satellite_id; 
    private String satellite_type; 
    private Double height; 
    private Angle position;
    
    public Satellite(String id, String type, Double height, Angle position) {
        this.satellite_id = id;
        this.satellite_type = type; 
        this.height = height;
        this.position = position;
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

}

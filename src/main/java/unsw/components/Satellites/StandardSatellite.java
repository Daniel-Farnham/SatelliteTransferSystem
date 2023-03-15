package unsw.components.Satellites;

import unsw.components.Files.File;
import unsw.utils.Angle;

public class StandardSatellite extends Satellite {
    private static final int LINEAR_SPEED = 2500; // kilometres per minute
    private static final int MAX_RANGE = 150000; // kilometres 
    private static final int MAX_FILES = 3;
    private static final int MAX_BYTES = 80;
    private static final int BYTES_PER_MINUTE = 1;
    //Device support?
    
    private int filesStored = 0;
    private int bytesStored = 0;

    public StandardSatellite(String id, Double height, Angle position) {
        super(id, "StandardSatellite", height, position);
    }
    
    public int getLinearSpeed() {
        return LINEAR_SPEED;
    }
    
    public int getMaxRange() {
        return MAX_RANGE;
    }
    
    public int getMaxFiles() {
        return MAX_FILES;
    }
    
    public int getMaxBytes() {
        return MAX_BYTES;
    }
    
    public int getBytesPerMinute() {
        return BYTES_PER_MINUTE;
    }
     

    public boolean canStoreFile(String filename, String content) {
        File fileInSatellite = new File(filename, content);
        if (filesStored >= MAX_FILES || bytesStored + fileInSatellite.getFilesize() > MAX_BYTES) {
            return false;
        }
        return true;
        
    }
    
    public void addFile(String filename, String content) {
        if (!canStoreFile(filename, content)) {
            return;
        }

        File fileInSatellite = new File(filename, content); 
        filesStored++;
        bytesStored += fileInSatellite.getFilesize(); 
        addSatelliteFile(filename, content);

    }

    // Need to work on this: A problem is that am I making multiple instances of the same object in the File class? 
    public void removeFile(String filename, String content) {
        File fileInSatellite = new File (filename, content);
        filesStored--;
        bytesStored -= fileInSatellite.getFilesize();
        removeSatelliteFile(filename);
    }


    public double getAngularVelocity() {
        double linearSpeed = getLinearSpeed();
        double radius = getHeight();
        double angularVelocity = linearSpeed/radius; 
        return angularVelocity; 

        // angularVelocity * minute = radians 
    }


    public void updatePosition() {
        Angle angleChange = Angle.fromRadians(-getAngularVelocity() * 1); 
        Angle newPosition = getPosition().add(angleChange);
        // Check if newPosition is less than 0 or more than 360 degrees. 
        double degrees = newPosition.toDegrees();
        if (degrees < 0) {
            degrees += 360; 
        }
        newPosition = Angle.fromDegrees(degrees);
        setPosition(newPosition);
    
    }
    

    
}
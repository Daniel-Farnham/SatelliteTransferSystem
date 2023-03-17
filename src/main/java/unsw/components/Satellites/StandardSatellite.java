package unsw.components.Satellites;

import unsw.utils.Angle;


public class StandardSatellite extends Satellite {
    private static final int LINEAR_SPEED = 2500; 
    private static final int MAX_RANGE = 150000; 
    private static final int MAX_FILES = 3;
    private static final int MAX_BYTES = 80;
    private static final int BYTES_PER_MINUTE = 1;
    private int byteIndex = 0;
    private boolean transferComplete = false; 
    
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

    public double getAngularVelocity() {
        double linearSpeed = getLinearSpeed();
        double radius = getHeight();
        double angularVelocity = linearSpeed/radius; 
        return angularVelocity; 

    }

    public void updatePosition() {
        Angle angleChange = Angle.fromRadians(-getAngularVelocity() * 1); 
        Angle newPosition = getPosition().add(angleChange);
        double degrees = newPosition.toDegrees();
        if (degrees < 0) {
            degrees += 360; 
        }
        newPosition = Angle.fromDegrees(degrees);
        setPosition(newPosition);
    }

    public boolean canStoreFile(String content) {
        if (filesStored >= MAX_FILES || bytesStored + content.length() > MAX_BYTES) {
            return false;
        }
        return true;
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
            bytesStored += bytesReceived.length(); 
        }
        else if (transferComplete == true) {
            filesStored++; 
            addSatelliteFile(filename, fileContent); 
        }
    }

    public boolean getTransferComplete() {
        return transferComplete; 
    }   
    

    
}
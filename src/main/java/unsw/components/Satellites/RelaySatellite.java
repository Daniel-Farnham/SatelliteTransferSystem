package unsw.components.Satellites;

import java.util.ArrayList;
import java.util.List;

import unsw.utils.Angle;

public class RelaySatellite extends Satellite {
    private static final double LINEAR_SPEED = 1500; 
    private static final double MAX_RANGE = 300000; 
    private boolean isDirectionClockwise = true; 
    

    public RelaySatellite(String id, Double height, Angle position) {
        super(id, "RelaySatellite", height, position);
    }

    public double getLinearSpeed() {
        return LINEAR_SPEED;
    }
    
    public double getMaxRange() {
        return MAX_RANGE;
    }

    public double getAngularVelocity() {
        double linearSpeed = getLinearSpeed();
        double radius = getHeight();
        double angularVelocity = linearSpeed/radius; 
        return angularVelocity; 

    }



    public void updatePosition() {
         
        Angle currentPosition = getPosition(); 
        double currentPositionInDegrees = currentPosition.toDegrees(); 
         
        
        if (currentPositionInDegrees > 140 && currentPositionInDegrees < 190) {
            if (isDirectionClockwise) {
                Angle angleChange = Angle.fromRadians(-getAngularVelocity() * 1); 
                Angle newPosition = getPosition().add(angleChange);
                setPosition(newPosition);
            }
            if (!isDirectionClockwise) {
                Angle angleChange = Angle.fromRadians(getAngularVelocity() * 1); 
                Angle newPosition = getPosition().add(angleChange);
                setPosition(newPosition);
            }
        }
        else {
            if (currentPositionInDegrees >= 345) { 
                currentPositionInDegrees = currentPositionInDegrees - 360; 
            }
            if (currentPositionInDegrees < 140) {
                isDirectionClockwise = false; 
                Angle angleChange = Angle.fromRadians(getAngularVelocity() * 1); 
                Angle newPosition = getPosition().add(angleChange);
                double degrees = newPosition.toDegrees();
                if (degrees > 360) {
                    degrees -= 360; 
                }
                newPosition = Angle.fromDegrees(degrees);
                setPosition(newPosition);
            }
            if (currentPositionInDegrees > 190 && currentPositionInDegrees < 345) {
                isDirectionClockwise = true; 
                Angle angleChange = Angle.fromRadians(-getAngularVelocity() * 1); 
                Angle newPosition = getPosition().add(angleChange);
                setPosition(newPosition);
            }
        }
        
    }

    // Below methods were part of the incomplete sendFile and have been commented out to prevent compilation errors.

    public boolean getTransferComplete() {
        return false; 
    }

    public boolean canStoreFile(String content) {
        /* 
        if (filesStored >= MAX_FILES || bytesStored + content.length() > MAX_BYTES) {
            return false;
        }
        return true;
        */
        return false; 
    }

    public String sendBytes(String filename, String content) {
        /* 
        boolean transferComplete;
        char[] fileContent = content.toCharArray();
        String currByte = "";
        
        if (byteIndex < content.length()) {
            currByte += fileContent[byteIndex]; 
            byteIndex++; 
            //bytesStored--;
            transferComplete = false;
            
        }
        else if (byteIndex == content.length()) {
            //filesStored--; 
            //removeSatelliteFile(filename); //This might not be necessary
            transferComplete = true; 
        }
        else {
            transferComplete = true; 
        }

        return currByte; 
        */
        return "delete Me"; 
    }


    public void receiveBytes(boolean transferComplete, String filename, String bytesReceived) {        
        /* 
        String fileContent = "";
    
        if (transferComplete == false) {
            fileContent += byteReceived; 
            bytesStored++; 
        }
        else if (transferComplete == true) {
            filesStored++; 
            //addSatelliteFile(filename, fileContent); // This also may not be necessary 
        }
        */
    }

   
    
    
}

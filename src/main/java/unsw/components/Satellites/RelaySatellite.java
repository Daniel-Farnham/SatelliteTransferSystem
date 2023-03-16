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

    /* 
    public List<String> connectableEntities(String satellite_type) {
        return new ArrayList<>();
    }
    */
    
    
}

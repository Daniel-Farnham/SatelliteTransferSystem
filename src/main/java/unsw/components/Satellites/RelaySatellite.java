package unsw.components.Satellites;

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

    // Current problems: 
    //   1. It doesn't bounce between the desired thresholds of 140 - 190. 
    //   2. There is no transition between 0 and 360. 

    public void updatePosition() {
         
        Angle currentPosition = getPosition(); 
        double currentPositionInDegrees = currentPosition.toDegrees(); 
         
        // Pick direction to travel

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
            // lets say we have a distance of 300. 
            // if it is between 190 - 345. We go clockwise. 
            // if it is between 345 - 140. We go anticlockwise
            // Calculate shortest distance 
            //double shortestDistance = 345 - currentPositionInDegrees; 
            // Just outside the boundary. 
            // We have bounce!! 
            // Reset position so that currentPositionInDegrees is less than 140. 
            if (currentPositionInDegrees >= 345) { // this stuff doesn't work. But maybe we can look at it later on. 
                currentPositionInDegrees = currentPositionInDegrees - 360; 
            }
            // Current problem is that the satellite won't travel in a circular direction. We are in the right area
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
        
        
        /* 
        if (currentPositionInDegrees < 140 || currentPositionInDegrees > 190) {

            
            // Resets degrees between 0 - 360. 
            if (currentPositionInDegrees < 0) {
                currentPositionInDegrees += 360;
            } else if (currentPositionInDegrees > 360) {
                currentPositionInDegrees -= 360;
            }

            if (currentPositionInDegrees >= 345) {
                isDirectionClockwise = false; // move in positive direction (anti-clockwise)
            }
            else if (currentPositionInDegrees < 345) {
                isDirectionClockwise = true; 
            }
            
        }
        */
        
        // Maybe add some logic 

        /* 
        
        if (!isDirectionClockwise) {
            Angle angleChange = Angle.fromRadians(getAngularVelocity() * 1); 
            Angle newPosition = getPosition().add(angleChange);
            setPosition(newPosition);
        }
        if (isDirectionClockwise) {
            Angle angleChange = Angle.fromRadians(-getAngularVelocity() * 1); 
            Angle newPosition = getPosition().add(angleChange);
            setPosition(newPosition);
        }
        */
        
        
    }
    
    
}

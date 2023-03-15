package unsw.components.Satellites;

import unsw.utils.Angle;

public class RelaySatellite extends Satellite {
    private static final double LINEAR_SPEED = 1500; 
    private static final double MAX_RANGE = 300000; 
    

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

        // angularVelocity * minute = radians 
    }

    // Might need to check changing from degrees to radians and back again
    // might also need to check direction
    // also need to make considerations for 
    public void updatePosition() {
        Angle angleChange = Angle.fromRadians(getAngularVelocity() * 1); 
        Angle newPosition = getPosition().add(angleChange);
        setPosition(newPosition);
    }
    // Calculate angular velocity. Assume Angle is in radians. 
    
}

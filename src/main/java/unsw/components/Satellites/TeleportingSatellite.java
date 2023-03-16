package unsw.components.Satellites;

import unsw.components.Files.File;
import unsw.utils.Angle;

public class TeleportingSatellite extends Satellite {
    private static final int LINEAR_SPEED = 1000; // kilometers per minute
    private static final int MAX_RANGE = 200000; // kilometers 
    private static final int MAX_BYTES = 200;
    private static final int BYTES_PER_MINUTE_RECEIVE = 15;
    private static final int BYTES_PER_MINUTE_SEND = 10;
    private static final char T_CHARACTER = 't';
    private boolean isDirectionClockwise = false; 

    public TeleportingSatellite(String id, Double height, Angle position) {
        super(id, "TeleportingSatellite", height, position);
    }

    public int getLinearSpeed() {
        return LINEAR_SPEED;
    }

    public int getMaxRange() {
        return MAX_RANGE;
    }

    public int getMaxBytes() {
        return MAX_BYTES;
    }

    public int getBytesPerMinuteReceive() {
        return BYTES_PER_MINUTE_RECEIVE;
    }

    public int getBytesPerMinuteSend() {
        return BYTES_PER_MINUTE_SEND;
    }

    public double getAngularVelocity() {
        double linearSpeed = getLinearSpeed();
        double radius = getHeight();
        double angularVelocity = linearSpeed/radius; 
        return angularVelocity; 

        // angularVelocity * minute = radians 
    }

    public void updatePosition() {
        if (!isDirectionClockwise) {
            Angle angleChange = Angle.fromRadians(getAngularVelocity() * 1); 
            Angle newPosition = getPosition().add(angleChange);
            double degrees = newPosition.toDegrees();
            if (degrees > 180) {
                setPosition(Angle.fromDegrees(0)); 
                isDirectionClockwise = true;
            }
            else {
                setPosition(newPosition);
            }
        }

        if (isDirectionClockwise) {
            Angle angleChange = Angle.fromRadians(-getAngularVelocity() * 1); 
            Angle newPosition = getPosition().add(angleChange);
            double degrees = newPosition.toDegrees();
            if (degrees < 180) {
                setPosition(Angle.fromDegrees(0)); 
                isDirectionClockwise = false;
            }
            else {
                setPosition(newPosition);
            }
        }  

    }
   
}

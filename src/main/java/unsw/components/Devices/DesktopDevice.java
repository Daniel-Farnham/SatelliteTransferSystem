package unsw.components.Devices;

import unsw.utils.Angle;


public class DesktopDevice extends Device {
    private static final int MAX_RANGE = 200000;

    public DesktopDevice(String id, Angle position) {
        super(id, "DesktopDevice", position);
    }

    public int getMaxRange() {
        return MAX_RANGE;
    }
}
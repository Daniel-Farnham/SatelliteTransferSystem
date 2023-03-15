package unsw.components.Devices;

import unsw.utils.Angle;


public class LaptopDevice extends Device {
    private static final int MAX_RANGE = 100000;

    public LaptopDevice(String id, Angle position) {
        super(id, "LaptopDevice", position);
    }

    public int getMaxRange() {
        return MAX_RANGE;
    }
}


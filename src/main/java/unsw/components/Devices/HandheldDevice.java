package unsw.components.Devices;

import unsw.utils.Angle;


public class HandheldDevice extends Device implements DeviceInterface {

        private static final int MAX_RANGE = 50000;

        public HandheldDevice(String id, Angle position) {
            super(id, "HandheldDevice", position);
        }

        public int getMaxRange() {
            return MAX_RANGE;
        }
}

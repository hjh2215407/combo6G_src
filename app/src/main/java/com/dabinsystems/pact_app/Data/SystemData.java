package com.dabinsystems.pact_app.Data;

public class SystemData {

    public enum LOCK_STATUS {
        OFF(0),
        ON(1),
        HOLDOVER(2);

        int val;
        LOCK_STATUS(int onOff) {
            val = onOff;
        }

        public int getStatus() {
            return val;
        }
    }

    public enum CLOCK_SOURCE {

        INTERNAL(0, "Internal"),
        GPS(1, "GPS"),
        EXT10MHZ(2, "Ext. 10MHz");

        int val;
        String name;

        CLOCK_SOURCE(int source, String name) {

            val = source;
            this.name = name;

        }

        public int getVal() {
            return val;
        }
        public String getName() {return name;}
    }

    CLOCK_SOURCE source = CLOCK_SOURCE.INTERNAL;
    LOCK_STATUS lockStatus = LOCK_STATUS.OFF;

    public CLOCK_SOURCE getSource() {
        return source;
    }

    public void setSource(CLOCK_SOURCE source) {
        this.source = source;
    }

    public LOCK_STATUS getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(LOCK_STATUS lockStatus) {
        this.lockStatus = lockStatus;
    }
}

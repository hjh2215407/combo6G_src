package com.dabinsystems.pact_app.Data.ModAccuracy;

import com.dabinsystems.pact_app.Handler.DataHandler;

public class TriggerSource {

    public enum TRIGGER_SOURCE {

        INTERNAL(0, "Internal"),
        GPS(1, "GPS"),
        EXT1PPS(2, "Ext. 1pps");

        private final int Value;
        private final String Name;

        TRIGGER_SOURCE(int val, String name) {

            Value = val;
            Name = name;

        }

        public String getHexString(){

            return DataHandler.getInstance().intToHexStr(Value);

        }

        public int getValue() {

            return Value;

        }

        public String getModeString() {

            return Name;

        }

        public byte getByte() {

            int val = Value & 0xff;
            return (byte)val;
        }

    }

    private TRIGGER_SOURCE mTriggerSource = TRIGGER_SOURCE.INTERNAL;

    public TRIGGER_SOURCE getTriggerSource() {
        return mTriggerSource;
    }

    public void setTriggerSource(TRIGGER_SOURCE mTriggerSource) {
        this.mTriggerSource = mTriggerSource;
    }

}

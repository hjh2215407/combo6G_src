package com.dabinsystems.pact_app.Data.ModAccuracy.NR5G;

import com.dabinsystems.pact_app.Handler.DataHandler;

public class NrDuplexData {

    public enum DUPLEX_TYPE {
        FDD(0, "FDD"),
        TDD(1, "TDD");

        private final int Value;
        private final String Type;

        DUPLEX_TYPE(int val, String type) {
            Value = val;
            Type = type;
        }

        public String getHexString(){

            return DataHandler.getInstance().intToHexStr(Value);

        }

        public int getValue() {

            return Value;

        }

        public String getTypeString() {

            return Type;

        }

        public byte getByte() {

            int val = Value & 0xff;
            return (byte)val;
        }
    }

    private DUPLEX_TYPE mDuplexType = DUPLEX_TYPE.TDD; // default value : TDD

    public void setDuplexType(DUPLEX_TYPE dType) {
        mDuplexType = dType;
    }

    public DUPLEX_TYPE getDuplexType() {
        return mDuplexType;
    }
}

package com.dabinsystems.pact_app.Data.VSWR;

import com.dabinsystems.pact_app.Handler.DataHandler;

public class WindowingData {

    public enum WINDOWING {

        RECTANGULAR(0x00, "Rectangular"),
        NOMINAL_SIDE_LOBE(0x01, "Nominal Side Lobe"),
        LOW_SIDE_LOBE(0x02, "Low Side Lobe"),
        MINIMUM_SIDE_LOBE(0x03, "Minimum Side Lobe");

        private final int value;
        private String valueStr;

        WINDOWING(int val, String str) {

            value = val;
            valueStr = str;

        }

        public int getValue() {
            return value;
        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(value);

        }

        public String getString() {

            return valueStr;

        }

        public byte getByte() {

            byte b = (byte)value;
            return b;

        }

    }

    WINDOWING mSelectedMode = WINDOWING.RECTANGULAR;

    public void setMode(WINDOWING mode) {

        mSelectedMode = mode;

    }

    public WINDOWING getMode() {
        return mSelectedMode;
    }

}

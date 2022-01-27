package com.dabinsystems.pact_app.Data.SA;

public class BWData {

    public enum BAND_WIDTH {

        HZ1(0, "1 Hz", 0.001f),
        HZ300(5, "300 Hz", 0.3f),
        KHZ1(6, "1 kHz", 1f),
        KHZ3(7, "3 kHz", 3f),
        KHZ10(8, "10 kHz", 10f),
        KHZ30(9, "30 kHz", 30f),
        KHZ100(10, "100 kHz", 100f),
        KHZ300(11, "300 kHz", 300f),
        MHZ1(12, "1 MHz", 1000f),
        MHZ3(13, "3 MHz", 3000f);

        private final int mCmd;
        private final float mValue;
        private String mValueStr;

        BAND_WIDTH(int cmd, String valueStr, float val) {

            mCmd = cmd;
            mValueStr = valueStr;
            mValue = val;
        }

        public float getValue() {

            return mValue;

        }

        public int getCmdValue() {

            return mCmd;

        }

        public byte getByte() {

            return (byte)mCmd;
        }

        public String getString() {
            return mValueStr;
        }

    }

    private BAND_WIDTH mSelectedRBW = BAND_WIDTH.KHZ100;
    private BAND_WIDTH mSelectedVBW = BAND_WIDTH.KHZ100;

    public void setRBW(BAND_WIDTH bw) {

        mSelectedRBW = bw;

    }

    public void setRBW(int val) {

        if(BAND_WIDTH.KHZ1.getValue() == val) {

            mSelectedRBW = BAND_WIDTH.KHZ1;

        } else if(BAND_WIDTH.KHZ3.getValue() == val) {

            mSelectedRBW = BAND_WIDTH.KHZ3;

        } else if(BAND_WIDTH.KHZ10.getValue() == val) {


            mSelectedRBW = BAND_WIDTH.KHZ10;

        } else if(BAND_WIDTH.KHZ30.getValue() == val) {

            mSelectedRBW = BAND_WIDTH.KHZ30;

        } else if(BAND_WIDTH.KHZ100.getValue() == val) {


            mSelectedRBW = BAND_WIDTH.KHZ100;

        } else if(BAND_WIDTH.KHZ300.getValue() == val) {


            mSelectedRBW = BAND_WIDTH.KHZ300;

        } else if(BAND_WIDTH.MHZ1.getValue() == val) {

            mSelectedRBW = BAND_WIDTH.MHZ1;

        } else if(BAND_WIDTH.MHZ3.getValue() == val) {

            mSelectedRBW = BAND_WIDTH.MHZ3;

        } else {

            mSelectedRBW = BAND_WIDTH.KHZ1;

        }

    }

    public BAND_WIDTH getRBW() {

        return mSelectedRBW;
    }

    public void setVBW(BAND_WIDTH bw) {

        mSelectedVBW = bw;
    }

    public void setVBW(int val) {

        if(BAND_WIDTH.KHZ1.getValue() == val) {

            mSelectedVBW = BAND_WIDTH.KHZ1;

        } else if(BAND_WIDTH.KHZ3.getValue() == val) {

            mSelectedVBW = BAND_WIDTH.KHZ3;

        } else if(BAND_WIDTH.KHZ10.getValue() == val) {


            mSelectedVBW = BAND_WIDTH.KHZ10;

        } else if(BAND_WIDTH.KHZ30.getValue() == val) {

            mSelectedVBW = BAND_WIDTH.KHZ30;

        } else if(BAND_WIDTH.KHZ100.getValue() == val) {


            mSelectedVBW = BAND_WIDTH.KHZ100;

        } else if(BAND_WIDTH.KHZ300.getValue() == val) {


            mSelectedVBW = BAND_WIDTH.KHZ300;

        } else if(BAND_WIDTH.MHZ1.getValue() == val) {

            mSelectedVBW = BAND_WIDTH.MHZ1;

        } else if(BAND_WIDTH.MHZ3.getValue() == val) {

            mSelectedVBW = BAND_WIDTH.MHZ3;

        } else {

            mSelectedVBW = BAND_WIDTH.KHZ1;

        }

    }

    public BAND_WIDTH getVBW() {

        return mSelectedVBW;
    }

}

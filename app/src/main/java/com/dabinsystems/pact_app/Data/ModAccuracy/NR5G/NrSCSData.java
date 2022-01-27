package com.dabinsystems.pact_app.Data.ModAccuracy.NR5G;

import com.dabinsystems.pact_app.Handler.DataHandler;

public class NrSCSData {

    public enum SCS {
        KHZ15(0, "15 kHz", 15),
        KHZ30(1, "30 kHz", 30);

        private final int  mCmd;
        private final int mValue;
        private String mValueStr;

        SCS(int cmd, String valueStr, int val) {
            mCmd = cmd;
            mValueStr = valueStr;
            mValue = val;
        }

        public int getValue() {
            return mValue;
        }

        public String getHexString() {
            return DataHandler.getInstance().intToHexStr(mCmd);
        }

        public int getCmd() {
            return mCmd;
        }

        public byte getByte() {
            return (byte) mCmd;
        }

        public String getString() {
            return mValueStr;
        }
    }

    private SCS mSelectedSCS = SCS.KHZ30;

    public void setSCS(SCS scs) {
        mSelectedSCS = scs;
    }

    public SCS getSCS() {
        return mSelectedSCS;
    }
}

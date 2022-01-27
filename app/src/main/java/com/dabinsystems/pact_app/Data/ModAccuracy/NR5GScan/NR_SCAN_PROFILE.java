package com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan;

public enum NR_SCAN_PROFILE {
    MHZ5(0, "5 MHz", 5),
    MHZ10(1, "10 MHz", 10),
    MHZ15(2, "15 MHz", 15),
    MHZ20(3, "20 MHz", 20),
    MHZ25(4, "25 MHz", 25),
    MHZ30(5, "30 MHz", 30),
    MHZ40(6, "40 MHz", 40),
    MHZ50(7, "50 MHz", 50),
    MHZ60(8, "60 MHz", 60),
    MHZ70(9, "70 MHz", 70),
    MHZ80(10, "80 MHz", 80),
    MHZ90(11, "90 MHz", 90),
    MHZ100(12, "100 MHz", 100);

    private final int mCmd;
    private final int mValue;
    private final String mValueStr;

    NR_SCAN_PROFILE(int cmd, String valueStr, int val) {
        mCmd = cmd;
        mValueStr = valueStr;
        mValue = val;
    }

    public int getValue() {
        return mValue;
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

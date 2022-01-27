package com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan;

public enum NR_SCAN_TRACE {
    PCI("PCI", 0, 0, 1007),
    SS_RSRP("SS-RSRP", 1, -120, -20),
    SS_SINR("SS-SINR", 2, -20, 40),
    TimingOffset("Timing Offset", 3, -5, 5);

    private final String mValueStr;
    private final int mValue;
    private final int ScaleDefaultMin, ScaleDefaultMax;

    NR_SCAN_TRACE(String valueStr, int val, int min, int max) {
        mValueStr = valueStr;
        mValue = val;
        this.ScaleDefaultMin = min;
        this.ScaleDefaultMax = max;
    }

    public int getValue() {
        return mValue;
    }

    public String getString() {
        return mValueStr;
    }

    public int getScaleDefaultMin() {
        return ScaleDefaultMin;
    }

    public int getScaleDefaultMax() {
        return ScaleDefaultMax;
    }
}

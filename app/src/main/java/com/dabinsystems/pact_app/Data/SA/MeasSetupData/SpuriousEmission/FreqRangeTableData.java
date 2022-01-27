package com.dabinsystems.pact_app.Data.SA.MeasSetupData.SpuriousEmission;

import com.dabinsystems.pact_app.Data.SA.BWData;

public class FreqRangeTableData {

    private boolean State;

    private double StartFreq;
    private double StopFreq;

    private BWData BW;

    private float AbsStartLimit;
    private float AbsStopLimit;

    private int peakIdx = -1;


    public FreqRangeTableData(boolean state, double start, double stop, BWData.BAND_WIDTH rbw, BWData.BAND_WIDTH vbw, float absStartLimit, float absStopLimit) {

        State = state;
        StartFreq = start;
        StopFreq = stop;
        BW = new BWData();
        BW.setRBW(rbw);
        BW.setVBW(vbw);
        AbsStartLimit = absStartLimit;
        AbsStopLimit = absStopLimit;

    }

    public boolean isState() {
        return State;
    }

    public void setState(boolean state) {
        State = state;
    }

    public double getStartFreq() {
        return StartFreq;
    }

    public void setStartFreq(double startFreq) {
        StartFreq = startFreq;
    }

    public double getStopFreq() {
        return StopFreq;
    }

    public void setStopFreq(double stopFreq) {
        StopFreq = stopFreq;
    }

    public BWData getBW() {
        return BW;
    }

    public void setBW(BWData BW) {
        this.BW = BW;
    }

    public float getAbsStartLimit() {
        return AbsStartLimit;
    }

    public void setAbsStartLimit(float absStartLimit) {
        AbsStartLimit = absStartLimit;
    }

    public float getAbsStopLimit() {
        return AbsStopLimit;
    }

    public void setAbsStopLimit(float absStopLimit) {
        AbsStopLimit = absStopLimit;
    }

    public int getPeakIdx() {
        return peakIdx;
    }

    public void setPeakIdx(int peakIdx) {
        this.peakIdx = peakIdx;
    }
}

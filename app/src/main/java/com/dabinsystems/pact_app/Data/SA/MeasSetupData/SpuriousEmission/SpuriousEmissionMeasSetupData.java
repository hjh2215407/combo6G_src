package com.dabinsystems.pact_app.Data.SA.MeasSetupData.SpuriousEmission;

import com.dabinsystems.pact_app.Data.SA.BWData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.MeasSetupData;

import java.util.ArrayList;

public class SpuriousEmissionMeasSetupData extends MeasSetupData {

    ArrayList<FreqRangeTableData> mFreqRangeTableDataList;

    int SelectedIndex = 0;

    double LowFreq = 3300f;
    double HighFreq = 3800f;

    private AVG_HOLD_MODE AvgMode;
    private int AvgHoldNum = 10;

    protected final int AVG_MIN = 1;
    protected final int AVG_MAX = 200;

    public SpuriousEmissionMeasSetupData() {

        mFreqRangeTableDataList = new ArrayList<>();
        mFreqRangeTableDataList.add(new FreqRangeTableData(true, 30d, 1000d, BWData.BAND_WIDTH.KHZ100, BWData.BAND_WIDTH.KHZ10, -13, -13));
        mFreqRangeTableDataList.add(new FreqRangeTableData(true, 1000d, 12750d, BWData.BAND_WIDTH.MHZ1, BWData.BAND_WIDTH.KHZ100, -13, -13));
        mFreqRangeTableDataList.add(new FreqRangeTableData(true, 12750d, 18500d, BWData.BAND_WIDTH.MHZ1, BWData.BAND_WIDTH.KHZ100, -13, -13));
        mFreqRangeTableDataList.add(new FreqRangeTableData(true, 18500d, 26500d, BWData.BAND_WIDTH.MHZ1, BWData.BAND_WIDTH.KHZ100, -13, -13));
        mFreqRangeTableDataList.add(new FreqRangeTableData(true, 18500d, 26500d, BWData.BAND_WIDTH.MHZ1, BWData.BAND_WIDTH.KHZ100, -13, -13));
        mFreqRangeTableDataList.add(new FreqRangeTableData(true, 18500d, 26500d, BWData.BAND_WIDTH.MHZ1, BWData.BAND_WIDTH.KHZ100, -13, -13));

    }

    public void setAvgHold(int val) {

        if (val < AVG_MIN || val > AVG_MAX) return;

        AvgHoldNum = val;

    }

    public int getAvgHold() {

        return AvgHoldNum;

    }

    public AVG_HOLD_MODE getAvgMode() {

        return AvgMode;

    }

    public Boolean isOnAvg() {

        if (AvgMode == AVG_HOLD_MODE.ON) return true;
        else return false;


    }

    public void setAvgMode(AVG_HOLD_MODE onOff) {

        AvgMode = onOff;

    }

    public void setAvgMode(int mode) {

        if (mode == 0) AvgMode = AVG_HOLD_MODE.OFF;
        else AvgMode = AVG_HOLD_MODE.ON;

    }

    public void setAvgMode(Boolean onOff) {

        if (!onOff) AvgMode = AVG_HOLD_MODE.OFF;
        else AvgMode = AVG_HOLD_MODE.ON;

    }

    public double getLowFreq() {
        return LowFreq;
    }

    public void setLowFreq(double lowFreq) {
        LowFreq = lowFreq;
    }

    public double getHighFreq() {
        return HighFreq;
    }

    public void setHighFreq(double highFreq) {
        HighFreq = highFreq;
    }

    public ArrayList<FreqRangeTableData> getFreqRangeTableDataList() {
        return mFreqRangeTableDataList;
    }

    public double getMinStartFreq() {

        int size = mFreqRangeTableDataList.size();
        double minStartFreq = mFreqRangeTableDataList.get(0).getStartFreq();

        for(int i=1; i<size; i++) {
            double start = mFreqRangeTableDataList.get(i).getStartFreq();
            if(start < minStartFreq) minStartFreq = start;
        }

        return minStartFreq;

    }

    public double getMaxStopFreq() {

        int size = mFreqRangeTableDataList.size();
        double maxStopFreq = mFreqRangeTableDataList.get(0).getStopFreq();

        for(int i=1; i<size; i++) {
            double stop = mFreqRangeTableDataList.get(i).getStopFreq();
            if(stop > maxStopFreq) maxStopFreq = stop;
        }

        return maxStopFreq;

    }

    public int getSelectedIndex() {
        return SelectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        SelectedIndex = selectedIndex;
    }
}

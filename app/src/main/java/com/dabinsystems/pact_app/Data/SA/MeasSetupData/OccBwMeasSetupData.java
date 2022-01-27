package com.dabinsystems.pact_app.Data.SA.MeasSetupData;

import android.util.Log;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrCarrierSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrOffsetSetupData;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

public class OccBwMeasSetupData extends MeasSetupData {

    private AVG_HOLD_MODE AvgMode;

    private int AvgHoldNum = 100;
    private final int MIN = 1;
    private final int MAX = 200;

    /*
     * Occupied BW
     * */

    private int mOBWPower = 9900;
    private final int MIN_OBW = 1;
    private final int MAX_OBW = 9999;

    private int mXdB = -2600;
    private final int MIN_XDB = -10000;
    private final int MAX_XDB = -10;

    private Float mOccBW = null;
    private Float mTotalPower = null;
    private Float mXDBBW = null;

    private AclrCarrierSetupData CarrierSetupData;
    private AclrOffsetSetupData OffsetSetupData;

    private Integer LowerPowerIndex = null;
    private Integer UpperPowerIndex = null;

    public OccBwMeasSetupData() {

        AvgMode = AVG_HOLD_MODE.ON;
        AvgHoldNum = 100;

    }

    public void setAvgHold(int val) {

        if (val < MIN || val > MAX) return;

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


    /*Occupied BW Meas setup*/

    public void setOBWPower(float val) {

        int result = (int) (val * 100);

        if (result < MIN_OBW || result > MAX_OBW) return;

        mOBWPower = result;

    }

    public float getOBWPower() {

        float val = (float) mOBWPower / 100f;
        return val;

    }

    public void setXDB(float val) {

        int result = (int) (val * 100);

        if (result < MIN_XDB || result > MAX_XDB) return;

        mXdB = result;

    }

    public Float getXDB() {

        float val = (float) mXdB / 100f;

        return val;

    }

    public void setOccupiedBW(float occBW) {

        mOccBW = occBW;

    }

    public Float getOccupiedBW() {
        return mOccBW;
    }

    public void setTotalPower(float power) {

        mTotalPower = power;

    }

    public Float getTotalPower() {
        return mTotalPower;
    }

    public void setXDBBW(float bw) {

        mXDBBW = bw;

    }

    public Float getXDBBW() {
        return mXDBBW;
    }

    public AclrCarrierSetupData getCarrierSetupData() {

        if (CarrierSetupData == null) CarrierSetupData = new AclrCarrierSetupData();
        return CarrierSetupData;
    }

    public AclrOffsetSetupData getOffsetSetupData() {
        if (OffsetSetupData == null) OffsetSetupData = new AclrOffsetSetupData();
        return OffsetSetupData;
    }

    public Integer getLowerPowerIndex() {
        return LowerPowerIndex;
    }

    public void setLowerPowerIndex(Integer lowerPowerIndex) {
        LowerPowerIndex = lowerPowerIndex;
    }

    public Integer getUpperPowerIndex() {
        return UpperPowerIndex;
    }

    public void setUpperPowerIndex(Integer upperPowerIndex) {
        UpperPowerIndex = upperPowerIndex;
    }

}

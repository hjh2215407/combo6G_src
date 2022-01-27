package com.dabinsystems.pact_app.Data.SA.MeasSetupData;

import android.util.Log;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrCarrierSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrOffsetSetupData;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;

public class ChannelPowerMeasSetupData extends MeasSetupData {

    private AVG_HOLD_MODE AvgMode;

    private int AvgHoldNum = 100;
    private final int MIN = 1;
    private final int MAX = 200;

    private long mIntegBw = 38 * (long) Math.pow(10, 5);
    private Float mChannelPower = null;
    private Float mDensity = null;

    private final int MIN_INTEG = 1;
    private final long MAX_INTEG = 1000000000; // 1,000,000,000

    private AclrCarrierSetupData CarrierSetupData;
    private AclrOffsetSetupData OffsetSetupData;

    public ChannelPowerMeasSetupData() {

        AvgMode = AVG_HOLD_MODE.ON;
        AvgHoldNum = 10;

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


    /*Channel Poewr Meas setup*/

    public void setIngegBW(long val) {

        if (val < MIN_INTEG || val > MAX_INTEG) return;

        mIntegBw = val;

    }

    public double getIntegMHzVal() {

        double val = mIntegBw / Math.pow(10, 6);
        //org
        //double roundVal = Math.round((val * 100)) / 100.0;
        //
        double roundVal = Math.round((val * 1000)) / 1000.0;

        InitActivity.logMsg("getIntegMHzVal", roundVal + "");

        return roundVal;

    }

    public long getIntegHzVal() {

        return mIntegBw;

    }

    public void setChannelPower(float power) {

        mChannelPower = power;
    }

    public Float getChannelPower() {
        return mChannelPower;
    }

    public void setDensity(float density) {

        mDensity = density;

    }

    public Float getDensity() {
        return mDensity;

    }

}

package com.dabinsystems.pact_app.Data.SA.MeasSetupData;

import android.util.Log;

import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrCarrierSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrOffsetSetupData;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;

public class SweptSaMeasSetupData extends MeasSetupData {

    private AVG_HOLD_MODE AvgMode;

    /*Swept SA*/

    private int AvgHoldNum = 100;
    private final int MIN = 1;
    private final int MAX = 200;

    public SweptSaMeasSetupData() {

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

}

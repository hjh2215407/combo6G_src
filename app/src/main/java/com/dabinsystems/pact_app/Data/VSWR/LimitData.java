package com.dabinsystems.pact_app.Data.VSWR;

import android.util.Log;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;

public class LimitData {

    public static final float DEFAULT_VSWR_LIMIT = 1.5f;
    public static final float DEFAULT_RL_LIMIT = 13.98f;

    /*
     * Limit
     * */

    private Float LimitValueForVswr = DEFAULT_VSWR_LIMIT;
    private Float LimitValueForRl = DEFAULT_RL_LIMIT;

    private Boolean isUpper = true;
    private Boolean isOnAlarm = false;
    private Boolean isPassFailEnabled = false;

    public Float getLimitValueForVswr() {
        return LimitValueForVswr;
    }

    public void setLimitValueForVswr(Float limitValueForVswr) {

        LimitValueForVswr = limitValueForVswr;

        LimitValueForRl = Math.round(-20f * (float) Math.log10(
                (LimitValueForVswr - 1f) / (LimitValueForVswr + 1f)
        ) * 100f) / 100f;

//        InitActivity.logMsg("setLimitVswr", "VSWR : " + LimitValueForVswr + " RL : " + LimitValueForRl);

    }

    public Float getLimitValueForRl() {
        return LimitValueForRl;
    }

    public void setLimitValueForRl(Float limitValueForRl) {

        LimitValueForRl = limitValueForRl;
        LimitValueForVswr = Math.round((float) (
                (Math.pow(10, LimitValueForRl / 20f) + 1f) / ((Math.pow(10, LimitValueForRl / 20f) - 1f))
        ) * 100f) / 100f;

        InitActivity.logMsg("setLimitRl", "VSWR : " + LimitValueForVswr + " RL : " + LimitValueForRl + " Parameter : " + limitValueForRl);

    }

    public void setLimitValueByType(Float val) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if(type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

            setLimitValueForVswr(val);

        } else {

            setLimitValueForRl(val);

        }

    }

    public Boolean getUpper() {
        return isUpper;
    }

    public void setUpper(Boolean upper) {
        isUpper = upper;
    }

    public Boolean getOnAlarm() {
        return isOnAlarm;
    }

    public void setOnAlarm(Boolean onAlarm) {
        isOnAlarm = onAlarm;
    }

    public Boolean getPassFailEnabled() {
        return isPassFailEnabled;
    }

    public void setPassFailEnabled(Boolean passFailEnabled) {
        isPassFailEnabled = passFailEnabled;
    }

}

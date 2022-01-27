package com.dabinsystems.pact_app.Data.ModAccuracy.LteFdd;

import android.util.Log;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.TriggerSource;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;

public class LteFddData {

    private double CenterFreq = 1820d;

    private LteFddAmpData AmpData;
    private LteFddProfileData ProfileData;
    private LteFddLimitData LimitData;
    private com.dabinsystems.pact_app.Data.ModAccuracy.TriggerSource TriggerSource;

    public LteFddData() {

        AmpData = new LteFddAmpData();
        ProfileData = new LteFddProfileData();
        LimitData = new LteFddLimitData();

    }

    public com.dabinsystems.pact_app.Data.ModAccuracy.TriggerSource getTriggerSource() {

        if(TriggerSource == null) TriggerSource = new TriggerSource();
        return TriggerSource;

    }

    public void setCenterFreq(double freq) {

        CenterFreq = freq;

    }

    public double getCenterFreq() {

        return CenterFreq;

    }


    public String getModType(int value) {

        String result = "";

        switch(value) {

            case 0:
                result = "Zadoff";
                break;

            case 1:
                result = "BPSK";
                break;

            case 2:
                result = "QPSK";
                break;

            case 4:
                result = "16QAM";
                break;

            case 6:
                result = "64QAM";
                break;

            case 8:
                result = "256QAM";
                break;

            case -999999:
                result = "--.--";
                break;

            default:
                result = "--.--";
                break;

        }

        return result;

    }

    public void update() {

        getLimitData().update();

    }

    public LteFddLimitData getLimitData() {
        return LimitData;
    }

    public LteFddAmpData getAmpData() {
        return AmpData;
    }
    public LteFddProfileData getProfileData() {
        return ProfileData;
    }

    public String getLteFddCmd() {

        StringBuffer bufferStr = new StringBuffer();

        bufferStr.append(MeasureMode.MEASURE_MODE.MOD_ACCURACY.getHexString());
        bufferStr.append(" ");
        bufferStr.append(MeasureType.MEASURE_TYPE.LTE_FDD.getHexString());
        bufferStr.append(" ");
        bufferStr.append(Math.round(getCenterFreq() * 1000000d));
        bufferStr.append(" ");
        bufferStr.append(getAmpData().getATTMode().getValue());
        bufferStr.append(" ");
        bufferStr.append(getAmpData().getAttenuator());
        bufferStr.append(" ");
        bufferStr.append((int)(getAmpData().getOffset() * 100f));
        bufferStr.append(" ");
        bufferStr.append(getAmpData().getPreampMode().getValue());
        bufferStr.append(" ");
        bufferStr.append(getProfileData().getProfile().getCmd());
        bufferStr.append(" ");
        bufferStr.append((int)(getLimitData().getFreqOffset() * 100));
        bufferStr.append(" ");
        bufferStr.append((int)(getLimitData().getMinimumRsEvm() * 100));
        bufferStr.append(" ");
        bufferStr.append((int)(getLimitData().getTae() * 100));
        bufferStr.append(" ");
        bufferStr.append(getTriggerSource().getTriggerSource().getValue());

        String cmd = bufferStr.toString();

        InitActivity.logMsg("SendSettingValues", cmd);

        return cmd;

    }

}

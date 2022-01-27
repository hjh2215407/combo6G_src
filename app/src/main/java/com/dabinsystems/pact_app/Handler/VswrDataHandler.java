package com.dabinsystems.pact_app.Handler;

import android.util.Log;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Data.VSWR.VswrConfigData;
import com.dabinsystems.pact_app.Function.CalibrationFunc;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Data.VSWR.SweepData;

public class VswrDataHandler {

    private static VswrDataHandler Instance = null;

    /*PACT Config Classes*/

    private VswrConfigData VswrConfigData;
    private VswrConfigData DtfConfigData;
    private VswrConfigData ClConfigData;

    public static VswrDataHandler getInstance() {
        if(Instance == null) Instance = new VswrDataHandler();
        return Instance;
    }

    public com.dabinsystems.pact_app.Data.VSWR.VswrConfigData getVswrConfigData() {

        if (VswrConfigData == null) VswrConfigData = new VswrConfigData();
        return VswrConfigData;
    }


    public com.dabinsystems.pact_app.Data.VSWR.VswrConfigData getDtfConfigData() {

        if (DtfConfigData == null) DtfConfigData = new VswrConfigData();
        return DtfConfigData;
    }

    public com.dabinsystems.pact_app.Data.VSWR.VswrConfigData getClConfigData() {

        if (ClConfigData == null) ClConfigData = new VswrConfigData();
        return ClConfigData;
    }

    public VswrConfigData getConfigData() {

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

        switch(mode) {

            case VSWR:
                return getVswrConfigData();
            case DTF:
                return getDtfConfigData();
            case CL:
                return getClConfigData();
            default:
                return getVswrConfigData();

        }

    }

    public String getCalibrationCmd() {

        //        ByteBuffer buffer = ByteBuffer.allocate(40);
//        InitActivity.logMsg("SaSweptSAConfig", "size : " + buffer.array().length);

        StringBuffer bufferStr = new StringBuffer();

        bufferStr.append(CalibrationFunc.CALIBRATION.CAL_MODE.getHexString());
//        bufferStr.append(" ");
//
//        bufferStr.append((int) (getConfigData().getFrequencyData().getStartFreq() * 100));
//        bufferStr.append(" ");
//        bufferStr.append((int) (getConfigData().getFrequencyData().getStopFreq() * 100));

        bufferStr.append(" ");
        bufferStr.append(FunctionHandler.getInstance().getCalibrationFunc().getStep().getHexString());

        String cmd = bufferStr.toString();

        InitActivity.logMsg("CalibrationCmd", cmd);

        return cmd;

    }

    public String getUserCalCmd() {

        //        ByteBuffer buffer = ByteBuffer.allocate(40);
//        InitActivity.logMsg("SaSweptSAConfig", "size : " + buffer.array().length);

        StringBuffer bufferStr = new StringBuffer();

        bufferStr.append(CalibrationFunc.USER_CAL.USER_CAL_MODE.getHexString());
        bufferStr.append(" ");
        bufferStr.append(FunctionHandler.getInstance().getCalibrationFunc().getUserCal().getHexString());

        String cmd = bufferStr.toString();

        InitActivity.logMsg("UserCalCmd", cmd);

        return cmd;

    }

    public String getCalTypeCmd() {

        StringBuffer bufferStr = new StringBuffer();

        bufferStr.append(CalibrationFunc.CAL_TYPE.CAL_TYPE_MODE.getHexString());
        bufferStr.append(" ");
        bufferStr.append(FunctionHandler.getInstance().getCalibrationFunc().getCalType().getHexString());

        String cmd = bufferStr.toString();

        InitActivity.logMsg("UserCalCmd", cmd);

        return cmd;

    }

    public String getVswrParameter() {

        FunctionHandler.getInstance().getDataConnector().deleteBuffer();

        StringBuffer bufferStr = new StringBuffer();

        bufferStr.append(FunctionHandler.getInstance().getMeasureMode().getMode().getHexString());
        bufferStr.append(" ");
        bufferStr.append(FunctionHandler.getInstance().getMeasureType().getType().getHexString());
        bufferStr.append(" ");
        bufferStr.append(getConfigData().getSweepData().getDataPoint().getHexString());
        bufferStr.append(" ");

        bufferStr.append((long) Math.round((getConfigData().getFrequencyData().getStartFreq() * 1000000d)));
        bufferStr.append(" ");
        bufferStr.append((long) Math.round((getConfigData().getFrequencyData().getStopFreq() * 1000000d)));

        bufferStr.append(" ");
        bufferStr.append((int) (getConfigData().getDistance() * 100));
        bufferStr.append(" ");
        bufferStr.append((int) (FunctionHandler.getInstance().getCableInfoFunc().getLoss() * 100));
        bufferStr.append(" ");
        bufferStr.append((int) (FunctionHandler.getInstance().getCableInfoFunc().getVelocity() * 100));
        bufferStr.append(" ");
        bufferStr.append(getConfigData().getWindowingData().getMode().getHexString());

        String cmd = bufferStr.toString();

        FunctionHandler.getInstance().getMainLineChart().clearAllValues();
        InitActivity.logMsg("SendSettingValues", cmd);

        return cmd;

    }

}

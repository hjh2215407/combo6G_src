package com.dabinsystems.pact_app.Function;

import android.util.Log;

import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;

public class MeasureMode {


    public enum MEASURE_MODE {

        NONE(-1, "NoneMode"), // mode 선택 안 되었음.

        VSWR(0x01, "VSWR"),
        DTF(0x02, "DTF"),
        CL(0x03, "Cable Loss"),

        SA(0x04, "SA"),
        MOD_ACCURACY(0x42, "Mod. Accuracy"),
        IQ_LMB_CAL(0x43, "IQ lmb Cal"),

        SG(0x05, "SG");

        private final int value;
        private final String valueStr;

        MEASURE_MODE(int val, String str) {
            value = val;
            valueStr = str;
        }

        public int getValue() {
            return value;
        }

        public String getHexString() {
            return DataHandler.getInstance().intToHexStr(value);
        }

        public String getString() {
            return valueStr;
        }

        public byte getByte() {
            return (byte) value;
        }
    }

    private MEASURE_MODE SelectedMode = MEASURE_MODE.NONE;// MEASURE_MODE.SA;
    private MEASURE_MODE PrevMode = null;

    public void setMode(MEASURE_MODE mode) {

        Log.d("KDK", "setMode " + mode.getString());

        if (mode == SelectedMode) return;

        PrevMode = SelectedMode;
        SelectedMode = mode;

        if (SelectedMode == MEASURE_MODE.NONE)
            return;

        FunctionHandler.getInstance().getMainLineChart().setSkipFirstData(false);

        switch (SelectedMode) {

            case VSWR:
                switch (PrevMode) {
                    case VSWR:
                    case DTF:
                    case CL:
                        // mode 변경 필요 없음.
                        break;
                    default:
                        FunctionHandler.getInstance().getDataConnector().showModeChangeMessage("Loading " + MEASURE_MODE.VSWR.getString() + "...");
                        FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getChangeToVSWR());
                        break;
                }

                FunctionHandler
                        .getInstance()
                        .getMeasureType()
                        .setMeasureType(MeasureType.MEASURE_TYPE.DTF_RL);

                FunctionHandler.getInstance().getMainLineChart().update();

                break;

            case DTF:
                switch (PrevMode) {
                    case VSWR:
                    case DTF:
                    case CL:
                        // mode 변경 필요 없음.
                        break;
                    default:
                        FunctionHandler.getInstance().getDataConnector().showModeChangeMessage("Loading " + MEASURE_MODE.DTF.getString() + "...");
                        FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getChangeToVSWR());
                        break;
                }

                FunctionHandler
                        .getInstance()
                        .getMeasureType()
                        .setMeasureType(MeasureType.MEASURE_TYPE.DTF_RL);

                ViewHandler.getInstance().getContent().subInfoUpdate();
                FunctionHandler.getInstance().getMainLineChart().update();

                break;

            case CL:
                switch (PrevMode) {
                    case VSWR:
                    case DTF:
                    case CL:
                        // mode 변경 필요 없음.
                        break;
                    default:
                        FunctionHandler.getInstance().getDataConnector().showModeChangeMessage("Loading " + MEASURE_MODE.CL.getString() + "...");
                        FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getChangeToVSWR());
                        break;
                }

                FunctionHandler
                        .getInstance()
                        .getMeasureType()
                        .setMeasureType(MeasureType.MEASURE_TYPE.CABLE_LOSS);

                ViewHandler.getInstance().getContent().subInfoUpdate();
                FunctionHandler.getInstance().getMainLineChart().update();

                break;

            case SA:
                if (PrevMode != MEASURE_MODE.SA) {
                    FunctionHandler.getInstance().getDataConnector().showModeChangeMessage("Loading " + MEASURE_MODE.SA.getString() + "...");
                    FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getChangeToSA());
                }

                FunctionHandler
                        .getInstance()
                        .getMeasureType()
                        .setMeasureType(MeasureType.MEASURE_TYPE.SWEPT_SA);

                ViewHandler.getInstance().getContent().subInfoUpdate();
                FunctionHandler.getInstance().getMainLineChart().update();

                /*//@@ [22.01.10] SA mode 될 때 hold over set parameter send
                Log.e("Holdover", "(SA)Send Holdover Set Command");

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getChangeGPSHoldoverCmd()
                );

                //@@*/

                break;

            case MOD_ACCURACY:
                // type 에 따라 FW mode 가 다름.
                break;

            case SG:
                break;
        }

//        ViewHandler.getInstance().getContent().markerUnitUpdate();
        FunctionHandler.getInstance().getMainLineChart().clearDataList();
        FunctionHandler.getInstance().getMainLineChart().invalidate();
    }

    public void setModeOnly(MEASURE_MODE mode) {
        if (mode == SelectedMode) return;
        SelectedMode = mode;
    }

    public MEASURE_MODE getMode() {
        return SelectedMode;
    }

    public MEASURE_MODE getPrevMode() {
        return PrevMode;
    }

    //Current mode to Prev mode
    public void updatePrevMode() {
        PrevMode = SelectedMode;
    }

}

package com.dabinsystems.pact_app.Function;

import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData;
import com.dabinsystems.pact_app.Data.SA.SaConfigData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;

public class MeasureType {

    private MEASURE_TYPE PrevType = MEASURE_TYPE.NONE;

    public enum
    MEASURE_TYPE {

        NONE(-1, "NoneType", "NoneType"),

        VSWR(0x00, "VSWR", "VSWR"),
        RL(0x01, "Return Loss", "RL"),

        DTF_VSWR(0x00, "DTF_VSWR", "DTF_VSWR"),
        DTF_RL(0x01, "DTF_RL", "DTF_RL"),

        CABLE_LOSS(0x01, "Cable Loss", "CL"),

        SWEPT_SA(0x00, "Swept SA", "SA"),
        CHANNEL_POWER(0x01, "Channel Power", "CP"),
        OCCUPIED_BW(0x02, "Occupied BW", "OBW"),

        ACLR(0x03, "ACLR", "ACLR"),
        SEM(0x04, "SEM", "SEM"),
        TRANSMIT_MASK(0x05, "Transmit On/off Mask", "TransmitMask"),
        SPURIOUS_EMISSION(0x06, "Spurious Emission", "S.Emission"),

        NR_5G(0x07, "5G NR", "5G NR"),
        LTE_FDD(0x08, "LTE FDD", "LTE FDD"),
        TAE(0x09, "TAE", "TAE"),
        INTERFERENCE_HUNTING(0x10, "UL Interference Hunting", "UL Interference"),
        NR_5G_SCAN(0x11, "5G NR SCAN", "5G NR SCAN"); // TODO 신규 추가 통신 및 UI 추가 해야 함

        private final int value;
        private final String fullName;
        private final String shortName;

        MEASURE_TYPE(int val, String fName, String sName) {
            value = val;
            fullName = fName;
            shortName = sName;
        }

        public int getValue() {
            return value;
        }

        public String getHexString() {
            return DataHandler.getInstance().intToHexStr(value);
        }

        public String getFullName() {
            return fullName;
        }

        public String getShortName() {
            return shortName;
        }

        public byte getByte() {
            return (byte) value;
        }
    }

    private MEASURE_TYPE mMeasureType = MEASURE_TYPE.NONE;// MEASURE_TYPE.SWEPT_SA;

    public MEASURE_TYPE getType() {
        return mMeasureType;
    }

    public void setMeasureType(MEASURE_TYPE type) {

        PrevType = mMeasureType;
        mMeasureType = type;

        if (mMeasureType == MEASURE_TYPE.NONE) {
            return;
        }

        FunctionHandler.getInstance().getDataConnector().calMqttTimeout();

        FunctionHandler.getInstance().getMainLineChart().setSkipFirstData(mMeasureType == MEASURE_TYPE.TAE);

        FunctionHandler.getInstance().getDataConnector().setReady(true);
//        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
//        FunctionHandler.getInstance().getMainLineChart().updateSemBox();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
            FunctionHandler.getInstance().getMainLineChart().updateOccBox();
            FunctionHandler.getInstance().getMainLineChart().updateChannelBox();
        }, 500);

        switch (mMeasureType) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR:
            case SEM:
            case TRANSMIT_MASK:
            case SPURIOUS_EMISSION:
            case INTERFERENCE_HUNTING:
//                SaDataHandler.getInstance().getConfigData().getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE);
                FunctionHandler.getInstance().getMainLineChart().setEnabledLimitMsg(false);
                SaConfigData data = SaDataHandler.getInstance().getConfigData();
                data.loadData();

                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getSAView().update();
                ViewHandler.getInstance().getSaMeas().update();

                if (mMeasureType == MEASURE_TYPE.INTERFERENCE_HUNTING)
                    data.getInterferenceHuntingData().clearTableView();

                MeasureMode mode = FunctionHandler.getInstance().getMeasureMode();

                if (mode.getPrevMode() == MeasureMode.MEASURE_MODE.SA)
                    FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());

                DataHandler.getInstance().setAutoAtten(false);
                DataHandler.getInstance().setAutoScaleCount(0);
                DataHandler.getInstance().setSaAutoScale(false);

                break;

            case VSWR:
            case RL:
            case DTF_VSWR:
            case DTF_RL:
            case CABLE_LOSS:
                VswrDataHandler.getInstance().getConfigData().getSweepData().setRun(true);
                VswrDataHandler.getInstance().getConfigData().loadData();

                DataHandler.getInstance().setAutoAtten(false);
                DataHandler.getInstance().setAutoScaleCount(0);
                DataHandler.getInstance().setSaAutoScale(false);

                FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());
                break;

            case TAE:
                InitActivity.logMsg("MeasureType", "in TAE " + type.toString());
//                FunctionHandler.getInstance().getDataConnector().sendCommand(
//                        DataHandler.getInstance().getConfigCmd()
//                );
                break;
            case NR_5G:
                ViewHandler.getInstance().getSAView().update();

                FunctionHandler.getInstance().getCandleChartFunc().clearAllValues();
                FunctionHandler.getInstance().getScatterChart().clearAllValues();
                FunctionHandler.getInstance().getScatterChart2().clearAllValues();
                DataHandler.getInstance().getNrData().getInfoData().clearValues();
                FunctionHandler.getInstance().getMainLineChart().setEnabledLimitMsg(false);

                DataHandler.getInstance().getNrData().getInfoData().setChannelBandwidth(
                        DataHandler.getInstance().getNrData().getProfileData().getProfile().getValue()
                );

                DataHandler.getInstance().getNrData().getInfoData().setPpm(
                        DataHandler.getInstance().getNrData().getLimitData().getFreqOffsetValue()
                );

                //init data for chart box size
                FunctionHandler.getInstance().getScatterChart().updateEntry();
                InitActivity.logMsg("setType", "5G NR");

                if (getPrevType() != MEASURE_TYPE.TAE && getPrevType() != MEASURE_TYPE.NR_5G) {
                    InitActivity.logMsg("setMeasureType", "to 5GNR - > Prev type : " + getPrevType().getFullName() + " Cur Type : " + getType().getFullName());
                    FunctionHandler.getInstance().getDataConnector().showModeChangeMessage("Loading " + MEASURE_TYPE.NR_5G.getFullName() + "...");
                    FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getChangeTo5G());
                }

//                Log.e("5GNR Sync", "Passed here(MeasureType, NR5G)");
                DataHandler.getInstance().getStatusData().setSync(false);
                new Handler(Looper.getMainLooper()).post(() -> {
                    MainActivity.getBinding().tvNrSync.setBackground(MainActivity.getActivity().getResources().getDrawable(R.drawable.fw_status_background));
                    MainActivity.getBinding().tvNrSync.setTextColor(MainActivity.getActivity().getResources().getColor(R.color.norText));
                });

                /*//@@ [22.01.10] SA mode 될 때 hold over set parameter send
                Log.e("Holdover", "(Mod Accuracy)Send Holdover Set Command");
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getChangeGPSHoldoverCmd()
                );
                //@@*/

                /*FunctionHandler.getInstance().getDataConnector().sendCommand("0x54");*/

                break;

            case LTE_FDD:
                FunctionHandler.getInstance().getCandleChartFunc().clearAllValues();
                FunctionHandler.getInstance().getScatterChart().clearAllValues();
                FunctionHandler.getInstance().getScatterChart2().clearAllValues();
//                DataHandler.getInstance().getLteFddData().clearValues();
                FunctionHandler.getInstance().getMainLineChart().setEnabledLimitMsg(false);

                DataHandler.getInstance().getLteFddData().getProfileData().setProfile(
                        DataHandler.getInstance().getLteFddData().getProfileData().getProfile()
                );

                //init data for chart box size
                FunctionHandler.getInstance().getScatterChart2().updateEntry();
                InitActivity.logMsg("setType", "LTE FDD");

                FunctionHandler.getInstance().getDataConnector().showModeChangeMessage("Loading " + MEASURE_TYPE.LTE_FDD.getFullName() + "...");
                FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getChangeToLte());

//                Log.e("5GNR Sync", "Passed here(MeasureType, LTE_FDD)");
                DataHandler.getInstance().getStatusData().setSync(false);
                new Handler(Looper.getMainLooper()).post(() -> {
                    MainActivity.getBinding().tvNrSync.setBackground(MainActivity.getActivity().getResources().getDrawable(R.drawable.fw_status_background));
                    MainActivity.getBinding().tvNrSync.setTextColor(MainActivity.getActivity().getResources().getColor(R.color.norText));
                });

                break;

            case NR_5G_SCAN:
                // TODO 5G NR SCAN 선택 시
                FunctionHandler.getInstance().getCandleChartFunc().clearAllValues();
                FunctionHandler.getInstance().getScatterChart().clearAllValues();
                FunctionHandler.getInstance().getScatterChart2().clearAllValues();
                DataHandler.getInstance().getNrData().getInfoData().clearValues();
                FunctionHandler.getInstance().getMainLineChart().setEnabledLimitMsg(false);

                if (getPrevType() != MEASURE_TYPE.TAE && getPrevType() != MEASURE_TYPE.NR_5G) {
                    InitActivity.logMsg("setMeasureType", "to 5GNR - > Prev type : " + getPrevType().getFullName() + " Cur Type : " + getType().getFullName());
                    FunctionHandler.getInstance().getDataConnector().showModeChangeMessage("Loading " + MEASURE_TYPE.NR_5G_SCAN.getFullName() + "...");
                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            DataHandler.getInstance().getChangeTo5G()
                    );
                }

                break;

            default:
                throw new IllegalStateException("Unexpected value: " + mMeasureType.getValue());
        }

//        ViewHandler.getInstance().getContent().markerUnitUpdate();
        FunctionHandler.getInstance().getMainLineChart().clearAllValues();
        FunctionHandler.getInstance().getDataConnector().calMqttTimeout();
        FunctionHandler.getInstance().getDataConnector().startDataTimeoutHandler();
        FunctionHandler.getInstance().getGateLineChart().removeGateTimer();

    }

    public MEASURE_TYPE getPrevType() {
        return PrevType;
    }

    public void setPrevType(MEASURE_TYPE prevType) {
        PrevType = prevType;
    }
}

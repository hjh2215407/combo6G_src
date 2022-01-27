package com.dabinsystems.pact_app.Data.SA.MeasSetupData.InterferenceHunting;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.MeasSetupData;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;

import java.util.ArrayList;
import java.util.LinkedList;

import me.grantland.widget.AutofitTextView;

public class InterferenceMeasSetupData extends MeasSetupData {

    private AVG_HOLD_MODE AvgMode;

    private int AvgHoldNum = 100;
    private final int MIN = 1;
    private final int MAX = 200;

    private Double UplinkBandStart = 3300d;
    private Double UplinkBandStop = 3900d;
    private int UplinkBandValue = 5;
    private Double TargetFrequency = null;
    private Double Threshold = -80d;

    private SpurState SpurState = null;
    private SpurType SpurType = null;

    private ArrayList<SpurTableData> TableDataList;

    public enum SpurState {

        NO_SPUR(0),
        DETECT_SPUR(1);

        int State;

        SpurState(int state) {
            State = state;
        }

        public int getStateValue() {
            return State;
        }

    }

    public enum SpurType {

        SINGLE(0, "Single"),
        WIDE(1, "Wide");

        int Type;
        String Name;

        SpurType(int type, String name) {
            Type = type;
            Name = name;
        }

        public int getTypeValue() {
            return Type;
        }

        public String getName() {
            return Name;
        }

    }

    public InterferenceMeasSetupData() {

        AvgMode = AVG_HOLD_MODE.ON;
        AvgHoldNum = 100;

        UplinkBandStart = 3300d;
        UplinkBandStop = 3900d;
        TargetFrequency = 3650.01d;
        Threshold = -80d;

        TableDataList = new ArrayList<>();

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

    public Double getUplinkBandStart() {
        return UplinkBandStart;
    }

    public void setUplinkBandStart(Double uplinkBandStart) {
        UplinkBandStart = uplinkBandStart;
    }

    public Double getUplinkBandStop() {
        return UplinkBandStop;
    }

    public void setUplinkBandStop(Double uplinkBandStop) {
        UplinkBandStop = uplinkBandStop;
    }

    //Frequency data의 Frequency 메뉴가 막혀있으므로 Frequency data의 Frequency를 대신 사용해도 될듯함.
//    public Double getTargetFrequency() {
//        return TargetFrequency;
//    }
//
//    public void setTargetFrequency(Double targetFrequency) {
//        TargetFrequency = targetFrequency;
//    }

    public double getThreshold() {
        return Threshold;
    }

    public void setThreshold(Double threshold) {

        if (threshold > 200) threshold = 200d;
        if (threshold < -200) threshold = -200d;

        Threshold = Math.round(threshold * 100d) / 100d;

    }

    /*
        UpLinkBand Range
        0 : 717 ~ 728 MHz
        1 : 824 ~ 849 MHz
        2 : 1710 ~ 1785 MHz
        3 : 1920 ~ 1980 MHz
        4 : 2500 ~ 2570 MHz
        5 : 3300 ~ 3900 MHz

    * */
    public void setUplinkBand(int range) {

        UplinkBandValue = range;

        switch (range) {

            case 0:
                UplinkBandStart = 717d;
                UplinkBandStop = 228d;
                break;
            case 1:
                UplinkBandStart = 824d;
                UplinkBandStop = 849d;
                break;
            case 2:
                UplinkBandStart = 1710d;
                UplinkBandStop = 1785d;
                break;
            case 3:
                UplinkBandStart = 1920d;
                UplinkBandStop = 1980d;
                break;
            case 4:
                UplinkBandStart = 2500d;
                UplinkBandStop = 2570d;
                break;
            case 5:
                UplinkBandStart = 3300d;
                UplinkBandStop = 3900d;
                break;
            default:
                UplinkBandValue = 5;
                UplinkBandStart = 3300d;
                UplinkBandStop = 3900d;


        }

        FunctionHandler.getInstance().getMainLineChart().invalidate();

    }

    public void addTableData(SpurTableData data) {

        try {
            TableDataList.add(data);
            if (TableDataList.size() > 20) TableDataList.remove(0);
        } catch (IllegalStateException e) {
            InitActivity.logMsg("addTableData", "not enough storage to save data");
        }

    }

    public InterferenceMeasSetupData.SpurState getSpurState() {
        return SpurState;
    }

    public void setSpurState(InterferenceMeasSetupData.SpurState spurState) {
        SpurState = spurState;
    }

    public InterferenceMeasSetupData.SpurType getSpurType() {
        return SpurType;
    }

    public void setSpurType(InterferenceMeasSetupData.SpurType spurType) {
        SpurType = spurType;
    }

    public void updateTableView() {

        AutofitTextView[] tvIndex = {
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex0,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex1,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex2,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex3,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex4,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex5,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex6,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex7,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex8,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex9,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex10,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex11,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex12,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex13,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex14,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex15,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex16,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex17,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex18,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex19,
        };


        AutofitTextView[] tvPower = {
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower0,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower1,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower2,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower3,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower4,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower5,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower6,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower7,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower8,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower9,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower10,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower11,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower12,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower13,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower14,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower15,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower16,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower17,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower18,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower19,
        };

        AutofitTextView[] tvTime = {
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime0,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime1,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime2,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime3,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime4,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime5,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime6,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime7,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime8,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime9,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime10,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime11,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime12,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime13,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime14,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime15,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime16,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime17,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime18,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime19,
        };

        AutofitTextView[] tvType = {
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType0,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType1,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType2,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType3,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType4,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType5,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType6,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType7,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType8,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType9,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType10,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType11,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType12,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType13,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType14,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType15,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType16,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType17,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType18,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType19,
        };

        for (int i = 0; i < TableDataList.size(); i++) {
            String timeStamp = TableDataList.get(i).getTime();
            String type = TableDataList.get(i).getType().getName();
            Double power = TableDataList.get(i).getPower();

            tvIndex[i].setText((i + 1) + "");
            tvTime[i].setText(timeStamp);
            tvType[i].setText(type);
            tvPower[i].setText(power + " dBm");
        }

    }

    public void clearTableView() {

        TableDataList.clear();

        AutofitTextView[] tvIndex = {
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex0,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex1,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex2,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex3,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex4,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex5,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex6,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex7,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex8,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex9,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex10,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex11,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex12,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex13,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex14,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex15,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex16,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex17,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex18,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex19,
        };


        AutofitTextView[] tvPower = {
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower0,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower1,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower2,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower3,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower4,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower5,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower6,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower7,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower8,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower9,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower10,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower11,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower12,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower13,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower14,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower15,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower16,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower17,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower18,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower19,
        };

        AutofitTextView[] tvTime = {
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime0,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime1,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime2,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime3,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime4,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime5,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime6,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime7,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime8,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime9,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime10,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime11,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime12,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime13,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime14,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime15,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime16,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime17,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime18,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime19,
        };

        AutofitTextView[] tvType = {
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType0,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType1,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType2,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType3,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType4,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType5,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType6,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType7,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType8,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType9,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType10,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType11,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType12,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType13,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType14,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType15,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType16,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType17,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType18,
                MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType19,
        };

        for (int i = 0; i < TableDataList.size(); i++) {

            int finalI = i;
            new Thread(() -> {
                new Handler(Looper.getMainLooper()).post(() -> {

                    tvIndex[finalI].setText("");
                    tvTime[finalI].setText("");
                    tvType[finalI].setText("");
                    tvPower[finalI].setText("");

                });
            }).start();
        }
    }

    public void updateSpan() {
        double startBand = UplinkBandStart;

        double max = 700d;

        switch ((int) startBand) {
            case 717:
                max = 20d;
                break;

            case 824:
                max = 30d;
                break;

            case 1710:
                max = 90d;
                break;

            case 1920:
                max = 80d;
                break;

            case 2500:
                max = 80d;
                break;

            case 3300:
                max = 700d;
                break;

        }

        double span = SaDataHandler.getInstance().getInterferenceHuntingConfigData().getFrequencyData().getSpan();
        if (span > max) {
            SaDataHandler.getInstance().getInterferenceHuntingConfigData().getFrequencyData().setSpan(max);
            FunctionHandler.getInstance().getMainLineChart().invalidate();
        }
    }

    public int getUplinkBandValue() {
        return UplinkBandValue;
    }
}

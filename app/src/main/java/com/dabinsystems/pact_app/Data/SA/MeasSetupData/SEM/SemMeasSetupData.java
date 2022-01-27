package com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;

import androidx.annotation.MainThread;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SemMeasTypeData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.MeasSetupData;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import java.util.Arrays;

public class SemMeasSetupData extends MeasSetupData {

    /*Receive Data*/

    private Integer SemResult = 0;
    private Float ChPower = 0f;
    private Float Density = 0f;

    private Integer[] LowerPeakIndex;
    private Float[] LowerPkPower;
    private Integer[] LowerResult;
    private Float[] LowerLimit;
    private Float[] LowerFreq;

    private Integer[] UpperPeakIndex;
    private Float[] UpperPkPower;
    private Integer[] UpperResult;
    private Float[] UpperLimit;
    private Float[] UpperFreq;

    /*end*/

    private AVG_HOLD_MODE AvgMode;

    private int AvgHoldNum;
    private final int MIN = 1;
    private final int MAX = 200;

    private SemMeasTypeData.SEM_MEASURE_TYPE Type = SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER;
    private SemRefChannelData RefChannelData;
    private SemEditMaskData EditMaskData;

    public SemMeasSetupData() {

        AvgMode = AVG_HOLD_MODE.OFF;
        AvgHoldNum = 10;

        LowerPeakIndex = new Integer[4];
        LowerPkPower = new Float[4];
        LowerResult = new Integer[4];
        LowerFreq = new Float[4];
        LowerLimit = new Float[4];

        UpperPeakIndex = new Integer[4];
        UpperPkPower = new Float[4];
        UpperResult = new Integer[4];
        UpperFreq = new Float[4];
        UpperLimit = new Float[4];

        Arrays.fill(LowerPeakIndex, 0);
        Arrays.fill(LowerPkPower, 0f);
        Arrays.fill(LowerFreq, 0f);
        Arrays.fill(LowerResult, 0);
        Arrays.fill(LowerLimit, 0f);

        Arrays.fill(UpperPeakIndex, 0);
        Arrays.fill(UpperPkPower, 0f);
        Arrays.fill(UpperFreq, 0f);
        Arrays.fill(UpperResult, 0);
        Arrays.fill(UpperLimit, 0f);

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


    public SemMeasTypeData.SEM_MEASURE_TYPE getSemMeasType() {
        return Type;
    }

    public void setType(SemMeasTypeData.SEM_MEASURE_TYPE type) {
        Type = type;
    }

    public SemRefChannelData getRefChannelData() {

        if (RefChannelData == null) RefChannelData = new SemRefChannelData();
        return RefChannelData;

    }

    public SemEditMaskData getEditMaskData() {

        if (EditMaskData == null) EditMaskData = new SemEditMaskData();
        return EditMaskData;

    }

    public Float getChPower() {
        return ChPower;
    }

    public void setChPower(Float chPower) {
        ChPower = chPower;

        String chPowerToStr = Utils.formatNumber(ChPower, 2, false) + " dBm";
        String finalChToStr = chPowerToStr;

        MainActivity.getActivity().runOnUiThread(() -> {

            MainActivity.getBinding().lineChartLayout.semLayout.tvSemChPwrVal.setText(finalChToStr);

        });

    }

    public Float getDensity() {
        return Density;
    }

    @SuppressLint("SetTextI18n")
    public void setDensity(Float density) {
        Density = density;

        String densityToStr = Utils.formatNumber(Density, 2, false);
        String finalDensityToStr = densityToStr;

        MainActivity.getActivity().runOnUiThread(() -> {

            MainActivity.getBinding().lineChartLayout.semLayout.tvSemDensityVal.setText(finalDensityToStr + "dBm/100kHz");

        });

    }

    public Float getLowerPkPower(int idx) {
        return LowerPkPower[idx];
    }

    public void setLowerPkPower(Float lowerPkPower, int idx) {
        LowerPkPower[idx] = lowerPkPower;

        String val = Utils.formatNumber(LowerPkPower[idx], 2, false);

        String finalVal = val;
        MainActivity.getActivity().runOnUiThread(() -> {
            switch (idx) {

                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerPkPwr1.setText(finalVal);
                    break;

                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerPkPwr2.setText(finalVal);
                    break;

                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerPkPwr3.setText(finalVal);
                    break;

                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerPkPwr4.setText(finalVal);
                    break;

            }

        });
    }

    public Float getLowerLimit(int idx) {
        return LowerLimit[idx];
    }

    public void setLowerLimit(Float lowerLimit, int idx) {
        LowerLimit[idx] = lowerLimit;

        String val = Utils.formatNumber(LowerLimit[idx], 2, false);

        String finalVal = val;

        MainActivity.getActivity().runOnUiThread(() -> {

            switch (idx) {

                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerLimit1.setText(finalVal);
                    break;

                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerLimit2.setText(finalVal);
                    break;

                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerLimit3.setText(finalVal);
                    break;

                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerLimit4.setText(finalVal);
                    break;

            }

        });
    }

    public Float getLowerFreq(int idx) {
        return LowerFreq[idx];
    }

    public void setLowerFreq(Float lowerFreq, int idx) {
        LowerFreq[idx] = lowerFreq;

        String val = Utils.formatNumber(LowerFreq[idx], 2, false);



        String finalVal = val;
        MainActivity.getActivity().runOnUiThread(() -> {

            switch (idx) {

                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerFreq1.setText(finalVal);
                    break;

                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerFreq2.setText(finalVal);
                    break;

                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerFreq3.setText(finalVal);
                    break;

                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerFreq4.setText(finalVal);
                    break;

            }

        });

    }

    public Float[] getUpperPkPower() {
        return UpperPkPower;
    }

    public void setUpperPkPower(Float upperPkPower, int idx) {
        UpperPkPower[idx] = upperPkPower;

        String val = Utils.formatNumber(UpperPkPower[idx], 2, false);

        String finalVal = val;
        MainActivity.getActivity().runOnUiThread(() -> {

            switch (idx) {

                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperPkPwr1.setText(finalVal);
                    break;

                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperPkPwr2.setText(finalVal);
                    break;

                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperPkPwr3.setText(finalVal);
                    break;

                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperPkPwr4.setText(finalVal);
                    break;

            }

        });

    }

    public Float getUpperLimit(int idx) {
        return UpperLimit[idx];
    }

    public void setUpperLimit(Float upperLimit, int idx) {
        UpperLimit[idx] = upperLimit;

        String val = Utils.formatNumber(UpperLimit[idx], 2, false);

        String finalVal = val;
        MainActivity.getActivity().runOnUiThread(() -> {

            switch (idx) {

                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperLimit1.setText(finalVal);
                    break;

                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperLimit2.setText(finalVal);
                    break;

                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperLimit3.setText(finalVal);
                    break;

                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperLimit4.setText(finalVal);
                    break;

            }

        });
    }

    public Float[] getUpperFreq() {
        return UpperFreq;
    }

    public void setUpperFreq(Float upperFreq, int idx) {
        UpperFreq[idx] = upperFreq;

        String val = Utils.formatNumber(UpperFreq[idx], 2, false);

        String finalVal = val;
        MainActivity.getActivity().runOnUiThread(() -> {

            switch (idx) {

                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperFreq1.setText(finalVal);
                    break;

                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperFreq2.setText(finalVal);
                    break;

                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperFreq3.setText(finalVal);
                    break;

                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperFreq4.setText(finalVal);
                    break;

            }

        });

    }

    public Integer getLowerPeakIndex(int idx) {
        return LowerPeakIndex[idx];
    }

    public void setLowerPeakIndex(Integer lowerPeakIndex, int idx) {
        LowerPeakIndex[idx] = lowerPeakIndex;
    }

    public Integer getLowerResult(int idx) {
        return LowerResult[idx];
    }

    public void setLowerResult(Integer lowerResult, int idx) {
        LowerResult[idx] = lowerResult;
    }

    public Integer getUpperPeakIndex(int idx) {
        return UpperPeakIndex[idx];
    }

    public void setUpperPeakIndex(Integer upperPeakIndex, int idx) {
        UpperPeakIndex[idx] = upperPeakIndex;
    }

    public Integer getUpperResult(int idx) {
        return UpperResult[idx];
    }

    public void setUpperResult(Integer upperResult, int idx) {
        UpperResult[idx] = upperResult;
    }

    public Integer getSemResult() {
        return SemResult;
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    public void setSemResult(Integer semResult) {
        SemResult = semResult;

        /*MainActivity.getActivity().runOnUiThread(() -> {

            if(SemResult == 1) {
                *//*MainActivity.getBinding().lineChartLayout.semLayout.tvSemPassTitle.setTextColor(R.color.transmit_pass);*//*
                MainActivity.getBinding().lineChartLayout.semLayout.tvSemPassTitle.setText(R.string.pass_result);
            }
            else if(SemResult == 0) {
                *//*MainActivity.getBinding().lineChartLayout.semLayout.tvSemPassTitle.setTextColor(R.color.transmit_fail);*//*
                MainActivity.getBinding().lineChartLayout.semLayout.tvSemPassTitle.setText(R.string.fail_result);
            }

        });*/
    }

}

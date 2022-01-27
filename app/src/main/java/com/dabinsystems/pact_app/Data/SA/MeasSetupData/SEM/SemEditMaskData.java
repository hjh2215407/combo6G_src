package com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM;

import android.provider.ContactsContract;
import android.util.Log;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.BWData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.FailSourceData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SideData;
import com.dabinsystems.pact_app.Data.SA.SaConfigData;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.github.mikephil.charting.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SemEditMaskData {

    public enum MASK_ON_OFF {

        ON(1),
        OFF(0);

        int value;

        MASK_ON_OFF(int val) {

            value = val;

        }

        public int getValue() {
            return value;
        }

    }

    public final int MAX_MASK_INDEX = 4;

    private Integer MaskIndex = 0;
    private MASK_ON_OFF[] MaskOnOff;
    private Double[] StartFreq;
    private Double[] StopFreq;

    private SideData.SIDE_OPTION[] MaskSide;

    private BWData[] BwData;

    private Float[] AbsStartLimit;
    private Float[] AbsStopLimit;

    private Float[] RelStartLimit;
    private Float[] RelStopLimit;

    private FailSourceData.FAIL_SOURCE[] FailSource;

    public SemEditMaskData() {

        MaskOnOff = new MASK_ON_OFF[MAX_MASK_INDEX];
        StartFreq = new Double[MAX_MASK_INDEX];
        StopFreq = new Double[MAX_MASK_INDEX];
        MaskSide = new SideData.SIDE_OPTION[MAX_MASK_INDEX];

        BwData = new BWData[MAX_MASK_INDEX];

        for (int i = 0; i < 4; i++) {

            BwData[i] = new BWData();
        }

        AbsStartLimit = new Float[MAX_MASK_INDEX];
        AbsStopLimit = new Float[MAX_MASK_INDEX];
        RelStartLimit = new Float[MAX_MASK_INDEX];
        RelStopLimit = new Float[MAX_MASK_INDEX];
        FailSource = new FailSourceData.FAIL_SOURCE[MAX_MASK_INDEX];

        Arrays.fill(MaskOnOff, MASK_ON_OFF.ON);
        Arrays.fill(StartFreq, 0d);
        Arrays.fill(StopFreq, 0d);
        Arrays.fill(MaskSide, SideData.SIDE_OPTION.BOTH);
        /*Arrays.fill(BwData, new BWData());*/
        Arrays.fill(AbsStartLimit, 0f);
        Arrays.fill(AbsStopLimit, 0f);
        Arrays.fill(RelStartLimit, 0f);
        Arrays.fill(RelStopLimit, 0f);
        Arrays.fill(FailSource, FailSourceData.FAIL_SOURCE.ABSOLUTE);

    }

    /*
    * Mask Index
    * */
    public Integer getMaskIndex() {
        return MaskIndex;
    }

    public void setMaskIndex(Integer maskIndex) {
        if (maskIndex < MAX_MASK_INDEX)
            MaskIndex = maskIndex;
    }



    /*
    * Start Freq
    * */
    public Double getStartFreq() {
        return StartFreq[MaskIndex];
    }

    public Double getStartFreq(int idx) {
        return StartFreq[idx];
    }

    public void setStartFreq(Double startFreq) {
        StartFreq[MaskIndex] = startFreq;

        double start = StartFreq[MaskIndex];
        start = Math.round(start * 100d)/100d;
        String startFreqToStr = start + " MHz";
        if (StartFreq[MaskIndex] < 0.1f) {

            start = StartFreq[MaskIndex] * 1000f;
            start = Math.round(start * 100d)/100d;

            startFreqToStr = start + " kHz";
        }
        String finalStartFreqToStr = startFreqToStr;
        MainActivity.getActivity().runOnUiThread(() -> {

            switch (MaskIndex) {

                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq1.setText(finalStartFreqToStr);
                    break;

                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq2.setText(finalStartFreqToStr);
                    break;

                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq3.setText(finalStartFreqToStr);
                    break;

                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq4.setText(finalStartFreqToStr);
                    break;

            }

        });

    }

    public void setStartFreq(Double startFreq, int idx) {

        StartFreq[idx] = startFreq;

        double start = StartFreq[idx];
        start = Math.round(start * 100d)/100d;

        String startFreqToStr = start + " MHz";
        if (StartFreq[idx] < 0.1f) {
            start = StartFreq[idx] * 1000d;
            start = Math.round(start * 100d)/100d;
            startFreqToStr = start + " kHz";
        }
        String finalStartFreqToStr = startFreqToStr;
        MainActivity.getActivity().runOnUiThread(() -> {

            switch (idx) {

                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq1.setText(finalStartFreqToStr);
                    break;

                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq2.setText(finalStartFreqToStr);
                    break;

                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq3.setText(finalStartFreqToStr);
                    break;

                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq4.setText(finalStartFreqToStr);
                    break;

            }

        });

    }



    /*
     * Stop Freq
     * */
    public Double getStopFreq() {
        return StopFreq[MaskIndex];
    }

    public Double getStopFreq(int idx) {
        return StopFreq[idx];
    }

    public void setStopFreq(Double stopFreq) {
        StopFreq[MaskIndex] = stopFreq;

        double stop = StopFreq[MaskIndex];
        stop = Math.round(stop * 100d)/100d;

        String stopFreqToStr = stop + " MHz";
        if (StopFreq[MaskIndex] < 0.1f) {
            stop = StopFreq[MaskIndex] * 1000d;
            stop = Math.round(stop * 100d)/100d;

            stopFreqToStr = stop + " kHz";
        }
        String finalStopFreqToStr = stopFreqToStr;
        MainActivity.getActivity().runOnUiThread(() -> {

            switch (MaskIndex) {

                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq1.setText(finalStopFreqToStr);
                    break;

                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq2.setText(finalStopFreqToStr);
                    break;

                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq3.setText(finalStopFreqToStr);
                    break;

                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq4.setText(finalStopFreqToStr);
                    break;

            }

        });

    }

    public void setStopFreq(Double stopFreq, int idx) {
        StopFreq[idx] = stopFreq;

        double stop = StopFreq[idx];
        stop = Math.round(stop * 100d)/100d;

        String stopFreqToStr = stop + " MHz";
        if (StopFreq[idx] < 0.1f) {
            stop = StopFreq[idx] * 1000d;
            stop = Math.round(stop * 100d)/100d;

            stopFreqToStr = stop + " kHz";
        }

        String finalStopFreqToStr = stopFreqToStr;
        MainActivity.getActivity().runOnUiThread(() -> {

            switch (idx) {

                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq1.setText(finalStopFreqToStr);
                    break;

                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq2.setText(finalStopFreqToStr);
                    break;

                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq3.setText(finalStopFreqToStr);
                    break;

                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq4.setText(finalStopFreqToStr);
                    break;

            }

        });

    }

    public void updateValue() {

//        Log.e("SEM", "SEM Update Value Called");

        for(int i=0; i<MAX_MASK_INDEX; i++) {
            //org
            /*double start = StopFreq[i];
            start = Math.round(start * 1000d)/1000d;

            String startFreqToStr = start + " MHz";
            if (StopFreq[i] < 0.1f) {
                start = StopFreq[i] * 10000d;
                start = Math.round(start * 1000d)/1000d;

                startFreqToStr = start + " kHz";
            }*/
            //org

            //@@ [21.12.29] SEM Info Table Fix

            if (getMaskOnOff() == MASK_ON_OFF.OFF) {
                continue;
            }
            double start = StartFreq[i];
            start = Math.round(start * 1000d)/1000d;

            String startFreqToStr = start + " MHz";
            if (StartFreq[i] < 0.1f) {
                start = StartFreq[i] * 10000d;
                start = Math.round(start * 1000d)/1000d;

                startFreqToStr = start + " kHz";
            }
            //@@

            String finalStartFreqToStr = startFreqToStr;
            int finalI = i;

            MainActivity.getActivity().runOnUiThread(() -> {

                switch (finalI) {

                    case 0:
                        MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq1.setText(finalStartFreqToStr);
                        break;

                    case 1:
                        MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq2.setText(finalStartFreqToStr);
                        break;

                    case 2:
                        MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq3.setText(finalStartFreqToStr);
                        break;

                    case 3:
                        MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq4.setText(finalStartFreqToStr);
                        break;

                }

            });

            double stop = StopFreq[i];
            stop = Math.round(stop * 1000d)/1000d;

            String stopFreqToStr = stop + " MHz";
            if (StopFreq[i] < 0.1f) {
                stop = StopFreq[i] * 10000d;
                stop = Math.round(stop * 1000d)/1000d;

                stopFreqToStr = stop + " kHz";
            }

            String finalStopFreqToStr = stopFreqToStr;
            int finalI1 = i;

            MainActivity.getActivity().runOnUiThread(() -> {

                switch (finalI1) {

                    case 0:
                        MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq1.setText(finalStopFreqToStr);
                        break;

                    case 1:
                        MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq2.setText(finalStopFreqToStr);
                        break;

                    case 2:
                        MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq3.setText(finalStopFreqToStr);
                        break;

                    case 3:
                        MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq4.setText(finalStopFreqToStr);
                        break;

                }

            });

            // Mask RBW
            String finalMaskRbwStr = BwData[i].getRBW().getString();
            int finalI2 = i;

            MainActivity.getActivity().runOnUiThread(() -> {

                switch (finalI2) {
                    case 0:
                        MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW1.setText(getBwData(0).getRBW().getString());
                        break;
                    case 1:
                        MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW2.setText(getBwData(1).getRBW().getString());
                        break;
                    case 2:
                        MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW3.setText(getBwData(2).getRBW().getString());
                        break;
                    case 3:
                        MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW4.setText(getBwData(3).getRBW().getString());
                        break;
                }

            });

        }

    }



    /*
     * Mask Side
     * */
    public SideData.SIDE_OPTION getMaskSide() {
        return MaskSide[MaskIndex];
    }

    public SideData.SIDE_OPTION getMaskSide(int idx) {
        return MaskSide[idx];
    }

    public void setMaskSide(SideData.SIDE_OPTION maskSide) {
        MaskSide[MaskIndex] = maskSide;
    }

    public void setMaskSide(SideData.SIDE_OPTION maskSide, int idx) {
        MaskSide[idx] = maskSide;
    }



    /*
     * BW Data
     * */
    public BWData getBwData() {

        return BwData[MaskIndex];
    }

    public BWData getBwData(int idx) {
        return BwData[idx];
    }

    public void setBwData(BWData bwData) {
        BwData[MaskIndex] = bwData;
    }

    public void setBwData(BWData bwData, int idx) {
        BwData[idx] = bwData;
    }

    /*
     * Abs Start Freq
     * */
    public Float getAbsStartLimit() {
        return AbsStartLimit[MaskIndex];
    }

    public Float getAbsStartLimit(int idx) {
        return AbsStartLimit[idx];
    }

    public void setAbsStartLimit(Float absStartLimit) {
        AbsStartLimit[MaskIndex] = absStartLimit;
    }

    public void setAbsStartLimit(Float absStartLimit, int idx) {
        AbsStartLimit[idx] = absStartLimit;
    }




    /*
     * Abs Stop Freq
     * */
    public Float getAbsStopLimit() {
        return AbsStopLimit[MaskIndex];
    }

    public Float getAbsStopLimit(int idx) {
        return AbsStopLimit[idx];
    }

    public void setAbsStopLimit(Float absStopLimit) {
        AbsStopLimit[MaskIndex] = absStopLimit;
    }

    public void setAbsStopLimit(Float absStopLimit, int idx) {
        AbsStopLimit[idx] = absStopLimit;
    }




    /*
     * Rel Start Freq
     * */
    public Float getRelStartLimit() {
        return RelStartLimit[MaskIndex];
    }

    public Float getRelStartLimit(int idx) {
        return RelStartLimit[idx];
    }

    public void setRelStartLimit(Float relStartLimit) {
        RelStartLimit[MaskIndex] = relStartLimit;
    }

    public void setRelStartLimit(Float relStartLimit, int idx) {
        RelStartLimit[idx] = relStartLimit;
    }



    /*
     * Rel Stop Freq
     * */
    public Float getRelStopLimit() {
        return RelStopLimit[MaskIndex];
    }

    public Float getRelStopLimit(int idx) {
        return RelStopLimit[idx];
    }

    public void setRelStopLimit(Float relStopLimit) {
        RelStopLimit[MaskIndex] = relStopLimit;
    }

    public void setRelStopLimit(Float relStopLimit, int idx) {
        RelStopLimit[idx] = relStopLimit;
    }



    /*
     * Fail Source
     * */
    public FailSourceData.FAIL_SOURCE getFailSource() {
        return FailSource[MaskIndex];
    }

    public FailSourceData.FAIL_SOURCE getFailSource(int idx) {
        return FailSource[idx];
    }

    public void setFailSource(FailSourceData.FAIL_SOURCE failSource) {
        FailSource[MaskIndex] = failSource;
    }

    public void setFailSource(FailSourceData.FAIL_SOURCE failSource, int idx) {
        FailSource[idx] = failSource;
    }



    /*
    *  Mask On Off
    * */

    public MASK_ON_OFF getMaskOnOff() {
        return MaskOnOff[MaskIndex];
    }

    public MASK_ON_OFF getMaskOnOff(int idx) {
        return MaskOnOff[idx];
    }

    public void setMaskOnOff(MASK_ON_OFF maskOnOff) {
        MaskOnOff[MaskIndex] = maskOnOff;
        resetSubInfo(maskOnOff);
    }

    public void setMaskOnOff(MASK_ON_OFF maskOnOff, int idx) {
        MaskOnOff[idx] = maskOnOff;
    }

    public void setSemSpan() {

        SaConfigData data = SaDataHandler.getInstance().getConfigData();
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (type != MeasureType.MEASURE_TYPE.SEM) return;

        double semSpan = 0;
        double refChannelSpan = data.getSemMeasSetupData().getRefChannelData().getSpan();

        ArrayList<Double> temp = new ArrayList<>();

        //in case mask off, pass stop freq data
        for (int i = 0; i < StopFreq.length; i++) {
            if (MaskOnOff[i] == MASK_ON_OFF.OFF) continue;
            temp.add(StopFreq[i]);
        }

        //List<Double> temp = Arrays.asList(StopFreq.clone());
        double maxStopFreq = Collections.max(temp);

        semSpan = refChannelSpan + maxStopFreq * 2;

        /*Log.e("SEM", "ref Span : " + refChannelSpan + ", maxStopFreq : " + maxStopFreq);
        Log.e("SEM", "SEM SPAN : " + semSpan + " ?");*/

        data.getFrequencyData().updatePrevFreq();

        if(semSpan == 0f) {
            SaDataHandler.getInstance().getConfigData().getFrequencyData().zerospan();
        } else {

            SaDataHandler.getInstance().getConfigData().getFrequencyData().setSpan(semSpan);
            SaDataHandler.getInstance().getConfigData().getFrequencyData().checkFreqRange();

            float curStartFreq = FunctionHandler.getInstance().getMainLineChart().getStartFreq();
            float curStopFreq = FunctionHandler.getInstance().getMainLineChart().getStopFreq();

            Double newStartFreq = SaDataHandler.getInstance().getConfigData().getFrequencyData().getStartFreq();
            Double newStopFreq = SaDataHandler.getInstance().getConfigData().getFrequencyData().getStopFreq();

            /*Log.e("Span", "Pre Freq : (" + curStartFreq + " ~ " + curStopFreq +")");
            Log.e("Span", "Will be set Freq : (" + newStartFreq + " ~ " + newStopFreq +")");*/

            if(curStartFreq == newStartFreq && curStopFreq == newStopFreq) {
                return ;
            }

//                    FunctionHandler.getInstance().getMainLineChart().setStartFreq(
//                            SaDataHandler.getInstance().getConfigData().getFrequencyData().getStartFreq()
//                    );
//
//                    FunctionHandler.getInstance().getMainLineChart().setStopFreq(
//                            SaDataHandler.getInstance().getConfigData().getFrequencyData().getStopFreq()
//                    );

        }



        FunctionHandler.getInstance().getMainLineChart().updateInterferenceHuntingBox();
        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
        ViewHandler.getInstance().getSASpanView().updateSpan();
        ViewHandler.getInstance().getContent().subInfoUpdate();

        FunctionHandler.getInstance().getMainLineChart().updateMarkerPos();

        ViewHandler.getInstance().getContent().markerTableUpdate();
        ViewHandler.getInstance().getContent().markerValueUpdate();
        ViewHandler.getInstance().getSaMarkerView2().update();
        FunctionHandler.getInstance().getMainLineChart().updateSemBox();
        /*FunctionHandler.getInstance().getDataConnector().sendCommand(
                DataHandler.getInstance().getConfigCmd()
        );*/

    }

    public void resetSubInfo(MASK_ON_OFF maskOnOff) {
        if (maskOnOff == SemEditMaskData.MASK_ON_OFF.OFF) {
            switch (MaskIndex) {
                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerPkPwr1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerLimit1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerFreq1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperPkPwr1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperLimit1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperFreq1.setText("-");
                    break;
                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerPkPwr2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerLimit2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerFreq2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperPkPwr2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperLimit2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperFreq2.setText("-");
                    break;
                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerPkPwr3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerLimit3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerFreq3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperPkPwr3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperLimit3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperFreq3.setText("-");
                    break;
                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerPkPwr4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerLimit4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerFreq4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperPkPwr4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperLimit4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperFreq4.setText("-");
                    break;
            }
        }
        else {

            //float integBW = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getRefChannelData().getIntegBw();

            switch (MaskIndex) {
                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq1.setText(getStartFreq(0).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq1.setText(getStopFreq(0).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW1.setText(getBwData(0).getRBW().getString());
                    break;
                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq2.setText(getStartFreq(1).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq2.setText(getStopFreq(1).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW2.setText(getBwData(1).getRBW().getString());
                    break;
                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq3.setText(getStartFreq(2).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq3.setText(getStopFreq(2).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW3.setText(getBwData(2).getRBW().getString());
                    break;
                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq4.setText(getStartFreq(3).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq4.setText(getStopFreq(3).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW4.setText(getBwData(3).getRBW().getString());
                    break;
            }
        }
    }

    public void resetSubInfo(MASK_ON_OFF maskOnOff, int idx) {
        if (maskOnOff == SemEditMaskData.MASK_ON_OFF.OFF) {
            switch (idx) {
                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerPkPwr1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerLimit1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerFreq1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperPkPwr1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperLimit1.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperFreq1.setText("-");
                    break;
                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerPkPwr2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerLimit2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerFreq2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperPkPwr2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperLimit2.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperFreq2.setText("-");
                    break;
                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerPkPwr3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerLimit3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerFreq3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperPkPwr3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperLimit3.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperFreq3.setText("-");
                    break;
                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerPkPwr4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerLimit4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemLowerFreq4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperPkPwr4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperLimit4.setText("-");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemUpperFreq4.setText("-");
                    break;
            }
        }
        else {

            switch (MaskIndex) {
                case 0:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq1.setText(getStartFreq(0).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq1.setText(getStopFreq(0).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW1.setText(getBwData(0).getRBW().getString());
                    break;
                case 1:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq2.setText(getStartFreq(1).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq2.setText(getStopFreq(1).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW2.setText(getBwData(1).getRBW().getString());
                    break;
                case 2:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq3.setText(getStartFreq(2).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq3.setText(getStopFreq(2).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW3.setText(getBwData(2).getRBW().getString());
                    break;
                case 3:
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStartFreq4.setText(getStartFreq(3).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemStopFreq4.setText(getStopFreq(3).toString() + " MHz");
                    MainActivity.getBinding().lineChartLayout.semLayout.tvSemMaskRBW4.setText(getBwData(3).getRBW().getString());
                    break;
            }
        }
    }

}

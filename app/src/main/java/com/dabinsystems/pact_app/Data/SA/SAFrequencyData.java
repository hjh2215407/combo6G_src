package com.dabinsystems.pact_app.Data.SA;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;

public class SAFrequencyData {

    /*
     * set Frequency
     * */

    public final double DEFAULT_MIN_FREQ = 60d;
    public final double DEFAULT_MAX_FREQ = 6000d;
    public final double DEFAULT_MIN_CENTER_FREQ = 70d;
    public final double DEFAULT_MAX_CENTER_FREQ = 5990d;

    private Double CenterFreq = 3600.01d;
    private Double StartFreq = 3575.01d;
    private Double StopFreq = 3725.01d;
    private Double Span = 150d;
    private Double PrevCenterFreq = 3600.01d;
    private Double PrevStartFreq = 3575.01d;
    private Double PrevStopFreq = 3725.01d;
    private Double PrevSpan = 150d;

    public SAFrequencyData(MeasureType.MEASURE_TYPE type) {


    }

    /* start get/set method */

    public void setFreq(double start, double stop) {

        StartFreq = start;
        StopFreq = stop;
        CenterFreq = (StartFreq + StopFreq) / 2;
        Span = StopFreq - StartFreq;

        checkFreqRange();
    }

    public double getStartFreq() {
        return StartFreq;
    }

    public void setStartFreq(double startFreq) {

        if(CenterFreq != 10d && (startFreq < DEFAULT_MIN_FREQ || startFreq > DEFAULT_MAX_FREQ || startFreq > StopFreq)) {
            InitActivity.logMsg("SaFreq", "Start Freq Out of range : " + startFreq);
            Toast.makeText(MainActivity.getContext(), "out of range", Toast.LENGTH_SHORT).show();
            return;
        }

        startFreq = Math.round(startFreq * 1000000d) / 1000000d;

        if (Span == 0f) {
            StartFreq = startFreq;
            CenterFreq = (StartFreq + StopFreq) / 2;
            return;
        }

        StartFreq = startFreq;
        CenterFreq = (StartFreq + StopFreq) / 2;
        Span = StopFreq - StartFreq;

        CenterFreq = Math.round(CenterFreq * 1000000d)/1000000d;
        Span = Math.round(Span * 1000000d)/1000000d;

        InitActivity.logMsg("SaFreq", "setStartFreq : " + StartFreq);

    }

    public double getStopFreq() {
        return StopFreq;
    }

    public void setStopFreq(double stopFreq) {

        if(CenterFreq != 10f && (stopFreq < DEFAULT_MIN_FREQ || stopFreq > DEFAULT_MAX_FREQ || stopFreq < StartFreq)) {
            Toast.makeText(MainActivity.getContext(), "out of range", Toast.LENGTH_SHORT).show();
            InitActivity.logMsg("setStopFreq", "out of range");
            return;
        }

        stopFreq = Math.round(stopFreq * 1000000d) / 1000000d;

        if (Span == 0f) {
            StopFreq = stopFreq;
            CenterFreq = (StartFreq + StopFreq) / 2;
            return;
        }

        StopFreq = stopFreq;
        CenterFreq = (StartFreq + StopFreq) / 2;
        Span = StopFreq - StartFreq;

        CenterFreq = Math.round(CenterFreq * 1000000d)/1000000d;
        Span = Math.round(Span * 1000000d)/1000000d;

    }

    public double getCenterFreq() {
        return CenterFreq;
    }

    public void setCenterFreq(double centerFreq) {

        if(centerFreq != 10d && (centerFreq < DEFAULT_MIN_CENTER_FREQ || centerFreq > DEFAULT_MAX_CENTER_FREQ)) {
            Toast.makeText(MainActivity.getContext(), "out of range", Toast.LENGTH_SHORT).show();
            return;
        }

        centerFreq = Math.round(centerFreq * 1000000d) / 1000000d;

        if (Span == 0d) {
            CenterFreq = centerFreq;
            return;
        }

        CenterFreq = centerFreq;
        StartFreq = CenterFreq - (Span / 2);
        StopFreq = CenterFreq + (Span / 2);
        Span = StopFreq - StartFreq;

        StartFreq = Math.round(StartFreq * 1000000d)/1000000d;
        Span = Math.round(Span * 1000000d)/1000000d;
        StopFreq = Math.round(StopFreq * 1000000d)/1000000d;

    }

    public double getSpan() {
        return Span;
    }

    public void setSpan(double span) {

        if(span < 0d || span > 5940d) {
            Toast.makeText(MainActivity.getContext(), "out of range ( 0 < value <= 5940 )", Toast.LENGTH_SHORT).show();
            return;
        }

        span = Math.round(span * 1000000d) / 1000000d;

        InitActivity.logMsg("setSpan", span + "");

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if(type == MeasureType.MEASURE_TYPE.SWEPT_SA && span == 0d && Span != 0d) {
            zerospan();
            return;
        } else if (span == 0d) return;

        Span = span;
        StartFreq = CenterFreq - (Span / 2);
        StopFreq = CenterFreq + (Span / 2);

        StartFreq = Math.round(StartFreq * 1000000d)/1000000d;
        StopFreq = Math.round(StopFreq * 1000000d)/1000000d;

    }

    public void updatePrevFreq() {

        PrevSpan = Span;
        PrevCenterFreq = CenterFreq;
        PrevStartFreq = StartFreq;
        PrevStopFreq = StopFreq;

        FunctionHandler.getInstance().getMainLineChart().invalidate();

    }

    public void zerospan() {
        InitActivity.logMsg("zerospan", "Span : " + Span);

        Span = 0d;

        SaSweepTimeData sweepTimeData = SaDataHandler.getInstance().getConfigData().getSweepTimeData();
        sweepTimeData.setMode(SaSweepTimeData.SWEEP_TIME_MODE.MANUAL);
        sweepTimeData.setSweepTime(5000);

        ViewHandler.getInstance().getContent().markerTableUpdate();
        ViewHandler.getInstance().getContent().markerValueUpdate();
        ViewHandler.getInstance().getSaMarkerView2().updateValue();

    }

    public void setPrevFreq() {
        double tempSpan = Span;
        double tempStart = StartFreq;
        double tempStop = StopFreq;
        double tempCenter = CenterFreq;

        StartFreq = PrevStartFreq;
        StopFreq = PrevStopFreq;
        CenterFreq = PrevCenterFreq;
        Span = PrevSpan;

        PrevSpan = tempSpan;
        PrevStopFreq = tempStop;
        PrevCenterFreq = tempCenter;
        PrevStartFreq = tempStart;

        if(Span == 0f) zerospan();

        InitActivity.logMsg("setPrevFreq", "Span : " + Span);

    }

    public void setFullSpan() {

        StartFreq = DEFAULT_MIN_FREQ;
        StopFreq = DEFAULT_MAX_FREQ;
        Span = StopFreq - StartFreq;
        CenterFreq = (StartFreq + StopFreq)/2f;

    }

    public void checkFreqRange() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK)
            return;

        boolean isPass = true;

        if (CenterFreq == 10d) {

            if(SaDataHandler.getInstance().getConfigData().getBwData().getRBW() != BWData.BAND_WIDTH.HZ1) {
                if (Span > 1d)
                    setSpan(1);
            } else {
                if (Span > 0.004d)
                    setSpan(0.004d);
            }


            SaDataHandler.getInstance().getConfigData().getAmplitudeData().setAttenuator(0);
            SaDataHandler.getInstance().getConfigData().getAmplitudeData().setPreampMode(SaAmplitudeData.PREAMP_MODE.OFF);

            ViewHandler.getInstance().getSAView().update();
            ViewHandler.getInstance().getSAAmplitudeView().update();

        }

        else if(CenterFreq < DEFAULT_MIN_CENTER_FREQ || CenterFreq > DEFAULT_MAX_CENTER_FREQ) {

            isPass = false;

        } else {

            double startFreq = CenterFreq - Span/2;
            double stopFreq = CenterFreq + Span/2;

            if(startFreq < DEFAULT_MIN_FREQ) {
                setSpan((CenterFreq - DEFAULT_MIN_FREQ) * 2);
            } else if(stopFreq > DEFAULT_MAX_FREQ) {
                setSpan((DEFAULT_MAX_FREQ - CenterFreq) * 2);
            }

        }

        if(PrevSpan == 0f && Span != 0f) {
            SaDataHandler.getInstance().getConfigData().getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            ViewHandler.getInstance().getSaSweepView().update();
        }

//        if (StartFreq < DEFAULT_MIN_FREQ || StartFreq > DEFAULT_MAX_FREQ || StartFreq >= StopFreq) {
//            InitActivity.logMsg("out of range", "start freq");
//            setStartFreq(DEFAULT_MIN_FREQ);
//            isPass = false;
//        }
//
//        if (StopFreq > DEFAULT_MAX_FREQ || StopFreq < DEFAULT_MIN_FREQ || StartFreq >= StopFreq) {
//            InitActivity.logMsg("out of range", "stop freq");
//            setStopFreq(DEFAULT_MAX_FREQ);
//            isPass = false;
//        }

        if (!isPass) {
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(MainActivity.getActivity(), "Out of range(" + DEFAULT_MIN_FREQ + " ~ " + DEFAULT_MAX_FREQ + " MHz)", Toast.LENGTH_SHORT).show();
            });
        }
    }

}

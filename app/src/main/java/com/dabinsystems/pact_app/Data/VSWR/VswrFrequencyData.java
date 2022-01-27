package com.dabinsystems.pact_app.Data.VSWR;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Function.CalibrationFunc;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Handler.FunctionHandler;

public class VswrFrequencyData {

    /*
     * Frequency
     **/

    public static final double DEFAULT_START_FREQ = 3600.01d;
    public static final double DEFAULT_CENTER_FREQ = 3650.01d;
    public static final double DEFAULT_STOP_FREQ = 3700.01d;
    public static final double DEFAULT_SPAN_FREQ = 100d;
    public static final double DEFALUT_MIN_FREQ = 650d;
    public static final double DEFALUT_MAX_FREQ = 4200d;//6000d;
    public static final double DEFALUT_MIN_CENTER_FREQ = 650.5d;
    public static final double DEFALUT_MAX_CENTER_FREQ = 4199.5d;
    public static final double DEFALUT_MIN_SPAN_FREQ = 1d;
    public static final double DEFALUT_MAX_SPAN_FREQ = 3550d;//5350d;

    private Double StartFreq = DEFAULT_START_FREQ;
    private Double StopFreq = DEFAULT_STOP_FREQ;
    private Double CenterFreq = DEFAULT_CENTER_FREQ;
    private Double Span = DEFAULT_SPAN_FREQ;

    public VswrFrequencyData() {

        setStartFreq(DEFAULT_START_FREQ);
        setStopFreq(DEFAULT_STOP_FREQ);

    }

    public double getStartFreq() {
        return StartFreq;
    }

    public void setFreq(double startFreq, double stopFreq) {

        Double fwStart = FunctionHandler.getInstance().getCalibrationFunc().getCalStartFreq();

        if (startFreq < DEFALUT_MIN_FREQ || startFreq > DEFALUT_MAX_FREQ) {
            Toast.makeText(MainActivity.getActivity(), "Out of Range(" + DEFALUT_MIN_FREQ + " ~ " + DEFALUT_MAX_FREQ + ")", Toast.LENGTH_SHORT).show();
            return;
        } else if (FunctionHandler.getInstance().getCalibrationFunc().isUserCal() &&
                !FunctionHandler.getInstance().getCalibrationFunc().isFlex() &&
                fwStart != null && startFreq < fwStart) {

            Toast.makeText(MainActivity.getActivity(), "The frequency has changed and calibration needs to be reset.", Toast.LENGTH_LONG).show();
            FunctionHandler.getInstance().getCalibrationFunc().setUserCal(CalibrationFunc.USER_CAL.OFF);
            FunctionHandler.getInstance().getCalibrationFunc().resetStep();
            MainActivity.getBinding().recallMessageLayout.tvWarningCal.setVisibility(View.VISIBLE);

            return;

        } else {

            MainActivity.getBinding().recallMessageLayout.tvWarningCal.setVisibility(View.GONE);

        }

        Double fwStop = FunctionHandler.getInstance().getCalibrationFunc().getCalStopFreq();

        if (stopFreq < DEFALUT_MIN_FREQ || stopFreq > DEFALUT_MAX_FREQ) {
            Toast.makeText(MainActivity.getActivity(), "Out of Range(" + DEFALUT_MIN_FREQ + " ~ " + DEFALUT_MIN_FREQ + ")", Toast.LENGTH_SHORT).show();
            return;
        } else if (FunctionHandler.getInstance().getCalibrationFunc().isUserCal() &&
                !FunctionHandler.getInstance().getCalibrationFunc().isFlex() &&
                fwStop != null &&
                stopFreq > fwStop) {

            Toast.makeText(MainActivity.getActivity(), "The frequency has changed and calibration needs to be reset.", Toast.LENGTH_LONG).show();
            FunctionHandler.getInstance().getCalibrationFunc().setUserCal(CalibrationFunc.USER_CAL.OFF);
            FunctionHandler.getInstance().getCalibrationFunc().resetStep();
            MainActivity.getBinding().recallMessageLayout.tvWarningCal.setVisibility(View.VISIBLE);

            return;

        } else {
            MainActivity.getBinding().recallMessageLayout.tvWarningCal.setVisibility(View.GONE);

        }

        StartFreq = startFreq;
        StopFreq = stopFreq;

        CenterFreq = (StartFreq + StopFreq) / 2;
        Span = StopFreq - StartFreq;

        checkFreqRange();

        InitActivity.logMsg("VSWR Frequency", "set freq : " + StartFreq + " " + StopFreq);
        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

        if (mode != MeasureMode.MEASURE_MODE.DTF) {
            FunctionHandler.getInstance().getMainLineChart().invalidate();
        }

    }

    public void setStartFreq(double startFreq) {
        Double fwStart = FunctionHandler.getInstance().getCalibrationFunc().getCalStartFreq();

        if (startFreq < DEFALUT_MIN_FREQ || startFreq > DEFALUT_MAX_FREQ) {
            Toast.makeText(MainActivity.getActivity(), "Out of Range(" + DEFALUT_MIN_FREQ + " ~ " + DEFALUT_MAX_FREQ + ")", Toast.LENGTH_SHORT).show();
            return;
        }

        StartFreq = startFreq;

        CenterFreq = (StartFreq + StopFreq) / 2;
        Span = StopFreq - StartFreq;

        checkFreqRange();

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

        if (mode != MeasureMode.MEASURE_MODE.DTF) {
//            FunctionHandler.getInstance().getMainLineChart().setStartFreq(StartFreq);
            FunctionHandler.getInstance().getMainLineChart().invalidate();
        }

        if (FunctionHandler.getInstance().getCalibrationFunc().isUserCal() &&
                !FunctionHandler.getInstance().getCalibrationFunc().isFlex() &&
                fwStart != null && startFreq < fwStart) {

            Toast.makeText(MainActivity.getActivity(), "The frequency has changed and calibration needs to be reset.", Toast.LENGTH_LONG).show();
            FunctionHandler.getInstance().getCalibrationFunc().setUserCal(CalibrationFunc.USER_CAL.OFF);
            FunctionHandler.getInstance().getCalibrationFunc().resetStep();
            MainActivity.getBinding().recallMessageLayout.tvWarningCal.setVisibility(View.VISIBLE);

            return;

        } else {

            MainActivity.getBinding().recallMessageLayout.tvWarningCal.setVisibility(View.GONE);

        }

    }

    public double getStopFreq() {
        return StopFreq;
    }

    public void setStopFreq(double stopFreq) {

        Double fwStop = FunctionHandler.getInstance().getCalibrationFunc().getCalStopFreq();

        if (stopFreq < DEFALUT_MIN_FREQ || stopFreq > DEFALUT_MAX_FREQ) {
            Toast.makeText(MainActivity.getActivity(), "Out of Range(" + DEFALUT_MIN_FREQ + " ~ " + DEFALUT_MAX_FREQ + ")", Toast.LENGTH_SHORT).show();
            return;
        }

        StopFreq = stopFreq;

        CenterFreq = (StartFreq + StopFreq) / 2;
        Span = StopFreq - StartFreq;

        checkFreqRange();

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        if (mode != MeasureMode.MEASURE_MODE.DTF) {
//            FunctionHandler.getInstance().getMainLineChart().setStopFreq(StopFreq);
            FunctionHandler.getInstance().getMainLineChart().invalidate();
        }

        if (FunctionHandler.getInstance().getCalibrationFunc().isUserCal() &&
                !FunctionHandler.getInstance().getCalibrationFunc().isFlex() &&
                fwStop != null &&
                stopFreq > fwStop) {

            Toast.makeText(MainActivity.getActivity(), "The frequency has changed and calibration needs to be reset.", Toast.LENGTH_LONG).show();
            FunctionHandler.getInstance().getCalibrationFunc().setUserCal(CalibrationFunc.USER_CAL.OFF);
            FunctionHandler.getInstance().getCalibrationFunc().resetStep();
            MainActivity.getBinding().recallMessageLayout.tvWarningCal.setVisibility(View.VISIBLE);

            return;

        } else {
            MainActivity.getBinding().recallMessageLayout.tvWarningCal.setVisibility(View.GONE);

        }

    }

    public double getCenterFreq() {
        return CenterFreq;
    }

    public void setCenterFreq(double centerFreq) {

        double start = centerFreq - (Span / 2);
        double stop = centerFreq + (Span / 2);

        Double fwStart = FunctionHandler.getInstance().getCalibrationFunc().getCalStartFreq();
        Double fwStop = FunctionHandler.getInstance().getCalibrationFunc().getCalStopFreq();

        if (start < DEFALUT_MIN_FREQ || start > DEFALUT_MAX_FREQ ||
                stop < DEFALUT_MIN_FREQ || stop > DEFALUT_MAX_FREQ) {
            Toast.makeText(MainActivity.getActivity(), "Out of Range(" + DEFALUT_MIN_FREQ + " ~ " + DEFALUT_MAX_FREQ + ")", Toast.LENGTH_SHORT).show();
            return;
        }

        CenterFreq = centerFreq;
        StartFreq = CenterFreq - (Span / 2);
        StopFreq = CenterFreq + (Span / 2);

        checkFreqRange();

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        if (mode != MeasureMode.MEASURE_MODE.DTF) {
//            FunctionHandler.getInstance().getMainLineChart().setCenterFreq(CenterFreq);
//            FunctionHandler.getInstance().getMainLineChart().setSpan(Span);
            FunctionHandler.getInstance().getMainLineChart().invalidate();
        }

        if (FunctionHandler.getInstance().getCalibrationFunc().isUserCal() &&
                !FunctionHandler.getInstance().getCalibrationFunc().isFlex() &&
                fwStop != null &&
                fwStart != null &&
                (start < fwStart ||
                        stop > fwStop)) {

            InitActivity.logMsg("setCenterFreq", "VSWR " + centerFreq);
            Toast.makeText(MainActivity.getActivity(), "The frequency has changed and calibration needs to be reset.", Toast.LENGTH_LONG).show();
            FunctionHandler.getInstance().getCalibrationFunc().setUserCal(CalibrationFunc.USER_CAL.OFF);
            FunctionHandler.getInstance().getCalibrationFunc().resetStep();
            MainActivity.getBinding().recallMessageLayout.tvWarningCal.setVisibility(View.VISIBLE);

            return;

        } else {

            MainActivity.getBinding().recallMessageLayout.tvWarningCal.setVisibility(View.GONE);

        }


    }

    public double getSpan() {
        return Span;
    }

    public void setSpan(double span) {

        double start = CenterFreq - (span / 2);
        double stop = CenterFreq + (span / 2);
        Double fwStart = FunctionHandler.getInstance().getCalibrationFunc().getCalStartFreq();
        Double fwStop = FunctionHandler.getInstance().getCalibrationFunc().getCalStopFreq();

        if (start < DEFALUT_MIN_FREQ || start > DEFALUT_MAX_FREQ ||
                stop < DEFALUT_MIN_FREQ || stop > DEFALUT_MAX_FREQ) {
            Toast.makeText(MainActivity.getActivity(), "Out of Range(" + DEFALUT_MIN_FREQ + " ~ " + DEFALUT_MAX_FREQ + ")", Toast.LENGTH_SHORT).show();
            return;
        }

        Span = span;
        StartFreq = CenterFreq - (Span / 2);
        StopFreq = CenterFreq + (Span / 2);

        checkFreqRange();

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        if (mode != MeasureMode.MEASURE_MODE.DTF) {
//            FunctionHandler.getInstance().getMainLineChart().setSpan(Span);
//            FunctionHandler.getInstance().getMainLineChart().setCenterFreq(CenterFreq);
            FunctionHandler.getInstance().getMainLineChart().invalidate();
        }

        if (FunctionHandler.getInstance().getCalibrationFunc().isUserCal() &&
                !FunctionHandler.getInstance().getCalibrationFunc().isFlex() &&
                fwStop != null &&
                fwStart != null &&
                (start < fwStart || stop > fwStop)) {

            Toast.makeText(MainActivity.getActivity(), "The frequency has changed and calibration needs to be reset.", Toast.LENGTH_LONG).show();
            FunctionHandler.getInstance().getCalibrationFunc().setUserCal(CalibrationFunc.USER_CAL.OFF);
            FunctionHandler.getInstance().getCalibrationFunc().resetStep();
            MainActivity.getBinding().recallMessageLayout.tvWarningCal.setVisibility(View.VISIBLE);

            return;

        } else {

            MainActivity.getBinding().recallMessageLayout.tvWarningCal.setVisibility(View.GONE);

        }

    }

    public void checkFreqRange() {

        if (StartFreq < DEFALUT_MIN_FREQ || StartFreq > DEFALUT_MAX_FREQ || StartFreq >= StopFreq) {
            if (StopFreq == DEFALUT_MIN_FREQ)
                setStopFreq(DEFALUT_MIN_FREQ + 1d);
            else
                setStartFreq(DEFALUT_MIN_FREQ);
            InitActivity.logMsg("VSWR Frequency", "out of range1 : " + StartFreq + " " + StopFreq);
        }

        if (StopFreq > DEFALUT_MAX_FREQ || StopFreq < DEFALUT_MIN_FREQ || StartFreq >= StopFreq) {
            setStopFreq(DEFALUT_MAX_FREQ);
            InitActivity.logMsg("VSWR Frequency", "out of range2 : " + StartFreq + " " + StopFreq);
        }

        InitActivity.logMsg("SAFrequencyData", "Center Freq : " + CenterFreq);

    }


}

package com.dabinsystems.pact_app.Data.SA;

import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;

public class SaSweepTimeData {

    public enum SWEEP_TIME_MODE {

        MANUAL(0),
        AUTO(1);

        private final int value;

        SWEEP_TIME_MODE(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public byte getByte() {

            int val = value & 0xff;
            return (byte)val;

        }

    }

    private SWEEP_TIME_MODE SweepTimeMode = SWEEP_TIME_MODE.AUTO;

    private long MAX_SWEEP_TIME = 32000000000L;
    private int MIN_SWEEP_TIME = 100;
    private long mSweepTime = 5000L;

    private SaGateData GateData;

    public SaSweepTimeData(MeasureType.MEASURE_TYPE type) {

        GateData = new SaGateData();

        if(type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            MIN_SWEEP_TIME = 100;
            MAX_SWEEP_TIME = 32000000000L;

        } else if(type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            MIN_SWEEP_TIME = 5000;
            MAX_SWEEP_TIME = 32000000000L;

        } else if(type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            MIN_SWEEP_TIME = 100;
            MAX_SWEEP_TIME = 32000000000L;

        }

    }

    public void setMode(SWEEP_TIME_MODE mode) {

        this.SweepTimeMode = mode;

    }

    public SWEEP_TIME_MODE getSweepTimeMode() {

        return SweepTimeMode;

    }

    public void setSweepTime(long time) {

        if(time < MIN_SWEEP_TIME || time > MAX_SWEEP_TIME) {
            Toast.makeText(MainActivity.getActivity(), "Out of range(" + MIN_SWEEP_TIME + "us ~ 1600000000us)", Toast.LENGTH_SHORT).show();
            return;
        }
        mSweepTime = time;
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if(SaDataHandler.getInstance().getConfigData().getFrequencyData().getSpan() == 0f) {

//            FunctionHandler.getInstance().getMainLineChart().setStartFreq(0f);
//            FunctionHandler.getInstance().getMainLineChart().setStopFreq((float)getSweepTimeToMs());
            FunctionHandler.getInstance().getMainLineChart().update();

        }

    }

    public long getSweepTime() {
        return mSweepTime;
    }

    public double getSweepTimeToMs() {

        double val = mSweepTime / Math.pow(10, 3);

        return val;

    }

    public SaGateData getGateData() {

        return GateData;

    }

    public String getSweepTimeToString() {

        double val = mSweepTime / Math.pow(10, 3);
        if(val >= 1000) {
            val /= Math.pow(10, 3);
            String format = val + " s";
            return format;
        } else {
            String format = val + " ms";
            return format;
        }

    }

}

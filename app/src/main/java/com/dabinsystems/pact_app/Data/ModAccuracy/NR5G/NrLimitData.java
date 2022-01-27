package com.dabinsystems.pact_app.Data.ModAccuracy.NR5G;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.DemodulationLayoutBinding;

public class NrLimitData {

    public float MIN_EVM = 0.1f;
    public float MAX_EVM = 100f;

    public int MIN_TAE = 0;
    public int MAX_TAE = 5000;

    public float MIN_FREQ_OFFSET = 0;
    public float MAX_FREQ_OFFSET = 100;

    public int MIN_INTER_TAE = 0;
    public int MAX_INTER_TAE = 5000;

    private float FreqOffsetValue = 0.05f; //ppm
    private float MinEvm = 2.5f;
    private int Tae = 65;
    private int InterTae = 1300;

    public int getInterTae() {
        return InterTae;
    }

    public boolean setInterTae(int val) {
        if(val >= MIN_INTER_TAE && val <= MAX_INTER_TAE) {
            InterTae = val;
            return true;
        } else {
            Toast.makeText(MainActivity.getContext(), "Out of range(" + MIN_INTER_TAE + " ~ " + MAX_INTER_TAE + ")", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public int getTae() {
        return Tae;
    }

    public boolean setTae(int tae) {

        if(tae >= MIN_TAE && tae <= MAX_TAE) {
            Tae = tae;
            ViewHandler.getInstance().getContent().updateView();
            return true;
        } else {
            Toast.makeText(MainActivity.getContext(), "Out of range(" + MIN_TAE + " ~ " + MAX_TAE + ")", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public float getMinEvm() {
        return MinEvm;
    }

    public boolean setMinEvm(float minEvm) {

        if(minEvm >= MIN_EVM && minEvm <= MAX_EVM) {
            MinEvm = minEvm;
            return true;
        } else {
            Toast.makeText(MainActivity.getContext(), "Out of range(" + MIN_EVM + " ~ " + MAX_EVM + ")", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public float getFreqOffsetValue() {
        return FreqOffsetValue;
    }

    public boolean setFreqOffsetValue(float freqOffsetValue) {

        if(freqOffsetValue >= MIN_FREQ_OFFSET && freqOffsetValue <= MAX_FREQ_OFFSET) {
            FreqOffsetValue = freqOffsetValue;
            DataHandler.getInstance().getNrData().getInfoData().setPpm(FreqOffsetValue);
            return true;
        } else {
            Toast.makeText(MainActivity.getContext(), "Out of range(" + MIN_FREQ_OFFSET + " ~ " + MAX_FREQ_OFFSET + ")", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

}

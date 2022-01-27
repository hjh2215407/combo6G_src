package com.dabinsystems.pact_app.Data.ModAccuracy.LteFdd;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.LteFddLayoutBinding;

public class LteFddLimitData {

    public final Float MIN_FREQ_OFFSET = 0f;
    public final Float MAX_FREQ_OFFSET = 100f;

    public final Float MIN_MNIMUM_RS_EVM = 0.01f;
    public final Float MAX_MNIMUM_RS_EVM = 100f;

    public final Float MIN_TAE = 0.01f;
    public final Float MAX_TAE = 1000f;

    private Float FreqOffset = 0.05f;
    private Float MinimumRsEvm = 2.5f;
    private Float Tae = 90f;

    public Float getFreqOffset() {
        return FreqOffset;
    }

    public void setFreqOffset(Float freqOffset) {
        if (freqOffset >= MIN_FREQ_OFFSET && freqOffset <= MAX_FREQ_OFFSET) {
            FreqOffset = freqOffset;

            new Handler(Looper.getMainLooper()).post(() -> {
                ActivityMainBinding binding = MainActivity.getBinding();
                binding.lteFddLayout.tvPpmValue.setText(FreqOffset + "");
            });

        } else {

            new Handler(Looper.getMainLooper()).post(() -> {

                Toast.makeText(MainActivity.getContext(), "Out of range(" + MIN_FREQ_OFFSET + " ~ " + MAX_FREQ_OFFSET + ")", Toast.LENGTH_SHORT).show();

            });

        }


    }

    public Float getMinimumRsEvm() {
        return MinimumRsEvm;
    }

    public void setMinimumRsEvm(Float minimumRsEvm) {

        if (minimumRsEvm >= MIN_MNIMUM_RS_EVM && minimumRsEvm <= MAX_MNIMUM_RS_EVM) {

            MinimumRsEvm = minimumRsEvm;

        } else {

            new Handler(Looper.getMainLooper()).post(() -> {

                Toast.makeText(MainActivity.getContext(), "Out of range(" + MIN_MNIMUM_RS_EVM + " ~ " + MAX_MNIMUM_RS_EVM + ")", Toast.LENGTH_SHORT).show();

            });

        }

    }

    public Float getTae() {
        return Tae;
    }

    public void setTae(Float tae) {
        Tae = tae;

        if (tae >= MIN_TAE && tae <= MAX_TAE) {
            Tae = tae;

            new Handler(Looper.getMainLooper()).post(() -> {
                ActivityMainBinding binding = MainActivity.getBinding();
                binding.lteFddLayout.thrdValue.setText(Tae + " ns");
            });

        } else {

            new Handler(Looper.getMainLooper()).post(() -> {

                Toast.makeText(MainActivity.getContext(), "Out of range(" + MIN_TAE + " ~ " + MAX_TAE + ")", Toast.LENGTH_SHORT).show();

            });
        }
    }

    public void update() {

        new Handler(Looper.getMainLooper()).post(() -> {
            ActivityMainBinding binding = MainActivity.getBinding();
            binding.lteFddLayout.tvPpmValue.setText(FreqOffset + "");
        });

        new Handler(Looper.getMainLooper()).post(() -> {
            ActivityMainBinding binding = MainActivity.getBinding();
            binding.lteFddLayout.thrdValue.setText(Tae + " ns");
        });

    }
}

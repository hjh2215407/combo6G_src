package com.dabinsystems.pact_app.List.Item;

import android.util.Log;

public class CableLossInfoItem {

    private String Freq;
    private String CableLoss;

    public CableLossInfoItem(String freq, String cableLoss) {
        super();
        Freq = freq;
        CableLoss = cableLoss;
    }

    public Float getCableLoss() {
        try {
            Float val = Float.parseFloat(CableLoss);
            return val;
        } catch (NumberFormatException e) {
            return -1f;
        }
    }

    public void setCableLoss(float cableLoss) {

        CableLoss = cableLoss + "";
    }

    public Float getFreq() {

        try {

            Float value = Float.parseFloat(Freq);

            return value;

        } catch (NullPointerException e) {
            return -1f;
        }

    }

    public void setFreq(float freq) {
        Freq = freq + "";
    }
}

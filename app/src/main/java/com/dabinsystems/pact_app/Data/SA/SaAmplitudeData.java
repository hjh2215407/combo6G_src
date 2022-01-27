package com.dabinsystems.pact_app.Data.SA;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.github.mikephil.charting.utils.Utils;

import java.sql.Ref;

public class SaAmplitudeData {

    private int mAttenuator = 20; // 0 ~ 60 dB
    private Float RefLev = 0f;
    private Float ScaleDiv = 10f;
    private Float Offset = 0f;
    /*private boolean TestMode = false;*/

    public enum ATT_MODE {

        MANUAL(0, "Manual"),
        AUTO(1, "Auto");

        private final int Value;
        private final String Name;

        ATT_MODE(int val, String name) {

            Value = val;
            Name = name;

        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(Value);

        }

        public int getValue() {

            return Value;

        }

        public String getModeString() {

            return Name;

        }

        public byte getByte() {

            int val = Value & 0xff;
            return (byte) val;
        }

    }

    public enum AMP_MODE {

        MANUAL(0x00),
        AUTO(0x01);

        private final int value;

        AMP_MODE(int val) {

            value = val;
        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(value);

        }

        public int getValue() {

            return value;

        }

        public byte getByte() {

            int val = value & 0xff;
            return (byte) val;
        }

    }

    public enum PREAMP_MODE {

        OFF(0),
        ON(1);

        private final int value;

        PREAMP_MODE(int val) {

            value = val;
        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(value);

        }

        public int getValue() {

            return value;

        }

        public byte getByte() {

            int val = value & 0xff;
            return (byte) val;
        }

    }

    private AMP_MODE mAmpMode = AMP_MODE.MANUAL;
    private ATT_MODE mAttMode = ATT_MODE.AUTO;
    private PREAMP_MODE mPreampMode = PREAMP_MODE.OFF;

    public void setAttenuator(int atten) {

        if (atten < 0 || atten > 60) {

            new Handler(Looper.getMainLooper()).post(() -> {

                Toast.makeText(MainActivity.getActivity(), "Out of range(0~60)", Toast.LENGTH_SHORT).show();

            });

            return;
        }

        mAttenuator = atten;

    }

    public int getAttenuator() {
        return mAttenuator;
    }

    public byte getAttenToByte() {

        return (byte) mAttenuator;

    }

    public void setAmpMode(AMP_MODE mode) {

        mAmpMode = mode;

    }

    public void setAttMode(ATT_MODE mode) {

        mAttMode = mode;

    }

    public void setPreampMode(PREAMP_MODE mode) {

        mPreampMode = mode;

    }

    public AMP_MODE getAmpMode() {

        return mAmpMode;
    }

    public ATT_MODE getATTMode() {

        return mAttMode;

    }

    public PREAMP_MODE getPreampMode() {

        return mPreampMode;

    }

    public Float getRefLev() {
        return RefLev;
    }

    public void setRefLev(Float refLev) {

        if (refLev > 100f) {
            RefLev = 100f;
        } else if (refLev < -100) {
            RefLev = -100f;
        } else RefLev = refLev;


    }


    public Float getScaleDiv() {
        return ScaleDiv;
    }

    public void setScaleDiv(Float scaleDiv) {
        ScaleDiv = scaleDiv;
    }

    public Float getOffset() {
        return Offset;
    }

    public void setOffset(Float offset) {
        Offset = offset;
    }
/*
    public void setTestMode(boolean onOff) { TestMode = onOff; }

    public boolean getTestMode() { return TestMode; }*/
}

package com.dabinsystems.pact_app.Data.ModAccuracy.LteFdd;

import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;

public class LteFddAmpData {

    public final int ATT_MAX_RANGE = 60;
    public final int ATT_MIN_RANGE = 0;

    public final int OFFSET_MAX_RANGE = 100;
    public final int OFFSET_MIN_RANGE = -100;

    private int mAttenuator = 20; // 0 ~ 60 dB
    private float mOffset = 0f;

    public enum ATT_MODE {

        MANUAL(0x00, "Manual"),
        AUTO(0x01, "Auto");

        private final int Value;
        private final String Name;

        ATT_MODE(int val, String name) {

            Value = val;
            Name = name;

        }

        public String getHexString(){

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
            return (byte)val;
        }

    }

    public enum AMP_MODE {

        MANUAL(0x00),
        AUTO(0x01);

        private final int value;

        AMP_MODE(int val) {

            value = val;
        }

        public String getHexString(){

            return DataHandler.getInstance().intToHexStr(value);

        }

        public int getValue() {

            return value;

        }

        public byte getByte() {

            int val = value & 0xff;
            return (byte)val;
        }

    }

    public enum PREAMP_MODE {

        OFF(0x00),
        ON(0x01);

        private final int value;

        PREAMP_MODE(int val) {

            value = val;
        }

        public String getHexString(){

            return DataHandler.getInstance().intToHexStr(value);

        }

        public int getValue() {

            return value;

        }

        public byte getByte() {

            int val = value & 0xff;
            return (byte)val;
        }

    }

    private AMP_MODE mAmpMode = AMP_MODE.MANUAL;
    private ATT_MODE mAttMode = ATT_MODE.AUTO;
    private PREAMP_MODE mPreampMode = PREAMP_MODE.OFF;

    public void setAttenuator(int atten) {

        if (atten < ATT_MIN_RANGE || atten > ATT_MAX_RANGE) {
            Toast.makeText(MainActivity.getActivity(), "Out of range(0~60)", Toast.LENGTH_SHORT).show();
            return;
        }

        mAttenuator = atten;

    }

    public int getAttenuator() {
        return mAttenuator;
    }

    public byte getAttenToByte() {

        return (byte)mAttenuator;

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

    public float getOffset() {
        return mOffset;
    }

    public void setOffset(float offset) {
        if(offset < OFFSET_MIN_RANGE || offset > OFFSET_MAX_RANGE) return;
        this.mOffset = offset;
    }

}

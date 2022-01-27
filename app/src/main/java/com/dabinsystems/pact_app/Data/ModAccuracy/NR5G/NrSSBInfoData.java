package com.dabinsystems.pact_app.Data.ModAccuracy.NR5G;

import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;

public class NrSSBInfoData {

    public final int MIN_RB_OFFSET = 0;
    public final int MAX_RB_OFFSET = 506;
    //org
    /*public final int DEFAULT_RB_OFFSET = 24;*/
    //org

    //@@ [21.12.30] RB Offset Default value change 24 --> 249
    public final int DEFAULT_RB_OFFSET = 249;

    private int RbOffset = DEFAULT_RB_OFFSET;

    public final int MIN_K_SSB = 0;
    public final int MAX_K_SSB = 23;

    //org
    /*public final int DEFAULT_K_SSB = 0;
    */
    //org

    //@@ [21.12.30] k_SSB Default value change 0 --> 10
    public final int DEFAULT_K_SSB = 10;
    //@@
    private int Kssb = DEFAULT_K_SSB;


    public final int MIN_GSCN = 2;
    public final int MAX_GSCN = 26639;
    public final int DEFAULT_GSCN = 7950;
    private float Gscn = DEFAULT_GSCN;

    public enum CONFIG_MODE {

        MANUAL(0x00, "Manual"),
        AUTO(0x01, "Auto");

        private final int Value;
        private final String Name;

        CONFIG_MODE(int val, String name) {

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

    public enum SSB_LOCATION {

        OFFSET(0x00),
        GSCN(0x01);

        private final int value;

        SSB_LOCATION(int val) {

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

    private SSB_LOCATION mSsbMode = SSB_LOCATION.OFFSET;
    private CONFIG_MODE mConfigMode = CONFIG_MODE.MANUAL; // default value : auto

    public void setRbOffset(int offset) {

        if (offset < MIN_RB_OFFSET || offset > MAX_RB_OFFSET) {
            Toast.makeText(MainActivity.getActivity(), "Out of range(" + MIN_RB_OFFSET + " ~ " + MAX_RB_OFFSET + ")", Toast.LENGTH_SHORT).show();
            return;
        }

        RbOffset = offset;

    }

    public int getRbOffset() {
        return RbOffset;
    }


    public void setKssb(int kssb) {

        if (kssb < MIN_K_SSB || kssb > MAX_K_SSB) {
            Toast.makeText(MainActivity.getActivity(), "Out of range(" + MIN_K_SSB + " ~ " + MAX_K_SSB + ")", Toast.LENGTH_SHORT).show();
            return;
        }

        Kssb = kssb;

    }

    public int getKssb() {
        return Kssb;
    }

    public void setGscn(float gscn) {

        if (gscn < MIN_GSCN || gscn > MAX_GSCN) {
            Toast.makeText(MainActivity.getActivity(), "Out of range(" + MIN_GSCN + " ~ " + MAX_GSCN + ")", Toast.LENGTH_SHORT).show();
            return;
        }

        Gscn = gscn;

    }

    public float getGscn() {
        return Gscn;
    }

    public void setSsbLocationMode(SSB_LOCATION mode) {

        mSsbMode = mode;

    }

    public void setConfigMode(CONFIG_MODE mode) {

        mConfigMode = mode;

    }

    public SSB_LOCATION getSsbLocationMode() {

        return mSsbMode;
    }

    public CONFIG_MODE getConfigMode() {

        return mConfigMode;

    }

}

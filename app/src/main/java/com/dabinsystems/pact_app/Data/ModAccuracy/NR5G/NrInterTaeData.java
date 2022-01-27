package com.dabinsystems.pact_app.Data.ModAccuracy.NR5G;

import android.util.Log;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.tae.SelectCarrierView;

import java.util.concurrent.ConcurrentNavigableMap;

public class NrInterTaeData {

    public enum CARRIER_SETTING {

        OFF(0, "Off"),
        ON(1, "On");

        private final int Value;
        private final String Name;

        CARRIER_SETTING(int val, String name) {

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

    public enum PROFILE {

        SSB_ONLY(0x13, "SSB Only", -1),
        MHZ5(0, "5MHz", 5),
        MHZ10(1, "10MHz", 10),
        MHZ15(2, "15MHz", 15),
        MHZ20(3, "20MHz", 20),
        MHZ25(4, "25MHz", 25),
        MHZ30(5, "30MHz", 30),
        MHZ40(6, "40MHz", 40),
        MHZ50(7, "50MHz", 50),
        MHZ60(8, "60MHz", 60),
        MHZ70(9, "70MHz", 70),
        MHZ80(10, "80MHz", 80),
        MHZ90(11, "90MHz", 90),
        MHZ100(12, "100MHz", 100);

        private final int mCmd;
        private final int mValue;
        private String mValueStr;

        PROFILE(int cmd, String valueStr, int val) {

            mCmd = cmd;
            mValueStr = valueStr;
            mValue = val;
        }

        public int getValue() {

            return mValue;

        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(mCmd);

        }

        public int getCmd() {

            return mCmd;

        }

        public byte getByte() {

            return (byte) mCmd;
        }

        public String getString() {
            return mValueStr;
        }

    }

    public final int MIN_SELECTED_CARRIER_IDX = 0;
    public final int MAX_SELECTED_CARRIER_IDX = 3;

    public final int MIN_NUM_OF_CARRIER = 2;
    public final int MAX_NUM_OF_CARRIER = 4;

    public final float MIN_FREQ_OF_CARRIER = 70f;
    public final float MAX_FREQ_OF_CARRIER = 6000f;

    private int CurrentCarrierIdx = 0;
    private int NumOfCarrier = 3;
    private int SelectedCarrierIdx = 0;
    private float[] FreqOfCarrier = {3650.01f, 3549.99f, 3459.99f, 3650.01f};
    private PROFILE[] Profile = {PROFILE.MHZ100, PROFILE.MHZ100, PROFILE.MHZ80, PROFILE.MHZ80};
    private CARRIER_SETTING[] CarrierSetting = {CARRIER_SETTING.ON, CARRIER_SETTING.ON, CARRIER_SETTING.OFF, CARRIER_SETTING.OFF};

    public int getNumOfCarrier() {
        return NumOfCarrier;
    }

    public float getFreqOfCarrier(int idx) {
        return FreqOfCarrier[idx];
    }

    public float getFreqOfCarrier() {
        return FreqOfCarrier[SelectedCarrierIdx];
    }

    public boolean setFreqOfCarrier(int idx, float value) {

        if (value >= MIN_FREQ_OF_CARRIER && value <= MAX_FREQ_OF_CARRIER) {
            FreqOfCarrier[idx] = value;
            return true;
        } else {
            Toast.makeText(MainActivity.getContext(), "Out of range(" + MIN_FREQ_OF_CARRIER + " ~ " + MAX_FREQ_OF_CARRIER + ")", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public PROFILE getProfile(int idx) {

        return Profile[idx];

    }

    public void setProfile(int idx, PROFILE profile) {

        Profile[idx] = profile;

    }

    public PROFILE getProfile() {

        return Profile[SelectedCarrierIdx];

    }

    public void setProfile(PROFILE profile) {

        Profile[SelectedCarrierIdx] = profile;

    }

    public void setSelectedCarrierIdx(int idx) {
        if (idx < MIN_SELECTED_CARRIER_IDX || idx > MAX_SELECTED_CARRIER_IDX) {
            InitActivity.logMsg("setSelectedCarrierIdx", "out of range ... " + idx);
            return;

        }

        SelectedCarrierIdx = idx;
        InitActivity.logMsg("setSelectedCarrierIdx", SelectedCarrierIdx + "");
    }

    public int getSelectedCarrierIdx() {
        return SelectedCarrierIdx;
    }

    public void setCarrierSetting(int idx, CARRIER_SETTING setting) {

        if (idx < 0 || idx >= CarrierSetting.length) return;
        CarrierSetting[idx] = setting;

    }

    public void setCarrierSetting(CARRIER_SETTING setting) {

        CarrierSetting[SelectedCarrierIdx] = setting;

    }

    public CARRIER_SETTING getCarrierSetting(int idx) {
        if (idx < 0 || idx >= CarrierSetting.length) return null;
        return CarrierSetting[idx];
    }

    public CARRIER_SETTING getCarrierSetting() {
        return CarrierSetting[SelectedCarrierIdx];
    }

    public void initCurrnetCarrierIdx() {
        CurrentCarrierIdx = getStartIdxCarrierOn();
    }

    public int moveIdx() {

        CurrentCarrierIdx = CurrentCarrierIdx + 1;

        while(CurrentCarrierIdx < MAX_NUM_OF_CARRIER) {

            if(getCarrierSetting(CurrentCarrierIdx) == CARRIER_SETTING.ON)
                break;

            CurrentCarrierIdx++;

        }

        return CurrentCarrierIdx;

    }

    public void setCurrentCarrier(int idx) {

        if(idx < 0 || idx >= MAX_NUM_OF_CARRIER) return;
        CurrentCarrierIdx = idx;

    }

    public int getCurrentCarrierIdx() {
        return CurrentCarrierIdx;
    }

    public int getStartIdxCarrierOn() {

        int idx = 0;
        int curIdx = 0;

        while(idx < MAX_NUM_OF_CARRIER) {

            if(getCarrierSetting(idx) == CARRIER_SETTING.ON) {
                curIdx = idx;
                break;
            }
            idx++;

        }

        return curIdx;

    }

    public int getEndIdxCarrierOn() {

        int idx = 0;
        int curIdx = 0;

        while(idx < MAX_NUM_OF_CARRIER) {

            if(getCarrierSetting(idx) == CARRIER_SETTING.ON)
                curIdx = idx;

            idx++;
        }

        return curIdx;

    }

}

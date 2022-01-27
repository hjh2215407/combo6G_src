package com.dabinsystems.pact_app.Data;

import com.dabinsystems.pact_app.Handler.DataHandler;

public class GpsProfile {
    // PROFILE
    public enum PROFILE {

        MHZ1D4(0, "1.4 MHz", 1.4),
        MHZ3(1, "3 MHz", 3),
        MHZ5(2, "5 MHz", 5),
        MHZ10(3, "10 MHz", 10),
        MHZ15(4, "15 MHz", 15),
        MHZ20(5, "20 MHz", 20);

        private final int mCmd;
        private final double mValue;
        private String mValueStr;

        PROFILE(int cmd, String valueStr, double val) {
            mCmd = cmd;
            mValueStr = valueStr;
            mValue = val;
        }

        public double getValue() {
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

    // 5G Profile
    public enum PROFILE_5G {

        MHZ5(0, "5 MHz", 5),
        MHZ10(1, "10 MHz", 10),
        MHZ15(2, "15 MHz", 15),
        MHZ20(3, "20 MHz", 20),
        MHZ25(4, "25 MHz", 25),
        MHZ30(5, "30 MHz", 30),
        MHZ40(6, "40 MHz", 40),
        MHZ50(7, "50 MHz", 50),
        MHZ60(8, "60 MHz", 60),
        MHZ70(9, "70 MHz", 70),
        MHZ80(10, "80 MHz", 80),
        MHZ90(11, "90 MHz", 90),
        MHZ100(12, "100 MHz", 100);

        private final int mCmd;
        private final double mValue;
        private String mValueStr;

        PROFILE_5G(int cmd, String valueStr, double val) {
            mCmd = cmd;
            mValueStr = valueStr;
            mValue = val;
        }

        public double getValue() { return mValue; }

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

    public PROFILE getProfile() {
        return mSelectedProfile;
    }

    public void setProfile(PROFILE profile) {
        mSelectedProfile = profile;
    }

    public void setProfile(int val) {

        if (PROFILE.MHZ1D4.getValue() == val) {
            mSelectedProfile = PROFILE.MHZ1D4;
        }
        else if (PROFILE.MHZ3.getValue() == val) {
            mSelectedProfile = PROFILE.MHZ3;
        }
        else if (PROFILE.MHZ5.getValue() == val) {
            mSelectedProfile = PROFILE.MHZ5;
        }
        else if (PROFILE.MHZ10.getValue() == val) {
            mSelectedProfile = PROFILE.MHZ10;
        }
        else if (PROFILE.MHZ15.getValue() == val) {
            mSelectedProfile = PROFILE.MHZ15;
        }
        else if (PROFILE.MHZ20.getValue() == val) {
            mSelectedProfile = PROFILE.MHZ20;
        }
    }

    private PROFILE mSelectedProfile = PROFILE.MHZ10; // default ?

    // 5G Profile

    public PROFILE_5G getProfile5G() { return mSelectedProfile5G; }

    public void setProfile5G(PROFILE_5G profile) { mSelectedProfile5G = profile; }

    public void setProfile5G(int val) {
        if (PROFILE_5G.MHZ5.getValue() == val) {
            mSelectedProfile5G = PROFILE_5G.MHZ5;
        }
        else if (PROFILE_5G.MHZ10.getValue() == val) {
            mSelectedProfile5G = PROFILE_5G.MHZ10;
        }
        else if (PROFILE_5G.MHZ15.getValue() == val) {
            mSelectedProfile5G = PROFILE_5G.MHZ15;
        }
        else if (PROFILE_5G.MHZ20.getValue() == val) {
            mSelectedProfile5G = PROFILE_5G.MHZ20;
        }
        else if (PROFILE_5G.MHZ25.getValue() == val) {
            mSelectedProfile5G = PROFILE_5G.MHZ25;
        }
        else if (PROFILE_5G.MHZ30.getValue() == val) {
            mSelectedProfile5G = PROFILE_5G.MHZ30;
        }
        else if (PROFILE_5G.MHZ40.getValue() == val) {
            mSelectedProfile5G = PROFILE_5G.MHZ40;
        }
        else if (PROFILE_5G.MHZ50.getValue() == val) {
            mSelectedProfile5G = PROFILE_5G.MHZ50;
        }
        else if (PROFILE_5G.MHZ60.getValue() == val) {
            mSelectedProfile5G = PROFILE_5G.MHZ60;
        }
        else if (PROFILE_5G.MHZ70.getValue() == val) {
            mSelectedProfile5G = PROFILE_5G.MHZ70;
        }
        else if (PROFILE_5G.MHZ80.getValue() == val) {
            mSelectedProfile5G = PROFILE_5G.MHZ80;
        }
        else if (PROFILE_5G.MHZ90.getValue() == val) {
            mSelectedProfile5G = PROFILE_5G.MHZ90;
        }
        else if (PROFILE_5G.MHZ100.getValue() == val) {
            mSelectedProfile5G = PROFILE_5G.MHZ100;
        }
    }

    private PROFILE_5G mSelectedProfile5G = PROFILE_5G.MHZ10;

}

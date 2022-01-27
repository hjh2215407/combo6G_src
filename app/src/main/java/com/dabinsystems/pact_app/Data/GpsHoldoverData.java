package com.dabinsystems.pact_app.Data;

import com.dabinsystems.pact_app.Handler.DataHandler;

public class GpsHoldoverData {
    /**
     * Holdover Option(0: GPS / 1: LTE)
     * Time Period(1 ~ 3600 / Unit : Sec / Default 5 min(300sec))
     * Number of LTE
     * Frequency 1,2...N (70 MHz ~ 6GHz / Unit : Hz)
     * Profile 1,2...N (0 ~ 5 / 0: 1.4MHz / 1: 3MHz / 2: 5MHz / 3: 10MHz / 4: 15MHz / 5: 20MHz)
     */
    // this will be used on system tab (below clock source button)
    // OPTION
    public enum OPTION {
        GPS(0, "GPS"),
        LTE(1, "Tracking");

        private final int mCmd;
        private String mStr;

        OPTION(int cmd, String str) {
            mCmd = cmd;
            mStr = str;
        }

        public int getCmd() { return mCmd; }

        public String getString() { return mStr; }

        public String getHexString() { return DataHandler.getInstance().intToHexStr(mCmd); }

        public byte getByte() { return (byte) mCmd; }
    }

    public GpsHoldoverData() {
        LteInfoData data = DataHandler.getInstance().getLteInfoData();

        NumberOfLTE = data.getNumOfLte();

        TechType = new int[NumberOfLTE];
        Frequency = new double[NumberOfLTE];
        Profile = new GpsProfile[NumberOfLTE];

        for (int i = 0; i < NumberOfLTE; i++) {

            Profile[i] = new GpsProfile();
        }

    }

    public OPTION getOption() {
        return mSelectedOption;
    }

    public void setOption(OPTION option) {
        mSelectedOption = option;
    }

    private OPTION mSelectedOption = OPTION.GPS; // default : LTE

    // Time Period
    private int timePeriod = 300; // unit sec default 300sec (5min)

    public void setTimePeriod(int val) { timePeriod = val; }

    public int getTimePeriod() { return timePeriod; }

    // Num of LTE Signal
    private int NumberOfLTE; // 나중에 LTE_Info.ini 에서 가져와야 함

    // Tech Type List
    public int[] TechType;

    public int[] getTechType() { return TechType; }

    public int  getTechType(int i) { return TechType[i]; }

    public void setTechType(int[] techType) { TechType = techType; }


    // LTE Signal Frequency List
    public double[] Frequency;

    public double[] getFrequency() {
        return Frequency;
    }

    public double getFrequency(int i) { return Frequency[i]; }

    public void setFrequency(double[] freq) {
        Frequency = freq;
    }


    // Frequency Profile list
    public GpsProfile[] Profile;

    public GpsProfile[] getProfile() {
        return Profile;
    }

    public GpsProfile getProfile(int i) { return Profile[i]; }

    public void setGpsProfile(int[] profile) {
        /*
        Profile[0] = GpsProfile.PROFILE.MHZ1D4;
        Profile[1] = GpsProfile.PROFILE.MHZ3;
        */

        /*for (int i = 0; i < NumberOfLTE; i++) {
            Profile[i].setProfile(profile[i]);
        }*/

        // tech added
        for (int i = 0; i < NumberOfLTE; i++) {
            if (TechType[i] == 0) {
                Profile[i].setProfile(profile[i]);
            }
            else {
                Profile[i].setProfile5G(profile[i]);
            }
        }

    }

}

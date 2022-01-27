package com.dabinsystems.pact_app.Data.ModAccuracy.LteFdd;

import android.annotation.SuppressLint;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;

public class LteFddProfileData {

    public enum PROFILE {

        MHZ1D4(0, "1.4 MHz", 1.4f),
        MHZ3(1, "3 MHz", 3f),
        MHZ5(2, "5 MHz", 5f),
        MHZ10(3, "10 MHz", 10f),
        MHZ15(4, "15 MHz", 15f),
        MHZ20(5, "20 MHz", 20f);

        private final int mCmd;
        private final float mValue;
        private String mValueStr;

        PROFILE(int cmd, String valueStr, float val) {

            mCmd = cmd;
            mValueStr = valueStr;
            mValue = val;
        }

        public float getValue() {

            return mValue;

        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(mCmd);

        }

        public int getCmd() {

            return mCmd;

        }

        public byte getByte() {

            return (byte)mCmd;
        }

        public String getString() {
            return mValueStr;
        }

    }

    private PROFILE mSelectedProfile = PROFILE.MHZ20;

    @SuppressLint("SetTextI18n")
    public void setProfile(PROFILE profile) {

        mSelectedProfile = profile;
        MainActivity.getActivity().runOnUiThread(()->{

            MainActivity.getBinding().lteFddLayout.tvChBandWidthValue.setText(mSelectedProfile.getValue() + " MHz");

        });

    }

    public PROFILE getProfile() {

        return mSelectedProfile;
    }

}

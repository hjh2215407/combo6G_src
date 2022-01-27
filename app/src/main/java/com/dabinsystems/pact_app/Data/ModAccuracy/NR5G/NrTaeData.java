package com.dabinsystems.pact_app.Data.ModAccuracy.NR5G;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;

public class NrTaeData {

    int MIN_MEAS_COUNT = 0;
    int MAX_MEAS_COUNT = 10;

    boolean isRunning = false;

    public enum SCREENSHOT_MODE {

        OFF(0, "Off"),
        ON(1, "On");

        private final int Value;
        private final String Name;

        SCREENSHOT_MODE(int val, String name) {

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

    public enum TAE_MEAS_MODE {

        OFF(0, "Off"),
        ON(1, "On");

        private final int Value;
        private final String Name;

        TAE_MEAS_MODE(int val, String name) {

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

    public enum TAE_TYPE {

        INTRA(0, "Intra-TAE"),
        INTER(1, "Inter-TAE");

        private final int Value;
        private final String Name;

        TAE_TYPE(int val, String name) {

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

    public enum NUM_OF_TX_ANT {

        TX_2(0, 2),
        TX_4(1, 4),
        TX_8(2, 8),
        TX_32(3, 32);

        private final int Value;
        private final int Cmd;

        NUM_OF_TX_ANT(int cmd, int val) {

            Value = val;
            Cmd = cmd;

        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(Value);

        }

        public int getCmd() {
            return Cmd;
        }

        public int getValue() {

            return Value;

        }

        public byte getByte() {

            int val = Value & 0xff;
            return (byte) val;
        }

    }

    private TAE_MEAS_MODE mConfigMode = TAE_MEAS_MODE.OFF; // default value : OFF
    private TAE_TYPE mTaeType = TAE_TYPE.INTRA;
    private SCREENSHOT_MODE mScreenshotMode = SCREENSHOT_MODE.OFF;
    private NUM_OF_TX_ANT mNumOfTxAnt = NUM_OF_TX_ANT.TX_2;
    private int CurrentMeasCount = 0;
    private int CurrentPortIdx = 1;
    private int NumOfCarrier = 2;
    private int MeasCount = 10;

    public int getCurrentMeasCount() {
        return CurrentMeasCount;
    }

    public void setCurrentMeasCount(int currentMeasCount) {
        CurrentMeasCount = currentMeasCount;
    }

    public void setTeaMeasMode(TAE_MEAS_MODE mode) {

        mConfigMode = mode;

        ViewHandler.getInstance().getContent().updateView();

        if (mode == TAE_MEAS_MODE.ON) {

            FunctionHandler.getInstance().getDataConnector().removeDataTimeoutHandler();

        } else {

            FunctionHandler.getInstance().getDataConnector().startDataTimeoutHandler();
        }

        setCurrentMeasCount(0);
        setCurrentPortIdx(1);

    }

    public void setScreenshotMode(SCREENSHOT_MODE mode) {

        mScreenshotMode = mode;

    }

    public SCREENSHOT_MODE getScreenshotMode() {
        return mScreenshotMode;
    }

    public TAE_MEAS_MODE getTeaMeasMode() {

        return mConfigMode;

    }

    public NUM_OF_TX_ANT getNumOfTxAnt() {
        return mNumOfTxAnt;
    }

    public void setNumOfTxAnt(NUM_OF_TX_ANT tx) {
        this.mNumOfTxAnt = tx;
    }

    public int getCurrentPortIdx() {
        return CurrentPortIdx;
    }

    public void setCurrentPortIdx(int currentPortIdx) {
        CurrentPortIdx = currentPortIdx;
    }

    public TAE_TYPE getTaeType() {
        return mTaeType;
    }

    public void setTaeType(TAE_TYPE taeType) {
        mTaeType = taeType;

        new Handler(Looper.getMainLooper()).post(() -> {

            DataHandler.getInstance().getNrData().getInfoData().setInformationText(
                    MainActivity.getBinding().demodulationLayout.tvTaeType, mTaeType.Name
            );

        });

    }

    public int getNumOfCarrier() {
        return NumOfCarrier;
    }

    public void setNumOfCarrier(int numOfCarrier) {
        NumOfCarrier = numOfCarrier;
    }

    public int getMeasCount() {
        return MeasCount;
    }

    public boolean setMeasCount(int measCount) {
        if (measCount >= MIN_MEAS_COUNT && measCount <= MAX_MEAS_COUNT) {

            MeasCount = measCount;
            return true;

        } else {
            Toast.makeText(MainActivity.getContext(), "Out of range(" + MIN_MEAS_COUNT + " ~ " + MAX_MEAS_COUNT + ")", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRun) {
        isRunning = isRun;
    }


}

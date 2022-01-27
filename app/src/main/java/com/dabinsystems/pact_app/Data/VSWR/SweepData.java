package com.dabinsystems.pact_app.Data.VSWR;

import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;

public class SweepData {

    public enum DATA_POINT {

        P129(129, 0x00),
        P257(257, 0x01),
        P513(513, 0x02),
        P1025(1025, 0x03),
        P2049(2049, 0x04),
        P2002(2002, 0x05),
        P1001(1001, 0x06);

        int dataPoint;
        int value;

        DATA_POINT(int pointNum, int val) {

            dataPoint = pointNum;
            value = val;

        }

        public int getDataPoint() {

            return dataPoint;
        }

        public int getValue() {

            return value;

        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(value);

        }

        public byte getByte() {

            byte b = (byte) value;
            return b;
        }

    }

    private DATA_POINT DataPoint = DATA_POINT.P129;
    private Boolean isRun = true; // 1 : Run / 0 : Hold
    private Boolean isContinuous = true; // 1: Continuous / 0 : Single\

    public void setDataPoint(DATA_POINT point) {

        DataPoint = point;
        boolean isPass = VswrDataHandler.getInstance().getDtfConfigData().checkDistance(
                VswrDataHandler.getInstance().getDtfConfigData().getDistance()
        );

        if(!isPass)
            VswrDataHandler.getInstance().getDtfConfigData().updateDistance();

        FunctionHandler.getInstance().getMainLineChart().setSkipFirstData(false);

    }

    public DATA_POINT getDataPoint() {

        return DataPoint;

    }

    public void setRun(Boolean on) {

        isRun = on;

    }

    public Boolean isRun() {

        return isRun;

    }

    public void setContinuous(Boolean on) {

        isContinuous = on;

    }

    public Boolean isContinuous() {

        return isContinuous;

    }


}

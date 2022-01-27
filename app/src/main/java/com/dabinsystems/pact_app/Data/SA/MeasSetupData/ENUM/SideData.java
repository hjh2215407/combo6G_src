package com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM;

public class SideData {

    public enum SIDE_OPTION {

        NEG(0),
        BOTH(1),
        POS(2);

        int value;

        SIDE_OPTION(int val) {
            value = val;
        }

        public int getValue() {
            return value;
        }
    }

}

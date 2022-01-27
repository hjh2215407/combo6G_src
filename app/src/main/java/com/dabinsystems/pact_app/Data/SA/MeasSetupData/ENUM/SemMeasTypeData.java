package com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM;

public class SemMeasTypeData {

    public enum SEM_MEASURE_TYPE {

        TOTAL_POWER(0, "Total Power"),
        PSD(1, "PSD"),
        PEAK(2, "Peak");

        int value;
        String name;

        SEM_MEASURE_TYPE(int val, String name) {

            value = val;
            this.name = name;

        }

        public int getValue() {
            return value;
        }
        public String getName() {return name;}

    }


}

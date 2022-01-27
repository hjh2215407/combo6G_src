package com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM;

public class FailSourceData {

    public enum FAIL_SOURCE {

        NONE(0, "None"),
        ABSOLUTE(1, "Absolute"),
        RELATIVE(2, "Relative"),
        ALL(3, "All"),
        SEM_ABS_OR_REL(3, "Abs OR Rel"),
        SEM_ALL(4, "All");

        int value;
        String name;

        FAIL_SOURCE(int val, String name) {
            value = val;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {

            return name;
        }

    }

}

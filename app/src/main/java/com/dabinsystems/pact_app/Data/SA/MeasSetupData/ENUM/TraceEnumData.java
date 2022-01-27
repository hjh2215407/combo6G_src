package com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM;

import com.dabinsystems.pact_app.Handler.DataHandler;

public class TraceEnumData {

    public enum TRACE_MODE {

        CLEAR_WRITE(0, "Clear Write", "W"),
        AVERAGE(1, "Average", "A"),
        MAX_HOLD(2, "Max Hold", "M"),
        MIN_HOLD(3, "Min Hold", "m");

        private final int value;
        private String str;
        private String sign;

        TRACE_MODE(int value, String fullName, String shortName) {

            this.value = value;
            str = fullName;
            sign = shortName;
        }

        public int getValue() {
            return value;
        }

        public String getString() {

            return str;

        }

        public String getSignString() {

            return sign;

        }

        public byte getByte() {

            int val = value & 0xff;
            return (byte)val;

        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(value);

        }

    }

    public enum TRACE_TYPE {

        UPDATE(0, "Update"),
        VIEW(1, "View"),
        BLANK(2, "Blank");

        private final int value;
        private String str;

        TRACE_TYPE(int value, String name) {
            this.value = value;
            str = name;
        }

        public int getValue() {
            return value;
        }

        public String getString() {

            return str;

        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(value);
        }

        public byte getByte() {

            int val = value & 0xff;

            return (byte)val;

        }

    }

    public enum DETECTOR {

        NORMAL(0, "Normal", "N", 2002),
        PEAK(1, "Peak", "P", 1001),
        NEGATIVE(2, "Negative", "n", 1001),
        SAMPLE(3, "Sample", "S", 1001),
        RMS(4, "RMS", "R", 1001);

        private final int value;
        private final int points;
        private String str;
        private String sign;

        DETECTOR(int value, String name, String sName, int point) {
            this.value = value;
            str = name;
            sign = sName;
            points = point;
        }

        public int getValue() {
            return value;
        }

        public int getDataPoints() {

            return points;

        }

        public String getString() {

            return str;

        }

        public String getSignString() {

            return sign;

        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(value);

        }

        public byte getByte() {

            int val = value & 0xff;

            return (byte)val;

        }

    }

}

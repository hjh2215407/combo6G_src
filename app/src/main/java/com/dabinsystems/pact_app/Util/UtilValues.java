package com.dabinsystems.pact_app.Util;

import com.github.mikephil.charting.utils.Utils;

import java.text.NumberFormat;

public class UtilValues {

    public String toNanoSecUnitString(int val) {
        String unitVal = "";

        NumberFormat f = NumberFormat.getInstance();
        f.setGroupingUsed(false);

        if (val >= 1000000) {
            unitVal = f.format((float) val / 1000000) + " ms";
        } else if (val >= 1000) {
            unitVal = f.format((float) val / 1000) + " μs";
        } else {
            unitVal = val + " ns";
        }

        return unitVal;
    }

    public String toSecUnitString(int val) {
        String unitVal = "";

        if (val >= 1000000) {

            if ((float) val / 1000000f % (int) ((float) val / 1000000f) == 0)
                unitVal = Utils.formatNumber((float) val / 1000000, 0, false) + " s";
            else
                unitVal = Utils.formatNumber((float) val / 1000000, 3, false) + " s";

        } else if (val >= 1000) {

            if ((float) val / 1000f % (int) ((float) val / 1000f) == 0)
                unitVal = Utils.formatNumber((float) val / 1000, 0, false) + " ms";
            else
                unitVal = Utils.formatNumber((float) val / 1000, 3, false) + " ms";

        } else {
            unitVal = Utils.formatNumber(val, 0, false) + " μs";
        }

        return unitVal;
    }

    public String toSecUnitString(double val) {
        return toSecUnitString((float)val);
    }

    public String toSecUnitString(float val) {
        String unitVal = "";

        if (val >= 1000000) {
            if (val / 1000000f % (int) (val / 1000000f) == 0)
                unitVal = Utils.formatNumber(val / 1000000, 0, false) + " s";
            else
                unitVal = Utils.formatNumber(val / 1000000, 3, false) + " s";
        } else if (val >= 1000) {
            if (val / 1000f % (int) (val / 1000f) == 0)
                unitVal = Utils.formatNumber(val / 1000, 0, false) + " ms";
            else
                unitVal = Utils.formatNumber(val / 1000, 3, false) + " ms";
        } else {
            unitVal = Utils.formatNumber(val, 0, false) + " μs";
        }

        return unitVal;
    }

}

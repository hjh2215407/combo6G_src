package com.dabinsystems.pact_app.Function.Chart;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.Collection;

public class BeamIndexAxisValueFormatter extends IndexAxisValueFormatter {

    private String[] mValues = new String[] {};
    private int mValueCount = 0;

    public BeamIndexAxisValueFormatter() {
        super();
    }

    public BeamIndexAxisValueFormatter(String[] values) {
        super(values);
    }

    public BeamIndexAxisValueFormatter(Collection<String> values) {
        if (values != null)
            setValues(values.toArray(new String[values.size()]));
    }

    @Override
    public String[] getValues() {
        return mValues;
    }

    @Override
    public void setValues(String[] values) {
        if (values == null)
            values = new String[] {};

        this.mValues = values;
        this.mValueCount = values.length;
    }

    @Override
    public String getFormattedValue(float value) {

        int index = Math.round(value);

        if (index < 0 || index >= mValueCount)
            return "";

        return mValues[index];

    }
}

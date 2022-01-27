package com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM;

import android.util.Log;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.BWData;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.github.mikephil.charting.utils.Utils;

public class SemRefChannelData {

    private Double Span = 100d;
    private Float IntegBw = 98.28f;
    private BWData BwData;

    public SemRefChannelData() {

        BwData = new BWData();
        BwData.setRBW(BWData.BAND_WIDTH.KHZ100);
        BwData.setVBW(BWData.BAND_WIDTH.KHZ30);

    }

    public Double getSpan() {
        return Span;
    }

    public void setSpan(Double span) {
        InitActivity.logMsg("setSpan", span + "");
        Span = span;
//        FunctionHandler.getInstance().getMainLineChart().updateSemBox();
    }

    public Float getIntegBw() {
        return IntegBw;
    }

    public Long getIntegBWForHz() {

        Long hz = (long) ((double) IntegBw * 1000 * 1000);
        return hz;
    }

    //MHz
    public void setIntegBw(Float integBw) {
        IntegBw = integBw;

        String integBwToStr = Utils.formatNumber(IntegBw, 2, false) + " MHz";
        if (IntegBw < 0.1f)
            integBwToStr = Utils.formatNumber(IntegBw * 1000f, 2, false) + " kHz";

        String finalIntegBwToStr = integBwToStr;

        /*MainActivity.getActivity().runOnUiThread(() -> {

            MainActivity.getBinding().lineChartLayout.semLayout.tvSemIntegBwFreq1.setText(finalIntegBwToStr);
            MainActivity.getBinding().lineChartLayout.semLayout.tvSemIntegBwFreq2.setText(finalIntegBwToStr);
            MainActivity.getBinding().lineChartLayout.semLayout.tvSemIntegBwFreq3.setText(finalIntegBwToStr);
            MainActivity.getBinding().lineChartLayout.semLayout.tvSemIntegBwFreq4.setText(finalIntegBwToStr);

        });*/

    }

    public void updateIntegValue() {

        String integBwToStr = Utils.formatNumber(IntegBw, 2, false) + " MHz";

        if (IntegBw < 0.1f)
            integBwToStr = Utils.formatNumber(IntegBw * 1000f, 2, false) + " kHz";

        String finalIntegBwToStr = integBwToStr;

        /*MainActivity.getActivity().runOnUiThread(() -> {

            MainActivity.getBinding().lineChartLayout.semLayout.tvSemIntegBwFreq1.setText(finalIntegBwToStr);
            MainActivity.getBinding().lineChartLayout.semLayout.tvSemIntegBwFreq2.setText(finalIntegBwToStr);
            MainActivity.getBinding().lineChartLayout.semLayout.tvSemIntegBwFreq3.setText(finalIntegBwToStr);
            MainActivity.getBinding().lineChartLayout.semLayout.tvSemIntegBwFreq4.setText(finalIntegBwToStr);

        });*/

    }

    public BWData getBwData() {

        return BwData;

    }

}

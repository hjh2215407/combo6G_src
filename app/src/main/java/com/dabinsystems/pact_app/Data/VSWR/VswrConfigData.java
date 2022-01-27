package com.dabinsystems.pact_app.Data.VSWR;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;

public class VswrConfigData {

    /*
     * Frequency
     **/

    private VswrFrequencyData FrequencyData;

    /*
     * Distance
     * */

    private Integer Distance = 10;

    /*
     * Amplitude
     * */

    private VswrAmplitudeData AmplitudeData;

    /*
     * Limit
     * */

    private com.dabinsystems.pact_app.Data.VSWR.LimitData LimitData;

    /*
     * Sweep
     * */

    private com.dabinsystems.pact_app.Data.VSWR.SweepData SweepData;

    private WindowingData WindowingData;

    public VswrConfigData() {

    }

    public VswrFrequencyData getFrequencyData() {

        if (FrequencyData == null) FrequencyData = new VswrFrequencyData();
        return FrequencyData;

    }

    public VswrAmplitudeData getAmplitudeData() {
        if (AmplitudeData == null) AmplitudeData = new VswrAmplitudeData();
        return AmplitudeData;
    }

    public int getMinDistance() {

        double startFreq = VswrDataHandler.getInstance().getConfigData().getFrequencyData().getStartFreq();
        double stopFreq = VswrDataHandler.getInstance().getConfigData().getFrequencyData().getStopFreq();
        float velocity = FunctionHandler.getInstance().getCableInfoFunc().getVelocity();
        double freqDiff = stopFreq - startFreq;

        // TODO ?
        if (freqDiff < 1)
            freqDiff = 1;

        int distance = (int) Math.ceil(((1.5 * Math.pow(10, 2) * velocity) / freqDiff) * 2);

        return distance;
    }

    public int getMaxDistance() {

        double startFreq = VswrDataHandler.getInstance().getConfigData().getFrequencyData().getStartFreq();
        double stopFreq = VswrDataHandler.getInstance().getConfigData().getFrequencyData().getStopFreq();
        float velocity = FunctionHandler.getInstance().getCableInfoFunc().getVelocity();
        double freqDiff = stopFreq - startFreq;
        double dataPoints = (double) (VswrDataHandler.getInstance().getConfigData().getSweepData().getDataPoint().getDataPoint() - 2) / 2;

        int distance = (int)((((1.5 * Math.pow(10, 2) * velocity) / freqDiff) * dataPoints));

        // org
        //if (distance > 1000) distance = 1000;
        // org

        if (distance > 500) {
            Log.e("DTF", "Max Distance value : 500(current : " + distance + ")");
            distance = 500;
        }

        Log.e("DTF", "Distance : " + distance);

        return distance;

    }

    public Boolean checkDistance(double dist) {

        int mindistance = getMinDistance();
        int maxDistance = getMaxDistance();
        Log.e("DTF", "Min Distance : " + mindistance);
        Log.e("DTF", "Max Distance : " + maxDistance);
        if (dist > maxDistance || dist < mindistance) {
            return false;
        } else {
            return true;

        }


    }

    public Boolean checkDistance() {

        int minDistance = getMinDistance();
        int maxDistance = getMaxDistance();

        if (Distance > maxDistance || Distance < minDistance) {
            return false;
        } else {
            return true;
        }

    }

    public void updateDistance() {

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        if (mode == MeasureMode.MEASURE_MODE.DTF) {

            int curDistance = getDistance();
            int minDistance = getMinDistance();
            int maxDistance = getMaxDistance();

            InitActivity.logMsg("UpdateDistance", curDistance + " " + maxDistance);

            if (curDistance > maxDistance || curDistance < minDistance) {
                setDistance(maxDistance);
//                FunctionHandler.getInstance().getMainLineChart().setStopFreq(getMaxDistance());
                FunctionHandler.getInstance().getMainLineChart().invalidate();

                new Handler(Looper.getMainLooper()).post(() -> {

                    Toast.makeText(MainActivity.getContext(), "The distance has been reset to " + (int) maxDistance + "m", Toast.LENGTH_SHORT).show();

                });
            }

        }

    }

    public Integer getDistance() {
        return Distance;
    }

    public void setDistance(Integer distance) {
        Distance = distance;
    }

    public com.dabinsystems.pact_app.Data.VSWR.LimitData getLimitData() {

        if (LimitData == null) LimitData = new LimitData();
        return LimitData;

    }

    public com.dabinsystems.pact_app.Data.VSWR.SweepData getSweepData() {

        if (SweepData == null) SweepData = new SweepData();
        return SweepData;

    }

    public WindowingData getWindowingData() {

        if (WindowingData == null) WindowingData = new WindowingData();
        return WindowingData;

    }

    public void loadData() {

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        FunctionHandler.getInstance().getDataConnector().calMqttTimeout();

        /*set frequency OR Distance */

        if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.CL) {

//            FunctionHandler.getInstance().getMainLineChart().setStartFreq(getFrequencyData().getStartFreq());
//            FunctionHandler.getInstance().getMainLineChart().setStopFreq(getFrequencyData().getStopFreq());

            new Handler(Looper.getMainLooper()).post(()->{
                MainActivity.getBinding().lineChartLayout.tvChartInfo.setText(
                        ""
                );
            });

        } else if (mode == MeasureMode.MEASURE_MODE.DTF) {

//            FunctionHandler.getInstance().getMainLineChart().setStartFreq(0);
//            FunctionHandler.getInstance().getMainLineChart().setStopFreq(getDistance());

            try {
                FunctionHandler.getInstance().getCableInfoFunc().setLoss(
                        FunctionHandler.getInstance().getCableInfoFunc().getCableInfoAdapter().getSelectedCable().getLoss()
                );
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            FunctionHandler.getInstance().getCableInfoFunc().updateCableText();
        }

        /*set visible dataset*/
        FunctionHandler.getInstance().getMainLineChart().setVisible(0, true);
        FunctionHandler.getInstance().getMainLineChart().setVisible(1, false);
        FunctionHandler.getInstance().getMainLineChart().setVisible(2, false);
        FunctionHandler.getInstance().getMainLineChart().setVisible(3, false);

        /*remove markers % limitline(내부적으로는 bluezone과 같은 기능임) */
        FunctionHandler.getInstance().getMainLineChart().removeAllMarkers();
        FunctionHandler.getInstance().getMainLineChart().removeBlueZone();

        /*set limit line func*/
        FunctionHandler.getInstance().getMainLineChart().setEnabledLimitLine(true);
        FunctionHandler.getInstance().getMainLineChart().setLimitType(getLimitData().getUpper());
        FunctionHandler.getInstance().getMainLineChart().setEnabledLimitAlarm(getLimitData().getOnAlarm());
        FunctionHandler.getInstance().getMainLineChart().setEnabledLimitMsg(getLimitData().getPassFailEnabled());
        FunctionHandler.getInstance().getMainLineChart().setLimitValue(VswrDataHandler.getInstance().getConfigData().getLimitData().getLimitValueForVswr());


        /*set sweep parameter*/
        VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(getSweepData().getDataPoint());
        VswrDataHandler.getInstance().getConfigData().getSweepData().setContinuous(getSweepData().isContinuous());
        VswrDataHandler.getInstance().getConfigData().getSweepData().setRun(getSweepData().isRun());


        /*set value by vswr type*/
        if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

            FunctionHandler.getInstance().getMainLineChart().setInverted(false);

            InitActivity.logMsg("LimitLine VSWR", getLimitData().getLimitValueForVswr() + "");
            FunctionHandler.getInstance().getMainLineChart().setLimitValue(
                    getLimitData().getLimitValueForVswr()
            );

            FunctionHandler.getInstance().getMainLineChart().setMinYRange(getAmplitudeData().getAmpMinForVswr());
            FunctionHandler.getInstance().getMainLineChart().setMaxYRange(getAmplitudeData().getAmpMaxForVswr());

        } else if (type == MeasureType.MEASURE_TYPE.RL || type == MeasureType.MEASURE_TYPE.DTF_RL || type == MeasureType.MEASURE_TYPE.CABLE_LOSS) {

            FunctionHandler.getInstance().getMainLineChart().setInverted(true);

            InitActivity.logMsg("LimitLine RL", getLimitData().getLimitValueForRl() + "");
            FunctionHandler.getInstance().getMainLineChart().setLimitValue(
                    getLimitData().getLimitValueForRl()
            );
            FunctionHandler.getInstance().getMainLineChart().setMinYRange(getAmplitudeData().getAmpMinForRl());
            FunctionHandler.getInstance().getMainLineChart().setMaxYRange(getAmplitudeData().getAmpMaxForRl());

        }
        FunctionHandler.getInstance().getMainLineChart().invalidate();


    }

}

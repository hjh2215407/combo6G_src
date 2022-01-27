package com.dabinsystems.pact_app.Data.SA.MeasSetupData.TransmitOnOff;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.MeasSetupData;
import com.dabinsystems.pact_app.R;
import com.github.mikephil.charting.utils.Utils;

public class TransmitOnOffMeasSetupData extends MeasSetupData {

    public final Float MIN_LEV = 0.1f;
    public final Float MAX_LEV = 100f;

    public final Float MIN_TIME = 0f;
    public final Float MAX_TIME = 100f;

    public final Float MIN_OFF_POWER = -200f;
    public final Float MAX_OFF_POWER = 200f;

    private Float RampUpStartLev = 10f;
    private Float RampUpStopLev = 90f;
    private Float RampDownStartLev = 90f;
    private Float RampDownStopLev = 10f;

    private Float LimitOffPower = -82.5f;
    private Float LimitRampUpTime = 10f; //us
    private Float LimitRampDownTime = 10f; //us

    private Float OnPower = null;
    private Float OffPower = null;

    private Float RampUpTime = null;
    private Float RampDownTime = null;

    private Integer OffPowerPass = null;
    private Integer RampUpTimePass = null;
    private Integer RampDownTimePass = null;



    public TransmitOnOffMeasSetupData() {


    }

    public Float getRampUpStartLev() {
        return RampUpStartLev;
    }

    public void setRampUpStartLev(Float rampUpStartLev) {
        if (rampUpStartLev < MIN_LEV || rampUpStartLev > MAX_LEV) return;
        RampUpStartLev = rampUpStartLev;
    }

    public Float getRampUpStopLev() {
        return RampUpStopLev;
    }

    public void setRampUpStopLev(Float rampUpStopLev) {
        if (rampUpStopLev < MIN_LEV || rampUpStopLev > MAX_LEV) return;
        RampUpStopLev = rampUpStopLev;
    }

    public Float getRampDownStartLev() {
        return RampDownStartLev;
    }

    public void setRampDownStartLev(Float rampDownStartLev) {
        if (rampDownStartLev < MIN_LEV || rampDownStartLev > MAX_LEV) return;
        RampDownStartLev = rampDownStartLev;
    }

    public Float getRampDownStopLev() {
        return RampDownStopLev;
    }

    public void setRampDownStopLev(Float rampDownStopLev) {
        if (rampDownStopLev < MIN_LEV || rampDownStopLev > MAX_LEV) return;
        RampDownStopLev = rampDownStopLev;
    }

    public Float getLimitOffPower() {
        return LimitOffPower;
    }

    public void setLimitOffPower(Float limitOffPower) {
        if (limitOffPower < MIN_OFF_POWER || limitOffPower > MAX_OFF_POWER) return;
        LimitOffPower = limitOffPower;
    }

    public Float getLimitRampUpTime() {
        return LimitRampUpTime;
    }

    public void setLimitRampUpTime(Float limitRampUpTime) {
        if (limitRampUpTime < MIN_TIME || limitRampUpTime > MAX_TIME) return;
        LimitRampUpTime = limitRampUpTime;
    }

    public Float getLimitRampDownTime() {
        return LimitRampDownTime;
    }

    public void setLimitRampDownTime(Float limitRampDownTime) {
        if (limitRampDownTime < MIN_TIME || limitRampDownTime > MAX_TIME) return;
        LimitRampDownTime = limitRampDownTime;
    }

    public Float getOnPower() {
        return OnPower;
    }

    public void setOnPower(Float onPower) {
        OnPower = onPower;

        String val;

        val = Utils.formatNumber(OnPower, 2, false) + " dBm";
        MainActivity.getActivity().runOnUiThread(()->{

            MainActivity.getBinding().lineChartLayout.transmitOnOffLayout.tvTransmitOnPowerVal.setText(val + "");

        });

    }

    public Float getOffPower() {
        return OffPower;
    }

    public void setOffPower(Float offPower) {
        OffPower = offPower;

        String val;

        val = Utils.formatNumber(OffPower, 2, false) + " dBm/1MHz";
        MainActivity.getActivity().runOnUiThread(()->{

            MainActivity.getBinding().lineChartLayout.transmitOnOffLayout.tvTransmitOffPowerVal.setText(val + "");

        });

    }

    public Float getRampUpTime() {
        return RampUpTime;
    }

    public void setRampUpTime(Float rampUpTime) {
        RampUpTime = rampUpTime;

        String timeStr;

        timeStr = Utils.formatNumber(RampUpTime, 2, false) + " μs";
        MainActivity.getActivity().runOnUiThread(()->{

            MainActivity.getBinding().lineChartLayout.transmitOnOffLayout.tvTransmitRampUpVal.setText(timeStr);

        });

    }

    public Float getRampDownTime() {
        return RampDownTime;
    }

    public void setRampDownTime(Float rampDownTime) {
        RampDownTime = rampDownTime;

        String timeStr;

        timeStr = Utils.formatNumber(RampDownTime, 2, false) + " μs";
        MainActivity.getActivity().runOnUiThread(()->{

            MainActivity.getBinding().lineChartLayout.transmitOnOffLayout.tvTransmitRampDownVal.setText(timeStr);

        });

    }

    public Integer getOffPowerPass() {
        return OffPowerPass;
    }

    public void setOffPowerPass(Integer onOffPowerPass) {
        OffPowerPass = onOffPowerPass;


        MainActivity.getActivity().runOnUiThread(()->{
            String val;
            if(OffPowerPass == 0) {

                val = "Fail";
                MainActivity.getBinding().lineChartLayout.transmitOnOffLayout.tvTransmitOffPowerPass.setTextColor(
                        MainActivity.getActivity().getResources().getColor(R.color.transmit_fail)
                );

            } else {

                val = "Pass";
                MainActivity.getBinding().lineChartLayout.transmitOnOffLayout.tvTransmitOffPowerPass.setTextColor(
                        MainActivity.getActivity().getResources().getColor(R.color.transmit_pass)
                );

            }

            MainActivity.getBinding().lineChartLayout.transmitOnOffLayout.tvTransmitOffPowerPass.setText(val);

        });

    }

    public Integer getRampUpTimePass() {
        return RampUpTimePass;
    }

    public void setRampUpTimePass(Integer rampUpTimePass) {
        RampUpTimePass = rampUpTimePass;

        MainActivity.getActivity().runOnUiThread(()->{
            String val;
            if(RampUpTimePass == 0) {

                val = "Fail";
                MainActivity.getBinding().lineChartLayout.transmitOnOffLayout.tvTransmitRampUpPass.setTextColor(
                        MainActivity.getActivity().getResources().getColor(R.color.transmit_fail)
                );

            } else {

                val = "Pass";
                MainActivity.getBinding().lineChartLayout.transmitOnOffLayout.tvTransmitRampUpPass.setTextColor(
                        MainActivity.getActivity().getResources().getColor(R.color.transmit_pass)
                );

            }

            MainActivity.getBinding().lineChartLayout.transmitOnOffLayout.tvTransmitRampUpPass.setText(val);

        });
    }

    public Integer getRampDownTimePass() {
        return RampDownTimePass;
    }

    public void setRampDownTimePass(Integer rampDownTimePass) {
        RampDownTimePass = rampDownTimePass;

        MainActivity.getActivity().runOnUiThread(()->{
            String val;
            if(RampDownTimePass == 0) {

                val = "Fail";
                MainActivity.getBinding().lineChartLayout.transmitOnOffLayout.tvTransmitRampDownPass.setTextColor(
                        MainActivity.getActivity().getResources().getColor(R.color.transmit_fail)
                );

            } else {

                val = "Pass";
                MainActivity.getBinding().lineChartLayout.transmitOnOffLayout.tvTransmitRampDownPass.setTextColor(
                        MainActivity.getActivity().getResources().getColor(R.color.transmit_pass)
                );

            }

            MainActivity.getBinding().lineChartLayout.transmitOnOffLayout.tvTransmitRampDownPass.setText(val);

        });
    }
}

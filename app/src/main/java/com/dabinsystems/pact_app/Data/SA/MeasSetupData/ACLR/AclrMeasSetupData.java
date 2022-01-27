package com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR;

import android.util.Log;

import com.dabinsystems.pact_app.Data.SA.MeasSetupData.MeasSetupData;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;

import java.util.Arrays;

public class AclrMeasSetupData extends MeasSetupData {

    private AVG_HOLD_MODE AvgMode;

    private int AvgHoldNum = 100;
    private final int MIN = 1;
    private final int MAX = 200;

    private Float TotalCarrierPower;

    private Float[] CarrierPower;

    private Float[] LowerPowerDbc;
    private Float[] LowerPowerDbm;
    private Integer[] LowerPassFail;

    private Float[] UpperPowerDbc;
    private Float[] UpperPowerDbm;
    private Integer[] UpperPassFail;

    private AclrCarrierSetupData CarrierSetupData;
    private AclrOffsetSetupData OffsetSetupData;

    public AclrMeasSetupData() {

        AvgMode = AVG_HOLD_MODE.ON;
        AvgHoldNum = 10;

        CarrierPower = new Float[2];

        LowerPowerDbc = new Float[5];
        LowerPowerDbm = new Float[5];
        LowerPassFail = new Integer[5];

        UpperPowerDbc = new Float[5];
        UpperPowerDbm = new Float[5];
        UpperPassFail = new Integer[5];

        Arrays.fill(CarrierPower, null);

        Arrays.fill(LowerPowerDbc, null);
        Arrays.fill(LowerPowerDbm, null);
        Arrays.fill(LowerPassFail, 1);

        Arrays.fill(UpperPowerDbc, null);
        Arrays.fill(UpperPowerDbm, null);
        Arrays.fill(UpperPassFail, 1);

    }

    public void setAvgHold(int val) {

        if (val < MIN || val > MAX) return;

        AvgHoldNum = val;

    }

    public int getAvgHold() {

        return AvgHoldNum;

    }

    public AVG_HOLD_MODE getAvgMode() {

        return AvgMode;

    }

    public Boolean isOnAvg() {

        if (AvgMode == AVG_HOLD_MODE.ON) return true;
        else return false;


    }

    public void setAvgMode(AVG_HOLD_MODE onOff) {

        AvgMode = onOff;

    }

    public void setAvgMode(int mode) {

        if (mode == 0) AvgMode = AVG_HOLD_MODE.OFF;
        else AvgMode = AVG_HOLD_MODE.ON;

    }

    public void setAvgMode(Boolean onOff) {

        if (!onOff) AvgMode = AVG_HOLD_MODE.OFF;
        else AvgMode = AVG_HOLD_MODE.ON;

    }

    public AclrCarrierSetupData getCarrierSetupData() {

        if (CarrierSetupData == null) CarrierSetupData = new AclrCarrierSetupData();
        return CarrierSetupData;
    }

    public AclrOffsetSetupData getOffsetSetupData() {
        if (OffsetSetupData == null) OffsetSetupData = new AclrOffsetSetupData();
        return OffsetSetupData;
    }

    public Float getTotalCarrierPower() {
        return TotalCarrierPower;
    }

    public void setTotalCarrierPower(Float totalCarrierPower) {
        TotalCarrierPower = totalCarrierPower;
    }

    public Float getCarrierPower(int idx) {
        return CarrierPower[idx];
    }

    public void setCarrierPower(Float carrierPower, int idx) {
        CarrierPower[idx] = carrierPower;
    }

    public Float getLowerPowerDbc(int idx) {
        return LowerPowerDbc[idx];
    }

    public void setLowerPowerDbc(Float lowerPowerDbc, int idx) {
        LowerPowerDbc[idx] = lowerPowerDbc;
    }

    public Float getLowerPowerDbm(int idx) {
        return LowerPowerDbm[idx];
    }

    public void setLowerPowerDbm(Float lowerPowerDbm, int idx) {
        LowerPowerDbm[idx] = lowerPowerDbm;
    }

    public Float getUpperPowerDbc(int idx) {
        return UpperPowerDbc[idx];
    }

    public void setUpperPowerDbc(Float upperPowerDbc, int idx) {
        UpperPowerDbc[idx] = upperPowerDbc;
    }

    public Float getUpperPowerDbm(int idx) {
        return UpperPowerDbm[idx];
    }

    public void setUpperPowerDbm(Float upperPowerDbm, int idx) {
        UpperPowerDbm[idx] = upperPowerDbm;
    }

    public Integer getLowerPassFail(int idx) {
        return LowerPassFail[idx];
    }

    public void setLowerPassFail(Integer lowerPassFail, int idx) {
        if(lowerPassFail != 0 && lowerPassFail != 1) return ;
        LowerPassFail[idx] = lowerPassFail;
    }

    public Integer getUpperPassFail(int idx) {
        return UpperPassFail[idx];
    }

    public void setUpperPassFail(Integer upperPassFail, int idx) {
        if(upperPassFail != 0 && upperPassFail != 1) return ;
        UpperPassFail[idx] = upperPassFail;
    }

}

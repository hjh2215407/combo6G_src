package com.dabinsystems.pact_app.Data.ModAccuracy.NR5G;

import android.util.Log;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.TriggerSource;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;

public class NrData {

    private NrInfoData InfoData;
    private NrFrequencyData FreqData;
    private NrLimitData LimitData;
    private NrAmpData AmpData;
    private NrProfileData ProfileData;
    private NrTaeData TaeInfoData;
    private NrSSBInfoData NrSSBInfoData;
    private com.dabinsystems.pact_app.Data.ModAccuracy.TriggerSource TriggerSource;
    private NrInterTaeData InterTaeData;
    private NrDuplexData NrDuplexData;
    private NrSCSData NrSCSData;

    public NrInterTaeData getInterTaeData() {

        if(InterTaeData == null) InterTaeData = new NrInterTaeData();
        return InterTaeData;

    }

    public com.dabinsystems.pact_app.Data.ModAccuracy.TriggerSource getTriggerSource() {

        if(TriggerSource == null) TriggerSource = new TriggerSource();
        return TriggerSource;

    }

    public NrTaeData getTaeInfoData() {

        if(TaeInfoData == null) TaeInfoData = new NrTaeData();
        return TaeInfoData;

    }

    public NrProfileData getProfileData() {

        if(ProfileData == null) ProfileData = new NrProfileData();
        return ProfileData;

    }

    public NrSSBInfoData getSsbInfoData() {
        if(NrSSBInfoData == null) NrSSBInfoData = new NrSSBInfoData();
        return NrSSBInfoData;
    }

    public NrAmpData getAmpData() {

        if(AmpData == null) AmpData = new NrAmpData();
        return AmpData;

    }

    public NrLimitData getLimitData() {

        if(LimitData == null) LimitData = new NrLimitData();
        return LimitData;

    }

    public NrInfoData getInfoData() {

        if(InfoData == null) InfoData = new NrInfoData();
        return InfoData;

    }

    public NrDuplexData getDuplexData() {

        if (NrDuplexData == null) NrDuplexData = new NrDuplexData();
        return NrDuplexData;
    }

    public NrSCSData getScsData() {

        if (NrSCSData == null) NrSCSData = new NrSCSData();
        return NrSCSData;
    }

    public NrFrequencyData getFreqData() {
        if(FreqData == null) FreqData = new NrFrequencyData();
        return FreqData;
    }

    public String getNrCmd() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

        StringBuffer bufferStr = new StringBuffer();
        bufferStr.append(mode.getHexString());
        bufferStr.append(" ");
        bufferStr.append(type.getHexString());
        bufferStr.append(" ");
        bufferStr.append(Math.round(getFreqData().getCenterFreq() * 1000000d));
        bufferStr.append(" ");
        bufferStr.append(getAmpData().getATTMode().getHexString());
        bufferStr.append(" ");
        bufferStr.append(getAmpData().getAttenuator());
        bufferStr.append(" ");
        bufferStr.append((int)(getAmpData().getOffset() * 100f));
        bufferStr.append(" ");
        bufferStr.append(getAmpData().getPreampMode().getHexString());
        bufferStr.append(" ");
        bufferStr.append(getProfileData().getProfile().getCmd());
        bufferStr.append(" ");
        bufferStr.append(getSsbInfoData().getConfigMode().getHexString());
        bufferStr.append(" ");
        bufferStr.append(getSsbInfoData().getRbOffset());
        bufferStr.append(" ");
        bufferStr.append(getSsbInfoData().getKssb());
        bufferStr.append(" ");
        bufferStr.append((int)(getLimitData().getFreqOffsetValue() * 100f));
        bufferStr.append(" ");
        bufferStr.append((int)(getLimitData().getMinEvm() * 100f));
        bufferStr.append(" ");
        bufferStr.append((int)(getLimitData().getTae() * 100f));
        bufferStr.append(" ");
        bufferStr.append((int)(getLimitData().getInterTae() * 100f)); // [jigum] 2021-07-16 ‘Inter-TAE’ 추가.
        bufferStr.append(" ");
        bufferStr.append(getTriggerSource().getTriggerSource().getValue());
        bufferStr.append(" ");
        bufferStr.append(getTaeInfoData().getTeaMeasMode().getValue());
        bufferStr.append(" ");
        bufferStr.append(getTaeInfoData().getTaeType().getValue());
        bufferStr.append(" ");
        bufferStr.append(getTaeInfoData().getMeasCount());
        bufferStr.append(" ");
        bufferStr.append(getTaeInfoData().getNumOfTxAnt().getCmd());
        bufferStr.append(" ");
        bufferStr.append(getInterTaeData().getCarrierSetting(0).getValue());
        bufferStr.append(" ");
        bufferStr.append(getInterTaeData().getCarrierSetting(1).getValue());
        bufferStr.append(" ");
        bufferStr.append(getInterTaeData().getCarrierSetting(2).getValue());
        bufferStr.append(" ");
        bufferStr.append(getInterTaeData().getCarrierSetting(3).getValue());
        bufferStr.append(" ");
        bufferStr.append((long)((double)Math.round(getInterTaeData().getFreqOfCarrier(0) * 100)/100 * 1000 * 1000));
        bufferStr.append(" ");
        bufferStr.append((long)((double)Math.round(getInterTaeData().getFreqOfCarrier(1) * 100)/100 * 1000 * 1000));
        bufferStr.append(" ");
        bufferStr.append((long)((double)Math.round(getInterTaeData().getFreqOfCarrier(2) * 100)/100 * 1000 * 1000));
        bufferStr.append(" ");
        bufferStr.append((long)((double)Math.round(getInterTaeData().getFreqOfCarrier(3) * 100)/100 * 1000 * 1000));
        bufferStr.append(" ");
        bufferStr.append(getInterTaeData().getProfile(0).getCmd());
        bufferStr.append(" ");
        bufferStr.append(getInterTaeData().getProfile(1).getCmd());
        bufferStr.append(" ");
        bufferStr.append(getInterTaeData().getProfile(2).getCmd());
        bufferStr.append(" ");
        bufferStr.append(getInterTaeData().getProfile(3).getCmd());
        //@@ [21.12.31] NR 5G Parameter Added Duplex Type, SCS
        bufferStr.append(" ");
        bufferStr.append(getDuplexData().getDuplexType().getValue());
        bufferStr.append(" ");
        bufferStr.append(getScsData().getSCS().getCmd());
        //@@


        String cmd = bufferStr.toString();

        InitActivity.logMsg("SendSettingValues", cmd);

        return cmd;

    }

}

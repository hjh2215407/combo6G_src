package com.dabinsystems.pact_app.Data.SA;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ChannelPowerMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.FailSourceData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SemMeasTypeData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.DETECTOR;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.TRACE_MODE;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.TRACE_TYPE;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.InterferenceHunting.InterferenceMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.MeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.OccBwMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemRefChannelData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SpuriousEmission.FreqRangeTableData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SpuriousEmission.SpuriousEmissionMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SweptSaMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.TransmitOnOff.TransmitOnOffMeasSetupData;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;

import java.util.ArrayList;

import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.KHZ1;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.KHZ10;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.KHZ100;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.MHZ1;
import static com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SideData.SIDE_OPTION.BOTH;
import static com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData.MASK_ON_OFF.OFF;
import static com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData.MASK_ON_OFF.ON;

public class SaConfigData {

    private final int MIN_INTEG = 1;
    private final long MAX_INTEG = 1000000000; // 1,000,000,000

    MeasureMode.MEASURE_MODE Mode;
    MeasureType.MEASURE_TYPE Type;

    /* set value parameter */

    /*
     * set Freuquency
     * */

    private SAFrequencyData FrequencyData;

    /*
     * set Amplitude
     * */

    private SaAmplitudeData AmplitudeData;

    //BW Data
    private BWData BwData;

    //Trace data
    private TraceData TraceData;

    //SweepTimeData
    private SaSweepTimeData SweepTimeData;

    //MeasSetup
    private SweptSaMeasSetupData SweptSaMeasSetupData;
    private ChannelPowerMeasSetupData ChannelPowerMeasSetupData;
    private OccBwMeasSetupData OccupiedBwMeasSetupData;
    private AclrMeasSetupData AclrMeasSetupData;
    private SemMeasSetupData SemMeasSetupData;
    private TransmitOnOffMeasSetupData TransmitOnOffData;
    private SpuriousEmissionMeasSetupData SpuriousEmissionData;
    private InterferenceMeasSetupData InterferenceHuntingData;

    private Boolean isInit = false;

    public SaConfigData(MeasureMode.MEASURE_MODE mode, MeasureType.MEASURE_TYPE type) {

        Mode = mode;
        Type = type;

        FrequencyData = new SAFrequencyData(Type);
        AmplitudeData = new SaAmplitudeData();
        BwData = new BWData();
        TraceData = new TraceData();

        initData();

    }

    public SAFrequencyData getFrequencyData() {
        if (FrequencyData == null) FrequencyData = new SAFrequencyData(Type);
        return FrequencyData;
    }

    public BWData getBwData() {
        if (BwData == null) BwData = new BWData();
        return BwData;

    }

    public TraceData getTraceData() {
        if (TraceData == null) TraceData = new TraceData();
        return TraceData;

    }

    public SaSweepTimeData getSweepTimeData() {

        if (SweepTimeData == null) SweepTimeData = new SaSweepTimeData(Type);
        return SweepTimeData;

    }

    public SaAmplitudeData getAmplitudeData() {
        if (AmplitudeData == null) AmplitudeData = new SaAmplitudeData();
        return AmplitudeData;
    }

    public SweptSaMeasSetupData getSweptSaMeasSetupData() {
        if (SweptSaMeasSetupData == null) SweptSaMeasSetupData = new SweptSaMeasSetupData();
        return SweptSaMeasSetupData;
    }

    public ChannelPowerMeasSetupData getChannelPowerMeasSetupData() {
        if (ChannelPowerMeasSetupData == null)
            ChannelPowerMeasSetupData = new ChannelPowerMeasSetupData();
        return ChannelPowerMeasSetupData;

    }

    public OccBwMeasSetupData getOccupiedBwMeasSetupData() {

        if (OccupiedBwMeasSetupData == null) OccupiedBwMeasSetupData = new OccBwMeasSetupData();
        return OccupiedBwMeasSetupData;

    }

    public AclrMeasSetupData getAclrMeasSetupData() {

        if (AclrMeasSetupData == null) AclrMeasSetupData = new AclrMeasSetupData();
        return AclrMeasSetupData;

    }

    public SemMeasSetupData getSemMeasSetupData() {

        if (SemMeasSetupData == null) SemMeasSetupData = new SemMeasSetupData();
        return SemMeasSetupData;

    }

    public TransmitOnOffMeasSetupData getTransmitOnOffData() {
        if (TransmitOnOffData == null) TransmitOnOffData = new TransmitOnOffMeasSetupData();
        return TransmitOnOffData;
    }

    public SpuriousEmissionMeasSetupData getSpuriousEmissionData() {
        if (SpuriousEmissionData == null)
            SpuriousEmissionData = new SpuriousEmissionMeasSetupData();
        return SpuriousEmissionData;
    }

    public InterferenceMeasSetupData getInterferenceHuntingData() {
        if(InterferenceHuntingData == null) InterferenceHuntingData = new InterferenceMeasSetupData();
        return InterferenceHuntingData;
    }

    public void calSpanForAclr() {

        Double carrierSpacing = getAclrMeasSetupData().getCarrierSetupData().getCarrierSpacing();
        Double carrierInteg = getAclrMeasSetupData().getCarrierSetupData().getIntegBw();
        Double calCarrier = (carrierSpacing + carrierInteg / 2);

        Integer numOffOffset = getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset();

        Double[] calArr = new Double[numOffOffset + 1];
        calArr[0] = calCarrier;
        Double maxVal = calArr[0];

        for (int i = 0; i < numOffOffset; i++) {

            Double spacing = getAclrMeasSetupData().getOffsetSetupData().getOffsetSpacing(i);
            Double integ = getAclrMeasSetupData().getOffsetSetupData().getIntegBw(i);
            calArr[i] = spacing + integ / 2;

            if (calArr[i] > maxVal) maxVal = calArr[i];

        }

        Double calSpan = maxVal * 2;
        if (getFrequencyData().getSpan() < calSpan) getFrequencyData().setSpan(calSpan);

    }

    public void initData() {

        if (Type == MeasureType.MEASURE_TYPE.SWEPT_SA) {
            SweptSaMeasSetupData = new SweptSaMeasSetupData();

            FrequencyData.setCenterFreq(3650.01d);
            FrequencyData.setSpan(150d);
            FrequencyData.checkFreqRange();

            BwData.setRBW(BWData.BAND_WIDTH.KHZ100); // Original
            BwData.setVBW(BWData.BAND_WIDTH.KHZ100); // Original

//            BwData.setVBW(MHZ1); // DEMO

            AmplitudeData.setRefLev(0f);
            AmplitudeData.setScaleDiv(10f);
            AmplitudeData.setAttenuator(20);
            TraceData.setAllMode(TRACE_MODE.CLEAR_WRITE);
            SweptSaMeasSetupData.setAvgMode(MeasSetupData.AVG_HOLD_MODE.ON);
            SweptSaMeasSetupData.setAvgHold(100);

        } else if (Type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            ChannelPowerMeasSetupData = new ChannelPowerMeasSetupData();
            FrequencyData.setCenterFreq(3650.01d);
            FrequencyData.setSpan(150d);
            FrequencyData.checkFreqRange();

            BwData.setRBW(BWData.BAND_WIDTH.KHZ100);
            BwData.setVBW(BWData.BAND_WIDTH.KHZ100); // Original
//            BwData.setVBW(MHZ1); // DEMO

            AmplitudeData.setRefLev(0f);
            AmplitudeData.setAttenuator(20);
            TraceData.setAllMode(TRACE_MODE.AVERAGE);
            TraceData.setAllType(TRACE_TYPE.UPDATE);
            TraceData.setAllDetector(DETECTOR.RMS);

            ChannelPowerMeasSetupData.setAvgMode(MeasSetupData.AVG_HOLD_MODE.ON);
            ChannelPowerMeasSetupData.setAvgHold(10);
            ChannelPowerMeasSetupData.setIngegBW((long) (98.28 * 1000 * 1000));

        } else if (Type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            OccupiedBwMeasSetupData = new OccBwMeasSetupData();

            FrequencyData.setCenterFreq(3650.01d);
            FrequencyData.setSpan(200f);
            FrequencyData.checkFreqRange();
            BwData.setRBW(BWData.BAND_WIDTH.KHZ100); // 30KHZ
            BwData.setVBW(BWData.BAND_WIDTH.MHZ1);
            AmplitudeData.setRefLev(0f);
            AmplitudeData.setAttenuator(20);
            TraceData.setAllMode(TRACE_MODE.AVERAGE);
            TraceData.setAllDetector(DETECTOR.RMS);

            OccupiedBwMeasSetupData.setAvgMode(MeasSetupData.AVG_HOLD_MODE.ON);
            OccupiedBwMeasSetupData.setAvgHold(10);
            OccupiedBwMeasSetupData.setOBWPower(99.00f);
            OccupiedBwMeasSetupData.setXDB(-26f);


        } else if (Type == MeasureType.MEASURE_TYPE.ACLR) {

            AclrMeasSetupData = new AclrMeasSetupData();

            FrequencyData.setCenterFreq(3650.01d);
            FrequencyData.setSpan(500f);
            FrequencyData.checkFreqRange();
            BwData.setRBW(BWData.BAND_WIDTH.KHZ100);
            BwData.setVBW(BWData.BAND_WIDTH.MHZ1);
            AmplitudeData.setRefLev(0f);
            AmplitudeData.setAttenuator(20);


            TraceData.setAllMode(TRACE_MODE.AVERAGE);
            TraceData.setAllDetector(DETECTOR.RMS);

            AclrMeasSetupData.setAvgMode(MeasSetupData.AVG_HOLD_MODE.ON);
            AclrMeasSetupData.setAvgHold(10);

        } else if (Type == MeasureType.MEASURE_TYPE.SEM) {
            //org
            /*SemMeasSetupData = new SemMeasSetupData();

            FrequencyData.setCenterFreq(3650.01d);
            FrequencyData.setSpan(100f);
            FrequencyData.checkFreqRange();
            BwData.setRBW(BWData.BAND_WIDTH.KHZ100);
            BwData.setVBW(BWData.BAND_WIDTH.MHZ1);
            AmplitudeData.setRefLev(0f);
            AmplitudeData.setAttenuator(20);
            TraceData.setAllMode(TRACE_MODE.AVERAGE);
            TraceData.setAllDetector(DETECTOR.RMS);

            SemMeasSetupData.setAvgMode(MeasSetupData.AVG_HOLD_MODE.ON);
            SemMeasSetupData.setAvgHold(10);

            getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = SemMeasSetupData.getRefChannelData();
            refChannelData.setSpan(100d);
            refChannelData.setIntegBw(98.28f);
            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = SemMeasSetupData.getEditMaskData();

            *//*
             * mask index 0(1)
             * *//*
            editMaskData.setMaskOnOff(ON, 0);
            editMaskData.setMaskSide(BOTH, 0);

            editMaskData.setStartFreq(0.05d, 0);
            editMaskData.setStopFreq(5.05d, 0);

            editMaskData.getBwData(0).setRBW(KHZ100);
            editMaskData.getBwData(0).setVBW(KHZ1);

            editMaskData.setAbsStartLimit(-5.2f, 0);
            editMaskData.setAbsStopLimit(-12.2f, 0);

            editMaskData.setRelStartLimit(-30f, 0);
            editMaskData.setRelStopLimit(-30f, 0);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 0);

            *//*
             * mask index 1(2)
             * *//*

            editMaskData.setMaskOnOff(ON, 1);
            editMaskData.setMaskSide(BOTH, 1);

            editMaskData.setStartFreq(5.05d, 1);
            editMaskData.setStopFreq(10.05d, 1);

            editMaskData.getBwData(1).setRBW(KHZ100);
            editMaskData.getBwData(1).setVBW(KHZ1);

            editMaskData.setAbsStartLimit(-12.2f, 1);
            editMaskData.setAbsStopLimit(-12.2f, 1);

            editMaskData.setRelStartLimit(-30f, 1);
            editMaskData.setRelStopLimit(-30f, 1);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 1);

            *//*
             * mask index 2(3)
             * *//*

            editMaskData.setMaskOnOff(ON, 2);
            editMaskData.setMaskSide(BOTH, 2);

            editMaskData.setStartFreq(10.05d, 2);
            editMaskData.setStopFreq(40d, 2);

            editMaskData.getBwData(2).setRBW(MHZ1);
            editMaskData.getBwData(2).setVBW(KHZ10);

            editMaskData.setAbsStartLimit(-15f, 2);
            editMaskData.setAbsStopLimit(-15f, 2);

            editMaskData.setRelStartLimit(-30f, 2);
            editMaskData.setRelStopLimit(-30f, 2);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 2);

            *//*
             * mask index 3(4)
             * *//*

            editMaskData.setMaskOnOff(OFF, 3);
            editMaskData.setMaskSide(BOTH, 3);

            editMaskData.setStartFreq(40d, 3);
            editMaskData.setStopFreq(50d, 3);

            editMaskData.getBwData(3).setRBW(MHZ1);
            editMaskData.getBwData(3).setVBW(KHZ10);

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);*/
            //org

            //@@ [21.12.30] SEM init fix (5G NR 100MHz)
            SemMeasSetupData = new SemMeasSetupData();

            FrequencyData.setCenterFreq(3650.01d);
            FrequencyData.setSpan(100f);
            FrequencyData.checkFreqRange();
            BwData.setRBW(BWData.BAND_WIDTH.KHZ100);
            BwData.setVBW(BWData.BAND_WIDTH.MHZ1);
            AmplitudeData.setRefLev(0f);
            AmplitudeData.setAttenuator(20);
            TraceData.setAllMode(TRACE_MODE.AVERAGE);
            TraceData.setAllDetector(DETECTOR.RMS);

            SemMeasSetupData.setAvgMode(MeasSetupData.AVG_HOLD_MODE.ON);
            SemMeasSetupData.setAvgHold(10);

            getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = SemMeasSetupData.getRefChannelData();
            refChannelData.setSpan(100d);
            refChannelData.setIntegBw(98.28f);
            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = SemMeasSetupData.getEditMaskData();

            /*
             * mask index 0(1)
             * */

            editMaskData.setMaskOnOff(ON, 0);
            editMaskData.setStartFreq(0.05d, 0);
            editMaskData.setStopFreq(5.05d, 0);
            editMaskData.setMaskSide(BOTH, 0);

            editMaskData.getBwData(0).setRBW(KHZ100);
            editMaskData.getBwData(0).setVBW(KHZ1);

            editMaskData.setAbsStartLimit(-5.2f, 0);
            editMaskData.setAbsStopLimit(-12.2f, 0);

            editMaskData.setRelStartLimit(-30f, 0);
            editMaskData.setRelStopLimit(-30f, 0);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 0);

            /*
             * mask index 1(2)
             * */

            editMaskData.setMaskOnOff(ON, 1);
            editMaskData.setStartFreq(5.05d, 1);
            editMaskData.setStopFreq(10.05d, 1);
            editMaskData.setMaskSide(BOTH, 1);

            editMaskData.getBwData(1).setRBW(KHZ100);
            editMaskData.getBwData(1).setVBW(KHZ1);

            editMaskData.setAbsStartLimit(-12.2f, 1);
            editMaskData.setAbsStopLimit(-12.2f, 1);

            editMaskData.setRelStartLimit(-30f, 1);
            editMaskData.setRelStopLimit(-30f, 1);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 1);

            /*
             * mask index 2(3)
             * */

            editMaskData.setMaskOnOff(ON, 2);
            editMaskData.setStartFreq(10.5d, 2);
            editMaskData.setStopFreq(40d, 2);
            editMaskData.setMaskSide(BOTH, 2);

            editMaskData.getBwData(2).setRBW(MHZ1);
            editMaskData.getBwData(2).setVBW(KHZ10);

            editMaskData.setAbsStartLimit(-15f, 2);
            editMaskData.setAbsStopLimit(-15f, 2);

            editMaskData.setRelStartLimit(-30f, 2);
            editMaskData.setRelStopLimit(-30f, 2);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 2);

            /*
             * mask index 3(4)
             * */

            editMaskData.setMaskOnOff(OFF, 3);
            editMaskData.setStartFreq(40d, 3);
            editMaskData.setStopFreq(50d, 3);
            editMaskData.setMaskSide(BOTH, 3);

            editMaskData.getBwData(3).setRBW(MHZ1);
            editMaskData.getBwData(3).setVBW(KHZ10);

            editMaskData.setAbsStartLimit(-12.2f, 3);
            editMaskData.setAbsStopLimit(-12.2f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);
            //@@

        } else if (Type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {

            TransmitOnOffData = new TransmitOnOffMeasSetupData();
            FrequencyData.setCenterFreq(3650.01d);
//            FrequencyData.zerospan();

        }

        isInit = true;

    }

    public void loadData() {

        Type = FunctionHandler.getInstance().getMeasureType().getType();

        if (!isInit) initData();

        FunctionHandler.getInstance().getMainLineChart().setInverted(false);

        InitActivity.logMsg("loadData", getFrequencyData().getCenterFreq() + " " + FunctionHandler.getInstance().getMeasureType().getType().getFullName());

        if (Type == MeasureType.MEASURE_TYPE.SPURIOUS_EMISSION) {

            ArrayList<FreqRangeTableData> dataList = SaDataHandler.getInstance().getConfigData().getSpuriousEmissionData().getFreqRangeTableDataList();

            Double start = dataList.get(0).getStartFreq();
            Double stop = dataList.get(0).getStopFreq();

            for(FreqRangeTableData data : dataList) {

                if(data.getStartFreq() < start)
                    start = data.getStartFreq();

                if(data.getStopFreq() > stop)
                    stop = data.getStopFreq();

            }

        }

        FunctionHandler.getInstance().getMainLineChart().setScaleDiv(AmplitudeData.getScaleDiv());
        FunctionHandler.getInstance().getMainLineChart().setRefLev(AmplitudeData.getRefLev());
        FunctionHandler.getInstance().getMainLineChart().setOffset(AmplitudeData.getOffset());

        FunctionHandler.getInstance().getGateLineChart().setRefLev(AmplitudeData.getRefLev());
        FunctionHandler.getInstance().getGateLineChart().setOffset(AmplitudeData.getOffset());

        if (Type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            FunctionHandler.getInstance().getMainLineChart().setVisible(0, true);
            FunctionHandler.getInstance().getMainLineChart().setVisible(1, true);
            FunctionHandler.getInstance().getMainLineChart().setVisible(2, true);
            FunctionHandler.getInstance().getMainLineChart().setVisible(3, true);

        } else {

            FunctionHandler.getInstance().getMainLineChart().setVisible(0, true);
            FunctionHandler.getInstance().getMainLineChart().setVisible(1, false);
            FunctionHandler.getInstance().getMainLineChart().setVisible(2, false);
            FunctionHandler.getInstance().getMainLineChart().setVisible(3, false);

        }

        FunctionHandler.getInstance().getMainLineChart().removeAllMarkers();
        if(Type == MeasureType.MEASURE_TYPE.INTERFERENCE_HUNTING) {
            FunctionHandler.getInstance().getMainLineChart().setEnabledLimitLine(true);
            FunctionHandler.getInstance().getMainLineChart().setEnabledLimitMsg(false);
            FunctionHandler.getInstance().getMainLineChart().setLabel("");
            FunctionHandler.getInstance().getMainLineChart().setEnabledLimitAlarm(false);
            FunctionHandler.getInstance().getMainLineChart().setLimitValue(
                    (float)getInterferenceHuntingData().getThreshold()
            );
        } else {
            FunctionHandler.getInstance().getMainLineChart().removeLimitLine();
        }

        /*Test code*/
//
//        FunctionHandler.getInstance().getMainLineChart().setEnabledLimitLine(true);
//        FunctionHandler.getInstance().getMainLineChart().setEnabledLimitMsg(false);
//        FunctionHandler.getInstance().getMainLineChart().setLabel("");
//        FunctionHandler.getInstance().getMainLineChart().setEnabledLimitAlarm(false);

        /*end test code*/

        FunctionHandler.getInstance().getMainLineChart().removeBlueZone();

        FunctionHandler.getInstance().getMainLineChart().updateMarkerPos();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            //@@ [21.12.30] SEM Default setup fix
            SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
            //
            FunctionHandler.getInstance().getMainLineChart().updateInterferenceHuntingBox();
            FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
            FunctionHandler.getInstance().getMainLineChart().updateSemBox();
            FunctionHandler.getInstance().getMainLineChart().updateOccBox();
            FunctionHandler.getInstance().getMainLineChart().updateChannelBox();
            FunctionHandler.getInstance().getMainLineChart().updateSpuriousEmissionLine();
            FunctionHandler.getInstance().getMainLineChart().invalidate();

        }, 500);


        ViewHandler.getInstance().getContent().update();
        ViewHandler.getInstance().getBW().update();
        ViewHandler.getInstance().getSweepView().update();
        ViewHandler.getInstance().getTraceMode().update();
        ViewHandler.getInstance().getTraceType().update();
        ViewHandler.getInstance().getTrace().update();
        ViewHandler.getInstance().getGateView().update();
        ViewHandler.getInstance().getSaSweepView().update();
        ViewHandler.getInstance().getMeasSetupSweptSa().update();
        ViewHandler.getInstance().getMeasSetupChannelPower().update();
        ViewHandler.getInstance().getMeasSetupOccupiedBW().update();
        ViewHandler.getInstance().getMeasSetupAclrView().update();
//        ViewHandler.getInstance().getSaMeas().addMenu();

    }

    public String getSaParameter() {

        FunctionHandler.getInstance().getDataConnector().setReady(true);

        StringBuffer bufferStr = new StringBuffer();
        bufferStr.append(FunctionHandler.getInstance().getMeasureMode().getMode().getHexString());
        bufferStr.append(" ");
        bufferStr.append(FunctionHandler.getInstance().getMeasureType().getType().getHexString());
        bufferStr.append(" ");

        Double centerFreq = null;
        centerFreq = getFrequencyData().getCenterFreq();
        Long center = (long) Math.round((centerFreq * 1000000d));
        bufferStr.append(center);

        bufferStr.append(" ");
        bufferStr.append((long) Math.round((getFrequencyData().getSpan() * 1000000d)));
        bufferStr.append(" ");
        bufferStr.append(getBwData().getRBW().getCmdValue());
        bufferStr.append(" ");
        bufferStr.append(getBwData().getVBW().getCmdValue());
        bufferStr.append(" ");
        bufferStr.append(getAmplitudeData().getAmpMode().getValue());
        bufferStr.append(" ");
        bufferStr.append(getAmplitudeData().getAttenuator());
        bufferStr.append(" ");
        bufferStr.append(getAmplitudeData().getPreampMode().getValue());
        bufferStr.append(" ");
        bufferStr.append((int) (getAmplitudeData().getOffset() * 100f));
        bufferStr.append(" ");

        for (int i = 0; i < 4; i++) {
            bufferStr.append(getTraceData().getMode(i).getValue());
//            InitActivity.logMsg("SaConfig", "Mode : " + getTraceData().getMode().getString() + " : " + getTraceData().getMode().getValue() + " i : " + i);
            bufferStr.append(" ");
            bufferStr.append(getTraceData().getType(i).getValue());
//            InitActivity.logMsg("SaConfig", "Type : " + getTraceData().getType().getString() + " : " + getTraceData().getType().getValue() + " i : " + i);
            bufferStr.append(" ");
            bufferStr.append(getTraceData().getDetector(i).getValue());
//            InitActivity.logMsg("SaConfig", "Detector : " + getTraceData().getDetector().getString() + " : " + getTraceData().getDetector().getValue() + " i : " + i);
            bufferStr.append(" ");

            InitActivity.logMsg("SaConfig", "==========================================================================================");

//        0x04 0x00 190000 300000
//        12 12
//        0 20 0 0
//
//        0 0 0
//        0 0 0
//        0 0 0
//        0 0 0
//
//        1 5000
//        0 1000
//        0 800 1 1 100 0 0 0
        }

        SaSweepTimeData saSweepTimeData = getSweepTimeData();
        int gateTypeVal = saSweepTimeData.getGateData().getGateType().getValue();

        //Sweep Time Mode       0 : Manual 1 : Auto
        bufferStr.append(saSweepTimeData.getSweepTimeMode().getValue());
        bufferStr.append(" ");
        //Sweep Time            100 ~ 1600000000 us
        bufferStr.append(saSweepTimeData.getSweepTime());
        bufferStr.append(" ");
        //Gate Mode             0 : Off 1 : On
        bufferStr.append(saSweepTimeData.getGateData().getGateMode().getValue());
        bufferStr.append(" ");
        //Gate View             0 : Off 1 : On
        bufferStr.append(saSweepTimeData.getGateData().getGateView().getValue());
        bufferStr.append(" ");
        //Gate View Sweep Time  1|5|10|15|20 ms     unit : us. 기본값 : 10ms
        bufferStr.append(saSweepTimeData.getGateData().getGateViewSweepTime());
        bufferStr.append(" ");
        //Gate Number           Range : 1, 2, 4, 5, 8   Default : 1
        bufferStr.append(saSweepTimeData.getGateData().getGateNum());
        bufferStr.append(" ");
        //Gate Type             0 : Time, 1 : Symbol
        bufferStr.append(saSweepTimeData.getGateData().getGateType().getValue());
        bufferStr.append(" ");
        //Gate Delta            Gate View Sweep Time / Gate Number          Unit : us
        bufferStr.append(saSweepTimeData.getGateData().getGateDelta());
        bufferStr.append(" ");

        if (gateTypeVal == SaGateData.GATE_TYPE.Time.getValue()) {
            //Gate Delay            0us ~ Gate View Sweep Time / Gate Number - Gate Length             단위 : us
            bufferStr.append(saSweepTimeData.getGateData().getGateDelay());
            bufferStr.append(" ");
            //Gate Length           5us ~  Gate View Sweep Time / Gate Number - Gate Delay             단위 : us
            bufferStr.append(saSweepTimeData.getGateData().getGateLength());
            bufferStr.append(" ");
        } else {
            //Gate Delay            0 ~ 28 * Gate View Sweep time - 1             단위 : us
            bufferStr.append(saSweepTimeData.getGateData().getGateDelaySymbol());
            bufferStr.append(" ");
            //Gate Length           Fixed 1 symbol             단위 : us
            bufferStr.append(saSweepTimeData.getGateData().getGateLengthSymbol());
            bufferStr.append(" ");
        }
//        //Gate Delay Slot       0 ~ 20 / Gate Number    Gate Type이 ‘1(Slot)’ 인 경우 FW에서 사용되는 변수. ‘20’은 국내 서비스 조건. 해외를 위해 추후 일반화 예정
//        bufferStr.append(saSweepTimeData.getGateData().getGateDelaySlot());
//        bufferStr.append(" ");
//        //Gate Delay Symbol     0 ~ 14                  Gate Type이 ‘1(Slot)’ 인 경우 FW에서 사용되는 변수
//        bufferStr.append(saSweepTimeData.getGateData().getGateDelaySymbol());
//        bufferStr.append(" ");
//        //Gate Length Slot      0 ~ 20 / Gate Number    Gate Type이 ‘1(Slot)’ 인 경우 FW에서 사용되는 변수
//        bufferStr.append(saSweepTimeData.getGateData().getGateLengthSlot());
//        bufferStr.append(" ");
//        //Gate Length Symbol    0 ~ 14                  Gate Type이 ‘1(Slot)’ 인 경우 FW에서 사용되는 변수
//        bufferStr.append(saSweepTimeData.getGateData().getGateLengthSymbol());
//        bufferStr.append(" ");
        //Gate Source           0 : Internal, 1 : GPS, 2 : SSB, 3 : Ext.1pps
        bufferStr.append(saSweepTimeData.getGateData().getGateSource().getValue());
        bufferStr.append(" ");

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        switch (type) {

            case SWEPT_SA:
                //Measure Setup for Swept SA
                bufferStr.append(getSweptSaMeasSetupData().getAvgMode().getValue());
                bufferStr.append(" ");
                bufferStr.append(getSweptSaMeasSetupData().getAvgHold());
                bufferStr.append(" ");
                bufferStr.append("0");
                bufferStr.append(" ");
                bufferStr.append("0");
                bufferStr.append(" ");
                bufferStr.append("0");
                break;

            case CHANNEL_POWER:
                //Measure Setup for Channel Power
                bufferStr.append(getChannelPowerMeasSetupData().getAvgMode().getValue()); // 8bit
                bufferStr.append(" ");
                bufferStr.append(getChannelPowerMeasSetupData().getAvgHold()); // 8bit
                bufferStr.append(" ");
                bufferStr.append("0"); // 16bit
                bufferStr.append(" ");
                bufferStr.append("0"); // 16bit
                bufferStr.append(" ");
                bufferStr.append(getChannelPowerMeasSetupData().getIntegHzVal()); // 32bit
                break;

            case OCCUPIED_BW:
                //Measure Setup for Occupied BW
                bufferStr.append(getSweptSaMeasSetupData().getAvgMode().getValue()); // 8bit
                bufferStr.append(" ");
                bufferStr.append(getSweptSaMeasSetupData().getAvgHold()); // 8bit
                bufferStr.append(" ");
                bufferStr.append((int) (getOccupiedBwMeasSetupData().getOBWPower() * 100)); // 16bit
                bufferStr.append(" ");
                bufferStr.append((int) (getOccupiedBwMeasSetupData().getXDB() * 100)); // 16bit
                bufferStr.append(" ");
                bufferStr.append("0"); // 32bit
                break;

            case ACLR:

                bufferStr.append(getAclrMeasSetupData().getAvgMode().getValue());
                bufferStr.append(" ");
                bufferStr.append(getAclrMeasSetupData().getAvgHold());
                bufferStr.append(" ");
                bufferStr.append(getAclrMeasSetupData().getCarrierSetupData().getCarriers());
                bufferStr.append(" ");
                bufferStr.append(getAclrMeasSetupData().getCarrierSetupData().getRefCarrier());
                bufferStr.append(" ");
                bufferStr.append(getAclrMeasSetupData().getCarrierSetupData().getCarrierSpacingToHz());
                bufferStr.append(" ");
                bufferStr.append(getAclrMeasSetupData().getCarrierSetupData().getIntegBwToHz());
                bufferStr.append(" ");
                bufferStr.append(getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset());

                for (int i = 0; i < getAclrMeasSetupData().getOffsetSetupData().MAX_NUM_OF_OFFSET; i++) {
                    bufferStr.append(" ");
                    bufferStr.append(getAclrMeasSetupData().getOffsetSetupData().getOffsetSpacingToHz(i));
                    bufferStr.append(" ");
                    bufferStr.append(getAclrMeasSetupData().getOffsetSetupData().getIntegBwToHz(i));
                    bufferStr.append(" ");
                    bufferStr.append(getAclrMeasSetupData().getOffsetSetupData().getOffsetSide(i).getValue());
                    bufferStr.append(" ");
                    bufferStr.append(getAclrMeasSetupData().getOffsetSetupData().getFailSource(i).getValue());
                    bufferStr.append(" ");
                    bufferStr.append((int) (getAclrMeasSetupData().getOffsetSetupData().getAbsLimit(i) * 100f));
                    bufferStr.append(" ");
                    bufferStr.append((int) (getAclrMeasSetupData().getOffsetSetupData().getRelLimit(i) * 100f));
                }
                break;

            case SEM:
                //org
                /*bufferStr.append(getSemMeasSetupData().getAvgMode().getValue());
                bufferStr.append(" ");
                bufferStr.append(getSemMeasSetupData().getAvgHold());
                bufferStr.append(" ");
                bufferStr.append(getTraceData().getSemTraceMode().getValue());
                bufferStr.append(" ");
                bufferStr.append(getTraceData().getSemTraceType().getValue());
                bufferStr.append(" ");
                bufferStr.append(getTraceData().getSemChannelDetector().getValue());
                bufferStr.append(" ");
                bufferStr.append(getTraceData().getSemOffsetDetector().getValue());
                bufferStr.append(" ");
                bufferStr.append(getSemMeasSetupData().getSemMeasType().getValue());
                bufferStr.append(" ");
                bufferStr.append((long) (getSemMeasSetupData().getRefChannelData().getSpan() * 1000d) * 1000);
                bufferStr.append(" ");
                bufferStr.append((long) (getSemMeasSetupData().getRefChannelData().getIntegBw() * 1000f) * 1000);
                bufferStr.append(" ");
                bufferStr.append(getSemMeasSetupData().getRefChannelData().getBwData().getRBW().getCmdValue());
                bufferStr.append(" ");
                bufferStr.append(getSemMeasSetupData().getRefChannelData().getBwData().getVBW().getCmdValue());*/
                //org

                //avg/hold mode
                bufferStr.append(getSemMeasSetupData().getAvgMode().getValue());
                bufferStr.append(" ");
                //avg number
                bufferStr.append(getSemMeasSetupData().getAvgHold());
                bufferStr.append(" ");
                //sem meas type
                bufferStr.append(getSemMeasSetupData().getSemMeasType().getValue());
                bufferStr.append(" ");
                //sem trace mode
                bufferStr.append(getTraceData().getSemTraceMode().getValue());
                bufferStr.append(" ");
                //sem trace type
                bufferStr.append(getTraceData().getSemTraceType().getValue());
                bufferStr.append(" ");
                //sem channel detector
                bufferStr.append(getTraceData().getSemChannelDetector().getValue());
                bufferStr.append(" ");
                //sem offset detector
                bufferStr.append(getTraceData().getSemOffsetDetector().getValue());
                bufferStr.append(" ");
                //ref.channel span
                bufferStr.append((long) (getSemMeasSetupData().getRefChannelData().getSpan() * 1000d) * 1000);
                bufferStr.append(" ");
                //ref.channel integ.bw
                bufferStr.append((long) (getSemMeasSetupData().getRefChannelData().getIntegBw() * 1000f) * 1000);
                bufferStr.append(" ");
                //ref.channel rbw
                bufferStr.append(getSemMeasSetupData().getRefChannelData().getBwData().getRBW().getCmdValue());
                bufferStr.append(" ");
                //ref.channel vbw
                bufferStr.append(getSemMeasSetupData().getRefChannelData().getBwData().getVBW().getCmdValue());

                for (int i = 0; i < 4; i++) {

                    bufferStr.append(" ");
                    //edit mask index(off/ on)
                    bufferStr.append(getSemMeasSetupData().getEditMaskData().getMaskOnOff(i).getValue());
                    bufferStr.append(" ");
                    //edit mask start freq
                    bufferStr.append((long) (getSemMeasSetupData().getEditMaskData().getStartFreq(i) * 1000d) * 1000);
                    bufferStr.append(" ");
                    //edit mask stop freq
                    bufferStr.append((long) (getSemMeasSetupData().getEditMaskData().getStopFreq(i) * 1000d) * 1000);
                    bufferStr.append(" ");
                    //edit mask side
                    bufferStr.append(getSemMeasSetupData().getEditMaskData().getMaskSide(i).getValue());
                    bufferStr.append(" ");
                    //edit mask rbw
                    bufferStr.append(getSemMeasSetupData().getEditMaskData().getBwData(i).getRBW().getCmdValue());
                    bufferStr.append(" ");
                    //edit mask vbw
                    bufferStr.append(getSemMeasSetupData().getEditMaskData().getBwData(i).getVBW().getCmdValue());
                    bufferStr.append(" ");
                    //edit mask abs start limit
                    bufferStr.append((int) (getSemMeasSetupData().getEditMaskData().getAbsStartLimit(i) * 100f));
                    bufferStr.append(" ");
                    //edit mask abs stop limit
                    bufferStr.append((int) (getSemMeasSetupData().getEditMaskData().getAbsStopLimit(i) * 100f));
                    bufferStr.append(" ");
                    //edit mask rel start limit
                    bufferStr.append((int) (getSemMeasSetupData().getEditMaskData().getRelStartLimit(i) * 100f));
                    bufferStr.append(" ");
                    //edit mask rel stop limit
                    bufferStr.append((int) (getSemMeasSetupData().getEditMaskData().getRelStopLimit(i) * 100f));
                    bufferStr.append(" ");
                    //edit mask fail source
                    bufferStr.append(getSemMeasSetupData().getEditMaskData().getFailSource(i).getValue());

                }

                break;
            case SPURIOUS_EMISSION:
                break;
            case TRANSMIT_MASK:
                bufferStr.append((int) (getTransmitOnOffData().getRampUpStartLev() * 100f));
                bufferStr.append(" ");
                bufferStr.append((int) (getTransmitOnOffData().getRampUpStopLev() * 100f));
                bufferStr.append(" ");
                bufferStr.append((int) (getTransmitOnOffData().getRampDownStartLev() * 100f));
                bufferStr.append(" ");
                bufferStr.append((int) (getTransmitOnOffData().getRampDownStopLev() * 100f));
                bufferStr.append(" ");
                bufferStr.append((int) (getTransmitOnOffData().getLimitOffPower() * 100f));
                bufferStr.append(" ");
                bufferStr.append((int) (getTransmitOnOffData().getLimitRampUpTime() * 100f));
                bufferStr.append(" ");
                bufferStr.append((int) (getTransmitOnOffData().getLimitRampDownTime() * 100f));
                break;

            case INTERFERENCE_HUNTING:
                bufferStr.append(getInterferenceHuntingData().getAvgMode().getValue());
                bufferStr.append(" ");
                bufferStr.append(getInterferenceHuntingData().getAvgHold());
                bufferStr.append(" ");
                bufferStr.append(getInterferenceHuntingData().getUplinkBandValue());
                bufferStr.append(" ");
                bufferStr.append(Math.round(getInterferenceHuntingData().getThreshold() * 100));
                break;

        }

        String cmd = bufferStr.toString();

//        TRACE_TYPE traceType = getTraceData().getType();

//        if(traceType != TRACE_TYPE.VIEW || traceType != TRACE_TYPE.BLANK)
//            FunctionHandler.getInstance().getMainLineChart().clearAllValues();

        InitActivity.logMsg("SA Parameter", cmd);

        return cmd;

    }

//    public String getCalParameter() {
//
//        StringBuffer bufferStr = new StringBuffer();
//        bufferStr.append(MeasureMode.MEASURE_MODE.IQ_LMB_CAL.getHexString());
//        bufferStr.append(" ");
//        bufferStr.append(MeasureType.MEASURE_TYPE.SWEPT_SA.getHexString());
//        bufferStr.append(" ");
//        bufferStr.append((int)(getFrequencyData().getCenterFreq() * 100));
//        bufferStr.append(" ");
//        bufferStr.append((int)(getFrequencyData().getSpan() * 100));
//        bufferStr.append(" ");
//        bufferStr.append(getBwData().getRBW().getCmdValue());
//        bufferStr.append(" ");
//        bufferStr.append(getBwData().getVBW().getCmdValue());
//        bufferStr.append(" ");
//        bufferStr.append(SaAmplitudeData.AMP_MODE.MANUAL.getValue());
//        bufferStr.append(" ");
//        bufferStr.append(getAmplitudeData().getAttenuator());
//        bufferStr.append(" ");
//        bufferStr.append(getAmplitudeData().getPreampMode().getValue());
//        bufferStr.append(" ");
//        bufferStr.append((int)(getAmplitudeData().getOffset() * 100f));
//        bufferStr.append(" ");
//
//        bufferStr.append(TRACE_MODE.CLEAR_WRITE.getValue());
//        bufferStr.append(" ");
//        bufferStr.append(TRACE_TYPE.UPDATE.getValue());
//        bufferStr.append(" ");
//        bufferStr.append(DETECTOR.PEAK.getValue());
//        bufferStr.append(" ");
//
//        bufferStr.append(TRACE_MODE.CLEAR_WRITE.getValue());
//        bufferStr.append(" ");
//        bufferStr.append(TRACE_TYPE.BLANK.getValue());
//        bufferStr.append(" ");
//        bufferStr.append(DETECTOR.PEAK.getValue());
//        bufferStr.append(" ");
//
//        bufferStr.append(TRACE_MODE.CLEAR_WRITE.getValue());
//        bufferStr.append(" ");
//        bufferStr.append(TRACE_TYPE.BLANK.getValue());
//        bufferStr.append(" ");
//        bufferStr.append(DETECTOR.PEAK.getValue());
//        bufferStr.append(" ");
//
//        bufferStr.append(TRACE_MODE.CLEAR_WRITE.getValue());
//        bufferStr.append(" ");
//        bufferStr.append(TRACE_TYPE.BLANK.getValue());
//        bufferStr.append(" ");
//        bufferStr.append(DETECTOR.PEAK.getValue());
//        bufferStr.append(" ");
//
//        bufferStr.append(SaSweepTimeData.SWEEP_TIME_MODE.AUTO.getValue());
//        bufferStr.append(" ");
//        bufferStr.append(SaGateData.GATE_MODE.OFF.getValue());
//        bufferStr.append(" ");
//        bufferStr.append(MeasSetupData.AVG_HOLD_MODE.ON.getValue());
//        bufferStr.append(" ");
//        bufferStr.append(getChannelPowerMeasSetupData().getAvgHold());
//        bufferStr.append(" ");
//
//        String cmd = bufferStr.toString();
//
//        InitActivity.logMsg("SA Parameter", cmd);
//
//        return cmd;
//
//    }

}

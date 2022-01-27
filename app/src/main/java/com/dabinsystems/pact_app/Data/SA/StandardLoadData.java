package com.dabinsystems.pact_app.Data.SA;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.FailSourceData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SemMeasTypeData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.DETECTOR;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.TRACE_MODE;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemRefChannelData;
import com.dabinsystems.pact_app.Dialog.PresetDialog;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;

import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.HZ300;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.KHZ1;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.KHZ10;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.KHZ100;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.KHZ30;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.KHZ300;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.MHZ1;
import static com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SideData.SIDE_OPTION.BOTH;
import static com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData.MASK_ON_OFF.OFF;
import static com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData.MASK_ON_OFF.ON;

public class StandardLoadData {

    public void loadClockData() {
        loadClockData(10d);
    }

    public void loadClockData(double freq) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();
        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(1d);
            data.getFrequencyData().setCenterFreq(freq);
            data.getBwData().setRBW(KHZ1);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);

        }

        data.loadData();

    }

    public void loadIF75MHzData() {
        loadIFData(75d);
    }

    public void loadIF130MHzData() {
        loadIFData(130d);
    }

    public void loadIFData(double freq) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();
        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(30d);
            data.getFrequencyData().setCenterFreq(freq);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        }

        data.loadData();

    }

    public void loadWcdmaData() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();
        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(7.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (5 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(7.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (5 * 1000 * 1000));
            //org

            //@@ [21.12.17] Integ BW 설정값 수정
            data.getChannelPowerMeasSetupData().setIngegBW((long) (3.84 * 1000 * 1000));
            //@@

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(10d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ300);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(25d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(3.84d);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(5d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(3.84d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.RELATIVE, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(50f, i);
            }

            data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, 0);
            data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-49.2f, 1);

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(5d);
            refChannelData.setIntegBw(3.84f);
            refChannelData.getBwData().setRBW(KHZ30);
            refChannelData.getBwData().setVBW(KHZ30);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

            /*
             * mask index 0(1)
             * */

            editMaskData.setMaskOnOff(ON, 0);

            editMaskData.setStartFreq(0.015d, 0);
            editMaskData.setStopFreq(0.215d, 0);

            editMaskData.setMaskSide(BOTH, 0);

            editMaskData.getBwData(0).setRBW(KHZ30);
            editMaskData.getBwData(0).setVBW(HZ300);

            editMaskData.setAbsStartLimit(-12.5f, 0);
            editMaskData.setAbsStopLimit(-12.5f, 0);

            editMaskData.setRelStartLimit(-30f, 0);
            editMaskData.setRelStopLimit(-30f, 0);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 0);

            /*
             * mask index 1(2)
             * */

            editMaskData.setMaskOnOff(ON, 1);

            editMaskData.setStartFreq(0.215d, 1);
            editMaskData.setStopFreq(1.015d, 1);

            editMaskData.setMaskSide(BOTH, 1);

            editMaskData.getBwData(1).setRBW(KHZ30);
            editMaskData.getBwData(1).setVBW(HZ300);

            editMaskData.setAbsStartLimit(-12.5f, 1);
            editMaskData.setAbsStopLimit(-24.5f, 1);

            editMaskData.setRelStartLimit(-30f, 1);
            editMaskData.setRelStopLimit(-30f, 1);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 1);

            /*
             * mask index 2(3)
             * */

            editMaskData.setMaskOnOff(ON, 2);

            editMaskData.setStartFreq(1.015d, 2);
            editMaskData.setStopFreq(1.5d, 2);

            editMaskData.setMaskSide(BOTH, 2);

            editMaskData.getBwData(2).setRBW(KHZ30);
            editMaskData.getBwData(2).setVBW(HZ300);

            editMaskData.setAbsStartLimit(-24.5f, 2);
            editMaskData.setAbsStopLimit(-24.5f, 2);

            editMaskData.setRelStartLimit(-30f, 2);
            editMaskData.setRelStopLimit(-30f, 2);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 2);

            /*
             * mask index 3(4)
             * */

            editMaskData.setMaskOnOff(ON, 3);

            editMaskData.setStartFreq(1.5d, 3);
            editMaskData.setStopFreq(5.5d, 3);

            editMaskData.setMaskSide(BOTH, 3);

            editMaskData.getBwData(3).setRBW(MHZ1);
            editMaskData.getBwData(3).setVBW(KHZ10);

            editMaskData.setAbsStartLimit(-11.5f, 3);
            editMaskData.setAbsStopLimit(-11.5f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

        }

        data.loadData();

    }

    public void load1d4MHzDataForLte() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(2.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (1.4 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(2.5d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ300);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (1.4 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (1.095 * 1000 * 1000));
            //@@

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(10d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(7d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(1.095d);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);
                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(1.4d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(1.095d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-14.61f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, i);

            }


        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(1.4);
            refChannelData.setIntegBw(1.095f);
            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

            /*
             * mask index 0(1)
             * */

            editMaskData.setMaskOnOff(ON, 0);

            editMaskData.setStartFreq(0.05d, 0);
            editMaskData.setStopFreq(1.45d, 0);

            editMaskData.setMaskSide(BOTH, 0);

            editMaskData.getBwData(0).setRBW(KHZ100);
            editMaskData.getBwData(0).setVBW(KHZ1);

            editMaskData.setAbsStartLimit(0.5f, 0);
            editMaskData.setAbsStopLimit(-9.5f, 0);

            editMaskData.setRelStartLimit(-30f, 0);
            editMaskData.setRelStopLimit(-30f, 0);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 0);

            /*
             * mask index 1(2)
             * */

            editMaskData.setMaskOnOff(ON, 1);

            editMaskData.setStartFreq(1.45d, 1);
            editMaskData.setStopFreq(2.85d, 1);

            editMaskData.setMaskSide(BOTH, 1);

            editMaskData.getBwData(1).setRBW(KHZ100);
            editMaskData.getBwData(1).setVBW(KHZ1);

            editMaskData.setAbsStartLimit(-9.5f, 1);
            editMaskData.setAbsStopLimit(-9.5f, 1);

            editMaskData.setRelStartLimit(-30f, 1);
            editMaskData.setRelStopLimit(-30f, 1);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 1);

            /*
             * mask index 2(3)
             * */

            editMaskData.setMaskOnOff(ON, 2);

            editMaskData.setStartFreq(3.3d, 2);
            editMaskData.setStopFreq(15d, 2);

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

            editMaskData.setStartFreq(3.3d, 3);
            editMaskData.setStopFreq(15d, 3);

            editMaskData.setMaskSide(BOTH, 3);

            editMaskData.getBwData(3).setRBW(MHZ1);
            editMaskData.getBwData(3).setVBW(KHZ10);

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

        }

        data.loadData();

    }

    public void load3MHzDataForLte() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(4.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (3 * 1000 * 1000));


        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(4.5d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ300);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);


            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (3 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (2.715 * 1000 * 1000));
            //@@
            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(10d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(15d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);


            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(2.715d);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(3d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(2.715d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-10.66f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, i);

            }


        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(3d);
            refChannelData.setIntegBw(2.715f);
            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

            /*
             * mask index 0(1)
             * */

            editMaskData.setMaskOnOff(ON, 0);

            editMaskData.setStartFreq(0.05d, 0);
            editMaskData.setStopFreq(3.05d, 0);

            editMaskData.setMaskSide(BOTH, 0);

            editMaskData.getBwData(0).setRBW(KHZ100);
            editMaskData.getBwData(0).setVBW(KHZ1);

            editMaskData.setAbsStartLimit(-3.5f, 0);
            editMaskData.setAbsStopLimit(-13.5f, 0);

            editMaskData.setRelStartLimit(-30f, 0);
            editMaskData.setRelStopLimit(-30f, 0);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 0);

            /*
             * mask index 1(2)
             * */

            editMaskData.setMaskOnOff(ON, 1);

            editMaskData.setStartFreq(3.05d, 1);
            editMaskData.setStopFreq(6.05d, 1);

            editMaskData.setMaskSide(BOTH, 1);

            editMaskData.getBwData(1).setRBW(KHZ100);
            editMaskData.getBwData(1).setVBW(KHZ1);

            editMaskData.setAbsStartLimit(-13.5f, 1);
            editMaskData.setAbsStopLimit(-13.5f, 1);

            editMaskData.setRelStartLimit(-30f, 1);
            editMaskData.setRelStopLimit(-30f, 1);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 1);

            /*
             * mask index 2(3)
             * */

            editMaskData.setMaskOnOff(ON, 2);

            editMaskData.setStartFreq(6.6d, 2);
            editMaskData.setStopFreq(15d, 2);

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

            editMaskData.setStartFreq(6.5d, 3);
            editMaskData.setStopFreq(15d, 3);

            editMaskData.setMaskSide(BOTH, 3);

            editMaskData.getBwData(3).setRBW(MHZ1);
            editMaskData.getBwData(3).setVBW(KHZ10);

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

        }

        data.loadData();

    }

    public void load5MHzDataForLte() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(7.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (5 * 1000 * 1000));


        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(7.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (5 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (4.515 * 1000 * 1000));
            //@@


            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(10d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(25d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(4.515d);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(5d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(4.515d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-8.45f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, i);

            }


        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(5d);
            refChannelData.setIntegBw(4.515f);
            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

            /*
             * mask index 0(1)
             * */

            editMaskData.setMaskOnOff(ON, 0);

            editMaskData.setStartFreq(0.05d, 0);
            editMaskData.setStopFreq(5.05d, 0);

            editMaskData.setMaskSide(BOTH, 0);

            editMaskData.getBwData(0).setRBW(KHZ100);
            editMaskData.getBwData(0).setVBW(KHZ1);

            editMaskData.setAbsStartLimit(-5.5f, 0);
            editMaskData.setAbsStopLimit(-12.5f, 0);

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

            editMaskData.setAbsStartLimit(-12.5f, 1);
            editMaskData.setAbsStopLimit(-12.5f, 1);

            editMaskData.setRelStartLimit(-30f, 1);
            editMaskData.setRelStopLimit(-30f, 1);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 1);

            /*
             * mask index 2(3)
             * */

            editMaskData.setMaskOnOff(ON, 2);

            editMaskData.setStartFreq(10.5d, 2);
            editMaskData.setStopFreq(15d, 2);

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

            editMaskData.setStartFreq(10.5d, 3);
            editMaskData.setStopFreq(15d, 3);

            editMaskData.setMaskSide(BOTH, 3);

            editMaskData.getBwData(3).setRBW(MHZ1);
            editMaskData.getBwData(3).setVBW(KHZ10);

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

        }

        data.loadData();

    }

    public void load10MHzDataForLte() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(15d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (10 * 1000 * 1000));


        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(15d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);


            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (10 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (9.015 * 1000 * 1000));
            //@@



            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(20d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(50d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(9.015d);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(10d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(9.015d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-5.45f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, i);

            }

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(10d);
            refChannelData.setIntegBw(9.015f);
            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

            /*
             * mask index 0(1)
             * */

            editMaskData.setMaskOnOff(ON, 0);

            editMaskData.setStartFreq(0.05d, 0);
            editMaskData.setStopFreq(5.05d, 0);

            editMaskData.setMaskSide(BOTH, 0);

            editMaskData.getBwData(0).setRBW(KHZ100);
            editMaskData.getBwData(0).setVBW(KHZ1);

            editMaskData.setAbsStartLimit(-5.5f, 0);
            editMaskData.setAbsStopLimit(-12.5f, 0);

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

            editMaskData.setAbsStartLimit(-12.5f, 1);
            editMaskData.setAbsStopLimit(-12.5f, 1);

            editMaskData.setRelStartLimit(-30f, 1);
            editMaskData.setRelStopLimit(-30f, 1);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 1);

            /*
             * mask index 2(3)
             * */

            editMaskData.setMaskOnOff(ON, 2);

            editMaskData.setStartFreq(10.5d, 2);
            editMaskData.setStopFreq(15d, 2);

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

            editMaskData.setStartFreq(10.5d, 3);
            editMaskData.setStopFreq(15d, 3);

            editMaskData.setMaskSide(BOTH, 3);

            editMaskData.getBwData(3).setRBW(MHZ1);
            editMaskData.getBwData(3).setVBW(KHZ10);

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

        }

        data.loadData();

    }

    public void load15MHzDataForLte() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(22.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (15 * 1000 * 1000));


        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(22.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (15 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (13.515 * 1000 * 1000));
            //@@



            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(30d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(75d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(13.515d);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(15d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(13.515d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-3.69f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, i);

            }


        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(15d);
            refChannelData.setIntegBw(13.515f);
            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

            /*
             * mask index 0(1)
             * */

            editMaskData.setMaskOnOff(ON, 0);

            editMaskData.setStartFreq(0.05d, 0);
            editMaskData.setStopFreq(5.05d, 0);

            editMaskData.setMaskSide(BOTH, 0);

            editMaskData.getBwData(0).setRBW(KHZ100);
            editMaskData.getBwData(0).setVBW(KHZ1);

            editMaskData.setAbsStartLimit(-5.5f, 0);
            editMaskData.setAbsStopLimit(-12.5f, 0);

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

            editMaskData.setAbsStartLimit(-12.5f, 1);
            editMaskData.setAbsStopLimit(-12.5f, 1);

            editMaskData.setRelStartLimit(-30f, 1);
            editMaskData.setRelStopLimit(-30f, 1);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 1);

            /*
             * mask index 2(3)
             * */

            editMaskData.setMaskOnOff(ON, 2);

            editMaskData.setStartFreq(10.5d, 2);
            editMaskData.setStopFreq(15d, 2);

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

            editMaskData.setStartFreq(10.5d, 3);
            editMaskData.setStopFreq(15d, 3);

            editMaskData.setMaskSide(BOTH, 3);

            editMaskData.getBwData(3).setRBW(MHZ1);
            editMaskData.getBwData(3).setVBW(KHZ10);

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

        }

        data.loadData();

    }

    public void load20MHzDataForLte() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(30d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (20 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(30d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);


            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (20 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (18.015 * 1000 * 1000));
            //@@



            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(40d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(100d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(18.015d);

            data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);
            data.getSweepTimeData().getGateData().setGateDelay(0);
            data.getSweepTimeData().getGateData().setGateLength(1 * 1000);
            data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.INTERNAL);
            // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
            data.getSweepTimeData().getGateData().setGateNum(1);
            data.getSweepTimeData().getGateData().setGateDelta(0);
            data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(20d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(18.015d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-2.44f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, i);

            }

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.OFF);
            data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.OFF);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(20d);
            refChannelData.setIntegBw(18.015f);
            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

            /*
             * mask index 0(1)
             * */

            editMaskData.setMaskOnOff(ON, 0);

            editMaskData.setStartFreq(0.05d, 0);
            editMaskData.setStopFreq(5.05d, 0);

            editMaskData.setMaskSide(BOTH, 0);

            editMaskData.getBwData(0).setRBW(KHZ100);
            editMaskData.getBwData(0).setVBW(KHZ1);

            editMaskData.setAbsStartLimit(-5.5f, 0);
            editMaskData.setAbsStopLimit(-12.5f, 0);

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

            editMaskData.setAbsStartLimit(-12.5f, 1);
            editMaskData.setAbsStopLimit(-12.5f, 1);

            editMaskData.setRelStartLimit(-30f, 1);
            editMaskData.setRelStopLimit(-30f, 1);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 1);

            /*
             * mask index 2(3)
             * */

            editMaskData.setMaskOnOff(ON, 2);

            editMaskData.setStartFreq(10.5d, 2);
            editMaskData.setStopFreq(15d, 2);

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

            editMaskData.setStartFreq(10.5d, 3);
            editMaskData.setStopFreq(15d, 3);

            editMaskData.setMaskSide(BOTH, 3);

            editMaskData.getBwData(3).setRBW(MHZ1);
            editMaskData.getBwData(3).setVBW(KHZ10);

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

        }

        data.loadData();

    }

    public void load5MHzDataFor5G(PresetDialog.UL_DL_STATUS status) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(7.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (5 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(7.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (5 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (4.5 * 1000 * 1000));
            //@@




        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(10d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(25d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(4.5d);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(5d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(4.5d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-8.47f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, i);

            }

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(5d);
            refChannelData.setIntegBw(4.5f);

            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

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

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);
        }

        // Gate
        switch (type) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR: {
                data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);

                // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                // 2021-10-19 Time 으로 변경
                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                } else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
            }
            case SEM: {
                //@@ [22.01.03] SEM Standard Setting 추가
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);

                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                }
                else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
                //@@
            }
        }

        data.loadData();
    }

    public void load10MHzDataFor5G(PresetDialog.UL_DL_STATUS status) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(15d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (10 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(15d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (10 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (9.36 * 1000 * 1000));
            //@@



        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(20d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(50d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(9.36d);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(10d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(9.36d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-5.29f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, i);

            }


        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(10d);
            refChannelData.setIntegBw(9.36f);

            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

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

            editMaskData.setRelStartLimit(-15f, 2);
            editMaskData.setRelStopLimit(-15f, 2);

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

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

        }

        // Gate
        switch (type) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR: {
                data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);

                // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                // 2021-10-19 Time 으로 변경
                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                } else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
            }
            case SEM: {
                //@@ [22.01.03] SEM Standard Setting 추가
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);

                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                }
                else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
                //@@
            }
        }

        data.loadData();

    }

    public void load15MHzDataFor5G(PresetDialog.UL_DL_STATUS status) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(22.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(22.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (15 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (14.22 * 1000 * 1000));
            //@@


        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(30d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(75d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(14.22d);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(15d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(14.22d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-3.47f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, i);

            }

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(15d);
            refChannelData.setIntegBw(14.22f);

            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

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

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

        }

        // Gate
        switch (type) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR: {
                data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);

                // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                // 2021-10-19 Time 으로 변경
                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                } else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
            }
            case SEM: {
                //@@ [22.01.03] SEM Standard Setting 추가
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);

                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                }
                else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
                //@@
            }
        }

        data.loadData();

    }

    public void load20MHzDataFor5G(PresetDialog.UL_DL_STATUS status) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(30d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (20 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(30d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (20 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (19.08 * 1000 * 1000));
            //@@



        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(40d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(100d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(19.08d);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(20d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(19.08d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-2.19f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, i);

            }

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(20d);
            refChannelData.setIntegBw(19.08f);

            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

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

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

        }

        // Gate
        switch (type) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR: {
                data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);

                // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                // 2021-10-19 Time 으로 변경
                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                } else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
            }
            case SEM: {
                //@@ [22.01.03] SEM Standard Setting 추가
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);

                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                }
                else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
                //@@
            }
        }

        data.loadData();

    }

    public void load25MHzDataFor5G(PresetDialog.UL_DL_STATUS status) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(37.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (25 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(37.5d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (25 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (23.94 * 1000 * 1000));
            //@@


        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(50d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(125d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(23.94d);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(25d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(23.94d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-1.21f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-43.8f, i);

            }

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(25d);
            refChannelData.setIntegBw(23.94f);

            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

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

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

        }

        // Gate
        switch (type) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR: {
                data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);

                // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                // 2021-10-19 Time 으로 변경
                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                } else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
            }
            case SEM: {
                //@@ [22.01.03] SEM Standard Setting 추가
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);

                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                }
                else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
                //@@
            }
        }

        data.loadData();

    }

    public void load30MHzDataFor5G(PresetDialog.UL_DL_STATUS status) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(45d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (30 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(45d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (30 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (28.8 * 1000 * 1000));
            //@@


        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(60d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(150d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(28.8d);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(30d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(28.8d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-0.41f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-43.8f, i);

            }

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(30d);
            refChannelData.setIntegBw(28.8f);

            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

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

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

        }

        // Gate
        switch (type) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR: {
                data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);

                // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                // 2021-10-19 Time 으로 변경
                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                } else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
            }
            case SEM: {
                //@@ [22.01.03] SEM Standard Setting 추가
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);

                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                }
                else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
                //@@
            }
        }

        data.loadData();

    }

    public void load40MHzDataFor5G(PresetDialog.UL_DL_STATUS status) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(60d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (40 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(60d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (40 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (38.88 * 1000 * 1000));
            //@@


        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(80d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(200d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(38.88d);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(40d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(38.88d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(0.9f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-43.8f, i);

            }


        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(40d);
            refChannelData.setIntegBw(38.88f);

            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

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

            editMaskData.setAbsStartLimit(-15f, 3);
            editMaskData.setAbsStopLimit(-15f, 3);

            editMaskData.setRelStartLimit(-30f, 3);
            editMaskData.setRelStopLimit(-30f, 3);

            editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

        }

        // Gate
        switch (type) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR: {
                data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);

                // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                // 2021-10-19 Time 으로 변경
                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                } else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
            }
            case SEM: {
                //@@ [22.01.03] SEM Standard Setting 추가
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);

                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                }
                else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
                //@@
            }
        }

        data.loadData();

    }

    public void load50MHzDataFor5G(PresetDialog.UL_DL_STATUS status) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(75d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (50 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(75d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (50 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (48.6 * 1000 * 1000));
            //@@


        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(100d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(250d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(48.6d);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(50d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(48.6d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(1.87f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-43.8f, i);

            }

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(50d);
            refChannelData.setIntegBw(48.6f);

            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

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

        }

        // Gate
        switch (type) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR: {
                data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);

                // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                // 2021-10-19 Time 으로 변경
                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                } else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
            }
            case SEM: {
                //@@ [22.01.03] SEM Standard Setting 추가
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);

                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                }
                else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
                //@@
            }
        }

        data.loadData();

    }

    public void load60MHzDataFor5G(PresetDialog.UL_DL_STATUS status) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(90d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (60 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(90d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (60 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (58.32 * 1000 * 1000));
            //@@


        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(120d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(300d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(58.32d);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(60d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(58.32d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(2.66f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-43.8f, i);

            }

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(60d);
            refChannelData.setIntegBw(58.32f);

            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

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

        }

        // Gate
        switch (type) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR: {
                data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);

                // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                // 2021-10-19 Time 으로 변경
                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                } else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
            }
            case SEM: {
                //@@ [22.01.03] SEM Standard Setting 추가
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);

                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                }
                else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
                //@@
            }
        }

        data.loadData();

    }

    public void load70MHzDataFor5G(PresetDialog.UL_DL_STATUS status) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(105d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (70 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(105d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (70 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (68.04 * 1000 * 1000));
            //@@


        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(140d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(350d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(68.04d);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(70d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(68.04d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(3.33f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-43.8f, i);

            }

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(70d);
            refChannelData.setIntegBw(68.04f);

            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

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

        }

        // Gate
        switch (type) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR: {
                data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);

                // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                // 2021-10-19 Time 으로 변경
                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                } else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
            }
            case SEM: {
                //@@ [22.01.03] SEM Standard Setting 추가
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);

                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                }
                else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
                //@@
            }
        }

        data.loadData();

    }

    public void load80MHzDataFor5G(PresetDialog.UL_DL_STATUS status) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(120d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (80 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(120d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (80 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (78.12 * 1000 * 1000));
            //@@


        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(160d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(400d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(78.12d);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(80d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(78.12d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(3.93f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-43.8f, i);

            }

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(80d);
            refChannelData.setIntegBw(78.12f);

            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

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

        }

        // Gate
        switch (type) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR: {
                data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);

                // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                // 2021-10-19 Time 으로 변경
                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                } else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
            }
            case SEM: {
                //@@ [22.01.03] SEM Standard Setting 추가
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);

                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                }
                else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
                //@@
            }
        }

        data.loadData();

    }

    public void load90MHzDataFor5G(PresetDialog.UL_DL_STATUS status) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(135d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (90 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(135d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((long) (90 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long) (88.2 * 1000 * 1000));
            //@@


        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(180d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(450d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(88.2d);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(90d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(88.2d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(4.45f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-43.8f, i);

            }

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(90d);
            refChannelData.setIntegBw(88.2f);

            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

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

        }

        // Gate
        switch (type) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR: {
                data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);

                // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                // 2021-10-19 Time 으로 변경
                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                } else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
            }
            case SEM: {
                //@@ [22.01.03] SEM Standard Setting 추가
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);

                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                }
                else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
                //@@
            }
        }

        data.loadData();

    }

    public void load100MHzDataFor5G(PresetDialog.UL_DL_STATUS status) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        SaConfigData data = SaDataHandler.getInstance().getConfigData();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            data.getFrequencyData().setSpan(150d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.KHZ100);
            data.getTraceData().setAllMode(TRACE_MODE.CLEAR_WRITE);

            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.UPDATE, 0);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
            data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

            data.getTraceData().setAllDetector(DETECTOR.NORMAL);

            data.getChannelPowerMeasSetupData().setIngegBW((long) (100 * 1000 * 1000));

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            data.getFrequencyData().setSpan(150d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            //@@ [21.12.17] Integ BW 값 수정
            //org
            //data.getChannelPowerMeasSetupData().setIngegBW((100 * 1000 * 1000));
            //
            data.getChannelPowerMeasSetupData().setIngegBW((long)(98.28 * 1000 * 1000));
            //@@


        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            data.getFrequencyData().setSpan(200d);
            data.getBwData().setRBW(KHZ30);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);
            data.getOccupiedBwMeasSetupData().setOBWPower(99f);

        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

            data.getFrequencyData().setSpan(500d);
            data.getBwData().setRBW(KHZ100);
            data.getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
            data.getTraceData().setAllMode(TRACE_MODE.AVERAGE);
            data.getTraceData().setAllDetector(DETECTOR.RMS);

            data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
            data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
            data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(98.28d);

            data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

            for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                data.getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setOffsetSpacing(100d * (i + 1), i);

                data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(98.28d, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(4.92f, i);
                data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-43.8f, i);

            }

        } else if (type == MeasureType.MEASURE_TYPE.SEM) {

            data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

            SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
            refChannelData.setSpan(100d);
            refChannelData.setIntegBw(98.28f);
            refChannelData.getBwData().setRBW(KHZ100);
            refChannelData.getBwData().setVBW(KHZ100);

            SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

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

        }

        // Gate
        switch (type) {
            case SWEPT_SA:
            case CHANNEL_POWER:
            case OCCUPIED_BW:
            case ACLR: {
                data.getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateViewSweepTime(10 * 1000);

                // [jigum] 2021-07-21 Number Of Gate, Gate Delta, Gate Type 추가
                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                // 2021-10-19 Time 으로 변경
                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                } else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
            }
            case SEM: {
                //@@ [22.01.03] SEM Standard Setting 추가
                data.getSweepTimeData().getGateData().setGateMode(SaGateData.GATE_MODE.ON);
                data.getSweepTimeData().getGateData().setGateView(SaGateData.GATE_MODE.ON);

                data.getSweepTimeData().getGateData().setGateNum(4);
                data.getSweepTimeData().getGateData().setGateDelta(2500);

                data.getSweepTimeData().getGateData().setGateType(SaGateData.GATE_TYPE.Time);

                if (status == PresetDialog.UL_DL_STATUS.DL) {
                    data.getSweepTimeData().getGateData().setGateDelay(0);
                    data.getSweepTimeData().getGateData().setGateLength((int) (1.857 * 1000));
                }
                else if (status == PresetDialog.UL_DL_STATUS.UL) {
                    data.getSweepTimeData().getGateData().setGateLength((int) (0.571 * 1000));
                    data.getSweepTimeData().getGateData().setGateDelay((int) (1.9286 * 1000));
                }

                data.getSweepTimeData().getGateData().setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;
                //@@
            }
        }

        data.loadData();

    }

}

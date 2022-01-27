package com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan;

import android.provider.ContactsContract;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrDuplexData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrSCSData;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;

import java.nio.ByteBuffer;

/**
 * [jigum] 2021-07-22
 *
 * 5G NR Scan 데이터 관리
 *
 */
public class NrScanData {

    public static final int NR_SCAN_CARRIER_COUNT = 4;

    /** 시작 여부 */
    private boolean IsStart = false;
    /** Carriers */
    private NrScanCarrierData CarrierData;
    /** Amp. Offset      Range:-100 ~ +100. Step:0.01    GUI 설정 값 x 100이 FW에 전달됨 */
    private float AmpOffset = 0;
    /** Trace1, Trace2 */
    private NR_SCAN_TRACE Traces[] = {NR_SCAN_TRACE.PCI, NR_SCAN_TRACE.TimingOffset };
    /** Traces scale min */
    private int TraceScaleMin[] = { NR_SCAN_TRACE.PCI.getScaleDefaultMin(), NR_SCAN_TRACE.TimingOffset.getScaleDefaultMin() };
    /** Traces scale max */
    private int TraceScaleMax[] = { NR_SCAN_TRACE.PCI.getScaleDefaultMax(), NR_SCAN_TRACE.TimingOffset.getScaleDefaultMax() };
    /** 선택된 Trace 값 설정 시 사용 */
    private int SelectedTrace = 0;
    /** 로그 파일 저장 여부 */
    private boolean IsSaveLog = true;

    /** 수신 데이터 */
    private NrScanRcvData RcvData;

    /** CVS File 저장 */
    private NrScanCsvFile csvFile;

    /**
     * 수신 받을 데이터 중 마지막 index
     * 마지막 데이터 수신 후 다시 정보 요청을 해야함.
     */
    private int LastIndex;


    public NrScanData() {
        csvFile = new NrScanCsvFile(MainActivity.getContext());
    }

    public boolean isStart() {
        return IsStart;
    }

    public void setStart(boolean start) {
        IsStart = start;
        if (!IsStart) {
            // Stop
            getRcvData().clear();
            closeLog();
        }
    }

    public NrScanCarrierData getCarrierData() {
        if (CarrierData == null) CarrierData = new NrScanCarrierData();
        return CarrierData;
    }

    public float getAmpOffsetMin() {
        return -100;
    }

    public float getAmpOffsetMax() {
        return 100;
    }

    public float getAmpOffset() {
        return AmpOffset;
    }

    public void setAmpOffset(float ampOffset) {
        AmpOffset = ampOffset;
    }

    public boolean isSaveLog() {
        return IsSaveLog;
    }

    public void setSaveLog(boolean saveLog) {
        IsSaveLog = saveLog;

        if (!saveLog) {
            closeLog();
        }
    }

    public void closeLog() {
        csvFile.close();
    }

    /** 설정 명령어 생성 */
    public String getNrScanCmd() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

        StringBuilder bufferStr = new StringBuilder();
        bufferStr.append(mode.getHexString());
        bufferStr.append(" ");
        bufferStr.append(type.getHexString());
        bufferStr.append(" ");

        NrScanRcvData rcvData = getRcvData();

        NrScanCarrierData scdata = getCarrierData();
        for (int i = 0; i < scdata.getNumOfCarrier(); ++i) {
            // Carrier Index 에 대한 On/Off State 정보   0 : Off 1 : On
            bufferStr.append(scdata.getCarrierSetting(i).getValue());
            bufferStr.append(" ");

            //Freq_Of_Carrier        70 ~ 6000 MHz       Unit : Hz
            bufferStr.append(scdata.getFreqOfCarrier(i)); //Math.round(scdata.getFreqOfCarrier(i) * 1000000d));
            bufferStr.append(" ");

            // Profile      0 : 5MHz, 1 : 10MHz, ... 12 : 100 MHz
            bufferStr.append(scdata.getProfile(i).getCmd());
            bufferStr.append(" ");

            if (scdata.getCarrierSetting(i).getValue() == 1) {
                LastIndex = i;
                rcvData.setOn(i, true);
            } else {
                rcvData.setOn(i, false);
            }
        }

        //Amp. Offset       Range : -100 ~ + 100. Step :0.01        GUI 설정 값 x 100이 FW에 전달됨
        bufferStr.append((int) (AmpOffset * 100));

        //@@ [22.01.03] 5G NR Scan Protocol Add (FDD, SCS)
        bufferStr.append(" ");

        // Carrier Duplex Type
        for (int i = 0; i <scdata.getNumOfCarrier(); i++) {

            bufferStr.append(scdata.getDuplexData(i).getDuplexType().getValue());
            bufferStr.append(" ");

        }

        // Carrier SCS Value
        for (int i = 0; i < scdata.getNumOfCarrier(); i++) {

            bufferStr.append(scdata.getScsData(i).getSCS().getCmd());
            bufferStr.append(" ");
        }
        //@@


        String cmd = bufferStr.toString();

        InitActivity.logMsg("SendSettingValues", cmd);

        return cmd;
    }


    public NrScanRcvData getRcvData() {
        if (RcvData == null) RcvData = new NrScanRcvData();
        return RcvData;
    }

    /** 수신 data 저장 */
    public int setRcvData(ByteBuffer bb) {
        NrScanRcvData data = getRcvData();
        int idx = data.setRcvData(bb);

        // csv 저장
        if (IsSaveLog && IsStart && idx == LastIndex) {
            data.rcvAll();
            csvFile.write(data);
        }

        return idx;
    }

    /** 수신 해야 할 마지막 index */
    public int getLastIndex() {
        return LastIndex;
    }

    /** 수신 해야 할 마지막 index 여부? */
    public boolean isLast(int idx) {
        return idx == LastIndex;
    }

    public NR_SCAN_TRACE getTrace(int idx) {
        return Traces[idx];
    }

    public void setTrace(int idx, NR_SCAN_TRACE trace) {
        Traces[idx] = trace;
        TraceScaleMin[idx] = trace.getScaleDefaultMin();
        TraceScaleMax[idx] = trace.getScaleDefaultMax();
    }

    public String getBaseScaleToString(int idx) {
        String scale = "";
        switch (Traces[idx]) {
            case PCI:
                scale = TraceScaleMax[idx] + "";
                break;
            case SS_RSRP:
                scale = TraceScaleMax[idx] + " dBm";
                break;
            case SS_SINR:
                scale = TraceScaleMax[idx] + " dB";
                break;
            case TimingOffset:
                scale = TraceScaleMax[idx] + " μs";
                break;
        }
        return scale;
    }

    public void setBaseScale(int idx, int val) {
        TraceScaleMax[idx] = val;

        switch (Traces[idx]) {
            case PCI:
                break;
            case SS_RSRP:
                TraceScaleMin[idx] = TraceScaleMax[idx] - 100;
                break;
            case SS_SINR:
                TraceScaleMin[idx] = TraceScaleMax[idx] - 60;
                break;
            case TimingOffset:
                TraceScaleMin[idx] = -TraceScaleMax[idx];
                break;
        }
    }

    public int getTraceScaleMin(int idx) {
        return TraceScaleMin[idx];
    }

    public void setTraceScaleMin(int idx, int traceScaleMin) {
        TraceScaleMin[idx] = traceScaleMin;
    }

    public int getTraceScaleMax(int idx) {
        return TraceScaleMax[idx];
    }

    public void setTraceScaleMax(int idx, int traceScaleMax) {
        TraceScaleMax[idx] = traceScaleMax;
    }

    public int getSelectedTrace() {
        return SelectedTrace;
    }

    public void setSelectedTrace(int selectedTrace) {
        SelectedTrace = selectedTrace;
    }
}

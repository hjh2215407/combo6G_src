package com.dabinsystems.pact_app.Handler;

import android.util.Log;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Data.GpsHoldoverData;
import com.dabinsystems.pact_app.Data.LteInfoData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrData;
import com.dabinsystems.pact_app.Data.ModAccuracy.LteFdd.LteFddData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanData;
import com.dabinsystems.pact_app.Data.Nuclear.NuclearData;
import com.dabinsystems.pact_app.Data.SA.StandardLoadData;
import com.dabinsystems.pact_app.Data.StatusData;
import com.dabinsystems.pact_app.Data.SystemData;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DataHandler {

    private static DataHandler mInstance = null;

    private NrData NrData;
    private NrScanData NrScanData;
    private com.dabinsystems.pact_app.Data.ModAccuracy.LteFdd.LteFddData LteFddData;

    private final String RequestMainData = intToHexStr(0x11);

    private final String TempWarningCmd = intToHexStr(0x21);
    private final String TempCmd = intToHexStr(0x22);

    private final String GateDataCmd = intToHexStr(0x23);
    private final String AutoAtten = intToHexStr(0x24);
    private boolean isAutoAtten = false;
    private boolean isSaAutoScale = true;
    private int AutoScaleCount = 0;

    private final String OvfFlagCmd = intToHexStr(0x25);
    private final String SyncFlagCmd = intToHexStr(0x26);

    private final String SweepTimdCmd = intToHexStr(0x30);

    private final String ReadyCmd = intToHexStr(0x200000);

    private final String ResponceForConfigErr = intToHexStr(0x98);
    private final String ResponseForConfig = intToHexStr(0x99);

    private final String RestartConfig = intToHexStr(0x44);
    private final String ForceTurnOff = intToHexStr(0x45);

    private final String ChangeMode = intToHexStr(0x65);
    private final String ChangeToVSWR = intToHexStr(0x00);
    private final String ChangeToSA = intToHexStr(0x01);
    private final String ChangeTo5G = intToHexStr(0x02);
    private final String ChangeToLte = intToHexStr(0x03);

    private final String NrToSaCommand = intToHexStr(0x66);

    private final String ChangeClockSourceCmd = intToHexStr(0x50);
    private final String LockStatusCmd = intToHexStr(0x51);
    private final String GPSInfoRequestCmd = intToHexStr(0x52);
    private final int GPSInfoResponseCmdVal = 0x53;
    //@@ [22.01.10] GPS HoldoverResponseCmdVal
    private final int GPSHoldoverRequestCmdVal = 0x54;
    private final String GPSHoldoverRequestCmd = intToHexStr(0x54);

    private final String RequestTaeMeasure = intToHexStr(0x71);
    private final String ReadyTaeMeasure = intToHexStr(0x72);


    private StandardLoadData StandardLoadData;
    private SystemData SystemData;
    private StatusData StatusData;
    //@@ [22.01.10] GPS Holdover Data
    private GpsHoldoverData HoldoverData;
    //@@

    //@@ [22.01.27] 원자력모니터링 관련
    private NuclearData NuclearData;
    //@@

    //@@ [22.01.04] GPS Holdover Data
    private GpsHoldoverData GpsHoldoverData;
    private LteInfoData LteInfoData;
    //@@

    public static DataHandler getInstance() {
        if (mInstance == null) mInstance = new DataHandler();
        return mInstance;
    }

    public String getRequestTaeMeasure(int curPort) {
        return RequestTaeMeasure + " " + curPort;
    }

    public String getReadyTaeMeasure() {
        return ReadyTaeMeasure;
    }

    public StandardLoadData getStandardLoadData() {
        if (StandardLoadData == null) StandardLoadData = new StandardLoadData();
        return StandardLoadData;
    }

    public NrData getNrData() {
        if (NrData == null) NrData = new NrData();
        return NrData;
    }

    public NrScanData getNrScanData() {
        if (NrScanData == null) NrScanData = new NrScanData();
        return NrScanData;
    }

    public LteFddData getLteFddData() {
        if (LteFddData == null) LteFddData = new LteFddData();
        return LteFddData;
    }

    public com.dabinsystems.pact_app.Data.SystemData getSystemData() {
        if (SystemData == null) SystemData = new SystemData();
        return SystemData;
    }

    //@@ [22.01.04] GPS Holdover option data
    public GpsHoldoverData getGpsHoldoverData() {
        if (GpsHoldoverData == null) GpsHoldoverData = new GpsHoldoverData();
        return GpsHoldoverData;
    }

    public LteInfoData getLteInfoData() {
        if (LteInfoData == null) LteInfoData =new LteInfoData();
        return LteInfoData;
    }

    public GpsHoldoverData getHoldoverData() {
        if (GpsHoldoverData == null) GpsHoldoverData = new GpsHoldoverData();
        return GpsHoldoverData;
    }
    //

    //@@ [22.01.27] 원전 모니터링
    public NuclearData getNuclearData() {
        if (NuclearData == null) NuclearData = new NuclearData();
        return NuclearData;
    }
    //@@

    /* VSWR / DTF / CL / SA 각각 다른 설정값을 적용한 커맨드 반환 */
    public String getConfigCmd() {

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        String cmd = "";

        switch (mode) {
            case VSWR:
            case DTF:
            case CL:
                cmd = VswrDataHandler.getInstance().getVswrParameter();
                break;
            case MOD_ACCURACY:
                if (type == MeasureType.MEASURE_TYPE.NR_5G || type == MeasureType.MEASURE_TYPE.TAE) {
                    cmd = DataHandler.getInstance().getNrData().getNrCmd();
                    InitActivity.logMsg("getConfigCmd", "5G NR");
                } else if (type == MeasureType.MEASURE_TYPE.LTE_FDD) {
                    cmd = DataHandler.getInstance().getLteFddData().getLteFddCmd();
                    InitActivity.logMsg("getConfigCmd", "LTE FDD");
                } else if (type == MeasureType.MEASURE_TYPE.NR_5G_SCAN) {
                    InitActivity.logMsg("getConfigCmd", "5G NR Scan");
                    cmd = DataHandler.getInstance().getNrScanData().getNrScanCmd();
                }
                break;
            case SA:
                cmd = SaDataHandler.getInstance().getConfigData().getSaParameter();
                break;
            case SG:
                break;
        }
        InitActivity.logMsg("cmd : ", cmd);
        return cmd;
    }

    public byte[] intToBytes(Long input, int byteLen, ByteOrder endian) {

        ByteBuffer buffer = ByteBuffer.allocate(8).order(endian);
        buffer.putLong(input);
        buffer.position(0);

        byte[] b = new byte[byteLen];

        if (endian == ByteOrder.LITTLE_ENDIAN) {

            for (int i = 0; i < byteLen; i++) {

                b[i] = buffer.get(i);

            }

        } else {

            for (int i = 0; i < byteLen; i++) {

                b[i] = buffer.get(i + (8 - byteLen));
            }

        }

        return b;

    }

    public String getReadyCmd() {
        return ReadyCmd;
    }

    public String getClockSourceCmd() {
        return ChangeClockSourceCmd;
    }

    public String getChangeClockSourceCmd() {
        return ChangeClockSourceCmd + " " + getSystemData().getSource().getVal();
    }

    public int byteToInteger(byte[] bytes, ByteOrder order) {

        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.order(order);

        buffer.put(bytes);
        buffer.flip();

        int value = buffer.getInt();

        return value;
    }

    public short byteToShort(byte[] bytes, ByteOrder order) {

        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(order);

        buffer.put(bytes);
        buffer.position(0); // OR buffer.flip();

        short value = buffer.getShort();
        InitActivity.logMsg("byteToShort", buffer.position() + "");

        return value;

    }


    public String getRequestMainDataCmd() {
        return RequestMainData;
    }

    public String getGateDataCmd() {
        return GateDataCmd;
    }

    public int getGateDataCmdValue() {
        return 0x23;
    }

    public String getTempCmd() {
        return TempCmd;
    }

    public String getResponseForConfig() {
        return ResponseForConfig;
    }

    public String getNrToSaCmd() {
        return NrToSaCommand;
    }

    public String getOvfFlagCmd() {
        return OvfFlagCmd;
    }

    public String getSyncFlagCmd() {
        return SyncFlagCmd;
    }

    public String getRestartConfig() {
        return RestartConfig;
    }

    private String getChangeMode() {
        return ChangeMode;
    }

    public String getChangeToVSWR() {
        return ChangeMode + " " + ChangeToVSWR;
    }

    public String getChangeToSA() {
        return ChangeMode + " " + ChangeToSA;
    }

    public String getChangeTo5G() {
        return ChangeMode + " " + ChangeTo5G;
    }

    public String getChangeToLte() {
        return ChangeMode + " " + ChangeToLte;
    }

    public String getChangeCommandByMode() {

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        String cmd = getChangeToSA();
        switch (mode) {

            case VSWR:
            case DTF:
            case CL:
                cmd = getChangeToVSWR();
                break;
            case SA:
                cmd = getChangeToSA();
                break;
            case MOD_ACCURACY:
                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
                if (type == MeasureType.MEASURE_TYPE.LTE_FDD)
                    cmd = getChangeToLte();
                else // NR_5G, TAE, NR_5G_SCAN
                    cmd = getChangeTo5G();
//                if (type == MeasureType.MEASURE_TYPE.NR_5G)
//                    cmd = getChangeTo5G();
//                else if (type == MeasureType.MEASURE_TYPE.LTE_FDD)
//                    cmd = getChangeToLte();
                break;
        }

        return cmd;
    }

    public String getTempWarning(int val) {
        if (val == 0) return TempWarningCmd + " 0";
        else if (val == 1) return TempWarningCmd + " 1";
        else return TempWarningCmd + " 0";
    }

    public String getForceTurnOffCmd() {
        return ForceTurnOff;
    }

    public String intToHexStr(int i) {
        return "0x" + String.format("%2s", Integer.toHexString(i)).replace(' ', '0');
    }

    public String getSweepTimeCmd() {
        return SweepTimdCmd;
    }

    public String getAutoAttenCmd() {
        return AutoAtten;
    }

    public boolean isAutoAtten() {
        return isAutoAtten;
    }

    public void setAutoAtten(boolean autoAtten) {
        isAutoAtten = autoAtten;
    }

    //    if value is true => auto scale after receive data
    public boolean isSaAutoScale() {
        return isSaAutoScale;
    }

    public void setSaAutoScale(boolean saAutoScale) {
        isSaAutoScale = saAutoScale;
    }

    public int getAutoScaleCount() {
        return AutoScaleCount;
    }

    public void setAutoScaleCount(int autoScaleCount) {
        AutoScaleCount = autoScaleCount;
    }

    public String getLockStatusCmd() {
        return LockStatusCmd;
    }

    public StatusData getStatusData() {
        if (StatusData == null) StatusData = new StatusData();
        return StatusData;
    }

    public String getGPSInfoRequestCmd() { return GPSInfoRequestCmd; }

    public int getGPSInfoResponseCmdVal() { return GPSInfoResponseCmdVal; }

    //@@ [22.01.10] GPS Holdover Cmd
    public int getGPSHoldoverRequestCmdVal() { return GPSHoldoverRequestCmdVal; }

    public String getGPSHoldoverRequestCmd() { return GPSHoldoverRequestCmd; }

    public String getChangeGPSHoldoverCmd() {
        StringBuffer bufferStr = new StringBuffer();

        // 0x54 gps response cmd
        bufferStr.append(getGPSHoldoverRequestCmd());
        bufferStr.append(" ");
        // option
        // 0: GPS / 1: LTE(Tracking)
        bufferStr.append(getHoldoverData().getOption().getCmd());
        bufferStr.append(" ");
        // time period
        bufferStr.append(getHoldoverData().getTimePeriod());
        bufferStr.append(" ");
        // number of LTE
        bufferStr.append(getLteInfoData().getNumOfLte());
        bufferStr.append(" ");


        for (int i = 0; i < getLteInfoData().getNumOfLte(); i++) {
            // Tech Type[LTE(n)]
            // 0: LTE / 1: 5G
            bufferStr.append(getGpsHoldoverData().getTechType(i));
            bufferStr.append(" ");

            // Frequency[LTE(n)]
            /*bufferStr.append((long) Math.round(getLteInfoData().getLteFreq(i) * Math.pow(10, 6)));*/ // MHz to Hz
            bufferStr.append((long) Math.round(getGpsHoldoverData().getFrequency(i) * Math.pow(10, 6)));
            bufferStr.append(" ");

            // Profile[LTE(n)]
            // 0: 1.4 MHz / 1: 3 MHz / 2: 5 MHz / 3: 10 MHz / 4: 15 MHz / 5: 20 MHz
            /*bufferStr.append(getLteInfoData().getLteProfile(i));*/
            /*bufferStr.append(getGpsHoldoverData().getProfile(i).getProfile().getCmd());
            bufferStr.append(" ");*/

            // tech added
            if (getGpsHoldoverData().getTechType(i) == 0) {
                // LTE
                bufferStr.append(getGpsHoldoverData().getProfile(i).getProfile().getCmd());
                bufferStr.append(" ");
            }
            else {
                // 5G
                bufferStr.append(getGpsHoldoverData().getProfile(i).getProfile5G().getCmd());
                bufferStr.append(" ");
            }
        }

        // GPS command
//        Log.e("GPS", "GPS Command : " + bufferStr);

        return bufferStr.toString();
    }
    //@@
}

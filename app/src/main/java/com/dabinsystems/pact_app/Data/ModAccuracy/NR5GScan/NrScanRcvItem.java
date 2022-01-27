package com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan;

import android.annotation.SuppressLint;
import android.util.Log;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.text.NumberFormat;

/**
 * [jigum] 2021-07-22
 *
 * 5G NR Scan 장비로 부터 받는 데이터 항목
 */
public class NrScanRcvItem {

    public static final int NO_VALUE = -999999;
    public static final String NO_VALUE_TEXT = "--.--";
    public static final int NR_SCAN_RCV_PACKET_SIZE = 44 + 16;

    //Mode              S32     4 Byte      0x42 : Mod Accuracy
    //Type              S32     4 Byte      0x11 : 5G NR Scan
    //Carrier Index     U8      4 Byte      Range : 0 ~ 3.

    /** GPS Latitude    Double  8 Byte      GPS로 부터 수신한 현재 위치의 위도   단위: Degree ( -90.000000 ~ + 90.000000) */
    double GpsLatitude;
    /** GPS Longitude   Double  8 Byte      GPS로 부터 수신한 현재 위치의 경도   단위: Degree ( -180.000000 ~ + 180.000000) */
    double GpsLongitude;
    /** SSB Frequency     U32     4 Byte      Carrier Index에 대한 SSB Frequency 항목. x 0.01 값을 GUI에 표시 ex) FW 전달 값이 360879인 경우 3608.79MHz을 GUI에 표시 */
    long SSB_Frequency = NO_VALUE;
    /** PCI               S32     4 Byte      Carrier Index에 대한 PCI 항목. Range : 0 ~ 1007 */
    int PCI = NO_VALUE;
    /** SSB Index         S32     4 Byte      Carrier Index에 대한 SSB Index. Range : 0 ~ 7 */
    int SSB_Index = NO_VALUE;
    /** SS-RSRP           S32     4 Byte      Carrier Index에 대한 SS-RSRP 항목. x 0.01 값을 GUI에 표시 */
    int SS_RSRP = NO_VALUE;
    /** SS-RSRQ           S32     4 Byte      Carrier Index에 대한 SS-RSRQ 항목. x 0.01 값을 GUI에 표시 */
    int SS_RSRQ = NO_VALUE;
    /** SS-SINR           S32     4 Byte      Carrier Index에 대한 SS-SINR 항목 x 0.01 값을 GUI에 표시. */
    int SS_SINR = NO_VALUE;
    /** Timing Offset     S32     4 Byte      Carrier Index에 대한 Timing Offset. Unit : us x 0.001 값을 GUI에 표시 */
    int TimingOffset = NO_VALUE;
    /** TAE               S32     4 Byte      Carrier Index에 대한 TAE. Unit : us x 0.001 값을 GUI에 표시. */
    int TAE = NO_VALUE;
    /** Sync              S32     4 Byte      0 : Sync Fail, 1 : Sync Pass */
    int Sync = NO_VALUE;

    boolean IsOn;
    int timeIdx = 0;

    //byte[] dump = new byte[NR_SCAN_RCV_PACKET_SIZE];

    public void clear() {
        SSB_Frequency = NO_VALUE;
        PCI = NO_VALUE;
        SSB_Index = NO_VALUE;
        SS_RSRP = NO_VALUE;
        SS_RSRQ = NO_VALUE;
        SS_SINR = NO_VALUE;
        TimingOffset = NO_VALUE;
        TAE = NO_VALUE;
        timeIdx = 0;
    }

    public void set(ByteBuffer bb) {
        try {
            bb.position(0);
            //bb.get(dump);

            //Mode              S32     4 Byte      0x42 : Mod Accuracy
            //Type              S32     4 Byte      0x11 : 5G NR Scan
            //Carrier Index     U8      1 Byte      Range : 0 ~ 3.
            bb.position(12);

            //GPS Latitude      Double  8 Byte
            GpsLatitude = bb.getDouble();
            //GPS Longitude     Double  8 Byte
            GpsLongitude = bb.getDouble();

            //SSB Frequency     U32     4 Byte
            SSB_Frequency = bb.getInt();
            //Log.d("KDK-", "SSB_Frequency = " + SSB_Frequency);
            SSB_Frequency = ((long) SSB_Frequency) & 0xffffffffL;// Integer.toUnsignedLong(bb.getInt());
            //Log.d("KDK-", "SSB_Frequency to long = " + SSB_Frequency);

            //PCI               U16     2 Byte
            PCI = bb.getInt();
            //SSB Index         U8      1 Byte
            SSB_Index = bb.getInt();
            //SS-RSRP           S32     4 Byte
            SS_RSRP = bb.getInt();
            //SS-RSRQ           S32     4 Byte
            SS_RSRQ = bb.getInt();
            //SS-SINR           S32     4 Byte
            SS_SINR = bb.getInt();
            //Timing Offset     S32     4 Byte
            TimingOffset = bb.getInt();
            //TAE               S32     4 Byte
            TAE = bb.getInt();
            //Sync              S32     4 Byte
            Sync = bb.getInt();
        } catch (BufferUnderflowException e) {
            //e.printStackTrace();
        }
    }

    public String toViewText(int val) {
        if (val == NO_VALUE) {
            return NO_VALUE_TEXT;
        }
        return String.valueOf(val);
    }

    @SuppressLint("DefaultLocale")
    public String toViewText(long val, int x) {
        if (val == NO_VALUE) {
            return NO_VALUE_TEXT;
        } else if (val == 0) {
            return "0";
        }

        //return String.format("%.2f", (val * x));
        //return String.valueOf((double) val / x);
        NumberFormat f = NumberFormat.getInstance();
        f.setGroupingUsed(false);
        return f.format((double) val / x);
    }

    public String getSSB_FrequencyToViewText() {
        return toViewText(SSB_Frequency, 100);
    }

    public String getPCIToViewText() {
        return toViewText(PCI);
    }

    public String getSSB_IndexToViewText() {
        return toViewText(SSB_Index);
    }

    public String getSS_RSRPToViewText() {
        return toViewText(SS_RSRP, 100);
    }

    public String getSS_RSRQToViewText() {
        return toViewText(SS_RSRQ, 100);
    }

    public String getSS_SINRToViewText() {
        return toViewText(SS_SINR, 100);
    }

    public String getTimingOffsetToViewText() {
        return toViewText(TimingOffset, 1000);
    }

    public String getTAEToViewText() {
        return toViewText(TAE, 1000);
    }

    public float getTraceValue(NR_SCAN_TRACE eTrace) {
        float val = 0;
        switch (eTrace) {
            case PCI:
                val = PCI;
                break;
            case SS_RSRP:
                val = (float)SS_RSRP / 100;
                break;
            case SS_SINR:
                val = (float)SS_SINR / 100;
                break;
            case TimingOffset:
                val = (float)TimingOffset / 1000;
                break;
        }
        return val;
    }

    public double getGpsLatitude() {
        return GpsLatitude;
    }

    public double getGpsLongitude() {
        return GpsLongitude;
    }

    public String getCsv() {
        StringBuilder sb = new StringBuilder();

        //Sync 0 : Sync Fail, 1 : Sync Pass
        sb.append(Sync).append(",");
        //SSB Freq [MHz]
        sb.append(getSSB_FrequencyToViewText()).append(",");
        //PCI SSB
        sb.append(getPCIToViewText()).append(",");
        //Index
        sb.append(getSSB_IndexToViewText()).append(",");
        //RSRP [dBm]
        sb.append(getSS_RSRPToViewText()).append(",");
        //RSRQ [dB]
        sb.append(getSS_RSRQToViewText()).append(",");
        //SINR [dB]
        sb.append(getSS_SINRToViewText()).append(",");
        //Timing Offset [us]
        sb.append(getTimingOffsetToViewText()).append(",");
        //TAE [us]
        sb.append(getTAEToViewText());

        return sb.toString();
    }

    public boolean isOn() {
        return IsOn;
    }

    public void setOn(boolean on) {
        IsOn = on;
    }

    public int getTimeIdx() {
        return timeIdx;
    }

    public void setTimeIdx(int timeIdx) {
        this.timeIdx = timeIdx;
    }

//    public byte[] getDump() {
//        return dump;
//    }
//    public String getDumpToString() {
//        StringBuilder sbDump = new StringBuilder();
//        for (byte b : dump) {
//            sbDump.append(String.format("%02X", b)).append(" ");
//        }
//        sbDump.append("\r\n");
//        return sbDump.toString();
//    }
}

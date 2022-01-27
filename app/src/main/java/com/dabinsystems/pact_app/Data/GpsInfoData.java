package com.dabinsystems.pact_app.Data;

import android.annotation.SuppressLint;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class GpsInfoData {
    final String TAG = "GpsInfoData";
    final boolean D = true;

    final int SAT_COUNT = 12;

    public int Command; // U8 1 Byte 0x53
    public int Year; // U16 2 Byte year
    public int Month; // U8 1 Byte month of year
    public int Day; // U8 1 Byte day of month
    public int Hour; // U8 1 Byte hours of day
    public int Minute; // U8 1 Byte minute of hour
    public int Second; // U8 1 Byte seconds of minute
    public int SatelliteCount; // 활성화된 위성 수
    //12개의 Sate Num과 Sate Signal 값이 전송. (Satellite Count 만큼 업데이트되고 나머지는 모든 값이 ‘0’ 으로 전송됨)
    public int[] SateNum = new int[SAT_COUNT]; // 활성화된 위성의 인덱스
    public int[] SateSignal = new int[SAT_COUNT]; // 해당 위성의 CNR 값. (단위 : dB)
    public double Longitude; // 경도 정보 (단위 : Degree)
    public double Latitude; // 위도 정보(단위 : Degree)
    public double Altitude; // 고도 정보 (단위 : meter)
    public int AntennaStatus; // antenna status (0: OK, 1: Open or Short)
    public int GPSStatus; // GPS Lock status (0 : Unlock, 1 : Lock, 2 : Holdover)

    public void parsing(byte[] buf) {
        ByteBuffer bb = ByteBuffer.wrap(buf);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        Command = bb.get();
        Year = bb.getShort();
        Month = bb.get();
        Day = bb.get();
        Hour = bb.get();
        Minute = bb.get();
        Second = bb.get();
        SatelliteCount = bb.get();

        for (int i = 0; i < SAT_COUNT; ++i) {
            SateNum[i] = bb.get();
            SateSignal[i] = bb.getShort();
        }

        Longitude = bb.getDouble();
        Latitude = bb.getDouble();
        Altitude = bb.getDouble();
        AntennaStatus = bb.getShort();
        GPSStatus = bb.getInt();

        if (D) Log.d(TAG, toString());
    }

    @Override
    public String toString() {
        return "GpsInfoData{" +
                "Command=" + Command +
                ", Year=" + Year +
                ", Month=" + Month +
                ", Day=" + Day +
                ", Hour=" + Hour +
                ", Minute=" + Minute +
                ", Second=" + Second +
                ", SatelliteCount=" + SatelliteCount +
                ", SateNum=" + Arrays.toString(SateNum) +
                ", SateSignal=" + Arrays.toString(SateSignal) +
                ", Longitude=" + Longitude +
                ", Latitude=" + Latitude +
                ", Altitude=" + Altitude +
                ", AntennaStatus=" + AntennaStatus +
                ", GPSStatus=" + GPSStatus +
                '}';
    }

    public String dateToString() {
        return Year + " / " + Month + " / " + Day;
    }

    @SuppressLint("DefaultLocale")
    public String timeToString() {
        return String.format("%02d : %02d : %02d", Hour, Minute, Second);
    }

    @SuppressLint("DefaultLocale")
    public String longitudeToString() {
        return String.format("%.5f °", Longitude);
    }

    @SuppressLint("DefaultLocale")
    public String latitudeToString() {
        return String.format("%.5f °", Latitude);
    }

    @SuppressLint("DefaultLocale")
    public String altitudeToString() {
        return String.format("%.1f m", Altitude);
    }


    public String gpsStatusToString() {
        // GPS Lock status (0 : Unlock, 1 : Lock, 2 : Holdover)
        String rtn = "Unlock";

        switch (GPSStatus) {
            case 0:
                rtn = "Unlock";
                break;
            case 1:
                rtn = "Lock";
                break;
            case 2:
                rtn = "Holdover";
                break;
        }

        return rtn;
    }

    public String antennaStatusToString() {
        // antenna status (0: OK, 1: Open or Short)
        return (AntennaStatus == 0) ? "OK" : "Open or Short";
    }

    private int colorV, colorPre;

    public void setColor(int color, int colorPre) {
        this.colorV = color;
        this.colorPre = colorPre;
    }

    public int[] ViewSateNum = new int[SAT_COUNT];
    public int[] ViewSateSignal = new int[SAT_COUNT];
    public int[] ViewColor = new int[SAT_COUNT];

    public void changeSate() {
        for (int i = 0; i < SAT_COUNT; ++i)
            ViewColor[i] = colorPre;

        for (int i = 0; i < SAT_COUNT; ++i) {
            if (SateSignal[i] > 0)
                changeSate(SateNum[i], SateSignal[i]);
        }
    }

    private boolean changeSate(int num, int signal) {
        for (int i = 0; i < SAT_COUNT; ++i) {
            if (ViewSateNum[i] == num) {
                if (signal > 0) {
                    ViewSateSignal[i] = signal;
                    ViewColor[i] = colorV;
                } else {
                    ViewColor[i] = colorPre;
                }
                return true;
            } else if (ViewSateNum[i] == 0) {
                ViewSateNum[i] = num;
                ViewSateSignal[i] = signal;
                ViewColor[i] = colorV;
                return true;
            }
        }
        return false;
    }

}

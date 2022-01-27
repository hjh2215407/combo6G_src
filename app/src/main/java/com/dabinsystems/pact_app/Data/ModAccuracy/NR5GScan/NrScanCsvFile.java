package com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 5G NR Scan 수신 정보를 CSV 파일로 저장
 */
public class NrScanCsvFile {

    final Context context;

    private FileOutputStream fos = null;

    public NrScanCsvFile(Context context) {
        this.context = context;
    }

    @SuppressLint("SimpleDateFormat")
    public boolean open() {
        Date now = new Date();

        String strDir = new SimpleDateFormat("yyyyMMdd").format(now);
        File dir = new File(Environment.getExternalStorageDirectory(), "PACT/Scan/" + strDir);
        if (!dir.exists()) {
            if (!dir.mkdirs())
                return false;
        }

        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(now) + ".csv";

        File logFile = new File(dir, fileName);

        try {
            fos = new FileOutputStream(logFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fos = null;
            return false;
        }

        return true;
    }

    public void close() {
        if (fos == null)
            return;
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fos = null;
    }

    @SuppressLint("SimpleDateFormat")
    public boolean write(NrScanRcvData data) {

        if (fos == null) {
            if (!open()) {
                return false;
            } else {
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < NrScanData.NR_SCAN_CARRIER_COUNT; ++i) {
                    if (data.get(i).isOn()) {
                        sb.append(data.getName(i)).append(",");
                    }
                }
                sb.append("\r\n");

                sb.append("Time Stamp,Gps Latitude,Gps Longitude,");
                for (int i = 0; i < NrScanData.NR_SCAN_CARRIER_COUNT; ++i) {
                    if (data.get(i).isOn()) {
                        sb.append("Sync State,SSB Freq [MHz],PCI SSB,Index,RSRP [dBm],RSRQ [dB],SINR [dB],Timing Offset [us],TAE [us],");
                    }
                }
                sb.append("\r\n");

                if (!write(sb.toString().getBytes()))
                    return false;
            }
        }
        
        StringBuilder sb = new StringBuilder();

//        // 임시 덤프 저장
//        for (int i = 0; i < NrScanData.NR_SCAN_CARRIER_COUNT; ++i) {
//            if (data.get(i).isOn()) {
//                sb.append(data.get(i).getDumpToString());
//            }
//        }

        //Time Stamp
        sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append(",");
        //GPS 위치 정보
        sb.append(data.get(0).getGpsLatitude()).append(",");
        sb.append(data.get(0).getGpsLongitude()).append(",");

        for (int i = 0; i < NrScanData.NR_SCAN_CARRIER_COUNT; ++i) {
            if (data.get(i).isOn()) {
                sb.append(data.get(i).getCsv()).append(",");
            }
        }
        sb.append("\r\n");

        return write(sb.toString().getBytes());
    }

    public boolean write(byte[] buf) {
        try {
            fos.write(buf);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            close();
            return false;
        }
        return true;
    }
}

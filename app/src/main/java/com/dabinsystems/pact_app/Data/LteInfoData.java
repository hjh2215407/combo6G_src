package com.dabinsystems.pact_app.Data;

import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LteInfoData {

    private final String LTE_INFO_INI = "LTE_Info.ini";
    private final String mDirectoryName = "PACT/LteInfo";
    private final File mPath = Environment.getExternalStorageDirectory();


    private Ini ini;

    private int[] techType;
    private double[] freq;
    private int[] profile;
    private int numOfLte;

    public boolean loadIniFileInAsset() {
        AssetManager aMgr = MainActivity.getActivity().getResources().getAssets();
        InputStream is = null;

        if (ini != null) return false;

        try {
            is = aMgr.open(LTE_INFO_INI);

            ini = new Ini(is);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void loadLteIniData() {

        String str = ini.get("Num of LTE","numOfLte");
        numOfLte = Integer.parseInt(str);

        if (techType == null || profile == null) {
            techType = new int[numOfLte];
            freq = new double[numOfLte];
            profile = new int[numOfLte];
        }

        Log.e("LTE INI", "number of LTE : " + numOfLte);

        for (int i = 0; i < numOfLte; i++) {

            String tmp = ini.get(i + "", "techType");
            int techTypeTmp = Integer.parseInt(tmp);

            tmp = ini.get(i + "", "freq");
            double freqTmp = Double.parseDouble(tmp);

            tmp = ini.get(i + "", "profile");
            int profileTmp = Integer.parseInt(tmp);

            /*Log.e("LTE INI", i + ")" + " Tech : " + techTypeTmp + ", Freq : " + freqTmp + ", Profile : " + profileTmp);*/

            techType[i] = techTypeTmp;
            freq[i] = freqTmp;
            profile[i] = profileTmp;
        }

        DataHandler.getInstance().getGpsHoldoverData().setTechType(techType);
        DataHandler.getInstance().getGpsHoldoverData().setFrequency(freq);
        DataHandler.getInstance().getGpsHoldoverData().setGpsProfile(profile);


        // Check Loaded Data
        for (int i = 0; i < numOfLte; i++) {
            int tech = DataHandler.getInstance().getGpsHoldoverData().getTechType(i);
            double freq = DataHandler.getInstance().getGpsHoldoverData().getFrequency(i);
            GpsProfile profile = DataHandler.getInstance().getGpsHoldoverData().getProfile(i);

            if (tech == 0) {
                Log.e("LTE INI", i + ")" + " Tech : " + tech + ", Freq : " + freq + ", Profile : " + profile.getProfile().getString() + "(" +profile.getProfile().getCmd() + ")");
            }
            else if (tech == 1) {
                Log.e("LTE INI", i + ")" + " Tech : " + tech + ", Freq : " + freq + ", Profile : " + profile.getProfile5G().getString() + "(" + profile.getProfile5G().getCmd() + ")");
            }
        }

    }

    public boolean loadLteIniDataInLocal() {
        File mRootFolder = new File(mPath, mDirectoryName);
        if (!mRootFolder.exists()) {
            mRootFolder.mkdirs();
        }

        if (mRootFolder == null || mRootFolder.listFiles() == null)
            return false;

        for (File file : mRootFolder.listFiles()) {

            if (file.getName().equals(LTE_INFO_INI)) {
                try {
                    InputStream is = new FileInputStream(file);
                    ini = new Ini(is);
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                catch (InvalidFileFormatException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        return false;
    }

    public void copyLteFile() {
        AssetManager aMgr = MainActivity.getActivity().getResources().getAssets();
        InputStream is = null;
        FileOutputStream fos = null;
        String path = mPath + File.separator + mDirectoryName + File.separator + LTE_INFO_INI;

        File mRootFolder = new File(mPath, mDirectoryName);
        if (!mRootFolder.exists()) {
            mRootFolder.mkdirs();
        }

        try {
            is = aMgr.open(LTE_INFO_INI);
            int size = is.available();
            byte[] buf = new byte[size];
            File file = new File(path);
            fos = new FileOutputStream(file);
            for (int c = is.read(buf); c != -1; c = is.read(buf)) {
                fos.write(buf, 0, c);
            }

            is.close();
            fos.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLteList() {
        Log.e("LteInfoData", "Loading LTE Info ini file");

        if (!loadLteIniDataInLocal()) {
            Log.e("LteInfoData", "ini file not exist in local ... load from asset");

            if (loadIniFileInAsset()) {
                Log.e("LteInfoData", "ini file loaded from asset");

                copyLteFile();
                loadLteIniData();
            }

        }
        else {
            Log.e("LteInfoData", "ini file loaded from local");
            loadLteIniData();
        }

    }

    public double[] getLteFreq() {
        return freq;
    }

    public double getLteFreq(int idx) {
        return freq[idx];
    }

    public int[] getLteProfile() {
        return profile;
    }

    public int getLteProfile(int idx) {
        return profile[idx];
    }

    public int getNumOfLte() {
        return numOfLte;
    }
}

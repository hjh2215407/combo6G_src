package com.dabinsystems.pact_app.Function;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.List.Adapter.CableListAdapter;
import com.dabinsystems.pact_app.List.Item.CableListInfoItem;
import com.dabinsystems.pact_app.List.Item.CableLossInfoItem;

import org.ini4j.Ini;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipError;
import java.util.zip.ZipException;

public class CableInfoControl {

    private String CableName;
    private float CableLoss = 0f;
    private float Velocity = 0f;

    protected Ini ini;
    private final String mDirectoryName = "PACT/CableList";
    private final File mPath = Environment.getExternalStorageDirectory();

    private CableListAdapter CableListAdapter;

    private File mRootFolder;

    private String SP_LOAD_CABLE_LIST = "LoadCableList";
    private int mLoadCableList = 0;

    public void setCableName(String name) {

        CableName = name;

    }

    public String getCableName() {

        return CableName;
    }

    public void setLoss(float loss) {

        try {
            CableLoss = loss;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public Float getLoss() {

        return CableLoss;

    }

    public boolean setVelocity(float velo) {

        if (velo < 0.5f || velo > 1f) {
            MainActivity.getActivity().runOnUiThread(() -> {
                MainActivity.getActivity().runOnUiThread(() -> {
                    Toast.makeText(MainActivity.getContext(), "Out of range(0.5 ~ 1)", Toast.LENGTH_SHORT).show();
                });
            });
            return false;
        }

        Velocity = velo;
        return true;
    }

    public float getVelocity() {

        return Velocity;

    }

    @SuppressLint("SetTextI18n")
    public void updateCableText() throws NullPointerException {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (getCableInfoAdapter().getSelectedCable() != null) {

                Log.d("updateCableText", "not null");
                MainActivity.getBinding().lineChartLayout.tvChartInfo.setText(
                        getCableName() + " [PV : "
                                + getVelocity() + ", CL : "
                                + getLoss() + " dB/m]"
                );

            }

        });

    }

    public boolean checkCableListFile() {

        mRootFolder = new File(mPath, mDirectoryName);
        if (!mRootFolder.exists()) {
            mRootFolder.mkdirs();
        }

        if (mRootFolder == null || mRootFolder.listFiles() == null) return false;
        boolean isLoadCableFile = false;

        for (File file : mRootFolder.listFiles()) {

            if (file.getName().equals("cable_list.ini")) {

                isLoadCableFile = true;
                loadCableListFile(file);

            }

        }

        return isLoadCableFile;

    }

    public void showCableList() {

        MainActivity.getBinding().linCableList.setVisibility(View.VISIBLE);
        MainActivity.getBinding().rvCableList.setAdapter(
                getCableInfoAdapter()
        );

        MainActivity.getBinding().rvCableList.setLayoutManager(new LinearLayoutManager(MainActivity.getActivity()));
        getCableInfoAdapter().notifyDataSetChanged();
    }


    @SuppressLint("SetTextI18n")
    public void addCableList() {

        SharedPreferences sp = InitActivity.getSpData();

        mLoadCableList = sp.getInt(SP_LOAD_CABLE_LIST, 0);

        new Thread(() -> {

            Log.d("addCableList", "in");

            mRootFolder = new File(mPath, mDirectoryName);
            if (!mRootFolder.exists()) {
                mRootFolder.mkdirs();
                Log.d("addCableList", "mkdir folder");
            }

            if (mRootFolder == null) {
                Log.d("addCableList", "null");
                return;
            }

            boolean isLoadCableFile = false;
            InitActivity.logMsg("CableInfoControl", "mLoadCableList : " + mLoadCableList);
            if (mLoadCableList == 0) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt(SP_LOAD_CABLE_LIST, 1);
                editor.apply();
                mLoadCableList = sp.getInt(SP_LOAD_CABLE_LIST, 0);
                InitActivity.logMsg("CableInfoControl", "mLoadCableList : " + mLoadCableList);
            }
            else if (mRootFolder.listFiles() != null) {
                for (File file : mRootFolder.listFiles()) {

                    if (file.getName().equals("cable_list.ini")) {

                        Log.d("addCableList", "find cable list ini");
                        isLoadCableFile = true;
                        loadCableListFile(file);

                    }

                }
            }

            Log.d("addCableList", "check load cable file");
            Log.d("addCableList", isLoadCableFile + "");

            if (!isLoadCableFile) {

                Log.d("addCableList", "copy cable list file");

                copyCableListFile();

                if (loadIniFileInAsset())

                    Log.d("addCableList", "load cable list file");
                loadCableListFile();

            }

        }).start();

    }

    public void invisibleShowCableList() throws NullPointerException {

        MainActivity.getBinding().linCableList.setVisibility(View.GONE);

        MainActivity.getBinding().rvCableList.setAdapter(
                getCableInfoAdapter()
        );

        MainActivity.getBinding().rvCableList.setLayoutManager(new LinearLayoutManager(MainActivity.getActivity()));
        getCableInfoAdapter().notifyDataSetChanged();

    }

    public CableListInfoItem selectedCableList() throws NullPointerException {

        showCableList();

        return getCableInfoAdapter().getSelectedCable();

    }

    private boolean loadIniFileInAsset() {
        AssetManager aMgr = MainActivity.getActivity().getResources().getAssets();

        InputStream is = null;
        try {
            is = aMgr.open("cable_list.ini");
            ini = new Ini(is);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void copyCableListFile() {

        AssetManager aMgr = MainActivity.getActivity().getResources().getAssets();
        InputStream is = null;
        FileOutputStream fos = null;
        String path = mPath + File.separator + mDirectoryName + File.separator + "cable_list.ini";

        try {
            is = aMgr.open("cable_list.ini");
            int size = is.available();
            byte[] buf = new byte[size];
            File file = new File(path);
            fos = new FileOutputStream(file);
            for (int c = is.read(buf); c != -1; c = is.read(buf)) {
                fos.write(buf, 0, c);
            }

            is.close();
            fos.close();
//            convertInputStreamToFile(is, mPath + File.separator + mDirectoryName + File.separator + "cable_list.ini");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadCableListFile() {

        if (ini == null) return;

        try {
            Ini.Section section = ini.get("Num of Cable");
            int numOfCable = Integer.parseInt(section.get("numOfCable"));

            for (int i = 0; i < numOfCable; i++) {

                section = ini.get(i + "");
                String name = section.get("name");
                String velocity = section.get("velocity");
                int numOfCableLoss = Integer.parseInt(section.get("numOfCableLoss"));
                ArrayList<CableLossInfoItem> lossList = new ArrayList<>();

                for (int k = 0; k < numOfCableLoss; k++) {

//                        InitActivity.logMsg(k + "", section.get("freq" + k) + " " + section.get("loss" + k));
                    lossList.add(new CableLossInfoItem(section.get("freq" + k), section.get("loss" + k)));

                }

                getCableInfoAdapter()
                        .getCableInfoList()
                        .add(new CableListInfoItem(name, velocity, lossList));

//                CableListInfoList.add(new CableListInfoItem(name, velocity, lossList));

            }

            String name = getCableInfoAdapter().getSelectedCable().getCableName();
            float loss = getCableInfoAdapter().getSelectedCable().getLoss();
            float velocity = getCableInfoAdapter().getSelectedCable().getPropVelocity();

            setCableName(name);
            setLoss(loss);
            setVelocity(velocity);

            InitActivity.getActivity().runOnUiThread(() -> {
                InitActivity.getBinding().tvCableName.setText(name);
                InitActivity.getBinding().tvPropVelocity.setText(velocity + "");
            });

            Log.d("getCableInfo", name + " " + loss + " " + velocity);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadCableListFile(File file) {

        try {

            InputStream is = new FileInputStream(file);
            ini = new Ini(is);

            Ini.Section section = ini.get("Num of Cable");
            int numOfCable = Integer.parseInt(section.get("numOfCable"));

            if (getCableInfoAdapter().getCableInfoList().size() == numOfCable) return;

            for (int i = 0; i < numOfCable; i++) {

                section = ini.get(i + "");
                String name = section.get("name");
                String velocity = section.get("velocity");
                int numOfCableLoss = Integer.parseInt(section.get("numOfCableLoss"));
                ArrayList<CableLossInfoItem> lossList = new ArrayList<>();

                for (int k = 0; k < numOfCableLoss; k++) {

//                    InitActivity.logMsg(k + "", section.get("freq" + k) + " " + section.get("loss" + k));
                    lossList.add(new CableLossInfoItem(section.get("freq" + k), section.get("loss" + k)));

                }

                getCableInfoAdapter()
                        .getCableInfoList()
                        .add(new CableListInfoItem(name, velocity, lossList));

                MainActivity.getActivity().runOnUiThread(() -> {

                    MainActivity.getBinding().rvCableList.setAdapter(getCableInfoAdapter());
                    getCableInfoAdapter().notifyDataSetChanged();

                });

            }

            String name = getCableInfoAdapter().getSelectedCable().getCableName();
            float loss = getCableInfoAdapter().getSelectedCable().getLoss();
            float velocity = getCableInfoAdapter().getSelectedCable().getPropVelocity();

            setCableName(name);
            setLoss(loss);
            setVelocity(velocity);

            InitActivity.getActivity().runOnUiThread(() -> {
                InitActivity.getBinding().tvCableName.setText(name);
                InitActivity.getBinding().tvPropVelocity.setText(velocity + "");
            });

            Log.d("getCableInfo", name + " " + loss + " " + velocity);

        } catch (IOException | NullPointerException | ZipError e) {
            e.printStackTrace();
        }
    }

    public CableListAdapter getCableInfoAdapter() {

        if (CableListAdapter == null) {
            CableListAdapter = new CableListAdapter(MainActivity.getActivity());
        }

        return CableListAdapter;

    }

}

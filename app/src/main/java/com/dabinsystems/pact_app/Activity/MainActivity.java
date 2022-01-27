package com.dabinsystems.pact_app.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dabinsystems.pact_app.Function.Hotspot;
import com.dabinsystems.pact_app.Function.WifiFunc;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;

public class MainActivity extends InitActivity {
    final String TAG = "MainActivity";

    @Override
    public void setValues() {
        mActivity = this;
        ViewHandler.getInstance(this, binding).getContent();

        super.setValues();

//        new Thread(() -> {
//            FunctionHandler.getInstance().getCableInfoFunc().addCableList();
//        }).start();

        //DEBUG: pass WIFI connection
        /*
         * if setWifiNetwork is true  : WIFI search ON
         * if setWifiNetwork is false : WIFI search OFF => AP MODE ON
         * */

//        WifiFunc.setWifiNetwork(true);

        if (!WifiFunc.isWifiNetwork()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(this)) {
                    Toast.makeText(this, "onCreate: Already Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "onCreate: Not Granted. Permission Requested", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + this.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            mHotspotManager.scan();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        logMsg("onResume", "register resources");

        new Thread(() -> {

            new Handler(getMainLooper()).postDelayed(() -> {

                FunctionHandler.getInstance().getDataConnector().registerRes();

            }, 1000);

        }).start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

        FunctionHandler.getInstance().getWifiFunc().unbindProcessToPact();

        try {
            unRegWifiState();
            FunctionHandler.getInstance().getDataConnector().setBackgroundReset(true);

            FunctionHandler.getInstance().getDataConnector().removeDataTimeoutHandler();
            FunctionHandler.getInstance().getDataConnector().setReady(false);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

        FunctionHandler.getInstance().getWifiFunc().bindProcessToPact();

        new Thread(() -> {

            new Handler(getMainLooper()).postDelayed(() -> {

                FunctionHandler.getInstance().getDataConnector().setReady(true);
                FunctionHandler.getInstance().getDataConnector().startDataTimeoutHandler();

            }, 1000);

            if (WifiFunc.isWifiNetwork() && isPermitted()) {
                regWifiState();
                logMsg("MainActivity", "onStart : " + "init wifi");
            }

        }).start();

    }

    @Override
    protected void onDestroy() {

        stopUpdateTime();

        try {
            // [jigum] 5G NR Scan 화면이 닫힐 경우 Scan stop
            DataHandler.getInstance().getNrScanData().setStart(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();

        try {
            FunctionHandler.getInstance().getDataConnector().unregisterRes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logMsg("OnDestroy", "IN");
        System.exit(0);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}

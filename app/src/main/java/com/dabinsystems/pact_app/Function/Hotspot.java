package com.dabinsystems.pact_app.Function;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.MacAddress;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Network.HotspotManager.ClientScanResult;
import com.dabinsystems.pact_app.Network.HotspotManager.FinishScanListener;
import com.dabinsystems.pact_app.Network.HotspotManager.WifiApManager;
import com.dabinsystems.pact_app.Network.NetworkUtils;
import com.dabinsystems.pact_app.Network.WifiConnector.WifiConnector;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hotspot {

    WifiApManager mWifiApManager;
    String mIpAddr = null;

    public Hotspot(Activity activity) {
        this.mWifiApManager = new WifiApManager(activity.getApplicationContext());

//        mWifiApManager.showWritePermissionSettings(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            activity.requestPermissions(new String[]{Manifest.permission.WRITE_SETTINGS}, 55);
//        }
    }

    public void scan() {

        new Thread(() -> {

            if (!InitActivity.isApEnabled() && !InitActivity.isUsbTethering()) {

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    //InitActivity.logMsg("Hotspot", "disable hotspot...");
                    Log.e("Hotspot", "disable hotspot...");
                    scan();
                }, 3000);

            } else {

                //org
                mWifiApManager.getClientList(false, new FinishScanListener() {

                    @Override
                    public void onFinishScan(final ArrayList<ClientScanResult> clients) {

                        boolean isReachable = false;
                        for (ClientScanResult clientScanResult : clients) {

                            if (clientScanResult.isReachable()) {
                                isReachable = true;
                                mIpAddr = clientScanResult.getIpAddr();

                                //InitActivity.logMsg("Hotspot", "Find hotspot client : " + mIpAddr);
                                //InitActivity.logMsg("Hotspot", "My IP : " + NetworkUtils.getIPAddress(true));
                                Log.e("Hotspot", "Find hotspot client : " + mIpAddr);

                                Log.e("Hotspot", "My IP : " + NetworkUtils.getIPAddress(true));
                                FunctionHandler.getInstance().getDataConnector().setMqtt(mIpAddr);
                            }

                        }

                        if (!isReachable) {

                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                //InitActivity.logMsg("Hotspot", "Can't find hotspot client... rescanning...");
                                Log.e("Hotspot", "Can't find hotspot client... rescanning");
                                scan();
                            }, 3000);

                        }
                    }
                });
                //org

                //@@ [22.01.19] fix in AP mode unblock here
                /*if (!FunctionHandler.getInstance().getDataConnector().isMqttConnected()) {

                    // 시나리오 정리가 필요
                    // Ap mode 에선 Combo 가 App 쪽으로 연결을 시도하므로 App 은 고정 IP 로 연결만 하면 됨
                    String clientIp = "192.168.43.100";

                    Log.e("Hotspot", "Try MQTT Connection To Combo (IP : " + clientIp + ")");

                    FunctionHandler.getInstance().getDataConnector().setMqtt(clientIp);

                    if (!FunctionHandler.getInstance().getDataConnector().isMqttConnected()) {
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            //InitActivity.logMsg("Hotspot", "Can't find hotspot client... rescanning...");
                            Log.e("Hotspot", "Can't find hotspot client... rescanning");
                            scan();
                        }, 3000);
                    }
                }*/
                //@@
            }
        }).start();
    }
}

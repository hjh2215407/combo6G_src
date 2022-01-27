package com.dabinsystems.pact_app.Function;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.ChangeIpDialog;
import com.dabinsystems.pact_app.Dialog.LoadingDefaultDialog;
import com.dabinsystems.pact_app.Dialog.WiFiDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Network.WifiConnector.WifiConnector;
import com.dabinsystems.pact_app.Network.interfaces.ConnectionResultListener;
import com.dabinsystems.pact_app.Network.interfaces.ShowWifiListener;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.Objects;

import static com.dabinsystems.pact_app.Recorder.VideoRecorder.REQUEST_PERMISSIONS;

public class WifiFunc {
    private final String TAG = "WifiFunc";
    private final boolean D = true;

    private final InitActivity mActivity;
    private final ActivityMainBinding binding;
    private WiFiDialog mWifiDialog;
    private Boolean isFindPact = false;

    private static String mSSID;
    private static WifiConnector mConnector;
    //    private static boolean isWifiConnected = false;
    private static boolean isWifiNetwork = true;

    private boolean isPlayingAnim = false;

    List<ScanResult> ScanResults;
    private AlertDialog mProgress;
    private ConnectivityManager mConnectivityManager;
    private Handler mScanWifiTimeoutHandler;

    private final Animation animation = new AlphaAnimation(1, 0);
    private final int mScanWifiTimeout = 10000;

    LoadingDefaultDialog loading;

    //와이파이 상태변화 수신
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (Objects.requireNonNull(intent.getAction())) {
                //와이파이 상태변화
                case WifiManager.WIFI_STATE_CHANGED_ACTION: {
                    //와이파이 상태값 가져오기
                    int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

                    if (D) Log.d("WifiState", "wifi state is changed. " + wifiState);

                    switch (wifiState) {
                        case WifiManager.WIFI_STATE_DISABLING: //와이파이 비활성화중
                            if (D) Log.d("WifiState", "deactivating...");
                            break;
                        case WifiManager.WIFI_STATE_DISABLED:  //와이파이 비활성화
                            if (D) Log.d("WifiState", "deactivated");

                            mActivity.runOnUiThread(() -> {
                                binding.tvWifiName.setText("-");
                            });

                            // wifi 활성화 요청
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                // 사용자가 직접 켜야 함.
                                if (!getWifiConnector().isWifiEnbled()) {
                                    Intent i = new Intent(Settings.Panel.ACTION_WIFI);//ACTION_INTERNET_CONNECTIVITY);
                                    mActivity.startActivityForResult(i, 99);
                                }
                            } else {
                                // app 에서 on
                                getWifiConnector().enableWifi();

                                if (!isPlayingAnim) {
                                    startAnimation();
                                }
                            }

                            FunctionHandler.getInstance().getDataConnector().setFirstConnected(true);

                            break;
                        case WifiManager.WIFI_STATE_ENABLING:  //와이파이 활성화중
                            if (D) Log.d("WifiState", "enabling..");
//                            mActivity.requestPermission();
//                            FunctionHandler.getInstance().getDataConnector().startProgressDialog("Enabling WiFi...");
                            break;

                        case WifiManager.WIFI_STATE_ENABLED:   //와이파이 활성화
                        {
                            if (D) Log.d("WifiState", "activated");

                            SharedPreferences sp = mActivity.getSharedPreferences("AppInfo", Context.MODE_PRIVATE);
                            mSSID = sp.getString("SSID", WifiManager.UNKNOWN_SSID);
                            if (D) Log.d("WifiState", "activate ssid : " + mSSID);

                            String networkSsid = getParsingSSID(getWifiConnector().getWifiManager().getConnectionInfo().getSSID());

                            if (mSSID.equals(networkSsid)
                                    && (mSSID.startsWith(mActivity.getResources().getString(R.string.pact_ssid_name1))
                                    || mSSID.startsWith(mActivity.getResources().getString(R.string.pact_ssid_name2)))) {
                                // 기존 연결 되었던 장비랑 이미 연결 되어 있음.
                                binding.tvWifiName.post(() -> binding.tvWifiName.setText(networkSsid));
                                //bindProcessToPact();

                            } else if ((mSSID.startsWith(mActivity.getResources().getString(R.string.pact_ssid_name1)) || mSSID.startsWith(mActivity.getResources().getString(R.string.pact_ssid_name2)))) {
                                if (D) Log.d("WifiState", "Connecting Saved SSID : " + mSSID);
                                // 기존에 연결 했던 이력이 있으면 해당 이름으로 연결 시도
                                connectWifi();
                            } else {
                                // 새로운 연결 시도
                                clearAnimation();
                                Toast.makeText(mActivity, "Please select Wi-Fi Network.", Toast.LENGTH_SHORT).show();
                                showDialog();
                                if (D) Log.d("WifiState", "Maybe first execution.1");
                            }

                            FunctionHandler.getInstance().getDataConnector().setFirstConnected(true);

                            break;
                        }
                        default:
//                            txtWifistate.setText("알수없음");
                            if (D) Log.d("WifiState", "unknown");
                            break;
                    }
                    break;
                }
                //네트워크 상태변화
                case WifiManager.NETWORK_STATE_CHANGED_ACTION: {
                    NetworkInfo info = (NetworkInfo) intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    //네트워크 상태값 가져오기
                    NetworkInfo.DetailedState state = info.getDetailedState();

                    if (D) Log.d("WifiState", "The network is changed. " + state);

                    if (state == NetworkInfo.DetailedState.CONNECTED) { //네트워크 연결

                        // 연속으로 2번 오는 경우가 있어 한번만 처리 되도록 보완.
                        if (wifiHandler == null) {
                            wifiHandler = new Handler(Looper.getMainLooper());
                        }
                        wifiHandler.removeCallbacks(wifiConnectRunnable);
                        wifiHandler.postDelayed(wifiConnectRunnable, 1000);
                        //onWifiConnected();

                    } else if (state == NetworkInfo.DetailedState.DISCONNECTED) { //네트워크 끊음
//                        isWifiConnected = false;
                        FunctionHandler.getInstance().getDataConnector().removeCallback();

                        mActivity.runOnUiThread(() -> {
                            binding.ivWifiImage.setImageResource(R.drawable.wifi_none_icon);
                            binding.tvWifiName.setText("-");
                        });

                        FunctionHandler.getInstance().getDataConnector().disconnectMqtt();
                    }

                    break;
                }
            }
        }
    };

    Handler wifiHandler = null;
    Runnable wifiConnectRunnable = new Runnable() {
        @Override
        public void run() {
            onWifiConnected();
        }
    };

    public void onWifiConnected() {
        final String ssid = getParsingSSID(getWifiConnector().getWifiManager().getConnectionInfo().getSSID());

        if (!(ssid.startsWith(mActivity.getResources().getString(R.string.pact_ssid_name1)) || ssid.startsWith(mActivity.getResources().getString(R.string.pact_ssid_name2)))) {
            mActivity.runOnUiThread(() -> {
                binding.ivWifiImage.setImageResource(R.drawable.wifi_none_icon);
                binding.tvWifiName.setText(ssid);
            });

            if (FunctionHandler.getInstance().getDataConnector().isMqttConnected()) {
                FunctionHandler.getInstance().getDataConnector().disconnectMqtt();
            }

            return;
        }

        binding.tvWifiName.post(() -> binding.tvWifiName.setText(ssid));

        FunctionHandler.getInstance().getDataConnector().setFirstConnected(true);

        //bindProcessToPact();

        saveSSID();

        // MQTT 연결
        FunctionHandler.getInstance().getDataConnector().setMqtt();

        if (FunctionHandler.getInstance().getFwFunc().isUpdating() &&
                FunctionHandler.getInstance().getFwFunc().getUpdateProgress().isShowing()) {
            FunctionHandler.getInstance().getFwFunc().getUpdateProgress().completeUpdate();
        }

        new Handler().postDelayed(() -> {
            clearAnimation();
            FunctionHandler.getInstance().getDataConnector().startDataTimeoutHandler();
        }, 3000);

        //FunctionHandler.getInstance().getDataConnector().startCheckMqttConnectionHandler();
    }

    private Runnable mScanWifiTimeoutRunnable = new Runnable() {
        @Override
        public void run() {
            mActivity.runOnUiThread(() -> {

                if (loading != null && loading.isShowing()) loading.dismiss();
                if (mProgress != null && mProgress.isShowing()) mProgress.dismiss();
                if (mWifiDialog != null && mWifiDialog.isShowing()) mWifiDialog.dismiss();

                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.wait_sec), Toast.LENGTH_SHORT).show();

            });

        }
    };

    public WifiFunc(InitActivity activity, final ActivityMainBinding binding) {
        super();

        mActivity = activity;
        this.binding = binding;

        mConnector = new WifiConnector(InitActivity.getContext());

        if (!isWifiNetwork) return;

        new Handler(Looper.getMainLooper()).post(() -> {
            mWifiDialog = new WiFiDialog(mActivity, binding);
            loading = new LoadingDefaultDialog(mActivity, binding);
        });

        SharedPreferences sp = mActivity.getSharedPreferences("AppInfo", Context.MODE_PRIVATE);
        mSSID = sp.getString("SSID", WifiManager.UNKNOWN_SSID);
        if (D) Log.d("SharedSSID", mSSID);

        ScanResults = null;

        mConnector.setLog(true);

        addEvents();
        //bindProcessToPact();
    }

    public BroadcastReceiver getWifiReceiver() {
        if (D) Log.d("getWifiReceiver", "in");
        return mReceiver;
    }

    public void connectWifi() {
        if (mSSID.equals(WifiManager.UNKNOWN_SSID))
            return;

        String ssid = getWifiConnector().getWifiManager().getConnectionInfo().getSSID();
        if (ssid.contains(mSSID))// || ssid.contains(mActivity.getResources().getString(R.string.pact_ssid_name1)) || ssid.contains(mActivity.getResources().getString(R.string.pact_ssid_name2)))
            return;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            startAnimation();
            FunctionHandler.getInstance().getDataConnector().showProgressDialog("Attempting to connect to stored SSID...");
            if (D) Log.d("WifiFunc", "conencting wifi");
            FunctionHandler.getInstance().getDataConnector().removeCallback();

            // TODO ?
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (!isDeviceWifiConnected())
                    Toast.makeText(mActivity, "Connection failed", Toast.LENGTH_SHORT).show();
                dismissLoadingDialog();
                FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
            }, 10000);
        }

        isFindPact = false;

        mConnector.showWifiList(new ShowWifiListener() {
            @Override
            public void onNetworksFound(WifiManager wifiManager, List<ScanResult> wifiScanResult) {

                for (ScanResult result : wifiScanResult) {
                    if (D) Log.d("WifiFound(List)", result.SSID);
                }

                for (ScanResult result : wifiScanResult) {

                    if (result.SSID.contains(mSSID)) {
                        if (D) Log.d("onNetworksFound", "result : " + result.SSID + " " + mSSID);
                        isFindPact = true;

                        mConnector.setScanResult(result, mActivity.getResources().getString(R.string.pact_pw));
                        mConnector.connectToWifi(new ConnectionResultListener() {
                            @Override
                            public void successfulConnect(String SSID) {

                                new Handler().postDelayed(() -> {

                                    clearAnimation();

                                    mActivity.runOnUiThread(() -> {
                                        if (D) Log.d("WifiFunc", "successfulConnect");
                                        binding.tvWifiName.setText(getParsingSSID(getWifiConnector().getWifiManager().getConnectionInfo().getSSID()));
                                    });

                                    saveSSID();

                                    Toast.makeText(mActivity, mActivity.getResources().getString(R.string.connected), Toast.LENGTH_SHORT).show();

                                    FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();

                                }, 1000);

                            }

                            @Override
                            public void errorConnect(int codeReason) {
                                clearAnimation();
                                if (D) Log.d("WifiFunc", "Can't connect wifi");
                                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.unable_to_connect_wifi), Toast.LENGTH_SHORT).show();
                                FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
                            }

                            @Override
                            public void onStateChange(SupplicantState supplicantState) {
                                if (D) Log.d("WifiFunc", "onStatgeChange");
                                FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
                            }
                        });

                        break;
                    }
                }

                if (!isFindPact) {
                    Toast.makeText(mActivity, mActivity.getResources().getString(R.string.unable_to_find_device) + " (" + mSSID + ")", Toast.LENGTH_LONG).show();

                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        showDialog();
                    }, 1000);
                }

                FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
            }

            @Override
            public void onNetworksFound(JSONArray wifiList) {
                if (D) Log.d("WifiFunc", "Scan => on NetworkFound");
                for (int i = 0; i < wifiList.length(); i++) {
                    try {
                        if (D) Log.d("WifiFound", wifiList.getJSONObject(i).getString("SSID"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
            }

            @Override
            public void errorSearchingNetworks(int errorCode) {
                if (D) Log.d("WifiFunc", "EfforSearchingNetwork");
                clearAnimation();
                FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.wifi_not_found), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showDialog() {
        if (D) Log.d(TAG, "showDialog()");
//        mActivity.requestPermission();

        if (!getWifiConnector().getWifiManager().isWifiEnabled()) {
            getWifiConnector().getWifiManager().setWifiEnabled(true);
        }

        if (mWifiDialog.isShowing()) return;
        if (loading.isShowing()) {
            loading.dismiss();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setCancelable(false);

        showLoadingDialog();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.loading_default);
        }

//        mProgress = builder.create();
//        mProgress.setCancelable(false);
//        mProgress.setCanceledOnTouchOutside(false);
//
//        mWifiDialog.setCancelable(false);
//        mWifiDialog.setCanceledOnTouchOutside(false);
//
//        mActivity.runOnUiThread(() -> {
//
//            try {
//                mProgress.show();
//
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//
//                if (mProgress != null && mProgress.isShowing()) mProgress.dismiss();
//
//                mProgress = new ProgressDialog(mActivity);
//                mProgress.show();
//
//            }
//        });

        if (mScanWifiTimeoutHandler == null) mScanWifiTimeoutHandler = new Handler();
        mScanWifiTimeoutHandler.postDelayed(mScanWifiTimeoutRunnable, mScanWifiTimeout);

        mConnector.showWifiList(new ShowWifiListener() {
            @Override
            public void onNetworksFound(WifiManager wifiManager, List<ScanResult> scanResults) {

                ScanResults = scanResults;

                for (ScanResult result : ScanResults) {
                    if (D) Log.d("WifiFound(List)", result.SSID);
                }

                for (int i = 0; i < ScanResults.size(); i++) {
                    String ssid = ScanResults.get(i).SSID;

                    if (ssid.contains(mActivity.getResources().getString(R.string.pact_ssid_name1)) || ssid.contains(mActivity.getResources().getString(R.string.pact_ssid_name2))) {

                        mWifiDialog.show();
                        mWifiDialog.refreshScanResults(scanResults);
                        mScanWifiTimeoutHandler.removeCallbacks(mScanWifiTimeoutRunnable);
                        mScanWifiTimeoutHandler.removeCallbacksAndMessages(null);
                        dismissLoadingDialog();
                        return;

                    }
                }

                mScanWifiTimeoutHandler.removeCallbacks(mScanWifiTimeoutRunnable);
                mScanWifiTimeoutHandler.removeCallbacksAndMessages(null);
                dismissLoadingDialog();
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.wifi_not_found), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworksFound(JSONArray wifiList) {
                if (D) Log.d("WifiFunc", "Scan => on NetworkFound");
                for (int i = 0; i < wifiList.length(); i++) {
                    try {
                        if (D) Log.d("WifiFound", wifiList.getJSONObject(i).getString("SSID"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
            }

            @Override
            public void errorSearchingNetworks(int errorCode) {
                mActivity.runOnUiThread(() -> {

                    dismissLoadingDialog();
                    mScanWifiTimeoutHandler.removeCallbacks(mScanWifiTimeoutRunnable);
                    mScanWifiTimeoutHandler.removeCallbacksAndMessages(null);

                });

                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.wifi_not_found), Toast.LENGTH_SHORT).show();

                mScanWifiTimeoutHandler.removeCallbacksAndMessages(null);
                if (ScanResults != null)
                    mWifiDialog.refreshScanResults(ScanResults);

            }
        });


//        new WifiDialog().execute();

    }

    public void addEvents() {
        new Handler(Looper.getMainLooper()).post(() -> {
            binding.ivWifiImage.setOnClickListener(v -> {
                //210628 수정
                Log.e("Permission", "GPS Permission request for Wifi");
                ActivityCompat.requestPermissions(MainActivity.getActivity()
                        , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                        , REQUEST_PERMISSIONS);

                //210628 수정
                if (isWifiNetwork)
                    showDialog();
                else if (!InitActivity.isApEnabled()) {
                    new ChangeIpDialog(mActivity, binding).show();
                }
            });
        });
    }

    public void saveSSID() {
        String ssid = getWifiConnector().getWifiManager().getConnectionInfo().getSSID();

        if (!ssid.contains(MainActivity.getActivity().getString(R.string.pact_ssid_name1)) && !ssid.contains(MainActivity.getActivity().getString(R.string.pact_ssid_name2)))
            return;

        ssid = ssid.replaceAll("\"", "");

        setSSID(ssid);

        SharedPreferences sp = MainActivity.getActivity().getSharedPreferences("AppInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("SSID", ssid);
        editor.apply();
    }

    public boolean isDeviceWifiConnected() {
        String ssid = mConnector.getWifiManager().getConnectionInfo().getSSID();
        String deviceWifiName1 = mActivity.getResources().getString(R.string.pact_ssid_name1);
        String deviceWifiName2 = mActivity.getResources().getString(R.string.pact_ssid_name2);

        if (ssid.contains(deviceWifiName1) || ssid.contains(deviceWifiName2)) return true;
        else return false;
    }

    private void startAnimation() {
        if (isPlayingAnim) {
            if (D) Log.d("getAnimation", "not null");
            return;
        }

        binding.ivWifiImage.post(() -> {
            animation.setDuration(500);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (D) Log.d("isPlaying", true + "");
                    isPlayingAnim = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            MainActivity.getBinding().ivWifiImage.startAnimation(animation);
        });
    }

    private void clearAnimation() {
        if (isPlayingAnim) {
            if (D) Log.d("clear Animation", "Clear");
            binding.ivWifiImage.post(() -> {
                binding.ivWifiImage.clearAnimation();
                isPlayingAnim = false;
            });
        }
    }

    public static String getParsingSSID(String ssid) {
        String pSSID = ssid.replace("PACT_AP_", "").replace("\"", "");
        return pSSID;
    }

    public static String getSSID() {
        if (mConnector == null)
            return "";
        try {
            mSSID = mConnector.getWifiManager().getConnectionInfo().getSSID();
            return mSSID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean checkPactConnection() {
        if (!mSSID.equals(MainActivity.getActivity().getResources().getString(R.string.pact_ssid_name1))
                && (mSSID.contains(MainActivity.getActivity().getResources().getString(R.string.pact_ssid_name1))
                || mSSID.contains(MainActivity.getActivity().getResources().getString(R.string.pact_ssid_name2)))
        ) {
            return true;
        } else return false;
    }

    public void unbindProcessToPact() {
        if (mConnectivityManager != null)
            mConnectivityManager.unregisterNetworkCallback(mNetworkCallback);
    }

    public void bindProcessToPact() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            //set the transport type do WIFI
            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);

            mConnectivityManager = (ConnectivityManager) mActivity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            mConnectivityManager.requestNetwork(builder.build(), mNetworkCallback);
        }
    }

    ConnectivityManager.NetworkCallback mNetworkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            if (D) Log.d("onAvailable", "in");

            LinkProperties linkProperties = mConnectivityManager.getLinkProperties(network);
            Log.d(TAG, linkProperties.toString());
            //Log.d(TAG, mConnectivityManager.getActiveNetworkInfo().toString());

            String ssid = getParsingSSID(getWifiConnector().getWifiManager().getConnectionInfo().getSSID());
            if (D) Log.d("onAvailable", ssid);

            boolean bCaDs = ssid != null && (ssid.startsWith("CA") || ssid.startsWith("DS"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (bCaDs && mConnectivityManager.getBoundNetworkForProcess() == network)
//                    return;

                if (Build.VERSION.RELEASE.equalsIgnoreCase("6.0")) {
                    if (!Settings.System.canWrite(mActivity)) {
                        Intent goToSettings = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        goToSettings.setData(Uri.parse("package:" + mActivity.getPackageName()));
                        mActivity.startActivity(goToSettings);
                    }
                }

//                mConnectivityManager.bindProcessToNetwork(null);

                if (bCaDs) {
                    mConnectivityManager.bindProcessToNetwork(network);
                    if (D) Log.d("onAvailable", "Bind Process To Network : PACT");
                }

            } else {
                //This method was deprecated in API level 23
                ConnectivityManager.setProcessDefaultNetwork(null);
                if (bCaDs) {
                    ConnectivityManager.setProcessDefaultNetwork(network);
                }
            }

            //mConnectivityManager.unregisterNetworkCallback(this);
        }
    };


    public void showLoadingDialog() {
        if (loading == null) loading = new LoadingDefaultDialog(mActivity, binding);
        loading.show();
    }

    public void dismissLoadingDialog() {

        if (loading == null) return;
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loading.dismiss();
        }, 1000);
    }

    public static boolean isWifiNetwork() {
        return isWifiNetwork;
    }

    public static void setWifiNetwork(boolean isWifiNet) {

        isWifiNetwork = isWifiNet;

    }

    public void setSSID(String SSID) {
        mSSID = SSID;
    }

    public WifiConnector getWifiConnector() {
        return mConnector;
    }

}
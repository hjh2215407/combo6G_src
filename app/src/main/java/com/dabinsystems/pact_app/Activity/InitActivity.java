package com.dabinsystems.pact_app.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.dabinsystems.pact_app.Data.LteInfoData;
import com.dabinsystems.pact_app.Dialog.HotspotDialog;
import com.dabinsystems.pact_app.Function.DataConnector;
import com.dabinsystems.pact_app.Function.Hotspot;
import com.dabinsystems.pact_app.Function.WifiFunc;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.Network.RequestHttpConnection;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Recorder.VideoRecService;
import com.dabinsystems.pact_app.Recorder.VideoRecorder;
import com.dabinsystems.pact_app.Util.FontUtil;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.LoadingMsgLayoutBinding;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.material.snackbar.Snackbar;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.LogManager;

import me.grantland.widget.AutofitTextView;

import static com.dabinsystems.pact_app.Dialog.WiFiDialog.MULTIPLE_PERMISSIONS;
import static com.dabinsystems.pact_app.Recorder.VideoRecorder.REQUEST_PERMISSIONS;

/*
Activity For MainActivity
 */

public class InitActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private final String TAG = "InitActivity";

    protected static Context mContext;
    protected static InitActivity mActivity;
    private static boolean isCompInit = false;
    public final static boolean isLogEnabled = false;

    Hotspot mHotspotManager;

    private boolean isBackPressed = false;
    private static boolean isPermitted = false;

    private final int mExitTime = 2000;

    protected VideoRecorder mMediaRecorder;

    protected static ActivityMainBinding binding;

    private static FadingCircle mFadingAnimationForLoading;

    private final static String[] mPermissionForMeas =
            {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
                    Manifest.permission.CHANGE_NETWORK_STATE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_SETTINGS,
                    Manifest.permission.WRITE_SECURE_SETTINGS,

                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
            };

    private FontUtil mFontUtil;
    private boolean mIsFirstRun;

    private FunctionHandler mFunctionHandler = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService();

        mIsFirstRun = true;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mFunctionHandler = FunctionHandler.getInstance(this, binding);
        //mFunctionHandler.getDataConnector();

        setValues();
        setUpEvents();

        isCompInit = true;

        //@@ input webserver IP Address
        /*String url = "https://www.naver.com";

        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();
        */
    }

    Intent serviceIntent;

    public void startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            serviceIntent = new Intent(this, VideoRecService.class);
            //startService(serviceIntent);
            startForegroundService(serviceIntent);
        }
    }

    public void stopService() {
        if (serviceIntent != null) {
            stopService(serviceIntent);
        }
    }

    public void setValues() {
        logMsg("InitActivity", "start setValues");

        requestPermission();
        initialize();
        updateTimeThread();

        new Thread(() -> {
            mFunctionHandler.getMainLineChart().initChart();
            mFunctionHandler.getGateLineChart().initChart();
            mFunctionHandler.getScatterChart().initChart();
            mFunctionHandler.getScatterChart2().initChart();
            mFunctionHandler.getCandleChartFunc().initChart();

            // [jigum] 2021-07-27 5G NR Scan Trace chart 추가
            mFunctionHandler.getNrScanTrace1CharFunc().initChart();
            mFunctionHandler.getNrScanTrace2CharFunc().initChart();

            logMsg("InitActivity", "end setValues");
        }).start();
    }

    public void requestPermission() {

        //210628 수정 permission
        checkPermissionF();
        //210628 수정 permission
        ActivityCompat.requestPermissions(this, mPermissionForMeas, MULTIPLE_PERMISSIONS);
        ActivityCompat.requestPermissions(this, mPermissionForMeas, REQUEST_PERMISSIONS);

    }

    public void initialize() {
        mContext = this;
        mActivity = this;

        mHotspotManager = new Hotspot(this);
        LteInfoData lteInfoData = DataHandler.getInstance().getLteInfoData();

        new Thread(() -> {
            mMediaRecorder = new VideoRecorder(this, binding);
            mMediaRecorder.onCreateMethod();

            /*if (lteInfoData.loadIniFileInAsset()) {
                lteInfoData.copyLteFile();
                lteInfoData.loadLteIniData();
            }*/

            lteInfoData.addLteList();

            new Handler(getMainLooper()).post(() -> {
                mFontUtil = new FontUtil(mActivity, binding);
                mFontUtil.initView();
            });
        }).start();
    }

    public Hotspot getHotspotManager() {
        return mHotspotManager;
    }

    public void setUpEvents() {


    }

    protected Handler mUpdateTimeHandler;

    protected void updateTimeThread() {
        mUpdateTimeHandler = new Handler(getMainLooper());
        mUpdateTimeHandler.postDelayed(updateTimeRunnable, 1000);
//        Thread timeThread = new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    while (true) {
//                        Thread.sleep(1000);
//                        runOnUiThread(() -> {
//                            updateTime();
//                        });
//                    }
//                } catch (
//                        InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        timeThread.start();
    }

    protected void stopUpdateTime() {
        mUpdateTimeHandler.removeCallbacks(updateTimeRunnable);
        mUpdateTimeHandler = null;
    }

    Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            updateTime();
            if (mUpdateTimeHandler != null)
                mUpdateTimeHandler.postDelayed(updateTimeRunnable, 1000);
        }
    };

    private void updateTime() {
        try {
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formatDate = timeFormat.format(date);
            binding.tvTimeText.setText(formatDate);

//        int count = mFunctionHandler.getDataConnector().getDrawCount();
//        binding.tvRightMenuTitle.setText(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPermissionF() {


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            int storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int gpsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);


            if (storagePermission == PackageManager.PERMISSION_DENIED) {
                //요청한 권한이 없을 때..거부일때...
                /* 사용자가 권한을 한번이라도 거부한 적이 있는 지 조사한다.
                 * 거부한 이력이 한번이라도 있다면, true를 리턴한다.
                 */
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //최초 실행이 아니고 단순 거절을 누른 경우
                    Log.e("Permission", "Storage Permission Denied");

                } else {
                    //최초 실행
                    if (mIsFirstRun = true) {
                        mIsFirstRun = false;
                        Log.e("Storage Permission", "최초 실행");
                        ActivityCompat.requestPermissions(this, mPermissionForMeas, REQUEST_PERMISSIONS);
                        return;
                    }
                }
                ActivityCompat.requestPermissions(this, mPermissionForMeas, REQUEST_PERMISSIONS);
            } else if (gpsPermission == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //최초 실행이 아니고 단순 거절을 누른 경우
                    Log.e("Permission", "Location Permission Denied");

                } else {
                    //최초 실행
                    if (mIsFirstRun = true) {
                        mIsFirstRun = false;
                        Log.e("Location Permission", "최초 실행");
                        ActivityCompat.requestPermissions(this, mPermissionForMeas, REQUEST_PERMISSIONS);
                        return;
                    }
                }
                ActivityCompat.requestPermissions(this, mPermissionForMeas, REQUEST_PERMISSIONS);
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        if (mMediaRecorder != null)
//            mMediaRecorder.onRequestPermissionResultMethod(requestCode, grantResults);

        switch (requestCode) {

            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission", "Mulitple Permission granted");

                    Handler handler = new Handler();
                    handler.postDelayed(() -> {

                        isPermitted = true;
                        VideoRecorder.setIsRecording(false);

                        new Thread(() -> {
                            Log.e("Permission", "Get cable info, add list (multiple permission)");
                            mFunctionHandler.getCableInfoFunc().addCableList();
                        }).start();

//                        isUsbTethering();

                        if (!isApEnabled() && !WifiFunc.isWifiNetwork()) {
                            logMsg("isAPEnabled", isApEnabled() + "");
                            HotspotDialog dialog = new HotspotDialog((MainActivity) this, binding);

                            new Thread(() -> {
                                mActivity.runOnUiThread(() -> {
                                    /*dialog.show("핫스팟 모드를 아래 그림처럼 ON으로 변경해주세요.");*/
                                    dialog.show("Turn on hotspot mode like a picture below");
                                    dialog.setImage(getResources().getDrawable(R.drawable.hotspot_on));
                                });
                            }).start();
//                            return;
                        }

                        if (WifiFunc.isWifiNetwork() && isPermitted()) {
                            regWifiState();
                            logMsg("InitActivity", "onRequestPermissionsResult : " + "init wifi");
                        }

                        mFunctionHandler.getDataConnector().startDataTimeoutHandler();

                        logMsg("permission", "granted");

                    }, 0);
                } else {

                }
                break;
            }
            //210628 수정중
            case REQUEST_PERMISSIONS: {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
                    return;
                }

                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        //Denied
                        Log.e("Permission", "Request Permission denied");

                        //권한 거절
                        Log.e("Permission", "Requested Permission : " + permissions[0]);
                        boolean showRatinale = shouldShowRequestPermissionRationale(permissions[0]);
                        if (!showRatinale) {
                            //다시 보지 않음
                            if (!mIsFirstRun) {
                                Log.e("Permission", permissions[0] + " : Never Ask Again");
                                //open app detail setting to access permission
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        } else if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[0])) {
                            //단순 거절의 경우
                            Log.e("Permission", "Storage Access permission Denied");
                            ActivityCompat.requestPermissions(this, mPermissionForMeas, REQUEST_PERMISSIONS);

                        } else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permissions[0])) {
                            //단순 거절의 경우
                            Log.e("Permission", "GPS Access Permission Denied");
                            ActivityCompat.requestPermissions(this, mPermissionForMeas, REQUEST_PERMISSIONS);

                        }
                    } else {
                        //Granted
                        Log.e("Permission", "Request Permission Granted");

                        Handler handler = new Handler();
                        handler.postDelayed(() -> {

                            isPermitted = true;
                            VideoRecorder.setIsRecording(false);

                            new Thread(() -> {
                                Log.e("Permission", "Get cable info, add list(Request Permission)");
                                mFunctionHandler.getCableInfoFunc().addCableList();
                            }).start();

//                        isUsbTethering();

                            if (!isApEnabled() && !WifiFunc.isWifiNetwork()) {
                                logMsg("isAPEnabled", isApEnabled() + "");
                                HotspotDialog dialog = new HotspotDialog((MainActivity) this, binding);

                                new Thread(() -> {

                                    mActivity.runOnUiThread(() -> {

                                        /*dialog.show("핫스팟 모드를 아래 그림처럼 ON으로 변경해주세요.");*/
                                        dialog.show("Turn on hotspot mode like a picture below");
                                        dialog.setImage(getResources().getDrawable(R.drawable.hotspot_on));

                                    });
                                }).start();
//                            return;
                            }

                            if (WifiFunc.isWifiNetwork() && isPermitted()) {
                                regWifiState();
                                logMsg("InitActivity", "onRequestPermissionsResult : " + "init wifi");
                            }

                            mFunctionHandler.getDataConnector().startDataTimeoutHandler();

                            logMsg("permission", "granted");
                        }, 0);
                    }
                }
            }
            //210628 수정중
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case VideoRecorder.REQUEST_CODE: {
                mMediaRecorder.onActivityResultMethod(requestCode, resultCode, data);
                break;
            }
            case 99: {

                break;
            }
        }

//        if (requestCode == 55) {
//
//            if (isApEnabled()) {
//                mHotspotManager.scan();
//            }
//        }

    }

    public static String[] getPermission() {
        return mPermissionForMeas;
    }

    public static boolean isCompleteInit() {
        return isCompInit;
    }

    public VideoRecorder getRecorder() {
        return mMediaRecorder;
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Background Issue 수정
        mFunctionHandler.getDataConnector().setmInitMqttTimeout(10000);
        mFunctionHandler.getDataConnector().setMqttTimeout(10000);
        mFunctionHandler.getDataConnector().setBackgroundReset(true);
        //Background Issue 수정

        mFunctionHandler.getWifiFunc().saveSSID();
        mFunctionHandler.getDataConnector().disconnectMqtt();
        mFunctionHandler.getDataConnector().removeCallback();
        mFunctionHandler.getDataConnector().setPrepare(false);

    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(() -> {

            mFunctionHandler.getDataConnector().registerRes();
            mFunctionHandler.getDataConnector().startDataTimeoutHandler(mFunctionHandler.getDataConnector().getInitMqttTimeout());
            //mFunctionHandler.getDataConnector().startCheckMqttConnectionHandler();
            mFunctionHandler.getDataConnector().setPrepare(true);
            mFunctionHandler.getDataConnector().pingTimer();

        }).start();

        logMsg("OnStart", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logMsg("OnResume", "onResume");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this) && !WifiFunc.isWifiNetwork()) {
                isUsbTethering();
                Toast.makeText(this, "onResume: Granted", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

//        mFunctionHandler.getDataConnector().setMqtt();
//        mFunctionHandler.getDataConnector().sendingReadyCmd(mStartDataTimeOut);

    }

    @Override
    protected void onDestroy() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mMediaRecorder.destroyMediaProjection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mFunctionHandler.getDataConnector().removeCallback();
            mFunctionHandler.getDataConnector().destroyMqtt();
            mFunctionHandler.getDataConnector().setNullReadyHandler();

            //reset calibration step
            mFunctionHandler.getCalibrationFunc().resetStep();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            stopService();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();

        logMsg("OnDestroy", "onDestroy");
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public static Activity getActivity() {
        return mActivity;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onBackPressed() {

        if (isBackPressed) {

            mFunctionHandler.getWifiFunc().saveSSID();

            finish();
        } else {

            isBackPressed = true;
            Toast.makeText(mContext, "Press again to exit", Toast.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(() -> {

                isBackPressed = false;

            }, mExitTime);

        }

    }

    public void disableButton() {

        binding.linTopMenu.setEnabled(false);
//        binding.tvVswr.setEnabled(false);
//        binding.tvSmallVswr.setEnabled(false);
//        binding.tvSmallReturnLoss.setEnabled(false);
//        binding.tvDtf.setEnabled(false);
//        binding.tvCableLoss.setEnabled(false);
        binding.tvMarker1.setEnabled(false);
        binding.tvMarker2.setEnabled(false);
        binding.tvMarker3.setEnabled(false);
        binding.tvMarker4.setEnabled(false);
        binding.tvMarker5.setEnabled(false);
        binding.tvBack.setEnabled(false);
        binding.ivWifiImage.setEnabled(false);
        binding.ivPreset.setEnabled(false);
        binding.tvAutoScale.setEnabled(false);
        binding.ivAutoScale.setEnabled(false);
        binding.ivGPS.setEnabled(false);
        binding.ivScreenshot.setEnabled(false);

    }

    public void enableButton() {

        binding.linTopMenu.setEnabled(true);
//        binding.tvVswr.setEnabled(true);
//        binding.tvSmallVswr.setEnabled(true);
//        binding.tvSmallReturnLoss.setEnabled(true);
//        binding.tvDtf.setEnabled(true);
//        binding.tvCableLoss.setEnabled(true);
        binding.tvMarker1.setEnabled(true);
        binding.tvMarker2.setEnabled(true);
        binding.tvMarker3.setEnabled(true);
        binding.tvMarker4.setEnabled(true);
        binding.tvMarker5.setEnabled(true);
        binding.tvBack.setEnabled(true);
        binding.ivWifiImage.setEnabled(true);
        binding.ivPreset.setEnabled(true);
        binding.tvAutoScale.setEnabled(true);
        binding.ivAutoScale.setEnabled(true);
        binding.ivGPS.setEnabled(true);
        binding.ivScreenshot.setEnabled(true);

    }

    public void startLoadingAnim() {
        runOnUiThread(() -> {
            if (mFadingAnimationForLoading == null) {
                mFadingAnimationForLoading = new FadingCircle();
                mFadingAnimationForLoading.setBounds(0, 0, 100, 100);
                mFadingAnimationForLoading.setColor(MainActivity.getActivity().getResources().getColor(R.color.app_title));
            }

            if (mFadingAnimationForLoading.isRunning()) return;

            LoadingMsgLayoutBinding loadingLayout = binding.loadingWindowLayout;
            AutofitTextView loadingText = loadingLayout.tvloadingWindow;

            if (loadingText.getVisibility() == View.GONE)
                loadingText.setVisibility(View.VISIBLE);

            loadingText.setCompoundDrawables(null, null, mFadingAnimationForLoading, null);

            if (mFadingAnimationForLoading != null && !mFadingAnimationForLoading.isRunning())
                mFadingAnimationForLoading.start();
        });
    }

    public void stopLoadingAnim() {
        MainActivity.getActivity().runOnUiThread(() -> {
            if (mFadingAnimationForLoading == null) {
                return;
            }

            if (mFunctionHandler.getDataConnector().getBackgroundReset()) {
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    mFunctionHandler.getDataConnector().setBackgroundReset(false);
                }, 20000);
            }

            LoadingMsgLayoutBinding loadingLayout = binding.loadingWindowLayout;
            AutofitTextView loadingText = loadingLayout.tvloadingWindow;

            if (loadingText.getVisibility() == View.VISIBLE)
                loadingText.setVisibility(View.GONE);

            if (mFadingAnimationForLoading != null && mFadingAnimationForLoading.isRunning()) {
                mFadingAnimationForLoading.stop();
                mFadingAnimationForLoading = null;
            }
        });
    }

    private static boolean isAirplaneModeOn(Context context) {

        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

    }

    public static boolean isApEnabled() {

        WifiManager wifi = (WifiManager) MainActivity.getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Method[] wmMethods = wifi.getClass().getDeclaredMethods();
        for (Method method : wmMethods) {
            if (method.getName().equals("isWifiApEnabled")) {

                try {
                    boolean isWifiAPEnabled = (boolean) method.invoke(wifi);
                    return isWifiAPEnabled;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return false;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return false;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean isUsbTethering() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!intf.isLoopback()) {
                        if (intf.getName().contains("rndis")) {
                            logMsg("isUsbTethering", "Enable usb tethering successfully!");
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logMsg("isUsbTethering", "Enable usb tethering failed!");
        return false;
    }

    public static void setInformationText(AutofitTextView textView, float val) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -9999.99f || val == -999999f || val == -9999f) textView.setText("--.--");
            else textView.setText(Utils.formatNumber(val, 2, false));

        });

    }

    public static void setInformationText(AutofitTextView textView, String val) {

        MainActivity.getActivity().runOnUiThread(() -> {

            textView.setText(val);

        });

    }

    public static void setInformationText(AutofitTextView textView, String val, int color) {

        MainActivity.getActivity().runOnUiThread(() -> {

            textView.setText(val);
            textView.setTextColor(mActivity.getResources().getColor(color));

        });

    }

    @SuppressLint("SetTextI18n")
    public static void setInformationText(AutofitTextView textView, float val, String unit) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -999999 || val == -9999) textView.setText("--.--");
            else textView.setText(Utils.formatNumber(val, 2, false) + unit);

        });

    }

    public static void setInformationText(AutofitTextView textView, float val, int div, String unit) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -999999) textView.setText("--.--");
            else textView.setText(Utils.formatNumber(val / div, 2, false) + unit);

        });

    }

    public static boolean isPermitted() {
        return isPermitted;
    }

    public static ActivityMainBinding getBinding() {
        return binding;
    }

    public static void logMsg(String title, String msg) {

        if (!isLogEnabled)
            return;

        Log.d(title, msg);

    }

//    public static void setLogEnabled(boolean isEnable) {
//        isLogEnabled = isEnable;
//    }

    public static SharedPreferences getSpData() {

        SharedPreferences sp = mActivity.getSharedPreferences("AppInfo", MODE_PRIVATE);
        return sp;

    }

    public static String getFrequencyWithUnit(double mhzFreq) {

        String unit = "";
        double value = mhzFreq;

        if (mhzFreq >= 1000) {

            unit = "GHz";
            value = mhzFreq / 1000d;
            value = Math.round(value * 1000d) / 1000d;

        } else if (mhzFreq >= 1 && mhzFreq < 1000) {

            unit = "MHz";
            value = Math.round(value * 1000d) / 1000d;

        } else if (mhzFreq >= 0.001 && mhzFreq < 1) {

            unit = "KHz";
            value = mhzFreq * 1000d;
            value = Math.round(value * 1000d) / 1000d;

        } else if (mhzFreq < 0.001) {
            unit = "Hz";
            value = mhzFreq * 1000d * 1000d;
            value = Math.round(value);
        }

        String result = value + " " + unit;

        return result;

    }

    public static String getCurrentTime(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);

        return time;
    }

    /*
     *
     * Report 측정시 사용되는 포맷은 "yyyy년 MM월 dd일 HH시 mm분 ss초" 임
     *
     * */
    public static String getTimeDiff(String format, String date1, String date2) {

        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        try {
            Date dateFormat1 = simpleFormat.parse(date1);
            Date dateFormat2 = simpleFormat.parse(date2);

            long dateToMiliSeconds1 = dateFormat1.getTime();
            long dateToMiliSeconds2 = dateFormat2.getTime();

            long diffTime = Math.abs(dateToMiliSeconds2 - dateToMiliSeconds1);

            SimpleDateFormat diffFormat = new SimpleDateFormat("mm분 ss초");
            Date diffDate = new Date(diffTime);
            String diffResult = diffFormat.format(diffDate);

            return diffResult;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "N/A";

    }

    /*
     *
     * Report 측정시 사용되는 포맷은 "yyyy년 MM월 dd일 HH시 mm분 ss초" 임
     *
     * */
    public static String getNewFomatFromOldFormat(String oldFormatDate, String oldFormat, String newFormat) {

        try {
            SimpleDateFormat oldDateFormat = new SimpleDateFormat(oldFormat);
            Date oldDate = oldDateFormat.parse(oldFormatDate);
            long oldTime = oldDate.getTime();

            SimpleDateFormat newDateFormat = new SimpleDateFormat(newFormat);
            Date newDate = new Date(oldTime);

            String result = newDateFormat.format(newDate);

            return result;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "N/A";

    }


    final Object wifiRegLock = new Object();
    boolean isWifiReg = false;

    protected void regWifiState() {
        synchronized (wifiRegLock) {
            Log.e(TAG, "regWifiState()");

            if (isWifiReg) {

                return;
            }

            IntentFilter filter = new IntentFilter();
            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
            filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
            registerReceiver(mFunctionHandler.getWifiFunc().getWifiReceiver(), filter);
            isWifiReg = true;
        }
    }

    protected void unRegWifiState() {
        synchronized (wifiRegLock) {
            Log.e(TAG, "unRegWifiState()");

            if (isWifiReg) {
                unregisterReceiver(mFunctionHandler.getWifiFunc().getWifiReceiver());
                isWifiReg = false;
            }
//        if (WifiFunc.isWifiNetwork()) {
//            WifiFunc wifiFunc = FunctionHandler.getInstance().getWifiFunc();
//            unregisterReceiver(wifiFunc.getWifiReceiver());
//        }
        }
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result;

            RequestHttpConnection con = new RequestHttpConnection();

            result = con.request(url, values);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("onPostExe", s);
            /// do something
        }
    }
}

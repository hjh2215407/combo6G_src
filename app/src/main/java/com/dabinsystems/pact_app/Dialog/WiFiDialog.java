package com.dabinsystems.pact_app.Dialog;

import android.Manifest;
import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.WifiFunc;
import com.dabinsystems.pact_app.List.Adapter.AccessPointAdapter;
import com.dabinsystems.pact_app.List.Adapter.WrapContentLinearLayoutManager;
import com.dabinsystems.pact_app.List.Item.AccessPointItem;
import com.dabinsystems.pact_app.Network.WifiConnector.WifiConnector;
import com.dabinsystems.pact_app.Network.interfaces.ConnectionResultListener;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.WifiDialogBinding;

import java.util.ArrayList;
import java.util.List;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;


public class WiFiDialog extends CustomBaseDialog {
    private final String TAG = "WifiDialog";
    private final boolean D = true;

    private final Context parentContext;
    private final ActivityMainBinding mainBinding;

    private WifiDialogBinding binding;
    private WiFiDialog dialog;
    private String mSelectedSSID = "";
    //private String mSelectedBSSID = "";
    private boolean isPlayingAnim = false;

    public WiFiDialog(Context context, ActivityMainBinding mainBinding) {
        super(context, R.style.AppFullScreenTheme);
        parentContext = context;
        this.mainBinding = mainBinding;
    }

    ArrayList<AccessPointItem> mAccessPoints;
    WrapContentLinearLayoutManager mLinearLayoutManager;
    AccessPointAdapter mAccessPointAdapter;
    List<ScanResult> mScanResults;
    /* Location permission 을 위한 필드 */

    public static final int MULTIPLE_PERMISSIONS = 20; // code you want.

    String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_MULTICAST_STATE
    };
    /* Location permission 을 위한 필드 */


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(LayoutInflater.from(parentContext), R.layout.wifi_dialog, null, false);
        setContentView(binding.getRoot());

        //WifiManager wifiManager = (WifiManager) parentContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mLinearLayoutManager = new WrapContentLinearLayoutManager(parentContext, LinearLayoutManager.VERTICAL, false);
        binding.accessPointRecyclerView.setLayoutManager(mLinearLayoutManager);
        if (mAccessPoints == null) mAccessPoints = new ArrayList<>();

        setValues();
        setUpEvents();
    }

    private void setValues() {

        dialog = this;

        binding.tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, getContext()));
        binding.tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, getContext()));
        binding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, getContext()));

        binding.tvApply.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, getContext()));
        binding.tvApply.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(17, getContext()));
        binding.tvApply.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(17, getContext()));

        binding.tvCancel.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, getContext()));
        binding.tvCancel.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(17, getContext()));
        binding.tvCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(17, getContext()));

    }

    public void setUpEvents() {

        binding.parent.setOnClickListener(v -> dismiss());

        binding.tvApply.setOnClickListener(v -> {

            if (mAccessPointAdapter == null || mAccessPointAdapter.getSelectedItem() == null ||
                    mAccessPointAdapter.getSelectedItem().getSsid() == null) {

                Toast.makeText(parentContext, "Please try connecting again.", Toast.LENGTH_SHORT).show();
                return;
            }

            mSelectedSSID = mAccessPointAdapter.getSelectedItem().getSsid();
            //mSelectedBSSID = mAccessPointAdapter.getSelectedItem().getBssid();

            if (D) Log.d(TAG, mSelectedSSID + "");

            connectWifi();

//                Toast.makeText(MainActivity.getActivity(), "Connecting to selected network...", Toast.LENGTH_SHORT).show();

//                if(!WifiFunc.getWifiConnector().getWifiManager().getConnectionInfo().getSSID().contains(mSelectedSSID)) {
//                    connectWifi();
//                    Toast.makeText(MainActivity.getActivity(), "Connecting to selected network...", Toast.LENGTH_SHORT).show();
//                } else Toast.makeText(parentContext, "Already connected.", Toast.LENGTH_SHORT).show();

            dismiss();
            FunctionHandler.getInstance().getWifiFunc().dismissLoadingDialog();
        });

        binding.tvCancel.setOnClickListener(v -> {
            dismiss();
            FunctionHandler.getInstance().getWifiFunc().dismissLoadingDialog();
        });

    }

    public void getWiFiScanResult() {
        if (D) Log.d(TAG, "getWiFiScanResult IN");

        if (mAccessPoints != null && mAccessPoints.size() != 0) {
            mAccessPoints.clear();
            if (D) Log.d(TAG, "mAccessPoints clear");
        }

        if (mAccessPoints == null) {
            mAccessPoints = new ArrayList<>();
        }

        if (D) Log.d(TAG, "mScanResults Size : " + mScanResults.size());

        for (int i = 0; i < mScanResults.size(); i++) {
            ScanResult result = mScanResults.get(i);
            if (D) Log.d(TAG, "mScanResults result : " + result.SSID);
            if ((result.SSID.contains(parentContext.getResources().getString(R.string.pact_ssid_name1)) || result.SSID.contains(parentContext.getResources().getString(R.string.pact_ssid_name2)))) {
                mAccessPoints.add(new AccessPointItem(result.SSID, result.BSSID, String.valueOf(result.level), result));
            }
//            mAccessPoints.add(new AccessPointItem(result.SSID, result.BSSID, String.valueOf(result.level)));
        }

        if (binding == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parentContext), R.layout.wifi_dialog, null, false);
            setContentView(binding.getRoot());
        }

        if (binding != null && binding.accessPointRecyclerView != null) {
            binding.accessPointRecyclerView.post(() -> {
                if (mAccessPointAdapter == null)
                    mAccessPointAdapter = new AccessPointAdapter(mAccessPoints, parentContext, dialog);
                binding.accessPointRecyclerView.setAdapter(mAccessPointAdapter);
                mAccessPointAdapter.notifyDataSetChanged();
            });
        } else getWiFiScanResult();
    }

    public void refreshScanResults(List<ScanResult> scanResults) {
        mScanResults = scanResults;
        getWiFiScanResult();
    }

    public void startAnimation() {

        if (isPlayingAnim)
            return;

        mainBinding.ivWifiImage.post(() -> {
            final Animation animation = new AlphaAnimation(1, 0);
            animation.setDuration(500);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    isPlayingAnim = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            mainBinding.ivWifiImage.startAnimation(animation);
        });
    }

    public void clearAnimation() {
        if (isPlayingAnim) {
            //if (InitActivity.isLogEnabled) Log.d("clear Animation", "Clear");
            mainBinding.ivWifiImage.post(() -> {
                mainBinding.ivWifiImage.clearAnimation();
                isPlayingAnim = false;
            });
        }
    }

    private void connectWifi() {
        FunctionHandler.getInstance().getDataConnector().removeCallback();

        WifiConnector wifiConnector = FunctionHandler.getInstance().getWifiFunc().getWifiConnector();

        wifiConnector.setScanResult(getAdapter().getSelectedItem().getScanResult(), getContext().getResources().getString(R.string.pact_pw));

        if (!wifiConnector.isAlreadyConnected(getAdapter().getSelectedItem().getBssid())) {

            startAnimation();

            FunctionHandler.getInstance().getDataConnector().showProgressDialog("Trying to connect selected WiFi AP.");

            wifiConnector.connectToWifi(new ConnectionResultListener() {
                @Override
                public void successfulConnect(String SSID) {

                    new Handler().postDelayed(() -> {

                        clearAnimation();

                        mainBinding.tvWifiName.post(() -> mainBinding.tvWifiName.setText(
                                WifiFunc.getParsingSSID(wifiConnector.getWifiManager().getConnectionInfo().getSSID())
                        ));

                        FunctionHandler.getInstance().getWifiFunc().setSSID(wifiConnector.getWifiManager().getConnectionInfo().getSSID());
                        FunctionHandler.getInstance().getWifiFunc().saveSSID();
                        FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
                        Toast.makeText(getContext(), "WiFi Connected.", Toast.LENGTH_SHORT).show();

                    }, 3000);

                    FunctionHandler.getInstance().getDataConnector().startDataTimeoutHandler();

                }

                @Override
                public void errorConnect(int codeReason) {
                    clearAnimation();
                    FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
                    //WiFi에 연결에 실패했습니다. > 
                    Toast.makeText(parentContext, "WiFi Connection Failed.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStateChange(SupplicantState supplicantState) {

                }
            });

        } else {
            FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
            clearAnimation();
            //이미 WiFi에 연결되어있습니다.
            Toast.makeText(parentContext, "Wifi Already Connected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void show() {
        super.show();
    }

    public AccessPointAdapter getAdapter() {
        return mAccessPointAdapter;
    }

}

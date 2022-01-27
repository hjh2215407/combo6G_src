package com.dabinsystems.pact_app.Dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Function.WifiFunc;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.SelectNetworkLayoutBinding;

public class SelectNetworkDialog extends CustomBaseDialog {

    private SelectNetworkLayoutBinding mSelectNetworkBinding;
    private Activity mActivity;

    public SelectNetworkDialog(Activity activity) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
//        mExcelEditor = new ExcelEditor("data.xlsx", Environment.getExternalStorageDirectory() + File.separator + "PACT/SaveData" + File.separator);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSelectNetworkBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.select_network_layout, null, false);
        setContentView(mSelectNetworkBinding.getRoot());

        setValues();
        addEvents();

    }

    private void setValues() {
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        mSelectNetworkBinding.rbWiFi.setChecked(true);
    }

    public void addEvents() {

        mSelectNetworkBinding.tvStart.setOnClickListener(v -> {

            if (mSelectNetworkBinding.rbWiFi.isChecked()) {
                WifiFunc.setWifiNetwork(true);
            } else if (mSelectNetworkBinding.rbTethering.isChecked()) {
                WifiFunc.setWifiNetwork(false);
            }

            mActivity.runOnUiThread(() -> {
                Intent intent = new Intent(mActivity, MainActivity.class);
                mActivity.startActivity(intent);
                mActivity.finish();
            });

            dismiss();

        });

    }

    @Override
    public void show() {
        super.show();
    }

}




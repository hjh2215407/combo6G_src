package com.dabinsystems.pact_app.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.LoadingTrackingHoldoverBinding;

public class LoadingTrackingDialog extends CustomBaseDialog {

    //private LayoutLoadingDialogBinding mLoadingBinding;
    private LoadingTrackingHoldoverBinding mLoadingBinding;
    private ActivityMainBinding mMainBinding;
    private InitActivity mActivity;

    public LoadingTrackingDialog(InitActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.loading_tracking_holdover, null, false);
        setContentView(mLoadingBinding.getRoot());

        setValues();
        addEvents();

    }

    public void setMessage(String message) {

        mActivity.runOnUiThread(() -> {

            if (!isShowing()) {
                show();
            }

            mLoadingBinding.tvMessage.setText(message);

        });
    }

    private void setValues() {
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    public void addEvents() {

    }


    @Override
    public void show() {
        super.show();
    }

}




package com.dabinsystems.pact_app.Dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrTaeData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.LayoutLoadingDialogBinding;

public class LoadingMessageDialog extends CustomBaseDialog {

    private LayoutLoadingDialogBinding mLoadingBinding;
    private ActivityMainBinding mMainBinding;
    private InitActivity mActivity;

    public LoadingMessageDialog(InitActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.layout_loading_dialog, null, false);
        setContentView(mLoadingBinding.getRoot());

        setValues();
        addEvents();

    }

    public void setMessage(String message) {

        mActivity.runOnUiThread(() -> {

            if (!isShowing())
                show();

            mLoadingBinding.tvMessage.setText(message);

        });
    }

    private void setValues() {
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    public void addEvents() {

        mLoadingBinding.btnCancel.setOnClickListener(v -> {

            NrTaeData taeData = DataHandler.getInstance().getNrData().getTaeInfoData();

            if(taeData.getTeaMeasMode() == NrTaeData.TAE_MEAS_MODE.ON) {

                taeData.setTeaMeasMode(NrTaeData.TAE_MEAS_MODE.OFF);
                ViewHandler.getInstance().getTaeView().update();

            }
            dismiss();

        });

    }


    @Override
    public void show() {
        super.show();
    }

}




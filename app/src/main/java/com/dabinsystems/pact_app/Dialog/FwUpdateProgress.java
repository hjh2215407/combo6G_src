package com.dabinsystems.pact_app.Dialog;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.UpdateDialogLayoutBinding;

import org.json.JSONObject;

public class FwUpdateProgress extends CustomBaseDialog {

    private UpdateDialogLayoutBinding mUpdateBinding;
    private ActivityMainBinding mMainBinding;

    private InitActivity mActivity;
    private AlertDialog.Builder mDialogBuilder;
    private AlertDialog pDialog;

    private JSONObject json;

    public FwUpdateProgress(InitActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUpdateBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.update_dialog_layout, null, false);
        setContentView(mUpdateBinding.getRoot());

        setValues();
        addEvents();

    }

    private void setValues() {
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    public void addEvents() {

        mUpdateBinding.tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());
                FunctionHandler.getInstance().getDataConnector().startDataTimeoutHandler();
                dismiss();
            }
        });

    }

    public void setProgress(int progress) {

        new Thread(() -> {

            mActivity.runOnUiThread(() -> {
                try {
//            InitActivity.logMsg("updateProgress", progress + "");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mUpdateBinding.updateProgressbar.setProgress(progress, true);
                    } else {
                        mUpdateBinding.updateProgressbar.setProgress(progress);
                    }
                    mUpdateBinding.updateProgressbar.invalidate();
                    mUpdateBinding.tvProgressValue.setText(progress + "%");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            });

        }).start();


    }

    public void setDescription(String message) {
        mActivity.runOnUiThread(() -> {
            try {
                mUpdateBinding.tvDescription.setText(message);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
    }

    public void completeUpdate() {
        mActivity.runOnUiThread(() -> {
            try {
                mUpdateBinding.tvComplete.setText("Complete");
                mUpdateBinding.tvComplete.setVisibility(View.VISIBLE);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

    }

    public void failUpdate() {
        mActivity.runOnUiThread(() -> {
            try {
                mUpdateBinding.tvDescription.setText("Update failed.");
                mUpdateBinding.tvComplete.setText("OK");
                mUpdateBinding.tvComplete.setVisibility(View.VISIBLE);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void show() {
        super.show();
        mUpdateBinding.tvComplete.setVisibility(View.GONE);
    }

}




package com.dabinsystems.pact_app.Dialog;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
import com.dabinsystems.pact_app.databinding.MacroDialogLayoutBinding;

import org.json.JSONObject;

public class AutoMeasureProgress extends CustomBaseDialog {

    private MacroDialogLayoutBinding mMacroBinding;
    private ActivityMainBinding mMainBinding;

    private MainActivity mActivity;

    public AutoMeasureProgress(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMacroBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.macro_dialog_layout, null, false);
        setContentView(mMacroBinding.getRoot());

        setValues();
        addEvents();

    }

    private void setValues() {
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    public void addEvents() {


    }

    public void setProgress(int progress) {

        try {
//            InitActivity.logMsg("updateProgress", progress + "");
            mMacroBinding.tvProgressValue.setText(progress + "%");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mMacroBinding.autoMeasureProgressbar.setProgress(progress, true);
            } else {
                mMacroBinding.autoMeasureProgressbar.setProgress(progress);
            }
            mMacroBinding.autoMeasureProgressbar.invalidate();

        } catch (NullPointerException e) {
            InitActivity.logMsg("setProgress", "Nullpointer Exception");
        }
    }

    public void setTitle(String description) {
        if(isShowing()) {
            new Handler(Looper.getMainLooper()).post(() -> {
                mMacroBinding.tvTitle.setText(description);
            });
        }
    }

    public void setDescription(String message) {
        mActivity.runOnUiThread(() -> {
            try {
                mMacroBinding.tvDescription.setText(message);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
    }

    public void completeMeasurement() {
        mActivity.runOnUiThread(() -> {
            try {
                mMacroBinding.btnComplete.setVisibility(View.VISIBLE);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void show() {
        super.show();

        if (MacroDialog.getInstance().getSelectedDevice() == MacroDialog.MEASURE_DEVICE.DEVICE1) {

            mActivity.runOnUiThread(() -> {

                mMacroBinding.btnComplete.setText("Next");

            });

            mMacroBinding.btnComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MacroDialog.getInstance().setDevice(MacroDialog.MEASURE_DEVICE.DEVICE2);
                    DeviceImageDialog imageDialog = new DeviceImageDialog(mActivity, mMainBinding);
                    //수정
                    /*imageDialog.show("장치2에 연결 후 OK버튼을 눌러주세요.");*/
                    imageDialog.show("After connect to device 2, press OK button");
                    imageDialog.setImage(MainActivity.getActivity().getResources().getDrawable(R.drawable.device2));
                    dismiss();
                }
            });

        } else {

            mActivity.runOnUiThread(() -> {

                mMacroBinding.btnComplete.setText("Complete");

            });

            mMacroBinding.btnComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }

        mMacroBinding.btnComplete.setVisibility(View.GONE);
    }

}




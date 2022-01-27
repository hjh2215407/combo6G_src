package com.dabinsystems.pact_app.Dialog;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.ImageViewBinding;

public class DeviceImageDialog extends CustomBaseDialog {

    private ImageViewBinding mLayoutBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    public DeviceImageDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.image_view, null, false);
        setContentView(mLayoutBinding.getRoot());

        setValues();
        addEvents();

    }

    private void setValues() {

        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

    }

    public void addEvents() {

        mLayoutBinding.btnOk.setOnClickListener(v -> {

            dismiss();
            MacroDialog dialog = MacroDialog.getInstance();
            MacroDialog.MEASURE_DEVICE device = dialog.getSelectedDevice();
            if (device == MacroDialog.MEASURE_DEVICE.DEVICE1)
                dialog.show();
            else
                dialog.start();

            dialog.getAutoMeasureWindow().setTitle("자동 측정중...");

        });

    }

    public void setImage(Drawable drawable) {

        mActivity.runOnUiThread(() -> {
            try {
                mLayoutBinding.ivDeviceView.setImageDrawable(drawable);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

    }

    public void show(String message) {
        show();
        new Handler(Looper.getMainLooper()).post(() -> {
            mLayoutBinding.tvTitle.setText(message);
        });

    }

    @Override
    public void show() {
        super.show();

    }

}




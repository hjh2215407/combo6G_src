package com.dabinsystems.pact_app.Dialog;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.ImageViewBinding;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class HotspotDialog extends CustomBaseDialog {

    private ImageViewBinding mLayoutBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    public HotspotDialog(MainActivity activity, ActivityMainBinding binding) {
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

        mLayoutBinding.tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mLayoutBinding.tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(17, mActivity));
        mLayoutBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(17, mActivity));

        mLayoutBinding.btnOk.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(17, mActivity));

    }

    public void addEvents() {

        mLayoutBinding.btnOk.setOnClickListener(v -> {

            dismiss();
            moveToConfiguration();

        });

    }

    public void moveToConfiguration() {
//        Intent intent= new Intent(Settings.ACTION_DATA_USAGE_SETTINGS);
        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.TetherSettings");
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivityForResult(intent,55);   //
        dismiss();
    }

    public void setImage(Drawable drawable) {

        mActivity.runOnUiThread(() -> {
            try {
                mLayoutBinding.ivDeviceView.setImageDrawable(drawable);
                mLayoutBinding.ivDeviceView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
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




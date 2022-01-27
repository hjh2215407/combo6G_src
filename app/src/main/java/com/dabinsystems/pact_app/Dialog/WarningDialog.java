package com.dabinsystems.pact_app.Dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.WarningDialogBinding;

public class WarningDialog extends CustomBaseDialog {

    private WarningDialogBinding mWarningBinding;
    private String mMainMessage = null;
    private String mSubMessage = null;
    private ActivityMainBinding mMainBinding;
    private Activity mActivity;

    public WarningDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    public WarningDialog(Activity activity, ActivityMainBinding binding, String mainMsg, String subMsg) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;
        mMainMessage = mainMsg;
        mSubMessage = subMsg;

    }

    public WarningDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWarningBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.warning_dialog, null, false);
        setContentView(mWarningBinding.getRoot());

        viewSetting();
        addEvents();

    }

    private void viewSetting() {


    }

    public void setTitleBackground(Drawable color) {

        if(!isShowing()) return;

        new Handler(Looper.getMainLooper()).post(() -> {

            mWarningBinding.linTitle.setBackground(color);

        });

    }

    public void setTitleBackgroundResource(final int resId) {

        if(!isShowing()) return;

        new Handler(Looper.getMainLooper()).post(() -> {

            mWarningBinding.linTitle.setBackgroundResource(resId);

        });

    }

    public void setMessage(String main, String sub) {

        if(!isShowing()) return;

        new Handler(Looper.getMainLooper()).post(() -> {

            mWarningBinding.tvWarningMsg.setText(main);
            mWarningBinding.tvSubMsg.setText(sub);

        });

    }

    public void setEventListener(View.OnClickListener listener) {

        if(!isShowing()) return;

        mWarningBinding.tvOk.setOnClickListener(listener);
        mWarningBinding.linParent.setOnClickListener(listener);

    }

    public void addEvents() {

        mWarningBinding.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        mWarningBinding.linParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void show() {
        super.show();

        InitActivity.logMsg("WarningDialog", "show");

        if (mMainMessage == null) return;

        new Handler(Looper.getMainLooper()).post(() -> {

            mWarningBinding.tvWarningMsg.setText(mMainMessage);
            mWarningBinding.tvSubMsg.setText(mSubMessage);

        });
    }
}

package com.dabinsystems.pact_app.Dialog;

import android.app.Activity;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.BatteryWarningDialogBinding;

public class BatteryWarningDialog extends CustomBaseDialog {

    private BatteryWarningDialogBinding mBatteryBinding;
    private ActivityMainBinding mMainBinding;
    private InitActivity mActivity;
    private int mBatteryLev = 100;


    public BatteryWarningDialog(InitActivity activity, ActivityMainBinding binding, int batteryLev) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;
        mBatteryLev = batteryLev;

    }

    public BatteryWarningDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBatteryBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.battery_warning_dialog, null, false);
        setContentView(mBatteryBinding.getRoot());

        viewSetting();
        addEvents();

    }

    private void viewSetting() {

//        mBatteryBinding.tvOk.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
//        mBatteryBinding.tvOk.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));
//        mBatteryBinding.tvOk.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));
//
//        mBatteryBinding.tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
//        mBatteryBinding.tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));
//        mBatteryBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));
//
//        mBatteryBinding.tvLowBattery.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
//        mBatteryBinding.tvLowBattery.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(22, mActivity));
//        mBatteryBinding.tvLowBattery.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(22, mActivity));
//
//        mBatteryBinding.tvBatteryLev.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
//        mBatteryBinding.tvBatteryLev.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(22, mActivity));
//        mBatteryBinding.tvBatteryLev.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(22, mActivity));
//
//        mBatteryBinding.tvPleaseCharge.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
//        mBatteryBinding.tvPleaseCharge.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(18, mActivity));
//        mBatteryBinding.tvPleaseCharge.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(18, mActivity));

        if(mBatteryLev == 10) {

            mBatteryBinding.linTitle.setBackground(mActivity.getResources().getDrawable(R.drawable.battery_warning_title_blue_border));
            mBatteryBinding.tvBatteryLev.setText(mActivity.getResources().getText(R.string.battery_level_10_message));

        } else if(mBatteryLev == 5) {

            mBatteryBinding.linTitle.setBackground(mActivity.getResources().getDrawable(R.drawable.battery_warning_title_red_border));
            mBatteryBinding.tvBatteryLev.setText(mActivity.getResources().getText(R.string.battery_level_5_message));
        }

    }

    public void addEvents() {

        mBatteryBinding.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        mBatteryBinding.linParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}

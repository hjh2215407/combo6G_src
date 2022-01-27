package com.dabinsystems.pact_app.View.SA.MeasSetup.TransmitOnOff;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.TRACE_MODE;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.TransmitOnOff.TransmitOnOffMeasSetupData;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.TransmitOnOff.OffPowerKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.TransmitOnOff.RampDownTimeKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.TransmitOnOff.RampUpTimeKeypad;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class TransmitOnOffLimitView extends LayoutBase {

    private LinearLayout linOffPower, linRampUpTime, linRampDownTime;
    private AutofitTextView tvOffPowerVal, tvRampUpTimeVal, tvRampDownTimeVal;
    private ArrayList<View> mOffPowerList, mRampUpTimeList, mRampDownTimeList;
    private DynamicView mDynamicView;

    public TransmitOnOffLimitView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {
                binding.linRightMenu.removeAllViews();

                binding.linRightMenu.addView(linOffPower);
                binding.linRightMenu.addView(linRampUpTime);
                binding.linRightMenu.addView(linRampDownTime);

                binding.tvBack.setOnClickListener(v -> {

                    ViewHandler.getInstance().getTransmitMeasSetupView().addMenu();

                });

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mOffPowerList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.transmit_off_power), "dBm/MHz");
        linOffPower = (LinearLayout) mOffPowerList.get(0);
        tvOffPowerVal = (AutofitTextView) mOffPowerList.get(2);

        mRampUpTimeList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ramp_up_time), "us");
        linRampUpTime = (LinearLayout) mRampUpTimeList.get(0);
        tvRampUpTimeVal = (AutofitTextView) mRampUpTimeList.get(2);

        mRampDownTimeList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ramp_down_time), "us");
        linRampDownTime = (LinearLayout) mRampDownTimeList.get(0);
        tvRampDownTimeVal = (AutofitTextView) mRampDownTimeList.get(2);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {


            linOffPower.setOnClickListener(v -> {

                new OffPowerKeypad(mActivity, binding).show();

            });

            linRampUpTime.setOnClickListener(v -> {


                new RampUpTimeKeypad(mActivity, binding).show();

            });

            linRampDownTime.setOnClickListener(v -> {

                new RampDownTimeKeypad(mActivity, binding).show();

            });

        });

    }

    @Override
    public void update() {
        super.update();

        mActivity.runOnUiThread(()->{

            TransmitOnOffMeasSetupData transmitData = SaDataHandler.getInstance().getTransmitConfigData().getTransmitOnOffData();

            String offPower = Utils.formatNumber(transmitData.getLimitOffPower(), 1, false) + " dBm/MHz";
            String rampUpTime = Utils.formatNumber(transmitData.getLimitRampUpTime(), 2, false) + " μs";
            String rampDownTime = Utils.formatNumber(transmitData.getLimitRampDownTime(), 2, false) + " μs";

            tvOffPowerVal.setText(offPower);
            tvRampUpTimeVal.setText(rampUpTime);
            tvRampDownTimeVal.setText(rampDownTime);

        });


    }
}

package com.dabinsystems.pact_app.View.SA.MeasSetup.TransmitOnOff;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.TRACE_MODE;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.TransmitOnOff.TransmitOnOffMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.TraceData;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.TransmitOnOff.RampDownStartLevKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.TransmitOnOff.RampDownStopLevKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.TransmitOnOff.RampUpStartLevKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.TransmitOnOff.RampUpStopLevKeypad;
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

import static com.dabinsystems.pact_app.Handler.SaDataHandler.getInstance;

public class TransmitOnOffMeasSetupView extends LayoutBase {

    private LinearLayout linRampUpStartLev, linRampUpStopLev, linRampDownStartLev, linRampDownStopLev, linLimit;
    private AutofitTextView tvRampUpStartVal, tvRampUpStopVal, tvRampDownStartVal, tvRampDownStopVal;
    private ArrayList<View> mRampUpStartList, mRampUpStopList, mRampDownStartList, mRampDownStopList, mLimitList;
    private DynamicView mDynamicView;

    public TransmitOnOffMeasSetupView(MainActivity activity, ActivityMainBinding binding) {
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
                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.transmit_on_off_power));
                binding.linRightMenu.addView(linRampUpStartLev);
                binding.linRightMenu.addView(linRampUpStopLev);
                binding.linRightMenu.addView(linRampDownStartLev);
                binding.linRightMenu.addView(linRampDownStopLev);
                binding.linRightMenu.addView(linLimit);

                binding.tvBack.setOnClickListener(v -> {

                });

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mRampUpStartList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ramp_up_start_lev), "");
        linRampUpStartLev = (LinearLayout) mRampUpStartList.get(0);
        tvRampUpStartVal = (AutofitTextView) mRampUpStartList.get(2);

        mRampUpStopList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ramp_up_stop_lev), "");
        linRampUpStopLev = (LinearLayout) mRampUpStopList.get(0);
        tvRampUpStopVal = (AutofitTextView) mRampUpStopList.get(2);

        mRampDownStartList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ramp_down_start_lev), "");
        linRampDownStartLev = (LinearLayout) mRampDownStartList.get(0);
        tvRampDownStartVal = (AutofitTextView) mRampDownStartList.get(2);

        mRampDownStopList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ramp_down_stop_lev), "");
        linRampDownStopLev = (LinearLayout) mRampDownStopList.get(0);
        tvRampDownStopVal = (AutofitTextView) mRampDownStopList.get(2);

        mLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.limit_name));
        linLimit = (LinearLayout)mLimitList.get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {


            linRampUpStartLev.setOnClickListener(v -> {

                new RampUpStartLevKeypad(mActivity, binding).show();

            });

            linRampUpStopLev.setOnClickListener(v -> {

                new RampUpStopLevKeypad(mActivity, binding).show();
            });

            linRampDownStartLev.setOnClickListener(v -> {

                new RampDownStartLevKeypad(mActivity, binding).show();

            });

            linRampDownStopLev.setOnClickListener(v -> {


                new RampDownStopLevKeypad(mActivity, binding).show();

            });

            linLimit.setOnClickListener(v->{

                ViewHandler.getInstance().getTransmitLimitView().addMenu();

            });

        });

    }

    @Override
    public void update() {
        super.update();

        mActivity.runOnUiThread(()->{

            TransmitOnOffMeasSetupData transmitData = SaDataHandler.getInstance().getTransmitConfigData().getTransmitOnOffData();

            String rampUpStart = Utils.formatNumber(transmitData.getRampUpStartLev(), 1, false) + " %";
            String rampUpStop = Utils.formatNumber(transmitData.getRampUpStopLev(), 1, false) + " %";

            String rampDownStart = Utils.formatNumber(transmitData.getRampDownStartLev(), 1, false) + " %";
            String rampDownStop = Utils.formatNumber(transmitData.getRampDownStopLev(), 1, false) + " %";

            tvRampUpStartVal.setText(rampUpStart);
            tvRampUpStopVal.setText(rampUpStop);
            tvRampDownStartVal.setText(rampDownStart);
            tvRampDownStopVal.setText(rampDownStop);

        });

    }
}

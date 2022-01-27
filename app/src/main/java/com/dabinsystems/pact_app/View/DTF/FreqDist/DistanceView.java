package com.dabinsystems.pact_app.View.DTF.FreqDist;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.VSWR.VswrConfigData;
import com.dabinsystems.pact_app.Dialog.DistanceKeypadDialog;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class DistanceView extends LayoutBase {

    private DynamicView dynamicView;

    private LinearLayout mLinDist10, mLinDist20, mLinDist50, mLinDist150, mLinDist500, mLinUserDefined;
    private AutofitTextView tvDist10, tvDist20, tvDist50, tvDist150, tvDist500, tvUserDefined;
    private ArrayList<View> mDistList10, mDistList20, mDistList50, mDistList150, mDistList500, mUserDefinedList;

    private float mCurrentDist = 10f;

    public DistanceView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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

                binding.linRightMenu.addView(mLinDist10);
                binding.linRightMenu.addView(mLinDist20);
                binding.linRightMenu.addView(mLinDist50);
                binding.linRightMenu.addView(mLinDist150);
                binding.linRightMenu.addView(mLinDist500);
                binding.linRightMenu.addView(mLinUserDefined);

                binding.linCalibration.setVisibility(View.GONE);
                binding.linCableList.setVisibility(View.GONE);

                binding.tvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ViewHandler.getInstance().getFreqDistView().addMenu();

                    }
                });

            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (dynamicView != null) return;

        dynamicView = new DynamicView(mActivity);

        mDistList10 = dynamicView.addRightMenuButton("10m", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mDistList20 = dynamicView.addRightMenuButton("20m", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mDistList50 = dynamicView.addRightMenuButton("50m", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mDistList150 = dynamicView.addRightMenuButton("150m", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mDistList500 = dynamicView.addRightMenuButton("500m", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mUserDefinedList = dynamicView.addRightMenuButton("User Defined", Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        mLinDist10 = (LinearLayout) mDistList10.get(0);
        tvDist10 = (AutofitTextView) mDistList10.get(1);

        mLinDist20 = (LinearLayout) mDistList20.get(0);
        tvDist20 = (AutofitTextView) mDistList20.get(1);

        mLinDist50 = (LinearLayout) mDistList50.get(0);
        tvDist50 = (AutofitTextView) mDistList50.get(1);

        mLinDist150 = (LinearLayout) mDistList150.get(0);
        tvDist150 = (AutofitTextView) mDistList150.get(1);

        mLinDist500 = (LinearLayout) mDistList500.get(0);
        tvDist500 = (AutofitTextView) mDistList500.get(1);

        mLinUserDefined = (LinearLayout) mUserDefinedList.get(0);
        tvUserDefined = (AutofitTextView) mUserDefinedList.get(1);

//        resetSelect();
//        setSelect(mLinDist10, tvDist10);

        setUpEvents();

    }

    @Override
    public void update() {
        super.update();

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

        if (mode == MeasureMode.MEASURE_MODE.DTF) {

            VswrConfigData config = VswrDataHandler.getInstance().getDtfConfigData();

            if (!config.checkDistance(10)) {

                enabledView(tvDist10, false);
                mLinDist10.setEnabled(false);

            } else {

                enabledView(tvDist10, true);
                mLinDist10.setEnabled(true);
            }

            if (!config.checkDistance(20)) {

                enabledView(tvDist20, false);
                mLinDist20.setEnabled(false);

            } else {

                enabledView(tvDist20, true);
                mLinDist20.setEnabled(true);

            }

            if (!config.checkDistance(50)) {

                enabledView(tvDist50, false);
                mLinDist50.setEnabled(false);

            } else {

                enabledView(tvDist50, true);
                mLinDist50.setEnabled(true);

            }

            if (!config.checkDistance(150)) {

                enabledView(tvDist150, false);
                mLinDist150.setEnabled(false);

            } else {

                enabledView(tvDist150, true);
                mLinDist150.setEnabled(true);

            }

            if (!config.checkDistance(500)) {

                enabledView(tvDist500, false);
                mLinDist500.setEnabled(false);

            } else {

                enabledView(tvDist500, true);
                mLinDist500.setEnabled(true);

            }


        }
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mLinDist10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FunctionHandler.getInstance().getMainLineChart().setStopFreq(10);
                VswrDataHandler.getInstance().getDtfConfigData().setDistance(10);
                FunctionHandler.getInstance().getMainLineChart().updateMarkerPos();
                FunctionHandler.getInstance().getMainLineChart().clearAllValues();
//                FunctionHandler.getInstance().getMainLineChart().invalidate();
                ViewHandler.getInstance().getFreqDistView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());

            }
        });

        mLinDist20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                FunctionHandler.getInstance().getMainLineChart().setStopFreq(20);
                VswrDataHandler.getInstance().getDtfConfigData().setDistance(20);
                FunctionHandler.getInstance().getMainLineChart().updateMarkerPos();
                FunctionHandler.getInstance().getMainLineChart().clearAllValues();
//                FunctionHandler.getInstance().getMainLineChart().invalidate();
                ViewHandler.getInstance().getFreqDistView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());

            }
        });

        mLinDist50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FunctionHandler.getInstance().getMainLineChart().setStopFreq(50);
                VswrDataHandler.getInstance().getDtfConfigData().setDistance(50);
                FunctionHandler.getInstance().getMainLineChart().updateMarkerPos();
                FunctionHandler.getInstance().getMainLineChart().clearAllValues();
//                FunctionHandler.getInstance().getMainLineChart().invalidate();
                ViewHandler.getInstance().getFreqDistView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());

            }
        });

        mLinDist150.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FunctionHandler.getInstance().getMainLineChart().setStopFreq(150);
                VswrDataHandler.getInstance().getDtfConfigData().setDistance(150);
                FunctionHandler.getInstance().getMainLineChart().updateMarkerPos();
                FunctionHandler.getInstance().getMainLineChart().clearAllValues();
//                FunctionHandler.getInstance().getMainLineChart().invalidate();
                ViewHandler.getInstance().getFreqDistView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());
            }
        });

        mLinDist500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FunctionHandler.getInstance().getMainLineChart().setStopFreq(500);
                VswrDataHandler.getInstance().getDtfConfigData().setDistance(500);
                FunctionHandler.getInstance().getMainLineChart().updateMarkerPos();
                FunctionHandler.getInstance().getMainLineChart().clearAllValues();
//                FunctionHandler.getInstance().getMainLineChart().invalidate();
                ViewHandler.getInstance().getFreqDistView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());
            }
        });

        mLinUserDefined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DistanceKeypadDialog(mActivity, binding).show();
            }
        });

    }

}

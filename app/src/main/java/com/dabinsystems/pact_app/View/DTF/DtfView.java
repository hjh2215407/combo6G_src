package com.dabinsystems.pact_app.View.DTF;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class DtfView extends LayoutBase {

    DynamicView mDynamicView;

    protected LinearLayout linMeasurement, linFreqDist, linAmplitude, linMarker, linLimit, linSweep, linCalibration, linSystem;

    public DtfView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            InitActivity.logMsg("VswrView", "init view");

            mActivity.runOnUiThread(() -> {

                if (binding.lineChartLayout.tvChartInfo.getVisibility() != View.VISIBLE)
                    binding.lineChartLayout.tvChartInfo.setVisibility(View.VISIBLE);

                binding.linBottomMenu.removeAllViews();

                binding.linBottomMenu.addView(linMeasurement);
                binding.linBottomMenu.addView(linFreqDist);
                binding.linBottomMenu.addView(linAmplitude);
                binding.linBottomMenu.addView(linMarker);
                binding.linBottomMenu.addView(linLimit);
                binding.linBottomMenu.addView(linSweep);
                binding.linBottomMenu.addView(linCalibration);
                binding.linBottomMenu.addView(linSystem);

                binding.linCableList.setVisibility(View.GONE);
                binding.linCalibration.setVisibility(View.GONE);

                selectMeasurement();
                ViewHandler.getInstance().getMeasurementView().addMenu();

                InitActivity.logMsg("VswrView", "init view");

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        ArrayList<View> mMeasurementList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.meas_short_name), 1 / 8f);
        linMeasurement = (LinearLayout) mMeasurementList.get(0);

        ArrayList<View> mFreqDistList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.freq_dist_short_name), 1 / 8f);
        linFreqDist = (LinearLayout) mFreqDistList.get(0);

        ArrayList<View> mAmplitudeList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.amp_short_name), 1 / 8f);
        linAmplitude = (LinearLayout) mAmplitudeList.get(0);

        ArrayList<View> mMarkerList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.marker_name), 1 / 8f);
        linMarker = (LinearLayout) mMarkerList.get(0);

        ArrayList<View> mLimitList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.limit_name), 1 / 8f);
        linLimit = (LinearLayout) mLimitList.get(0);

        ArrayList<View> mSweepList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.sweep_name), 1 / 8f);
        linSweep = (LinearLayout) mSweepList.get(0);

        ArrayList<View> mCalibrationList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.cal_short_name), 1 / 8f);
        linCalibration = (LinearLayout) mCalibrationList.get(0);

        ArrayList<View> mSystemList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.sys_short_name), 1 / 8f);
        linSystem = (LinearLayout) mSystemList.get(0);

        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linMeasurement.setOnClickListener(v -> {
                selectMeasurement();
                ViewHandler.getInstance().getMeasurementView().addMenu();
            });

            linFreqDist.setOnClickListener(v -> {
                selectFrequency();
                ViewHandler.getInstance().getFreqDistView().addMenu();
            });

            linLimit.setOnClickListener(v -> {
                selectLimit();
                ViewHandler.getInstance().getLimitView().addMenu();
            });

            linAmplitude.setOnClickListener(v -> {
                selectAmplitude();
                ViewHandler.getInstance().getAmplitudeView().addMenu();
            });

            linMarker.setOnClickListener(v -> {
                selectMarker();
                ViewHandler.getInstance().getMarkerView().addMenu();
            });

            linSweep.setOnClickListener(v -> {
                selectSweep();
                ViewHandler.getInstance().getSweepView().addMenu();
            });

            linCalibration.setOnClickListener(v -> {
                selectCalibration();
                ViewHandler.getInstance().getCalibrationView().addMenu();
            });

            linSystem.setOnClickListener(v -> {
                selectSystem();
                ViewHandler.getInstance().getSystemView().addMenu();
            });
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void update() {
        super.update();
        FunctionHandler.getInstance().getCableInfoFunc().updateCableText();
    }

    public void selectBottomView(View parent) {
        resetBottomViewColor();
        try {
            parent.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void resetBottomViewColor() {

        initView();

        mActivity.runOnUiThread(() -> {
            try {
                linFreqDist.setSelected(false);
                linMeasurement.setSelected(false);
                linAmplitude.setSelected(false);
                linLimit.setSelected(false);
                linMarker.setSelected(false);
                linSweep.setSelected(false);
                linSystem.setSelected(false);
                linCalibration.setSelected(false);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
    }

    public void selectMeasurement() {
        selectBottomView(linMeasurement);
    }

    public void selectFrequency() {
        selectBottomView(linFreqDist);
    }

    public void selectAmplitude() {
        selectBottomView(linAmplitude);
    }

    public void selectMarker() {
        selectBottomView(linMarker);
    }

    public void selectLimit() {
        selectBottomView(linLimit);
    }

    public void selectSweep() {
        selectBottomView(linSweep);
    }

    public void selectCalibration() {
        selectBottomView(linCalibration);
    }

    public void selectSystem() {
        selectBottomView(linSystem);
    }
}

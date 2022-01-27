package com.dabinsystems.pact_app.View.SA.Sweep;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData;
import com.dabinsystems.pact_app.Data.SA.SaSweepTimeData;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.ChannelAvgHoldKeypadDialog;
import com.dabinsystems.pact_app.Dialog.SA.SweepKeypadDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SaSweepTimeView extends LayoutBase {

    private LinearLayout linSweepTime, linGate;
    private AutofitTextView tvSweepVal, tvAuto, tvManual, tvGateVal;
    private ArrayList<View> SweepTimeList, GateList;
    private DynamicView mDynamicView;
    private Runnable mSweepLongTouchRunnable;
    private boolean isLongTouched = false;
    private Handler mSweepLongClickHandler;
    private final int LONG_TOUCH_DELAY = 500; // mil

    public SaSweepTimeView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
        mSweepLongClickHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            try {

                initView();
                update();

                mActivity.runOnUiThread(() -> {


                    binding.linRightMenu.removeAllViews();
                    binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.sweep_name));
                    binding.linRightMenu.addView(linSweepTime);
//                    binding.linRightMenu.addView(linGate);

                    binding.linCableList.setVisibility(View.GONE);

                    binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getSAView().selectSweepTime());


                });

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        SweepTimeList = mDynamicView.addRightMenuButtonForTopSub("Sweep Time", "5 ms", "Auto", "Manual");
        linSweepTime = (LinearLayout) SweepTimeList.get(0);
        tvAuto = (AutofitTextView) SweepTimeList.get(2);
        tvManual = (AutofitTextView) SweepTimeList.get(3);
        tvSweepVal = (AutofitTextView) SweepTimeList.get(4);

        GateList = mDynamicView.addRightMenuButton("Gate", "");
        linGate = (LinearLayout) GateList.get(0);
        tvGateVal = (AutofitTextView) GateList.get(2);

        setUpEvents();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setUpEvents() throws NullPointerException {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            mSweepLongTouchRunnable = () -> {

                if (SaDataHandler.getInstance().getConfigData().getSweepTimeData().getSweepTimeMode() == SaSweepTimeData.SWEEP_TIME_MODE.MANUAL) {
                    isLongTouched = true;
                    new SweepKeypadDialog(mActivity, binding).show();

                }

            };


            linSweepTime.setOnTouchListener((view, motionEvent) -> {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        SaSweepTimeData sweepData = SaDataHandler.getInstance().getConfigData().getSweepTimeData();
//                        if (sweepData.getSweepTimeMode() == SaSweepTimeData.SWEEP_TIME_MODE.AUTO)
//                            break;
                        mSweepLongClickHandler.postDelayed(mSweepLongTouchRunnable, LONG_TOUCH_DELAY);
                        break;

                    case MotionEvent.ACTION_UP:

                        if (isLongTouched) {

                            isLongTouched = false;
                            return false;

                        }

                        sweepData = SaDataHandler.getInstance().getConfigData().getSweepTimeData();
                        if (sweepData.getSweepTimeMode() == SaSweepTimeData.SWEEP_TIME_MODE.MANUAL)
                            sweepData.setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);
                        else sweepData.setMode(SaSweepTimeData.SWEEP_TIME_MODE.MANUAL);

                        mSweepLongClickHandler.removeCallbacksAndMessages(null);
                        update();

                        FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());

                        break;

                }

                return true;

            });

            linGate.setOnClickListener(v -> {

                ViewHandler.getInstance().getGateView().addMenu();

            });

        });

    }

    @SuppressLint("SetTextI18n")
    public void update() {

        initView();

        mActivity.runOnUiThread(() -> {
            try {
                if (SaDataHandler.getInstance().getConfigData().getSweepTimeData().getSweepTimeMode() == SaSweepTimeData.SWEEP_TIME_MODE.AUTO) {
                    selectOptionView(tvAuto, tvManual);
                } else {
                    selectOptionView(tvManual, tvAuto);
                }

                InitActivity.logMsg("SaSweepTimeView", SaDataHandler.getInstance().getConfigData().getSweepTimeData().getSweepTime() + "");
                String sweepTimeStr = Utils.formatNumber((float) SaDataHandler.getInstance().getConfigData().getSweepTimeData().getSweepTimeToMs(), 2, false);
                tvSweepVal.setText(sweepTimeStr + " ms");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}

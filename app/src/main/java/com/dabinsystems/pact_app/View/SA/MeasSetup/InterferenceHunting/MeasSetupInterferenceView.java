package com.dabinsystems.pact_app.View.SA.MeasSetup.InterferenceHunting;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.SaConfigData;
import com.dabinsystems.pact_app.Dialog.LimitLineKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.ACLR.AclrAvgHoldKeypadDialog;
import com.dabinsystems.pact_app.Dialog.SA.SaCenterFreqKeypad;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class MeasSetupInterferenceView extends LayoutBase {

    private LinearLayout linAvgHold, linUplinkBand, linTargetFreq, linThreshold;
    private AutofitTextView tvAvgHoldVal, tvUplinkBand, tvTargetFreq, tvThreshold;
    private DynamicView mDynamicView;

    private Handler mAvgLongClickHandler;
    private Runnable mAvgLongTouchRunnable;

    private boolean isLongTouched = false;
    private final int LONG_TOUCH_DELAY = 500; // mil

    public MeasSetupInterferenceView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
        mAvgLongClickHandler = new Handler();
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {
            initView();
            update();
            mActivity.runOnUiThread(() -> {
                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.meas_setup));

                binding.linRightMenu.removeAllViews();

                binding.linRightMenu.addView(linAvgHold);
                binding.linRightMenu.addView(linUplinkBand);
                binding.linRightMenu.addView(linTargetFreq);
                binding.linRightMenu.addView(linThreshold);

                binding.tvBack.setOnClickListener(v -> {
                });
            });
        }).start();
    }

    @SuppressLint("SetTextI18n")
    public void update() {
        initView();
        mActivity.runOnUiThread(() -> {

            tvAvgHoldVal.setText(SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData().getAvgHold() + "");

            linAvgHold.setSelected(SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData().isOnAvg());

            SaConfigData data = SaDataHandler.getInstance().getInterferenceHuntingConfigData();
            double val = data.getFrequencyData().getCenterFreq();
            val = Math.round(val * 1000000d) / 1000000d;
            tvTargetFreq.setText(val + " MHz");

            Double startBand = data.getInterferenceHuntingData().getUplinkBandStart();
            Double stopBand = data.getInterferenceHuntingData().getUplinkBandStop();

            if (startBand != null && stopBand != null) {
                tvUplinkBand.setText(startBand + " ~ " + stopBand);
            }

            updateThreshold();
        });
    }

    public void updateThreshold() {
        mActivity.runOnUiThread(() -> {
            try {
                float limitValue = FunctionHandler.getInstance().getMainLineChart().getLimitValue();
                limitValue = Math.round(limitValue * 100f) / 100f;
                tvThreshold.setText(limitValue + " dBm");
            } catch (NullPointerException e) {
                InitActivity.logMsg("updateThreshold", "except");
            }
        });
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        ArrayList<View> mAvgList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.avg_hold_num_title), SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData().getAvgHold() + "");
        linAvgHold = (LinearLayout) mAvgList.get(0);
        tvAvgHoldVal = (AutofitTextView) mAvgList.get(2);

        ArrayList<View> mUplinkBandList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.uplink_band), "");
        linUplinkBand = (LinearLayout) mUplinkBandList.get(0);
        tvUplinkBand = (AutofitTextView) mUplinkBandList.get(2);

        ArrayList<View> mTargetFreqList = mDynamicView.addRightMenuButton(mActivity.getString(R.string.target_freq), "");
        linTargetFreq = (LinearLayout) mTargetFreqList.get(0);
        tvTargetFreq = (AutofitTextView) mTargetFreqList.get(2);

        ArrayList<View> mThresholdList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.threshold), "");
        linThreshold = (LinearLayout) mThresholdList.get(0);
        tvThreshold = (AutofitTextView) mThresholdList.get(2);

        setUpEvents();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            mAvgLongTouchRunnable = () -> {
                if (SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData().isOnAvg()) {
                    isLongTouched = true;
                    new AclrAvgHoldKeypadDialog(mActivity, binding).show();
                }
            };

            linAvgHold.setOnTouchListener((view, motionEvent) -> {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mAvgLongClickHandler.postDelayed(mAvgLongTouchRunnable, LONG_TOUCH_DELAY);
                        break;

                    case MotionEvent.ACTION_UP:
                        if (isLongTouched) {
                            isLongTouched = false;
                            return false;
                        }

                        SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData().setAvgMode(
                                !SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData().isOnAvg()
                        );

                        mAvgLongClickHandler.removeCallbacksAndMessages(null);
                        update();
                        break;
                }

                return true;
            });

            linUplinkBand.setOnClickListener(v -> ViewHandler.getInstance().getUplinkBandView().addMenu());

            linTargetFreq.setOnClickListener(v -> new SaCenterFreqKeypad(mActivity, binding).show());

            linThreshold.setOnClickListener(v -> new LimitLineKeypad(mActivity, binding).show());
        });
    }
}

package com.dabinsystems.pact_app.View.SA.MeasSetup;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.ChannelAvgHoldKeypadDialog;
import com.dabinsystems.pact_app.Dialog.SA.ChannelPowerIntegrationKeypadDialog;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class MeasSetupChannelPowerView extends LayoutBase {

    private LinearLayout linAvgHold, linIntegBw;
    private AutofitTextView tvAvgHoldVal, tvIntegVal;
    private DynamicView mDynamicView;

    private Handler mAvgLongClickHandler;
    private Runnable mAvgLongTouchRunnable;

    private Boolean isLongTouched = false;
    private final int LONG_TOUCH_DELAY = 500; // mil

    public MeasSetupChannelPowerView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
        mAvgLongClickHandler = new Handler();
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {
            initView();
            update();
            mActivity.runOnUiThread(()-> {
                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.meas_setup));

                binding.linRightMenu.removeAllViews();

                binding.linRightMenu.addView(linAvgHold);
                binding.linRightMenu.addView(linIntegBw);

                binding.tvBack.setOnClickListener(v->{
                });
            });
        }).start();
    }

    public void update() {
        initView();
        mActivity.runOnUiThread(()->{
            tvAvgHoldVal.setText(SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().getAvgHold() + "");
            tvIntegVal.setText((SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().getIntegMHzVal()) + " MHz");
            linAvgHold.setSelected(SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().isOnAvg());
        });
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        ArrayList<View> mAvgList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.avg_hold_num_title), "100");
        linAvgHold = (LinearLayout) mAvgList.get(0);
        tvAvgHoldVal = (AutofitTextView) mAvgList.get(2);

        ArrayList<View> mIntegList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.integ_bw), "3.84 MHz");
        linIntegBw = (LinearLayout) mIntegList.get(0);
        tvIntegVal = (AutofitTextView) mIntegList.get(2);

        setUpEvents();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()-> {
            mAvgLongTouchRunnable = () -> {
                if(SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().isOnAvg()) {
                    isLongTouched = true;
                    new ChannelAvgHoldKeypadDialog(mActivity, binding).show();
                }
            };

            linAvgHold.setOnTouchListener((view, motionEvent) -> {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mAvgLongClickHandler.postDelayed(mAvgLongTouchRunnable, LONG_TOUCH_DELAY);
                        break;

                    case MotionEvent.ACTION_UP:
                        if(isLongTouched) {
                            isLongTouched = false;
                            return false;
                        }

                        SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().setAvgMode(
                                !SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().isOnAvg()
                        );

                        mAvgLongClickHandler.removeCallbacksAndMessages(null);
                        update();

                        break;
                }

                return true;
            });

            linIntegBw.setOnClickListener(v-> new ChannelPowerIntegrationKeypadDialog(mActivity, binding).show());
        });
    }
}

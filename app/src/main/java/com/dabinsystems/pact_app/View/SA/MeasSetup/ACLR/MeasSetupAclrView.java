package com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.ACLR.AclrAvgHoldKeypadDialog;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.OccAvgHoldKeypadDialog;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class MeasSetupAclrView extends LayoutBase {

    private LinearLayout linAvgHold, linCarrierSetup, linOffset;
    private AutofitTextView tvAvgHoldVal;
    private DynamicView mDynamicView;

    private Handler mAvgLongClickHandler;
    private Runnable mAvgLongTouchRunnable;

    private boolean isLongTouched = false;
    private final int LONG_TOUCH_DELAY = 500; // mil

    public MeasSetupAclrView(MainActivity activity, ActivityMainBinding binding) {
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
                binding.linRightMenu.addView(linCarrierSetup);
                binding.linRightMenu.addView(linOffset);

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

        ArrayList<View> mCarrierSetupList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.carrier_setup));
        linCarrierSetup = (LinearLayout) mCarrierSetupList.get(0);

        ArrayList<View> mOffsetList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.offset_setup));
        linOffset = (LinearLayout) mOffsetList.get(0);

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

            linCarrierSetup.setOnClickListener(v -> ViewHandler.getInstance().getCarrierSetupView().addMenu());

            linOffset.setOnClickListener(v -> ViewHandler.getInstance().getOffsetSetupView().addMenu());
        });
    }
}

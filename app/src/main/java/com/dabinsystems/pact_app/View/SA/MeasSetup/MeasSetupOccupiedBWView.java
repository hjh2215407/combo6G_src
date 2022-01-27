package com.dabinsystems.pact_app.View.SA.MeasSetup;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.SA.OBWKeypadDialog;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.OccAvgHoldKeypadDialog;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.XDBKeypadDialog;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class MeasSetupOccupiedBWView extends LayoutBase {

    private LinearLayout linAvgHold, linOBWPower, linXDB;
    private AutofitTextView tvAvgHoldVal, tvOBWPower, tvXDB;
    private DynamicView mDynamicView;

    private Handler mAvgLongClickHandler;
    private Runnable mAvgLongTouchRunnable;

    private boolean isLongTouched = false;
    private final int LONG_TOUCH_DELAY = 500; // mil

    public MeasSetupOccupiedBWView(MainActivity activity, ActivityMainBinding binding) {
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
                binding.linRightMenu.addView(linOBWPower);
//                binding.linRightMenu.addView(linXDB);

                binding.tvBack.setOnClickListener(v -> {
                });
            });
        }).start();
    }

    @SuppressLint("SetTextI18n")
    public void update() {
        initView();
        mActivity.runOnUiThread(() -> {
            tvAvgHoldVal.setText(SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getAvgHold() + "");
            tvOBWPower.setText(SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getOBWPower() + " %");
            tvXDB.setText(SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getXDB() + " dB");
            linAvgHold.setSelected(SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().isOnAvg());
        });
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        ArrayList<View> mAvgList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.avg_hold_num_title), SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getAvgHold() + "");
        linAvgHold = (LinearLayout) mAvgList.get(0);
        tvAvgHoldVal = (AutofitTextView) mAvgList.get(2);

        ArrayList<View> mOBWList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.obw_power), SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getOBWPower() + " %");
        linOBWPower = (LinearLayout) mOBWList.get(0);
        tvOBWPower = (AutofitTextView) mOBWList.get(2);

        ArrayList<View> mXDBList = mDynamicView.addRightMenuButton("x dB", SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getXDB() + " dB");
        linXDB = (LinearLayout) mXDBList.get(0);
        tvXDB = (AutofitTextView) mXDBList.get(2);

        setUpEvents();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            mAvgLongTouchRunnable = () -> {
                if (SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().isOnAvg()) {
                    isLongTouched = true;
                    new OccAvgHoldKeypadDialog(mActivity, binding).show();
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

                        SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().setAvgMode(
                                !SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().isOnAvg()
                        );

                        mAvgLongClickHandler.removeCallbacksAndMessages(null);
                        update();
                        break;
                }

                return true;
            });

            linOBWPower.setOnClickListener(v -> new OBWKeypadDialog(mActivity, binding).show());

            linXDB.setOnClickListener(v -> new XDBKeypadDialog(mActivity, binding).show());
        });
    }
}

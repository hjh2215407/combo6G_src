package com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.EditMask;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SideData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemMeasSetupData;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.ACLR.OffsetSetupIntegBwKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SEM.SemAbsStartLimitKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SEM.SemAbsStopLimitKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SEM.SemRelStartLimitKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SEM.SemRelStopLimitKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SEM.SemStartFreqKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SEM.SemStopFreqKeypad;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class EditMaskLimitView extends LayoutBase {

    private LinearLayout linMaskIndex, linAbsStartLimit, linAbsStopLimit, linRelStartLimit, linRelStopLimit;
    private AutofitTextView tvMaskIndexVal, tvMaskIndex, tvMaskIndexOn, tvMaskIndexOff, tvAbsStratLimitVal, tvAbsStopLimitVal, tvRelStartLimitVal, tvRelStopLimitVal;

    private ArrayList<View> mMaskIndexList, mAbsStartLimitList, mAbsStopLimitList, mRelStartLimitList, mRelStopLimitList;
    private DynamicView mDynamicView;

    private Handler mEditMaskLongClickHandler;
    private Runnable mEditMaskLongTouchRunnable;

    private boolean isLongTouched = false;
    private final int LONG_TOUCH_DELAY = 500; // mil

    public EditMaskLimitView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);

        mEditMaskLongClickHandler = new Handler();
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {
                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.edit_mask));
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linMaskIndex);
                binding.linRightMenu.addView(linAbsStartLimit);
                binding.linRightMenu.addView(linAbsStopLimit);
                binding.linRightMenu.addView(linRelStartLimit);
                binding.linRightMenu.addView(linRelStopLimit);

                binding.tvBack.setOnClickListener(v -> {

                    ViewHandler.getInstance().getEditMaskView().addMenu();

                });

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mMaskIndexList = mDynamicView.addRightMenuButtonForTopSub(mActivity.getResources().getString(R.string.mask_index), SaDataHandler
                .getInstance()
                .getSemConfigData()
                .getSemMeasSetupData()
                .getEditMaskData()
                .getMaskIndex() + 1 + "", "On", "Off");

        linMaskIndex = (LinearLayout) mMaskIndexList.get(0);
        tvMaskIndex = (AutofitTextView) mMaskIndexList.get(1);
        tvMaskIndexOn = (AutofitTextView) mMaskIndexList.get(2);
        tvMaskIndexOff = (AutofitTextView) mMaskIndexList.get(3);
        tvMaskIndexVal = (AutofitTextView) mMaskIndexList.get(4);

        mAbsStartLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.abs_start_limit), " dBm");
        linAbsStartLimit = (LinearLayout) mAbsStartLimitList.get(0);
        tvAbsStratLimitVal = (AutofitTextView) mAbsStartLimitList.get(2);

        mAbsStopLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.abs_stop_limit), " dBm");
        linAbsStopLimit = (LinearLayout) mAbsStopLimitList.get(0);
        tvAbsStopLimitVal = (AutofitTextView) mAbsStopLimitList.get(2);

        mRelStartLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.rel_start_limit), " dB");
        linRelStartLimit = (LinearLayout) mRelStartLimitList.get(0);
        tvRelStartLimitVal = (AutofitTextView) mRelStartLimitList.get(2);

        mRelStopLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.rel_stop_limit), " dB");
        linRelStopLimit = (LinearLayout) mRelStopLimitList.get(0);
        tvRelStopLimitVal = (AutofitTextView) mRelStopLimitList.get(2);

        setUpEvents();

    }

    @SuppressLint("SetTextI18n")
    public void update() {

        initView();

        mActivity.runOnUiThread(() -> {

            SemMeasSetupData semData = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData();

            if (semData.getEditMaskData().getMaskOnOff() == SemEditMaskData.MASK_ON_OFF.ON) {

                tvMaskIndexOn.setBackgroundColor(Color.BLACK);
                tvMaskIndexOn.setTextColor(Color.YELLOW);

                tvMaskIndexOff.setBackground(null);
                tvMaskIndexOff.setTextColor(Color.BLACK);

            } else {

                tvMaskIndexOff.setBackgroundColor(Color.BLACK);
                tvMaskIndexOff.setTextColor(Color.YELLOW);

                tvMaskIndexOn.setBackground(null);
                tvMaskIndexOn.setTextColor(Color.BLACK);

            }

            tvMaskIndexVal.setText(
                    (semData.getEditMaskData().getMaskIndex() + 1) + ""
            );

            Float startAbsLimit = semData.getEditMaskData().getAbsStartLimit();
            Float stopAbsLimit = semData.getEditMaskData().getAbsStopLimit();

            Float startRelLimit = semData.getEditMaskData().getRelStartLimit();
            Float stopRelLimit = semData.getEditMaskData().getRelStopLimit();

            if (startAbsLimit != null)
                tvAbsStratLimitVal.setText(startAbsLimit + " dBm");

            if (stopAbsLimit != null)
                tvAbsStopLimitVal.setText(stopAbsLimit + " dBm");

            if (startRelLimit != null)
                tvRelStartLimitVal.setText(startRelLimit + " dB");

            if (stopRelLimit != null)
                tvRelStopLimitVal.setText(stopRelLimit + " dB");

        });

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            SemEditMaskData maskData = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getEditMaskData();

            mEditMaskLongTouchRunnable = () -> {

                isLongTouched = true;
                ViewHandler.getInstance().getEditMaskIndexView().addMenu();

            };

            linMaskIndex.setOnTouchListener((view, motionEvent) -> {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        isLongTouched = false;
                        mEditMaskLongClickHandler.postDelayed(mEditMaskLongTouchRunnable, LONG_TOUCH_DELAY);
                        break;

                    case MotionEvent.ACTION_UP:

                        if (isLongTouched) {

                            isLongTouched = false;
                            return false;

                        }


                        if (maskData.getMaskOnOff() == SemEditMaskData.MASK_ON_OFF.ON)
                            maskData.setMaskOnOff(SemEditMaskData.MASK_ON_OFF.OFF);
                        else
                            maskData.setMaskOnOff(SemEditMaskData.MASK_ON_OFF.ON);

                        mEditMaskLongClickHandler.removeCallbacksAndMessages(null);
                        isLongTouched = false;

                        update();

                        break;

                }

                return true;

            });

            linAbsStartLimit.setOnClickListener(v -> {

                new SemAbsStartLimitKeypad(mActivity, binding).show();

            });

            linAbsStopLimit.setOnClickListener(v -> {


                new SemAbsStopLimitKeypad(mActivity, binding).show();

            });

            linRelStartLimit.setOnClickListener(v -> {

                new SemRelStartLimitKeypad(mActivity, binding).show();

            });

            linRelStopLimit.setOnClickListener(v -> {

                new SemRelStopLimitKeypad(mActivity, binding).show();

            });

        });

    }

}

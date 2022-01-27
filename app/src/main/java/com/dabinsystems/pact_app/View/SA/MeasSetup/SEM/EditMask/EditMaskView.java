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
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SEM.SemStartFreqKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SEM.SemStopFreqKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SemAvgHoldKeypadDialog;
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

public class EditMaskView extends LayoutBase {

    private LinearLayout linMaskIndex, linStartFreq, linStopFreq, linRbw, linVbw, linMaskSide, linFailSource, linLimit;
    private AutofitTextView tvMaskIndexVal, tvMaskIndex, tvMaskIndexOn, tvMaskIndexOff, tvStratFreqVal, tvStopFreqVal, tvRbwVal, tvVbwVal, tvMaskSideNeg, tvMaskSideBoth, tvMaskSidePos,
            tvFailSourceVal, tvLimitVal;

    private ArrayList<View> mMaskIndexList, mStartFreqList, mStopFreqList, mRbwList, mVbwList, mMaskSideList, mFailSourceList, mLimitList;
    private DynamicView mDynamicView;

    private Handler mEditMaskLongClickHandler;
    private Runnable mEditMaskLongTouchRunnable;

    private boolean isLongTouched = false;
    private final int LONG_TOUCH_DELAY = 500; // mil

    public EditMaskView(MainActivity activity, ActivityMainBinding binding) {
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
                binding.linRightMenu.addView(linStartFreq);
                binding.linRightMenu.addView(linStopFreq);
                binding.linRightMenu.addView(linRbw);
                binding.linRightMenu.addView(linVbw);
                binding.linRightMenu.addView(linMaskSide);
                binding.linRightMenu.addView(linLimit);
                binding.linRightMenu.addView(linFailSource);

                binding.tvBack.setOnClickListener(v -> {

                    ViewHandler.getInstance().getMeasSetupSemView().addMenu();

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

        mStartFreqList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.start_freq), "");
        linStartFreq = (LinearLayout) mStartFreqList.get(0);
        tvStratFreqVal = (AutofitTextView) mStartFreqList.get(2);

        mStopFreqList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.stop_freq), "");
        linStopFreq = (LinearLayout) mStopFreqList.get(0);
        tvStopFreqVal = (AutofitTextView) mStopFreqList.get(2);

        mMaskSideList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.mask_side), "Neg", "Both", "Pos");
        linMaskSide = (LinearLayout) mMaskSideList.get(0);
        tvMaskSideNeg = (AutofitTextView) mMaskSideList.get(2);
        tvMaskSideBoth = (AutofitTextView) mMaskSideList.get(3);
        tvMaskSidePos = (AutofitTextView) mMaskSideList.get(4);

        mRbwList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.rbw_name), " kHz");
        linRbw = (LinearLayout) mRbwList.get(0);
        tvRbwVal = (AutofitTextView) mRbwList.get(2);

        mVbwList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.vbw_name), " kHz");
        linVbw = (LinearLayout) mVbwList.get(0);
        tvVbwVal = (AutofitTextView) mVbwList.get(2);

        mLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.limit_name));
        linLimit = (LinearLayout) mLimitList.get(0);
//        tvLimitVal = (AutofitTextView) mLimitList.get(2);

        mFailSourceList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.fail_source), "Absolute");
        linFailSource = (LinearLayout) mFailSourceList.get(0);
        tvFailSourceVal = (AutofitTextView) mFailSourceList.get(2);

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

            Double startFreq = semData.getEditMaskData().getStartFreq();
            Double stopFreq = semData.getEditMaskData().getStopFreq();

            if (startFreq != null)
                tvStratFreqVal.setText(startFreq + "");


            if (stopFreq != null)
                tvStopFreqVal.setText(stopFreq + "");


            tvRbwVal.setText(

                    /*Utils.formatNumber(semData.getEditMaskData().getBwData().getRBW().getValue()
                            , 4, false) + " MHz"*/
                    SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().getBwData().getRBW().getString()

            );

            tvVbwVal.setText(

                    /*Utils.formatNumber(semData.getEditMaskData().getBwData().getVBW().getValue()
                            , 4, false) + " MHz"*/
                    SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().getBwData().getVBW().getString()

            );

            updateMaskSideOption();

            tvFailSourceVal.setText(

                    semData.getEditMaskData()
                            .getFailSource()
                            .getName()

            );


        });

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            mEditMaskLongTouchRunnable = () -> {

                isLongTouched = true;
                ViewHandler.getInstance().getEditMaskIndexView().addMenu();


            };

            linMaskIndex.setOnTouchListener((view, motionEvent) -> {

                SemEditMaskData maskData = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getEditMaskData();

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



                        if (maskData.getMaskOnOff() == SemEditMaskData.MASK_ON_OFF.ON) {
                            maskData.setMaskOnOff(SemEditMaskData.MASK_ON_OFF.OFF);
                        }
                        else
                            maskData.setMaskOnOff(SemEditMaskData.MASK_ON_OFF.ON);

                        mEditMaskLongClickHandler.removeCallbacksAndMessages(null);
                        isLongTouched = false;

                        update();

                        break;

                }

                return true;

            });

            linStartFreq.setOnClickListener(v -> {

                new SemStartFreqKeypad(mActivity, binding).show();

            });

            linStopFreq.setOnClickListener(v -> {


                new SemStopFreqKeypad(mActivity, binding).show();

            });

            linRbw.setOnClickListener(v -> {

                ViewHandler.getInstance().getEditMaskRBWView().addMenu();

            });

            linVbw.setOnClickListener(v -> {

                ViewHandler.getInstance().getEditMaskVBWView().addMenu();

            });

            linMaskSide.setOnClickListener(v -> {

                SemEditMaskData maskData = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getEditMaskData();

                SideData.SIDE_OPTION maskSide = maskData.getMaskSide();

                switch (maskSide) {

                    case NEG:
                        maskData.setMaskSide(SideData.SIDE_OPTION.BOTH);
                        break;

                    case BOTH:
                        maskData.setMaskSide(SideData.SIDE_OPTION.POS);
                        break;

                    case POS:
                        maskData.setMaskSide(SideData.SIDE_OPTION.NEG);
                        break;

                }
                FunctionHandler.getInstance().getMainLineChart().updateSemBox();
                updateMaskSideOption();
                FunctionHandler.getInstance().getMainLineChart().invalidate();

            });

            linLimit.setOnClickListener(v -> {

                ViewHandler.getInstance().getEditMaskLimitView().addMenu();

            });

            linFailSource.setOnClickListener(v -> {

                ViewHandler.getInstance().getSemFailSourceView().addMenu();

            });

        });

    }

    public void updateMaskSideOption() {

        SideData.SIDE_OPTION offset =
                SaDataHandler.getInstance()
                        .getSemConfigData()
                        .getSemMeasSetupData()
                        .getEditMaskData()
                        .getMaskSide();

        switch (offset) {

            case NEG:
                selectOptionView(tvMaskSideNeg, tvMaskSideBoth, tvMaskSidePos);
                break;

            case BOTH:
                selectOptionView(tvMaskSideBoth, tvMaskSideNeg, tvMaskSidePos);
                break;

            case POS:
                selectOptionView(tvMaskSidePos, tvMaskSideNeg, tvMaskSideBoth);
                break;

        }

    }

}

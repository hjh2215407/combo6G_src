package com.dabinsystems.pact_app.View.SA.MeasSetup.SEM;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemMeasSetupData;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SemAvgHoldKeypadDialog;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class  MeasSetupSemView extends LayoutBase {

    private LinearLayout linAvgHold, linMeasType, linRefChannel, linEditMask;
    private AutofitTextView tvAvgHoldVal, tvMeasType;
    private DynamicView mDynamicView;

    private Handler mAvgLongClickHandler;
    private Runnable mAvgLongTouchRunnable;

    private boolean isLongTouched = false;
    private final int LONG_TOUCH_DELAY = 500; // mil

    public MeasSetupSemView(MainActivity activity, ActivityMainBinding binding) {
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
                binding.linRightMenu.addView(linMeasType);
                binding.linRightMenu.addView(linRefChannel);
                binding.linRightMenu.addView(linEditMask);

                binding.tvBack.setOnClickListener(v->{
                });
            });
        }).start();
    }

    @SuppressLint("SetTextI18n")
    public void update() {
        initView();

        mActivity.runOnUiThread(()->{
            SemMeasSetupData semData = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData();

            tvAvgHoldVal.setText(semData.getAvgHold() + "");

            linAvgHold.setSelected(semData.isOnAvg());

            tvMeasType.setText(semData.getSemMeasType().getName());
        });

        ViewHandler.getInstance().getContent().subInfoUpdate();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        ArrayList<View> mAvgList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.avg_hold_num_title), SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getAvgHold() + "");
        linAvgHold = (LinearLayout) mAvgList.get(0);
        tvAvgHoldVal = (AutofitTextView) mAvgList.get(2);

        ArrayList<View> mMeasTypeList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.meas_type), SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getSemMeasType().getName());
        linMeasType = (LinearLayout) mMeasTypeList.get(0);
        tvMeasType = (AutofitTextView) mMeasTypeList.get(2);

        ArrayList<View> mRefChannelList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ref_channel));
        linRefChannel = (LinearLayout) mRefChannelList.get(0);

        ArrayList<View> mEditMaskList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.edit_mask));
        linEditMask = (LinearLayout) mEditMaskList.get(0);

        setUpEvents();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()-> {

            mAvgLongTouchRunnable = () -> {
                if(SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().isOnAvg()) {
                    isLongTouched = true;
                    new SemAvgHoldKeypadDialog(mActivity, binding).show();
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

                        SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().setAvgMode(
                                !SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().isOnAvg()
                        );

                        mAvgLongClickHandler.removeCallbacksAndMessages(null);
                        update();

                        break;
                }

                return true;
            });

            linMeasType.setOnClickListener(v-> ViewHandler.getInstance().getSemMeasTypeView().addMenu());

            linRefChannel.setOnClickListener(v-> ViewHandler.getInstance().getRefChannelView().addMenu());

            linEditMask.setOnClickListener(v-> ViewHandler.getInstance().getEditMaskView().addMenu());
        });
    }
}

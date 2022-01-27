package com.dabinsystems.pact_app.View.SA.Trace;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SaTraceView extends LayoutBase {

    private LinearLayout linSelectTrace, linTraceMode, linTraceType, linDetector;
    private AutofitTextView tvSelectTrace, tvTraceMode, tvTraceType, tvDetector,
            tvSelectVal, tvModeVal, tvTypeVal, tvDetectorVal;
    private ArrayList<View> mSelectList, mModeList, mTraceTypeList, mDetectorList;
    private DynamicView mDynamicView;

    public SaTraceView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {
                ViewHandler.getInstance().getSAView().selectTrace();
                binding.tvRightMenuTitle.setText("Trace");
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linSelectTrace);
                binding.linRightMenu.addView(linTraceMode);
                binding.linRightMenu.addView(linTraceType);
                binding.linRightMenu.addView(linDetector);

//                tvModeVal.setText(CommandData.getInstance().getTraceModeToString());
//                tvTypeVal.setText(CommandData.getInstance().getTraceTypeToString());
//                tvDetectorVal.setText(CommandData.getInstance().getTraceDetectorToString());

                binding.tvBack.setOnClickListener(v->{



                });

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mSelectList = mDynamicView.addRightMenuButton("Select Trace", "Trace 1");
        linSelectTrace = (LinearLayout) mSelectList.get(0);
        tvSelectTrace = (AutofitTextView) mSelectList.get(1);
        tvSelectVal = (AutofitTextView) mSelectList.get(2);

        mModeList = mDynamicView.addRightMenuButton("Trace Mode", "Clear Write");
        linTraceMode = (LinearLayout) mModeList.get(0);
        tvTraceMode = (AutofitTextView) mModeList.get(1);
        tvModeVal = (AutofitTextView) mModeList.get(2);

        mTraceTypeList = mDynamicView.addRightMenuButton("Trace Type", "Update");
        linTraceType = (LinearLayout) mTraceTypeList.get(0);
        tvTraceType = (AutofitTextView) mTraceTypeList.get(1);
        tvTypeVal = (AutofitTextView) mTraceTypeList.get(2);

        mDetectorList = mDynamicView.addRightMenuButton("Detector", "Normal");
        linDetector = (LinearLayout) mDetectorList.get(0);
        tvDetector = (AutofitTextView) mDetectorList.get(1);
        tvDetectorVal = (AutofitTextView) mDetectorList.get(2);

        setUpEvents();

    }

    public void update() {

        initView();

        mActivity.runOnUiThread(()->{

            try {

                tvSelectVal.setText("Trace " + (SaDataHandler.getInstance().getConfigData().getTraceData().getTraceIndex() + 1));
                tvModeVal.setText(SaDataHandler.getInstance().getConfigData().getTraceData().getMode().getString());
                tvDetectorVal.setText(SaDataHandler.getInstance().getConfigData().getTraceData().getDetector().getString());
                tvTypeVal.setText(SaDataHandler.getInstance().getConfigData().getTraceData().getType().getString());

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linSelectTrace.setOnClickListener(v -> {
                ViewHandler.getInstance().getSelectTrace().addMenu();

            });

            linTraceMode.setOnClickListener(v -> {
                ViewHandler.getInstance().getTraceMode().addMenu();

            });

            linTraceType.setOnClickListener(v -> {
                ViewHandler.getInstance().getTraceType().addMenu();

            });

            linDetector.setOnClickListener(v -> {
                ViewHandler.getInstance().getSADetector().addMenu();

            });

        });

    }

}

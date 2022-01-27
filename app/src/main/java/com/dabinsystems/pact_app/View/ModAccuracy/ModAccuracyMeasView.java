package com.dabinsystems.pact_app.View.ModAccuracy;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class ModAccuracyMeasView extends LayoutBase {

    private LinearLayout linModAccuracy, linTae;
    private AutofitTextView tvModAccuracy, tvTae;
    private ArrayList<View> mModAccuracyList, mTaeList;
    private DynamicView mDynamicView;

    public ModAccuracyMeasView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {
                binding.linRightMenu.removeAllViews();
                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.mode_name));
                binding.linRightMenu.addView(linModAccuracy);
                binding.linRightMenu.addView(linTae);

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

        mModAccuracyList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.mod_accuracy), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linModAccuracy = (LinearLayout) mModAccuracyList.get(0);
        tvModAccuracy = (AutofitTextView) mModAccuracyList.get(1);
        tvModAccuracy.setSingleLine(false);

        mTaeList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.tae), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linTae = (LinearLayout) mTaeList.get(0);
        tvTae = (AutofitTextView) mTaeList.get(1);
        tvTae.setSingleLine(false);

        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()->{

            linModAccuracy.setOnClickListener(v->{

                //todo : set SA

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if(mode != MeasureMode.MEASURE_MODE.MOD_ACCURACY)
                    FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.MOD_ACCURACY);

                if(type != MeasureType.MEASURE_TYPE.NR_5G)
                    FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.NR_5G);

                ViewHandler.getInstance().getNrMeasSetupView().addMenu();
//                ViewHandler.getInstance().getSAView().update();

            });

            linTae.setOnClickListener(v->{

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if(mode != MeasureMode.MEASURE_MODE.MOD_ACCURACY)
                    FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.MOD_ACCURACY);

                if(type != MeasureType.MEASURE_TYPE.NR_5G)
                    FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.NR_5G);

                ViewHandler.getInstance().getTaeView().addMenu();

            });

        });
    }



}
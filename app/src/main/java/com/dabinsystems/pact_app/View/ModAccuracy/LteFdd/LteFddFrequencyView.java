package com.dabinsystems.pact_app.View.ModAccuracy.LteFdd;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.LteFdd.LteFddCenterFreqKeypad;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class LteFddFrequencyView extends LayoutBase {

    private LinearLayout linCenter;
    private AutofitTextView tvFreqCenterValue;
    private ArrayList<View> mCenterList;
    private DynamicView mDynamicView;

    public LteFddFrequencyView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);

    }

    @Override
    public void addMenu() {
        super.addMenu();

        new Thread(() -> {

            try {

                initView();

                mActivity.runOnUiThread(() -> {

                    ViewHandler.getInstance().getSAView().selectFrequency();
                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(linCenter);

                    binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.frequency_name));

                    binding.linCableList.setVisibility(View.GONE);

                    updateFreq();

                    binding.tvBack.setOnClickListener(v -> {


                    });

                });

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }).start();

    }

    @Override
    public void initView() {

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);
        mCenterList = mDynamicView.addRightMenuButton("Center.", DataHandler.getInstance().getLteFddData().getCenterFreq() + " MHz");

        linCenter = (LinearLayout) mCenterList.get(0);
        tvFreqCenterValue = (AutofitTextView) mCenterList.get(2);

        mActivity.runOnUiThread(() -> {
            setUpEvents();
        });

    }

    @Override
    public void update() {
        super.update();
        updateFreq();
    }

    public void updateFreq() {

        mActivity.runOnUiThread(() -> {
            double val = DataHandler.getInstance().getLteFddData().getCenterFreq();
            val = Math.round(val * 1000000d)/1000000d;
            tvFreqCenterValue.setText(val + " MHz");

        });

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new LteFddCenterFreqKeypad(mActivity, binding).show();
                }

            });

        });

    }

}

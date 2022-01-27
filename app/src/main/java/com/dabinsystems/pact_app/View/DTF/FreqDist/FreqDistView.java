package com.dabinsystems.pact_app.View.DTF.FreqDist;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class FreqDistView extends LayoutBase {

    private LinearLayout linFreq, linDist, linDtfSetup;
    private DynamicView mDynamicView;

    public FreqDistView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            update();

            mActivity.runOnUiThread(() -> {
                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.freq_dist_name));

                binding.linRightMenu.removeAllViews();

                binding.linRightMenu.addView(linFreq);
                binding.linRightMenu.addView(linDist);
                binding.linRightMenu.addView(linDtfSetup);

                binding.linCableList.setVisibility(View.GONE);
                binding.linCalibration.setVisibility(View.GONE);

                binding.tvBack.setOnClickListener(v -> addMenu());
            });
        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity.getApplicationContext());

        ArrayList<View> mFreqList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.frequency_name));
        ArrayList<View> mDistList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.distance_name));
        ArrayList<View> mDtfSetupList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.dtf_setup));

        linFreq = (LinearLayout) mFreqList.get(0);
        linDist = (LinearLayout) mDistList.get(0);
        linDtfSetup = (LinearLayout) mDtfSetupList.get(0);

        setUpEvents();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linFreq.setOnClickListener(v -> ViewHandler.getInstance().getFrequencyView().addMenu());

            linDist.setOnClickListener(v -> ViewHandler.getInstance().getDistanceView().addMenu());

            linDtfSetup.setOnClickListener(v -> ViewHandler.getInstance().getDtfSetupView().addMenu());

        });
    }
}

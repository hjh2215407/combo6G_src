package com.dabinsystems.pact_app.View.SA.Sweep;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SweptAvgHoldKeypadDialog;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class GateFuncSweepView extends LayoutBase {

    ArrayList<View> mSweepList, mGateList;
    LinearLayout linSweep, linGate;
    AutofitTextView tvSweepVal, tvGateVal;
    DynamicView mDynamicView;

    public GateFuncSweepView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();
            mActivity.runOnUiThread(()-> {

                binding.linRightMenu.removeAllViews();
                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.meas_setup));

                binding.linRightMenu.addView(linSweep);
                binding.linRightMenu.addView(linGate);

                binding.tvBack.setOnClickListener(v->{

                });

            });

        }).start();

    }

    public void update() {

        initView();

        mActivity.runOnUiThread(()->{

            tvSweepVal.setText(SaDataHandler.getInstance().getSweptSaConfigData().getSweptSaMeasSetupData().getAvgHold() + "");
            tvGateVal.setText("");
        });

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mSweepList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.sweep_time_name), "Auto", "Manual", "5 ms");
        linSweep = (LinearLayout) mSweepList.get(0);
        tvSweepVal = (AutofitTextView) mSweepList.get(2);

        mGateList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.gate), "Off");
        linGate = (LinearLayout) mGateList.get(0);
        tvGateVal = (AutofitTextView) mGateList.get(2);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()-> {


            linSweep.setOnClickListener(v->{

                new SweptAvgHoldKeypadDialog(mActivity, binding).show();

            });

            linGate.setOnClickListener(v->{


            });

        });

    }
}

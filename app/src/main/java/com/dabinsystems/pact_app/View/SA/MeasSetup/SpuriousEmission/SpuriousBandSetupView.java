package com.dabinsystems.pact_app.View.SA.MeasSetup.SpuriousEmission;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SpuriousEmission.SpuriousHighFreqKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SpuriousEmission.SpuriousLowFreqKeypad;
import com.dabinsystems.pact_app.Dialog.SA.SaSpanFreqKeypad;
import com.dabinsystems.pact_app.Handler.DataHandler;
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

public class SpuriousBandSetupView extends LayoutBase {

    private LinearLayout linLowFreq, linHighFreq;
    private AutofitTextView tvLowFreq, tvHighFreq, tvLowFreqVal, tvHighFreqVal;
    private ArrayList<View> mLowFreqList, mHighFreqList;
    private DynamicView mDynamicView;


    public SpuriousBandSetupView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
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
                binding.linRightMenu.addView(linLowFreq);
                binding.linRightMenu.addView(linHighFreq);

                binding.tvBack.setOnClickListener(v->{

                    ViewHandler.getInstance().getMeasSetupSpuriousView().addMenu();

                });
            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mLowFreqList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.low_freq), "");
        linLowFreq = (LinearLayout) mLowFreqList.get(0);
        tvLowFreq = (AutofitTextView) mLowFreqList.get(1);
        tvLowFreqVal = (AutofitTextView) mLowFreqList.get(2);
        tvLowFreq.setSingleLine(false);

        mHighFreqList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.high_freq), "");
        linHighFreq = (LinearLayout) mHighFreqList.get(0);
        tvHighFreq = (AutofitTextView) mHighFreqList.get(1);
        tvHighFreqVal = (AutofitTextView) mHighFreqList.get(2);
        tvHighFreq.setSingleLine(false);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linLowFreq.setOnClickListener(v -> {

                new SpuriousLowFreqKeypad(mActivity, binding).show();

            });

            linHighFreq.setOnClickListener(v -> {

                new SpuriousHighFreqKeypad(mActivity, binding).show();

            });

        });
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void update() {
        super.update();
        mActivity.runOnUiThread(() -> {

            if (tvLowFreqVal != null) {
                tvLowFreqVal.post(() -> {
                    double val = SaDataHandler.getInstance().getSpuriousEmissionConfigData().getSpuriousEmissionData().getLowFreq();
                    val = Math.round(val * 1000000d)/1000000d;
                    tvLowFreqVal.setText(val + " MHz");
                    tvLowFreqVal.invalidate();

                });
            }

            if (tvHighFreqVal != null) {
                tvHighFreqVal.post(() -> {
                    double val = SaDataHandler.getInstance().getSpuriousEmissionConfigData().getSpuriousEmissionData().getHighFreq();
                    val = Math.round(val * 1000000d) / 1000000d;
                    tvHighFreqVal.setText(val + " MHz");
                    tvHighFreqVal.invalidate();

                });
            }

        });
    }

}

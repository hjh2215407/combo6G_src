package com.dabinsystems.pact_app.View.SA;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.SA.SaCenterFreqKeypad;
import com.dabinsystems.pact_app.Dialog.SA.SaStartFreqKeypad;
import com.dabinsystems.pact_app.Dialog.SA.SaStopFreqKeypad;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import org.apache.poi.hpsf.Util;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SaFrequencyView extends LayoutBase {

    private LinearLayout linFreqStart, linFreqStop, linCenter, linSpan, linFullSpan, linFreqSetting;
    private AutofitTextView tvFreqStartValue, tvFreqStopValue, tvFreqCenterValue, tvFreqSpanValue, tvFreqSetting;
    private ArrayList<View> mFreqStartList, mFreqStopList, mCenterList, mSpanList, mFullSpanList, mSettingList;
    private DynamicView mDynamicView;


    public SaFrequencyView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() {
        super.addMenu();

        new Thread(() -> {
            try {
                initView();

                update();

                mActivity.runOnUiThread(() -> {
                    binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.frequency_name));

                    ViewHandler.getInstance().getSAView().selectFrequency();

                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(linCenter);
                    binding.linRightMenu.addView(linFreqStart);
                    binding.linRightMenu.addView(linFreqStop);

//                    binding.linRightMenu.addView(linSpan);
//        binding.linRightMenu.addView(linFullSpan);

                    binding.linCableList.setVisibility(View.GONE);

                    updateFreq();
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

        mFreqStartList = mDynamicView.addRightMenuButton("Start Freq.", "");
        mFreqStopList = mDynamicView.addRightMenuButton("Stop Freq.", "");
        mCenterList = mDynamicView.addRightMenuButton("Center.", "");
        mSpanList = mDynamicView.addRightMenuButton("Span", "");
        mFullSpanList = mDynamicView.addRightMenuButton("Full Span", Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        linFreqStart = (LinearLayout) mFreqStartList.get(0);
        tvFreqStartValue = (AutofitTextView) mFreqStartList.get(2);

        linFreqStop = (LinearLayout) mFreqStopList.get(0);
        tvFreqStopValue = (AutofitTextView) mFreqStopList.get(2);

        linCenter = (LinearLayout) mCenterList.get(0);
        tvFreqCenterValue = (AutofitTextView) mCenterList.get(2);

        linSpan = (LinearLayout) mSpanList.get(0);
        tvFreqSpanValue = (AutofitTextView) mSpanList.get(2);

        linFullSpan = (LinearLayout) mFullSpanList.get(0);

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
        MainActivity.getActivity().runOnUiThread(() -> {

            if (tvFreqStartValue != null) {
                double val = SaDataHandler.getInstance().getConfigData().getFrequencyData().getStartFreq();
                val = Math.round(val * 1000000d) / 1000000d;
                tvFreqStartValue.setText(val + " MHz");
                tvFreqStartValue.invalidate();
            }

            if (tvFreqStopValue != null) {
                double val = SaDataHandler.getInstance().getConfigData().getFrequencyData().getStopFreq();
                val = Math.round(val * 1000000d) / 1000000d;
                tvFreqStopValue.setText(val + " MHz");
                tvFreqStopValue.invalidate();
            }

            if (tvFreqCenterValue != null) {
                double val = SaDataHandler.getInstance().getConfigData().getFrequencyData().getCenterFreq();
                val = Math.round(val * 1000000d) / 1000000d;
                tvFreqCenterValue.setText(val + " MHz");
                tvFreqCenterValue.invalidate();
            }

//            FunctionHandler.getInstance().getMainLineChart().invalidate();
        });
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        linFreqStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaStartFreqKeypad(mActivity, binding).show();
            }
        });

        linFreqStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaStopFreqKeypad(mActivity, binding).show();
            }
        });

        linCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaCenterFreqKeypad(mActivity, binding).show();
            }
        });

        binding.tvBack.setOnClickListener(v -> {
        });
    }
}

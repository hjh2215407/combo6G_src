package com.dabinsystems.pact_app.View;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.VSWR.VswrCenterFreqKeypad;
import com.dabinsystems.pact_app.Dialog.VSWR.VswrSpanFreqKeypad;
import com.dabinsystems.pact_app.Dialog.VSWR.VswrStartFreqKeypad;
import com.dabinsystems.pact_app.Dialog.VSWR.VswrStopFreqKeypad;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

/**
 * Freq 하단 버튼 클릭 시 나오는 오른쪽 메뉴 (VSWR, DTF 공통으로 사용 중)
 */
public class FrequencyView extends LayoutBase {

    private LinearLayout linFreqStart, linFreqStop, linCenter, linSpan, linFullSpan;
    private AutofitTextView tvFreqStartValue, tvFreqStopValue, tvFreqCenterValue, tvFreqSpanValue;
    private DynamicView mDynamicView;

    public FrequencyView(MainActivity activity, ActivityMainBinding binding) {
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
                    ViewHandler.getInstance().getVswrView().selectFrequency();

                    binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.frequency_name));

                    binding.linRightMenu.removeAllViews();

//        binding.linRightMenu.addView(linFreqSetting);
                    binding.linRightMenu.addView(linFreqStart);
                    binding.linRightMenu.addView(linFreqStop);
                    binding.linRightMenu.addView(linCenter);
                    binding.linRightMenu.addView(linSpan);
//        binding.linRightMenu.addView(linFullSpan);

                    binding.linCableList.setVisibility(View.GONE);
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void initView() {

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity.getApplicationContext());

//        mSettingList = mDynamicView.addRightMenuButton("Setting", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        ArrayList<View> mFreqStartList = mDynamicView.addRightMenuButton("Start Freq.", FunctionHandler.getInstance().getMainLineChart().getStartFreq() + "");
        ArrayList<View> mFreqStopList = mDynamicView.addRightMenuButton("Stop Freq.", FunctionHandler.getInstance().getMainLineChart().getStopFreq() + "");
        ArrayList<View> mCenterList = mDynamicView.addRightMenuButton("Center.", FunctionHandler.getInstance().getMainLineChart().getCenterFreq() + "");
        ArrayList<View> mSpanList = mDynamicView.addRightMenuButton("Span", FunctionHandler.getInstance().getMainLineChart().getSpan() + "");
        ArrayList<View> mFullSpanList = mDynamicView.addRightMenuButton("Full Span");

//        linFreqSetting = (LinearLayout) mSettingList.get(0);
//        tvFreqSetting = (AutofitTextView) mSettingList.get(1);

        linFreqStart = (LinearLayout) mFreqStartList.get(0);
        tvFreqStartValue = (AutofitTextView) mFreqStartList.get(2);

        linFreqStop = (LinearLayout) mFreqStopList.get(0);
        tvFreqStopValue = (AutofitTextView) mFreqStopList.get(2);

        linCenter = (LinearLayout) mCenterList.get(0);
        tvFreqCenterValue = (AutofitTextView) mCenterList.get(2);

        linSpan = (LinearLayout) mSpanList.get(0);
        tvFreqSpanValue = (AutofitTextView) mSpanList.get(2);

        linFullSpan = (LinearLayout) mFullSpanList.get(0);

        mActivity.runOnUiThread(this::setUpEvents);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void update() {
        super.update();

        mActivity.runOnUiThread(() -> {

            double start = VswrDataHandler.getInstance().getConfigData().getFrequencyData().getStartFreq();
//            start = Math.round(start * 1000)/1000;

            tvFreqStartValue.setText(start + " MHz");

            double stop = VswrDataHandler.getInstance().getConfigData().getFrequencyData().getStopFreq();
            stop = Math.round(stop * 1000)/1000;
            tvFreqStopValue.setText(stop + " MHz");

            double center = VswrDataHandler.getInstance().getConfigData().getFrequencyData().getCenterFreq();
            center = Math.round(center * 1000)/1000;
            tvFreqCenterValue.setText(center + " MHz");

            double span = VswrDataHandler.getInstance().getConfigData().getFrequencyData().getSpan();
            span = Math.round(span * 1000)/1000;
            tvFreqSpanValue.setText(span + " MHz");


        });

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        linFreqStart.setOnClickListener(v -> {
            new VswrStartFreqKeypad(mActivity, binding).show();
        });

        linFreqStop.setOnClickListener(v -> {
            new VswrStopFreqKeypad(mActivity, binding).show();
        });

        linCenter.setOnClickListener(v -> {
            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
            //@@ [21.12.20] DTF mode 에서 CenterFreq 값 지정 가능 하도록 수정
            //@@ [21.12.23] CL mode 포함
            if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL)
                new VswrCenterFreqKeypad(mActivity, binding).show();
        });

        linSpan.setOnClickListener(v -> {
            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
            //@@ [21.12.20] DTF mode 에서 span 값 지정 가능하도록 수정
            //@@ [21.12.23] CL mode 포함
            if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL)
                new VswrSpanFreqKeypad(mActivity, binding).show();
        });

        binding.tvBack.setOnClickListener(v -> {

        });
    }
}

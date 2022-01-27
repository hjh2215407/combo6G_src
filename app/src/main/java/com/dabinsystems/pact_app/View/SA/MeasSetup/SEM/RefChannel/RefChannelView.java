package com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.RefChannel;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemMeasSetupData;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SEM.RefChannelIntegBwKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SEM.SemSpanFreqKeypad;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class RefChannelView extends LayoutBase {

    private LinearLayout linSpan, linRBW, linVBW, linIntegBw;
    private AutofitTextView tvSpan, tvRbw, tvVbw, tvIntegBw,
            tvSpanVal, tvRbwVal, tvVbwVal, tvIntegBwVal;
    private ArrayList<View> mSpanList, mRbwList, mVbwList, mIntegBwList;
    private SemMeasSetupData SemData;
    private DynamicView mDynamicView;

    public RefChannelView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {

                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.ref_channel));
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linSpan);
                binding.linRightMenu.addView(linIntegBw);
                binding.linRightMenu.addView(linRBW);
                binding.linRightMenu.addView(linVBW);

                binding.tvBack.setOnClickListener(v->{

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

        SemData = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData();

        double span = SemData.getRefChannelData().getSpan();
        span = Math.round(span * 1000000)/1000000;

        mSpanList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.span_name), span + " MHz");
        linSpan = (LinearLayout) mSpanList.get(0);
        tvSpan = (AutofitTextView) mSpanList.get(1);
        tvSpanVal = (AutofitTextView) mSpanList.get(2);

        mIntegBwList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.integ_bw), SemData.getRefChannelData().getIntegBw() + " MHz");
        linIntegBw = (LinearLayout) mIntegBwList.get(0);
        tvIntegBw = (AutofitTextView) mIntegBwList.get(1);
        tvIntegBwVal = (AutofitTextView) mIntegBwList.get(2);

        mRbwList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.rbw_name), SemData.getRefChannelData().getBwData().getRBW().getString());
        linRBW = (LinearLayout) mRbwList.get(0);
        tvRbw = (AutofitTextView) mRbwList.get(1);
        tvRbwVal = (AutofitTextView) mRbwList.get(2);

        mVbwList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.vbw_name), SemData.getRefChannelData().getBwData().getVBW().getString());
        linVBW = (LinearLayout) mVbwList.get(0);
        tvVbw = (AutofitTextView) mVbwList.get(1);
        tvVbwVal = (AutofitTextView) mVbwList.get(2);


        setUpEvents();

    }

    @SuppressLint("SetTextI18n")
    public void update() {

        initView();

        mActivity.runOnUiThread(()->{

            double span = SemData.getRefChannelData().getSpan();
            span = Math.round(span * 1000000)/1000000;

            tvSpanVal.setText(span + " MHz");

            tvIntegBwVal.setText(
                    Utils.formatNumber(SemData.getRefChannelData().getIntegBw(), 2, false) + " MHz"

            );

            tvRbwVal.setText(SemData.getRefChannelData().getBwData().getRBW().getString());
            tvVbwVal.setText(SemData.getRefChannelData().getBwData().getVBW().getString());

        });

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linSpan.setOnClickListener(v -> {

                new SemSpanFreqKeypad(mActivity, binding).show();

            });

            linRBW.setOnClickListener(v -> {

                ViewHandler.getInstance().getRefChannelRBWView().addMenu();

            });

            linVBW.setOnClickListener(v -> {

                ViewHandler.getInstance().getRefChannelVBWView().addMenu();

            });

            linIntegBw.setOnClickListener(v -> {

                new RefChannelIntegBwKeypad(mActivity, binding).show();

            });

        });

    }

}

package com.dabinsystems.pact_app.View.ModAccuracy.NR5G;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.TriggerSource;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.NR5G.Nr5gBeamPowerRefLevKeypadDialog;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.NR5G.Nr5gConstellationKeypadDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class NrMeasSetupView2 extends LayoutBase {

    ArrayList<View> mProfileList, mSSBInfoList, mLimitList, mTaeList,  mConstellationScaleList, mBeamPwrRefList, mGuideLineList, mTriggerSourceList;
    LinearLayout linProfile, linSSB, linLimit, linTae, linConstellation, linBeamPwrRef, linGuideLine, linTriggerSource, linDuplex, linSCS, linMore;
    AutofitTextView tvScaleVal, tvBeamPwrRef, tvGuideLine, tvStatus, tvInternal;

    AutofitTextView tvDuplex, tvDuplexTDD, tvDuplexFDD;
    AutofitTextView tvSCS, tvSCS1, tvSCS2;

    DynamicView mDynamicView;

    public NrMeasSetupView2(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();
            mActivity.runOnUiThread(()-> {

                try {

                    binding.linRightMenu.removeAllViews();
                    binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.meas_setup));

//                    binding.linRightMenu.addView(linProfile);
//                    binding.linRightMenu.addView(linSSB);
//                    binding.linRightMenu.addView(linTae);
//                    binding.linRightMenu.addView(linLimit);
                    binding.linRightMenu.addView(linConstellation);
                    binding.linRightMenu.addView(linBeamPwrRef);
//                    binding.linRightMenu.addView(linGuideLine);
//                    binding.linRightMenu.addView(linTriggerSource);
                    binding.linRightMenu.addView(linMore);

                } catch (NullPointerException | IllegalArgumentException e) {
                    e.printStackTrace();
                }

            });

        }).start();

    }

    @SuppressLint("SetTextI18n")
    public void update() {

        initView();

        mActivity.runOnUiThread(()->{

            try {

                tvScaleVal.setText(FunctionHandler.getInstance().getScatterChart().getConstellationScale() + "");
                tvBeamPwrRef.setText(FunctionHandler.getInstance().getCandleChartFunc().getBeamPowerRefLev() + " dBm");

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

//        mProfileList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.profile));
//        linProfile = (LinearLayout) mProfileList.get(0);

//        mSSBInfoList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ssb_info));
//        linSSB = (LinearLayout) mSSBInfoList.get(0);

//        mTaeList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.tae_title));
//        linTae = (LinearLayout) mTaeList.get(0);

//        mLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.limit_name));
//        linLimit = (LinearLayout) mLimitList.get(0);

        mConstellationScaleList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.constellation_scale), "");
        linConstellation = (LinearLayout) mConstellationScaleList.get(0);
        tvScaleVal = (AutofitTextView) mConstellationScaleList.get(2);

        mBeamPwrRefList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.beam_pwr_ref), "");
        linBeamPwrRef = (LinearLayout) mBeamPwrRefList.get(0);
        tvBeamPwrRef = (AutofitTextView) mBeamPwrRefList.get(2);

//        mGuideLineList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.guide_line));
//        linGuideLine = (LinearLayout) mGuideLineList.get(0);

//        mTriggerSourceList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.trigger_source), "");
//        linTriggerSource = (LinearLayout)mTriggerSourceList.get(0);
//        tvStatus = (AutofitTextView)mTriggerSourceList.get(2);

        ArrayList<View> mMoreList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.more_name), "2/2");
        linMore = (LinearLayout) mMoreList.get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()-> {

            linConstellation.setOnClickListener(v->{

                new Nr5gConstellationKeypadDialog(mActivity, binding).show();

            });

            linBeamPwrRef.setOnClickListener(v->{

                new Nr5gBeamPowerRefLevKeypadDialog(mActivity, binding).show();

            });

            linMore.setOnClickListener(v -> ViewHandler.getInstance().getNrMeasSetupView().addMenu());

        });

    }

}

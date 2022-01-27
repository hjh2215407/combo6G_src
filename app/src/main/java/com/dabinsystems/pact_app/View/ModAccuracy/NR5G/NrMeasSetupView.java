package com.dabinsystems.pact_app.View.ModAccuracy.NR5G;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrDuplexData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrSCSData;
import com.dabinsystems.pact_app.Data.ModAccuracy.TriggerSource;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class NrMeasSetupView extends LayoutBase {

    ArrayList<View> mProfileList, mSSBInfoList, mLimitList, mTaeList,  mConstellationScaleList, mBeamPwrRefList, mGuideLineList, mTriggerSourceList;
    LinearLayout linProfile, linSSB, linLimit, linTae, linConstellation, linBeamPwrRef, linGuideLine, linTriggerSource, linDuplex, linSCS, linMore;
    AutofitTextView tvScaleVal, tvBeamPwrRef, tvGuideLine, tvStatus, tvInternal, tvProfile;

    AutofitTextView tvDuplex, tvDuplexTDD, tvDuplexFDD;
    AutofitTextView tvSCS, tvSCS1, tvSCS2;

    DynamicView mDynamicView;

    public NrMeasSetupView(MainActivity activity, ActivityMainBinding binding) {
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

                    //@@ [21.12.30] 5G NR Setup Menu Tree fix
                    binding.linRightMenu.addView(linDuplex);
                    //@@

                    binding.linRightMenu.addView(linProfile);

                    //@@ [21.12.30] 5G NR Setup Menu Tree fix
                    binding.linRightMenu.addView(linSCS);
                    //@@

                    binding.linRightMenu.addView(linSSB);
                    binding.linRightMenu.addView(linTae);
                    binding.linRightMenu.addView(linLimit);
//                    binding.linRightMenu.addView(linConstellation);
//                    binding.linRightMenu.addView(linBeamPwrRef);
//                    binding.linRightMenu.addView(linGuideLine);
                    binding.linRightMenu.addView(linTriggerSource);
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

//                tvScaleVal.setText(FunctionHandler.getInstance().getScatterChart().getConstellationScale() + "");
//                tvBeamPwrRef.setText(FunctionHandler.getInstance().getCandleChartFunc().getBeamPowerRefLev() + " dBm");

                TriggerSource triggerData = DataHandler.getInstance().getNrData().getTriggerSource();

                tvStatus.setText(triggerData.getTriggerSource().getModeString());

                //@@ [22.01.24] Defect 126. Profile 설정값 표시
                tvProfile.setText(DataHandler.getInstance().getNrData().getProfileData().getProfile().getString());
                //@@

//                if(triggerData.getTriggerSource() == NrTriggerSource.TRIGGER_SOURCE.INTERNAL) {
//
//                    selectOptionView(tvInternal, tvStatus);
//
//                } else {
//
//                    selectOptionView(tvStatus, tvInternal);
//
//                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

        updateDuplexView();
        updateScsView();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        //@@ [21.12.30] 5G NR Menu Tree fix
        ArrayList<View> mDuplexList = mDynamicView
                .addRightMenuButton(
                        mActivity.getResources().getString(R.string.duplex),
                        "TDD", "FDD");
        linDuplex = (LinearLayout) mDuplexList.get(0);
        tvDuplex = (AutofitTextView) mDuplexList.get(1);
        tvDuplexTDD = (AutofitTextView) mDuplexList.get(2);
        tvDuplexFDD = (AutofitTextView) mDuplexList.get(3);
        //@@

        mProfileList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.profile), "");
        linProfile = (LinearLayout) mProfileList.get(0);
        //@@ [22.01.24] Defect 126. Profile 설정값 표시
        tvProfile = (AutofitTextView) mProfileList.get(2);
        //@@

        //@@ [21.12.30] 5G NR Menu Tree fix
        ArrayList<View> mSCSList = mDynamicView
                .addRightMenuButton(
                        mActivity.getResources().getString(R.string.scs),
                        "30 kHz", "15 kHz");
        linSCS = (LinearLayout) mSCSList.get(0);
        tvSCS = (AutofitTextView) mSCSList.get(1);
        tvSCS1 = (AutofitTextView) mSCSList.get(2);
        tvSCS2 = (AutofitTextView) mSCSList.get(3);
        //@@

        mSSBInfoList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ssb_info));
        linSSB = (LinearLayout) mSSBInfoList.get(0);

        mTaeList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.tae_title));
        linTae = (LinearLayout) mTaeList.get(0);

        mLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.limit_name));
        linLimit = (LinearLayout) mLimitList.get(0);

        /*mConstellationScaleList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.constellation_scale), "");
        linConstellation = (LinearLayout) mConstellationScaleList.get(0);
        tvScaleVal = (AutofitTextView) mConstellationScaleList.get(2);

        mBeamPwrRefList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.beam_pwr_ref), "");
        linBeamPwrRef = (LinearLayout) mBeamPwrRefList.get(0);
        tvBeamPwrRef = (AutofitTextView) mBeamPwrRefList.get(2);

        mGuideLineList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.guide_line));
        linGuideLine = (LinearLayout) mGuideLineList.get(0);*/

        mTriggerSourceList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.trigger_source), "");
        linTriggerSource = (LinearLayout)mTriggerSourceList.get(0);
        tvStatus = (AutofitTextView)mTriggerSourceList.get(2);

        ArrayList<View> mMoreList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.more_name), "1/2");
        linMore = (LinearLayout) mMoreList.get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()-> {

            //@@ [21.12.30] 5G NR Menu Tree fix
            linDuplex.setOnClickListener(v -> {

                NrDuplexData.DUPLEX_TYPE duplex = DataHandler.getInstance().getNrData().getDuplexData().getDuplexType();

                if (duplex == NrDuplexData.DUPLEX_TYPE.TDD) {

                    DataHandler.getInstance().getNrData().getDuplexData().setDuplexType(NrDuplexData.DUPLEX_TYPE.FDD);

                }
                else {

                    DataHandler.getInstance().getNrData().getDuplexData().setDuplexType(NrDuplexData.DUPLEX_TYPE.TDD);

                }

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                updateDuplexView();
            });
            //@@

            linProfile.setOnClickListener(v->{

                ViewHandler.getInstance().getNr5gProfileView1().addMenu();

            });

            //@@ [21.12.30] 5G NR Menu Tree fix
            linSCS.setOnClickListener(v -> {

                NrSCSData.SCS scs = DataHandler.getInstance().getNrData().getScsData().getSCS();
                
                if (scs == NrSCSData.SCS.KHZ15) {
                    DataHandler.getInstance().getNrData().getScsData().setSCS(NrSCSData.SCS.KHZ30);
                }
                else {
                    DataHandler.getInstance().getNrData().getScsData().setSCS(NrSCSData.SCS.KHZ15);
                }
                
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );
                
                updateScsView();
            });
            //@@

            linSSB.setOnClickListener(v->{

                ViewHandler.getInstance().getSSBInfoView().addMenu();

            });

            linTae.setOnClickListener(v->{

                ViewHandler.getInstance().getTaeView().addMenu();

            });

            linLimit.setOnClickListener(v->{

                ViewHandler.getInstance().getNrLimitView().addMenu();

            });

            /*linConstellation.setOnClickListener(v->{

                new Nr5gConstellationKeypadDialog(mActivity, binding).show();

            });

            linBeamPwrRef.setOnClickListener(v -> {

                new Nr5gBeamPowerRefLevKeypadDialog(mActivity, binding).show();

            });

            linGuideLine.setOnClickListener(v->{



            });*/

            linTriggerSource.setOnClickListener(v -> {

                ViewHandler.getInstance().getNrSelectTriggerSourceView().addMenu();

//                NrTriggerSource triggerData = DataHandler.getInstance().getNrData().getTriggerSource();
//                NrTriggerSource.TRIGGER_SOURCE source = triggerData.getTriggerSource();

//                if(source == NrTriggerSource.TRIGGER_SOURCE.INTERNAL)
//                    triggerData.setTriggerSource(NrTriggerSource.TRIGGER_SOURCE.GPS);
//                else triggerData.setTriggerSource(NrTriggerSource.TRIGGER_SOURCE.INTERNAL);
//
//                FunctionHandler.getInstance().getDataConnector().sendCommand(
//                        DataHandler.getInstance().getNrData().getNrCmd()
//                );
//
//                update();


            });

            linMore.setOnClickListener(v -> ViewHandler.getInstance().getNrMeasSetupView2().addMenu());

        });

    }

    public void updateDuplexView() {

        mActivity.runOnUiThread(() -> {

            NrDuplexData.DUPLEX_TYPE duplexType = DataHandler.getInstance().getNrData().getDuplexData().getDuplexType();

            if (duplexType == NrDuplexData.DUPLEX_TYPE.TDD) {

                selectOptionView(tvDuplexTDD, tvDuplexFDD);

            }
            else {

                selectOptionView(tvDuplexFDD, tvDuplexTDD);

            }
        });
    }

    public void updateScsView() {

        mActivity.runOnUiThread(() -> {

            NrSCSData.SCS scs = DataHandler.getInstance().getNrData().getScsData().getSCS();

            if (scs == NrSCSData.SCS.KHZ30) {

                selectOptionView(tvSCS1, tvSCS2);

            }
            else {

                selectOptionView(tvSCS2, tvSCS1);

            }
        });
    }
}

package com.dabinsystems.pact_app.View.ModAccuracy.LteFdd;

import android.annotation.SuppressLint;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.LteFdd.LteFddConstellationKeypadDialog;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.NR5G.Nr5gBeamPowerRefLevKeypadDialog;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.NR5G.Nr5gConstellationKeypadDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class LteFddMeasSetupView extends LayoutBase {

    ArrayList<View> mProfileList, mSSBInfoList, mLimitList, mTaeList,  mConstellationScaleList, mGuideLineList, mTriggerList;
    LinearLayout linProfile, linSSB, linLimit, linTae, linConstellation, linGuideLine, linTrigger;
    AutofitTextView tvScaleVal, tvGuideLine, tvStatus, tvProfile;
    DynamicView mDynamicView;

    public LteFddMeasSetupView(MainActivity activity, ActivityMainBinding binding) {
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

                    binding.linRightMenu.addView(linProfile);
                    binding.linRightMenu.addView(linLimit);
                    binding.linRightMenu.addView(linConstellation);
                    binding.linRightMenu.addView(linTrigger);
//                    binding.linRightMenu.addView(linGuideLine);

                    binding.tvBack.setOnClickListener(v->{

                    });

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

                tvScaleVal.setText(FunctionHandler.getInstance().getScatterChart2().getConstellationScale() + "");
                tvStatus.setText(DataHandler.getInstance().getLteFddData().getTriggerSource().getTriggerSource().getModeString());
                //@@ [22.01.24] Defect 126. Profile 설정값 표시
                tvProfile.setText(DataHandler.getInstance().getLteFddData().getProfileData().getProfile().getString());
                //@@

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

        mProfileList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.profile), "");
        linProfile = (LinearLayout) mProfileList.get(0);
        //@@ [22.01.24] Defect 126. Profile 설정값 표시
        tvProfile = (AutofitTextView) mProfileList.get(2);
        //@@

        mSSBInfoList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ssb_info));
        linSSB = (LinearLayout) mSSBInfoList.get(0);

        mTaeList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.tae_title));
        linTae = (LinearLayout) mTaeList.get(0);

        mLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.limit_name));
        linLimit = (LinearLayout) mLimitList.get(0);

        mConstellationScaleList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.constellation_scale), "");
        linConstellation = (LinearLayout) mConstellationScaleList.get(0);
        tvScaleVal = (AutofitTextView) mConstellationScaleList.get(2);

        mGuideLineList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.guide_line));
        linGuideLine = (LinearLayout) mGuideLineList.get(0);

        mTriggerList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.trigger_source), "");
        linTrigger = (LinearLayout) mTriggerList.get(0);
        tvStatus = (AutofitTextView) mTriggerList.get(2);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()-> {


            linProfile.setOnClickListener(v->{

                ViewHandler.getInstance().getLteFddProfileView().addMenu();

            });

            linLimit.setOnClickListener(v->{

                ViewHandler.getInstance().getLteFddLimitView().addMenu();

            });

            linConstellation.setOnClickListener(v->{

                new LteFddConstellationKeypadDialog(mActivity, binding).show();

            });

            linTrigger.setOnClickListener(v->{

                /*ViewHandler.getInstance().getNrSelectTriggerSourceView().addMenu();*/
                ViewHandler.getInstance().getLteSelectTriggerSourceView().addMenu();
            });


        });

    }
}

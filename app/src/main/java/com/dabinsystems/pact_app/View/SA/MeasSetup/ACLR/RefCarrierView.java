package com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR;

import android.util.Log;
import android.view.View;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class RefCarrierView extends LayoutBase {

    private ArrayList<ArrayList<View>> mSelectList;
    private DynamicView mDynamicView;

    public RefCarrierView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            update();
            initView();

            mActivity.runOnUiThread(() -> {

                binding.linRightMenu.removeAllViews();
                Integer numOffset = SaDataHandler
                        .getInstance()
                        .getAclrConfigData()
                            .getAclrMeasSetupData()
                        .getCarrierSetupData()
                        .getCarriers();

                for(int i=0; i<numOffset; i++) {
                    InitActivity.logMsg("RefCarrier", i + "");
                    binding.linRightMenu.addView(mSelectList.get(i).get(0));

                }

                binding.tvBack.setOnClickListener(v -> {

                    ViewHandler
                            .getInstance()
                            .getCarrierSetupView()
                            .addMenu();

                });

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);
        mSelectList = mDynamicView.getMultipleLayout(new String[]{"1", "2", "3", "4", "5"});



        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            for(int i=0; i<5; i++) {

                int finalI = i;
                mSelectList.get(i).get(0).setOnClickListener(v->{

                    SaDataHandler
                            .getInstance()
                            .getAclrConfigData()
                            .getAclrMeasSetupData()
                            .getCarrierSetupData()
                            .setRefCarrier(finalI + 1);

                    ViewHandler.getInstance().getCarrierSetupView().addMenu();

                });

            }

        });
    }
}

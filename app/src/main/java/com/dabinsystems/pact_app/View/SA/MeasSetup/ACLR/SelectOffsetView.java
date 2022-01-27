package com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class SelectOffsetView extends LayoutBase {

    private LinearLayout[] linSelect;
    private ArrayList<ArrayList<View>> mSelectList;
    private DynamicView mDynamicView;

    public SelectOffsetView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {

                binding.linRightMenu.removeAllViews();
                Integer numOffset = SaDataHandler
                        .getInstance()
                        .getAclrConfigData()
                            .getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .getNumOfOffset();

                for(int i=0; i<numOffset; i++) {

                    binding.linRightMenu.addView(mSelectList.get(i).get(0));

                }

                binding.tvBack.setOnClickListener(v -> {

                    ViewHandler
                            .getInstance()
                            .getOffsetSetupView()
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
                            .getOffsetSetupData()
                            .setSelectOffset(finalI);

                    ViewHandler.getInstance().getOffsetSetupView().addMenu();

                });

            }

        });
    }
}

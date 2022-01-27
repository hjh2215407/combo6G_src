package com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class NumberOfOffsetView extends LayoutBase {

    private LinearLayout linSelect1, linSelect2, linSelect3, linSelect4, linSelect5;
    private ArrayList<View> mSelectList1, mSelectList2, mSelectList3, mSelectList4, mSelectList5;
    private DynamicView mDynamicView;

    public NumberOfOffsetView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {

                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linSelect1);
                binding.linRightMenu.addView(linSelect2);

                //@@ [21.12.20] num of offset 3 이상으로 설정되지 않도록 수정
                /*binding.linRightMenu.addView(linSelect3);
                binding.linRightMenu.addView(linSelect4);
                binding.linRightMenu.addView(linSelect5);*/
                //@@

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mSelectList1 = mDynamicView.addRightMenuButton("1", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linSelect1 = (LinearLayout) mSelectList1.get(0);

        mSelectList2 = mDynamicView.addRightMenuButton("2", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linSelect2 = (LinearLayout) mSelectList2.get(0);

        mSelectList3 = mDynamicView.addRightMenuButton("3", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linSelect3 = (LinearLayout) mSelectList3.get(0);

        mSelectList4 = mDynamicView.addRightMenuButton("4", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linSelect4 = (LinearLayout) mSelectList4.get(0);

        mSelectList5 = mDynamicView.addRightMenuButton("5", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linSelect5 = (LinearLayout) mSelectList5.get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {


            linSelect1.setOnClickListener(v -> {

                SaDataHandler
                        .getInstance()
                        .getAclrConfigData()
                            .getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setNumOfOffset(1);

                ViewHandler
                        .getInstance()
                        .getOffsetSetupView()
                        .addMenu();

            });

            linSelect2.setOnClickListener(v -> {

                SaDataHandler
                        .getInstance()
                        .getAclrConfigData()
                            .getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setNumOfOffset(2);

                ViewHandler
                        .getInstance()
                        .getOffsetSetupView()
                        .addMenu();

            });

            linSelect3.setOnClickListener(v -> {

                SaDataHandler
                        .getInstance()
                        .getAclrConfigData()
                            .getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setNumOfOffset(3);

                ViewHandler
                        .getInstance()
                        .getOffsetSetupView()
                        .addMenu();

            });

            linSelect4.setOnClickListener(v -> {

                SaDataHandler
                        .getInstance()
                        .getAclrConfigData()
                            .getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setNumOfOffset(4);

                ViewHandler
                        .getInstance()
                        .getOffsetSetupView()
                        .addMenu();

            });

            linSelect5.setOnClickListener(v->{

                SaDataHandler
                        .getInstance()
                        .getAclrConfigData()
                            .getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .setNumOfOffset(5);

                ViewHandler
                        .getInstance()
                        .getOffsetSetupView()
                        .addMenu();

            });

            binding.tvBack.setOnClickListener(v -> {

                ViewHandler
                        .getInstance()
                        .getOffsetSetupView()
                        .addMenu();

            });

        });
    }
}

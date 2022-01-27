package com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class CarriersView extends LayoutBase {

    private LinearLayout linSelect1, linSelect2;
    private ArrayList<View> mSelectList1, mSelectList2;
    private DynamicView mDynamicView;

    public CarriersView(MainActivity activity, ActivityMainBinding binding) {
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

        mSelectList1 = mDynamicView.addRightMenuButton("1", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linSelect1 = (LinearLayout) mSelectList1.get(0);

        mSelectList2 = mDynamicView.addRightMenuButton("2", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linSelect2 = (LinearLayout) mSelectList2.get(0);

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
                        .getCarrierSetupData()
                        .setCarriers(1);

                ViewHandler
                        .getInstance()
                        .getCarrierSetupView()
                        .addMenu();

                FunctionHandler.getInstance().getMainLineChart().calCarrierBox();

            });

            linSelect2.setOnClickListener(v -> {

                SaDataHandler
                        .getInstance()
                        .getAclrConfigData()
                            .getAclrMeasSetupData()
                        .getCarrierSetupData()
                        .setCarriers(2);

                ViewHandler
                        .getInstance()
                        .getCarrierSetupView()
                        .addMenu();

            });

        });
    }
}

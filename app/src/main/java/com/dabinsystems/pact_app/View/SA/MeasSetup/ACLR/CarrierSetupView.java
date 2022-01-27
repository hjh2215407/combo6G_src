package com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.ACLR.CarrierSetupIntegBwKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.ACLR.CarrierSpacingKeypad;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class CarrierSetupView extends LayoutBase {

    private LinearLayout linCarriers, linRefCarrier, linCarrierSpacing, linIntegBw;
    private AutofitTextView tvCarriers, tvRefCarrier, tvCarrierSpacing, tvIntegBw,
            tvCarriersVal, tvRefCarrierVal, tvCarrierSpacingVal, tvIntegBwVal;
    private ArrayList<View> mCarriersList, mRefCarrierList, mCarrierSpacingList, mIntegBwList;
    private DynamicView mDynamicView;

    public CarrierSetupView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {

                binding.tvRightMenuTitle.setText("Carrier Setup");
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linCarriers);
                binding.linRightMenu.addView(linRefCarrier);
                binding.linRightMenu.addView(linCarrierSpacing);
                binding.linRightMenu.addView(linIntegBw);

                binding.tvBack.setOnClickListener(v -> {

                    ViewHandler.getInstance().getMeasSetupAclrView().addMenu();

                });

//                tvModeVal.setText(CommandData.getInstance().getTraceModeToString());
//                tvTypeVal.setText(CommandData.getInstance().getTraceTypeToString());
//                tvDetectorVal.setText(CommandData.getInstance().getTraceDetectorToString());

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mCarriersList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.carriers), "");
        linCarriers = (LinearLayout) mCarriersList.get(0);
        tvCarriers = (AutofitTextView) mCarriersList.get(1);
        tvCarriersVal = (AutofitTextView) mCarriersList.get(2);

        mRefCarrierList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ref_carrier), "");
        linRefCarrier = (LinearLayout) mRefCarrierList.get(0);
        tvRefCarrier = (AutofitTextView) mRefCarrierList.get(1);
        tvRefCarrierVal = (AutofitTextView) mRefCarrierList.get(2);

        mCarrierSpacingList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.carrier_spacing), " MHz");
        linCarrierSpacing = (LinearLayout) mCarrierSpacingList.get(0);
        tvCarrierSpacing = (AutofitTextView) mCarrierSpacingList.get(1);
        tvCarrierSpacingVal = (AutofitTextView) mCarrierSpacingList.get(2);

        mIntegBwList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.integ_bw), " MHz");
        linIntegBw = (LinearLayout) mIntegBwList.get(0);
        tvIntegBw = (AutofitTextView) mIntegBwList.get(1);
        tvIntegBwVal = (AutofitTextView) mIntegBwList.get(2);

        setUpEvents();

    }

    @SuppressLint("SetTextI18n")
    public void update() {

        initView();

        mActivity.runOnUiThread(() -> {

            tvCarriersVal.setText(

                    SaDataHandler
                            .getInstance()
                            .getAclrConfigData()
                            .getAclrMeasSetupData()
                            .getCarrierSetupData()
                            .getCarriers() + ""

            );

            tvRefCarrierVal.setText(

                    SaDataHandler
                            .getInstance()
                            .getAclrConfigData()
                            .getAclrMeasSetupData()
                            .getCarrierSetupData()
                            .getRefCarrier() + ""

            );

            double integ = SaDataHandler
                    .getInstance()
                    .getAclrConfigData()
                    .getAclrMeasSetupData()
                    .getCarrierSetupData()
                    .getIntegBw();

            integ = Math.round(integ * 10000d) / 10000d;
            tvIntegBwVal.setText(integ + " MHz"

            );

            double carrierSpacing = SaDataHandler
                    .getInstance()
                    .getAclrConfigData()
                    .getAclrMeasSetupData()
                    .getCarrierSetupData()
                    .getCarrierSpacing();

            carrierSpacing = Math.round(carrierSpacing * 10000d)/10000d;

            tvCarrierSpacingVal.setText(carrierSpacing + " MHz");

        });

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linCarriers.setOnClickListener(v -> {

                ViewHandler.getInstance().getCarriersView().addMenu();

            });

            linRefCarrier.setOnClickListener(v -> {

                ViewHandler.getInstance().getRefCarrierView().addMenu();

            });

            linCarrierSpacing.setOnClickListener(v -> {

                new CarrierSpacingKeypad(mActivity, binding).show();

            });

            linIntegBw.setOnClickListener(v -> {

                new CarrierSetupIntegBwKeypad(mActivity, binding).show();

            });

        });

    }

}

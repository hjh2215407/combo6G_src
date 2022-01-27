package com.dabinsystems.pact_app.View.SA.Standard;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class LteStandardView extends LayoutBase {

    private LinearLayout lin1d4MHz, lin5MHz, lin10MHz, lin15MHz, lin20MHz, lin3MHz;
    private DynamicView mDynamicView;


    public LteStandardView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);

    }


    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {
                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.standard_title));
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(lin1d4MHz);
                binding.linRightMenu.addView(lin3MHz);
                binding.linRightMenu.addView(lin5MHz);
                binding.linRightMenu.addView(lin10MHz);
                binding.linRightMenu.addView(lin15MHz);
                binding.linRightMenu.addView(lin20MHz);

                binding.tvBack.setOnClickListener(v -> {

                    ViewHandler.getInstance().getStandardView().addMenu();

                });

            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        String[] nameArr = {"1.4 MHz", "3 MHz", "5 MHz", "10 MHz", "15 MHz", "20 MHz"};
        ArrayList<ArrayList<View>> layoutList = mDynamicView.getMultipleLayout(nameArr);

        lin1d4MHz = (LinearLayout) layoutList.get(0).get(0);
        lin3MHz = (LinearLayout) layoutList.get(1).get(0);
        lin5MHz = (LinearLayout) layoutList.get(2).get(0);
        lin10MHz = (LinearLayout) layoutList.get(3).get(0);
        lin15MHz = (LinearLayout) layoutList.get(4).get(0);
        lin20MHz = (LinearLayout) layoutList.get(5).get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            lin1d4MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load1d4MHzDataForLte();
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });

            lin3MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load3MHzDataForLte();
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });

            lin5MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load5MHzDataForLte();
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });

            lin10MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load10MHzDataForLte();
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });

            lin15MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load15MHzDataForLte();
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });

            lin20MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load20MHzDataForLte();
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });


            binding.tvBack.setOnClickListener(v -> {
                ViewHandler.getInstance().getBW().addMenu();

            });

        });

    }

}

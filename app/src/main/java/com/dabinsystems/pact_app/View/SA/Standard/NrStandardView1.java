package com.dabinsystems.pact_app.View.SA.Standard;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.PresetDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class NrStandardView1 extends LayoutBase {

    private LinearLayout lin5MHz, lin10MHz, lin15MHz, lin20MHz, lin25MHz, lin30MHz, lin40MHz, linNext;
    private DynamicView mDynamicView;

    public NrStandardView1(MainActivity activity, ActivityMainBinding binding) {
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
                binding.linRightMenu.addView(lin5MHz);
                binding.linRightMenu.addView(lin10MHz);
                binding.linRightMenu.addView(lin15MHz);
                binding.linRightMenu.addView(lin20MHz);
                binding.linRightMenu.addView(lin25MHz);
                binding.linRightMenu.addView(lin30MHz);
                binding.linRightMenu.addView(lin40MHz);
                binding.linRightMenu.addView(linNext);

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

        String[] nameArr = {"5 MHz", "10 MHz", "15 MHz", "20 MHz","25 MHz", "30MHz", "40MHz", "1/2"};
        ArrayList<ArrayList<View>> layoutList = mDynamicView.getMultipleLayout(nameArr);

        lin5MHz = (LinearLayout) layoutList.get(0).get(0);
        lin10MHz = (LinearLayout) layoutList.get(1).get(0);
        lin15MHz = (LinearLayout) layoutList.get(2).get(0);
        lin20MHz = (LinearLayout) layoutList.get(3).get(0);
        lin25MHz = (LinearLayout) layoutList.get(4).get(0);
        lin30MHz = (LinearLayout) layoutList.get(5).get(0);
        lin40MHz = (LinearLayout) layoutList.get(6).get(0);
        linNext = (LinearLayout) layoutList.get(7).get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            lin5MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load5MHzDataFor5G(PresetDialog.UL_DL_STATUS.DL);
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
                DataHandler.getInstance().getStandardLoadData().load10MHzDataFor5G(PresetDialog.UL_DL_STATUS.DL);
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
                DataHandler.getInstance().getStandardLoadData().load15MHzDataFor5G(PresetDialog.UL_DL_STATUS.DL);
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
                DataHandler.getInstance().getStandardLoadData().load20MHzDataFor5G(PresetDialog.UL_DL_STATUS.DL);
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });


            lin25MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load25MHzDataFor5G(PresetDialog.UL_DL_STATUS.DL);
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });

            lin30MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load30MHzDataFor5G(PresetDialog.UL_DL_STATUS.DL);
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });


            lin40MHz.setOnClickListener(v->{
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load40MHzDataFor5G(PresetDialog.UL_DL_STATUS.DL);
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });


            linNext.setOnClickListener(v->{

                ViewHandler.getInstance().getNrStandardView2().addMenu();

            });


            binding.tvBack.setOnClickListener(v -> {
                ViewHandler.getInstance().getBW().addMenu();

            });

        });

    }

}

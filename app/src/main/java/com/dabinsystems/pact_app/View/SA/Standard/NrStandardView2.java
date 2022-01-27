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

public class NrStandardView2 extends LayoutBase {

    private LinearLayout lin50MHz, lin60MHz, lin70MHz, lin80MHz, lin90MHz, lin100MHz, linPrev;
    private DynamicView mDynamicView;

    public NrStandardView2(MainActivity activity, ActivityMainBinding binding) {
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
                binding.linRightMenu.addView(lin50MHz);
                binding.linRightMenu.addView(lin60MHz);
                binding.linRightMenu.addView(lin70MHz);
                binding.linRightMenu.addView(lin80MHz);
                binding.linRightMenu.addView(lin90MHz);
                binding.linRightMenu.addView(lin100MHz);
                binding.linRightMenu.addView(linPrev);

                binding.tvBack.setOnClickListener(v -> {

                    ViewHandler.getInstance().getNrStandardView1().addMenu();

                });

            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        String[] nameArr = {"50 MHz", "60 MHz", "70 MHz", "80 MHz", "90MHz", "100MHz", "2/2"};
        ArrayList<ArrayList<View>> layoutList = mDynamicView.getMultipleLayout(nameArr);

        lin50MHz = (LinearLayout) layoutList.get(0).get(0);
        lin60MHz = (LinearLayout) layoutList.get(1).get(0);
        lin70MHz = (LinearLayout) layoutList.get(2).get(0);
        lin80MHz = (LinearLayout) layoutList.get(3).get(0);
        lin90MHz = (LinearLayout) layoutList.get(4).get(0);
        lin100MHz = (LinearLayout) layoutList.get(5).get(0);
        linPrev = (LinearLayout) layoutList.get(6).get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            lin50MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load50MHzDataFor5G(PresetDialog.UL_DL_STATUS.DL);
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });


            lin60MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load60MHzDataFor5G(PresetDialog.UL_DL_STATUS.DL);
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });

            lin70MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load70MHzDataFor5G(PresetDialog.UL_DL_STATUS.DL);
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });


            lin80MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load80MHzDataFor5G(PresetDialog.UL_DL_STATUS.DL);
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });


            lin90MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load90MHzDataFor5G(PresetDialog.UL_DL_STATUS.DL);
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });

            lin100MHz.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                DataHandler.getInstance().getStandardLoadData().load100MHzDataFor5G(PresetDialog.UL_DL_STATUS.DL);
                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });


            linPrev.setOnClickListener(v -> {

                ViewHandler.getInstance().getNrStandardView1().addMenu();

            });

        });

    }

}

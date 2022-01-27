package com.dabinsystems.pact_app.View.SA.BW;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.SaConfigData;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Data.SA.BWData;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class SaRBWView1 extends LayoutBase {

    private LinearLayout lin1Hz, lin300Hz, lin1kHz, lin3kHz, lin10kHz, lin30kHz, lin100kHz, linNext, lin1MHz, lin3MHz;
    private DynamicView mDynamicView;


    public SaRBWView1(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);

    }


    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {
                binding.linRightMenu.removeAllViews();

                // DEMO VER
//                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
//
//                switch (type) {
//
//                    case SWEPT_SA:
//                        binding.linRightMenu.addView(lin100kHz);
//                        binding.linRightMenu.addView(lin300kHz);
//                        binding.linRightMenu.addView(lin1MHz);
//                        binding.linRightMenu.addView(lin3MHz);
//                        break;
//
//                    case CHANNEL_POWER:
//                        binding.linRightMenu.addView(lin100kHz);
//                        break;
//
//                    case OCCUPIED_BW:
//                        binding.linRightMenu.addView(lin100kHz);
//                        break;
//
//                    case ACLR:
//                        binding.linRightMenu.addView(lin100kHz);
//                        break;
//
//                }

                /*
                * center freq가 10 MHz 일 경우에만 1Hz 활성화
                * */
                if (SaDataHandler.getInstance().getConfigData().getFrequencyData().getCenterFreq() == 10f)
                    binding.linRightMenu.addView(lin1Hz);

//                binding.linRightMenu.addView(lin1Hz); // 테스트 버전

                binding.linRightMenu.addView(lin300Hz);
                binding.linRightMenu.addView(lin1kHz);
                binding.linRightMenu.addView(lin3kHz);
                binding.linRightMenu.addView(lin10kHz);
                binding.linRightMenu.addView(lin30kHz);
                binding.linRightMenu.addView(lin100kHz);
                binding.linRightMenu.addView(linNext);

                binding.tvBack.setOnClickListener(v -> {

                    ViewHandler.getInstance().getBW().addMenu();

                });

            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        String[] nameArr = {"1 Hz", "1 kHz", "3 kHz", "10 kHz", "30 kHz", "100 kHz", "300 Hz", "1 MHz", "3 MHz"};
        ArrayList<ArrayList<View>> layoutList = mDynamicView.getMultipleLayout(nameArr);

        lin1Hz = (LinearLayout) layoutList.get(0).get(0);
        lin1kHz = (LinearLayout) layoutList.get(1).get(0);
        lin3kHz = (LinearLayout) layoutList.get(2).get(0);
        lin10kHz = (LinearLayout) layoutList.get(3).get(0);
        lin30kHz = (LinearLayout) layoutList.get(4).get(0);
        lin100kHz = (LinearLayout) layoutList.get(5).get(0);
        lin300Hz = (LinearLayout) layoutList.get(6).get(0);
        lin1MHz = (LinearLayout) layoutList.get(7).get(0);
        lin3MHz = (LinearLayout) layoutList.get(8).get(0);
        linNext = (LinearLayout) mDynamicView.addRightMenuButton("More", "1/2").get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            lin1Hz.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.HZ1);
                SaDataHandler.getInstance().getConfigData().getFrequencyData().checkFreqRange();
                FunctionHandler.getInstance().getMainLineChart().invalidate();
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getBW().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin300Hz.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.HZ300);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getBW().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin1kHz.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.KHZ1);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getBW().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin3kHz.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.KHZ3);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getBW().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin10kHz.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.KHZ10);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getBW().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin30kHz.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.KHZ30);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getBW().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin100kHz.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.KHZ100);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getBW().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin1MHz.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.MHZ1);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getBW().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });


            lin3MHz.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.MHZ3);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getBW().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });


            linNext.setOnClickListener(v -> {

                ViewHandler.getInstance().getRBW2().addMenu();

            });


            binding.tvBack.setOnClickListener(v -> {
                ViewHandler.getInstance().getBW().addMenu();

            });

        });

    }

}

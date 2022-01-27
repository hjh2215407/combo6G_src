package com.dabinsystems.pact_app.View.SA.MeasSetup.InterferenceHunting;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.BWData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class UplinkBandView extends LayoutBase {

    private LinearLayout linBand0, linBand1, linBand2, linBand3, linBand4, linBand5;
    private DynamicView mDynamicView;


    public UplinkBandView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);

    }


    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {
                binding.linRightMenu.removeAllViews();

                binding.linRightMenu.addView(linBand0);
                binding.linRightMenu.addView(linBand1);
                binding.linRightMenu.addView(linBand2);
                binding.linRightMenu.addView(linBand3);
                binding.linRightMenu.addView(linBand4);
                binding.linRightMenu.addView(linBand5);

                binding.tvBack.setOnClickListener(v -> {

                    ViewHandler.getInstance().getInterferenceHuntingView().addMenu();

                });

            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        String[] nameArr = {"717 ~ 728 MHz", "824 ~ 849 MHz", "1710 ~ 1785 MHz", "1920 ~ 1980 MHz", "2500 ~ 2570 MHz", "3300 ~ 3900 MHz"};
        ArrayList<ArrayList<View>> layoutList = mDynamicView.getMultipleLayout(nameArr);

        linBand0 = (LinearLayout) layoutList.get(0).get(0);
        linBand1 = (LinearLayout) layoutList.get(1).get(0);
        linBand2 = (LinearLayout) layoutList.get(2).get(0);
        linBand3 = (LinearLayout) layoutList.get(3).get(0);
        linBand4 = (LinearLayout) layoutList.get(4).get(0);
        linBand5 = (LinearLayout) layoutList.get(5).get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linBand0.setOnClickListener(v -> {

                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().setUplinkBand(0);
                FunctionHandler.getInstance().getMainLineChart().invalidate();
                ViewHandler.getInstance().getInterferenceHuntingView().addMenu();
                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().clearTableView();
                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().updateSpan();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linBand1.setOnClickListener(v -> {

                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().setUplinkBand(1);
                FunctionHandler.getInstance().getMainLineChart().invalidate();
                ViewHandler.getInstance().getInterferenceHuntingView().addMenu();
                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().clearTableView();
                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().updateSpan();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linBand2.setOnClickListener(v -> {

                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().setUplinkBand(2);
                FunctionHandler.getInstance().getMainLineChart().invalidate();
                ViewHandler.getInstance().getInterferenceHuntingView().addMenu();
                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().clearTableView();
                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().updateSpan();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linBand3.setOnClickListener(v -> {

                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().setUplinkBand(3);
                FunctionHandler.getInstance().getMainLineChart().invalidate();
                ViewHandler.getInstance().getInterferenceHuntingView().addMenu();
                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().clearTableView();
                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().updateSpan();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linBand4.setOnClickListener(v -> {

                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().setUplinkBand(4);
                FunctionHandler.getInstance().getMainLineChart().invalidate();
                ViewHandler.getInstance().getInterferenceHuntingView().addMenu();
                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().clearTableView();
                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().updateSpan();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linBand5.setOnClickListener(v -> {

                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().setUplinkBand(5);
                FunctionHandler.getInstance().getMainLineChart().invalidate();
                ViewHandler.getInstance().getInterferenceHuntingView().addMenu();
                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().clearTableView();
                SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().updateSpan();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

        });

    }

}

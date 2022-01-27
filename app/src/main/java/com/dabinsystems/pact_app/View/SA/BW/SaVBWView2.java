package com.dabinsystems.pact_app.View.SA.BW;

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

public class SaVBWView2 extends LayoutBase {

    private LinearLayout linPrev, lin300kHz, lin1MHz, lin3MHz;
    private DynamicView mDynamicView;


    public SaVBWView2(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }


    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(lin300kHz);
                binding.linRightMenu.addView(lin1MHz);
                binding.linRightMenu.addView(lin3MHz);
                binding.linRightMenu.addView(linPrev);
            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        String[] nameArr = {"1 MHz", "3 MHz", "300 kHz"};
        ArrayList<ArrayList<View>> layoutList = mDynamicView.getMultipleLayout(nameArr);

        lin1MHz = (LinearLayout) layoutList.get(0).get(0);
        lin3MHz = (LinearLayout) layoutList.get(1).get(0);
        lin300kHz = (LinearLayout) layoutList.get(2).get(0);
        linPrev = (LinearLayout) mDynamicView.addRightMenuButton("More", "2/2").get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linPrev.setOnClickListener(v->{

                ViewHandler.getInstance().getVBW1().addMenu();

            });


            lin1MHz.setOnClickListener(v->{

                SaDataHandler.getInstance().getConfigData().getBwData().setVBW(BWData.BAND_WIDTH.MHZ1);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getBW().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin3MHz.setOnClickListener(v->{

                SaDataHandler.getInstance().getConfigData().getBwData().setVBW(BWData.BAND_WIDTH.MHZ3);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getBW().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin300kHz.setOnClickListener(v->{

                SaDataHandler.getInstance().getConfigData().getBwData().setVBW(BWData.BAND_WIDTH.KHZ300);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getBW().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            binding.tvBack.setOnClickListener(v -> {

                ViewHandler.getInstance().getBW().addMenu();

            });

        });

    }
}

package com.dabinsystems.pact_app.View.System;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.GpsHoldoverData;
import com.dabinsystems.pact_app.Data.LteInfoData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class HoldoverOptionView extends LayoutBase {

    private LinearLayout linGPS, linLTE;
    private DynamicView mDynamicView;

    public HoldoverOptionView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {

                binding.linRightMenu.removeAllViews();
                binding.tvRightMenuTitle.setText("System");

                binding.linRightMenu.addView(linGPS);
                binding.linRightMenu.addView(linLTE);

                binding.tvBack.setOnClickListener(v -> {
                    ViewHandler.getInstance().getSystemView().addMenu();
                });

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity.getApplicationContext());

        // GPS
        ArrayList<View> mGpsList = mDynamicView.addRightMenuButton("GPS", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        // LTE
        ArrayList<View> mLteList = mDynamicView.addRightMenuButton("Tracking", Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        linGPS = (LinearLayout) mGpsList.get(0);
        linLTE = (LinearLayout) mLteList.get(0);

        setUpEvents();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {
            GpsHoldoverData data = DataHandler.getInstance().getGpsHoldoverData();

            linGPS.setOnClickListener(v -> {
                data.setOption(GpsHoldoverData.OPTION.GPS);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getChangeGPSHoldoverCmd()
                );

                ViewHandler.getInstance().getSystemView().addMenu();
            });

            linLTE.setOnClickListener(v -> {
                data.setOption(GpsHoldoverData.OPTION.LTE);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getChangeGPSHoldoverCmd()
                );

                ViewHandler.getInstance().getSystemView().addMenu();

                /// Do Something Here
                /**
                 * in this part, get LTE_Info.ini file data (Frequency, Profile) and set them, send command
                 *
                 */




                ///
            });
        });
    }
}

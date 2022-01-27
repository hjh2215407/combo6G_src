package com.dabinsystems.pact_app.View.System;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SystemData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;


public class SelectClockSourceView extends LayoutBase {

    private LinearLayout linInternal, linGps, linFile, linRestart, linExt10Mhz;
    private DynamicView mDynamicView;
    //private FileView mFileView;

    public SelectClockSourceView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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
                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.system_name));

                binding.linRightMenu.addView(linInternal);
                binding.linRightMenu.addView(linGps);
                binding.linRightMenu.addView(linExt10Mhz);

                binding.linCableList.setVisibility(View.GONE);
                binding.linCalibration.setVisibility(View.GONE);

                binding.tvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewHandler.getInstance().getSystemView().addMenu();
                    }
                });
            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity.getApplicationContext());
        //mFileView = new FileView(mActivity, binding);

        ArrayList<View> mInternalList = mDynamicView.addRightMenuButton("Internal", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        ArrayList<View> mGpsList = mDynamicView.addRightMenuButton("GPS", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        ArrayList<View> mFileList = mDynamicView.addRightMenuButton("File", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        ArrayList<View> mExt10MhzList = mDynamicView.addRightMenuButton("Ext. 10MHz", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        ArrayList<View> mRestartList = mDynamicView.addRightMenuButton("FW Restart", Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        linInternal = (LinearLayout) mInternalList.get(0);
        linGps = (LinearLayout) mGpsList.get(0);
        linFile = (LinearLayout) mFileList.get(0);
        linExt10Mhz = (LinearLayout) mExt10MhzList.get(0);
        linRestart = (LinearLayout) mRestartList.get(0);

        setUpEvents();

    }

    @Override
    public void update() {
        super.update();

//        mActivity.runOnUiThread(() -> {
//            SystemData.CLOCK_SOURCE source = DataHandler.getInstance().getSystemData().getSource();
//
//            linInternal.setSelected(source == SystemData.CLOCK_SOURCE.INTERNAL);
//            linGps.setSelected(source == SystemData.CLOCK_SOURCE.GPS);
//            linExt10Mhz.setSelected(source == SystemData.CLOCK_SOURCE.EXT10MHZ);
//        });
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linInternal.setOnClickListener(v -> {
                SystemData systemData = DataHandler.getInstance().getSystemData();
//                SystemData.CLOCK_SOURCE source = systemData.getSource();
                systemData.setSource(SystemData.CLOCK_SOURCE.INTERNAL);
                FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getChangeClockSourceCmd());

                ViewHandler.getInstance().getSystemView().addMenu();
            });

            linGps.setOnClickListener(v -> {
                SystemData systemData = DataHandler.getInstance().getSystemData();
//                SystemData.CLOCK_SOURCE source = systemData.getSource();
                systemData.setSource(SystemData.CLOCK_SOURCE.GPS);
                FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getChangeClockSourceCmd());

                ViewHandler.getInstance().getSystemView().addMenu();
            });

            linExt10Mhz.setOnClickListener(v -> {
                SystemData systemData = DataHandler.getInstance().getSystemData();
//                SystemData.CLOCK_SOURCE source = systemData.getSource();
                systemData.setSource(SystemData.CLOCK_SOURCE.EXT10MHZ);
                FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getChangeClockSourceCmd());

                ViewHandler.getInstance().getSystemView().addMenu();
            });
        });
    }

}

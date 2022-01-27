package com.dabinsystems.pact_app.View.System;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.GpsHoldoverData;
import com.dabinsystems.pact_app.Data.LteInfoData;
import com.dabinsystems.pact_app.Data.Nuclear.NuclearData;
import com.dabinsystems.pact_app.Data.SystemData;
import com.dabinsystems.pact_app.Dialog.SystemVersionDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

import static com.dabinsystems.pact_app.Handler.DataHandler.getInstance;


public class SystemView extends LayoutBase {

    private LinearLayout linVer, linFwUpdate, linFile, linRestart, linClockSource, linThreshold, linConnection, linRf;
    //private ArrayList<View> mVerList, mFwUpdateList, mFileList, mRestartList, mClockSourceList;
    private AutofitTextView tvSatatus, tvRf, tvConnection;
    private DynamicView mDynamicView;
    private FileView mFileView;

    private LinearLayout linHoldoverOpt;
    private AutofitTextView tvHoldoverOpt;

    public SystemView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {

                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.system_name));

                binding.linRightMenu.removeAllViews();

                binding.linRightMenu.addView(linVer);
                binding.linRightMenu.addView(linFwUpdate);

                //if pact is disabled
//                binding.linRightMenu.addView(linFile);
//                binding.linRightMenu.addView(linRestart);
                binding.linRightMenu.addView(linClockSource);

                //@@ [22.01.04] Holdover Option
//                binding.linRightMenu.addView(linHoldoverOpt);
                //@@

                //@@ [22.01.27] 원전 모니터링 Threshold
                binding.linRightMenu.addView(linConnection);
                binding.linRightMenu.addView(linRf);
                binding.linRightMenu.addView(linThreshold);

                binding.linCableList.setVisibility(View.GONE);
                binding.linCalibration.setVisibility(View.GONE);

            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity.getApplicationContext());
        mFileView = new FileView(mActivity, binding);

        ArrayList<View> mVerList = mDynamicView.addRightMenuButton("Version", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        ArrayList<View> mFwUpdateList = mDynamicView.addRightMenuButton("FW Update", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        ArrayList<View> mFileList = mDynamicView.addRightMenuButton("File", Gravity.RIGHT | Gravity.TOP);
        ArrayList<View> mClockSourceList = mDynamicView.addRightMenuButton("Clock Source", "GPS");
        ArrayList<View> mRestartList = mDynamicView.addRightMenuButton("FW Restart", Gravity.RIGHT | Gravity.TOP);

        linVer = (LinearLayout) mVerList.get(0);
        linFwUpdate = (LinearLayout) mFwUpdateList.get(0);
        linFile = (LinearLayout) mFileList.get(0);
        linClockSource = (LinearLayout) mClockSourceList.get(0);
        tvSatatus = (AutofitTextView) mClockSourceList.get(2);
//        tvInternal = (AutofitTextView) mClockSourceList.get(3);
        linRestart = (LinearLayout) mRestartList.get(0);

        //@@ [22.01.04] Holdover Option
        ArrayList<View> mHoldoverOptList = mDynamicView.addRightMenuButton("Holdover Opt", "GPS");
        linHoldoverOpt = (LinearLayout) mHoldoverOptList.get(0);
        tvHoldoverOpt = (AutofitTextView) mHoldoverOptList.get(2);
        //@@

        //@@ [22.01.25] 원전모니터링 기능
        ArrayList<View> mThresholdList = mDynamicView.addRightMenuButton("Threshold", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linThreshold = (LinearLayout)  mThresholdList.get(0);

        ArrayList<View> mConnectionList = mDynamicView.addRightMenuButton("Connection", "Local");
        linConnection = (LinearLayout) mConnectionList.get(0);
        tvConnection = (AutofitTextView) mConnectionList.get(2);

        ArrayList<View> mRfList = mDynamicView.addRightMenuButton("RF", "1");
        linRf = (LinearLayout) mRfList.get(0);
        tvRf = (AutofitTextView) mRfList.get(2);
        //@@

        setUpEvents();
    }

    @Override
    public void update() {
        super.update();

        mActivity.runOnUiThread(() -> {
            SystemData.CLOCK_SOURCE source = DataHandler.getInstance().getSystemData().getSource();
            GpsHoldoverData.OPTION option = DataHandler.getInstance().getGpsHoldoverData().getOption();
            NuclearData nData = DataHandler.getInstance().getNuclearData();


            tvSatatus.setText(source.getName());
            //@@ [22.01.04] Gps Holdover Option Button
//            tvHoldoverOpt.setText(option.getString());

            //@@ [22.01.27] 원자력 모니터링 관련
            tvConnection.setText(nData.getConnection().getName());
            tvRf.setText((nData.getSelectedRf() + 1) + "");
            //@@

        });
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linVer.setOnClickListener(v -> FunctionHandler.getInstance().getDataConnector().sendCommand(FunctionHandler.getInstance().getFwFunc().getVerCheckCmd()));

            linFwUpdate.setOnClickListener(v -> FunctionHandler.getInstance().getDataConnector().sendCommand(FunctionHandler.getInstance().getFwFunc().getVerCheckCmd()));

            linFile.setOnClickListener(v -> ViewHandler.getInstance().getFileView().addMenu());

            linClockSource.setOnClickListener(v -> {
                //SystemData systemData = DataHandler.getInstance().getSystemData();
                ViewHandler.getInstance().getSelectClockSourceView().addMenu();
                //update();
            });

            //@@ [22.01.04] Holdover Option
            linHoldoverOpt.setOnClickListener(v -> {

                LteInfoData lteInfoData = DataHandler.getInstance().getLteInfoData();

                ViewHandler.getInstance().getHoldoverOptView().addMenu();
            });
            //@@

            //@@ [22.01.25] Threshold
            linThreshold.setOnClickListener(v -> {

                ViewHandler.getInstance().getLimitView().addMenu();
            });
            //@@

            //@@ [22.01.27] 원전 모니터링 관련 기능
            linConnection.setOnClickListener(v -> {
                /*
                * Local or Remote
                *
                * */

                ViewHandler.getInstance().getSelectConnectionView().addMenu();
            });

            linRf.setOnClickListener(v -> {
                /*
                *
                * 1~4
                *
                * */
                ViewHandler.getInstance().getSelectRfView().addMenu();
            });

            //@@

            linRestart.setOnClickListener(v -> {
                FunctionHandler.getInstance().getDataConnector().sendCommand(getInstance().getRestartConfig());
            });

            binding.tvBack.setOnClickListener(v -> addMenu());

        });
    }

    public FileView getFileEvent() {
        return mFileView;
    }
}

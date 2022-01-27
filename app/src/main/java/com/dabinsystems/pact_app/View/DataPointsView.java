package com.dabinsystems.pact_app.View;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Data.VSWR.SweepData;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class DataPointsView extends LayoutBase {

    private LinearLayout linDataPoints129, linDataPoints257, linDataPoints513, linDataPoints1025, linDataPoints2049;
    private ArrayList<View> mDataPointsList129, mDataPointsList257, mDataPointsList513, mDataPointsList1025, mDataPointsList2049;
    private AutofitTextView tvDataPoints129, tvDataPoints257, tvDataPoints513, tvDataPoints1025, tvDataPoints2049;
    private DynamicView mDynamicView;

    public DataPointsView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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
                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.sweep_name));
                binding.linRightMenu.addView(linDataPoints129);
                binding.linRightMenu.addView(linDataPoints257);
                binding.linRightMenu.addView(linDataPoints513);
                binding.linRightMenu.addView(linDataPoints1025);
                binding.linRightMenu.addView(linDataPoints2049);

                binding.linCableList.setVisibility(View.GONE);
                binding.linCalibration.setVisibility(View.GONE);

            });


        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mDataPointsList129 = mDynamicView.addRightMenuButton("129");
        linDataPoints129 = (LinearLayout) mDataPointsList129.get(0);
        tvDataPoints129 = (AutofitTextView) mDataPointsList129.get(1);

        mDataPointsList257 = mDynamicView.addRightMenuButton("257");
        linDataPoints257 = (LinearLayout) mDataPointsList257.get(0);
        tvDataPoints257 = (AutofitTextView) mDataPointsList257.get(1);

        mDataPointsList513 = mDynamicView.addRightMenuButton("513");
        linDataPoints513 = (LinearLayout) mDataPointsList513.get(0);
        tvDataPoints513 = (AutofitTextView) mDataPointsList513.get(1);

        mDataPointsList1025 = mDynamicView.addRightMenuButton("1025");
        linDataPoints1025 = (LinearLayout) mDataPointsList1025.get(0);
        tvDataPoints1025 = (AutofitTextView) mDataPointsList1025.get(1);

        mDataPointsList2049 = mDynamicView.addRightMenuButton("2049");
        linDataPoints2049 = (LinearLayout) mDataPointsList2049.get(0);
        tvDataPoints2049 = (AutofitTextView) mDataPointsList2049.get(1);

        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {


            linDataPoints129.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P129);
                    FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());
                    ViewHandler.getInstance().getSweepView().addMenu();

                }
            });

            linDataPoints257.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
//
//                    if (mode == MeasureMode.MEASURE_MODE.VSWR
//                            || mode == MeasureMode.MEASURE_MODE.CL) {
//
//                        Toast.makeText(mActivity, "Disable menu", Toast.LENGTH_SHORT).show();
//                        return;
//
//                    }

                    VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P257);
                    FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());
                    ViewHandler.getInstance().getSweepView().addMenu();

                }
            });

            linDataPoints513.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
//
//                    if (mode == MeasureMode.MEASURE_MODE.VSWR
//                            || mode == MeasureMode.MEASURE_MODE.CL) {
//
//                        Toast.makeText(mActivity, "Disable menu", Toast.LENGTH_SHORT).show();
//                        return;
//
//                    }

                    VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P513);
                    FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());
                    ViewHandler.getInstance().getSweepView().addMenu();

                }
            });

            linDataPoints1025.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
//
//                    if (mode == MeasureMode.MEASURE_MODE.VSWR
//                            || mode == MeasureMode.MEASURE_MODE.CL) {
//
//                        Toast.makeText(mActivity, "Disable menu", Toast.LENGTH_SHORT).show();
//                        return;
//
//                    }

                    VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P1025);
                    FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());
                    ViewHandler.getInstance().getSweepView().addMenu();
                }
            });

            linDataPoints2049.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
//
//                    if (mode == MeasureMode.MEASURE_MODE.VSWR
//                            || mode == MeasureMode.MEASURE_MODE.CL) {
//
//                        Toast.makeText(mActivity, "Disable menu", Toast.LENGTH_SHORT).show();
//                        return;
//
//                    }

                    VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P2049);

                    FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());
                    ViewHandler.getInstance().getSweepView().addMenu();

                }
            });

            binding.tvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ViewHandler.getInstance().getSweepView().addMenu();
                }
            });


        });

    }

    @Override
    public void update() {
        super.update();


    }

}

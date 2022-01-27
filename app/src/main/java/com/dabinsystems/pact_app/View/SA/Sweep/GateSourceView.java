package com.dabinsystems.pact_app.View.SA.Sweep;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.SaGateData;
import com.dabinsystems.pact_app.Function.Chart.GateLineChartFunc;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class GateSourceView extends LayoutBase {

    private DynamicView dynamicView;

    private LinearLayout linInternal, linGps, linSsb, linExt;
    private AutofitTextView tvInternal, tvGps, tvSsb, tvExt;
    private ArrayList<View> mInternalList, mGpsList, mSsbList, mExtList;

    public GateSourceView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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

                binding.linRightMenu.addView(linInternal);
                binding.linRightMenu.addView(linGps);
                binding.linRightMenu.addView(linSsb);
                binding.linRightMenu.addView(linExt);

                binding.tvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ViewHandler.getInstance().getGateView().addMenu();

                    }
                });

            });


        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (dynamicView != null) return;

        dynamicView = new DynamicView(mActivity);

        mInternalList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.internal));
        mGpsList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.gps));
        mSsbList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ssb));
        mExtList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ext_1pps));

        linInternal = (LinearLayout) mInternalList.get(0);
        tvInternal = (AutofitTextView) mInternalList.get(1);

        linGps = (LinearLayout) mGpsList.get(0);
        tvGps = (AutofitTextView) mGpsList.get(1);

        linSsb = (LinearLayout) mSsbList.get(0);
        tvSsb = (AutofitTextView) mSsbList.get(1);

        linExt = (LinearLayout) mExtList.get(0);
        tvExt = (AutofitTextView) mExtList.get(1);

        setUpEvents();

    }

    @Override
    public void update() {
        super.update();

        switch (SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData().getGateSource()) {

            case INTERNAL:

                SaDataHandler
                        .getInstance()
                        .getConfigData()
                        .getSweepTimeData()
                        .getGateData()
                        .setGateSource(SaGateData.GATE_SOURCE.INTERNAL);

                break;

            case GPS:

                SaDataHandler
                        .getInstance()
                        .getConfigData()
                        .getSweepTimeData()
                        .getGateData()
                        .setGateSource(SaGateData.GATE_SOURCE.GPS);

                break;

            case SSB:

                SaDataHandler
                        .getInstance()
                        .getConfigData()
                        .getSweepTimeData()
                        .getGateData()
                        .setGateSource(SaGateData.GATE_SOURCE.SSB);

                break;

            case EXT1PPS:

                SaDataHandler
                        .getInstance()
                        .getConfigData()
                        .getSweepTimeData()
                        .getGateData()
                        .setGateSource(SaGateData.GATE_SOURCE.EXT1PPS);

                break;



        }

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linInternal.setOnClickListener(v -> {

                selectMode(SaGateData.GATE_SOURCE.INTERNAL);


            });

            linGps.setOnClickListener(v -> {

                selectMode(SaGateData.GATE_SOURCE.GPS);


            });

            linSsb.setOnClickListener(v -> {

                selectMode(SaGateData.GATE_SOURCE.SSB);


            });

            linExt.setOnClickListener(v->{

                selectMode(SaGateData.GATE_SOURCE.EXT1PPS);


            });


        });

    }

    public void selectMode(SaGateData.GATE_SOURCE mode) {

        SaDataHandler.getInstance()
                .getConfigData()
                .getSweepTimeData()
                .getGateData()
                .setGateSource(mode);

        FunctionHandler.getInstance().getDataConnector().sendCommand(
                SaDataHandler.getInstance().getConfigData().getSaParameter()
        );

        ViewHandler.getInstance().getGateView().addMenu();

        FunctionHandler.getInstance().getGateLineChart().update();
    }

}

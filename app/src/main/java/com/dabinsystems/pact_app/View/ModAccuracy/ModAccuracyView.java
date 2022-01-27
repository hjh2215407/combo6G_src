package com.dabinsystems.pact_app.View.ModAccuracy;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class ModAccuracyView extends LayoutBase {

    private LinearLayout linLteFdd, lin5GNR, linWcdma;
    private AutofitTextView tvLteFdd, tv5GNR, tvWcdma;
    private ArrayList<View> mLteFddList, m5GNRList, mWcdmaList;
    private DynamicView mDynamicView;

    public ModAccuracyView(MainActivity activity, ActivityMainBinding binding) {
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
                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.mod_accuracy));
                binding.linRightMenu.addView(lin5GNR);
                binding.linRightMenu.addView(linLteFdd);
//                binding.linRightMenu.addView(linWcdma); //hidden

                binding.tvBack.setOnClickListener(v -> {

                });
            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        m5GNRList = mDynamicView.addRightMenuButton("5G NR", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        lin5GNR = (LinearLayout) m5GNRList.get(0);
        tv5GNR = (AutofitTextView) m5GNRList.get(1);
        tv5GNR.setSingleLine(false);

        mLteFddList = mDynamicView.addRightMenuButton("Spectrum\nAnalyzer", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linLteFdd = (LinearLayout) mLteFddList.get(0);
        tvLteFdd = (AutofitTextView) mLteFddList.get(1);
        tvLteFdd.setSingleLine(false);

        mWcdmaList = mDynamicView.addRightMenuButton("WCDMA", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linWcdma = (LinearLayout) mWcdmaList.get(0);
        tvWcdma = (AutofitTextView) mWcdmaList.get(1);
        tvWcdma.setSingleLine(false);

        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linLteFdd.setOnClickListener(v -> {

                //todo : set SA
                FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.MOD_ACCURACY);
                ViewHandler.getInstance().getSaMeas().addMenu();
                ViewHandler.getInstance().getSAView().update();

            });

            lin5GNR.setOnClickListener(v -> {

                //todo : set 5G NR
                FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.MOD_ACCURACY);
                ViewHandler.getInstance().getSAView().addMenu();
                ViewHandler.getInstance().getNrMeasSetupView().addMenu();
//                update();


//                FunctionHandler.getInstance().getDataConnector().sendCommand(
//                        SaDataHandler.getInstance().getNrChannelPowerConfigData().getSaParameter()
//                );

//                FunctionHandler.getInstance().getDataConnector().sendCommand(
//                        DataHandler.getInstance().getDemodulationData().getDemodulationCmd()
//                );

                ViewHandler.getInstance().getSaMeas().addMenu();
                ViewHandler.getInstance().getSAView().update();

            });

            linWcdma.setOnClickListener(v->{

            });

        });


    }

}

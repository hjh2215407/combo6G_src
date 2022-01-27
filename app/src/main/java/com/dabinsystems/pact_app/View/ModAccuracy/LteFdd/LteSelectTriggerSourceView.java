package com.dabinsystems.pact_app.View.ModAccuracy.LteFdd;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.TriggerSource;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class LteSelectTriggerSourceView extends LayoutBase {

    private DynamicView dynamicView;

    private LinearLayout linInternal, linGps, linExt;

    public LteSelectTriggerSourceView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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
                binding.linRightMenu.addView(linExt);

                binding.tvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewHandler.getInstance().getLteFddMeasSetupView().addMenu();
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

        ArrayList<View> mInternalList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.internal));
        linInternal = (LinearLayout) mInternalList.get(0);

        ArrayList<View> mGpsList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.gps));
        linGps = (LinearLayout) mGpsList.get(0);

        ArrayList<View> mExtList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ext_1pps));
        linExt = (LinearLayout) mExtList.get(0);

        setUpEvents();
    }

    @Override
    public void update() {
        super.update();

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (type == MeasureType.MEASURE_TYPE.NR_5G) {

            TriggerSource triggerSource = DataHandler.getInstance().getNrData().getTriggerSource();

            switch (triggerSource.getTriggerSource()) {

                case INTERNAL:

                    triggerSource.setTriggerSource(TriggerSource.TRIGGER_SOURCE.INTERNAL);
                    break;

                case GPS:

                    triggerSource.setTriggerSource(TriggerSource.TRIGGER_SOURCE.GPS);
                    break;

                case EXT1PPS:

                    triggerSource.setTriggerSource(TriggerSource.TRIGGER_SOURCE.EXT1PPS);
                    break;


            }

        } else if (type == MeasureType.MEASURE_TYPE.LTE_FDD) {

            TriggerSource triggerSource = DataHandler.getInstance().getLteFddData().getTriggerSource();

            switch (triggerSource.getTriggerSource()) {

                case INTERNAL:

                    triggerSource.setTriggerSource(TriggerSource.TRIGGER_SOURCE.INTERNAL);
                    break;

                case GPS:

                    triggerSource.setTriggerSource(TriggerSource.TRIGGER_SOURCE.GPS);
                    break;

                case EXT1PPS:

                    triggerSource.setTriggerSource(TriggerSource.TRIGGER_SOURCE.EXT1PPS);
                    break;


            }

        }

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linInternal.setOnClickListener(v -> {
                selectMode(TriggerSource.TRIGGER_SOURCE.INTERNAL);

            });

            linGps.setOnClickListener(v -> {
                selectMode(TriggerSource.TRIGGER_SOURCE.GPS);

            });

            linExt.setOnClickListener(v -> {
                selectMode(TriggerSource.TRIGGER_SOURCE.EXT1PPS);

            });


        });

    }

    public void selectMode(TriggerSource.TRIGGER_SOURCE mode) {

        TriggerSource triggerData = null;

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (type == MeasureType.MEASURE_TYPE.NR_5G) {
            triggerData = DataHandler.getInstance().getNrData().getTriggerSource();
            triggerData.setTriggerSource(mode);
            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    DataHandler.getInstance().getNrData().getNrCmd()
            );
            ViewHandler.getInstance().getNrMeasSetupView().addMenu();
        } else {
            triggerData = DataHandler.getInstance().getLteFddData().getTriggerSource();
            triggerData.setTriggerSource(mode);
            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    DataHandler.getInstance().getLteFddData().getLteFddCmd()
            );
            ViewHandler.getInstance().getLteFddMeasSetupView().addMenu();
        }

    }

}

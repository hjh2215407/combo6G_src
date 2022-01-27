package com.dabinsystems.pact_app.View;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MeasurementView extends LayoutBase {

    private LinearLayout linVswr, linReturnLoss;
    //private AutofitTextView tvVswr, tvReturnLoss;
    //private ArrayList<View> mVswrList, mReturnLossList;
    private DynamicView mDynamicView;

    public MeasurementView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                if (mode == MeasureMode.MEASURE_MODE.VSWR)
                    ViewHandler.getInstance().getVswrView().selectMeasurement();
                else
                    ViewHandler.getInstance().getDtfView().selectMeasurement();

                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.measurement_name));
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linVswr);
                binding.linRightMenu.addView(linReturnLoss);
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

        ArrayList<View> mVswrList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.vswr_name));

        ArrayList<View> mReturnLossList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.return_loss));

        linVswr = (LinearLayout) mVswrList.get(0);

        linReturnLoss = (LinearLayout) mReturnLossList.get(0);

        setUpEvents();
    }

    @Override
    public void setUpEvents() throws NullPointerException {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

            linVswr.setOnClickListener(v -> {
                if (mode == MeasureMode.MEASURE_MODE.VSWR) {
                    FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.VSWR);
                } else if (mode == MeasureMode.MEASURE_MODE.DTF) {
                    FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.DTF_VSWR);
                }

                update();
                ViewHandler.getInstance().getContent().smallIconUpdate();
//                FunctionHandler.getInstance().getDataConnector().sendCommand(
//                        DataHandler.getInstance().getConfigCmd()
//                );
            });

            linReturnLoss.setOnClickListener(v -> {
                if (mode == MeasureMode.MEASURE_MODE.VSWR) {
                    FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.RL);
                } else if (mode == MeasureMode.MEASURE_MODE.DTF) {
                    FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.DTF_RL);
                }

                update();
                ViewHandler.getInstance().getContent().smallIconUpdate();

//                FunctionHandler.getInstance().getDataConnector().sendCommand(
//                        DataHandler.getInstance().getConfigCmd()
//                );
            });

            binding.tvBack.setOnClickListener(v -> addMenu());

        });
    }

    @Override
    public void update() {
        super.update();

        mActivity.runOnUiThread(() -> {
            try {
                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {
                    linVswr.setSelected(true);
                    linReturnLoss.setSelected(false);
                } else {
                    linReturnLoss.setSelected(true);
                    linVswr.setSelected(false);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
    }
}

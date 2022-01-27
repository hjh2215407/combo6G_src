package com.dabinsystems.pact_app.View.SA;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class SaPeakView extends LayoutBase {

    private DynamicView mDynamicView;

    private LinearLayout linContinuous, linToPeak, linToMin;

    public SaPeakView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        ArrayList<View> mContinuousList = mDynamicView.addRightMenuButton("Continuous");
        ArrayList<View> mToPeakList = mDynamicView.addRightMenuButton("Marker To Peak");
        ArrayList<View> mToMinList = mDynamicView.addRightMenuButton("Marker To Min");

        linContinuous = (LinearLayout) mContinuousList.get(0);

        linToPeak = (LinearLayout) mToPeakList.get(0);

        linToMin = (LinearLayout) mToMinList.get(0);

        setUpEvents();
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            try {
                mActivity.runOnUiThread(() -> {
                    ViewHandler.getInstance().getSAView().selectPeak();

                    binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.peak_name));

                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(linContinuous);

                    if (FunctionHandler.getInstance().getMainLineChart().getMarker() != null
                            && FunctionHandler.getInstance().getMainLineChart().getMarker().isContinuous()) {
                        binding.linRightMenu.addView(linToPeak);
                        binding.linRightMenu.addView(linToMin);
                    }
                });

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void update() {
        mActivity.runOnUiThread(() -> {
            if (FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex() == -1) {
                linContinuous.setSelected(false);
            } else {
                linContinuous.setSelected(FunctionHandler.getInstance().getMainLineChart().getMarker().isContinuous());
            }
        });
    }

    @Override
    public void setUpEvents() {

        mActivity.runOnUiThread(() -> {

            linContinuous.setOnClickListener(v -> {

                if (binding.lineChartLayout.mainLineChart.getData().getEntryCount() == 0) return;

                if (FunctionHandler.getInstance().getMainLineChart().getMarker() == null) {
                    FunctionHandler.getInstance().getMainLineChart().setMarker(0);
                    binding.tvMarker1.setSelected(true);
                }

                boolean isContinuous = !FunctionHandler.getInstance().getMainLineChart().getMarker().isContinuous();

                linContinuous.setSelected(isContinuous);

                FunctionHandler.getInstance().getMainLineChart().getMarker().setContinuous(isContinuous);
                FunctionHandler.getInstance().getMainLineChart().setMarkerToMin(false);
                FunctionHandler.getInstance().getMainLineChart().setMarkerToPeak(false);

                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linContinuous);

                if (isContinuous) {
                    binding.linRightMenu.addView(linToPeak);
                    binding.linRightMenu.addView(linToMin);

                    linToPeak.performClick();
                }
            });

            linToPeak.setOnClickListener(v -> {
                Boolean isPeak = FunctionHandler.getInstance().getMainLineChart().isMarkerToPeak();
                if (!isPeak) {
                    linToPeak.setSelected(true);
                    linToMin.setSelected(false);

                    FunctionHandler.getInstance().getMainLineChart().setMarkerToPeak(true);

                    // TODO 데이터 수신 새로 수신 시 갱신 되므로
                    //FunctionHandler.getInstance().getMainLineChart().moveToPeak();
                    //FunctionHandler.getInstance().getMainLineChart().invalidate();
                }
            });

            linToMin.setOnClickListener(v -> {
                Boolean isMin = FunctionHandler.getInstance().getMainLineChart().isMarkerToMin();

                if (!isMin) {
                    linToMin.setSelected(true);
                    linToPeak.setSelected(false);

                    FunctionHandler.getInstance().getMainLineChart().setMarkerToMin(true);

                    // TODO 데이터 수신 새로 수신 시 갱신 되므로
//                    FunctionHandler.getInstance().getMainLineChart().moveToMin();
//                    FunctionHandler.getInstance().getMainLineChart().invalidate();
                }
            });

            binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getSAView().selectMarker());
        });
    }
}

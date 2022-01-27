package com.dabinsystems.pact_app.View.Marker;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;

public class MarkerSearchView extends LayoutBase {

    private DynamicView mDynamicView;

    private LinearLayout linContinuous, linToPeak, linToMin;

    public MarkerSearchView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        ArrayList<View> mContinuousList = mDynamicView.addRightMenuButton("Continuous");
        linContinuous = (LinearLayout) mContinuousList.get(0);

        ArrayList<View> mToPeakList = mDynamicView.addRightMenuButton("Marker To Peak");
        linToPeak = (LinearLayout) mToPeakList.get(0);

        ArrayList<View> mToMinList = mDynamicView.addRightMenuButton("Marker To Min");
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
                    binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.marker_name));

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
        super.update();

        new Thread(() -> {
            try {
                mActivity.runOnUiThread(() -> {

                    Highlight marker = FunctionHandler.getInstance().getMainLineChart().getMarker();
                    if (marker == null) {
                        linContinuous.setSelected(false);
                    }

//            InitActivity.logMsg("MarkerSearchView", "selected marker index : " + FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex());
//            InitActivity.logMsg("MarkerSearchView", "isContinuous : " + FunctionHandler.getInstance().getMainLineChart().getMarker().isContinuous());

                    if (FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex() == -1) {
                        linContinuous.setSelected(false);
                    } else {
                        linContinuous.setSelected(FunctionHandler.getInstance().getMainLineChart().getMarker().isContinuous());
                    }

                });

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }).start();

    }

    @Override
    public void setUpEvents() {

        mActivity.runOnUiThread(() -> {

            linContinuous.setOnClickListener(v -> new Handler(Looper.getMainLooper()).post(() -> {
                try {
                    if (FunctionHandler.getInstance().getMainLineChart().getMarker() == null)
                        FunctionHandler.getInstance().getMainLineChart().setMarker(0);

                    boolean isContinuous = !FunctionHandler.getInstance().getMainLineChart().getMarker().isContinuous();
                    FunctionHandler.getInstance().getMainLineChart().getMarker().setContinuous(isContinuous);

                    linContinuous.setSelected(isContinuous);

                    if (isContinuous) {
                        binding.linRightMenu.addView(linToPeak);
                        binding.linRightMenu.addView(linToMin);

                        FunctionHandler.getInstance().getMainLineChart().setMarkerToMin(false);
                        FunctionHandler.getInstance().getMainLineChart().setMarkerToPeak(false);

                        linToPeak.performClick();
                    } else {
                        binding.linRightMenu.removeView(linToPeak);
                        binding.linRightMenu.removeView(linToMin);

                        FunctionHandler.getInstance().getMainLineChart().setMarkerToMin(false);
                        FunctionHandler.getInstance().getMainLineChart().setMarkerToPeak(false);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity, "No Data", Toast.LENGTH_SHORT).show();
                }
            }));

            linToPeak.setOnClickListener(v -> new Handler(Looper.getMainLooper()).post(() -> {
                Boolean isPeak = FunctionHandler.getInstance().getMainLineChart().isMarkerToPeak();
                if (!isPeak) {
                    linToPeak.setSelected(true);
                    linToMin.setSelected(false);

                    FunctionHandler.getInstance().getMainLineChart().setMarkerToPeak(true);

                    // TODO 데이터 수신 새로 수신 시 갱신 되므로
                    //FunctionHandler.getInstance().getMainLineChart().moveToPeak();
                    //FunctionHandler.getInstance().getMainLineChart().invalidate();
                }
            }));

            linToMin.setOnClickListener(v -> new Handler(Looper.getMainLooper()).post(() -> {
                Boolean isMin = FunctionHandler.getInstance().getMainLineChart().isMarkerToMin();
                if (!isMin) {
                    linToMin.setSelected(true);
                    linToPeak.setSelected(false);

                    FunctionHandler.getInstance().getMainLineChart().setMarkerToMin(true);

                    // TODO 데이터 수신 새로 수신 시 갱신 되므로
//                    FunctionHandler.getInstance().getMainLineChart().moveToMin();
//                    FunctionHandler.getInstance().getMainLineChart().invalidate();
                }
            }));
        });
    }
}

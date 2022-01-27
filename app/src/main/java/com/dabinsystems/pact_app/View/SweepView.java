package com.dabinsystems.pact_app.View;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SweepView extends LayoutBase {

    private LinearLayout linDataPoints, linRunAndHold, linSweepType;
    private AutofitTextView tvDataPointValue, tvRunHold, tvSweepType, tvRunOption, tvHoldOption, tvSweepCont, tvSweepSingle;
    private ArrayList<View> mDataPointsList, mRunAndHoldList, mSweepTypeList;
    private DynamicView mDynamicView;

    public SweepView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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
                binding.linRightMenu.addView(linDataPoints);
                binding.linRightMenu.addView(linRunAndHold);
                binding.linRightMenu.addView(linSweepType);

                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.sweep_name));

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

        mDataPointsList = mDynamicView.addRightMenuButton("Data Points", VswrDataHandler.getInstance().getConfigData().getSweepData().getDataPoint().getDataPoint() + "");
        mRunAndHoldList = mDynamicView.addRightMenuButton("Run/Hold", "Run", "Hold");
        mSweepTypeList = mDynamicView.addRightMenuButton("Sweep Type", "Cont", "Single");

        linDataPoints = (LinearLayout) mDataPointsList.get(0);
        tvDataPointValue = (AutofitTextView) mDataPointsList.get(2);

        linRunAndHold = (LinearLayout) mRunAndHoldList.get(0);
        tvRunHold = (AutofitTextView) mRunAndHoldList.get(1);
        tvRunOption = (AutofitTextView) mRunAndHoldList.get(2);
        tvHoldOption = (AutofitTextView) mRunAndHoldList.get(3);

        linSweepType = (LinearLayout) mSweepTypeList.get(0);
        tvSweepType = (AutofitTextView) mSweepTypeList.get(1);
        tvSweepCont = (AutofitTextView) mSweepTypeList.get(2);
        tvSweepSingle = (AutofitTextView) mSweepTypeList.get(3);

        setUpEvents();
    }


    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linDataPoints.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ViewHandler.getInstance().getDataPointsView().addMenu();

                }
            });

            linRunAndHold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    VswrDataHandler.getInstance().getConfigData().getSweepData().setRun(
                            !VswrDataHandler.getInstance().getConfigData().getSweepData().isRun()
                    );

                    update();

                }
            });

            linSweepType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    VswrDataHandler.getInstance().getConfigData().getSweepData().setContinuous(
                            !VswrDataHandler.getInstance().getConfigData().getSweepData().isContinuous()
                    );

                    update();

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

    @SuppressLint("SetTextI18n")
    @Override
    public void update() {
        super.update();

        mActivity.runOnUiThread(() -> {

            tvDataPointValue.setText(
                    VswrDataHandler.getInstance().getConfigData().getSweepData().getDataPoint().getDataPoint() + ""
            );

            if (VswrDataHandler.getInstance().getConfigData().getSweepData().isRun()) {

                selectOptionView(tvRunOption, tvHoldOption);

            } else {

                selectOptionView(tvHoldOption, tvRunOption);


            }

            if (VswrDataHandler.getInstance().getConfigData().getSweepData().isContinuous()) {

                selectOptionView(tvSweepCont, tvSweepSingle);


            } else {

                selectOptionView(tvSweepSingle, tvSweepCont);

            }


        });

    }

}

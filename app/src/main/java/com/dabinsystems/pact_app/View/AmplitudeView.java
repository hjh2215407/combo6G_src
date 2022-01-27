package com.dabinsystems.pact_app.View;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.VSWR.VswrAmplitudeData;
import com.dabinsystems.pact_app.Dialog.VSWR.VswrSetMaxYKeypad;
import com.dabinsystems.pact_app.Dialog.VSWR.VswrSetMinYKeypad;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import org.apache.commons.net.io.Util;

import java.text.Format;
import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class AmplitudeView extends LayoutBase {

    private LinearLayout linTop, linBottom, linAutoScale, linFullScale;
    private AutofitTextView tvTopValue, tvBottomValue;
    private ArrayList<View> mTopList, mBottomList, mAutoScaleList, mFullScaleList;
    private DynamicView mDynamicView;

    public AmplitudeView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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
                binding.linRightMenu.addView(linTop);
                binding.linRightMenu.addView(linBottom);
                binding.linRightMenu.addView(linAutoScale);
                binding.linRightMenu.addView(linFullScale);

                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.amplitude_name));
                binding.linCalibration.setVisibility(View.GONE);
                binding.linCableList.setVisibility(View.GONE);

            });


        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mTopList = mDynamicView.addRightMenuButton("Top", "");
        mBottomList = mDynamicView.addRightMenuButton("Bottom", "");
        mAutoScaleList = mDynamicView.addRightMenuButton("Auto scale");
        mFullScaleList = mDynamicView.addRightMenuButton("Full scale");

        linTop = (LinearLayout) mTopList.get(0);
        tvTopValue = (AutofitTextView) mTopList.get(2);

        linBottom = (LinearLayout) mBottomList.get(0);
        tvBottomValue = (AutofitTextView) mBottomList.get(2);

        linAutoScale = (LinearLayout) mAutoScaleList.get(0);
        linFullScale = (LinearLayout) mFullScaleList.get(0);

        setUpEvents();

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void update() {
        super.update();

        mActivity.runOnUiThread(() -> {

            MeasureType.MEASURE_TYPE type =
                    FunctionHandler.getInstance().getMeasureType().getType();

            String maxYRange = Utils.formatNumber(FunctionHandler.getInstance().getMainLineChart().getMaxYRange(), 2, false);
            String minYRange = Utils.formatNumber(FunctionHandler.getInstance().getMainLineChart().getMinYRange(), 2, false);

            if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

                tvTopValue.setText(maxYRange + "");
                tvBottomValue.setText(minYRange + "");

            } else {

                tvTopValue.setText(minYRange + " dB");
                tvBottomValue.setText(maxYRange + " dB");

            }

        });
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linTop.setOnClickListener(v -> {

                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

                    new VswrSetMaxYKeypad(mActivity, binding).show();

                } else if(type == MeasureType.MEASURE_TYPE.RL || type == MeasureType.MEASURE_TYPE.DTF_RL || type == MeasureType.MEASURE_TYPE.CABLE_LOSS) {

                    new VswrSetMinYKeypad(mActivity, binding).show();

                }

            });

            linBottom.setOnClickListener(v -> {

                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

                    new VswrSetMinYKeypad(mActivity, binding).show();

                } else if(type == MeasureType.MEASURE_TYPE.RL || type == MeasureType.MEASURE_TYPE.DTF_RL || type == MeasureType.MEASURE_TYPE.CABLE_LOSS) {

                    new VswrSetMaxYKeypad(mActivity, binding).show();

                }


            });

            linAutoScale.setOnClickListener(v -> {

                FunctionHandler.getInstance().getMainLineChart().autoScale();
                update();

            });

            linFullScale.setOnClickListener(v -> {

                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if (type == MeasureType.MEASURE_TYPE.VSWR) {

                    FunctionHandler.getInstance().getMainLineChart().setMinYRange(
                            VswrAmplitudeData.DEFALUT_VSWR_Y_MIN
                    );

                    VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMinForVswr(
                            VswrAmplitudeData.DEFALUT_VSWR_Y_MIN
                    );


                    FunctionHandler.getInstance().getMainLineChart().setMaxYRange(
                            VswrAmplitudeData.DEFALUT_VSWR_Y_MAX
                    );

                    VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMaxForVswr(
                            VswrAmplitudeData.DEFALUT_VSWR_Y_MAX
                    );

                } else if(type == MeasureType.MEASURE_TYPE.RL) {

                    FunctionHandler.getInstance().getMainLineChart().setMinYRange(
                            VswrAmplitudeData.DEFALUT_RL_Y_MIN
                    );

                    VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMinForRl(
                            VswrAmplitudeData.DEFALUT_RL_Y_MIN
                    );


                    FunctionHandler.getInstance().getMainLineChart().setMaxYRange(
                            VswrAmplitudeData.DEFALUT_RL_Y_MAX
                    );

                    VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMaxForRl(
                            VswrAmplitudeData.DEFALUT_RL_Y_MAX
                    );

                } else if (type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

                    FunctionHandler.getInstance().getMainLineChart().setMinYRange(
                            VswrAmplitudeData.DEFALUT_VSWR_Y_MIN
                    );

                    VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMinForVswr(
                            VswrAmplitudeData.DEFALUT_VSWR_Y_MIN
                    );


                    FunctionHandler.getInstance().getMainLineChart().setMaxYRange(
                            VswrAmplitudeData.DEFALUT_VSWR_Y_MAX
                    );

                    VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMaxForVswr(
                            VswrAmplitudeData.DEFALUT_VSWR_Y_MAX
                    );

                } else if (type == MeasureType.MEASURE_TYPE.DTF_RL || type == MeasureType.MEASURE_TYPE.CABLE_LOSS) {

                    FunctionHandler.getInstance().getMainLineChart().setMinYRange(
                            VswrAmplitudeData.DEFALUT_RL_Y_MIN
                    );

                    VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMinForRl(
                            VswrAmplitudeData.DEFALUT_RL_Y_MIN
                    );


                    FunctionHandler.getInstance().getMainLineChart().setMaxYRange(
                            VswrAmplitudeData.DEFALUT_RL_Y_MAX
                    );

                    VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMaxForRl(
                            VswrAmplitudeData.DEFALUT_RL_Y_MAX
                    );

                } else {

                    Toast.makeText(mActivity, mActivity.getResources().getString(R.string.wrong_type), Toast.LENGTH_SHORT).show();

                }

                FunctionHandler.getInstance().getMainLineChart().invalidate();
                update();

            });

            binding.tvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addMenu();

                }
            });

        });

    }

}

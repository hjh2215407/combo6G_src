package com.dabinsystems.pact_app.View;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.LimitLineKeypad;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class LimitView extends LayoutBase {

    private LinearLayout linLimitType, linLimitLine, linLimitAlarm, linPassFail, linPresetLimit;
    private AutofitTextView tvUpper, tvLower, tvLimitValue, tvPassFailOn, tvPassFailOff, tvAlarmOn, tvAlarmOff;
    private DynamicView mDynamicView;

    public LimitView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {
            initView();
            update();

            mActivity.runOnUiThread(() -> {
                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.limit_name));

                binding.linRightMenu.removeAllViews();

                binding.linRightMenu.addView(linLimitType);
                binding.linRightMenu.addView(linLimitLine);
                binding.linRightMenu.addView(linLimitAlarm);
                binding.linRightMenu.addView(linPassFail);
//        binding.linRightMenu.addView(linPresetLimit); //보류
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

        ArrayList<View> mLimitTypeList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.limit_type), "Upper", "Lower");
        ArrayList<View> mLimitLineList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.limit_line), "");
        ArrayList<View> mLimitAlarmList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.limit_alram), "ON", "OFF");
        ArrayList<View> mPassFailList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.limit_pass_fail), "ON", "OFF");
        ArrayList<View> mPresetLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.limit_preset));

        linLimitType = (LinearLayout) mLimitTypeList.get(0);
        tvUpper = (AutofitTextView) mLimitTypeList.get(2);
        tvLower = (AutofitTextView) mLimitTypeList.get(3);

        linLimitLine = (LinearLayout) mLimitLineList.get(0);
        tvLimitValue = (AutofitTextView) mLimitLineList.get(2);

        linLimitAlarm = (LinearLayout) mLimitAlarmList.get(0);
        tvAlarmOn = (AutofitTextView) mLimitAlarmList.get(2);
        tvAlarmOff = (AutofitTextView) mLimitAlarmList.get(3);

        linPassFail = (LinearLayout) mPassFailList.get(0);
        tvPassFailOn = (AutofitTextView) mPassFailList.get(2);
        tvPassFailOff = (AutofitTextView) mPassFailList.get(3);

        linPresetLimit = (LinearLayout) mPresetLimitList.get(0);

        setUpEvents();

//        setLimitValue(1.5f);
//        setEnabledLimitLine(true);
//        setLimitType(true);
//        setEnabledLimitAlarm(false);
//        setPassFailOn(false);

    }

    @Override
    public void update() {
        super.update();

        /*new Thread(() -> mActivity.runOnUiThread(() -> {
            if (FunctionHandler.getInstance().getMainLineChart().isUpper()) {
                selectOptionView(tvUpper, tvLower);
            } else {
                selectOptionView(tvLower, tvUpper);
            }

            if (FunctionHandler.getInstance().getMainLineChart().isAlarm()) {
                selectOptionView(tvAlarmOn, tvAlarmOff);
            } else {
                selectOptionView(tvAlarmOff, tvAlarmOn);
            }

            if (FunctionHandler.getInstance().getMainLineChart().isEnabledLimitMsg()) {
                selectOptionView(tvPassFailOn, tvPassFailOff);
            } else {
                selectOptionView(tvPassFailOff, tvPassFailOn);
            }
        })).start();*/

        mActivity.runOnUiThread(() -> {
            if (FunctionHandler.getInstance().getMainLineChart().isUpper()) {
                selectOptionView(tvUpper, tvLower);
            } else {
                selectOptionView(tvLower, tvUpper);
            }

            if (FunctionHandler.getInstance().getMainLineChart().isAlarm()) {
                selectOptionView(tvAlarmOn, tvAlarmOff);
            } else {
                selectOptionView(tvAlarmOff, tvAlarmOn);
            }

            if (FunctionHandler.getInstance().getMainLineChart().isEnabledLimitMsg()) {
                selectOptionView(tvPassFailOn, tvPassFailOff);
            } else {
                selectOptionView(tvPassFailOff, tvPassFailOn);
            }
        });

        limitValueUpdate();
    }

    public void limitValueUpdate() {
        new Thread(() -> {
            initView();

            mActivity.runOnUiThread(() -> {
                try {
                    String value = Utils.formatNumber(FunctionHandler.getInstance().getMainLineChart().getLimitValue(), 2, false);

                    MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
                    MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
//                    if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {
//                    } else
                    if (type == MeasureType.MEASURE_TYPE.RL || type == MeasureType.MEASURE_TYPE.DTF_RL || type == MeasureType.MEASURE_TYPE.CABLE_LOSS || mode == MeasureMode.MEASURE_MODE.SA) {
                        value += " dB";
                    }
//                    else if (type == MeasureType.MEASURE_TYPE.CABLE_LOSS) {
//                    }

                    tvLimitValue.setText(value);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        linLimitLine.setOnClickListener(v -> new LimitLineKeypad(mActivity, binding).show());

        linLimitType.setOnClickListener(v -> {
            FunctionHandler.getInstance().getMainLineChart().setLimitType(!FunctionHandler.getInstance().getMainLineChart().isUpper());
            update();
        });

        linLimitAlarm.setOnClickListener(v -> {
            FunctionHandler.getInstance().getMainLineChart().setEnabledLimitAlarm(!FunctionHandler.getInstance().getMainLineChart().isAlarm());
            update();
        });

        linPassFail.setOnClickListener(v -> {
            FunctionHandler.getInstance().getMainLineChart().setEnabledLimitMsg(!FunctionHandler.getInstance().getMainLineChart().isEnabledLimitMsg());
            update();
        });

//        linPresetLimit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        binding.tvBack.setOnClickListener(v -> addMenu());
    }
}

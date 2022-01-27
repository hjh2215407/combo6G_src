package com.dabinsystems.pact_app.Dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.VSWR.VswrFrequencyData;
import com.dabinsystems.pact_app.Function.CalibrationFunc;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.CalibrationDialogBinding;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class CalibrationCompleteDialog extends CustomBaseDialog {

    private CalibrationDialogBinding mCalibrationBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;


    public CalibrationCompleteDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    public CalibrationCompleteDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCalibrationBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.calibration_dialog, null, false);
        setContentView(mCalibrationBinding.getRoot());

        initValues();
        viewSetting();
        addEvents();

    }

    private void viewSetting() {

        mCalibrationBinding.tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mCalibrationBinding.tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));
        mCalibrationBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));

        mCalibrationBinding.tvCalTypeTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mCalibrationBinding.tvCalTypeTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mCalibrationBinding.tvCalTypeTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mCalibrationBinding.tvCalType.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mCalibrationBinding.tvCalType.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mCalibrationBinding.tvCalType.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mCalibrationBinding.tvFreqTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mCalibrationBinding.tvFreqTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mCalibrationBinding.tvFreqTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mCalibrationBinding.tvFreq.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mCalibrationBinding.tvFreq.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mCalibrationBinding.tvFreq.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mCalibrationBinding.tvExit.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mCalibrationBinding.tvExit.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mCalibrationBinding.tvExit.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

    }

    public void initValues() {


    }

    @SuppressLint("SetTextI18n")
    public void addEvents() {

        mActivity.runOnUiThread(() -> {

            CalibrationFunc cal = FunctionHandler.getInstance().getCalibrationFunc();

            if(cal.getCalType() == CalibrationFunc.CAL_TYPE.TYPE_STANDARD) {

                mCalibrationBinding.tvFreq.setText(VswrDataHandler.getInstance().getConfigData().getFrequencyData().getStartFreq() + "MHz" +
                        " ~ " + VswrDataHandler.getInstance().getConfigData().getFrequencyData().getStopFreq() + "MHz");

            } else if(cal.getCalType() == CalibrationFunc.CAL_TYPE.TYPE_FLEX) {

                mCalibrationBinding.tvFreq.setText(VswrFrequencyData.DEFALUT_MIN_FREQ + "MHz" +
                        " ~ " + VswrFrequencyData.DEFALUT_MAX_FREQ + "MHz");

            }

            mCalibrationBinding.tvCalType.setText(
                    FunctionHandler.getInstance().getCalibrationFunc().getCalType().getString()
            );

            mCalibrationBinding.tvExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            mCalibrationBinding.linParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        });

    }


}

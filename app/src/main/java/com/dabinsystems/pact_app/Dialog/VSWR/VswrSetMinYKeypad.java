package com.dabinsystems.pact_app.Dialog.VSWR;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.VSWR.VswrAmplitudeData;
import com.dabinsystems.pact_app.Dialog.CustomBaseDialog;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.DbKeypadBinding;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class VswrSetMinYKeypad extends CustomBaseDialog {

    private DbKeypadBinding mKeypadBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    private Boolean isMinus = false;
    private Boolean isDot = false;

    public VswrSetMinYKeypad(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mKeypadBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.db_keypad, null, false);
        setContentView(mKeypadBinding.getRoot());

        if (FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.DTF_RL
                || FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.RL
                || FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.CABLE_LOSS)
            mKeypadBinding.tvdB.setVisibility(View.VISIBLE);
        else mKeypadBinding.tvdB.setVisibility(View.INVISIBLE);

        viewSetting();
        addEvents();

    }

    private void viewSetting() {

        mKeypadBinding.tvDot.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvDot.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvDot.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvdB.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvdB.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvdB.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvView.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvView.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));
        mKeypadBinding.tvView.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));

        mKeypadBinding.tvEnter.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvEnter.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvEnter.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvPlusMinus.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvPlusMinus.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvPlusMinus.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvValue0.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvValue0.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvValue0.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvValue1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvValue1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvValue1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvValue2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvValue2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvValue2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvValue3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvValue3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvValue3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvValue4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvValue4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvValue4.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvValue5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvValue5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvValue5.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvValue6.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvValue6.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvValue6.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvValue7.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvValue7.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvValue7.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvValue8.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvValue8.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvValue8.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvValue9.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvValue9.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvValue9.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        dismiss();
    }

    private void addEvents() {

        mKeypadBinding.linKeypadParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitActivity.logMsg("linKeypadParent", "linKeypad");
            }
        });

        mKeypadBinding.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mKeypadBinding.tvValue0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "0");
            }
        });

        mKeypadBinding.tvValue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "1");
            }
        });

        mKeypadBinding.tvValue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "2");
            }
        });

        mKeypadBinding.tvValue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "3");
            }
        });

        mKeypadBinding.tvValue4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "4");
            }
        });

        mKeypadBinding.tvValue5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "5");
            }
        });

        mKeypadBinding.tvValue6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "6");
            }
        });

        mKeypadBinding.tvValue7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "7");
            }
        });

        mKeypadBinding.tvValue8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "8");
            }
        });

        mKeypadBinding.tvValue9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "9");
            }
        });

        mKeypadBinding.tvPlusMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mKeypadBinding.tvView.getText().length() != 0 &&
                        mKeypadBinding.tvView.getText().toString().charAt(0) == '-' &&
                        isMinus) {

                    mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString().replace("-", ""));
                    isMinus = false;
                } else {
                    mKeypadBinding.tvView.setText("-" + mKeypadBinding.tvView.getText().toString());
                    isMinus = true;
                }
            }
        });

        mKeypadBinding.tvdB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickEnter();

            }
        });

        mKeypadBinding.tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickEnter();

            }
        });

        mKeypadBinding.tvDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDot) return;
                else {
                    mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + ".");
                    isDot = true;
                }
            }
        });

        mKeypadBinding.ivBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mKeypadBinding.tvView.getText().length() - 1 >= 0) {

                    if (mKeypadBinding.tvView.getText().toString().charAt(mKeypadBinding.tvView.getText().toString().length() - 1) == '.')
                        isDot = false;

                    mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString()
                            .substring(0, mKeypadBinding.tvView.getText().toString().length() - 1));
                }

            }
        });

    }

    private void clickEnter() {

        mActivity.runOnUiThread(() -> {


            try {
                Float val = Float.parseFloat(mKeypadBinding.tvView.getText().toString());
                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                switch (type) {

                    case VSWR:
                    case DTF_VSWR:

                        if (val >= VswrAmplitudeData.DEFALUT_VSWR_Y_MIN && val < VswrAmplitudeData.DEFALUT_VSWR_Y_MAX
                                && val < VswrDataHandler.getInstance().getConfigData().getAmplitudeData().getAmpMax()) {

                            VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMin(val);
                            FunctionHandler.getInstance().getMainLineChart().setMinYRange(val);
                            FunctionHandler.getInstance().getMainLineChart().invalidate();

                        } else {
                            Toast.makeText(mActivity, "Out of range (1 ~ 65)", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case RL:
                    case DTF_RL:
                    case CABLE_LOSS:
                        if (val >= VswrAmplitudeData.DEFALUT_RL_Y_MIN && val < VswrAmplitudeData.DEFALUT_VSWR_Y_MAX
                                && val < VswrDataHandler.getInstance().getConfigData().getAmplitudeData().getAmpMax()) {

                            VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMin(val);
                            FunctionHandler.getInstance().getMainLineChart().setMinYRange(val);
                            FunctionHandler.getInstance().getMainLineChart().invalidate();

                        } else
                            Toast.makeText(mActivity, "Out of range (0 ~ 60)", Toast.LENGTH_SHORT).show();

                        break;

                }

                ViewHandler.getInstance().getAmplitudeView().update();
                dismiss();

            } catch (NumberFormatException e) {
                e.printStackTrace();
                dismiss();
            } catch (NullPointerException e) {
                e.printStackTrace();
                dismiss();
            }

        });

    }
}

package com.dabinsystems.pact_app.Dialog.SA;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Dialog.CustomBaseDialog;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.FreqKeypadBinding;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class SaStopFreqKeypad extends CustomBaseDialog {

    private FreqKeypadBinding mKeypadBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    private Boolean isMinus = false;
    private Boolean isDot = false;

    public SaStopFreqKeypad(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mKeypadBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.freq_keypad, null, false);
        setContentView(mKeypadBinding.getRoot());

        viewSetting();
        addEvents();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        dismiss();
    }

    private void viewSetting() {

        mKeypadBinding.tvView.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvView.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));
        mKeypadBinding.tvView.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));

        mKeypadBinding.tvDot.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvDot.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvDot.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvKhz.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvKhz.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvKhz.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvHz.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvHz.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvHz.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvMhz.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvMhz.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvMhz.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvGhz.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvGhz.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvGhz.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

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

    private void addEvents() {

        mKeypadBinding.tvGhz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    double value = Double.parseDouble(mKeypadBinding.tvView.getText().toString());
                    value *= 1000000000d;
                    mKeypadBinding.tvView.setText(value + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                clickEnter();
                dismiss();


            }
        });

        mKeypadBinding.tvMhz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    double value = Double.parseDouble(mKeypadBinding.tvView.getText().toString());
                    value *= 1000000d;
                    mKeypadBinding.tvView.setText(value + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                clickEnter();
                dismiss();

            }
        });

        mKeypadBinding.tvKhz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double value = Double.parseDouble(mKeypadBinding.tvView.getText().toString());
                    value *= 1000d;
                    String strValue = (long)value + "";
                    mKeypadBinding.tvView.setText(strValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                clickEnter();

                dismiss();
            }
        });

        mKeypadBinding.tvHz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                clickEnter();

                dismiss();
            }
        });


        mKeypadBinding.linKeypadParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitActivity.logMsg("linKeypadParent", "linKeypad");
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

        mKeypadBinding.tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickEnter();

                dismiss();
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

        try {


            if (mKeypadBinding.tvView.getText().length() == 0) {

                MainActivity.getActivity().runOnUiThread(() -> {

                    Toast.makeText(mActivity, "Out of range", Toast.LENGTH_SHORT).show();
                    InitActivity.logMsg("clickEnter", "Out of range");

                });
            } else if (mKeypadBinding.tvView.getText().length() == 1 &&
                    (mKeypadBinding.tvView.getText().toString().charAt(0) == '-' ||
                            mKeypadBinding.tvView.getText().toString().charAt(0) == '+')) {

                MainActivity.getActivity().runOnUiThread(() -> {

                    Toast.makeText(mActivity, "Out of range", Toast.LENGTH_SHORT).show();
                    InitActivity.logMsg("clickEnter", "Out of range");

                });

            } else {

                double val = (Double.parseDouble(mKeypadBinding.tvView.getText().toString()) / Math.pow(10, 6));

                SaDataHandler.getInstance().getConfigData().getFrequencyData().setStopFreq(val);
                SaDataHandler.getInstance().getConfigData().getFrequencyData().checkFreqRange();

                Double span = SaDataHandler.getInstance().getConfigData().getFrequencyData().getSpan();

//                float curStartFreq = FunctionHandler.getInstance().getMainLineChart().getStartFreq();
//                float curStopFreq = FunctionHandler.getInstance().getMainLineChart().getStopFreq();
//
//                Double newStartFreq = SaDataHandler.getInstance().getConfigData().getFrequencyData().getStartFreq();
//                Double newStopFreq = SaDataHandler.getInstance().getConfigData().getFrequencyData().getStopFreq();

//                if(curStartFreq == newStartFreq && curStopFreq == newStopFreq) {
//                    return ;
//                }
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                if(span != 0f) {
//                    FunctionHandler.getInstance().getMainLineChart().setStopFreq(
//                            SaDataHandler.getInstance().getConfigData().getFrequencyData().getStopFreq()
//                    );

                    FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
                    FunctionHandler.getInstance().getMainLineChart().updateSemBox();
                    new Handler(Looper.getMainLooper()).postDelayed(()->{

                        FunctionHandler.getInstance().getMainLineChart().updateOccBox();
                        FunctionHandler.getInstance().getMainLineChart().updateChannelBox();

                    }, 500);


                }

                ViewHandler.getInstance().getSaFrequencyView().updateFreq();
                ViewHandler.getInstance().getContent().subInfoUpdate();

                FunctionHandler.getInstance().getMainLineChart().updateMarkerPos();

                ViewHandler.getInstance().getContent().markerTableUpdate();
                ViewHandler.getInstance().getContent().markerValueUpdate();
                ViewHandler.getInstance().getSaMarkerView2().update();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

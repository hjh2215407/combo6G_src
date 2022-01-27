package com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SpuriousEmission;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SpuriousEmission.FreqRangeTableData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SpuriousEmission.SpuriousEmissionMeasSetupData;
import com.dabinsystems.pact_app.Dialog.CustomBaseDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.FreqKeypadBinding;

import java.util.ArrayList;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class FreqRangeTableStartFreqKeypad extends CustomBaseDialog {

    private FreqKeypadBinding mKeypadBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    private Boolean isMinus = false;
    private Boolean isDot = false;

    public FreqRangeTableStartFreqKeypad(MainActivity activity, ActivityMainBinding binding) {
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
                    value *= 1000;
                    String strValue = (long)value + "";
                    if(strValue.contains("."))
                        strValue = strValue.replace(".", "000000.");
                    else strValue += "000000";
                    mKeypadBinding.tvView.setText(strValue);
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
                    value *= 1000;
                    String strValue = (long)value + "";
                    if(strValue.contains("."))
                        strValue = strValue.replace(".", "000.");
                    else strValue += "000";
                    mKeypadBinding.tvView.setText(strValue);
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
                    value *= 1000;
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

            String[] value = mKeypadBinding.tvView.getText().toString().split("\\.");
            InitActivity.logMsg("CenterFreqKeypad", mKeypadBinding.tvView.getText().toString() + " " + value[0] + " " + value.length);

            if (mKeypadBinding.tvView.getText().length() == 0) {


            } else if (mKeypadBinding.tvView.getText().length() == 1 &&
                    (mKeypadBinding.tvView.getText().toString().charAt(0) == '-' ||
                            mKeypadBinding.tvView.getText().toString().charAt(0) == '+')) {

            } else if (value.length > 0 && value[0].length() > 11 ) {

                InitActivity.logMsg("CenterFreqKeypad", value[0]);
                Toast.makeText(mActivity, "out of range", Toast.LENGTH_SHORT).show();

            } else {
                double doubleVal = Double.parseDouble(mKeypadBinding.tvView.getText().toString()) / Math.pow(10, 6);
                float val = (float) (Double.parseDouble(mKeypadBinding.tvView.getText().toString()) / Math.pow(10, 6));
                val = (float)Math.round(val * 10000)/10000;

//                InitActivity.logMsg("CenterFrequency", val + " " + doubleVal + " " + 0.123456789f);

                SpuriousEmissionMeasSetupData data = SaDataHandler.getInstance().getSpuriousEmissionConfigData().getSpuriousEmissionData();
                ArrayList<FreqRangeTableData> FreqRangeList = data.getFreqRangeTableDataList();
                int selectedIdx = data.getSelectedIndex();

                FreqRangeTableData freqTableData = FreqRangeList.get(selectedIdx);

                freqTableData.setStartFreq(val);
                FunctionHandler.getInstance().getMainLineChart().updateSpuriousEmissionLine();
//                FunctionHandler.getInstance().getMainLineChart().invalidate();
                ViewHandler.getInstance().getFreqRangeTableView().update();

//                SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(val);
//                SaDataHandler.getInstance().getConfigData().getFrequencyData().checkFreqRange();

//                float span = SaDataHandler.getInstance().getConfigData().getFrequencyData().getSpan();

//                if(span != 0f) {
//
//                    float curCenterFreq = FunctionHandler.getInstance().getMainLineChart().getCenterFreq();
//                    float curSpan = FunctionHandler.getInstance().getMainLineChart().getSpan();
//
//                    float newCenterFreq = SaDataHandler.getInstance().getConfigData().getFrequencyData().getCenterFreq();
//                    float newSpan = SaDataHandler.getInstance().getConfigData().getFrequencyData().getSpan();
//
//                    if(curCenterFreq == newCenterFreq && curSpan == newSpan) {
//                        return ;
//                    }
//
//                    FunctionHandler.getInstance().getMainLineChart().setCenterFreq(
//                            SaDataHandler.getInstance().getConfigData().getFrequencyData().getCenterFreq()
//                    );
//
//                    FunctionHandler.getInstance().getMainLineChart().setSpan(
//                            SaDataHandler.getInstance().getConfigData().getFrequencyData().getSpan()
//                    );
//
//                    FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
//                    FunctionHandler.getInstance().getMainLineChart().updateSemBox();
//                    ViewHandler.getInstance().getSaFrequencyView().updateFreq();
//                    ViewHandler.getInstance().getContent().subInfoUpdate();
//
//                    FunctionHandler.getInstance().getMainLineChart().invalidate();
//
//                }

//                FunctionHandler.getInstance().getDataConnector().sendCommand(
//                        DataHandler.getInstance().getConfigCmd()
//                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

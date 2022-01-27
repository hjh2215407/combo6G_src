package com.dabinsystems.pact_app.Dialog;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.SweepTimeKeypadBinding;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class NumberKeypadDialog extends CustomBaseDialog {

    private ValueEnterListener valueEnterListener = null;

    public void setValueEnterListener(ValueEnterListener listener) {
        this.valueEnterListener = listener;
    }

    public int maxStringLen = 256;

    public void setMaxStringLen(int max) {
        this.maxStringLen = max;
    }

    public boolean isViewUnit = true, isViewPlusMinus = true, isViewDot = true;
    public boolean isView1 = true, isView2 = true, isView3 = true, isView4 = true, isView5 = true;
    public boolean isView6 = true, isView7 = true, isView8 = true, isView9 = true, isView0 = true;
    public String x1000000 = "S", x1000 = "ms", x1 = "μs";

    private SweepTimeKeypadBinding mKeypadBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    private Boolean isMinus = false;
    private Boolean isDot = false;

    public NumberKeypadDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);
        mActivity = activity;
        mMainBinding = binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mKeypadBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.sweep_time_keypad, null, false);
        setContentView(mKeypadBinding.getRoot());

        viewSetting();
        addEvents();
    }

    private void viewSetting() {

        mKeypadBinding.tvDot.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvDot.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvDot.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvS.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvS.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvS.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvS.setText(x1000000);

        mKeypadBinding.tvMs.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvMs.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvMs.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvMs.setText(x1000);

        mKeypadBinding.tvUs.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
        mKeypadBinding.tvUs.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvUs.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvUs.setText(x1);

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


        mKeypadBinding.tvDot.setVisibility(isViewDot ? View.VISIBLE : View.INVISIBLE);
        mKeypadBinding.tvS.setVisibility(isViewUnit ? View.VISIBLE : View.INVISIBLE);
        mKeypadBinding.tvMs.setVisibility(isViewUnit ? View.VISIBLE : View.INVISIBLE);
        mKeypadBinding.tvUs.setVisibility(isViewUnit ? View.VISIBLE : View.INVISIBLE);
        mKeypadBinding.tvPlusMinus.setVisibility(isViewPlusMinus ? View.VISIBLE : View.INVISIBLE);

        mKeypadBinding.tvValue0.setVisibility(isView0 ? View.VISIBLE : View.INVISIBLE);
        mKeypadBinding.tvValue1.setVisibility(isView1 ? View.VISIBLE : View.INVISIBLE);
        mKeypadBinding.tvValue2.setVisibility(isView2 ? View.VISIBLE : View.INVISIBLE);
        mKeypadBinding.tvValue3.setVisibility(isView3 ? View.VISIBLE : View.INVISIBLE);
        mKeypadBinding.tvValue4.setVisibility(isView4 ? View.VISIBLE : View.INVISIBLE);
        mKeypadBinding.tvValue5.setVisibility(isView5 ? View.VISIBLE : View.INVISIBLE);
        mKeypadBinding.tvValue6.setVisibility(isView6 ? View.VISIBLE : View.INVISIBLE);
        mKeypadBinding.tvValue7.setVisibility(isView7 ? View.VISIBLE : View.INVISIBLE);
        mKeypadBinding.tvValue8.setVisibility(isView8 ? View.VISIBLE : View.INVISIBLE);
        mKeypadBinding.tvValue9.setVisibility(isView9 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        dismiss();
    }

    private void add(String text) {
        String str = mKeypadBinding.tvView.getText().toString();
        if (str.length() < maxStringLen)
            mKeypadBinding.tvView.setText(str + text);
    }

    private void addEvents() {

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
                    add(".");//mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + ".");
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
                add("0");//mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "0");
            }
        });

        mKeypadBinding.tvValue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("1");//mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "1");
            }
        });

        mKeypadBinding.tvValue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("2");//mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "2");
            }
        });

        mKeypadBinding.tvValue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("3");//mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "3");
            }
        });

        mKeypadBinding.tvValue4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("4");//mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "4");
            }
        });

        mKeypadBinding.tvValue5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("5");//mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "5");
            }
        });

        mKeypadBinding.tvValue6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("6");//mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "6");
            }
        });

        mKeypadBinding.tvValue7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("7");//mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "7");
            }
        });

        mKeypadBinding.tvValue8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("8");//mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "8");
            }
        });

        mKeypadBinding.tvValue9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add("9");//mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "9");
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
                clickUs();
            }
        });

        mKeypadBinding.tvS.setOnClickListener(v -> clickS());

        mKeypadBinding.tvMs.setOnClickListener(v -> clickMs());

        mKeypadBinding.tvUs.setOnClickListener(v -> clickUs());

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

    private Boolean isValue() {

        String valueStr = mKeypadBinding.tvView.getText().toString();

        if (valueStr.length() == 0) return false;
        else if (valueStr.equals("-") || valueStr.equals("+")) return false;
        else return true;

    }

    private void clickS() {
        if (isValue()) {
            double val = Double.parseDouble(mKeypadBinding.tvView.getText().toString()) * Math.pow(10, 6);
            if (valueEnterListener != null)
                valueEnterListener.OnValue(val);
        }

//        if(isValue()) {
//            valueEnterListener.OnValue(mKeypadBinding.tvView.getText().toString());
//
//            int val = (int)(Double.parseDouble(mKeypadBinding.tvView.getText().toString()) * Math.pow(10, 6));
//
//            SaDataHandler
//                    .getInstance()
//                    .getConfigData()
//                    .getSweepTimeData()
//                    .getGateData()
//                    .setGateViewSweepTime(val);
//
//            ViewHandler.getInstance().getGateView().update();
//            FunctionHandler.getInstance().getGateLineChart().update();
//        }
//
//        FunctionHandler.getInstance().getDataConnector().sendCommand(
//                DataHandler.getInstance().getConfigCmd()
//        );

        dismiss();

    }

    private void clickMs() {
        if (isValue()) {
            double val = Double.parseDouble(mKeypadBinding.tvView.getText().toString()) * Math.pow(10, 3);
            if (valueEnterListener != null)
                valueEnterListener.OnValue(val);
        }

//        if(isValue()) {
//            int val = (int)(Double.parseDouble(mKeypadBinding.tvView.getText().toString()) * Math.pow(10, 3));
//
//            SaDataHandler
//                    .getInstance()
//                    .getConfigData()
//                    .getSweepTimeData()
//                    .getGateData()
//                    .setGateViewSweepTime(val);
//
//            ViewHandler.getInstance().getGateView().update();
//            FunctionHandler.getInstance().getGateLineChart().update();
//        }
//
//        FunctionHandler.getInstance().getDataConnector().sendCommand(
//                DataHandler.getInstance().getConfigCmd()
//        );

        dismiss();

    }

    private void clickUs() {
        if (isValue()) {
            double val = Double.parseDouble(mKeypadBinding.tvView.getText().toString());
            if (valueEnterListener != null)
                valueEnterListener.OnValue(val);
        }

//        if(mKeypadBinding.tvView.getText().toString().contains(".")) return;
//
//        if(isValue()) {
//            int val = (int)Double.parseDouble(mKeypadBinding.tvView.getText().toString());
//
//            SaDataHandler
//                    .getInstance()
//                    .getConfigData()
//                    .getSweepTimeData()
//                    .getGateData()
//                    .setGateViewSweepTime(val);
//
//            ViewHandler.getInstance().getGateView().update();
//            FunctionHandler.getInstance().getGateLineChart().update();
//
//        }
//
//        FunctionHandler.getInstance().getDataConnector().sendCommand(
//                DataHandler.getInstance().getConfigCmd()
//        );

        dismiss();

    }

}

package com.dabinsystems.pact_app.Dialog;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.VSWR.VswrConfigData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.DistanceKeypadBinding;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class DistanceKeypadDialog extends CustomBaseDialog {

    private DistanceKeypadBinding mKeypadBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    private Boolean isMinus = false;
    private Boolean isDot = false;

    public DistanceKeypadDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mKeypadBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.distance_keypad, null, false);
        setContentView(mKeypadBinding.getRoot());

        viewSetting();
        addEvents();

    }

    private void viewSetting() {

        mActivity.runOnUiThread(() -> {

            mKeypadBinding.tvDot.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
            mKeypadBinding.tvDot.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
            mKeypadBinding.tvDot.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

            mKeypadBinding.tvKm.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
            mKeypadBinding.tvKm.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
            mKeypadBinding.tvKm.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

            mKeypadBinding.tvM.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
            mKeypadBinding.tvM.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
            mKeypadBinding.tvM.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

            mKeypadBinding.tvView.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
            mKeypadBinding.tvView.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));
            mKeypadBinding.tvView.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));

            mKeypadBinding.tvRangeText.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
            mKeypadBinding.tvRangeText.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(13, mActivity));
            mKeypadBinding.tvRangeText.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(15, mActivity));
            mKeypadBinding.tvRangeText.setText("Range : " + VswrDataHandler.getInstance().getDtfConfigData().getMinDistance() + "m ~ " + VswrDataHandler.getInstance().getDtfConfigData().getMaxDistance() + "m");

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

        });

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

                clickMeterEnter();
            }
        });

        mKeypadBinding.tvKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickKmEnter();
            }
        });

        mKeypadBinding.tvM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMeterEnter();
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

    private void clickMeterEnter() {

        try {
            if (mKeypadBinding.tvView.getText().length() == 0) {


            } else if (mKeypadBinding.tvView.getText().length() == 1 &&
                    (mKeypadBinding.tvView.getText().toString().charAt(0) == '-' ||
                            mKeypadBinding.tvView.getText().toString().charAt(0) == '+')) {


            } else if (Float.parseFloat(mKeypadBinding.tvView.getText().toString()) < 0) {


            } else {

                int val = (int) Float.parseFloat(mKeypadBinding.tvView.getText().toString());

                VswrConfigData config = VswrDataHandler.getInstance().getDtfConfigData();

                if (!config.checkDistance(val)) {

                    Toast.makeText(mActivity, "Out of range(" + config.getMinDistance() + " ~ " + (int) (config.getMaxDistance()) + "m)", Toast.LENGTH_SHORT).show();
                    return;

                }
                FunctionHandler.getInstance().getMainLineChart().clearAllValues();
                VswrDataHandler.getInstance().getDtfConfigData().setDistance(val);
//                FunctionHandler.getInstance().getMainLineChart().setStartFreq(0);
//                FunctionHandler.getInstance().getMainLineChart().setStopFreq(val);
                FunctionHandler.getInstance().getMainLineChart().invalidate();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

                ViewHandler.getInstance().getFreqDistView().addMenu();


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        dismiss();

    }

    private void clickKmEnter() {

        try {
            if (mKeypadBinding.tvView.getText().length() == 0) {


            } else if (mKeypadBinding.tvView.getText().length() == 1 &&
                    (mKeypadBinding.tvView.getText().toString().charAt(0) == '-' ||
                            mKeypadBinding.tvView.getText().toString().charAt(0) == '+')) {


            } else if (Float.parseFloat(mKeypadBinding.tvView.getText().toString()) <= 0) {


            } else {

                int val = (int) (Double.parseDouble(mKeypadBinding.tvView.getText().toString()) * Math.pow(10, 3));

                VswrConfigData config = VswrDataHandler.getInstance().getDtfConfigData();

                if (!config.checkDistance(val)) {

                    Toast.makeText(mActivity, "Out of range(" + config.getMinDistance() + " ~ " + (int) (config.getMaxDistance()) + "m)", Toast.LENGTH_SHORT).show();
                    return;

                }
//                FunctionHandler.getInstance().getMainLineChart().setStopFreq(val);
                VswrDataHandler.getInstance().getDtfConfigData().setDistance(val);
                FunctionHandler.getInstance().getMainLineChart().updateMarkerPos();
                FunctionHandler.getInstance().getMainLineChart().clearAllValues();
//                FunctionHandler.getInstance().getMainLineChart().invalidate();

                ViewHandler.getInstance().getFreqDistView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );


            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            ViewHandler.getInstance().getFreqDistView().addMenu();
        }
        dismiss();

    }
}

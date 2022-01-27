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
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.FreqSetKeypadBinding;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class FreqSettingDialog extends CustomBaseDialog {

    private FreqSetKeypadBinding mKeypadBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    private Boolean isMinus = false;
    private Boolean isDot = false;

    public FreqSettingDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mKeypadBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.freq_set_keypad, null, false);
        setContentView(mKeypadBinding.getRoot());

        viewSetting();
        addEvents();

    }

    private void viewSetting() {

        mKeypadBinding.linKeypadParent.setPadding((int)convertDpToPixel(10, mActivity),
                (int)convertDpToPixel(10, mActivity),
                (int)convertDpToPixel(10, mActivity),
                (int)convertDpToPixel(10, mActivity));

        mKeypadBinding.tvStart.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvStop.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.edStartFreq.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.edStopFreq.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvKhz.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mKeypadBinding.tvKhz.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvKhz.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvHz.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mKeypadBinding.tvHz.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvHz.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvMhz.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mKeypadBinding.tvMhz.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvMhz.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvGhz.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mKeypadBinding.tvGhz.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvGhz.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mKeypadBinding.tvEnter.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mKeypadBinding.tvEnter.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mKeypadBinding.tvEnter.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        dismiss();
    }

    private void addEvents() {

        mKeypadBinding.tvGhz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mKeypadBinding.edStartFreq.setText((Float.parseFloat(mKeypadBinding.edStartFreq.getText().toString()) * Math.pow(10, 9)) + "");
                    mKeypadBinding.edStopFreq.setText((Float.parseFloat(mKeypadBinding.edStopFreq.getText().toString()) * Math.pow(10, 9)) + "");
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
                    mKeypadBinding.edStartFreq.setText((Float.parseFloat(mKeypadBinding.edStartFreq.getText().toString()) * Math.pow(10, 6)) + "");
                    mKeypadBinding.edStopFreq.setText((Float.parseFloat(mKeypadBinding.edStopFreq.getText().toString()) * Math.pow(10, 6)) + "");
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
                    mKeypadBinding.edStartFreq.setText((Float.parseFloat(mKeypadBinding.edStartFreq.getText().toString()) * Math.pow(10, 3)) + "");
                    mKeypadBinding.edStopFreq.setText((Float.parseFloat(mKeypadBinding.edStopFreq.getText().toString()) * Math.pow(10, 3)) + "");
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
                    mKeypadBinding.edStartFreq.setText((Float.parseFloat(mKeypadBinding.edStartFreq.getText().toString()) * Math.pow(10, 0)) + "");
                    mKeypadBinding.edStopFreq.setText((Float.parseFloat(mKeypadBinding.edStopFreq.getText().toString()) * Math.pow(10, 0)) + "");
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

        mKeypadBinding.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mKeypadBinding.tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickEnter();

                dismiss();
            }
        });


    }

    private void clickEnter() {

        try {
            if (mKeypadBinding.edStartFreq.getText().length() == 0 ||
                    mKeypadBinding.edStopFreq.getText().length() == 0 ) {

                Toast.makeText(mActivity, "Out of range(650 ~ 6000 MHz).", Toast.LENGTH_SHORT).show();
                InitActivity.logMsg("clickEnter", "Out of range(650 ~ 6000 MHz).");

            } else if ((Float.parseFloat(mKeypadBinding.edStartFreq.getText().toString()) / (float) Math.pow(10, 6)) < 450 ||
                    (Float.parseFloat(mKeypadBinding.edStartFreq.getText().toString()) / (float) Math.pow(10, 6)) > 4200 ||
                    (Float.parseFloat(mKeypadBinding.edStopFreq.getText().toString()) / (float) Math.pow(10, 6)) < 450 ||
                    (Float.parseFloat(mKeypadBinding.edStopFreq.getText().toString()) / (float) Math.pow(10, 6)) > 4200) {

                Toast.makeText(mActivity, "Out of range(650 ~ 6000 MHz).", Toast.LENGTH_SHORT).show();
                InitActivity.logMsg("clickEnter", "Out of range(650 ~ 6000 MHz).");

            } else {


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

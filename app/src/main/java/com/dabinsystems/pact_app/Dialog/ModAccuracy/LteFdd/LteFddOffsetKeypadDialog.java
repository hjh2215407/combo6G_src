package com.dabinsystems.pact_app.Dialog.ModAccuracy.LteFdd;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.CustomBaseDialog;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.DbKeypadBinding;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class LteFddOffsetKeypadDialog extends CustomBaseDialog {

    private DbKeypadBinding mKeypadBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    private Boolean isMinus = false;
    private Boolean isDot = false;

    public LteFddOffsetKeypadDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mKeypadBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.db_keypad, null, false);
        setContentView(mKeypadBinding.getRoot());

        if (FunctionHandler.getInstance().getMeasureMode().getMode() == MeasureMode.MEASURE_MODE.VSWR)
            mKeypadBinding.tvdB.setVisibility(View.INVISIBLE);
        else if (FunctionHandler.getInstance().getMeasureMode().getMode() == MeasureMode.MEASURE_MODE.DTF)
            mKeypadBinding.tvdB.setVisibility(View.VISIBLE);

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

        mKeypadBinding.linKeypadParent.setOnClickListener(v -> InitActivity.logMsg("linKeypadParent", "linKeypad"));

        mKeypadBinding.parent.setOnClickListener(v -> dismiss());

        mKeypadBinding.tvValue0.setOnClickListener(v -> mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "0"));

        mKeypadBinding.tvValue1.setOnClickListener(v -> mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "1"));

        mKeypadBinding.tvValue2.setOnClickListener(v -> mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "2"));

        mKeypadBinding.tvValue3.setOnClickListener(v -> mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "3"));

        mKeypadBinding.tvValue4.setOnClickListener(v -> mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "4"));

        mKeypadBinding.tvValue5.setOnClickListener(v -> mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "5"));

        mKeypadBinding.tvValue6.setOnClickListener(v -> mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "6"));

        mKeypadBinding.tvValue7.setOnClickListener(v -> mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "7"));

        mKeypadBinding.tvValue8.setOnClickListener(v -> mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "8"));

        mKeypadBinding.tvValue9.setOnClickListener(v -> mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + "9"));

        mKeypadBinding.tvPlusMinus.setOnClickListener(v -> {

            if (mKeypadBinding.tvView.getText().length() != 0 &&
                    mKeypadBinding.tvView.getText().toString().charAt(0) == '-' &&
                    isMinus) {

                mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString().replace("-", ""));
                isMinus = false;
            } else {
                mKeypadBinding.tvView.setText("-" + mKeypadBinding.tvView.getText().toString());
                isMinus = true;
            }
        });

        mKeypadBinding.tvdB.setOnClickListener(v -> {

            clickEnter();
            dismiss();

        });

        mKeypadBinding.tvEnter.setOnClickListener(v -> {

            clickEnter();
            dismiss();

        });

        mKeypadBinding.tvDot.setOnClickListener(v -> {
            if (isDot) return;
            else {
                mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString() + ".");
                isDot = true;
            }
        });

        mKeypadBinding.ivBackspace.setOnClickListener(v -> {
            if (mKeypadBinding.tvView.getText().length() - 1 >= 0) {

                if (mKeypadBinding.tvView.getText().toString().charAt(mKeypadBinding.tvView.getText().toString().length() - 1) == '.')
                    isDot = false;

                mKeypadBinding.tvView.setText(mKeypadBinding.tvView.getText().toString()
                        .substring(0, mKeypadBinding.tvView.getText().toString().length() - 1));
            }

        });

    }

    private void clickEnter() {

        try {
            if (mKeypadBinding.tvView.getText().length() == 0) {
                Toast.makeText(mActivity, "Please input value.", Toast.LENGTH_SHORT).show();

            } else if (mKeypadBinding.tvView.getText().length() == 1 &&
                    mKeypadBinding.tvView.getText().toString().charAt(0) == '-' ||
                    mKeypadBinding.tvView.getText().toString().charAt(0) == '+') {

                Toast.makeText(mActivity, "Please input value.", Toast.LENGTH_SHORT).show();

            } else {

                float val = Float.parseFloat(mKeypadBinding.tvView.getText().toString());

                DataHandler.getInstance().getLteFddData().getAmpData().setOffset(val);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getLteFddData().getLteFddCmd()
                );

                ViewHandler.getInstance().getLteFddAmpView().update();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

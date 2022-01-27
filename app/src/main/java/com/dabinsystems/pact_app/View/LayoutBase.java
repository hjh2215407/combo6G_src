package com.dabinsystems.pact_app.View;

import android.view.View;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Function.CalibrationFunc;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import me.grantland.widget.AutofitTextView;

public class LayoutBase {

    protected MainActivity mActivity;
    protected ActivityMainBinding binding;

    public LayoutBase(MainActivity activity, ActivityMainBinding binding) {

        mActivity = activity;
        this.binding = binding;

    }

    public void addMenu() throws NullPointerException {

        if (binding.linCalibration.getVisibility() == View.VISIBLE)
            binding.linCalibration.setVisibility(View.GONE);

        FunctionHandler.getInstance().getCalibrationFunc().setStep(CalibrationFunc.CALIBRATION.CAL_OFF);
        InitActivity.logMsg("LayoutBase", FunctionHandler.getInstance().getRecallFunc().isPreview() + "");
        if (FunctionHandler.getInstance().getRecallFunc().isPreview()) return;

    }

    public void initView() {
    }

    public void update() {

        initView();

    }

    public void setUpEvents() {
    }

    public void selectOptionView(AutofitTextView selectTextView, AutofitTextView unselectTextView) {
        if (selectTextView == null || unselectTextView == null)
            return;

        mActivity.runOnUiThread(() -> {
            try {
//                if(selectTextView != null) {
                selectTextView.setSelected(true);
//                    selectTextView.setBackgroundColor(Color.BLACK);
//                    selectTextView.setTextColor(Color.YELLOW);
//                }

//                if(unselectTextView != null) {
                unselectTextView.setSelected(false);
//                    unselectTextView.setBackground(null);
//                    unselectTextView.setTextColor(Color.BLACK);
//                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

    }

    public void selectOptionView(AutofitTextView selectTextView, AutofitTextView unselectTextView1, AutofitTextView unselectTextView2) {
        if (selectTextView == null || unselectTextView1 == null || unselectTextView2 == null)
            return;

        mActivity.runOnUiThread(() -> {
            try {
                selectTextView.setSelected(true);
                unselectTextView1.setSelected(false);
                unselectTextView2.setSelected(false);

                //selectTextView.setTypeface(null, Typeface.BOLD);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
    }

    public void enabledView(AutofitTextView textView, boolean enabled) {
        if (textView == null)
            return;

        mActivity.runOnUiThread(() -> textView.setEnabled(enabled));
    }

}

package com.dabinsystems.pact_app.Dialog;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.CablePropVelocityDialogBinding;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class CablePropDialog extends CustomBaseDialog {

    private CablePropVelocityDialogBinding mCablePropBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;


    public CablePropDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    public CablePropDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCablePropBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.cable_prop_velocity_dialog, null, false);
        setContentView(mCablePropBinding.getRoot());

        viewSetting();
        addEvents();

    }

    private void viewSetting() {

        mCablePropBinding.tvApply.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mCablePropBinding.tvApply.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mCablePropBinding.tvApply.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mActivity));

        mCablePropBinding.tvExit.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mCablePropBinding.tvExit.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mCablePropBinding.tvExit.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mActivity));

        mCablePropBinding.tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mCablePropBinding.tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mCablePropBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mActivity));

        mCablePropBinding.edProp.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mActivity));
    }

    public void addEvents() {


        mCablePropBinding.tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    boolean isValid;
                    if (mCablePropBinding.edProp.getText().toString().length() > 0) {

                        float val = Float.parseFloat(mCablePropBinding.edProp.getText().toString());

                        isValid = FunctionHandler.getInstance().getCableInfoFunc().setVelocity(val);

                        VswrDataHandler.getInstance().getConfigData().updateDistance();

                        FunctionHandler.getInstance().getCableInfoFunc().setCableName("User Defined");
                        String name = FunctionHandler.getInstance().getCableInfoFunc().getCableName();
                        mMainBinding.lineChartLayout.tvChartInfo.setText(name + " [PV : "
                                + FunctionHandler.getInstance().getCableInfoFunc().getVelocity() + ", CL : "
                                + FunctionHandler.getInstance().getCableInfoFunc().getLoss() + " dB/m]");

                        FunctionHandler.getInstance().getDataConnector().sendCommand(
                                DataHandler.getInstance().getConfigCmd()
                        );

                    } else isValid = false;

                    if(isValid)
                        dismiss();

                } catch (NumberFormatException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });


        mCablePropBinding.tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mCablePropBinding.linParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}

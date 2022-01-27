package com.dabinsystems.pact_app.Dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.CableLossInputDialogBinding;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class CableLossDialog extends CustomBaseDialog {

    private CableLossInputDialogBinding mCableLossBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;


    public CableLossDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    public CableLossDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCableLossBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.cable_loss_input_dialog, null, false);
        setContentView(mCableLossBinding.getRoot());

        viewSetting();
        addEvents();

    }

    private void viewSetting() {

        mCableLossBinding.tvApply.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mCableLossBinding.tvApply.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mCableLossBinding.tvApply.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mActivity));

        mCableLossBinding.tvExit.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mCableLossBinding.tvExit.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mCableLossBinding.tvExit.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mActivity));

        mCableLossBinding.tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mCableLossBinding.tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mCableLossBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mActivity));

        mCableLossBinding.tvLossTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mCableLossBinding.tvLossTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mCableLossBinding.tvLossTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mActivity));

        mCableLossBinding.edDB.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mActivity));

    }

    public void addEvents() {

        mCableLossBinding.tvApply.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (mCableLossBinding.edDB.getText().toString().length() > 0) {

                    FunctionHandler.getInstance().getCableInfoFunc().setLoss(
                            Float.parseFloat(mCableLossBinding.edDB.getText().toString())
                    );

                    FunctionHandler.getInstance().getCableInfoFunc().setCableName("User Defined");
                    String name = FunctionHandler.getInstance().getCableInfoFunc().getCableName();
                    mMainBinding.lineChartLayout.tvChartInfo.setText(name + " [PV : "
                            + FunctionHandler.getInstance().getCableInfoFunc().getVelocity() + ", CL : "
                            + FunctionHandler.getInstance().getCableInfoFunc().getLoss() + " dB/m]");

                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            DataHandler.getInstance().getConfigCmd()
                    );

                }
                dismiss();
            }
        });


        mCableLossBinding.tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mCableLossBinding.linParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}

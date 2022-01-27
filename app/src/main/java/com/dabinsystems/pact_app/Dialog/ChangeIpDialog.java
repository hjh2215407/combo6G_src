package com.dabinsystems.pact_app.Dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.ChangeIpDialogBinding;

import java.util.Objects;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class ChangeIpDialog extends CustomBaseDialog {

    private ChangeIpDialogBinding mIpBinding;
    private ActivityMainBinding mMainBinding;
    private InitActivity mActivity;


    public ChangeIpDialog(InitActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    public ChangeIpDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIpBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.change_ip_dialog, null, false);
        setContentView(mIpBinding.getRoot());

        viewSetting();
        addEvents();

    }

    private void viewSetting() {

        mIpBinding.tvApply.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mIpBinding.tvApply.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mIpBinding.tvApply.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mActivity));

        mIpBinding.tvExit.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mIpBinding.tvExit.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mIpBinding.tvExit.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mActivity));

        mIpBinding.tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mIpBinding.tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mIpBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mActivity));

        mIpBinding.edIP.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mActivity));
    }

    public void addEvents() {


        mIpBinding.tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FunctionHandler.getInstance().getDataConnector().setMqtt(Objects.requireNonNull(mIpBinding.edIP.getText()).toString());
                dismiss();
            }
        });


        mIpBinding.tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mIpBinding.linParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}

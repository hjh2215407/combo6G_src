package com.dabinsystems.pact_app.Dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrTaeData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.LayoutLoadingDialogBinding;
import com.dabinsystems.pact_app.databinding.LoadingDefaultBinding;

public class LoadingDefaultDialog extends CustomBaseDialog {

    private LoadingDefaultBinding mLoadingBinding;
    private ActivityMainBinding mMainBinding;
    private Activity mActivity;

    public LoadingDefaultDialog(Activity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.loading_default, null, false);
        setContentView(mLoadingBinding.getRoot());

        setValues();
        addEvents();

    }


    private void setValues() {
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    public void addEvents() {

    }


    @Override
    public void show() {
        super.show();
    }

}




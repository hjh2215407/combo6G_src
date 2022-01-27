package com.dabinsystems.pact_app.Dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Function.FWFunc;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.SystemVersionDialogBinding;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class SystemVersionDialog extends CustomBaseDialog {

    private SystemVersionDialogBinding mVersionBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    public SystemVersionDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    public SystemVersionDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mVersionBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.system_version_dialog, null, false);
        setContentView(mVersionBinding.getRoot());

        setValues();
        addEvents();

    }

    private void setValues() {

        mVersionBinding.tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mVersionBinding.tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));
        mVersionBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mActivity));

        mVersionBinding.tvAppVerTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mVersionBinding.tvAppVerTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mVersionBinding.tvAppVerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mVersionBinding.tvAppVer.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mVersionBinding.tvAppVer.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mVersionBinding.tvAppVer.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mVersionBinding.tvFwVerTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mVersionBinding.tvFwVerTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mVersionBinding.tvFwVerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mVersionBinding.tvFwVer.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mVersionBinding.tvFwVer.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));
        mVersionBinding.tvFwVer.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mActivity));

        mVersionBinding.tvExit.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mActivity));
        mVersionBinding.tvExit.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(17, mActivity));
        mVersionBinding.tvExit.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(17, mActivity));

    }

    public void addEvents() {

        FunctionHandler.getInstance().getDataConnector().sendCommand(
                FWFunc.FW_CMD.CHECK_VER.getHexString()
        );

        try {
            PackageInfo pInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
            String version = pInfo.versionName;
            mVersionBinding.tvAppVer.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        SharedPreferences sp = mActivity.getSharedPreferences("AppInfo", Context.MODE_PRIVATE);

        mVersionBinding.tvFwVer.setText(sp.getString("FWVer", "-"));

        mVersionBinding.tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mVersionBinding.linParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public SystemVersionDialogBinding getVersionBinding() {
        return mVersionBinding;
    }
}

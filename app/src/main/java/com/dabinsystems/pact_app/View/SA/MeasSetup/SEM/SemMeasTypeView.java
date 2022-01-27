package com.dabinsystems.pact_app.View.SA.MeasSetup.SEM;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SemMeasTypeData;
import com.dabinsystems.pact_app.Data.SA.TraceData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SemMeasTypeView extends LayoutBase {

    private LinearLayout linTotalPower, linPeak, linPsd;
    private AutofitTextView tvTotalPower, tvPeak, tvPsd;
    private ArrayList<View> mTotalPowerList, mPeakList, mPsdList;
    private DynamicView mDynamicView;

    public SemMeasTypeView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {

                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linTotalPower);
                binding.linRightMenu.addView(linPsd);
                binding.linRightMenu.addView(linPeak);

                binding.tvBack.setOnClickListener(v->{
                    ViewHandler.getInstance().getMeasSetupSemView().addMenu();
                });

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mTotalPowerList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.total_power_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linTotalPower = (LinearLayout) mTotalPowerList.get(0);
        tvTotalPower = (AutofitTextView) mTotalPowerList.get(1);

        mPsdList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.psd_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linPsd = (LinearLayout) mPsdList.get(0);
        tvPsd = (AutofitTextView) mPsdList.get(1);

        mPeakList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.peak_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linPeak = (LinearLayout) mPeakList.get(0);
        tvPeak = (AutofitTextView) mPeakList.get(1);

        setUpEvents();


    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()->{

            linTotalPower.setOnClickListener(v-> {

                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);
                ViewHandler.getInstance().getMeasSetupSemView().addMenu();
                FunctionHandler.getInstance().getMainLineChart().updateSemBox();
                FunctionHandler.getInstance().getMainLineChart().invalidate();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });


            linPsd.setOnClickListener(v-> {


                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.PSD);
                ViewHandler.getInstance().getMeasSetupSemView().addMenu();
                FunctionHandler.getInstance().getMainLineChart().updateSemBox();
                FunctionHandler.getInstance().getMainLineChart().invalidate();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linPeak.setOnClickListener(v-> {


                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.PEAK);
                ViewHandler.getInstance().getMeasSetupSemView().addMenu();
                FunctionHandler.getInstance().getMainLineChart().updateSemBox();
                FunctionHandler.getInstance().getMainLineChart().invalidate();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });
        });

    }

}

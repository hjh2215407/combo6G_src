package com.dabinsystems.pact_app.View.SA.Trace;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.DETECTOR;
import com.dabinsystems.pact_app.Data.SA.TraceData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SaDetectorView extends LayoutBase {

    private LinearLayout linNormal, linPeak, linNegative, linSample, linRMS;
    private AutofitTextView tvNormal, tvPeak, tvNegative, tvSample, tvRMS;
    private ArrayList<View> mNormalList, mPeakList, mNegativeList, mSampleList, mRMSList;
    private DynamicView mDynamicView;

    public SaDetectorView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {

                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linNormal);
                binding.linRightMenu.addView(linPeak);
                binding.linRightMenu.addView(linNegative);
                binding.linRightMenu.addView(linSample);
                binding.linRightMenu.addView(linRMS);


            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mNormalList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.normal_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linNormal = (LinearLayout) mNormalList.get(0);
        tvNormal = (AutofitTextView) mNormalList.get(1);

        mPeakList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.peak_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linPeak = (LinearLayout) mPeakList.get(0);
        tvPeak = (AutofitTextView) mPeakList.get(1);

        mNegativeList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.negative_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linNegative = (LinearLayout) mNegativeList.get(0);
        tvNegative = (AutofitTextView) mNegativeList.get(1);

        mSampleList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.sample_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linSample = (LinearLayout) mSampleList.get(0);
        tvSample = (AutofitTextView) mSampleList.get(1);

        mRMSList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.rms_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linRMS = (LinearLayout) mRMSList.get(0);
        tvRMS = (AutofitTextView) mRMSList.get(1);

        setUpEvents();


    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()->{

            linNormal.setOnClickListener(v-> {

                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.NORMAL);
                ViewHandler.getInstance().getContent().traceIconUpdate();
                ViewHandler.getInstance().getTrace().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linPeak.setOnClickListener(v-> {


                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.PEAK);
                ViewHandler.getInstance().getContent().traceIconUpdate();
                ViewHandler.getInstance().getTrace().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );


            });

            linNegative.setOnClickListener(v-> {


                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.NEGATIVE);
                ViewHandler.getInstance().getContent().traceIconUpdate();
                ViewHandler.getInstance().getTrace().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linSample.setOnClickListener(v-> {


                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.SAMPLE);
                ViewHandler.getInstance().getContent().traceIconUpdate();
                ViewHandler.getInstance().getTrace().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linRMS.setOnClickListener(v->{

                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.RMS);
                ViewHandler.getInstance().getContent().traceIconUpdate();
                ViewHandler.getInstance().getTrace().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            binding.tvBack.setOnClickListener(v->{
                ViewHandler.getInstance().getTrace().addMenu();
            });

        });

    }

}

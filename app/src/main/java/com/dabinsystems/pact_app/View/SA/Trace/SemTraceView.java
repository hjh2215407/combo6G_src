package com.dabinsystems.pact_app.View.SA.Trace;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.TRACE_MODE;
import com.dabinsystems.pact_app.Data.SA.TraceData;
import com.dabinsystems.pact_app.Function.MeasureType;
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

public class SemTraceView extends LayoutBase {

    private LinearLayout linClearWite, linAverage, linMaxHold, linMinHold, linChannelDetector, linOffsetDetector;
    private AutofitTextView tvClearWrite, tvAverage, tvMaxHold, tvMinHold, tvChannelDetector, tvOffsetDetector,
            tvChannelDetectorVal, tvOffsetDetectorVal;
    private ArrayList<View> mClearWriteList, mAverageList, mMaxHoldList, mMinHoldList, mChannelDetectorList, mOffsetDetectorList;
    private DynamicView mDynamicView;

    public SemTraceView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {
                binding.linRightMenu.removeAllViews();

                binding.linRightMenu.addView(linClearWite);
                binding.linRightMenu.addView(linAverage);
                binding.linRightMenu.addView(linMaxHold);
                binding.linRightMenu.addView(linMinHold);

                binding.linRightMenu.addView(linChannelDetector);
                binding.linRightMenu.addView(linOffsetDetector);

                binding.tvBack.setOnClickListener(v -> {

                });


            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mClearWriteList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.clear_write_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linClearWite = (LinearLayout) mClearWriteList.get(0);
        tvClearWrite = (AutofitTextView) mClearWriteList.get(1);

        mAverageList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.average_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linAverage = (LinearLayout) mAverageList.get(0);
        tvAverage = (AutofitTextView) mAverageList.get(1);

        mMaxHoldList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.max_hold_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linMaxHold = (LinearLayout) mMaxHoldList.get(0);
        tvMaxHold = (AutofitTextView) mMaxHoldList.get(1);

        mMinHoldList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.min_hold_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linMinHold = (LinearLayout) mMinHoldList.get(0);
        tvMinHold = (AutofitTextView) mMinHoldList.get(1);

        mChannelDetectorList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.channel_detector), "RMS");
        linChannelDetector = (LinearLayout) mChannelDetectorList.get(0);
        tvChannelDetector = (AutofitTextView) mChannelDetectorList.get(1);
        tvChannelDetectorVal = (AutofitTextView) mChannelDetectorList.get(2);

        mOffsetDetectorList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.offset_detector), "Peak");
        linOffsetDetector = (LinearLayout) mOffsetDetectorList.get(0);
        tvOffsetDetector = (AutofitTextView) mOffsetDetectorList.get(1);
        tvOffsetDetectorVal = (AutofitTextView) mOffsetDetectorList.get(2);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {


            linClearWite.setOnClickListener(v -> {

                SaDataHandler.getInstance().getSemConfigData().getTraceData().setSemTraceMode(TRACE_MODE.CLEAR_WRITE);
                ViewHandler.getInstance().getContent().traceIconUpdate();
                ViewHandler.getInstance().getSemTraceView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linAverage.setOnClickListener(v -> {

                SaDataHandler.getInstance().getSemConfigData().getTraceData().setSemTraceMode(TRACE_MODE.AVERAGE);
                ViewHandler.getInstance().getContent().traceIconUpdate();
                ViewHandler.getInstance().getSemTraceView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linMaxHold.setOnClickListener(v -> {
                SaDataHandler.getInstance().getSemConfigData().getTraceData().setSemTraceMode(TRACE_MODE.MAX_HOLD);
                ViewHandler.getInstance().getContent().traceIconUpdate();
                ViewHandler.getInstance().getSemTraceView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linMinHold.setOnClickListener(v -> {
                SaDataHandler.getInstance().getSemConfigData().getTraceData().setSemTraceMode(TRACE_MODE.MIN_HOLD);
                ViewHandler.getInstance().getContent().traceIconUpdate();
                ViewHandler.getInstance().getSemTraceView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linChannelDetector.setOnClickListener(v -> {

                ViewHandler.getInstance().getChannelDetectorView().addMenu();

            });

            linOffsetDetector.setOnClickListener(v -> {

                ViewHandler.getInstance().getOffsetDetectorView().addMenu();

            });


        });

    }

}

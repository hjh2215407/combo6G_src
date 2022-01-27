package com.dabinsystems.pact_app.View.SA.Trace;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.TRACE_TYPE;
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

public class SaTraceTypeView extends LayoutBase {

    private LinearLayout linUpdate, linView, linBlank;
    private AutofitTextView tvUpdate, tvView, tvBlank;
    private ArrayList<View> mUpdateList, mViewList, mBlankList;
    private DynamicView mDynamicView;

    private final int UPDATE = 0;
    private final int VIEW = 1;
    private final int BLANK = 2;

    public SaTraceTypeView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linUpdate);
                binding.linRightMenu.addView(linView);
                binding.linRightMenu.addView(linBlank);

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mUpdateList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.update_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linUpdate = (LinearLayout) mUpdateList.get(0);
        tvUpdate = (AutofitTextView) mUpdateList.get(1);

        mViewList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.view_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linView = (LinearLayout) mViewList.get(0);
        tvView = (AutofitTextView) mViewList.get(1);

        mBlankList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.blank_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linBlank = (LinearLayout) mBlankList.get(0);
        tvBlank = (AutofitTextView) mBlankList.get(1);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {


            linUpdate.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getTraceData().setType(TRACE_TYPE.UPDATE);
                ViewHandler.getInstance().getTrace().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

                ViewHandler.getInstance().getContent().traceIconUpdate();

            });

            linView.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getTraceData().setType(TRACE_TYPE.VIEW);
                ViewHandler.getInstance().getTrace().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

                ViewHandler.getInstance().getContent().traceIconUpdate();

            });

            linBlank.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getTraceData().setType(TRACE_TYPE.BLANK);
                FunctionHandler.getInstance().getMainLineChart().clearValues(
                        SaDataHandler.getInstance().getConfigData().getTraceData().getTraceIndex()
                );
                ViewHandler.getInstance().getTrace().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

                ViewHandler.getInstance().getContent().traceIconUpdate();

            });

            binding.tvBack.setOnClickListener(v -> {
                ViewHandler.getInstance().getTrace().addMenu();
            });

        });

    }
}

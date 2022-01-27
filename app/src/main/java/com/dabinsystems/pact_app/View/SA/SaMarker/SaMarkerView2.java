package com.dabinsystems.pact_app.View.SA.SaMarker;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.SaSetMarkerValueKeypad;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SaMarkerView2 extends LayoutBase {

    private LinearLayout linFreq, linRelative, linTrace, linTable, linAllOff, linMore;
    private AutofitTextView tvFreqVal, tvMarkerTableOn, tvMarkerTableOff, tvRelativeVal, tvTraceVal, tvTableTitle, tvFreqTitle;
    private boolean isOpenTable = false;

    private DynamicView mDynamicView;

    double preFreqVal = 0;
    private String strFreqVal = "";

    public SaMarkerView2(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            try {

                initView();
                update();

                mActivity.runOnUiThread(() -> {

                    ViewHandler.getInstance().getSAView().selectMarker();
                    binding.linRightMenu.removeAllViews();
                    binding.linRightMenu.addView(linFreq);
                    binding.linRightMenu.addView(linRelative);
                    binding.linRightMenu.addView(linTrace);
                    binding.linRightMenu.addView(linTable);
                    binding.linRightMenu.addView(linAllOff);
                    binding.linRightMenu.addView(linMore);

                    binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.marker_name));
                    binding.linCableList.setVisibility(View.GONE);

                    binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getSaMarkerView().addMenu());

                });

            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity.getApplicationContext());

        ArrayList<View> mFreqList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.frequency_name), "");
        ArrayList<View> mRelativeList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.relative_to_name), "");
        ArrayList<View> mTraceList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.maker_trace_name), "1");
        ArrayList<View> mTableList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.marker_table_name)
                , mActivity.getResources().getString(R.string.on)
                , mActivity.getResources().getString(R.string.off));

        tvMarkerTableOn = (AutofitTextView) mTableList.get(2);
        tvMarkerTableOff = (AutofitTextView) mTableList.get(3);

        ArrayList<View> mOffList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.all_marker_off_name));
        ArrayList<View> mMoreList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.more_name), "2/2");

        linFreq = (LinearLayout) mFreqList.get(0);
        tvFreqTitle = (AutofitTextView) mFreqList.get(1);
        tvFreqVal = (AutofitTextView) mFreqList.get(2);

        linRelative = (LinearLayout) mRelativeList.get(0);
        tvRelativeVal = (AutofitTextView) mRelativeList.get(2);

        linTrace = (LinearLayout) mTraceList.get(0);
        tvTraceVal = (AutofitTextView) mTraceList.get(2);

        linTable = (LinearLayout) mTableList.get(0);
        tvTableTitle = (AutofitTextView) mTableList.get(1);

        linAllOff = (LinearLayout) mOffList.get(0);
        linMore = (LinearLayout) mMoreList.get(0);

        setUpEvents();
    }

    @Override
    public void setUpEvents() throws NullPointerException {

        mActivity.runOnUiThread(() -> {

            linFreq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new SaSetMarkerValueKeypad(mActivity, binding).show();
                }
            });

            linRelative.setOnClickListener(v -> {

                ViewHandler.getInstance().getRelativeTo().addMenu();

            });

            linTrace.setOnClickListener(v -> {
                ViewHandler.getInstance().getMarkerTrace().addMenu();
            });

            linTable.setOnClickListener(v -> {

                ViewHandler.getInstance().getContent().setMarkerTable(
                        !ViewHandler.getInstance().getContent().isOpenMarkerTable()
                );

                ViewHandler.getInstance().getContent().update();
                update();

            });

            linAllOff.setOnClickListener(v -> {

                FunctionHandler.getInstance().getMainLineChart().removeAllMarkers();
                update();
                ViewHandler.getInstance().getContent().markerIconUpdate();
                ViewHandler.getInstance().getContent().markerTableUpdate();

            });

            linMore.setOnClickListener(v -> {
                ViewHandler.getInstance().getSaMarkerView().addMenu();
            });

        });
    }


    public void update() {

        updateTrace();
        updateValue();
        updateRef();
        updateMarkerTableBtn();
        updateMenu();

    }

    public void updateMenu() {

        initView();
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {

            new Handler(Looper.getMainLooper()).post(() -> {

                tvFreqTitle.setText(mActivity.getResources().getString(R.string.frame_time));

            });

        } else {
            new Handler(Looper.getMainLooper()).post(() -> {
                try {
                    tvFreqTitle.setText(mActivity.getResources().getString(R.string.frequency_name));
                } catch (NullPointerException e) {
                    InitActivity.logMsg("SaMarkerView2", "except");
                }
            });
        }

    }

    public void updateMarkerTableBtn() {

        if (ViewHandler.getInstance().getContent().isOpenMarkerTable()) {

            selectOptionView(tvMarkerTableOn, tvMarkerTableOff);

        } else {

            selectOptionView(tvMarkerTableOff, tvMarkerTableOn);

        }

    }


    public void updateValue() {
        //new Thread(() -> {
        initView();

        int idx = FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex();
        if (idx == -1)
            return;

        Double val = FunctionHandler.getInstance().getMainLineChart().getCurrentMarkerFreq(idx);
        if (val == null)
            return;

        if (preFreqVal == val)
            return;
        preFreqVal = val;

        val = Math.round(val * 10000d) / 10000d;

        MeasureType type = FunctionHandler.getInstance().getMeasureType();
        String unit;

        if (type.getType() == MeasureType.MEASURE_TYPE.TRANSMIT_MASK)
            unit = " ms";
        else if (SaDataHandler.getInstance().getConfigData().getFrequencyData().getSpan() == 0f)
            unit = " mS";
        else
            unit = " MHz";

        String formatVal = val + unit;

        if (strFreqVal.equals(formatVal))
            return;
        strFreqVal = formatVal;

        mActivity.runOnUiThread(() -> {
            try {
                if (tvFreqVal != null)
                    tvFreqVal.setText(strFreqVal);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
        //}).start();
    }

    public void updateTrace() {

        new Thread(() -> {


            initView();

//        InitActivity.logMsg("SaMarkerTraceView", FunctionHandler.getInstance().getMainLineChart().getMarkerTrace() + "");
            if (FunctionHandler.getInstance().getMainLineChart().getMarkerTrace() == -1) return;

            mActivity.runOnUiThread(() -> {

                try {
                    tvTraceVal.setText((FunctionHandler.getInstance().getMainLineChart().getMarkerTrace() + 1) + "");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            });

        }).start();

    }

    public void updateRef() {

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {

                try {

                    if (FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex() == -1) {

                        tvRelativeVal.setText("");

                    } else if (FunctionHandler.getInstance().getMainLineChart().getRefMarkerIndex() == -1) {

                        FunctionHandler.getInstance().getMainLineChart().setRefMarkerIndex(FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex() + 1);
                        InitActivity.logMsg("refUpdate", FunctionHandler.getInstance().getMainLineChart().getRefMarkerIndex() + "");
                        tvRelativeVal.setText((FunctionHandler.getInstance().getMainLineChart().getRefMarkerIndex() + 1) + "");

                    } else {
                        InitActivity.logMsg("refUpdate2", FunctionHandler.getInstance().getMainLineChart().getRefMarkerIndex() + "");
                        tvRelativeVal.setText((FunctionHandler.getInstance().getMainLineChart().getRefMarkerIndex() + 1) + "");
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            });


        }).start();


    }

}

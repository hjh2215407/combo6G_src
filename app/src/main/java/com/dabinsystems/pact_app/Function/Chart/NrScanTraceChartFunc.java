package com.dabinsystems.pact_app.Function.Chart;

import android.graphics.Color;
import android.os.strictmode.CleartextNetworkViolation;
import android.util.Log;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NR_SCAN_TRACE;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanRcvItem;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

/**
 * [jigum] 2021-07-27
 * <p>
 * 5G NR Scan Chart
 */
public class NrScanTraceChartFunc {

    private final int MAX_X_COUNT = 100;

    private final InitActivity mActivity;
    private final ActivityMainBinding binding;
    private final int traceIndex;
    private final AutofitTextView tvTitle;
    private final LineChart lineChart;
    private final ArrayList<ILineDataSet> mDataSets;
    //private final int xIdx[] = {0, 0, 0, 0};

    private NR_SCAN_TRACE eSelTrace;
    private boolean isInitChart = false;

    public NrScanTraceChartFunc(InitActivity activity, ActivityMainBinding binding, int traceIndex) {
        super();
        mActivity = activity;
        this.binding = binding;
        this.traceIndex = traceIndex;
        if (traceIndex == 0) {
            tvTitle = binding.nrScanLayout.tvChart1Title;
            lineChart = binding.nrScanLayout.chartTrace1;
        } else {
            tvTitle = binding.nrScanLayout.tvChart2Title;
            lineChart = binding.nrScanLayout.chartTrace2;
        }
        mDataSets = new ArrayList<>();
    }

    public void changeTrace(boolean isResetXMinMax) {
        if (!isInitChart)
            return;

        tvTitle.post(() -> {
            NrScanData data = DataHandler.getInstance().getNrScanData();
            eSelTrace = data.getTrace(traceIndex);
            tvTitle.setText(eSelTrace.getString());
        });

        reSetYMinMax(false);

        clearData(isResetXMinMax);

        lineChart.notifyDataSetChanged();

        lineChart.postInvalidate();
    }

    public void clearData(boolean isResetXMinMax) {
        for (int i = 0; i < mDataSets.size(); ++i) {
            //xIdx[i] = 0;
            mDataSets.get(i).clear();
        }

        resetXMinMax(MAX_X_COUNT);
    }

    private void resetXMinMax(int max) {
        if (max < MAX_X_COUNT)
            return;
        int min = max - MAX_X_COUNT;

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setAxisMinimum(min);
        xAxis.setAxisMaximum(max - 1);
    }

    public void initChart() {
        initDataSet();
        initLegend();
        initAxis();
        reSetYMinMax(false);

        LineData lineData = new LineData(mDataSets);
        lineChart.setData(lineData);

        isInitChart = true;

        changeTrace(true);
    }

    private void initDataSet() {
        String[] names = new String[]{
                "SKT", "KT", "LGU+", "C4"
        };
        int[] colors = new int[]{
                mActivity.getResources().getColor(R.color.nr_scan_trace1),
                mActivity.getResources().getColor(R.color.nr_scan_trace2),
                mActivity.getResources().getColor(R.color.nr_scan_trace3),
                mActivity.getResources().getColor(R.color.nr_scan_trace4),
        };
        LegendEntry[] legendEntries = new LegendEntry[4];

        for (int idx = 0; idx < 4; idx++) {

            ArrayList<Entry> values = new ArrayList<>();
            values.add(new Entry(0, 0));
            // TODO test
//            for (int i = 0; i < MAX_X_COUNT; ++i) {
//                values.add(new Entry(i, idx+i));
//                xIdx[idx]++;
//            }


            LineDataSet d = new LineDataSet(values, names[idx]);

            d.setAxisDependency(YAxis.AxisDependency.LEFT);

            d.setLineWidth(1.7f);
            d.setCircleRadius(1f);
            d.setCircleHoleRadius(1f);
            d.setFillAlpha(0);
            d.setValueTextSize(9f);
            d.setHighlightLineWidth(1.5f);

            d.setDrawCircles(false);
            d.setDrawCircles(false);
            d.setDrawValues(false);
            d.setDrawIcons(true);

            int color = colors[idx];

            d.setColor(color);
            d.setCircleColor(color);
            d.setFillColor(color);
            d.setHighLightColor(Color.rgb(244, 117, 117));
            d.setValueTextColor(Color.WHITE);

            mDataSets.add(d);


            legendEntries[idx] = new LegendEntry();
            legendEntries[idx].formColor = colors[idx];
            legendEntries[idx].label = names[idx];
        }
        lineChart.getLegend().setCustom(legendEntries);

    }

    private void initAxis() {
        lineChart.setTouchEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);

        YAxis leftY = lineChart.getAxisLeft();
        leftY.setTextColor(Color.WHITE);
        leftY.setLabelCount(6, true);
        leftY.setMinWidth(40);

        YAxis rightY = lineChart.getAxisRight();
        rightY.setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setLabelCount(11, true);
        xAxis.setDrawLabels(false);

        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(MAX_X_COUNT);

    }

    private void initLegend() {
        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.WHITE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
    }

    public void reSetYMinMax(boolean isRefresh) {
        NrScanData data = DataHandler.getInstance().getNrScanData();
        float min = data.getTraceScaleMin(traceIndex);
        float max = data.getTraceScaleMax(traceIndex);

        lineChart.getAxisLeft().setAxisMinimum(min);
        lineChart.getAxisLeft().setAxisMaximum(max);

        //lineChart.resetZoom();
        if (isRefresh) {
            lineChart.notifyDataSetChanged();
            lineChart.postInvalidate();
        }
    }


    public void add(int idx) {
        NrScanRcvItem item = DataHandler.getInstance().getNrScanData().getRcvData().get(idx);
        int x = item.getTimeIdx();
        float val = item.getTraceValue(eSelTrace);
        add(idx, x, val);
    }

    public void add(int idx, int x, float val) {

        LineData lineData = lineChart.getData();
        ILineDataSet set = lineData.getDataSetByIndex(idx);
        set.addEntry(new Entry(x, val));
        //lineData.notifyDataChanged();

        resetXMinMax(++x);

        if (set.getEntryCount() > MAX_X_COUNT) {
            set.removeFirst();
        }

        lineChart.notifyDataSetChanged();
        lineChart.postInvalidate();
    }
}

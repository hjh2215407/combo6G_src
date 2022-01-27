package com.dabinsystems.pact_app.Dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.GpsInfoData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.GpsInfoDialogBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class GpsInfoDialog extends CustomBaseDialog {
    final String TAG = "GpsInfoDialog";
    final boolean D = true;

    private MainActivity mActivity;
    private GpsInfoDialogBinding mLayoutBinding;
    private GpsInfoData mGpsInfoData;
    private Handler mHandler;

    private int colorV, colorPre;

    public GpsInfoDialog(MainActivity activity) {
        super(activity);
        mActivity = activity;
        mGpsInfoData = new GpsInfoData();
        mHandler = new Handler();

        colorV = getContext().getColor(R.color.gps_chart_bar);
        colorPre = getContext().getColor(R.color.gps_chart_bar_pre);
        mGpsInfoData.setColor(colorV, colorPre);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.gps_info_dialog, null, false);
        setContentView(mLayoutBinding.getRoot());

        setValues();
        addEvents();

        initChart();

        // GPS info 요청
        FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getGPSInfoRequestCmd());
    }

    private void setValues() {
    }

    private void addEvents() {
        mLayoutBinding.btnClose.setOnClickListener(v -> dismiss());
    }

    private void initChart() {
        BarChart chart = mLayoutBinding.barChart;

        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setDragEnabled(false);
        chart.setDrawMarkers(false);
        chart.setTouchEnabled(false);
        chart.setClickable(false);

        chart.setDescription(null); // 설명 X

        // x 축
        MyXValueFormatter xAxisFormatter = new MyXValueFormatter(mGpsInfoData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(12);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setTextColor(getContext().getColor(R.color.norText));
        xAxis.setAxisLineColor(getContext().getColor(R.color.norText));

        // left y
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisLeft().setAxisMaximum(80f);

        // right y
        chart.getAxisRight().setEnabled(false);

        // legend
        chart.getLegend().setEnabled(false);

        chart.setDrawBorders(true);
        chart.setBorderColor(getContext().getColor(R.color.norText));


        ArrayList<BarEntry> values = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        int count = mGpsInfoData.SateNum.length;
        for (int i = 0; i < count; ++i) {
            //values.add(new BarEntry(mGpsInfoData.SateNum[i], mGpsInfoData.SateSignal[i]));
            values.add(new BarEntry(i, i + 1));
            colors.add(colorV);
        }

        BarDataSet set1 = new BarDataSet(values, "gps signal");
        set1.setValueFormatter(new MyYValueFormatter());
        set1.setValueTextColor(getContext().getColor(R.color.selText));
        //set1.setColor(getContext().getColor(R.color.gps_chart_bar));
        set1.setColors(colors);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        //data.setValueTextSize(10f);
        //data.setValueTypeface(tfLight);
        //data.setBarWidth(0.9f);
        chart.setData(data);
    }

    private void setChartData() {
        BarChart chart = mLayoutBinding.barChart;
        BarDataSet set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
        List<Integer> colors = set1.getColors();

        int count = mGpsInfoData.ViewSateNum.length;

        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            //values.add(new BarEntry(mGpsInfoData.SateNum[i], mGpsInfoData.SateSignal[i]));
            values.add(new BarEntry(i, mGpsInfoData.ViewSateSignal[i]));
            colors.set(i, mGpsInfoData.ViewColor[i]);
        }

        set1.setValues(values);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    public void updateGpsInfo(byte[] buf) {
        if (D) Log.d(TAG, "updateGpsInfo");

        // 수신 데이터 분석
        mGpsInfoData.parsing(buf);

        Log.e("GPS Info Response(0x53)", "(Dialog) "+mGpsInfoData.gpsStatusToString() + "(" + mGpsInfoData.GPSStatus + ")");

        mGpsInfoData.changeSate();

        // 갱신
        mHandler.post(updateRunnable);
    }

    @SuppressLint("SetTextI18n")
    private void updateInfo() {
        mLayoutBinding.tvDate.setText(mActivity.getString(R.string.gps_date) + mGpsInfoData.dateToString());
        mLayoutBinding.tvTime.setText(mActivity.getString(R.string.gps_time) + mGpsInfoData.timeToString());
        mLayoutBinding.tvLat.setText(mActivity.getString(R.string.gps_lat) + mGpsInfoData.latitudeToString());
        mLayoutBinding.tvLong.setText(mActivity.getString(R.string.gps_lon) + mGpsInfoData.longitudeToString());
        mLayoutBinding.tvAlt.setText(mActivity.getString(R.string.gps_alt) + mGpsInfoData.altitudeToString());

        mLayoutBinding.tvGpsStatusVal.setText(mGpsInfoData.gpsStatusToString());
        mLayoutBinding.tvGpsAntStatusVal.setText(mGpsInfoData.antennaStatusToString());
    }

    Runnable updateRunnable = () -> {
        // UI 갱신
        updateInfo();
        setChartData();

        // GPS info 요청
        FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getGPSInfoRequestCmd());
    };

    class MyXValueFormatter extends ValueFormatter {
        private GpsInfoData gpsInfoData;

        MyXValueFormatter(GpsInfoData data) {
            gpsInfoData = data;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            if (value > -1 && value < gpsInfoData.ViewSateNum.length) {
                if (gpsInfoData.ViewSateNum[(int) value] == 0)
                    return "";
                else
                    return String.valueOf(gpsInfoData.ViewSateNum[(int) value]);
            }
            return "";
//            return getFormattedValue(value);
        }
    }

    class MyYValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            if (value == 0)
                return "";
            return String.valueOf((int) value);
        }
    }
}

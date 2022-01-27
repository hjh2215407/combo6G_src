package com.dabinsystems.pact_app.Function.Chart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class ScatterChartFunc2 {

    private ActivityMainBinding binding;
    private MainActivity mActivity;

    private Boolean isInit = false;

    private ArrayList<IScatterDataSet> mDataSets;

    /* =========== new variable ============= */


    private Boolean[] isDatasetOnDisplay;

    private float mCenterFreq = 0f;
    private float mStartFreq = -2.0f; // 400 ~ 4200
    private float mStopFreq = 2.0f; // 400 ~ 4200
    private float mSpan = 2.0f; // 10 Hz ~ 3.8 GHz

    private float mScaleDiv = 10; // 0.1 ~ 20 dB Vertical axis space
    private float mOffset = 0; // -100 ~ +100 dBm // mRefLev = mRefLev + Offset
    private float mRefLev = 0; // -100 ~ +100 dBm // mRefLev = mRefLev + Offset

    private float mConstellationScale = 0.5f;

    private float mMaxRefLev = 2.0f;
    private float mMinRefLev = -2.0f;

    public final int MAX_TRACE_SIZE = 8;
    public final int PSS_INDEX = 0;
    public final int SSS_INDEX = 1;
    public final int PBCH_INDEX = 2;
    public final int PBCH_DMRS_INDEX = 3;
    public final int PDCCH = 4;
    public final int PDCCH_DMRS_INDEX = 5;
    public final int PDSCH = 6;
    public final int PDSCH_DMRS = 7;

    public final String[] DATASET_LABELS = {"PSS", "SSS", "PBCH", "PCFICH", "PHICH", "PDCCH", "CRS", "PDSCH"};

    private ArrayList[] mDataList;

    public ScatterChartFunc2(Activity activity, ActivityMainBinding binding) {

        mActivity = (MainActivity) activity;
        this.binding = binding;

        initValues();

    }

    public void initValues() {

        if (isInit) return;

        isInit = true;

        mDataList = new ArrayList[MAX_TRACE_SIZE];

        for (int i = 0; i < mDataList.length; i++) {

            mDataList[i] = new ArrayList<>();
            mDataList[i].add(new Entry(-9999, -9999));
        }

        isDatasetOnDisplay = new Boolean[MAX_TRACE_SIZE];
        Arrays.fill(isDatasetOnDisplay, false);

//        initChart();

    }

    public void initChart() throws NullPointerException {

        new Thread(() -> {

            mActivity.requestPermission();

            setConstellationScale(0.5f);

            binding.lteFddLayout.scatterChart.setOnChartValueSelectedListener(mActivity);

            //bottom - left data box color
            binding.lteFddLayout.scatterChart.getLegend().setEnabled(true);
            binding.lteFddLayout.scatterChart.getLegend().setMaxSizePercent(10f);
            binding.lteFddLayout.scatterChart.getDescription().setEnabled(false);//그래프 오른쪽 하단의 설명 유무( 마커랑 관계 X ) // enable description text
            binding.lteFddLayout.scatterChart.setTouchEnabled(true); //false일 경우 화면 터치로 그래프를 움직일 수 없음 ( 마커 찍는 것도 불가능) // enable touch gestures
            binding.lteFddLayout.scatterChart.setDragEnabled(true); // enable scaling and dragging
            binding.lteFddLayout.scatterChart.setScaleEnabled(false);
            binding.lteFddLayout.scatterChart.setDoubleTapToZoomEnabled(false);
            binding.lteFddLayout.scatterChart.setPinchZoom(true);// if disabled, scaling can be done on x- and y-axis separately

            binding.lteFddLayout.scatterChart.post(() -> {
                binding.lteFddLayout.scatterChart.setBackgroundColor(Color.BLACK); // set an alternative background color
            });

            binding.lteFddLayout.scatterChart.setDrawBorders(true);

            binding.lteFddLayout.scatterChart.getAxisLeft().setDrawAxisLine(false);
            binding.lteFddLayout.scatterChart.setDrawBorders(false);
            binding.lteFddLayout.scatterChart.setBorderColor(Color.WHITE);
            binding.lteFddLayout.scatterChart.setBorderWidth(2);

            binding.lteFddLayout.scatterChart.setViewPortOffsets(80f, 50f, 140f, 100f);
            //binding.demodulationLayout.scatterChart.setViewPortOffsets(90f, 50f, 130f, 100f);

//            binding.demodulationLayout.scatterChart.setClipToPadding(true);

//            binding.demodulationLayout.scatterChart.setPadding(20, 20, 20, 20);

            // get the legend (only possible after setting mData)
            Legend l = binding.lteFddLayout.scatterChart.getLegend();

            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setFormLineWidth(0);
            //l.setYOffset(5f);
//            l.setXOffset(35f);
            l.setTextSize(6f);//8f);
            l.setEnabled(true);


            // modify the legend ...
            //왼쪽 하단 그래프 설명
//        l.setForm(Legend.LegendForm.LINE);
            l.setTextColor(Color.WHITE);

            //X축 값 텍스트
            XAxis xl = binding.lteFddLayout.scatterChart.getXAxis();
            xl.setTextColor(Color.WHITE);
            xl.setTextSize(8);// convertDpToPixel(5, mActivity));
//        xl.setCenterAxisLabels(true);
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            xl.setEnabled(true);

            //격자 설정
            xl.setDrawGridLines(true);
            xl.setGridColor(Color.WHITE);
            xl.setGridLineWidth(1f);
            xl.setLabelCount(9, true);

            xl.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {

                    float val = Float.parseFloat(Utils.formatNumber(value, 1, false));

                    float width = binding.lteFddLayout.scatterChart.getXAxis().mAxisMaximum
                            - binding.lteFddLayout.scatterChart.getXAxis().mAxisMinimum;

                    float quarter = width / 4f;

                    if (val != -quarter && val != quarter && val != getCenterFreq() && val != getStopFreq())
                        return "";
                    else return super.getFormattedValue(val);
                }
            });

            //왼쪽 Y값 텍스트
            YAxis leftAxis = binding.lteFddLayout.scatterChart.getAxisLeft();
//        leftAxis.setTextColor(Color.BLACK);
            leftAxis.setTextColor(Color.WHITE);
            leftAxis.setDrawLabels(true);
            leftAxis.setTextSize(8);//convertDpToPixel(5, mActivity));
            leftAxis.setXOffset(0);
            leftAxis.setYOffset(0);
            leftAxis.setLabelCount(9, true);

            leftAxis.setDrawGridLines(true);
            leftAxis.setGridColor(Color.WHITE);
            leftAxis.setGridLineWidth(1f);

            leftAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {

                    float minY = binding.lteFddLayout.scatterChart.getAxisLeft().mAxisMinimum;
                    float maxY = binding.lteFddLayout.scatterChart.getAxisLeft().mAxisMaximum;
                    float width = maxY - minY;
                    float quarter = width / 4f;
                    float center = (maxY + minY) / 2f;

                    if (value == minY || value == -quarter || value == quarter || value == center || value == maxY)
                        return super.getFormattedValue(value);
                    else return "";

                }
            });

            //

            YAxis rightAxis = binding.lteFddLayout.scatterChart.getAxisRight();
            rightAxis.setEnabled(false);

//        binding.lteFddLayout.scatterChart.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

            initDataset();
//
//        binding.lteFddLayout.scatterChart.invalidate();
//        binding.lteFddLayout.scatterChart.notifyDataSetChanged();

            updateEntry();

//        ViewHandler.getInstance().getContent().traceIconUpdate();
//        ViewHandler.getInstance().getContent().subInfoUpdate();

        }).start();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void addEvents() {

        binding.lteFddLayout.scatterChart.setOnTouchListener((v, event) -> {

            binding.lteFddLayout.scatterChart.getOnTouchListener().onTouch(v, event);

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    break;

                case MotionEvent.ACTION_MOVE:
                    break;

                case MotionEvent.ACTION_UP:
                    break;

            }

            return true;
        });

    }

    public ScatterDataSet createSet(int color, ArrayList<Entry> values, String label) {

        ScatterDataSet set = new ScatterDataSet(values, label);
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setScatterShapeHoleColor(color);
//        set.setScatterShapeHoleRadius(3f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(color);
        set.setScatterShapeSize(8f);
//        set.setLabel(label);
        set.setValueTextSize(9f);
        set.setDrawValues(false);

        return set;
    }

    public ScatterDataSet createSet(int color, ArrayList<Entry> values) {

        ScatterDataSet set = new ScatterDataSet(values, "");
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setScatterShapeHoleColor(color);
        set.setScatterShapeHoleRadius(3f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(color);
        set.setScatterShapeSize(8f);

//        set.setLabel(label);
        set.setValueTextSize(9f);
        set.setDrawValues(false);

        return set;
    }


    /* ======================== new function ============================ */

    private void initDataset() {

        if (mDataSets == null)
            mDataSets = new ArrayList<>();
        else return;

        int[] colors = new int[]{
                mActivity.getResources().getColor(R.color.lte_pss),
                mActivity.getResources().getColor(R.color.lte_sss),
                mActivity.getResources().getColor(R.color.lte_pbch),
                mActivity.getResources().getColor(R.color.lte_pcfich),
                mActivity.getResources().getColor(R.color.lte_phich),
                mActivity.getResources().getColor(R.color.lte_pdcch),
                mActivity.getResources().getColor(R.color.lte_crs),
                mActivity.getResources().getColor(R.color.lte_pdsch)
        };


        for (int i = 0; i < MAX_TRACE_SIZE; i++) {

            ArrayList<Entry> values = new ArrayList<>();
            mDataSets.add(createSet(colors[i], values, DATASET_LABELS[i]));
            getDataList(i).add(new Entry(-9999, -9999));

        }

        ScatterData data = new ScatterData(mDataSets);
        binding.lteFddLayout.scatterChart.setData(data);

    }

    public ArrayList<Entry> getRandomList(float random, int dataset, int length) {

        if (getDataList(dataset) == null) return null;
        getDataList(dataset).clear();

        for (int i = 0; i < length; i++) {

            Random rnd = new Random();
            rnd.setSeed(System.currentTimeMillis() * i);
            float numX = rnd.nextFloat() * random - 2;

            Random rnd2 = new Random();
            float numY = rnd2.nextFloat() * random - 2;
//            InitActivity.logMsg("random", num + "");
            getDataList(dataset).add(new Entry(numX, numY));

        }

        return getDataList(dataset);

    }

    public ScatterDataSet getDataset(int idx) {

        return (ScatterDataSet) binding.lteFddLayout.scatterChart.getData().getDataSetByIndex(idx);

    }

    public void setConstellationScale(float scale) {

        mConstellationScale = scale;
        float length = scale * 8;
        mMaxRefLev = length / 2;
        mMinRefLev = -length / 2;

        setStopFreq(mMaxRefLev);
        setStartFreq(mMinRefLev);
        setCenterFreq((mMaxRefLev + mMinRefLev) / 2);

        binding.lteFddLayout.scatterChart.getAxisLeft().setAxisMaximum(mMaxRefLev);
        binding.lteFddLayout.scatterChart.getAxisLeft().setAxisMinimum(mMinRefLev);
        binding.lteFddLayout.scatterChart.getAxisLeft().setLabelCount(9, true);
        binding.lteFddLayout.scatterChart.getXAxis().setAxisMaximum(mMaxRefLev);
        binding.lteFddLayout.scatterChart.getXAxis().setAxisMinimum(mMinRefLev);
        binding.lteFddLayout.scatterChart.getXAxis().setLabelCount(9, true);


    }

    public float getConstellationScale() {
        return mConstellationScale;
    }

    public float getOffset() {
        return mOffset;
    }

    public boolean setRefLev(float lev) {

        if (lev > 100 || lev < -100) return false;

        mRefLev = lev;
        mMaxRefLev = mRefLev + mOffset;
        mMinRefLev = mMaxRefLev - (mScaleDiv * 10);
        binding.lteFddLayout.scatterChart.getAxisLeft().setAxisMaximum(mMaxRefLev);
        binding.lteFddLayout.scatterChart.getAxisLeft().setAxisMinimum(mMinRefLev);

        return true;

    }

    public float getRefLev() {

        return mRefLev;
    }


    public Boolean isVisible(int trace) {

        Boolean visible = false;

        try {
            visible = binding.lteFddLayout.scatterChart.getData().getDataSetByIndex(trace).isVisible();
        } catch (NullPointerException e) {
            return false;
        }
        return visible;
    }


    /* Set Freq */

    public float getWidth() {

        float width = getStopFreq() - getStartFreq();
        return width;

    }

    public boolean setCenterFreq(float freq) {

        float centerFreq = freq;
        float startFreq = centerFreq - (mSpan / 2);
        float stopFreq = centerFreq + (mSpan / 2);

        mCenterFreq = freq;
        mStartFreq = startFreq;
        mStopFreq = stopFreq;

        binding.lteFddLayout.scatterChart.getXAxis().setAxisMinimum(mStartFreq);
        binding.lteFddLayout.scatterChart.getXAxis().setAxisMaximum(mStopFreq);

        return true;

    }

    public float getCenterFreq() {
        return mCenterFreq;
    }

    public Long getCenterFreqToHz() {

        Long freq = ((long) (mCenterFreq * 100) * 10) * 1000;

        return freq;

    }


    public boolean setStartFreq(float freq) {

        float start = freq;
        float center = start + (mStopFreq - start) / 2;
        float span = mStopFreq - start;

        mStartFreq = start;
        mCenterFreq = center;
        mSpan = span;

        binding.lteFddLayout.scatterChart.getXAxis().setAxisMinimum(mStartFreq);

        return true;

    }

    public float getStartFreq() {
        return mStartFreq;
    }

    public Long getStartFreqToHz() {

        Long freq = ((long) (mStartFreq * 100) * 10) * 1000;

        return freq;

    }

    public byte[] getStartFreqToBytes() {

        byte[] b = DataHandler.getInstance().intToBytes(getStartFreqToHz(), 8, ByteOrder.LITTLE_ENDIAN);
        return b;
    }

    public boolean setStopFreq(float freq) {

        float stop = freq;
        float center = mStartFreq + (stop - mStartFreq) / 2;
        float span = stop - mStartFreq;

        mStopFreq = stop;
        mCenterFreq = center;
        mSpan = span;

        binding.lteFddLayout.scatterChart.getXAxis().setAxisMaximum(mStopFreq);


        return true;

    }

    public float getStopFreq() {
        return mStopFreq;
    }

    public Long getStopFreqToHz() {

        Long freq = ((long) (mCenterFreq * 100) * 10) * 1000;

        return freq;

    }

    public boolean setSpan(float freq) {

        float span = freq;
        float startFreq = mCenterFreq - (span / 2);
        float stopFreq = mCenterFreq + (span / 2);

        mSpan = freq;
        mStartFreq = startFreq;
        mStopFreq = stopFreq;

        binding.lteFddLayout.scatterChart.getXAxis().setAxisMinimum(mStartFreq);
        binding.lteFddLayout.scatterChart.getXAxis().setAxisMaximum(mStopFreq);

        return true;

    }

    public float getSpan() {
        return mSpan;
    }

    public Long getSpanToHz() {

        Long freq = ((long) (mSpan * 100) * 10) * 1000;

        return freq;


    }


    /* Set ScatterChartFunc Data */

    public void addEntry(ArrayList<Entry> dataList, int dataset) {

        mActivity.runOnUiThread(() -> {

            ScatterData data = binding.lteFddLayout.scatterChart.getScatterData();

            IScatterDataSet scatterDataSet = data.getDataSetByIndex(dataset);
            scatterDataSet.clear();

            for (int i = 0; i < dataList.size(); i++) {

                scatterDataSet.addEntry(dataList.get(i));

            }

            binding.lteFddLayout.scatterChart.notifyDataSetChanged();
            binding.lteFddLayout.scatterChart.postInvalidate();

        });
    }

    public void updateEntry() {

        InitActivity.logMsg("updateEntry", mDataList.length + "");

        for (int i = 0; i < mDataList.length; i++) {
            getDataList(i).add(new Entry(-9999, -9999));
            addEntry(getDataList(i), i);

        }

//        resizeChart();

    }

    public void resizeChart() {

        try {

            binding.lteFddLayout.scatterChart.getViewPortHandler().setChartDimens(

                    binding.lteFddLayout.scatterChart.getHeight()
                            + binding.lteFddLayout.scatterChart.getLegend().mTextWidthMax
                            + binding.lteFddLayout.scatterChart.getExtraLeftOffset()
                            + binding.lteFddLayout.scatterChart.getExtraRightOffset()

                    , binding.lteFddLayout.scatterChart.getHeight());

            binding.lteFddLayout.scatterChart.notifyDataSetChanged();
            binding.lteFddLayout.scatterChart.invalidate();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    public void update() {


    }


    public ArrayList<Entry> getDataList(int idx) {

        if (idx < 0 || idx >= mDataList.length) return null;

        return mDataList[idx];
    }

    public ArrayList[] getDataSetList() {

        if (mDataList == null) initValues();

        return mDataList;

    }

    public void invalidate() {
        binding.lteFddLayout.scatterChart.notifyDataSetChanged();
        binding.lteFddLayout.scatterChart.invalidate();

    }

    public void clearAllValues() {
        for (int i = 0; i < MAX_TRACE_SIZE; i++) {
            if (binding.lteFddLayout.scatterChart.getData().getDataSetByIndex(i) != null)
                binding.lteFddLayout.scatterChart.getData().getDataSetByIndex(i).clear();
            getDataList(i).clear();
        }

    }


}

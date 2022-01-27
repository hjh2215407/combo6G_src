package com.dabinsystems.pact_app.Function.Chart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Function.Chart.BeamIndexAxisValueFormatter;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class CandleChartFunc {

    private ActivityMainBinding binding;
    private MainActivity mActivity;

    private Boolean isInit = false;

    private ArrayList<ICandleDataSet> mDataSets;

    /* =========== new variable ============= */


    private Boolean[] isDatasetOnDisplay;

    private float mCenterX = 3.5f;
    private float mStartX = -0.5f; // 400 ~ 4200
    private float mEntX = 7.5f; // 400 ~ 4200
    private float mSpan = 2.0f; // 10 Hz ~ 3.8 GHz

    private float mBeamPowerRefLev = 0;
    private float mScaleDiv = 10; // 0.1 ~ 20 dB Vertical axis space
    private float mOffset = 0; // -100 ~ +100 dBm // mRefLev = mRefLev + Offset
    private float mRefLev = 0; // -100 ~ +100 dBm // mRefLev = mRefLev + Offset

    public final float DEFAULT_Y_MAX = 0;
    public final float DEFAULT_Y_MIN = -100;

    private float mMaxY = DEFAULT_Y_MAX;
    private float mMinY = DEFAULT_Y_MIN;


    public final int GRID_VERTICAL_LINE_NUMBER = 9;
    public final int GRID_HORIZONTAL_LINE_NUMBER = 6;

    public final int MAX_TRACE_SIZE = 8;
    public final int PSS_INDEX = 0;
    public final int SSS_INDEX = 1;
    public final int PBCH_INDEX = 2;
    public final int PBCH_DMRS_INDEX = 3;
    public final int PDCCH = 4;
    public final int PDCCH_DMRS_INDEX = 5;
    public final int PDSCH = 6;
    public final int PDSCH_DMRS = 7;

    public final String[] DATASET_LABELS = {"PSS", "SSS", "PBCH", "PBCH DMRS", "PDCCH", "PDCCH DMRS", "PDSCH", "PDSCH DMRS"};

    private ArrayList[] mDataList;

    public CandleChartFunc(Activity activity, ActivityMainBinding binding) {

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
        }

        isDatasetOnDisplay = new Boolean[MAX_TRACE_SIZE];
        Arrays.fill(isDatasetOnDisplay, false);

    }

    public void initChart() throws NullPointerException {

        new Thread(() -> {

            mActivity.requestPermission();

            binding.demodulationLayout.candleChart.setOnChartValueSelectedListener(mActivity);
            setBeamPowerRefLev(0);
            binding.demodulationLayout.candleChart.getXAxis().setAxisMinimum(mStartX);
            binding.demodulationLayout.candleChart.getXAxis().setAxisMaximum(mEntX);

            //bottom - left data box color

            binding.demodulationLayout.candleChart.getLegend().setEnabled(true);
            binding.demodulationLayout.candleChart.getDescription().setEnabled(false);//그래프 오른쪽 하단의 설명 유무( 마커랑 관계 X ) // enable description text
            binding.demodulationLayout.candleChart.setTouchEnabled(true); //false일 경우 화면 터치로 그래프를 움직일 수 없음 ( 마커 찍는 것도 불가능) // enable touch gestures
            binding.demodulationLayout.candleChart.setDragEnabled(true); // enable scaling and dragging
            binding.demodulationLayout.candleChart.setScaleEnabled(false);
            binding.demodulationLayout.candleChart.setDoubleTapToZoomEnabled(false);
            binding.demodulationLayout.candleChart.setPinchZoom(true);// if disabled, scaling can be done on x- and y-axis separately

            binding.demodulationLayout.candleChart.post(() -> {
                binding.demodulationLayout.candleChart.setBackgroundColor(Color.BLACK); // set an alternative background color
            });

            binding.demodulationLayout.candleChart.setDrawBorders(true);

            binding.demodulationLayout.candleChart.getAxisLeft().setDrawAxisLine(false);
            binding.demodulationLayout.candleChart.setDrawBorders(false);
            binding.demodulationLayout.candleChart.setBorderColor(Color.WHITE);
            binding.demodulationLayout.candleChart.setBorderWidth(2);
            binding.demodulationLayout.candleChart.setExtraLeftOffset(convertDpToPixel(10, mActivity));
            binding.demodulationLayout.candleChart.setExtraBottomOffset(convertDpToPixel(5, mActivity));
            binding.demodulationLayout.candleChart.setExtraRightOffset(convertDpToPixel(20, mActivity));

            // get the legend (only possible after setting mData)
            Legend l = binding.demodulationLayout.candleChart.getLegend();

            l.setEnabled(false);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setDrawInside(false);
            l.setXOffset(5f);

            // modify the legend ...
            //왼쪽 하단 그래프 설명
//        l.setForm(Legend.LegendForm.LINE);
            l.setTextColor(Color.WHITE);

            //X축 값 텍스트
            XAxis xl = binding.demodulationLayout.candleChart.getXAxis();
            xl.setTextColor(Color.WHITE);
            xl.setTextSize(convertDpToPixel(5, mActivity));
//        xl.setCenterAxisLabels(true);
            xl.setAvoidFirstLastClipping(false);
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            xl.setEnabled(true);

            //왼쪽 Y값 텍스트
            YAxis leftAxis = binding.demodulationLayout.candleChart.getAxisLeft();
//        leftAxis.setTextColor(Color.BLACK);
            leftAxis.setTextColor(Color.WHITE);
            leftAxis.setDrawLabels(true);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setTextSize(convertDpToPixel(5, mActivity));
            leftAxis.setGridColor(Color.WHITE);
            leftAxis.setGridLineWidth(1f);
            leftAxis.setLabelCount(GRID_HORIZONTAL_LINE_NUMBER, true);

            //격자 설정
            xl.setDrawGridLines(true);
            xl.setGridColor(Color.WHITE);
            xl.setGridLineWidth(1f);
            xl.setLabelCount(GRID_VERTICAL_LINE_NUMBER, true);

            ArrayList<String> xLabels = new ArrayList<>();

            for (int i = 0; i < 8; i++) {

                xLabels.add(i + "");

            }

            xl.setValueFormatter(new BeamIndexAxisValueFormatter(xLabels));
            xl.setCenterAxisLabels(true);

            YAxis rightAxis = binding.demodulationLayout.candleChart.getAxisRight();
            rightAxis.setAxisMinimum(mMinY);
            rightAxis.setEnabled(false);

            binding.demodulationLayout.candleChart.invalidate();
            binding.demodulationLayout.candleChart.notifyDataSetChanged();
            binding.demodulationLayout.candleChart.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

            initDataset();

//        addEntry(getRandomList(-100, 0), 0);
//        addEntry(getRandomList(-100, 1), 1);
//        addEntry(getRandomList(-100, 2), 2);
//        addEntry(getRandomList(-100, 3), 3);
//        addEntry(getRandomList(-100, 4), 4);
//        addEntry(getRandomList(-100, 5), 5);
//        addEntry(getRandomList(-100, 6), 6);
//        addEntry(getRandomList(-100, 7), 7);

//        FunctionHandler.getInstance().getSgMeasFunc().setMeasureType(MeasureType.MEASURE_TYPE.SWEPT_SA);

            InitActivity.logMsg("dataList", getDataList(0).size() + "");

            selectChartData(3);

//        ViewHandler.getInstance().getContent().traceIconUpdate();
//        ViewHandler.getInstance().getContent().subInfoUpdate();

        }).start();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void addEvents() {

        binding.demodulationLayout.candleChart.setOnTouchListener((v, event) -> {

            binding.demodulationLayout.candleChart.getOnTouchListener().onTouch(v, event);

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

    public void selectChartData(int idx) {

        for (int i = 0; i < mDataSets.size(); i++) {

            if (i == idx) {

                mDataSets.set(i, createSet(
                        mActivity.getResources().getColor(R.color.beam_index_power_select_value), getDataList(i), "")
                );

            } else {

                mDataSets.set(i, createSet(
                        mActivity.getResources().getColor(R.color.beam_index_power_value), getDataList(i), "")
                );

            }


        }


        CandleData data1 = new CandleData(mDataSets);
        binding.demodulationLayout.candleChart.setData(data1);
        binding.demodulationLayout.candleChart.invalidate();
        binding.demodulationLayout.candleChart.notifyDataSetChanged();

    }

    public CandleDataSet createSet(int color, ArrayList<CandleEntry> values, String label) {

        CandleDataSet set = new CandleDataSet(values, label);

        set.setDrawIcons(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(color);
        set.setDecreasingColor(color);
        set.setNeutralColor(color);
        set.setIncreasingColor(color);
        set.setShadowColor(color);

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


        for (int i = 0; i < 8; i++) {

            ArrayList<CandleEntry> values = new ArrayList<>();
            mDataSets.add(createSet(mActivity.getResources().getColor(R.color.beam_index_power_value), values, ""));

        }

        CandleData data = new CandleData(mDataSets);
        binding.demodulationLayout.candleChart.setData(data);

    }

    public ArrayList<CandleEntry> getRandomList(float random, int dataset) {

        if (getDataList(dataset) == null) return null;
        getDataList(dataset).clear();


        Random rnd2 = new Random();
        float numY = rnd2.nextFloat() * random;
        InitActivity.logMsg("random", numY + "");
        getDataList(dataset).add(new CandleEntry(dataset, numY, -100, numY, -100));


        return getDataList(dataset);

    }

    public CandleDataSet getDataset(int idx) {

        return (CandleDataSet) binding.demodulationLayout.candleChart.getData().getDataSetByIndex(idx);

    }

    public void setBeamPowerRefLev(float level) {


        mBeamPowerRefLev = level;
        mMaxY = level;
        mMinY = DEFAULT_Y_MIN + level;

        binding.demodulationLayout.candleChart.getAxisLeft().setAxisMaximum(mMaxY);
        binding.demodulationLayout.candleChart.getAxisLeft().setAxisMinimum(mMinY);
        binding.demodulationLayout.candleChart.getAxisLeft().setLabelCount(GRID_HORIZONTAL_LINE_NUMBER, true);
        binding.demodulationLayout.candleChart.invalidate();
        binding.demodulationLayout.candleChart.notifyDataSetChanged();
    }

    public float getBeamPowerRefLev() {
        return mBeamPowerRefLev;
    }

    public boolean setOffset(float offset) {

        if (offset < -100 || offset > 100) return false;

        mOffset = offset;
        mMaxY = mRefLev + mOffset;
        mMinY = mMaxY - (mScaleDiv * 10);
        binding.demodulationLayout.candleChart.getAxisLeft().setAxisMaximum(mMaxY);
        binding.demodulationLayout.candleChart.getAxisLeft().setAxisMinimum(mMinY);
        binding.demodulationLayout.candleChart.invalidate();

        return true;
    }

    public float getOffset() {
        return mOffset;
    }

    public Boolean isVisible(int trace) {

        Boolean visible = false;

        try {
            visible = binding.demodulationLayout.candleChart.getData().getDataSetByIndex(trace).isVisible();
        } catch (NullPointerException e) {
            return false;
        }
        return visible;
    }


    /* Set Freq */

    public float getWidth() {

        float width = getStopX() - getStartX();
        return width;

    }

    public boolean setCenterFreq(float freq) {

        float centerFreq = freq;
        float startFreq = centerFreq - (mSpan / 2);
        float stopFreq = centerFreq + (mSpan / 2);

        mCenterX = freq;
        mStartX = startFreq;
        mEntX = stopFreq;

        binding.demodulationLayout.candleChart.getXAxis().setAxisMinimum(mStartX);
        binding.demodulationLayout.candleChart.getXAxis().setAxisMaximum(mEntX);

        return true;

    }

    public float getCenterFreq() {
        return mCenterX;
    }

    public Long getCenterFreqToHz() {

        Long freq = ((long) (mCenterX * 100) * 10) * 1000;

        return freq;

    }


    public boolean setStartX(float freq) {

        float start = freq;
        float center = start + (mEntX - start) / 2;
        float span = mEntX - start;

        mStartX = start;
        mCenterX = center;
        mSpan = span;

        binding.demodulationLayout.candleChart.getXAxis().setAxisMinimum(mStartX);

        return true;

    }

    public float getStartX() {
        return mStartX;
    }

    public Long getStartXToHz() {

        Long freq = ((long) (mStartX * 100) * 10) * 1000;

        return freq;

    }

    public byte[] getStartFreqToBytes() {

        byte[] b = DataHandler.getInstance().intToBytes(getStartXToHz(), 8, ByteOrder.LITTLE_ENDIAN);
        return b;
    }

    public boolean setStopX(float freq) {

        float stop = freq;
        float center = mStartX + (stop - mStartX) / 2;
        float span = stop - mStartX;

        mEntX = stop;
        mCenterX = center;
        mSpan = span;

        binding.demodulationLayout.candleChart.getXAxis().setAxisMaximum(mEntX);


        return true;

    }

    public float getStopX() {
        return mEntX;
    }

    public Long getStopFreqToHz() {

        Long freq = ((long) (mCenterX * 100) * 10) * 1000;

        return freq;

    }

    public boolean setSpan(float freq) {

        float span = freq;
        float startX = mCenterX - (span / 2);
        float endX = mCenterX + (span / 2);

        mSpan = freq;
        mStartX = startX;
        mEntX = endX;

        binding.demodulationLayout.candleChart.getXAxis().setAxisMinimum(mStartX);
        binding.demodulationLayout.candleChart.getXAxis().setAxisMaximum(mEntX);

        return true;

    }

    public float getSpan() {
        return mSpan;
    }

    public Long getSpanToHz() {

        Long freq = ((long) (mSpan * 100) * 10) * 1000;

        return freq;

    }


    /* Set candleChartFunc Data */

    public void addEntry(ArrayList<CandleEntry> dataList, int dataset) {

        mActivity.runOnUiThread(() -> {

            if (dataList.get(0).getHigh() == -999999) return;

            CandleData data = binding.demodulationLayout.candleChart.getCandleData();

            ICandleDataSet candleDataSet = data.getDataSetByIndex(dataset);

//            InitActivity.logMsg("addEntry Bar Range", dataList.get(0).getY() + "");
            candleDataSet.addEntry(dataList.get(0));

            binding.demodulationLayout.candleChart.notifyDataSetChanged();
            binding.demodulationLayout.candleChart.postInvalidate();

        });
    }

    public Boolean isDataOnDisplay(int idx) {

        try {

            if (binding.demodulationLayout.candleChart.getData().getDataSets().size() == 0)
                return false;
            else if (binding.demodulationLayout.candleChart.getData().getDataSets().get(idx).getEntryCount() == 0)
                return false;
            else return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void update() {


    }


    public ArrayList<CandleEntry> getDataList(int idx) {

        if (idx < 0 || idx >= mDataList.length) return null;

        return mDataList[idx];
    }

    public ArrayList<ICandleDataSet> getDataSetList() {

        if (mDataSets == null) initValues();

        return mDataSets;
    }

    public void addData(float data, int idx) {

        getDataList(idx).add(new CandleEntry(idx, data, mMinY, data, mMinY));

    }

    public void updateEntry() {

        for (int i = 0; i < mDataList.length; i++) {

            addEntry(getDataList(i), i);

        }

    }

    public ArrayList<Integer> getDataList() {

        if (SaDataHandler.getInstance().getConfigData().getTraceData().getTraceIndex() == -1)
            return null;

        return mDataList[SaDataHandler.getInstance().getConfigData().getTraceData().getTraceIndex()];
    }

    public int getActiveDataListIndex() {

        for (int i = 0; i < mDataList.length; i++) {

            if (isVisible(i)) return i;

        }

        return -1;

    }

    public void invalidate() {
        binding.demodulationLayout.candleChart.notifyDataSetChanged();
        binding.demodulationLayout.candleChart.invalidate();

    }

    public void clearDataList() {

        for (int i = 0; i < MAX_TRACE_SIZE + 4; i++) {
            getDataList(i).clear();
        }

    }

    public void clearAllValues() {


        for (int i = 0; i < MAX_TRACE_SIZE + 4; i++) {
            if (binding.demodulationLayout.candleChart.getData().getDataSetByIndex(i) != null)
                binding.demodulationLayout.candleChart.getData().getDataSetByIndex(i).clear();
        }
        binding.demodulationLayout.candleChart.invalidate();
    }

    public void clearValues(int idx) {
        if (idx < 0 || idx >= binding.demodulationLayout.candleChart.getData().getDataSets().size())
            return;

        binding.demodulationLayout.candleChart.getData().clearValues(idx);
        binding.demodulationLayout.candleChart.invalidate();
    }

}

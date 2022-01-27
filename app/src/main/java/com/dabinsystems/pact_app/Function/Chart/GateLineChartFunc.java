package com.dabinsystems.pact_app.Function.Chart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.GateBoxOffsetData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData;
import com.dabinsystems.pact_app.Data.SA.SaConfigData;
import com.dabinsystems.pact_app.Data.SA.SaGateData;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;


public class GateLineChartFunc {
    final String TAG = "GateLineChartFunc";
    final boolean D = false;

    private ActivityMainBinding binding;
    private MainActivity mActivity;

    private Runnable GateRunnable;
    private Handler GateHandler;
    private int delay = 40000;

    //Touched point
    private double GateStartX, GateEndX;
    private int TouchingPointX;
    private boolean isInBox = false;
    private boolean isChangedGateDelay = false;
    /**
     * 차트를 클릭하여 Gate Length 조절 중인지
     */
    private boolean isClickGateLengthChange = false;


    private Float mChartDataYMin = null;
    private Float mChartDataYMax = null;

    private Boolean isInit = false;

    ArrayList<ILineDataSet> mDataSets;
    private final static int mChartDataSetIndex = 0;

    /* =========== new variable ============= */

    private ArrayList<Float> mDataList;

    private float mCenterX;
    private float mStartX;
    private float mStopFreq;


    private float MAX_Y = 0;
    private float MIN_Y = -100f;

    private float mTopY = MAX_Y;
    private float mBottomY = MIN_Y;

    public final int MAIN_LINE_INDEX = 0;
    public final int GATE_BOX_OFFSET_IDX = 1; // Downlink 4개, Uplink 4개 
    public final int GATE_BOX_INDEX = 9; // Gate Box 최대 8개

    //org
    /*public final int LINK_BOX_MAX_COUNT = 8;*/
    //org
    //@@ [21.12.22] Gate Sweep View Up/Down Link 관련 수정
    public final int LINK_BOX_MAX_COUNT = 16;
    //

    public final int GATE_BOX_MAX_COUNT = 8;

    public final int NUMBER_X_GRID_LINE = 11;
    public final int NUMBER_Y_GRID_LINE = 5;

    private float RefLev = 0f;
    private float Offset = 0f;


    /**
     * Gate View Chart
     */
    private final LineChart gateLineChart;

    /**
     * DownLink / UpLink 표출 그래프
     */
    private final LineChart gateLinkChart;

    public GateLineChartFunc(Activity activity, ActivityMainBinding binding) {

        mActivity = (MainActivity) activity;
        this.binding = binding;

        gateLineChart = binding.lineChartLayout.gateLayout.gateLineChart;
        gateLinkChart = binding.lineChartLayout.gateLayout.gateLinkChart;

        initValues();
        addEvents();

    }

    public void initValues() {

        if (isInit) return;

        isInit = true;
        mDataList = new ArrayList<>();
        mStartX = 0f; // 400 ~ 4200
        mStopFreq = 1000f; // 400 ~ 4200

        GateRunnable = () -> {

            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
            SaConfigData configData = SaDataHandler.getInstance().getConfigData();

            if (mode != MeasureMode.MEASURE_MODE.SA) return;
            if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) return;

            if (configData.getSweepTimeData().getGateData().getGateMode() == SaGateData.GATE_MODE.OFF)
                return;

            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    DataHandler.getInstance().getGateDataCmd()
            );

        };

        new Handler(Looper.getMainLooper()).post(() -> {
            GateHandler = new Handler();
        });

    }

    public void startGateTimer() {

        if (GateHandler == null) GateHandler = new Handler();
        GateHandler.postDelayed(GateRunnable, delay);

    }

    public void removeGateTimer() {

        if (GateHandler == null) return;

        GateHandler.removeCallbacks(GateRunnable);
        GateHandler.removeCallbacksAndMessages(null);

    }

    public void initChart() throws NullPointerException {

        new Thread(() -> {

            mActivity.requestPermission();

            gateLineChart.setRenderer(new GateLineChartRenderer(gateLineChart, gateLineChart.getAnimator(), gateLineChart.getViewPortHandler()));

            // get the legend (only possible after setting mData)
            Legend l = gateLineChart.getLegend();

            // modify the legend ...
            //왼쪽 하단 그래프 설명
//        l.setForm(Legend.LegendForm.LINE);
            l.setTextColor(Color.WHITE);

            gateLineChart.setOnChartValueSelectedListener(mActivity);
            gateLineChart.getAxisLeft().setAxisMaximum(MAX_Y);
            gateLineChart.getAxisLeft().setAxisMinimum(MIN_Y);
            gateLineChart.getXAxis().setAxisMinimum(mStartX);
            gateLineChart.getXAxis().setAxisMaximum(mStopFreq);

            gateLineChart.getLegend().setEnabled(false);
            gateLineChart.getDescription().setEnabled(false);//그래프 오른쪽 하단의 설명 유무( 마커랑 관계 X ) // enable description text
            gateLineChart.setTouchEnabled(true); //false일 경우 화면 터치로 그래프를 움직일 수 없음 ( 마커 찍는 것도 불가능) // enable touch gestures
            gateLineChart.setDragEnabled(true); // enable scaling and dragging
            gateLineChart.setScaleEnabled(false);
            gateLineChart.setDoubleTapToZoomEnabled(false);
            gateLineChart.setPinchZoom(true);// if disabled, scaling can be done on x- and y-axis separately

            gateLineChart.post(() -> {
                gateLineChart.setBackgroundColor(Color.BLACK); // set an alternative background color
            });

            gateLineChart.setDrawBorders(true);

            gateLineChart.getAxisLeft().setDrawAxisLine(false);
            gateLineChart.setDrawBorders(false);
            gateLineChart.setBorderColor(Color.WHITE);
            gateLineChart.setBorderWidth(2);

            //gateLineChart.setExtraLeftOffset(convertDpToPixel(10, mActivity));
            //gateLineChart.setExtraBottomOffset(convertDpToPixel(5, mActivity));
            //gateLineChart.setExtraRightOffset(convertDpToPixel(10, mActivity));
            //gateLineChart.setViewPortOffsets(80, 50, 60, 8);
            gateLineChart.setViewPortOffsets(80, convertDpToPixel(6, mActivity), 50, convertDpToPixel(4, mActivity));

            //X축 값 텍스트
            XAxis xl = gateLineChart.getXAxis();
            xl.setTextColor(Color.WHITE);
            //xl.setTextSize(10f);
//        xl.setCenterAxisLabels(true);
            xl.setAvoidFirstLastClipping(false);
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            //xl.setYOffset(10);
            xl.setEnabled(false);

            //왼쪽 Y값 텍스트
            YAxis leftAxis = gateLineChart.getAxisLeft();
            leftAxis.setTextColor(Color.WHITE);
            leftAxis.setTextSize(10f); // 10f
            //leftAxis.setXOffset(0);
            //leftAxis.setYOffset(0);
            leftAxis.setLabelCount(NUMBER_Y_GRID_LINE, true);
            leftAxis.setDrawGridLines(true);
            leftAxis.setGridColor(mActivity.getResources().getColor(R.color.grid_line_color));
            leftAxis.setGridLineWidth(1f);
            leftAxis.setMinWidth(40);
            leftAxis.setMaxWidth(40);

            leftAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {

                    float minY = gateLineChart.getAxisLeft().mAxisMinimum;
                    float maxY = gateLineChart.getAxisLeft().mAxisMaximum;
                    float space = (maxY - minY) / 4;
                    float secendY = minY + space;
                    float centerY = secendY + space;
                    float thirdY = maxY - space;

                    float val = (float) Double.parseDouble(Utils.formatNumber(value, 4, false));

                    minY = (float) Double.parseDouble(Utils.formatNumber(minY, 4, false));
                    maxY = (float) Double.parseDouble(Utils.formatNumber(maxY, 4, false));
                    secendY = (float) Double.parseDouble(Utils.formatNumber(secendY, 4, false));
                    centerY = (float) Double.parseDouble(Utils.formatNumber(centerY, 4, false));
                    thirdY = (float) Double.parseDouble(Utils.formatNumber(thirdY, 4, false));

                    if (val == minY || val == secendY || val == centerY || val == thirdY || val == maxY) {
                        String strVal = Utils.formatNumber(value, 0, false);
                        return strVal;
                    } else return "";

                }

            });

            //격자 설정
            xl.setDrawGridLines(true);
            xl.setGridColor(mActivity.getResources().getColor(R.color.grid_line_color));
            xl.setGridLineWidth(1f);
            xl.setLabelCount(NUMBER_X_GRID_LINE, true);

            xl.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {

                    return "";
                }
            });

            //

            YAxis rightAxis = gateLineChart.getAxisRight();
            rightAxis.setEnabled(false);

            //gateLineChart.invalidate();
            gateLineChart.notifyDataSetChanged();
            gateLineChart.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

            initDataset();
            LineData data = new LineData(mDataSets);
            gateLineChart.setData(data);
//        addEntry(getRandomList(-10000, 0), 0);
//        addEntry(getRandomList(-50000, 2), 2);
//        addEntry(getRandomList(-70000, 3), 3);
            gateLineChart.notifyDataSetChanged();
//            gateViewChart.invalidate();


            initLinkChart();

            update();

        }).start();
    }

    private void initLinkChart() {

        //gateLinkChart.setExtraTopOffset(-convertDpToPixel(10, mActivity));
        //gateLinkChart.setExtraLeftOffset(convertDpToPixel(10, mActivity));
        //gateLinkChart.setExtraBottomOffset(convertDpToPixel(5, mActivity));
        //gateLinkChart.setExtraRightOffset(convertDpToPixel(10, mActivity));
        gateLinkChart.setViewPortOffsets(80, 0, 50, 0);

        gateLinkChart.getAxisRight().setEnabled(false);

        gateLinkChart.getAxisLeft().setAxisMaximum(2);//MAX_Y);
        gateLinkChart.getAxisLeft().setAxisMinimum(0);//MIN_Y);
        gateLinkChart.getAxisLeft().setTextColor(Color.WHITE);
        gateLinkChart.getAxisLeft().setDrawLabels(false);
        gateLinkChart.getAxisLeft().setMinWidth(40);
        gateLinkChart.getAxisLeft().setMaxWidth(40);
        gateLinkChart.getAxisLeft().setDrawGridLines(false);
        gateLinkChart.getAxisLeft().setDrawZeroLine(false);
        gateLinkChart.getAxisLeft().setDrawAxisLine(false);

        gateLinkChart.getXAxis().setAxisMinimum(mStartX);
        gateLinkChart.getXAxis().setAxisMaximum(mStopFreq);
        gateLinkChart.getXAxis().setTextColor(Color.WHITE);
        gateLinkChart.getXAxis().setDrawLabels(false);
        gateLinkChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        gateLinkChart.getXAxis().setDrawGridLines(false);
        gateLinkChart.getXAxis().setDrawAxisLine(false);

        // Legend
        LegendEntry[] legendEntries = {new LegendEntry(), new LegendEntry()};
        legendEntries[0].formColor = Color.BLUE;
        legendEntries[0].label = mActivity.getString(R.string.downlink);
        legendEntries[1].formColor = Color.RED;
        legendEntries[1].label = mActivity.getString(R.string.uplink);
        gateLinkChart.getLegend().setCustom(legendEntries);

        Legend legend = gateLinkChart.getLegend();
        legend.setEnabled(false);
        legend.setTextColor(Color.WHITE);
        //legend.setTextSize(10);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        gateLinkChart.getDescription().setEnabled(false);//그래프 오른쪽 하단의 설명 유무( 마커랑 관계 X ) // enable description text
        gateLinkChart.setTouchEnabled(true); //false일 경우 화면 터치로 그래프를 움직일 수 없음 ( 마커 찍는 것도 불가능) // enable touch gestures
        gateLinkChart.setDragEnabled(true); // enable scaling and dragging
        gateLinkChart.setScaleEnabled(false);
        gateLinkChart.setDoubleTapToZoomEnabled(false);
        gateLinkChart.setPinchZoom(true);// if disabled, scaling can be done on x- and y-axis separately

        gateLinkChart.post(() -> {
            gateLinkChart.setBackgroundColor(Color.BLACK); // set an alternative background color
        });

        gateLinkChart.setDrawBorders(false);


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        int downColor = mActivity.getResources().getColor(R.color.blue);
        int upColor = mActivity.getResources().getColor(R.color.red);

        for (int i = 0; i < LINK_BOX_MAX_COUNT; ++i) {
            int color = (i % 2 == 0) ? downColor : upColor;
            //createBoxDataSet(dataSets, color);

            ArrayList<Entry> values = new ArrayList<>();
            LineDataSet set = new LineDataSet(values, "");

            set.setColor(color);
            set.setLineWidth(10);
            set.setFillColor(color);
            set.setDrawFilled(true);
            set.setHighlightEnabled(false);
            set.setDrawCircles(false);
            set.setDrawCircleHole(false);

            dataSets.add(set);
        }

        LineData data = new LineData(dataSets);
        gateLinkChart.setData(data);
        gateLinkChart.notifyDataSetChanged();

    }

    @SuppressLint("ClickableViewAccessibility")
    public void addEvents() {

        gateLineChart.setOnTouchListener((view, motionEvent) -> {

            float x, y;
            //MPPointD point;
            SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();

            switch (motionEvent.getAction()) {

                case MotionEvent.ACTION_DOWN: {
//                    if (gateData.getGateType() == SaGateData.GATE_TYPE.Slot)
//                        break;

                    x = motionEvent.getX();
                    y = motionEvent.getY();

                    MPPointD startPoint = gateLineChart.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(x, y);

                    int nRightClickZone = (int) (gateLineChart.getXRange() / 30);
                    int gateNum = gateData.getGateNum(); // 표출 개수
                    int gateLength = gateData.getGateLength();
                    int gateDelay = gateData.getGateDelay();
                    int gateDelta = gateData.getGateDelta();
                    int endX;

                    if (gateData.getGateType() == SaGateData.GATE_TYPE.Time) {
                        for (int i = 0; i < gateNum; ++i) {
                            endX = gateDelay + (gateDelta * i) + gateLength;

                            int nRight = Math.abs(endX - (int) startPoint.x);
                            if (nRight < nRightClickZone) {
                                // Gate Box 오른쪽 라인 부분 클릭
                                isClickGateLengthChange = true;
                                TouchingPointX = (int) startPoint.x - gateData.getGateLength();
                            }
                        }
                    }

                    if (isClickGateLengthChange) {
                        // Gate Box 오른쪽 라인 부분 클릭 되었음.
                    } else if (startPoint.x >= gateLineChart.getXAxis().getAxisMinimum() && startPoint.x <= gateLineChart.getXAxis().getAxisMaximum()) {
                        //} else if (startPoint.x >= gateData.getGateDelay() && startPoint.x <= (gateData.getGateDelay() + gateData.getGateLength())) {
                        // 그래프 클릭 되었음. // Gate Box 안쪽 부분 클릭
                        isInBox = true;
                        TouchingPointX = (int) (startPoint.x - gateData.getGateDelay());
                        InitActivity.logMsg("GateDelay", "down -> isInBox is true");
                    } else {
                        isInBox = false;
                        isClickGateLengthChange = false;
                        InitActivity.logMsg("GateDelay", "down -> isInBox is false");
                    }

                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    if (!isInBox && !isClickGateLengthChange) {
                        InitActivity.logMsg("GateDelay", "move -> break");
                        break;
                    }

                    x = motionEvent.getX();
                    y = motionEvent.getY();
                    MPPointD endPoint = gateLineChart.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(x, y);

                    if (isInBox) {
                        float prevDelay = gateData.getGateDelay();
                        int curDelay = (int) (endPoint.x - TouchingPointX);

                        if (Math.abs(curDelay - prevDelay) >= 1) {

                            if (gateData.getGateType() == SaGateData.GATE_TYPE.Time) {

                                if (curDelay < gateData.getGateDelayMin())
                                    curDelay = gateData.getGateDelayMin();
                                else if (curDelay > gateData.getGateDelayMax())
                                    curDelay = gateData.getGateDelayMax();

                                gateData.setGateDelay(curDelay);
                                InitActivity.logMsg("GateDelay", "move -> setGateDelay : " + curDelay);

                                updateGateBox();
                                ViewHandler.getInstance().getGateView().update();

                            } else {
                                // Chart의 X 축을 28 * Gate View Sweep Time 으로 등분
                                // Gate Box가 28 * Gate View Sweep Time 개의 구간 중 하나로 들어오는 경우 구간의 인덱스를 Gate Delay 값으로 설정 구간의 시간 길이는 대략 35.677us 정도\
                                Log.d(TAG, "gateData.getGateViewSweepTime() = " + gateData.getGateViewSweepTime() + ", curDelay = " + curDelay);
                                float unit = gateLineChart.getXAxis().getAxisMaximum() / (28 * (gateData.getGateViewSweepTime() / 1000));
                                Log.d(TAG, "unit = " + unit);
                                int idx = (int) (curDelay / unit);

                                if (idx < gateData.getGateDelaySymbolMin())
                                    idx = gateData.getGateDelaySymbolMin();
                                else if (idx > gateData.getGateDelaySymbolMax())
                                    idx = gateData.getGateDelaySymbolMax();

                                gateData.setGateDelaySymbol(idx);

                                updateGateBox();
                                ViewHandler.getInstance().getGateView().update();
                            }

                        } else {
                            InitActivity.logMsg("GateDelay", "down -> isChangeGateDelay is false");
                        }
                    } else if (isClickGateLengthChange) {
                        int gateLength = gateData.getGateLength();
                        int curLength = (int) endPoint.x - TouchingPointX;

                        if (Math.abs(curLength - gateLength) >= 1) {
                            if (curLength < gateData.getGateLengthMin())
                                curLength = gateData.getGateLengthMin();
                            else if (curLength > gateData.getGateLengthMax())
                                curLength = gateData.getGateLengthMax();
                            gateData.setGateLength(curLength);

                            updateGateBox();
                            ViewHandler.getInstance().getGateView().update();
                        }
                    }

                    break;
                }
                case MotionEvent.ACTION_UP: {
                    if (isInBox || isClickGateLengthChange) {
                        FunctionHandler.getInstance().getDataConnector().sendCommand(
                                SaDataHandler.getInstance().getConfigData().getSaParameter()
                        );
                    }

                    isInBox = false;
                    isClickGateLengthChange = false;

                    break;
                }
            }

            return true;
        });

    }

    public void createBorderDataSet(int color) {

        ArrayList<Entry> values = new ArrayList<>();

        LineDataSet dataset = new LineDataSet(values, "");
//            d.setLabel("Data");
        dataset.setDrawStartStop(true); // "start" "stop" 표시 하기

        dataset.setDrawIcons(false);

        dataset.setAxisDependency(YAxis.AxisDependency.LEFT);
//            dataset.setCubicIntensity(0f);
        dataset.setDrawFilled(false);
//            dataset.setFillAlpha();
        dataset.setHighlightEnabled(false);

        dataset.setDrawCircles(true);//false);
        dataset.setDrawCircleHole(false);
        dataset.setCircleColor(color);
        dataset.setCircleRadius(4);

        //dataset.setLineWidth(5f);

        dataset.setValueTextSize(12f);
        //dataset.setHighlightLineWidth(1.5f);
        dataset.setDrawValues(true);// false);
        dataset.setValueTextColor(color);
        dataset.setValueTypeface(Typeface.DEFAULT_BOLD);

        dataset.setColor(color);
//            dataset.setCircleColor(color);
//            dataset.setFillColor(color);

        mDataSets.add(dataset);

    }


    public void createLineDataSet(int color) {
        ArrayList<Entry> values = new ArrayList<>();

        LineDataSet dataset = new LineDataSet(values, "");

        dataset.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset.setDrawFilled(false);

        dataset.setHighlightEnabled(false);
        dataset.setDrawCircles(false);
        dataset.setDrawCircleHole(false);
        dataset.setLineWidth(2.5f);

        dataset.setValueTextSize(9f);
        dataset.setHighlightLineWidth(1.5f);
        dataset.setDrawValues(false);

        dataset.setColor(color);
//            dataset.setCircleColor(color);
//            dataset.setFillColor(color);

        mDataSets.add(dataset);
    }

    public void createBoxDataSet(ArrayList<ILineDataSet> list, int color) {

        ArrayList<Entry> values = new ArrayList<>();

        LineDataSet dataset = new LineDataSet(values, "");
        dataset.setAxisDependency(YAxis.AxisDependency.LEFT);

        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataset.setCubicIntensity(0f);
        dataset.setDrawFilled(true);
        dataset.setFillAlpha(125);//20);

        dataset.setHighlightEnabled(false);
        dataset.setDrawCircles(false);
        dataset.setDrawCircleHole(false);
        dataset.setLineWidth(0f);
        dataset.setFormLineWidth(0f);

        dataset.setValueTextSize(9f);
        dataset.setHighlightLineWidth(0f);
        dataset.setDrawValues(false);
        dataset.setDrawIcons(false);

        dataset.setColor(color);
        dataset.setCircleColor(color);
        dataset.setFillColor(color);
        dataset.setHighLightColor(color);
        dataset.setValueTextColor(Color.WHITE);

        list.add(dataset);
        InitActivity.logMsg("mDataSets", mDataSets.size() + "");

    }

    public Float getChartDataYMin() {
        return mChartDataYMin;
    }

    public Float getChartDataYMax() {

        return mChartDataYMax;
    }

    public static int getChartDataSetIndex() {
        return mChartDataSetIndex;
    }

    /* ======================== new function ============================ */

    private void initDataset() {

        if (mDataSets == null)
            mDataSets = new ArrayList<>();
        else return;

        createLineDataset();

        createBoxDataSet(mDataSets, mActivity.getColor(R.color.blue));
        createBoxDataSet(mDataSets, mActivity.getColor(R.color.red));
        createBoxDataSet(mDataSets, mActivity.getColor(R.color.blue));
        createBoxDataSet(mDataSets, mActivity.getColor(R.color.red));
        createBoxDataSet(mDataSets, mActivity.getColor(R.color.blue));
        createBoxDataSet(mDataSets, mActivity.getColor(R.color.red));
        createBoxDataSet(mDataSets, mActivity.getColor(R.color.blue));
        createBoxDataSet(mDataSets, mActivity.getColor(R.color.red));


        for (int i = 0; i < GATE_BOX_MAX_COUNT; ++i) {
            createBorderDataSet(mActivity.getColor(R.color.gate_box));
        }
    }

    private void createLineDataset() {

        ArrayList<Entry> values = new ArrayList<>();

        LineDataSet d = new LineDataSet(values, "Gate");
//            d.setLabel("Data");

        d.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.setLineWidth(1f);
        d.setCircleRadius(1f);
        d.setCircleHoleRadius(1f);
        d.setFillAlpha(0);
        d.setValueTextSize(9f);
        d.setHighlightLineWidth(1.5f);

        d.setDrawCircles(false);
        d.setDrawCircles(false);
        d.setDrawValues(false);
        d.setDrawIcons(false);

        int color = mActivity.getColor(R.color.trace1);

        d.setColor(color);
        d.setCircleColor(color);
        d.setFillColor(color);
        d.setHighLightColor(Color.rgb(244, 117, 117));
        d.setValueTextColor(Color.WHITE);

        mDataSets.add(d);

    }

    /*
     *
     * gate box
     * */

    public void removeGateBox() {

        //gateViewChart.getLineData().getDataSetByIndex(GATE_BOX_INDEX).clear();
        for (int i = GATE_BOX_INDEX; i < GATE_BOX_INDEX + GATE_BOX_MAX_COUNT; ++i) {
            gateLineChart.getLineData().getDataSetByIndex(i).clear();
        }
        invalidate();

    }

    public void removeGateOffsetBox() {
        //org
        /*for (int i = GATE_BOX_OFFSET_IDX; i < GATE_BOX_OFFSET_IDX + LINK_BOX_MAX_COUNT; i++) {
            gateLineChart.getLineData().getDataSetByIndex(i).clear();
        }*/
        //org

        //@@ [22.01.05] Sweep Gate Internal gate box fix
        /*for (int i = GATE_BOX_OFFSET_IDX; i < GATE_BOX_OFFSET_IDX + LINK_BOX_MAX_COUNT; i++) {
            gateLineChart.getLineData().getDataSetByIndex(i).clear();
        }*/
        //@@


        for (int i = 0; i < LINK_BOX_MAX_COUNT; ++i) {
            gateLinkChart.getLineData().getDataSetByIndex(i).clear();
        }

        gateLinkChart.notifyDataSetChanged();
        gateLinkChart.postInvalidate();

        //invalidate();
    }

    public void updateGateOffsetBox() {

        SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();

        boolean isNotViewLink = (gateData.getGateView() == SaGateData.GATE_MODE.OFF || gateData.getGateSource() == SaGateData.GATE_SOURCE.INTERNAL);
        new Handler(Looper.getMainLooper()).post(() -> {
            binding.lineChartLayout.gateLayout.layerLinkChartLabel.setVisibility(isNotViewLink ? View.INVISIBLE : View.VISIBLE);
        });

        if (isNotViewLink) {
            removeGateOffsetBox();
            return;
        }

        for (int i = 0; i < LINK_BOX_MAX_COUNT; ++i) {
            gateLinkChart.getLineData().getDataSetByIndex(i).clear();
        }

//        LineData data = gateViewChart.getLineData();
//        float height = gateViewChart.getAxisLeft().getAxisMaximum();
//        float offsetY = gateViewChart.getAxisLeft().getAxisMinimum();
        float space = (gateLineChart.getXAxis().getAxisMaximum() - gateLineChart.getXAxis().getAxisMinimum()) / 10000f;

        //int endPoint = 2 * 4;
        int idx = 0;

        for (int i = 0; i < LINK_BOX_MAX_COUNT; i += 2) {

            GateBoxOffsetData offsetBoxData = gateData.getGateBoxOffsetList().get(idx);
            idx++;

            float blueBoxLength = offsetBoxData.getBlueBoxLength();
            float redBoxLength = offsetBoxData.getRedBoxLength();

            float blueBoxOffset = offsetBoxData.getBlueBoxOffset();
            float redBoxOffset = offsetBoxData.getRedBoxOffset();

            float blueBoxEndX = blueBoxOffset + blueBoxLength;
            float redBoxEndX = redBoxOffset + redBoxLength;

//            InitActivity.logMsg("GateBox", "Gate Box Update " + offsetX + " " + endX + " " + offsetY + " " + height);

           /* Log.e("GateView", "EndX : " + blueBoxEndX + ", " + redBoxEndX);
            Log.e("GateView", "space : " + space);*/

            if (blueBoxEndX >= getStopX()) {
                blueBoxEndX = getStopX() - space;
            }

            if (redBoxEndX >= getStopX()) {
                redBoxEndX = getStopX() - space;
            }

            //Log.e("GateVIew", "Res : " + blueBoxEndX + ", " + redBoxEndX);

//            if (offsetX < getTouchingPointX() || offsetX > getStopX()) return;

//            data.addEntry(new Entry(blueBoxOffset, offsetY), GATE_BOX_OFFSET_IDX + i);
//            data.addEntry(new Entry(blueBoxOffset, height), GATE_BOX_OFFSET_IDX + i);
//            data.addEntry(new Entry(blueBoxEndX, height), GATE_BOX_OFFSET_IDX + i);
//            data.addEntry(new Entry(blueBoxEndX, offsetY), GATE_BOX_OFFSET_IDX + i);
//            data.addEntry(new Entry(blueBoxOffset, offsetY), GATE_BOX_OFFSET_IDX + i);
//
//            data.addEntry(new Entry(redBoxOffset, offsetY), GATE_BOX_OFFSET_IDX + i + 1);
//            data.addEntry(new Entry(redBoxOffset, height), GATE_BOX_OFFSET_IDX + i + 1);
//            data.addEntry(new Entry(redBoxEndX, height), GATE_BOX_OFFSET_IDX + i + 1);
//            data.addEntry(new Entry(redBoxEndX, offsetY), GATE_BOX_OFFSET_IDX + i + 1);
//            data.addEntry(new Entry(redBoxOffset, offsetY), GATE_BOX_OFFSET_IDX + i + 1);

            gateLinkChart.getLineData().addEntry(new Entry(blueBoxOffset, 1), i);
            gateLinkChart.getLineData().addEntry(new Entry(blueBoxEndX, 1), i);

            gateLinkChart.getLineData().addEntry(new Entry(redBoxOffset, 1), i + 1);
            gateLinkChart.getLineData().addEntry(new Entry(redBoxEndX, 1), i + 1);

//            InitActivity.logMsg("GateOffsetBox", (GATE_BOX_OFFSET_IDX + i) + " " + mDataSets.size());
        }

//        data.notifyDataChanged();

        //gateLinkChart.getLineData().notifyDataChanged();
        gateLinkChart.notifyDataSetChanged();
        gateLinkChart.postInvalidate();

//        invalidate();
//        InitActivity.logMsg("GateBox", "Gate Box Update");

    }

    private void updateGateBox() {

        SaGateData gateData = SaDataHandler
                .getInstance()
                .getConfigData()
                .getSweepTimeData().getGateData();

        if (gateData.getGateView() == SaGateData.GATE_MODE.OFF) {
            removeGateBox();
            return;
        }

        LineData data = gateLineChart.getLineData();

        // [jigum] 2021-07-21 Multiple Gate Box
        // 기존 정보 삭제
        for (int i = GATE_BOX_INDEX; i < GATE_BOX_INDEX + GATE_BOX_MAX_COUNT; ++i) {
            data.getDataSetByIndex(i).clear();
        }

        int gateNum = gateData.getGateNum(); // 표출 개수
        float gateLength = gateData.getGateLength();
        float gateDelay = gateData.getGateDelay();
        float gateDelta = gateData.getGateDelta();

        float height = gateLineChart.getAxisLeft().getAxisMaximum();
        float offsetX;// = gateData.getGateDelay();

        float offsetY = gateLineChart.getAxisLeft().getAxisMinimum();
        float endX;// = offsetX + gateLength;

        boolean isSymbol = gateData.getGateType() == SaGateData.GATE_TYPE.Slot;
        float unit = gateLineChart.getXAxis().getAxisMaximum() / (28 * (gateData.getGateViewSweepTime() / 1000));
        //Log.d(TAG, "unit = " + unit);

        for (int i = 0; i < gateNum; ++i) {
            offsetX = gateDelay + (gateDelta * i);
            endX = offsetX + gateLength - 1; //TODO 임시 그래프 끝에 가면 안 그려지는 문제가 있음.

//            data.addEntry(new Entry(offsetX, offsetY), GATE_BOX_INDEX + i);
//            data.addEntry(new Entry(offsetX, height), GATE_BOX_INDEX + i);
//            data.addEntry(new Entry(endX, height), GATE_BOX_INDEX + i);
//            data.addEntry(new Entry(endX, offsetY), GATE_BOX_INDEX + i);
//            data.addEntry(new Entry(offsetX, offsetY), GATE_BOX_INDEX + i);

            LineDataSet dataSet = (LineDataSet) data.getDataSetByIndex(GATE_BOX_INDEX + i);

            dataSet.addEntry(new Entry(offsetX, offsetY));
            dataSet.addEntry(new Entry(offsetX, height));
            dataSet.addEntry(new Entry(endX, height));
            dataSet.addEntry(new Entry(endX, offsetY));
            dataSet.addEntry(new Entry(offsetX, offsetY));

            dataSet.setDrawGateSymbol(isSymbol);
            if (isSymbol) {
                //Gate Box의 위치 정보 표시
                // Gate Box의 위치 정보는 -- slot/-- symb 형태로 표시
                // Number of slot = floor(Gate Delay / 14) 으로 계산
                // Number of symbol = Gate Delay – 14 * Number of slot 으로 계산하여 표시
                // Ex) Gate Delay 가 53 symbol인 경우의 Gate Box 위치 정보
                //Number of slot = floor(53 / 14) = 3
                //Number of symbol = 53 – 14 * 3 = 11
                //따라서, Gate Box 위치 정보는 3 slot/11 symb 로 표시

                int idx = (int) (offsetX / unit);
                int slot = (int) Math.floor(idx / 14);
                int symbol = (int) (idx - (14 * slot));
                dataSet.setSymbolValue(slot + "slot/" + symbol + "symb");
            }
        }

//        data.getDataSets().get(GATE_BOX_INDEX).clear();
//        float gateLength = gateData.getGateLength();
//
//        float height = gateViewChart.getAxisLeft().getAxisMaximum();
//        float offsetX = gateData.getGateDelay();
//
//        float offsetY = gateViewChart.getAxisLeft().getAxisMinimum();
//        float endX = offsetX + gateLength;
//
//        InitActivity.logMsg("GateBox", "Gate Box Update " + offsetX + " " + endX + " " + offsetY + " " + height);
//
//        if (endX >= getStopX()) {
//            Float space = (gateViewChart.getXAxis().getAxisMaximum() - gateViewChart.getXAxis().getAxisMinimum()) / 10000f;
//            endX = getStopX() - space;
//        } else if (endX < getTouchingPointX())
//            endX = getTouchingPointX();
//        if (offsetX < getTouchingPointX() || offsetX > getStopX())
//            return;
//
//        data.addEntry(new Entry(offsetX, offsetY), GATE_BOX_INDEX);
//        data.addEntry(new Entry(offsetX, height), GATE_BOX_INDEX);
//        data.addEntry(new Entry(endX, height), GATE_BOX_INDEX);
//        data.addEntry(new Entry(endX, offsetY), GATE_BOX_INDEX);
//        data.addEntry(new Entry(offsetX, offsetY), GATE_BOX_INDEX);

        data.notifyDataChanged();
        //invalidate();

        gateLinkChart.notifyDataSetChanged();
        gateLinkChart.postInvalidate();

        InitActivity.logMsg("GateBox", "Gate Box Update");
    }


    public LineDataSet getDataset(int idx) {

        return (LineDataSet) gateLineChart.getData().getDataSetByIndex(idx);

    }


    /* Set Freq */

    public float getWidth() {

        float width = getStopX() - getTouchingPointX();
        return width;

    }


    public boolean setStartX(float freq) {

        float start = freq;
        float center = start + (mStopFreq - start) / 2;
        float span = mStopFreq - start;

        mStartX = start;
        mCenterX = center;

        gateLineChart.getXAxis().setAxisMinimum(mStartX);
        gateLinkChart.getXAxis().setAxisMinimum(mStartX);

        refreshAllData();

        return true;

    }

    public float getTouchingPointX() {
        return mStartX;
    }

    public boolean setOffset(float offset) {

        Offset = offset;

        gateLineChart.getAxisLeft().setAxisMaximum(MAX_Y + Offset + RefLev);
        gateLineChart.getAxisLeft().setAxisMinimum(MIN_Y + Offset + RefLev);
        //gateLinkChart.getAxisLeft().setAxisMaximum(MAX_Y + Offset + RefLev);
        //gateLinkChart.getAxisLeft().setAxisMinimum(MIN_Y + Offset + RefLev);

        updateGateBox();
        updateGateOffsetBox();

        return true;
    }

    public boolean setRefLev(float refLev) {

        if (refLev > 100) {
            refLev = 100;
        } else if (refLev < -100) {
            refLev = -100;
        }

        RefLev = refLev;

        gateLineChart.getAxisLeft().setAxisMaximum(MAX_Y + Offset + RefLev);
        gateLineChart.getAxisLeft().setAxisMinimum(MIN_Y + Offset + RefLev);
        //gateLinkChart.getAxisLeft().setAxisMaximum(MAX_Y + Offset + RefLev);
        //gateLinkChart.getAxisLeft().setAxisMinimum(MIN_Y + Offset + RefLev);

        updateGateBox();
        updateGateOffsetBox();

        return true;
    }


    public boolean setStopX(float freq) {

        float stop = freq;
        float center = mStartX + (stop - mStartX) / 2;

        mStopFreq = stop;
        mCenterX = center;
        gateLineChart.getXAxis().setAxisMaximum(mStopFreq);
        gateLinkChart.getXAxis().setAxisMaximum(mStopFreq);
        //gateLinkChart.notifyDataSetChanged();

        //refreshAllData();
        return true;

    }

    public float getStopX() {
        return mStopFreq;
    }


    /* Set LineChartFunc Data */

    @SuppressLint("ResourceType")
    public void addEntry() {

        //mActivity.runOnUiThread(() -> {

        try {

            LineData data = gateLineChart.getLineData();

            // 차트 데이터 초기화
            data.clearValuesNotChanging(0);

            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
            SaConfigData saConfigData = SaDataHandler.getInstance().getConfigData();

            InitActivity.logMsg("addEntryForGate", mDataList.size() + "");
            float space = (gateLineChart.getXAxis().getAxisMaximum() - gateLineChart.getXAxis().getAxisMinimum()) / (mDataList.size() - 1);

            float xVal = gateLineChart.getXAxis().getAxisMinimum();


            if (D) {
                if (mDataList.size() > 9) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 10; ++i) {
                        sb.append(mDataList.get(i)).append(",");
                    }
                    Log.d(TAG, sb.toString());
                }
            }

            if (mode == MeasureMode.MEASURE_MODE.SA
                    && saConfigData.getTraceData().getDetector(0) == TraceEnumData.DETECTOR.NORMAL
                    && mDataList.size() == 2002) {

                space = (gateLineChart.getXAxis().getAxisMaximum() - gateLineChart.getXAxis().getAxisMinimum()) / (1001 - 1);

                for (int i = 0; i < mDataList.size(); i += 2) {
                    //divided by 1000 or 100
                    float yValMin = mDataList.get(i);
                    float yValMax = mDataList.get(i + 1);

                    data.addEntry(new Entry(xVal, yValMin), 0);
                    data.addEntry(new Entry(xVal, yValMax), 0);

                    xVal += space;
                }
            } else {
                for (int i = 0; i < mDataList.size(); i++) {
                    //divided by 1000 or 100
                    float yVal = mDataList.get(i);
//                    float yVal = (float) listOfNum.get(i).intValue();

                    data.addEntry(new Entry(xVal, yVal), 0);

                    xVal += space;
                }
            }

//                    InitActivity.logMsg("GateList", log);

            //                data.notifyDataChanged();
//                // let the chart know it's data has changed
//                gateLineChart.moveViewToX(data.getEntryCount());
            gateLineChart.notifyDataSetChanged();
            gateLineChart.postInvalidate(); // gateLineChart.invalidate();

//                gateLinkChart.moveViewToX(data.getEntryCount());
//                gateLinkChart.notifyDataSetChanged();
//                gateLinkChart.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //});
    }

    @SuppressLint("SetTextI18n")
    public void update() {

        setStopX(
                SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData().getGateViewSweepTime()
        );

        //Log.e("GateSweepTime", "setStopX : " + SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData().getGateViewSweepTime());

        invalidate();
        updateGateBox();
        updateGateOffsetBox();
        //invalidate();
    }

    public ArrayList<Float> getDataList() {
        return mDataList;
    }


    public void invalidate() {

        new Handler(Looper.getMainLooper()).post(() -> {

//            gateLineChart.notifyDataSetChanged();
//            gateLineChart.invalidate();

//            gateLinkChart.notifyDataSetChanged();
//            gateLinkChart.invalidate();
            refreshAllData();

        });

    }


    public void clearAllValues() {
        gateLineChart.getData().getDataSetByIndex(0).clear();
        gateLineChart.postInvalidate();
    }

    public void clearValues(int idx) {
        if (idx < 0 || idx >= gateLineChart.getData().getDataSets().size())
            return;

        gateLineChart.getData().clearValues(idx);
        gateLineChart.postInvalidate();
    }

    // TODO 왜 하는 것일까??
    public void refreshAllData() {

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

        if (mode != MeasureMode.MEASURE_MODE.SA) return;

        SaConfigData configData = SaDataHandler.getInstance().getConfigData();

        if (configData.getSweepTimeData().getGateData().getGateView() == SaGateData.GATE_MODE.OFF)
            return;

        new Thread(() -> {

            new Handler(Looper.getMainLooper()).post(() -> {

                clearAllValues();
                addEntry();
                gateLineChart.notifyDataSetChanged();
                gateLineChart.postInvalidate();
            });

        }).start();

    }


    public int getSizeOfDataset() {

        int cnt = gateLineChart.getData().getDataSets().size();
        return cnt;
    }

    public void autoScale() throws NullPointerException {

        if (gateLineChart.getData().getDataSetByIndex(0).getEntryCount() == 0)
            return;

        mActivity.runOnUiThread(() -> {

            try {

                Float maxY = getChartDataYMax();
                Float minY = getChartDataYMin();

                if (maxY == null || minY == null) {
                    return;
                }
                if (Float.isNaN(maxY) || Float.isNaN(minY)) {
                    return;
                }

                float maxRangeY = 0;
                float minRangeY = 0;

//            InitActivity.logMsg("Max Y", maxY + "");
//            InitActivity.logMsg("MaxRangeY", maxRangeY + "");

                Float middle = (maxY + minY) / 2;
                float refLev = middle + 80;


                if (Float.isNaN(maxRangeY) || Float.isNaN(minRangeY)) return;

                setRefLev((float) ((int) refLev));

                InitActivity.logMsg("autoScale", maxY + " " + minY);

//        binding.lineChartLayout.mainLineChart.getData().notifyDataChanged();
                gateLineChart.notifyDataSetChanged();

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
//            refreshAllData();

        });

    }

}

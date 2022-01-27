package com.dabinsystems.pact_app.Function.Chart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrCarrierSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrOffsetSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.FailSourceData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SemMeasTypeData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SideData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.InterferenceHunting.InterferenceMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemRefChannelData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SpuriousEmission.FreqRangeTableData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SpuriousEmission.SpuriousEmissionMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.SAFrequencyData;
import com.dabinsystems.pact_app.Data.SA.SaConfigData;
import com.dabinsystems.pact_app.Data.VSWR.SweepData.DATA_POINT;
import com.dabinsystems.pact_app.Data.VSWR.VswrConfigData;
import com.dabinsystems.pact_app.Data.VSWR.VswrFrequencyData;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.Marker.CustomMarkerView;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;

import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainLineChartFunc {
    private final String TAG = "MainLineChartFunc";
    private final boolean D = false;

    private ActivityMainBinding binding;
    private InitActivity mActivity;
    private final LineChart mainLineChart;

    private final int LIMIT_TOUCH_RANGE = 100;

    private Boolean isInit = false;
    private Boolean isSkipFirstData = false;

    ArrayList<ILineDataSet> mDataSets;
    private final static int mChartDataSetIndex = 0;
    public final int[] RECALL_DATASET_IDX = {4, 5, 6, 7};

    /* =========== new variable ============= */

    private ArrayList<Integer>[] mDataList = new ArrayList[8];

    private int[] mSelectedMarkerTrace;
    private int[] mRefMarkIndex = {1, 2, 3, 4, 1};
    private double[] mMarkerFreq = {0d, 0d, 0d, 0d, 0d};

    private Boolean[] isDatasetOnDisplay;
    private Boolean[] isNormal;
    private Boolean[] isFixed;
    private Boolean[] isDelta;

    private float mCenterX;
    private float mStartX;
    private float mEndX;
    private float mSpan;

    private float mScaleDiv = 10; // 0.1 ~ 20 dB Vertical axis space
    private float mOffset = 0; // -100 ~ +100 dBm // mRefLev = mRefLev + Offset
    private float mRefLev = 0; // -100 ~ +100 dBm // mRefLev = mRefLev + Offset

    private float mMaxRefLev = 0f;
    private float mMinRefLev = -100f;

    private Float mAverage = 0f;

    public final int MAX_MARKER_SIZE = 5;
    public final int MAX_MARKER_TRACE_SIZE = 5;
    public final int MAX_TRACE_SIZE = 4;

    /*ACLR*/
    public final int MAX_OFFSET_NUM = 5;
    public final int MAX_CARRIER_NUM = 2;

    public final int CARRIER_INNER_BOX_DATASET_INDEX = 9;
    public final int CARRIER_OUTER_BOX_DATASET_INDEX = 11;

    public final int OFFSET_POS_INNER_DATASET_INDEX = 13;
    public final int OFFSET_POS_OUTER_DATASET_INDEX = 18;
    public final int OFFSET_NEG_INNER_DATASET_INDEX = 23;
    public final int OFFSET_NEG_OUTER_DATASET_INDEX = 28;

    public final int OFFSET_POS_ABS_LIMIT_DATASET_INDEX = 33;
    public final int OFFSET_POS_REL_LIMIT_DATASET_INDEX = 38;
    public final int OFFSET_NEG_ABS_LIMIT_DATASET_INDEX = 43;
    public final int OFFSET_NEG_REL_LIMIT_DATASET_INDEX = 48;

    /*SEM*/
    public final int MAX_MASK_SIZE = 4;

    public final int SEM_POS_BOX_DATASET_INDEX = 53;
    public final int SEM_NEG_BOX_DATASET_INDEX = 57;

    public final int SEM_POS_ABS_LIMIT_DATASET_INDEX = 61;
    public final int SEM_NEG_ABS_LIMIT_DATASET_INDEX = 65;

    public final int SEM_POS_REL_LIMIT_DATASET_INDEX = 69;
    public final int SEM_NEG_REL_LIMIT_DATASET_INDEX = 73;
    public final int SEM_RELATIVE_LINE_INDEX = 77;

    public final int SPURIOUS_EMISSION_INDEX = 81;
    public final int SPURIOUS_EMISSION_DATASET_COUNT = 12;

    public final int INTERFERENCE_HUNTING = 91;
    public final int INTERFERENCE_HUNTING_COUNT = 2; // 그리는 박스는 두개까지만

    public final int NUMBER_OF_GRID_LINE = 11;

    private CustomMarkerView mMarker;

    /*============= Limit Line ===============*/

    public final int LIMIT_DATASET_INDEX = 8;

    private float mLimitValue;
    private boolean isSelectLimitLine = false;

    private Boolean isEnabledLimit = false;
    private Boolean isFail = false;

    private String mLabel = "U";

    private SoundPool soundPool; // only low size file
    private MediaPlayer player;

    private Boolean isUpper = true;
    private Boolean isOnAlarm = false;
    private Boolean isPassFailEnabled = false;
    private Boolean isPlayingAlarm = false;
    private Boolean isDrawingChart = false;

    private LineDataSet mLimitDataSet;

    public MainLineChartFunc(Activity activity, ActivityMainBinding binding) {

        mActivity = (MainActivity) activity;
        this.binding = binding;
        mainLineChart = binding.lineChartLayout.mainLineChart;

        initValues();
        addEvents();

    }

    public void initValues() {

        if (isInit) return;

        isInit = true;

        for (int i = 0; i < mDataList.length; i++) {

            mDataList[i] = new ArrayList<>();
        }

        mSelectedMarkerTrace = new int[MAX_MARKER_TRACE_SIZE];
        Arrays.fill(mSelectedMarkerTrace, 0);

        isDatasetOnDisplay = new Boolean[MAX_TRACE_SIZE];
        Arrays.fill(isDatasetOnDisplay, false);

        isNormal = new Boolean[MAX_MARKER_SIZE];
        Arrays.fill(isNormal, true);

        isFixed = new Boolean[MAX_MARKER_SIZE];
        Arrays.fill(isFixed, false);

        isDelta = new Boolean[MAX_MARKER_SIZE];
        Arrays.fill(isDelta, false);

        mRefMarkIndex = new int[MAX_MARKER_SIZE];
        Arrays.fill(mRefMarkIndex, -1);

        mMarker = new CustomMarkerView(mActivity, R.layout.custom_normal_marker, R.layout.custom_select_marker, R.layout.custom_fixed_marker, R.layout.custom_sem_marker);
        mMarker.setChartView(mainLineChart);
        mainLineChart.setMarker(mMarker);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        player = MediaPlayer.create(mActivity, R.raw.beep_effect);

        mCenterX = 500f;
        mStartX = 0f; // 400 ~ 4200
        mEndX = 1000f; // 400 ~ 4200
        mSpan = 1000f; // 10 Hz ~ 3.8 GHz
//
//        if (SplashActivity.getAppMode() == AppMode.PACT) {
//
//            mCenterX = 64f;
//            mStartX = 0f; // 400 ~ 4200
//            mEndX = 129f; // 400 ~ 4200
//            mSpan = 129f; // 10 Hz ~ 3.8 GHz
//
//        }

        mLimitValue = 1.5f;

    }

    public void initChart() throws NullPointerException {

        new Thread(() -> {

            //mainLineChart.setDrawingCacheEnabled(true);

//        mActivity.requestPermission();

//            mainLineChart.setLogEnabled(true);

            // get the legend (only possible after setting mData)
            Legend l = mainLineChart.getLegend();

            // modify the legend ...
            //왼쪽 하단 그래프 설명
//        l.setForm(Legend.LegendForm.LINE);
            l.setTextColor(Color.WHITE);
            LineChart chart = mainLineChart;
            CustomLineChartRenderer renderer = new CustomLineChartRenderer(chart, chart.getAnimator(), chart.getViewPortHandler());
            chart.setRenderer(renderer);

            mainLineChart.setOnChartValueSelectedListener(mActivity);
            mainLineChart.getAxisLeft().setAxisMaximum(mMaxRefLev);
            mainLineChart.getAxisLeft().setAxisMinimum(mMinRefLev);
            mainLineChart.getXAxis().setAxisMinimum(mStartX);
            mainLineChart.getXAxis().setAxisMaximum(mEndX);

            //bottom - left data box color
            mainLineChart.getLegend().setEnabled(false);
            mainLineChart.getDescription().setEnabled(false);//그래프 오른쪽 하단의 설명 유무( 마커랑 관계 X ) // enable description text
            mainLineChart.setTouchEnabled(true); //false일 경우 화면 터치로 그래프를 움직일 수 없음 ( 마커 찍는 것도 불가능) // enable touch gestures
            mainLineChart.setDragEnabled(true); // enable scaling and dragging
            mainLineChart.setScaleEnabled(false);
            mainLineChart.setDoubleTapToZoomEnabled(false);
            mainLineChart.setPinchZoom(true);// if disabled, scaling can be done on x- and y-axis separately
            mainLineChart.setDrawBorders(true);

            mainLineChart.post(() -> {
                mainLineChart.setBackgroundColor(Color.BLACK); // set an alternative background color
            });

            mainLineChart.getAxisLeft().setDrawAxisLine(false);
            mainLineChart.setDrawBorders(false);
            mainLineChart.setBorderWidth(2);
            mainLineChart.setBorderColor(Color.WHITE);


            float offsetBottom = mainLineChart.getViewPortHandler().offsetBottom();
            float offsetTop = mainLineChart.getViewPortHandler().offsetTop();
            float offsetLeft = mainLineChart.getViewPortHandler().offsetLeft();
            float offsetRight = mainLineChart.getViewPortHandler().offsetRight();

            if (D)
                Log.d("MainLineChart", "Bottom : " + offsetBottom + " Top : " + offsetTop + " Left : " + offsetLeft + " Right : " + offsetRight);

            mainLineChart.setExtraLeftOffset(offsetLeft + 15);
            mainLineChart.setExtraBottomOffset(offsetBottom + 5);
//        mainLineChart.setExtraRightOffset(convertDpToPixel(5, mActivity));


            //X축 값 텍스트
            XAxis xl = mainLineChart.getXAxis();
            xl.setTextColor(Color.WHITE);
            xl.setTextSize(13f);
//        xl.setCenterAxisLabels(true);
            xl.setAvoidFirstLastClipping(false);
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            xl.setYOffset(10);
            xl.setEnabled(true);

            //왼쪽 Y값 텍스트
            YAxis leftAxis = mainLineChart.getAxisLeft();
//        leftAxis.setTextColor(Color.BLACK);
            leftAxis.setTextColor(Color.WHITE);
//        leftAxis.setDrawLabels(false);
//        leftAxis.
//        leftAxis.setTextSize(convertDpToPixel(10, mActivity));
            leftAxis.setTextSize(13f);
            leftAxis.setXOffset(0);
            leftAxis.setYOffset(0);
            leftAxis.setLabelCount(NUMBER_OF_GRID_LINE, true);
            leftAxis.setDrawGridLines(true);
            leftAxis.setGridColor(mActivity.getResources().getColor(R.color.grid_line_color));
            leftAxis.setGridLineWidth(1f);

            leftAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {

                    MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                    MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                    float val = (float) Double.parseDouble(Utils.formatNumber(value, 2, false));
                    boolean isInverted = mainLineChart.getAxisLeft().isInverted();
                    float topVal = (float) Double.parseDouble(Utils.formatNumber(mainLineChart.getAxisLeft().mAxisMaximum, 2, false));
                    if (isInverted)
                        topVal = (float) Double.parseDouble(Utils.formatNumber(mainLineChart.getAxisLeft().mAxisMinimum, 2, false));

                    String formattedValue = super.getFormattedValue(val);
                    if (mode == MeasureMode.MEASURE_MODE.SA && val == topVal) {
                        formattedValue = "(dBm) " + formattedValue;
                    } else if (mode == MeasureMode.MEASURE_MODE.VSWR && type == MeasureType.MEASURE_TYPE.RL && val == topVal) {
                        formattedValue = "(dB) " + formattedValue;
                    } else if (mode == MeasureMode.MEASURE_MODE.DTF && type == MeasureType.MEASURE_TYPE.DTF_RL && val == topVal) {
                        formattedValue = "(dB) " + formattedValue;
                    } else if (mode == MeasureMode.MEASURE_MODE.CL && val == topVal) {
                        formattedValue = "(dB) " + formattedValue;
                    }

                    return formattedValue;


                }
            });

            //격자 설정
            xl.setDrawGridLines(true);
            xl.setGridColor(mActivity.getResources().getColor(R.color.grid_line_color));
            xl.setGridLineWidth(1f);
            xl.setLabelCount(NUMBER_OF_GRID_LINE, true);
            xl.setAvoidFirstLastClipping(true);
            xl.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {

                    XAxis axis = mainLineChart.getXAxis();
                    float chartStart = axis.getAxisMinimum();
                    float chartStop = axis.getAxisMaximum();
                    float chartCenter = (chartStart + chartStop) / 2f;

//                    if (D) Log.d("xLabel", "value : " + value + " start : " + chartStart + " stop : " + chartStop + " center : " + chartCenter);
//
//                    String strVal = Utils.formatNumber(value, 3, false);
//                    if(strVal.length() > 7) strVal = value + "";
//                    float val = Float.parseFloat(strVal);
//
//                    String strStart = Utils.formatNumber(chartStart, 3, false);
//                    if(strStart.length() > 7) strStart = chartStart + "";
//                    float start = Float.parseFloat(strStart);
//
//                    String strStop = Utils.formatNumber(chartStop, 3, false);
//                    if(strStop.length() > 7) strStop = chartStop + "";
//                    float stop = Float.parseFloat(strStop);
//
//                    String strCenter = Utils.formatNumber(chartCenter, 3, false);
//                    if(strCenter.length() > 7) strCenter = chartCenter + "";
//                    float center = Float.parseFloat(strCenter);

                    MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                    MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                    double start;
                    double center;
                    double end;

                    if (mode == MeasureMode.MEASURE_MODE.SA) {

                        SaConfigData configData = SaDataHandler.getInstance().getConfigData();
                        SAFrequencyData frequencyData = configData.getFrequencyData();

                        if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {

                            start = 0;
                            center = 5;
                            end = 10;

                        } else if (type == MeasureType.MEASURE_TYPE.SPURIOUS_EMISSION) {

                            start = configData.getSpuriousEmissionData().getMinStartFreq();
                            if (D) Log.d("ValueFormatter", start + "");
                            end = configData.getSpuriousEmissionData().getMaxStopFreq();
                            center = (start + end) / 2;

                        } else if (frequencyData.getSpan() == 0) {

                            start = 0;
                            end = configData.getSweepTimeData().getSweepTimeToMs();
                            center = end / 2;

                        } else {

                            start = frequencyData.getStartFreq();
                            start = Math.round(start * 1000000d) / 1000000d;

                            center = frequencyData.getCenterFreq();
                            center = Math.round(center * 1000000d) / 1000000d;

                            end = frequencyData.getStopFreq();
                            end = Math.round(end * 1000000d) / 1000000d;

                        }

                    } else {

                        VswrConfigData configData = VswrDataHandler.getInstance().getConfigData();
                        VswrFrequencyData frequencyData = configData.getFrequencyData();

                        if (mode == MeasureMode.MEASURE_MODE.DTF) {

                            start = 0;
                            end = configData.getDistance();
                            center = end / 2;

                        } else {

                            start = frequencyData.getStartFreq();
                            center = frequencyData.getCenterFreq();
                            end = frequencyData.getStopFreq();
                        }

                    }

                    if (value == chartStart) {

                        return start + "";
                    } else if (value == chartStop) {


                        String stopValue = end + "";
                        if (mode == MeasureMode.MEASURE_MODE.SA && type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK)
                            stopValue += " (ms)";
                        else if (mode == MeasureMode.MEASURE_MODE.SA) {

                            double span = SaDataHandler.getInstance().getConfigData().getFrequencyData().getSpan();
                            if (span == 0f)
                                stopValue += " (mSec)";
                            else
                                stopValue += " (MHz)";
                        } else if (mode == MeasureMode.MEASURE_MODE.VSWR && type == MeasureType.MEASURE_TYPE.VSWR) {
                            stopValue += " (MHz)";
                        } else if (mode == MeasureMode.MEASURE_MODE.VSWR && type == MeasureType.MEASURE_TYPE.RL) {
                            stopValue += " (MHz)";
                        } else if (mode == MeasureMode.MEASURE_MODE.DTF && type == MeasureType.MEASURE_TYPE.DTF_VSWR) {
                            stopValue += " (m)";
                        } else if (mode == MeasureMode.MEASURE_MODE.DTF && type == MeasureType.MEASURE_TYPE.DTF_RL) {
                            stopValue += " (m)";
                        } else if (mode == MeasureMode.MEASURE_MODE.CL) {
                            stopValue += " (MHz)";
                        }

                        return stopValue;
                    } else if (value == chartCenter) return center + "";
                    else return "";
                }
            });

            //

            YAxis rightAxis = mainLineChart.getAxisRight();
            rightAxis.setEnabled(false);
            initDataset();

            mainLineChart.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

            LineData data = new LineData(mDataSets);
            mainLineChart.setData(data);
            FunctionHandler.getInstance().getMainLineChart().invalidate();

            if (D)
                Log.d("MainLineChart", "dataset count : " + mainLineChart.getLineData().getDataSetCount());
            if (D)
                Log.d("inner box cound", mainLineChart.getData().getDataSetByIndex(CARRIER_INNER_BOX_DATASET_INDEX).getEntryCount() + "");

            ViewHandler.getInstance().getContent().traceIconUpdate();
            ViewHandler.getInstance().getContent().subInfoUpdate();

        }).start();

    }

    @SuppressLint("ClickableViewAccessibility")
    public void addEvents() {

        mainLineChart.setOnChartGestureListener(new OnChartGestureListener() {

            float startY = 0f;
            float startX = 0f;
            float stopY = 0f;
            float stopX = 0f;
            float xRange = 300;

            boolean isGesture = false;

            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                if (mode != MeasureMode.MEASURE_MODE.SA) return;
                startY = me.getY();
                startX = me.getX();
                isGesture = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        isGesture = false;

                    }
                }, 1000);

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

                if (isGesture) {
                    stopY = me.getY();
                    stopX = me.getX();

                    float dragRangeY = stopY - startY;
                    float dragRangeX = stopX - startX;

                    if (Math.abs(dragRangeX) > xRange) {
                        if (D) Log.d("Gesture", "Cancel / dragRange : " + dragRangeX);
                        return;
                    }

                    if (dragRangeY < 0 && Math.abs(dragRangeY) >= xRange) {
                        if (D) Log.d("Gesture", "up gesture / dragRange : " + dragRangeY);
                        float lev = getRefLev() - getScaleDiv();
                        setRefLev(lev);
                        FunctionHandler.getInstance().getGateLineChart().setRefLev(lev);
                    } else if (dragRangeY > 0 && Math.abs(dragRangeY) >= xRange) {
                        if (D) Log.d("Gesture", "down gesture / dragRange : " + dragRangeY);
                        float lev = getRefLev() + getScaleDiv();
                        setRefLev(lev);
                        FunctionHandler.getInstance().getGateLineChart().setRefLev(lev);
                    }

                    ViewHandler.getInstance().getSAAmplitudeView().update();
                    ViewHandler.getInstance().getContent().subInfoUpdate();
                }

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });

        mainLineChart.setOnTouchListener((v, event) -> {

            mainLineChart.getOnTouchListener().onTouch(v, event);

            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
            ViewHandler viewHandler = ViewHandler.getInstance();
            float limitValueToPx;
            float limitRange;

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    limitValueToPx = (float) mainLineChart.getPixelForValues(0, mLimitValue, YAxis.AxisDependency.LEFT).y;
                    limitRange = Math.abs(limitValueToPx - event.getY());

                    if (isEnabledLimitLine() && limitRange <= LIMIT_TOUCH_RANGE) {
                        isSelectLimitLine = true;
                        break;
                    } else isSelectLimitLine = false;

                    if (!isOnMarker()) return true;

                    if (D)
                        Log.d("MainChart", "minDistance : " + mainLineChart.getOnTouchListener().getMinDistanceIndex());
                    if (D)
                        Log.d("MainChart", "Selected index : " + mainLineChart.getOnTouchListener().getSelectedHighlightIndex());

                    if (mainLineChart.getOnTouchListener().getSelectedHighlightIndex() == -1 ||
                            mainLineChart.getOnTouchListener().getMinDistanceIndex() == -1) {
                        viewHandler.getSaMarkerView().update();
                        viewHandler.getMarkerSetupView().update();
                        viewHandler.getContent().markerValueUpdate();
                        return false;
                    }

                    if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF) {

                        viewHandler.getMarkerSetupView().addMenu();

                    } else if (mode.getValue() == MeasureMode.MEASURE_MODE.SA.getValue()) {

                        viewHandler.getSaMarkerView2().addMenu();

                    }

                    viewHandler.getContent().markerValueUpdate();
//                    viewHandler.getContent().markerTableUpdate();

                    break;

                case MotionEvent.ACTION_MOVE:

                    if (isSelectLimitLine) {

                        float limitValue = (float) mainLineChart.getValuesByTouchPoint(event.getX(), event.getY(), YAxis.AxisDependency.LEFT).y;
                        setLimitValue(limitValue);
                        VswrDataHandler.getInstance().getConfigData().getLimitData().setLimitValueByType(limitValue);
                        viewHandler.getLimitView().limitValueUpdate();

                    }

                    if (!isOnMarker()) return false;

                    if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL) {

                        new Thread(() -> {
                            viewHandler.getMarkerSetupView().update();
                        }).start();

                    } else if (mode.getValue() == MeasureMode.MEASURE_MODE.SA.getValue()) {

                        new Thread(() -> {

                            viewHandler.getSaMarkerView2().updateValue();
                            viewHandler.getSaMarkerView2().updateTrace();
                        }).start();

                    } else if (mode == MeasureMode.MEASURE_MODE.SG) {


                    }

                    viewHandler.getContent().markerValueUpdate();

                    break;

                case MotionEvent.ACTION_UP:

                    if (type == MeasureType.MEASURE_TYPE.INTERFERENCE_HUNTING) {
                        if (isSelectLimitLine) {
                            SaDataHandler.getInstance().getInterferenceHuntingConfigData().getInterferenceHuntingData().setThreshold(
                                    (double) mLimitValue
                            );

                            FunctionHandler.getInstance().getDataConnector().sendCommand(
                                    SaDataHandler.getInstance().getInterferenceHuntingConfigData().getSaParameter()
                            );
                        }

                    }

                    if (getSelectedMarkerIndex() == -1) break;
                    if (getMarker() == null) break;
                    mSelectedMarkerTrace[getSelectedMarkerIndex()] = getMarker().getDataSetIndex();

                    double span;
                    double start;
                    double end;

                    if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL) {

                        VswrConfigData data = VswrDataHandler.getInstance().getConfigData();
                        VswrFrequencyData freqData = data.getFrequencyData();

                        if (mode == MeasureMode.MEASURE_MODE.DTF) {

                            start = 0d;
                            end = data.getDistance();
                            span = end - start;

                        } else {

                            start = freqData.getStartFreq();
                            end = freqData.getStopFreq();
                            span = freqData.getSpan();
                        }

                    } else if (mode == MeasureMode.MEASURE_MODE.SA) {

                        SaConfigData data = SaDataHandler.getInstance().getConfigData();
                        SAFrequencyData freqData = data.getFrequencyData();

                        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

                            if (freqData.getSpan() == 0d) {

                                start = 0d;
                                end = data.getSweepTimeData().getSweepTimeToMs();
                                span = end;

                            } else {

                                start = freqData.getStartFreq();
                                end = freqData.getStopFreq();
                                span = freqData.getSpan();

                            }

                        } else if (type == MeasureType.MEASURE_TYPE.SPURIOUS_EMISSION) {

                            start = 0;
                            end = 10;
                            span = 10;

                        } else {
                            start = freqData.getStartFreq();
                            end = freqData.getStopFreq();
                            span = end - start;
                        }

                    } else {

                        start = 0;
                        end = 1000;
                        span = 1000;
                    }

                    Highlight marker = mainLineChart.getSelectedHighlight();
                    int mkrIdx = mainLineChart.getSelectedHighlightIndex();
                    double absMarkerFreq = (double) marker.getX();
                    double relFreq = start + ((span * absMarkerFreq) / mSpan);

                    mMarkerFreq[mkrIdx] = relFreq;

                    viewHandler.getContent().markerTableUpdate();

                    break;

            }

            return true;
        });

    }

    public void createBorderDataSet(int color) {

        ArrayList<Entry> values = new ArrayList<>();

        LineDataSet dataset = new LineDataSet(values, "");
//            d.setLabel("Data");

        dataset.setAxisDependency(YAxis.AxisDependency.LEFT);
//            dataset.setCubicIntensity(0f);
        dataset.setDrawFilled(false);
//            dataset.setFillAlpha();

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

    public void createBoxDataSet(int color) {

        ArrayList<Entry> values = new ArrayList<>();

        LineDataSet dataset = new LineDataSet(values, "");

        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataset.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset.setCubicIntensity(0f);
        dataset.setDrawFilled(true);
        dataset.setFillAlpha(50);

        dataset.setHighlightEnabled(false);
        dataset.setDrawCircles(false);
        dataset.setDrawCircleHole(false);
        dataset.setLineWidth(0f);
        dataset.setFormLineWidth(0f);

        dataset.setValueTextSize(9f);
        dataset.setHighlightLineWidth(0f);
        dataset.setDrawValues(false);
        dataset.setColor(color);
        dataset.setCircleColor(color);
        dataset.setFillColor(color);
        dataset.setHighLightColor(Color.rgb(244, 117, 117));
        dataset.setValueTextColor(Color.WHITE);

        mDataSets.add(dataset);

    }

    public Float getChartDataYMin() {
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        float divVal = 1000f;
        Float min = null;

        if (mode == MeasureMode.MEASURE_MODE.SA || mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY)
            divVal = 100f;

        for (int i = 0; i < MAX_TRACE_SIZE; i++) {

            ILineDataSet dataList = mainLineChart.getData().getDataSetByIndex(i);
            if (dataList == null || i > 0 && type != MeasureType.MEASURE_TYPE.SWEPT_SA || !getTrace(i).isVisible())
                continue;

            for (int j = 0; j < dataList.getEntryCount(); j++) {

                try {
                    float y = dataList.getEntryForIndex(j).getY();
                    if (min == null) min = y;
                    if (y > -150 && min > y)
                        min = y;
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }

        }

        if (min == null) return null;

        return min;
    }

    public Float getChartDataYMax() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        float divVal = 1000f;
        Float max = null;

        if (mode == MeasureMode.MEASURE_MODE.SA || mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY)
            divVal = 100f;

        for (int i = 0; i < MAX_TRACE_SIZE; i++) {

            ILineDataSet dataList = mainLineChart.getData().getDataSetByIndex(i);
            if (dataList == null || i > 0 && type != MeasureType.MEASURE_TYPE.SWEPT_SA) continue;

            for (int j = 0; j < dataList.getEntryCount(); j++) {
                try {
//                if (dataList.get(j) == -99999) continue;
                    if (max == null) max = dataList.getEntryForIndex(j).getY();
                    if (max < dataList.getEntryForIndex(j).getY())
                        max = dataList.getEntryForIndex(j).getY();
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    Log.e("ChartMaxY", "Null pointer Exception : + " + e);
                }

            }

        }

        if (max == null) return null;
//        Float result = (float) max / divVal;

        return max;
    }

    public static int getChartDataSetIndex() {
        return mChartDataSetIndex;
    }

    /* ======================== new function ============================ */

    private void initDataset() {

        if (mDataSets == null)
            mDataSets = new ArrayList<>();
        else return;

        int[] colors = new int[]{
                mActivity.getResources().getColor(R.color.trace1),
                mActivity.getResources().getColor(R.color.trace2),
                mActivity.getResources().getColor(R.color.trace3),
                mActivity.getResources().getColor(R.color.trace4),
                mActivity.getResources().getColor(R.color.recall_trace1),
                mActivity.getResources().getColor(R.color.recall_trace2),
                mActivity.getResources().getColor(R.color.recall_trace3),
                mActivity.getResources().getColor(R.color.recall_trace4),
                mActivity.getResources().getColor(R.color.green),

        };

        for (int z = 0; z < 9; z++) {

            ArrayList<Entry> values = new ArrayList<>();

            LineDataSet d = new LineDataSet(values, "DataSet " + (z + 1));
//            d.setLabel("Data");

            d.setAxisDependency(YAxis.AxisDependency.LEFT);

            if (z > 3) d.setHighlightEnabled(false);

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

            int color = colors[z];

            d.setColor(color);
            d.setCircleColor(color);
            d.setFillColor(color);
            d.setHighLightColor(Color.rgb(244, 117, 117));
            d.setValueTextColor(Color.WHITE);

            mDataSets.add(d);
        }

        /*
         * ========================== start sa dataset ( aclr ~ ) ===========================
         * */

        //carriers inner box
        for (int i = 0; i < MAX_CARRIER_NUM; i++)
            createBoxDataSet(mActivity.getResources().getColor(R.color.aclr_blue_box));

        //carriers outer box
        for (int i = 0; i < MAX_CARRIER_NUM; i++)
            createBorderDataSet(mActivity.getResources().getColor(R.color.aclr_box));

        /*offset setup box*/

        //offset pos inner box
        for (int i = 0; i < MAX_OFFSET_NUM; i++)
            createBoxDataSet(mActivity.getResources().getColor(R.color.aclr_blue_box));

        //offset pos outer box
        for (int i = 0; i < MAX_OFFSET_NUM; i++)
            createBorderDataSet(mActivity.getResources().getColor(R.color.aclr_box));


        //offset neg inner box
        for (int i = 0; i < MAX_OFFSET_NUM; i++)
            createBoxDataSet(mActivity.getResources().getColor(R.color.aclr_blue_box));

        //offset neg outer box
        for (int i = 0; i < MAX_OFFSET_NUM; i++)
            createBorderDataSet(mActivity.getResources().getColor(R.color.aclr_box));

        //aclr pos abs limit line
        for (int i = 0; i < MAX_OFFSET_NUM; i++)
            createLineDataSet(mActivity.getResources().getColor(R.color.aclr_abs_limit_line));

        //aclr pos rel limit line
        for (int i = 0; i < MAX_OFFSET_NUM; i++)
            createLineDataSet(mActivity.getResources().getColor(R.color.aclr_rel_limit_line));

        //aclr neg abs limit line
        for (int i = 0; i < MAX_OFFSET_NUM; i++)
            createLineDataSet(mActivity.getResources().getColor(R.color.aclr_abs_limit_line));

        //aclr neg rel limit line
        for (int i = 0; i < MAX_OFFSET_NUM; i++)
            createLineDataSet(mActivity.getResources().getColor(R.color.aclr_rel_limit_line));

        /*
         * ========================== start sa dataset ( sem ~ ) ===========================
         * */


        //SEM POS BOX
        for (int i = 0; i < MAX_MASK_SIZE; i++)
            createBoxDataSet(mActivity.getResources().getColor(R.color.colorWhite));

        //SEM NEG BOX
        for (int i = 0; i < MAX_MASK_SIZE; i++)
            createBoxDataSet(mActivity.getResources().getColor(R.color.colorWhite));

        //SEM ABS POS LIMIT
        for (int i = 0; i < MAX_MASK_SIZE; i++)
            createLineDataSet(mActivity.getResources().getColor(R.color.sem_abs_limit_line));

        //SEM ABS NEG LIMIT
        for (int i = 0; i < MAX_MASK_SIZE; i++)
            createLineDataSet(mActivity.getResources().getColor(R.color.sem_abs_limit_line));

        //SEM REL POS LIMIT
        for (int i = 0; i < MAX_MASK_SIZE; i++)
            createLineDataSet(mActivity.getResources().getColor(R.color.sem_rel_limit_line));

        //SEM REL NEG LIMIT
        for (int i = 0; i < MAX_MASK_SIZE; i++)
            createLineDataSet(mActivity.getResources().getColor(R.color.sem_rel_limit_line));

        createLineDataSet(mActivity.getResources().getColor(R.color.sem_rel_limit_line));
        createBoxDataSet(mActivity.getResources().getColor(R.color.occ_blue_box));

        for (int i = 0; i < SPURIOUS_EMISSION_DATASET_COUNT; i++) {
            createLineDataSet(mActivity.getResources().getColor(R.color.spurious_emission_dataset_color));
        }

        //INTERFERENCE HUNTING
        createBoxDataSet(mActivity.getResources().getColor(R.color.interference_uplink_box));
        createBoxDataSet(mActivity.getResources().getColor(R.color.interference_uplink_box));
    }

    public ILineDataSet getInnerBoxDataset(int idx) {

        ILineDataSet dataset = mainLineChart.getLineData().getDataSetByIndex(CARRIER_INNER_BOX_DATASET_INDEX + idx);
        return dataset;

    }

    /*
     * Occ Box
     * */
    public void updateOccBox() {
        updateOccBox(true);
    }

    public void updateOccBox(boolean isInvalidate) {

        try {

            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
            if (type != MeasureType.MEASURE_TYPE.OCCUPIED_BW) return;

            Integer lowIndex = SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getLowerPowerIndex();
            Integer upperIndex = SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getUpperPowerIndex();

            if (upperIndex == null || lowIndex == null) {
                setBlueZone(100d, isInvalidate);
                return;
            }

            LineChart chart = mainLineChart;
            Entry startEntry = chart.getLineData().getDataSetByIndex(0).getEntryForIndex(lowIndex);
            Entry stopEntry = chart.getLineData().getDataSetByIndex(0).getEntryForIndex(upperIndex);

            if (startEntry == null || stopEntry == null) return;

            double span = SaDataHandler.getInstance().getConfigData().getFrequencyData().getSpan();
            Double startFreq = (span * (double) startEntry.getX()) / mSpan;
            Double stopFreq = (span * (double) stopEntry.getX()) / mSpan;
            double width = stopFreq - startFreq;

            if (D)
                Log.d("updateOccBox", "startFreq : " + startFreq + " stopFreq : " + stopFreq + " width : " + (stopFreq - startFreq));

            if (startFreq == null || stopFreq == null) return;

            setBlueZone(width, isInvalidate);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void updateChannelBox() {
        updateChannelBox(true);
    }

    public void updateChannelBox(boolean isInvalidate) {

        try {

            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
            if (type != MeasureType.MEASURE_TYPE.CHANNEL_POWER)
                return;

            SaConfigData configData = SaDataHandler.getInstance().getChannelPowerConfigData();
            double integ = configData.getChannelPowerMeasSetupData().getIntegMHzVal();
            setBlueZone(integ, isInvalidate);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    /*
     *
     * aclr box
     * */

    public void setAclrLowerBoxColor(int color, int idx) {

        LineDataSet dataset = (LineDataSet) mainLineChart.getLineData().getDataSetByIndex(OFFSET_NEG_INNER_DATASET_INDEX + idx);
        if (dataset.getFillColor() != color)
            dataset.setFillColor(color);
        dataset.setColor(color);
//        invalidate();

    }

    public void setAclrUpperBoxColor(int color, int idx) {

        LineDataSet dataset = (LineDataSet) mainLineChart.getLineData().getDataSetByIndex(OFFSET_POS_INNER_DATASET_INDEX + idx);
        if (dataset.getFillColor() != color)
            dataset.setFillColor(color);
        dataset.setColor(color);
//        invalidate();

    }

    public void removeAclrBox() {

        for (int i = 0; i < MAX_CARRIER_NUM; i++) {

            mainLineChart.getLineData().getDataSetByIndex(CARRIER_INNER_BOX_DATASET_INDEX + i).clear();
            mainLineChart.getLineData().getDataSetByIndex(CARRIER_OUTER_BOX_DATASET_INDEX + i).clear();

        }

        for (int i = 0; i < MAX_OFFSET_NUM; i++) {

            mainLineChart.getLineData().getDataSetByIndex(OFFSET_NEG_INNER_DATASET_INDEX + i).clear();
            mainLineChart.getLineData().getDataSetByIndex(OFFSET_NEG_OUTER_DATASET_INDEX + i).clear();
            mainLineChart.getLineData().getDataSetByIndex(OFFSET_NEG_REL_LIMIT_DATASET_INDEX + i).clear();
            mainLineChart.getLineData().getDataSetByIndex(OFFSET_NEG_ABS_LIMIT_DATASET_INDEX + i).clear();

            mainLineChart.getLineData().getDataSetByIndex(OFFSET_POS_INNER_DATASET_INDEX + i).clear();
            mainLineChart.getLineData().getDataSetByIndex(OFFSET_POS_OUTER_DATASET_INDEX + i).clear();
            mainLineChart.getLineData().getDataSetByIndex(OFFSET_POS_REL_LIMIT_DATASET_INDEX + i).clear();
            mainLineChart.getLineData().getDataSetByIndex(OFFSET_POS_ABS_LIMIT_DATASET_INDEX + i).clear();

        }

        invalidate();

    }

    public void updateAclrBox() {
//        new Handler(Looper.getMainLooper()).post(() -> {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (type != MeasureType.MEASURE_TYPE.ACLR) {
            removeAclrBox();
            return;
        }

        calCarrierBox();
        calOffsetBox();
        createOffsetLimitLine();

//        });
    }

    public void calCarrierBox() {
//        new Thread(() -> {
//        new Handler(Looper.getMainLooper()).post(() -> {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (type == MeasureType.MEASURE_TYPE.ACLR) {
            createCarrierInnerBox();
            createCarrierOuterBox();
        }

//        });
//        }).start();
    }

    private void createCarrierOuterBox() {
//        new Thread(() -> {

        SaConfigData aclrConfigData = SaDataHandler
                .getInstance()
                .getAclrConfigData();

        AclrCarrierSetupData carrierData = aclrConfigData
                .getAclrMeasSetupData()
                .getCarrierSetupData();

        LineData data = mainLineChart.getLineData();
        int carriers = SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData().getCarrierSetupData().getCarriers();

        data.getDataSets().get(CARRIER_OUTER_BOX_DATASET_INDEX).clear();
        data.getDataSets().get(CARRIER_OUTER_BOX_DATASET_INDEX + 1).clear();
        float height = mainLineChart.getAxisLeft().getAxisMaximum();

        Double width = carrierData.getIntegBw();
        double span = aclrConfigData.getFrequencyData().getSpan();
        width = (mSpan * width) / span;

        Double spacing = carrierData.getCarrierSpacing();
        spacing = (mSpan * spacing) / span;

        for (int i = 0; i < carriers; i++) {

            if (carriers == 2 && i == 0) {

                float offsetX = (float) (getCenterFreq() - spacing + width / 2);
                float offsetY = mainLineChart.getAxisLeft().getAxisMinimum();
                float endX = (float) (offsetX - width);

                if (endX > getStopFreq()) endX = getStopFreq();
                else if (endX < getStartFreq()) endX = getStartFreq();
                if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                data.addEntry(new Entry(offsetX, offsetY), CARRIER_OUTER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(offsetX, height), CARRIER_OUTER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(endX, height), CARRIER_OUTER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(endX, offsetY), CARRIER_OUTER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(offsetX, offsetY), CARRIER_OUTER_BOX_DATASET_INDEX + i);

            } else {

                if (D) Log.d("carriers", carriers + " " + i);

                float offsetX = (float) (getCenterFreq() + spacing - width / 2);
                float offsetY = mainLineChart.getAxisLeft().getAxisMinimum();
                float endX = (float) (offsetX + width);

                if (endX > getStopFreq()) endX = getStopFreq();
                else if (endX < getStartFreq()) endX = getStartFreq();
                if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                data.addEntry(new Entry(offsetX, offsetY), CARRIER_OUTER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(offsetX, height), CARRIER_OUTER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(endX, height), CARRIER_OUTER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(endX, offsetY), CARRIER_OUTER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(offsetX, offsetY), CARRIER_OUTER_BOX_DATASET_INDEX + i);

            }

        }

        data.notifyDataChanged();// new Handler(Looper.getMainLooper()).post(data::notifyDataChanged);
//        }).start();
//        invalidate();
    }

    private void createCarrierInnerBox() {

//        new Thread(() -> {

        SaConfigData aclrConfigData = SaDataHandler
                .getInstance()
                .getAclrConfigData();

        AclrMeasSetupData aclrData = aclrConfigData.getAclrMeasSetupData();

        AclrCarrierSetupData carrierData = aclrData.getCarrierSetupData();

        LineChart chart = mainLineChart;
        LineData data = chart.getLineData();
        int carriers = SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData().getCarrierSetupData().getCarriers();

        data.getDataSets().get(CARRIER_INNER_BOX_DATASET_INDEX).clear();
        data.getDataSets().get(CARRIER_INNER_BOX_DATASET_INDEX + 1).clear();

        Double width = carrierData.getIntegBw();
        double span = aclrConfigData.getFrequencyData().getSpan();
        width = (mSpan * width) / span;

        Double spacing = carrierData.getCarrierSpacing();
        spacing = (mSpan * spacing) / span;

        float offsetX = 0;
        float offsetY = 0;

        float boundaryOffset = (binding.lineChartLayout.gateLayout.gateLineChart.getXAxis().getAxisMaximum()
                - binding.lineChartLayout.gateLayout.gateLineChart.getXAxis().getAxisMinimum()) / 10000f;

        for (int i = 0; i < carriers; i++) {

            ILineDataSet dataset = data.getDataSets().get(CARRIER_INNER_BOX_DATASET_INDEX + i);
            Float height = aclrData.getCarrierPower(i);
            if (height == null) return;

            Float finalHeight = height;
            dataset.setDrawValues(true);
            dataset.setValueTextSize(15f);
            dataset.setValueTextColor(Color.YELLOW);

//            ((CustomLineChartRenderer) chart.getRenderer()).setXOffset((int) Utils.convertDpToPixel(width / 1.75f));
            dataset.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {

                    if (value == finalHeight) {
                        return super.getFormattedValue(value) + " dBm";
                    } else return "";

                }
            });


            if (carriers == 2 && i == 0) {

                offsetX = (float) (getCenterFreq() - spacing - width / 2);
                offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                float endX = (float) (offsetX + width);

                if (endX > getStopFreq()) endX = getStopFreq() - boundaryOffset;
                else if (endX < getStartFreq()) continue;

                if (offsetX < getStartFreq()) {
                    offsetX = getStartFreq() + boundaryOffset;
                } else if (offsetX > getStopFreq()) continue;

                Entry offsetHeight = new Entry(offsetX, height);
                offsetHeight.setBoxHeight(height);

                Transformer transformer = new Transformer(chart.getViewPortHandler());
                MPPointD point = transformer.getPixelForValues(offsetX, offsetY);
                MPPointD point2 = transformer.getPixelForValues(endX, height);
                ((CustomLineChartRenderer) chart.getRenderer()).setXOffset((float) (point2.x - point.x) / 2f);

                data.addEntry(new Entry(offsetX, offsetY), CARRIER_INNER_BOX_DATASET_INDEX + i);
                data.addEntry(offsetHeight, CARRIER_INNER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(endX, height), CARRIER_INNER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(endX, offsetY), CARRIER_INNER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(offsetX, offsetY), CARRIER_INNER_BOX_DATASET_INDEX + i);

            } else {

                offsetX = (float) (getCenterFreq() + spacing - width / 2);
                offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                float endX = (float) (offsetX + width);
                Entry entryForValue = new Entry(offsetX, height);
                entryForValue.setBoxHeight(height);

                if (endX > getStopFreq()) endX = getStopFreq() - boundaryOffset;
                else if (endX < getStartFreq()) continue;

                if (offsetX < getStartFreq()) {
                    offsetX = getStartFreq() + boundaryOffset;
                } else if (offsetX > getStopFreq()) continue;

                data.addEntry(new Entry(offsetX, offsetY), CARRIER_INNER_BOX_DATASET_INDEX + i);
                data.addEntry(entryForValue, CARRIER_INNER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(endX, height), CARRIER_INNER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(endX, offsetY), CARRIER_INNER_BOX_DATASET_INDEX + i);
                data.addEntry(new Entry(offsetX, offsetY), CARRIER_INNER_BOX_DATASET_INDEX + i);
            }

        }

        data.notifyDataChanged();// new Handler(Looper.getMainLooper()).post(data::notifyDataChanged);
//        invalidate();

//        }).start();

    }

    public void calOffsetBox() {
//        new Thread(() -> {
//        new Handler(Looper.getMainLooper()).post(() -> {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (type == MeasureType.MEASURE_TYPE.ACLR) {
            createOffsetInnerBox();
            createOffsetOuterBox();
        }

//        });
//        }).start();
    }

    private void createOffsetOuterBox() {

//        new Thread(() -> {

        SaConfigData aclrConfigData = SaDataHandler
                .getInstance()
                .getAclrConfigData();

        AclrOffsetSetupData offsetData = aclrConfigData
                .getAclrMeasSetupData()
                .getOffsetSetupData();

        Double carrierSpacing = SaDataHandler
                .getInstance()
                .getAclrConfigData()
                .getAclrMeasSetupData()
                .getCarrierSetupData()
                .getCarrierSpacing();

        LineData data = mainLineChart.getLineData();
        int numOffset = offsetData.getNumOfOffset();
        double span = aclrConfigData.getFrequencyData().getSpan();

        for (int i = 0; i < MAX_OFFSET_NUM; i++) {

            data.getDataSets().get(OFFSET_POS_OUTER_DATASET_INDEX + i).clear();
            data.getDataSets().get(OFFSET_NEG_OUTER_DATASET_INDEX + i).clear();

        }

        for (int i = 0; i < numOffset; i++) {


            float height = mainLineChart.getAxisLeft().getAxisMaximum();

            double width = offsetData.getIntegBw(i);
            width = (mSpan * width) / span;

            double spacing = offsetData.getOffsetSpacing(i);
            spacing = (mSpan * spacing) / span;

            SideData.SIDE_OPTION sideOption = offsetData.getOffsetSide(i);

            float offsetX;
            float offsetY;
            float endX;

            switch (sideOption) {

                case NEG:

                    offsetX = (float) (getCenterFreq() - carrierSpacing - spacing + width / 2);
                    offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                    endX = (float) (offsetX - width);

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    data.addEntry(new Entry(endX, offsetY), OFFSET_NEG_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, height), OFFSET_NEG_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(offsetX, height), OFFSET_NEG_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(offsetX, offsetY), OFFSET_NEG_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, offsetY), OFFSET_NEG_OUTER_DATASET_INDEX + i);
                    break;

                case BOTH:

                    //NEG
                    offsetX = (float) (getCenterFreq() - carrierSpacing - spacing + width / 2);
                    offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                    endX = (float) (offsetX - width);

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    data.addEntry(new Entry(endX, offsetY), OFFSET_NEG_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, height), OFFSET_NEG_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(offsetX, height), OFFSET_NEG_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(offsetX, offsetY), OFFSET_NEG_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, offsetY), OFFSET_NEG_OUTER_DATASET_INDEX + i);


                    //POS
                    offsetX = (float) (getCenterFreq() + carrierSpacing + spacing - width / 2);
                    offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                    endX = (float) (offsetX + width);

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    data.addEntry(new Entry(endX, offsetY), OFFSET_POS_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, height), OFFSET_POS_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(offsetX, height), OFFSET_POS_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(offsetX, offsetY), OFFSET_POS_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, offsetY), OFFSET_POS_OUTER_DATASET_INDEX + i);
                    break;

                case POS:

                    //POS
                    offsetX = (float) (getCenterFreq() + carrierSpacing + spacing - width / 2);
                    offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                    endX = (float) (offsetX + width);

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    data.addEntry(new Entry(endX, offsetY), OFFSET_POS_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, height), OFFSET_POS_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(offsetX, height), OFFSET_POS_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(offsetX, offsetY), OFFSET_POS_OUTER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, offsetY), OFFSET_POS_OUTER_DATASET_INDEX + i);
                    break;

            }

        }

        data.notifyDataChanged();// new Handler(Looper.getMainLooper()).post(data::notifyDataChanged);
//        invalidate();

//        }).start();

    }

    private void createOffsetInnerBox() {

//        new Thread(() -> {

        SaConfigData aclrConfigData = SaDataHandler
                .getInstance()
                .getAclrConfigData();

        AclrMeasSetupData aclrData = aclrConfigData
                .getAclrMeasSetupData();

        AclrOffsetSetupData offsetData = aclrData.getOffsetSetupData();
        Double carrierSpacing = aclrData.getCarrierSetupData().getCarrierSpacing();

        LineData data = mainLineChart.getLineData();
        int numOffset = offsetData.getNumOfOffset();
        double span = aclrConfigData.getFrequencyData().getSpan();

        Double width = null;
        Float height = null;
        Double spacing = null;

        SideData.SIDE_OPTION sideOption = null;

        Float offsetX = null;
        Float offsetY = null;
        Float endX = null;

        float boundaryOffset = (binding.lineChartLayout.gateLayout.gateLineChart.getXAxis().getAxisMaximum()
                - binding.lineChartLayout.gateLayout.gateLineChart.getXAxis().getAxisMinimum()) / 10000f;

        for (int i = 0; i < MAX_OFFSET_NUM; i++) {

            data.getDataSets().get(OFFSET_POS_INNER_DATASET_INDEX + i).clear();
            data.getDataSets().get(OFFSET_NEG_INNER_DATASET_INDEX + i).clear();

        }

        for (int i = 0; i < numOffset; i++) {

            width = offsetData.getIntegBw(i);
            width = (mSpan * width) / span;

            spacing = offsetData.getOffsetSpacing(i);
            spacing = (mSpan * spacing) / span;

            sideOption = offsetData.getOffsetSide(i);
            ILineDataSet dataset;
            Entry entryForValue;

            switch (sideOption) {

                case NEG:

                    dataset = data.getDataSetByIndex(OFFSET_NEG_INNER_DATASET_INDEX + i);
                    dataset.setValueTextSize(15f);
                    dataset.setDrawValues(true);
                    int finalI = i;
                    dataset.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            Float dbc = aclrData.getLowerPowerDbc(finalI);
                            return dbc + " dBc";
                        }
                    });

                    if (aclrData.getLowerPassFail(i) == 1) {
                        //org
                        //dataset.setValueTextColor(Color.WHITE);
                        //org

                        //@@ [21.12.28] ACLR Passed dBc text Color
                        dataset.setValueTextColor(Color.YELLOW);
                        //@@
                    } else {
                        dataset.setValueTextColor(Color.RED);
                    }

                    offsetX = (float) (getCenterFreq() - carrierSpacing - spacing - width / 2);
                    offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                    endX = (float) (offsetX + width);
                    height = aclrData.getLowerPowerDbm(i);
                    if (height == null) return;

                    if (endX > getStopFreq()) endX = getStopFreq() - boundaryOffset;
                    else if (endX < getStartFreq()) continue;

                    if (offsetX < getStartFreq()) offsetX = getStartFreq() + boundaryOffset;
                    else if (offsetX > getStopFreq()) continue;

                    entryForValue = new Entry(offsetX, height);
                    entryForValue.setBoxHeight(height);

                    data.addEntry(new Entry(offsetX, offsetY), OFFSET_NEG_INNER_DATASET_INDEX + i);
                    data.addEntry(entryForValue, OFFSET_NEG_INNER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, height), OFFSET_NEG_INNER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, offsetY), OFFSET_NEG_INNER_DATASET_INDEX + i);
                    data.addEntry(new Entry(offsetX, offsetY), OFFSET_NEG_INNER_DATASET_INDEX + i);

                    break;

                case BOTH:

                    //NEG
                    dataset = data.getDataSetByIndex(OFFSET_NEG_INNER_DATASET_INDEX + i);
                    dataset.setDrawValues(true);
                    dataset.setValueTextSize(15f);
                    int finalI1 = i;
                    dataset.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            Float dbc = aclrData.getLowerPowerDbc(finalI1);
                            return dbc + " dBc";
                        }
                    });

                    if (aclrData.getLowerPassFail(i) == 1) {
                        //org
                        //dataset.setValueTextColor(Color.WHITE);
                        //org
                        //@@ [21.12.28] ACLR Passed dBc text Color
                        dataset.setValueTextColor(Color.YELLOW);
                        //@@
                    } else {
                        dataset.setValueTextColor(Color.RED);
                    }

                    offsetX = (float) (getCenterFreq() - carrierSpacing - spacing - width / 2);
                    offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                    endX = (float) (offsetX + width);
                    height = aclrData.getLowerPowerDbm(i);


                    if (height == null) return;

                    if (endX > getStopFreq()) endX = getStopFreq() - boundaryOffset;
                    else if (endX < getStartFreq()) continue;

                    if (offsetX < getStartFreq()) offsetX = getStartFreq() + boundaryOffset;
                    else if (offsetX > getStopFreq()) continue;

                    entryForValue = new Entry(offsetX, height);
                    entryForValue.setBoxHeight(height);

                    data.addEntry(new Entry(offsetX, offsetY), OFFSET_NEG_INNER_DATASET_INDEX + i);
                    data.addEntry(entryForValue, OFFSET_NEG_INNER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, height), OFFSET_NEG_INNER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, offsetY), OFFSET_NEG_INNER_DATASET_INDEX + i);
                    data.addEntry(new Entry(offsetX, offsetY), OFFSET_NEG_INNER_DATASET_INDEX + i);

                    //POS

                    dataset = data.getDataSetByIndex(OFFSET_POS_INNER_DATASET_INDEX + i);
                    dataset.setDrawValues(true);
                    dataset.setValueTextSize(15f);
                    dataset.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            Float dbc = aclrData.getUpperPowerDbc(finalI1);
                            return dbc + " dBc";
                        }
                    });

                    if (aclrData.getUpperPassFail(i) == 1) {
                        //org
                        //dataset.setValueTextColor(Color.WHITE);
                        //org

                        //@@ [21.12.28] ACLR Passed dBc text Color
                        dataset.setValueTextColor(Color.YELLOW);
                        //@@

                    } else {
                        dataset.setValueTextColor(Color.RED);
                    }

                    offsetX = (float) (getCenterFreq() + carrierSpacing + spacing - width / 2);
                    offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                    endX = (float) (offsetX + width);
                    height = aclrData.getUpperPowerDbm(i);

                    if (endX > getStopFreq()) endX = getStopFreq() - boundaryOffset;
                    else if (endX < getStartFreq()) continue;

                    if (offsetX < getStartFreq()) offsetX = getStartFreq() + boundaryOffset;
                    else if (offsetX > getStopFreq()) continue;

                    entryForValue = new Entry(offsetX, height);
                    entryForValue.setBoxHeight(height);

                    data.addEntry(new Entry(offsetX, offsetY), OFFSET_POS_INNER_DATASET_INDEX + i);
                    data.addEntry(entryForValue, OFFSET_POS_INNER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, height), OFFSET_POS_INNER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, offsetY), OFFSET_POS_INNER_DATASET_INDEX + i);
                    data.addEntry(new Entry(offsetX, offsetY), OFFSET_POS_INNER_DATASET_INDEX + i);

                    break;

                case POS:

                    dataset = data.getDataSetByIndex(OFFSET_POS_INNER_DATASET_INDEX + i);
                    dataset.setDrawValues(true);
                    dataset.setValueTextSize(15f);
                    int finalI2 = i;
                    dataset.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            Float dbc = aclrData.getUpperPowerDbc(finalI2);
                            return dbc + " dBc";
                        }
                    });

                    if (aclrData.getUpperPassFail(i) == 1) {
                        //org
                        //dataset.setValueTextColor(Color.WHITE);
                        //org

                        //@@ [21.12.28] ACLR Passed dBc text Color
                        dataset.setValueTextColor(Color.YELLOW);
                        //@@

                    } else {
                        dataset.setValueTextColor(Color.RED);
                    }

                    offsetX = (float) (getCenterFreq() + carrierSpacing + spacing - width / 2);
                    offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                    endX = (float) (offsetX + width);
                    height = aclrData.getUpperPowerDbm(i);

                    if (endX > getStopFreq()) endX = getStopFreq() - boundaryOffset;
                    else if (endX < getStartFreq()) continue;

                    if (offsetX < getStartFreq()) offsetX = getStartFreq() + boundaryOffset;
                    else if (offsetX > getStopFreq()) continue;

                    entryForValue = new Entry(offsetX, height);
                    entryForValue.setBoxHeight(height);

                    data.addEntry(new Entry(offsetX, offsetY), OFFSET_POS_INNER_DATASET_INDEX + i);
                    data.addEntry(entryForValue, OFFSET_POS_INNER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, height), OFFSET_POS_INNER_DATASET_INDEX + i);
                    data.addEntry(new Entry(endX, offsetY), OFFSET_POS_INNER_DATASET_INDEX + i);
                    data.addEntry(new Entry(offsetX, offsetY), OFFSET_POS_INNER_DATASET_INDEX + i);

                    break;

            }

        }

        data.notifyDataChanged();// new Handler(Looper.getMainLooper()).post(data::notifyDataChanged);
//        invalidate();

//        }).start();

    }

    private void createOffsetLimitLine() {

//        new Thread(() -> {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        if (type != MeasureType.MEASURE_TYPE.ACLR) return;

        SaConfigData aclrConfigData = SaDataHandler
                .getInstance()
                .getAclrConfigData();

        AclrMeasSetupData aclrData = aclrConfigData
                .getAclrMeasSetupData();

        AclrOffsetSetupData offsetData = aclrData.getOffsetSetupData();
        Double carrierSpacing = aclrData.getCarrierSetupData().getCarrierSpacing();

        LineData data = mainLineChart.getLineData();
        int numOffset = offsetData.getNumOfOffset();
        double span = aclrConfigData.getFrequencyData().getSpan();

        Float carrierPower;

        if (aclrData.getCarrierSetupData().getRefCarrier() == 1)
            carrierPower = aclrData.getCarrierPower(0);
        else carrierPower = aclrData.getCarrierPower(1);

        for (int i = 0; i < MAX_OFFSET_NUM; i++) {

            data.getDataSets().get(OFFSET_POS_ABS_LIMIT_DATASET_INDEX + i).clear();
            data.getDataSets().get(OFFSET_NEG_ABS_LIMIT_DATASET_INDEX + i).clear();

            data.getDataSets().get(OFFSET_POS_REL_LIMIT_DATASET_INDEX + i).clear();
            data.getDataSets().get(OFFSET_NEG_REL_LIMIT_DATASET_INDEX + i).clear();

        }

        for (int i = 0; i < numOffset; i++) {

            double width = offsetData.getIntegBw(i);
            width = (mSpan * width) / span;

            double spacing = offsetData.getOffsetSpacing(i);
            spacing = (mSpan * spacing) / span;

            float relHeight = offsetData.getRelLimit(i);
            float absHeight = offsetData.getAbsLimit(i);
            if (carrierPower != null)
                relHeight = carrierPower + offsetData.getRelLimit(i);

            SideData.SIDE_OPTION sideOption = offsetData.getOffsetSide(i);
            FailSourceData.FAIL_SOURCE failSource = offsetData.getFailSource(i);

            float offsetX;
            float endX;

            switch (sideOption) {

                case NEG:

                    offsetX = (float) (getCenterFreq() - carrierSpacing - spacing + width / 2);
                    endX = (float) (offsetX - width);

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    if (failSource == FailSourceData.FAIL_SOURCE.ALL || failSource == FailSourceData.FAIL_SOURCE.ABSOLUTE) {
                        data.addEntry(new Entry(endX, absHeight), OFFSET_NEG_ABS_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry(offsetX, absHeight), OFFSET_NEG_ABS_LIMIT_DATASET_INDEX + i);
                    }

                    if (failSource == FailSourceData.FAIL_SOURCE.ALL || failSource == FailSourceData.FAIL_SOURCE.RELATIVE) {
                        data.addEntry(new Entry(endX, relHeight), OFFSET_NEG_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry(offsetX, relHeight), OFFSET_NEG_REL_LIMIT_DATASET_INDEX + i);
                    }

                    break;

                case BOTH:

                    /*
                     * NEG
                     * */

                    offsetX = (float) (getCenterFreq() - carrierSpacing - spacing + width / 2);
                    endX = (float) (offsetX - width);

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    if (failSource == FailSourceData.FAIL_SOURCE.ALL || failSource == FailSourceData.FAIL_SOURCE.ABSOLUTE) {
                        data.addEntry(new Entry(endX, absHeight), OFFSET_NEG_ABS_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry(offsetX, absHeight), OFFSET_NEG_ABS_LIMIT_DATASET_INDEX + i);
                    }

                    if (failSource == FailSourceData.FAIL_SOURCE.ALL || failSource == FailSourceData.FAIL_SOURCE.RELATIVE) {
                        data.addEntry(new Entry(endX, relHeight), OFFSET_NEG_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry(offsetX, relHeight), OFFSET_NEG_REL_LIMIT_DATASET_INDEX + i);
                    }

                    /*
                     * POS
                     * */

                    offsetX = (float) (getCenterFreq() + carrierSpacing + spacing - width / 2);
                    endX = (float) (offsetX + width);

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    if (failSource == FailSourceData.FAIL_SOURCE.ALL || failSource == FailSourceData.FAIL_SOURCE.ABSOLUTE) {
                        data.addEntry(new Entry(endX, absHeight), OFFSET_POS_ABS_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry(offsetX, absHeight), OFFSET_POS_ABS_LIMIT_DATASET_INDEX + i);
                    }

                    if (failSource == FailSourceData.FAIL_SOURCE.ALL || failSource == FailSourceData.FAIL_SOURCE.RELATIVE) {
                        data.addEntry(new Entry(endX, relHeight), OFFSET_POS_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry(offsetX, relHeight), OFFSET_POS_REL_LIMIT_DATASET_INDEX + i);
                    }
                    break;

                case POS:

                    offsetX = (float) (getCenterFreq() + carrierSpacing + spacing - width / 2);
                    endX = (float) (offsetX + width);

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    if (failSource == FailSourceData.FAIL_SOURCE.ALL || failSource == FailSourceData.FAIL_SOURCE.ABSOLUTE) {
                        data.addEntry(new Entry(endX, absHeight), OFFSET_POS_ABS_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry(offsetX, absHeight), OFFSET_POS_ABS_LIMIT_DATASET_INDEX + i);
                    }

                    if (failSource == FailSourceData.FAIL_SOURCE.ALL || failSource == FailSourceData.FAIL_SOURCE.RELATIVE) {
                        data.addEntry(new Entry(endX, relHeight), OFFSET_POS_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry(offsetX, relHeight), OFFSET_POS_REL_LIMIT_DATASET_INDEX + i);
                    }
                    break;

            }

        }

        data.notifyDataChanged();// new Handler(Looper.getMainLooper()).post(data::notifyDataChanged);
//        invalidate();

//        }).start();

    }

    /*
     * end aclr box
     * start sem box
     * */


    public void updateSemBox() {
        updateSemBox(true);
    }

    public void updateSemBox(boolean isChartInvalidate) {
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (type == MeasureType.MEASURE_TYPE.SEM) {

            createSemBox();
            createSemLimitLine();
            createSemSpanLine();

            mainLineChart.getLineData().notifyDataChanged();

            if (isChartInvalidate)
                invalidate();

        } else {
            removeSemLine();
        }
    }


    private void createSemBox() {

        SaConfigData semConfigData = SaDataHandler
                .getInstance()
                .getSemConfigData();

        SemMeasSetupData semData = semConfigData
                .getSemMeasSetupData();

        double start = semConfigData.getFrequencyData().getStartFreq();
        double stop = semConfigData.getFrequencyData().getStopFreq();
        double span = semConfigData.getFrequencyData().getSpan();

        SemEditMaskData maskData = semData.getEditMaskData();
        Double absRefSpan = semData.getRefChannelData().getSpan();
        Double refSpan = (mSpan * absRefSpan) / span;

        LineData data = mainLineChart.getLineData();

        for (int i = 0; i < MAX_MASK_SIZE; i++) {

            data.getDataSets().get(SEM_POS_BOX_DATASET_INDEX + i).clear();
            data.getDataSets().get(SEM_NEG_BOX_DATASET_INDEX + i).clear();

        }

        for (int i = 0; i < MAX_MASK_SIZE; i++) {

            SemEditMaskData.MASK_ON_OFF maskOnOff = semData.getEditMaskData().getMaskOnOff(i);
            if (maskOnOff == SemEditMaskData.MASK_ON_OFF.OFF) continue;

            float height = mainLineChart.getAxisLeft().getAxisMaximum();

            SideData.SIDE_OPTION sideOption = maskData.getMaskSide(i);

            double offsetX;
            float offsetY;
            double endX;

            double absMaskStart = maskData.getStartFreq(i);
            double absMaskStop = maskData.getStopFreq(i);

            double maskStart = (mSpan * absMaskStart) / span;
            double maskStop = (mSpan * absMaskStop) / span;

            //Log.e("SEM", "Edit Mask " + (i + 1) + ") mask start : " + maskStart + ", mask stop : " + maskStop);

            switch (sideOption) {

                case NEG:

                    offsetX = getCenterFreq() - refSpan / 2 - maskStart;
                    offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                    endX = getCenterFreq() - refSpan / 2 - maskStop;

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    data.addEntry(new Entry((float) endX, offsetY), SEM_NEG_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, height), SEM_NEG_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, height), SEM_NEG_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, offsetY), SEM_NEG_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, offsetY), SEM_NEG_BOX_DATASET_INDEX + i);
                    break;

                case BOTH:

                    //NEG
                    offsetX = getCenterFreq() - refSpan / 2 - maskStart;
                    offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                    if (D)
                        Log.d("updateSemBox", "absRefSpan : " + absRefSpan + "Ref span : " + refSpan + " Mask start : " + maskStart + "NEG VAL : " + (refSpan / 2 - maskStart));

                    endX = getCenterFreq() - refSpan / 2 - maskStop;

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    data.addEntry(new Entry((float) endX, offsetY), SEM_NEG_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, height), SEM_NEG_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, height), SEM_NEG_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, offsetY), SEM_NEG_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, offsetY), SEM_NEG_BOX_DATASET_INDEX + i);


                    //POS
                    offsetX = getCenterFreq() + refSpan / 2 + maskStart;
                    offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                    endX = getCenterFreq() + refSpan / 2 + maskStop;

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    data.addEntry(new Entry((float) endX, offsetY), SEM_POS_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, height), SEM_POS_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, height), SEM_POS_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, offsetY), SEM_POS_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, offsetY), SEM_POS_BOX_DATASET_INDEX + i);
                    break;

                case POS:

                    offsetX = getCenterFreq() + refSpan / 2 + maskStart;
                    offsetY = mainLineChart.getAxisLeft().getAxisMinimum();

                    endX = getCenterFreq() + refSpan / 2 + maskStop;

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    data.addEntry(new Entry((float) endX, offsetY), SEM_POS_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, height), SEM_POS_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, height), SEM_POS_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, offsetY), SEM_POS_BOX_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, offsetY), SEM_POS_BOX_DATASET_INDEX + i);
                    break;

            }

        }

    }

    private void createSemLimitLine() {

        SaConfigData semConfigData = SaDataHandler
                .getInstance()
                .getSemConfigData();

        SemMeasSetupData semData = semConfigData
                .getSemMeasSetupData();

        SemEditMaskData maskData = semData.getEditMaskData();
        double span = semConfigData.getFrequencyData().getSpan();
        double start = semConfigData.getFrequencyData().getStartFreq();
        double stop = semConfigData.getFrequencyData().getStopFreq();

        Double refSpan = semData.getRefChannelData().getSpan();
        refSpan = (mSpan * refSpan) / span;

        LineData data = mainLineChart.getLineData();

        Boolean isRelative = false;

        for (int i = 0; i < MAX_MASK_SIZE; i++) {

            data.getDataSets().get(SEM_POS_ABS_LIMIT_DATASET_INDEX + i).clear();
            data.getDataSets().get(SEM_NEG_ABS_LIMIT_DATASET_INDEX + i).clear();

            data.getDataSets().get(SEM_POS_REL_LIMIT_DATASET_INDEX + i).clear();
            data.getDataSets().get(SEM_NEG_REL_LIMIT_DATASET_INDEX + i).clear();

            if (maskData.getFailSource(i).equals(FailSourceData.FAIL_SOURCE.RELATIVE) ||
                    maskData.getFailSource(i).equals(FailSourceData.FAIL_SOURCE.SEM_ABS_OR_REL) ||
                    maskData.getFailSource(i).equals(FailSourceData.FAIL_SOURCE.SEM_ALL))
                isRelative = true;

        }

        for (int i = 0; i < MAX_MASK_SIZE; i++) {

            SemEditMaskData.MASK_ON_OFF maskOnOff = semData.getEditMaskData().getMaskOnOff(i);
            if (maskOnOff == SemEditMaskData.MASK_ON_OFF.OFF) continue;

            Float height = mainLineChart.getAxisLeft().getAxisMaximum();

            SideData.SIDE_OPTION sideOption = maskData.getMaskSide(i);

            double offsetX;
            double endX;

            Float absStartY, absStopY;
            Float relStartY, relStopY;

            Float relOffset;
            if (semData.getSemMeasType() == SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER) {
                relOffset = semData.getChPower();
            } else if (semData.getSemMeasType() == SemMeasTypeData.SEM_MEASURE_TYPE.PEAK) {
                relOffset = getPeakForSemSpan();
            } else relOffset = 0f;

            double absStartSpace = maskData.getStartFreq(i);
            double absStopSpace = maskData.getStopFreq(i);

            double maskStart = (mSpan * absStartSpace) / span;
            double maskStop = (mSpan * absStopSpace) / span;

            switch (sideOption) {

                case NEG:

                    offsetX = getCenterFreq() - refSpan / 2 - maskStart;
                    endX = getCenterFreq() - refSpan / 2 - maskStop;

                    absStartY = maskData.getAbsStartLimit(i);
                    absStopY = maskData.getAbsStopLimit(i);

                    relStartY = relOffset + maskData.getRelStartLimit(i);
                    relStopY = relOffset + maskData.getRelStopLimit(i);

                    if (endX > getStopFreq()) endX = (double) getStopFreq();
                    else if (endX < getStartFreq()) endX = (double) getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    //abs Limit
                    data.addEntry(new Entry((float) endX, height), SEM_NEG_ABS_LIMIT_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, absStopY), SEM_NEG_ABS_LIMIT_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, absStartY), SEM_NEG_ABS_LIMIT_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, height), SEM_NEG_ABS_LIMIT_DATASET_INDEX + i);

                    if (isRelative) {

                        //Rel Limit
                        data.addEntry(new Entry((float) endX, height), SEM_NEG_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry((float) endX, relStopY), SEM_NEG_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry((float) offsetX, relStartY), SEM_NEG_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry((float) offsetX, height), SEM_NEG_REL_LIMIT_DATASET_INDEX + i);
                    }
                    break;

                case BOTH:

                    //NEG
                    offsetX = getCenterFreq() - refSpan / 2 - maskStart;
                    endX = getCenterFreq() - refSpan / 2 - maskStop;

                    absStartY = maskData.getAbsStartLimit(i);
                    absStopY = maskData.getAbsStopLimit(i);

                    relStartY = relOffset + maskData.getRelStartLimit(i);
                    relStopY = relOffset + maskData.getRelStopLimit(i);

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    //abs Limit
                    data.addEntry(new Entry((float) endX, height), SEM_NEG_ABS_LIMIT_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, absStopY), SEM_NEG_ABS_LIMIT_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, absStartY), SEM_NEG_ABS_LIMIT_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, height), SEM_NEG_ABS_LIMIT_DATASET_INDEX + i);

                    if (isRelative) {

                        //Rel Limit
                        data.addEntry(new Entry((float) endX, height), SEM_NEG_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry((float) endX, relStopY), SEM_NEG_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry((float) offsetX, relStartY), SEM_NEG_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry((float) offsetX, height), SEM_NEG_REL_LIMIT_DATASET_INDEX + i);
                    }

                    //POS
                    offsetX = getCenterFreq() + refSpan / 2 + maskStart;
                    endX = getCenterFreq() + refSpan / 2 + maskStop;

                    absStartY = maskData.getAbsStartLimit(i);
                    absStopY = maskData.getAbsStopLimit(i);

                    relStartY = relOffset + maskData.getRelStartLimit(i);
                    relStopY = relOffset + maskData.getRelStopLimit(i);

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    //abs Limit
                    data.addEntry(new Entry((float) offsetX, height), SEM_POS_ABS_LIMIT_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, absStartY), SEM_POS_ABS_LIMIT_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, absStopY), SEM_POS_ABS_LIMIT_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, height), SEM_POS_ABS_LIMIT_DATASET_INDEX + i);

                    if (isRelative) {

                        //Rel Limit
                        data.addEntry(new Entry((float) offsetX, height), SEM_POS_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry((float) offsetX, relStartY), SEM_POS_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry((float) endX, relStopY), SEM_POS_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry((float) endX, height), SEM_POS_REL_LIMIT_DATASET_INDEX + i);

                    }

                    break;

                case POS:

                    offsetX = getCenterFreq() + refSpan / 2 + maskStart;
                    endX = getCenterFreq() + refSpan / 2 + maskStop;

                    absStartY = maskData.getAbsStartLimit(i);
                    absStopY = maskData.getAbsStopLimit(i);

                    relStartY = relOffset + maskData.getRelStartLimit(i);
                    relStopY = relOffset + maskData.getRelStopLimit(i);

                    if (endX > getStopFreq()) endX = getStopFreq();
                    else if (endX < getStartFreq()) endX = getStartFreq();
                    if (offsetX < getStartFreq() || offsetX > getStopFreq()) continue;

                    //abs Limit
                    data.addEntry(new Entry((float) offsetX, height), SEM_POS_ABS_LIMIT_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) offsetX, absStartY), SEM_POS_ABS_LIMIT_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, absStopY), SEM_POS_ABS_LIMIT_DATASET_INDEX + i);
                    data.addEntry(new Entry((float) endX, height), SEM_POS_ABS_LIMIT_DATASET_INDEX + i);

                    if (isRelative) {

                        //Rel Limit
                        data.addEntry(new Entry((float) offsetX, height), SEM_POS_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry((float) offsetX, relStartY), SEM_POS_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry((float) endX, relStopY), SEM_POS_REL_LIMIT_DATASET_INDEX + i);
                        data.addEntry(new Entry((float) endX, height), SEM_POS_REL_LIMIT_DATASET_INDEX + i);

                    }

                    break;

            }

        }

    }

    private void createSemSpanLine() {

        SaConfigData semConfigData = SaDataHandler
                .getInstance()
                .getSemConfigData();

        SemMeasSetupData semData = semConfigData
                .getSemMeasSetupData();

        double span = semConfigData.getFrequencyData().getSpan();
        Double refSpan = semData.getRefChannelData().getSpan();
        refSpan = (mSpan * refSpan) / span;

        LineData data = mainLineChart.getLineData();

        double offsetX;
        double endX;

        Float peak;

        if (semData.getSemMeasType() == SemMeasTypeData.SEM_MEASURE_TYPE.PEAK) {
            peak = getPeakForSemSpan();
        } else {
            data.getDataSets().get(SEM_RELATIVE_LINE_INDEX).clear();
            return;
        }

        offsetX = getCenterFreq() - refSpan / 2;
        endX = getCenterFreq() + refSpan / 2;

        if (endX > getStopFreq()) endX = getStopFreq();
        else if (endX < getStartFreq()) endX = getStartFreq();
        if (offsetX < getStartFreq() || offsetX > getStopFreq()) return;

        //abs Limit
        data.addEntry(new Entry((float) offsetX, peak), SEM_RELATIVE_LINE_INDEX);
        data.addEntry(new Entry((float) endX, peak), SEM_RELATIVE_LINE_INDEX);

    }

    public void removeSemLine() {

        LineData data = mainLineChart.getLineData();

        for (int i = 0; i < MAX_MASK_SIZE; i++) {

            if (data.getDataSets().get(SEM_POS_BOX_DATASET_INDEX + i).getEntryCount() != 0)
                data.getDataSets().get(SEM_POS_BOX_DATASET_INDEX + i).clear();
            if (data.getDataSets().get(SEM_NEG_BOX_DATASET_INDEX + i).getEntryCount() != 0)
                data.getDataSets().get(SEM_NEG_BOX_DATASET_INDEX + i).clear();

            if (data.getDataSets().get(SEM_POS_ABS_LIMIT_DATASET_INDEX + i).getEntryCount() != 0)
                data.getDataSets().get(SEM_POS_ABS_LIMIT_DATASET_INDEX + i).clear();
            if (data.getDataSets().get(SEM_NEG_ABS_LIMIT_DATASET_INDEX + i).getEntryCount() != 0)
                data.getDataSets().get(SEM_NEG_ABS_LIMIT_DATASET_INDEX + i).clear();

            if (data.getDataSets().get(SEM_POS_REL_LIMIT_DATASET_INDEX + i).getEntryCount() != 0)
                data.getDataSets().get(SEM_POS_REL_LIMIT_DATASET_INDEX + i).clear();
            if (data.getDataSets().get(SEM_NEG_REL_LIMIT_DATASET_INDEX + i).getEntryCount() != 0)
                data.getDataSets().get(SEM_NEG_REL_LIMIT_DATASET_INDEX + i).clear();

        }

        data.getDataSets().get(SEM_RELATIVE_LINE_INDEX).clear();

    }

    public void removeSemLine(int i) {

        LineData data = mainLineChart.getLineData();
        data.getDataSets().get(SEM_POS_BOX_DATASET_INDEX + i).clear();
        data.getDataSets().get(SEM_NEG_BOX_DATASET_INDEX + i).clear();

        data.getDataSets().get(SEM_POS_ABS_LIMIT_DATASET_INDEX + i).clear();
        data.getDataSets().get(SEM_NEG_ABS_LIMIT_DATASET_INDEX + i).clear();

        data.getDataSets().get(SEM_POS_REL_LIMIT_DATASET_INDEX + i).clear();
        data.getDataSets().get(SEM_NEG_REL_LIMIT_DATASET_INDEX + i).clear();

    }

    public Float getPeakForSemSpan() {

        SemRefChannelData refData = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getRefChannelData();

        Double startFreq = mCenterX - refData.getSpan() / 2;
        Double stopFreq = mCenterX + refData.getSpan() / 2;
        int count = mDataSets.get(0).getEntryCount();

        Integer startIdx = null;
        Integer stopIdx = null;

        for (int i = 0; i < count / 2; i++) {

            float freq = mainLineChart.getLineData().getDataSets().get(0).getEntryForIndex(i).getX();

            if (freq >= startFreq) {

                startIdx = i;
                break;

            }

        }

        for (int i = count - 1; i > count / 2; i--) {

            float freq = mainLineChart.getLineData().getDataSets().get(0).getEntryForIndex(i).getX();

            if (freq <= stopFreq) {

                stopIdx = i;
                break;

            }

        }

        if (startIdx == null || stopIdx == null) return null;

        float peak = mainLineChart.getLineData().getDataSets().get(0).getEntryForIndex(startIdx).getY();

        for (int i = startIdx + 1; i <= stopIdx; i++) {

            float y = mainLineChart.getLineData().getDataSets().get(0).getEntryForIndex(i).getY();

            if (y > peak) peak = y;

        }

        return peak;
    }

    public void updateInterferenceHuntingBox() {

//        new Thread(() -> {

        LineData data = mainLineChart.getLineData();

        //remove

        if (D) Log.d("MainLineChartFunc", "dataset size : " + data.getDataSets().size());
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        if (type != MeasureType.MEASURE_TYPE.INTERFERENCE_HUNTING) {
            for (int i = 0; i < 2; i++) {
                data.getDataSets().get(INTERFERENCE_HUNTING + i).clear();
            }
            return;
        }

        SaConfigData configData = SaDataHandler
                .getInstance()
                .getInterferenceHuntingConfigData();

        InterferenceMeasSetupData measData = configData
                .getInterferenceHuntingData();

        double startBand = measData.getUplinkBandStart();
        double stopBand = measData.getUplinkBandStop();

        double span = configData.getFrequencyData().getSpan();
        double start = configData.getFrequencyData().getStartFreq();
        double stop = configData.getFrequencyData().getStopFreq();

        if ((startBand - start) > 0) {

            double width = startBand - start;
            width = (mSpan * width) / span;
            Float height = mainLineChart.getAxisLeft().getAxisMaximum();
            Float offsetY = mainLineChart.getAxisLeft().getAxisMinimum();
            Float offsetX = FunctionHandler.getInstance().getMainLineChart().getStartFreq();
            Float endX = offsetX + (float) width;

            data.addEntry(new Entry(offsetX, offsetY), INTERFERENCE_HUNTING);
            data.addEntry(new Entry(endX, offsetY), INTERFERENCE_HUNTING);
            data.addEntry(new Entry(endX, height), INTERFERENCE_HUNTING);
            data.addEntry(new Entry(offsetX, height), INTERFERENCE_HUNTING);

        }

        if ((stop - stopBand) > 0) {

            double width = stop - stopBand;
            width = (mSpan * width) / span;
            Float height = mainLineChart.getAxisLeft().getAxisMaximum();
            Float offsetY = mainLineChart.getAxisLeft().getAxisMinimum();
            Float offsetX = FunctionHandler.getInstance().getMainLineChart().getStopFreq();
            Float endX = offsetX - (float) width;

            data.addEntry(new Entry(offsetX, offsetY), INTERFERENCE_HUNTING + 1);
            data.addEntry(new Entry(endX, offsetY), INTERFERENCE_HUNTING + 1);
            data.addEntry(new Entry(endX, height), INTERFERENCE_HUNTING + 1);
            data.addEntry(new Entry(offsetX, height), INTERFERENCE_HUNTING + 1);

        }

        float boundaryOffset = (binding.lineChartLayout.gateLayout.gateLineChart.getXAxis().getAxisMaximum()
                - binding.lineChartLayout.gateLayout.gateLineChart.getXAxis().getAxisMinimum()) / 10000f;

//        for (int i = 0; i < 2; i++) {
//            data.getDataSets().get(INTERFERENCE_HUNTING + i).clear();
//        }


        new Handler(Looper.getMainLooper()).post(data::notifyDataChanged);
//        invalidate();

//        }).start();

    }

    public ArrayList<Integer> getRandomList(int random, int dataset) {

        if (getDataList(dataset) == null) return null;
        getDataList(dataset).clear();

        for (int i = 0; i < SaDataHandler.getInstance().getConfigData().getTraceData().getDetector(0).getDataPoints(); i++) {

            Random rnd = new Random();
            rnd.setSeed(System.currentTimeMillis() * i);
            int num = rnd.nextInt(20000) + random;
//            if (D) Log.d("random", num + "");
            getDataList(dataset).add(num);

        }

        return getDataList(dataset);

    }

    public ILineDataSet getTrace(int index) {

        ILineDataSet dataset = null;
        try {
            dataset = mainLineChart.getLineData().getDataSetByIndex(index);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return dataset;

    }

    public LineDataSet getDataset(int idx) {

        return (LineDataSet) mainLineChart.getData().getDataSetByIndex(idx);

    }

    public boolean setScaleDiv(float scale) {

//        if (scale < 0.1f || scale > 20) return false;

        mScaleDiv = scale;
        mMinRefLev = mMaxRefLev - (scale * 10);

        mainLineChart.getAxisLeft().setAxisMaximum(mMaxRefLev);
        mainLineChart.getAxisLeft().setAxisMinimum(mMinRefLev);

        calCarrierBox();
        calOffsetBox();
        updateAclrBox();
        updateSemBox();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            FunctionHandler.getInstance().getMainLineChart().updateOccBox();
            FunctionHandler.getInstance().getMainLineChart().updateChannelBox();

        }, 500);


        invalidate();

        return true;

    }

    public float getScaleDiv() {
        return mScaleDiv;
    }


    public boolean setOffset(float offset) {

        mOffset = offset;
        mMaxRefLev = mRefLev + mOffset;
        mMinRefLev = mMaxRefLev - (mScaleDiv * 10);
        mainLineChart.getAxisLeft().setAxisMaximum(mMaxRefLev);
        mainLineChart.getAxisLeft().setAxisMinimum(mMinRefLev);

        calCarrierBox();
        calOffsetBox();
        updateSemBox();
        updateAclrBox();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            FunctionHandler.getInstance().getMainLineChart().updateOccBox();
            FunctionHandler.getInstance().getMainLineChart().updateChannelBox();

        }, 500);


        invalidate();

        return true;
    }

    public float getOffset() {
        return mOffset;
    }

    public boolean setRefLev(float lev) {

        if (lev > 100) {
            lev = 100;
        } else if (lev < -100) {
            lev = -100;
        }

        mRefLev = lev;
        SaDataHandler.getInstance().getConfigData().getAmplitudeData().setRefLev(mRefLev);
        mMaxRefLev = mRefLev + mOffset;
        mMinRefLev = mMaxRefLev - (mScaleDiv * 10);
        mainLineChart.getAxisLeft().setAxisMaximum(mMaxRefLev);
        mainLineChart.getAxisLeft().setAxisMinimum(mMinRefLev);

        calCarrierBox();
        calOffsetBox();
        updateAclrBox();
        updateSemBox();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            FunctionHandler.getInstance().getMainLineChart().updateOccBox();
            FunctionHandler.getInstance().getMainLineChart().updateChannelBox();

        }, 500);


        invalidate();

        return true;

    }

    public float getRefLev() {

        return mRefLev;
    }

    public void setVisible(int trace, Boolean onOff) {

        try {

            mainLineChart.getData().getDataSetByIndex(trace).setVisible(onOff);

            if (!onOff) {

                for (int i = 0; i < MAX_MARKER_SIZE; i++) {

                    if (getMarker(i) == null) continue;
                    if (D) Log.d("markerDataset", getMarker(i).getDataSetIndex() + "");

                    if (getMarker(i).getDataSetIndex() == trace) {

                        removeMarker(i);

                    }

                }

            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public Boolean isVisible(int trace) {

        Boolean visible = false;

        try {
            visible = mainLineChart.getData().getDataSetByIndex(trace).isVisible();
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

    public void setCenterFreq(float freq) {

        float centerFreq = freq;
        float startFreq = centerFreq - (mSpan / 2);
        float stopFreq = centerFreq + (mSpan / 2);

        mCenterX = freq;
        mStartX = startFreq;
        mEndX = stopFreq;

        new Thread(() -> {


//            mainLineChart.getXAxis().setAxisMinimum(mStartX);
//            mainLineChart.getXAxis().setAxisMaximum(mEndX);
            FunctionHandler.getInstance().getMainLineChart().invalidate();

            new Handler(Looper.getMainLooper()).postDelayed(() -> {

                FunctionHandler.getInstance().getMainLineChart().updateOccBox();
                FunctionHandler.getInstance().getMainLineChart().updateChannelBox();

            }, 500);

        }).start();

    }

    public float getCenterFreq() {
        return mCenterX;
    }

    public Long getCenterFreqToHz() {

        Long freq = ((long) (mCenterX * 100) * 10) * 1000;

        return freq;

    }

    public byte[] getCenterFreqToBytes() {

        byte[] byteData = DataHandler.getInstance().intToBytes(getCenterFreqToHz(), 8, ByteOrder.BIG_ENDIAN);

        StringBuilder sb = new StringBuilder();
        for (byte b : byteData) {
            sb.append(String.format("%02x", b));
        }

        if (D) Log.d("getCenterFreqToByte", "hex : " + sb.toString());

        return byteData;
    }

    public void setFreq(float start, float stop) {

        float startFreq = start;
        float stopFreq = stop;
        float center = start + (mEndX - start) / 2;
        float span = mEndX - start;

        mStartX = startFreq;
        mEndX = stopFreq;
        mCenterX = center;
        mSpan = span;

//        mainLineChart.getXAxis().setAxisMinimum(mStartX);
//        mainLineChart.getXAxis().setAxisMaximum(mEndX);

        new Thread(() -> {

            new Handler(Looper.getMainLooper()).postDelayed(() -> {

                FunctionHandler.getInstance().getMainLineChart().invalidate();
                FunctionHandler.getInstance().getMainLineChart().updateOccBox();
                FunctionHandler.getInstance().getMainLineChart().updateChannelBox();

            }, 500);

        }).start();
    }

    public void setStartFreq(float freq) {

        float start = freq;
        float center = start + (mEndX - start) / 2;
        float span = mEndX - start;

        mStartX = start;
        mCenterX = center;
        mSpan = span;

        mainLineChart.getLineData().getDataSetByIndex(0).clear();
//        mainLineChart.getXAxis().setAxisMinimum(mStartX);

        new Thread(() -> {

            new Handler(Looper.getMainLooper()).postDelayed(() -> {

                FunctionHandler.getInstance().getMainLineChart().updateOccBox();
                FunctionHandler.getInstance().getMainLineChart().updateChannelBox();

            }, 500);

        }).start();

    }

    public float getStartFreq() {
        return mStartX;
    }

    public Long getStartFreqToHz() {

        Long freq = ((long) (mStartX * 100) * 10) * 1000;

        return freq;

    }

    public byte[] getStartFreqToBytes() {

        byte[] b = DataHandler.getInstance().intToBytes(getStartFreqToHz(), 8, ByteOrder.LITTLE_ENDIAN);
        return b;
    }

    public void setStopFreq(float freq) {

        float stop = freq;
        float center = mStartX + (stop - mStartX) / 2;
        float span = stop - mStartX;

        mEndX = stop;
        mCenterX = center;
        mSpan = span;

//        mainLineChart.getLineData().getDataSetByIndex(0).clear();
//        mainLineChart.getXAxis().setAxisMaximum(mEndX);

        new Thread(() -> {

            new Handler(Looper.getMainLooper()).postDelayed(() -> {

                FunctionHandler.getInstance().getMainLineChart().updateOccBox();
                FunctionHandler.getInstance().getMainLineChart().updateChannelBox();

            }, 500);

        }).start();

    }

    public float getStopFreq() {
        return mEndX;
    }

    public Long getStopFreqToHz() {

        Long freq = ((long) (mCenterX * 100) * 10) * 1000;

        return freq;

    }

    public byte[] getStopFreqToBytes() {

        byte[] b = DataHandler.getInstance().intToBytes(getCenterFreqToHz(), 8, ByteOrder.LITTLE_ENDIAN);

        return b;
    }

    public void setSpan(float freq) {

        float span = freq;
        float startFreq = mCenterX - (span / 2);
        float stopFreq = mCenterX + (span / 2);

        mSpan = freq;
        mStartX = startFreq;
        mEndX = stopFreq;

//        mainLineChart.getLineData().getDataSetByIndex(0).clear();
//        mainLineChart.getXAxis().setAxisMinimum(mStartX);
//        mainLineChart.getXAxis().setAxisMaximum(mEndX);
//        if (D) Log.d("setSpan", mainLineChart.getXAxis().getAxisMinimum() + " " + mainLineChart.getXAxis().getAxisMaximum());

        invalidate();

    }

    public float getSpan() {
        return mSpan;
    }

    public Long getSpanToHz() {

        Long freq = ((long) (mSpan * 100) * 10) * 1000;

        return freq;

    }

    public byte[] getSpanToBytes() {

        byte[] b = DataHandler.getInstance().intToBytes(getSpanToHz(), 8, ByteOrder.BIG_ENDIAN);

        return b;
    }


    /* Set LineChartFunc Data */

    @SuppressLint("ResourceType")
    public void addEntry(ArrayList<Integer> dataList, int dataset
            , MeasureMode.MEASURE_MODE mode, MeasureType.MEASURE_TYPE type
            , SaConfigData saConfigData) {
//        new Thread(() -> {
        try {

//            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
//            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

            SemEditMaskData maskData = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getEditMaskData();

            if (!isSkipFirstData && type != MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {
                //if (D)
                Log.d("addEntry", "Skip First Data");
                isSkipFirstData = true;
                return;
            }

            if (mode == MeasureMode.MEASURE_MODE.SA) {
                //SaConfigData saConfigData = SaDataHandler.getInstance().getConfigData();
                if (saConfigData.getTraceData().getType(dataset) == TraceEnumData.TRACE_TYPE.BLANK ||
                        saConfigData.getTraceData().getType(dataset) == TraceEnumData.TRACE_TYPE.VIEW)
                    return;
            }

            isDrawingChart = true;
            LineChart lineChart = mainLineChart;

            LineData data = lineChart.getLineData();
            data.getDataSets().get(dataset).setDrawIcons(true);

            if (data != null) {
                // set.addEntry(...); // can be called as well

                float DIV_VALUE = 1000f;

                if (mode == MeasureMode.MEASURE_MODE.SA || mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY)
                    DIV_VALUE = 100f;

//                if (D) Log.d("addEntry", dataList.size() + "");
                double space = (lineChart.getXAxis().getAxisMaximum()
                        - lineChart.getXAxis().getAxisMinimum()) / (dataList.size() - 1);

                double xVal = lineChart.getXAxis().getAxisMinimum();
//                    data.clearValuesNotChanging(dataset);
                String dataLog = "";

                // size 2002
                if (mode == MeasureMode.MEASURE_MODE.SA && dataList.size() == 2002
                        && saConfigData.getTraceData().getDetector(dataset) == TraceEnumData.DETECTOR.NORMAL) {

                    if (D) Log.d("addEntry", "NORMAL");

                    space = (lineChart.getXAxis().getAxisMaximum()
                            - lineChart.getXAxis().getAxisMinimum()) / (1001 - 1);

                    for (int i = 0; i < dataList.size(); i += 2) {

                        if (dataList.get(i) == null) continue;
                        if (dataList.get(i) == -99999) {
                            if (D) Log.d("addEntry", "dummyData");
                            continue;
                        }

                        //divided by 1000 or 100
                        float yValMin = (float) dataList.get(i) / DIV_VALUE;
                        float yValMax = (float) dataList.get(i + 1) / DIV_VALUE;

                        if (D) {
                            if (i < 20) dataLog += yValMin + ", ";
                            if (i == 20) dataLog += " ..... ";
                            if (dataList.size() - i < 20) dataLog += yValMin + ", ";
                        }

                        data.addEntry(new Entry((float) xVal, yValMin), dataset);
                        data.addEntry(new Entry((float) xVal, yValMax), dataset);

                        // type == SEM
                        if (type == MeasureType.MEASURE_TYPE.SEM) {

                            for (int k = 0; k < MAX_MASK_SIZE; k++) {
                                int lowerPeakIndex = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getLowerPeakIndex(k);
                                int upperPeakIndex = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getUpperPeakIndex(k);

                                if (lowerPeakIndex == i) {
                                    final int finalI = i;
                                    //org
                                    /*new Thread(() -> {
                                        mActivity.runOnUiThread(() -> {
                                            data.getDataSetByIndex(0).getEntryForIndex(finalI).setIcon(
                                                    mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
                                            );
                                        });
                                    }).start();*/
                                    //org

                                    //@@ [21.12.29] SEM triangle blink fix
                                    /*data.getDataSetByIndex(0).getEntryForIndex(finalI).setIcon(
                                            mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
                                    );*/
                                    //@@

                                    if (maskData.getMaskOnOff(k) == SemEditMaskData.MASK_ON_OFF.ON) {
                                        data.getDataSetByIndex(0).getEntryForIndex(finalI).setIcon(
                                                mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
                                        );
                                    }
                                }

                                if (upperPeakIndex == i) {
                                    final int finalI1 = i;
                                    //org
                                    /*new Thread(() -> {
                                        mActivity.runOnUiThread(() -> {
                                            data.getDataSetByIndex(0).getEntryForIndex(finalI1).setIcon(
                                                    mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
                                            );
                                        });
                                    }).start();*/
                                    //org

                                    //@@ [21.12.29] sem triangle fix
                                    /*data.getDataSetByIndex(0).getEntryForIndex(finalI1).setIcon(
                                            mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
                                    );*/
                                    //@@

                                    if (maskData.getMaskOnOff(k) == SemEditMaskData.MASK_ON_OFF.ON) {
                                        data.getDataSetByIndex(0).getEntryForIndex(finalI1).setIcon(
                                                mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
                                        );
                                    }
                                }
                            }

                        }
                        // type == SPURIOUS_EMISSION
                        else if (type == MeasureType.MEASURE_TYPE.SPURIOUS_EMISSION) {
                            SpuriousEmissionMeasSetupData measData = SaDataHandler.getInstance().getSpuriousEmissionConfigData().getSpuriousEmissionData();
                            ArrayList<FreqRangeTableData> freqRangeList = measData.getFreqRangeTableDataList();

                            for (FreqRangeTableData tableData : freqRangeList) {
                                if (!tableData.isState()) continue;

//                                    if (lowerPeakIndex == i) {
//
//                                        int finalI = i;
//                                        new Thread(() -> {
//
//                                            mActivity.runOnUiThread(() -> {
//
//                                                data.getDataSetByIndex(0).getEntryForIndex(finalI).setIcon(
//                                                        mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
//                                                );
//
//                                            });
//
//                                        }).start();
//                                    }
//
//                                    if (upperPeakIndex == i) {
//
//                                        int finalI1 = i;
//                                        new Thread(() -> {
//
//                                            mActivity.runOnUiThread(() -> {
//
//                                                data.getDataSetByIndex(0).getEntryForIndex(finalI1).setIcon(
//                                                        mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
//                                                );
//
//                                            });
//                                        }).start();
//                                    }

                            }
                        }

//                    if (D) Log.d("addEntry", "MAX : " + yVal);
                        xVal += space;
                    }

                }
                // size 1001
                else {
                    if (D) Log.d("addEntry", "data size : " + dataList.size());

                    for (int i = 0; i < dataList.size(); i++) {
                        //if (dataList.size() <= i) continue;
                        if (dataList.get(i) == null || dataList.get(i) == -99999) {
                            if (D) Log.d("addEntry", "dummyData");
                            continue;
                        }

                        //divided by 1000 or 100
                        float yVal = (float) dataList.get(i) / DIV_VALUE;

                        if (D) {
                            if (i < 20) dataLog += yVal + ", ";
                            if (i == 20) dataLog += " ..... ";
                            if (dataList.size() - i < 20) dataLog += yVal + ", ";
                        }

                        data.addEntry(new Entry((float) xVal, yVal), dataset);

                        if (type == MeasureType.MEASURE_TYPE.SEM) {

                            for (int k = 0; k < MAX_MASK_SIZE; k++) {

                                int lowerPeakIndex = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getLowerPeakIndex(k);
                                int upperPeakIndex = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getUpperPeakIndex(k);

                                if (lowerPeakIndex == i) {
                                    int finalI = i;
                                    //org
                                    /*new Thread(() -> {
                                        mActivity.runOnUiThread(() -> {
                                            data.getDataSetByIndex(0).getEntryForIndex(finalI).setIcon(
                                                    mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
                                            );
                                        });
                                    }).start();*/
                                    //org

                                    //@@ [21.12.28] SEM Triangle blink
                                    /*data.getDataSetByIndex(0).getEntryForIndex(finalI).setIcon(
                                            mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
                                    );*/
                                    //@@

                                    if (maskData.getMaskOnOff(k) == SemEditMaskData.MASK_ON_OFF.ON) {
                                        data.getDataSetByIndex(0).getEntryForIndex(finalI).setIcon(
                                                mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
                                        );
                                    }
                                }

                                if (upperPeakIndex == i) {
                                    int finalI1 = i;
                                    //org
                                    /*new Thread(() -> {
                                        mActivity.runOnUiThread(() -> {
                                            data.getDataSetByIndex(0).getEntryForIndex(finalI1).setIcon(
                                                    mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
                                            );
                                        });
                                    }).start();*/
                                    //org

                                    //@@ [21.12.28] SEM Triangle blink
                                    /*data.getDataSetByIndex(0).getEntryForIndex(finalI1).setIcon(
                                            mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
                                    );*/
                                    //@@

                                    if (maskData.getMaskOnOff(k) == SemEditMaskData.MASK_ON_OFF.ON) {
                                        data.getDataSetByIndex(0).getEntryForIndex(finalI1).setIcon(
                                                mActivity.getResources().getDrawable(R.drawable.inverted_triangle)
                                        );
                                    }
                                }
                            }
                        }

//                    if (D) Log.d("addEntry", "MAX : " + yVal);
                        xVal += space;
                    }
                }

                if (D) Log.d("addEntry" + dataset, dataLog);
            }

//            if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK)
//                FunctionHandler.getInstance().getMainLineChart().autoScale();

            // TODO 화면 갱신 부분은 addEntryUpdate() 로 분리
            //invalidate();

            // let the chart know it's data has changed
            // TODO ? lineChart.moveViewToX(data.getEntryCount());

        } catch (Exception e) {
            //isDrawingChart = false;
            e.printStackTrace();
        }
//        }).start();
    }

    boolean isChartInvalidate = false;

    // data add 와 update 분리
    public void addEntryUpdate() {
        long time = System.currentTimeMillis();

        try {
            mainLineChart.notifyDataSetChanged();

            // TODO mix , max 값을 다시 찾는 것은 좀 문제 있음.
            moveToPeak();
            moveToMin();

            // TODO ?
            checkLimitLine();

            refreshMarkers();

            //Log.d("chart", "isDirty = " + mainLineChart.isDirty());
            mainLineChart.postInvalidate(); // mainLineChart.invalidate();
            //mActivity.runOnUiThread(() -> mainLineChart.invalidate());

//            mainLineChart.post(() -> {
//                if (isChartInvalidate) {
//                    Log.e("addEntryUpdate", "isChartInvalidate = " + isChartInvalidate);
//                }
//                isChartInvalidate = true;
//                long ct = System.currentTimeMillis();
//                mainLineChart.invalidate();
//                Log.d("addEntryUpdate", "mainLineChart.invalidate time = " + (System.currentTimeMillis() - ct));
//                isChartInvalidate = false;
//            });
            //mActivity.runOnUiThread(() -> mainLineChart.invalidate());
//            new Thread(() -> {
//                mainLineChart.invalidate();
//            }).start();


            //new Thread(() -> {
            ViewHandler.getInstance().getContent().markerTableUpdate();
            ViewHandler.getInstance().getContent().markerValueUpdate();
            //}).start();

            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
            if (mode == MeasureMode.MEASURE_MODE.SA) {
                // 오른쪽 메뉴에 Freq 값 변경
                ViewHandler.getInstance().getSaMarkerView2().updateValue();
            }

            // Auto Scale
            DataHandler handler = DataHandler.getInstance();
            boolean isSaAutoScale = handler.isSaAutoScale();

            if (isSaAutoScale && handler.getAutoScaleCount() == 0) {
                handler.setAutoScaleCount(1);
            } else if (isSaAutoScale && handler.getAutoScaleCount() == 1) {
                FunctionHandler.getInstance().getMainLineChart().autoScale();
                FunctionHandler.getInstance().getGateLineChart().autoScale();
                handler.setSaAutoScale(false);
                handler.setAutoScaleCount(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (D) Log.d("addEntryUpdate", "time = " + (System.currentTimeMillis() - time));

        isDrawingChart = false;
    }

    public Boolean isDataOnDisplay(int idx) {

        try {

            if (mainLineChart.getData().getDataSets().size() == 0)
                return false;
            else if (mainLineChart.getData().getDataSets().get(idx).getEntryCount() == 0)
                return false;
            else return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void update() {

        moveToPeak();
        moveToMin();
        updateType();

        FunctionHandler.getInstance().getMainLineChart().invalidate();

    }

    public void updateType() {

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (mode == MeasureMode.MEASURE_MODE.SA || mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {

//            if(mode == MeasureMode.MEASURE_MODE.SA && type == MeasureType.MEASURE_TYPE.INTERFERENCE_HUNTING) {
//                FunctionHandler.getInstance().getMainLineChart().setEnabledLimitLine(true);
//                FunctionHandler.getInstance().getMainLineChart().setLabel("");
//                FunctionHandler.getInstance().getMainLineChart().setEnabledLimitAlarm(false);
//            } else {
//                removeLimitLine();
//            }

            /*Test code*/
            FunctionHandler.getInstance().getMainLineChart().setEnabledLimitLine(true);
            FunctionHandler.getInstance().getMainLineChart().setLabel("");
            FunctionHandler.getInstance().getMainLineChart().setEnabledLimitAlarm(false);

            //@@ [22.01.27] 원전 Threshold
//            setEnabledLimitMsg(false);
            //@@
        } else {

            FunctionHandler.getInstance().getMainLineChart().setLimitType(
                    VswrDataHandler.getInstance().getConfigData().getLimitData().getUpper()
            );
            setEnabledLimitLine(true);
            setEnabledLimitMsg(true);

        }

    }

    public ArrayList<Integer> getDataList(int idx) {

        if (idx < 0 || idx >= mDataList.length) return null;

        return mDataList[idx];
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

//        checkLimitLine();
        new Handler(Looper.getMainLooper()).post(() -> {
            mainLineChart.notifyDataSetChanged();
            mainLineChart.invalidate();
        });
    }

    public void clearDataList() {

        for (int i = 0; i < MAX_TRACE_SIZE + 4; i++) {
            if (getDataList(i) != null)
                getDataList(i).clear();
        }

    }

    public void clearAllValues() {

        new Thread(() -> {

            for (int i = 0; i < MAX_TRACE_SIZE + 4; i++) {
                if (mainLineChart.getData().getDataSetByIndex(i) != null)
                    mainLineChart.getData().clearValues(i);

            }

            for (int i = 0; i < 4; i++) {

                if (getDataList(i) != null)
                    getDataList(i).clear();

            }

            new Handler(Looper.getMainLooper()).post(() -> {
                invalidate();
            });

        }).start();
    }

    public void clearValues(int idx) {
        if (idx < 0 || idx >= mainLineChart.getData().getDataSets().size())
            return;

        new Thread(() -> {

            new Handler(Looper.getMainLooper()).post(() -> {

                mainLineChart.getData().clearValues(idx);
                mainLineChart.notifyDataSetChanged();
                mainLineChart.invalidate();
            });

        }).start();
    }


    /*  */

    public void showChartData(int idx, Boolean on) {

        if (mDataList[idx].isEmpty()) return;

        Boolean isOn = on;

        if (!isOn) {
            clearValues(idx);
            return;
        }

        clearValues(idx);
        if (D) Log.d("dataListSize", mDataList[idx].size() + "");

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        addEntry(mDataList[idx], idx, mode, type, SaDataHandler.getInstance().getConfigData());
        // chart 데이터 모두 추가 후 갱신
        addEntryUpdate();

        //invalidate();
        return;

    }

    public void showAllChartData(Boolean on) {
        if (on) {
            for (int i = 0; i < MAX_TRACE_SIZE + 4; i++) { // +4 is recall data index
                showChartData(i, true);
            }
        } else {
            for (int i = 0; i < MAX_TRACE_SIZE; i++) {
                showChartData(i, false);
            }
        }
    }

    /*Set Min / Max value*/

    public void moveToPeak() {

        //new Thread(() -> {

        try {

            boolean isContinuous = false;

            for (int i = 0; i < MAX_MARKER_SIZE; i++) {

                if (getMarker(i) == null) continue;
//                    ArrayList<Integer> arrayList = getDataList(getMarker(i).getDataSetIndex());
//                    if (arrayList.size() == 0) continue;

                if (getMarker(i) != null && getMarker(i).isContinuous() && getMarker(i).isMarkerToPeak()) {

                    if (!isContinuous)
                        isContinuous = true;

                    int dataset = getMarker(i).getDataSetIndex();
                    IDataSet entryData = mainLineChart.getLineData().getDataSetByIndex(dataset);

                    float x = 0;
                    float y = entryData.getEntryForIndex(0).getY();

                    for (int j = 1; j < entryData.getEntryCount(); j++) {

                        float x2 = entryData.getEntryForIndex(j).getX();
                        Float y2 = null;
                        List entryList = entryData.getEntriesForXValue(x2);

                        for (int k = 0; k < entryList.size(); k++) {
                            float entryY = ((Entry) entryList.get(k)).getY();
                            if (D) Log.d("moveToPeak", entryY + " " + k);
                            if (y2 == null) {
                                y2 = entryY;
                            } else if (y2 < entryY) {
                                y2 = entryY;
                            }
                        }

                        if (y < y2) {
                            x = x2;
                            y = y2;
                        }

                    }

//                    if (D) Log.d("moveToPeak", getMarker().getDataSetIndex() + " " + binding.lineChartLayout.lineChart.getData().getDataSetByIndex(getMarker(i).getDataSetIndex()));
//                        float x = mainLineChart.getData().getDataSetByIndex(getMarker(i).getDataSetIndex()).getEntryForIndex(index).getX();
//                        float y = getYForX(x, dataset);

                    getMarker(i).setXY(x, y);

                }

            }

            if (isContinuous) {
                // ? ViewHandler.getInstance().getContent().subInfoUpdate();
                // ? ViewHandler.getInstance().getContent().markerIconUpdate();
                // ? ViewHandler.getInstance().getContent().markerTableUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //}).start();
    }

    public void moveToMin() {

        //new Thread(() -> {

        try {

            boolean isContinuous = false;

            for (int i = 0; i < MAX_MARKER_SIZE; i++) {

                if (getMarker(i) == null) continue;
//                    ArrayList<Integer> arrayList = getDataList(getMarker(i).getDataSetIndex());
//                    if (arrayList.size() == 0) continue;

                if (getMarker(i) != null && getMarker(i).isContinuous() && getMarker(i).isMarkerToMin()) {

                    if (!isContinuous)
                        isContinuous = true;

                    int dataset = getMarker(i).getDataSetIndex();
                    IDataSet entryData = mainLineChart.getLineData().getDataSetByIndex(dataset);

                    float x = 0;
                    float y = entryData.getEntryForIndex(0).getY();

                    for (int j = 1; j < entryData.getEntryCount(); j++) {

                        float x2 = entryData.getEntryForIndex(j).getX();
                        Float y2 = null;
                        List entryList = entryData.getEntriesForXValue(x2);

                        for (int k = 0; k < entryList.size(); k++) {
                            float entryY = ((Entry) entryList.get(k)).getY();
                            if (D) Log.d("moveToMin", entryY + " " + k);
                            if (y2 == null) {
                                y2 = entryY;
                            } else if (y2 > entryY) {
                                y2 = entryY;
                            }
                        }

                        if (y > y2) {
                            x = x2;
                            y = y2;
                        }

                    }

//                    if (D) Log.d("moveToPeak", getMarker().getDataSetIndex() + " " + binding.lineChartLayout.lineChart.getData().getDataSetByIndex(getMarker(i).getDataSetIndex()));
//                        float x = mainLineChart.getData().getDataSetByIndex(getMarker(i).getDataSetIndex()).getEntryForIndex(index).getX();
//                        float y = getYForX(x, dataset);

                    getMarker(i).setXY(x, y);

                }

            }

            if (isContinuous) {

//                    ViewHandler.getInstance().getContent().subInfoUpdate();
//                    ViewHandler.getInstance().getContent().markerIconUpdate();
//                    ViewHandler.getInstance().getContent().markerTableUpdate();
            }

        } catch (NullPointerException e) {

            e.printStackTrace();

        }

        //}).start();
    }

    public void setMarkerToPeak(Boolean peak) {

        if (getSelectedMarkerIndex() == -1) return;
        getMarker().setMarkerToPeak(peak);

    }

    public void setMarkerToMin(Boolean min) {

        if (getSelectedMarkerIndex() == -1) return;
        getMarker().setMarkerToMin(min);

    }

    public Boolean isMarkerToPeak() {
        if (getSelectedMarkerIndex() == -1) return false;
        return getMarker().isMarkerToPeak();

    }

    public Boolean isMarkerToMin() {
        if (getSelectedMarkerIndex() == -1) return false;
        return getMarker().isMarkerToMin();
    }




    /* Set DemodulationProfileView1 */

    public Highlight getMarker() {

        if (getSelectedMarkerIndex() == -1) return null;
        ArrayList<Highlight> markerList = mainLineChart.getHighlighted();
        if (markerList == null) return null;
        if (markerList.size() <= getSelectedMarkerIndex()) return null;

        Highlight high = markerList.get(getSelectedMarkerIndex());
        return high;

    }

    public Highlight getMarker(int idx) {

        if (idx < 0 || idx >= MAX_MARKER_SIZE) return null;
        ArrayList<Highlight> markerList = mainLineChart.getHighlighted();

        if (markerList == null) return null;
        if (markerList.size() <= idx) return null;

        Highlight high = markerList.get(idx);
        return high;

    }

    public void setMarker(int idx) {

        try {

            if (FunctionHandler.getInstance().getMeasureType().getType().equals(MeasureType.MEASURE_TYPE.CHANNEL_POWER)
                    || FunctionHandler.getInstance().getMeasureType().getType().equals(MeasureType.MEASURE_TYPE.OCCUPIED_BW)) {

                mSelectedMarkerTrace[idx] = 0;
                if (D) Log.d("setMarker", "set trace : " + mSelectedMarkerTrace[idx]);
            } else if (mainLineChart.getData().getDataSetByIndex(mSelectedMarkerTrace[idx]).getEntryCount() == 0) {
                if (D) Log.d("setMarker", "entry count is 0 : " + idx);

                // TODO
                boolean isFind = false;
                for (int i = 0; i < 4; ++i) {
                    if (mainLineChart.getData().getDataSetByIndex(i).getEntryCount() > 0) {
                        mSelectedMarkerTrace[idx] = i;
                        isFind = true;
                    }
                }

                if (!isFind) {
                    Toast.makeText(mActivity, mActivity.getResources().getString(R.string.not_available_trace), Toast.LENGTH_SHORT).show();
                    return;
                }
            } else if (getActiveDataListIndex() == -1) {
                if (D) Log.d("setMarker", "Active Data List Idx is -1 : " + idx);
                return;
            } else if (getSizeOfDataset() == 0) {
                if (D) Log.d("setMarker", "dataset length is 0");
                return;

            } else if (idx < 0 || idx > 4) {
                if (D) Log.d("setMarker", "out of range : " + idx);
                return;
            } else if (getMarker(idx) != null) {
                selectMarker(idx);
                mainLineChart.setSelectedHighlightIndex(idx);
                if (D) Log.d("setMarker", "create marker : " + idx);
                invalidate();
                return;
            } else if (isFixed[idx]) {
                mainLineChart.setSelectedHighlightIndex(idx);
                selectMarker(idx);
                if (D) Log.d("setMarker", "isFixed : " + idx);
                return;
            } else if (mSelectedMarkerTrace[idx] == -1 || !isVisible(mSelectedMarkerTrace[idx])) {
                if (D) Log.d("setMarker", "mSelectedMarkerTrace is -1 || !visible : " + idx);
                mSelectedMarkerTrace[idx] = getActiveDataListIndex();
            } else {

                if (D) Log.d("setMarker", "else.. : " + idx);

            }

            float x = getMarkerPos(idx)[0];

            if (D) Log.d("setMarker", "x value : " + x);
            Float y = getYForX(x, mSelectedMarkerTrace[idx]);

            if (y == null) {

                if (D) Log.d("setMarker", "Y value is NULL");
                return;
            }

            Highlight high = new Highlight(x, y, mSelectedMarkerTrace[idx]);
//        high.setDraw(1, 1);
            high.setFixed(isFixed[idx]);

            mainLineChart.addHighlightValue(idx, high);
            setRefMarkerIndex(idx, ((idx + 1) % 5));

            ArrayList<Highlight> markerList = mainLineChart.getHighlighted();
            if (markerList.size() <= idx) return;
            mainLineChart.getHighlighted().get(idx).setFixed(isFixed[idx]);

            invalidate();

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    public void selectMarker(int idx) {
        if (D) Log.d("selectMarker", "idx : " + idx);
        mainLineChart.setSelectedHighlightIndex(idx);
        invalidate();

    }

    public void updateMarkerPos() {

        for (int i = 0; i < MAX_MARKER_SIZE; i++) {

            if (isOnMarker(i)) {
                if (D) Log.d("updateMarkerPos", "index : " + i + " freq : " + mMarkerFreq[i]);
                setMarker(i, getMarkerTrace(i), mMarkerFreq[i]);

            }

        }

        invalidate();

    }

    /*APP에서 보여지는 Frequency로 적용함*/
    public void setMarker(int idx, int traceIdx, double freq) {

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        double span;
        double start;
        double end;

        if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL) {

            VswrConfigData data = VswrDataHandler.getInstance().getConfigData();
            VswrFrequencyData freqData = data.getFrequencyData();

            if (mode == MeasureMode.MEASURE_MODE.DTF) {

                start = 0d;
                end = data.getDistance();
                span = end - start;

            } else {

                start = freqData.getStartFreq();
                end = freqData.getStopFreq();
                span = freqData.getSpan();
            }

        } else if (mode == MeasureMode.MEASURE_MODE.SA) {

            SaConfigData data = SaDataHandler.getInstance().getConfigData();
            SAFrequencyData freqData = data.getFrequencyData();

            if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

                if (freqData.getSpan() == 0d) {

                    start = 0d;
                    end = data.getSweepTimeData().getSweepTimeToMs();
                    span = end;

                } else {

                    start = freqData.getStartFreq();
                    end = freqData.getStopFreq();
                    span = freqData.getSpan();

                }

            } else if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {

                start = 0;
                end = 10;
                span = 10;

            } else if (type == MeasureType.MEASURE_TYPE.SPURIOUS_EMISSION) {

                start = data.getSpuriousEmissionData().getMinStartFreq();
                end = data.getSpuriousEmissionData().getMaxStopFreq();
                span = end - start;

            } else {
                start = freqData.getStartFreq();
                end = freqData.getStopFreq();
                span = end - start;
            }

        } else return;

        double startSpace = freq - start;
        float absFreq = (float) (((double) mSpan * startSpace) / span);
        if (D)
            Log.d("setMarker", "absFreq : " + absFreq + " relFreq : " + freq + " start : " + start + " end : " + end);

        int trace = traceIdx;

        if (idx < 0 || idx > 4 || trace < 0 || trace > 3) {
            if (D) Log.d("setMarker", "Error 0");
            return;
        } else if (getSizeOfDataset() == 0) {
            if (D) Log.d("setMarker", "Error 1");
            return;
        } else if (isFixed[idx]) {
            selectMarker(idx);
            if (D) Log.d("setMarker", "Error 2");
            return;
        } else if (getActiveDataListIndex() == -1) {
            if (D) Log.d("setMarker", "Error 3");
            return;
        } else if (getDataList(trace).size() == 0) {
            trace = getActiveDataListIndex();
        }

        mMarkerFreq[idx] = freq;
        float x = absFreq;
        Float y = getYForX(x, trace);

        if (y == null) {
            if (D) Log.d("setMarker", "Error 4");
            return;
        }

        Highlight high = new Highlight(x, y, trace);
        high.setFixed(isFixed[idx]);
        if (getMarker(idx) != null) {
            if (getMarker(idx).isContinuous() != null)
                high.setContinuous(getMarker(idx).isContinuous());
            high.setMarkerToPeak(getMarker(idx).isMarkerToPeak());
            high.setMarkerToMin(getMarker(idx).isMarkerToMin());
        }
        mainLineChart.addHighlightValue(idx, high);
        mSelectedMarkerTrace[idx] = trace;
        if (D) Log.d("setMarker", "End");

//        invalidate();

    }

    /*내부 Frequency로 적용함(0 ~ 1000)*/
    public void setMarkerForAbs(int idx, int traceIdx, float freq) {

        int trace = traceIdx;

        if (idx < 0 || idx > 4 || trace < 0 || trace > 3) return;
        else if (getSizeOfDataset() == 0) return;
        else if (isFixed[idx]) {
            selectMarker(idx);
            return;
        } else if (getActiveDataListIndex() == -1)
            return;
        else if (getDataList(trace).size() == 0)
            trace = getActiveDataListIndex();

        mMarkerFreq[idx] = freq;
        float x = freq;
        Float y = getYForX(x, trace);

        if (y == null) return;

        Highlight high = new Highlight(x, y, trace);
        high.setFixed(isFixed[idx]);
        if (getMarker(idx) != null) {
            if (getMarker(idx).isContinuous() != null)
                high.setContinuous(getMarker(idx).isContinuous());
            high.setMarkerToPeak(getMarker(idx).isMarkerToPeak());
            high.setMarkerToMin(getMarker(idx).isMarkerToMin());
        }
        mainLineChart.addHighlightValue(idx, high);
        mSelectedMarkerTrace[idx] = trace;

//        invalidate();

    }

    public void setMarkerFreq(double freq) {

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        double span;
        double start;
        double end;

        if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL) {

            VswrConfigData data = VswrDataHandler.getInstance().getConfigData();
            VswrFrequencyData freqData = data.getFrequencyData();

            if (mode == MeasureMode.MEASURE_MODE.DTF) {

                start = 0d;
                end = data.getDistance();
                span = end - start;

            } else {

                freq /= 1000000;

                start = freqData.getStartFreq();
                end = freqData.getStopFreq();
                span = freqData.getSpan();
            }

        } else if (mode == MeasureMode.MEASURE_MODE.SA) {

            SaConfigData data = SaDataHandler.getInstance().getConfigData();
            SAFrequencyData freqData = data.getFrequencyData();

            if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

                if (freqData.getSpan() == 0d) {

                    start = 0d;
                    end = data.getSweepTimeData().getSweepTimeToMs();
                    span = end;

                } else {

                    start = freqData.getStartFreq();
                    end = freqData.getStopFreq();
                    span = freqData.getSpan();

                }

            } else if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {

                start = 0;
                end = 10;
                span = 10;

            } else {
                start = freqData.getStartFreq();
                end = freqData.getStopFreq();
                span = end - start;
            }

        } else return;

        double startSpace = freq - start;
        float absFreq = (float) ((mSpan * startSpace) / span);

        int markerIdx = mainLineChart.getSelectedHighlightIndex();

        float xMin = mainLineChart.getXAxis().getAxisMinimum();
        float xmax = mainLineChart.getXAxis().getAxisMaximum();

        if (absFreq < xMin || absFreq > xmax)
            return;

        if (markerIdx == -1) {
            markerIdx = 0;
        }

        if (D) Log.d("setMarkerFreq", markerIdx + " " + absFreq);

        mMarkerFreq[markerIdx] = freq;
        float x = absFreq;
        Float y = getYForX(x, mSelectedMarkerTrace[markerIdx]);

        if (y == null) return;

        Highlight high = new Highlight(x, y, getActiveDataListIndex());
        high.setFixed(isFixed[markerIdx]);
        high.setContinuous(getMarker(markerIdx).isContinuous());
        high.setMarkerToPeak(getMarker(markerIdx).isMarkerToPeak());
        high.setMarkerToMin(getMarker(markerIdx).isMarkerToMin());

        mainLineChart.addHighlightValue(markerIdx, high);
        mSelectedMarkerTrace[markerIdx] = getActiveDataListIndex();

//        setMarker(markerIdx, getMarker().getDataSetIndex(), absFreq);

        invalidate();

    }

    public double getMarkerFreq(int idx) {

        return mMarkerFreq[idx];

    }

    public double getMarkerFreq() {

        int idx = mainLineChart.getSelectedHighlightIndex();

        return getMarkerFreq(idx);

    }

    public void removeMarker(int idx) {

        if (idx < 0 || idx >= MAX_MARKER_SIZE || getMarker(idx) == null) return;

        resetMarkOption(idx);

        mainLineChart.removeHighlightValue(idx);

    }

    public void removeMarkerOfTrace(int traceIndex) {
        for (int i = 0; i < 5; ++i) {
            if (mSelectedMarkerTrace[i] == traceIndex && isOnMarker(i)) {
                removeMarker(i);
            }
        }
    }

    public void removeSelectedMarker() {

        resetMarkOption();
        mainLineChart.getLineData().notifyDataChanged();
//        invalidate();
        mainLineChart.removeHighlightValue(mainLineChart.getSelectedHighlightIndex());

    }

    public void removeAllMarkers() {

        resetAllMarkOption();
        mainLineChart.removeAllHighlightValue();

    }

    public int getSelectedMarkerIndex() {

        int idx = mainLineChart.getSelectedHighlightIndex();
        return idx;

    }

    public int getMarkerTrace() {

        if (mainLineChart.getSelectedHighlightIndex() == -1) return -1;

        return mSelectedMarkerTrace[getSelectedMarkerIndex()];

//
//        if (isFixed[binding.lineChartLayout.lineChart.getSelectedHighlightIndex()])
//            return mSelectedMarkerTrace[binding.lineChartLayout.lineChart.getSelectedHighlightIndex()];
//
//        return getSaMarkerView().getDataSetIndex();
    }

    public int getMarkerTrace(int idx) {

        if (idx < 0 || idx >= MAX_MARKER_TRACE_SIZE) return -1;

        if (isFixed[idx])
            return mSelectedMarkerTrace[idx];

        return mSelectedMarkerTrace[idx];

    }

    public void setNormalMarker(Boolean isNormal) {
        //current marker to normal

        if (getSelectedMarkerIndex() == -1) return;

        getMarker().setContinuous(false);
        getMarker().setMarkerToMin(false);
        getMarker().setMarkerToPeak(false);
        getMarker().setDelta(false);
        getMarker().setFixed(false);

    }


    public Boolean isNormalMarker() {
        return isNormal[getSelectedMarkerIndex()];
    }

    public Boolean isNormalMarker(int idx) {

        if (idx < 0 || idx >= MAX_MARKER_SIZE) return false;

        return isNormal[idx];

    }

    public void setFixedMarker(Boolean fix) {
        //current marker to Fixed
        //현재 위치에서 더이상 이동하지 않도록

        try {

            if (getSelectedMarkerIndex() == -1) return;
            ArrayList<Highlight> markerList = mainLineChart.getHighlighted();
            if (markerList == null) return;
            if (markerList.size() <= getSelectedMarkerIndex()) return;

            isFixed[getSelectedMarkerIndex()] = fix;
            mainLineChart.getHighlighted().get(getSelectedMarkerIndex()).setFixed(fix);
            getMarker().setDataSetIndex(getMarkerTrace());
            refreshMarkers();
            invalidate();

        } catch (NullPointerException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }


    public void setFixedMarker(int idx, Boolean fix) {

        try {

            if (idx < 0 || idx >= MAX_MARKER_SIZE) return;
            ArrayList<Highlight> markerList = mainLineChart.getHighlighted();
            if (markerList == null) return;
            if (markerList.size() <= idx) return;

            isFixed[idx] = fix;
            markerList.get(idx).setFixed(fix);
            getMarker(idx).setDataSetIndex(getMarkerTrace());
            refreshMarkers();
            invalidate();

        } catch (NullPointerException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    public Boolean isFixed() {

        try {

            if (getSelectedMarkerIndex() == -1) return false;
            return isFixed[getSelectedMarkerIndex()];

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean isFixed(int idx) {

        if (idx < 0 || idx >= MAX_MARKER_SIZE) return false;

        return isFixed[idx];
    }

    public void setMarkerTrace(int trace) {

        //selected marker by index move to chart trace by idx
        if (trace < 0 || trace > 3) return;
        if (mainLineChart.getData().getDataSetByIndex(trace).getEntryCount() == 0) {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.not_available_trace), Toast.LENGTH_SHORT).show();
            return;
        }
        int markerIdx = getSelectedMarkerIndex();
        if (getMarker() == null) return;
        if (markerIdx == -1) markerIdx = 0;
        if (getActiveDataListIndex() == -1) return;
        if (!isVisible(trace)) trace = getActiveDataListIndex();

        if (getMarker().isFixed()) {

            mSelectedMarkerTrace[markerIdx] = trace;

        } else {

            mSelectedMarkerTrace[markerIdx] = trace;
            getMarker().setDataSetIndex(trace);

        }

        refreshMarkers();
        mainLineChart.getLineData().notifyDataChanged();
//        invalidate();

    }

    public void setMarkerTrace(int trace, int idx) {

        //selected marker by index move to chart trace by idx
        if (idx < 0 || idx >= MAX_MARKER_SIZE) return;
        if (trace < 0 || trace > 3) return;
        if (getMarker(idx) == null) {
            mSelectedMarkerTrace[idx] = trace;
            return;
        }

        if (getDataList(trace).isEmpty()) trace = getActiveDataListIndex();

        if (getMarker().isFixed()) {

            mSelectedMarkerTrace[idx] = trace;

        } else {

            mSelectedMarkerTrace[idx] = trace;
            getMarker().setDataSetIndex(trace);

        }

        refreshMarkers();
//        invalidate();

        mainLineChart.getLineData().notifyDataChanged();
    }


    private void resetMarkOption(int idx) {

        if (idx < 0 || idx >= MAX_MARKER_SIZE) return;
        if (getMarker(idx) == null) return;

//        mSelectedMarkerTrace[idx] = getActiveDataListIndex();
        isFixed[idx] = false;
        isDelta[idx] = false;
        getMarker(idx).setContinuous(false);
        getMarker(idx).setFixed(false);
        getMarker(idx).setDelta(false);

        for (int i = 0; i < MAX_MARKER_SIZE; i++) {

            if (getMarker(i) == null) continue;
            if (idx == getRefMarkerIndex(i)) {
                getMarker(i).setDelta(false);
                isDelta[i] = getMarker(i).isDelta();
            }

        }

//        invalidate();
    }

    public void resetMarkOption() {

        if (getSelectedMarkerIndex() == -1) {
            for (int i = 0; i < MAX_MARKER_SIZE; i++) {

                if (getMarker(i) == null) {
                    setMarker(i);
                    mainLineChart.setSelectedHighlightIndex(i);
                    break;
                }

            }
        }

        resetMarkOption(getSelectedMarkerIndex());

    }

    private void resetAllMarkOption() {

        for (int i = 0; i < MAX_MARKER_SIZE; i++) {

            resetMarkOption(i);

        }

    }

    public float[] getMarkerPos(int idx) {

        try {

            float x, y;

            if (getMarker(idx).isFixed()) {

                x = getMarker(idx).getFixedX();
                y = getMarker(idx).getFixedY();
                //Log.e("Marker", "Passed here?(getmarker.isFixed) : idx: " + idx + ", x : " + x);
            } else if (isDelta[idx]) {

                float cx = mainLineChart.getData().getEntryForHighlight(
                        mainLineChart.getHighlighted().get(idx)).getX();

                float cy = mainLineChart.getData().getEntryForHighlight(
                        mainLineChart.getHighlighted().get(idx)).getY();

                float rx = mainLineChart.getData().getEntryForHighlight(
                        mainLineChart.getHighlighted().get(mRefMarkIndex[idx])).getX();

                float ry = mainLineChart.getData().getEntryForHighlight(
                        mainLineChart.getHighlighted().get(mRefMarkIndex[idx])).getY();

                if (getMarker(mRefMarkIndex[idx]).isFixed()) {
                    rx = getMarker(mRefMarkIndex[idx]).getFixedX();
                    ry = getMarker(mRefMarkIndex[idx]).getFixedY();
                }

                x = rx - cx;
                y = ry - cy;
                //Log.e("Marker", "Passed here?(isDelta[idx]) : idx: " + idx + ", x : " + x);
            } else {

                x = mainLineChart.getData().getEntryForHighlight(
                        mainLineChart.getHighlighted().get(idx)).getX();

                y = mainLineChart.getData().getEntryForHighlight(
                        mainLineChart.getHighlighted().get(idx)).getY();
                //Log.e("Marker", "Passed here?(else) : idx: " + idx + ", x : " + x);
            }

            float[] pos = {x, y};

            return pos;

        } catch (NullPointerException | IndexOutOfBoundsException e) {

//            float[] pos = {mCenterFreq, mainLineChart.getData().getDataSets().get(0).getEntryForIndex(
//                    mainLineChart.getData().getDataSetByIndex(0).getEntryCount()/2).getY()};
            //org
            float[] pos = {mCenterX, 0};
            //org

            //@@ [21.12.17] delta 인 경우 selected marker 의 x 좌표에 생성
            if (FunctionHandler.getInstance().getMainLineChart().isDelta()) {

                for (int i = 0; i < MAX_MARKER_SIZE; i++) {

                    if (getMarker(i) == null) {
//                if (D) Log.d("Marker", "null" + i);
                        continue;
                    } else if (getMarker(i).isFixed()) {
                        continue;
                    }

                    ILineDataSet dataset = mainLineChart.getData().getDataSetByIndex(getMarker(i).getDataSetIndex());
                    if (dataset.getEntryCount() == 0) continue;

//            if (D) Log.d("Marker", "Marker index : " + getMarker(i).getDataSetIndex() + " entry count : " + (dataset.getEntryCount()/2-1)
//                    + " Entry : " +  dataset.getEntryForIndex(dataset.getEntryCount()/2-1));
                    Entry entry = dataset.getEntryForIndex(dataset.getEntryIndex(getMarker(i).getX(), 1f, DataSet.Rounding.CLOSEST));
                    float x = entry.getX();

                    List entryList = mainLineChart.getData().getDataSetByIndex(getMarker(i).getDataSetIndex()).getEntriesForXValue(x);

                    int size = entryList.size();
                    if (size == 0) {
//                if (D) Log.d("Marker", "size = 0 idx : " + i + " dataset idx : " + getMarker(i).getDataSetIndex());
                        continue;
                    }

                    float y = mainLineChart.getData().getDataSetByIndex(getMarker(i).getDataSetIndex()).getEntriesForXValue(x).get(0).getY();

                    for (int k = 0; k < size; k++) {

                        float y2 = ((Entry) entryList.get(k)).getY();
                        if (y < y2) y = y2;

                    }
                    pos[0] = x;
                    pos[1] = y;
                }
            }
            //@@
            return pos;

        }

    }

    public float[] getMarkerPos() {

        try {

            float x, y;

            if (getMarker().isFixed()) {

                x = getMarker().getFixedX();
                y = getMarker().getFixedY();

            } else if (isDelta[getSelectedMarkerIndex()]) {

                float cx = mainLineChart.getData().getEntryForHighlight(
                        mainLineChart.getHighlighted().get(getSelectedMarkerIndex())).getX();

                float cy = mainLineChart.getData().getEntryForHighlight(
                        mainLineChart.getHighlighted().get(getSelectedMarkerIndex())).getY();

                float rx = mainLineChart.getData().getEntryForHighlight(
                        mainLineChart.getHighlighted().get(mRefMarkIndex[getSelectedMarkerIndex()])).getX();

                float ry = mainLineChart.getData().getEntryForHighlight(
                        mainLineChart.getHighlighted().get(mRefMarkIndex[getSelectedMarkerIndex()])).getY();

                if (getMarker(mRefMarkIndex[getSelectedMarkerIndex()]).isFixed()) {
                    rx = getMarker(mRefMarkIndex[getSelectedMarkerIndex()]).getFixedX();
                    ry = getMarker(mRefMarkIndex[getSelectedMarkerIndex()]).getFixedY();
                }

                x = rx - cx;
                y = ry - cy;

            } else {

                x = mainLineChart.getData().getEntryForHighlight(
                        mainLineChart.getHighlighted().get(mainLineChart.getSelectedHighlightIndex())).getX();

                y = mainLineChart.getData().getEntryForHighlight(
                        mainLineChart.getHighlighted().get(mainLineChart.getSelectedHighlightIndex())).getY();

//                if (D) Log.d("getMarkerPos", "selectedIndex : " + mainLineChart.getSelectedHighlightIndex());
            }


            float[] pos = {x, y};

            return pos;

        } catch (NullPointerException | IndexOutOfBoundsException e) {

            float[] pos = {mStartX + (mSpan / (DATA_POINT.P1001.getDataPoint() - 1)), 0};
            return pos;

        }

    }


    public boolean isOnMarker(int idx) {
        if (idx < 0) return false;

        ArrayList<Highlight> markerList = mainLineChart.getHighlighted();
        if (markerList == null) return false;
        if (markerList.size() <= idx) return false;

        return mainLineChart.getHighlighted().get(idx) != null;
    }

    public boolean isOnMarker() {
        ArrayList<Highlight> markerList = mainLineChart.getHighlighted();

        if (markerList == null) return false;

        for (int i = 0; i < mainLineChart.getHighlighted().size(); i++) {
            if (markerList.size() <= i) return false;
            if (markerList.get(i) != null) return true;
        }

        return false;
    }

    public void setRefMarkerIndex(int marker, int idx) {

        if (idx < 0 || idx >= MAX_MARKER_SIZE || idx == getSelectedMarkerIndex()) return;
        else if (marker < 0 || marker >= MAX_MARKER_SIZE) return;

        mRefMarkIndex[marker] = idx;

    }

    public void setRefMarkerIndex(int idx) {

        if (idx < 0 || idx >= MAX_MARKER_SIZE || idx == getSelectedMarkerIndex() || getSelectedMarkerIndex() == -1)
            return;

        mRefMarkIndex[getSelectedMarkerIndex()] = idx;

    }

    public int getRefMarkerIndex() {
        return mRefMarkIndex[getSelectedMarkerIndex()];
    }

    public int getRefMarkerIndex(int idx) {

        if (idx < 0 || idx >= MAX_MARKER_SIZE) return -1;

        return mRefMarkIndex[idx];
    }

    public ArrayList<Highlight> getMarkerList() {
        ArrayList<Highlight> list = mainLineChart.getHighlighted();
        if (D) Log.d("getMarkerList", list.size() + "");
        return list;
    }

    public void setContinuous(Boolean con) {

        getMarker().setContinuous(con);

    }

    public void refreshMarkers() {

        //new Thread(() -> {

        for (int i = 0; i < MAX_MARKER_SIZE; i++) {

            if (getMarker(i) == null) {
//                if (D) Log.d("Marker", "null" + i);
                continue;
            } else if (getMarker(i).isFixed()) {
                continue;
            }

            ILineDataSet dataset = mainLineChart.getData().getDataSetByIndex(getMarker(i).getDataSetIndex());
            if (dataset.getEntryCount() == 0) continue;

//            if (D) Log.d("Marker", "Marker index : " + getMarker(i).getDataSetIndex() + " entry count : " + (dataset.getEntryCount()/2-1)
//                    + " Entry : " +  dataset.getEntryForIndex(dataset.getEntryCount()/2-1));
            Entry entry = dataset.getEntryForIndex(dataset.getEntryIndex(getMarker(i).getX(), 1f, DataSet.Rounding.CLOSEST));
            float x = entry.getX();

            List entryList = mainLineChart.getData().getDataSetByIndex(getMarker(i).getDataSetIndex()).getEntriesForXValue(x);

            int size = entryList.size();
            if (size == 0) {
//                if (D) Log.d("Marker", "size = 0 idx : " + i + " dataset idx : " + getMarker(i).getDataSetIndex());
                continue;
            }

            float y = mainLineChart.getData().getDataSetByIndex(getMarker(i).getDataSetIndex()).getEntriesForXValue(x).get(0).getY();

            for (int k = 0; k < size; k++) {

                float y2 = ((Entry) entryList.get(k)).getY();
                if (y < y2) y = y2;

            }

            getMarker(i).setXY(x, y);

        }

        //}).start();

    }

    public void updateGrid() {

        //X축 값 텍스트
        XAxis xl = mainLineChart.getXAxis();
        //왼쪽 Y값 텍스트
        YAxis leftAxis = mainLineChart.getAxisLeft();

        leftAxis.setDrawGridLines(true);
        leftAxis.setLabelCount(NUMBER_OF_GRID_LINE, true);

        //격자 설정
        xl.setDrawGridLines(true);
        xl.setLabelCount(NUMBER_OF_GRID_LINE, true);

        invalidate();

    }

    // deprecated
//    public void refreshAllData() {
//
//        new Thread(() -> {
//
//            mActivity.runOnUiThread(() -> {
//
//                clearAllValues();
//
////        for (int i = 4; i < mDataList.length; i++) {
////
////            addEntry(getDataList(i), i);
////
////        }
//
//                for (int i = 0; i < 4; i++) {
//
//                    int finalI = i;
//
//                    addEntry(getDataList(finalI), finalI);
//                }
//
////                mainLineChart.notifyDataSetChanged();
////                mainLineChart.invalidate();
//
//            });
//
//        }).start();
//
//    }


    /* get Y using X Pos */

    public Float getYForX(float x, int trace) {

        if (trace < 0 || trace >= MAX_MARKER_TRACE_SIZE || mainLineChart.getData().getEntryCount() == 0)
            return null;

        try {

            Float y = mainLineChart.getData().getDataSetByIndex(trace).getEntryForXValue(x, Float.NaN).getY();

            if (y == null) return null;

            return y;
        } catch (NullPointerException e) {
            if (D) Log.d("MainLineChart", "getYForX : Except");
        }

        return null;
    }


    public int getSizeOfDataset() {

        int cnt = mainLineChart.getData().getDataSets().size();
        return cnt;
    }


    /* Set Delta */

    public void setDeltaMarker(Boolean delta) {

        try {

            if (getSelectedMarkerIndex() == -1) {

                if (delta) {
                    for (int i = 0; i < MAX_MARKER_SIZE; i++) {

                        if (getMarker(i) == null) {
                            setMarker(i);
                            mainLineChart.setSelectedHighlightIndex(i);
                            break;
                        }

                    }
                } else return;
            }

            this.isDelta[getSelectedMarkerIndex()] = delta;

            if (delta) {

                getMarker().setDelta(true);
                int currentRefIndex = mRefMarkIndex[getSelectedMarkerIndex()];

                if (currentRefIndex == -1 || currentRefIndex == getSelectedMarkerIndex())
                    mRefMarkIndex[getSelectedMarkerIndex()] = (getSelectedMarkerIndex() + 1) % 5;

                getMarker().setMessage((getSelectedMarkerIndex() + 1) + "Δ" + (getRefMarkerIndex() + 1));

                if (getMarker(currentRefIndex) == null) setMarker(currentRefIndex);

                setFixedMarker(currentRefIndex, true);

//            invalidate();

            } else {

                getMarker().setDelta(false);
//            invalidate();

            }

            mainLineChart.getLineData().notifyDataChanged();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setDeltaMarkerNotFixed(Boolean delta) {

        if (getSelectedMarkerIndex() == -1) return;

        this.isDelta[getSelectedMarkerIndex()] = delta;

        if (delta) {

            getMarker().setDelta(true);
            int currentRefIndex = 0;

            mRefMarkIndex[getSelectedMarkerIndex()] = currentRefIndex;

            getMarker().setMessage((getSelectedMarkerIndex() + 1) + "Δ" + (getRefMarkerIndex() + 1));

            if (getMarker(currentRefIndex) == null) setMarker(currentRefIndex);

//            invalidate();

        } else {

            getMarker().setDelta(false);
//            invalidate();

        }

        mainLineChart.getLineData().notifyDataChanged();

    }

    public void setDeltaMarker(Boolean delta, int idx) {

        this.isDelta[idx] = delta;

        if (delta) {

            getMarker().setDelta(true);
            int currentRefIndex = mRefMarkIndex[idx];

            if (currentRefIndex == -1 || currentRefIndex == idx)
                mRefMarkIndex[idx] = (idx + 1) % 5;

            getMarker().setMessage((idx + 1) + "Δ" + (getRefMarkerIndex() + 1));

            if (getMarker(currentRefIndex) == null) setMarker(currentRefIndex);
            setFixedMarker(currentRefIndex, true);

//            invalidate();

        } else {

            getMarker().setDelta(false);
//            invalidate();

        }

        mainLineChart.getLineData().notifyDataChanged();

    }

    public Boolean isDelta() {

        if (getSelectedMarkerIndex() == -1) return false;

        return isDelta[getSelectedMarkerIndex()];

    }

    public Boolean isDelta(int idx) {
        if (idx < 0 || idx >= MAX_MARKER_SIZE) return false;

        return isDelta[idx];
    }

    public void setBlueZone(double integ) {
        setBlueZone(integ, true);
    }

    public void setBlueZone(double integ, boolean isInvalidate) {

        //MainActivity.getActivity().runOnUiThread(() -> {

        SaConfigData configData = SaDataHandler.getInstance().getConfigData();
//            ChannelPowerMeasSetupData data = configData.getChannelPowerMeasSetupData();

        double span = configData.getFrequencyData().getSpan();
        double inputInteg = integ;
        if (inputInteg > span) inputInteg = span;
        double width = (mSpan * inputInteg) / span;

        float high = getCenterFreq() + (float) width / 2;
        high = (float) (Math.round(high * 100) / 100);

        float low = getCenterFreq() - (float) width / 2;
        low = (float) (Math.round(low * 100) / 100);

        double x = mainLineChart.getRendererXAxis().getTransformer().getPixelForValues(low, 0f).x;
        double x2 = mainLineChart.getRendererXAxis().getTransformer().getPixelForValues(high, 0f).x;
        float widthOfPx = (float) (x2 - x);

        if (D) Log.d("Blue zone", "===============================");
        if (D) Log.d("Blue zone", "low : " + low + " high : " + high);
        if (D) Log.d("Blue zone", "low pixel : " + x + " high pixel : " + x2);
        if (D) Log.d("Blue zone", "width : " + (high - low) + " width(px) : " + widthOfPx);
        if (D) Log.d("Blue zone", "===============================");

        LimitLine line = new LimitLine(getCenterFreq());
        line.setLineWidth(widthOfPx);

        //@@ [21.12.17] 가시성 관련 색상 조정
        //line.setLineColor(Color.argb(65, 0, 125, 0));
        line.setLineColor(Color.argb(90, 070, 163, 210));
        //@@

        XAxis axis = mainLineChart.getXAxis();
        axis.removeAllLimitLines();
        //        axis.setDrawLimitLinesBehindData(true);

        axis.addLimitLine(line);

        if (isInvalidate) {
            mainLineChart.notifyDataSetChanged();
            mainLineChart.postInvalidate();
        }

        //invalidate();
        //});
    }

    public void removeBlueZone() {

        mainLineChart.getXAxis().removeAllLimitLines();

    }

    public void setInverted(Boolean on) {

        mainLineChart.getAxisLeft().setInverted(on);
        if (D) Log.d("setInverted", on + "");

    }

    public void autoScale() throws NullPointerException {

        if (mainLineChart.getData().getDataSetByIndex(0).getEntryCount() == 0)
            return;

//        mActivity.runOnUiThread(() -> {

        try {

            Float maxY = getChartDataYMax();
            Float minY = getChartDataYMin();

            if (maxY == null || minY == null) {
                return;
            }
            if (Float.isNaN(maxY) || Float.isNaN(minY)) {
                return;
            }


            float space = (maxY - minY) * 0.2f;
            float maxRangeY = 0;
            float minRangeY = 0;

//            if (D) Log.d("Max Y", maxY + "");
//            if (D) Log.d("MaxRangeY", maxRangeY + "");

            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

            if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

                if (maxY.equals(minY)) {
                    maxY = maxY + (maxY * 0.1f);
                    minY = minY - (minY * 0.1f);
                    space = (maxY - minY) * 0.2f;
                }

                maxRangeY = maxY + space;
                minRangeY = minY - space;
                if (Float.isNaN(maxRangeY) || Float.isNaN(minRangeY)) return;

                if (minRangeY < 1) minRangeY = 1;
                if (maxRangeY > 65) maxRangeY = 65;

                mainLineChart.getAxisLeft().setAxisMaximum(maxRangeY);
                mainLineChart.getAxisLeft().setAxisMinimum(minRangeY);
                invalidate();

                VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMaxForVswr(
                        FunctionHandler.getInstance().getMainLineChart().getMaxYRange()
                );

                VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMinForVswr(
                        FunctionHandler.getInstance().getMainLineChart().getMinYRange()
                );


//                        if (!isAutoReportMode)
//                            setLimitValue((minRangeY + maxRangeY) / 2f);
                ViewHandler.getInstance().getAmplitudeView().update();

            } else if (type == MeasureType.MEASURE_TYPE.RL || type == MeasureType.MEASURE_TYPE.DTF_RL || type == MeasureType.MEASURE_TYPE.CABLE_LOSS) {

                if (maxY.equals(minY)) {
                    maxY = maxY + (maxY * 0.1f);
                    minY = minY - (minY * 0.1f);
                    space = (maxY - minY) * 0.2f;
                }

                maxRangeY = maxY + space;
                minRangeY = minY - space;

                if (Float.isNaN(maxRangeY) || Float.isNaN(minRangeY)) return;

                if (minRangeY < 0) minRangeY = 0;
                if (maxRangeY > 60) maxRangeY = 60;


                mainLineChart.getAxisLeft().setAxisMaximum(maxRangeY);
                mainLineChart.getAxisLeft().setAxisMinimum(minRangeY);
                invalidate();

                VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMaxForRl(
                        FunctionHandler.getInstance().getMainLineChart().getMaxYRange()
                );

                VswrDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMinForRl(
                        FunctionHandler.getInstance().getMainLineChart().getMinYRange()
                );

//                        setLimitValue((minRangeY + maxRangeY) / 2f);
                ViewHandler.getInstance().getAmplitudeView().update();

            } else if (mode == MeasureMode.MEASURE_MODE.SA) {

                Float middle = (maxY + minY) / 2;
                float refLev = minY + 80;

                if (Float.isNaN(maxRangeY) || Float.isNaN(minRangeY)) return;

                setScaleDiv(10f);
                SaDataHandler.getInstance().getConfigData().getAmplitudeData().setScaleDiv(10f);

                setRefLev((float) ((int) refLev));
                SaDataHandler.getInstance().getConfigData().getAmplitudeData().setRefLev((float) ((int) refLev));
                FunctionHandler.getInstance().getGateLineChart().setRefLev((float) ((int) refLev));
//                if(gateData.getGateMode() == SaGateData.GATE_MODE.ON || gateData.getGateView() == SaGateData.GATE_MODE.ON)

                ViewHandler.getInstance().getSAAmplitudeView().update();
                ViewHandler.getInstance().getContent().subInfoUpdate();
//                DataHandler.getInstance().setAutoAtten(true);

                mActivity.stopLoadingAnim();

            }

            if (D) Log.d("autoScale", maxY + " " + minY);

//        mainLineChart.getData().notifyDataChanged();
            mainLineChart.notifyDataSetChanged();
            updateGrid();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void setMinYRange(float value) {

        mainLineChart.getAxisLeft().setAxisMinimum(value);

    }

    public void setMaxYRange(float value) {

        mainLineChart.getAxisLeft().setAxisMaximum(value);

    }

    public Float getMaxYRange() {

        return mainLineChart.getAxisLeft().getAxisMaximum();
    }

    public Float getMinYRange() {

        return mainLineChart.getAxisLeft().getAxisMinimum();

    }

    /*Limit Line Function */

    public void checkLimitLine() throws NullPointerException {

        if (mainLineChart.getAxisLeft().getLimitLines().size() == 0 ||
                mainLineChart.getData() == null) return;

        ArrayList<Entry> limitLineValues = new ArrayList<>();
        int entryCount;

        if (mainLineChart.getData().getDataSetByIndex(mChartDataSetIndex) != null)
            entryCount = mainLineChart.getData().getDataSetByIndex(mChartDataSetIndex).getEntryCount();
        else entryCount = 0;

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

        //@@ [22.01.27] 원전모니터링 Threshold 관련
        if (mode != MeasureMode.MEASURE_MODE.VSWR && mode != MeasureMode.MEASURE_MODE.DTF && mode != MeasureMode.MEASURE_MODE.CL && mode != MeasureMode.MEASURE_MODE.SA) {

            if (type == MeasureType.MEASURE_TYPE.INTERFERENCE_HUNTING) {
                mainLineChart.getAxisLeft().getLimitLines().clear();
                LimitLine limit = new LimitLine(mLimitValue, mLabel);
                limit.setLineWidth(5);

                limit.setLineColor(Color.rgb(0, 255, 0));
                limit.setTextColor(Color.rgb(0, 255, 0));
                limit.setTextSize(20);

                mLimitDataSet = (LineDataSet) mainLineChart.getLineData().getDataSetByIndex(LIMIT_DATASET_INDEX);
                mLimitDataSet.setColor(Color.GREEN);
                mLimitDataSet.setValues(limitLineValues);

                mainLineChart.getAxisLeft().addLimitLine(limit);
                mainLineChart.getData().setDataSet(LIMIT_DATASET_INDEX, mLimitDataSet);

                if (mainLineChart.getData().getDataSetByIndex(LIMIT_DATASET_INDEX) != null)
                    mainLineChart.getData().getDataSetByIndex(LIMIT_DATASET_INDEX).setHighlightEnabled(false);

                ViewHandler.getInstance().getInterferenceHuntingView().updateThreshold();
            }

            /*Test code*/
            mainLineChart.getAxisLeft().getLimitLines().clear();
            LimitLine limit = new LimitLine(mLimitValue, mLabel);
            limit.setLineWidth(5);

            limit.setLineColor(Color.rgb(0, 255, 0));
            limit.setTextColor(Color.rgb(0, 255, 0));
            limit.setTextSize(20);

            mLimitDataSet = (LineDataSet) mainLineChart.getLineData().getDataSetByIndex(LIMIT_DATASET_INDEX);
            mLimitDataSet.setColor(Color.GREEN);
            mLimitDataSet.setValues(limitLineValues);

            mainLineChart.getAxisLeft().addLimitLine(limit);
            mainLineChart.getData().setDataSet(LIMIT_DATASET_INDEX, mLimitDataSet);

            if (mainLineChart.getData().getDataSetByIndex(LIMIT_DATASET_INDEX) != null)
                mainLineChart.getData().getDataSetByIndex(LIMIT_DATASET_INDEX).setHighlightEnabled(false);

            return;
        }

        for (int i = 1; i < entryCount; i++) {

            Entry value1Entry = mainLineChart
                    .getData()
                    .getDataSetByIndex(mChartDataSetIndex)
                    .getEntryForIndex(i - 1);

            Float valueY1 = null;
            Float valueX1 = null;
            if (value1Entry != null) {
                valueX1 = value1Entry.getX();
                valueY1 = value1Entry.getY();
            } else return;

            Entry value2Entry = mainLineChart
                    .getData()
                    .getDataSetByIndex(mChartDataSetIndex)
                    .getEntryForIndex(i);

            Float valueY2 = null;
            Float valueX2 = null;
            if (value2Entry != null) {
                valueX2 = value2Entry.getX();
                valueY2 = value2Entry.getY();
            } else return;
//
//            if (D) Log.d("valueY", valueY + "");
//            if (D) Log.d("valueX", valueX + "");

            if (type == MeasureType.MEASURE_TYPE.VSWR ||
                    type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

                if (FunctionHandler.getInstance().getMainLineChart().isUpper()) {

                    if (valueY1 < mLimitValue && valueY2 > mLimitValue) {

                        float w = valueX2 - valueX1;
                        float h = valueY2 - valueY1;

                        float h2 = valueY2 - mLimitValue;
                        float w2 = (w * h2) / h;

                        float limitX = valueX2 - w2;

                        limitLineValues.add(new Entry(limitX, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, valueY2));

                    } else if (valueY1 > mLimitValue && valueY2 < mLimitValue) {

                        float w = valueX2 - valueX1;
                        float h = valueY1 - valueY2;

                        float h2 = mLimitValue - valueY2;
                        float w2 = (w * h2) / h;

                        float limitX = valueX2 - w2;

                        limitLineValues.add(new Entry(valueX1, valueY1));
                        limitLineValues.add(new Entry(limitX, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, mLimitValue));

                    } else if (valueY1 >= mLimitValue && valueY2 >= mLimitValue) {

                        limitLineValues.add(new Entry(valueX1, valueY1));
                        limitLineValues.add(new Entry(valueX2, valueY2));

                    } else if (valueY1 < mLimitValue && valueY2 < mLimitValue) {

                        limitLineValues.add(new Entry(valueX2, mLimitValue));

                    }

                } else {

                    if (valueY1 > mLimitValue && valueY2 < mLimitValue) {

                        float w = valueX2 - valueX1;
                        float h = valueY1 - valueY2;

                        float h2 = mLimitValue - valueY2;
                        float w2 = (w * h2) / h;

                        float limitX = valueX2 - w2;

                        limitLineValues.add(new Entry(limitX, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, valueY2));

                    } else if (valueY1 < mLimitValue && valueY2 > mLimitValue) {

                        float w = valueX2 - valueX1;
                        float h = valueY2 - valueY1;

                        float h2 = valueY2 - mLimitValue;
                        float w2 = (w * h2) / h;

                        float limitX = valueX2 - w2;

                        limitLineValues.add(new Entry(valueX1, valueY1));
                        limitLineValues.add(new Entry(limitX, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, mLimitValue));

                    } else if (valueY1 < mLimitValue && valueY2 < mLimitValue) {

                        limitLineValues.add(new Entry(valueX1, valueY1));
                        limitLineValues.add(new Entry(valueX2, valueY2));

                    } else if (valueY1 > mLimitValue && valueY2 > mLimitValue) {

                        limitLineValues.add(new Entry(valueX1, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, mLimitValue));

                    }

                }


            } else if (type == MeasureType.MEASURE_TYPE.RL || type == MeasureType.MEASURE_TYPE.DTF_RL ||
                    type == MeasureType.MEASURE_TYPE.CABLE_LOSS) {

                if (FunctionHandler.getInstance().getMainLineChart().isUpper()) {

                    if (valueY1 > mLimitValue && valueY2 < mLimitValue) {

                        float w = valueX2 - valueX1;
                        float h = valueY1 - valueY2;

                        float h2 = mLimitValue - valueY2;
                        float w2 = (w * h2) / h;

                        float limitX = valueX2 - w2;

                        limitLineValues.add(new Entry(limitX, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, valueY2));

                    } else if (valueY1 < mLimitValue && valueY2 > mLimitValue) {

                        float w = valueX2 - valueX1;
                        float h = valueY2 - valueY1;

                        float h2 = valueY2 - mLimitValue;
                        float w2 = (w * h2) / h;

                        float limitX = valueX2 - w2;

                        limitLineValues.add(new Entry(valueX1, valueY1));
                        limitLineValues.add(new Entry(limitX, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, mLimitValue));

                    } else if (valueY1 < mLimitValue && valueY2 < mLimitValue) {

                        limitLineValues.add(new Entry(valueX1, valueY1));
                        limitLineValues.add(new Entry(valueX2, valueY2));

                    } else if (valueY1 > mLimitValue && valueY2 > mLimitValue) {

                        limitLineValues.add(new Entry(valueX1, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, mLimitValue));

                    }
                } else {

                    if (valueY1 < mLimitValue && valueY2 > mLimitValue) {

                        float w = valueX2 - valueX1;
                        float h = valueY2 - valueY1;

                        float h2 = valueY2 - mLimitValue;
                        float w2 = (w * h2) / h;

                        float limitX = valueX2 - w2;

                        limitLineValues.add(new Entry(limitX, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, valueY2));

                    } else if (valueY1 > mLimitValue && valueY2 < mLimitValue) {

                        float w = valueX2 - valueX1;
                        float h = valueY1 - valueY2;

                        float h2 = mLimitValue - valueY2;
                        float w2 = (w * h2) / h;

                        float limitX = valueX2 - w2;

                        limitLineValues.add(new Entry(valueX1, valueY1));
                        limitLineValues.add(new Entry(limitX, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, mLimitValue));

                    } else if (valueY1 > mLimitValue && valueY2 > mLimitValue) {

                        limitLineValues.add(new Entry(valueX1, valueY1));
                        limitLineValues.add(new Entry(valueX2, valueY2));

                    } else if (valueY1 < mLimitValue && valueY2 < mLimitValue) {

                        limitLineValues.add(new Entry(valueX2, mLimitValue));

                    }

                }

            }
            else if (type == MeasureType.MEASURE_TYPE.SWEPT_SA ||
                    type == MeasureType.MEASURE_TYPE.CHANNEL_POWER ||
                    type == MeasureType.MEASURE_TYPE.OCCUPIED_BW ||
                    type == MeasureType.MEASURE_TYPE.SEM ||
                    type == MeasureType.MEASURE_TYPE.ACLR ||
                    type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {

                if (FunctionHandler.getInstance().getMainLineChart().isUpper()) {

                    if (valueY1 < mLimitValue && valueY2 > mLimitValue) {

                        float w = valueX2 - valueX1;
                        float h = valueY2 - valueY1;

                        float h2 = valueY2 - mLimitValue;
                        float w2 = (w * h2) / h;

                        float limitX = valueX2 - w2;

                        limitLineValues.add(new Entry(limitX, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, valueY2));

                    } else if (valueY1 > mLimitValue && valueY2 < mLimitValue) {

                        float w = valueX2 - valueX1;
                        float h = valueY1 - valueY2;

                        float h2 = mLimitValue - valueY2;
                        float w2 = (w * h2) / h;

                        float limitX = valueX2 - w2;

                        limitLineValues.add(new Entry(valueX1, valueY1));
                        limitLineValues.add(new Entry(limitX, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, mLimitValue));

                    } else if (valueY1 >= mLimitValue && valueY2 >= mLimitValue) {

                        limitLineValues.add(new Entry(valueX1, valueY1));
                        limitLineValues.add(new Entry(valueX2, valueY2));

                    } else if (valueY1 < mLimitValue && valueY2 < mLimitValue) {

                        limitLineValues.add(new Entry(valueX2, mLimitValue));

                    }

                } else {

                    if (valueY1 > mLimitValue && valueY2 < mLimitValue) {

                        float w = valueX2 - valueX1;
                        float h = valueY1 - valueY2;

                        float h2 = mLimitValue - valueY2;
                        float w2 = (w * h2) / h;

                        float limitX = valueX2 - w2;

                        limitLineValues.add(new Entry(limitX, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, valueY2));

                    } else if (valueY1 < mLimitValue && valueY2 > mLimitValue) {

                        float w = valueX2 - valueX1;
                        float h = valueY2 - valueY1;

                        float h2 = valueY2 - mLimitValue;
                        float w2 = (w * h2) / h;

                        float limitX = valueX2 - w2;

                        limitLineValues.add(new Entry(valueX1, valueY1));
                        limitLineValues.add(new Entry(limitX, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, mLimitValue));

                    } else if (valueY1 < mLimitValue && valueY2 < mLimitValue) {

                        limitLineValues.add(new Entry(valueX1, valueY1));
                        limitLineValues.add(new Entry(valueX2, valueY2));

                    } else if (valueY1 > mLimitValue && valueY2 > mLimitValue) {

                        limitLineValues.add(new Entry(valueX1, mLimitValue));
                        limitLineValues.add(new Entry(valueX2, mLimitValue));

                    }

                }
            }

        }

        if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

            if (FunctionHandler.getInstance().getMainLineChart().isUpper()) {

                if (FunctionHandler.getInstance().getMainLineChart().getChartDataYMax() != null
                        && mLimitValue < FunctionHandler.getInstance().getMainLineChart().getChartDataYMax()) {

//                    if (D) Log.d("LimitEvent", "Max : " + FunctionHandler.getInstance().getMainLineChart().getChartDataYMax() + "");
                    isFail = true;
                } else isFail = false;

            } else {
                if (FunctionHandler.getInstance().getMainLineChart().getChartDataYMin() != null
                        && mLimitValue > FunctionHandler.getInstance().getMainLineChart().getChartDataYMin()) {

//                    if (D) Log.d("LimitEvent", "Min : " + FunctionHandler.getInstance().getMainLineChart().getChartDataYMin() + "");
                    isFail = true;
                } else isFail = false;
            }

        } else if (type == MeasureType.MEASURE_TYPE.RL || type == MeasureType.MEASURE_TYPE.DTF_RL) {

            if (FunctionHandler.getInstance().getMainLineChart().isUpper()) {

                if (FunctionHandler.getInstance().getMainLineChart().getChartDataYMin() != null
                        && mLimitValue > FunctionHandler.getInstance().getMainLineChart().getChartDataYMin()) {
//                    if (D) Log.d("LimitEvent", "Min : " +FunctionHandler.getInstance().getMainLineChart().getChartDataYMin() + "");
                    isFail = true;
                } else isFail = false;

            } else {

                if (FunctionHandler.getInstance().getMainLineChart().getChartDataYMax() != null
                        && mLimitValue < FunctionHandler.getInstance().getMainLineChart().getChartDataYMax()) {

//                    if (D) Log.d("LimitEvent", "Max : " + FunctionHandler.getInstance().getMainLineChart().getChartDataYMax() + "");
                    isFail = true;
                } else isFail = false;

            }

        } else if (type == MeasureType.MEASURE_TYPE.CABLE_LOSS) {

            if (FunctionHandler.getInstance().getMainLineChart().isUpper()) {

                if (mainLineChart.getData() == null || getAverage() == null)
                    isFail = true;
                else if (mLimitValue > getAverage()) {
                    isFail = true;
                } else isFail = false;

            } else {

                if (mainLineChart.getData() == null || getAverage() == null)
                    isFail = true;
                else if (mLimitValue < getAverage()) {
                    isFail = true;
                } else isFail = false;

            }

        } else if (type == MeasureType.MEASURE_TYPE.SWEPT_SA ||
                    type == MeasureType.MEASURE_TYPE.OCCUPIED_BW ||
                    type == MeasureType.MEASURE_TYPE.CHANNEL_POWER ||
                    type == MeasureType.MEASURE_TYPE.ACLR ||
                    type == MeasureType.MEASURE_TYPE.SEM ||
                    type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {

            if (FunctionHandler.getInstance().getMainLineChart().isUpper()) {

                if (FunctionHandler.getInstance().getMainLineChart().getChartDataYMax() != null
                        && mLimitValue < FunctionHandler.getInstance().getMainLineChart().getChartDataYMax()) {

//                    if (D) Log.d("LimitEvent", "Max : " + FunctionHandler.getInstance().getMainLineChart().getChartDataYMax() + "");
                    isFail = true;
                } else isFail = false;

            } else {
                if (FunctionHandler.getInstance().getMainLineChart().getChartDataYMin() != null
                        && mLimitValue > FunctionHandler.getInstance().getMainLineChart().getChartDataYMin()) {

//                    if (D) Log.d("LimitEvent", "Min : " + FunctionHandler.getInstance().getMainLineChart().getChartDataYMin() + "");
                    isFail = true;
                } else isFail = false;
            }

        }

        mainLineChart.getAxisLeft().getLimitLines().clear();
        LimitLine limit = new LimitLine(mLimitValue, mLabel);
        limit.setLineWidth(5);

        if (isFail) {
            limit.setLineColor(Color.rgb(255, 0, 0));
            limit.setTextColor(Color.rgb(255, 0, 0));
            limit.setTextSize(20);

            if (isAlarm()) {
                playAlarm();
            }

            mLimitDataSet = (LineDataSet) mainLineChart.getLineData().getDataSetByIndex(LIMIT_DATASET_INDEX);
            mLimitDataSet.setColor(Color.RED);
            mLimitDataSet.setValues(limitLineValues);

        } else {

            limit.setLineColor(Color.rgb(0, 255, 0));
            limit.setTextColor(Color.rgb(0, 255, 0));
            limit.setTextSize(20);

            mLimitDataSet = (LineDataSet) mainLineChart.getLineData().getDataSetByIndex(LIMIT_DATASET_INDEX);
            mLimitDataSet.setColor(Color.GREEN);
            mLimitDataSet.setValues(limitLineValues);

        }


        mainLineChart.getAxisLeft().addLimitLine(limit);

        mActivity.runOnUiThread(() -> {

            setPassFailText();

        });

//        BottomEventController.getInstance().getLimitEvent().setLimitTextValue(mLimitValue);


        mainLineChart.getData().setDataSet(LIMIT_DATASET_INDEX, mLimitDataSet);
//        LIMIT_DATASET_INDEX = binding.lineChartLayout.lineChart.getData().getIndexOfDataSet(mLimitDataSet);
//        if (D) Log.d("LimitDataSetIndex", LIMIT_DATASET_INDEX + "");

        if (mainLineChart.getData().getDataSetByIndex(LIMIT_DATASET_INDEX) != null)
            mainLineChart.getData().getDataSetByIndex(LIMIT_DATASET_INDEX).setHighlightEnabled(false);

    }

    /*
    isUpperOn -> isUpper = isUpperOn
     */
    public void setLimitType(Boolean isUpperOn) throws NullPointerException {

        isUpper = isUpperOn;

        if (isUpper) {

            setLabel("U");

        } else {

            setLabel("L");

        }

    }

    public void setLabel(String label) {
        mLabel = label;
        setLimitValue(mLimitValue);
    }

    public Boolean isUpper() {

        return isUpper;

    }

    public void setEnabledLimitAlarm(Boolean isOn) throws NullPointerException {

        isOnAlarm = isOn;

        if (isOnAlarm) {


            if (player == null) player = MediaPlayer.create(mActivity, R.raw.beep_effect);

            if (D) Log.d("tvAlarm", "ON");

        } else {


            soundPool.release();

            try {
                if (player != null && player.isPlaying())
                    player.release();
            } catch (Exception e) {
                e.printStackTrace();
            }

            player = null;
            isPlayingAlarm = false;

            if (D) Log.d("tvAlarm", isOnAlarm + "");

        }

    }

    public void playAlarm() {

        if (player == null) player = MediaPlayer.create(mActivity, R.raw.beep_effect);

        if (isOnAlarm) {
//            isPlayingAlarm = true;
            player.setLooping(false);
            player.start();

        } else {
            return;
        }

    }

    public Boolean isAlarm() {
        return isOnAlarm;
    }

    public void setPassFailText() throws NullPointerException {

        mActivity.runOnUiThread(() -> {

            if (isFail) {
                binding.limitMessageLayout.tvPassFailBox.setBackgroundResource(R.drawable.fail_message_box);
                binding.limitMessageLayout.tvPassFailBox.setTextColor(mActivity.getResources().getColor(R.color.colorFailMessageBox));
                binding.limitMessageLayout.tvPassFailBox.setText(R.string.fail_name);

            } else {
                binding.limitMessageLayout.tvPassFailBox.setBackgroundResource(R.drawable.pass_message_box);
                binding.limitMessageLayout.tvPassFailBox.setTextColor(mActivity.getResources().getColor(R.color.colorPassMessageBox));
                binding.limitMessageLayout.tvPassFailBox.setText(R.string.pass_name);

            }

        });
    }

    public void setEnabledLimitMsg(Boolean on) {

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

        if (isPassFailEnabled == on) return;
        isPassFailEnabled = on;

        /*if (mode == MeasureMode.MEASURE_MODE.SA || mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {
            isPassFailEnabled = false;
        }*/

        mActivity.runOnUiThread(() -> {
            if (on) {
                binding.limitMessageLayout.linWarningMsg.setVisibility(View.VISIBLE);// tvPassFailBox.setVisibility(View.VISIBLE);
            } else {
                binding.limitMessageLayout.linWarningMsg.setVisibility(View.GONE);// tvPassFailBox.setVisibility(View.GONE);
            }
        });
    }

    public Boolean isEnabledLimitMsg() {
        return isPassFailEnabled;
    }

    public Boolean isEnabledLimitLine() {
        return isEnabledLimit;
    }

    public void removeLimitLine() {

        if (mainLineChart.getData().getDataSetByIndex(LIMIT_DATASET_INDEX) != null) {

            mainLineChart.getData().getDataSetByIndex(LIMIT_DATASET_INDEX).clear();
            mainLineChart.getAxisLeft().removeAllLimitLines();
            invalidate();

        }

        isEnabledLimit = false;

    }

    public void setLimitValue(float limitValue) throws NullPointerException {

//        if (D) Log.d("setLimitValue in Chart", limitValue + "");

        if (!isEnabledLimit) return;

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

        if (type == MeasureType.MEASURE_TYPE.VSWR) {

            if (1f <= limitValue && limitValue <= 65.33f) {

                mLimitValue = limitValue;
//                VswrDataHandler.getInstance().getConfigData().getLimitData().setLimitValueForVswr(mLimitValue);

                mainLineChart.getAxisLeft().getLimitLines().clear();
                LimitLine limit = new LimitLine(mLimitValue, mLabel);
                limit.setLineWidth(5);

                mainLineChart.getAxisLeft().addLimitLine(limit);
                checkLimitLine();
                invalidate();

            }


        } else if (type == MeasureType.MEASURE_TYPE.RL) {

            if (0f <= limitValue && limitValue <= 60f) {

                mLimitValue = limitValue;

                mainLineChart.getAxisLeft().getLimitLines().clear();
                LimitLine limit = new LimitLine(mLimitValue, mLabel);
                limit.setLineWidth(5);

//                VswrDataHandler.getInstance().getConfigData().getLimitData().setLimitValueForRl(mLimitValue);

                mainLineChart.getAxisLeft().addLimitLine(limit);
                checkLimitLine();
                invalidate();

            }

        } else if (type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

            if (1f <= limitValue && limitValue <= 65.33f) {

                mLimitValue = limitValue;

                mainLineChart.getAxisLeft().getLimitLines().clear();
                LimitLine limit = new LimitLine(mLimitValue, mLabel);
                limit.setLineWidth(5);

//                VswrDataHandler.getInstance().getDtfConfigData().getLimitData().setLimitValueForVswr(mLimitValue);

                mainLineChart.getAxisLeft().addLimitLine(limit);
//                setLimitTextValue(mLimitValue);
                checkLimitLine();
                invalidate();

            }

        } else if (type == MeasureType.MEASURE_TYPE.DTF_RL) {

            if (0f <= limitValue && limitValue <= 60f) {

                mLimitValue = limitValue;

                mainLineChart.getAxisLeft().getLimitLines().clear();
                LimitLine limit = new LimitLine(mLimitValue, mLabel);
                limit.setLineWidth(5);

//                VswrDataHandler.getInstance().getDtfConfigData().getLimitData().setLimitValueForRl(mLimitValue);

                mainLineChart.getAxisLeft().addLimitLine(limit);
                checkLimitLine();
                invalidate();
            }

        } else if (type == MeasureType.MEASURE_TYPE.CABLE_LOSS) {

            if (0f <= limitValue && limitValue <= 60f) {

                mLimitValue = limitValue;

                mainLineChart.getAxisLeft().getLimitLines().clear();
                LimitLine limit = new LimitLine(mLimitValue, mLabel);
                limit.setLineWidth(5);

                mainLineChart.getAxisLeft().addLimitLine(limit);
                checkLimitLine();
                invalidate();

            }

        } else if (type == MeasureType.MEASURE_TYPE.INTERFERENCE_HUNTING) {
            if (-200f <= limitValue && limitValue <= 200f) {

                mLimitValue = limitValue;

                mainLineChart.getAxisLeft().getLimitLines().clear();
                LimitLine limit = new LimitLine(mLimitValue, mLabel);
                limit.setLineWidth(5);
                mainLineChart.getAxisLeft().addLimitLine(limit);
                checkLimitLine();
                invalidate();

            }
        }
        else if (mode == MeasureMode.MEASURE_MODE.SA) {
            //@@ 임계값 필요

            mLimitValue = limitValue;

            mainLineChart.getAxisLeft().getLimitLines().clear();
            LimitLine limit = new LimitLine(mLimitValue, mLabel);
            limit.setLineWidth(5);

            mainLineChart.getAxisLeft().addLimitLine(limit);
            checkLimitLine();
            invalidate();
        }

    }

    public float getLimitValue() {

        return mLimitValue;

    }

    public void updateSpuriousEmissionLine() {

//        new Thread(() -> {
//
//            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
//
//            if (type != MeasureType.MEASURE_TYPE.SPURIOUS_EMISSION) {
//                clearSpuriousEmissionLine();
//                return;
//            }
//
//            //it doesn't draw from start to stop
//            LineData data = mainLineChart.getLineData();
//
//            SaConfigData configData = SaDataHandler.getInstance().getSpuriousEmissionConfigData();
//            SpuriousEmissionMeasSetupData emissionData = configData.getSpuriousEmissionData();
//
//            emissionData.calMinStartFreq();
//            emissionData.calMaxStopFreq();
//
//            double start = emissionData.getMinStartFreq();
//            double stop = emissionData.getMaxStopFreq();
//            double span = stop - start;
//
//            double absLowFreq = emissionData.getLowFreq() - start;
//            float lowFreq = (float)((mSpan * absLowFreq) / span);
//
//            double absHighFreq = emissionData.getHighFreq() - start;
//            float highFreq = (float)((mSpan * absHighFreq) / span);
//
//            if (D) Log.d("SpuriousEmissionLine", "Low Freq : " + lowFreq + "   High Freq : " + highFreq);
//
//            int datasetCount = 0;
//
//            for (int i = 0; i < emissionData.getFreqRangeTableDataList().size(); i++) {
//
//                boolean isOn = emissionData.getFreqRangeTableDataList().get(i).isState();
//                if (!isOn) continue;
//
//                float absStartLimit1 = emissionData.getFreqRangeTableDataList().get(i).getAbsStartLimit();
//                float absStopLimit1 = emissionData.getFreqRangeTableDataList().get(i).getAbsStopLimit();
//
//                double absStartFreq1 = emissionData.getFreqRangeTableDataList().get(i).getStartFreq() - start;
//                float startFreq1 = (float)((mSpan * absStartFreq1) / span);
//
//                double absStopFreq1 = emissionData.getFreqRangeTableDataList().get(i).getStopFreq() - start;
//                float stopFreq1 = (float)((mSpan * absStopFreq1) / span);
//
//                float width1 = stopFreq1 - startFreq1;
//                float height1 = absStopLimit1 - absStartLimit1;
//
//                if (startFreq1 < lowFreq && stopFreq1 > highFreq) {
//
////                    if (D) Log.d("SpuriousEmissionLine", "1" + " Start Freq : " + startFreq1 + " StopFreq : " + stopFreq1);
//
//                    float width2 = lowFreq - startFreq1;
//                    float height2 = (width2 * height1) / width1;
//
//                    float startFreq2 = startFreq1;
//                    float stopFreq2 = startFreq2 + width2;
//                    float absStartLimit2 = absStartLimit1;
//                    float absStopLimit2 = absStartLimit2 + height2;
//
//                    if (D) Log.d("SpuriousEmissionLine", "1" + " Start Freq : " + startFreq2 + " StopFreq : " + stopFreq2 + " StartLimit : " + absStartLimit2 + " StopLimit : " + absStopLimit2);
//
//                    ILineDataSet dataset = data.getDataSetByIndex(SPURIOUS_EMISSION_INDEX + datasetCount);
//                    if (dataset == null) continue;
//                    dataset.clear();
//
//                    dataset.addEntry(new Entry(startFreq2, absStartLimit2));
//                    dataset.addEntry(new Entry(stopFreq2, absStopLimit2));
//
//                    float width3 = stopFreq1 - highFreq;
//                    float height3 = (width3 * height1) / width1;
//
//                    float startFreq3 = highFreq;
//                    float stopFreq3 = stopFreq1;
//                    float absStartLimit3 = absStopLimit1 - height3;
//                    float absStopLimit3 = absStopLimit1;
//
//                    if (D) Log.d("SpuriousEmissionLine", "1" + " Start Freq : " + startFreq3 + " StopFreq : " + stopFreq3 + " StartLimit : " + absStartLimit3 + " StopLimit : " + absStopLimit3);
//
//                    datasetCount++;
//                    ILineDataSet dataset2 = data.getDataSetByIndex(SPURIOUS_EMISSION_INDEX + datasetCount);
//                    if (dataset2 == null) continue;
//                    dataset2.clear();
//
//                    dataset2.addEntry(new Entry(startFreq3, absStartLimit3));
//                    dataset2.addEntry(new Entry(stopFreq3, absStopLimit3));
//
//                    datasetCount++;
//
//                } else if (startFreq1 < lowFreq && stopFreq1 >= lowFreq && stopFreq1 <= highFreq) {
//
//                    ILineDataSet dataset = data.getDataSetByIndex(SPURIOUS_EMISSION_INDEX + datasetCount);
//                    if (dataset == null) continue;
//                    dataset.clear();
//
//                    float width2 = lowFreq - startFreq1;
//                    float height2 = (height1 * width2) / width1;
//
//                    float x = startFreq1;
//                    float x2 = lowFreq;
//
//                    float y = absStartLimit1;
//                    float y2 = absStartLimit1 + height2;
//
//                    if (D) Log.d("SpuriousEmissionLine", "2" + " Start Freq : " + x + " StopFreq : " + x2 + " StartLimit : " + y + " StopLimit : " + y2);
//
//                    dataset.addEntry(new Entry(x, y));
//                    dataset.addEntry(new Entry(x2, y2));
//
//                    datasetCount++;
//
//                } else if (startFreq1 >= lowFreq && startFreq1 <= highFreq && stopFreq1 > highFreq) {
//
//                    ILineDataSet dataset = data.getDataSetByIndex(SPURIOUS_EMISSION_INDEX + datasetCount);
//                    if (dataset == null) continue;
//                    dataset.clear();
//
//                    float width2 = stopFreq1 - highFreq;
//                    float height2 = (height1 * width2) / width1;
//
//                    float x = highFreq;
//                    float x2 = stopFreq1;
//
//                    float y = absStopLimit1 - height2;
//                    float y2 = absStopLimit1;
//
//                    if (D) Log.d("SpuriousEmissionLine", "3" + " Start Freq : " + x + " StopFreq : " + x2 + " StartLimit : " + y + " StopLimit : " + y2);
//
//                    dataset.addEntry(new Entry(x, y));
//                    dataset.addEntry(new Entry(x2, y2));
//
//                    datasetCount++;
//
//                } else if ((startFreq1 < lowFreq && stopFreq1 < lowFreq) || (startFreq1 > highFreq && stopFreq1 > highFreq)) {
//
//                    if (D) Log.d("SpuriousEmissionLine", "4" + " Start Freq : " + startFreq1 + " StopFreq : " + stopFreq1);
//
//                    ILineDataSet dataset = data.getDataSetByIndex(SPURIOUS_EMISSION_INDEX + datasetCount);
//                    if (dataset == null) continue;
//                    dataset.clear();
//
//                    dataset.addEntry(new Entry(startFreq1, absStartLimit1));
//                    dataset.addEntry(new Entry(stopFreq1, absStopLimit1));
//
//                    datasetCount++;
//
//                }
//
//            }
//
//            new Handler(Looper.getMainLooper()).post(this::invalidate);
//
//        }).start();

    }

    public void clearSpuriousEmissionLine() {

        new Thread(() -> {

            new Handler(Looper.getMainLooper()).post(() -> {

                for (int i = 0; i < SPURIOUS_EMISSION_DATASET_COUNT; i++) {
                    ILineDataSet dataset = mainLineChart.getLineData().getDataSetByIndex(SPURIOUS_EMISSION_INDEX + i);
                    if (dataset != null)
                        mainLineChart.getLineData().getDataSetByIndex(SPURIOUS_EMISSION_INDEX + i).clear();

                }

            });

        }).start();

    }

    public void removeRecallLine() {

        for (int i = 4; i < mDataList.length; i++) {
            mainLineChart.getData().getDataSetByIndex(i).clear();
            getDataList(i).clear();
        }

    }

    public void setEnabledLimitLine(Boolean isOn) {
        isEnabledLimit = isOn;
    }

    public Float getAverage() {
        return mAverage;
    }

    public void setAverage(Float average) {
        this.mAverage = average;
    }

    public Boolean getSkipFirstData() {
        return isSkipFirstData;
    }

    public void setSkipFirstData(Boolean skipFirstData) {
        isSkipFirstData = skipFirstData;
    }

    public boolean isSkipFirstData() {
        return isSkipFirstData;
    }

    public Boolean isDrawingChart() {
        return mainLineChart.getIsDrawing();
        //return mainLineChart.isShown() && mainLineChart.isDirty();
        //return isDrawingChart;
    }

    public Double getCurrentMarkerFreq(int idx) {

        if (!isOnMarker(idx)) return null;

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        Double currentMarkerX = -1d;

        float chartStartX = getStartFreq();
        float chartStopX = getStopFreq();

        double start = -1d;
        double stop = -1d;
        double span = -1d;

        switch (mode) {
            case VSWR:
            case CL:
            case DTF:
                VswrConfigData config = VswrDataHandler.getInstance().getConfigData();

                start = config.getFrequencyData().getStartFreq();
                stop = config.getFrequencyData().getStopFreq();
                span = stop - start;

                if (mode == MeasureMode.MEASURE_MODE.DTF) {
                    start = 0;
                    stop = config.getDistance();
                }

                int dataPoint = config.getSweepData().getDataPoint().getDataPoint();
                double space = (stop - start) / (dataPoint - 1);

                float chartSpace = (chartStopX - chartStartX) / (dataPoint - 1);

                Highlight marker = getMarker(idx);
                float markerX = marker.getX();
                float markerIdx = markerX / chartSpace;

                currentMarkerX = start + space * markerIdx;
                if (D)
                    Log.d("getCurrentMarkerFreq", "mkr IDX : " + markerIdx + " mkr freq : " + currentMarkerX);
                break;

            case SA:

                SaConfigData saConfigData = SaDataHandler.getInstance().getConfigData();
                SAFrequencyData frequencyData = saConfigData.getFrequencyData();

                if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {
                    start = 0;
                    stop = 10d;
                } else if (frequencyData.getSpan() == 0d) {
                    start = 0d;
                    stop = saConfigData.getSweepTimeData().getSweepTimeToMs();
                } else {
                    start = frequencyData.getStartFreq();
                    stop = frequencyData.getStopFreq();
                }

                span = stop - start;

                dataPoint = saConfigData.getTraceData().getDetector().getDataPoints();
                space = (stop - start) / (dataPoint - 1);
                chartSpace = (chartStopX - chartStartX) / (dataPoint - 1);
                marker = getMarker(idx);
                markerX = marker.getX();
                markerIdx = markerX / chartSpace;

                currentMarkerX = start + space * markerIdx;

                if (D)
                    Log.d("getCurrentMarkerFreq", "mkr IDX : " + markerIdx + " mkr freq : " + currentMarkerX);

                break;
        }

        if (span < 0.01d) {
            currentMarkerX = Math.round(currentMarkerX * Math.pow(10d, 6)) / Math.pow(10d, 6);
        } else if (0.01d <= span && span <= 0.1d) {
            currentMarkerX = Math.round(currentMarkerX * Math.pow(10d, 5)) / Math.pow(10d, 5);
        } else if (0.1d <= span && span < 1d) {
            currentMarkerX = Math.round(currentMarkerX * Math.pow(10d, 4)) / Math.pow(10d, 4);
        } else if (1d <= span && span < 10d) {
            currentMarkerX = Math.round(currentMarkerX * Math.pow(10d, 3)) / Math.pow(10d, 3);
        } else if (10d <= span) {
            currentMarkerX = Math.round(currentMarkerX * Math.pow(10d, 2)) / Math.pow(10d, 2);
        }

        return currentMarkerX;

    }

    public String getCurrentMarkerFreqToStr(int idx) {

        if (!isOnMarker(idx)) return null;

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        Double currentMarkerX = -1d;
        String XtoStr = "";
        DecimalFormat formatter;

        float chartStartX = getStartFreq();
        float chartStopX = getStopFreq();

        double start = -1d;
        double stop = -1d;
        double span = -1d;

        switch (mode) {
            case VSWR:
            case CL:
            case DTF:
                VswrConfigData config = VswrDataHandler.getInstance().getConfigData();

                start = config.getFrequencyData().getStartFreq();
                stop = config.getFrequencyData().getStopFreq();
                span = stop - start;

                if (mode == MeasureMode.MEASURE_MODE.DTF) {
                    start = 0;
                    stop = config.getDistance();
                }

                int dataPoint = config.getSweepData().getDataPoint().getDataPoint();
                double space = (stop - start) / (dataPoint - 1);


                float chartSpace = (chartStopX - chartStartX) / (dataPoint - 1);


                Highlight marker = getMarker(idx);
                float markerX = marker.getX();
                float markerIdx = markerX / chartSpace;

                currentMarkerX = start + space * markerIdx;
                if (D)
                    Log.d("getCurrentMarkerFreq", "mkr IDX : " + markerIdx + " mkr freq : " + currentMarkerX);
                break;

            case SA:

                SaConfigData saConfigData = SaDataHandler.getInstance().getConfigData();
                SAFrequencyData frequencyData = saConfigData.getFrequencyData();

                if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {
                    start = 0;
                    stop = 10d;
                } else if (frequencyData.getSpan() == 0d) {
                    start = 0d;
                    stop = saConfigData.getSweepTimeData().getSweepTimeToMs();
                } else {
                    start = frequencyData.getStartFreq();
                    stop = frequencyData.getStopFreq();
                }

                span = stop - start;

                dataPoint = saConfigData.getTraceData().getDetector().getDataPoints();
                space = (stop - start) / (dataPoint - 1);
                chartSpace = (chartStopX - chartStartX) / (dataPoint - 1);
                marker = getMarker(idx);
                markerX = marker.getX();
                markerIdx = markerX / chartSpace;

                currentMarkerX = start + space * markerIdx;

                if (D)
                    Log.d("getCurrentMarkerFreq", "mkr IDX : " + markerIdx + " mkr freq : " + currentMarkerX);

                break;
        }

        if (span < 0.01d) {
            currentMarkerX = Math.round(currentMarkerX * Math.pow(10d, 6)) / Math.pow(10d, 6);
            formatter = new DecimalFormat("#.000000");
            XtoStr = formatter.format(currentMarkerX);
        } else if (0.01d <= span && span <= 0.1d) {
            currentMarkerX = Math.round(currentMarkerX * Math.pow(10d, 5)) / Math.pow(10d, 5);
            formatter = new DecimalFormat("#.00000");
            XtoStr = formatter.format(currentMarkerX);
        } else if (0.1d <= span && span < 1d) {
            currentMarkerX = Math.round(currentMarkerX * Math.pow(10d, 4)) / Math.pow(10d, 4);
            formatter = new DecimalFormat("#.0000");
            XtoStr = formatter.format(currentMarkerX);
        } else if (1d <= span && span < 10d) {
            currentMarkerX = Math.round(currentMarkerX * Math.pow(10d, 3)) / Math.pow(10d, 3);
            formatter = new DecimalFormat("#.000");
            XtoStr = formatter.format(currentMarkerX);
        } else if (10d <= span) {
            currentMarkerX = Math.round(currentMarkerX * Math.pow(10d, 2)) / Math.pow(10d, 2);
            formatter = new DecimalFormat("#.00");
            XtoStr = formatter.format(currentMarkerX);
        }

        return XtoStr;

    }


}

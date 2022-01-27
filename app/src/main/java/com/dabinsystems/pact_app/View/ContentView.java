package com.dabinsystems.pact_app.View;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.tv.TvView;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Activity.SplashActivity;
import com.dabinsystems.pact_app.Data.AppMode;
import com.dabinsystems.pact_app.Data.SA.BWData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ChannelPowerMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SpuriousEmission.FreqRangeTableData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SpuriousEmission.SpuriousEmissionMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.SaConfigData;
import com.dabinsystems.pact_app.Data.SA.SaGateData;
import com.dabinsystems.pact_app.Data.SA.TraceData;
import com.dabinsystems.pact_app.Dialog.CallNameListDialog;
import com.dabinsystems.pact_app.Dialog.GpsInfoDialog;
import com.dabinsystems.pact_app.Dialog.PresetDialog;
import com.dabinsystems.pact_app.Dialog.ScreenshotDialog;
import com.dabinsystems.pact_app.Function.Chart.MainLineChartFunc;
import com.dabinsystems.pact_app.Function.DataConnector;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Screenshot.Screenshot;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class ContentView extends LayoutBase {

    public final float MARKER_TABLE_WEIGHT = 0.1f;
    public final float SWEPT_INFO_WEIGHT = 0.07f;
    public final float CHANNEL_POWER_INFO_WEIGHT = 0.05f;
    public final float OCC_INFO_WEIGHT = 0.1f;
    public final float DTF_WEIGHT = 0.1f;
    public final float CL_WEIGHT = 0.1f;
    public final float ACLR_WEIGHT = 0.2f;
    public final float SEM_WEIGHT = 0.35f;
    public final float SPURIOUS_WEIGHT = 0.3f;
    public final float INTERFERENCE_HUNTING_WEIGHT = 0.3f;
    public final float GATE_FUNC_WEIGHT = 0.3f;
    public final float TRANSMIT_ON_OFF_POWER_WEIGHT = 0.2f;

    private Boolean isOpenMarkeTable = false;
    private Handler mMainHandler;

    public ContentView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);

        mMainHandler = new Handler(Looper.getMainLooper());

        // if PACT than SA is False
        if (SplashActivity.getAppMode() == AppMode.PACT) {
            //@@ [22.01.25] 원전 모니터링용 SA 외 기능 block 처리
//            binding.tvVswr.setSelected(true); //selectTopView(binding.tvVswr);
//            FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.VSWR);
            //@@
            ViewHandler.getInstance().getVswrView().addMenu();
        } else {
//            binding.tvSa.setSelected(true); //selectTopView(binding.tvSa);
//            FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.SA);
//            ViewHandler.getInstance().getSAView().addMenu();
        }

        setUpEvents();
        smallIconUpdate();
        subInfoUpdate();
        updateView();

    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();
    }

    @Override
    public void initView() {
        super.initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setUpEvents() throws NullPointerException {

//        binding.ivWifiImage.setOnClickListener(v -> {
//
//            new ChangeIpDialog(mActivity, binding).show();
//
//        });

        binding.ivPreset.setOnClickListener(v -> {
            //check mode
            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
            InitActivity.logMsg("Preset Touched", mode.toString());

            new Thread(() -> {

                new Handler(Looper.getMainLooper()).post(() -> {

                    PresetDialog dialog = new PresetDialog(mActivity);
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();

                });

            }).start();

        });

        binding.ivScreenshot.setOnTouchListener((v, event) -> {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    FunctionHandler.getInstance().getScreenshotFunc().setLongPress(false);
                    FunctionHandler.getInstance().getScreenshotFunc().longPressTouchEvent();
                    FunctionHandler.getInstance().getScreenshotFunc().setTouchDown();
                    break;

                case MotionEvent.ACTION_UP:

                    FunctionHandler.getInstance().getScreenshotFunc().setTouchUp();
                    if (!FunctionHandler.getInstance().getScreenshotFunc().isLongPress()
                            && mActivity.getRecorder().isRecording()) {

                        FunctionHandler
                                .getInstance()
                                .getScreenshotFunc()
                                .removeLongPressCallback();

//                            mActivity.getRecorder().setUpEvents(binding.getRoot());
                        mActivity.getRecorder().stopShareScreen();
                        InitActivity.logMsg("Recording", "stop");

                    } else if (Math.abs(FunctionHandler.getInstance().getScreenshotFunc().getTouchUpTime()
                            - FunctionHandler.getInstance().getScreenshotFunc().getTouchDownTime())
                            < FunctionHandler.getInstance().getScreenshotFunc().getLongPressTime()) {

                        Screenshot.getInstance().takeScreenShotOfRootView(v.getRootView());
                        FunctionHandler.getInstance().getScreenshotFunc().setFileName(FunctionHandler.getInstance().getMeasureType().getType().toString());
                        new ScreenshotDialog(mActivity, FunctionHandler.getInstance().getMeasureType().getType().toString()).show();
                        FunctionHandler.getInstance().getScreenshotFunc().removeLongPressCallback();

                    } else {

                        InitActivity.logMsg("LongPress", "Unknown");
                    }

                    break;

            }

            return true;
        });
        //@@ [22.01.25] 원전 모니터링용 SA 외 기능 block 처리
        /*binding.tvVswr.setOnClickListener(v -> {
            if (FunctionHandler.getInstance().getMeasureMode().getMode() == MeasureMode.MEASURE_MODE.VSWR)
                return;
            FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.VSWR);
            ViewHandler.getInstance().getVswrView().addMenu();
            update();
        });

        binding.tvMainQuickVSWR.setOnClickListener(v -> {
            if (FunctionHandler.getInstance().getMeasureMode().getMode() == MeasureMode.MEASURE_MODE.VSWR)
                return;
            FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.VSWR);
            ViewHandler.getInstance().getVswrView().addMenu();
            update();
            // TODO 데이터 요청
        });

        binding.tvDtf.setOnClickListener(v -> {
            if (FunctionHandler.getInstance().getMeasureMode().getMode() == MeasureMode.MEASURE_MODE.DTF)
                return;
            FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.DTF);
            ViewHandler.getInstance().getDtfView().addMenu();
            update();
        });

        binding.tvMainQuickDTF.setOnClickListener(v -> {
            if (FunctionHandler.getInstance().getMeasureMode().getMode() == MeasureMode.MEASURE_MODE.DTF)
                return;
            FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.DTF);
            ViewHandler.getInstance().getDtfView().addMenu();
            update();
            // TODO 데이터 요청
        });

        binding.tvCableLoss.setOnClickListener(v -> {
            if (FunctionHandler.getInstance().getMeasureMode().getMode() == MeasureMode.MEASURE_MODE.CL)
                return;
            FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.CL);
            ViewHandler.getInstance().getCableLossView().addMenu();
            update();
        });

        binding.tvMainQuickCableLoss.setOnClickListener(v -> {
            if (FunctionHandler.getInstance().getMeasureMode().getMode() == MeasureMode.MEASURE_MODE.CL)
                return;
            FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.CL);
            ViewHandler.getInstance().getCableLossView().addMenu();
            update();
            // TODO 데이터 요청
        });*/
        //@@

        binding.tvSa.setOnClickListener(v -> {
            if (SplashActivity.getAppMode() == AppMode.PACT) {
                Toast.makeText(mActivity, "Disable", Toast.LENGTH_SHORT).show();
                return;
            }

            if (FunctionHandler.getInstance().getMeasureMode().getMode().getValue() == MeasureMode.MEASURE_MODE.SA.getValue()) {
                resetTopViewColor();
                binding.tvSa.setSelected(true); //selectTopView(binding.tvSa);
                ViewHandler.getInstance().getSaMeas().addMenu();
                return;
            }

            FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.SA);

            ViewHandler.getInstance().getSAView().addMenu();
            update();

        });

        binding.tvMainQuickSA.setOnClickListener(v -> {
            if (SplashActivity.getAppMode() == AppMode.PACT) {
                Toast.makeText(mActivity, "Disable", Toast.LENGTH_SHORT).show();
                return;
            }

            if (FunctionHandler.getInstance().getMeasureMode().getMode().getValue() == MeasureMode.MEASURE_MODE.SA.getValue()) {
                resetTopViewColor();
                binding.tvSa.setSelected(true); //selectTopView(binding.tvSa);
                ViewHandler.getInstance().getSaMeas().addMenu();
                return;
            }

            FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.SA);
            ViewHandler.getInstance().getSAView().addMenu();
            update();
        });
        //@@ [22.01.25] 원전 모니터링용 SA 외 기능 block 처리
        /*binding.tvModAccuracy.setOnClickListener(v -> {

            if (SplashActivity.getAppMode() == AppMode.PACT) {
                Toast.makeText(mActivity, "Disable", Toast.LENGTH_SHORT).show();
                return;
            }

            if (binding.linMainRight.getVisibility() != View.VISIBLE)
                binding.linMainRight.setVisibility(View.VISIBLE);

//            if (FunctionHandler.getInstance().getMeasureMode().getMode().getValue() == MeasureMode.MEASURE_MODE.MOD_ACCURACY.getValue())
//                return;
//
//            FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.MOD_ACCURACY);
//            ViewHandler.getInstance().getSAView().addMenu();
            ViewHandler.getInstance().getModAccuracyMode().addMenu();

            resetTopViewColor();
            binding.tvModAccuracy.setSelected(true);
        });

        binding.tvMainQuick5GNR.setOnClickListener(v -> {
            resetTopViewColor();
            binding.tvModAccuracy.setSelected(true);

            MeasureMode mode = FunctionHandler.getInstance().getMeasureMode();
            if (mode.getMode() != MeasureMode.MEASURE_MODE.MOD_ACCURACY)
                mode.setMode(MeasureMode.MEASURE_MODE.MOD_ACCURACY);

            FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.NR_5G);
            ViewHandler.getInstance().getSAView().addMenu();
            ViewHandler.getInstance().getNrMeasSetupView().addMenu();
            update();
        });

        binding.tvMainQuick5GNRScan.setOnClickListener(v -> {
            resetTopViewColor();
            binding.tvModAccuracy.setSelected(true);

            MeasureMode mode = FunctionHandler.getInstance().getMeasureMode();
            if (mode.getMode() != MeasureMode.MEASURE_MODE.MOD_ACCURACY)
                mode.setMode(MeasureMode.MEASURE_MODE.MOD_ACCURACY);

            FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.NR_5G_SCAN);
            ViewHandler.getInstance().getSAView().addMenu();
            ViewHandler.getInstance().getNrScanMeasSetupView().addMenu();
            update();
        });

        binding.tvMainQuickLteFdd.setOnClickListener(v -> {
            resetTopViewColor();
            binding.tvModAccuracy.setSelected(true);

            MeasureMode mode = FunctionHandler.getInstance().getMeasureMode();
            if (mode.getMode() != MeasureMode.MEASURE_MODE.MOD_ACCURACY)
                mode.setMode(MeasureMode.MEASURE_MODE.MOD_ACCURACY);

            FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.LTE_FDD);
            ViewHandler.getInstance().getSAView().addMenu();
            ViewHandler.getInstance().getLteFddMeasSetupView().addMenu();
            update();
        });*/
        //@@

        binding.tvMarker1.setOnClickListener(v -> {

            if (!FunctionHandler.getInstance().getMainLineChart().isOnMarker(0)) {

                FunctionHandler.getInstance().getMainLineChart().setMarker(0);

            } else {

                FunctionHandler.getInstance().getMainLineChart().removeMarker(0);

            }

            if (binding.lineChartLayout.mainLineChart.getData().getEntryCount() > 0) {

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                if (mode.getValue() == MeasureMode.MEASURE_MODE.SA.getValue())
                    ViewHandler.getInstance().getSaMarkerView().addMenu();
                else
                    ViewHandler.getInstance().getMarkerSetupView().addMenu();

            }
            markerValueUpdate();
            markerTableUpdate();
            markerIconUpdate();

        });

        binding.tvMarker2.setOnClickListener(v -> {

            if (!FunctionHandler.getInstance().getMainLineChart().isOnMarker(1)) {

                FunctionHandler.getInstance().getMainLineChart().setMarker(1);

            } else {
                FunctionHandler.getInstance().getMainLineChart().removeMarker(1);

            }

            if (binding.lineChartLayout.mainLineChart.getData().getEntryCount() > 0) {

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                if (mode.getValue() == MeasureMode.MEASURE_MODE.SA.getValue())
                    ViewHandler.getInstance().getSaMarkerView().addMenu();
                else
                    ViewHandler.getInstance().getMarkerSetupView().addMenu();

            }

            markerValueUpdate();
            markerTableUpdate();
            markerIconUpdate();

        });

        binding.tvMarker3.setOnClickListener(v -> {

            if (!FunctionHandler.getInstance().getMainLineChart().isOnMarker(2)) {

                FunctionHandler.getInstance().getMainLineChart().setMarker(2);

            } else {

                FunctionHandler.getInstance().getMainLineChart().removeMarker(2);

            }

            if (binding.lineChartLayout.mainLineChart.getData().getEntryCount() > 0) {

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                if (mode.getValue() == MeasureMode.MEASURE_MODE.SA.getValue())
                    ViewHandler.getInstance().getSaMarkerView().addMenu();
                else
                    ViewHandler.getInstance().getMarkerSetupView().addMenu();

            }

            markerValueUpdate();
            markerTableUpdate();
            markerIconUpdate();

        });

        binding.tvMarker4.setOnClickListener(v -> {

            if (!FunctionHandler.getInstance().getMainLineChart().isOnMarker(3)) {

                FunctionHandler.getInstance().getMainLineChart().setMarker(3);

            } else {
                FunctionHandler.getInstance().getMainLineChart().removeMarker(3);

            }

            if (binding.lineChartLayout.mainLineChart.getData().getEntryCount() > 0) {

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                if (mode.getValue() == MeasureMode.MEASURE_MODE.SA.getValue())
                    ViewHandler.getInstance().getSaMarkerView().addMenu();
                else
                    ViewHandler.getInstance().getMarkerSetupView().addMenu();

            }

            markerValueUpdate();
            markerTableUpdate();
            markerIconUpdate();

        });

        binding.tvMarker5.setOnClickListener(v -> {

            if (!FunctionHandler.getInstance().getMainLineChart().isOnMarker(4)) {
                FunctionHandler.getInstance().getMainLineChart().setMarker(4);

            } else {
                FunctionHandler.getInstance().getMainLineChart().removeMarker(4);
            }

            if (binding.lineChartLayout.mainLineChart.getData().getEntryCount() > 0) {

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                if (mode.getValue() == MeasureMode.MEASURE_MODE.SA.getValue())
                    ViewHandler.getInstance().getSaMarkerView().addMenu();
                else
                    ViewHandler.getInstance().getMarkerSetupView().addMenu();

            }

            markerValueUpdate();
            markerTableUpdate();
            markerIconUpdate();

        });

        binding.linTrace1.setOnClickListener(v -> {

            int idx = 0;
            TraceData traceData = SaDataHandler.getInstance().getConfigData().getTraceData();
            TraceEnumData.TRACE_TYPE type = traceData.getType(idx);

            if (type == TraceEnumData.TRACE_TYPE.BLANK) {

                binding.linTrace1.setSelected(true);//binding.linTrace1.setBackgroundResource(R.color.trace1);
                traceData.setType(TraceEnumData.TRACE_TYPE.UPDATE, idx);

            } else {

                binding.linTrace1.setSelected(false);//binding.linTrace1.setBackgroundResource(R.color.unselect_content_mark);
                traceData.setType(TraceEnumData.TRACE_TYPE.BLANK, idx);
                FunctionHandler.getInstance().getMainLineChart().clearValues(0);

                // 연관된 마커 끄기
                FunctionHandler.getInstance().getMainLineChart().removeMarkerOfTrace(0);
            }

            traceData.setTrace(idx);
            ViewHandler.getInstance().getTrace().addMenu();
            update();
            FunctionHandler.getInstance().getMainLineChart().invalidate();

            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    DataHandler.getInstance().getConfigCmd()
            );

        });

        binding.linTrace2.setOnClickListener(v -> {

            int idx = 1;
            TraceData traceData = SaDataHandler.getInstance().getConfigData().getTraceData();
            TraceEnumData.TRACE_TYPE type = traceData.getType(idx);

            if (type == TraceEnumData.TRACE_TYPE.BLANK) {

                binding.linTrace2.setSelected(true);//.setBackgroundResource(R.color.trace2);
                traceData.setType(TraceEnumData.TRACE_TYPE.UPDATE, idx);

            } else {

                binding.linTrace2.setSelected(false);//.setBackgroundResource(R.color.unselect_content_mark);
                traceData.setType(TraceEnumData.TRACE_TYPE.BLANK, idx);
                FunctionHandler.getInstance().getMainLineChart().clearValues(1);

                // 연관된 마커 끄기
                FunctionHandler.getInstance().getMainLineChart().removeMarkerOfTrace(1);
            }

            traceData.setTrace(idx);
            ViewHandler.getInstance().getTrace().addMenu();
            update();
            FunctionHandler.getInstance().getMainLineChart().invalidate();

            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    DataHandler.getInstance().getConfigCmd()
            );

        });

        binding.linTrace3.setOnClickListener(v -> {

            int idx = 2;
            TraceData traceData = SaDataHandler.getInstance().getConfigData().getTraceData();
            TraceEnumData.TRACE_TYPE type = traceData.getType(idx);

            if (type == TraceEnumData.TRACE_TYPE.BLANK) {

                binding.linTrace3.setSelected(true);//.setBackgroundResource(R.color.trace3);
                traceData.setType(TraceEnumData.TRACE_TYPE.UPDATE, idx);

            } else {

                binding.linTrace3.setSelected(false);//.setBackgroundResource(R.color.unselect_content_mark);
                traceData.setType(TraceEnumData.TRACE_TYPE.BLANK, idx);
                FunctionHandler.getInstance().getMainLineChart().clearValues(2);

                // 연관된 마커 끄기
                FunctionHandler.getInstance().getMainLineChart().removeMarkerOfTrace(2);
            }

            traceData.setTrace(idx);
            ViewHandler.getInstance().getTrace().addMenu();
            update();
            FunctionHandler.getInstance().getMainLineChart().invalidate();

            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    DataHandler.getInstance().getConfigCmd()
            );

        });

        binding.linTrace4.setOnClickListener(v -> {

            int idx = 3;
            TraceData traceData = SaDataHandler.getInstance().getConfigData().getTraceData();
            TraceEnumData.TRACE_TYPE type = traceData.getType(idx);

            if (type == TraceEnumData.TRACE_TYPE.BLANK) {

                binding.linTrace4.setSelected(true);//.setBackgroundResource(R.color.trace4);
                traceData.setType(TraceEnumData.TRACE_TYPE.UPDATE, idx);

            } else {

                binding.linTrace4.setSelected(false);//.setBackgroundResource(R.color.unselect_content_mark);
                traceData.setType(TraceEnumData.TRACE_TYPE.BLANK, idx);
                FunctionHandler.getInstance().getMainLineChart().clearValues(3);

                // 연관된 마커 끄기
                FunctionHandler.getInstance().getMainLineChart().removeMarkerOfTrace(3);
            }

            traceData.setTrace(idx);
            ViewHandler.getInstance().getTrace().addMenu();
            update();
            FunctionHandler.getInstance().getMainLineChart().invalidate();

            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    DataHandler.getInstance().getConfigCmd()
            );

        });

        binding.tvMode.setOnClickListener(v -> {

            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

            if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY)
                ViewHandler.getInstance().getModAccuracyMode().addMenu();
            else
                ViewHandler.getInstance().getSAMode().addMenu();

        });

        binding.tvMeas.setOnClickListener(v -> {

            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
            InitActivity.logMsg("CurMode", mode.getString());
            switch (mode) {
                case MOD_ACCURACY:
                    ViewHandler.getInstance().getModAccuracyMeas().addMenu();
                    break;
                case SA:
                    ViewHandler.getInstance().getSaMeas().addMenu();
                    break;
                default:
                    ViewHandler.getInstance().getSaMeas().addMenu();
                    break;

            }


        });

        binding.tvSmallVswr.setOnClickListener(v -> {

            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

            if (mode == MeasureMode.MEASURE_MODE.VSWR && type != MeasureType.MEASURE_TYPE.VSWR) {

                FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.VSWR);
                ViewHandler.getInstance().getMeasurementView().addMenu();

            } else if (mode == MeasureMode.MEASURE_MODE.DTF && type != MeasureType.MEASURE_TYPE.DTF_VSWR) {

                FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.DTF_VSWR);
                ViewHandler.getInstance().getMeasurementView().addMenu();

            } else if (mode == MeasureMode.MEASURE_MODE.CL && type != MeasureType.MEASURE_TYPE.CABLE_LOSS) {

                FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.CABLE_LOSS);
                ViewHandler.getInstance().getFrequencyView().addMenu();

            }

            update();

//            FunctionHandler
//                    .getInstance()
//                    .getDataConnector()
//                    .sendCommand(DataHandler.getInstance().getConfigCmd());

        });

        binding.tvSmallReturnLoss.setOnClickListener(v -> {

            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

            if (mode == MeasureMode.MEASURE_MODE.VSWR && type != MeasureType.MEASURE_TYPE.RL) {

                FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.RL);
                ViewHandler.getInstance().getMeasurementView().addMenu();

            } else if (mode == MeasureMode.MEASURE_MODE.DTF && type != MeasureType.MEASURE_TYPE.DTF_RL) {

                FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.DTF_RL);
                ViewHandler.getInstance().getMeasurementView().addMenu();

            } else if (mode == MeasureMode.MEASURE_MODE.CL && type != MeasureType.MEASURE_TYPE.CABLE_LOSS) {

                FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.CABLE_LOSS);

            }

            update();

//            FunctionHandler
//                    .getInstance()
//                    .getDataConnector()
//                    .sendCommand(DataHandler.getInstance().getConfigCmd());

        });

        binding.tvRecallOff.setOnClickListener(v -> {

            FunctionHandler.getInstance().getMainLineChart().removeRecallLine();
            FunctionHandler.getInstance().getMainLineChart().invalidate();
            binding.tvRecallOff.setVisibility(View.GONE);

        });

        binding.ivMacroImage.setOnClickListener(v -> {

            new CallNameListDialog(mActivity, binding).show();
//            FunctionHandler.getInstance().getMacroDialog().show();

        });

        //binding.tvAutoScale.setOnClickListener(new View.OnClickListener() {
        binding.ivAutoScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if (mode == MeasureMode.MEASURE_MODE.SA) {

                    //@@ [21.12.20] SA. Transmit on / off mode 에서 적용되지 않도록 수정
                    if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {
                        Log.e("Transmit On Off", "SA - Transmit on / off, Do not auto scale");
                        return;
                    }
                    //

                    FunctionHandler.getInstance().getDataConnector().showProgressDialog("Auto Scale...");
                    FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getAutoAttenCmd());
                    DataHandler.getInstance().setAutoAtten(true);
                    FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();

                    /*mActivity.startLoadingAnim();
                    FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getAutoAttenCmd());
                    DataHandler.getInstance().setAutoAtten(true);*/

                } else {
                    FunctionHandler.getInstance().getMainLineChart().autoScale();
                }
            }
        });

        binding.ivGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGpsInfoDialog = new GpsInfoDialog(mActivity);
                mGpsInfoDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mGpsInfoDialog = null;
                    }
                });
                mGpsInfoDialog.show();
            }
        });

        binding.tvRightMenuTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //@@ original source
                /*FunctionHandler.getInstance().getDataConnector().disconnectMqtt();
                FunctionHandler.getInstance().getDataConnector().startCheckMqttConnectionHandler();*/
                //@@ original source


                //FunctionHandler.getInstance().getWifiFunc().onWifiConnected();

//                FunctionHandler.getInstance().getDataConnector().Test();

//                if (binding.layoutTopMenu.getVisibility() == View.VISIBLE) {
//                    binding.layoutTopMenu.setVisibility(View.GONE);
//                    binding.linMainTopSub.setVisibility(View.GONE);
//                    binding.linBottomMenu.setVisibility(View.GONE);
//                    binding.lineChartLayout.conChartInfo.setVisibility(View.GONE);
//                } else {
//                    binding.layoutTopMenu.setVisibility(View.VISIBLE);
//                    binding.linMainTopSub.setVisibility(View.VISIBLE);
//                    binding.linBottomMenu.setVisibility(View.VISIBLE);
//                    binding.lineChartLayout.conChartInfo.setVisibility(View.VISIBLE);
//                }
            }
        });
    }

    public void update() {

        new Handler(Looper.getMainLooper()).post(() -> {

            topIconUpdate();
            smallIconUpdate();
            subInfoUpdate();
            traceIconUpdate();
            markerValueUpdate();
            markerTableUpdate();
            markerIconUpdate();
            updateView();

        });

    }

    public void topIconUpdate() {

        mActivity.runOnUiThread(() -> {

            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
            resetTopViewColor();

            //@@ [22.01.25] 원전 모니터링용 SA 외 기능 block 처리
            if (mode == MeasureMode.MEASURE_MODE.VSWR) {

//                binding.tvVswr.setSelected(true); //selectTopView(binding.tvVswr);

            } else if (mode == MeasureMode.MEASURE_MODE.DTF) {

//                binding.tvDtf.setSelected(true); //selectTopView(binding.tvDtf);

            } else if (mode == MeasureMode.MEASURE_MODE.CL) {

//                binding.tvCableLoss.setSelected(true); //selectTopView(binding.tvCableLoss);

            } else if (mode == MeasureMode.MEASURE_MODE.SA) {

                binding.tvSa.setSelected(true); //selectTopView(binding.tvSa);

            } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {

//                binding.tvModAccuracy.setSelected(true); //selectTopView(binding.tvModAccuracy);

            }

        });


    }

    public void smallIconUpdate() {

        mActivity.runOnUiThread(() -> {

            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

            if (mode == MeasureMode.MEASURE_MODE.VSWR
                    || mode == MeasureMode.MEASURE_MODE.DTF) {

                binding.tvSmallVswr.setVisibility(View.VISIBLE);
                binding.tvSmallReturnLoss.setVisibility(View.VISIBLE);

                binding.tvMeas.setVisibility(View.GONE);
                binding.tvMode.setVisibility(View.GONE);

                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if (type == MeasureType.MEASURE_TYPE.VSWR
                        || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

                    binding.tvSmallVswr.setSelected(true);//.setBackgroundColor(mActivity.getResources().getColor(R.color.select_content_mark));
                    binding.tvSmallReturnLoss.setSelected(false);//.setBackgroundColor(mActivity.getResources().getColor(R.color.unselect_content_mark));

                } else if (type == MeasureType.MEASURE_TYPE.RL
                        || type == MeasureType.MEASURE_TYPE.DTF_RL) {

                    binding.tvSmallVswr.setSelected(false);//.setBackgroundColor(mActivity.getResources().getColor(R.color.unselect_content_mark));
                    binding.tvSmallReturnLoss.setSelected(true);//.setBackgroundColor(mActivity.getResources().getColor(R.color.select_content_mark));

                }


            } else if (mode == MeasureMode.MEASURE_MODE.CL) {

                binding.tvSmallVswr.setVisibility(View.GONE);
                binding.tvSmallReturnLoss.setVisibility(View.GONE);

                binding.tvMeas.setVisibility(View.GONE);
                binding.tvMode.setVisibility(View.GONE);


            } else if (mode.getValue() == MeasureMode.MEASURE_MODE.SA.getValue()) {

                binding.tvSmallVswr.setVisibility(View.GONE);
                binding.tvSmallReturnLoss.setVisibility(View.GONE);

                binding.tvMeas.setVisibility(View.VISIBLE);
//                binding.tvMode.setVisibility(View.VISIBLE);

            } else if (mode.getValue() == MeasureMode.MEASURE_MODE.MOD_ACCURACY.getValue()) {

                binding.tvSmallVswr.setVisibility(View.GONE);
                binding.tvSmallReturnLoss.setVisibility(View.GONE);

                binding.tvMeas.setVisibility(View.VISIBLE);
//                    binding.tvMode.setVisibility(View.VISIBLE);

            } else if (mode == MeasureMode.MEASURE_MODE.SG) {

                binding.tvSmallVswr.setVisibility(View.VISIBLE);
                binding.tvSmallReturnLoss.setVisibility(View.VISIBLE);

                binding.tvMeas.setVisibility(View.GONE);
                binding.tvMode.setVisibility(View.GONE);


            }

        });

    }

    public void traceIconUpdate() {


        mActivity.runOnUiThread(() -> {

            MeasureType.MEASURE_TYPE measureType = FunctionHandler.getInstance().getMeasureType().getType();

            if (FunctionHandler.getInstance().getMeasureMode().getMode() != MeasureMode.MEASURE_MODE.SA) {

                binding.linTrace1.setVisibility(View.GONE);
                binding.linTrace2.setVisibility(View.GONE);
                binding.linTrace3.setVisibility(View.GONE);
                binding.linTrace4.setVisibility(View.GONE);

            } else if (measureType == MeasureType.MEASURE_TYPE.CHANNEL_POWER
                    || measureType == MeasureType.MEASURE_TYPE.OCCUPIED_BW
                    || measureType == MeasureType.MEASURE_TYPE.ACLR
                    || measureType == MeasureType.MEASURE_TYPE.SEM
                    || measureType == MeasureType.MEASURE_TYPE.TRANSMIT_MASK
            ) {

                binding.linTrace1.setVisibility(View.VISIBLE);
                binding.linTrace2.setVisibility(View.GONE);
                binding.linTrace3.setVisibility(View.GONE);
                binding.linTrace4.setVisibility(View.GONE);

            } else {

                binding.linTrace1.setVisibility(View.VISIBLE);
                binding.linTrace2.setVisibility(View.VISIBLE);
                binding.linTrace4.setVisibility(View.VISIBLE);
                binding.linTrace3.setVisibility(View.VISIBLE);

            }

//            Boolean isOn = FunctionHandler.getInstance().getMainLineChart().isVisible(0);
//
//            if (isOn) {
//
//                binding.linTrace1.setBackgroundResource(R.color.trace1);
//
//
//            } else {
//
//                binding.linTrace1.setBackgroundResource(R.color.unselect_content_mark);
//
//
//            }
//
//            isOn = FunctionHandler.getInstance().getMainLineChart().isVisible(1);
//
//            if (isOn) {
//
//                binding.linTrace2.setBackgroundResource(R.color.trace2);
//
//            } else {
//
//                binding.linTrace2.setBackgroundResource(R.color.unselect_content_mark);
//
//            }
//
//
//            isOn = FunctionHandler.getInstance().getMainLineChart().isVisible(2);
//
//            if (isOn) {
//
//                binding.linTrace3.setBackgroundResource(R.color.trace3);
////            ViewHandler.getInstance().getTrace().getSelectView().setText("SaTraceView 3");
//
//            } else {
//
//                binding.linTrace3.setBackgroundResource(R.color.unselect_content_mark);
//
//            }
//
//            isOn = FunctionHandler.getInstance().getMainLineChart().isVisible(3);
//
//            if (isOn) {
//
//                binding.linTrace4.setBackgroundResource(R.color.trace4);
////            ViewHandler.getInstance().getTrace().getSelectView().setText("SaTraceView 4");
//
//
//            } else {
//
//                binding.linTrace4.setBackgroundResource(R.color.unselect_content_mark);
//
//            }

            for (int idx = 0; idx < 4; idx++) {
                TraceData traceData = SaDataHandler.getInstance().getConfigData().getTraceData();
                TraceEnumData.TRACE_TYPE traceType = traceData.getType(idx);

                switch (idx) {
                    case 0:
                        if (traceType == TraceEnumData.TRACE_TYPE.UPDATE) {

                            binding.linTrace1.setSelected(true);//binding.linTrace1.setBackgroundResource(R.color.trace1);

                        } else if (traceType == TraceEnumData.TRACE_TYPE.VIEW) {

                            binding.linTrace1.setSelected(true);//binding.linTrace1.setBackgroundResource(R.color.trace1);

                        } else {

                            binding.linTrace1.setSelected(false);//binding.linTrace1.setBackgroundResource(R.color.unselect_content_mark);

                        }
                        break;
                    case 1:
                        if (traceType == TraceEnumData.TRACE_TYPE.UPDATE) {

                            binding.linTrace2.setSelected(true);//.setBackgroundResource(R.color.trace2);

                        } else if (traceType == TraceEnumData.TRACE_TYPE.VIEW) {

                            binding.linTrace2.setSelected(true);//.setBackgroundResource(R.color.trace2);

                        } else {

                            binding.linTrace2.setSelected(false);//.setBackgroundResource(R.color.unselect_content_mark);

                        }
                        break;
                    case 2:
                        if (traceType == TraceEnumData.TRACE_TYPE.UPDATE) {

                            binding.linTrace3.setSelected(true);//.setBackgroundResource(R.color.trace3);

                        } else if (traceType == TraceEnumData.TRACE_TYPE.VIEW) {

                            binding.linTrace3.setSelected(true);//.setBackgroundResource(R.color.trace3);

                        } else {

                            binding.linTrace3.setSelected(false);//.setBackgroundResource(R.color.unselect_content_mark);

                        }
                        break;
                    case 3:
                        if (traceType == TraceEnumData.TRACE_TYPE.UPDATE) {

                            binding.linTrace4.setSelected(true);//.setBackgroundResource(R.color.trace4);

                        } else if (traceType == TraceEnumData.TRACE_TYPE.VIEW) {

                            binding.linTrace4.setSelected(true);//.setBackgroundResource(R.color.trace4);

                        } else {

                            binding.linTrace4.setSelected(false);//.setBackgroundResource(R.color.unselect_content_mark);

                        }
                        break;

                }


            }


            if (measureType == MeasureType.MEASURE_TYPE.SEM) {
                binding.tvTraceMode1.setText(SaDataHandler.getInstance().getSemConfigData().getTraceData().getSemTraceMode().getSignString());
                binding.tvTraceDetector1.setText(SaDataHandler.getInstance().getSemConfigData().getTraceData().getSemChannelDetector().getSignString());
            } else {
                binding.tvTraceMode1.setText(SaDataHandler.getInstance().getConfigData().getTraceData().getMode(0).getSignString());
                binding.tvTraceDetector1.setText(SaDataHandler.getInstance().getConfigData().getTraceData().getDetector(0).getSignString());
            }

            binding.tvTraceMode2.setText(SaDataHandler.getInstance().getConfigData().getTraceData().getMode(1).getSignString());
            binding.tvTraceDetector2.setText(SaDataHandler.getInstance().getConfigData().getTraceData().getDetector(1).getSignString());

            binding.tvTraceMode3.setText(SaDataHandler.getInstance().getConfigData().getTraceData().getMode(2).getSignString());
            binding.tvTraceDetector3.setText(SaDataHandler.getInstance().getConfigData().getTraceData().getDetector(2).getSignString());

            binding.tvTraceMode4.setText(SaDataHandler.getInstance().getConfigData().getTraceData().getMode(3).getSignString());
            binding.tvTraceDetector4.setText(SaDataHandler.getInstance().getConfigData().getTraceData().getDetector(3).getSignString());

        });

    }

    @SuppressLint({"SetTextI18n"})
    public void subInfoUpdate() {

        new Thread(() -> {

            mActivity.runOnUiThread(() -> {

                try {

                    MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

                    if (mode == MeasureMode.MEASURE_MODE.SA || mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {

                        binding.lineChartLayout.tvChartInfo.setVisibility(View.GONE);

                        double span = SaDataHandler.getInstance().getConfigData().getFrequencyData().getSpan();
                        span = Math.round(span * 1000000d) / 1000000d;
                        String spanInfo = InitActivity.getFrequencyWithUnit(span);
                        if (!spanInfo.equals(binding.lineChartLayout.sweptSaInfo.tvSpanVal.getText().toString()))
                            binding.lineChartLayout.sweptSaInfo.tvSpanVal.setText(spanInfo);

//                        binding.lineChartLayout.sweptSaInfo.tvCenterVal.setText(SaDataHandler.getInstance().getConfigData().getFrequencyData().getCenterFreq() + " MHz");

                        String rbwInfo = SaDataHandler.getInstance().getConfigData().getBwData().getRBW().getString();
                        if (!rbwInfo.equals(binding.lineChartLayout.sweptSaInfo.tvRbwVal.getText().toString()))
                            binding.lineChartLayout.sweptSaInfo.tvRbwVal.setText(rbwInfo);

                        String vbwInfo = SaDataHandler.getInstance().getConfigData().getBwData().getVBW().getString();
                        if (!vbwInfo.equals(binding.lineChartLayout.sweptSaInfo.tvVbwVal.getText().toString()))
                            binding.lineChartLayout.sweptSaInfo.tvVbwVal.setText(vbwInfo);

                        String sweepInfo = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getSweepTimeToString();
                        if (!sweepInfo.equals(binding.lineChartLayout.sweptSaInfo.tvSweepVal.getText().toString()))
                            binding.lineChartLayout.sweptSaInfo.tvSweepVal.setText(sweepInfo);


                        String refVal = Utils.formatNumber(SaDataHandler.getInstance().getConfigData().getAmplitudeData().getRefLev(), 1, false) + " dBm";
                        InitActivity.logMsg("updateSubInfo", refVal);
                        if (!refVal.equals(binding.lineChartLayout.sweptSaInfo.tvRefVal.getText().toString()))
                            binding.lineChartLayout.sweptSaInfo.tvRefVal.setText(refVal);

                        String attenVal = SaDataHandler.getInstance().getConfigData().getAmplitudeData().getAttenuator() + " dB";
                        if (!attenVal.equals(binding.lineChartLayout.sweptSaInfo.tvAttenVal.getText().toString()))
                            binding.lineChartLayout.sweptSaInfo.tvAttenVal.setText(attenVal);

                        String offsetVal = SaDataHandler.getInstance().getConfigData().getAmplitudeData().getOffset() + " dB";
                        if (!offsetVal.equals(binding.lineChartLayout.sweptSaInfo.tvOffsetVal.getText().toString()))
                            binding.lineChartLayout.sweptSaInfo.tvOffsetVal.setText(offsetVal);

                        if (SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().getChannelPower() != null) {
                            ChannelPowerMeasSetupData chMeasData = SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData();
                            float chPower = chMeasData.getChannelPower();
                            double integ = chMeasData.getIntegMHzVal();

                            //org
                            /*binding.lineChartLayout.channelPowerLayout.tvChannelPowerVal.setText(
                                    Utils.formatNumber(chPower, 2, false) + " dBm /" +
                                            Utils.formatNumber((float) integ, 2, false) + " MHz"
                            );*/
                            //org

                            //@@ [21.12.20] integ BW 반올림 셋째짜리까지 수정
                            binding.lineChartLayout.channelPowerLayout.tvChannelPowerVal.setText(
                                    Utils.formatNumber(chPower, 2, false) + " dBm /" +
                                            Utils.formatNumber((float) integ, 3, false) + " MHz"
                            );
                            //@@

                        } else
                            binding.lineChartLayout.channelPowerLayout.tvChannelPowerVal.setText("");

                        if (SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().getDensity() != null) {

                            String value = Utils.formatNumber(SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().getDensity(), 2, false);

                            binding.lineChartLayout.channelPowerLayout.tvDensityVal.setText(
                                    value + " dBm / Hz"
                            );

                        } else
                            binding.lineChartLayout.channelPowerLayout.tvDensityVal.setText("");

                        /*====================================================================================*/

                        if (SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getOccupiedBW() != null) {

                            String value = Utils.formatNumber(SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getOccupiedBW(), 2, false);

                            binding.lineChartLayout.occBwLayout.tvOccBWVal.setText(
                                    value + " MHz"
                            );

                        } else
                            binding.lineChartLayout.occBwLayout.tvOccBWVal.setText("");


                        if (SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getTotalPower() != null) {

                            String value = Utils.formatNumber(SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getTotalPower(), 2, false);

                            binding.lineChartLayout.occBwLayout.tvTotalPowerVal.setText(
                                    value + " dBm"
                            );

                        } else
                            binding.lineChartLayout.occBwLayout.tvTotalPowerVal.setText("");

                        if (SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getXDB() != null) {
                            String xdbValue = Utils.formatNumber(SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getXDB(), 2, false);
                            binding.lineChartLayout.occBwLayout.tvXDBVal.setText(
                                    xdbValue + " dB"
                            );

                        } else {

                            binding.lineChartLayout.occBwLayout.tvXDBVal.setText("");

                        }


                        if (SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getXDBBW() != null) {

                            String value = Utils.formatNumber(SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getXDBBW(), 2, false);

                            binding.lineChartLayout.occBwLayout.tvXDBBWVal.setText(
                                    value + " MHz"
                            );

                        } else
                            binding.lineChartLayout.occBwLayout.tvXDBBWVal.setText("");

                        if (FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.SEM) {

                            SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getEditMaskData().updateValue();
                            SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getRefChannelData().updateIntegValue();


                            //@@ [21.12.30] SEM Sub info fix
                            /*SemEditMaskData data = SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData();*/
                            SemMeasSetupData data = SaDataHandler.getInstance().getConfigData().getSemMeasSetupData();
                            for (int i = 0; i < 4; i++) {
                                data.getEditMaskData().resetSubInfo(data.getEditMaskData().getMaskOnOff(i), i);
                            }
                            //@@

                            SaConfigData semConfigData = SaDataHandler.getInstance().getSemConfigData();
                            double semCenterFreq = semConfigData.getFrequencyData().getCenterFreq();
                            double semSpan = semConfigData.getFrequencyData().getSpan();

                            semCenterFreq = Math.round(semCenterFreq * 100d) / 100d;
                            semSpan = Math.round(semSpan * 100d) / 100d;

                            binding.lineChartLayout.semLayout.tvSemCenterFreqVal.setText(
                                    semCenterFreq + " MHz"
                            );

                            binding.lineChartLayout.semLayout.tvSemSpanFreqVal.setText(
                                    semSpan + " MHz"
                            );

                            attenVal = SaDataHandler.getInstance().getConfigData().getAmplitudeData().getAttenuator() + " dB";
                            if (!attenVal.equals(binding.lineChartLayout.semLayout.tvAttenVal.getText().toString()))
                                binding.lineChartLayout.semLayout.tvAttenVal.setText(attenVal);

                            offsetVal = SaDataHandler.getInstance().getConfigData().getAmplitudeData().getOffset() + " dB";
                            if (!offsetVal.equals(binding.lineChartLayout.semLayout.tvOffsetVal.getText().toString()))
                                binding.lineChartLayout.semLayout.tvOffsetVal.setText(offsetVal);


                            if(data.getSemResult() == 1) {
                                //binding.lineChartLayout.semLayout.tvSemPassTitle.setTextColor(R.color.transmit_pass);
                                binding.lineChartLayout.semLayout.tvSemPassTitle.setText(R.string.pass_result);
                            }
                            else if(data.getSemResult() == 0) {
                                //binding.lineChartLayout.semLayout.tvSemPassTitle.setTextColor(R.color.transmit_fail);
                                binding.lineChartLayout.semLayout.tvSemPassTitle.setText(R.string.fail_result);
                            }


                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        }).start();

    }

    public void markerValueUpdate() {
        final MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        if (type == MeasureType.MEASURE_TYPE.NR_5G || type == MeasureType.MEASURE_TYPE.LTE_FDD || type == MeasureType.MEASURE_TYPE.NR_5G_SCAN)
            return;

        final int selectedMarkerIdx = FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex();

        if (selectedMarkerIdx == -1) {
            if (binding.conMarkerInfo != null && binding.conMarkerInfo.getVisibility() == View.VISIBLE) {
                binding.conMarkerInfo.post(() -> binding.conMarkerInfo.setVisibility(View.INVISIBLE));
            }
            return;
        }

        mActivity.runOnUiThread(() -> { //new Thread(() -> {
            try {
                final MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

                binding.conMarkerInfo.setVisibility(View.VISIBLE);
                String strName = "Mkr " + (selectedMarkerIdx + 1) + " : ";
                if (!strName.equals(binding.tvMarkerValLabel.getText().toString()))
                    binding.tvMarkerValLabel.setText(strName);

                String x = FunctionHandler.getInstance().getMainLineChart().getCurrentMarkerFreqToStr(selectedMarkerIdx);
                float y = FunctionHandler.getInstance().getMainLineChart().getMarkerPos()[1];
//                    x = Math.round(x * 100d) / 100d;
                InitActivity.logMsg("markerValueUpdate", "x : " + x + " y : " + y);
                if (mode == MeasureMode.MEASURE_MODE.SA && SaDataHandler.getInstance().getConfigData().getFrequencyData().getSpan() == 0f) {
                    x += " mS";
                } else if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {
                    x += " ms";
                } else if (mode != MeasureMode.MEASURE_MODE.DTF) {
                    x += " MHz";
                } else {
                    x += "m";
                }

                if (!x.equals(binding.tvMarkerX.getText().toString()))
                    binding.tvMarkerX.setText(x);

                if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {
                    binding.tvMarkerY.setText(Utils.formatNumber(y, 2, false));
                } else if (type == MeasureType.MEASURE_TYPE.RL || type == MeasureType.MEASURE_TYPE.DTF_RL || type == MeasureType.MEASURE_TYPE.CABLE_LOSS) {
                    binding.tvMarkerY.setText(Utils.formatNumber(y, 2, false) + " dB");
                } else if (mode.getValue() == MeasureMode.MEASURE_MODE.SA.getValue()) {
                    String unit = " dBm";
                    if (FunctionHandler.getInstance().getMainLineChart().getMarker() == null)
                        return;
                    if (FunctionHandler.getInstance().getMainLineChart().getMarker().isDelta())
                        unit = " dB";

                    binding.tvMarkerY.setText(Utils.formatNumber(y, 2, false) + unit);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });//.start();
    }

    public void markerIconUpdate() {

        new Thread(() -> {

            try {

                mActivity.runOnUiThread(() -> {

                    ArrayList<Highlight> list = FunctionHandler.getInstance().getMainLineChart().getMarkerList();
                    int listSize = list.size();

                    if (listSize <= 0 || list.get(0) == null) {
                        binding.tvMarker1.setSelected(false);//.setBackgroundResource(R.color.unselect_content_mark);
                    } else {
                        binding.tvMarker1.setSelected(true);//.setBackgroundResource(R.color.select_content_mark);
                    }

                    if (listSize <= 1 || list.get(1) == null) {
                        binding.tvMarker2.setSelected(false);//.setBackgroundResource(R.color.unselect_content_mark);
                    } else {
                        binding.tvMarker2.setSelected(true);//.setBackgroundResource(R.color.select_content_mark);
                    }

                    if (listSize <= 2 || list.get(2) == null) {
                        binding.tvMarker3.setSelected(false);//.setBackgroundResource(R.color.unselect_content_mark);
                    } else {
                        binding.tvMarker3.setSelected(true);//.setBackgroundResource(R.color.select_content_mark);
                    }

                    if (listSize <= 3 || list.get(3) == null) {
                        binding.tvMarker4.setSelected(false);//.setBackgroundResource(R.color.unselect_content_mark);
                    } else {
                        binding.tvMarker4.setSelected(true);//.setBackgroundResource(R.color.select_content_mark);
                    }

                    if (listSize <= 4 || list.get(4) == null) {
                        binding.tvMarker5.setSelected(false);//.setBackgroundResource(R.color.unselect_content_mark);
                    } else {
                        binding.tvMarker5.setSelected(true);//.setBackgroundResource(R.color.select_content_mark);
                    }

                });

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

                if (mode == MeasureMode.MEASURE_MODE.VSWR) {

                    ViewHandler.getInstance().getMarkerSetupView().update();

                } else if (mode == MeasureMode.MEASURE_MODE.DTF) {


                } else if (mode == MeasureMode.MEASURE_MODE.CL) {

                } else if (mode.getValue() == MeasureMode.MEASURE_MODE.SA.getValue()) {

                    ViewHandler.getInstance().getSaMarkerView().update();

                } else if (mode == MeasureMode.MEASURE_MODE.SG) {


                }

            } catch (NullPointerException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        }).start();

    }

    public void markerTableUpdate() {
        try {
            if (binding.lineChartLayout.conMarkerTableParent.getVisibility() != View.VISIBLE)
                return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //new Thread(() -> {
            mActivity.runOnUiThread(() -> {

                try {
                    MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                    MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
                    MainLineChartFunc chart = FunctionHandler.getInstance().getMainLineChart();

                    String xUnit;
                    String yUnit;

                    if (mode == MeasureMode.MEASURE_MODE.SA && SaDataHandler.getInstance().getConfigData().getFrequencyData().getSpan() == 0f) {
                        xUnit = " mS";
                    } else if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {
                        xUnit = " ms";
                    } else if (mode != MeasureMode.MEASURE_MODE.DTF)
                        xUnit = " MHz";
                    else xUnit = "";

                    if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR)
                        yUnit = "";
                    else if (type == MeasureType.MEASURE_TYPE.RL || type == MeasureType.MEASURE_TYPE.DTF_RL)
                        yUnit = " dB";
                    else if (mode.getValue() == MeasureMode.MEASURE_MODE.SA.getValue())
                        yUnit = " dB";
                    else yUnit = "";

                    if (chart.isOnMarker(0)) {

                        float[] pos = chart.getMarkerPos(0);
                        String x = FunctionHandler.getInstance().getMainLineChart().getCurrentMarkerFreqToStr(0);
                        String y = Utils.formatNumber(pos[1], 2, false);
                        int trace = chart.getMarkerTrace(0);

                        Highlight marker = chart.getMarker(0);
                        String deltaUnit = "m";
                        if (mode == MeasureMode.MEASURE_MODE.SA && marker.isDelta())
                            deltaUnit = "";
                        else if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL)
                            deltaUnit = "";

                        String markerType = "N";
                        if (chart.isFixed(0))
                            markerType = "F";
                        else if (chart.isDelta(0)) {
                            markerType = "Δ" + (chart.getRefMarkerIndex(0) + 1);
                        }
                        String finalMarkerType = markerType;
                        String finalDeltaUnit = deltaUnit;
                        String finalX = x;

                        //mActivity.runOnUiThread(() -> {
                        String markerName = "M1(" + finalMarkerType + ")";
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerName1.getText().toString().equals(markerName))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerName1.setText(markerName);

                        String markerTrace = "T" + (trace + 1);
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace1.getText().toString().equals(markerTrace))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace1.setText(markerTrace);

                        String markerX = finalX + xUnit;
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerX1.getText().toString().equals(markerX))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerX1.setText(markerX);

                        String markerY = y + yUnit + finalDeltaUnit;
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerY1.getText().toString().equals(markerY))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerY1.setText(markerY);
                        //});
                    } else {
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerName1.getText().toString().equals("")) {
                            //mActivity.runOnUiThread(() -> {
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerName1.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace1.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerX1.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerY1.setText("");
                            //});
                        }
                    }

                    if (chart.isOnMarker(1)) {
                        String markerType = "N";
                        if (chart.isFixed(1))
                            markerType = "F";
                        else if (chart.isDelta(1)) {
                            markerType = "Δ" + (chart.getRefMarkerIndex(1) + 1);
                        }

                        Highlight marker = chart.getMarker(1);
                        String deltaUnit = "m";
                        if (mode == MeasureMode.MEASURE_MODE.SA && marker.isDelta())
                            deltaUnit = "";
                        else if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL)
                            deltaUnit = "";

                        float[] pos = chart.getMarkerPos(1);
                        String x = FunctionHandler.getInstance().getMainLineChart().getCurrentMarkerFreqToStr(1);
                        String y = Utils.formatNumber(pos[1], 2, false);
                        int trace = chart.getMarkerTrace(1);

                        String finalMarkerType = markerType;
                        String finalDeltaUnit = deltaUnit;

                        String finalX = x;
//                        mActivity.runOnUiThread(() -> {
                        String markerName = "M2(" + finalMarkerType + ")";
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerName2.getText().toString().equals(markerName))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerName2.setText(markerName);

                        String markerTrace = "T" + (trace + 1);
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace2.getText().toString().equals(markerTrace))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace2.setText(markerTrace);

                        String markerX = finalX + xUnit;
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerX2.getText().toString().equals(markerX))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerX2.setText(markerX);

                        String markerY = y + yUnit + finalDeltaUnit;
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerY2.getText().toString().equals(markerY))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerY2.setText(markerY);
                        //});
                    } else {
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerName2.getText().toString().equals("")) {
                            //mActivity.runOnUiThread(() -> {
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerName2.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace2.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerX2.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerY2.setText("");
                            //});
                        }
                    }

                    if (chart.isOnMarker(2)) {
                        String markerType = "N";
                        if (chart.isFixed(2))
                            markerType = "F";
                        else if (chart.isDelta(2)) {
                            markerType = "Δ" + (chart.getRefMarkerIndex(2) + 1);
                        }

                        Highlight marker = chart.getMarker(2);
                        String deltaUnit = "m";
                        if (mode == MeasureMode.MEASURE_MODE.SA && marker.isDelta())
                            deltaUnit = "";
                        else if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL)
                            deltaUnit = "";

                        float[] pos = chart.getMarkerPos(2);
                        String x = FunctionHandler.getInstance().getMainLineChart().getCurrentMarkerFreqToStr(2);
                        String y = Utils.formatNumber(pos[1], 2, false);
                        int trace = chart.getMarkerTrace(2);

                        String finalMarkerType = markerType;
                        String finalDeltaUnit = deltaUnit;
                        String finalX = x;
                        //mActivity.runOnUiThread(() -> {
                        String markerName = "M3(" + finalMarkerType + ")";
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerName3.getText().toString().equals(markerName))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerName3.setText(markerName);

                        String markerTrace = "T" + (trace + 1);
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace3.getText().toString().equals(markerTrace))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace3.setText(markerTrace);

                        String markerX = finalX + xUnit;
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerX3.getText().toString().equals(markerX))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerX3.setText(markerX);

                        String markerY = y + yUnit + finalDeltaUnit;
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerY3.getText().toString().equals(markerY))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerY3.setText(markerY);
                        //});
                    } else {
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerName3.getText().toString().equals("")) {
                            //mActivity.runOnUiThread(() -> {
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerName3.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace3.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerX3.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerY3.setText("");
                            //});
                        }
                    }

                    if (chart.isOnMarker(3)) {
                        String markerType = "N";
                        if (chart.isFixed(3))
                            markerType = "F";
                        else if (chart.isDelta(3)) {
                            markerType = "Δ" + (chart.getRefMarkerIndex(3) + 1);
                        }

                        Highlight marker = chart.getMarker(3);
                        String deltaUnit = "m";
                        if (mode == MeasureMode.MEASURE_MODE.SA && marker.isDelta())
                            deltaUnit = "";
                        else if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL)
                            deltaUnit = "";

                        float[] pos = chart.getMarkerPos(3);
                        String x = FunctionHandler.getInstance().getMainLineChart().getCurrentMarkerFreqToStr(3);
                        String y = Utils.formatNumber(pos[1], 2, false);
                        int trace = chart.getMarkerTrace(3);
                        String finalMarkerType = markerType;

                        String finalDeltaUnit = deltaUnit;
                        String finalX = x;
                        //mActivity.runOnUiThread(() -> {
                        String markerName = "M4(" + finalMarkerType + ")";
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerName4.getText().toString().equals(markerName))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerName4.setText(markerName);

                        String markerTrace = "T" + (trace + 1);
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace4.getText().toString().equals(markerTrace))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace4.setText(markerTrace);

                        String markerX = finalX + xUnit;
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerX4.getText().toString().equals(markerX))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerX4.setText(markerX);

                        String markerY = y + yUnit + finalDeltaUnit;
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerY4.getText().toString().equals(markerY))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerY4.setText(markerY);
                        //});
                    } else {
                        //mActivity.runOnUiThread(() -> {
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerName4.getText().toString().equals("")) {
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerName4.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace4.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerX4.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerY4.setText("");
                        }
                        //});
                    }

                    if (chart.isOnMarker(4)) {
                        float[] pos = chart.getMarkerPos(4);
                        String x = FunctionHandler.getInstance().getMainLineChart().getCurrentMarkerFreqToStr(4);
                        String y = Utils.formatNumber(pos[1], 2, false);
                        int trace = chart.getMarkerTrace(4);

                        Highlight marker = chart.getMarker(4);
                        String deltaUnit = "m";
                        if (mode == MeasureMode.MEASURE_MODE.SA && marker.isDelta())
                            deltaUnit = "";
                        else if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL)
                            deltaUnit = "";

                        String markerType = "N";
                        if (chart.isFixed(4))
                            markerType = "F";
                        else if (chart.isDelta(4)) {
                            markerType = "Δ" + (chart.getRefMarkerIndex(4) + 1);
                        }

                        String finalMarkerType = markerType;
                        String finalDeltaUnit = deltaUnit;

                        String finalX = x;
                        //mActivity.runOnUiThread(() -> {
                        String markerName = "M5(" + finalMarkerType + ")";
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerName5.getText().toString().equals(markerName))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerName5.setText(markerName);

                        String markerTrace = "T" + (trace + 1);
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace5.getText().toString().equals(markerTrace))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace5.setText(markerTrace);

                        String markerX = finalX + xUnit;
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerX5.getText().toString().equals(markerX))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerX5.setText(markerX);

                        String markerY = y + yUnit + finalDeltaUnit;
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerY5.getText().toString().equals(markerY))
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerY5.setText(markerY);
                        //});
                    } else {
                        if (!binding.lineChartLayout.markerTableLayout.tvTableMarkerName5.getText().toString().equals("")) {
                            //mActivity.runOnUiThread(() -> {
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerName5.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace5.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerX5.setText("");
                            binding.lineChartLayout.markerTableLayout.tvTableMarkerY5.setText("");
                            //});
                        }
                    }
                } catch (
                        NullPointerException e) {
                    e.printStackTrace();
                }
            });
        //}).start();
    }

    private void resetTopViewColor() {
        //@@ [22.01.25] 원전 모니터링용 SA 외 기능 block 처리
        mActivity.runOnUiThread(() -> {
//            binding.tvVswr.setSelected(false);
//            binding.tvDtf.setSelected(false);
//            binding.tvCableLoss.setSelected(false);
            binding.tvSa.setSelected(false);
//            binding.tvModAccuracy.setSelected(false);
        });
    }

    public void showSaInfo(Boolean on) {

        mActivity.runOnUiThread(() -> {

            if (on) {

//                binding.lineChartLayout.tvDBM.setVisibility(View.VISIBLE);
                binding.lineChartLayout.sweptSaInfo.tvRef.setVisibility(View.VISIBLE);
                binding.lineChartLayout.sweptSaInfo.tvRefVal.setVisibility(View.VISIBLE);
                binding.lineChartLayout.sweptSaInfo.tvAtten.setVisibility(View.VISIBLE);
                binding.lineChartLayout.sweptSaInfo.tvAttenVal.setVisibility(View.VISIBLE);
                binding.lineChartLayout.sweptSaInfo.tvOffset.setVisibility(View.VISIBLE);
                binding.lineChartLayout.sweptSaInfo.tvOffsetVal.setVisibility(View.VISIBLE);
                binding.lineChartLayout.tvChartInfo.setVisibility(View.GONE);

            } else {

//                binding.lineChartLayout.sweptSaInfo.tvDBM.setVisibility(View.INVISIBLE);
                binding.lineChartLayout.sweptSaInfo.tvRef.setVisibility(View.INVISIBLE);
                binding.lineChartLayout.sweptSaInfo.tvRefVal.setVisibility(View.INVISIBLE);
                binding.lineChartLayout.sweptSaInfo.tvAtten.setVisibility(View.INVISIBLE);
                binding.lineChartLayout.sweptSaInfo.tvAttenVal.setVisibility(View.INVISIBLE);
                binding.lineChartLayout.sweptSaInfo.tvOffset.setVisibility(View.INVISIBLE);
                binding.lineChartLayout.sweptSaInfo.tvOffsetVal.setVisibility(View.INVISIBLE);

            }


        });


    }

    public void setMarkerTable(Boolean on) {

        isOpenMarkeTable = on;

    }

    public Boolean isOpenMarkerTable() {

        return isOpenMarkeTable;

    }

    public void showMarkerView(boolean on) {

        mActivity.runOnUiThread(() -> {

            if (on) {

                binding.tvMarker1.setVisibility(View.VISIBLE);
                binding.tvMarker2.setVisibility(View.VISIBLE);
                binding.tvMarker3.setVisibility(View.VISIBLE);
                binding.tvMarker4.setVisibility(View.VISIBLE);
                binding.tvMarker5.setVisibility(View.VISIBLE);


            } else {

                binding.tvMarker1.setVisibility(View.GONE);
                binding.tvMarker2.setVisibility(View.GONE);
                binding.tvMarker3.setVisibility(View.GONE);
                binding.tvMarker4.setVisibility(View.GONE);
                binding.tvMarker5.setVisibility(View.GONE);

            }
        });

    }

    public void updateView() {

        /*if PACT is enabled*/
//        binding.tvSa.setEnabled(false);

        //Adjust (chart view and sub info view) weight

        new Thread(() -> {

            mActivity.runOnUiThread(() -> {

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if (mode == MeasureMode.MEASURE_MODE.NONE) {
                    binding.linQuickMenu.setVisibility(View.VISIBLE);
                    binding.linMainRight.setVisibility(View.INVISIBLE);
                    binding.linMainTopSub.setVisibility(View.INVISIBLE);
                    return;
                } else {
                    binding.linParent.setBackgroundResource(R.color.bg);
                    binding.linQuickMenu.setVisibility(View.GONE);
                    binding.linMainRight.setVisibility(View.VISIBLE);
                    binding.linMainTopSub.setVisibility(View.VISIBLE);
                }

                if (type == MeasureType.MEASURE_TYPE.NR_5G_SCAN) {
                    // 기존 정보 삭제
                    FunctionHandler.getInstance().getNrScanFunc().clear();
                    FunctionHandler.getInstance().getNrScanTrace1CharFunc().changeTrace(true);
                    FunctionHandler.getInstance().getNrScanTrace2CharFunc().changeTrace(true);
                } else {
                    // 5G NR Scan 화면이 닫힐 경우 Scan stop
                    DataHandler.getInstance().getNrScanData().setStart(false);
                }

                if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && (type == MeasureType.MEASURE_TYPE.NR_5G || type == MeasureType.MEASURE_TYPE.TAE)) {

                    binding.lineChartLayout.parent.setVisibility(View.GONE);
                    //binding.lineChartLayout.linChartParent.setVisibility(View.GONE);
                    showSaInfo(false);
                    binding.demodulationLayout.linDemodulation.setVisibility(View.VISIBLE);
                    binding.demodulationLayout.tvThrdValue.setText(DataHandler.getInstance().getNrData().getLimitData().getTae() + " ns");
                    binding.lteFddLayout.parent.setVisibility(View.INVISIBLE);
                    binding.nrScanLayout.lin5gNrScan.setVisibility(View.INVISIBLE);

                    binding.tvMeas.setVisibility(View.GONE);
                    binding.tvRecallOff.setVisibility(View.INVISIBLE);
                    binding.tvMode.setVisibility(View.VISIBLE);
                    binding.linNrFwStatus.setVisibility(View.VISIBLE);
                    binding.linSaFwStatus.setVisibility(View.GONE);

                    binding.linMarkerBox.setVisibility(View.INVISIBLE);
                    binding.conMarkerInfo.setVisibility(View.INVISIBLE);
                    binding.conTitleParent.setVisibility(View.VISIBLE);
                    binding.tvTopCenterTitle.setText("5G NR(FR1)");
                    binding.tvTopCenterTitle.setVisibility(View.VISIBLE);


//                    binding.demodulationLayout.scatterChart.resetViewPortOffsets();
//                    float offsetBottom = binding.demodulationLayout.scatterChart.getViewPortHandler().offsetBottom();
//                    float offsetTop = binding.demodulationLayout.scatterChart.getViewPortHandler().offsetTop();
//                    float offsetLeft = binding.demodulationLayout.scatterChart.getViewPortHandler().offsetLeft();
//                    float offsetRight = binding.demodulationLayout.scatterChart.getViewPortHandler().offsetRight();
//                    Log.d("KDK-", "initChart: " + offsetLeft + ", " + offsetTop + ", " + offsetRight + ", " + offsetBottom);
//                    binding.demodulationLayout.scatterChart.setViewPortOffsets(offsetLeft + 20, offsetTop + 10, offsetRight + 40, offsetBottom + 10);

                    InitActivity.logMsg("updateView", "5GNR Open");

                    return;
                } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && type == MeasureType.MEASURE_TYPE.NR_5G_SCAN) {

                    binding.lineChartLayout.parent.setVisibility(View.GONE);
                    showSaInfo(false);
                    binding.demodulationLayout.linDemodulation.setVisibility(View.INVISIBLE);
                    binding.lteFddLayout.parent.setVisibility(View.INVISIBLE);
                    binding.nrScanLayout.lin5gNrScan.setVisibility(View.VISIBLE);

                    binding.tvMeas.setVisibility(View.GONE);
                    binding.tvRecallOff.setVisibility(View.INVISIBLE);
                    binding.tvMode.setVisibility(View.VISIBLE);
                    binding.linNrFwStatus.setVisibility(View.VISIBLE);
                    binding.linSaFwStatus.setVisibility(View.GONE);

                    binding.linMarkerBox.setVisibility(View.INVISIBLE);
                    binding.conMarkerInfo.setVisibility(View.INVISIBLE);
                    binding.conTitleParent.setVisibility(View.VISIBLE);
                    binding.tvTopCenterTitle.setText("5G NR(FR1)-PCI Scan");
                    binding.tvTopCenterTitle.setVisibility(View.VISIBLE);

                    return;
                } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && type == MeasureType.MEASURE_TYPE.LTE_FDD) {

                    DataHandler.getInstance().getLteFddData().update();
                    binding.lineChartLayout.parent.setVisibility(View.GONE);
                    showSaInfo(false);
                    binding.lteFddLayout.parent.setVisibility(View.VISIBLE);
                    binding.nrScanLayout.lin5gNrScan.setVisibility(View.INVISIBLE);

                    binding.tvMeas.setVisibility(View.GONE);
                    binding.tvMode.setVisibility(View.VISIBLE);
                    binding.linNrFwStatus.setVisibility(View.VISIBLE);
                    binding.linSaFwStatus.setVisibility(View.GONE);

                    binding.demodulationLayout.linDemodulation.setVisibility(View.INVISIBLE);
                    binding.linMarkerBox.setVisibility(View.INVISIBLE);
                    binding.conMarkerInfo.setVisibility(View.INVISIBLE);
                    binding.conTitleParent.setVisibility(View.VISIBLE);
                    binding.tvTopCenterTitle.setText("LTE FDD");
                    binding.tvTopCenterTitle.setVisibility(View.VISIBLE);
                    InitActivity.logMsg("updateView", "LTE FDD Open");

//                    binding.lteFddLayout.scatterChart.resetViewPortOffsets();
//                    float offsetBottom = binding.lteFddLayout.scatterChart.getViewPortHandler().offsetBottom();
//                    float offsetTop = binding.lteFddLayout.scatterChart.getViewPortHandler().offsetTop();
//                    float offsetLeft = binding.lteFddLayout.scatterChart.getViewPortHandler().offsetLeft();
//                    float offsetRight = binding.lteFddLayout.scatterChart.getViewPortHandler().offsetRight();
//                    Log.d("KDK-", "initChart: " + offsetLeft + ", " + offsetTop + ", " + offsetRight + ", " + offsetBottom);
//                    binding.lteFddLayout.scatterChart.setViewPortOffsets(offsetLeft + 20, offsetTop + 10, offsetRight + 40, offsetBottom + 10);

                    return;

                } else if (mode == MeasureMode.MEASURE_MODE.SA) {
                    binding.tvMeas.setVisibility(View.VISIBLE);
                    binding.tvMode.setVisibility(View.GONE);
                    binding.linNrFwStatus.setVisibility(View.GONE);
                    binding.linSaFwStatus.setVisibility(View.VISIBLE);

                    binding.lineChartLayout.parent.setVisibility(View.VISIBLE);
                    binding.demodulationLayout.linDemodulation.setVisibility(View.INVISIBLE);
                    binding.lteFddLayout.parent.setVisibility(View.INVISIBLE);
                    binding.nrScanLayout.lin5gNrScan.setVisibility(View.INVISIBLE);
                    binding.linMarkerBox.setVisibility(View.VISIBLE);
                    binding.conMarkerInfo.setVisibility(View.INVISIBLE);
                    binding.conTitleParent.setVisibility(View.GONE);
                    InitActivity.logMsg("updateView", "LTE FDD & 5GNR Close");

                } else {
                    binding.tvMeas.setVisibility(View.GONE);
                    binding.tvMode.setVisibility(View.GONE);
                    binding.linNrFwStatus.setVisibility(View.GONE);
                    binding.linSaFwStatus.setVisibility(View.GONE);

                    binding.lineChartLayout.parent.setVisibility(View.VISIBLE);
                    binding.demodulationLayout.linDemodulation.setVisibility(View.INVISIBLE);
                    binding.lteFddLayout.parent.setVisibility(View.INVISIBLE);
                    binding.nrScanLayout.lin5gNrScan.setVisibility(View.INVISIBLE);
                    binding.linMarkerBox.setVisibility(View.VISIBLE);
                    binding.conMarkerInfo.setVisibility(View.INVISIBLE);
                    binding.conTitleParent.setVisibility(View.GONE);
                    InitActivity.logMsg("updateView", "LTE FDD & 5GNR Close");
                }

                InitActivity.logMsg("type", type.getFullName());

                float sumWeight = 0f;

                if (mode == MeasureMode.MEASURE_MODE.SA
                        && SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData().getGateView() == SaGateData.GATE_MODE.ON) {

                    binding.lineChartLayout.linGateView.setVisibility(View.VISIBLE);

                    sumWeight += GATE_FUNC_WEIGHT;

                } else {

                    binding.lineChartLayout.linGateView.setVisibility(View.GONE);

                }

                if (isOpenMarkeTable) {

                    binding.lineChartLayout.conMarkerTableParent.setVisibility(View.VISIBLE);
                    sumWeight += MARKER_TABLE_WEIGHT;

                } else {
                    binding.lineChartLayout.conMarkerTableParent.setVisibility(View.GONE);
                }

                if (mode == MeasureMode.MEASURE_MODE.SA && type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

                    showSaInfo(true);

                    binding.lineChartLayout.tvChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conChannelPower.setVisibility(View.GONE);
                    binding.lineChartLayout.conOccupiedBW.setVisibility(View.GONE);
                    binding.lineChartLayout.conChartInfo.setVisibility(View.VISIBLE);
                    binding.lineChartLayout.linAclrInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linSemInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linTransmitOnOffInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conSpuriousInfoParent.setVisibility(View.GONE);
                    binding.lineChartLayout.conInterferenceInfo.setVisibility(View.GONE);

                    sumWeight += SWEPT_INFO_WEIGHT;

                } else if (mode == MeasureMode.MEASURE_MODE.SA && type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

                    showSaInfo(true);

                    binding.lineChartLayout.tvChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conChannelPower.setVisibility(View.VISIBLE);
                    binding.lineChartLayout.conOccupiedBW.setVisibility(View.GONE);
                    binding.lineChartLayout.conChartInfo.setVisibility(View.VISIBLE);
                    binding.lineChartLayout.linAclrInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linSemInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linTransmitOnOffInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conSpuriousInfoParent.setVisibility(View.GONE);
                    binding.lineChartLayout.conInterferenceInfo.setVisibility(View.GONE);

                    sumWeight += (CHANNEL_POWER_INFO_WEIGHT + SWEPT_INFO_WEIGHT);

                } else if (mode == MeasureMode.MEASURE_MODE.SA && type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

                    showSaInfo(true);

                    binding.lineChartLayout.tvChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conChannelPower.setVisibility(View.GONE);
                    binding.lineChartLayout.conOccupiedBW.setVisibility(View.VISIBLE);
                    binding.lineChartLayout.conChartInfo.setVisibility(View.VISIBLE);
                    binding.lineChartLayout.linAclrInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linSemInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linTransmitOnOffInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conSpuriousInfoParent.setVisibility(View.GONE);
                    binding.lineChartLayout.conInterferenceInfo.setVisibility(View.GONE);

                    sumWeight += (OCC_INFO_WEIGHT + SWEPT_INFO_WEIGHT);

                } else if (mode == MeasureMode.MEASURE_MODE.SA && type == MeasureType.MEASURE_TYPE.ACLR) {

                    showSaInfo(true);

                    binding.lineChartLayout.tvChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conChannelPower.setVisibility(View.GONE);
                    binding.lineChartLayout.conOccupiedBW.setVisibility(View.GONE);
                    binding.lineChartLayout.linSemInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conChartInfo.setVisibility(View.VISIBLE);
                    binding.lineChartLayout.linAclrInfo.setVisibility(View.VISIBLE);
                    binding.lineChartLayout.linTransmitOnOffInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conInterferenceInfo.setVisibility(View.GONE);

                    sumWeight += (SWEPT_INFO_WEIGHT + ACLR_WEIGHT);

                } else if (mode == MeasureMode.MEASURE_MODE.SA && type == MeasureType.MEASURE_TYPE.SEM) {

                    showSaInfo(true);

                    binding.lineChartLayout.tvChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conChannelPower.setVisibility(View.GONE);
                    binding.lineChartLayout.conOccupiedBW.setVisibility(View.GONE);
                    binding.lineChartLayout.conChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linAclrInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linSemInfo.setVisibility(View.VISIBLE);
                    binding.lineChartLayout.linTransmitOnOffInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conSpuriousInfoParent.setVisibility(View.GONE);
                    binding.lineChartLayout.conInterferenceInfo.setVisibility(View.GONE);

                    sumWeight += (SEM_WEIGHT);

                } else if (mode == MeasureMode.MEASURE_MODE.SA && type == MeasureType.MEASURE_TYPE.INTERFERENCE_HUNTING) {

                    showSaInfo(true);

                    binding.lineChartLayout.tvChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conChannelPower.setVisibility(View.GONE);
                    binding.lineChartLayout.conOccupiedBW.setVisibility(View.GONE);
                    binding.lineChartLayout.conChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linAclrInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linSemInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linTransmitOnOffInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conSpuriousInfoParent.setVisibility(View.GONE);
                    binding.lineChartLayout.conInterferenceInfo.setVisibility(View.VISIBLE);

                    sumWeight += (INTERFERENCE_HUNTING_WEIGHT);

                } else if (mode == MeasureMode.MEASURE_MODE.SA && type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {

                    showSaInfo(true);

                    binding.lineChartLayout.tvChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conChannelPower.setVisibility(View.GONE);
                    binding.lineChartLayout.conOccupiedBW.setVisibility(View.GONE);
                    binding.lineChartLayout.conChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linAclrInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linSemInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linTransmitOnOffInfo.setVisibility(View.VISIBLE);
                    binding.lineChartLayout.conSpuriousInfoParent.setVisibility(View.GONE);
                    binding.lineChartLayout.conInterferenceInfo.setVisibility(View.GONE);

                    sumWeight += (TRANSMIT_ON_OFF_POWER_WEIGHT);

                } else if (mode == MeasureMode.MEASURE_MODE.VSWR) {

                    showSaInfo(false);

                    binding.lineChartLayout.tvChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conChannelPower.setVisibility(View.GONE);
                    binding.lineChartLayout.conOccupiedBW.setVisibility(View.GONE);
                    binding.lineChartLayout.conChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linAclrInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linSemInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linTransmitOnOffInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conSpuriousInfoParent.setVisibility(View.GONE);
                    binding.lineChartLayout.conInterferenceInfo.setVisibility(View.GONE);

                } else if (mode == MeasureMode.MEASURE_MODE.DTF) {

                    showSaInfo(false);

                    binding.lineChartLayout.tvChartInfo.setVisibility(View.VISIBLE);
                    binding.lineChartLayout.conChannelPower.setVisibility(View.GONE);
                    binding.lineChartLayout.conOccupiedBW.setVisibility(View.GONE);
                    binding.lineChartLayout.conChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linAclrInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linSemInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linTransmitOnOffInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conSpuriousInfoParent.setVisibility(View.GONE);
                    binding.lineChartLayout.conInterferenceInfo.setVisibility(View.GONE);

                    sumWeight += DTF_WEIGHT;

                } else if (mode == MeasureMode.MEASURE_MODE.CL) {

                    showSaInfo(false);

                    binding.lineChartLayout.tvChartInfo.setVisibility(View.VISIBLE);
                    binding.lineChartLayout.conChannelPower.setVisibility(View.GONE);
                    binding.lineChartLayout.conOccupiedBW.setVisibility(View.GONE);
                    binding.lineChartLayout.conChartInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linAclrInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linSemInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.linTransmitOnOffInfo.setVisibility(View.GONE);
                    binding.lineChartLayout.conSpuriousInfoParent.setVisibility(View.GONE);
                    binding.lineChartLayout.conInterferenceInfo.setVisibility(View.GONE);

                    sumWeight += CL_WEIGHT;

                }

                float chartWeight = 1f - sumWeight;

                if (chartWeight != ViewHandler.getInstance().getChartWeight())
                    ViewHandler.getInstance().setChartWeight(chartWeight);

            });

        }).start();
    }


    GpsInfoDialog mGpsInfoDialog = null;
    
    public void updateGpsInfo(byte[] buf) {
        if (mGpsInfoDialog == null)
            return;

        mGpsInfoDialog.updateGpsInfo(buf);
    }
}
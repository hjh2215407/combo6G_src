package com.dabinsystems.pact_app.Function;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.DETECTOR;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.TRACE_MODE;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.TRACE_TYPE;
import com.dabinsystems.pact_app.Data.SA.SaSweepTimeData;
import com.dabinsystems.pact_app.Data.VSWR.SweepData;
import com.dabinsystems.pact_app.Data.VSWR.WindowingData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Data.SA.SaAmplitudeData;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.List.Adapter.FileListAdapter;
import com.dabinsystems.pact_app.List.Item.FileListInfoItem;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.FileRecallDialogBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Recall {

    private FileRecallDialogBinding mRecallBinding;
    private ActivityMainBinding binding;
    private FileListAdapter mFileListAdapter;
    private ArrayList<FileListInfoItem> mFileListInfoList;
    private ArrayList<Integer> mDataList;
    private InitActivity mActivity;

    private final String mDirectoryName = "PACT/Save";
    private final File mPath = Environment.getExternalStorageDirectory();

    private final String All = "All";
    private final String DAT = "DAT";
    private final String STP = "STP";

    private final String mCurrentSettingFileName = "CurrentSetting";

    private static boolean isRecall = false;
    private static boolean isPreview = false;
    private static boolean isRecallButtonOff = true;

    private String mFileType = All;

    private File mRootFolder;

    public Recall(InitActivity activity, ActivityMainBinding binding) {

        mActivity = activity;
        this.binding = binding;
        mFileListInfoList = new ArrayList<>();
        mDataList = new ArrayList<>();

    }

    public FileListAdapter getFileAdapter() {

//        if(mFileListAdapter != null) {
//
//            mFileListAdapter.notifyDataSetChanged();
//            return mFileListAdapter;
//
//        }

        mFileListInfoList.clear();

        mRootFolder = new File(mPath, mDirectoryName);
        if (!mRootFolder.exists()) {
            mRootFolder.mkdirs();
        }

        if (mRootFolder.listFiles() != null) {

            if (mFileType.equals(All)) {

                readFileList(All);

            } else if (mFileType.equals(DAT)) {

                readFileList(DAT);

            } else if (mFileType.equals(STP)) {

                readFileList(STP);

            }

            mFileListAdapter = new FileListAdapter(mFileListInfoList,
                    ViewHandler.getInstance().getFileRecallDialog().getBinding(), mActivity);
            mFileListAdapter.notifyDataSetChanged();

        }

        return mFileListAdapter;

    }

    public void readFileList(final String fileExtension) {

        for (File file : mRootFolder.listFiles()) {

            String name = file.getName();
            String extension = "";
            if (name.contains(".") && name.lastIndexOf(".") != 0 && !fileExtension.equals(All)) {
                extension = name.substring(name.lastIndexOf(".") + 1).toUpperCase();
                if (!extension.equals(fileExtension)) continue;
            }

            InitActivity.logMsg("fileName", file.getName());
            mFileListInfoList.add(new FileListInfoItem(file));

        }

    }

    public void deleteCurrentSettingFile() {

        for (File file : mRootFolder.listFiles()) {

            String name = file.getName();

            if (name.equals(mCurrentSettingFileName + ".stp")) {

                new Thread(() -> {


                    while (true) {

                        if (!isPreview) {
                            if (file.delete()) {
                                InitActivity.logMsg("Recall", "deleted current setting file.");
                                break;
                            } else {
                                InitActivity.logMsg("Recall", "didn't delete current setting file.");
                                break;
                            }
                        }

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }).start();
                break;
            }

        }

    }

    public void loadCurrentSettingFile() {

        for (File file : mRootFolder.listFiles()) {

            String name = file.getName();

            if (name.equals(mCurrentSettingFileName + ".stp")) {

                FileListInfoItem item = new FileListInfoItem(file);
                mFileListAdapter.setSelectedFile(item);
                loadFile();
                new Thread(() -> {

                    while (true) {

                        if (!isPreview) {
                            if (file.delete()) {
                                InitActivity.logMsg("Recall", "deleted current setting file.");
                                break;
                            } else {
                                InitActivity.logMsg("Recall", "didn't delete current setting file.");
                                break;
                            }
                        }

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }).start();
                break;
            }

        }

    }

    public void loadFile() {

        FileListInfoItem fileInfo = mFileListAdapter.getSelectedFile();

        if (fileInfo == null) return;

        mActivity.runOnUiThread(() -> {

            isRecall = true;
            File selectedFile = fileInfo.getFile();

            try {

                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                String readLine = "";

                if (fileInfo.getFileType().equals(DAT)) {

                    isPreview = true;
                    FunctionHandler.getInstance().getSaveFunc().createSaveFile(mCurrentSettingFileName, ".stp");


                    //Mode

                    readLine = reader.readLine();

                    if (readLine.equals(MeasureMode.MEASURE_MODE.VSWR.getHexString())) {

                        FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.VSWR);

                    } else if (readLine.equals(MeasureMode.MEASURE_MODE.DTF.getHexString())) {

                        FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.DTF);

                    } else if (readLine.equals(MeasureMode.MEASURE_MODE.CL.getHexString())) {

                        FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.CL);


                    } else if (readLine.equals(MeasureMode.MEASURE_MODE.SA.getHexString())) {

                        FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.SA);

                    }

                    //Type

                    readLine = reader.readLine();

                    MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

                    if (mode == MeasureMode.MEASURE_MODE.VSWR && readLine.equals(MeasureType.MEASURE_TYPE.VSWR.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.VSWR);

                    } else if (mode == MeasureMode.MEASURE_MODE.VSWR && readLine.equals(MeasureType.MEASURE_TYPE.RL.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.RL);

                    } else if (mode == MeasureMode.MEASURE_MODE.DTF && readLine.equals(MeasureType.MEASURE_TYPE.DTF_VSWR.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.DTF_VSWR);

                    } else if (mode == MeasureMode.MEASURE_MODE.DTF && readLine.equals(MeasureType.MEASURE_TYPE.DTF_RL.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.DTF_RL);

                    } else if (mode == MeasureMode.MEASURE_MODE.CL && readLine.equals(MeasureType.MEASURE_TYPE.CABLE_LOSS.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.CABLE_LOSS);

                    } else if (mode == MeasureMode.MEASURE_MODE.SA && readLine.equals(MeasureType.MEASURE_TYPE.SWEPT_SA.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.SWEPT_SA);

                    } else if ((mode == MeasureMode.MEASURE_MODE.SA || mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) && readLine.equals(MeasureType.MEASURE_TYPE.CHANNEL_POWER.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.CHANNEL_POWER);

                    } else if ((mode == MeasureMode.MEASURE_MODE.SA || mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) && readLine.equals(MeasureType.MEASURE_TYPE.OCCUPIED_BW.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.OCCUPIED_BW);

                    }

                    if (mode.getValue() == MeasureMode.MEASURE_MODE.SA.getValue()) {

                        //Frequency
                        readLine = reader.readLine();
                        float centerFrequency = (float) (Double.parseDouble(readLine) / 100);
                        readLine = reader.readLine();
                        float spanFrequency = (float) (Double.parseDouble(readLine) / 100);

                        SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(centerFrequency);
                        SaDataHandler.getInstance().getConfigData().getFrequencyData().setSpan(spanFrequency);

                        //BW
                        int rbw = Integer.parseInt(reader.readLine());
                        int vbw = Integer.parseInt(reader.readLine());

                        SaDataHandler.getInstance().getConfigData().getBwData().setRBW(rbw);
                        SaDataHandler.getInstance().getConfigData().getBwData().setVBW(vbw);

                        String ampMode = reader.readLine();

                        if (SaAmplitudeData.AMP_MODE.MANUAL.getHexString().equals(ampMode)) {

                            SaDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMode(SaAmplitudeData.AMP_MODE.MANUAL);

                        } else if (SaAmplitudeData.AMP_MODE.AUTO.getHexString().equals(ampMode)) {


                            SaDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMode(SaAmplitudeData.AMP_MODE.AUTO);

                        }

                        int att = Integer.parseInt(reader.readLine());
                        SaDataHandler.getInstance().getConfigData().getAmplitudeData().setAttenuator(att);

                        for (int i = 0; i < FunctionHandler.getInstance().getMainLineChart().MAX_TRACE_SIZE; i++) {

                            String traceMode = reader.readLine();

                            if (TRACE_MODE.CLEAR_WRITE.getHexString().equals(traceMode)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setMode(TRACE_MODE.CLEAR_WRITE, i);

                            } else if (TRACE_MODE.AVERAGE.getHexString().equals(traceMode)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setMode(TRACE_MODE.AVERAGE, i);

                            } else if (TRACE_MODE.MAX_HOLD.getHexString().equals(traceMode)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setMode(TRACE_MODE.MAX_HOLD, i);

                            } else if (TRACE_MODE.MIN_HOLD.getHexString().equals(traceMode)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setMode(TRACE_MODE.MIN_HOLD, i);

                            }

                            String traceType = reader.readLine();

                            if (TRACE_TYPE.UPDATE.getHexString().equals(traceType)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setType(TRACE_TYPE.UPDATE, i);

                            } else if (TRACE_TYPE.VIEW.getHexString().equals(traceType)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setType(TRACE_TYPE.VIEW, i);

                            } else if (TRACE_TYPE.BLANK.getHexString().equals(traceType)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setType(TRACE_TYPE.BLANK, i);

                            }

                            String detector = reader.readLine();

                            if (DETECTOR.NORMAL.getHexString().equals(detector)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.NORMAL, i);

                            } else if (DETECTOR.PEAK.getHexString().equals(detector)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.PEAK, i);

                            } else if (DETECTOR.NEGATIVE.getHexString().equals(detector)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.NEGATIVE, i);

                            } else if (DETECTOR.SAMPLE.getHexString().equals(detector)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.SAMPLE, i);

                            } else if (DETECTOR.RMS.getHexString().equals(detector)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.RMS, i);

                            }

                        }


                        int sweepMode = Integer.decode(reader.readLine());

                        if (sweepMode == 1) {

                            SaDataHandler.getInstance().getConfigData().getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);

                        } else if (sweepMode == 0) {

                            SaDataHandler.getInstance().getConfigData().getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.MANUAL);

                        }

                        long sweepTime = Long.parseLong(reader.readLine());
                        SaDataHandler.getInstance().getConfigData().getSweepTimeData().setSweepTime(sweepTime);


                        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                        int avgMode = Integer.decode(reader.readLine());
                        int avgNumber = Integer.parseInt(reader.readLine());
                        float obwPower = (float) (Double.parseDouble(reader.readLine()) / 100);
                        float xdb = (float) (Double.parseDouble(reader.readLine()) / 100);
                        long interBw = Long.parseLong(reader.readLine());

                        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

                            SaDataHandler.getInstance().getSweptSaConfigData().getSweptSaMeasSetupData().setAvgMode(avgMode);
                            SaDataHandler.getInstance().getSweptSaConfigData().getSweptSaMeasSetupData().setAvgHold(avgNumber);

                        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

                            if (avgMode == 1)
                                SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().setAvgMode(true);
                            else
                                SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().setAvgMode(false);

                            SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().setAvgHold(avgNumber);
                            SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().setIngegBW(interBw);

                        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

                            if (avgMode == 1)
                                SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().setAvgMode(true);
                            else
                                SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().setAvgMode(false);

                            SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().setAvgHold(avgNumber);
                            SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().setOBWPower(obwPower);
                            SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().setXDB(xdb);

                        }

                        float refLevel = Float.parseFloat(reader.readLine()) / 100f;
                        int atten = Integer.parseInt(reader.readLine());
                        float scaleDiv = Float.parseFloat(reader.readLine()) / 100f;
                        float offset = Float.parseFloat(reader.readLine()) / 100f;

                        FunctionHandler.getInstance().getMainLineChart().setRefLev(refLevel);
                        SaDataHandler.getInstance().getConfigData().getAmplitudeData().setAttenuator(atten);
                        FunctionHandler.getInstance().getMainLineChart().setScaleDiv(scaleDiv);
                        FunctionHandler.getInstance().getMainLineChart().setOffset(offset);

                        for (int i = 0; i < 4; i++) {

                            FunctionHandler.getInstance().getMainLineChart().showChartData(i, false);
                            FunctionHandler.getInstance().getMainLineChart().showChartData(i + 4, true);

                        }

                        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

                            for (int i = 0; i < FunctionHandler.getInstance().getMainLineChart().MAX_TRACE_SIZE; i++) {

                                FunctionHandler.getInstance().getMainLineChart().getDataList(4 + i).clear();

                                for (int j = 0; j < SaDataHandler.getInstance().getConfigData().getTraceData().getDetector(i).getDataPoints(); j++) {

                                    try {
                                        readLine = reader.readLine();
                                        if (readLine == null) break;

                                        int value = Integer.parseInt(readLine);
                                        FunctionHandler.getInstance().getMainLineChart().getDataList(4 + i).add(value);

                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                        InitActivity.logMsg("readLine", readLine);
                                    }


                                }

                                FunctionHandler.getInstance().getMainLineChart().invalidate();

                                InitActivity.logMsg("SA Recall", FunctionHandler.getInstance().getMainLineChart().getDataList(4 + i).size() + "");
                            }


                        } else {

                            FunctionHandler.getInstance().getMainLineChart().getDataList(0).clear();
                            for (int j = 0; j < SaDataHandler.getInstance().getConfigData().getTraceData().getDetector(0).getDataPoints(); j++) {

                                try {

                                    readLine = reader.readLine();
                                    if (readLine == null) break;
//                                    InitActivity.logMsg("readLine", readLine + " " + j);

                                    int value = Integer.parseInt(readLine);
                                    FunctionHandler.getInstance().getMainLineChart().getDataList(4).add(value);

                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                    InitActivity.logMsg("readLine", readLine);
                                }

                            }

                            FunctionHandler.getInstance().getMainLineChart().invalidate();

                        }


                    } else {

                        //DataPoint
                        readLine = reader.readLine();
                        if (readLine.equals(SweepData.DATA_POINT.P129.getHexString())) {

                            VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P129);

                        } else if (readLine.equals(SweepData.DATA_POINT.P257.getHexString())) {

                            VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P257);

                        } else if (readLine.equals(SweepData.DATA_POINT.P513.getHexString())) {


                            VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P513);

                        } else if (readLine.equals(SweepData.DATA_POINT.P1025.getHexString())) {

                            VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P1025);

                        } else if (readLine.equals(SweepData.DATA_POINT.P2049.getHexString())) {

                            VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P2049);

                        } else if (readLine.equals(SweepData.DATA_POINT.P2002.getHexString())) {

                            VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P2002);

                        }

                        //Frequency
                        readLine = reader.readLine();
                        float startFrequency = (float) (Double.parseDouble(readLine) / 100);
                        readLine = reader.readLine();
                        float stopFrequency = (float) (Double.parseDouble(readLine) / 100);

                        VswrDataHandler.getInstance().getConfigData().getFrequencyData().setStartFreq(startFrequency);
                        VswrDataHandler.getInstance().getConfigData().getFrequencyData().setStopFreq(stopFrequency);


                        //Distance
                        readLine = reader.readLine();
                        int distance = (int) (Double.parseDouble(readLine) / 100);
                        VswrDataHandler.getInstance().getDtfConfigData().setDistance(distance);

                        //Cable Loss
                        readLine = reader.readLine();
                        float loss = (float) (Double.parseDouble(readLine) / 100);
                        readLine = reader.readLine();
                        float velocity = (float) (Double.parseDouble(readLine) / 100);

                        FunctionHandler.getInstance().getCableInfoFunc().setLoss(loss);
                        FunctionHandler.getInstance().getCableInfoFunc().setVelocity(velocity);

                        //Windowing
                        readLine = reader.readLine();

                        if (readLine.equals(WindowingData.WINDOWING.RECTANGULAR.getHexString())) {

                            VswrDataHandler.getInstance().getConfigData().getWindowingData().setMode(WindowingData.WINDOWING.RECTANGULAR);

                        } else if (readLine.equals(WindowingData.WINDOWING.NOMINAL_SIDE_LOBE.getHexString())) {

                            VswrDataHandler.getInstance().getConfigData().getWindowingData().setMode(WindowingData.WINDOWING.NOMINAL_SIDE_LOBE);

                        } else if (readLine.equals(WindowingData.WINDOWING.LOW_SIDE_LOBE.getHexString())) {

                            VswrDataHandler.getInstance().getConfigData().getWindowingData().setMode(WindowingData.WINDOWING.LOW_SIDE_LOBE);


                        } else if (readLine.equals(WindowingData.WINDOWING.MINIMUM_SIDE_LOBE.getHexString())) {

                            VswrDataHandler.getInstance().getConfigData().getWindowingData().setMode(WindowingData.WINDOWING.MINIMUM_SIDE_LOBE);

                        }


                        readLine = reader.readLine();
                        float maxRangeY = (float) (Double.parseDouble(readLine) / 100);
                        readLine = reader.readLine();
                        float minRangeY = (float) (Double.parseDouble(readLine) / 100);

                        FunctionHandler.getInstance().getMainLineChart().setMaxYRange(maxRangeY);
                        FunctionHandler.getInstance().getMainLineChart().setMinYRange(minRangeY);

                        float limitVal = (float) (Double.parseDouble(reader.readLine()) / 100);
                        String isEnabledLimitLine = reader.readLine();
                        String isEnabledAlram = reader.readLine();

                        FunctionHandler.getInstance().getMainLineChart().setLimitValue(limitVal);

                        if (isEnabledLimitLine.equals("true"))
                            FunctionHandler.getInstance().getMainLineChart().setEnabledLimitLine(true);
                        else FunctionHandler.getInstance().getMainLineChart().setEnabledLimitLine(false);


                        if (isEnabledAlram.equals("true"))
                            FunctionHandler.getInstance().getMainLineChart().setEnabledLimitAlarm(true);
                        else FunctionHandler.getInstance().getMainLineChart().setEnabledLimitAlarm(false);

                        FunctionHandler.getInstance().getMainLineChart().setEnabledLimitMsg(false);

                        String isRun = reader.readLine();
                        String isContunuous = reader.readLine();

                        if (isRun.equals("true")) {

                            VswrDataHandler.getInstance().getConfigData().getSweepData().setRun(true);

                        } else if (isRun.equals("false")) {


                            VswrDataHandler.getInstance().getConfigData().getSweepData().setRun(false);

                        }

                        if (isContunuous.equals("true")) {

                            VswrDataHandler.getInstance().getConfigData().getSweepData().setContinuous(true);

                        } else if (isRun.equals("false")) {

                            VswrDataHandler.getInstance().getConfigData().getSweepData().setContinuous(false);

                        }

                        FunctionHandler.getInstance().getMainLineChart().showAllChartData(false);

                        FunctionHandler.getInstance().getMainLineChart().getDataList(4).clear();
                        for (int i = 0; i < VswrDataHandler.getInstance().getConfigData().getSweepData().getDataPoint().getDataPoint(); i++) {

                            try {
                                readLine = reader.readLine();
                                if (readLine == null) break;

                                FunctionHandler.getInstance().getMainLineChart().getDataList(4).add(Integer.parseInt(readLine));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                InitActivity.logMsg("readLine", readLine);
                            }

                        }

                        //MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                        FunctionHandler.getInstance().getMainLineChart().addEntry(
                                mDataList,
                                FunctionHandler.getInstance().getMainLineChart().RECALL_DATASET_IDX[0]
                                , mode, type, SaDataHandler.getInstance().getConfigData()
                        );
                    }

                    // chart 데이터 모두 추가 후 갱신
                    FunctionHandler.getInstance().getMainLineChart().addEntryUpdate();


                    ViewHandler.getInstance().getContent().update();
                    ViewHandler.getInstance().getContent().showMarkerView(false);
//                    ViewHandler.getInstance().getContent().showSaInfo(false);
                    ViewHandler.getInstance().getRecallView().addMenu();

                    for(int i = 0; i<FunctionHandler.getInstance().getMainLineChart().MAX_TRACE_SIZE; i++) {

                        FunctionHandler.getInstance().getMainLineChart().showChartData(i, false);

                    }

                    mActivity.disableButton();


                } else if (fileInfo.getFileType().equals(STP)) {

                    //Mode
                    readLine = reader.readLine();

                    if (readLine.equals(MeasureMode.MEASURE_MODE.VSWR.getHexString())) {

                        FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.VSWR);

                    } else if (readLine.equals(MeasureMode.MEASURE_MODE.DTF.getHexString())) {

                        FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.DTF);

                    } else if (readLine.equals(MeasureMode.MEASURE_MODE.CL.getHexString())) {

                        FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.CL);


                    } else if (readLine.equals(MeasureMode.MEASURE_MODE.SA.getHexString())) {

                        FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.SA);

                    }

                    //Type
                    readLine = reader.readLine();

                    MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

                    if (mode == MeasureMode.MEASURE_MODE.VSWR && readLine.equals(MeasureType.MEASURE_TYPE.VSWR.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.VSWR);

                    } else if (mode == MeasureMode.MEASURE_MODE.VSWR && readLine.equals(MeasureType.MEASURE_TYPE.RL.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.RL);

                    } else if (mode == MeasureMode.MEASURE_MODE.DTF && readLine.equals(MeasureType.MEASURE_TYPE.DTF_VSWR.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.DTF_VSWR);

                    } else if (mode == MeasureMode.MEASURE_MODE.DTF && readLine.equals(MeasureType.MEASURE_TYPE.DTF_RL.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.DTF_RL);

                    } else if (mode == MeasureMode.MEASURE_MODE.CL && readLine.equals(MeasureType.MEASURE_TYPE.CABLE_LOSS.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.CABLE_LOSS);

                    } else if (mode == MeasureMode.MEASURE_MODE.SA && readLine.equals(MeasureType.MEASURE_TYPE.SWEPT_SA.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.SWEPT_SA);

                    } else if ((mode == MeasureMode.MEASURE_MODE.SA || mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) && readLine.equals(MeasureType.MEASURE_TYPE.CHANNEL_POWER.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.CHANNEL_POWER);

                    } else if ((mode == MeasureMode.MEASURE_MODE.SA || mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) && readLine.equals(MeasureType.MEASURE_TYPE.OCCUPIED_BW.getHexString())) {

                        FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.OCCUPIED_BW);

                    }

                    //DataPoint
                    readLine = reader.readLine();
                    if (readLine.equals(SweepData.DATA_POINT.P129.getHexString())) {

                        VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P129);

                    } else if (readLine.equals(SweepData.DATA_POINT.P257.getHexString())) {

                        VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P257);

                    } else if (readLine.equals(SweepData.DATA_POINT.P513.getHexString())) {


                        VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P513);

                    } else if (readLine.equals(SweepData.DATA_POINT.P1025.getHexString())) {

                        VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P1025);

                    } else if (readLine.equals(SweepData.DATA_POINT.P2049.getHexString())) {

                        VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P2049);

                    } else if (readLine.equals(SweepData.DATA_POINT.P2002.getHexString())) {

                        VswrDataHandler.getInstance().getConfigData().getSweepData().setDataPoint(SweepData.DATA_POINT.P2002);

                    }

                    if (mode.getValue() == MeasureMode.MEASURE_MODE.SA.getValue()) {

                        //Frequency
                        readLine = reader.readLine();
                        float centerFrequency = (float) (Double.parseDouble(readLine) / 100);
                        readLine = reader.readLine();
                        float spanFrequency = (float) (Double.parseDouble(readLine) / 100);

                        SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(centerFrequency);
                        SaDataHandler.getInstance().getConfigData().getFrequencyData().setSpan(spanFrequency);

                        //BW
                        int rbw = Integer.parseInt(reader.readLine());
                        int vbw = Integer.parseInt(reader.readLine());

                        SaDataHandler.getInstance().getConfigData().getBwData().setRBW(rbw);
                        SaDataHandler.getInstance().getConfigData().getBwData().setVBW(vbw);

                        String ampMode = reader.readLine();

                        if (SaAmplitudeData.AMP_MODE.MANUAL.getHexString().equals(ampMode)) {

                            SaDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMode(SaAmplitudeData.AMP_MODE.MANUAL);

                        } else if (SaAmplitudeData.AMP_MODE.AUTO.getHexString().equals(ampMode)) {


                            SaDataHandler.getInstance().getConfigData().getAmplitudeData().setAmpMode(SaAmplitudeData.AMP_MODE.AUTO);

                        }

                        int att = Integer.parseInt(reader.readLine());
                        SaDataHandler.getInstance().getConfigData().getAmplitudeData().setAttenuator(att);

                        for (int i = 0; i < FunctionHandler.getInstance().getMainLineChart().MAX_TRACE_SIZE; i++) {

                            String traceMode = reader.readLine();

                            if (TRACE_MODE.CLEAR_WRITE.getHexString().equals(traceMode)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setMode(TRACE_MODE.CLEAR_WRITE, i);

                            } else if (TRACE_MODE.AVERAGE.getHexString().equals(traceMode)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setMode(TRACE_MODE.AVERAGE, i);

                            } else if (TRACE_MODE.MAX_HOLD.getHexString().equals(traceMode)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setMode(TRACE_MODE.MAX_HOLD, i);

                            } else if (TRACE_MODE.MIN_HOLD.getHexString().equals(traceMode)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setMode(TRACE_MODE.MIN_HOLD, i);

                            }

                            String traceType = reader.readLine();

                            if (TRACE_TYPE.UPDATE.getHexString().equals(traceType)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setType(TRACE_TYPE.UPDATE, i);

                            } else if (TRACE_TYPE.VIEW.getHexString().equals(traceType)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setType(TRACE_TYPE.VIEW, i);

                            } else if (TRACE_TYPE.BLANK.getHexString().equals(traceType)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setType(TRACE_TYPE.BLANK, i);

                            }

                            String detector = reader.readLine();

                            if (DETECTOR.NORMAL.getHexString().equals(detector)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.NORMAL, i);

                            } else if (DETECTOR.PEAK.getHexString().equals(detector)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.PEAK, i);

                            } else if (DETECTOR.NEGATIVE.getHexString().equals(detector)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.NEGATIVE, i);

                            } else if (DETECTOR.SAMPLE.getHexString().equals(detector)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.SAMPLE, i);

                            } else if (DETECTOR.RMS.getHexString().equals(detector)) {

                                SaDataHandler.getInstance().getConfigData().getTraceData().setDetector(DETECTOR.RMS, i);

                            }

                        }


                        int sweepMode = Integer.decode(reader.readLine());

                        if (sweepMode == 1) {

                            SaDataHandler.getInstance().getConfigData().getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.AUTO);

                        } else if (sweepMode == 0) {

                            SaDataHandler.getInstance().getConfigData().getSweepTimeData().setMode(SaSweepTimeData.SWEEP_TIME_MODE.MANUAL);

                        }

                        long sweepTime = Long.parseLong(reader.readLine());
                        SaDataHandler.getInstance().getConfigData().getSweepTimeData().setSweepTime(sweepTime);


                        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                        int avgMode = Integer.decode(reader.readLine());
                        int avgNumber = Integer.parseInt(reader.readLine());
                        float obwPower = (float) (Double.parseDouble(reader.readLine()) / 100);
                        float xdb = (float) (Double.parseDouble(reader.readLine()) / 100);
                        long interBw = Long.parseLong(reader.readLine());

                        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

                            SaDataHandler.getInstance().getSweptSaConfigData().getSweptSaMeasSetupData().setAvgMode(avgMode);
                            SaDataHandler.getInstance().getSweptSaConfigData().getSweptSaMeasSetupData().setAvgHold(avgNumber);

                        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

                            if (avgMode == 1)
                                SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().setAvgMode(true);
                            else
                                SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().setAvgMode(false);

                            SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().setAvgHold(avgNumber);
                            SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().setIngegBW(interBw);

                        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

                            if (avgMode == 1)
                                SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().setAvgMode(true);
                            else
                                SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().setAvgMode(false);

                            SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().setAvgHold(avgNumber);
                            SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().setOBWPower(obwPower);
                            SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().setXDB(xdb);

                        }

                    } else {

                        //Frequency
                        readLine = reader.readLine();
                        float startFrequency = (float) (Double.parseDouble(readLine) / 100);
                        readLine = reader.readLine();
                        float stopFrequency = (float) (Double.parseDouble(readLine) / 100);




                        if (mode == MeasureMode.MEASURE_MODE.VSWR
                        || mode == MeasureMode.MEASURE_MODE.DTF
                        || mode == MeasureMode.MEASURE_MODE.CL) {

                            VswrDataHandler.getInstance().getConfigData().getFrequencyData().setStartFreq(startFrequency);
                            VswrDataHandler.getInstance().getConfigData().getFrequencyData().setStopFreq(stopFrequency);


                        } else if (mode == MeasureMode.MEASURE_MODE.SA || mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {

                            SaDataHandler.getInstance().getConfigData().getFrequencyData().setStartFreq(startFrequency);
                            SaDataHandler.getInstance().getConfigData().getFrequencyData().setStopFreq(stopFrequency);
                            SaDataHandler.getInstance().getConfigData().getFrequencyData().checkFreqRange();

                        }

                        //Distance
                        readLine = reader.readLine();
                        int distance = (int) (Double.parseDouble(readLine) / 100);
                        VswrDataHandler.getInstance().getDtfConfigData().setDistance(distance);

                        //Cable Loss
                        readLine = reader.readLine();
                        float loss = (float) (Double.parseDouble(readLine) / 100);
                        readLine = reader.readLine();
                        float velocity = (float) (Double.parseDouble(readLine) / 100);

                        FunctionHandler.getInstance().getCableInfoFunc().setLoss(loss);
                        FunctionHandler.getInstance().getCableInfoFunc().setVelocity(velocity);

                        //Windowing
                        readLine = reader.readLine();

                        if (readLine.equals(WindowingData.WINDOWING.RECTANGULAR)) {

                            VswrDataHandler.getInstance().getConfigData().getWindowingData().setMode(WindowingData.WINDOWING.RECTANGULAR);

                        } else if (readLine.equals(WindowingData.WINDOWING.NOMINAL_SIDE_LOBE)) {

                            VswrDataHandler.getInstance().getConfigData().getWindowingData().setMode(WindowingData.WINDOWING.NOMINAL_SIDE_LOBE);

                        } else if (readLine.equals(WindowingData.WINDOWING.LOW_SIDE_LOBE)) {

                            VswrDataHandler.getInstance().getConfigData().getWindowingData().setMode(WindowingData.WINDOWING.LOW_SIDE_LOBE);


                        } else if (readLine.equals(WindowingData.WINDOWING.MINIMUM_SIDE_LOBE)) {

                            VswrDataHandler.getInstance().getConfigData().getWindowingData().setMode(WindowingData.WINDOWING.MINIMUM_SIDE_LOBE);

                        }

                        readLine = reader.readLine();
                        float maxRangeY = (float) (Double.parseDouble(readLine) / 100);
                        readLine = reader.readLine();
                        float minRangeY = (float) (Double.parseDouble(readLine) / 100);

                        FunctionHandler.getInstance().getMainLineChart().setMaxYRange(maxRangeY);
                        FunctionHandler.getInstance().getMainLineChart().setMinYRange(minRangeY);


                        float limitVal = (float) (Double.parseDouble(reader.readLine()) / 100);
                        String isEnabledLimitLine = reader.readLine();
                        String isEnabledAlram = reader.readLine();

                        FunctionHandler.getInstance().getMainLineChart().setLimitValue(limitVal);

                        if (isEnabledLimitLine.equals("true"))
                            FunctionHandler.getInstance().getMainLineChart().setEnabledLimitLine(true);
                        else FunctionHandler.getInstance().getMainLineChart().setEnabledLimitLine(false);


                        if (isEnabledAlram.equals("true"))
                            FunctionHandler.getInstance().getMainLineChart().setEnabledLimitAlarm(true);
                        else FunctionHandler.getInstance().getMainLineChart().setEnabledLimitAlarm(false);

                        FunctionHandler.getInstance().getMainLineChart().setEnabledLimitMsg(false);

                        String isRun = reader.readLine();
                        String isContunuous = reader.readLine();

                        if (isRun.equals("true")) {

                            VswrDataHandler.getInstance().getConfigData().getSweepData().setRun(true);

                        } else if (isRun.equals("false")) {


                            VswrDataHandler.getInstance().getConfigData().getSweepData().setRun(false);

                        }

                        if (isContunuous.equals("true")) {

                            VswrDataHandler.getInstance().getConfigData().getSweepData().setContinuous(true);

                        } else if (isRun.equals("false")) {

                            VswrDataHandler.getInstance().getConfigData().getSweepData().setContinuous(false);

                        }

                    }

                    mActivity.enableButton();
                    ViewHandler.getInstance().getContent().update();
                    ViewHandler.getInstance().getSystemView().addMenu();
                    ViewHandler.getInstance().getContent().showMarkerView(true);

                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        });

        isRecall = false;

        if (!fileInfo.getFileType().equals(DAT))
            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    DataHandler.getInstance().getConfigCmd()
            );

    }

    public boolean isPreview() {
        return isPreview;
    }

    public void clickOk() {

        isPreview = false;
        isRecallButtonOff = false;

        FunctionHandler.getInstance().getMainLineChart().showAllChartData(true);

        mActivity.enableButton();
        deleteCurrentSettingFile();

        ViewHandler.getInstance().getContent().showMarkerView(true);

        mActivity.runOnUiThread(() -> {

            binding.linBottomMenu.setVisibility(View.VISIBLE);
            binding.linTopSubBtn.setVisibility(View.VISIBLE);
            binding.tvRecallOff.setVisibility(View.VISIBLE);
            binding.tvRecallMessage.setVisibility(View.GONE);
            binding.linBottomMenu.setVisibility(View.VISIBLE);

        });
        FunctionHandler.getInstance().getDataConnector().sendCommand(
                DataHandler.getInstance().getConfigCmd()
        );

        FunctionHandler.getInstance().getDataConnector().startDataTimeoutHandler(
                FunctionHandler.getInstance().getDataConnector().getMqttTimeout()
        );

        ViewHandler.getInstance().getSystemView().addMenu();
        ViewHandler.getInstance().getContent().update();

    }

    public void clickCancel() {

        isPreview = false;
        isRecallButtonOff = true;

        loadCurrentSettingFile();

        ViewHandler.getInstance().getContent().showMarkerView(true);

        int[] arr = FunctionHandler.getInstance().getMainLineChart().RECALL_DATASET_IDX;

        for (int i = 0; i < arr.length; i++) {
            binding.lineChartLayout.mainLineChart.getData().getDataSetByIndex(arr[i]).clear();
        }

        binding.tvRecallMessage.setVisibility(View.GONE);
        binding.linBottomMenu.setVisibility(View.VISIBLE);
        binding.linTopSubBtn.setVisibility(View.VISIBLE);

        mActivity.enableButton();

        FunctionHandler.getInstance().getDataConnector().sendCommand(
                DataHandler.getInstance().getConfigCmd()
        );

        FunctionHandler.getInstance().getDataConnector().startDataTimeoutHandler(
                FunctionHandler.getInstance().getDataConnector().getMqttTimeout()
        );

        ViewHandler.getInstance().getSystemView().addMenu();
        ViewHandler.getInstance().getContent().update();

    }


}

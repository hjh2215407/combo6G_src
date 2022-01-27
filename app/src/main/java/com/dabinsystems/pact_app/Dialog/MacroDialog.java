package com.dabinsystems.pact_app.Dialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.util.Log;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Screenshot.Screenshot;
import com.dabinsystems.pact_app.Util.ExcelEditor;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.MacroLayoutBinding;

import java.util.ArrayList;

public class MacroDialog extends CustomBaseDialog {

    public enum MEASURE_DEVICE {

        DEVICE1,
        DEVICE2;

    }

    private MacroLayoutBinding mMacroBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;
    private MEASURE_DEVICE mMeasureDevice = MEASURE_DEVICE.DEVICE1;

    private static MacroDialog mMacroDialog;

    private static Boolean isAutoMeasure = false;
    private final int MAX_REPEAT_COUNT = 15;
    private int mRepeatCount = 0;
    private int mTotalCount = 0;
    private int NUMBER_OF_MEASUREMENT = 0;
    private float PERCENT_PER_TYPE = 0;
    private int mPercent = 0;
    private ExcelEditor mExcelEditor = null;
    private AutoMeasureProgress mAutoMeasureProgress;

    private ArrayList<ArrayList<Integer>> dataList;

    public static MacroDialog getInstance() {

        if (mMacroDialog == null)
            mMacroDialog = new MacroDialog((MainActivity) MainActivity.getActivity(), MainActivity.getBinding());
        return mMacroDialog;

    }

    public MacroDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;
//        mExcelEditor = new ExcelEditor("data.xlsx", Environment.getExternalStorageDirectory() + File.separator + "PACT/SaveData" + File.separator);
        mAutoMeasureProgress = new AutoMeasureProgress(mActivity, mMainBinding);

    }

    public MacroDialog(ExcelEditor editor, MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;
//        mExcelEditor = new ExcelEditor("data.xlsx", Environment.getExternalStorageDirectory() + File.separator + "PACT/SaveData" + File.separator);
        mExcelEditor = editor;
        mAutoMeasureProgress = new AutoMeasureProgress(mActivity, mMainBinding);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMacroBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.macro_layout, null, false);
        setContentView(mMacroBinding.getRoot());

        setValues();
        addEvents();

    }

    private void setValues() {
        dataList = new ArrayList<>();
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    public static Boolean isAutoMeasure() {
        return isAutoMeasure;
    }

    public void setEditor(ExcelEditor editor) {
        mExcelEditor = editor;
    }

    public ExcelEditor getEditor() {
        return mExcelEditor;
    }

    public void checkMacroStatus() {

        new Handler(Looper.getMainLooper()).post(() -> {

            if (!isAutoMeasure) return;

            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

            if (type == MeasureType.MEASURE_TYPE.SWEPT_SA && !mMacroBinding.chSweptSA.isChecked())
                return;
            if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER && !mMacroBinding.chChannelPower.isChecked())
                return;
            if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW && !mMacroBinding.chOccBw.isChecked())
                return;
            if (type == MeasureType.MEASURE_TYPE.ACLR && !mMacroBinding.chAclr.isChecked()) return;
            if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK && !mMacroBinding.chTransmitOnOff.isChecked())
                return;

            if (mRepeatCount < MAX_REPEAT_COUNT) {

                if (mRepeatCount == 11) {
                    capture();
                    writeData(type);
                }

                mRepeatCount++;

            } else if (mRepeatCount == MAX_REPEAT_COUNT) {
                mRepeatCount = 0;
                mTotalCount++;
                nextMeasure();
            }

        });

    }

    public void capture() {

        Screenshot screenshot = Screenshot.getInstance();
        Screenshot.getInstance().takeScreenShotOfRootView(mMainBinding.linParent.getRootView());
        FunctionHandler.getInstance().getScreenshotFunc().setFileName(FunctionHandler.getInstance().getMeasureType().getType().toString());

        if (screenshot.getBitmap() != null)
            screenshot.saveScreenshot(screenshot.getBitmap());

        int row = 1 + (19 * mTotalCount);
        int cell = 1;

        InitActivity.logMsg("checkMacroStatus", "row index : " + row + " cell index : " + cell);

        if (mMeasureDevice == MEASURE_DEVICE.DEVICE1)
            mExcelEditor.addImage(row, cell, screenshot.getBitmap());
        else mExcelEditor.addImage(row, cell + 10, screenshot.getBitmap());

    }

    public void nextMeasure() {

        MeasureType typeFunc = FunctionHandler.getInstance().getMeasureType();
        MeasureType.MEASURE_TYPE type = typeFunc.getType();

        switch (type) {

            case SWEPT_SA:
                if (mMacroBinding.chChannelPower.isChecked()) {
                    typeFunc.setMeasureType(MeasureType.MEASURE_TYPE.CHANNEL_POWER);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(778);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setSpan(10);

//                    DataHandler.getInstance().getStandardLoadData().load10MHzDataForLte();
                    ViewHandler.getInstance().getContent().update();
                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            SaDataHandler.getInstance().getConfigData().getSaParameter()
                    );

                    mPercent += PERCENT_PER_TYPE;
                    setProgress(mPercent, MeasureType.MEASURE_TYPE.CHANNEL_POWER.getFullName());
                } else {
                    nextMeasure(MeasureType.MEASURE_TYPE.OCCUPIED_BW);
                }
                break;

            case CHANNEL_POWER:
                if (mMacroBinding.chOccBw.isChecked()) {
                    typeFunc.setMeasureType(MeasureType.MEASURE_TYPE.OCCUPIED_BW);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(778);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setSpan(10);

                    DataHandler.getInstance().getStandardLoadData().load10MHzDataForLte();
                    ViewHandler.getInstance().getContent().update();
                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            SaDataHandler.getInstance().getConfigData().getSaParameter()
                    );

                    mPercent += PERCENT_PER_TYPE;
                    setProgress(mPercent, MeasureType.MEASURE_TYPE.OCCUPIED_BW.getFullName());
                } else {
                    nextMeasure(MeasureType.MEASURE_TYPE.ACLR);
                }
                break;

            case OCCUPIED_BW:
                if (mMacroBinding.chAclr.isChecked()) {
                    typeFunc.setMeasureType(MeasureType.MEASURE_TYPE.ACLR);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(778);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setSpan(10);

                    DataHandler.getInstance().getStandardLoadData().load10MHzDataForLte();
                    ViewHandler.getInstance().getContent().update();
                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            SaDataHandler.getInstance().getConfigData().getSaParameter()
                    );

                    mPercent += PERCENT_PER_TYPE;
                    setProgress(mPercent, MeasureType.MEASURE_TYPE.ACLR.getFullName());
                } else {
                    nextMeasure(MeasureType.MEASURE_TYPE.TRANSMIT_MASK);
                }
                break;

            case ACLR:
                if (mMacroBinding.chTransmitOnOff.isChecked()) {
                    typeFunc.setMeasureType(MeasureType.MEASURE_TYPE.TRANSMIT_MASK);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(778);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setSpan(10);

                    DataHandler.getInstance().getStandardLoadData().load10MHzDataForLte();
                    ViewHandler.getInstance().getContent().update();
                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            SaDataHandler.getInstance().getConfigData().getSaParameter()
                    );

                    mPercent += PERCENT_PER_TYPE;
                    setProgress(mPercent, MeasureType.MEASURE_TYPE.TRANSMIT_MASK.getFullName());
                } else {

                    close();
                    return;
                }
                break;

            case TRANSMIT_MASK:
                close();
                return;

        }

    }

    public void setDevice(MEASURE_DEVICE device) {
        mMeasureDevice = device;
    }

    public MEASURE_DEVICE getSelectedDevice() {
        return mMeasureDevice;
    }

    public void writeData(MeasureType.MEASURE_TYPE type) {

        switch (type) {

            case CHANNEL_POWER:

                Float channelPower = SaDataHandler.getInstance().getChannelPowerConfigData().getChannelPowerMeasSetupData().getChannelPower();
                if (channelPower == null) {
                    InitActivity.logMsg("writeData", "channel power is null...");
                    return;
                }
                double dbmToWatt = (Math.pow(10, (double) channelPower / 10)) / 1000;

                //decimal point 3
                double watt = Math.round(dbmToWatt * 1000.0) / 1000.0;

                if (watt - (int) watt == 0) watt = 0;
                else watt = Math.round(watt * 10.0) / 10.0;

                if (mMeasureDevice == MEASURE_DEVICE.DEVICE1) {
                    mExcelEditor.addExcelData(25, 8, watt + "W");

                } else {
                    mExcelEditor.addExcelData(25, 12, watt + "W");
                }
                break;

            case OCCUPIED_BW:
                if (mMeasureDevice == MEASURE_DEVICE.DEVICE1) {

                    mExcelEditor.addExcelData(24, 8, SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getOccupiedBW() + "㎒");

                } else {
                    mExcelEditor.addExcelData(24, 12, SaDataHandler.getInstance().getOccupiedBwConfigData().getOccupiedBwMeasSetupData().getOccupiedBW() + "㎒");

                }
                break;

            case ACLR:
                if (mMeasureDevice == MEASURE_DEVICE.DEVICE1) {

                    mExcelEditor.addExcelData(26, 8, Math.abs(SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData().getLowerPowerDbc(0)) + "");
                    mExcelEditor.addExcelData(26, 10, Math.abs(SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData().getUpperPowerDbc(0)) + "");

                } else {

                    mExcelEditor.addExcelData(26, 12, Math.abs(SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData().getLowerPowerDbc(0)) + "");
                    mExcelEditor.addExcelData(26, 14, Math.abs(SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData().getUpperPowerDbc(0)) + "");

                }
                break;

        }

    }

    private void nextMeasure(MeasureType.MEASURE_TYPE type) {
        //이 함수에서는 각 케이스별로 현재 타입이 선택된 타입과 다르면 실행해주어야함
        MeasureType typeFunc = FunctionHandler.getInstance().getMeasureType();


        switch (type) {

            case SWEPT_SA:

                if (mMacroBinding.chSweptSA.isChecked()) {
                    if (type == typeFunc.getType()) return;
                    mPercent += PERCENT_PER_TYPE;
                    typeFunc.setMeasureType(MeasureType.MEASURE_TYPE.SWEPT_SA);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(778);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setSpan(10);

                    ViewHandler.getInstance().getContent().update();
                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            SaDataHandler.getInstance().getConfigData().getSaParameter()
                    );

                    setProgress(mPercent, MeasureType.MEASURE_TYPE.SWEPT_SA.getFullName());

                } else {
                    nextMeasure(MeasureType.MEASURE_TYPE.CHANNEL_POWER);
                }
                break;

            case CHANNEL_POWER:
                if (mMacroBinding.chChannelPower.isChecked()) {
                    if (type == typeFunc.getType()) return;
                    typeFunc.setMeasureType(MeasureType.MEASURE_TYPE.CHANNEL_POWER);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(778);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setSpan(10);

                    DataHandler.getInstance().getStandardLoadData().load10MHzDataForLte();
                    ViewHandler.getInstance().getContent().update();
                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            SaDataHandler.getInstance().getConfigData().getSaParameter()
                    );

                    mPercent += PERCENT_PER_TYPE;
                    setProgress(mPercent, MeasureType.MEASURE_TYPE.CHANNEL_POWER.getFullName());

                } else {
                    nextMeasure(MeasureType.MEASURE_TYPE.OCCUPIED_BW);
                }
                break;

            case OCCUPIED_BW:
                if (mMacroBinding.chOccBw.isChecked()) {
                    if (type == typeFunc.getType()) return;
                    typeFunc.setMeasureType(MeasureType.MEASURE_TYPE.OCCUPIED_BW);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(778);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setSpan(10);

                    DataHandler.getInstance().getStandardLoadData().load10MHzDataForLte();
                    ViewHandler.getInstance().getContent().update();
                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            SaDataHandler.getInstance().getConfigData().getSaParameter()
                    );

                    mPercent += PERCENT_PER_TYPE;
                    setProgress(mPercent, MeasureType.MEASURE_TYPE.OCCUPIED_BW.getFullName());
                } else {
                    nextMeasure(MeasureType.MEASURE_TYPE.ACLR);
                }
                break;

            case ACLR:
                if (mMacroBinding.chAclr.isChecked()) {
                    if (type == typeFunc.getType()) return;
                    typeFunc.setMeasureType(MeasureType.MEASURE_TYPE.ACLR);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(778);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setSpan(10);

                    DataHandler.getInstance().getStandardLoadData().load10MHzDataForLte();
                    ViewHandler.getInstance().getContent().update();
                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            SaDataHandler.getInstance().getConfigData().getSaParameter()
                    );

                    mPercent += PERCENT_PER_TYPE;
                    setProgress(mPercent, MeasureType.MEASURE_TYPE.ACLR.getFullName());
                } else {
                    nextMeasure(MeasureType.MEASURE_TYPE.TRANSMIT_MASK);
                }
                break;

            case TRANSMIT_MASK:
                if (mMacroBinding.chTransmitOnOff.isChecked()) {
                    if (type == typeFunc.getType()) return;
                    typeFunc.setMeasureType(MeasureType.MEASURE_TYPE.TRANSMIT_MASK);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(778);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setSpan(10);

                    DataHandler.getInstance().getStandardLoadData().load10MHzDataForLte();
                    ViewHandler.getInstance().getContent().update();
                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            SaDataHandler.getInstance().getConfigData().getSaParameter()
                    );

                    mPercent += PERCENT_PER_TYPE;
                    setProgress(mPercent, MeasureType.MEASURE_TYPE.TRANSMIT_MASK.getFullName());
                } else {
                    close();
                    return;
                }

                break;

        }

    }

    public void setProgress(int percent, String type) {

        InitActivity.logMsg("setProgress", percent + "% " + " PERCENT_PER_TYPE : " + PERCENT_PER_TYPE);
        new Handler(Looper.getMainLooper()).post(() -> {

            mAutoMeasureProgress.setProgress(percent);
            mAutoMeasureProgress.setDescription(type + " measuring...");

        });

    }

    public void close() {

        isAutoMeasure = false;
        mRepeatCount = 0;
        mTotalCount = 0;
        NUMBER_OF_MEASUREMENT = 0;
        PERCENT_PER_TYPE = 0;
        mPercent = 0;

        if (mMeasureDevice == MEASURE_DEVICE.DEVICE2) {
            mExcelEditor.writeExcelData();
            if (mAutoMeasureProgress.isShowing())
                mAutoMeasureProgress.setTitle("");
        } else {
            if (mAutoMeasureProgress.isShowing())
                mAutoMeasureProgress.setTitle("Go to next step by pressing next button.");
        }

        mAutoMeasureProgress.setProgress(100);
        mAutoMeasureProgress.setDescription("Auto measuring completed.");
        mAutoMeasureProgress.completeMeasurement();

    }

    public void start() {

        Boolean[] checkBoxes = {
                mMacroBinding.chSweptSA.isChecked(),
                mMacroBinding.chChannelPower.isChecked(),
                mMacroBinding.chOccBw.isChecked(),
                mMacroBinding.chAclr.isChecked(),
                mMacroBinding.chTransmitOnOff.isChecked()
        };

        for (int i = 0; i < checkBoxes.length; i++) {

            if (checkBoxes[i])
                NUMBER_OF_MEASUREMENT++;

        }
        if (NUMBER_OF_MEASUREMENT == 0) return;

        PERCENT_PER_TYPE = (float) 1f / (float) NUMBER_OF_MEASUREMENT * 100f;

        isAutoMeasure = true;
        mRepeatCount = 0;
        dataList.clear();
//        mExcelEditor.clearImage();
//        mExcelEditor.initExcelFile();

        ViewHandler.getInstance().getSaMeas().addMenu();
        ViewHandler.getInstance().getContent().subInfoUpdate();
        FunctionHandler.getInstance().getMainLineChart().update();
        nextMeasure(MeasureType.MEASURE_TYPE.SWEPT_SA);

        mAutoMeasureProgress.setProgress(0);
        mAutoMeasureProgress.show();

    }

    public void addEvents() {

        mMacroBinding.tvStart.setOnClickListener(v -> {

            start();
            dismiss();

        });

        mMacroBinding.tvCancel.setOnClickListener(v -> {

            dismiss();

        });

    }


    @Override
    public void show() {
        super.show();
    }

    public AutoMeasureProgress getAutoMeasureWindow() {
        return mAutoMeasureProgress;
    }

}




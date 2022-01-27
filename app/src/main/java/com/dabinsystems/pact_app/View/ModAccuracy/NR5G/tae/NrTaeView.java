package com.dabinsystems.pact_app.View.ModAccuracy.NR5G.tae;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrInterTaeData;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.NR5G.NrMeasCountKeypadDialog;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrTaeData;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Screenshot.Screenshot;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class NrTaeView extends LayoutBase {

    private LinearLayout linTaeMeas, linNumOfTxAnt, linSelectTxAntIdx, linGscn, linTaeType, linInterTae, linMeasCount, linScreenShot;
    private AutofitTextView tvTaeMeas, tvNumOfTxAntVal, tvSelectTxAntIdxVal, tvGscnVal, tvTaeType, tvMeasCount, tvTaeTypeInter, tvTaeTypeIntra, tvScreenShotOn, tvScreenShotOff;
    private AutofitTextView tvNumOfTxAntTitle, tvSSBGscnVal, tvTaeMeasOn, tvTaeMeasOff;
    private Spinner spSSBLocation, spConfig;
    private DynamicView mDynamicView;

    public NrTaeView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {
            try {
                initView();

                update();

                mActivity.runOnUiThread(() -> {
                    ViewHandler.getInstance().getSAView().selectMeasSetup();

                    binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.tae_title));

                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(linTaeMeas);
                    binding.linRightMenu.addView(linTaeType);
                    binding.linRightMenu.addView(linNumOfTxAnt);
                    binding.linRightMenu.addView(linInterTae);
                    binding.linRightMenu.addView(linMeasCount);
                    binding.linRightMenu.addView(linScreenShot);

                    binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getNrMeasSetupView().addMenu());
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

        ArrayList<View> mConfigList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.tae_meas), "On", "Off");


        ArrayList<View> mNumOfTxAntList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.num_of_tx_ant), "");
//        ArrayList<View> mSelectTxAntIdxList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.select_tx_ant_idx), "");
        ArrayList<View> taeTypeList = mDynamicView.addRightMenuButton("TAE Type", "Inter", "Intra");
        ArrayList<View> interTaeList = mDynamicView.addRightMenuButton("Inter-TAE");
        ArrayList<View> MeasCountList = mDynamicView.addRightMenuButton("Meas. Count", "");
        ArrayList<View> ScreenshotList = mDynamicView.addRightMenuButton("Screenshot", "on", "off");

        linTaeMeas = (LinearLayout) mConfigList.get(0);
        tvTaeMeas = (AutofitTextView) mConfigList.get(1);
        tvTaeMeasOn = (AutofitTextView) mConfigList.get(2);
        tvTaeMeasOff = (AutofitTextView) mConfigList.get(3);
//        spConfig = (Spinner) mConfigList.get(2);
//        spConfig.setSelection(1);

        linTaeType = (LinearLayout) taeTypeList.get(0);
        tvTaeType = (AutofitTextView) taeTypeList.get(1);
        tvTaeTypeInter = (AutofitTextView) taeTypeList.get(2);
        tvTaeTypeIntra = (AutofitTextView) taeTypeList.get(3);

        linInterTae = (LinearLayout) interTaeList.get(0);

        linMeasCount = (LinearLayout) MeasCountList.get(0);
        tvMeasCount = (AutofitTextView) MeasCountList.get(2);

        linNumOfTxAnt = (LinearLayout) mNumOfTxAntList.get(0);
        tvNumOfTxAntTitle = (AutofitTextView) mNumOfTxAntList.get(1);
//        tvNumOfTxAntTitle.setSingleLine(false);
        tvNumOfTxAntVal = (AutofitTextView) mNumOfTxAntList.get(2);

//        linSelectTxAntIdx = (LinearLayout) mSelectTxAntIdxList.get(0);
//        tvSelectTxAntIdxVal = (AutofitTextView) mSelectTxAntIdxList.get(2);

        linScreenShot = (LinearLayout) ScreenshotList.get(0);
        tvScreenShotOn = (AutofitTextView) ScreenshotList.get(2);
        tvScreenShotOff = (AutofitTextView) ScreenshotList.get(3);

        setUpEvents();
    }

    @Override
    public void setUpEvents() throws NullPointerException {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linTaeMeas.setOnClickListener(v -> {
                NrTaeData.TAE_MEAS_MODE mode = DataHandler.getInstance().getNrData().getTaeInfoData().getTeaMeasMode();

                if (mode == NrTaeData.TAE_MEAS_MODE.ON) {
                    DataHandler.getInstance().getNrData().getTaeInfoData().setTeaMeasMode(NrTaeData.TAE_MEAS_MODE.OFF);
                    FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.NR_5G);
                } else {
                    DataHandler.getInstance().getNrData().getTaeInfoData().setTeaMeasMode(NrTaeData.TAE_MEAS_MODE.ON);
                    FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.TAE);
                    DataHandler.getInstance().getNrData().getTaeInfoData().setCurrentMeasCount(0);
                    NrInterTaeData interTaeData = DataHandler.getInstance().getNrData().getInterTaeData();
                    interTaeData.initCurrnetCarrierIdx();

                    NrTaeData.TAE_TYPE type = DataHandler.getInstance().getNrData().getTaeInfoData().getTaeType();

                    if (type == NrTaeData.TAE_TYPE.INTRA)
                        FunctionHandler.getInstance().getTaeFunc().TaeMeasureDialog(1);
                    else FunctionHandler.getInstance().getTaeFunc().TaeMeasureDialog(
                            DataHandler.getInstance().getNrData().getInterTaeData().getStartIdxCarrierOn() + 1
                    );
                }

                addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                updateTaeMeasView();
            });

            linTaeType.setOnClickListener(v -> {
                NrTaeData taeData = DataHandler.getInstance().getNrData().getTaeInfoData();
                if (taeData.getTaeType() == NrTaeData.TAE_TYPE.INTER) {
                    taeData.setTaeType(NrTaeData.TAE_TYPE.INTRA);
                } else {
                    taeData.setTaeType(NrTaeData.TAE_TYPE.INTER);
                }

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                updateTaeTypeView();
            });

            linNumOfTxAnt.setOnClickListener(v -> ViewHandler.getInstance().getNumOfTxAntView().addMenu());

            linInterTae.setOnClickListener(v -> ViewHandler.getInstance().getInterTaeView().addMenu());

//            linSelectTxAntIdx.setOnClickListener(v -> {
//
//                new SelectTxAntIdxKeypad(mActivity, binding).show();
//
//            });

            linMeasCount.setOnClickListener(v -> new NrMeasCountKeypadDialog(mActivity, binding).show());

            linScreenShot.setOnClickListener(v -> {
                NrTaeData nrTaeData = DataHandler.getInstance().getNrData().getTaeInfoData();
                if (nrTaeData.getScreenshotMode() == NrTaeData.SCREENSHOT_MODE.ON) {
                    nrTaeData.setScreenshotMode(NrTaeData.SCREENSHOT_MODE.OFF);
                } else {
                    nrTaeData.setScreenshotMode(NrTaeData.SCREENSHOT_MODE.ON);
                }
                updateScreenshotMode();
            });
        });
    }

    @SuppressLint("SetTextI18n")
    public void update() {
        updateTaeMeasView();
        updateTaeTypeView();
        updateNumOfTxAntView();
        updateMeasCount();
        updateScreenshotMode();
    }

    public void updateScreenshotMode() {
        NrTaeData nrTaeData = DataHandler.getInstance().getNrData().getTaeInfoData();
        if (nrTaeData.getScreenshotMode() == NrTaeData.SCREENSHOT_MODE.ON) {
            selectOptionView(tvScreenShotOn, tvScreenShotOff);
        } else {
            selectOptionView(tvScreenShotOff, tvScreenShotOn);
        }
    }

    public void updateMeasCount() {
        mActivity.runOnUiThread(() -> {
            NrTaeData taeInfoData = DataHandler.getInstance().getNrData().getTaeInfoData();
            tvMeasCount.setText(taeInfoData.getMeasCount() + "");
        });
    }

    public void updateTaeTypeView() {
        mActivity.runOnUiThread(() -> {
            NrTaeData.TAE_TYPE type = DataHandler.getInstance().getNrData().getTaeInfoData().getTaeType();
            if (type == NrTaeData.TAE_TYPE.INTER) {
                selectOptionView(tvTaeTypeInter, tvTaeTypeIntra);
            } else {
                selectOptionView(tvTaeTypeIntra, tvTaeTypeInter);
            }
        });
    }

    public void updateTaeMeasView() {
        mActivity.runOnUiThread(() -> {
            NrTaeData.TAE_MEAS_MODE taeMode = DataHandler.getInstance().getNrData().getTaeInfoData().getTeaMeasMode();
            if (taeMode == NrTaeData.TAE_MEAS_MODE.ON) {
                selectOptionView(tvTaeMeasOn, tvTaeMeasOff);
            } else {
                selectOptionView(tvTaeMeasOff, tvTaeMeasOn);
            }

            if (linTaeType != null) {
                linTaeType.setEnabled(taeMode != NrTaeData.TAE_MEAS_MODE.ON);
            }
            if (linNumOfTxAnt != null) {
                linNumOfTxAnt.setEnabled(taeMode != NrTaeData.TAE_MEAS_MODE.ON);
            }
            if (linInterTae != null) {
                linInterTae.setEnabled(taeMode != NrTaeData.TAE_MEAS_MODE.ON);
            }
            if (linMeasCount != null) {
                linMeasCount.setEnabled(taeMode != NrTaeData.TAE_MEAS_MODE.ON);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void updateNumOfTxAntView() {
        mActivity.runOnUiThread(() -> {
            tvNumOfTxAntVal.setText(DataHandler.getInstance().getNrData().getTaeInfoData().getNumOfTxAnt().getValue() + "");
        });
    }
}

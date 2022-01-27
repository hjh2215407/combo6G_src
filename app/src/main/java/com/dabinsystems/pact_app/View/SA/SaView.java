package com.dabinsystems.pact_app.View.SA;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.SAFrequencyData;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SaView extends LayoutBase {

    DynamicView mDynamicView;

    protected ArrayList<View> mSpanList, mFrequencyList, mAmplitudeList, mBwList, mPeakList, mTraceList, mMeasSetupList,
            mMarkerList, mStandardList, mSweepTimeList, mTriggerList, mSystemList;

    protected AutofitTextView tvSpan, tvFrequency, tvAmplitude, tvMarker, tvBw, tvSweepTime, tvPeak, tvTrace, tvStandard, tvMeasSetup, tvSystem, tvTrigger;
    protected LinearLayout linSpan, linFrequency, linAmplitude, linMarker, linBw, linSweeptime, linPeak, linTrace, linStandard, linMeasSetup, linSystem, linTrigger;

    public SaView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);

    }

    @Override
    public void addMenu() {
        super.addMenu();

        new Thread(() -> {

            try {

                initView();
                update();

                mActivity.runOnUiThread(() -> {

                    binding.linBottomMenu.removeAllViews();
                    binding.linBottomMenu.addView(linFrequency);
                    binding.linBottomMenu.addView(linSpan);
                    binding.linBottomMenu.addView(linAmplitude);
                    binding.linBottomMenu.addView(linBw);
                    binding.linBottomMenu.addView(linMarker);
                    binding.linBottomMenu.addView(linPeak);
                    binding.linBottomMenu.addView(linTrace);

                    MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
                    binding.linBottomMenu.addView(linSweeptime);

                    binding.linBottomMenu.addView(linStandard);
                    binding.linBottomMenu.addView(linMeasSetup);
                    binding.linBottomMenu.addView(linSystem);

                    binding.linCableList.setVisibility(View.GONE);

                    updateBottomViewColor();

                    if (type == MeasureType.MEASURE_TYPE.NR_5G)
                        ViewHandler.getInstance().getNrMeasSetupView().addMenu();
                    else if (type == MeasureType.MEASURE_TYPE.LTE_FDD)
                        ViewHandler.getInstance().getLteFddMeasSetupView().addMenu();
                    else if (type == MeasureType.MEASURE_TYPE.NR_5G_SCAN)
                        ViewHandler.getInstance().getNrScanMeasSetupView().addMenu();
                    else
                        ViewHandler.getInstance().getSaMeas().addMenu();

                });

            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }).start();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linFrequency.setOnClickListener(v -> {
                selectFrequency();

                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if (type == MeasureType.MEASURE_TYPE.NR_5G || type == MeasureType.MEASURE_TYPE.TAE) {
                    ViewHandler.getInstance().getDemodulationFrequencyView().addMenu();
                } else if (type == MeasureType.MEASURE_TYPE.LTE_FDD) {
                    ViewHandler.getInstance().getLteFddFrequencyView().addMenu();
                } else
                    ViewHandler.getInstance().getSaFrequencyView().addMenu();

            });

            linSpan.setOnClickListener(view -> {
                selectSpan();
                ViewHandler.getInstance().getSASpanView().addMenu();
            });

            linAmplitude.setOnClickListener(v -> {
                selectAmplitude();

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if (mode == MeasureMode.MEASURE_MODE.SA)
                    ViewHandler.getInstance().getSAAmplitudeView().addMenu();
                else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && (type == MeasureType.MEASURE_TYPE.NR_5G || type == MeasureType.MEASURE_TYPE.TAE))
                    ViewHandler.getInstance().getDemodulationAmplitudeView().addMenu();
                else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && type == MeasureType.MEASURE_TYPE.LTE_FDD)
                    ViewHandler.getInstance().getLteFddAmpView().addMenu();
            });

            linBw.setOnClickListener(view -> {
                selectBW();
                ViewHandler.getInstance().getBW().addMenu();
            });

            linMarker.setOnClickListener(v -> {
                selectMarker();
                ViewHandler.getInstance().getSaMarkerView().addMenu();
            });

            linPeak.setOnClickListener(view -> {
                selectPeak();
                ViewHandler.getInstance().getSAPeakView().addMenu();
            });

            linTrace.setOnClickListener(view -> {
                selectTrace();

                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if (type == MeasureType.MEASURE_TYPE.SEM)
                    ViewHandler.getInstance().getSemTraceView().addMenu();
                else ViewHandler.getInstance().getTrace().addMenu();

            });

            linMeasSetup.setOnClickListener(view -> {
                selectMeasSetup();

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if (mode == MeasureMode.MEASURE_MODE.SA) {
                    ViewHandler.getInstance().getSaMeasSetup().addMenu();
                } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {
                    switch (type) {
                        case NR_5G:
                        case TAE:
                            ViewHandler.getInstance().getNrMeasSetupView().addMenu();
                            break;
                        case LTE_FDD:
                            ViewHandler.getInstance().getLteFddMeasSetupView().addMenu();
                            break;
                        case NR_5G_SCAN:
                            ViewHandler.getInstance().getNrScanMeasSetupView().addMenu();
                            break;
                    }
                }
            });

            linStandard.setOnClickListener(v -> {

                selectStandard();
                ViewHandler.getInstance().getStandardView().addMenu();

            });

            linSweeptime.setOnClickListener(v -> {
                selectSweepTime();
                ViewHandler.getInstance().getSaSweepView().addMenu();
            });

            linTrigger.setOnClickListener(v -> {
                selectTrigger();
                //todo: ViewHandler.getInstance().getTriggerView.addMenu();
            });

            linSystem.setOnClickListener(v -> {
                selectSystem();
                ViewHandler.getInstance().getSystemView().addMenu();
            });

        });

    }

    protected void updateBottomViewColor() {
        initView();

        mActivity.runOnUiThread(() -> {
            linFrequency.setSelected(false);
            linSpan.setSelected(false);
            linAmplitude.setSelected(false);
            linBw.setSelected(false);
            linMarker.setSelected(false);
            linPeak.setSelected(false);
            linTrace.setSelected(false);
            linMeasSetup.setSelected(false);
            linStandard.setSelected(false);
            linSystem.setSelected(false);
            linSweeptime.setSelected(false);
        });
    }

    @Override
    public void update() {
        super.update();
        ViewHandler.getInstance().getContent().update();
        updateMenu();
    }

    public void selectBottomView(View parent) {
        updateBottomViewColor();

        try {
            parent.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectFrequency() {
        selectBottomView(linFrequency);
    }

    public void selectSpan() {
        selectBottomView(linSpan);
    }

    public void selectAmplitude() {
        selectBottomView(linAmplitude);
    }

    public void selectBW() {
        selectBottomView(linBw);
    }

    public void selectMarker() {
        selectBottomView(linMarker);
    }

    public void selectMarkerLayout() {
        selectBottomView(linMarker);
    }

    public void selectPeak() {
        selectBottomView(linPeak);
    }

    public void selectTrace() {
        initView();
        selectBottomView(linTrace);
    }

    public void selectMeasSetup() {
        selectBottomView(linMeasSetup);
    }

    public void selectStandard() {
        selectBottomView(linStandard);
    }

    public void selectSweepTime() {
        selectBottomView(linSweeptime);
    }

    public void selectTrigger() {
        selectBottomView(linTrigger);
    }

    public void selectSystem() {
        selectBottomView(linSystem);
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mFrequencyList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.freq_short_name));
        linFrequency = (LinearLayout) mFrequencyList.get(0);
        tvFrequency = (AutofitTextView) mFrequencyList.get(1);

        mSpanList = mDynamicView.addBottomMenuButton(mActivity.getString(R.string.span_name));
        linSpan = (LinearLayout) mSpanList.get(0);
        tvSpan = (AutofitTextView) mSpanList.get(1);

        mAmplitudeList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.amp_short_name));
        linAmplitude = (LinearLayout) mAmplitudeList.get(0);
        tvAmplitude = (AutofitTextView) mAmplitudeList.get(1);

        mBwList = mDynamicView.addBottomMenuButton(mActivity.getString(R.string.bw_name));
        linBw = (LinearLayout) mBwList.get(0);
        tvBw = (AutofitTextView) mBwList.get(1);

        mMarkerList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.marker_name));
        linMarker = (LinearLayout) mMarkerList.get(0);
        tvMarker = (AutofitTextView) mMarkerList.get(1);

        mPeakList = mDynamicView.addBottomMenuButton(mActivity.getString(R.string.peak_name));
        linPeak = (LinearLayout) mPeakList.get(0);
        tvPeak = (AutofitTextView) mPeakList.get(1);

        mTraceList = mDynamicView.addBottomMenuButton(mActivity.getString(R.string.trace_name));
        linTrace = (LinearLayout) mTraceList.get(0);
        tvTrace = (AutofitTextView) mTraceList.get(1);

        mSweepTimeList = mDynamicView.addBottomMenuButton(mActivity.getString(R.string.sweep_time_name));
        linSweeptime = (LinearLayout) mSweepTimeList.get(0);
        tvSweepTime = (AutofitTextView) mSweepTimeList.get(1);

        mTriggerList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.trigger));
        linTrigger = (LinearLayout) mTriggerList.get(0);
        tvTrigger = (AutofitTextView) mTriggerList.get(1);

        mStandardList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.standard_title));
        linStandard = (LinearLayout) mStandardList.get(0);
        tvStandard = (AutofitTextView) mStandardList.get(1);
        tvStandard.setTypeface(Typeface.DEFAULT_BOLD);

        mMeasSetupList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.set_up));
        linMeasSetup = (LinearLayout) mMeasSetupList.get(0);
        tvMeasSetup = (AutofitTextView) mMeasSetupList.get(1);

        mSystemList = mDynamicView.addBottomMenuButton(mActivity.getResources().getString(R.string.sys_short_name));
        linSystem = (LinearLayout) mSystemList.get(0);
        tvSystem = (AutofitTextView) mSystemList.get(1);


        setUpEvents();

    }

    public void updateMenu() {
        initView();

        mActivity.runOnUiThread(() -> {
            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
            SAFrequencyData freqData = SaDataHandler.getInstance().getConfigData().getFrequencyData();

            switch (type) {

                case SWEPT_SA:
                    linFrequency.setEnabled(true);
                    linSpan.setEnabled(true);
                    linBw.setEnabled(true);
                    linMarker.setEnabled(true);
                    linPeak.setEnabled(true);
                    linTrace.setEnabled(true);
                    if (freqData.getCenterFreq() == 10f)
                        linStandard.setEnabled(false);
                    else linStandard.setEnabled(true);
                    linSweeptime.setEnabled(true);
                    break;
                case ACLR:
                    linFrequency.setEnabled(true);
                    linSpan.setEnabled(false); // [2021-10-18] ACLR 화면에서 'Span' 메뉴 비활성화
                    linBw.setEnabled(true);
                    linMarker.setEnabled(true);
                    linPeak.setEnabled(true);
                    linTrace.setEnabled(true);
                    if (freqData.getCenterFreq() == 10f)
                        linStandard.setEnabled(false);
                    else linStandard.setEnabled(true);
                    linSweeptime.setEnabled(true);
                    break;
                case INTERFERENCE_HUNTING:
                    linFrequency.setEnabled(false);
                    linSpan.setEnabled(true);
                    linBw.setEnabled(true);
                    linMarker.setEnabled(true);
                    linPeak.setEnabled(true);
                    linTrace.setEnabled(true);
                    if (freqData.getCenterFreq() == 10f)
                        linStandard.setEnabled(false);
                    else linStandard.setEnabled(true);
                    linSweeptime.setEnabled(true);
                    break;
                case SEM:
                    linFrequency.setEnabled(true);
                    linSpan.setEnabled(false);
                    linBw.setEnabled(false);
                    linMarker.setEnabled(true);
                    linPeak.setEnabled(true);
                    linTrace.setEnabled(true);
                    if (freqData.getCenterFreq() == 10f)
                        linStandard.setEnabled(false);
                    else linStandard.setEnabled(true);
//                    linStandard.setEnabled(false); // DEMO
                    linSweeptime.setEnabled(true);

                    break;
                case TRANSMIT_MASK:
                    linFrequency.setEnabled(true);
                    linSpan.setEnabled(false);
                    linBw.setEnabled(false);
                    linMarker.setEnabled(true);
                    linPeak.setEnabled(true);
                    linTrace.setEnabled(true);
                    if (freqData.getCenterFreq() == 10f)
                        linStandard.setEnabled(false);
                    else linStandard.setEnabled(true);
//                    linStandard.setEnabled(false); // DEMO

                    linSweeptime.setEnabled(false);

                    break;

                case SPURIOUS_EMISSION:
                    linFrequency.setEnabled(false);
                    linSpan.setEnabled(false);
                    linBw.setEnabled(false);
                    linMarker.setEnabled(true);
                    linPeak.setEnabled(true);
                    linTrace.setEnabled(true);
                    if (freqData.getCenterFreq() == 10f)
                        linStandard.setEnabled(false);
                    else linStandard.setEnabled(true);
//                    linStandard.setEnabled(false); // DEMO
                    linSweeptime.setEnabled(true);
                    break;

                case TAE:
                case NR_5G:
                    linFrequency.setEnabled(true);
                    linSpan.setEnabled(false);
                    linBw.setEnabled(false);
                    linMarker.setEnabled(false);
                    linPeak.setEnabled(false);
                    linTrace.setEnabled(false);
                    linStandard.setEnabled(false);
                    linSweeptime.setEnabled(false);
                    break;
                case NR_5G_SCAN:
                    linFrequency.setEnabled(false);
                    linSpan.setEnabled(false);
                    linBw.setEnabled(false);
                    linMarker.setEnabled(false);
                    linPeak.setEnabled(false);
                    linTrace.setEnabled(false);
                    linStandard.setEnabled(false);
                    linSweeptime.setEnabled(false);
                    break;

                case LTE_FDD:
                    linFrequency.setEnabled(true);
                    linSpan.setEnabled(false);
                    linBw.setEnabled(false);
                    linMarker.setEnabled(false);
                    linPeak.setEnabled(false);
                    linTrace.setEnabled(false);
                    linStandard.setEnabled(false);
                    linSweeptime.setEnabled(false);
                    break;

                default:
                    linFrequency.setEnabled(true);
                    linSpan.setEnabled(true);
                    linBw.setEnabled(true);
                    linMarker.setEnabled(true);
                    linPeak.setEnabled(true);
                    linTrace.setEnabled(true);
                    if (freqData.getCenterFreq() == 10f)
                        linStandard.setEnabled(false);
                    else linStandard.setEnabled(true);
//                    linStandard.setEnabled(false); // DEMO
                    linSweeptime.setEnabled(true);
                    break;
            }

            if (type == MeasureType.MEASURE_TYPE.NR_5G_SCAN) {
                linAmplitude.setEnabled(false);
            } else {
                linAmplitude.setEnabled(true);
            }

            updateBottomViewColor();

        });
    }

}

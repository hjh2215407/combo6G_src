package com.dabinsystems.pact_app.View.Marker;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.DtfMarkerFreqKeypad;
import com.dabinsystems.pact_app.Dialog.VswrMarkerFreqKeypad;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class MarkerSetupView extends LayoutBase {

    private DynamicView dynamicView;
    private LinearLayout linSelect, linFreq, linMarkerType, linMarker, linMarkerTable, linMarkerPreset;
    private AutofitTextView tvSelectedMarker, tvFreqTitle, tvFreqVal, tvMarkerTypeSub, tvMarkerTableOn, tvMarkerTableOff;
    private AutofitTextView tvMarkerOn, tvMarkerOff;

    public MarkerSetupView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {

                ViewHandler.getInstance().getVswrView().selectMarker();
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linSelect);
                binding.linRightMenu.addView(linFreq);
                binding.linRightMenu.addView(linMarkerType);
                binding.linRightMenu.addView(linMarker);
                binding.linRightMenu.addView(linMarkerTable);
                binding.linRightMenu.addView(linMarkerPreset);

                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.marker_name));

                binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getMarkerView().addMenu());
            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (dynamicView != null) return;

        dynamicView = new DynamicView(mActivity.getApplicationContext());

        ArrayList<View> mSelectList = dynamicView.addRightMenuButton("Select", "");
        linSelect = (LinearLayout) mSelectList.get(0);
        tvSelectedMarker = (AutofitTextView) mSelectList.get(2);

        ArrayList<View> mFreqList = dynamicView.addRightMenuButton("Frequency", "");
        linFreq = (LinearLayout) mFreqList.get(0);
        tvFreqTitle = (AutofitTextView) mFreqList.get(1);
        tvFreqVal = (AutofitTextView) mFreqList.get(2);

        ArrayList<View> mMarkerTypeList = dynamicView.addRightMenuButton("Marker Type", "Ref");
        linMarkerType = (LinearLayout) mMarkerTypeList.get(0);
        tvMarkerTypeSub = (AutofitTextView) mMarkerTypeList.get(2);

        ArrayList<View> mMarkerList = dynamicView.addRightMenuButton("Marker", mActivity.getResources().getString(R.string.on), mActivity.getResources().getString(R.string.off));
        linMarker = (LinearLayout) mMarkerList.get(0);
        tvMarkerOn = (AutofitTextView) mMarkerList.get(2);
        tvMarkerOff = (AutofitTextView) mMarkerList.get(3);

        ArrayList<View> mMarkerTableList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.marker_table_name), mActivity.getResources().getString(R.string.on), mActivity.getResources().getString(R.string.off));
        linMarkerTable = (LinearLayout) mMarkerTableList.get(0);
        tvMarkerTableOn = (AutofitTextView) mMarkerTableList.get(2);
        tvMarkerTableOff = (AutofitTextView) mMarkerTableList.get(3);

        ArrayList<View> mMarkerPresetList = dynamicView.addRightMenuButton("Marker Preset", Gravity.END | Gravity.CENTER_VERTICAL);
        linMarkerPreset = (LinearLayout) mMarkerPresetList.get(0);

        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        linSelect.setOnClickListener(v -> ViewHandler.getInstance().getSelectMarkerView().addMenu());

        linFreq.setOnClickListener(v -> {
            MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

            if (mode == MeasureMode.MEASURE_MODE.DTF) {
                new DtfMarkerFreqKeypad(mActivity, binding).show();
            } else {
                new VswrMarkerFreqKeypad(mActivity, binding).show();
            }
        });

        linMarkerType.setOnClickListener(v -> new Handler(Looper.getMainLooper()).post(() -> {
            if (FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex() == 0)
                return;

            FunctionHandler.getInstance().getMainLineChart().setDeltaMarkerNotFixed(!FunctionHandler.getInstance().getMainLineChart().isDelta());

            update();
            ViewHandler.getInstance().getContent().update();
        }));

        linMarker.setOnClickListener(v -> new Handler(Looper.getMainLooper()).post(() -> {
            if (FunctionHandler.getInstance().getMainLineChart().isOnMarker()) {
                FunctionHandler.getInstance().getMainLineChart().removeSelectedMarker();
            } else {
                FunctionHandler.getInstance().getMainLineChart().setMarker(
                        0,
                        0,
                        FunctionHandler.getInstance().getMainLineChart().getCenterFreq()
                );
            }

            FunctionHandler.getInstance().getMainLineChart().invalidate();

            ViewHandler.getInstance().getContent().update();
            update();
        }));

        linMarkerTable.setOnClickListener(v -> new Handler(Looper.getMainLooper()).post(() -> {
            Boolean on = !ViewHandler.getInstance().getContent().isOpenMarkerTable();
            ViewHandler.getInstance().getContent().setMarkerTable(on);
            ViewHandler.getInstance().getContent().update();
            update();
        }));

        linMarkerPreset.setOnClickListener(v -> new Handler(Looper.getMainLooper()).post(() -> {
            if (FunctionHandler.getInstance().getMainLineChart().isOnMarker()) {
                FunctionHandler.getInstance().getMainLineChart().removeAllMarkers();
                FunctionHandler.getInstance().getMainLineChart().setMarkerForAbs(
                        0,
                        0,
                        FunctionHandler.getInstance().getMainLineChart().getCenterFreq());
                FunctionHandler.getInstance().getMainLineChart().invalidate();
            }

            update();
            ViewHandler.getInstance().getContent().update();
        }));


    }

    @SuppressLint("SetTextI18n")
    public void freqUpdate() {

        new Thread(() -> {

            initView();

            try {
                int idx = FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex();
                if (idx == -1) {
                    mActivity.runOnUiThread(() -> tvFreqVal.setText(""));
                    return;
                }

                double val = FunctionHandler.getInstance().getMainLineChart().getCurrentMarkerFreq(idx);
                val = Math.round(val * 100d) / 100d;

                if (tvFreqVal != null) {
                    if (!FunctionHandler.getInstance().getMainLineChart().isOnMarker()) {
                        mActivity.runOnUiThread(() -> tvFreqVal.setText(""));
                        return;
                    }

                    String xUnit;
                    MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

                    if (mode != MeasureMode.MEASURE_MODE.DTF) xUnit = " MHz";
                    else xUnit = " m";

                    double finalVal = val;
                    mActivity.runOnUiThread(() -> tvFreqVal.setText(finalVal + xUnit));
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void markerUpdate() {

        new Thread(() -> {
            try {
                initView();

                if (ViewHandler.getInstance().getContent().isOpenMarkerTable()) {
                    selectOptionView(tvMarkerTableOn, tvMarkerTableOff);
                } else {
                    selectOptionView(tvMarkerTableOff, tvMarkerTableOn);
                }

                if (FunctionHandler.getInstance().getMainLineChart().isOnMarker()
                        && FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex() != -1) {
                    mActivity.runOnUiThread(() -> {
                        try {
                            tvSelectedMarker.setText((FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex() + 1) + "");
                            selectOptionView(tvMarkerOn, tvMarkerOff);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    mActivity.runOnUiThread(() -> {
                        tvSelectedMarker.setText("");
                        selectOptionView(tvMarkerOff, tvMarkerOn);
                    });
                }

                if (FunctionHandler.getInstance().getMainLineChart().isDelta()) {
                    mActivity.runOnUiThread(() -> {
                        linMarkerType.setSelected(true);
                        tvMarkerTypeSub.setText("Delta");
                    });
                } else {
                    mActivity.runOnUiThread(() -> {
                        linMarkerType.setSelected(false);
                        tvMarkerTypeSub.setText("Ref");
                    });
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }).start();

    }

    @Override
    public void update() {
        super.update();

        try {
            new Handler(Looper.getMainLooper()).post(() -> {
                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

                if (mode == MeasureMode.MEASURE_MODE.DTF)
                    tvFreqTitle.setText("Distance");
                else
                    tvFreqTitle.setText("Frequency");
            });
            freqUpdate();
            markerUpdate();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}

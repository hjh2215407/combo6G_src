package com.dabinsystems.pact_app.View.SA.MeasSetup.SpuriousEmission;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SpuriousEmission.FreqRangeTableData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SpuriousEmission.SpuriousEmissionMeasSetupData;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SpuriousEmission.FreqRangeTableAbsStartLimitKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SpuriousEmission.FreqRangeTableAbsStopLimitKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SpuriousEmission.FreqRangeTableStartFreqKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SpuriousEmission.FreqRangeTableStopFreqKeypad;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SpuriousFreqRangeTableView extends LayoutBase {

    ArrayList<View> mFreqRangeList, mStartFreqList, mRbwList, mStopFreqList, mVbwList, mAbsStartLimitList, mAbsStopLimitList, mSelectFreqRangeList;
    LinearLayout linFreqRange, linStartFreq, linRbw, linStopFreq, linVbw, linAbsStartLimit, linAbsStopLimit, linSelectFreqRange;
    AutofitTextView tvVbwVal, tvAbsStartLimitVal, tvAbsStopLimitVal, tvGps, tvSelectFreqRangeVal, tvFreqRange, tvFreqRangeVal, tvFreqRangeOff, tvFreqRangeOn, tvStartFreqVal, tvStopFreqVal, tvRbwVal;
    DynamicView mDynamicView;

    public SpuriousFreqRangeTableView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();
            mActivity.runOnUiThread(() -> {

                try {

                    binding.linRightMenu.removeAllViews();
                    binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.meas_setup));

                    binding.linRightMenu.addView(linSelectFreqRange);
                    binding.linRightMenu.addView(linFreqRange);
                    binding.linRightMenu.addView(linStartFreq);
                    binding.linRightMenu.addView(linStopFreq);
                    binding.linRightMenu.addView(linRbw);
                    binding.linRightMenu.addView(linVbw);
                    binding.linRightMenu.addView(linAbsStartLimit);
                    binding.linRightMenu.addView(linAbsStopLimit);

                    binding.tvBack.setOnClickListener(v->{

                        ViewHandler.getInstance().getMeasSetupSpuriousView().addMenu();

                    });

                } catch (NullPointerException | IllegalArgumentException e) {
                    e.printStackTrace();
                }

            });

        }).start();

    }

    @SuppressLint("SetTextI18n")
    public void update() {

        initView();

        mActivity.runOnUiThread(() -> {

            try {

                SpuriousEmissionMeasSetupData measSetupData = SaDataHandler.getInstance().getSpuriousEmissionConfigData().getSpuriousEmissionData();
                ArrayList<FreqRangeTableData> freqRangeTableList = measSetupData.getFreqRangeTableDataList();

                if (measSetupData.getSelectedIndex() < freqRangeTableList.size()) {

                    new Handler(Looper.getMainLooper()).post(() -> {

                        if(tvSelectFreqRangeVal != null)
                            tvSelectFreqRangeVal.setText((measSetupData.getSelectedIndex() + 1) + "");

                        if (tvFreqRangeVal != null)
                            tvFreqRangeVal.setText(measSetupData.getSelectedIndex() + 1 + "");
                        FreqRangeTableData freqRangeTableData = freqRangeTableList.get(measSetupData.getSelectedIndex());
                        boolean isState = freqRangeTableData.isState();

                        if (isState) {

                            selectOptionView(tvFreqRangeOn, tvFreqRangeOff);

                        } else {

                            selectOptionView(tvFreqRangeOff, tvFreqRangeOn);

                        }

                        if (tvStartFreqVal != null)
                            tvStartFreqVal.setText(freqRangeTableData.getStartFreq() + " MHz");
                        if (tvStopFreqVal != null)
                            tvStopFreqVal.setText(freqRangeTableData.getStopFreq() + " MHz");
                        if (tvRbwVal != null)
                            tvRbwVal.setText(freqRangeTableData.getBW().getRBW().getString());
                        if (tvVbwVal != null)
                            tvVbwVal.setText(freqRangeTableData.getBW().getVBW().getString());

                        if (tvAbsStartLimitVal != null)
                            tvAbsStartLimitVal.setText(freqRangeTableData.getAbsStartLimit() + "dBm");
                        if (tvAbsStopLimitVal != null)
                            tvAbsStopLimitVal.setText(freqRangeTableData.getAbsStopLimit() + "dBm");

                    });

                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mSelectFreqRangeList = mDynamicView.addRightMenuButton("Select Freq Range", "");
        linSelectFreqRange = (LinearLayout) mSelectFreqRangeList.get(0);
        tvSelectFreqRangeVal = (AutofitTextView) mSelectFreqRangeList.get(2);

        mFreqRangeList = mDynamicView.addRightMenuButton("Freq Range", "on", "off");
        linFreqRange = (LinearLayout) mFreqRangeList.get(0);
        tvFreqRange = (AutofitTextView) mFreqRangeList.get(1);
        tvFreqRangeOn = (AutofitTextView) mFreqRangeList.get(2);
        tvFreqRangeOff = (AutofitTextView) mFreqRangeList.get(3);

        mStartFreqList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.start_freq), "");
        linStartFreq = (LinearLayout) mStartFreqList.get(0);
        tvStartFreqVal = (AutofitTextView) mStartFreqList.get(2);

        mStopFreqList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.stop_freq), "");
        linStopFreq = (LinearLayout) mStopFreqList.get(0);
        tvStopFreqVal = (AutofitTextView) mStopFreqList.get(2);

        mRbwList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.rbw_name), "");
        linRbw = (LinearLayout) mRbwList.get(0);
        tvRbwVal = (AutofitTextView) mRbwList.get(2);

        mVbwList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.vbw_name), "");
        linVbw = (LinearLayout) mVbwList.get(0);
        tvVbwVal = (AutofitTextView) mVbwList.get(2);

        mAbsStartLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.abs_start_limit), "");
        linAbsStartLimit = (LinearLayout) mAbsStartLimitList.get(0);
        tvAbsStartLimitVal = (AutofitTextView) mAbsStartLimitList.get(2);

        mAbsStopLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.abs_stop_limit), "");
        linAbsStopLimit = (LinearLayout) mAbsStopLimitList.get(0);
        tvAbsStopLimitVal = (AutofitTextView) mAbsStopLimitList.get(2);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linSelectFreqRange.setOnClickListener(v -> {

                //// TODO: 1/19/21 add to freq range selection menu...
                ViewHandler.getInstance().getSelectFreqRangeView().addMenu();

            });

            linFreqRange.setOnClickListener(v -> {

                SpuriousEmissionMeasSetupData data = SaDataHandler.getInstance().getSpuriousEmissionConfigData().getSpuriousEmissionData();
                ArrayList<FreqRangeTableData> dataList = data.getFreqRangeTableDataList();
                int selectedIdx = data.getSelectedIndex();
                FreqRangeTableData freqRangeTableData = dataList.get(selectedIdx);
                boolean state = freqRangeTableData.isState();
                freqRangeTableData.setState(!state);

                FunctionHandler.getInstance().getMainLineChart().updateSpuriousEmissionLine();
                update();

            });

            linStartFreq.setOnClickListener(v -> {

                new FreqRangeTableStartFreqKeypad(mActivity, binding).show();

            });

            linStopFreq.setOnClickListener(v -> {
                new FreqRangeTableStopFreqKeypad(mActivity, binding).show();

            });

            linRbw.setOnClickListener(v -> {

            });

            linVbw.setOnClickListener(v -> {

            });

            linAbsStartLimit.setOnClickListener(v -> {
                new FreqRangeTableAbsStartLimitKeypad(mActivity, binding).show();
            });

            linAbsStopLimit.setOnClickListener(v -> {
                new FreqRangeTableAbsStopLimitKeypad(mActivity, binding).show();
            });

        });

    }
}

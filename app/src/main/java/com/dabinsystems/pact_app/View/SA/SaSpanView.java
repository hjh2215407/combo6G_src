package com.dabinsystems.pact_app.View.SA;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.SA.SaSpanFreqKeypad;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SaSpanView extends LayoutBase {

    private LinearLayout linSpan, linFullSpan, linZeroSpan, linLastSpan;
    private AutofitTextView tvSpan, tvFullSpan, tvZeroSpan, tvLastSpan, tvSpanVal;
    private ArrayList<View> mSpanList, mFullSpanList, mZeroSpanList, mLastSpanList;
    private DynamicView mDynamicView;


    public SaSpanView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }


    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            updateSpan();

            mActivity.runOnUiThread(() -> {
                ViewHandler.getInstance().getSAView().selectSpan();
                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.span_name));
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linSpan);

                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if(type == MeasureType.MEASURE_TYPE.SWEPT_SA) {
                    binding.linRightMenu.addView(linFullSpan);
                    binding.linRightMenu.addView(linZeroSpan); // 추후 개발
                }

                binding.linRightMenu.addView(linLastSpan);
            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mSpanList = mDynamicView.addRightMenuButton(mActivity.getString(R.string.span_name), "");
        linSpan = (LinearLayout) mSpanList.get(0);
        tvSpan = (AutofitTextView) mSpanList.get(1);
        tvSpanVal = (AutofitTextView) mSpanList.get(2);
        tvSpan.setSingleLine(false);

        mFullSpanList = mDynamicView.addRightMenuButton(mActivity.getString(R.string.full_span_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linFullSpan = (LinearLayout) mFullSpanList.get(0);
        tvFullSpan = (AutofitTextView) mFullSpanList.get(1);
        tvFullSpan.setSingleLine(false);

        mZeroSpanList = mDynamicView.addRightMenuButton(mActivity.getString(R.string.zero_span_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linZeroSpan = (LinearLayout) mZeroSpanList.get(0);
        tvZeroSpan = (AutofitTextView) mZeroSpanList.get(1);
        tvZeroSpan.setSingleLine(false);

        mLastSpanList = mDynamicView.addRightMenuButton(mActivity.getString(R.string.last_span_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linLastSpan = (LinearLayout) mLastSpanList.get(0);
        tvLastSpan = (AutofitTextView) mLastSpanList.get(1);
        tvLastSpan.setSingleLine(false);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linSpan.setOnClickListener(v -> {

                new SaSpanFreqKeypad(mActivity, binding).show();

            });

            linFullSpan.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                SaDataHandler.getInstance().getConfigData().getFrequencyData().setFullSpan();
//                double startFreq = SaDataHandler.getInstance().getConfigData().getFrequencyData().getStartFreq();
//                double stopFreq = SaDataHandler.getInstance().getConfigData().getFrequencyData().getStopFreq();
//                FunctionHandler.getInstance().getMainLineChart().setStartFreq(startFreq);
//                FunctionHandler.getInstance().getMainLineChart().setStopFreq(stopFreq);

                FunctionHandler.getInstance().getMainLineChart().updateMarkerPos();

                new Handler(Looper.getMainLooper()).postDelayed(()->{

                    FunctionHandler.getInstance().getMainLineChart().updateOccBox();
                    FunctionHandler.getInstance().getMainLineChart().updateChannelBox();

                }, 500);

                FunctionHandler.getInstance().getMainLineChart().invalidate();

                updateSpan();
                ViewHandler.getInstance().getContent().subInfoUpdate();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linZeroSpan.setOnClickListener(v -> {
                SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                SaDataHandler.getInstance().getConfigData().getFrequencyData().zerospan();


                FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
                FunctionHandler.getInstance().getMainLineChart().updateSemBox();

                ViewHandler.getInstance().getSASpanView().updateSpan();
                ViewHandler.getInstance().getContent().subInfoUpdate();

                FunctionHandler.getInstance().getMainLineChart().updateMarkerPos();
                FunctionHandler.getInstance().getMainLineChart().invalidate();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            linLastSpan.setOnClickListener(v -> {

                SaDataHandler.getInstance().getConfigData().getFrequencyData().setPrevFreq();
                SaDataHandler.getInstance().getConfigData().getFrequencyData().checkFreqRange();

                if(SaDataHandler.getInstance().getConfigData().getFrequencyData().getSpan() != 0f) {

                    double startFreq = SaDataHandler.getInstance().getConfigData().getFrequencyData().getStartFreq();
                    double stopFreq = SaDataHandler.getInstance().getConfigData().getFrequencyData().getStopFreq();
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setStartFreq(startFreq);
                    SaDataHandler.getInstance().getConfigData().getFrequencyData().setStopFreq(stopFreq);

                }

                FunctionHandler.getInstance().getMainLineChart().updateMarkerPos();

                new Handler(Looper.getMainLooper()).postDelayed(()->{

                    FunctionHandler.getInstance().getMainLineChart().updateInterferenceHuntingBox();
                    FunctionHandler.getInstance().getMainLineChart().updateOccBox();
                    FunctionHandler.getInstance().getMainLineChart().updateChannelBox();

                }, 500);

                FunctionHandler.getInstance().getMainLineChart().invalidate();

                ViewHandler.getInstance().getContent().subInfoUpdate();
                updateSpan();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

        });
    }

    @SuppressLint("SetTextI18n")
    public void updateSpan() {

        mActivity.runOnUiThread(() -> {

            if (tvSpanVal != null) {
                tvSpanVal.post(() -> {
                    double val = SaDataHandler.getInstance().getConfigData().getFrequencyData().getSpan();
                    val = Math.round(val * 1000000d)/1000000d;
                    String frequency = InitActivity.getFrequencyWithUnit(val);

                    tvSpanVal.setText(frequency);
                    tvSpanVal.invalidate();

                });
            }

        });


//        FunctionHandler.getInstance().getMainLineChart().invalidate();

    }
}

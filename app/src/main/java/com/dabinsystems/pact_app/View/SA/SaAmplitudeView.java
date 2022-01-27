package com.dabinsystems.pact_app.View.SA;

import android.annotation.SuppressLint;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.SAFrequencyData;
import com.dabinsystems.pact_app.Data.SA.SaAmplitudeData;
import com.dabinsystems.pact_app.Data.SA.SaConfigData;
import com.dabinsystems.pact_app.Dialog.SA.AttenuatorKeypadDialog;
import com.dabinsystems.pact_app.Dialog.SA.OffsetKeypadDialog;
import com.dabinsystems.pact_app.Dialog.SA.RefLevKeypadDialog;
import com.dabinsystems.pact_app.Dialog.SA.ScaleDivKeypadDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.View.SA.Trace.OffsetDetectorView;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import me.grantland.widget.AutofitTextView;

public class SaAmplitudeView extends LayoutBase {

    private LinearLayout linRefLev, linAttenuator, linScaleDiv, linOffset, linPreamp;
    private AutofitTextView tvRefLev, tvAttenuator, tvScaleDiv, tvOffset, tvPreampOff, tvPreampOn;
    private ArrayList<View> mRefLevList, mAttenuatorList, mScaleDivList, mOffsetList, mPreampList;
    private DynamicView mDynamicView;

    public SaAmplitudeView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            try {

                initView();
                update();

                mActivity.runOnUiThread(() -> {
                    ViewHandler.getInstance().getSAView().selectAmplitude();

                    binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.amplitude_name));

                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(linRefLev);
//                    double freqData = SaDataHandler.getInstance().getConfigData().getFrequencyData().getCenterFreq();
                    binding.linRightMenu.addView(linAttenuator);
                    binding.linRightMenu.addView(linPreamp);
                    binding.linRightMenu.addView(linScaleDiv);
                    binding.linRightMenu.addView(linOffset);

                    binding.linCableList.setVisibility(View.GONE);

                    binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getSAView().selectAmplitude());
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

        mRefLevList = mDynamicView.addRightMenuButton(mActivity.getString(R.string.ref_level_name), "0 dBm");
        mAttenuatorList = mDynamicView.addRightMenuButton(mActivity.getString(R.string.attenuator_name), "0 dB");
        mPreampList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.preamp), "On", "Off");
        mScaleDivList = mDynamicView.addRightMenuButton("Scale/Div", "10.0 dB");
        mOffsetList = mDynamicView.addRightMenuButton("Offset", "0.00 dB");

        linRefLev = (LinearLayout) mRefLevList.get(0);
        tvRefLev = (AutofitTextView) mRefLevList.get(2);

        linAttenuator = (LinearLayout) mAttenuatorList.get(0);
        tvAttenuator = (AutofitTextView) mAttenuatorList.get(2);

        linPreamp = (LinearLayout) mPreampList.get(0);
        tvPreampOn = (AutofitTextView) mPreampList.get(2);
        tvPreampOff = (AutofitTextView) mPreampList.get(3);

        linScaleDiv = (LinearLayout) mScaleDivList.get(0);
        tvScaleDiv = (AutofitTextView) mScaleDivList.get(2);

        linOffset = (LinearLayout) mOffsetList.get(0);
        tvOffset = (AutofitTextView) mOffsetList.get(2);

        setUpEvents();

    }

    @Override
    public void setUpEvents() throws NullPointerException {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {


            linRefLev.setOnClickListener(v -> new RefLevKeypadDialog(mActivity, binding).show());

            linAttenuator.setOnClickListener(v -> {

                double freq = SaDataHandler.getInstance().getConfigData().getFrequencyData().getCenterFreq();

                if (freq == 10) {
                    Toast.makeText(mActivity, "Disable", Toast.LENGTH_SHORT).show();
                    return;
                }

                new AttenuatorKeypadDialog(mActivity, binding).show();
            });

            /*linAttenuator.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SaAmplitudeData data = SaDataHandler.getInstance().getConfigData().getAmplitudeData();

                    Timer timer = new Timer();

                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            final int min = 0;
                            final int max = 60;

                            int val = new Random().nextInt((max - min) + 1) + min;

                            if (val % 2 == 1) val += 1;

                            SaDataHandler.getInstance().getConfigData().getAmplitudeData().setAttenuator(val);

                            ViewHandler.getInstance().getSAAmplitudeView().update();
                            ViewHandler.getInstance().getContent().update();

                            FunctionHandler.getInstance().getDataConnector().sendCommand(
                                    DataHandler.getInstance().getConfigCmd()
                            );
                        }
                    };

                    if (data.getTestMode()) {
                        data.setTestMode(false);
                        timer.cancel();
                    }
                    else {
                        data.setTestMode(true);
                        Toast.makeText(mActivity, "Auto Test Mode On", Toast.LENGTH_SHORT).show();
                        timer.schedule(task, 3000, 1000);
                    }



                    return false;
                }
            });*/

            linPreamp.setOnClickListener(v -> {

                double freq = SaDataHandler.getInstance().getConfigData().getFrequencyData().getCenterFreq();

                if (freq == 10) {
                    Toast.makeText(mActivity, "Disable", Toast.LENGTH_SHORT).show();
                    return;
                }

                SaAmplitudeData ampData = SaDataHandler.getInstance().getConfigData().getAmplitudeData();

                if (ampData.getPreampMode() == SaAmplitudeData.PREAMP_MODE.OFF) {

                    ampData.setPreampMode(SaAmplitudeData.PREAMP_MODE.ON);

                } else {

                    ampData.setPreampMode(SaAmplitudeData.PREAMP_MODE.OFF);
                }

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
                update();

            });

            linScaleDiv.setOnClickListener(v -> new ScaleDivKeypadDialog(mActivity, binding).show());

            linOffset.setOnClickListener(v -> new OffsetKeypadDialog(mActivity, binding).show());

            binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getSAView().selectAmplitude());

        });
    }


    @SuppressLint("SetTextI18n")
    public void update() {

        initView();

        mActivity.runOnUiThread(() -> {

            tvRefLev.setText(Utils.formatNumber(FunctionHandler.getInstance().getMainLineChart().getRefLev(), 2, false) + " dBm");
            tvScaleDiv.setText(Utils.formatNumber(FunctionHandler.getInstance().getMainLineChart().getScaleDiv(), 2, false) + " dB");
            tvAttenuator.setText(SaDataHandler.getInstance().getConfigData().getAmplitudeData().getAttenuator() + " dB");
            tvOffset.setText(Utils.formatNumber(SaDataHandler.getInstance().getConfigData().getAmplitudeData().getOffset(), 2, false) + " dB");

            SaAmplitudeData.PREAMP_MODE preamp = SaDataHandler.getInstance().getConfigData().getAmplitudeData().getPreampMode();

            if (preamp == SaAmplitudeData.PREAMP_MODE.OFF) {
                selectOptionView(tvPreampOff, tvPreampOn);
                binding.tvPre.setBackgroundResource(R.drawable.fw_status_background);
                binding.tvPre.setTextColor(mActivity.getResources().getColor(R.color.norText));
            } else {
                selectOptionView(tvPreampOn, tvPreampOff);
                binding.tvPre.setBackgroundResource(R.drawable.fw_status_pre_on);
                binding.tvPre.setTextColor(mActivity.getResources().getColor(R.color.pre_color));
            }

        });


    }

}

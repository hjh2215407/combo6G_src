package com.dabinsystems.pact_app.View.ModAccuracy.LteFdd;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.LteFdd.LteFddAmpData;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.LteFdd.LteFddAttenuatorKeypadDialog;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.LteFdd.LteFddOffsetKeypadDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class LteFddAmplitudeView extends LayoutBase {

    private LinearLayout linAttenMode, linAttenuator, linPreamp, linOffset;
    private AutofitTextView tvAttenMode, tvAttenuator, tvPreamp, tvOffset, tvAttenModeAuto, tvAttenModeManual;
    private DynamicView mDynamicView;

    public LteFddAmplitudeView(MainActivity activity, ActivityMainBinding binding) {
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

                    binding.linRightMenu.addView(linAttenMode);
                    binding.linRightMenu.addView(linAttenuator);
                    binding.linRightMenu.addView(linOffset);
                    binding.linRightMenu.addView(linPreamp);

                    binding.linCableList.setVisibility(View.GONE);
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

        ArrayList<View> mAttenModeList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.atten_mode), "Auto", "Manual");
        ArrayList<View> mAttenuatorList = mDynamicView.addRightMenuButton(mActivity.getString(R.string.attenuator_name), "0 dB");
        ArrayList<View> mOffsetList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.offset), "0.00 dB");
        ArrayList<View> mPreampList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.preamp));

        linAttenMode = (LinearLayout) mAttenModeList.get(0);
        tvAttenMode = (AutofitTextView) mAttenModeList.get(1);
        tvAttenModeAuto = (AutofitTextView) mAttenModeList.get(2);
        tvAttenModeManual = (AutofitTextView) mAttenModeList.get(3);

        linAttenuator = (LinearLayout) mAttenuatorList.get(0);
        tvAttenuator = (AutofitTextView) mAttenuatorList.get(2);

        linOffset = (LinearLayout) mOffsetList.get(0);
        tvOffset = (AutofitTextView) mOffsetList.get(2);

        linPreamp = (LinearLayout) mPreampList.get(0);
        tvPreamp = (AutofitTextView) mPreampList.get(1);

        setUpEvents();
    }

    @Override
    public void setUpEvents() throws NullPointerException {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {


            linAttenMode.setOnClickListener(v -> {

                LteFddAmpData.ATT_MODE mode = DataHandler.getInstance().getLteFddData().getAmpData().getATTMode();

                if (mode == LteFddAmpData.ATT_MODE.AUTO)
                    DataHandler.getInstance().getLteFddData().getAmpData().setAttMode(LteFddAmpData.ATT_MODE.MANUAL);
                else
                    DataHandler.getInstance().getLteFddData().getAmpData().setAttMode(LteFddAmpData.ATT_MODE.AUTO);

                attModeButtonUpdate();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getLteFddData().getLteFddCmd()
                );

            });

            linAttenuator.setOnClickListener(v -> new LteFddAttenuatorKeypadDialog(mActivity, binding).show());

            linPreamp.setOnClickListener(v -> {

                LteFddAmpData.PREAMP_MODE mode = DataHandler.getInstance().getLteFddData().getAmpData().getPreampMode();

                if (mode == LteFddAmpData.PREAMP_MODE.OFF)
                    DataHandler.getInstance().getLteFddData().getAmpData().setPreampMode(LteFddAmpData.PREAMP_MODE.ON);
                else
                    DataHandler.getInstance().getLteFddData().getAmpData().setPreampMode(LteFddAmpData.PREAMP_MODE.OFF);

                preampButtonUpdate();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getLteFddData().getLteFddCmd()
                );

            });

            linOffset.setOnClickListener(v -> new LteFddOffsetKeypadDialog(mActivity, binding).show());

            binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getSAView().selectAmplitude());
        });
    }


    @SuppressLint("SetTextI18n")
    public void update() {
        mActivity.runOnUiThread(() -> {
//            tvAttenModeAuto.setText(DataHandler.getInstance().getLteFddData().getAmpData().getATTMode().getModeString());
            if (tvAttenuator != null)
                tvAttenuator.setText(DataHandler.getInstance().getLteFddData().getAmpData().getAttenuator() + " dB");

            if (tvOffset != null)
                tvOffset.setText(DataHandler.getInstance().getLteFddData().getAmpData().getOffset() + " dB");
        });

        preampButtonUpdate();
        attModeButtonUpdate();
    }

    public void preampButtonUpdate() {
        if (linPreamp == null)
            return;

        mActivity.runOnUiThread(() -> {
            try {
                LteFddAmpData.PREAMP_MODE preampMode = DataHandler.getInstance().getLteFddData().getAmpData().getPreampMode();

                if (preampMode == LteFddAmpData.PREAMP_MODE.ON) {
                    linPreamp.setSelected(true);

                    binding.tvNrPre.setBackgroundResource(R.drawable.fw_status_pre_on);
                    binding.tvNrPre.setTextColor(mActivity.getResources().getColor(R.color.pre_color));
                } else {
                    linPreamp.setSelected(false);

                    binding.tvNrPre.setBackgroundResource(R.drawable.fw_status_background);
                    binding.tvNrPre.setTextColor(mActivity.getResources().getColor(R.color.norText));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void attModeButtonUpdate() {
        mActivity.runOnUiThread(() -> {
            try {
                LteFddAmpData.ATT_MODE attMode = DataHandler.getInstance().getLteFddData().getAmpData().getATTMode();
                if (attMode == LteFddAmpData.ATT_MODE.AUTO) {
                    selectOptionView(tvAttenModeAuto, tvAttenModeManual);
                } else {
                    selectOptionView(tvAttenModeManual, tvAttenModeAuto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

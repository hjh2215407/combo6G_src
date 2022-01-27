package com.dabinsystems.pact_app.View.ModAccuracy.NR5G;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.NR5G.Nr5gAttenuatorKeypadDialog;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.NR5G.Nr5gOffsetKeypadDialog;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrAmpData;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class NrAmplitudeView extends LayoutBase {

    private LinearLayout linAttenMode, linAttenuator, linPreamp, linOffset;
    private AutofitTextView tvAttenuator, tvOffset, tvAttenModeAuto, tvAttenModeManual;
    private DynamicView mDynamicView;

    public NrAmplitudeView(MainActivity activity, ActivityMainBinding binding) {
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
        tvAttenModeAuto = (AutofitTextView) mAttenModeList.get(2);
        tvAttenModeManual = (AutofitTextView) mAttenModeList.get(3);

        linAttenuator = (LinearLayout) mAttenuatorList.get(0);
        tvAttenuator = (AutofitTextView) mAttenuatorList.get(2);

        linOffset = (LinearLayout) mOffsetList.get(0);
        tvOffset = (AutofitTextView) mOffsetList.get(2);

        linPreamp = (LinearLayout) mPreampList.get(0);

        setUpEvents();
    }

    @Override
    public void setUpEvents() throws NullPointerException {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linAttenMode.setOnClickListener(v -> {

                NrAmpData.ATT_MODE mode = DataHandler.getInstance().getNrData().getAmpData().getATTMode();

                if (mode == NrAmpData.ATT_MODE.AUTO)
                    DataHandler.getInstance().getNrData().getAmpData().setAttMode(NrAmpData.ATT_MODE.MANUAL);
                else
                    DataHandler.getInstance().getNrData().getAmpData().setAttMode(NrAmpData.ATT_MODE.AUTO);

                attModeButtonUpdate();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

            });

            linAttenuator.setOnClickListener(v -> new Nr5gAttenuatorKeypadDialog(mActivity, binding).show());

            linPreamp.setOnClickListener(v -> {

                NrAmpData.PREAMP_MODE mode = DataHandler.getInstance().getNrData().getAmpData().getPreampMode();

                if (mode == NrAmpData.PREAMP_MODE.OFF)
                    DataHandler.getInstance().getNrData().getAmpData().setPreampMode(NrAmpData.PREAMP_MODE.ON);
                else
                    DataHandler.getInstance().getNrData().getAmpData().setPreampMode(NrAmpData.PREAMP_MODE.OFF);

                preampButtonUpdate();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

            });

            linOffset.setOnClickListener(v -> new Nr5gOffsetKeypadDialog(mActivity, binding).show());

            binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getSAView().selectAmplitude());
        });
    }


    @SuppressLint("SetTextI18n")
    public void update() {
        mActivity.runOnUiThread(() -> {
//            tvAttenModeAuto.setText(DataHandler.getInstance().getNrData().getAmpData().getATTMode().getModeString());
            if (tvAttenuator != null)
                tvAttenuator.setText(DataHandler.getInstance().getNrData().getAmpData().getAttenuator() + " dB");

            if (tvOffset != null)
                tvOffset.setText(DataHandler.getInstance().getNrData().getAmpData().getOffset() + " dB");
        });

        preampButtonUpdate();
        attModeButtonUpdate();
    }

    public void preampButtonUpdate() {
        mActivity.runOnUiThread(() -> {
            try {
                NrAmpData.PREAMP_MODE preampMode = DataHandler.getInstance().getNrData().getAmpData().getPreampMode();

                if (preampMode == NrAmpData.PREAMP_MODE.ON) {
                    if (linPreamp != null)
                        linPreamp.setSelected(true);

                    binding.tvNrPre.setBackground(mActivity.getResources().getDrawable(R.drawable.fw_status_pre_on));
                    binding.tvNrPre.setTextColor(mActivity.getResources().getColor(R.color.pre_color));
                } else {
                    if (linPreamp != null)
                        linPreamp.setSelected(false);

                    binding.tvNrPre.setBackground(mActivity.getResources().getDrawable(R.drawable.fw_status_background));
                    binding.tvNrPre.setTextColor(mActivity.getResources().getColor(R.color.norText));
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
    }

    public void attModeButtonUpdate() {
        mActivity.runOnUiThread(() -> {
            NrAmpData.ATT_MODE attMode = DataHandler.getInstance().getNrData().getAmpData().getATTMode();
            if (attMode == NrAmpData.ATT_MODE.AUTO) {
                selectOptionView(tvAttenModeAuto, tvAttenModeManual);
            } else {
                selectOptionView(tvAttenModeManual, tvAttenModeAuto);
            }
        });
    }
}

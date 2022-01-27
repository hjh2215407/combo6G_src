package com.dabinsystems.pact_app.View.ModAccuracy.NR5G;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Dialog.SA.GscnKeypadDialog;
import com.dabinsystems.pact_app.Dialog.SA.KssbKeypadDialog;
import com.dabinsystems.pact_app.Dialog.SA.RbOffsetKeypadDialog;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrSSBInfoData;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class NrSSBInfoView extends LayoutBase {

    private LinearLayout linSSBLocation, linConfig, linRBOffset, linKSSB, linGscn;
    private AutofitTextView tvSSBLocation, tvConfig, tvRBOffsetVal, tvKssbVal, tvGscnVal;
    private AutofitTextView tvSSBOffsetVal, tvSSBGscnVal, tvConfigAuto, tvConfigManual;
    private Spinner spSSBLocation, spConfig;
    private DynamicView mDynamicView;

    public NrSSBInfoView(MainActivity activity, ActivityMainBinding binding) {
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
                    binding.linRightMenu.removeAllViews();
//                    binding.linRightMenu.addView(linSSBLocation);
                    binding.linRightMenu.addView(linConfig);
                    binding.linRightMenu.addView(linRBOffset);
                    binding.linRightMenu.addView(linKSSB);

//                    SSBInfoFunc.SSB_LOCATION location = FunctionHandler.getInstance().getSSBInfoFunc().getSsbLocationMode();

//                    if (location == SSBInfoFunc.SSB_LOCATION.OFFSET) {
//
//                        binding.linRightMenu.addView(linRBOffset);
//                        binding.linRightMenu.addView(linKSSB);
//
//                    } else {
//
//                        binding.linRightMenu.addView(linGscn);
//
//                    }


                    binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.ssb_info));
                    binding.tvBack.setOnClickListener(v->{
                        ViewHandler.getInstance().getNrMeasSetupView().addMenu();
                    });

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

        ArrayList<View> mSSBLocationList = mDynamicView
                .addRightMenuButton(
                        mActivity.getResources().getString(R.string.ssb_locationn),
                        "Offset", "GSCN");

        ArrayList<View> mConfigList = mDynamicView
                .addRightMenuButton(
                        mActivity.getResources().getString(R.string.config),
                        "Auto", "Manual");


        ArrayList<View> mRbOffsetList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.rb_offset), "");
        ArrayList<View> mKssbList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.k_ssb), "");
        ArrayList<View> mGscnList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.gscn), "");


        linSSBLocation = (LinearLayout) mSSBLocationList.get(0);
        tvSSBLocation = (AutofitTextView) mSSBLocationList.get(1);
//        spSSBLocation = (Spinner) mSSBLocationList.get(2);
        tvSSBOffsetVal = (AutofitTextView) mSSBLocationList.get(2);
        tvSSBGscnVal = (AutofitTextView) mSSBLocationList.get(3);

        linConfig = (LinearLayout) mConfigList.get(0);
        tvConfig = (AutofitTextView) mConfigList.get(1);
        tvConfigAuto = (AutofitTextView) mConfigList.get(2);
        tvConfigManual = (AutofitTextView) mConfigList.get(3);
//        spConfig = (Spinner) mConfigList.get(2);
//        spConfig.setSelection(1);

        linRBOffset = (LinearLayout) mRbOffsetList.get(0);
        tvRBOffsetVal = (AutofitTextView) mRbOffsetList.get(2);

        linKSSB = (LinearLayout) mKssbList.get(0);
        tvKssbVal = (AutofitTextView) mKssbList.get(2);

        linGscn = (LinearLayout) mGscnList.get(0);
        tvGscnVal = (AutofitTextView) mGscnList.get(2);

        setUpEvents();

    }

    @Override
    public void setUpEvents() throws NullPointerException {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {


            linSSBLocation.setOnClickListener(v -> {

                NrSSBInfoData.SSB_LOCATION mode = DataHandler.getInstance().getNrData().getSsbInfoData().getSsbLocationMode();

                if (mode == NrSSBInfoData.SSB_LOCATION.OFFSET) {

                    DataHandler.getInstance().getNrData().getSsbInfoData().setSsbLocationMode(NrSSBInfoData.SSB_LOCATION.GSCN);

                } else {
                    DataHandler.getInstance().getNrData().getSsbInfoData().setSsbLocationMode(NrSSBInfoData.SSB_LOCATION.OFFSET);

                }

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                updateSsbLocationView();
                addMenu();

            });

//            spSSBLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    InitActivity.logMsg("Spinner", ((AutofitTextView)adapterView.getSelectedView()).getText().toString() + " " + ((AutofitTextView)view).getText().toString() + " " + i);
//
//                    if(i == 0) DataHandler.getInstance().getNrData().getSsbInfoData().setSsbLocationMode(SSBInfoFunc.SSB_LOCATION.OFFSET);
//                    else if(i == 1) DataHandler.getInstance().getNrData().getSsbInfoData().setSsbLocationMode(SSBInfoFunc.SSB_LOCATION.GSCN);
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });

            linConfig.setOnClickListener(v -> {

                NrSSBInfoData.CONFIG_MODE mode = DataHandler.getInstance().getNrData().getSsbInfoData().getConfigMode();

                if (mode == NrSSBInfoData.CONFIG_MODE.AUTO) {

                    DataHandler.getInstance().getNrData().getSsbInfoData().setConfigMode(NrSSBInfoData.CONFIG_MODE.MANUAL);

                } else {
                    DataHandler.getInstance().getNrData().getSsbInfoData().setConfigMode(NrSSBInfoData.CONFIG_MODE.AUTO);

                }

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                updateConfigView();

            });

//            spConfig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    if(i == 0) DataHandler.getInstance().getNrData().getSsbInfoData().setConfigMode(SSBInfoFunc.CONFIG_MODE.MANUAL);
//                    else if(i == 1) DataHandler.getInstance().getNrData().getSsbInfoData().setConfigMode(SSBInfoFunc.CONFIG_MODE.AUTO);
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });

            linRBOffset.setOnClickListener(v -> {

                new RbOffsetKeypadDialog(mActivity, binding).show();

            });

            linKSSB.setOnClickListener(v -> {

                new KssbKeypadDialog(mActivity, binding).show();

            });

            linGscn.setOnClickListener(v -> {

                new GscnKeypadDialog(mActivity, binding).show();

            });

        });
    }


    @SuppressLint("SetTextI18n")
    public void update() {

        updateSsbLocationView();
        updateConfigView();
        updateRbOffsetView();
        updateKssbView();
        updateGscnView();


    }

    public void updateSsbLocationView() {

        mActivity.runOnUiThread(() -> {

            NrSSBInfoData.SSB_LOCATION locationMode = DataHandler.getInstance().getNrData().getSsbInfoData().getSsbLocationMode();

            if (locationMode == NrSSBInfoData.SSB_LOCATION.OFFSET) {

                selectOptionView(tvSSBOffsetVal, tvSSBGscnVal);

            } else {

                selectOptionView(tvSSBGscnVal, tvSSBOffsetVal);
            }

        });

    }

    public void updateConfigView() {

        mActivity.runOnUiThread(() -> {

            NrSSBInfoData.CONFIG_MODE configMode = DataHandler.getInstance().getNrData().getSsbInfoData().getConfigMode();

            if (configMode == NrSSBInfoData.CONFIG_MODE.AUTO) {

                selectOptionView(tvConfigAuto, tvConfigManual);

            } else {

                selectOptionView(tvConfigManual, tvConfigAuto);
            }

        });

    }

    @SuppressLint("SetTextI18n")
    public void updateRbOffsetView() {

        mActivity.runOnUiThread(() -> {

            tvRBOffsetVal.setText(DataHandler.getInstance().getNrData().getSsbInfoData().getRbOffset() + "");

        });

    }

    @SuppressLint("SetTextI18n")
    public void updateKssbView() {

        mActivity.runOnUiThread(() -> {

            tvKssbVal.setText(DataHandler.getInstance().getNrData().getSsbInfoData().getKssb() + "");

        });

    }

    @SuppressLint("SetTextI18n")
    public void updateGscnView() {

        mActivity.runOnUiThread(() -> {

            tvGscnVal.setText(DataHandler.getInstance().getNrData().getSsbInfoData().getGscn() + "");

        });

    }

}

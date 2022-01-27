package com.dabinsystems.pact_app.View.ModAccuracy.NR5G.tae;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrInterTaeData;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.NR5G.Nr5gFreqOfCarrierKeypad;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;

import me.grantland.widget.AutofitTextView;

/**
 * right menu 5G NR -> Setup -> TAE -> Inter-TAE
 * - Select Carrier
 * - Carrier
 * - Freq Of Carrier
 * - Profile
 */
public class NrInterTaeView extends LayoutBase {

    private LinearLayout linNumOfCarrier, linFreqOfCarrier1, linFreqOfCarrier2, linFreqOfCarrier3, linFreqOfCarrier4, linSelectCarrier, linCarrier, linFreqOfCarrier, linProfile;
    private AutofitTextView tvNumOfCarrier, tvFreqOfCarrier1, tvFreqOfCarrier2, tvFreqOfCarrier3, tvFreqOfCarrier4, tvSelectCarrier, tvCarrier, tvFreqOfCarrierValue, tvCarrierOn, tvCarrierOff, tvFreqOfCarrier, tvProfile, tvProfileValue;
    private DynamicView mDynamicView;
    private LinearLayout[] linFreqOfCarrierArray;
    private AutofitTextView[] tvFreqOfCarrierArray;

    public NrInterTaeView(MainActivity activity, ActivityMainBinding binding) {
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
                    binding.linRightMenu.addView(linSelectCarrier);
                    binding.linRightMenu.addView(linCarrier);
                    binding.linRightMenu.addView(linFreqOfCarrier);
                    binding.linRightMenu.addView(linProfile);

//                    binding.linRightMenu.addView(linNumOfCarrier);
//                    binding.linRightMenu.addView(linFreqOfCarrier1);
//                    binding.linRightMenu.addView(linFreqOfCarrier2);
//                    binding.linRightMenu.addView(linFreqOfCarrier3);
//                    binding.linRightMenu.addView(linFreqOfCarrier4);

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


                    binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.tae));
                    binding.tvBack.setOnClickListener(v -> {
                        ViewHandler.getInstance().getTaeView().addMenu();
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

        //ArrayList<String> subMenuList = new ArrayList<>(Arrays.asList("1", "2", "3", "4"));

        ArrayList<View> selectedCarrierList = mDynamicView.addRightMenuButton("Select Carrier", "");

        ArrayList<View> carrierList = mDynamicView.addRightMenuButton("Carrier", "On", "Off");
        ArrayList<View> freqOfCarrierList = mDynamicView.addRightMenuButton("Freq Of Carrier", "--");
        ArrayList<View> profileList = mDynamicView.addRightMenuButton("Profile", "");

        ArrayList<View> freqOfCarrierList1 = mDynamicView.addRightMenuButton("Freq Of Carrier1", "--");
        ArrayList<View> freqOfCarrierList2 = mDynamicView.addRightMenuButton("Freq Of Carrier2", "--");
        ArrayList<View> freqOfCarrierList3 = mDynamicView.addRightMenuButton("Freq Of Carrier3", "--");
        ArrayList<View> freqOfCarrierList4 = mDynamicView.addRightMenuButton("Freq of Carrier4", "--");

        linSelectCarrier = (LinearLayout) selectedCarrierList.get(0);
        tvSelectCarrier = (AutofitTextView) selectedCarrierList.get(2);

        linCarrier = (LinearLayout) carrierList.get(0);
        tvCarrier = (AutofitTextView) carrierList.get(1);
        tvCarrierOn = (AutofitTextView) carrierList.get(2);
        tvCarrierOff = (AutofitTextView) carrierList.get(3);

        linFreqOfCarrier = (LinearLayout) freqOfCarrierList.get(0);
        tvFreqOfCarrier = (AutofitTextView) freqOfCarrierList.get(1);
        tvFreqOfCarrierValue = (AutofitTextView) freqOfCarrierList.get(2);

        linProfile = (LinearLayout) profileList.get(0);
        tvProfile = (AutofitTextView) profileList.get(1);
        tvProfileValue = (AutofitTextView) profileList.get(2);


//        linNumOfCarrier = (LinearLayout) selectedCarrierList.get(0);
//        tvNumOfCarrier = (AutofitTextView) selectedCarrierList.get(2);

        linFreqOfCarrier1 = (LinearLayout) freqOfCarrierList1.get(0);
        tvFreqOfCarrier1 = (AutofitTextView) freqOfCarrierList1.get(2);

        linFreqOfCarrier2 = (LinearLayout) freqOfCarrierList2.get(0);
        tvFreqOfCarrier2 = (AutofitTextView) freqOfCarrierList2.get(2);

        linFreqOfCarrier3 = (LinearLayout) freqOfCarrierList3.get(0);
        tvFreqOfCarrier3 = (AutofitTextView) freqOfCarrierList3.get(2);

        linFreqOfCarrier4 = (LinearLayout) freqOfCarrierList4.get(0);
        tvFreqOfCarrier4 = (AutofitTextView) freqOfCarrierList4.get(2);

        linFreqOfCarrierArray = new LinearLayout[]{linFreqOfCarrier1, linFreqOfCarrier2, linFreqOfCarrier3, linFreqOfCarrier4};
        tvFreqOfCarrierArray = new AutofitTextView[]{tvFreqOfCarrier1, tvFreqOfCarrier2, tvFreqOfCarrier3, tvFreqOfCarrier4};

        setUpEvents();

    }

    @Override
    public void setUpEvents() throws NullPointerException {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            // new...
            linSelectCarrier.setOnClickListener(v -> {

                ViewHandler.getInstance().getSelectCarrierView().addMenu();

            });

            linCarrier.setOnClickListener(v -> {

                NrInterTaeData interData = DataHandler.getInstance().getNrData().getInterTaeData();
                if (interData.getCarrierSetting() == NrInterTaeData.CARRIER_SETTING.ON) {
                    interData.setCarrierSetting(NrInterTaeData.CARRIER_SETTING.OFF);
                } else {
                    interData.setCarrierSetting(NrInterTaeData.CARRIER_SETTING.ON);
                }

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

                update();

            });

            linFreqOfCarrier.setOnClickListener(v -> {

                NrInterTaeData interData = DataHandler.getInstance().getNrData().getInterTaeData();
                int selectedIdx = interData.getSelectedCarrierIdx();

                new Nr5gFreqOfCarrierKeypad(mActivity, binding, selectedIdx).show();

            });

            linProfile.setOnClickListener(v -> {

                ViewHandler.getInstance().getTaeProfileView1().addMenu();

            });


            // old.....

//            linNumOfCarrier.setOnClickListener(v -> {
//                ViewHandler.getInstance().getNrNumOfCarrierView().addMenu();
//            });


            linFreqOfCarrier1.setOnClickListener(v -> {

                new Nr5gFreqOfCarrierKeypad(mActivity, binding, 0).show();

            });


            linFreqOfCarrier2.setOnClickListener(v -> {

                new Nr5gFreqOfCarrierKeypad(mActivity, binding, 1).show();

            });


            linFreqOfCarrier3.setOnClickListener(v -> {

                new Nr5gFreqOfCarrierKeypad(mActivity, binding, 2).show();


            });

            linFreqOfCarrier4.setOnClickListener(v -> {

                new Nr5gFreqOfCarrierKeypad(mActivity, binding, 3).show();

            });

        });
    }


    @SuppressLint("SetTextI18n")
    public void update() {

        initView();

        mActivity.runOnUiThread(() -> {

//            int numOfCarrier = DataHandler.getInstance().getNrData().getInterTaeData().getNumOfCarrier();
//            int maxNumOfCarrier = DataHandler.getInstance().getNrData().getInterTaeData().MAX_NUM_OF_CARRIER;
//
//            tvNumOfCarrier.setText(numOfCarrier + "");
//
//            for(int i=0; i<maxNumOfCarrier; i++) {
//
//                if(i < numOfCarrier) {
//                    linFreqOfCarrierArray[i].setEnabled(true);
//                    tvFreqOfCarrierArray[i].setText(DataHandler.getInstance().getNrData().getInterTaeData().getFreqOfCarrier(i) + " MHz");
//
//                } else {
//
//                    linFreqOfCarrierArray[i].setEnabled(false);
//                    tvFreqOfCarrierArray[i].setText("--");
//
//                }
//
//            }

            NrInterTaeData interData = DataHandler.getInstance().getNrData().getInterTaeData();
            int selectedIdx = interData.getSelectedCarrierIdx();

            InitActivity.logMsg("selectedIdx", selectedIdx + "");

            float cableFrequency = interData.getFreqOfCarrier();
            NrInterTaeData.PROFILE profile = interData.getProfile();

            String company = "SKT";

            switch (selectedIdx) {
                case 0:
                    company = "(SKT)";
                    break;
                case 1:
                    company = "(KT)";
                    break;
                case 2:
                    company = "(LG U+)";
                    break;
                default:
                    company = "";
                    break;
            }

            tvCarrier.setText("Carrier " + (selectedIdx + 1) + company);
            tvFreqOfCarrierValue.setText(cableFrequency + " MHz");
            tvProfileValue.setText(profile.getString());
            tvSelectCarrier.setText((selectedIdx + 1) + "");


            NrInterTaeData.CARRIER_SETTING setting = interData.getCarrierSetting(selectedIdx);

            if (setting == NrInterTaeData.CARRIER_SETTING.ON) {
                selectOptionView(tvCarrierOn, tvCarrierOff);
            } else if (setting == NrInterTaeData.CARRIER_SETTING.OFF) {
                selectOptionView(tvCarrierOff, tvCarrierOn);
            }

            // [jigum] 2021-07-16 Carrier 1의 On/Off 는 항상 On으로 설정하고 사용자가 설정할 수 없도록 함
            linCarrier.setEnabled(selectedIdx != 0);
        });
    }

}

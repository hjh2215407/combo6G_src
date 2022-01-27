package com.dabinsystems.pact_app.View.ModAccuracy.NR5GScan;

import android.annotation.SuppressLint;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrDuplexData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrSCSData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanCarrierData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanRcvData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanRcvItem;
import com.dabinsystems.pact_app.Data.SA.SaGateData;
import com.dabinsystems.pact_app.Dialog.NumberKeypadDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

/**
 * [jigum] 2021-07-23
 * <p>
 * right menu 5G NR Scan -> Setup -> Carriers
 * - Select Carrier
 * - Carrier 1 / ...
 * - Freq.Of Carrier
 * - Profile
 */

/**
 * [jeun] 2021-12-31
 * right menu 5G NR Scan - Setup - Carriers
 * Select Carrier
 * Carrier n / ()
 * Duplex
 * Freq. Of Carrier
 * Profile
 * SCS
 * */

public class NrScanCarrierView extends LayoutBase {

    LinearLayout linSelect, linCarrier, linFreq, linProfile, linDuplex, linSCS;
    AutofitTextView tvSelect, tvCarrier, tvCarrierOn, tvCarrierOff, tvFreq, tvProfile;

    AutofitTextView tvDuplex, tvDuplexTDD, tvDuplexFDD;
    AutofitTextView tvSCS, tvSCS1, tvSCS2;

    boolean isInitView = false;

    public NrScanCarrierView(MainActivity activity, ActivityMainBinding binding) {
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
                    binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.carriers));
                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(linSelect);
                    binding.linRightMenu.addView(linCarrier);

                    //@@ [21.12.31] NR SCAN Duplex, SCS ADD
                    binding.linRightMenu.addView(linDuplex);
                    //@@

                    binding.linRightMenu.addView(linFreq);
                    binding.linRightMenu.addView(linProfile);

                    //@@ [21.12.31] NR SCAN Duplex, SCS ADD
                    binding.linRightMenu.addView(linSCS);
                    //@@

                    binding.tvBack.setOnClickListener(v -> {
                        ViewHandler.getInstance().getNrScanMeasSetupView().addMenu();
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
                NrScanCarrierData data = DataHandler.getInstance().getNrScanData().getCarrierData();
                NrScanRcvData rcvData = DataHandler.getInstance().getNrScanData().getRcvData();
                int selIdx = data.getSelectedCarrierIdx();

                String strNum = "", strCarrier = "";
                switch (selIdx) {
                    case 0:
                        strNum = "1(Ref)";
                        strCarrier = "Carrier 1 (" + rcvData.getName(selIdx) + ")";
                        break;
                    case 1:
                        strNum = "2";
                        strCarrier = "Carrier 2 (" + rcvData.getName(selIdx) + ")";
                        break;
                    case 2:
                        strNum = "3";
                        strCarrier = "Carrier 3 (" + rcvData.getName(selIdx) + ")";
                        break;
                    case 3:
                        strNum = "4";
                        strCarrier = "Carrier 4";
                        break;
                }

                tvSelect.setText(strNum);
                tvCarrier.setText(strCarrier);
                if (data.isOn(selIdx))
                    selectOptionView(tvCarrierOn, tvCarrierOff);
                else
                    selectOptionView(tvCarrierOff, tvCarrierOn);
                tvFreq.setText(data.getFreqToMHzString(selIdx, true));
                tvProfile.setText(data.getProfileToMHzString(selIdx));


//                // Carrier 1 은 변경 안 되도록 함.
//                linCarrier.setEnabled(selIdx != 0);
//                enabledView(tvCarrierOn, selIdx != 0);
//                enabledView(tvCarrierOff, selIdx != 0);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

        updateDuplexView();
        updateScsView();
    }

    @Override
    public void initView() {
        super.initView();

        if (isInitView)
            return;
        isInitView = true;

        DynamicView mDynamicView = new DynamicView(mActivity);

        ArrayList<View> listView = mDynamicView.addRightMenuButton("Select Carrier", "");
        linSelect = (LinearLayout) listView.get(0);
        tvSelect = (AutofitTextView) listView.get(2);

        listView = mDynamicView.addRightMenuButton("Carrier", "On", "Off");
        linCarrier = (LinearLayout) listView.get(0);
        tvCarrier = (AutofitTextView) listView.get(1);
        tvCarrierOn = (AutofitTextView) listView.get(2);
        tvCarrierOff = (AutofitTextView) listView.get(3);

        //@@ [21.12.31] NR SCAN Duplex ADD
        listView = mDynamicView.addRightMenuButton("Duplex", "TDD", "FDD");
        linDuplex = (LinearLayout) listView.get(0);
        tvDuplex = (AutofitTextView) listView.get(1);
        tvDuplexTDD = (AutofitTextView) listView.get(2);
        tvDuplexFDD = (AutofitTextView) listView.get(3);
        //@@

        listView = mDynamicView.addRightMenuButton("Freq. Of Carrier", "");
        linFreq = (LinearLayout) listView.get(0);
        tvFreq = (AutofitTextView) listView.get(2);

        listView = mDynamicView.addRightMenuButton("Profile", "");
        linProfile = (LinearLayout) listView.get(0);
        tvProfile = (AutofitTextView) listView.get(2);

        //@@ [22.01.03] NR SCAN SCS ADD
        listView = mDynamicView.addRightMenuButton("SCS", "30 kHz", "15 kHz");
        linSCS = (LinearLayout) listView.get(0);
        tvSCS = (AutofitTextView) listView.get(1);
        tvSCS1 = (AutofitTextView) listView.get(2);
        tvSCS2 = (AutofitTextView) listView.get(3);
        //@@

        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();
        mActivity.runOnUiThread(() -> {

            linSelect.setOnClickListener(v -> {
                ViewHandler.getInstance().getNrScanSelCarrierView().addMenu();
            });

            linCarrier.setOnClickListener(v -> {
                NrScanCarrierData data = DataHandler.getInstance().getNrScanData().getCarrierData();
                int selIdx = data.getSelectedCarrierIdx();

                data.setOn(selIdx, !data.isOn(selIdx));

                if (data.isOn(selIdx))
                    selectOptionView(tvCarrierOn, tvCarrierOff);
                else
                    selectOptionView(tvCarrierOff, tvCarrierOn);
            });

            linDuplex.setOnClickListener(v -> {
                //NrDuplexData.DUPLEX_TYPE duplexType = DataHandler.getInstance().getNrData().getDuplexData().getDuplexType();
                NrScanCarrierData data = DataHandler.getInstance().getNrScanData().getCarrierData();

                if (data.getDuplexData().getDuplexType() == NrDuplexData.DUPLEX_TYPE.TDD) {

                    //DataHandler.getInstance().getNrData().getDuplexData().setDuplexType(NrDuplexData.DUPLEX_TYPE.FDD);
                    data.getDuplexData().setDuplexType(NrDuplexData.DUPLEX_TYPE.FDD);
                }
                else {

                    //DataHandler.getInstance().getNrData().getDuplexData().setDuplexType(NrDuplexData.DUPLEX_TYPE.TDD);
                    data.getDuplexData().setDuplexType(NrDuplexData.DUPLEX_TYPE.TDD);
                }

                /*FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );*/

                updateDuplexView();

            });

            linFreq.setOnClickListener(v -> {
                // 입력 창
                NumberKeypadDialog dlg = new NumberKeypadDialog(mActivity, binding);
                //dlg.isViewDot = false;
                dlg.isViewPlusMinus = false;
                dlg.x1000000 = "MHz";
                dlg.x1000 = "KHz";
                dlg.x1 = "Hz";

                dlg.setValueEnterListener(val -> {
                    long nVal = (long) val;

                    NrScanCarrierData data = DataHandler.getInstance().getNrScanData().getCarrierData();

                    long min = data.getFreqOfCarrierMin();
                    long max = data.getFreqOfCarrierMax();

                    if (nVal < min || nVal > max) {
                        Toast.makeText(MainActivity.getContext(), "Out of range(" + data.getFreqOfCarrierMinMaxToString() + ")", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        int selIdx = data.getSelectedCarrierIdx();
                        data.setFreqOfCarrier(selIdx, nVal);
                        tvFreq.setText(data.getFreqToMHzString(selIdx, true));

                        FunctionHandler.getInstance().getNrScanFunc().updateFreq();
                    }
                });
                dlg.show();

            });

            linProfile.setOnClickListener(v -> {
                ViewHandler.getInstance().getNrScanSelProfileView().addMenu();
            });

            linSCS.setOnClickListener(v -> {

                //NrSCSData.SCS scs = DataHandler.getInstance().getNrData().getScsData().getSCS();
                NrScanCarrierData data = DataHandler.getInstance().getNrScanData().getCarrierData();

                if (data.getScsData().getSCS() == NrSCSData.SCS.KHZ30) {

                    //DataHandler.getInstance().getNrData().getScsData().setSCS(NrSCSData.SCS.KHZ15);
                    data.getScsData().setSCS(NrSCSData.SCS.KHZ15);
                }
                else {

                    //DataHandler.getInstance().getNrData().getScsData().setSCS(NrSCSData.SCS.KHZ30);
                    data.getScsData().setSCS(NrSCSData.SCS.KHZ30);
                }

                /*FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );*/

                updateScsView();
            });

        });
    }

    public void updateDuplexView() {

        mActivity.runOnUiThread(() -> {

            //NrDuplexData.DUPLEX_TYPE duplexType = DataHandler.getInstance().getNrData().getDuplexData().getDuplexType();
            NrScanCarrierData data = DataHandler.getInstance().getNrScanData().getCarrierData();

            if (data.getDuplexData().getDuplexType() == NrDuplexData.DUPLEX_TYPE.TDD) {

                selectOptionView(tvDuplexTDD, tvDuplexFDD);

            }
            else {

                selectOptionView(tvDuplexFDD, tvDuplexTDD);

            }
        });
    }

    public void updateScsView() {

        mActivity.runOnUiThread(() -> {

            //NrSCSData.SCS scs = DataHandler.getInstance().getNrData().getScsData().getSCS();
            NrScanCarrierData data = DataHandler.getInstance().getNrScanData().getCarrierData();

            if (data.getScsData().getSCS() == NrSCSData.SCS.KHZ30) {

                selectOptionView(tvSCS1, tvSCS2);

            }
            else {

                selectOptionView(tvSCS2, tvSCS1);

            }
        });
    }
}

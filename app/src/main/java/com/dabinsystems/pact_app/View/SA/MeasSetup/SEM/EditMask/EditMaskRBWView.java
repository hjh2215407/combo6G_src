package com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.EditMask;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.BWData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemMeasSetupData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class EditMaskRBWView extends LayoutBase {

    private LinearLayout lin1kHz, lin3kHz, lin10kHz, lin30kHz, lin100kHz, lin300kHz, lin1MHz, lin3MHz;
    private AutofitTextView tvRBW, tvVBW, tvVBWSubMenu, tvRBWSubMenu;
    private ArrayList<View> mRBWList, mVBWList;
    private DynamicView mDynamicView;


    public EditMaskRBWView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);

    }


    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(lin1kHz);
                binding.linRightMenu.addView(lin3kHz);
                binding.linRightMenu.addView(lin10kHz);
                binding.linRightMenu.addView(lin30kHz);
                binding.linRightMenu.addView(lin100kHz);
                binding.linRightMenu.addView(lin300kHz);
                binding.linRightMenu.addView(lin1MHz);
                binding.linRightMenu.addView(lin3MHz);

                binding.tvBack.setOnClickListener(v -> {

                    ViewHandler.getInstance().getEditMaskView().addMenu();

                });

            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        String[] nameArr = {"1 kHz", "3 kHz", "10 kHz", "30 kHz", "100 kHz", "300 kHz", "1 MHz", "3 MHz"};
        ArrayList<ArrayList<View>> layoutList = mDynamicView.getMultipleLayout(nameArr);

        lin1kHz = (LinearLayout)layoutList.get(0).get(0);
        lin3kHz = (LinearLayout)layoutList.get(1).get(0);
        lin10kHz = (LinearLayout)layoutList.get(2).get(0);
        lin30kHz = (LinearLayout)layoutList.get(3).get(0);
        lin100kHz = (LinearLayout)layoutList.get(4).get(0);
        lin300kHz = (LinearLayout)layoutList.get(5).get(0);
        lin1MHz = (LinearLayout)layoutList.get(6).get(0);
        lin3MHz = (LinearLayout)layoutList.get(7).get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()->{

            SemMeasSetupData semData = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData();

            int selectedMask = semData.getEditMaskData().getMaskIndex();

            lin1kHz.setOnClickListener(v->{
                //org
                /*SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.KHZ1);*/
                //org

                //@@ [21.12.29] SEM RBW Fix
                semData.getEditMaskData().getBwData(selectedMask).setRBW(BWData.BAND_WIDTH.KHZ1);
                //@@


                ViewHandler.getInstance().getContent().update();
                //org
                /*ViewHandler.getInstance().getBW().addMenu();*/
                //org

                //@@ [21.12.29] SEM Edit Mask View Fix
                ViewHandler.getInstance().getEditMaskView().addMenu();
                //
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin3kHz.setOnClickListener(v->{
                //org
                /*SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.KHZ3);*/
                //org

                //@@ [21.12.29] SEM RBW Fix
                semData.getEditMaskData().getBwData(selectedMask).setRBW(BWData.BAND_WIDTH.KHZ3);
                //@@

                ViewHandler.getInstance().getContent().update();
                //org
                /*ViewHandler.getInstance().getBW().addMenu();*/
                //org

                //@@ [21.12.29] SEM Edit Mask View Fix
                ViewHandler.getInstance().getEditMaskView().addMenu();
                //

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin10kHz.setOnClickListener(v-> {
                //org
                /*SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.KHZ10);*/
                //

                //@@ [21.12.29] SEM RBW Fix
                semData.getEditMaskData().getBwData(selectedMask).setRBW(BWData.BAND_WIDTH.KHZ10);
                //@@

                ViewHandler.getInstance().getContent().update();
                //org
                /*ViewHandler.getInstance().getBW().addMenu();*/
                //org

                //@@ [21.12.29] SEM Edit Mask View Fix
                ViewHandler.getInstance().getEditMaskView().addMenu();
                //

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin30kHz.setOnClickListener(v-> {
                //org
                /*SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.KHZ30);*/
                //org

                //@@ [21.12.29] SEM RBW Fix
                semData.getEditMaskData().getBwData(selectedMask).setRBW(BWData.BAND_WIDTH.KHZ30);
                //@@

                ViewHandler.getInstance().getContent().update();
                //org
                /*ViewHandler.getInstance().getBW().addMenu();*/
                //org

                //@@ [21.12.29] SEM Edit Mask View Fix
                ViewHandler.getInstance().getEditMaskView().addMenu();
                //

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin100kHz.setOnClickListener(v-> {
                //org
                /*SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.KHZ100);*/
                //org

                //@@ [21.12.29] SEM RBW Fix
                semData.getEditMaskData().getBwData(selectedMask).setRBW(BWData.BAND_WIDTH.KHZ100);
                //@@

                ViewHandler.getInstance().getContent().update();
                //org
                /*ViewHandler.getInstance().getBW().addMenu();*/
                //org

                //@@ [21.12.29] SEM Edit Mask View Fix
                ViewHandler.getInstance().getEditMaskView().addMenu();
                //

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin300kHz.setOnClickListener(v->{
                //org
                /*SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.KHZ300);*/
                //org

                //@@ [21.12.29] SEM RBW Fix
                semData.getEditMaskData().getBwData(selectedMask).setRBW(BWData.BAND_WIDTH.KHZ300);
                //@@

                ViewHandler.getInstance().getContent().update();
                //org
                /*ViewHandler.getInstance().getBW().addMenu();*/
                //org

                //@@ [21.12.29] SEM Edit Mask View Fix
                ViewHandler.getInstance().getEditMaskView().addMenu();
                //

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin1MHz.setOnClickListener(v->{
                //org
                /*SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.MHZ1);*/
                //org

                //@@ [21.12.29] SEM RBW Fix
                semData.getEditMaskData().getBwData(selectedMask).setRBW(BWData.BAND_WIDTH.MHZ1);
                //@@

                ViewHandler.getInstance().getContent().update();
                //org
                /*ViewHandler.getInstance().getBW().addMenu();*/
                //org

                //@@ [21.12.29] SEM Edit Mask View Fix
                ViewHandler.getInstance().getEditMaskView().addMenu();
                //

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            lin3MHz.setOnClickListener(v->{
                //org
                /*SaDataHandler.getInstance().getConfigData().getBwData().setRBW(BWData.BAND_WIDTH.MHZ3);*/
                //org

                //@@ [21.12.29] SEM RBW Fix
                semData.getEditMaskData().getBwData(selectedMask).setRBW(BWData.BAND_WIDTH.MHZ3);
                //@@

                ViewHandler.getInstance().getContent().update();
                //org
                /*ViewHandler.getInstance().getBW().addMenu();*/
                //org

                //@@ [21.12.29] SEM Edit Mask View Fix
                ViewHandler.getInstance().getEditMaskView().addMenu();
                //

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );

            });

            binding.tvBack.setOnClickListener(v->{
                //org
                /*ViewHandler.getInstance().getBW().addMenu();*/
                //org

                //@@ [21.12.29] SEM Edit Mask View Fix
                ViewHandler.getInstance().getEditMaskView().addMenu();
                //

            });

        });

    }

}

package com.dabinsystems.pact_app.View.SA;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class SaMeasView extends LayoutBase {

    private LinearLayout linSwept, linChannelPower, linOccupied, linACLR, linSEM, linSpuriousEmission, linTransmitOnOffMask, lin5GNR, linLteFdd, linInterferenceHunting;

    private DynamicView mDynamicView;

    public SaMeasView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {

                ViewHandler.getInstance().getSAView().selectMeasSetup();
                binding.linRightMenu.removeAllViews();

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

                if (mode == MeasureMode.MEASURE_MODE.SA) {

                    binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.meas_name));
                    binding.linRightMenu.addView(linSwept);
                    binding.linRightMenu.addView(linChannelPower);
                    binding.linRightMenu.addView(linOccupied);
                    binding.linRightMenu.addView(linACLR);
                    binding.linRightMenu.addView(linSEM);
//                    binding.linRightMenu.addView(linSpuriousEmission);
                    binding.linRightMenu.addView(linTransmitOnOffMask);
                    //binding.linRightMenu.addView(linInterferenceHunting);

                } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {

//                    binding.linRightMenu.addView(linChannelPower);
//                    binding.linRightMenu.addView(linOccupied);
//                    binding.linRightMenu.addView(linACLR);
//                    binding.linRightMenu.addView(linSEM);
//                    binding.linRightMenu.addView(linSpuriousEmission);
//                    binding.linRightMenu.addView(linTransmitOnOffMask);
                    binding.linRightMenu.addView(lin5GNR);
                    lin5GNR.performClick();

                }

                binding.tvBack.setOnClickListener(v -> {

                });

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        ArrayList<View> mSweptList = mDynamicView.addRightMenuButton("Swept SA");
        linSwept = (LinearLayout) mSweptList.get(0);

        ArrayList<View> mChannelList = mDynamicView.addRightMenuButton("Channel Power");
        linChannelPower = (LinearLayout) mChannelList.get(0);

        ArrayList<View> mOccupiedList = mDynamicView.addRightMenuButton("Occupied BW");
        linOccupied = (LinearLayout) mOccupiedList.get(0);

        ArrayList<View> mACLRList = mDynamicView.addRightMenuButton("ACLR");
        linACLR = (LinearLayout) mACLRList.get(0);

        ArrayList<View> mSEMList = mDynamicView.addRightMenuButton("SEM");
        linSEM = (LinearLayout) mSEMList.get(0);

        ArrayList<View> mSpuriousEmissionList = mDynamicView.addRightMenuButton("Spurious Emission");
        linSpuriousEmission = (LinearLayout) mSpuriousEmissionList.get(0);

        ArrayList<View> mTransmitOnOffMaskList = mDynamicView.addRightMenuButton("Transmit On/Off");
        linTransmitOnOffMask = (LinearLayout) mTransmitOnOffMaskList.get(0);

        ArrayList<View> m5GNRList = mDynamicView.addRightMenuButton("5G NR");
        lin5GNR = (LinearLayout) m5GNRList.get(0);

        ArrayList<View> mLteFddList = mDynamicView.addRightMenuButton("LTE FDD");
        linLteFdd = (LinearLayout) mLteFddList.get(0);

        ArrayList<View> mInterferenceHuntingList = mDynamicView.addRightMenuButton("UL Interference");
        linInterferenceHunting = (LinearLayout) mInterferenceHuntingList.get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linSwept.setOnClickListener(v -> FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.SWEPT_SA));

            linChannelPower.setOnClickListener(v -> FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.CHANNEL_POWER));

            linOccupied.setOnClickListener(v -> FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.OCCUPIED_BW));

            linACLR.setOnClickListener(v -> FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.ACLR));

            linSEM.setOnClickListener(v -> FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.SEM));

            linSpuriousEmission.setOnClickListener(v -> FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.SPURIOUS_EMISSION));

            linTransmitOnOffMask.setOnClickListener(v -> FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.TRANSMIT_MASK));

            lin5GNR.setOnClickListener(v -> FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.NR_5G));

            linLteFdd.setOnClickListener(v -> FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.LTE_FDD));

            linInterferenceHunting.setOnClickListener(v -> FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.INTERFERENCE_HUNTING));

            binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getSaMeas().addMenu());

        });
    }

    public void update() {
        mActivity.runOnUiThread(() -> {
            try {
                linSwept.setSelected(FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.SWEPT_SA);
                linChannelPower.setSelected(FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.CHANNEL_POWER);
                linOccupied.setSelected(FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.OCCUPIED_BW);
                linACLR.setSelected(FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.ACLR);
                linSEM.setSelected(FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.SEM);
                linSpuriousEmission.setSelected(FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.SPURIOUS_EMISSION);
                linTransmitOnOffMask.setSelected(FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.TRANSMIT_MASK);
                lin5GNR.setSelected(FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.NR_5G);
                linLteFdd.setSelected(FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.LTE_FDD);
                linInterferenceHunting.setSelected(FunctionHandler.getInstance().getMeasureType().getType() == MeasureType.MEASURE_TYPE.INTERFERENCE_HUNTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

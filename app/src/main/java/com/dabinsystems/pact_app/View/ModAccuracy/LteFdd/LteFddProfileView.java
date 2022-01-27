package com.dabinsystems.pact_app.View.ModAccuracy.LteFdd;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.LteFdd.LteFddProfileData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class LteFddProfileView extends LayoutBase {

    private LinearLayout lin1d4MHz, lin10MHz, lin15MHz, lin20MHz, lin3MHz, lin5MHz;
    private DynamicView mDynamicView;

    public LteFddProfileView(MainActivity activity, ActivityMainBinding binding) {
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
//                    ViewHandler.getInstance().getSAView().selectMarker();

                    binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.profile));

                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(lin1d4MHz);
                    binding.linRightMenu.addView(lin3MHz);
                    binding.linRightMenu.addView(lin5MHz);
                    binding.linRightMenu.addView(lin10MHz);
                    binding.linRightMenu.addView(lin15MHz);
                    binding.linRightMenu.addView(lin20MHz);

                    binding.linCableList.setVisibility(View.GONE);

                    binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getLteFddMeasSetupView().addMenu());
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

        ArrayList<View> m1d4MHzList = mDynamicView.addRightMenuButton("1.4MHz");
        lin1d4MHz = (LinearLayout) m1d4MHzList.get(0);

        ArrayList<View> m3MHzList = mDynamicView.addRightMenuButton("3MHz");
        lin3MHz = (LinearLayout) m3MHzList.get(0);

        ArrayList<View> m5MHzList = mDynamicView.addRightMenuButton("5MHz");
        lin5MHz = (LinearLayout) m5MHzList.get(0);

        ArrayList<View> m10MHzList = mDynamicView.addRightMenuButton("10MHz");
        lin10MHz = (LinearLayout) m10MHzList.get(0);

        ArrayList<View> m15MHzList = mDynamicView.addRightMenuButton("15MHz");
        lin15MHz = (LinearLayout) m15MHzList.get(0);

        ArrayList<View> m20MHzList = mDynamicView.addRightMenuButton("20MHz");
        lin20MHz = (LinearLayout) m20MHzList.get(0);

        setUpEvents();
    }

    @Override
    public void setUpEvents() throws NullPointerException {

        mActivity.runOnUiThread(() -> {

            lin1d4MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getLteFddData().getProfileData().setProfile(LteFddProfileData.PROFILE.MHZ1D4);
                checkSelectButton();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getLteFddData().getLteFddCmd()
                );

                ViewHandler.getInstance().getLteFddMeasSetupView().addMenu();

            });

            lin3MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getLteFddData().getProfileData().setProfile(LteFddProfileData.PROFILE.MHZ3);
                checkSelectButton();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getLteFddData().getLteFddCmd()
                );

                ViewHandler.getInstance().getLteFddMeasSetupView().addMenu();
            });

            lin5MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getLteFddData().getProfileData().setProfile(LteFddProfileData.PROFILE.MHZ5);
                checkSelectButton();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getLteFddData().getLteFddCmd()
                );

                ViewHandler.getInstance().getLteFddMeasSetupView().addMenu();
            });


            lin10MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getLteFddData().getProfileData().setProfile(LteFddProfileData.PROFILE.MHZ10);
                checkSelectButton();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getLteFddData().getLteFddCmd()
                );

                ViewHandler.getInstance().getLteFddMeasSetupView().addMenu();
            });

            lin15MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getLteFddData().getProfileData().setProfile(LteFddProfileData.PROFILE.MHZ15);
                checkSelectButton();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getLteFddData().getLteFddCmd()
                );
                ViewHandler.getInstance().getLteFddMeasSetupView().addMenu();
            });

            lin20MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getLteFddData().getProfileData().setProfile(LteFddProfileData.PROFILE.MHZ20);
                checkSelectButton();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getLteFddData().getLteFddCmd()
                );

                ViewHandler.getInstance().getLteFddMeasSetupView().addMenu();
            });

        });

    }

    public void update() {
        initView();
        checkSelectButton();
    }

    public void checkSelectButton() {
        LteFddProfileData.PROFILE profile = DataHandler.getInstance().getLteFddData().getProfileData().getProfile();
        lin1d4MHz.setSelected(profile == LteFddProfileData.PROFILE.MHZ1D4);
        lin3MHz.setSelected(profile == LteFddProfileData.PROFILE.MHZ3);
        lin5MHz.setSelected(profile == LteFddProfileData.PROFILE.MHZ5);
        lin10MHz.setSelected(profile == LteFddProfileData.PROFILE.MHZ10);
        lin15MHz.setSelected(profile == LteFddProfileData.PROFILE.MHZ15);
        lin20MHz.setSelected(profile == LteFddProfileData.PROFILE.MHZ20);
    }
}

package com.dabinsystems.pact_app.View.ModAccuracy.NR5G;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrProfileData;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class NrProfileView1 extends LayoutBase {

    private LinearLayout linSSBOnly, lin5MHz, lin10MHz, lin15MHz, lin20MHz, lin25MHz, lin30MHz, lin40MHz, linMore;
    private DynamicView mDynamicView;

    public NrProfileView1(MainActivity activity, ActivityMainBinding binding) {
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
//                    binding.linRightMenu.addView(linSSBOnly);
                    binding.linRightMenu.addView(lin5MHz);
                    binding.linRightMenu.addView(lin10MHz);
                    binding.linRightMenu.addView(lin15MHz);
                    binding.linRightMenu.addView(lin20MHz);
                    binding.linRightMenu.addView(lin25MHz);
                    binding.linRightMenu.addView(lin30MHz);
                    binding.linRightMenu.addView(lin40MHz);
                    binding.linRightMenu.addView(linMore);

                    binding.linCableList.setVisibility(View.GONE);

                    binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getNrMeasSetupView().addMenu());
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

        ArrayList<View> mSSBOnlyList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.ssb_only));
        linSSBOnly = (LinearLayout) mSSBOnlyList.get(0);

        ArrayList<View> m5MHzList = mDynamicView.addRightMenuButton("5MHz");
        lin5MHz = (LinearLayout) m5MHzList.get(0);

        ArrayList<View> m10MHzList = mDynamicView.addRightMenuButton("10MHz");
        lin10MHz = (LinearLayout) m10MHzList.get(0);

        ArrayList<View> m15MHzList = mDynamicView.addRightMenuButton("15MHz");
        lin15MHz = (LinearLayout) m15MHzList.get(0);

        ArrayList<View> m20MHzList = mDynamicView.addRightMenuButton("20MHz");
        lin20MHz = (LinearLayout) m20MHzList.get(0);

        ArrayList<View> m25MHzList = mDynamicView.addRightMenuButton("25MHz");
        lin25MHz = (LinearLayout) m25MHzList.get(0);

        ArrayList<View> m30MHzList = mDynamicView.addRightMenuButton("30MHz");
        lin30MHz = (LinearLayout) m30MHzList.get(0);

        ArrayList<View> m40MHzList = mDynamicView.addRightMenuButton("40MHz");
        lin40MHz = (LinearLayout) m40MHzList.get(0);

        ArrayList<View> mMoreList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.more_name), "1/2");
        linMore = (LinearLayout) mMoreList.get(0);

        setUpEvents();
    }

    @Override
    public void setUpEvents() throws NullPointerException {

        mActivity.runOnUiThread(() -> {

//            linSSBOnly.setOnClickListener(v -> {
//
//                FunctionHandler.getInstance().getProfileFunc().setProfile(NRProfileFunc.PROFILE.SSB_ONLY);
//                checkSelectButton();
//
//            });

            lin5MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getProfileData().setProfile(NrProfileData.PROFILE.MHZ5);
                checkSelectButton();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                ViewHandler.getInstance().getNrMeasSetupView().addMenu();

            });

            lin10MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getProfileData().setProfile(NrProfileData.PROFILE.MHZ10);
                checkSelectButton();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                ViewHandler.getInstance().getNrMeasSetupView().addMenu();
            });

            lin15MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getProfileData().setProfile(NrProfileData.PROFILE.MHZ15);
                checkSelectButton();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                ViewHandler.getInstance().getNrMeasSetupView().addMenu();
            });

            lin20MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getProfileData().setProfile(NrProfileData.PROFILE.MHZ20);
                checkSelectButton();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                ViewHandler.getInstance().getNrMeasSetupView().addMenu();
            });

            lin25MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getProfileData().setProfile(NrProfileData.PROFILE.MHZ25);
                checkSelectButton();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                ViewHandler.getInstance().getNrMeasSetupView().addMenu();
            });


            lin30MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getProfileData().setProfile(NrProfileData.PROFILE.MHZ30);
                checkSelectButton();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                ViewHandler.getInstance().getNrMeasSetupView().addMenu();
            });

            lin40MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getProfileData().setProfile(NrProfileData.PROFILE.MHZ40);
                checkSelectButton();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                ViewHandler.getInstance().getNrMeasSetupView().addMenu();
            });


            linMore.setOnClickListener(v -> ViewHandler.getInstance().getNr5gProfileView2().addMenu());

        });
    }

    public void update() {
        initView();
        checkSelectButton();
    }

    public void checkSelectButton() {
        linSSBOnly.setSelected(DataHandler.getInstance().getNrData().getProfileData().getProfile() == NrProfileData.PROFILE.SSB_ONLY);
        lin5MHz.setSelected(DataHandler.getInstance().getNrData().getProfileData().getProfile() == NrProfileData.PROFILE.MHZ5);
        lin10MHz.setSelected(DataHandler.getInstance().getNrData().getProfileData().getProfile() == NrProfileData.PROFILE.MHZ10);
        lin15MHz.setSelected(DataHandler.getInstance().getNrData().getProfileData().getProfile() == NrProfileData.PROFILE.MHZ15);
        lin20MHz.setSelected(DataHandler.getInstance().getNrData().getProfileData().getProfile() == NrProfileData.PROFILE.MHZ20);
        lin25MHz.setSelected(DataHandler.getInstance().getNrData().getProfileData().getProfile() == NrProfileData.PROFILE.MHZ25);
        lin30MHz.setSelected(DataHandler.getInstance().getNrData().getProfileData().getProfile() == NrProfileData.PROFILE.MHZ30);
        lin40MHz.setSelected(DataHandler.getInstance().getNrData().getProfileData().getProfile() == NrProfileData.PROFILE.MHZ40);
    }

}

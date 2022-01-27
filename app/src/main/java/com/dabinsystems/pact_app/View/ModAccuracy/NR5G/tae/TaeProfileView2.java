package com.dabinsystems.pact_app.View.ModAccuracy.NR5G.tae;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrInterTaeData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class TaeProfileView2 extends LayoutBase {

    private LinearLayout lin50MHz, lin60MHz, lin70MHz, lin80MHz, lin90MHz, lin100MHz, linMore;

    private DynamicView mDynamicView;

    public TaeProfileView2(MainActivity activity, ActivityMainBinding binding) {
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
                    ViewHandler.getInstance().getSAView().selectMarker();

                    binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.profile));

                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(lin50MHz);
                    binding.linRightMenu.addView(lin60MHz);
                    binding.linRightMenu.addView(lin70MHz);
                    binding.linRightMenu.addView(lin80MHz);
                    binding.linRightMenu.addView(lin90MHz);
                    binding.linRightMenu.addView(lin100MHz);
                    binding.linRightMenu.addView(linMore);

                    binding.linCableList.setVisibility(View.GONE);

                    binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getInterTaeView().addMenu());
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

        ArrayList<View> m50MHzList = mDynamicView.addRightMenuButton("50MHz");
        ArrayList<View> m60MHzList = mDynamicView.addRightMenuButton("60MHz");
        ArrayList<View> m70MHzList = mDynamicView.addRightMenuButton("70MHz");
        ArrayList<View> m80MHzList = mDynamicView.addRightMenuButton("80MHz");
        ArrayList<View> m90MHzList = mDynamicView.addRightMenuButton("90MHz");
        ArrayList<View> m100MHzList = mDynamicView.addRightMenuButton("100MHz");
        ArrayList<View> mMoreList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.more_name), "2/2");

        lin50MHz = (LinearLayout) m50MHzList.get(0);
        lin60MHz = (LinearLayout) m60MHzList.get(0);
        lin70MHz = (LinearLayout) m70MHzList.get(0);
        lin80MHz = (LinearLayout) m80MHzList.get(0);
        lin90MHz = (LinearLayout) m90MHzList.get(0);
        lin100MHz = (LinearLayout) m100MHzList.get(0);
        linMore = (LinearLayout) mMoreList.get(0);

        setUpEvents();
    }

    @Override
    public void setUpEvents() throws NullPointerException {

        mActivity.runOnUiThread(() -> {

            lin50MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getInterTaeData().setProfile(NrInterTaeData.PROFILE.MHZ50);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                ViewHandler.getInstance().getInterTaeView().addMenu();
            });

            lin60MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getInterTaeData().setProfile(NrInterTaeData.PROFILE.MHZ60);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                ViewHandler.getInstance().getInterTaeView().addMenu();

            });

            lin70MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getInterTaeData().setProfile(NrInterTaeData.PROFILE.MHZ70);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                ViewHandler.getInstance().getInterTaeView().addMenu();

            });

            lin80MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getInterTaeData().setProfile(NrInterTaeData.PROFILE.MHZ80);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                ViewHandler.getInstance().getInterTaeView().addMenu();

            });


            lin90MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getInterTaeData().setProfile(NrInterTaeData.PROFILE.MHZ90);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                ViewHandler.getInstance().getInterTaeView().addMenu();

            });


            lin100MHz.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getInterTaeData().setProfile(NrInterTaeData.PROFILE.MHZ100);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

                ViewHandler.getInstance().getInterTaeView().addMenu();
            });


            linMore.setOnClickListener(v -> ViewHandler.getInstance().getTaeProfileView1().addMenu());
        });
    }

    public void update() {
        initView();
        checkSelectButton();
    }

    public void checkSelectButton() {
        mActivity.runOnUiThread(() -> {
            lin50MHz.setSelected(DataHandler.getInstance().getNrData().getInterTaeData().getProfile() == NrInterTaeData.PROFILE.MHZ50);
            lin60MHz.setSelected(DataHandler.getInstance().getNrData().getInterTaeData().getProfile() == NrInterTaeData.PROFILE.MHZ60);
            lin70MHz.setSelected(DataHandler.getInstance().getNrData().getInterTaeData().getProfile() == NrInterTaeData.PROFILE.MHZ70);
            lin80MHz.setSelected(DataHandler.getInstance().getNrData().getInterTaeData().getProfile() == NrInterTaeData.PROFILE.MHZ80);
            lin90MHz.setSelected(DataHandler.getInstance().getNrData().getInterTaeData().getProfile() == NrInterTaeData.PROFILE.MHZ90);
            lin100MHz.setSelected(DataHandler.getInstance().getNrData().getInterTaeData().getProfile() == NrInterTaeData.PROFILE.MHZ100);
        });
    }
}

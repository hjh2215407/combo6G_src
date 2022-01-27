package com.dabinsystems.pact_app.View.ModAccuracy.NR5G.tae;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrTaeData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class NumOfTxAntView extends LayoutBase {

    private LinearLayout linNumOfAnt2, linNumOfAnt4, linNumOfAnt8, linNumOfAnt32;
    private ArrayList<View> mNumOfAntList2, mNumOfAntList4, mNumOfAntList8, mNumOfAntList32;
    private DynamicView mDynamicView;

    public NumOfTxAntView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            mActivity.runOnUiThread(() -> {

                ViewHandler.getInstance().getVswrView().selectMarker();
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linNumOfAnt2);
                binding.linRightMenu.addView(linNumOfAnt4);
                binding.linRightMenu.addView(linNumOfAnt8);
//                binding.linRightMenu.addView(linNumOfAnt32);

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mNumOfAntList2 = mDynamicView.addRightMenuButton("2", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linNumOfAnt2 = (LinearLayout) mNumOfAntList2.get(0);

        mNumOfAntList4 = mDynamicView.addRightMenuButton("4", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linNumOfAnt4 = (LinearLayout) mNumOfAntList4.get(0);

        mNumOfAntList8 = mDynamicView.addRightMenuButton("8", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linNumOfAnt8 = (LinearLayout) mNumOfAntList8.get(0);

        mNumOfAntList32 = mDynamicView.addRightMenuButton("32", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linNumOfAnt32 = (LinearLayout) mNumOfAntList32.get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linNumOfAnt2.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getTaeInfoData().setNumOfTxAnt(NrTaeData.NUM_OF_TX_ANT.TX_2);
                ViewHandler.getInstance().getTaeView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );
            });

            linNumOfAnt4.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getTaeInfoData().setNumOfTxAnt(NrTaeData.NUM_OF_TX_ANT.TX_4);
                ViewHandler.getInstance().getTaeView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

            });

            linNumOfAnt8.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getTaeInfoData().setNumOfTxAnt(NrTaeData.NUM_OF_TX_ANT.TX_8);
                ViewHandler.getInstance().getTaeView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

            });

            linNumOfAnt32.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getTaeInfoData().setNumOfTxAnt(NrTaeData.NUM_OF_TX_ANT.TX_32);
                ViewHandler.getInstance().getTaeView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );

            });

            binding.tvBack.setOnClickListener(v -> {
                ViewHandler.getInstance().getTaeView().addMenu();

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getNrData().getNrCmd()
                );
            });

        });

    }
}

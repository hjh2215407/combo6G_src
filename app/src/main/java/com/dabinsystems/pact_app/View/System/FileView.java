package com.dabinsystems.pact_app.View.System;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class FileView extends LayoutBase {

    private DynamicView dynamicView;

    private LinearLayout linSave, linRecall;
    private ArrayList<View> mSaveList, mRecallList;

    public FileView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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

                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(linSave);
                    binding.linRightMenu.addView(linRecall);

                    binding.linCableList.setVisibility(View.GONE);
                    binding.linCalibration.setVisibility(View.GONE);

                    binding.tvBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ViewHandler.getInstance().getSystemView().addMenu();

                        }
                    });

                } catch(IllegalArgumentException e) {
                    e.printStackTrace();
                }

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (dynamicView != null) return;

        dynamicView = new DynamicView(mActivity);

        mSaveList = dynamicView.addRightMenuButton("Save", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mRecallList = dynamicView.addRightMenuButton("Recall", Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        linSave = (LinearLayout) mSaveList.get(0);
        linRecall = (LinearLayout) mRecallList.get(0);

        setUpEvents();

    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {


            binding.linCableList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //null
                }
            });

            linSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ViewHandler.getInstance().getFileSaveDialog().show();

                }
            });

            linRecall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ViewHandler.getInstance().getFileRecallDialog().show();

                }
            });


        });

    }

}

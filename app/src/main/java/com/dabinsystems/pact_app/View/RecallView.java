package com.dabinsystems.pact_app.View;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class RecallView extends LayoutBase {

    private LinearLayout linOk, linCancel;
    private ArrayList<View> mDataPointsList, mRunAndHoldList;
    private DynamicView mDynamicView;

    public RecallView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
        super(activity, binding);

    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {

                binding.linRightMenu.removeAllViews();

                if (binding.tvRecallMessage.getVisibility() != View.VISIBLE)
                    binding.tvRecallMessage.setVisibility(View.VISIBLE);

                binding.linBottomMenu.setVisibility(View.GONE);
                binding.linTopSubBtn.setVisibility(View.INVISIBLE);

                binding.linRightMenu.addView(linOk);
                binding.linRightMenu.addView(linCancel);
                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.system_name));
                binding.linCableList.setVisibility(View.GONE);
                binding.linCalibration.setVisibility(View.GONE);

                binding.tvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });

            });


        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity.getApplicationContext());

        mDataPointsList = mDynamicView.addRightMenuButton("OK");
        mRunAndHoldList = mDynamicView.addRightMenuButton("Cancel");

        linOk = (LinearLayout) mDataPointsList.get(0);
        linCancel = (LinearLayout) mRunAndHoldList.get(0);

        setUpEvents();
    }


    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linOk.setOnClickListener(v -> {
                FunctionHandler.getInstance().getRecallFunc().clickOk();
            });

            linCancel.setOnClickListener(v -> FunctionHandler.getInstance().getRecallFunc().clickCancel());


        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void update() {
        super.update();

        mActivity.runOnUiThread(() -> {


        });

    }

}

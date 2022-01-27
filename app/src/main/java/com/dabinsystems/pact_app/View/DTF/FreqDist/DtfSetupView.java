package com.dabinsystems.pact_app.View.DTF.FreqDist;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.app.ActivityCompat;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.CableLossDialog;
import com.dabinsystems.pact_app.Dialog.CablePropDialog;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import static com.dabinsystems.pact_app.Dialog.WiFiDialog.MULTIPLE_PERMISSIONS;

public class DtfSetupView extends LayoutBase {

    private DynamicView dynamicView;

    private LinearLayout mCableList, mCableLoss, mPropVelocity, mWindowing;
    private ArrayList<View> mCableLstList, mCableLossList, mPropVelocityList, mWindowingList;

    public DtfSetupView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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

                binding.linRightMenu.addView(mCableList);
                binding.linRightMenu.addView(mCableLoss);
                binding.linRightMenu.addView(mPropVelocity);
//                binding.linRightMenu.addView(mWindowing);

                binding.linCableList.setVisibility(View.GONE);
                binding.linCalibration.setVisibility(View.GONE);

                binding.tvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ViewHandler.getInstance().getFreqDistView().addMenu();

                    }
                });

            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (dynamicView != null) return;

        dynamicView = new DynamicView(mActivity);

        mCableLstList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.cable_list), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mCableLossList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.cableloss_name), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mPropVelocityList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.prop_velocity), Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mWindowingList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.windowing), Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        mCableList = (LinearLayout) mCableLstList.get(0);
        mCableLoss = (LinearLayout) mCableLossList.get(0);
        mPropVelocity = (LinearLayout) mPropVelocityList.get(0);
        mWindowing = (LinearLayout) mWindowingList.get(0);

        setUpEvents();

    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()->{

            binding.linCableList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //null
                }
            });

            mCableList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ViewHandler.getInstance().getCableListView().addMenu();

                }
            });

            mCableLoss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new CableLossDialog(mActivity, binding).show();

                }
            });

            mPropVelocity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new CablePropDialog(mActivity, binding).show();

                }
            });

            mWindowing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ViewHandler.getInstance().getWindowingView().addMenu();

                }
            });


        });

    }

}

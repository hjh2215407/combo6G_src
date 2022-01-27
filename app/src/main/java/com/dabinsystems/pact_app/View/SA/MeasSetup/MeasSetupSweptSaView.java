package com.dabinsystems.pact_app.View.SA.MeasSetup;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.SweptAvgHoldKeypadDialog;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class MeasSetupSweptSaView extends LayoutBase {

    ArrayList<View> mAvgList;
    LinearLayout linAvgHold;
    AutofitTextView tvAvgHoldVal;
    DynamicView mDynamicView;

    public MeasSetupSweptSaView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();
            mActivity.runOnUiThread(()-> {

                binding.linRightMenu.removeAllViews();
                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.meas_setup));

                binding.linRightMenu.addView(linAvgHold);

                binding.tvBack.setOnClickListener(v->{

                });

            });

        }).start();

    }

    public void update() {

        initView();

        mActivity.runOnUiThread(()->{

            tvAvgHoldVal.setText(SaDataHandler.getInstance().getSweptSaConfigData().getSweptSaMeasSetupData().getAvgHold() + "");

        });

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mAvgList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.avg_hold_num_title), "100");
        linAvgHold = (LinearLayout) mAvgList.get(0);
        tvAvgHoldVal = (AutofitTextView) mAvgList.get(2);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()-> {


            linAvgHold.setOnClickListener(v->{

                new SweptAvgHoldKeypadDialog(mActivity, binding).show();

            });

        });

    }
}

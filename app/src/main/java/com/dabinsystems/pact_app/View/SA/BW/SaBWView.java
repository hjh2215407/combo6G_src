package com.dabinsystems.pact_app.View.SA.BW;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SaBWView extends LayoutBase {

    private LinearLayout linRBW, linVBW;
    private AutofitTextView tvRBW, tvVBW, tvVBWSubMenu, tvRBWSubMenu;
    private ArrayList<View> mRBWList, mVBWList;
    private DynamicView mDynamicView;

    public SaBWView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }


    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {

                ViewHandler.getInstance().getSAView().selectBW();
                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.bw_name));
                tvRBWSubMenu.setText(SaDataHandler.getInstance().getConfigData().getBwData().getRBW().getString());
                tvVBWSubMenu.setText(SaDataHandler.getInstance().getConfigData().getBwData().getVBW().getString());
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linRBW);
                binding.linRightMenu.addView(linVBW);
            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mRBWList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.rbw_name), "");
        linRBW = (LinearLayout) mRBWList.get(0);
        tvRBW = (AutofitTextView) mRBWList.get(1);
        tvRBWSubMenu = (AutofitTextView) mRBWList.get(2);
        tvRBW.setSingleLine(false);

        mVBWList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.vbw_name), "");
        linVBW = (LinearLayout) mVBWList.get(0);
        tvVBW = (AutofitTextView) mVBWList.get(1);
        tvVBWSubMenu = (AutofitTextView) mVBWList.get(2);
        tvVBW.setSingleLine(false);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {


            linRBW.setOnClickListener(view -> {

                ViewHandler.getInstance().getRBW1().addMenu();

            });

            linVBW.setOnClickListener(view -> {
                ViewHandler.getInstance().getVBW1().addMenu();
            });

        });

    }
}

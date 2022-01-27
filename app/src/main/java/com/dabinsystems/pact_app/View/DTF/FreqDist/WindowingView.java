package com.dabinsystems.pact_app.View.DTF.FreqDist;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Data.VSWR.WindowingData;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class WindowingView extends LayoutBase {

    private DynamicView dynamicView;

    private LinearLayout linRect, linNominalSideLobe, linLowSideLobe, linMinSideLobe;

    public WindowingView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
        super(activity, binding);

//        FunctionHandler.getInstance().getDataConnector().sendCommand(ByteBuffer.allocate(4).putInt(win_select.value()).array());

//        new Handler(Looper.getMainLooper()).post(() -> {
//
//            CommandData.getInstance().setWinRectCmd();
//            CommandData.getInstance().sendSettingValuesCmd();
//
//        });

    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {

                binding.linRightMenu.removeAllViews();

                binding.linRightMenu.addView(linRect);
                binding.linRightMenu.addView(linNominalSideLobe);
                binding.linRightMenu.addView(linLowSideLobe);
                binding.linRightMenu.addView(linMinSideLobe);

                binding.linCableList.setVisibility(View.GONE);
                binding.linCalibration.setVisibility(View.GONE);

                binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getDtfSetupView().addMenu());
            });
        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (dynamicView != null) return;

        dynamicView = new DynamicView(mActivity);

        ArrayList<View> mRectList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.rectangular));
        ArrayList<View> mNominalSideList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.normal_side_lobe));
        ArrayList<View> mLowSideList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.low_side_lobe));
        ArrayList<View> mMinSideList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.minimum_side_lobe));

        linRect = (LinearLayout) mRectList.get(0);
        linNominalSideLobe = (LinearLayout) mNominalSideList.get(0);
        linLowSideLobe = (LinearLayout) mLowSideList.get(0);
        linMinSideLobe = (LinearLayout) mMinSideList.get(0);

        resetSelect();

        linRect.setSelected(true);

        setUpEvents();
    }

    @Override
    public void update() {
        super.update();

        switch (VswrDataHandler.getInstance().getConfigData().getWindowingData().getMode()) {

            case RECTANGULAR:
                resetSelect();
                linRect.setSelected(true);
                break;

            case NOMINAL_SIDE_LOBE:
                resetSelect();
                linNominalSideLobe.setSelected(true);
                break;

            case LOW_SIDE_LOBE:
                resetSelect();
                linLowSideLobe.setSelected(true);
                break;

            case MINIMUM_SIDE_LOBE:
                resetSelect();
                linMinSideLobe.setSelected(true);
                break;
        }
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linRect.setOnClickListener(v -> {

                VswrDataHandler.getInstance().getConfigData().getWindowingData().setMode(WindowingData.WINDOWING.RECTANGULAR);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        VswrDataHandler.getInstance().getVswrParameter()
                );

                ViewHandler.getInstance().getDtfSetupView().addMenu();

            });

            linNominalSideLobe.setOnClickListener(v -> {

                VswrDataHandler.getInstance().getConfigData().getWindowingData().setMode(WindowingData.WINDOWING.NOMINAL_SIDE_LOBE);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        VswrDataHandler.getInstance().getVswrParameter()
                );

                ViewHandler.getInstance().getDtfSetupView().addMenu();
            });

            linLowSideLobe.setOnClickListener(v -> {

                VswrDataHandler.getInstance().getConfigData().getWindowingData().setMode(WindowingData.WINDOWING.LOW_SIDE_LOBE);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        VswrDataHandler.getInstance().getVswrParameter()
                );

                ViewHandler.getInstance().getDtfSetupView().addMenu();

            });

            linMinSideLobe.setOnClickListener(v -> {

                VswrDataHandler.getInstance().getConfigData().getWindowingData().setMode(WindowingData.WINDOWING.MINIMUM_SIDE_LOBE);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        VswrDataHandler.getInstance().getVswrParameter()
                );

                ViewHandler.getInstance().getDtfSetupView().addMenu();

            });

        });

    }

    private void resetSelect() throws NullPointerException {
        linRect.setSelected(false);
        linLowSideLobe.setSelected(false);
        linNominalSideLobe.setSelected(false);
        linMinSideLobe.setSelected(false);
    }

}

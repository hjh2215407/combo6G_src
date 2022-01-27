package com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.FailSourceData;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class AclrFailSourceView extends LayoutBase {

    private DynamicView dynamicView;

    private LinearLayout linNone, linAbsolute, linRelative, linAll;

    public AclrFailSourceView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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

                binding.linRightMenu.addView(linNone);
                binding.linRightMenu.addView(linAbsolute);
                binding.linRightMenu.addView(linRelative);
                binding.linRightMenu.addView(linAll);

                binding.linCableList.setVisibility(View.GONE);
                binding.linCalibration.setVisibility(View.GONE);

                binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getOffsetSetupView().addMenu());
            });
        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (dynamicView != null) return;

        dynamicView = new DynamicView(mActivity);

        ArrayList<View> mNoneList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.none));
        ArrayList<View> mAbsoluteList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.absolute));
        ArrayList<View> mRelativeList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.relative));
        ArrayList<View> mAllList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.all));

        linNone = (LinearLayout) mNoneList.get(0);
        linAbsolute = (LinearLayout) mAbsoluteList.get(0);
        linRelative = (LinearLayout) mRelativeList.get(0);
        linAll = (LinearLayout) mAllList.get(0);

        resetSelect();
        linNone.setSelected(true);

        setUpEvents();
    }

    @Override
    public void update() {
        super.update();

        switch (SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData().getOffsetSetupData().getFailSource()) {
            case NONE:
                resetSelect();
                linNone.setSelected(true);
                break;
            case ABSOLUTE:
                resetSelect();
                linAbsolute.setSelected(true);
                break;
            case RELATIVE:
                resetSelect();
                linRelative.setSelected(true);
                break;
            case ALL:
                resetSelect();
                linAll.setSelected(true);
                break;
        }
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linNone.setOnClickListener(v -> {
                selectMode(FailSourceData.FAIL_SOURCE.NONE);
                resetSelect();
                linNone.setSelected(true);
            });

            linAbsolute.setOnClickListener(v -> {
                selectMode(FailSourceData.FAIL_SOURCE.ABSOLUTE);
                resetSelect();
                linAbsolute.setSelected(true);
            });

            linRelative.setOnClickListener(v -> {
                selectMode(FailSourceData.FAIL_SOURCE.RELATIVE);
                resetSelect();
                linRelative.setSelected(true);
            });

            linAll.setOnClickListener(v -> {
                selectMode(FailSourceData.FAIL_SOURCE.ALL);
                resetSelect();
                linAll.setSelected(true);
            });
            
        });
    }

    public void selectMode(FailSourceData.FAIL_SOURCE mode) {
        SaDataHandler.getInstance()
                .getAclrConfigData()
                .getAclrMeasSetupData()
                .getOffsetSetupData()
                .setFailSource(mode);

        FunctionHandler.getInstance().getDataConnector().sendCommand(
                SaDataHandler.getInstance().getAclrConfigData().getSaParameter()
        );

        ViewHandler.getInstance().getOffsetSetupView().addMenu();
    }

    private void resetSelect() throws NullPointerException {
        linNone.setSelected(false);
        linRelative.setSelected(false);
        linAbsolute.setSelected(false);
        linAll.setSelected(false);
    }
}

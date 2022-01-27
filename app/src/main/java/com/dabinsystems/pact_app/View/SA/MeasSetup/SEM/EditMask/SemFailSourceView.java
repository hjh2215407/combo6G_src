package com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.EditMask;

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


public class SemFailSourceView extends LayoutBase {

    private DynamicView dynamicView;

    private LinearLayout linNone, linAbsolute, linRelative, linAbsOrRel, linAll;

    public SemFailSourceView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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
                binding.linRightMenu.addView(linAbsOrRel);
                binding.linRightMenu.addView(linAll);

                binding.linCableList.setVisibility(View.GONE);
                binding.linCalibration.setVisibility(View.GONE);

                binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getEditMaskView().addMenu());
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
        ArrayList<View> mAbsOrRelList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.abs_or_rel));
        ArrayList<View> mAllList = dynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.all));

        linNone = (LinearLayout) mNoneList.get(0);
        linAbsolute = (LinearLayout) mAbsoluteList.get(0);
        linRelative = (LinearLayout) mRelativeList.get(0);
        linAbsOrRel = (LinearLayout) mAbsOrRelList.get(0);
        linAll = (LinearLayout) mAllList.get(0);

        resetSelect();
        linNone.setSelected(true);

        setUpEvents();
    }

    @Override
    public void update() {
        super.update();

        switch(SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getEditMaskData().getFailSource()) {

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

            case SEM_ABS_OR_REL:
                resetSelect();
                linAbsOrRel.setSelected(true);
                break;

            case SEM_ALL:
                resetSelect();
                linAll.setSelected(true);
                break;
        }

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()->{

            linNone.setOnClickListener(v -> {
                resetSelect();
                linNone.setSelected(true);
                selectMode(FailSourceData.FAIL_SOURCE.NONE);
            });

            linAbsolute.setOnClickListener(v -> {
                resetSelect();
                linAbsolute.setSelected(true);
                selectMode(FailSourceData.FAIL_SOURCE.ABSOLUTE);
            });

            linRelative.setOnClickListener(v -> {
                resetSelect();
                linRelative.setSelected(true);
                selectMode(FailSourceData.FAIL_SOURCE.RELATIVE);
            });

            linAbsOrRel.setOnClickListener(v->{
                resetSelect();
                linAbsOrRel.setSelected(true);
                selectMode(FailSourceData.FAIL_SOURCE.SEM_ABS_OR_REL);
            });

            linAll.setOnClickListener(v -> {
                resetSelect();
                linAll.setSelected(true);
                selectMode(FailSourceData.FAIL_SOURCE.SEM_ALL);
            });

        });
    }

    public void selectMode(FailSourceData.FAIL_SOURCE mode) {
        SaDataHandler.getInstance()
                .getSemConfigData()
                .getSemMeasSetupData()
                .getEditMaskData()
                .setFailSource(mode);

        FunctionHandler.getInstance().getDataConnector().sendCommand(
                SaDataHandler.getInstance().getSemConfigData().getSaParameter()
        );

        ViewHandler.getInstance().getEditMaskView().addMenu();
    }

    private void resetSelect() throws NullPointerException {
        linNone.setSelected(false);
        linRelative.setSelected(false);
        linAbsolute.setSelected(false);
        linAbsOrRel.setSelected(false);
        linAll.setSelected(false);
    }

}

package com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SideData;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.ACLR.AclrAbsLimitKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.ACLR.AclrRelLimitKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.ACLR.OffsetSetupIntegBwKeypad;
import com.dabinsystems.pact_app.Dialog.SA.MeasSetup.ACLR.OffsetSpacingKeypad;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class OffsetSetupView extends LayoutBase {

    private LinearLayout linNumOffset, linSelectOffset, linOffsetSpacing, linIntegBw, linOffsetSide, linFailSource, linAbsLimit, linRelLimit;
    private AutofitTextView tvNumOffsetVal, tvSelectOffsetVal, tvOffsetSpacingVal, tvIntegBwVal, tvOffsetSideNeg, tvOffsetSideBoth, tvOffsetSidePos,
            tvFailSourceVal, tvAbsLimitVal, tvRelLimitVal;

    private ArrayList<View> mNumOffsetList, mSelectOffsetList, mOffsetSpacingList, mIntegBwList, mOffsetSideList, mFailSourceList, mAbsLimitList, mRelLimitList;
    private DynamicView mDynamicView;

    public OffsetSetupView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {
                binding.tvRightMenuTitle.setText("Offset Setup");
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linNumOffset);
                binding.linRightMenu.addView(linSelectOffset);
                binding.linRightMenu.addView(linOffsetSpacing);
                binding.linRightMenu.addView(linIntegBw);
                binding.linRightMenu.addView(linOffsetSide);
                binding.linRightMenu.addView(linFailSource);
                binding.linRightMenu.addView(linAbsLimit);
                binding.linRightMenu.addView(linRelLimit);

                binding.tvBack.setOnClickListener(v -> {

                    ViewHandler.getInstance().getMeasSetupAclrView().addMenu();

                });


//                tvModeVal.setText(CommandData.getInstance().getTraceModeToString());
//                tvTypeVal.setText(CommandData.getInstance().getTraceTypeToString());
//                tvDetectorVal.setText(CommandData.getInstance().getTraceDetectorToString());

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mNumOffsetList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.number_of_offset), "");
        linNumOffset = (LinearLayout) mNumOffsetList.get(0);
        tvNumOffsetVal = (AutofitTextView) mNumOffsetList.get(2);

        mSelectOffsetList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.select_offset), "");
        linSelectOffset = (LinearLayout) mSelectOffsetList.get(0);
        tvSelectOffsetVal = (AutofitTextView) mSelectOffsetList.get(2);

        mOffsetSpacingList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.offset_spacing), " MHz");
        linOffsetSpacing = (LinearLayout) mOffsetSpacingList.get(0);
        tvOffsetSpacingVal = (AutofitTextView) mOffsetSpacingList.get(2);

        mIntegBwList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.integ_bw), " MHz");
        linIntegBw = (LinearLayout) mIntegBwList.get(0);
        tvIntegBwVal = (AutofitTextView) mIntegBwList.get(2);

        mOffsetSideList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.offset_side), "Neg", "Both", "Pos");
        linOffsetSide = (LinearLayout) mOffsetSideList.get(0);
        tvOffsetSideNeg = (AutofitTextView) mOffsetSideList.get(2);
        tvOffsetSideBoth = (AutofitTextView) mOffsetSideList.get(3);
        tvOffsetSidePos = (AutofitTextView) mOffsetSideList.get(4);

        mFailSourceList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.fail_source), "Relative");
        linFailSource = (LinearLayout) mFailSourceList.get(0);
        tvFailSourceVal = (AutofitTextView) mFailSourceList.get(2);

        mAbsLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.abs_limit), " dBm");
        linAbsLimit = (LinearLayout) mAbsLimitList.get(0);
        tvAbsLimitVal = (AutofitTextView) mAbsLimitList.get(2);

        mRelLimitList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.rel_limit), " dB");
        linRelLimit = (LinearLayout) mRelLimitList.get(0);
        tvRelLimitVal = (AutofitTextView) mRelLimitList.get(2);

        setUpEvents();

    }

    @SuppressLint("SetTextI18n")
    public void update() {

        initView();

        mActivity.runOnUiThread(() -> {

            tvNumOffsetVal.setText(

                    SaDataHandler
                            .getInstance()
                            .getAclrConfigData()
                            .getAclrMeasSetupData()
                            .getOffsetSetupData()
                            .getNumOfOffset() + ""

            );

            tvSelectOffsetVal.setText(

                    SaDataHandler
                            .getInstance()
                            .getAclrConfigData()
                            .getAclrMeasSetupData()
                            .getOffsetSetupData()
                            .getSelectOffset() + 1 + ""

            );

            double offsetSpacing = SaDataHandler
                    .getInstance()
                    .getAclrConfigData()
                    .getAclrMeasSetupData()
                    .getOffsetSetupData()
                    .getOffsetSpacing();

            offsetSpacing = Math.round(offsetSpacing * 10000d) / 10000d;

            tvOffsetSpacingVal.setText(

                    offsetSpacing + " MHz"

            );

            double integ =
                    SaDataHandler
                            .getInstance()
                            .getAclrConfigData()
                            .getAclrMeasSetupData()
                            .getOffsetSetupData()
                            .getIntegBw();

            integ = Math.round(integ * 10000d)/10000d;

            tvIntegBwVal.setText(integ + " MHz");

            updateOffsetSideOption();

            tvFailSourceVal.setText(

                    SaDataHandler
                            .getInstance()
                            .getAclrConfigData()
                            .getAclrMeasSetupData()
                            .getOffsetSetupData()
                            .getFailSource()
                            .getName()

            );

            tvAbsLimitVal.setText(
                    Utils.formatNumber(
                            SaDataHandler
                                    .getInstance()
                                    .getAclrConfigData()
                                    .getAclrMeasSetupData()
                                    .getOffsetSetupData()
                                    .getAbsLimit()
                            , 1, false) + " dBm"

            );

            tvRelLimitVal.setText(
                    Utils.formatNumber(
                            SaDataHandler
                                    .getInstance()
                                    .getAclrConfigData()
                                    .getAclrMeasSetupData()
                                    .getOffsetSetupData()
                                    .getRelLimit()
                            , 1, false) + " dB"

            );

        });

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linNumOffset.setOnClickListener(v -> {

                ViewHandler
                        .getInstance()
                        .getNumberOfOffsetView()
                        .addMenu();

            });

            linSelectOffset.setOnClickListener(v -> {

                ViewHandler
                        .getInstance()
                        .getSelectOffsetView()
                        .addMenu();

            });

            linOffsetSpacing.setOnClickListener(v -> {

                new OffsetSpacingKeypad(mActivity, binding).show();

            });

            linIntegBw.setOnClickListener(v -> {

                new OffsetSetupIntegBwKeypad(mActivity, binding).show();

            });

            linOffsetSide.setOnClickListener(v -> {

                SideData.SIDE_OPTION offsetSide = SaDataHandler
                        .getInstance()
                        .getAclrConfigData()
                        .getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .getOffsetSide();

                switch (offsetSide) {

                    case NEG:
                        SaDataHandler
                                .getInstance()
                                .getAclrConfigData()
                                .getAclrMeasSetupData()
                                .getOffsetSetupData()
                                .setOffsetSide(SideData.SIDE_OPTION.BOTH);
                        break;

                    case BOTH:
                        SaDataHandler
                                .getInstance()
                                .getAclrConfigData()
                                .getAclrMeasSetupData()
                                .getOffsetSetupData()
                                .setOffsetSide(SideData.SIDE_OPTION.POS);
                        break;

                    case POS:
                        SaDataHandler
                                .getInstance()
                                .getAclrConfigData()
                                .getAclrMeasSetupData()
                                .getOffsetSetupData()
                                .setOffsetSide(SideData.SIDE_OPTION.NEG);
                        break;

                }

                updateOffsetSideOption();

            });

            linFailSource.setOnClickListener(v -> {

                ViewHandler.getInstance().getAclrFailSourceView().addMenu();

            });

            linAbsLimit.setOnClickListener(v -> {

                new AclrAbsLimitKeypad(mActivity, binding).show();

            });

            linRelLimit.setOnClickListener(v -> {

                new AclrRelLimitKeypad(mActivity, binding).show();

            });

        });

    }

    public void updateOffsetSideOption() {

        SideData.SIDE_OPTION offset =
                SaDataHandler.getInstance()
                        .getAclrConfigData()
                        .getAclrMeasSetupData()
                        .getOffsetSetupData()
                        .getOffsetSide();

        switch (offset) {

            case NEG:
                selectOptionView(tvOffsetSideNeg, tvOffsetSideBoth, tvOffsetSidePos);
                break;

            case BOTH:
                selectOptionView(tvOffsetSideBoth, tvOffsetSideNeg, tvOffsetSidePos);
                break;

            case POS:
                selectOptionView(tvOffsetSidePos, tvOffsetSideNeg, tvOffsetSideBoth);
                break;

        }

    }

}

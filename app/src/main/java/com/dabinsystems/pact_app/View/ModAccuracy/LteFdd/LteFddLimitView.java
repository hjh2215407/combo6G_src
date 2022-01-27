package com.dabinsystems.pact_app.View.ModAccuracy.LteFdd;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.LteFdd.Limit.LteFddFreqOffsetKeypad;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.LteFdd.Limit.LteFddMinRsEvmDialog;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.LteFdd.Limit.LteFddTaeKeypadDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

/**
 * right menu LTE FDD -> Setup -> Limit
 * - Freq. Offset
 * - Min. RS EVM
 * - TAE
 */
public class LteFddLimitView extends LayoutBase {

    private LinearLayout linFreqOffset, linMinRsEvm, linTae;
    private ArrayList<View> mFreqOffsetList, mMinRsEvmList, mTaeList;
    private AutofitTextView tvFreqOffsetView, tvMinRsEvm, tvTae;
    private DynamicView mDynamicView;

    public LteFddLimitView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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
                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.limit_name));

                binding.linRightMenu.addView(linFreqOffset);
                binding.linRightMenu.addView(linMinRsEvm);
                binding.linRightMenu.addView(linTae);

                binding.tvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ViewHandler.getInstance().getLteFddMeasSetupView().addMenu();

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

        mFreqOffsetList = mDynamicView.addRightMenuButton("Freq. Offset", "");
        linFreqOffset = (LinearLayout) mFreqOffsetList.get(0);
        tvFreqOffsetView = (AutofitTextView) mFreqOffsetList.get(2);

        mMinRsEvmList = mDynamicView.addRightMenuButton("Min. RS EVM", "");
        linMinRsEvm = (LinearLayout) mMinRsEvmList.get(0);
        tvMinRsEvm = (AutofitTextView) mMinRsEvmList.get(2);

        mTaeList = mDynamicView.addRightMenuButton("TAE", "");
        linTae = (LinearLayout) mTaeList.get(0);
        tvTae = (AutofitTextView) mTaeList.get(2);

        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linFreqOffset.setOnClickListener(v -> {

                new LteFddFreqOffsetKeypad(mActivity, binding).show();

            });

            linMinRsEvm.setOnClickListener(v -> {

                new LteFddMinRsEvmDialog(mActivity, binding).show();

            });

            linTae.setOnClickListener(v -> {

                new LteFddTaeKeypadDialog(mActivity, binding).show();

            });

        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void update() {
        super.update();

        mActivity.runOnUiThread(() -> {

            tvFreqOffsetView.setText(DataHandler.getInstance().getLteFddData().getLimitData().getFreqOffset() + " ppm");
            tvMinRsEvm.setText(DataHandler.getInstance().getLteFddData().getLimitData().getMinimumRsEvm() + " %");
            tvTae.setText(DataHandler.getInstance().getLteFddData().getLimitData().getTae() + " ns");

        });

    }

}

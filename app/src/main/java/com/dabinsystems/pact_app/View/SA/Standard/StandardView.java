package com.dabinsystems.pact_app.View.SA.Standard;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class StandardView extends LayoutBase {

    private LinearLayout linNone, linWcdma, linLte, lin5gnr;
    private AutofitTextView tvNone, tvWcdma, tvLte, tv5gnr;
    private ArrayList<View> mNoneList, mWcdmaList, mLteList, m5gnrList;
    private DynamicView mDynamicView;

    public StandardView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {

                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.standard_title));
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linNone);
                binding.linRightMenu.addView(linWcdma);
                binding.linRightMenu.addView(linLte);
                binding.linRightMenu.addView(lin5gnr);

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

        mNoneList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.none));
        linNone = (LinearLayout) mNoneList.get(0);
        tvNone = (AutofitTextView) mNoneList.get(1);

        mWcdmaList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.wcdma));
        linWcdma = (LinearLayout) mWcdmaList.get(0);
        tvWcdma = (AutofitTextView) mWcdmaList.get(1);

        mLteList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.lte));
        linLte = (LinearLayout) mLteList.get(0);
        tvLte = (AutofitTextView) mLteList.get(1);

        m5gnrList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.nr_5g_title));
        lin5gnr = (LinearLayout) m5gnrList.get(0);
        tv5gnr = (AutofitTextView) m5gnrList.get(1);

        setUpEvents();

    }

    public void update() {

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linNone.setOnClickListener(v -> {


            });

            linWcdma.setOnClickListener(v -> {
                DataHandler.getInstance().getStandardLoadData().loadWcdmaData();

                //@@ [21.12.30] SEM Box fix
                SaDataHandler.getInstance().getConfigData().getSemMeasSetupData().getEditMaskData().setSemSpan();
                //@@

                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getStandardView().addMenu();
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );
            });

            linLte.setOnClickListener(v -> {

                ViewHandler.getInstance().getLteStandardView().addMenu();

            });

            lin5gnr.setOnClickListener(v -> {

                ViewHandler.getInstance().getNrStandardView1().addMenu();

            });

        });

    }

}

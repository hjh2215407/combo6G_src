package com.dabinsystems.pact_app.View.ModAccuracy.NR5G.tae;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

/**
 * right menu 5G NR -> Setup -> TAE -> Inter-TAE -> Select Carrier
 * - 1(Ref)
 * - 2
 * - 3
 * - 4
 */
public class SelectCarrierView extends LayoutBase {

    private LinearLayout linCarrier1, linCarrier2, linCarrier3, linCarrier4;
    private AutofitTextView tvCarrier1, tvCarrier2, tvCarrier3, tvCarrier4;
    private ArrayList<View> mCarrierList1, mCarrierList2, mCarrierList3, mCarrierList4;
    private DynamicView mDynamicView;

    public SelectCarrierView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            mActivity.runOnUiThread(() -> {

                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linCarrier1);
                binding.linRightMenu.addView(linCarrier2);
                binding.linRightMenu.addView(linCarrier3);
                binding.linRightMenu.addView(linCarrier4);

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mCarrierList1 = mDynamicView.addRightMenuButton("1(Ref)", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linCarrier1 = (LinearLayout) mCarrierList1.get(0);
        tvCarrier1 = (AutofitTextView) mCarrierList1.get(1);

        mCarrierList2 = mDynamicView.addRightMenuButton("2", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linCarrier2 = (LinearLayout) mCarrierList2.get(0);
        tvCarrier2 = (AutofitTextView) mCarrierList2.get(1);

        mCarrierList3 = mDynamicView.addRightMenuButton("3", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linCarrier3 = (LinearLayout) mCarrierList3.get(0);
        tvCarrier3 = (AutofitTextView) mCarrierList3.get(1);

        mCarrierList4 = mDynamicView.addRightMenuButton("4", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linCarrier4 = (LinearLayout) mCarrierList4.get(0);
        tvCarrier4 = (AutofitTextView) mCarrierList4.get(1);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linCarrier1.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getInterTaeData().setSelectedCarrierIdx(0);

                InitActivity.logMsg("Carrier", "Carrier set 0 ... " + DataHandler.getInstance().getNrData().getInterTaeData().getSelectedCarrierIdx());
                ViewHandler.getInstance().getInterTaeView().addMenu();

            });

            linCarrier2.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getInterTaeData().setSelectedCarrierIdx(1);
                InitActivity.logMsg("Carrier", "Carrier set 1 ... " + DataHandler.getInstance().getNrData().getInterTaeData().getSelectedCarrierIdx());
                ViewHandler.getInstance().getInterTaeView().addMenu();

            });

            linCarrier3.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getInterTaeData().setSelectedCarrierIdx(2);
                InitActivity.logMsg("Carrier", "Carrier set 2 ... " + DataHandler.getInstance().getNrData().getInterTaeData().getSelectedCarrierIdx());
                ViewHandler.getInstance().getInterTaeView().addMenu();

            });

            linCarrier4.setOnClickListener(v -> {

                DataHandler.getInstance().getNrData().getInterTaeData().setSelectedCarrierIdx(3);

                InitActivity.logMsg("Carrier", "Carrier set 3 ... " + DataHandler.getInstance().getNrData().getInterTaeData().getSelectedCarrierIdx());
                ViewHandler.getInstance().getInterTaeView().addMenu();

            });


            binding.tvBack.setOnClickListener(v -> {
                ViewHandler.getInstance().getInterTaeView().addMenu();
            });

        });

    }
}

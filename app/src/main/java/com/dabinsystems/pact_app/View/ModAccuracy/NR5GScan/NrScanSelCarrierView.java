package com.dabinsystems.pact_app.View.ModAccuracy.NR5GScan;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanCarrierData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

/**
 * [jigum] 2021-07-23
 * <p>
 * right menu 5G NR Scan -> Setup -> Carriers -> Select Carrier
 * - 1
 * - 2
 * - 3
 * - 4
 */
public class NrScanSelCarrierView extends LayoutBase implements View.OnClickListener {

    LinearLayout lin1, lin2, lin3, lin4;
    AutofitTextView tv1, tv2, tv3, tv4;
    boolean isInitView = false;

    public NrScanSelCarrierView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();
        new Thread(() -> {
            initView();
            update();
            mActivity.runOnUiThread(() -> {
                try {
                    binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.select_carrier));
                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(lin1);
                    binding.linRightMenu.addView(lin2);
                    binding.linRightMenu.addView(lin3);
                    binding.linRightMenu.addView(lin4);

                    binding.tvBack.setOnClickListener(v -> {
                        ViewHandler.getInstance().getNrScanCarrierView().addMenu();
                    });

                } catch (NullPointerException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    @SuppressLint("SetTextI18n")
    public void update() {
        initView();
        mActivity.runOnUiThread(() -> {
            try {
                NrScanCarrierData data = DataHandler.getInstance().getNrScanData().getCarrierData();
                int selIdx = data.getSelectedCarrierIdx();

                lin1.setSelected(selIdx == 0);
                lin2.setSelected(selIdx == 1);
                lin3.setSelected(selIdx == 2);
                lin4.setSelected(selIdx == 3);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initView() {
        super.initView();

        if (isInitView)
            return;
        isInitView = true;

        DynamicView mDynamicView = new DynamicView(mActivity);

        ArrayList<View> listView = mDynamicView.addRightMenuButton("1");
        lin1 = (LinearLayout) listView.get(0);
        lin1.setOnClickListener(this);
        tv1 = (AutofitTextView) listView.get(1);

        listView = mDynamicView.addRightMenuButton("2");
        lin2 = (LinearLayout) listView.get(0);
        lin2.setOnClickListener(this);
        tv2 = (AutofitTextView) listView.get(1);

        listView = mDynamicView.addRightMenuButton("3");
        lin3 = (LinearLayout) listView.get(0);
        lin3.setOnClickListener(this);
        tv3 = (AutofitTextView) listView.get(1);

        listView = mDynamicView.addRightMenuButton("4");
        lin4 = (LinearLayout) listView.get(0);
        lin4.setOnClickListener(this);
        tv4 = (AutofitTextView) listView.get(1);
    }

    @Override
    public void onClick(View v) {
        NrScanCarrierData data = DataHandler.getInstance().getNrScanData().getCarrierData();

        if (v == lin1) {
            data.setSelectedCarrierIdx(0);
        } else if (v == lin2) {
            data.setSelectedCarrierIdx(1);
        } else if (v == lin3) {
            data.setSelectedCarrierIdx(2);
        } else if (v == lin4) {
            data.setSelectedCarrierIdx(3);
        }

        ViewHandler.getInstance().getNrScanCarrierView().addMenu();
    }
}

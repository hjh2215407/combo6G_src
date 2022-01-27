package com.dabinsystems.pact_app.View.System.RF;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.Nuclear.NuclearData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class SelectRfView extends LayoutBase {

    private LinearLayout linRf1, linRf2, linRf3, linRf4;
    private DynamicView mDynamicView;

    public SelectRfView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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
                binding.tvRightMenuTitle.setText("RF");

                binding.linRightMenu.addView(linRf1);
                binding.linRightMenu.addView(linRf2);
                binding.linRightMenu.addView(linRf3);
                binding.linRightMenu.addView(linRf4);

                binding.tvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewHandler.getInstance().getSystemView().addMenu();
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

        ArrayList<View> mRfList1 = mDynamicView.addRightMenuButton("1", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        ArrayList<View> mRfList2 = mDynamicView.addRightMenuButton("2", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        ArrayList<View> mRfList3 = mDynamicView.addRightMenuButton("3", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        ArrayList<View> mRfList4 = mDynamicView.addRightMenuButton("4", Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        linRf1 = (LinearLayout) mRfList1.get(0);
        linRf2 = (LinearLayout) mRfList2.get(0);
        linRf3 = (LinearLayout) mRfList3.get(0);
        linRf4 = (LinearLayout) mRfList4.get(0);

        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            NuclearData data = DataHandler.getInstance().getNuclearData();

            linRf1.setOnClickListener(v -> {

                data.setSelectedRf(0);

                ViewHandler.getInstance().getSystemView().addMenu();
            });

            linRf2.setOnClickListener(v -> {

                data.setSelectedRf(1);

                ViewHandler.getInstance().getSystemView().addMenu();
            });

            linRf3.setOnClickListener(v -> {

                data.setSelectedRf(2);

                ViewHandler.getInstance().getSystemView().addMenu();
            });

            linRf4.setOnClickListener(v -> {

                data.setSelectedRf(3);

                ViewHandler.getInstance().getSystemView().addMenu();
            });
        });

    }

    @Override
    public void update() {
        super.update();
    }
}

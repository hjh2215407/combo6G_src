package com.dabinsystems.pact_app.View.System.Connection;

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

public class SelectConnectionView extends LayoutBase {

    private LinearLayout linLocal, linRemote;
    private DynamicView mDynamicView;

    public SelectConnectionView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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
                binding.tvRightMenuTitle.setText("Connection");

                binding.linRightMenu.addView(linLocal);
                binding.linRightMenu.addView(linRemote);

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

        ArrayList<View> mLocalList1 = mDynamicView.addRightMenuButton("Local", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        ArrayList<View> mRemoteList2 = mDynamicView.addRightMenuButton("Remote", Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        linLocal = (LinearLayout) mLocalList1.get(0);
        linRemote = (LinearLayout) mRemoteList2.get(0);

        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            NuclearData data = DataHandler.getInstance().getNuclearData();

            linLocal.setOnClickListener(v -> {

                data.setConnection(NuclearData.CONNECTION.LOCAL);

                ViewHandler.getInstance().getSystemView().addMenu();
            });

            linRemote.setOnClickListener(v -> {

                data.setConnection(NuclearData.CONNECTION.REMOTE);

                ViewHandler.getInstance().getSystemView().addMenu();
            });

        });

    }

    @Override
    public void update() {
        super.update();
    }

}

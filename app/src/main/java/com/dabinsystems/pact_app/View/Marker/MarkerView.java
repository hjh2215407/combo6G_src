package com.dabinsystems.pact_app.View.Marker;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MarkerView extends LayoutBase {

    private LinearLayout linMarkerSetup, linMarkerSearch;
    private DynamicView mDynamicView;

    public MarkerView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
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
                binding.linRightMenu.addView(linMarkerSetup);
                binding.linRightMenu.addView(linMarkerSearch);

                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.marker_name));
                binding.linCalibration.setVisibility(View.GONE);
                binding.linCableList.setVisibility(View.GONE);

            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity.getApplicationContext());

        ArrayList<View> mMarkerSetupList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.marker_setup));
        ArrayList<View> mMarkerSearchList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.marker_search));

        linMarkerSetup = (LinearLayout) mMarkerSetupList.get(0);
        linMarkerSearch = (LinearLayout) mMarkerSearchList.get(0);

        setUpEvents();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linMarkerSetup.setOnClickListener(v -> ViewHandler.getInstance().getMarkerSetupView().addMenu());

            linMarkerSearch.setOnClickListener(v -> ViewHandler.getInstance().getMarkerSearchView().addMenu());

            binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getMarkerView().addMenu());

        });
    }
}

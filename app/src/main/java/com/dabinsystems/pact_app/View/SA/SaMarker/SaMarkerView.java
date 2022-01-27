package com.dabinsystems.pact_app.View.SA.SaMarker;

import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SaMarkerView extends LayoutBase {

    private LinearLayout linSelectMarker, linNormal, linDelta, linFixed, linOff, linMore;
    //private ArrayList<View> mSelectList, mNormalList, mDeltaList, mFixedList, mOffList, mMoreList;
    private AutofitTextView tvSelectMarkerVal; //, tvFixed, tvDelta, tvNormal;
    private DynamicView mDynamicView;

    public SaMarkerView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            try {

                initView();
                update();

                mActivity.runOnUiThread(() -> {
                    ViewHandler.getInstance().getSAView().selectMarker();

                    binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.marker_name));

                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(linSelectMarker);
                    binding.linRightMenu.addView(linNormal);
                    binding.linRightMenu.addView(linDelta);
                    binding.linRightMenu.addView(linFixed);
                    binding.linRightMenu.addView(linOff);
                    binding.linRightMenu.addView(linMore);

                    binding.linCableList.setVisibility(View.GONE);
                });

            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity.getApplicationContext());

        ArrayList<View> mSelectList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.select_marker_name), "");
        ArrayList<View> mNormalList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.normal_name));
        ArrayList<View> mDeltaList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.delta_name));
        ArrayList<View> mFixedList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.fixed_name));
        ArrayList<View> mOffList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.off_name));
        ArrayList<View> mMoreList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.more_name), "1/2");

        linSelectMarker = (LinearLayout) mSelectList.get(0);
        tvSelectMarkerVal = (AutofitTextView) mSelectList.get(2);

        linNormal = (LinearLayout) mNormalList.get(0);

        linDelta = (LinearLayout) mDeltaList.get(0);

        linFixed = (LinearLayout) mFixedList.get(0);

        linOff = (LinearLayout) mOffList.get(0);
        linMore = (LinearLayout) mMoreList.get(0);

        setUpEvents();
    }

    @Override
    public void setUpEvents() throws NullPointerException {

        mActivity.runOnUiThread(() -> {

            linSelectMarker.setOnClickListener(v -> ViewHandler.getInstance().getSaSelectMarkerView().addMenu());

            linNormal.setOnClickListener(v -> {
                linNormal.setSelected(true);

                FunctionHandler.getInstance().getMainLineChart().resetMarkOption();
                ViewHandler.getInstance().getContent().markerIconUpdate();
                ViewHandler.getInstance().getContent().markerTableUpdate();
                ViewHandler.getInstance().getContent().markerValueUpdate();
                update();
            });

            linDelta.setOnClickListener(v -> {
                boolean isDelta = !FunctionHandler.getInstance().getMainLineChart().isDelta();

                linDelta.setSelected(isDelta);

                FunctionHandler.getInstance().getMainLineChart().setDeltaMarker(isDelta);
//                ViewHandler.getInstance().getContent().markerUnitUpdate();
                ViewHandler.getInstance().getContent().markerIconUpdate();
                ViewHandler.getInstance().getContent().markerTableUpdate();
                ViewHandler.getInstance().getContent().markerValueUpdate();

                //@@ [21.12.17] Delta 이동 관련 delta 만들기
                int currentRefIndex = FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex();

                FunctionHandler.getInstance().getMainLineChart().setMarker(currentRefIndex);
                ViewHandler.getInstance().getSaMarkerView2().update();
                ViewHandler.getInstance().getContent().update();
                //@@

                update();
            });

            linFixed.setOnClickListener(v -> {
                boolean isFixed = !FunctionHandler.getInstance().getMainLineChart().isFixed();

                linFixed.setSelected(isFixed);

                FunctionHandler.getInstance().getMainLineChart().setFixedMarker(isFixed);
                ViewHandler.getInstance().getContent().markerTableUpdate();
                update();
                ViewHandler.getInstance().getContent().markerIconUpdate();
                ViewHandler.getInstance().getContent().markerTableUpdate();
                ViewHandler.getInstance().getContent().markerValueUpdate();
            });

            linOff.setOnClickListener(v -> {
                FunctionHandler.getInstance().getMainLineChart().removeSelectedMarker();
                update();
                ViewHandler.getInstance().getContent().markerIconUpdate();
                ViewHandler.getInstance().getContent().markerTableUpdate();
            });

            linMore.setOnClickListener(v -> {
                ViewHandler.getInstance().getSaMarkerView2().addMenu();
            });

            binding.tvBack.setOnClickListener(v -> {
                ViewHandler.getInstance().getSaMarkerView().addMenu();
            });

        });

    }

    public void update() {

        initView();

        mActivity.runOnUiThread(() -> {

            if (FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex() == -1)
                tvSelectMarkerVal.setText("");
            else
                tvSelectMarkerVal.setText("Mkr " + (FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex() + 1));

            linFixed.setSelected(FunctionHandler.getInstance().getMainLineChart().isFixed());
            FunctionHandler.getInstance().getMainLineChart().setFixedMarker(FunctionHandler.getInstance().getMainLineChart().isFixed());
            ViewHandler.getInstance().getContent().markerTableUpdate();

            linDelta.setSelected(FunctionHandler.getInstance().getMainLineChart().isDelta());

            linNormal.setSelected(FunctionHandler.getInstance().getMainLineChart().getSelectedMarkerIndex() != -1 &&
                    !FunctionHandler.getInstance().getMainLineChart().isDelta() && !FunctionHandler.getInstance().getMainLineChart().isFixed());
        });
    }
}

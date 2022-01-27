package com.dabinsystems.pact_app.View.SA.SaMarker;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SaSelectMarkerView extends LayoutBase {

    private LinearLayout linMarker1, linMarker2, linMarker3, linMarker4, linMarker5;
    private AutofitTextView tvMarker1, tvMarker2, tvMarker3, tvMarker4, tvMarker5;
    private ArrayList<View> mMarkerList1, mMarkerList2, mMarkerList3, mMarkerList4, mMarkerList5;
    private DynamicView mDynamicView;

    public SaSelectMarkerView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            mActivity.runOnUiThread(() -> {

                ViewHandler.getInstance().getSAView().selectMarker();
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linMarker1);
                binding.linRightMenu.addView(linMarker2);
                binding.linRightMenu.addView(linMarker3);
                binding.linRightMenu.addView(linMarker4);
                binding.linRightMenu.addView(linMarker5);

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mMarkerList1 = mDynamicView.addRightMenuButton("Marker 1", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linMarker1 = (LinearLayout) mMarkerList1.get(0);
        tvMarker1 = (AutofitTextView) mMarkerList1.get(1);

        mMarkerList2 = mDynamicView.addRightMenuButton("Marker 2", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linMarker2 = (LinearLayout) mMarkerList2.get(0);
        tvMarker2 = (AutofitTextView) mMarkerList2.get(1);

        mMarkerList3 = mDynamicView.addRightMenuButton("Marker 3", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linMarker3 = (LinearLayout) mMarkerList3.get(0);
        tvMarker3 = (AutofitTextView) mMarkerList3.get(1);

        mMarkerList4 = mDynamicView.addRightMenuButton("Marker 4", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linMarker4 = (LinearLayout) mMarkerList4.get(0);
        tvMarker4 = (AutofitTextView) mMarkerList4.get(1);

        mMarkerList5 = mDynamicView.addRightMenuButton("Marker 5", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linMarker5 = (LinearLayout) mMarkerList5.get(0);
        tvMarker5 = (AutofitTextView) mMarkerList5.get(1);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linMarker1.setOnClickListener(v -> {

                FunctionHandler.getInstance().getMainLineChart().setMarker(0);
                ViewHandler.getInstance().getSaMarkerView2().update();
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getSaMarkerView().addMenu();

            });

            linMarker2.setOnClickListener(v -> {

                FunctionHandler.getInstance().getMainLineChart().setMarker(1);
                ViewHandler.getInstance().getSaMarkerView2().update();
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getSaMarkerView().addMenu();

            });

            linMarker3.setOnClickListener(v -> {

                FunctionHandler.getInstance().getMainLineChart().setMarker(2);
                ViewHandler.getInstance().getSaMarkerView2().update();
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getSaMarkerView().addMenu();

            });

            linMarker4.setOnClickListener(v -> {

                FunctionHandler.getInstance().getMainLineChart().setMarker(3);
                ViewHandler.getInstance().getSaMarkerView2().update();
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getSaMarkerView().addMenu();

            });

            linMarker5.setOnClickListener(v -> {

                FunctionHandler.getInstance().getMainLineChart().setMarker(4);
                ViewHandler.getInstance().getSaMarkerView2().update();
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getSaMarkerView().addMenu();

            });

            binding.tvBack.setOnClickListener(v -> {
                ViewHandler.getInstance().getSaMarkerView().addMenu();
            });

        });

    }
}

package com.dabinsystems.pact_app.View.Marker;

import android.os.Handler;
import android.os.Looper;
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

public class SelectMarkerView extends LayoutBase {

    private LinearLayout linMarker1, linMarker2, linMarker3, linMarker4, linMarker5;
    private DynamicView mDynamicView;

    public SelectMarkerView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            mActivity.runOnUiThread(() -> {

                ViewHandler.getInstance().getVswrView().selectMarker();
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

        ArrayList<View> mMarkerList1 = mDynamicView.addRightMenuButton("Marker 1", Gravity.END | Gravity.CENTER_VERTICAL);
        linMarker1 = (LinearLayout) mMarkerList1.get(0);

        ArrayList<View> mMarkerList2 = mDynamicView.addRightMenuButton("Marker 2", Gravity.END | Gravity.CENTER_VERTICAL);
        linMarker2 = (LinearLayout) mMarkerList2.get(0);

        ArrayList<View> mMarkerList3 = mDynamicView.addRightMenuButton("Marker 3", Gravity.END | Gravity.CENTER_VERTICAL);
        linMarker3 = (LinearLayout) mMarkerList3.get(0);

        ArrayList<View> mMarkerList4 = mDynamicView.addRightMenuButton("Marker 4", Gravity.END | Gravity.CENTER_VERTICAL);
        linMarker4 = (LinearLayout) mMarkerList4.get(0);

        ArrayList<View> mMarkerList5 = mDynamicView.addRightMenuButton("Marker 5", Gravity.END | Gravity.CENTER_VERTICAL);
        linMarker5 = (LinearLayout) mMarkerList5.get(0);

        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linMarker1.setOnClickListener(v -> new Handler(Looper.getMainLooper()).post(()->{
                FunctionHandler.getInstance().getMainLineChart().setMarker(0);
                ViewHandler.getInstance().getMarkerSetupView().update();
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getMarkerView().addMenu();
            }));

            linMarker2.setOnClickListener(v -> new Handler(Looper.getMainLooper()).post(()->{
                FunctionHandler.getInstance().getMainLineChart().setMarker(1);
                ViewHandler.getInstance().getSaMarkerView2().update();
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getMarkerView().addMenu();
            }));

            linMarker3.setOnClickListener(v -> new Handler(Looper.getMainLooper()).post(()->{
                FunctionHandler.getInstance().getMainLineChart().setMarker(2);
                ViewHandler.getInstance().getSaMarkerView2().update();
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getMarkerView().addMenu();
            }));

            linMarker4.setOnClickListener(v -> new Handler(Looper.getMainLooper()).post(()->{
                FunctionHandler.getInstance().getMainLineChart().setMarker(3);
                ViewHandler.getInstance().getSaMarkerView2().update();
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getMarkerView().addMenu();
            }));

            linMarker5.setOnClickListener(v -> new Handler(Looper.getMainLooper()).post(()->{
                FunctionHandler.getInstance().getMainLineChart().setMarker(4);
                ViewHandler.getInstance().getSaMarkerView2().update();
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getMarkerView().addMenu();
            }));

            binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getMarkerSetupView().addMenu());
        });
    }
}

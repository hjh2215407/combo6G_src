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

public class SaMarkerTraceView extends LayoutBase {

    private LinearLayout linTrace1, linTrace2, linTrace3, linTrace4;
    private AutofitTextView tvTrace1, tvTrace2, tvTrace3, tvTrace4;
    private ArrayList<View> mTraceList1, mTraceList2, mTraceList3, mTraceList4;
    private DynamicView mDynamicView;

    public SaMarkerTraceView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            mActivity.runOnUiThread(() -> {

                binding.tvRightMenuTitle.setText("Marker Trace");
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linTrace1);
                binding.linRightMenu.addView(linTrace2);
                binding.linRightMenu.addView(linTrace3);
                binding.linRightMenu.addView(linTrace4);

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mTraceList1 = mDynamicView.addRightMenuButton("Trace 1", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linTrace1 = (LinearLayout) mTraceList1.get(0);
        tvTrace1 = (AutofitTextView) mTraceList1.get(1);

        mTraceList2 = mDynamicView.addRightMenuButton("Trace 2", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linTrace2 = (LinearLayout) mTraceList2.get(0);
        tvTrace2 = (AutofitTextView) mTraceList2.get(1);

        mTraceList3 = mDynamicView.addRightMenuButton("Trace 3", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linTrace3 = (LinearLayout) mTraceList3.get(0);
        tvTrace3 = (AutofitTextView) mTraceList3.get(1);

        mTraceList4 = mDynamicView.addRightMenuButton("Trace 4", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linTrace4 = (LinearLayout) mTraceList4.get(0);
        tvTrace4 = (AutofitTextView) mTraceList4.get(1);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {


            linTrace1.setOnClickListener(v -> {
                FunctionHandler.getInstance().getMainLineChart().setMarkerTrace(0);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getSaMarkerView2().addMenu();

            });

            linTrace2.setOnClickListener(v -> {

                FunctionHandler.getInstance().getMainLineChart().setMarkerTrace(1);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getSaMarkerView2().addMenu();

            });

            linTrace3.setOnClickListener(v -> {

                FunctionHandler.getInstance().getMainLineChart().setMarkerTrace(2);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getSaMarkerView2().addMenu();

            });

            linTrace4.setOnClickListener(v -> {

                FunctionHandler.getInstance().getMainLineChart().setMarkerTrace(3);
                ViewHandler.getInstance().getContent().update();
                ViewHandler.getInstance().getSaMarkerView2().addMenu();

            });

            binding.tvBack.setOnClickListener(v -> {
                ViewHandler.getInstance().getSaMarkerView2().addMenu();
            });

        });

    }
}

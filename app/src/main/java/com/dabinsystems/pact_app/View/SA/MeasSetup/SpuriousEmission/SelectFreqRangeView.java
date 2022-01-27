package com.dabinsystems.pact_app.View.SA.MeasSetup.SpuriousEmission;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SelectFreqRangeView extends LayoutBase {

    private LinearLayout linFreqRange1, linFreqRange2, linFreqRange3, linFreqRange4, linFreqRange5, linFreqRange6;
    private AutofitTextView tvFreqRange1, tvFreqRange2, tvFreqRange3, tvFreqRange4, tvFreqRange5, tvFreqRange6;
    private ArrayList<View> mFreqRangeList1, mFreqRangeList2, mFreqRangeList3, mFreqRangeList4, mFreqRangeList5, mFreqRangeList6;
    private DynamicView mDynamicView;

    public SelectFreqRangeView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            mActivity.runOnUiThread(() -> {

                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linFreqRange1);
                binding.linRightMenu.addView(linFreqRange2);
                binding.linRightMenu.addView(linFreqRange3);
                binding.linRightMenu.addView(linFreqRange4);
                binding.linRightMenu.addView(linFreqRange5);
                binding.linRightMenu.addView(linFreqRange6);

                binding.tvBack.setOnClickListener(v -> {
                    ViewHandler.getInstance().getFreqRangeTableView().addMenu();
                });

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mFreqRangeList1 = mDynamicView.addRightMenuButton("1", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linFreqRange1 = (LinearLayout) mFreqRangeList1.get(0);
        tvFreqRange1 = (AutofitTextView) mFreqRangeList1.get(1);

        mFreqRangeList2 = mDynamicView.addRightMenuButton("2", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linFreqRange2 = (LinearLayout) mFreqRangeList2.get(0);
        tvFreqRange2 = (AutofitTextView) mFreqRangeList2.get(1);

        mFreqRangeList3 = mDynamicView.addRightMenuButton("3", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linFreqRange3 = (LinearLayout) mFreqRangeList3.get(0);
        tvFreqRange3 = (AutofitTextView) mFreqRangeList3.get(1);

        mFreqRangeList4 = mDynamicView.addRightMenuButton("4", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linFreqRange4 = (LinearLayout) mFreqRangeList4.get(0);
        tvFreqRange4 = (AutofitTextView) mFreqRangeList4.get(1);

        mFreqRangeList5 = mDynamicView.addRightMenuButton("5", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linFreqRange5 = (LinearLayout) mFreqRangeList5.get(0);
        tvFreqRange5 = (AutofitTextView) mFreqRangeList5.get(1);

        mFreqRangeList6 = mDynamicView.addRightMenuButton("6", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linFreqRange6 = (LinearLayout) mFreqRangeList6.get(0);
        tvFreqRange6 = (AutofitTextView) mFreqRangeList6.get(1);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linFreqRange1.setOnClickListener(v -> {

                new Handler(Looper.getMainLooper()).post(()->{

                    SaDataHandler.getInstance().getSpuriousEmissionConfigData().getSpuriousEmissionData().setSelectedIndex(0);
                    ViewHandler.getInstance().getFreqRangeTableView().addMenu();

                });

            });

            linFreqRange2.setOnClickListener(v -> {

                new Handler(Looper.getMainLooper()).post(()->{

                    SaDataHandler.getInstance().getSpuriousEmissionConfigData().getSpuriousEmissionData().setSelectedIndex(1);
                    ViewHandler.getInstance().getFreqRangeTableView().addMenu();

                });


            });

            linFreqRange3.setOnClickListener(v -> {

                new Handler(Looper.getMainLooper()).post(()->{

                    SaDataHandler.getInstance().getSpuriousEmissionConfigData().getSpuriousEmissionData().setSelectedIndex(2);
                    ViewHandler.getInstance().getFreqRangeTableView().addMenu();

                });

            });

            linFreqRange4.setOnClickListener(v -> {

                new Handler(Looper.getMainLooper()).post(()->{

                    SaDataHandler.getInstance().getSpuriousEmissionConfigData().getSpuriousEmissionData().setSelectedIndex(3);
                    ViewHandler.getInstance().getFreqRangeTableView().addMenu();
                });


            });

            linFreqRange5.setOnClickListener(v -> {

                new Handler(Looper.getMainLooper()).post(()->{

                    SaDataHandler.getInstance().getSpuriousEmissionConfigData().getSpuriousEmissionData().setSelectedIndex(4);
                    ViewHandler.getInstance().getFreqRangeTableView().addMenu();

                });

            });

            linFreqRange6.setOnClickListener(v -> {

                new Handler(Looper.getMainLooper()).post(()->{

                    SaDataHandler.getInstance().getSpuriousEmissionConfigData().getSpuriousEmissionData().setSelectedIndex(5);
                    ViewHandler.getInstance().getFreqRangeTableView().addMenu();

                });

            });

            binding.tvBack.setOnClickListener(v -> {
                ViewHandler.getInstance().getFreqRangeTableView().addMenu();
            });

        });

    }
}

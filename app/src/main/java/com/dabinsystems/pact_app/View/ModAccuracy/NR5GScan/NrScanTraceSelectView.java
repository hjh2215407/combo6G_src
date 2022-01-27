package com.dabinsystems.pact_app.View.ModAccuracy.NR5GScan;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NR_SCAN_TRACE;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NR_SCAN_TRACE;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanCarrierData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

/**
 * [jigum] 2021-07-27
 * <p>
 * right menu 5G NR Scan -> Setup -> Trace -> Data Format
 * - PCI
 * - SS-RSRP
 * - SS-SINR
 * - Timing Offset
 */
public class NrScanTraceSelectView extends LayoutBase implements View.OnClickListener {

    ArrayList<LinearLayout> layouts;
    ArrayList<AutofitTextView> tvs;
    boolean isInitView = false;

    public NrScanTraceSelectView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
        layouts = new ArrayList<>();
        tvs = new ArrayList<>();
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();
        new Thread(() -> {
            initView();
            update();
            mActivity.runOnUiThread(() -> {
                try {
                    binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.trace_name));
                    binding.linRightMenu.removeAllViews();

                    for (int i = 0; i < layouts.size(); ++i) {
                        binding.linRightMenu.addView(layouts.get(i));
                    }

                    binding.tvBack.setOnClickListener(v -> {
                        ViewHandler.getInstance().getNrScanTraceSetView().addMenu();
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
                NrScanData data = DataHandler.getInstance().getNrScanData();
                int selIdx = data.getSelectedTrace();
                NR_SCAN_TRACE nst = data.getTrace(selIdx);

                // 기존 선택 항목 표시
                for (int i = 0; i < layouts.size(); ++i) {
                    NR_SCAN_TRACE st = (NR_SCAN_TRACE) layouts.get(i).getTag();
                    layouts.get(i).setSelected(nst == st);
                }

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

        for (NR_SCAN_TRACE st : NR_SCAN_TRACE.values()) {
            ArrayList<View> listView = mDynamicView.addRightMenuButton(st.getString());
            LinearLayout layout = (LinearLayout) listView.get(0);
            layout.setTag(st);
            layout.setOnClickListener(this);
            layouts.add(layout);
            tvs.add((AutofitTextView) listView.get(1));
        }

    }

    @Override
    public void onClick(View v) {
        // 선택
        NR_SCAN_TRACE p = (NR_SCAN_TRACE) v.getTag();

        // 저장
        NrScanData data = DataHandler.getInstance().getNrScanData();
        int selIdx = data.getSelectedTrace();
        data.setTrace(selIdx, p);

        // 화면 이동
        ViewHandler.getInstance().getNrScanTraceSetView().addMenu();

        if (selIdx == 0) {
            FunctionHandler.getInstance().getNrScanTrace1CharFunc().changeTrace(false);
        } else {
            FunctionHandler.getInstance().getNrScanTrace2CharFunc().changeTrace(false);
        }
    }

}

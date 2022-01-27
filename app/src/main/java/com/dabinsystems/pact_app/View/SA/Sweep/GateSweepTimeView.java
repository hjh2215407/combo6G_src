package com.dabinsystems.pact_app.View.SA.Sweep;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.SaGateData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

/**
 * [jigum] 2021-10-19
 * <p>
 * right menu SA -> Setup -> Gate -> Gate View Sweep Time
 * - 1
 * - 5
 * - 10
 * - 15
 * - 20
 */
public class GateSweepTimeView extends LayoutBase implements View.OnClickListener {

    ArrayList<LinearLayout> layouts;
    ArrayList<AutofitTextView> tvs;
    boolean isInitView = false;

    public GateSweepTimeView(MainActivity activity, ActivityMainBinding binding) {
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
                    binding.tvRightMenuTitle.setText("Sweep Time");
                    binding.linRightMenu.removeAllViews();

                    for (int i = 0; i < layouts.size(); ++i) {
                        binding.linRightMenu.addView(layouts.get(i));
                    }

                    binding.tvBack.setOnClickListener(v -> {
                        ViewHandler.getInstance().getGateView().addMenu();
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
                SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();
                int selNum = gateData.getGateViewSweepTime() / 1000;

                // 기존 선택 항목 표시
                for (int i = 0; i < layouts.size(); ++i) {
                    int num = (int) layouts.get(i).getTag();
                    layouts.get(i).setSelected(selNum == num);
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

        int[] numOfGate = { 1, 5, 10, 15, 20 };

        for (int num : numOfGate) {
            ArrayList<View> listView = mDynamicView.addRightMenuButton(num + " ms");
            LinearLayout layout = (LinearLayout) listView.get(0);
            layout.setTag(num);
            layout.setOnClickListener(this);
            layouts.add(layout);
            tvs.add((AutofitTextView) listView.get(1));
        }

    }

    @Override
    public void onClick(View v) {
        // 선택된 value
        int value = (int) v.getTag();

        //Log.e("GateViewSweep", "Gate View Sweep Time Value : " + value);

        SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();
        gateData.setGateViewSweepTime(value * 1000);

        // 하단 차트 업데이트
        FunctionHandler.getInstance().getGateLineChart().update();

        // 전송
        FunctionHandler.getInstance().getDataConnector().sendCommand(
                DataHandler.getInstance().getConfigCmd()
        );

        // 화면 이동
        ViewHandler.getInstance().getGateView().addMenu();
    }

}

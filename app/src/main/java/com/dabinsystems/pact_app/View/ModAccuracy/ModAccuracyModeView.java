package com.dabinsystems.pact_app.View.ModAccuracy;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
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
 * right menu
 * - 5G NR – Mod. Accuracy
 * - 5G NR – TAE
 * - 5G NR Scan
 * - LTE FDD
 */
public class ModAccuracyModeView extends LayoutBase {

    private LinearLayout lin5GNR, linLteFdd, lin5GNRScan;
    private DynamicView mDynamicView;

    public ModAccuracyModeView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {

//                ViewHandler.getInstance().getSAView().selectMeasSetup();
                binding.linRightMenu.removeAllViews();

                binding.linRightMenu.addView(lin5GNR);
                binding.linRightMenu.addView(lin5GNRScan);
                binding.linRightMenu.addView(linLteFdd);

                binding.tvBack.setOnClickListener(v -> {

                });

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        // [jigum] 2021-07-16 기존 ‘5G NR’ 메뉴 이름을 ‘5G NR – Mod. Accuracy’로 변경
        ArrayList<View> m5GNRList = mDynamicView.addRightMenuButton("5G NR – Mod.\r\nAccuracy", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        lin5GNR = (LinearLayout) m5GNRList.get(0);

        // TODO 5G NR – TAE

        // TODO 5G NR Scan
        // [jigum] 2021-07-16 ‘5G NR Scan’ 메뉴 추가
        ArrayList<View> m5GNRScanList = mDynamicView.addRightMenuButton("5G NR Scan", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        lin5GNRScan = (LinearLayout) m5GNRScanList.get(0);

        ArrayList<View> mLteFddList = mDynamicView.addRightMenuButton("LTE FDD", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linLteFdd = (LinearLayout) mLteFddList.get(0);

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            lin5GNR.setOnClickListener(v -> {

                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if (type == MeasureType.MEASURE_TYPE.NR_5G) return;

                MeasureMode mode = FunctionHandler.getInstance().getMeasureMode();

                if (mode.getMode() != MeasureMode.MEASURE_MODE.MOD_ACCURACY)
                    mode.setMode(MeasureMode.MEASURE_MODE.MOD_ACCURACY);

                FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.NR_5G);
                update();
                ViewHandler.getInstance().getSAView().addMenu();
                ViewHandler.getInstance().getNrMeasSetupView().addMenu();
                ViewHandler.getInstance().getContent().update();

            });

            linLteFdd.setOnClickListener(v -> {

                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                if (type == MeasureType.MEASURE_TYPE.LTE_FDD) return;

                MeasureMode mode = FunctionHandler.getInstance().getMeasureMode();
                if (mode.getMode() != MeasureMode.MEASURE_MODE.MOD_ACCURACY)
                    mode.setMode(MeasureMode.MEASURE_MODE.MOD_ACCURACY);

                FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.LTE_FDD);
                update();
                ViewHandler.getInstance().getSAView().addMenu();
                ViewHandler.getInstance().getLteFddMeasSetupView().addMenu();
                ViewHandler.getInstance().getContent().update();

            });

            lin5GNRScan.setOnClickListener(v -> {
                // TODO 5G NR Scan 클릭
                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
                if (type == MeasureType.MEASURE_TYPE.NR_5G_SCAN) return;

                MeasureMode mode = FunctionHandler.getInstance().getMeasureMode();
                if (mode.getMode() != MeasureMode.MEASURE_MODE.MOD_ACCURACY)
                    mode.setMode(MeasureMode.MEASURE_MODE.MOD_ACCURACY);

                FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.NR_5G_SCAN);
                update();
                ViewHandler.getInstance().getSAView().addMenu();
                ViewHandler.getInstance().getNrScanMeasSetupView().addMenu();
                ViewHandler.getInstance().getContent().update();
            });

            binding.tvBack.setOnClickListener(v -> {
                ViewHandler.getInstance().getSaMeas().addMenu();
            });
        });
    }

    public void update() {
        mActivity.runOnUiThread(() -> {
            try {
                // 버튼 선택 여부 표시
                MeasureType.MEASURE_TYPE selType = FunctionHandler.getInstance().getMeasureType().getType();
                lin5GNR.setSelected(selType == MeasureType.MEASURE_TYPE.NR_5G);
                lin5GNRScan.setSelected(selType == MeasureType.MEASURE_TYPE.NR_5G_SCAN);
                linLteFdd.setSelected(selType == MeasureType.MEASURE_TYPE.LTE_FDD);
            } catch (NullPointerException e) {
            }
        });
    }

}


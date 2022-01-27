package com.dabinsystems.pact_app.View.ModAccuracy.NR5GScan;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NR_SCAN_TRACE;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanData;
import com.dabinsystems.pact_app.Dialog.Number2KeypadDialog;
import com.dabinsystems.pact_app.Dialog.NumberKeypadDialog;
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
 * [jigum] 2021-07-23
 * <p>
 * right menu 5G NR Scan -> Setup -> Trace1 / Trace2
 * - Data Format
 * - Scale
 */
public class NrScanTraceSetView extends LayoutBase {

    LinearLayout linDataFormat, linScale;
    AutofitTextView tvDataFormat, tvScale;
    boolean isInitView = false;

    public NrScanTraceSetView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();
        new Thread(() -> {
            initView();
            update();
            mActivity.runOnUiThread(() -> {
                try {
                    binding.tvRightMenuTitle.setText("Trace");
                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(linDataFormat);
                    binding.linRightMenu.addView(linScale);

                    binding.tvBack.setOnClickListener(v -> {
                        ViewHandler.getInstance().getNrScanMeasSetupView().addMenu();
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

                tvDataFormat.setText(data.getTrace(selIdx).getString());
                tvScale.setText(data.getBaseScaleToString(selIdx));

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

        ArrayList<View> listView = mDynamicView.addRightMenuButton("Data Format", "");
        linDataFormat = (LinearLayout) listView.get(0);
        tvDataFormat = (AutofitTextView) listView.get(2);

        listView = mDynamicView.addRightMenuButton("Scale", "");
        linScale = (LinearLayout) listView.get(0);
        tvScale = (AutofitTextView) listView.get(2);

        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();
        mActivity.runOnUiThread(() -> {

            linDataFormat.setOnClickListener(v -> {
                ViewHandler.getInstance().getNrScanTraceSelectView().addMenu();
            });

            /*tvScale.setOnClickListener(v -> {

                // 입력 창
                Number2KeypadDialog dlg = new Number2KeypadDialog(mActivity, binding);

                dlg.isViewDot = false;
                //dlg.isViewPlusMinus = false;
                *//*dlg.isViewUnit = false;*//*
                dlg.isViewUnit = true;


                dlg.setValueEnterListener(val -> {
                    int nVal = (int) val;

                    //@@ [22.01.17] 5GNR Scan Trace Scale 양수 값만 입력 되도록
                    if (val == 0 || val < 0) {
                        Toast.makeText(mActivity, "out of range", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // 입력 값 저장
                        NrScanData data = DataHandler.getInstance().getNrScanData();
                        int selIdx = data.getSelectedTrace();
                        data.setBaseScale(selIdx, nVal);

                        tvScale.setText(data.getBaseScaleToString(selIdx));

                        // 그래프에 적용
                        if (selIdx == 0)
                            FunctionHandler.getInstance().getNrScanTrace1CharFunc().reSetYMinMax(true);
                        else
                            FunctionHandler.getInstance().getNrScanTrace2CharFunc().reSetYMinMax(true);
                    }
                    //@@

                });
                dlg.show();

            });*/

            linScale.setOnClickListener(v -> {
                Number2KeypadDialog dlg = new Number2KeypadDialog(mActivity, binding);

                dlg.isViewDot = false;
                //dlg.isViewPlusMinus = false;
                /*dlg.isViewUnit = false;*/
                dlg.isViewUnit = true;


                dlg.setValueEnterListener(val -> {
                    int nVal = (int) val;

                    //@@ [22.01.17] 5GNR Scan Trace Scale 양수 값만 입력 되도록
                    if (val == 0 || val < 0) {
                        Toast.makeText(mActivity, "out of range", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // 입력 값 저장
                        NrScanData data = DataHandler.getInstance().getNrScanData();
                        int selIdx = data.getSelectedTrace();
                        data.setBaseScale(selIdx, nVal);

                        tvScale.setText(data.getBaseScaleToString(selIdx));

                        // 그래프에 적용
                        if (selIdx == 0)
                            FunctionHandler.getInstance().getNrScanTrace1CharFunc().reSetYMinMax(true);
                        else
                            FunctionHandler.getInstance().getNrScanTrace2CharFunc().reSetYMinMax(true);
                    }
                    //@@

                });
                dlg.show();
            });

        });
    }

}

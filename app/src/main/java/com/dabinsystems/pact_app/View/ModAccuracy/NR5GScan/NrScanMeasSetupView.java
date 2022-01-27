package com.dabinsystems.pact_app.View.ModAccuracy.NR5GScan;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
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
 * [jigum] 2021-07-22
 *
 * right menu 5G NR Scan -> Setup
 * - Start / Stop
 * - Carriers
 * - Trace 1
 * - Trace 2
 * - Amp Offset
 * - Save Log
 */

public class NrScanMeasSetupView extends LayoutBase {

    ArrayList<View> listStart, listCarriers, listTrace1, listTrace2, listAmpOffset, listSaveLog;
    LinearLayout linStart, linCarriers, linTrace1, linTrace2, linAmpOffset, linSaveLog;
    AutofitTextView tvStart, tvCarriersTitle, tvCarriersVal, tvTrace1, tvTrace2, tvAmpOffsetTitle, tvAmpOffsetVal, tvSaveLogOn, tvSaveLogOff;
    DynamicView mDynamicView;

    public NrScanMeasSetupView(MainActivity activity, ActivityMainBinding binding) {
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
                    binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.meas_setup));
                    binding.linRightMenu.removeAllViews();

                    binding.linRightMenu.addView(linStart);
                    binding.linRightMenu.addView(linCarriers);
                    binding.linRightMenu.addView(linTrace1);
                    binding.linRightMenu.addView(linTrace2);
                    binding.linRightMenu.addView(linAmpOffset);
                    binding.linRightMenu.addView(linSaveLog);
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

                tvStart.setText(data.isStart() ? "Stop" : "Start");
                //tvCarriers.setText();
                tvTrace1.setText(data.getTrace(0).getString());
                tvTrace2.setText(data.getTrace(1).getString());
                tvAmpOffsetVal.setText(data.getAmpOffset() + " dB");


                if (data.isSaveLog())
                    selectOptionView(tvSaveLogOn, tvSaveLogOff);
                else
                    selectOptionView(tvSaveLogOff, tvSaveLogOn);

                // Start 상태일 때는 ‘Carriers’ 와 ‘Amp Offset’을 비활성화하여 사용자가 설정할 수 없게 함
                linCarriers.setEnabled(!data.isStart());
                linAmpOffset.setEnabled(!data.isStart());
                enabledView(tvCarriersTitle, !data.isStart());
                enabledView(tvCarriersVal, !data.isStart());
                enabledView(tvAmpOffsetTitle, !data.isStart());
                enabledView(tvAmpOffsetVal, !data.isStart());

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        listStart = mDynamicView.addRightMenuButton("Start", "");
        linStart = (LinearLayout) listStart.get(0);
        tvStart = (AutofitTextView) listStart.get(1);

        listCarriers = mDynamicView.addRightMenuButton("Carriers", "");
        linCarriers = (LinearLayout) listCarriers.get(0);
        tvCarriersTitle = (AutofitTextView) listCarriers.get(1);
        tvCarriersVal = (AutofitTextView) listCarriers.get(2);

        listTrace1 = mDynamicView.addRightMenuButton("Trace1", "");
        linTrace1 = (LinearLayout) listTrace1.get(0);
        tvTrace1 = (AutofitTextView) listTrace1.get(2);

        listTrace2 = mDynamicView.addRightMenuButton("Trace2", "");
        linTrace2 = (LinearLayout) listTrace2.get(0);
        tvTrace2 = (AutofitTextView) listTrace2.get(2);

        listAmpOffset = mDynamicView.addRightMenuButton("AmpOffset", "");
        linAmpOffset = (LinearLayout) listAmpOffset.get(0);
        tvAmpOffsetTitle = (AutofitTextView) listAmpOffset.get(1);
        tvAmpOffsetVal = (AutofitTextView) listAmpOffset.get(2);

        listSaveLog = mDynamicView.addRightMenuButton("SaveLog", "On", "Off");
        linSaveLog = (LinearLayout) listSaveLog.get(0);
        tvSaveLogOn = (AutofitTextView) listSaveLog.get(2);
        tvSaveLogOff = (AutofitTextView) listSaveLog.get(3);

        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();
        mActivity.runOnUiThread(()-> {

            linStart.setOnClickListener(v->{
                NrScanData data = DataHandler.getInstance().getNrScanData();
                boolean isStart = !data.isStart();
                data.setStart(isStart);

                update();//tvStart.setText(data.isStart() ? "Stop" : "Start");//update();

                if (isStart) {
                    // 기존 정보 삭제
                    FunctionHandler.getInstance().getNrScanFunc().clear();
                    FunctionHandler.getInstance().getNrScanTrace1CharFunc().changeTrace(true);
                    FunctionHandler.getInstance().getNrScanTrace2CharFunc().changeTrace(true);

                    // Start 일 경우만 전송
                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            DataHandler.getInstance().getConfigCmd()
                    );
                }
            });

            linCarriers.setOnClickListener(v->{
                ViewHandler.getInstance().getNrScanCarrierView().addMenu();
            });

            linTrace1.setOnClickListener(v->{
                DataHandler.getInstance().getNrScanData().setSelectedTrace(0);
                ViewHandler.getInstance().getNrScanTraceSetView().addMenu();
            });

            linTrace2.setOnClickListener(v->{
                DataHandler.getInstance().getNrScanData().setSelectedTrace(1);
                ViewHandler.getInstance().getNrScanTraceSetView().addMenu();
            });

            linAmpOffset.setOnClickListener(v->{
                // 입력 창 Amp. Offset Range : -100 ~ + 100. Step : 0.01
                Number2KeypadDialog dlg = new Number2KeypadDialog(mActivity, binding);
                dlg.setValueEnterListener(val -> {
                    float fVal = (float)val;

                    NrScanData data = DataHandler.getInstance().getNrScanData();
                    float min = data.getAmpOffsetMin();
                    float max = data.getAmpOffsetMax();

                    if (fVal < min || fVal > max) {
                        Toast.makeText(MainActivity.getContext(), "Out of range(" + min + " ~ " + max + ")", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        data.setAmpOffset(fVal);

                        tvAmpOffsetVal.setText(data.getAmpOffset() + " dB");

                        // 전송
//                        FunctionHandler.getInstance().getDataConnector().sendCommand(
//                                DataHandler.getInstance().getConfigCmd()
//                        );
                    }
                });
                dlg.show();
            });

            linSaveLog.setOnClickListener(v->{
                NrScanData data = DataHandler.getInstance().getNrScanData();
                boolean isSaveLog = !data.isSaveLog();
                data.setSaveLog(isSaveLog);
                //update();
                if (data.isSaveLog())
                    selectOptionView(tvSaveLogOn, tvSaveLogOff);
                else
                    selectOptionView(tvSaveLogOff, tvSaveLogOn);

                //TODO 로그 처리
            });

        });

    }
}

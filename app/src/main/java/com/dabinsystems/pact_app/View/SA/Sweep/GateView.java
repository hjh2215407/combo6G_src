package com.dabinsystems.pact_app.View.SA.Sweep;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.SaGateData;
import com.dabinsystems.pact_app.Dialog.Number2KeypadDialog;
import com.dabinsystems.pact_app.Dialog.SA.GateDelayKeypadDialog;
import com.dabinsystems.pact_app.Dialog.SA.GateLengthKeypadDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Util.UtilValues;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

/**
 * right menu (SA - Sweep) - Gate
 * - Gate
 * - Gate View
 * - Gate View Sweep Time
 * - Num. Of Gate
 * - Gate Delta
 * - Gate Type
 * - Gate Delay
 * - Gate Length
 * - Gate Source
 * - More
 */
public class GateView extends LayoutBase {

    private LinearLayout linGate, linGateView, linGateViewSweepTime, linGateDelay, linGateLength, linGateSource;
    private AutofitTextView tvGateOn, tvGateOff, tvGateViewOn, tvGateViewOff, tvGateSweepTimeVal, tvGateDelayVal, tvGateLengthVal, tvGateSourceVal;
    boolean isInitView = false;

    private LinearLayout linMore, linNumOfGate, linGateDelta, linGateType;
    private AutofitTextView tvMore, tvNumOfGate, tvGateDelta, tvGateTypeSlot, tvGateTypeTime;

    private LinearLayout linGateDelaySlot, linGateDelaySymbol, linGateLengthSlot, linGateLengthSymbol;
    private AutofitTextView tvGateDelaySlot, tvGateDelaySymbol, tvGateLengthSlot, tvGateLengthSymbol;

    int idxPage = 0;
    int totalPage = 2;

    UtilValues utilValues = new UtilValues();

    public GateView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            idxPage = 0;

            initView();
            update();

            mActivity.runOnUiThread(() -> {
                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.gate));
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linGate);

                if (SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData().getGateMode().equals(SaGateData.GATE_MODE.ON)) {
                    // 1/2
                    binding.linRightMenu.addView(linGateView);
                    binding.linRightMenu.addView(linGateViewSweepTime);
                    binding.linRightMenu.addView(linNumOfGate);
                    binding.linRightMenu.addView(linGateDelta);
                    binding.linRightMenu.addView(linGateType);
                    binding.linRightMenu.addView(linGateDelay);
                    binding.linRightMenu.addView(linGateDelaySymbol);

                    // 2/2
                    binding.linRightMenu.addView(linGateLength);
                    //binding.linRightMenu.addView(linGateDelaySlot);
                    //binding.linRightMenu.addView(linGateLengthSlot);
                    binding.linRightMenu.addView(linGateLengthSymbol);
                    binding.linRightMenu.addView(linGateSource);

                    binding.linRightMenu.addView(linMore);
                }

                binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getSaSweepView().addMenu());

                ShowButton();

            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (isInitView)
            return;
        isInitView = true;

        DynamicView mDynamicView = new DynamicView(mActivity);

        ArrayList<View> mGateList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.gate), "On", "Off");
        linGate = (LinearLayout) mGateList.get(0);
        tvGateOn = (AutofitTextView) mGateList.get(2);
        tvGateOff = (AutofitTextView) mGateList.get(3);

        ArrayList<View> mGateViewList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.gate_view), "On", "Off");
        linGateView = (LinearLayout) mGateViewList.get(0);
        tvGateViewOn = (AutofitTextView) mGateViewList.get(2);
        tvGateViewOff = (AutofitTextView) mGateViewList.get(3);

        ArrayList<View> mGateViewSweepTimeList = mDynamicView.addRightMenuButton("Gate View Sweep Time", "");
        linGateViewSweepTime = (LinearLayout) mGateViewSweepTimeList.get(0);
        tvGateSweepTimeVal = (AutofitTextView) mGateViewSweepTimeList.get(2);

        ArrayList<View> mNumOfGateList = mDynamicView.addRightMenuButton("Num. Of Gate", "");
        linNumOfGate = (LinearLayout) mNumOfGateList.get(0);
        tvNumOfGate = (AutofitTextView) mNumOfGateList.get(2);

        ArrayList<View> mGateDeltaList = mDynamicView.addRightMenuButton("Gate Delta", "");
        linGateDelta = (LinearLayout) mGateDeltaList.get(0);
        tvGateDelta = (AutofitTextView) mGateDeltaList.get(2);

        ArrayList<View> mGateSourceList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.gate_source), "");
        linGateSource = (LinearLayout) mGateSourceList.get(0);
        tvGateSourceVal = (AutofitTextView) mGateSourceList.get(2);


        // [jigum] 2021-07-20 기존 Gate 메뉴에 Number Of Gate, Gate Delta, Gate Type 추가
        ArrayList<View> mGateTypeList = mDynamicView.addRightMenuButton("Gate Type", "Symbol", "Time");
        linGateType = (LinearLayout) mGateTypeList.get(0);
        tvGateTypeSlot = (AutofitTextView) mGateTypeList.get(2);
        tvGateTypeTime = (AutofitTextView) mGateTypeList.get(3);

        ArrayList<View> mGateDelayList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.gate_delay), "");
        linGateDelay = (LinearLayout) mGateDelayList.get(0);
        tvGateDelayVal = (AutofitTextView) mGateDelayList.get(2);

        ArrayList<View> mGateLengthList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.gate_length), "");
        linGateLength = (LinearLayout) mGateLengthList.get(0);
        tvGateLengthVal = (AutofitTextView) mGateLengthList.get(2);

        ArrayList<View> mGateDelaySlotList = mDynamicView.addRightMenuButton("Gate Delay Slots", "");
        linGateDelaySlot = (LinearLayout) mGateDelaySlotList.get(0);
        tvGateDelaySlot = (AutofitTextView) mGateDelaySlotList.get(2);

        ArrayList<View> mGateDelaySymbolList = mDynamicView.addRightMenuButton("Gate Delay", "");
        linGateDelaySymbol = (LinearLayout) mGateDelaySymbolList.get(0);
        tvGateDelaySymbol = (AutofitTextView) mGateDelaySymbolList.get(2);

        ArrayList<View> mGateLengthSlotList = mDynamicView.addRightMenuButton("Gate Length Slots", "");
        linGateLengthSlot = (LinearLayout) mGateLengthSlotList.get(0);
        tvGateLengthSlot = (AutofitTextView) mGateLengthSlotList.get(2);

        ArrayList<View> mGateLengthSymbolList = mDynamicView.addRightMenuButton("Gate Length", "");
        linGateLengthSymbol = (LinearLayout) mGateLengthSymbolList.get(0);
        tvGateLengthSymbol = (AutofitTextView) mGateLengthSymbolList.get(2);
//        enabledView((AutofitTextView) mGateLengthSymbolList.get(1), false);
//        enabledView(tvGateLengthSymbol, false);

        ArrayList<View> mMoreList = mDynamicView.addRightMenuButton("More", (idxPage + 1) + "/" + totalPage);
        linMore = (LinearLayout) mMoreList.get(0);
        tvMore = (AutofitTextView) mMoreList.get(2);

        setUpEvents();
    }

    @SuppressLint("SetTextI18n")
    public void update() {

        mActivity.runOnUiThread(() -> {

            initView();

            try {
                SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();

                SaGateData.GATE_MODE mode = gateData.getGateMode();
                SaGateData.GATE_MODE viewMode = gateData.getGateView();
                SaGateData.GATE_SOURCE source = gateData.getGateSource();
                SaGateData.GATE_TYPE type = gateData.getGateType();

                switch (mode) {
                    case OFF:
                        selectOptionView(tvGateOff, tvGateOn);
                        binding.tvGate.setBackgroundResource(R.drawable.fw_status_background);
                        binding.tvGate.setTextColor(mActivity.getResources().getColor(R.color.norText));
                        break;
                    case ON:
                        selectOptionView(tvGateOn, tvGateOff);
                        binding.tvGate.setBackgroundResource(R.drawable.fw_status_gate_on);
                        binding.tvGate.setTextColor(mActivity.getResources().getColor(R.color.gate_color));
                        break;
                }

                switch (viewMode) {
                    case OFF:
                        selectOptionView(tvGateViewOff, tvGateViewOn);
                        break;
                    case ON:
                        selectOptionView(tvGateViewOn, tvGateViewOff);
                        break;
                }

                // [jigum] 2021-07-20 기존 Gate 메뉴에 Number Of Gate, Gate Delta, Gate Type 추가
                tvNumOfGate.setText(String.valueOf(gateData.getGateNum()));

                tvGateDelta.setText(utilValues.toSecUnitString(gateData.getGateDelta()));

                switch (type) {
                    case Slot:
                        selectOptionView(tvGateTypeSlot, tvGateTypeTime);
                        break;
                    case Time:
                        selectOptionView(tvGateTypeTime, tvGateTypeSlot);
                        break;
                }

                tvGateSourceVal.setText(source.getName());

                int sweepTime = gateData.getGateViewSweepTime();
                String sweepTimeForUnit = utilValues.toSecUnitString(sweepTime);
                tvGateSweepTimeVal.setText(sweepTimeForUnit);
                binding.lineChartLayout.gateLayout.tvTimeSpanVal.setText(sweepTimeForUnit);

                tvGateDelayVal.setText(utilValues.toSecUnitString(gateData.getGateDelay()));
//                binding.lineChartLayout.gateLayout.tvGateDelay.setText(gateDelayForUnit);

                tvGateLengthVal.setText(utilValues.toSecUnitString(gateData.getGateLength()));

                tvGateDelaySlot.setText(String.valueOf(gateData.getGateDelaySlot()));
                tvGateDelaySymbol.setText(gateData.getGateDelaySymbol() + " Symb");
                tvGateLengthSlot.setText(String.valueOf(gateData.getGateLengthSlot()));
                tvGateLengthSymbol.setText(gateData.getGateLengthSymbol() + " Symb");



                FunctionHandler.getInstance().getGateLineChart().update();

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        });

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linGate.setOnClickListener(v -> {

                SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();
                SaGateData.GATE_MODE mode = gateData.getGateMode();

                if (mode == SaGateData.GATE_MODE.OFF)
                    gateData.setGateMode(SaGateData.GATE_MODE.ON);
                else {
                    gateData.setGateMode(SaGateData.GATE_MODE.OFF);
                    gateData.setGateView(SaGateData.GATE_MODE.OFF);
                }

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );

                addMenu();
                ViewHandler.getInstance().getContent().updateView();
                FunctionHandler.getInstance().getGateLineChart().update();
                update();

            });

            linGateView.setOnClickListener(v -> {

                SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();
                SaGateData.GATE_MODE mode = gateData.getGateView();

                if (mode == SaGateData.GATE_MODE.OFF)
                    gateData.setGateView(SaGateData.GATE_MODE.ON);
                else
                    gateData.setGateView(SaGateData.GATE_MODE.OFF);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getConfigData().getSaParameter()
                );

                ViewHandler.getInstance().getContent().updateView();
                FunctionHandler.getInstance().getGateLineChart().update();
                update();

            });

            linGateViewSweepTime.setOnClickListener(v -> ViewHandler.getInstance().getGateSweepTimeView().addMenu());

            linGateDelay.setOnClickListener(v -> new GateDelayKeypadDialog(mActivity, binding).show());

            linGateLength.setOnClickListener(v -> new GateLengthKeypadDialog(mActivity, binding).show());

            linGateSource.setOnClickListener(v -> ViewHandler.getInstance().getGateSourceView().addMenu());

            // [jigum] 2021-07-20 기존 Gate 메뉴에 Number Of Gate, Gate Delta, Gate Type 추가
            linNumOfGate.setOnClickListener(v -> {
                ViewHandler.getInstance().getGateNumOfGateView().addMenu();

//                // 입력 창
//                NumberKeypadDialog dlg = new NumberKeypadDialog(mActivity, binding);
//                dlg.isViewDot = false;
//                dlg.isViewPlusMinus = false;
//                dlg.isViewUnit = false;
//                dlg.isView0 = false;
//                dlg.isView3 = false;
//                dlg.isView6 = false;
//                dlg.isView7 = false;
//                dlg.isView9 = false;
//                dlg.setMaxStringLen(1);
//                dlg.setValueEnterListener(val -> {
//
//                    int gateNum = (int) val;
//                    switch (gateNum) {
//                        case 1:
//                        case 2:
//                        case 4:
//                        case 5:
//                        case 8:
//                            break;
//                        default:
//                            Toast.makeText(MainActivity.getContext(), "Out of range(1, 2, 4, 5, 8)", Toast.LENGTH_SHORT).show();
//                            return;
//                    }
//                    SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();
//                    gateData.setGateNum(gateNum);
//
//                    tvNumOfGate.setText(String.valueOf(gateNum));
//
//                    //TODO 영향 받는 애들 전부 갱신?
//                    tvGateDelta.setText(utilValues.toSecUnitString(gateData.getGateDelta()));
//
//                    // 하단 차트 업데이트
//                    FunctionHandler.getInstance().getGateLineChart().update();
//
//                    // 전송
//                    FunctionHandler.getInstance().getDataConnector().sendCommand(
//                            DataHandler.getInstance().getConfigCmd()
//                    );
//                });
//                dlg.show();
            });

//            linGateDelta.setOnClickListener(v -> {
//                // 입력 창
//                NumberKeypadDialog dlg = new NumberKeypadDialog(mActivity, binding);
//                //dlg.isViewDot = false;
//                dlg.isViewPlusMinus = false;
//                dlg.setValueEnterListener(val -> {
//                    int nVal = (int)val;
//
//                    SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();
//                    int min = gateData.getGateDeltaMin();
//                    int max = gateData.getGateDeltaMax();
//
//                    if (nVal < min || nVal > max) {
//                        Toast.makeText(MainActivity.getContext(), "Out of range(" + min + " ~ " + max + ")", Toast.LENGTH_SHORT).show();
//                        return;
//                    } else {
//                        gateData.setGateDelta(nVal);
//                        tvGateDelta.setText(utilValues.toSecUnitString(gateData.getGateDelta()));
//
//                        // 하단 차트 업데이트
//                        FunctionHandler.getInstance().getGateLineChart().update();
//
//                        // 전송
//                        FunctionHandler.getInstance().getDataConnector().sendCommand(
//                                DataHandler.getInstance().getConfigCmd()
//                        );
//                    }
//                });
//                dlg.show();
//            });

            linGateType.setOnClickListener(v -> {
                // Slot / Time 변경
                SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();
                if (gateData.getGateType() == SaGateData.GATE_TYPE.Time) {
                    gateData.setGateType(SaGateData.GATE_TYPE.Slot);
                    selectOptionView(tvGateTypeSlot, tvGateTypeTime);
                } else {
                    gateData.setGateType(SaGateData.GATE_TYPE.Time);
                    selectOptionView(tvGateTypeTime, tvGateTypeSlot);
                }
                
                // 표출 버튼 변경
                ShowButton();

                // 하단 차트 업데이트
                //org
                //FunctionHandler.getInstance().getGateLineChart().update();
                //

                //@@ [21.12.22] gate length 값 유지되지 않는 점 수정
                tvGateDelayVal.setText(utilValues.toSecUnitString(gateData.getGateDelay()));
                tvGateLengthVal.setText(utilValues.toSecUnitString(gateData.getGateLength()));
                FunctionHandler.getInstance().getGateLineChart().update();
                //@@

                // 전송
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        DataHandler.getInstance().getConfigCmd()
                );
            });

            linGateDelaySlot.setOnClickListener(v -> {
                // 입력 창
                Number2KeypadDialog dlg = new Number2KeypadDialog(mActivity, binding);
                dlg.isViewUnit = false;
                dlg.isViewDot = false;
                dlg.isViewPlusMinus = false;
                dlg.setValueEnterListener(val -> {
                    int nVal = (int)val;

                    SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();
                    int min = gateData.getGateDelaySlotMin();
                    int max = gateData.getGateDelaySlotMax();

                    if (nVal < min || nVal > max) {
                        Toast.makeText(MainActivity.getContext(), "Out of range(" + min + " ~ " + max + ")", Toast.LENGTH_SHORT).show();
                    } else {
                        gateData.setGateDelaySlot(nVal);
                        tvGateDelaySlot.setText(String.valueOf(gateData.getGateDelaySlot()));

                        // 하단 차트 업데이트
                        FunctionHandler.getInstance().getGateLineChart().update();

                        // 전송
                        FunctionHandler.getInstance().getDataConnector().sendCommand(
                                DataHandler.getInstance().getConfigCmd()
                        );
                    }
                });
                dlg.show();
            });

            linGateDelaySymbol.setOnClickListener(v -> {
                // 입력 창
                Number2KeypadDialog dlg = new Number2KeypadDialog(mActivity, binding);
                dlg.isViewUnit = false;
                dlg.isViewDot = false;
                dlg.isViewPlusMinus = false;
                dlg.setValueEnterListener(val -> {
                    int nVal = (int)val;

                    SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();
                    int min = gateData.getGateDelaySymbolMin();
                    int max = gateData.getGateDelaySymbolMax();

                    if (nVal < min || nVal > max) {
                        Toast.makeText(MainActivity.getContext(), "Out of range(" + min + " ~ " + max + ")", Toast.LENGTH_SHORT).show();
                    } else {
                        gateData.setGateDelaySymbol(nVal);
                        tvGateDelaySymbol.setText(gateData.getGateDelaySymbol() + " Symb");

                        // 하단 차트 업데이트
                        FunctionHandler.getInstance().getGateLineChart().update();

                        // 전송
                        FunctionHandler.getInstance().getDataConnector().sendCommand(
                                DataHandler.getInstance().getConfigCmd()
                        );
                    }
                });
                dlg.show();
            });

            linGateLengthSlot.setOnClickListener(v -> {
                // 입력 창
                Number2KeypadDialog dlg = new Number2KeypadDialog(mActivity, binding);
                dlg.isViewUnit = false;
                dlg.isViewDot = false;
                dlg.isViewPlusMinus = false;
                dlg.setValueEnterListener(val -> {
                    int nVal = (int)val;

                    SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();
                    int min = gateData.getGateLengthSlotMin();
                    int max = gateData.getGateLengthSlotMax();

                    if (nVal < min || nVal > max) {
                        Toast.makeText(MainActivity.getContext(), "Out of range(" + min + " ~ " + max + ")", Toast.LENGTH_SHORT).show();
                    } else {
                        gateData.setGateLengthSlot(nVal);
                        tvGateLengthSlot.setText(String.valueOf(gateData.getGateLengthSlot()));

                        // 하단 차트 업데이트
                        FunctionHandler.getInstance().getGateLineChart().update();

                        // 전송
                        FunctionHandler.getInstance().getDataConnector().sendCommand(
                                DataHandler.getInstance().getConfigCmd()
                        );
                    }
                });
                dlg.show();
            });

            linGateLengthSymbol.setOnClickListener(v -> {
                Toast.makeText(MainActivity.getContext(), "Fixed 1 Symbol", Toast.LENGTH_SHORT).show();
//                // 입력창
//                Number2KeypadDialog dlg = new Number2KeypadDialog(mActivity, binding);
//                dlg.isViewUnit = false;
//                dlg.isViewDot = false;
//                dlg.isViewPlusMinus = false;
//                dlg.setValueEnterListener(val -> {
//                    int nVal = (int)val;
//
//                    SaGateData gateData = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData();
//                    int min = gateData.getGateLengthSymbolMin();
//                    int max = gateData.getGateLengthSymbolMax();
//
//                    if (nVal < min || nVal > max) {
//                        Toast.makeText(MainActivity.getContext(), "Out of range(" + min + " ~ " + max + ")", Toast.LENGTH_SHORT).show();
//                    } else {
//                        gateData.setGateLengthSymbol(nVal);
//                        tvGateLengthSymbol.setText(String.valueOf(gateData.getGateLengthSymbol()) + " Symb");
//
//                        // 하단 차트 업데이트
//                        FunctionHandler.getInstance().getGateLineChart().update();
//
//                        // 전송
//                        FunctionHandler.getInstance().getDataConnector().sendCommand(
//                                DataHandler.getInstance().getConfigCmd()
//                        );
//                    }
//                });
//                dlg.show();
            });

            linMore.setOnClickListener(v -> {
                // 다음 버튼 표시
                idxPage++;
                if (idxPage >= totalPage)
                    idxPage = 0;
                tvMore.setText((idxPage + 1) + "/" + totalPage);

                ShowButton();
            });
        });

    }

    private void ShowButton() {
        // 1/2
        linGate.setVisibility(idxPage == 0 ? View.VISIBLE : View.GONE);
        linGateView.setVisibility(idxPage == 0 ? View.VISIBLE : View.GONE);
        linGateViewSweepTime.setVisibility(idxPage == 0 ? View.VISIBLE : View.GONE);
        linNumOfGate.setVisibility(idxPage == 0 ? View.VISIBLE : View.GONE);
        linGateDelta.setVisibility(idxPage == 0 ? View.VISIBLE : View.GONE);
        linGateType.setVisibility(idxPage == 0 ? View.VISIBLE : View.GONE);

        boolean isTime = SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData().getGateType() == SaGateData.GATE_TYPE.Time;

        linGateDelay.setVisibility((idxPage == 0 && isTime) ? View.VISIBLE : View.GONE);
        linGateDelaySymbol.setVisibility((idxPage == 0 && !isTime) ? View.VISIBLE : View.GONE);

        // 2/2
        linGateLength.setVisibility((idxPage == 1 && isTime) ? View.VISIBLE : View.GONE);
        linGateDelaySlot.setVisibility((idxPage == 1 && !isTime) ? View.VISIBLE : View.GONE);
        linGateLengthSlot.setVisibility((idxPage == 1 && !isTime) ? View.VISIBLE : View.GONE);
        linGateLengthSymbol.setVisibility((idxPage == 1 && !isTime) ? View.VISIBLE : View.GONE);

        linGateSource.setVisibility(idxPage == 1 ? View.VISIBLE : View.GONE);
    }
}

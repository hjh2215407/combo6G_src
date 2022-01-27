package com.dabinsystems.pact_app.View.ModAccuracy.NR5G;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrLimitData;
import com.dabinsystems.pact_app.Data.SA.SaGateData;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.NR5G.NrInterTaeKeypadDialog;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.NR5G.NrMinEvmKeypadDialog;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.NR5G.NrTaeKeypadDialog;
import com.dabinsystems.pact_app.Dialog.Number2KeypadDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Dialog.ModAccuracy.NR5G.Nr5gLimitKeypad;
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
 * right menu 5G NR -> Setup -> Limit
 * - Freq. Offset
 * - Min EVM
 * - Intra-TAE
 * - Inter-TAE
 */
public class NrLimitView extends LayoutBase {

    private LinearLayout linFreqOffset, linMinEvm, linTae, linInterTae;
    private ArrayList<View> mFreqOffsetList, mMinEvmList, mTaeList, mInterTaeList;
    private AutofitTextView tvFreqOffsetView, tvMinEvm, tvTae, tvInterTae;
    private DynamicView mDynamicView;

    public NrLimitView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {

                binding.linRightMenu.removeAllViews();
                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.limit_name));

                binding.linRightMenu.addView(linFreqOffset);
                binding.linRightMenu.addView(linMinEvm);
                binding.linRightMenu.addView(linTae);
                binding.linRightMenu.addView(linInterTae);

                binding.tvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ViewHandler.getInstance().getNrMeasSetupView().addMenu();

                    }
                });

            });


        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity.getApplicationContext());

        mFreqOffsetList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.freq_offset), "");
        linFreqOffset = (LinearLayout) mFreqOffsetList.get(0);
        tvFreqOffsetView = (AutofitTextView) mFreqOffsetList.get(2);

        mMinEvmList = mDynamicView.addRightMenuButton(mActivity.getResources().getString(R.string.min_evm), "");
        linMinEvm = (LinearLayout) mMinEvmList.get(0);
        tvMinEvm = (AutofitTextView) mMinEvmList.get(2);

        mTaeList = mDynamicView.addRightMenuButton("Intra-TAE", "");
        linTae = (LinearLayout) mTaeList.get(0);
        tvTae = (AutofitTextView) mTaeList.get(2);

        // [jigum] 2021-07-16 ‘Setup -> Limit’ 메뉴에 ‘Inter-TAE’ 메뉴 추가. 설정 값은 FW에 전달해야 함
        mInterTaeList = mDynamicView.addRightMenuButton("Inter-TAE", "");
        linInterTae = (LinearLayout) mInterTaeList.get(0);
        tvInterTae = (AutofitTextView) mInterTaeList.get(2);

        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            linFreqOffset.setOnClickListener(v -> {

                new Nr5gLimitKeypad(mActivity, binding).show();

            });

            linMinEvm.setOnClickListener(v -> {

                new NrMinEvmKeypadDialog(mActivity, binding).show();

            });

            linTae.setOnClickListener(v -> {

                new NrTaeKeypadDialog(mActivity, binding).show();

            });

            linInterTae.setOnClickListener(v -> {
                //new NrInterTaeKeypadDialog(mActivity, binding).show();

                // 입력 창
                Number2KeypadDialog dlg = new Number2KeypadDialog(mActivity, binding);
                dlg.mUnit = "us";
                //dlg.isViewUnit = false;
                //dlg.isViewDot = false;
                dlg.isViewPlusMinus = false;
                dlg.setValueEnterListener(val -> {
                    int nVal = (int) (val * 1000);

                    NrLimitData data = DataHandler.getInstance().getNrData().getLimitData();
                    int min = data.MIN_INTER_TAE;
                    int max = data.MAX_INTER_TAE;

                    UtilValues utilValues = new UtilValues();

                    if (nVal < min || nVal > max) {
                        Toast.makeText(MainActivity.getContext(), "Out of range(" + utilValues.toNanoSecUnitString(min) + " ~ " + utilValues.toNanoSecUnitString(max) + ")", Toast.LENGTH_SHORT).show();
                    } else {
                        data.setInterTae(nVal);

                        tvInterTae.setText(utilValues.toNanoSecUnitString(DataHandler.getInstance().getNrData().getLimitData().getInterTae()));

                        // 전송
                        FunctionHandler.getInstance().getDataConnector().sendCommand(
                                DataHandler.getInstance().getNrData().getNrCmd()
                        );
                    }
                });
                dlg.show();

            });

        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void update() {
        super.update();

        mActivity.runOnUiThread(() -> {
            UtilValues utilValues = new UtilValues();

            tvFreqOffsetView.setText(DataHandler.getInstance().getNrData().getLimitData().getFreqOffsetValue() + " ppm");
            tvMinEvm.setText(DataHandler.getInstance().getNrData().getLimitData().getMinEvm() + " %");
            tvTae.setText(DataHandler.getInstance().getNrData().getLimitData().getTae() + " ns");
            tvInterTae.setText(utilValues.toNanoSecUnitString(DataHandler.getInstance().getNrData().getLimitData().getInterTae()));

        });

    }

}

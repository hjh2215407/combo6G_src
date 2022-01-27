package com.dabinsystems.pact_app.View.SA;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class SaModeView extends LayoutBase {

    private LinearLayout linSpectrum, lin5GNR;;
    private AutofitTextView tvSpectrum, tv5GNR;
    private ArrayList<View> mSpectrumList, m5GNRList;
    private DynamicView mDynamicView;

    public SaModeView(MainActivity activity, ActivityMainBinding binding) {
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
                binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.sa_mode));
                binding.linRightMenu.addView(linSpectrum);
//                binding.linRightMenu.addView(lin5GNR);

                binding.tvBack.setOnClickListener(v->{

                });
            });

        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity);

        mSpectrumList = mDynamicView.addRightMenuButton("Spectrum\nAnalyzer", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        linSpectrum = (LinearLayout) mSpectrumList.get(0);
        tvSpectrum = (AutofitTextView) mSpectrumList.get(1);
        tvSpectrum.setSingleLine(false);

        m5GNRList = mDynamicView.addRightMenuButton("5G NR", Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        lin5GNR = (LinearLayout) m5GNRList.get(0);
        tv5GNR = (AutofitTextView) m5GNRList.get(1);
        tv5GNR.setSingleLine(false);


        setUpEvents();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(()->{

            linSpectrum.setOnClickListener(v->{

                //todo : set SA
                FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.SA);
                ViewHandler.getInstance().getSaMeas().addMenu();
                ViewHandler.getInstance().getSAView().update();

            });

            lin5GNR.setOnClickListener(v->{

                //todo : set 5G NR
                FunctionHandler.getInstance().getMeasureMode().setMode(MeasureMode.MEASURE_MODE.MOD_ACCURACY);

                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        SaDataHandler.getInstance().getNrChannelPowerConfigData().getSaParameter()
                );

                ViewHandler.getInstance().getSaMeas().addMenu();
                ViewHandler.getInstance().getSAView().update();

            });

        });


    }

}

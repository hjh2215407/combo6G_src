package com.dabinsystems.pact_app.View.Calibration;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Function.CalibrationFunc;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

public class CalibrationView extends LayoutBase {

    private LinearLayout linStart, linUserCal, linCalType;
    private AutofitTextView tvUserCal, tvCalType, tvCalTypeVal, tvUserCalOn, tvUserCalOff, tvCalStd, tvCalFlex;
    private ArrayList<View> mStartCalibrationList, mUserCalList, mCalTypeList;
    private DynamicView mDynamicView;

    public CalibrationView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
        super(activity, binding);

//        tvUserCalOff.setPaintFlags(tvUserCalOff.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG); //Underline


    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();
            update();

            mActivity.runOnUiThread(() -> {

                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.calibration_name));
                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linStart);
                binding.linRightMenu.addView(linUserCal);
                binding.linRightMenu.addView(linCalType);

                binding.linCableList.setVisibility(View.GONE);

            });

        }).start();

    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity.getApplicationContext());

        mStartCalibrationList = mDynamicView.addRightMenuButton("Start Cal");
        mUserCalList = mDynamicView.addRightMenuButton("User Cal", "ON", "OFF");

        linStart = (LinearLayout) mStartCalibrationList.get(0);

        linUserCal = (LinearLayout) mUserCalList.get(0);
        tvUserCal = (AutofitTextView) mUserCalList.get(1);
        tvUserCalOn = (AutofitTextView) mUserCalList.get(2);
        tvUserCalOff = (AutofitTextView) mUserCalList.get(3);

        mCalTypeList = mDynamicView.addRightMenuButton("Cal Type", "Std", "Flex");
        linCalType = (LinearLayout) mCalTypeList.get(0);
        tvCalType = (AutofitTextView) mCalTypeList.get(1);
        tvCalStd = (AutofitTextView) mCalTypeList.get(2);
        tvCalFlex = (AutofitTextView) mCalTypeList.get(3);

        setUpEvents();

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void update() {
        super.update();

        initView();

        mActivity.runOnUiThread(() -> {

            try {

                if (FunctionHandler.getInstance().getCalibrationFunc().isUserCal()) {

                    selectOptionView(tvUserCalOn, tvUserCalOff);

                } else {

                    selectOptionView(tvUserCalOff, tvUserCalOn);
                }

                if (FunctionHandler.getInstance().getCalibrationFunc().isFlex()) {

                    selectOptionView(tvCalFlex, tvCalStd);

                } else {

                    selectOptionView(tvCalStd, tvCalFlex);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        });

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {


            linStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ViewHandler.getInstance().getStartCalibrationView().addMenu();

                }
            });

            linUserCal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(FunctionHandler.getInstance().getCalibrationFunc().isUserCal()) {

                        FunctionHandler.getInstance().getCalibrationFunc().setUserCal(CalibrationFunc.USER_CAL.OFF);

                    } else {

                        FunctionHandler.getInstance().getCalibrationFunc().setUserCal(CalibrationFunc.USER_CAL.ON);
                    }

                    update();

                }
            });

            linCalType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(FunctionHandler.getInstance().getCalibrationFunc().isFlex()) {

                        FunctionHandler.getInstance().getCalibrationFunc().setCalType(
                                CalibrationFunc.CAL_TYPE.TYPE_STANDARD
                        );

                    } else {

                        FunctionHandler.getInstance().getCalibrationFunc().setCalType(
                                CalibrationFunc.CAL_TYPE.TYPE_FLEX
                        );

                    }


                    CalibrationFunc.USER_CAL userCal = FunctionHandler.getInstance().getCalibrationFunc().getUserCal();
                    if(userCal == CalibrationFunc.USER_CAL.ON)
                        FunctionHandler.getInstance().getCalibrationFunc().setUserCal(CalibrationFunc.USER_CAL.ON);


                    update();

                }
            });

            binding.tvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addMenu();

                    // Cal 종료 후 측정화면으로 돌아가면 바로 측정하도록
                    FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());
                }
            });

        });

    }
}

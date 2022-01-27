package com.dabinsystems.pact_app.View.Calibration;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.LoadingDialog;
import com.dabinsystems.pact_app.Function.CalibrationFunc;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class StartCalibrationView extends LayoutBase {

    private LinearLayout linMeasure;
    private DynamicView mDynamicView;

    private LoadingDialog loadingDialog;

    public StartCalibrationView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {
            initView();

            mActivity.runOnUiThread(() -> {

                resetColorMeasure();

                FunctionHandler.getInstance().getDataConnector().removeDataTimeoutHandler();
                FunctionHandler.getInstance().getCalibrationFunc().setStep(CalibrationFunc.CALIBRATION.CAL_MODE);
                binding.linCalibration.setVisibility(View.VISIBLE);

                update();

                binding.linRightMenu.removeAllViews();
                binding.linRightMenu.addView(linMeasure);

                binding.tvRightMenuTitle.setText(mActivity.getResources().getText(R.string.calibration_name));
                binding.linCableList.setVisibility(View.GONE);

                binding.tvBack.setOnClickListener(v -> {
                    binding.linCalibration.setVisibility(View.GONE);
                    ViewHandler.getInstance().getCalibrationView().addMenu();
                    FunctionHandler.getInstance().getCalibrationFunc().resetStep();
                    resetColorMeasure();

                    // 화면 닫을 시 데이터 요청
                    FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getConfigCmd());
                });
            });
        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (mDynamicView != null) return;

        mDynamicView = new DynamicView(mActivity.getApplicationContext());

        ArrayList<View> mMeasureList = mDynamicView.addRightMenuButton("Measure");
        linMeasure = (LinearLayout) mMeasureList.get(0);

        setUpEvents();
    }

    @Override
    public void update() {
        super.update();

        if (binding.linCalibration.getVisibility() != View.VISIBLE) {
            InitActivity.logMsg("calibration", "return");
            return;
        }

        CalibrationFunc.CALIBRATION step = FunctionHandler.getInstance().getCalibrationFunc().getStep();

        if (step == CalibrationFunc.CALIBRATION.CAL_MODE) {
            mActivity.runOnUiThread(() -> {
                resetColorMeasure();
                binding.tvCaliDesc.setText(mActivity.getResources().getText(R.string.calibration_step1));
                binding.ivCaliStep.setImageResource(R.drawable.cali_step1);
                binding.ivCaliStep.invalidate();
                binding.tvCaliDesc.invalidate();
            });
        } else if (step == CalibrationFunc.CALIBRATION.STEP1) {
            mActivity.runOnUiThread(() -> {
                resetColorMeasure();
                binding.tvCaliDesc.setText(mActivity.getResources().getText(R.string.calibration_step2));
                binding.ivCaliStep.setImageResource(R.drawable.cali_step2);
                binding.ivCaliStep.invalidate();
                binding.tvCaliDesc.invalidate();
            });
        } else if (step == CalibrationFunc.CALIBRATION.STEP2) {
            mActivity.runOnUiThread(() -> {
                resetColorMeasure();
                binding.tvCaliDesc.setText(mActivity.getResources().getText(R.string.calibration_step3));
                binding.ivCaliStep.setImageResource(R.drawable.cali_step3);
                binding.ivCaliStep.invalidate();
                binding.tvCaliDesc.invalidate();
            });
        } else if (step == CalibrationFunc.CALIBRATION.STEP3) {
            mActivity.runOnUiThread(() -> {
                resetColorMeasure();
//                new CalibrationCompleteDialog(mActivity, binding).show();
            });
        } else {
            resetColorMeasure();
            binding.tvCaliDesc.setText(mActivity.getResources().getText(R.string.calibration_step1));
            binding.ivCaliStep.setImageResource(R.drawable.cali_step1);
            binding.ivCaliStep.invalidate();
            binding.tvCaliDesc.invalidate();
        }
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> linMeasure.setOnClickListener(v -> {

            if (FunctionHandler.getInstance().getCalibrationFunc().getStep() == CalibrationFunc.CALIBRATION.STEP3) {
                Toast.makeText(mActivity, "Already complete calibration.", Toast.LENGTH_SHORT).show();
                return;
            }

            mActivity.runOnUiThread(() -> startProgress());

            FunctionHandler.getInstance().getCalibrationFunc().moveStep();

            if (FunctionHandler.getInstance().getCalibrationFunc().getStep() == CalibrationFunc.CALIBRATION.STEP1) {
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        VswrDataHandler.getInstance().getCalTypeCmd()
                );
                FunctionHandler.getInstance().getCalibrationFunc().setUserCal(CalibrationFunc.USER_CAL.ON);
            }

            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    VswrDataHandler.getInstance().getCalibrationCmd()
            );

            selectColorMeasure();
        }));
    }

    public void startProgress() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(mActivity);

            //            new Handler(Looper.getMainLooper()).post(() -> {
//
//                mDialogBuilder = new AlertDialog.Builder(mActivity);
//                mDialogBuilder.setCancelable(true);
//
//                mDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialogInterface) {
//                        Toast.makeText(mActivity, "", Toast.LENGTH_SHORT).show();
//                        FunctionHandler.getInstance().getCalibrationFunc().setStep(CalibrationFunc.CALIBRATION.CAL_OFF);
//                    }
//                });
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//                    mDialogBuilder.setView(R.layout.layout_loading_dialog);
//                    TextView text = mCalDialog.findViewById(R.id.tvMessage);
//                    text.setText(mActivity.getResources().getString(R.string.wait_cal));
//
//                }
//
//                mCalDialog = mDialogBuilder.create();
//                mCalDialog.setMessage("Please wait... Calibration is running...");
//                mCalDialog.show();
//
//
//            });

        }

        loadingDialog.startLoadingDialog();
    }

    public void dismissProgress() {
        if (loadingDialog != null) {
            loadingDialog.dismissDialog();
        }
    }

    public void selectColorMeasure() throws NullPointerException {
        linMeasure.setSelected(true);
    }

    public void resetColorMeasure() throws NullPointerException {
        linMeasure.setSelected(false);
    }

}

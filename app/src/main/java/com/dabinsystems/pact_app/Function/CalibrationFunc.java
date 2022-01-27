package com.dabinsystems.pact_app.Function;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.WarningDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Dialog.CalibrationCompleteDialog;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

public class CalibrationFunc {

    private CAL_TYPE CalType = CAL_TYPE.TYPE_STANDARD; // 0 : Flex / 1 : Standard

    private USER_CAL UserCal = USER_CAL.OFF; // 0 : Off / 1 : On
    private USER_CAL FwUserCal = USER_CAL.OFF;

    private Double CalStartFreq = null;
    private Double CalStopFreq = null;

    public enum USER_CAL {

        USER_CAL_MODE(0x09),
        OFF(0x00),
        ON(0x01);

        int Value;

        USER_CAL(int val) {

            Value = val;

        }

        public int getValue() {

            return Value;

        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(Value);

        }

    }

    public enum CAL_TYPE {

        CAL_TYPE_MODE(0x08, "Cal Type Command"),
        TYPE_FLEX(0x00, "Flex"),
        TYPE_STANDARD(0x01, "Standard");

        int Value;
        String Type;

        CAL_TYPE(int val, String type) {

            Value = val;
            Type = type;

        }

        public int getValue() {

            return Value;

        }

        public String getString() {

            return Type;

        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(Value);

        }

    }

    public enum CALIBRATION {

        CAL_START(0x12),
        CAL_MODE(0x00),
        CAL_OFF(0x00),
        STEP1(0x01),
        STEP2(0x02),
        STEP3(0x03),
        EXCEEDED_TEMPERATURE(0x04);

        int Value;

        CALIBRATION(int val) {

            Value = val;

        }

        public int getValue() {

            return Value;

        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(Value);

        }

    }

    CALIBRATION Step = CALIBRATION.CAL_OFF;


    public CAL_TYPE getCalType() {
        return CalType;
    }

    public Boolean isFlex() {

        if (CalType == CAL_TYPE.TYPE_STANDARD) {

            return false;

        } else {

            return true;

        }

    }

    public void setCalType(CAL_TYPE type) {

        CalType = type;

        FunctionHandler.getInstance().getDataConnector().sendCommand(
                VswrDataHandler.getInstance().getCalTypeCmd()
        );

        if(UserCal == USER_CAL.ON) {
            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    VswrDataHandler.getInstance().getUserCalCmd()
            );
        }

    }

    public Boolean isUserCal() {

        if (UserCal == USER_CAL.ON) {

            return true;

        } else {

            return false;

        }
    }

    public Boolean isFwUserCal() {

        if (FwUserCal == USER_CAL.ON) {

            return true;

        } else {

            return false;

        }
    }

    public void setUserCal(USER_CAL userCal) {

        UserCal = userCal;
        FunctionHandler.getInstance().getDataConnector().sendCommand(
                VswrDataHandler.getInstance().getUserCalCmd()
        );

    }

    public USER_CAL getUserCal() {
        return UserCal;
    }


    public CALIBRATION getStep() {
        return Step;
    }


    /*
        원래 step은 단계적으로 올라가야하나
        이 함수를 사용할 경우 step을 임의로 설정가능
        step 변경 후 DataHandler.getInstance().getConfigCmd() 반환시 적용한 설정 데이터 반환
     */

    public void setStep(CALIBRATION step) {

        Step = step;

    }

    public void moveStep() {

        if(Step == CALIBRATION.CAL_OFF) {
            Toast.makeText(MainActivity.getActivity(), "Not in calibration mode", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Step == CALIBRATION.CAL_MODE) Step = CALIBRATION.STEP1;
        else if (Step == CALIBRATION.STEP1) Step = CALIBRATION.STEP2;
        else if (Step == CALIBRATION.STEP2) Step = CALIBRATION.STEP3;
        else if (Step == CALIBRATION.STEP3) Step = CALIBRATION.CAL_OFF;
        else Step = CALIBRATION.CAL_OFF;

    }

    public void resetStep() {

        Step = CALIBRATION.CAL_OFF;

    }

    public void updateCalibration(String[] cmd) {

        MainActivity mActivity = (MainActivity) MainActivity.getActivity();
        ActivityMainBinding binding = MainActivity.getBinding();

        //Calibration
        if (cmd[0].equals(CALIBRATION.CAL_MODE.getHexString())) {

            mActivity.runOnUiThread(() -> {

                if (getStep() == CALIBRATION.STEP1 && cmd[1].equals(CALIBRATION.STEP1.getHexString())) {

                    setStep(CALIBRATION.STEP1);
                    ViewHandler.getInstance().getStartCalibrationView().dismissProgress();
                    InitActivity.logMsg("DataConnector", "Receive cal step1");
                    Toast.makeText(mActivity, "Complete 1 step", Toast.LENGTH_SHORT).show();

                } else if (getStep() == CALIBRATION.STEP2 && cmd[1].equals(CALIBRATION.STEP2.getHexString())) {

                    setStep(CALIBRATION.STEP2);
                    ViewHandler.getInstance().getStartCalibrationView().dismissProgress();
                    InitActivity.logMsg("DataConnector", "Receive cal step2");
                    Toast.makeText(mActivity, "Complete 2 step", Toast.LENGTH_SHORT).show();

                } else if (getStep() == CALIBRATION.STEP3 && cmd[1].equals(CALIBRATION.STEP3.getHexString())) {

                    setStep(CALIBRATION.STEP3);
                    setUserCal(USER_CAL.ON);
                    ViewHandler.getInstance().getStartCalibrationView().dismissProgress();
                    InitActivity.logMsg("DataConnector", "Receive cal step3");
                    new CalibrationCompleteDialog(mActivity, binding).show();
                    Toast.makeText(mActivity, "Complete calibration", Toast.LENGTH_SHORT).show();

                }

                if(cmd[1].equals(CALIBRATION.EXCEEDED_TEMPERATURE.getHexString())) {
                    InitActivity.logMsg("updateCalibration", cmd[1] + " " + CALIBRATION.EXCEEDED_TEMPERATURE.getHexString());
                    WarningDialog dialog = new WarningDialog(mActivity, binding, mActivity.getResources().getString(R.string.warning_cal_temp), "We recommend user calibration");
                    dialog.show();
                }

            });

        }

    }

    public void updateUserCal(String[] cmd) {

        MainActivity mActivity = (MainActivity) MainActivity.getActivity();
        ActivityMainBinding binding = MainActivity.getBinding();

        if (cmd[0].equals(USER_CAL.USER_CAL_MODE.getHexString())) {

            double appStartFreq = 0d;
            double appStopFreq = 0d;

            appStartFreq = VswrDataHandler.getInstance().getConfigData().getFrequencyData().getStartFreq();
            appStopFreq = VswrDataHandler.getInstance().getConfigData().getFrequencyData().getStopFreq();

            if (cmd[1].equals(USER_CAL.ON.getHexString())) FwUserCal = USER_CAL.ON;
            else FwUserCal = USER_CAL.OFF;

            if (isFwUserCal()) {

                CalStartFreq = (Double.parseDouble(cmd[2]) / 100);
                CalStopFreq =  (Double.parseDouble(cmd[3]) / 100);
                InitActivity.logMsg("CalibrationFunc", CalStartFreq + " " + CalStopFreq + " "
                        + appStartFreq + " " + appStopFreq + " " + isUserCal() + " " + FwUserCal.getValue());
            }

            double finalAppStartFreq = appStartFreq;
            double finalAppStopFreq = appStopFreq;

            mActivity.runOnUiThread(() -> {

                if (isUserCal() && !isFwUserCal()) {

                    binding.recallMessageLayout.tvWarningCal.setVisibility(View.VISIBLE);
                    Toast.makeText(mActivity, "UserCal File doesn't exist", Toast.LENGTH_SHORT).show();
                    InitActivity.logMsg("CalibrationFunc", "UserCal File doesn't exist");
                } else if (isUserCal() && isFwUserCal() &&
                        finalAppStartFreq >= CalStartFreq && finalAppStopFreq <= CalStopFreq) {

                    binding.recallMessageLayout.tvWarningCal.setVisibility(View.GONE);
                    InitActivity.logMsg("CalibrationFunc", "gone warning cal");
                } else if(CalType == CAL_TYPE.TYPE_FLEX) {

                    binding.recallMessageLayout.tvWarningCal.setVisibility(View.GONE);

                } else {

                    binding.recallMessageLayout.tvWarningCal.setVisibility(View.VISIBLE);
                    InitActivity.logMsg("CalibrationFunc", "else");

                }

            });

        }

    }

    public Double getCalStartFreq() {
        return CalStartFreq;
    }

    public Double getCalStopFreq() {
        return CalStopFreq;
    }

}

package com.dabinsystems.pact_app.Function;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Dialog.BatteryWarningDialog;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

public class BatteryFunc {

    private final int WARNING1 = 10;
    private final int WARNING2 = 5;

    public enum BATTERY_CMD {

        BATTERY_MODE(0x06),
        CHARGE_OFF(0x00),
        CHARGE_ON(0x01),
        BATTERY_STATUS(0x02);

        int value;

        BATTERY_CMD(int val) {

            value = val;

        }

        public int getValue() {

            return value;

        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(value);

        }

    }

    InitActivity mActivity;
    ActivityMainBinding binding;

    private boolean isBatteryWarning1 = false;
    private boolean isBatteryWarning2 = false;

    public BatteryFunc(InitActivity activity, ActivityMainBinding binding) {

        mActivity = activity;
        this.binding = binding;

    }

    private boolean isBatteryCharge = false;
    private int preBattery = 0;

    public void updateBatteryStatus(String[] batteryArr) {
        try {
            if (batteryArr[1].equals(BATTERY_CMD.BATTERY_STATUS.getHexString())) {

                int leftBattery = (int) Float.parseFloat(batteryArr[2]);
                //InitActivity.logMsg("leftBattery", leftBattery + "");
                //Log.d("KDK-leftBattery", leftBattery + ", " + batteryArr[2]);

                if (preBattery == leftBattery)
                    return;

                preBattery = leftBattery;

                mActivity.runOnUiThread(() -> {

                    if (preBattery > WARNING2 && preBattery < WARNING1)
                        isBatteryWarning2 = false;
                    else if (preBattery > WARNING1) {
                        isBatteryWarning1 = false;
                        isBatteryWarning2 = false;
                    }

//                    if (preBattery >= 50 && preBattery <= 100) {
//                        //binding.linBattery.setBackgroundColor(Color.rgb((int) (255 * ((100 - preBattery) / 100)) * 2, 255, 0));
//                    } else
                    if (preBattery < 50) {
                        //binding.linBattery.setBackgroundColor(Color.rgb(255, (int) (255 * (preBattery / 50)), 0));

                        if (preBattery == WARNING2 && !isBatteryWarning2) {
                            isBatteryWarning2 = true;
                            isBatteryWarning1 = true;
                            new BatteryWarningDialog(mActivity, binding, WARNING2).show();
                        } else if (preBattery == WARNING1 && !isBatteryWarning1) {
                            isBatteryWarning1 = true;
                            new BatteryWarningDialog(mActivity, binding, WARNING1).show();
                        }
                    }

//                    binding.linBatteryBg.setSelected(isBatteryWarning1);
//                    binding.tvBattery.setText(String.valueOf(preBattery));
//                    if (isBatteryWarning1 || isBatteryWarning2) {
//                        binding.linBatteryBg.setBackgroundResource(R.drawable.battery_sm_icon);
//                    } else
                    if (preBattery >= 80) {
                        binding.linBatteryBg.setBackgroundResource(R.drawable.battery_icon);
                    } else if (preBattery >= 40) {
                        binding.linBatteryBg.setBackgroundResource(R.drawable.battery_lg_icon);
                    } else if (preBattery >= 10) {
                        binding.linBatteryBg.setBackgroundResource(R.drawable.battery_sm_icon);
                    } else {
                        binding.linBatteryBg.setBackgroundResource(R.drawable.battery_form);
                    }

//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, Float.parseFloat(batteryArr[2]));
//                    binding.linBattery.setLayoutParams(params);
//                    binding.linBattery.invalidate();

                });

            } else if (batteryArr[1].equals(BATTERY_CMD.CHARGE_OFF.getHexString())) {
                if (isBatteryCharge) {
                    isBatteryCharge = false;
                    mActivity.runOnUiThread(() -> binding.ivChargeIcon.setVisibility(View.INVISIBLE));
                }
            } else if (batteryArr[1].equals(BATTERY_CMD.CHARGE_ON.getHexString())) {
                if (!isBatteryCharge) {
                    isBatteryCharge = true;
                    mActivity.runOnUiThread(() -> binding.ivChargeIcon.setVisibility(View.VISIBLE));
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

}

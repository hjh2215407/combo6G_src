package com.dabinsystems.pact_app.Data.ModAccuracy.NR5G;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.utils.Utils;

import me.grantland.widget.AutofitTextView;

public class NrInfoData {

    ActivityMainBinding binding = MainActivity.getBinding();

    public enum SIGNAL_QUALITY_MODE {

        BPSK("BPSK"),
        QPSK("QPSK"),
        QAM16("16QAM"),
        QAM64("64QAM"),
        QAM256("256QAM");

        String Mode;

        SIGNAL_QUALITY_MODE(String mode) {

            Mode = mode;
        }

        public String getMode() {
            return Mode;
        }

    }

    public void setChannelBandwidth(int value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            binding.demodulationLayout.tvChannelBandwidthValue.setText(value + " MHz");

        });

    }

    @SuppressLint("SetTextI18n")
    public void setPhysicalCellId(Integer val) {

        MainActivity.getActivity().runOnUiThread(() -> {
            InitActivity.logMsg("setPhysicalId", val + "");
            if (val == -9999 || val == -999999)
                binding.demodulationLayout.tvPhysicalCellIdValue.setText("--.--");
            else binding.demodulationLayout.tvPhysicalCellIdValue.setText(val + "");

        });

    }

    @SuppressLint("SetTextI18n")
    public void setGroupId(Integer val) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -9999 || val == -999999)
                binding.demodulationLayout.tvGroupIdValue.setText("--.--");
            else binding.demodulationLayout.tvGroupIdValue.setText(val + "");

        });

    }


    @SuppressLint("SetTextI18n")
    public void setSector(Integer val) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -9999 || val == -999999)
                binding.demodulationLayout.tvSectorValue.setText("--.--");
            else binding.demodulationLayout.tvSectorValue.setText(val + "");

        });

    }

    @SuppressLint("SetTextI18n")
    public void setSubcarrierSpacing(Integer value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            int spacing = 0;

            switch (value) {

                case 0:
                    spacing = 15;
                    break;

                case 1:
                    spacing = 30;
                    break;

                case 2:
                    spacing = 60;
                    break;

                case 3:
                    spacing = 120;
                    break;

                case 4:
                    spacing = 240;
                    break;

            }

            binding.demodulationLayout.tvSubcarrierSpacingValue.setText(spacing + " kHz");

        });

    }

    @SuppressLint("SetTextI18n")
    public void setSSBFreq(float val) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -9999.99f || val == -999999f)
                binding.demodulationLayout.tvSsbFreqValue.setText("--.--");
            else
                binding.demodulationLayout.tvSsbFreqValue.setText(Utils.formatNumber(val, 2, false) + " MHz");

        });

    }

    /* Cell Information end
     * Power Information start*/


    @SuppressLint("SetTextI18n")
    public void setSsRsrp(float val) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -9999.99f || val == -999999f)
                binding.demodulationLayout.tvRsrpValue.setText("--.--");
            else
                binding.demodulationLayout.tvRsrpValue.setText(Utils.formatNumber(val, 2, false) + " dBm");

        });

    }

    @SuppressLint("SetTextI18n")
    public void setSsRsrq(float val) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -9999.99f || val == -999999f)
                binding.demodulationLayout.tvRsrqValue.setText("--.--");
            else
                binding.demodulationLayout.tvRsrqValue.setText(Utils.formatNumber(val, 2, false) + " dB");

        });

    }

    @SuppressLint("SetTextI18n")
    public void setSsSinr(float val) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -9999.99f || val == -999999f)
                binding.demodulationLayout.tvSinrValue.setText("--.--");
            else
                binding.demodulationLayout.tvSinrValue.setText(Utils.formatNumber(val, 2, false) + " dB");

        });

    }


    @SuppressLint("SetTextI18n")
    public void setBeamPower(float val, int idx) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -9999.99f || val == -999999f)
                binding.demodulationLayout.tvBeamPowerValue.setText("--.--");
            else
                binding.demodulationLayout.tvBeamPowerValue.setText(Utils.formatNumber(val, 2, false) + " dBm at Bm " + idx);

        });


    }

    public void setExpectedTxMaxPower(float val) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -9999.99f || val == -999999f)
                binding.demodulationLayout.tvExpectedTxMaxPowerValue.setText("--.--");
            else
                binding.demodulationLayout.tvExpectedTxMaxPowerValue.setText(Utils.formatNumber(val, 2, false) + " dBm");

        });

    }

    /*
    Power Information end
    Signal Quality start
     */


    public void setThreshold(float val) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -9999.99f || val == -999999f)
                binding.demodulationLayout.tvThresholdValue.setText("--.--");
            else
                binding.demodulationLayout.tvThresholdValue.setText(Utils.formatNumber(val, 2, false));

        });

    }

    public void setPpm(float val) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -9999.99f || val == -999999f)
                binding.demodulationLayout.tvPpmValue.setText("--.--");
            else binding.demodulationLayout.tvPpmValue.setText(Utils.formatNumber(val, 2, false));

        });

    }

    public void setHz(float val) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -9999.99f || val == -999999f)
                binding.demodulationLayout.tvHzValue.setText("--.--");
            else binding.demodulationLayout.tvHzValue.setText(Utils.formatNumber(val, 2, false));

        });

    }

    public void setOffsetResult(int result) {

        MainActivity.getActivity().runOnUiThread(() -> {

            String offsetResult = "F";

            switch (result) {

                case 0:
                    offsetResult = "F";
                    binding.demodulationLayout.tvFreqOffsetResult.setTextColor(Color.RED);
                    break;

                case 1:
                    offsetResult = "P";
                    binding.demodulationLayout.tvFreqOffsetResult.setTextColor(Color.CYAN);
                    break;

                default:
                    offsetResult = "F";
                    binding.demodulationLayout.tvFreqOffsetResult.setTextColor(Color.RED);
                    break;


            }

            binding.demodulationLayout.tvFreqOffsetResult.setText(offsetResult);


        });

    }

    public void setInformationText(AutofitTextView textView, float val) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -9999.99f || val == -999999f) textView.setText("--.--");
            else textView.setText(Utils.formatNumber(val, 2, false));

        });

    }

    public void setInformationText(AutofitTextView textView, String message) {

        MainActivity.getActivity().runOnUiThread(() -> {

            textView.setText(message);

        });

    }

//    @SuppressLint("SetTextI18n")
//    public void setInformationText(AutofitTextView textView, int val) {
//
//        MainActivity.getActivity().runOnUiThread(() -> {
//
//            if (val == -999999 || val == -9999) textView.setText("--.--");
//            else textView.setText(val + "");
//
//        });
//
//    }

    @SuppressLint("SetTextI18n")
    public void setInformationText(AutofitTextView textView, float val, String unit) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (val == -999999 || val == -9999 || val == -9999.99f) textView.setText("--.--");
            else textView.setText(Utils.formatNumber(val, 2, false) + unit);

        });

    }

    public void setPssPwr(float value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            if (value == -9999.99f)
                binding.demodulationLayout.tvPssPwrValue.setText("--.--");
            else
                binding.demodulationLayout.tvPssPwrValue.setText(Utils.formatNumber(value, 2, false));

        });

    }

    public void setModeType(AutofitTextView textView, int mode) {

        String typeMode;

        switch (mode) {

            case 0:
                typeMode = SIGNAL_QUALITY_MODE.BPSK.getMode();
                break;

            case 1:
                typeMode = SIGNAL_QUALITY_MODE.QPSK.getMode();
                break;

            case 2:
                typeMode = SIGNAL_QUALITY_MODE.QAM16.getMode();
                break;

            case 3:
                typeMode = SIGNAL_QUALITY_MODE.QAM64.getMode();
                break;

            case 4:
                typeMode = SIGNAL_QUALITY_MODE.QAM256.getMode();
                break;

            default:
                typeMode = "--";
                break;

        }

        String finalTypeMode = typeMode;
        MainActivity.getActivity().runOnUiThread(() -> {

            textView.setText(finalTypeMode);

        });

    }

    public void setPssRb(float value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            binding.demodulationLayout.tvPssRbValue.setText(Utils.formatNumber(value, 0, false));

        });

    }

    public void setSssEvm(float value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            binding.demodulationLayout.tvSssEvmValue.setText(Utils.formatNumber(value, 2, false));

        });

    }

    public void setSssPwr(float value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            binding.demodulationLayout.tvSssPwrValue.setText(Utils.formatNumber(value, 2, false));

        });

    }


    public void setSssRb(float value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            binding.demodulationLayout.tvSssRbValue.setText(Utils.formatNumber(value, 0, false));

        });

    }


    public void setPbchEvm(float value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            binding.demodulationLayout.tvPbchEvmValue.setText(Utils.formatNumber(value, 2, false));

        });

    }

    public void setPbchPwr(float value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            binding.demodulationLayout.tvPbchPwrValue.setText(Utils.formatNumber(value, 2, false));

        });

    }

    public void setPbchRb(float value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            binding.demodulationLayout.tvPbchRbValue.setText(Utils.formatNumber(value, 0, false));

        });

    }


    public void setPbchDmrsEvm(float value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            binding.demodulationLayout.tvPbchDmrsEvmValue.setText(Utils.formatNumber(value, 2, false));

        });

    }

    public void setPbchDmrsPwr(float value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            binding.demodulationLayout.tvPbchDmrsPwrValue.setText(Utils.formatNumber(value, 2, false));

        });

    }


    public void setPbchDmrsRb(float value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            binding.demodulationLayout.tvPbchDmrsRbValue.setText(Utils.formatNumber(value, 0, false));

        });

    }


//    public void setPdcchEvm(float value) {
//
//        MainActivity.getActivity().runOnUiThread(() -> {
//
//            binding.demodulationLayout.tvPdcchEvmValue.setText(Utils.formatNumber(value, 2, false));
//
//        });
//
//    }
//
//    public void setPdcchPwr(float value) {
//
//        MainActivity.getActivity().runOnUiThread(() -> {
//
//            binding.demodulationLayout.tvPdcchPwrValue.setText(Utils.formatNumber(value, 2, false));
//
//        });
//
//    }
//
//    public void setPdcchRb(float value) {
//
//        MainActivity.getActivity().runOnUiThread(() -> {
//
//            binding.demodulationLayout.tvPdcchRbValue.setText(Utils.formatNumber(value, 0, false));
//
//        });
//
//    }
//
//
//    public void setPdcchDmrsEvm(float value) {
//
//        MainActivity.getActivity().runOnUiThread(() -> {
//
//            binding.demodulationLayout.tvPdcchDmrsEvmValue.setText(Utils.formatNumber(value, 2, false));
//
//        });
//
//    }
//
//    public void setPdcchDmrsPwr(float value) {
//
//        MainActivity.getActivity().runOnUiThread(() -> {
//
//            binding.demodulationLayout.tvPdcchDmrsPwrValue.setText(Utils.formatNumber(value, 2, false));
//
//        });
//
//    }
//
//    public void setPdcchDmrsRb(float value) {
//
//        MainActivity.getActivity().runOnUiThread(() -> {
//
//            binding.demodulationLayout.tvPdcchDmrsRbValue.setText(Utils.formatNumber(value, 0, false));
//
//        });
//
//    }
//
//
//    public void setPdschEvm(float value) {
//
//        MainActivity.getActivity().runOnUiThread(() -> {
//
//            binding.demodulationLayout.tvPdschEvmValue.setText(Utils.formatNumber(value, 2, false));
//
//        });
//
//    }
//
//    public void setPdschPwr(float value) {
//
//        MainActivity.getActivity().runOnUiThread(() -> {
//
//            binding.demodulationLayout.tvPdschPwrValue.setText(Utils.formatNumber(value, 2, false));
//
//        });
//
//    }
//
//
//    public void setPdschRb(float value) {
//
//        MainActivity.getActivity().runOnUiThread(() -> {
//
//            binding.demodulationLayout.tvPdschRbValue.setText(Utils.formatNumber(value, 0, false));
//
//        });
//
//    }
//
//    public void setPdschDmrsEvm(float value) {
//
//        MainActivity.getActivity().runOnUiThread(() -> {
//
//            binding.demodulationLayout.tvPdschDmrsEvmValue.setText(Utils.formatNumber(value, 2, false));
//
//        });
//
//    }
//
//    public void setPdschDmrsPwr(float value) {
//
//        MainActivity.getActivity().runOnUiThread(() -> {
//
//            binding.demodulationLayout.tvPdschDmrsPwrValue.setText(Utils.formatNumber(value, 2, false));
//
//        });
//
//    }
//
//    public void setPdschDmrsRb(float value) {
//
//        MainActivity.getActivity().runOnUiThread(() -> {
//
//            binding.demodulationLayout.tvPdschDmrsRbValue.setText(Utils.formatNumber(value, 0, false));
//
//        });
//
//    }

    public void setTimingOffset(float value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            binding.demodulationLayout.tvTimingOffsetValue.setText(Utils.formatNumber(value, 0, false));

        });

    }

    public void setAlignmentError(float value) {

        MainActivity.getActivity().runOnUiThread(() -> {

            binding.demodulationLayout.tvTimeAlignmentErrorValue.setText(Utils.formatNumber(value, 0, false));

        });

    }

    public void clearValues() {

        MainActivity.getActivity().runOnUiThread(() -> {

            // Signal Quality
            binding.demodulationLayout.tvPssEvmValue.setText("--.--");
            binding.demodulationLayout.tvPssPwrValue.setText("--.--");
            binding.demodulationLayout.tvPssModeValue.setText("-");
            binding.demodulationLayout.tvPssRbValue.setText("--.--");

            binding.demodulationLayout.tvSssEvmValue.setText("--.--");
            binding.demodulationLayout.tvSssPwrValue.setText("--.--");
            binding.demodulationLayout.tvSssModeValue.setText("-");
            binding.demodulationLayout.tvSssRbValue.setText("--.--");

            binding.demodulationLayout.tvPbchEvmValue.setText("--.--");
            binding.demodulationLayout.tvPbchPwrValue.setText("--.--");
            binding.demodulationLayout.tvPbchModeValue.setText("-");
            binding.demodulationLayout.tvPbchRbValue.setText("--.--");

            binding.demodulationLayout.tvPbchDmrsEvmValue.setText("--.--");
            binding.demodulationLayout.tvPbchDmrsPwrValue.setText("--.--");
            binding.demodulationLayout.tvPbchDmrsModeValue.setText("-");
            binding.demodulationLayout.tvPbchDmrsRbValue.setText("--.--");

//            binding.demodulationLayout.tvPdcchEvmValue.setText("--.--");
//            binding.demodulationLayout.tvPdcchPwrValue.setText("--.--");
//            binding.demodulationLayout.tvPdcchModeValue.setText("-");
//            binding.demodulationLayout.tvPdcchRbValue.setText("--.--");
//
//            binding.demodulationLayout.tvPdcchDmrsEvmValue.setText("--.--");
//            binding.demodulationLayout.tvPdcchDmrsPwrValue.setText("--.--");
//            binding.demodulationLayout.tvPdcchDmrsModeValue.setText("-");
//            binding.demodulationLayout.tvPdcchDmrsRbValue.setText("--.--");
//
//            binding.demodulationLayout.tvPdschEvmValue.setText("--.--");
//            binding.demodulationLayout.tvPdschPwrValue.setText("--.--");
//            binding.demodulationLayout.tvPdschModeValue.setText("-");
//            binding.demodulationLayout.tvPdschRbValue.setText("--.--");
//
//            binding.demodulationLayout.tvPdschDmrsEvmValue.setText("--.--");
//            binding.demodulationLayout.tvPdschDmrsPwrValue.setText("--.--");
//            binding.demodulationLayout.tvPdschDmrsModeValue.setText("-");
//            binding.demodulationLayout.tvPdschDmrsRbValue.setText("--.--");

            //Time Offset
            binding.demodulationLayout.tvTimingOffsetValue.setText("--.--");
            binding.demodulationLayout.tvTimeAlignmentErrorValue.setText("--.--");

            //Signal Quality
            binding.demodulationLayout.tvThresholdValue.setText("--.--");
            binding.demodulationLayout.tvPpmValue.setText("--.--");
            binding.demodulationLayout.tvHzValue.setText("--.--");

            //Cell Information
            binding.demodulationLayout.tvChannelBandwidthValue.setText("--.--");
            binding.demodulationLayout.tvPhysicalCellIdValue.setText("--");
            binding.demodulationLayout.tvGroupIdValue.setText("--");
            binding.demodulationLayout.tvSectorValue.setText("--");
            binding.demodulationLayout.tvSubcarrierSpacingValue.setText("--.--");
            binding.demodulationLayout.tvSsbFreqValue.setText("--.--");

            //Power Information
            binding.demodulationLayout.tvRsrpValue.setText("--.--");
            binding.demodulationLayout.tvRsrqValue.setText("--.--");
            binding.demodulationLayout.tvBeamPowerValue.setText("--.--");
            binding.demodulationLayout.tvExpectedTxMaxPowerValue.setText("--.--");

        });

    }

}

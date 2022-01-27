package com.dabinsystems.pact_app.Function;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.Screenshot.Screenshot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Save {

    private final String DAT = ".dat";
    private final String STP = ".stp";
    private final String PNG = ".png";

    private String mFileName = Screenshot.getInstance().getFileName();
    private File mRootFile = null;
    private File mCreateFile = null;

    public void createSaveFile(String fileName, String fileType) {

        try {

            ((MainActivity)MainActivity.getActivity()).requestPermission();

            mRootFile = new File(Environment.getExternalStorageDirectory(), "PACT/Save");
            if (!mRootFile.exists()) {
                mRootFile.mkdirs();
            }
            mFileName = fileName;
            mCreateFile = new File(mRootFile, mFileName + fileType);
            FileWriter writer = new FileWriter(mCreateFile);

            if (fileType.equals(DAT)) {

                writer.append(
                        DataHandler.getInstance().getConfigCmd().replace(" ", "\n") + "\n"
                );

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

                if(mode != MeasureMode.MEASURE_MODE.SA) {

                    //Amplitude
                    writer.append((int) (FunctionHandler.getInstance().getMainLineChart().getMaxYRange() * 100) + "\n");
                    writer.append((int) (FunctionHandler.getInstance().getMainLineChart().getMinYRange() * 100) + "\n");

                    //Limit
                    writer.append((FunctionHandler.getInstance().getMainLineChart().getLimitValue() * 100) + "\n");
                    writer.append(FunctionHandler.getInstance().getMainLineChart().isEnabledLimitLine() + "\n");
                    writer.append(FunctionHandler.getInstance().getMainLineChart().isAlarm() + "\n");
//                writer.append(FunctionHandler.getInstance().getMainLineChart().isEnabledLimitMsg() + "\n");

                    //Sweep
                    writer.append(VswrDataHandler.getInstance().getConfigData().getSweepData().isRun().toString() + "\n");
                    writer.append(VswrDataHandler.getInstance().getConfigData().getSweepData().isContinuous().toString() + "\n");

                    for (int i = 0; i < MainActivity.getBinding().lineChartLayout.mainLineChart.getData().getDataSetByIndex(FunctionHandler.getInstance().getMainLineChart().getChartDataSetIndex()).getEntryCount(); i++) {

                        Double y = (double) MainActivity.getBinding().lineChartLayout.mainLineChart.getData().getDataSetByIndex(FunctionHandler.getInstance().getMainLineChart().getChartDataSetIndex()).getEntryForIndex(i).getY();
                        int yVal = (int) (y * 1000);
                        writer.append(yVal + "\n");

                    }

                } else {

                    writer.append((int)(FunctionHandler.getInstance().getMainLineChart().getRefLev() * 100) + "\n");
                    writer.append(SaDataHandler.getInstance().getConfigData().getAmplitudeData().getAttenuator() + "\n");
                    writer.append((int)(FunctionHandler.getInstance().getMainLineChart().getScaleDiv() * 100) + "\n");
                    writer.append((int)(FunctionHandler.getInstance().getMainLineChart().getOffset() * 100) + "\n");

                    MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

                    if(type != MeasureType.MEASURE_TYPE.SWEPT_SA) {

                        for (int i = 0; i < MainActivity.getBinding().lineChartLayout.mainLineChart.getData().getDataSetByIndex(FunctionHandler.getInstance().getMainLineChart().getChartDataSetIndex()).getEntryCount(); i++) {

                            Double y = (double) MainActivity.getBinding().lineChartLayout.mainLineChart.getData().getDataSetByIndex(FunctionHandler.getInstance().getMainLineChart().getChartDataSetIndex()).getEntryForIndex(i).getY();
                            int yVal = (int) (y * 1000f);
                            writer.append(yVal + "\n");

                        }

                    } else {

                        for(int i = 0; i<FunctionHandler.getInstance().getMainLineChart().MAX_TRACE_SIZE; i++) {

                            for(int j = 0; j<FunctionHandler.getInstance().getMainLineChart().getDataset(i).getEntryCount(); j++) {

                                Double y = (double) MainActivity.getBinding().lineChartLayout.mainLineChart.getData().getDataSetByIndex(i).getEntryForIndex(j).getY();
                                int yVal = (int) (y * 1000f);
                                writer.append(yVal + "\n");

                            }

                        }

                    }

                }

            }


            else if (fileType.equals(STP)) {

                writer.append(
                        DataHandler.getInstance().getConfigCmd().replace(" ", "\n") + "\n"
                );

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

                if(mode != MeasureMode.MEASURE_MODE.SA) {

                    //Amplitude
                    writer.append((int) (FunctionHandler.getInstance().getMainLineChart().getMaxYRange() * 100) + "\n");
                    writer.append((int) (FunctionHandler.getInstance().getMainLineChart().getMinYRange() * 100) + "\n");

                    //Limit
                    writer.append((FunctionHandler.getInstance().getMainLineChart().getLimitValue() * 100) + "\n");
                    writer.append(FunctionHandler.getInstance().getMainLineChart().isEnabledLimitLine() + "\n");
                    writer.append(FunctionHandler.getInstance().getMainLineChart().isAlarm() + "\n");

                    //Sweep
                    writer.append(VswrDataHandler.getInstance().getConfigData().getSweepData().isRun().toString() + "\n");
                    writer.append(VswrDataHandler.getInstance().getConfigData().getSweepData().isContinuous().toString() + "\n");

                }

            } else if (fileType.equals(PNG)) {

                Screenshot.getInstance().takeScreenShotOfRootView(MainActivity.getBinding().linParent);

                if (Screenshot.getInstance().getBitmap() != null)
                    Screenshot.getInstance().saveScreenshot(Screenshot.getInstance().getBitmap());
                else
                    Toast.makeText(MainActivity.getActivity(), "Error", Toast.LENGTH_SHORT).show();

            }

            writer.flush();
            writer.close();

            Toast.makeText(MainActivity.getActivity(), "Saved", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

}

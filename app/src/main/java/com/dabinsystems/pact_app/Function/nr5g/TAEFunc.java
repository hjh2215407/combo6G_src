package com.dabinsystems.pact_app.Function.nr5g;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrInterTaeData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrTaeData;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.Screenshot.Screenshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TAEFunc {

    AlertDialog.Builder builder = null;
    AlertDialog TaeMeasureDialog = null;
    private final int mQos = FunctionHandler.getInstance().getDataConnector().getmQoS();

    private Handler taeTimeoutHandler = null;
    private Runnable taeTimeoutRunnable = new Runnable() {
        @Override
        public void run() {
            DataHandler.getInstance().getNrData().getTaeInfoData().setTeaMeasMode(NrTaeData.TAE_MEAS_MODE.OFF);
            FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getNrData().getNrCmd());
            ViewHandler.getInstance().getTaeView().update();
            FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
            Toast.makeText(MainActivity.getContext(), "Waiting for response...\nPlease wait for a moment", Toast.LENGTH_SHORT).show();
        }
    };

    private Handler interTaeTimeoutHandler = null;
    private Runnable interTaeTimeoutRunnable = new Runnable() {
        @Override
        public void run() {

            NrTaeData taeData = DataHandler.getInstance().getNrData().getTaeInfoData();
            taeData.setCurrentMeasCount(0);
            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    DataHandler.getInstance().getRequestTaeMeasure(taeData.getCurrentPortIdx())
            );

            FunctionHandler.getInstance().getDataConnector().showProgressDialog("Waiting for response...\nPlease wait for a moment");
            startTaeCancelTimeoutHandler();
            if (TaeMeasureDialog != null) TaeMeasureDialog.dismiss();

        }
    };

    public void startInterTaeRunHandler() {

        interTaeTimeoutHandler = new Handler(Looper.getMainLooper());
        interTaeTimeoutHandler.postDelayed(interTaeTimeoutRunnable, 10000);

    }

    public void removeCallbackInterTaeHandler() {

        if (interTaeTimeoutHandler == null || interTaeTimeoutRunnable == null) return;
        interTaeTimeoutHandler.removeCallbacks(interTaeTimeoutRunnable);
        interTaeTimeoutHandler.removeCallbacksAndMessages(null);
    }

    public void startTaeCancelTimeoutHandler() {

        taeTimeoutHandler = new Handler(Looper.getMainLooper());
        taeTimeoutHandler.postDelayed(taeTimeoutRunnable, 10000);

    }

    public void removeCallbackTimeoutHandler() {

        if (taeTimeoutHandler == null || taeTimeoutRunnable == null) return;
        taeTimeoutHandler.removeCallbacks(taeTimeoutRunnable);
        taeTimeoutHandler.removeCallbacksAndMessages(null);
    }

    public void
    TaeMeasureDialog(int portNum) {

        builder = new AlertDialog.Builder(MainActivity.getActivity());

        NrTaeData taeData = DataHandler.getInstance().getNrData().getTaeInfoData();
        NrInterTaeData interData = DataHandler.getInstance().getNrData().getInterTaeData();

        if (taeData.getTaeType() == NrTaeData.TAE_TYPE.INTRA) {

            builder.setTitle("Time Alignment Error Measurement").setMessage(

                    "Please connect Base Station's RF port " + portNum + " to Combo Analyzer's SA port through RF cable."

            );

        } else {

            builder.setTitle("Time Alignment Error Measurement").setMessage(

                    "Carrier " + portNum + " timing measurement starts after 5 seconds."

            );

        }

        builder.setCancelable(false);

        builder.setPositiveButton("OK", (dialogInterface, i) -> {

            if (taeData.getTaeType() == NrTaeData.TAE_TYPE.INTER && interTaeTimeoutHandler != null && interTaeTimeoutRunnable != null)
                removeCallbackInterTaeHandler();

            taeData.setCurrentMeasCount(0);
            taeData.setCurrentPortIdx(portNum);
            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    DataHandler.getInstance().getRequestTaeMeasure(taeData.getCurrentPortIdx())
            );

            FunctionHandler.getInstance().getDataConnector().showProgressDialog("Waiting for response...\nPlease wait for a moment");
            startTaeCancelTimeoutHandler();
            dialogInterface.dismiss();

        });


        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {

            if (taeData.getTaeType() == NrTaeData.TAE_TYPE.INTER && interTaeTimeoutHandler != null && interTaeTimeoutRunnable != null)
                removeCallbackInterTaeHandler();

            taeData.setTeaMeasMode(NrTaeData.TAE_MEAS_MODE.OFF);
            FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.NR_5G);
            FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getNrData().getNrCmd());
            taeData.setCurrentMeasCount(0);
            taeData.setCurrentPortIdx(1);
            interData.setCurrentCarrier(0);
            removeCallbackTimeoutHandler();
            ViewHandler.getInstance().getTaeView().update();
            dialogInterface.dismiss();

            //original source
//            FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getRequestMainDataCmd(), 2);
            //original source

            //Qos fix
            //@@ mQos 로 변경
            FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getRequestMainDataCmd(), mQos);
            //Qos fix
        });

        TaeMeasureDialog = builder.create();
        TaeMeasureDialog.show();

        if (taeData.getTaeType() == NrTaeData.TAE_TYPE.INTER)
            startInterTaeRunHandler();

    }

    public void completeTaeMeasureDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getActivity());
        NrTaeData taeData = DataHandler.getInstance().getNrData().getTaeInfoData();
        NrInterTaeData interData = DataHandler.getInstance().getNrData().getInterTaeData();

        if (taeData.getTaeType() == NrTaeData.TAE_TYPE.INTRA) {

            builder.setTitle("Time Alignment Error Measurement").setMessage(

                    "Intra-TAE measurement is completed.\nDo you want to continue TAE measurement?"

            );

        } else {

            builder.setTitle("Time Alignment Error Measurement").setMessage(

                    "Inter-TAE measurement is completed.\nDo you want to continue TAE measurement?"

            );

        }


        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
//            FunctionHandler.getInstance().getDataConnector().sendCommand(
//                    getRequestUpdateCmd()
//            );
            taeData.setCurrentMeasCount(0);
            taeData.setCurrentPortIdx(1);
            interData.setCurrentCarrier(0);

            ViewHandler.getInstance().getTaeView().update();
            ViewHandler.getInstance().getContent().updateView();
            TaeMeasureDialog(1);
            dialogInterface.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            DataHandler.getInstance().getNrData().getTaeInfoData().setTeaMeasMode(NrTaeData.TAE_MEAS_MODE.OFF);
            FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.NR_5G);
            ViewHandler.getInstance().getTaeView().update();
            ViewHandler.getInstance().getContent().updateView();
            ViewHandler.getInstance().getNrMeasSetupView().update();

            taeData.setCurrentMeasCount(0);
            taeData.setCurrentPortIdx(1);
            interData.setCurrentCarrier(0);

            FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getNrData().getNrCmd());

            //original source
//            FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getRequestMainDataCmd(), 2);
            //original source

            //Qos fix
            FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getRequestMainDataCmd(), mQos);
            //Qos fix

            FunctionHandler.getInstance().getDataConnector().startDataTimeoutHandler();
            dialogInterface.dismiss();
        });

        builder.setNeutralButton("Screenshot", (dialog, i) ->{
            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
            String timeStamp = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss").format(new Date());
            String fileName = type + timeStamp;
            Screenshot.getInstance().capture(MainActivity.getBinding().linParent.getRootView(), fileName);

            taeData.setCurrentMeasCount(0);
            taeData.setCurrentPortIdx(1);
            interData.setCurrentCarrier(0);

            DataHandler.getInstance().getNrData().getTaeInfoData().setTeaMeasMode(NrTaeData.TAE_MEAS_MODE.OFF);
            FunctionHandler.getInstance().getMeasureType().setMeasureType(MeasureType.MEASURE_TYPE.NR_5G);
            FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getNrData().getNrCmd());
            ViewHandler.getInstance().getTaeView().update();
            ViewHandler.getInstance().getContent().updateView();
            ViewHandler.getInstance().getNrMeasSetupView().update();
            FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getNrData().getNrCmd());

            //original source
//            FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getRequestMainDataCmd(), 2);
            //original source

            //Qos fix
            FunctionHandler.getInstance().getDataConnector().sendCommand(DataHandler.getInstance().getRequestMainDataCmd(), mQos);
            //Qos fix

            FunctionHandler.getInstance().getDataConnector().startDataTimeoutHandler();
            dialog.dismiss();

        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


}

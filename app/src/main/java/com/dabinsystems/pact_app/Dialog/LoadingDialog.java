package com.dabinsystems.pact_app.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.dabinsystems.pact_app.Function.CalibrationFunc;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog dialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_progress_bar, null));
        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                FunctionHandler.getInstance().getCalibrationFunc().setStep(CalibrationFunc.CALIBRATION.CAL_MODE);
                ViewHandler.getInstance().getStartCalibrationView().update();
                Toast.makeText(activity, "Calibration has been suspended.", Toast.LENGTH_SHORT).show();
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }
}

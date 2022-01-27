package com.dabinsystems.pact_app.Function;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Data.FWUpdateData;
import com.dabinsystems.pact_app.Dialog.FwUpdateProgress;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Util.FTPUploader;
import com.dabinsystems.pact_app.Util.UpdateFileParser;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

//import org.eclipse.paho.client.mqttv3.internal.wire.CountingInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class FWFunc {

    InitActivity mActivity;
    ActivityMainBinding binding;

    private boolean isUpdating = false;

    private FwUpdateProgress UpdateProgress;
    private FtpUploadTask UpdateAsyncUploader;

    private String FWVer = "-";

    private int UpdateTimeout = 50;
    private int currentTimeout = UpdateTimeout;

    private Handler CompleteUpdateTimeoutHandler;
    private Runnable CompleteUpdateTimeoutRunnable = new Runnable() {
        @Override
        public void run() {

            FunctionHandler.getInstance().getDataConnector().showProgressDialog("Restarting device..." + currentTimeout);

            if (currentTimeout == 0 || !isUpdating) {

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
                    completeUpdate();
                    getUpdateProgress().dismiss();
                    FunctionHandler.getInstance().getWifiFunc().showDialog();
                    currentTimeout = UpdateTimeout;
                }, 1000);
                return;

            }

            currentTimeout--;
            startCompleteUpdateTimeoutHandler();

        }
    };

    public enum FW_CMD {

        FW_CMD(0x07),
        CHECK_VER(0x00),
        REQEUST_UPDATE(0x01),
        READY_UPDATE(0x02),
        SUCCESS_TRANSFER(0x03),
        COMPLETE_UPDATE(0x04),
        FAIL_TRANSFER(0x05);

        private final int value;

        FW_CMD(int val) {

            value = val;

        }

        public int getValue() {
            return value;
        }

        public String getHexString() {

            return DataHandler.getInstance().intToHexStr(value);

        }

        public byte getByte() {

            byte b = (byte) value;
            return b;

        }

    }

    public FWFunc(InitActivity activity, ActivityMainBinding binding) {

        mActivity = activity;
        this.binding = binding;

    }

    public void updateFwInfo(String[] cmd) {

        if (cmd[1].equals(FW_CMD.CHECK_VER.getHexString())) {

            try {

                FWVer = cmd[2];

                InitActivity.logMsg("Ver", FWVer + "");

                SharedPreferences sp = mActivity.getSharedPreferences("AppInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("FWVer", FWVer);
                editor.apply();

//                updateDialog();

                InitActivity.logMsg("updateFwInfo", checkVer() + "");

                if (checkVer() == null) {
                    updateDialog();
                } else if (checkVer()) {
                    updateDialog();
                } else {
                    versionInfoDialog();
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        } else if (cmd[1].equals(FW_CMD.READY_UPDATE.getHexString())) {

            //FW is prepared to update;

            startFwUpdate();

        } else if (cmd[1].equals(FW_CMD.COMPLETE_UPDATE.getHexString())) {

            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(mActivity, "Complete update... Please connect to divice.", Toast.LENGTH_SHORT).show();
            });


        }

    }

    private void startFwUpdate() {

        new Handler(Looper.getMainLooper()).post(() -> {

            if (getUpdateProgress().isShowing()) {
                Toast.makeText(mActivity, "Already updating...", Toast.LENGTH_SHORT).show();
                return;
            }

            isUpdating = true;
            FunctionHandler.getInstance().getDataConnector().removeDataTimeoutHandler();
            UpdateAsyncUploader = null;
            UpdateAsyncUploader = new FtpUploadTask();

            UpdateAsyncUploader.execute();

        });

    }

    public FwUpdateProgress getUpdateProgress() {
        if (UpdateProgress == null) UpdateProgress = new FwUpdateProgress(mActivity, binding);
        return UpdateProgress;
    }

    public boolean isUpdating() {
        return isUpdating;
    }

    public void completeUpdate() {

        isUpdating = false;
        FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
        FunctionHandler.getInstance().getDataConnector().startDataTimeoutHandler();
        removeUpdateTimeoutHandler();
        getUpdateProgress().completeUpdate();
    }

    public String getFwVer() {

        return FWVer;
    }

    public String getVerCheckCmd() {

        String cmd = FWFunc.FW_CMD.FW_CMD.getHexString() + " " + FWFunc.FW_CMD.CHECK_VER.getHexString();
        return cmd;
    }

    public String getRequestUpdateCmd() {

        String cmd = FWFunc.FW_CMD.FW_CMD.getHexString() + " " + FW_CMD.REQEUST_UPDATE.getHexString();
        return cmd;

    }

    public String getSuccessTransferCmd() {

        String cmd = FWFunc.FW_CMD.FW_CMD.getHexString() + " " + FW_CMD.SUCCESS_TRANSFER.getHexString();
        return cmd;

    }

    public String getFailedTransferCmd() {

        String cmd = FWFunc.FW_CMD.FW_CMD.getHexString() + " " + FW_CMD.FAIL_TRANSFER.getHexString();
        return cmd;


    }


    /*
    return null -> app version is low.. need app apk update
    return true -> current fw version is low .. need fw update
    return false -> latest version
     */
    public Boolean checkVer() {

        SharedPreferences preferences = mActivity.getSharedPreferences("AppInfo", Context.MODE_PRIVATE);
        String fwVer = preferences.getString("FWVer", "-");

        UpdateFileParser parser = new UpdateFileParser();
        FWUpdateData updateFileInfo = parser.parsing();

        try {

//            if (fwVer == null) {
//                Toast.makeText(mActivity, "retry ver check once again...", Toast.LENGTH_SHORT).show();
//                return null;
//            }

            String[] currentFwVer = fwVer.split("\\.");
            String[] recommandedFwVer = updateFileInfo.getFwVersion().split("\\.");

            InitActivity.logMsg("VersionInfo", "current ver : " + fwVer + " latest ver : " + updateFileInfo.getFwVersion() + " currentFwVerLength : " + currentFwVer.length);

            if (fwVer.equals(updateFileInfo.getFwVersion())) {
                return false;
            }

            for (int i = 0; i < recommandedFwVer.length; i++) {

                int current = Integer.parseInt(currentFwVer[i]);
                int recommand = Integer.parseInt(recommandedFwVer[i]);
                InitActivity.logMsg("VersionInfo", "current ver : " + current + " latest ver : " + recommand + " i : " + i);

                if (current < recommand) {
                    InitActivity.logMsg("VersionInfoTrue", "current ver : " + current + " latest ver : " + recommand + " i : " + i);
                    return true;
                } else if (i == currentFwVer.length - 1 && current == recommand) {
                    InitActivity.logMsg("VersionInfoFalse", "current ver : " + current + " latest ver : " + recommand + " i : " + i);
//                    InitActivity.logMsg("VersionInfo", "current ver : " + current  +  " latest ver : " + latest + " i : " + i);
                    return false;
                } else if (current > recommand) {
                    InitActivity.logMsg("VersionInfoNull", "current ver : " + current + " latest ver : " + recommand + " i : " + i);
//                    InitActivity.logMsg("VersionInfo", "current ver : " + current  +  " latest ver : " + latest + " i : " + i);
                    return null;
                }

            }

            return null;

//            if (currentFwVer < latestFwVer) {
//
//                return true;
//
//            } else if (currentFwVer == latestFwVer) {
//
//                return false;
//
//            } else {
//
//                return null;
//
//            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return null;
        }

        return null;

    }

    public void versionInfoDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        String appVersion = "";
        SharedPreferences preferences = mActivity.getSharedPreferences("AppInfo", Context.MODE_PRIVATE);
        String fwVer = preferences.getString("FWVer", "-");

        UpdateFileParser parser = new UpdateFileParser();
        FWUpdateData updateFileInfo = parser.parsing();

        try {
            PackageInfo pInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
            appVersion = pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        builder.setTitle("Version Info").setMessage(

                "Connected SSID ...                " + WifiFunc.getSSID().replace("\"", "") + "\n" +
                        "FW Version ...                         " + fwVer + "\n" +
                        "Application Version ...           " + appVersion + "\n" +
                        "Recommended Version ...    " + updateFileInfo.getFwVersion() + "\n"

        );

        builder.setCancelable(false);

        builder.setPositiveButton("OK", (dialogInterface, i) -> {
//            startFwUpdate();
            dialogInterface.dismiss();
        });
//
//        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
//            dialogInterface.dismiss();
//        });

        new Handler(Looper.getMainLooper()).post(() -> {

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });


    }

    public void checkDowngradeDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

        builder.setTitle("Downgrade").setMessage(
                "Do you really want to lower the version?"
        );

        builder.setCancelable(false);

        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    getRequestUpdateCmd()
            );
            dialogInterface.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void updateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        String appVersion = "";
        SharedPreferences preferences = mActivity.getSharedPreferences("AppInfo", Context.MODE_PRIVATE);
        String fwVer = preferences.getString("FWVer", "-");

        UpdateFileParser parser = new UpdateFileParser();
        FWUpdateData updateFileInfo = parser.parsing();

        try {
            PackageInfo pInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
            appVersion = pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        builder.setTitle("Version Info").setMessage(

                "Connected SSID ...                 " + WifiFunc.getSSID().replace("\"", "") + "\n" +
                        "FW Version ...                         " + fwVer + "\n" +
                        "Application Version ...           " + appVersion + "\n" +
                        "Recommended Version ...    " + updateFileInfo.getFwVersion() + "\n\n" +
                        "Connected device version is different...\n" +
                        "Do you want to start the update?"

        );

        builder.setCancelable(false);

        builder.setPositiveButton("OK", (dialogInterface, i) -> {

            if (checkVer() == null) {
                checkDowngradeDialog();
            } else {
                FunctionHandler.getInstance().getDataConnector().sendCommand(
                        getRequestUpdateCmd()
                );
            }
            dialogInterface.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        new Handler(Looper.getMainLooper()).post(() -> {
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });


    }

    public void startCompleteUpdateTimeoutHandler() {

        if (CompleteUpdateTimeoutHandler == null) CompleteUpdateTimeoutHandler = new Handler();
        CompleteUpdateTimeoutHandler.postDelayed(CompleteUpdateTimeoutRunnable, 1000);

    }

    public void removeUpdateTimeoutHandler() {

        try {

            if (CompleteUpdateTimeoutHandler == null || CompleteUpdateTimeoutRunnable == null)
                return;

            CompleteUpdateTimeoutHandler.removeCallbacks(CompleteUpdateTimeoutRunnable);
            CompleteUpdateTimeoutHandler.removeCallbacksAndMessages(null);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private class FtpUploadTask extends AsyncTask<String, Void, String> {

        FTPUploader uploader;
        FWUpdateData updateData;

        @Override
        protected void onPreExecute() {
            UpdateFileParser parser = new UpdateFileParser();
            updateData = parser.parsing();
            getUpdateProgress().show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {


            uploadData();
            uploader.disconnect();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mActivity.runOnUiThread(() -> {
                getUpdateProgress().setDescription("Complete!!");
                getUpdateProgress().dismiss();
            });

            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    FunctionHandler.getInstance().getFwFunc().getSuccessTransferCmd()
            );

            startCompleteUpdateTimeoutHandler();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


        int total, send;

        private void upload(InputStream is, String fileName) throws IOException {
            int size = is.available();
            getUpdateProgress().setDescription("updating " + fileName + " ... " + size);
            uploader.uploadFile(is, fileName, updateData.getPath());
            send += size;
            getUpdateProgress().setProgress((int) ((double) send / (double) total * 100f));
        }

        private void uploadData() {

            FunctionHandler.getInstance().getDataConnector().removeCallback();

            total = send = 0;

            try {
                uploader = new FTPUploader(mActivity.getResources().getString(R.string.ip_address),
                        "",
                        "");

                String updateFileBuilder = "fw_update_folder" + File.separator;

                InputStream pactFwFile = mActivity.getAssets().open(updateFileBuilder + updateData.getFwName());
                int fwSize = pactFwFile.available();
                InputStream elfMd5File = mActivity.getAssets().open(updateFileBuilder + updateData.getMd5ElfName());
                int elfMd5Size = elfMd5File.available();

                InputStream vswrFile = mActivity.getAssets().open(updateFileBuilder + updateData.getFpgaVswrName());
                int vswrSize = vswrFile.available();
                InputStream vswrMd5File = mActivity.getAssets().open(updateFileBuilder + updateData.getMd5VswrName());
                int vswrMd5Size = vswrMd5File.available();

                InputStream saFile = mActivity.getAssets().open(updateFileBuilder + updateData.getFpgaSaName());
                int saSize = saFile.available();
                InputStream saMd5File = mActivity.getAssets().open(updateFileBuilder + updateData.getMd5SaName());
                int saMd5Size = saMd5File.available();

                InputStream lteFile = mActivity.getAssets().open(updateFileBuilder + updateData.getLteFileName());
                int lteSize = lteFile.available();
                InputStream lteMd5File = mActivity.getAssets().open(updateFileBuilder + updateData.getMd5LteName());
                int lteMd5Size = lteMd5File.available();

                InputStream updateFile = mActivity.getAssets().open(updateFileBuilder + updateData.getUpdateShellFileName());
                int updateSize = updateFile.available();
                int fileSize = fwSize + elfMd5Size + vswrSize + vswrMd5Size + saSize + saMd5Size + lteSize + lteMd5Size + updateSize;

                InitActivity.logMsg("uploadData", "fileSize -> " + fwSize + " " + elfMd5Size + " " + vswrSize + " " + vswrMd5Size + " " + saSize + " " + saMd5Size + " total -> " + fileSize);

                total = fileSize;

                upload(pactFwFile, updateData.getFwName());
                upload(elfMd5File, updateData.getMd5ElfName());
                upload(vswrFile, updateData.getFpgaVswrName());
                upload(vswrMd5File, updateData.getMd5VswrName());
                upload(saFile, updateData.getFpgaSaName());
                upload(saMd5File, updateData.getMd5SaName());
                upload(lteFile, updateData.getLteFileName());
                upload(lteMd5File, updateData.getMd5LteName());
                upload(updateFile, updateData.getUpdateShellFileName());

                pactFwFile.close();
                elfMd5File.close();

                vswrFile.close();
                vswrMd5File.close();

                saFile.close();
                saMd5File.close();

                lteFile.close();
                lteMd5File.close();

                updateFile.close();

                // 팝업이 너무 빨리 사라져서 잠시 멈춤
                Thread.sleep(2000);

            } catch (Exception e) {
                e.printStackTrace();

                mActivity.runOnUiThread(() -> {

                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            FunctionHandler.getInstance().getFwFunc().getFailedTransferCmd()
                    );
                    isUpdating = false;
                    FunctionHandler.getInstance().getDataConnector().dismissProgressDialog();
                    removeUpdateTimeoutHandler();
                    getUpdateProgress().dismiss();
                    getUpdateProgress().failUpdate();
                    Toast.makeText(mActivity, "Update Failed...", Toast.LENGTH_SHORT).show();

                });

            }
        }

    }


}
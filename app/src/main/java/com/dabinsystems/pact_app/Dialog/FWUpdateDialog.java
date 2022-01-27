//package com.dabinsystems.pact_app.Dialog;
//
//import android.content.res.AssetManager;
//import androidx.databinding.DataBindingUtil;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import androidx.appcompat.app.AlertDialog;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.dabinsystems.pact_app.Activity.MainActivity;
//import com.dabinsystems.pact_app.Function.FWFunc;
//import com.dabinsystems.pact_app.Handler.FunctionHandler;
//import com.dabinsystems.pact_app.List.Adapter.UpdateFileListAdapter;
//import com.dabinsystems.pact_app.List.Item.UpdateFileListItem;
//import com.dabinsystems.pact_app.Function.DataConnector;
//import com.dabinsystems.pact_app.R;
//import com.dabinsystems.pact_app.Util.FTPUploader;
//import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
//import com.dabinsystems.pact_app.databinding.FwUpdateDialogBinding;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//
//import static com.dabinsystems.pact_app.Activity.InitActivity.getActivity;
//import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;
//
//public class FWUpdateDialog extends CustomBaseDialog {
//
//    private final int mTimeoutForUpdate = 120;
//    private final int mDelayForTimeout = 1000;
//
//    private int mTimeForWaiting = 0;
//
//    private Runnable mTimeoutForUpdateRun = new Runnable() {
//        @Override
//        public void run() {
//
//            if (mTimeForWaiting < mTimeoutForUpdate) {
//                mTimeForWaiting++;
//                InitActivity.logMsg("mTimeForWating", mTimeForWaiting + "");
//                startTimeoutHandler();
//            } else {
//                pDialog.dismiss();
//                stopTimeoutHandler();
//                Toast.makeText(mActivity, "Please check WiFi connection...", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
//
//    private Handler mTimeoutHandler;
//
//    private void startTimeoutHandler() {
//
//        if (mTimeoutHandler == null) mTimeoutHandler = new Handler();
//        mTimeoutHandler.postDelayed(mTimeoutForUpdateRun, mDelayForTimeout);
//
//    }
//
//    private void stopTimeoutHandler() {
//
//        if (mTimeoutHandler != null) {
//
//            mTimeoutHandler.removeCallbacksAndMessages(null);
//            mTimeoutHandler = null;
//
//        }
//
//    }
//
//    private FwUpdateDialogBinding mUpdateBinding;
//    private ActivityMainBinding mMainBinding;
//    private UpdateFileListAdapter mFileListAdapter;
//    private ArrayList<UpdateFileListItem> mFileListInfoList;
//    private MainActivity mActivity;
//    private FtpAsyncTask mFtpAsyncTask;
//
//    private AlertDialog.Builder mDialogBuilder;
//    private AlertDialog pDialog;
//
//    private int mVersion = -1;
//    private String mVersionStr = "";
//    private String mFwUpdatePath = "";
//    private String mUpdateFileName = "";
//    private String mChecksumFileName = "";
//
//    private JSONObject json;
//
//    public FWUpdateDialog(MainActivity activity, ActivityMainBinding binding) {
//        super(activity, R.style.AppFullScreenTheme);
//
//        mActivity = activity;
//        mMainBinding = binding;
//        mFileListInfoList = new ArrayList<>();
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mUpdateBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.fw_update_dialog, null, false);
//        setContentView(mUpdateBinding.getRoot());
//
//        setValues();
//        addEvents();
//
//    }
//
//    private void setValues() {
//
//        viewSetting();
//        showFileList();
//
//    }
//
//    private void viewSetting() {
//
//        mActivity.runOnUiThread(() -> {
//
//
//            mUpdateBinding.tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, getActivity().getApplicationContext()));
//            mUpdateBinding.tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, getActivity().getApplicationContext()));
//            mUpdateBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, getActivity().getApplicationContext()));
//
//            mUpdateBinding.tvOK.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, getActivity().getApplicationContext()));
//            mUpdateBinding.tvOK.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, getActivity().getApplicationContext()));
//            mUpdateBinding.tvOK.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, getActivity().getApplicationContext()));
//
//            mUpdateBinding.tvCancel.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, getActivity().getApplicationContext()));
//            mUpdateBinding.tvCancel.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, getActivity().getApplicationContext()));
//            mUpdateBinding.tvCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, getActivity().getApplicationContext()));
//
//            mUpdateBinding.tvUpdateInfo.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, getActivity().getApplicationContext()));
//            mUpdateBinding.tvUpdateInfo.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(18, getActivity().getApplicationContext()));
//            mUpdateBinding.tvUpdateInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(18, getActivity().getApplicationContext()));
//
//
//        });
//
//    }
//
//    public void addEvents() {
//
//        mUpdateBinding.tvOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startFWUpdateProcess();
//            }
//        });
//
//        mUpdateBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//
//    }
//
//    private void showDialog(String message) {
//
//        mActivity.runOnUiThread(() -> {
//
//            if (pDialog == null) {
//                mDialogBuilder = new AlertDialog.Builder(mActivity);
//                mDialogBuilder.setCancelable(false);
//                mDialogBuilder.setView(R.layout.layout_loading_dialog);
//                pDialog = mDialogBuilder.create();
//            }
//
//            try {
//                if (!pDialog.isShowing())
//                    pDialog.show();
//
//                TextView text = pDialog.findViewById(R.id.tvMessage);
//                if (text != null) {
//                    text.setText(message);
//                }
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//
//                if (pDialog != null && pDialog.isShowing()) {
//
//                    pDialog.dismiss();
//
//                }
//
//                if (pDialog != null) {
//                    pDialog.show();
//                }
//
//            }
//        });
//
//    }
//
//    private void startFWUpdateProcess() {
//
////        FunctionHandler.getInstance().getFwFunc().startFwUpdate();
//
//        showDialog("Check Connection...");
//
//        new Thread(() -> {
//
//            if (!FunctionHandler
//                    .getInstance()
//                    .getWifiFunc()
//                    .getWifiConnector()
//                    .getWifiManager()
//                    .getConnectionInfo()
//                    .getSSID()
//                    .contains(mActivity.getResources().getString(R.string.pact_ssid_name1))
//
//                && !FunctionHandler
//                    .getInstance()
//                    .getWifiFunc()
//                    .getWifiConnector()
//                    .getWifiManager()
//                    .getConnectionInfo()
//                    .getSSID()
//                    .contains(mActivity.getResources().getString(R.string.pact_ssid_name2))) {
//
//                mActivity.runOnUiThread(() -> {
//
//                    Toast.makeText(mActivity, "No connection to WiFi", Toast.LENGTH_SHORT).show();
//
//                    pDialog.dismiss();
//
//                });
//
//                FunctionHandler.getInstance().getFwFunc().finishFwUpdate();
//
//            } else if (!FunctionHandler.getInstance().getDataConnector().isMqttConnected()) {
//
//                mActivity.runOnUiThread(() -> {
//
//                    Toast.makeText(mActivity, "No connection to PACT-FW", Toast.LENGTH_SHORT).show();
//                    pDialog.dismiss();
//
//                });
//
//                FunctionHandler.getInstance().getFwFunc().finishFwUpdate();
//
//                return;
//
//            } else {
//
//                showDialog("Checking Update File Info...");
//
//                while (true) {
//
//                    FunctionHandler.getInstance().getDataConnector().sendCommand(
//                            FunctionHandler.getInstance().getFwFunc().getVerCheckCmd()
//                    );
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    if (!FunctionHandler.getInstance().getFwFunc().getFwVer().equals("") && !FunctionHandler.getInstance().getFwFunc().getFwVer().equals("-")) {
//
//                        String jsonStr = loadJSONFromAsset("fw_update_folder/fw_info.json");
//                        try {
//
//                            json = new JSONObject(jsonStr);
//                            mVersion = Integer.parseInt(json.getString("version").replace(".", ""));
//                            mUpdateFileName = json.getString("firmware");
//                            mChecksumFileName = json.getString("md5");
//                            mFwUpdatePath = json.getString("path");
//
//                            InitActivity.logMsg("json_debug", mVersion + " " + mUpdateFileName + " " + mChecksumFileName + " " + mFwUpdatePath);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            dismiss();
//                        } catch (NullPointerException e) {
//                            e.printStackTrace();
//                        }
//
//                        break;
//                    }
//
//                }
//
//                showDialog("Checking FW Ver...");
//
//                while (FunctionHandler.getInstance().getFwFunc().getFwVer().equals("")
//                        || FunctionHandler.getInstance().getFwFunc().getFwVer().equals("-")) {
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                try {
//
//                    if (Integer.parseInt(FunctionHandler.getInstance().getFwFunc().getFwVer().replace(".", "")) >= mVersion) {
//
//                        mActivity.runOnUiThread(() -> {
//
//                            Toast.makeText(mActivity, "Already updated...", Toast.LENGTH_SHORT).show();
//                            pDialog.dismiss();
//
//                        });
//
//                        FunctionHandler.getInstance().getFwFunc().finishFwUpdate();
//
//                        InitActivity.logMsg("VerCheck", Integer.parseInt(
//                                FunctionHandler.getInstance().getFwFunc().getFwVer().replace(".", "")) + " " + mVersion);
//
//                        return;
//
//                    }
//
//                    InitActivity.logMsg("json_debug", mVersion + " " + mUpdateFileName + " " + mChecksumFileName + " " + mFwUpdatePath);
//
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//
//                FunctionHandler.getInstance().getDataConnector().sendCommand(
//                        FunctionHandler.getInstance().getFwFunc().getRequestUpdateCmd()
//                );
//                showDialog("Waiting for FW Response...");
//
//                while (!FunctionHandler.getInstance().getFwFunc().isAckReady()) {
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//            }
//
//            showDialog("Transferring update file to PACT...");
//
//            if (mFtpAsyncTask == null)
//                mFtpAsyncTask = new
//
//                        FtpAsyncTask(DataConnector.getUrl()
//                        .replace("tcp://", ""), "", "");
//
//            try {
//                mFtpAsyncTask.execute();
//            } catch (
//                    IllegalStateException e) {
//                e.printStackTrace();
//            }
//
//            while (!FunctionHandler.getInstance().getFwFunc().isUpdating()) {
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            mActivity.runOnUiThread(() -> {
//
//                pDialog.dismiss();
//                Toast.makeText(mActivity, "Complete update.", Toast.LENGTH_SHORT).show();
//                stopTimeoutHandler();
//                this.dismiss();
//
//            });
//
//            FunctionHandler.getInstance().getFwFunc().finishFwUpdate();
//
//            checkVer();
//
//            mFtpAsyncTask.cancel(true);
//            mFtpAsyncTask = null;
//
//
//        }).start();
//
//    }
//
//    private void checkVer() {
//
//        new Thread(() -> {
//
//            if (!FunctionHandler
//                    .getInstance()
//                    .getWifiFunc()
//                    .isDeviceWifiConnected()) {
//
//                mActivity.runOnUiThread(() -> {
//
//                    Toast.makeText(mActivity, "No connection to WiFi", Toast.LENGTH_SHORT).show();
//
//                    String noConnectionStr = "No connection to WiFi";
//                    mUpdateBinding.tvUpdateInfo.setText(noConnectionStr);
//
//                    try {
//                        pDialog.dismiss();
//                    } catch (NullPointerException e) {
//                        e.printStackTrace();
//                    }
//
//                });
//
//            } else if (!FunctionHandler.getInstance().getDataConnector().isMqttConnected()) {
//
//                mActivity.runOnUiThread(() -> {
//
//                    Toast.makeText(mActivity, "No connection to PACT-FW", Toast.LENGTH_SHORT).show();
//                    mUpdateBinding.tvUpdateInfo.setText("No connection to FW");
//
//                    try {
//                        pDialog.dismiss();
//                    } catch (NullPointerException e) {
//                        e.printStackTrace();
//                    }
//
//                });
//
//                return;
//
//            } else {
//
//                while (true) {
//
//                    if (!FunctionHandler.getInstance().getWifiFunc().isDeviceWifiConnected() || !FunctionHandler.getInstance().getDataConnector().isMqttConnected()) {
//
//                        Toast.makeText(mActivity, "Network state is unstable...", Toast.LENGTH_SHORT).show();
//                        return;
//
//                    }
//
//                    FunctionHandler.getInstance().getDataConnector().sendCommand(
//                            FWFunc.FW_CMD.CHECK_VER.getHexString()
//                    );
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    if (!FunctionHandler.getInstance().getFwFunc().getFwVer().equals("")
//                            && !FunctionHandler.getInstance().getFwFunc().getFwVer().equals("-")) {
//
//                        break;
//
//                    }
//
//                }
//
//                try {
//                    mActivity.runOnUiThread(() -> {
//
//                        String verStr = "Current Version : " +
//                                FunctionHandler.getInstance().getFwFunc().getFwVer();
//                        mUpdateBinding.tvUpdateInfo.setText(verStr);
//
//
//                    });
//
//                } catch (
//                        NullPointerException | IllegalArgumentException e) {
//                    mUpdateBinding.tvUpdateInfo.setText("Unknown");
//                    e.printStackTrace();
//                }
//
//            }
//
//            try {
//                pDialog.dismiss();
//            } catch (
//                    NullPointerException e) {
//                e.printStackTrace();
//            }
//
//        }).start();
//
//    }
//
//    private void showFileList() {
//
//        mFileListInfoList.clear();
//
//        String jsonStr = loadJSONFromAsset("fw_update_folder/fw_info.json");
//
//        try {
//
//            json = new JSONObject(jsonStr);
//            mVersionStr = json.getString("version");
//            mVersion = Integer.parseInt(mVersionStr.replace(".", ""));
//            mUpdateFileName = json.getString("firmware");
//            mChecksumFileName = json.getString("md5");
//            mFwUpdatePath = json.getString("path");
//
//            InitActivity.logMsg("json_debug", mVersion + " " + mUpdateFileName + " " + mChecksumFileName + " " + mFwUpdatePath);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Toast.makeText(mActivity, "Can't read update info...", Toast.LENGTH_SHORT).show();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            Toast.makeText(mActivity, "Can't find update info...", Toast.LENGTH_SHORT).show();
//        }
//
//        checkVer();
//        readFileList();
//
//        mFileListAdapter = new UpdateFileListAdapter(mFileListInfoList, mUpdateBinding, mActivity);
//        mUpdateBinding.rvUpdateFileList.setAdapter(mFileListAdapter);
//        mUpdateBinding.rvUpdateFileList.setLayoutManager(new LinearLayoutManager(mActivity));
//        mFileListAdapter.notifyDataSetChanged();
//
//
//    }
//
//    private void readFileList() {
//
//        AssetManager aMgr = mActivity.getResources().getAssets();
//        String[] updateFileList;
//        try {
//            updateFileList = aMgr.list("fw_update_folder");
//        } catch (IOException e) {
//            e.printStackTrace();
//            updateFileList = null;
//        }
//
//        if (updateFileList != null) {
//
//            InitActivity.logMsg("readFileList", updateFileList.length + "");
//
//            for (String name : updateFileList) {
//
//                if (name.contains(".elf") && !mVersionStr.equals("")) {
//                    mFileListInfoList.add(new UpdateFileListItem(mVersionStr));
//                }
//
//            }
//
//        } else InitActivity.logMsg("readFileList", "else");
//
//    }
//
//    private String loadJSONFromAsset(String name) {
//        String json = null;
//        try {
//            InputStream is = getActivity().getAssets().open(name);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//
//        return json;
//    }
//
//    @Override
//    public void show() {
//        super.show();
//
//        showDialog("Checking version...");
//    }
//
//    private class FtpAsyncTask extends AsyncTask<Void, Void, Void> {
//
//        String mHost = "IP";
//        int mPort = 21;
//        int mPercent = 0;
//        String mUser = "";
//        String mPassword = "";
//
//
//        private FtpAsyncTask(String host, String user, String pw) {
//            super();
//            mHost = host;
//            mUser = user;
//            mPassword = pw;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            showDialog("Please don't close the app.\nFW is being updated...");
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            InitActivity.logMsg("FtpAsyncTask", "doInBackground");
//            final String assetDir = "fw_update_folder";
//            uploadData(assetDir, mHost, mUser, mPassword);
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
////            pDialog.dismiss();
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            InitActivity.logMsg("FtpAyncTask", "onProgressUpdate");
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected void onCancelled(Void aVoid) {
//
//            InitActivity.logMsg("FtpAsyncTask", "onCancelled");
//            super.onCancelled(aVoid);
//        }
//
//        private void uploadData(String assetDir, String host, String user, String pw) {
//
//            if (pDialog == null) {
//
//                showDialog("Please don't close the app.\nFW is being updated..." + mPercent + "%");
//
//            }
//
//            System.out.println("Start");
//            FTPUploader ftpUploader = null;
//            try {
//
////                String localFileFullName = mPath + File.separator + mDirectoryName + File.separator + mFileListAdapter.getSelectedItem().getFileName();
//                ftpUploader = new FTPUploader(host, user, pw);
//
//
//                String updateFileBuilder = assetDir +
//                        File.separator +
//                        mUpdateFileName;
//
//                String checksumFileBuilder = assetDir +
//                        File.separator +
//                        mChecksumFileName;
//
//                InputStream updateFile = mActivity.getAssets().open(updateFileBuilder);
//                InputStream md5File = mActivity.getAssets().open(checksumFileBuilder);
//
//                ftpUploader.uploadFile(updateFile, mUpdateFileName, mFwUpdatePath);
//                ftpUploader.uploadFile(md5File, mChecksumFileName, mFwUpdatePath);
//
//                FunctionHandler.getInstance().getDataConnector().sendCommand(
//                        FunctionHandler.getInstance().getFwFunc().getSuccessTransferCmd()
//                );
//                showDialog("Transfer succeeded...");
//
//                Thread.sleep(1000);
//
//                showDialog("FW is rebooting...\nPlease don't turn off PACT...");
//
//                ftpUploader.disconnect();
//
//                updateFile.close();
//                md5File.close();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//
//                mActivity.runOnUiThread(() -> {
//
//                    FunctionHandler.getInstance().getDataConnector().sendCommand(
//                            FunctionHandler.getInstance().getFwFunc().getFailedTransferCmd()
//                    );
//                    showDialog("Transfer failed...");
//                    Toast.makeText(mActivity, "Update Failed...", Toast.LENGTH_SHORT).show();
//                    stopTimeoutHandler();
//                    pDialog.dismiss();
//
//                });
//
//            }
//        }
//
//    }
//}
//
//
//

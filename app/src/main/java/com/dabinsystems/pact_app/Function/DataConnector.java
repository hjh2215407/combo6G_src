package com.dabinsystems.pact_app.Function;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Data.GpsHoldoverData;
import com.dabinsystems.pact_app.Data.ModAccuracy.LteFdd.LteFddAmpData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrAmpData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrInterTaeData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrSSBInfoData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrTaeData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanRcvItem;
import com.dabinsystems.pact_app.Data.ModAccuracy.TriggerSource;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrCarrierSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrOffsetSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ChannelPowerMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.DETECTOR;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData.TRACE_TYPE;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.InterferenceHunting.InterferenceMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.InterferenceHunting.SpurTableData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemRefChannelData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.TransmitOnOff.TransmitOnOffMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.SaAmplitudeData;
import com.dabinsystems.pact_app.Data.SA.SaConfigData;
import com.dabinsystems.pact_app.Data.SA.SaGateData;
import com.dabinsystems.pact_app.Data.SA.SaSweepTimeData;
import com.dabinsystems.pact_app.Data.SystemData;
import com.dabinsystems.pact_app.Data.VSWR.SweepData;
import com.dabinsystems.pact_app.Data.VSWR.VswrAmplitudeData;
import com.dabinsystems.pact_app.Data.VSWR.VswrConfigData;
import com.dabinsystems.pact_app.Dialog.LoadingMessageDialog;
import com.dabinsystems.pact_app.Dialog.LoadingTrackingDialog;
import com.dabinsystems.pact_app.Dialog.MacroDialog;
import com.dabinsystems.pact_app.Dialog.WarningDialog;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Screenshot.Screenshot;
import com.dabinsystems.pact_app.Util.DequeBasedSynchronizedStack;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.Utils;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import me.grantland.widget.AutofitTextView;

import static com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrTaeData.TAE_MEAS_MODE.OFF;
import static com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrTaeData.TAE_MEAS_MODE.ON;
import static com.dabinsystems.pact_app.Data.SA.TraceData.MAX_TRACE_NUM;
import static com.dabinsystems.pact_app.Function.DataConnector.CONNECTION_TYPE.MQTT;

public class DataConnector {
    private final String TAG = "DataConnector";
    private final boolean D = false;// InitActivity.isLogEnabled;

    public enum CONNECTION_TYPE {
        MQTT
    }

    private CONNECTION_TYPE mConnectionType = MQTT;
    private ActivityMainBinding binding;
    private final InitActivity mActivity;
    private Context mContext;
    private FunctionHandler mFunctionHandler;
    private final Handler mMainHandler;

    // 25000ms 동안 데이터가 오지 않으면 Start Command 전송
    private int mMqttTimeout = 1000000;

    //Background Issue 수정
    private int mInitMqttTimeout = 1000000;
    private boolean isBackgroundReset = false;
    //Background Issue 수정

    //Qos fix
    private final int mQoS = 1;

    public int getmQoS() {
        return mQoS;
    }
    //Qos fix

    private String mURL;// = "tcp://" + MainActivity.getActivity().getString(R.string.ip_address);
    private final String mSubClientID;

    private final String TOPIC_DATA1 = "pact/data1";
    private final String TOPIC_DATA2 = "pact/data2";
    private final String TOPIC_DUMMY = "pact/dummy";
    public static final String TOPIC_COMMAND = "pact/command";

    private Handler mDatatimeoutHandler, mMqttConnectionTimeoutHandler;

    private boolean isFirstConnected = true;
    private boolean isAutoScale = false;
    private boolean isFirstData = true;
    private boolean isSetMqtt = false;

    private boolean isPrepare = false;

    private Mqtt3AsyncClient mMqttClient;
    private boolean isConnected = false;

    private Boolean isReady = true;
    private Boolean isReceivingData = false;

    private LoadingMessageDialog mLoadingDialog;
    private LoadingMessageDialog mLoadModeDialog;

    //@@ [22.01.12] loading gps holdover tracking dialog
    private LoadingTrackingDialog mLoadingTrackingHoldoverDialog;
    //@@

    /**
     * 장비의 현재 동작 모드
     */
    private int CurDeviceMode = MeasureMode.MEASURE_MODE.NONE.getByte();


    private final Runnable ModeChangeTimeoutRunnable = () -> {
        if (mFunctionHandler != null)
            mFunctionHandler.getDataConnector().dismissModeChangeMessage();
        if (mContext != null)
            /*Toast.makeText(mContext, "장치 재부팅 후 다시 시도해보세요.", Toast.LENGTH_SHORT).show();*/
            Toast.makeText(mContext, "After reboot device, try again", Toast.LENGTH_SHORT).show();
    };

    String PREV_COMMAND = "";
    //MqttMessage PREV_MESSAGE = null;
    //@@ original 30
    private final int PROTECT_MSG_TIMEOUT = 1;

    private boolean isProtectReqMsg = false;
    private Handler mProtectDuplicateReqMsgHandler;
    private final Runnable mProtectDuplicateReqMsgRunnable = () -> {
        if (D) Log.d("PROTECT_MSG", "false");
        PREV_COMMAND = "";
        isProtectReqMsg = false;
    };

    private boolean isProtectCallbackMsg = false;
    private Handler mProtectDuplicateCallbackMsgHandler;
    private final Runnable mProtectDuplicateCallbackMsgRunnable = () -> {
        if (D) Log.d("PROTECT_MSG", "false");
        isProtectCallbackMsg = false;
    };

    public DataConnector(CONNECTION_TYPE type, InitActivity activity, ActivityMainBinding binding, FunctionHandler functionHandler) {
        super();
        if (D) Log.d("DataConnect", "initialize");

        mConnectionType = type;
        mActivity = activity;
        mContext = activity.getApplicationContext();
        this.binding = binding;
        mFunctionHandler = functionHandler;
        mMainHandler = new Handler(Looper.getMainLooper());

        mURL = mContext.getString(R.string.ip_address); //"tcp://" +
        mSubClientID = UUID.randomUUID().toString();// "pact/sub" + MqttClient.generateClientId();

        switch (mConnectionType) {
            case MQTT:
                break;
        }
    }

    public void protectMessage() {
        isProtectReqMsg = true;
        if (mProtectDuplicateReqMsgHandler == null) mProtectDuplicateReqMsgHandler = new Handler();
        mProtectDuplicateReqMsgHandler.removeCallbacks(mProtectDuplicateReqMsgRunnable);
        mProtectDuplicateReqMsgHandler.removeCallbacksAndMessages(null);
        //@@ delay
        mProtectDuplicateReqMsgHandler.postDelayed(mProtectDuplicateReqMsgRunnable, PROTECT_MSG_TIMEOUT);
    }

    public boolean isProtectReqMsg() {
        return isProtectReqMsg;
    }

    public void protectCallbackMessage() {
        isProtectCallbackMsg = true;
        if (mProtectDuplicateCallbackMsgHandler == null)
            mProtectDuplicateCallbackMsgHandler = new Handler();

        mProtectDuplicateCallbackMsgHandler.removeCallbacks(mProtectDuplicateCallbackMsgRunnable);
        mProtectDuplicateCallbackMsgHandler.removeCallbacksAndMessages(null);
        PREV_COMMAND = "";
        mProtectDuplicateCallbackMsgHandler.postDelayed(mProtectDuplicateCallbackMsgRunnable, PROTECT_MSG_TIMEOUT);
    }

    public boolean isProtectCallbackMsg() {
        return isProtectCallbackMsg;
    }

    public void showModeChangeMessage(String message) throws NullPointerException {
        if (!mFunctionHandler.getWifiFunc().isDeviceWifiConnected()) {
            mMainHandler.post(() -> {
                /*Toast.makeText(mActivity, "WiFi 연결 상태를 확인해주세요.", Toast.LENGTH_LONG).show();*/
                Toast.makeText(mActivity, "Check WiFi Connection State", Toast.LENGTH_LONG).show();
            });
            return;
        }

        mMainHandler.postDelayed(ModeChangeTimeoutRunnable, 15000);

        mMainHandler.post(() -> {
            if (mLoadModeDialog == null) {
                mLoadModeDialog = new LoadingMessageDialog(mActivity, binding);
            }
            mLoadModeDialog.setMessage(message);
        });
    }

    public void dismissModeChangeMessage() {
        if (mLoadModeDialog == null) return;

        if (mMainHandler != null) {
            mMainHandler.removeCallbacks(ModeChangeTimeoutRunnable);
            mMainHandler.removeCallbacksAndMessages(null);
        }

        mActivity.runOnUiThread(() -> {
            if (mLoadModeDialog.isShowing()) {
                try {
                    mLoadModeDialog.dismiss();
                    if (D) Log.d("DataConnector", "loading mode message dismiss");
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showProgressDialog(String message) throws NullPointerException {
        mMainHandler.post(() -> {
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingMessageDialog(mActivity, binding);
            }
            mLoadingDialog.setMessage(message);
        });
    }

    public void dismissProgressDialog() {
        if (mLoadingDialog == null) return;

        mActivity.runOnUiThread(() -> {
            if (mLoadingDialog.isShowing()) {
                try {
                    mLoadingDialog.dismiss();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //@@ [22.01.12] Waiting for tracking for GPS Holdover dialog

    public void showTrackingHoldoverDialog() throws NullPointerException {
        mMainHandler.post(() -> {
            if (mLoadingTrackingHoldoverDialog == null) {
                mLoadingTrackingHoldoverDialog = new LoadingTrackingDialog(mActivity, binding);
            }
            mLoadingTrackingHoldoverDialog.setMessage("Waiting for Tracking for GPS Holdover \n(It takes up to 5 ~ 6 minutes)");
        });
    }

    public void dismissTrackingHoldoverDialog() {
        if (mLoadingTrackingHoldoverDialog == null) return;

        mActivity.runOnUiThread(() -> {
            if (mLoadingTrackingHoldoverDialog.isShowing()) {
                try {
                    mLoadingTrackingHoldoverDialog.dismiss();
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //@@

    public void deleteBuffer() {
//        try {
//            if (D)
//                Log.d("deleteBuffer", "get bufferd count : " + mMqttClient.getBufferedMessageCount());
//
//            for (int i = 0; i < mMqttClient.getBufferedMessageCount(); i++) {
//                MqttMessage message = mMqttClient.getBufferedMessage(i);
//                String msg = message.toString();
//                if (D) Log.d("deleteBuffer", "message : " + i + " " + msg);
//                mMqttClient.deleteBufferedMessage(i);
//            }
//
//            if (D) Log.d("deleteBuffer", "Deleted buffer!");
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            if (D) Log.d("deleteBuffer", "null");
//        }
    }

    // mqtt 생성
    public void setMqtt(String url) {
        mURL = url;//"tcp://" + url;
        isSetMqtt = false;
        mMqttClient = null;

        setMqtt();
    }

    public void setMqtt() {
        if (mMqttClient == null) {
            mMqttClient = MqttClient.builder()
                    .useMqttVersion3()
                    //.automaticReconnectWithDefaultConfig()
                    .identifier(UUID.randomUUID().toString())//mSubClientID)
                    .serverHost(mURL)
                    .serverPort(1883)
                    .buildAsync();

            if (D) Log.d("setMqtt", "Init mSubClient");
        } else {
            // 기존 연결 있는지
            if (isConnected) {
                // 기존에 연결 되어 있었으면. ping cmd 전송하여 연결 유무 확인
                //mMqttClient.disconnect();
                sendCommandNotSkip("0x8290", TOPIC_DUMMY, mQoS);
                return;
            }
        }

        if (mTopic1Thread == null) {
            mTopic1Thread = new Topic1Thread();
            mTopic1Thread.start();
        }
        if (mTopic2Thread == null) {
            mTopic2Thread = new Topic2Thread();
            mTopic2Thread.start();
        }


//        if (isSetMqtt) {
//            if (D) Log.d("setMqtt", "isSetMqtt : " + "setMqtt is already initializing.");
//            return;
//        }

        isSetMqtt = true;

//        startCheckMqttConnectionHandler();
//        callbackEvents();
        connectClient();

        if (D) Log.d("setMqtt", "initialize mqtt");
    }

    boolean isConnecting = false;

    public void connectClient() {
        if (mMqttClient == null) {
            //if (D)
                Log.e("connectSubClient", "null");
            return;
        }
        if (isConnecting) {
            Log.e(TAG, "isConnecting = " + isConnecting);
            return;
        }

        isConnecting = true;

        mMqttClient.connect()
                .whenComplete((connAck, throwable) -> {
                    if (throwable != null) {
                        // handle failure
                        Log.e(TAG, "connect failure");
                        isConnected = false;
                        isConnecting = false;

                        if (mMqttClient != null) {
                            // 연결 재 시도
                            startCheckMqttConnectionHandler();
                        }

                    } else {
                        // setup subscribes or start publishing
                        isConnected = true;
                        connectCompleted();
                        isConnecting = false;
                    }
                });
    }

    private void connectCompleted() {
        subscribe();

        try {
            binding.ivWifiImage.post(() -> binding.ivWifiImage.setImageResource(R.drawable.wifi_icon));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //calMqttTimeout();

//        if (isProtectCallbackMsg) {
//            if (D) Log.d("connectComplete", "protect duplicated callback... ");
//            return;
//        }
//        protectCallbackMessage();

        sendCommand(DataHandler.getInstance().getChangeClockSourceCmd());

        try {
            calMqttTimeout();

            startDataTimeoutHandler();

            mMainHandler.postDelayed(() -> sendCommand(DataHandler.getInstance().getReadyCmd()), 1500);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void subscribe() {

        mMqttClient.subscribeWith()
                .topicFilter(TOPIC_DATA1)
                .callback(publish -> {
                    // Process the received message
                    if (D)
                        Log.d(TAG, "Received " + new String(publish.getPayloadAsBytes()) + " from " + publish.getTopic());

                    if (mTopic1Thread != null)
                        mTopic1Thread.put(publish);

                })
                .send()
                .whenComplete((subAck, throwable) -> {
                    if (throwable != null) {
                        // Handle failure to subscribe
                        Log.e(TAG, "Handle failure to subscribe");
                    } else {
                        // Handle successful subscription, e.g. logging or incrementing a metric
                        Log.d(TAG, "Handle successful subscription");
                    }
                });

        mMqttClient.subscribeWith()
                .topicFilter(TOPIC_DATA2)
                .callback(publish -> {
                    // Process the received message
                    if (D)
                    Log.d(TAG, "Received pact/data2 " + publish.getPayloadAsBytes().length);

//                    if (publish.getPayloadAsBytes() == null) {
//                        if (D) Log.e("messageArrived", "publish.getPayloadAsBytes() == null");
//                        return;
//                    }
//                    if (publish.getPayloadAsBytes().length == 0) {
//                        if (D) Log.e("messageArrived", "publish.getPayloadAsBytes().length == 0");
//                        return;
//                    }

                    if (mTopic2Thread != null)
                        mTopic2Thread.put(publish);

                })
                .send()
                .whenComplete((subAck, throwable) -> {
                    if (throwable != null) {
                        // Handle failure to subscribe
                        Log.e(TAG, "Handle failure to subscribe");
                    } else {
                        // Handle successful subscription, e.g. logging or incrementing a metric
                        Log.d(TAG, "Handle successful subscription");

                        topic2Complete();

                    }
                });
    }

    private void topic2Complete() {
        if (mFunctionHandler.getFwFunc().isUpdating()) {
            mFunctionHandler.getFwFunc().completeUpdate();
        }

        startDataTimeoutHandler();

        if (D) Log.d("pubCallbackEvents", "isFirstConnected");

        sendCommand(mFunctionHandler.getFwFunc().getVerCheckCmd());

        pingTimer();
    }


    // MQTT 재 연결
    private final Runnable mMqttConnectionTimeoutRun = () -> {
        if (!WifiFunc.isWifiNetwork()) return;

        if (D) Log.d("mMqttConnectionTimeout", "in");

        try {
//            if (!mFunctionHandler.getWifiFunc().isDeviceWifiConnected()) {
//                String ssid = mFunctionHandler.getWifiFunc().getWifiConnector().getWifiManager().getConnectionInfo().getSSID();
//                //if (D)
//                Log.d("SendingRunnable", ssid);
//                if (!binding.tvWifiName.getText().toString().contains(ssid))
//                    binding.tvWifiName.setText(ssid);
//            }
//
            if (!mFunctionHandler.getWifiFunc().isDeviceWifiConnected()) {
                if (D) Log.d("CheckMqttConnection", "Disconnected PACT WiFi.");
                //startCheckMqttConnectionHandler();
                return;
            }

            if (mMqttClient != null && !isConnected) {
                if (D)
                    Log.d("SendingRunnable", "Check MQTT Connection : Disconnected => mSubClient");
                connectClient();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        //startCheckMqttConnectionHandler();
    };

    public void startCheckMqttConnectionHandler() {
        if (!WifiFunc.isWifiNetwork()) return;

        if (D) Log.d("CheckMqttConenction", "in");

        mMainHandler.post(() -> {
            if (mMqttConnectionTimeoutHandler == null) {
                mMqttConnectionTimeoutHandler = new Handler();
            }

            mMqttConnectionTimeoutHandler.removeCallbacksAndMessages(mMqttConnectionTimeoutRun);
            mMqttConnectionTimeoutHandler.postDelayed(mMqttConnectionTimeoutRun, 2000);// mInitMqttTimeout);
        });
    }

    private final Runnable mDataTimeoutRunnable = new Runnable() {
        @Override
        public void run() {

            MeasureMode.MEASURE_MODE mode = mFunctionHandler.getMeasureMode().getMode();
            MeasureType.MEASURE_TYPE type = mFunctionHandler.getMeasureType().getType();

            if (mode != MeasureMode.MEASURE_MODE.MOD_ACCURACY
                    && mode != MeasureMode.MEASURE_MODE.SA
                    && mFunctionHandler.getCalibrationFunc().getStep() != CalibrationFunc.CALIBRATION.CAL_OFF) {
                if (D)
                    Log.d("DataConnector", "Calibration is running... quit mqtt timeout handler");
                return;
            }

            if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && DataHandler.getInstance().getNrData().getTaeInfoData().getTeaMeasMode() == ON) {
                if (D) Log.d("DataTimeoutRunnable", "skip... tae meas on");
                return;
            }

            if (mFunctionHandler.getFwFunc().isUpdating()) return;

            if (mMqttClient != null && isConnected) {
                if (D) Log.d("DataTimeoutRunnable", "in");
                setReady(true);
                if (D) Log.d("DataTimeoutRunnable", "send ready command");
                sendCommand(DataHandler.getInstance().getReadyCmd());
                mActivity.startLoadingAnim();
            } else if (mFunctionHandler.getWifiFunc().isDeviceWifiConnected()
                    && mMqttClient != null && !isConnected) {
                if (D) Log.d("DataTimeoutRunnable", "not connected to mqtt");
            } else {
                Toast.makeText(mActivity, mActivity.getString(R.string.check_wifi_connection), Toast.LENGTH_SHORT).show();
                if (D)
                    Log.d("DataTimeoutRunnable", "SSID : " + mFunctionHandler.getWifiFunc().getWifiConnector().getWifiManager().getConnectionInfo().getSSID());
            }

            mDatatimeoutHandler.postDelayed(mDataTimeoutRunnable, mMqttTimeout);

            if (D) Log.d("DataTimeoutRunnable", "Timeout..." + mMqttTimeout);
        }
    };

    public void startDataTimeoutHandler(int timeout) {
        mMqttTimeout = timeout;
        // TODO 테스트를 위해 막음.
        mMainHandler.post(() -> {
            if (mDatatimeoutHandler == null) mDatatimeoutHandler = new Handler();

            mDatatimeoutHandler.removeCallbacks(mDataTimeoutRunnable);
            mDatatimeoutHandler.removeCallbacksAndMessages(null);
            mDatatimeoutHandler.postDelayed(mDataTimeoutRunnable, mMqttTimeout);
            if (D)
                Log.d("DataConnector", "Start sending data handler!!...timeout : " + mMqttTimeout);
        });
    }

    public void startDataTimeoutHandler() {
        // TODO 테스트를 위해 막음.
        try {
            mMainHandler.post(() -> {
                if (mDatatimeoutHandler == null) mDatatimeoutHandler = new Handler();

                mDatatimeoutHandler.removeCallbacks(mDataTimeoutRunnable);
                mDatatimeoutHandler.removeCallbacksAndMessages(null);

                calMqttTimeout();

                mDatatimeoutHandler.postDelayed(mDataTimeoutRunnable, mMqttTimeout);
                if (D)
                    Log.d("DataConnector", "Start sending data handler!!... timeout : " + mMqttTimeout);
            });
        } catch (OutOfMemoryError e) {
            removeDataTimeoutHandler();
        }
    }

    public void removeDataTimeoutHandler() {
        try {
            if (mDatatimeoutHandler == null) return;

            mDatatimeoutHandler.removeCallbacks(mDataTimeoutRunnable);
            mDatatimeoutHandler.removeCallbacksAndMessages(null);
            mDatatimeoutHandler = null;
            if (D) Log.d("DataConnector", "Remove sending data handler!!");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void sendCommandNotSkip(String command, String topic, int qos) {
        if (D)
        Log.d("KDK-", "sendCommandNotSkip " + command + ", qos = " + qos);

        if (isConnected) {
            try {
//                if (D) Log.d("sendMessage", command);
                sendCommand(command.getBytes());
                Log.e("SendCommand", "Command(not skip) : " + command);
//                protectMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (command.equals(DataHandler.getInstance().getReadyCmd()))
                setReady(true);
//            connectClient();
            if (D) Log.d("sendMessage", "can't send message -> not connected");
        }
    }

    public void sendCommand(String command) {
        if (D)
        Log.d("KDK-", "sendCommand " + command);

        if (isConnected) {
            try {
//                if (command.equals(PREV_COMMAND) && isProtectReqMsg) {
//                    //Log.d("KDK-sendCommand", "skip req msg - " + command);
//                    if (D) Log.d("sendMessage", "skip req msg - " + command);
//                    return;
//                } else isProtectReqMsg = false;
//
//                if (D)
//                    Log.d("sendMessage", "cur command : " + command + " prev command : " + PREV_COMMAND);
//                PREV_COMMAND = command;
//                protectMessage();
                sendCommand(command.getBytes());
                Log.e("SendCommand", "Command(str) : " + command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (command.equals(DataHandler.getInstance().getReadyCmd()))
                setReady(true);

//            connectClient();
            if (D) Log.d("sendMessage", "can't send message -> not connected : " + command);
        }
    }

    public void sendCommand(String command, int qos) {
        if (D)
        Log.d("KDK-", "sendCommand " + command + ", qos = " + qos);

        if (isConnected) {
            try {
//                if (command.equals(PREV_COMMAND) && isProtectReqMsg) {
//                    if (D) Log.d("sendMessage", "skip req msg - " + command);
//                    return;
//                } else isProtectReqMsg = false;
//
//                if (D)
//                    Log.d("sendMessage", "cur command : " + command + " prev command : " + PREV_COMMAND);
//                PREV_COMMAND = command;
//                protectMessage();
                sendCommand(command.getBytes());
                Log.e("SendCommand", "Command(qos) : " + command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (command == DataHandler.getInstance().getReadyCmd())
                setReady(true);

//            connectClient();
            if (D) Log.d("sendMessage", "can't send message -> not connected");
        }
    }

    public void sendCommand(byte[] command) {

        mMqttClient.publishWith()
                .topic(TOPIC_COMMAND)
                //.qos(MqttQos.AT_LEAST_ONCE)
                .payload(command)
                .send()
                .whenComplete((mqtt3Publish, throwable) -> {
                    if (throwable != null) {
                        // handle failure to publish
                        Log.e(TAG, "failure to publish");

                        // TODO 임시 테스트
                        if (isConnected) {
                            isConnected = false;
                            connectClient();
                        }
                        Log.e("SendCommand", "Command(byte[]) : " + command);
                    } else {
                        // handle successful publish, e.g. logging or incrementing a metric
                        //Log.d(TAG, "handle successful publish");
                    }
                });
    }

    public void pingTimer() {
        //@@
        if (mMqttClient != null && isConnected) {
            mMainHandler.postDelayed(() -> {
                if (!isPrepare) return;
                sendCommandNotSkip("0x8290", TOPIC_DUMMY, mQoS);
                pingTimer();
                //@@ delay
            }, 800);
        }
    }

    public void tempTimer() {
        //@@
        if (mMqttClient != null && isConnected) {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                sendCommand(DataHandler.getInstance().getTempCmd());
                tempTimer();
            }, 10000);
        }
    }

    public boolean isFirstConnected() {
        return isFirstConnected;
    }

    public void setFirstConnected(boolean firstConnected) {
        isFirstConnected = firstConnected;
    }


    Topic1Thread mTopic1Thread = null;
    Topic2Thread mTopic2Thread = null;

    class Topic1Thread extends Thread {

        DequeBasedSynchronizedStack<Mqtt3Publish> mqttMessages = new DequeBasedSynchronizedStack<>();

        public void put(Mqtt3Publish msg) {
            //Log.e("Topic1Thread", "put");

            mqttMessages.push(msg);

            // TODO
//            synchronized (Topic1Thread.this) {
//                try {
//                    Topic1Thread.this.notifyAll();
//                } catch (IllegalMonitorStateException e) {
//                    e.printStackTrace();
//                }
//            }
        }

        @Override
        public void run() {
            super.run();

            Log.e("Topic1Thread", "run start");

            DataHandler dataHandler = DataHandler.getInstance();

            while (!interrupted()) {
                try {
                    if (mqttMessages.size() < 1) {
                        // TODO
//                        synchronized (Topic1Thread.this) {
//                            Topic1Thread.this.wait(50);
//                        }
                        sleep(25);
                        continue;
                    }
                    Mqtt3Publish message = mqttMessages.pop();
                    if (message == null) {
                        continue;
                    }

                    String strMsg = new String(message.getPayloadAsBytes());
                    String[] splitMsg = strMsg.split(" ");

                    if (D)
                    Log.d("DATA1", strMsg);

                    /*String subscribeMessage = "";
                    for (int i = 0; i < splitMsg.length; i++) {
                        subscribeMessage += splitMsg[i] + " ";
                    }

                    Log.e("MQTT(Topic1)", "Topic1 Message(received) : " + subscribeMessage);*/

//                    Log.e("ReceiveCommand", "Topic1 Message : " + Arrays.toString(splitMsg));

                    if (splitMsg[0].equals("0x11")) {
                        // 주기적으로 수신되고 처리하는 곳이 없어 return 처리
                        continue;// return;
                    }
                    // OVF 0x25
                    else if (splitMsg[0].equals(dataHandler.getOvfFlagCmd())) {

                        try {
                            if (splitMsg.length > 1) {

                                MeasureMode.MEASURE_MODE mode = mFunctionHandler.getMeasureMode().getMode();

                                //@@ [22.01.11] ACLR OVF bug fix
                                //org
                                /*if (splitMsg[1].equals("1")) {
                                    if (mode == MeasureMode.MEASURE_MODE.SA && !dataHandler.getStatusData().isSaOvf()) {
                                        dataHandler.getStatusData().setSaOvf(true);
                                        mMainHandler.post(() -> {
                                            binding.tvOvf.setBackgroundResource(R.drawable.fw_status_ovf_on);
                                            binding.tvOvf.setTextColor(mActivity.getColor(R.color.ovf_color));
                                        });
                                    } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && !dataHandler.getStatusData().isModOvf()) {
                                        dataHandler.getStatusData().setModOvf(true);
                                        mMainHandler.post(() -> {
                                            binding.tvNrOvf.setBackgroundResource(R.drawable.fw_status_ovf_on);
                                            binding.tvNrOvf.setTextColor(mActivity.getColor(R.color.ovf_color));
                                        });
                                    }
                                } else if (splitMsg[1].equals("0")) {
                                    if (mode == MeasureMode.MEASURE_MODE.SA && dataHandler.getStatusData().isSaOvf()) {
                                        dataHandler.getStatusData().setSaOvf(false);
                                        mMainHandler.post(() -> {
                                            binding.tvOvf.setBackgroundResource(R.drawable.fw_status_background);
                                            binding.tvOvf.setTextColor(mActivity.getColor(R.color.norText));
                                        });

                                    } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && dataHandler.getStatusData().isModOvf()) {
                                        dataHandler.getStatusData().setModOvf(false);
                                        mMainHandler.post(() -> {
                                            binding.tvNrOvf.setBackgroundResource(R.drawable.fw_status_background);
                                            binding.tvNrOvf.setTextColor(mActivity.getColor(R.color.norText));
                                        });
                                    }
                                }*/
                                //org

                                if (splitMsg[1].equals("1")) {
                                    if (mode == MeasureMode.MEASURE_MODE.SA) {

                                        dataHandler.getStatusData().setSaOvf(true);

                                        // ovf 색상이 현재 상태와 다를 때만 변경해줌 (속도 때문에)
                                        if (binding.tvOvf.getCurrentTextColor() != mActivity.getColor(R.color.ovf_color)) {
                                            mMainHandler.post(() -> {
                                                binding.tvOvf.setBackgroundResource(R.drawable.fw_status_ovf_on);
                                                binding.tvOvf.setTextColor(mActivity.getColor(R.color.ovf_color));
                                            });
                                        }
                                    } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {

                                        dataHandler.getStatusData().setModOvf(true);

                                        // ovf 색상이 현재 상태와 다를 때만 변경해줌 (속도 때문에)
                                        if (binding.tvNrOvf.getCurrentTextColor() != mActivity.getColor(R.color.ovf_color)) {
                                            mMainHandler.post(() -> {
                                                binding.tvNrOvf.setBackgroundResource(R.drawable.fw_status_ovf_on);
                                                binding.tvNrOvf.setTextColor(mActivity.getColor(R.color.ovf_color));
                                            });
                                        }

                                    }
                                } else if (splitMsg[1].equals("0")) {
                                    if (mode == MeasureMode.MEASURE_MODE.SA) {
                                        dataHandler.getStatusData().setSaOvf(false);

                                        // ovf 색상이 현재 상태와 다를 때만 변경해줌 (속도 때문에)
                                        if (binding.tvOvf.getCurrentTextColor() != mActivity.getColor(R.color.norText)) {
                                            mMainHandler.post(() -> {
                                                binding.tvOvf.setBackgroundResource(R.drawable.fw_status_background);
                                                binding.tvOvf.setTextColor(mActivity.getColor(R.color.norText));
                                            });
                                        }

                                    } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {
                                        dataHandler.getStatusData().setModOvf(false);

                                        // ovf 색상이 현재 상태와 다를 때만 변경해줌 (속도 때문에)
                                        if (binding.tvNrOvf.getCurrentTextColor() != mActivity.getColor(R.color.norText)) {
                                            mMainHandler.post(() -> {
                                                binding.tvNrOvf.setBackgroundResource(R.drawable.fw_status_background);
                                                binding.tvNrOvf.setTextColor(mActivity.getColor(R.color.norText));
                                            });
                                        }


                                    }
                                }


                                //@@
                            }

                        } catch (NullPointerException | IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }

                    }
                    // sweep time 0x30
                    else if (splitMsg[0].equals(dataHandler.getSweepTimeCmd())) {
                        // Sweep time   0x30 SweepTimeValue
                        // APP에서는 받은 값의 * 1000 하여 저장
                        if (SaDataHandler.getInstance().getConfigData().getSweepTimeData().getSweepTimeMode() != SaSweepTimeData.SWEEP_TIME_MODE.AUTO)
                            continue;// return;

                        try {
                            final float val = Float.parseFloat(splitMsg[1]);
                            long sweepVal = (long) ((double) val * 1000);
                            if (sweepVal != SaDataHandler.getInstance().getConfigData().getSweepTimeData().getSweepTime()) {
                                SaDataHandler.getInstance().getConfigData().getSweepTimeData().setSweepTime(sweepVal);
                                ViewHandler.getInstance().getSaSweepView().update();
                                binding.lineChartLayout.sweptSaInfo.tvSweepVal.post(() -> binding.lineChartLayout.sweptSaInfo.tvSweepVal.setText(SaDataHandler.getInstance().getConfigData().getSweepTimeData().getSweepTimeToString()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //Calibration
                    else if (splitMsg[0].equals(CalibrationFunc.CALIBRATION.CAL_MODE.getHexString())) {

                        mFunctionHandler.getCalibrationFunc().updateCalibration(splitMsg);
                        ViewHandler.getInstance().getStartCalibrationView().update();

                    }
                    // user cal
                    else if (splitMsg[0].equals(CalibrationFunc.USER_CAL.USER_CAL_MODE.getHexString())) {
                        mFunctionHandler.getCalibrationFunc().updateUserCal(splitMsg);
                        ViewHandler.getInstance().getCalibrationView().update();
                    }
                    // battery
                    else if (splitMsg[0].equals(BatteryFunc.BATTERY_CMD.BATTERY_MODE.getHexString())) {
                        // 배터리 상태 수신
                        if (D)
                            Log.d("Battery", BatteryFunc.BATTERY_CMD.BATTERY_MODE.getHexString() + " " + splitMsg[0]);
                        mFunctionHandler.getBatteryFunc().updateBatteryStatus(splitMsg);

                    }
                    // fw
                    else if (splitMsg[0].equals(FWFunc.FW_CMD.FW_CMD.getHexString())) {
                        // FW 정보
                        mFunctionHandler.getFwFunc().updateFwInfo(splitMsg);

                    }
                    // ready
                    else if (splitMsg[0].equals(dataHandler.getReadyCmd())) {
                        // 연결확인 0x200000 MODE   MODE 값에 따라 FW를 재시작 시킬지 결정함
                        // 일정 시간동안 0x200000이 오지 않으면 Timeout 으로 판단하고 다시 0x200000을 보냄(APP -> FW)
                        dataHandler.setAutoAtten(false);
                        dataHandler.setSaAutoScale(false);
                        if (D) Log.d("Ready", dataHandler.getReadyCmd());

                        MeasureMode.MEASURE_MODE mode = mFunctionHandler.getMeasureMode().getMode();
                        MeasureType.MEASURE_TYPE type = mFunctionHandler.getMeasureType().getType();

                        try {
                            if (splitMsg.length > 1)
                                CurDeviceMode = Integer.parseInt(splitMsg[1]);
                        } catch (NumberFormatException | NullPointerException e) {
                            //값을 제대로 받지 못했을 경우, FW의 기본값인 1(SA)로 설정하고 재시작 요청
                            //@@MQTT retry
//                            CurDeviceMode = 1;
//                            sendCommand(dataHandler.getChangeCommandByMode());
//                            showModeChangeMessage("loading " + mFunctionHandler.getMeasureMode().getMode().getString() + " ...");
//                            return;
                        }

                        int curMeasMode = getCurMeasMode(); // App 의 현재 측정 모드

                        startDataTimeoutHandler();
                        mFunctionHandler.getMeasureType().setPrevType(null);

                        if (D)
                            Log.d("getReadyCmd", "CurDeviceMode : " + CurDeviceMode + " CurAppMode : " + curMeasMode);

                        if (CurDeviceMode != curMeasMode) {
                            if (curMeasMode == MeasureMode.MEASURE_MODE.NONE.getByte()) {
                                //return;
                            } else {
                                sendCommand(dataHandler.getChangeCommandByMode());
                                showModeChangeMessage("loading " + mFunctionHandler.getMeasureMode().getMode().getString() + " ...");
                            }
                            continue;// return;
                        } else if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL) {

                            mFunctionHandler.getDataConnector().sendCommand(VswrDataHandler.getInstance().getCalTypeCmd());
                            mFunctionHandler.getDataConnector().sendCommand(VswrDataHandler.getInstance().getUserCalCmd());
                            mFunctionHandler.getDataConnector().sendCommand(dataHandler.getRequestMainDataCmd());

                        } else {
                            sendCommand(dataHandler.getConfigCmd());
                        }

                        sendCommand(dataHandler.getChangeClockSourceCmd());
//                        sendCommand(dataHandler.getConfigCmd());
//                        sendCommand(dataHandler.getTempCmd(), 2);
//                        dismissModeMessage();

                    }
                    // auto attend
                    else if (splitMsg[0].equals(dataHandler.getAutoAttenCmd())) {
                        // Auto atten   0x24 atten preamp   FW로부터 값을 전달 받으면 해당 값으로 세팅 뒤 Set parameter 전송 후 Auto scale
                        try {
                            MeasureMode.MEASURE_MODE mode = mFunctionHandler.getMeasureMode().getMode();
                            MeasureType.MEASURE_TYPE type = mFunctionHandler.getMeasureType().getType();

                            int atten = Integer.parseInt(splitMsg[1]);
                            int preamp = Integer.parseInt(splitMsg[2]);

                            if (mode == MeasureMode.MEASURE_MODE.SA) {

                                SaConfigData configData = SaDataHandler.getInstance().getConfigData();
                                SaAmplitudeData ampData = configData.getAmplitudeData();

                                if (preamp == SaAmplitudeData.PREAMP_MODE.ON.getValue()) {
                                    ampData.setPreampMode(SaAmplitudeData.PREAMP_MODE.ON);
                                } else {
                                    ampData.setPreampMode(SaAmplitudeData.PREAMP_MODE.OFF);
                                }

                                ampData.setAttenuator(atten);
                                ViewHandler.getInstance().getSAAmplitudeView().update();
                                ViewHandler.getInstance().getContent().subInfoUpdate();

                                mFunctionHandler.getDataConnector().sendCommand(dataHandler.getConfigCmd());

                                mMainHandler.postDelayed(() -> {
//                                    sendCommand(dataHandler.getConfigCmd());
                                    sendCommand(dataHandler.getRequestMainDataCmd());
                                    dataHandler.setAutoAtten(false);
                                    dataHandler.setSaAutoScale(true);
                                    //@@ delay
                                }, 1000);

                            } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && (type == MeasureType.MEASURE_TYPE.NR_5G || type == MeasureType.MEASURE_TYPE.TAE)) {

                                NrAmpData ampData = dataHandler.getNrData().getAmpData();

                                if (preamp == NrAmpData.PREAMP_MODE.ON.getValue()) {
                                    ampData.setPreampMode(NrAmpData.PREAMP_MODE.ON);
                                    mMainHandler.post(() -> {
                                        binding.tvNrPre.setBackgroundResource(R.drawable.fw_status_pre_on);
                                        binding.tvNrPre.setTextColor(mActivity.getColor(R.color.pre_color));
                                    });
                                } else {
                                    ampData.setPreampMode(NrAmpData.PREAMP_MODE.OFF);
                                    mMainHandler.post(() -> {
                                        binding.tvNrPre.setBackgroundResource(R.drawable.fw_status_background);
                                        binding.tvNrPre.setTextColor(mActivity.getColor(R.color.norText));
                                    });
                                }

                                ampData.setAttenuator(atten);
                                ViewHandler.getInstance().getDemodulationAmplitudeView().update();

//                                mFunctionHandler.getDataConnector().sendCommand(
//                                        dataHandler.getConfigCmd()
//                                );

                            } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && type == MeasureType.MEASURE_TYPE.LTE_FDD) {
                                LteFddAmpData ampData = dataHandler.getLteFddData().getAmpData();
                                if (preamp == LteFddAmpData.PREAMP_MODE.ON.getValue()) {
                                    ampData.setPreampMode(LteFddAmpData.PREAMP_MODE.ON);
                                    mMainHandler.post(() -> {
                                        binding.tvNrPre.setBackgroundResource(R.drawable.fw_status_pre_on);
                                        binding.tvNrPre.setTextColor(mActivity.getColor(R.color.pre_color));
                                    });
                                } else {
                                    ampData.setPreampMode(LteFddAmpData.PREAMP_MODE.OFF);
                                    mMainHandler.post(() -> {
                                        binding.tvNrPre.setBackgroundResource(R.drawable.fw_status_background);
                                        binding.tvNrPre.setTextColor(mActivity.getColor(R.color.norText));
                                    });
                                }

                                ampData.setAttenuator(atten);
                                ViewHandler.getInstance().getLteFddAmpView().update();

//                                mFunctionHandler.getDataConnector().sendCommand(
//                                        dataHandler.getConfigCmd()
//                                );
                            }

                        } catch (NullPointerException | NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    // sync
                    else if (splitMsg[0].equals(dataHandler.getSyncFlagCmd())) {
                        try {
                            if (splitMsg.length > 1) {
                                /*Log.e("5GNR Sync", "strMsg : " + strMsg);
                                Log.e("5GNR Sync", "splitMsg : " + splitMsg[1]);
                                Log.e("5GNR Sync", "isSync? : " + dataHandler.getStatusData().isSync());*/
                                if (splitMsg[1].equals("1") && !dataHandler.getStatusData().isSync()) {
                                    Log.e("5GNR Sync", "sync true");
                                    dataHandler.getStatusData().setSync(true);
                                    //@@ [22.01.12] icon update fix
                                    //org
                                    /*mMainHandler.post(() -> {
                                        binding.tvNrSync.setBackgroundResource(R.drawable.fw_status_sync_on);
                                        binding.tvNrSync.setTextColor(mActivity.getColor(R.color.sync_color));
                                    });*/
                                    //org

                                    if (binding.tvNrSync.getCurrentTextColor() != mActivity.getColor(R.color.sync_color)) {
                                        mMainHandler.post(() -> {
                                            binding.tvNrSync.setBackgroundResource(R.drawable.fw_status_sync_on);
                                            binding.tvNrSync.setTextColor(mActivity.getColor(R.color.sync_color));
                                        });
                                    }

                                    //@@
                                } else if (splitMsg[1].equals("0") && dataHandler.getStatusData().isSync()) {
                                    Log.e("5GNR Sync", "sync false");
                                    dataHandler.getStatusData().setSync(false);
                                    //@@ [22.01.12] icon update fix

                                    //org
                                    /*mMainHandler.post(() -> {
                                        binding.tvNrSync.setBackgroundResource(R.drawable.fw_status_background);
                                        binding.tvNrSync.setTextColor(mActivity.getColor(R.color.norText));
                                    });*/
                                    //org

                                    if (binding.tvNrSync.getCurrentTextColor() != mActivity.getColor(R.color.norText)) {
                                        mMainHandler.post(() -> {
                                            binding.tvNrSync.setBackgroundResource(R.drawable.fw_status_background);
                                            binding.tvNrSync.setTextColor(mActivity.getColor(R.color.norText));
                                        });
                                    }
                                    //@@
                                }
                            }
                        } catch (NullPointerException | IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }

                    }
                    // nr to sa
                    else if (splitMsg[0].equals(dataHandler.getNrToSaCmd())) {
                        // FW 시작 알림     0x66 MODE
                        // FW가 시작되면 가장 먼저 APP으로 보내주는 커맨드.
                        // 해당 커맨드의 값을 비교하여 현재 APP의 모드와 다르다면 0x65 + MODE를 전송하여 APP의 모드와 맞춤.
                        // 맞을 경우 Clock source 값을 FW에 전송.
                        // VSWR모드의 경우 Cal type과 UserCal, 데이터 요청 커맨드 전송
                        //if (D)
                        Log.d("0x66", "in");
//                        if (mode == null || mode.getPrevMode() != MeasureMode.MEASURE_MODE.NR_5G ||
//                                mode.getMode() != MeasureMode.MEASURE_MODE.SA) return;
//
                        MeasureMode mode = mFunctionHandler.getMeasureMode();
                        MeasureType type = mFunctionHandler.getMeasureType();

                        try {
                            if (splitMsg.length > 1)
                                CurDeviceMode = Integer.parseInt(splitMsg[1]);
                        } catch (NumberFormatException | NullPointerException e) {
                            //값을 제대로 받지 못했을 경우, FW의 기본값인 1(SA)로 설정하고 재시작 요청
                            //@@ MQTT retry
//                            CurDeviceMode = 1;
//                            sendCommand(dataHandler.getChangeCommandByMode());
//                            showModeChangeMessage("loading " + mFunctionHandler.getMeasureMode().getMode().getString() + " ...");
//                            return;
                        }

                        int curMeasMode = getCurMeasMode();

                        if (CurDeviceMode != curMeasMode) {
                            Log.e(TAG, "CurDeviceMode = " + CurDeviceMode + ", curMeasMode = " + curMeasMode);

                            // 현재 모드와 FW 모드가 다른 경우
                            if (curMeasMode == MeasureMode.MEASURE_MODE.NONE.getByte()) {
                                //dismissModeChangeMessage();
                            } else {
                                sendCommand(dataHandler.getChangeCommandByMode());
                                showModeChangeMessage("loading " + mFunctionHandler.getMeasureMode().getMode().getString() + " ...");
                            }
                            continue;// return;
                        }

                        sendCommand(dataHandler.getChangeClockSourceCmd());

                        if (mode.getMode() == MeasureMode.MEASURE_MODE.VSWR || mode.getMode() == MeasureMode.MEASURE_MODE.DTF || mode.getMode() == MeasureMode.MEASURE_MODE.CL) {
                            mFunctionHandler.getDataConnector().sendCommand(VswrDataHandler.getInstance().getCalTypeCmd());
                            mFunctionHandler.getDataConnector().sendCommand(VswrDataHandler.getInstance().getUserCalCmd());
                            mFunctionHandler.getDataConnector().sendCommand(dataHandler.getRequestMainDataCmd());
                        }

                        startDataTimeoutHandler();

                        mMainHandler.postDelayed(() -> {
                            mode.updatePrevMode();
                            type.setPrevType(null);

                            MeasureMode.MEASURE_MODE curMode = mode.getMode();
                            setReady(true);

                            sendCommand(dataHandler.getConfigCmd());

                            dismissModeChangeMessage();
                            mActivity.startLoadingAnim();
//
                            //@@ delay
                        }, 3000);

                    }
                    // ResponseForConfig 0x99
                    else if (strMsg.equals(dataHandler.getResponseForConfig())) {
                        // Set parameter 응답
                        // SA/Mode Accuracy 모드일 때 설정 파라미터에 대한 응답으로 0x99를 보내줌
                        // APP에서는 해당 값을 받고 중복해서 온 커맨드인지 확인하고 중복된 커맨드가 아닐 경우 0x11을 보내 데이터를 요청함
                        if (D) Log.d("DataConnector", "in 0x99");

                        startDataTimeoutHandler();

                        MeasureMode.MEASURE_MODE prevMode = mFunctionHandler.getMeasureMode().getPrevMode();
                        MeasureMode.MEASURE_MODE mode = mFunctionHandler.getMeasureMode().getMode();

                        MeasureType.MEASURE_TYPE prevType = mFunctionHandler.getMeasureType().getPrevType();
                        MeasureType.MEASURE_TYPE type = mFunctionHandler.getMeasureType().getType();

                        NrTaeData taeData = dataHandler.getNrData().getTaeInfoData();

                        //Log.d("KDK-", "getResponseForConfig " + prevMode + " " + prevType + " " + mode + " " + type + ", isReady = " + isReady);

                        if (D) Log.d("DataConnector", "before tae mode check");
                        if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && type == MeasureType.MEASURE_TYPE.TAE && taeData.getTeaMeasMode() == ON)
                            continue;// return;
                        if (D) Log.d("DataConnector", "after tae mode check");

//                        if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL)
//                            return;

                        // [jigum] 2021-07-14 5G NR/LTE 분석 화면의 장비 State Information 아이콘에서 TRIG 아이콘을 추가
                        // ‘Setup -> Trigger Source’ 메뉴의 설정 값이 ‘GPS’ or ‘Ext. 1pps’로 설정될 때 주황색으로 아이콘 표시
                        if (type == MeasureType.MEASURE_TYPE.NR_5G || type == MeasureType.MEASURE_TYPE.LTE_FDD) {
                            TriggerSource triggerSource;
                            if (type == MeasureType.MEASURE_TYPE.NR_5G) {
                                triggerSource = dataHandler.getNrData().getTriggerSource();
                            } else {
                                triggerSource = dataHandler.getLteFddData().getTriggerSource();
                            }

                            switch (triggerSource.getTriggerSource()) {
                                case INTERNAL:
                                    mMainHandler.post(() -> {
                                        binding.tvNrTrig.setBackgroundResource(R.drawable.fw_status_background);
                                        binding.tvNrTrig.setTextColor(mActivity.getColor(R.color.norText));
                                    });
                                    break;
                                case GPS:
                                case EXT1PPS:
                                    mMainHandler.post(() -> {
                                        binding.tvNrTrig.setBackgroundResource(R.drawable.fw_status_trig_on);
                                        binding.tvNrTrig.setTextColor(mActivity.getColor(R.color.trig_color));
                                    });
                                    break;
                            }
                        }


                        if (!isReady) {
                            // mode 변경 후 첫번째 이 후 설정 값 수신 하였을 경우
                            if (type == MeasureType.MEASURE_TYPE.NR_5G_SCAN) {
                                mActivity.stopLoadingAnim();
                                // [jigum] 2021-07-23 5G NR Scan - "Start" 누르면 설정 전송 후 데이터 요청
                                if (dataHandler.getNrScanData().isStart())
                                    sendCommand(dataHandler.getRequestMainDataCmd());
                            } else {
                                sendCommand(dataHandler.getRequestMainDataCmd());
                            }

                            if (D) Log.d("DataConnector", "already send... request command");
                            continue;// return;
                        }

                        removeCallback();

                        if (prevMode == null || prevType == null || prevMode != mode || prevType != type) {
                            // mode 변경 후 첫번째로 수신 하였을 경우
                            if (D) Log.d("DataConnector", "0x99" + " send request cmd 0x11");
                            switch (type) {
                                case TAE:
                                    break;
                                case NR_5G_SCAN:
                                    // [jigum] 2021-07-23 5G NR Scan - "Start" 누르면 설정 전송 후 데이터 요청
                                    mActivity.stopLoadingAnim();
                                    if (dataHandler.getNrScanData().isStart())
                                        sendCommand(dataHandler.getRequestMainDataCmd());
                                    break;
                                default:
                                    //@@ [22.01.20] Change Mode Send hold over set parameter
                                    /*if (type == MeasureType.MEASURE_TYPE.NR_5G) {
                                        // 5G NR Mod Accuracy hold over set parameter send
                                        Log.e("Holdover", "(Mod Accuracy)Send Holdover Set Command");
                                        FunctionHandler.getInstance().getDataConnector().sendCommand(
                                                DataHandler.getInstance().getChangeGPSHoldoverCmd()
                                        );

                                    }
                                    else if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {
                                        // SA mode hold over set parameter send
                                        Log.e("Holdover", "(SA)Send Holdover Set Command");

                                        FunctionHandler.getInstance().getDataConnector().sendCommand(
                                                DataHandler.getInstance().getChangeGPSHoldoverCmd()
                                        );

                                    }*/
                                    //@@

                                    sendCommand(dataHandler.getRequestMainDataCmd());
                                    break;
                            }

                            mFunctionHandler.getMeasureType().setPrevType(type);
                            mFunctionHandler.getMeasureMode().updatePrevMode();

                        } else if (type == MeasureType.MEASURE_TYPE.NR_5G_SCAN) {
                            // [jigum] 2021-07-23 5G NR Scan - "Start" 누르면 설정 전송 후 데이터 요청
                            if (dataHandler.getNrScanData().isStart())
                                sendCommand(dataHandler.getRequestMainDataCmd());
                        } else {
                            // [jigum] 2021-08-05 모드 변경과 상관없이 설정이 바뀌면 데이터 요청 해야함.
                            sendCommand(dataHandler.getRequestMainDataCmd());
                        }

                        isReady = false;

                        if (D)
                            Log.d("getResponseForConfig", prevMode + " " + prevType + " " + mode + type);

                    }
                    // clock source
                    else if (splitMsg[0].equals(dataHandler.getClockSourceCmd())) {
                        // Clock source 아이콘 변경  0x50 ClockSource
                        // GPS나 EXT10MHz 일 경우 EXT 아이콘 활성화
                        MeasureMode.MEASURE_MODE mode = mFunctionHandler.getMeasureMode().getMode();
                        int val = Integer.parseInt(splitMsg[1]);
                        if (val == SystemData.CLOCK_SOURCE.GPS.getVal() || val == SystemData.CLOCK_SOURCE.EXT10MHZ.getVal()) {
                            if (D) Log.d("Clock Source", "GPS");

                            if (mode == MeasureMode.MEASURE_MODE.SA) {
                                if (binding.tvExt.getCurrentTextColor() != mActivity.getColor(R.color.ext_color)) {
                                    mMainHandler.post(() -> {
                                        binding.tvExt.setBackgroundResource(R.drawable.fw_status_ext_on);
                                        binding.tvExt.setTextColor(mActivity.getColor(R.color.ext_color));
                                    });
                                }
                                /*mMainHandler.post(() -> {
                                    binding.tvExt.setBackgroundResource(R.drawable.fw_status_ext_on);
                                    binding.tvExt.setTextColor(mActivity.getColor(R.color.ext_color));
                                });*/
                            } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {
                                if (binding.tvNrExt.getCurrentTextColor() != mActivity.getColor(R.color.ext_color)) {
                                    mMainHandler.post(() -> {
                                        binding.tvNrExt.setBackgroundResource(R.drawable.fw_status_ext_on);
                                        binding.tvNrExt.setTextColor(mActivity.getColor(R.color.ext_color));
                                    });
                                }
                                /*mMainHandler.post(() -> {
                                    binding.tvNrExt.setBackgroundResource(R.drawable.fw_status_ext_on);
                                    binding.tvNrExt.setTextColor(mActivity.getColor(R.color.ext_color));
                                });*/
                            }
                        } else {
                            if (D) Log.d("Clock Source", "Internal");
                            if (mode == MeasureMode.MEASURE_MODE.SA) {
                                if (binding.tvExt.getCurrentTextColor() != mActivity.getColor(R.color.norText)) {
                                    mMainHandler.post(() -> {
                                        binding.tvExt.setBackgroundResource(R.drawable.fw_status_background);
                                        binding.tvExt.setTextColor(mActivity.getColor(R.color.norText));
                                    });
                                }
                                /*mMainHandler.post(() -> {
                                    binding.tvExt.setBackgroundResource(R.drawable.fw_status_background);
                                    binding.tvExt.setTextColor(mActivity.getColor(R.color.norText));
                                });*/
                            } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {
                                if (binding.tvNrExt.getCurrentTextColor() != mActivity.getColor(R.color.norText)) {
                                    mMainHandler.post(() -> {
                                        binding.tvNrExt.setBackgroundResource(R.drawable.fw_status_background);
                                        binding.tvNrExt.setTextColor(mActivity.getColor(R.color.norText));
                                    });
                                }
                                /*mMainHandler.post(() -> {
                                    binding.tvNrExt.setBackgroundResource(R.drawable.fw_status_background);
                                    binding.tvNrExt.setTextColor(mActivity.getColor(R.color.norText));
                                });*/
                            }
                        }

                    }
                    // Lock Status
                    else if (splitMsg[0].equals(dataHandler.getLockStatusCmd())) {
                        // GPS 상태 수신
                        //Log.d("KDK-gps", strMsg);
//                        Log.e("LOCK STATUS", "GPS LOCK STATUS COMMAND(RCV) : " + Arrays.toString(splitMsg));

                        int rcvGpsState = Integer.parseInt(splitMsg[1]);

                        SystemData systemData = dataHandler.getSystemData();
                        int curStatus = systemData.getLockStatus().getStatus();

                        Log.e("LOCK STATUS(0x51)", "GPS LOCK Status(ICON) : " + rcvGpsState);


                        //org
                        if (curStatus == rcvGpsState) {
                            continue;// return;
                        }

                        if (rcvGpsState == SystemData.LOCK_STATUS.ON.getStatus()) {

                            systemData.setLockStatus(SystemData.LOCK_STATUS.ON);

                            mMainHandler.post(() -> {
                                binding.ivGPS.clearAnimation();
                                binding.ivGPS.setImageResource(R.drawable.gps_location_enabled_icon);
                            });
                        } else if (rcvGpsState == SystemData.LOCK_STATUS.OFF.getStatus()) {
                            systemData.setLockStatus(SystemData.LOCK_STATUS.OFF);

                            mMainHandler.post(() -> {
                                binding.ivGPS.clearAnimation();
                                binding.ivGPS.setImageResource(R.drawable.gps_location_disabled_icon);
                            });
                        } else {
                            // [jigum] 2021-07-15 0x51의 값이 ‘2’ 으로 전달되는 경우 GPS Unlock 아이콘을 깜빡임
                            systemData.setLockStatus(SystemData.LOCK_STATUS.HOLDOVER);

                            mMainHandler.post(() -> {
                                binding.ivGPS.setImageResource(R.drawable.gps_location_disabled_icon);

                                Animation animation = new AlphaAnimation(1, 0);
                                animation.setDuration(500);
                                animation.setInterpolator(new LinearInterpolator());
                                animation.setRepeatCount(Animation.INFINITE);
                                animation.setRepeatMode(Animation.REVERSE);
                                binding.ivGPS.startAnimation(animation);
                            });
                        }
                        //org

                    }
                    // 0x22
                    else if (splitMsg[0].equals("0x22")) {
                        // 온도 요청    0x22 temp1 temp2
                        mActivity.runOnUiThread(() -> {
//                            binding.tvTemp1.setText("T1 : " + splitMsg[1] + "℃  ");
//                            binding.tvTemp2.setText("T2 : " + splitMsg[2] + "℃");
                        });
                    }
                    // temp warning 0
                    else if (strMsg.equals(dataHandler.getTempWarning(0))) {

                        mMainHandler.post(() -> {
                            new WarningDialog(mActivity, binding, mActivity.getString(R.string.warning_temperature1), mActivity.getString(R.string.please_cool_the_equip)).show();
                        });

                    }
                    // temp warning 1
                    else if (strMsg.equals(dataHandler.getTempWarning(1))) {
                        mMainHandler.post(() -> {

                            WarningDialog dialog = new WarningDialog(mActivity, binding, mActivity.getString(R.string.warning_temperature2), mActivity.getString(R.string.will_turn_off_power));

                            dialog.show();
                            dialog.setEventListener(view -> {
                                sendCommand(dataHandler.getForceTurnOffCmd());
                                dialog.dismiss();
                            });
                            dialog.setTitleBackgroundResource(R.drawable.battery_warning_title_red_border);
                        });

                    }
                    // 0x43
                    else if (splitMsg[0].equals("0x43")) {

                        if (D) Log.d("DataConnector", "receive 0x43 : " + strMsg);

                    }
                    // ready tae measure
                    else if (strMsg.equals(dataHandler.getReadyTaeMeasure())) {
                        if (D) Log.d("DataConnector", strMsg + " in 0x72");

                        NrInterTaeData interTaeData = dataHandler.getNrData().getInterTaeData();
                        NrTaeData taeData = dataHandler.getNrData().getTaeInfoData();
                        mMainHandler.postDelayed(() -> {
                            showProgressDialog("Preparing Timing measurement...");
                            taeData.setRunning(true);

                            //original source
//                            sendCommand(dataHandler.getRequestMainDataCmd(), 2);
                            //original sourcce

                            //Qos fix
                            sendCommand(dataHandler.getRequestMainDataCmd(), mQoS);
                            //Qos fix

                        }, 2000);
                        mFunctionHandler.getTaeFunc().removeCallbackTimeoutHandler();
                        taeData.setCurrentMeasCount(0);

                        mActivity.runOnUiThread(() -> {

                            if (taeData.getTaeType() == NrTaeData.TAE_TYPE.INTRA) {

                                binding.tvTopCenterTitle.setText("Measuring RF port " + taeData.getCurrentPortIdx() + " timing... (" + taeData.getCurrentMeasCount() + "/" + taeData.getMeasCount() + ")");

                            } else {

                                binding.tvTopCenterTitle.setText("Measuring Carrier " + (interTaeData.getCurrentCarrierIdx() + 1) + " timing... (" + taeData.getCurrentMeasCount() + "/" + taeData.getMeasCount() + ")");
                            }

                        });

                    }
                    /*else if (splitMsg[0].equals(dataHandler.getGPSHoldoverRequestCmd())) {

                        // GPS Holdover request command here
                        Log.e("Holdover", "Command : " + Arrays.toString(splitMsg));
                        //@@ [22.01.10] GPS Holdover command 응답 처리 0x54
                        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
                        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

                        GpsHoldoverData gpsHoldoverData = DataHandler.getInstance().getGpsHoldoverData();

                        Log.e("Holdover", "Clock Source : " + DataHandler.getInstance().getSystemData().getSource());
                        Log.e("Holdover", "GPS Status : " + DataHandler.getInstance().getSystemData().getLockStatus().getStatus());
                        Log.e("Holdover", "GPS Holdover opt : " + gpsHoldoverData.getOption().getString());
                        Log.e("Holdover", "Gate : " + SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData().getGateMode().toString());
                        Log.e("Holdover", "Gate Source : " + SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData().getGateSource().toString());

                        // Mod Accuracy 인 경우
                        if (type == MeasureType.MEASURE_TYPE.NR_5G) {
                            // ready 수신 하면 dialog 지우기
                            if (splitMsg[1].equals("2")) {
                                Log.e("Holdover", "*** Ready ***");
                                dismissTrackingHoldoverDialog();
                            }

                            if ((DataHandler.getInstance().getSystemData().getSource() == SystemData.CLOCK_SOURCE.GPS) &&
                                    (DataHandler.getInstance().getSystemData().getLockStatus().getStatus() == SystemData.LOCK_STATUS.HOLDOVER.getStatus()) &&
                                    (gpsHoldoverData.getOption().getString() == GpsHoldoverData.OPTION.LTE.getString())
                            ) {
                                // Clock source = GPS, Gps Status = Holdover, Gps Holdover Option = tracking
                                Log.e("Holdover", "NR5G 모든 조건 만족");

                                if (splitMsg[1].equals("0")) {
                                    // ack
                                    Log.e("Holdover", "*** Ack ***");
                                }
                                else if (splitMsg[1].equals("1")) {
                                    // searching(waiting)
                                    // show dialog Loading ...
                                    Log.e("Holdover", "*** Searching ***");
                                    showTrackingHoldoverDialog();
                                }
                                else if (splitMsg[1].equals("2")) {
                                    // ready
                                    Log.e("Holdover", "*** Ready ***");
                                    dismissTrackingHoldoverDialog();
                                }

                            }
                        }

                        if (mode == MeasureMode.MEASURE_MODE.SA) {
                            // ready 수신 하면 dialog 지우기
                            if (splitMsg[1].equals("2")) {
                                Log.e("Holdover", "*** Ready ***");
                                dismissTrackingHoldoverDialog();
                            }

                            if (
                                    (DataHandler.getInstance().getSystemData().getSource() == SystemData.CLOCK_SOURCE.GPS) &&
                                    (DataHandler.getInstance().getSystemData().getLockStatus().getStatus() == SystemData.LOCK_STATUS.HOLDOVER.getStatus()) &&
                                    (gpsHoldoverData.getOption().getString() == GpsHoldoverData.OPTION.LTE.getString()) &&
                                    (SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData().getGateMode() == SaGateData.GATE_MODE.ON) &&
                                    (SaDataHandler.getInstance().getConfigData().getSweepTimeData().getGateData().getGateSource() == SaGateData.GATE_SOURCE.GPS)
                            ) {
                                Log.e("Holdover", "SA 모든 조건 만족");

                                // clock source = GPS, gps status = holdover, gps holdover option = tacking, gate = on, gate source = gps
                                if (splitMsg[1].equals("0")) {
                                    // ack
                                    Log.e("Holdover", "*** Ack ***");

                                }
                                else if (splitMsg[1].equals("1")) {
                                    // searching(waiting)
                                    // show Loading dialog ...
                                    Log.e("Holdover", "*** Searching ***");
                                    showTrackingHoldoverDialog();
                                    //dialog 는 새로 만들어 써야할 듯 progress dialog 참조해서 만들 것

                                }
                                else if (splitMsg[1].equals("2")) {
                                    // ready
                                    // dismiss loading dialog
                                    Log.e("Holdover", "*** Ready ***");
                                    dismissTrackingHoldoverDialog();
                                }
                            }
                        }
                        //@@

                    }*/
                    else if (D)
                        Log.d("TOPIC_DATA1", "Unknown Data : " + strMsg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Log.e("Topic1Thread", "run end");
        }
    }

    class Topic2Thread extends Thread {

        DequeBasedSynchronizedStack<Mqtt3Publish> mqttMessages = new DequeBasedSynchronizedStack<>();

        public void put(Mqtt3Publish msg) {
            if (D) Log.d("DATA2", "put");
            mqttMessages.push(msg);

//            synchronized (Topic2Thread.this) {
//                try {
//                    Topic2Thread.this.notifyAll();
//                } catch (IllegalMonitorStateException e) {
//                    e.printStackTrace();
//                }
//            }
        }

        @Override
        public void run() {
            super.run();

            final DataHandler dataHandler = DataHandler.getInstance();
            final int gpsInfoResCmdVal = dataHandler.getGPSInfoResponseCmdVal();//0x53
            /*final int gpsHoldoverCmdVal = dataHandler.getGPSHoldoverRequestCmdVal();*/

            long preTimeSA = 0;
//            int gateMode = dataHandler.getGateDataCmdValue();
//            long preTimeGate = 0;

            boolean isPreSkip = false; //

            while (!interrupted()) {
                try {
                    if (mqttMessages.size() < 1) {
//                        synchronized (Topic2Thread.this) {
//                            Topic2Thread.this.wait(50);
//                        }
                        sleep(25);
                        continue;
                    }
                    Mqtt3Publish message = mqttMessages.pop();

                    if (message == null) {
                        continue;
                    }

                    byte[] buf = message.getPayloadAsBytes();

//                    Log.e("ReceiveCommand", "Topic2 Message : " + Arrays.toString(buf));


                    if (buf.length == 0) {
                        if (D) Log.e("messageArrived", "publish.getPayloadAsBytes().length == 0");
                        continue;
                    }

                    if (dataHandler.isAutoAtten()) {
                        //if (D)
                        Log.e("data2", "dataHandler.isAutoAtten()");
                        continue;// return;
                    }

                    if (mFunctionHandler.getRecallFunc().isPreview()) {
                        //if (D)
                        Log.e("data2", "mFunctionHandler.getRecallFunc().isPreview()");
                        continue;// return;
                    }

                    if (!isPrepare) {
                        //if (D)
                        Log.e("isPrepare", "not prepared... don't receive data!!");
                        continue;// return;
                    }

                    if (mFunctionHandler.getFwFunc().isUpdating()) {
                        //if (D)
                        Log.e("data2", "mFunctionHandler.getFwFunc().isUpdating()");
                        continue;// return;
                    }

                    dismissProgressDialog();

                    if (mFunctionHandler.getCalibrationFunc().getStep() != CalibrationFunc.CALIBRATION.CAL_OFF) {
                        //if (D)
                        Log.e("data2", "Calibration is running");
                        continue;// return;
                    }

                    if (binding.ivWifiImage.getAnimation() != null)
                        binding.ivWifiImage.clearAnimation();

                    //calMqttTimeout();

                    if (buf.length < 8) {
                        Log.e(TAG, "DATA2 - " + " size = " + buf.length);
                        continue;
                    }

                    ByteBuffer bb = ByteBuffer.wrap(buf);
                    bb.order(ByteOrder.LITTLE_ENDIAN);
                    int mode = bb.getInt();
                    int type = bb.getInt();

                    if (D)
                    Log.d(TAG, "DATA2 - " + " size = " + buf.length + ", " + String.format("mode = %02X, type = %02X", mode, type));

                    if (buf[0] == gpsInfoResCmdVal) {
                        // GPS Info
                        Log.d(TAG, "DATA2 - GPS INFO Response");
//                        Log.e("GPS INFO", "GPS INFO : " + Arrays.toString(buf));
                        ViewHandler.getInstance().getContent().updateGpsInfo(buf);
                        continue;
                    }

                    if (mode == -99999 || type == -99999) {
                        sendCommand(dataHandler.getRequestMainDataCmd());
                        if (D)
                        Log.d("DataConnector", "received invalid data : " + mode + " " + type);
                        continue;
                    }

                    if (mFunctionHandler.getMeasureMode().getMode() == MeasureMode.MEASURE_MODE.NONE) {
                        if (D) Log.d(TAG, "MEASURE_MODE.NONE");
                        continue;
                    }

                    // “0x11(or 0x23)” 명령어가 pact/command로 보내졌을 때 그래프에 출력될 데이터를 바이너리형태로 보내줌.
                    //if (D)
                    if (mFunctionHandler.getMeasureMode().getMode() == MeasureMode.MEASURE_MODE.SA) {

//                            if (mode == MeasureMode.MEASURE_MODE.SA.getValue() && type == MeasureType.MEASURE_TYPE.SWEPT_SA.getValue()) {
                        long now = System.currentTimeMillis();
                        if ((now - preTimeSA) < 50) {
                            //Log.d("DATA2", "sleep = " + (40 - (now - preTimeSA)));

                            // TODO 데이터가 누적 되지 않도록 일정 수량 이상 쌓이면 삭제
                            if (mqttMessages.size() > 9 && !isPreSkip) {
                                if (D)
                                    Log.e(TAG, "DATA2" + " remove size = " + mqttMessages.size());
                                mqttMessages.pop();
//                                        mqttMessages.remove();
//                                        mqttMessages.remove();
                                isPreSkip = true;
                                continue;
                            }
                            isPreSkip = false;

                            sleep(50 - (now - preTimeSA));
                        }

                        preTimeSA = System.currentTimeMillis();
//                            } else if (mode == gateMode) {
//                                long now = System.currentTimeMillis();
//                                if ((now - preTimeGate) < 40) {
//                                    sleep(40 - (now - preTimeGate));
//                                }
//                                preTimeGate = System.currentTimeMillis();
//                            }

                    }

//                        try {
                    //long start = System.currentTimeMillis();
                    addChartValue(buf);
                    //Log.d(TAG, "addChartValue time = " + (System.currentTimeMillis() - start));
//                        } catch (NullPointerException e) {
//                            isReceivingData = false;
//                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isFirstData() {
        return isFirstData;
    }

    public void removeCallback() {
        if (mDatatimeoutHandler != null) {
            mDatatimeoutHandler.removeCallbacks(mDataTimeoutRunnable);
            mDatatimeoutHandler.removeCallbacksAndMessages(null);
        }

        mFunctionHandler.getGateLineChart().removeGateTimer();

        if (mMqttConnectionTimeoutHandler != null) {
            mMqttConnectionTimeoutHandler.removeCallbacksAndMessages(null);
        }
    }

    public void setNullReadyHandler() {
        mDatatimeoutHandler = null;
    }


    private int drawCount = 0;

    public int getDrawCount() {
        int count = drawCount;
        drawCount = 0;
        return count;
    }

    @SuppressLint({"LongLogTag", "SetTextI18n", "ResourceAsColor"})
    private void addChartValue(byte[] buf) {

        if (isReceivingData) {
            //if (D)
            Log.e("addChartValue", "isReceivingData  = true");
            //sendCommand(DataHandler.getInstance().getRequestMainDataCmd(), mQoS);
            return;
        }

        isReceivingData = true;

        if (D) Log.d("addChartValue", "=========================================================");
        if (D) Log.d("addChartValue", "Data Size : " + buf.length / 4);

        if (buf.length < 8) {
            Log.e("addChartValue", "isReceivingData  size = " + buf.length);
            isReceivingData = false;
            sendCommand(DataHandler.getInstance().getRequestMainDataCmd());
            return;
        }

        final DataHandler dataHandler = DataHandler.getInstance();
        final VswrDataHandler vswrDataHandler = VswrDataHandler.getInstance();
        final SaDataHandler saDataHandler = SaDataHandler.getInstance();

        final ByteBuffer bb = ByteBuffer.wrap(buf);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        byte[] modeByte = new byte[4];
        byte[] typeByte = new byte[4];
        byte[] integer4ByteArr = new byte[4];

        int payloadLength = buf.length;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
        MeasureMode.MEASURE_MODE mode = mFunctionHandler.getMeasureMode().getMode();
        MeasureType.MEASURE_TYPE type = mFunctionHandler.getMeasureType().getType();

        if ((mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL)
                && !vswrDataHandler.getConfigData().getSweepData().isRun()) {
            Log.e(TAG, "(VSWR | DTF | CL) && isRun");
            sendCommand(dataHandler.getRequestMainDataCmd());
            startDataTimeoutHandler();
            mActivity.stopLoadingAnim();
            isReceivingData = false;
            return;
        }

        try {
            inputStream.read(modeByte);
            inputStream.read(typeByte);
        } catch (IOException e) {
            e.printStackTrace();
            if (D) Log.d("addChartValue", "Wrong Data");

            //@@MQTT
            Log.e("MQTT", "Wrong Data");
            //
            isReceivingData = false;
            return;
        }

        int modeValue = bb.getInt();// byteToInteger(modeByte, 4);
        int typeValue = bb.getInt();// byteToInteger(typeByte, 4);
        int totalPartialCount = 0;
        int currentPartialDataIndex = 0;

        if (D) Log.d("addChartValue", "mode value : " + modeValue);
        if (D) Log.d("addChartValue", "type value : " + typeValue);

        // 현재 mode 와 일치 하는지 확인 ?
        if (modeValue != mode.getValue() && modeValue != dataHandler.getGateDataCmdValue()) {
            //if (D)
            Log.e("DataConnector", "not match mode... receive value : " + modeValue + " cur mode value : " + mode.getValue());

            if (modeValue == MeasureMode.MEASURE_MODE.VSWR.getValue()
                    || modeValue == MeasureMode.MEASURE_MODE.DTF.getValue()
                    || modeValue == MeasureMode.MEASURE_MODE.CL.getValue())
                sendCommand(dataHandler.getRequestMainDataCmd());

            if (D) {
                new Thread(() -> {
                    String log = "";
                    while (true) {
                        try {
                            if (inputStream.read(integer4ByteArr) == -1) {
                                break;
                            }
                            Integer val = byteToInteger(integer4ByteArr, 4);
                            log += val + " ";
                            if (D) Log.d("addChartValue", "Surplus data : " + log);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            isReceivingData = false;
            return;
        }

        // 현재 type 과 일치 하는지 확인 ?
        if (type.getValue() != typeValue) {
            //if (D)
            Log.e("DataConnector", "not match type ... receive value : " + typeValue + "  cur type value : " + type.getValue());

            if (typeValue == MeasureType.MEASURE_TYPE.VSWR.getValue()
                    || typeValue == MeasureType.MEASURE_TYPE.RL.getValue()
                    || typeValue == MeasureType.MEASURE_TYPE.CABLE_LOSS.getValue())
                sendCommand(dataHandler.getRequestMainDataCmd());

            if (D) {
                new Thread(() -> {
                    String log = "";
                    while (true) {
                        try {
                            if (inputStream.read(integer4ByteArr) == -1) {
                                break;
                            }
                            Integer val = byteToInteger(integer4ByteArr, 4);
                            log += val + " ";
                            if (D) Log.d("addChartValue", "Surplus data : " + log);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            isReceivingData = false;
            return;
        }

        //cook and draw chart
//        mFunctionHandler.getMainLineChart().countMqttStamp();
//        mFunctionHandler.getMainLineChart().mqttTimeStamp(true);
        //

        dismissModeChangeMessage();
        mActivity.stopLoadingAnim();

        if (saDataHandler.getConfigData().getSweepTimeData().getGateData().getGateMode() == SaGateData.GATE_MODE.ON) {
            mFunctionHandler.getGateLineChart().removeGateTimer();
            mFunctionHandler.getGateLineChart().startGateTimer();
        }

        NrTaeData taeData = dataHandler.getNrData().getTaeInfoData();
        if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && type == MeasureType.MEASURE_TYPE.TAE && taeData.getTeaMeasMode() == ON && !taeData.isRunning()) {
            removeDataTimeoutHandler();
            isReceivingData = false;
            return;
        } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && type == MeasureType.MEASURE_TYPE.TAE && taeData.getTeaMeasMode() == ON) {
            removeDataTimeoutHandler();
        } else {
            startDataTimeoutHandler();
        }

        // 아래 부터 받은 데이터 분석
        // SA
        if (mode == MeasureMode.MEASURE_MODE.SA && modeValue == MeasureMode.MEASURE_MODE.SA.getValue()) {

            //TODO 차트 그리는 중 데이터가 수신되면 pass
            if (mFunctionHandler.getMainLineChart().isDrawingChart()) {
                //if (D)
                Log.e("addChartValue", "isDrawingChart = true");
                isReceivingData = false;
                return;
            }


            if (type == MeasureType.MEASURE_TYPE.SWEPT_SA && typeValue == MeasureType.MEASURE_TYPE.SWEPT_SA.getValue()) {

                // SA - Swept SA 분석
                try {
//                    //TODO 차트 그리는 중 데이터가 수신되면 pass
//                    if (mFunctionHandler.getMainLineChart().isDrawingChart()) {
//                        //if (D)
//                        Log.e("addChartValue", "isDrawingChart = true");
//                        isReceivingData = false;
//                        return;
//                    }

                    // ??
                    binding.lineChartLayout.mainLineChart.getData().clearValuesNotChanging(mFunctionHandler.getMainLineChart().LIMIT_DATASET_INDEX);

                    SaConfigData saConfigData = saDataHandler.getSweptSaConfigData();

                    // add all trace data
                    for (int i = 0; i < MAX_TRACE_NUM; i++) {

                        TRACE_TYPE traceType = saConfigData.getTraceData().getType(i);

                        int count; // Measurement Result Count
                        if (saConfigData.getTraceData().getDetector(i) == DETECTOR.NORMAL) {
                            if (D) Log.d("addChartValue", "Swept SA Points : " + 2002);
                            count = SweepData.DATA_POINT.P2002.getDataPoint();
                        } else {
                            if (D) Log.d("addChartValue", "Swept SA Points : " + 1001);
                            count = SweepData.DATA_POINT.P1001.getDataPoint();
                        }

                        ArrayList<Integer> dataList = mFunctionHandler.getMainLineChart().getDataList(i);
                        //dataList.clear();

                        if (traceType == TRACE_TYPE.UPDATE) {
                            if (dataList.size() != count) {
                                dataList.clear();
                                for (int j = 0; j < count; j++) {
                                    dataList.add(bb.getInt());
                                }
                            } else {
                                for (int j = 0; j < count; j++) {
                                    dataList.set(j, bb.getInt());
                                }
                            }
                        } else {
                            // skip
                            int pos = bb.position() + (count * 4);
                            bb.position(pos);
                        }

                        if (traceType == TRACE_TYPE.VIEW)
                            continue;

                        // chart 데이터 추가
                        binding.lineChartLayout.mainLineChart.getData().clearValuesNotChanging(i);
                        mFunctionHandler.getMainLineChart().addEntry(mFunctionHandler.getMainLineChart().getDataList(i), i, mode, type, saConfigData);
                    }

                    // chart 데이터 모두 추가 후 갱신
                    mFunctionHandler.getMainLineChart().addEntryUpdate();

                    drawCount++;

                } catch (Exception e) {
                    e.printStackTrace();
                    isReceivingData = false;
                }
            }
            // SA - channel power
            else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER && typeValue == MeasureType.MEASURE_TYPE.CHANNEL_POWER.getValue()) {
//                if (D) Log.d("addChartValue", "ChannelPower");
                try {
                    SaConfigData saConfigData = saDataHandler.getChannelPowerConfigData();

                    TRACE_TYPE traceType = saConfigData.getTraceData().getType(0);
                    int count = (saConfigData.getTraceData().getDetector(0) == DETECTOR.NORMAL) ? SweepData.DATA_POINT.P2002.getDataPoint() : SweepData.DATA_POINT.P1001.getDataPoint();
                    ArrayList<Integer> dataList = mFunctionHandler.getMainLineChart().getDataList(0);
                    //dataList.clear();

                    if (traceType == TRACE_TYPE.UPDATE) {
                        if (dataList.size() != count) {
                            dataList.clear();
                            for (int j = 0; j < count; j++) {
                                dataList.add(bb.getInt());
                            }
                        } else {
                            for (int j = 0; j < count; j++) {
                                dataList.set(j, bb.getInt());
                            }
                        }

                        ChannelPowerMeasSetupData measData = saDataHandler.getChannelPowerConfigData().getChannelPowerMeasSetupData();
                        measData.setChannelPower((float) ((double) bb.getInt() / 100));
                        measData.setDensity((float) ((double) bb.getInt() / 100));
                        if (D)
                            Log.d("Channel Power", measData.getChannelPower() + " " + measData.getDensity());
                        //org
                        /*mActivity.runOnUiThread(() -> {
                            // 글자 변경으로 layout 크기 재 계산이 발생하면 UI에 부담이 많이 가서 수정.
                            binding.lineChartLayout.channelPowerLayout.tvChannelPowerVal.setText(mActivity.getString(R.string.channel_power)
                                    + Utils.formatNumber(measData.getChannelPower(), 2, false) + " dBm /"
                                    + Utils.formatNumber((float) measData.getIntegMHzVal(), 2, false) + " MHz");
                            binding.lineChartLayout.channelPowerLayout.tvDensityVal.setText(mActivity.getString(R.string.power_spectral_density)
                                    + Utils.formatNumber(measData.getDensity(), 2, false) + " dBm / Hz");
                        });*/
                        //org

                        //@@ [21.12.20] integ BW 셋째자리 까지 표현
                        mActivity.runOnUiThread(() -> {
                            // 글자 변경으로 layout 크기 재 계산이 발생하면 UI에 부담이 많이 가서 수정.
                            binding.lineChartLayout.channelPowerLayout.tvChannelPowerVal.setText(mActivity.getString(R.string.channel_power)
                                    + Utils.formatNumber(measData.getChannelPower(), 2, false) + " dBm /"
                                    + Utils.formatNumber((float) measData.getIntegMHzVal(), 3, false) + " MHz");
                            binding.lineChartLayout.channelPowerLayout.tvDensityVal.setText(mActivity.getString(R.string.power_spectral_density)
                                    + Utils.formatNumber(measData.getDensity(), 2, false) + " dBm / Hz");
                        });
                        //


                        binding.lineChartLayout.mainLineChart.getData().clearValuesNotChanging(0);
                        mFunctionHandler.getMainLineChart().addEntry(mFunctionHandler.getMainLineChart().getDataList(0), 0, mode, type, saConfigData);
                        // chart 데이터 모두 추가 후 갱신
                        mFunctionHandler.getMainLineChart().addEntryUpdate();

                        drawCount++;

                    } else {
                        // skip
                        int pos = bb.position() + (count * 4 + 8);
                        bb.position(pos);
                    }

                    inputStream.skip(count * 4 + 8);

                    if (traceType == TRACE_TYPE.BLANK) {
                        binding.lineChartLayout.mainLineChart.getData().clearValues(0);
                    }

                    //@@ [22.01.27] 원전모니터링 Threshold 관련
                    //ToDo :
                    /*if (mFunctionHandler.getMainLineChart().isEnabledLimitMsg())
                        mFunctionHandler.getMainLineChart().setEnabledLimitMsg(false);*/
                    //@@
                } catch (Exception e) {
                    e.printStackTrace();
                    isReceivingData = false;
                }
            }
            // SA - Occupied BW
            else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW && typeValue == MeasureType.MEASURE_TYPE.OCCUPIED_BW.getValue()) {
                try {
                    SaConfigData saConfigData = saDataHandler.getOccupiedBwConfigData();

                    TRACE_TYPE traceType = saConfigData.getTraceData().getType(0);

                    if (traceType == TRACE_TYPE.UPDATE) {
                        int count = (saConfigData.getTraceData().getDetector(0) == DETECTOR.NORMAL) ? SweepData.DATA_POINT.P2002.getDataPoint() : SweepData.DATA_POINT.P1001.getDataPoint();
                        ArrayList<Integer> dataList = mFunctionHandler.getMainLineChart().getDataList(0);

                        if (dataList.size() != count) {
                            dataList.clear();
                            for (int j = 0; j < count; j++) {
                                dataList.add(bb.getInt());
                            }
                        } else {
                            for (int j = 0; j < count; j++) {
                                dataList.set(j, bb.getInt());
                            }
                        }

                        final float obw = (float) ((double) bb.getInt() / (1000 * 1000));
                        final float xdb = (float) ((double) bb.getInt() / (1000 * 1000));
                        final float totalPower = (float) ((double) bb.getInt() / 100);
                        final int lowerPower = bb.getInt();
                        final int upperPower = bb.getInt();

                        saConfigData.getOccupiedBwMeasSetupData().setLowerPowerIndex(lowerPower);
                        saConfigData.getOccupiedBwMeasSetupData().setUpperPowerIndex(upperPower);
                        saConfigData.getOccupiedBwMeasSetupData().setOccupiedBW(obw);
                        saConfigData.getOccupiedBwMeasSetupData().setXDBBW(xdb);
                        saConfigData.getOccupiedBwMeasSetupData().setTotalPower(totalPower);

                        //@@ [22.01.27] 원전모니터링 Threshold 관련
                        /*if (mFunctionHandler.getMainLineChart().isEnabledLimitMsg())
                            mFunctionHandler.getMainLineChart().setEnabledLimitMsg(false);*/
                        //@@
                        mFunctionHandler.getMainLineChart().updateOccBox(false);

                        binding.lineChartLayout.mainLineChart.getData().clearValuesNotChanging(0);
                        mFunctionHandler.getMainLineChart().addEntry(mFunctionHandler.getMainLineChart().getDataList(0), 0, mode, type, saConfigData);
                        // chart 데이터 모두 추가 후 갱신
                        mFunctionHandler.getMainLineChart().addEntryUpdate();

                        drawCount++;

                        mActivity.runOnUiThread(() -> {
                            if (obw < 1f)
                                binding.lineChartLayout.occBwLayout.tvOccBWVal.setText(mActivity.getString(R.string.occupied_bandwidth)
                                        + Utils.formatNumber(obw * 1000, 2, false) + " kHz");
                            else
                                binding.lineChartLayout.occBwLayout.tvOccBWVal.setText(mActivity.getString(R.string.occupied_bandwidth)
                                        + Utils.formatNumber(obw, 2, false) + " MHz");

                            if (xdb < 1f)
                                binding.lineChartLayout.occBwLayout.tvXDBBWVal.setText("x dB Bandwidth : "
                                        + Utils.formatNumber(xdb * 1000, 2, false) + " kHz");
                            else
                                binding.lineChartLayout.occBwLayout.tvXDBBWVal.setText("x dB Bandwidth : "
                                        + Utils.formatNumber(xdb, 2, false) + " MHz");

                            binding.lineChartLayout.occBwLayout.tvTotalPowerVal.setText(mActivity.getString(R.string.total_power)
                                    + Utils.formatNumber(totalPower, 2, false) + " dBm");
//                        mFunctionHandler.getMainLineChart().setBlueZone(upperPower - lowerPower);
                        });
                    } else if (traceType == TRACE_TYPE.BLANK) {
                        binding.lineChartLayout.mainLineChart.getData().clearValuesNotChanging(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    isReceivingData = false;
                }
            }
            // SA - ACLR
            else if (mode == MeasureMode.MEASURE_MODE.SA && type == MeasureType.MEASURE_TYPE.ACLR && mode.getValue() == modeValue && type.getValue() == typeValue) {
                try {
                    if (D) Log.d("Data Connector", "ACLR");

                    SaConfigData saConfigData = saDataHandler.getAclrConfigData();

                    TRACE_TYPE traceType = saConfigData.getTraceData().getType(0);

                    if (traceType != TRACE_TYPE.VIEW) {
                        //
                        int count = (saConfigData.getTraceData().getDetector(0) == DETECTOR.NORMAL) ? SweepData.DATA_POINT.P2002.getDataPoint() : SweepData.DATA_POINT.P1001.getDataPoint();
                        ArrayList<Integer> dataList = mFunctionHandler.getMainLineChart().getDataList(0);

                        if (traceType == TRACE_TYPE.UPDATE) {
                            if (dataList.size() != count) {
                                dataList.clear();
                                for (int j = 0; j < count; j++) {
                                    dataList.add(bb.getInt());
                                }
                            } else {
                                for (int j = 0; j < count; j++) {
                                    dataList.set(j, bb.getInt());
                                }
                            }
                        } else {
                            // Blank
                            dataList.clear();
                            int pos = bb.position() + (count * 4);
                            bb.position(pos);
                        }

                        if (D) Log.d("DataConnector", "ACLR - Complete Data For State");

                        AclrMeasSetupData aclrData = saDataHandler.getAclrConfigData().getAclrMeasSetupData();

                        float totalCarrierPower = (float) ((double) bb.getInt() / 100);
                        float[] carrierPower = new float[2];

                        for (int i = 0; i < carrierPower.length; i++) {
                            carrierPower[i] = (float) ((double) bb.getInt() / 100);
                            aclrData.setCarrierPower(carrierPower[i], i);
                        }

                        float[] offsetLowerPowerDbc = new float[5];
                        float[] offsetLowerPowerDbm = new float[5];
                        Integer[] offsetLowerPassFail = new Integer[5];

                        float[] offsetUpperPowerDbc = new float[5];
                        float[] offsetUpperPowerDbm = new float[5];
                        Integer[] offsetUpperPassFail = new Integer[5];

                        for (int i = 0; i < 5; i++) {
                            offsetLowerPowerDbc[i] = (float) ((double) bb.getInt() / 100);
                            aclrData.setLowerPowerDbc(offsetLowerPowerDbc[i], i);

                            offsetLowerPowerDbm[i] = (float) ((double) bb.getInt() / 100);
                            aclrData.setLowerPowerDbm(offsetLowerPowerDbm[i], i);

                            offsetLowerPassFail[i] = bb.getInt();
                            aclrData.setLowerPassFail(offsetLowerPassFail[i], i);

                            offsetUpperPowerDbc[i] = (float) ((double) bb.getInt() / 100);
                            aclrData.setUpperPowerDbc(offsetUpperPowerDbc[i], i);

                            offsetUpperPowerDbm[i] = (float) ((double) bb.getInt() / 100);
                            aclrData.setUpperPowerDbm(offsetUpperPowerDbm[i], i);

                            offsetUpperPassFail[i] = bb.getInt();
                            aclrData.setUpperPassFail(offsetUpperPassFail[i], i);
                        }

//                    if (D) Log.d("DataConnector", "ACLR - Complete Setting value For State");

                        binding.lineChartLayout.mainLineChart.getData().clearValuesNotChanging(0);
                        mFunctionHandler.getMainLineChart().addEntry(mFunctionHandler.getMainLineChart().getDataList(0), 0, mode, type, saConfigData);

                        //@@ [22.01.27] 원전모니터링 Threshold 관련
                        /*if (mFunctionHandler.getMainLineChart().isEnabledLimitMsg())
                            mFunctionHandler.getMainLineChart().setEnabledLimitMsg(false);*/
                        //@@
                        mFunctionHandler.getMainLineChart().updateAclrBox();

                        Integer numOffset = saConfigData.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset();

                        for (int i = 0; i < numOffset; i++) {
                            /*Lower*/
                            if (offsetLowerPassFail[i] == 0) {
                                mFunctionHandler.getMainLineChart().setAclrLowerBoxColor(Color.RED, i);
                            } else {
                                mFunctionHandler.getMainLineChart().setAclrLowerBoxColor(Color.BLUE, i);
                            }

                            /*Upper*/
                            if (offsetUpperPassFail[i] == 0) {
                                mFunctionHandler.getMainLineChart().setAclrUpperBoxColor(Color.RED, i);
                            } else {
                                mFunctionHandler.getMainLineChart().setAclrUpperBoxColor(Color.BLUE, i);
                            }
                        }


                        // chart 데이터 모두 추가 후 갱신
                        mFunctionHandler.getMainLineChart().addEntryUpdate();

                        drawCount++;

                        mActivity.runOnUiThread(() -> {

                            AutofitTextView[] carrierPowerViews = {
                                    binding.lineChartLayout.aclrInfo.tvAclrCarrierPower1,
                                    binding.lineChartLayout.aclrInfo.tvAclrCarrierPower2,
                            };

                            AutofitTextView[] offsetSpacingViews = {
                                    binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq1,
                                    binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq2,
                                    binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq3,
                                    binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq4,
                                    binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq5,
                            };

                            AutofitTextView[] offsetIntegViews = {
                                    binding.lineChartLayout.aclrInfo.tvAclrIntegBw1,
                                    binding.lineChartLayout.aclrInfo.tvAclrIntegBw2,
                                    binding.lineChartLayout.aclrInfo.tvAclrIntegBw3,
                                    binding.lineChartLayout.aclrInfo.tvAclrIntegBw4,
                                    binding.lineChartLayout.aclrInfo.tvAclrIntegBw5,
                            };

                            AutofitTextView[] lowerPowerDbcViews = {
                                    binding.lineChartLayout.aclrInfo.tvAclrLowerDbc1,
                                    binding.lineChartLayout.aclrInfo.tvAclrLowerDbc2,
                                    binding.lineChartLayout.aclrInfo.tvAclrLowerDbc3,
                                    binding.lineChartLayout.aclrInfo.tvAclrLowerDbc4,
                                    binding.lineChartLayout.aclrInfo.tvAclrLowerDbc5,
                            };

                            AutofitTextView[] lowerPowerDbmViews = {
                                    binding.lineChartLayout.aclrInfo.tvAclrLowerDbm1,
                                    binding.lineChartLayout.aclrInfo.tvAclrLowerDbm2,
                                    binding.lineChartLayout.aclrInfo.tvAclrLowerDbm3,
                                    binding.lineChartLayout.aclrInfo.tvAclrLowerDbm4,
                                    binding.lineChartLayout.aclrInfo.tvAclrLowerDbm5,
                            };

                            AutofitTextView[] upperPowerDbcViews = {
                                    binding.lineChartLayout.aclrInfo.tvAclrUpperDbc1,
                                    binding.lineChartLayout.aclrInfo.tvAclrUpperDbc2,
                                    binding.lineChartLayout.aclrInfo.tvAclrUpperDbc3,
                                    binding.lineChartLayout.aclrInfo.tvAclrUpperDbc4,
                                    binding.lineChartLayout.aclrInfo.tvAclrUpperDbc5,
                            };

                            AutofitTextView[] upperPowerDbmViews = {
                                    binding.lineChartLayout.aclrInfo.tvAclrUpperDbm1,
                                    binding.lineChartLayout.aclrInfo.tvAclrUpperDbm2,
                                    binding.lineChartLayout.aclrInfo.tvAclrUpperDbm3,
                                    binding.lineChartLayout.aclrInfo.tvAclrUpperDbm4,
                                    binding.lineChartLayout.aclrInfo.tvAclrUpperDbm5,
                            };

                            binding.lineChartLayout.aclrInfo.tvAclrTotalCarrierPowerVal.setText(Utils.formatNumber(totalCarrierPower, 2, false) + " dBm");

                            AclrOffsetSetupData offsetData = SaDataHandler
                                    .getInstance()
                                    .getAclrConfigData()
                                    .getAclrMeasSetupData()
                                    .getOffsetSetupData();

                            /*Carrier data*/
                            AclrCarrierSetupData carrierData = aclrData.getCarrierSetupData();

                            double integ = carrierData.getIntegBw();
                            integ = Math.round(integ * 100d) / 100d;

                            Integer carriers = saDataHandler.getAclrConfigData().getAclrMeasSetupData().getCarrierSetupData().getCarriers();

                            for (int i = 0; i < carriers; i++) {

                                carrierPowerViews[i].setText(
                                        i + ". " + Utils.formatNumber(carrierPower[i], 2, false) + " dBm / " + integ + " MHz"
                                );

                            }

                            //Integer numOffset = offsetData.getNumOfOffset();

                            for (int i = 0; i < numOffset; i++) {

                                if (D) Log.d("Data Connector", i + " " + numOffset);

                                double spacing = offsetData.getOffsetSpacing(i);
                                spacing = Math.round(spacing * 100d) / 100d;

                                double offsetInteg = offsetData.getIntegBw(i);
                                offsetInteg = Math.round(offsetInteg * 100d) / 100d;
                                offsetSpacingViews[i].setText(spacing + " MHz");
                                offsetIntegViews[i].setText(offsetInteg + " MHz");

                                /*Lower*/
                                if (offsetLowerPassFail[i] == 0) {
                                    lowerPowerDbcViews[i].setTextColor(Color.RED);
                                    lowerPowerDbmViews[i].setTextColor(Color.RED);
                                    //mFunctionHandler.getMainLineChart().setAclrLowerBoxColor(Color.RED, i);
                                } else {
                                    lowerPowerDbcViews[i].setTextColor(Color.WHITE);
                                    lowerPowerDbmViews[i].setTextColor(Color.WHITE);
                                    //mFunctionHandler.getMainLineChart().setAclrLowerBoxColor(Color.BLUE, i);
                                }

                                lowerPowerDbcViews[i].setText(Utils.formatNumber(aclrData.getLowerPowerDbc(i), 2, false));
                                if (D)
                                    Log.d("DataConnector", "lower power dbm : " + aclrData.getLowerPowerDbm(i));
                                lowerPowerDbmViews[i].setText(Utils.formatNumber(aclrData.getLowerPowerDbm(i), 2, false));

                                /*Upper*/
                                if (offsetUpperPassFail[i] == 0) {
                                    upperPowerDbcViews[i].setTextColor(Color.RED);
                                    upperPowerDbmViews[i].setTextColor(Color.RED);
                                    //mFunctionHandler.getMainLineChart().setAclrUpperBoxColor(Color.RED, i);
                                } else {
                                    upperPowerDbcViews[i].setTextColor(Color.WHITE);
                                    upperPowerDbmViews[i].setTextColor(Color.WHITE);
                                    //mFunctionHandler.getMainLineChart().setAclrUpperBoxColor(Color.BLUE, i);
                                }

                                upperPowerDbcViews[i].setText(Utils.formatNumber(aclrData.getUpperPowerDbc(i), 2, false));
                                upperPowerDbmViews[i].setText(Utils.formatNumber(aclrData.getUpperPowerDbm(i), 2, false));
                            }
                        });

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    isReceivingData = false;
                }

            }
            // SA - ?
            else if (type == MeasureType.MEASURE_TYPE.SPURIOUS_EMISSION && typeValue == MeasureType.MEASURE_TYPE.SPURIOUS_EMISSION.getValue()) {
//
//                TRACE_TYPE traceType = saDataHandler.getConfigData().getTraceData().getType(0);
//
//                if (traceType != TRACE_TYPE.VIEW) {
//
//                    mFunctionHandler.getMainLineChart().getDataList(0).clear();
//
//                    for (int j = 0; j < SweepData.DATA_POINT.P2002.getDataPoint(); j++) {
//
//                        try {
//                            if (inputStream.read(integer4ByteArr) == -1) break;
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        int data = byteToInteger(integer4ByteArr, 4);
//                        if (traceType == TRACE_TYPE.UPDATE)
//                            mFunctionHandler.getMainLineChart().getDataList(0).add(data);
//
//                    }
//
//                    SpuriousEmissionMeasSetupData measData = saDataHandler.getSpuriousEmissionConfigData().getSpuriousEmissionData();
//                    ArrayList<FreqRangeTableData> tableList = measData.getFreqRangeTableDataList();
//                    try {
//
//                        if (inputStream.read(integer4ByteArr) == -1) {
//                            isReceivingData = false;
//                            return;
//                        }
//
//                        int totalResult = byteToInteger(integer4ByteArr, 4);
//
//                        AutofitTextView[] peakFreqArr = {
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousPeakFreq1,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousPeakFreq2,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousPeakFreq3,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousPeakFreq4,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousPeakFreq5,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousPeakFreq6
//                        };
//
//                        AutofitTextView[] peakPwrArr = {
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousPower1,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousPower2,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousPower3,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousPower4,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousPower5,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousPower6
//                        };
//
//                        AutofitTextView[] deltaLimitArr = {
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousLimit1,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousLimit2,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousLimit3,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousLimit4,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousLimit5,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousLimit6
//                        };
//
//                        AutofitTextView[] resultArr = {
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousResult1,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousResult2,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousResult3,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousResult4,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousResult5,
//                                binding.lineChartLayout.spuriousEmission.tvSpuriousResult6
//                        };
//
//                        for (int i = 0; i < tableList.size(); i++) {
//
//                            if (inputStream.read(integer4ByteArr) == -1) {
//                                isReceivingData = false;
//                                return;
//                            }
//
//                            if (inputStream.read(integer4ByteArr) == -1) {
//                                if (D) Log.d("addChartValue", "peak freq error" + i);
//                            }
//                            double peakFreq = (double)byteToInteger(integer4ByteArr, 4) / 10000d;
//                            tableList.get(i).setPeakFreq(peakFreq);
//
//                            if (inputStream.read(integer4ByteArr) == -1) {
//                                if (D) Log.d("addChartValue", "peak pwr error" + i);
//                            }
//                            double peakPwr = (double)byteToInteger(integer4ByteArr, 4) / 100d;
//                            tableList.get(i).setPeakPwr(peakPwr);
//
//                            if (inputStream.read(integer4ByteArr) == -1) {
//                                if (D) Log.d("addChartValue", "delta limit error" + i);
//                            }
//                            double deltaLimit = byteToInteger(integer4ByteArr, 4) / 100d;
//                            tableList.get(i).setDeltaLimit(deltaLimit);
//
//                            if (inputStream.read(integer4ByteArr) == -1) {
//                                if (D) Log.d("addChartValue", "spurious result error" + i);
//                            }
//                            int result = byteToInteger(integer4ByteArr, 4);
//                            tableList.get(i).setResult(result);
//
//                            int finalI = i;
//                            new Thread(()->{
//                                mMainHandler.post(()->{
//
//                                    peakFreqArr[finalI].setText(peakFreq + " MHz");
//                                    peakPwrArr[finalI].setText(peakPwr + "");
//                                    deltaLimitArr[finalI].setText(deltaLimit + "");
//
//                                    if(result == 0) {
//                                        peakFreqArr[finalI].setTextColor(Color.RED);
//                                        peakPwrArr[finalI].setTextColor(Color.RED);
//                                        deltaLimitArr[finalI].setTextColor(Color.RED);
//                                        resultArr[finalI].setTextColor(Color.RED);
//                                        resultArr[finalI].setText(mActivity.getString(R.string.fail_name));
//                                    } else if(result == 1) {
//                                        peakFreqArr[finalI].setTextColor(Color.WHITE);
//                                        peakPwrArr[finalI].setTextColor(Color.WHITE);
//                                        deltaLimitArr[finalI].setTextColor(Color.WHITE);
//                                        resultArr[finalI].setTextColor(mActivity.getColor(R.color.pass));
//                                        resultArr[finalI].setText(mActivity.getString(R.string.pass_name));
//                                    }
//
//                                });
//                            }).start();
//
//                        }
//
//                    } catch (NullPointerException e) {
//                        if (D) Log.d("addChartValue", "receive data... Null Error");
//                    } catch (IOException e) {
//                        if (D) Log.d("addChartValue", "receive data... IO Error");
//                    }
//
//                }
            }
            // SA - SEM ?
            else if (mode == MeasureMode.MEASURE_MODE.SA && type == MeasureType.MEASURE_TYPE.SEM && mode.getValue() == modeValue && type.getValue() == typeValue) {
                try {
                    if (D) Log.d("Data Connector", "SEM");

                    SaConfigData saConfigData = saDataHandler.getSemConfigData();

                    TRACE_TYPE traceType = saConfigData.getTraceData().getType(0);

                    if (traceType != TRACE_TYPE.VIEW) {

                        mFunctionHandler.getMainLineChart().getDataList(0).clear();

                        for (int j = 0; j < SweepData.DATA_POINT.P1001.getDataPoint(); j++) {

                            try {
                                if (inputStream.read(integer4ByteArr) == -1) break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            int data = byteToInteger(integer4ByteArr, 4);
                            if (traceType == TRACE_TYPE.UPDATE)
                                mFunctionHandler.getMainLineChart().getDataList(0).add(data);

                        }

                        SemMeasSetupData semData = saDataHandler.getSemConfigData().getSemMeasSetupData();
                        SemEditMaskData maskData = semData.getEditMaskData();
                        SemRefChannelData chData = semData.getRefChannelData();


                        if (inputStream.read(integer4ByteArr) == -1) {
                            isReceivingData = false;
                            return;
                        }
                        int semResult = byteToInteger(integer4ByteArr, 4);
                        semData.setSemResult(semResult);

                        //Log.e("SEM", "Sem Result : " + semResult);

                        if (inputStream.read(integer4ByteArr) == -1) {
                            isReceivingData = false;
                            return;
                        }
                        float chPower = (float) byteToInteger(integer4ByteArr, 4) / 100f;
                        semData.setChPower(chPower);

                        if (inputStream.read(integer4ByteArr) == -1) {
                            isReceivingData = false;
                            return;
                        }
                        float density = (float) byteToInteger(integer4ByteArr, 4) / 100f;
                        semData.setDensity(density);

                        Integer[] lowerPeakIndex = new Integer[4];
                        Float[] lowerPeakPower = new Float[4];
                        Integer[] lowerResult = new Integer[4];
                        Float[] lowerDeltaLimit = new Float[4];
                        Float[] lowerPeakFreq = new Float[4];
                        Integer[] lowerPassFail = new Integer[4];

                        Integer[] upperPeakIndex = new Integer[4];
                        Float[] upperPeakPower = new Float[4];
                        Integer[] upperResult = new Integer[4];
                        Float[] upperDeltaLimit = new Float[4];
                        Float[] upperPeakFreq = new Float[4];
                        Integer[] upperPassFail = new Integer[4];

                        for (int i = 0; i < 4; i++) {
                            /*//@@ [21.12.29] SEM Info Table fix
                            SemEditMaskData.MASK_ON_OFF maskOnOff = SaDataHandler.getInstance().getSemConfigData().getSemMeasSetupData().getEditMaskData().getMaskOnOff(i);

                            if (maskOnOff == SemEditMaskData.MASK_ON_OFF.OFF) continue;
                            //@@*/

                            if (inputStream.read(integer4ByteArr) == -1) {
                                if (D) Log.d("addChartValue", "lower peak error" + i);
                            }
                            lowerPeakIndex[i] = byteToInteger(integer4ByteArr, 4);

                            if (inputStream.read(integer4ByteArr) == -1) {
                                if (D) Log.d("addChartValue", "lower peak power error" + i);
                            }
                            lowerPeakPower[i] = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                            if (inputStream.read(integer4ByteArr) == -1) {
                                if (D) Log.d("addChartValue", "lower result error" + i);
                            }
                            lowerResult[i] = byteToInteger(integer4ByteArr, 4);

                            if (inputStream.read(integer4ByteArr) == -1) {
                                if (D) Log.d("addChartValue", "lower delta limit error" + i);
                            }
                            lowerDeltaLimit[i] = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                            if (inputStream.read(integer4ByteArr) == -1) {
                                if (D) Log.d("addChartValue", "lower peak freq error" + i);
                            }
                            lowerPeakFreq[i] = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                            if (inputStream.read(integer4ByteArr) == -1) {
                                if (D) Log.d("addChartValue", "upper peak index error" + i);
                            }
                            upperPeakIndex[i] = byteToInteger(integer4ByteArr, 4);

                            if (inputStream.read(integer4ByteArr) == -1) {
                                if (D) Log.d("addChartValue", "upper peak power error" + i);
                            }
                            upperPeakPower[i] = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                            if (inputStream.read(integer4ByteArr) == -1) {
                                if (D) Log.d("addChartValue", "upper result error" + i);
                            }
                            upperResult[i] = byteToInteger(integer4ByteArr, 4);

                            if (inputStream.read(integer4ByteArr) == -1) {
                                if (D) Log.d("addChartValue", "upper delta limit error" + i);
                            }
                            upperDeltaLimit[i] = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                            if (inputStream.read(integer4ByteArr) == -1) {
                                if (D) Log.d("addChartValue", "upper peak freq error" + i);
                            }
                            upperPeakFreq[i] = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                            //@@ SEM Data text refresh here

                            /*semData.setLowerPeakIndex(lowerPeakIndex[i], i);
                            semData.setLowerPkPower(lowerPeakPower[i], i);
                            semData.setLowerResult(lowerResult[i], i);
                            semData.setLowerLimit(lowerDeltaLimit[i], i);
                            semData.setLowerFreq(lowerPeakFreq[i], i);

                            semData.setUpperPeakIndex(upperPeakIndex[i], i);
                            semData.setUpperPkPower(upperPeakPower[i], i);
                            semData.setUpperResult(upperResult[i], i);
                            semData.setUpperLimit(upperDeltaLimit[i], i);
                            semData.setUpperFreq(upperPeakFreq[i], i);*/

                            semData.setLowerPeakIndex(lowerPeakIndex[i], i);
                            semData.setUpperPeakIndex(upperPeakIndex[i], i);

                            if (semData.getEditMaskData().getMaskOnOff(i) == SemEditMaskData.MASK_ON_OFF.ON) {
                                semData.setLowerPkPower(lowerPeakPower[i], i);
                                semData.setLowerResult(lowerResult[i], i);
                                semData.setLowerLimit(lowerDeltaLimit[i], i);
                                semData.setLowerFreq(lowerPeakFreq[i], i);

                                semData.setUpperPkPower(upperPeakPower[i], i);
                                semData.setUpperResult(upperResult[i], i);
                                semData.setUpperLimit(upperDeltaLimit[i], i);
                                semData.setUpperFreq(upperPeakFreq[i], i);
                            }
                            else {

                            }



                        }

                        //mActivity.runOnUiThread(() -> {
                        //@@ [22.01.27] 원전모니터링 Threshold 관련
                        /*if (mFunctionHandler.getMainLineChart().isEnabledLimitMsg())
                            mFunctionHandler.getMainLineChart().setEnabledLimitMsg(false);*/
                        //@@
                        mFunctionHandler.getMainLineChart().updateSemBox();

                        binding.lineChartLayout.mainLineChart.getData().clearValuesNotChanging(0);
                        mFunctionHandler.getMainLineChart().addEntry(mFunctionHandler.getMainLineChart().getDataList(0), 0, mode, type, saConfigData);
                        // chart 데이터 모두 추가 후 갱신
                        mFunctionHandler.getMainLineChart().addEntryUpdate();

                        //@@ [22.01.05] Sem sub Info Pass/Fail fix
                        mActivity.runOnUiThread(() -> {
                            if (semResult == 1) {
                                binding.lineChartLayout.semLayout.tvSemPassTitle.setTextColor(Color.parseColor("#21FFFF"));
                                binding.lineChartLayout.semLayout.tvSemPassTitle.setText(R.string.pass_result);
                            }
                            else {
                                binding.lineChartLayout.semLayout.tvSemPassTitle.setTextColor(Color.RED);
                                binding.lineChartLayout.semLayout.tvSemPassTitle.setText(R.string.fail_result);
                            }

                            int numEditMask = semData.getEditMaskData().MAX_MASK_INDEX;
                            int pass = Color.WHITE;
                            int fail = Color.RED;

                            for (int i = 0; i < numEditMask; i++) {
                                //@@ off 인경우 pass/fail 여부 상관없이 색상 흰색으로 변경
                                if (semData.getEditMaskData().getMaskOnOff(i) == SemEditMaskData.MASK_ON_OFF.OFF) {
                                    switch (i) {
                                        case 0:
                                            if (binding.lineChartLayout.semLayout.tvSemLowerPkPwr1.getCurrentTextColor() == fail) {
                                                binding.lineChartLayout.semLayout.tvSemLowerPkPwr1.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerLimit1.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerFreq1.setTextColor(pass);

                                                binding.lineChartLayout.semLayout.tvSemUpperPkPwr1.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperLimit1.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperFreq1.setTextColor(pass);
                                            }
                                            break;
                                        case 1:
                                            if (binding.lineChartLayout.semLayout.tvSemLowerPkPwr2.getCurrentTextColor() == fail) {
                                                binding.lineChartLayout.semLayout.tvSemLowerPkPwr2.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerLimit2.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerFreq2.setTextColor(pass);

                                                binding.lineChartLayout.semLayout.tvSemUpperPkPwr2.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperLimit2.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperFreq2.setTextColor(pass);
                                            }
                                            break;
                                        case 2:
                                            if (binding.lineChartLayout.semLayout.tvSemLowerPkPwr3.getCurrentTextColor() == fail) {
                                                binding.lineChartLayout.semLayout.tvSemLowerPkPwr3.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerLimit3.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerFreq3.setTextColor(pass);

                                                binding.lineChartLayout.semLayout.tvSemUpperPkPwr3.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperLimit3.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperFreq3.setTextColor(pass);
                                            }
                                            break;
                                        case 3:
                                            if (binding.lineChartLayout.semLayout.tvSemLowerPkPwr4.getCurrentTextColor() == fail) {
                                                binding.lineChartLayout.semLayout.tvSemLowerPkPwr4.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerLimit4.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerFreq4.setTextColor(pass);

                                                binding.lineChartLayout.semLayout.tvSemUpperPkPwr4.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperLimit4.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperFreq4.setTextColor(pass);
                                            }
                                            break;
                                    }
                                }

                                if (semData.getLowerResult(i) == 0 && semData.getEditMaskData().getMaskOnOff(i) == SemEditMaskData.MASK_ON_OFF.ON) {
                                    // lower fail
                                    switch (i) {
                                        case 0: {
                                            if (binding.lineChartLayout.semLayout.tvSemLowerPkPwr1.getCurrentTextColor() != fail) {

                                                binding.lineChartLayout.semLayout.tvSemLowerPkPwr1.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemLowerLimit1.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemLowerFreq1.setTextColor(fail);

                                            }
                                            break;
                                        }
                                        case 1: {
                                            if (binding.lineChartLayout.semLayout.tvSemLowerPkPwr2.getCurrentTextColor() != fail) {

                                                binding.lineChartLayout.semLayout.tvSemLowerPkPwr2.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemLowerLimit2.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemLowerFreq2.setTextColor(fail);

                                            }

                                            break;
                                        }
                                        case 2: {
                                            if (binding.lineChartLayout.semLayout.tvSemLowerPkPwr3.getCurrentTextColor() != fail) {

                                                binding.lineChartLayout.semLayout.tvSemLowerPkPwr3.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemLowerLimit3.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemLowerFreq3.setTextColor(fail);

                                            }
                                            break;
                                        }
                                        case 3: {

                                            if (binding.lineChartLayout.semLayout.tvSemLowerPkPwr4.getCurrentTextColor() != fail) {

                                                binding.lineChartLayout.semLayout.tvSemLowerPkPwr4.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemLowerLimit4.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemLowerFreq4.setTextColor(fail);

                                            }
                                            break;

                                        }
                                    }
                                }
                                else if (semData.getLowerResult(i) == 1 && semData.getEditMaskData().getMaskOnOff(i) == SemEditMaskData.MASK_ON_OFF.ON) {
                                    // lower pass
                                    switch (i) {
                                        case 0: {
                                            if (binding.lineChartLayout.semLayout.tvSemLowerPkPwr1.getCurrentTextColor() != pass) {

                                                binding.lineChartLayout.semLayout.tvSemLowerPkPwr1.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerLimit1.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerFreq1.setTextColor(pass);

                                            }
                                            break;
                                        }
                                        case 1: {
                                            if (binding.lineChartLayout.semLayout.tvSemLowerPkPwr2.getCurrentTextColor() != pass) {

                                                binding.lineChartLayout.semLayout.tvSemLowerPkPwr2.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerLimit2.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerFreq2.setTextColor(pass);

                                            }
                                            break;
                                        }
                                        case 2: {
                                            if (binding.lineChartLayout.semLayout.tvSemLowerPkPwr3.getCurrentTextColor() != pass) {

                                                binding.lineChartLayout.semLayout.tvSemLowerPkPwr3.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerLimit3.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerFreq3.setTextColor(pass);

                                            }
                                            break;
                                        }
                                        case 3: {
                                            if (binding.lineChartLayout.semLayout.tvSemLowerPkPwr4.getCurrentTextColor() != pass) {

                                                binding.lineChartLayout.semLayout.tvSemLowerPkPwr4.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerLimit4.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemLowerFreq4.setTextColor(pass);

                                            }
                                            break;
                                        }
                                    }
                                }

                                if (semData.getUpperResult(i) == 0 && semData.getEditMaskData().getMaskOnOff(i) == SemEditMaskData.MASK_ON_OFF.ON) {
                                    // upper fail
                                    switch (i) {
                                        case 0: {
                                            if (binding.lineChartLayout.semLayout.tvSemUpperPkPwr1.getCurrentTextColor() != fail) {

                                                binding.lineChartLayout.semLayout.tvSemUpperPkPwr1.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemUpperLimit1.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemUpperFreq1.setTextColor(fail);

                                            }
                                            break;
                                        }
                                        case 1: {
                                            if (binding.lineChartLayout.semLayout.tvSemUpperPkPwr2.getCurrentTextColor() != fail) {

                                                binding.lineChartLayout.semLayout.tvSemUpperPkPwr2.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemUpperLimit2.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemUpperFreq2.setTextColor(fail);

                                            }
                                            break;
                                        }
                                        case 2: {
                                            if (binding.lineChartLayout.semLayout.tvSemUpperPkPwr3.getCurrentTextColor() != fail) {

                                                binding.lineChartLayout.semLayout.tvSemUpperPkPwr3.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemUpperLimit3.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemUpperFreq3.setTextColor(fail);

                                            }
                                            break;
                                        }
                                        case 3: {
                                            if (binding.lineChartLayout.semLayout.tvSemUpperPkPwr4.getCurrentTextColor() != fail) {

                                                binding.lineChartLayout.semLayout.tvSemUpperPkPwr4.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemUpperLimit4.setTextColor(fail);
                                                binding.lineChartLayout.semLayout.tvSemUpperFreq4.setTextColor(fail);

                                            }
                                            break;
                                        }
                                    }
                                }
                                else if (semData.getUpperResult(i) == 1 && semData.getEditMaskData().getMaskOnOff(i) == SemEditMaskData.MASK_ON_OFF.ON) {
                                    // upper pass
                                    switch (i) {
                                        case 0: {
                                            if (binding.lineChartLayout.semLayout.tvSemUpperPkPwr1.getCurrentTextColor() != pass) {

                                                binding.lineChartLayout.semLayout.tvSemUpperPkPwr1.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperLimit1.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperFreq1.setTextColor(pass);
                                            }
                                            break;
                                        }
                                        case 1: {
                                            if (binding.lineChartLayout.semLayout.tvSemUpperPkPwr2.getCurrentTextColor() != pass) {

                                                binding.lineChartLayout.semLayout.tvSemUpperPkPwr2.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperLimit2.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperFreq2.setTextColor(pass);

                                            }
                                            break;
                                        }
                                        case 2: {

                                            if (binding.lineChartLayout.semLayout.tvSemUpperPkPwr3.getCurrentTextColor() != pass) {

                                                binding.lineChartLayout.semLayout.tvSemUpperPkPwr3.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperLimit3.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperFreq3.setTextColor(pass);

                                            }
                                            break;
                                        }
                                        case 3: {

                                            if (binding.lineChartLayout.semLayout.tvSemUpperPkPwr4.getCurrentTextColor() != pass) {

                                                binding.lineChartLayout.semLayout.tvSemUpperPkPwr4.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperLimit4.setTextColor(pass);
                                                binding.lineChartLayout.semLayout.tvSemUpperFreq4.setTextColor(pass);

                                            }
                                            break;
                                        }
                                    }
                                }
                            }

                        });
                        //@@

                        drawCount++;
                        //});

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    isReceivingData = false;
                }

            }
            // SA - Interference
            else if (mode == MeasureMode.MEASURE_MODE.SA && type == MeasureType.MEASURE_TYPE.INTERFERENCE_HUNTING && mode.getValue() == modeValue && type.getValue() == typeValue) {
                try {
                    if (D) Log.d("Data Connector", "Interference hunting");

                    SaConfigData saConfigData = saDataHandler.getInterferenceHuntingConfigData();

                    TRACE_TYPE traceType = saConfigData.getTraceData().getType(0);

                    if (traceType != TRACE_TYPE.VIEW) {

                        int count = SweepData.DATA_POINT.P2002.getDataPoint();
                        ArrayList<Integer> dataList = mFunctionHandler.getMainLineChart().getDataList(0);

                        if (traceType == TRACE_TYPE.UPDATE) {
                            if (dataList.size() != count) {
                                dataList.clear();
                                for (int j = 0; j < count; j++) {
                                    dataList.add(bb.getInt());
                                }
                            } else {
                                for (int j = 0; j < count; j++) {
                                    dataList.set(j, bb.getInt());
                                }
                            }
                        } else {
                            // Blank
                            dataList.clear();
                            int pos = bb.position() + (count * 4);
                            bb.position(pos);
                        }


                        InterferenceMeasSetupData huntingData = saDataHandler.getInterferenceHuntingConfigData().getInterferenceHuntingData();

                        int spurState = bb.getInt();
                        if (spurState == 0) {
                            huntingData.setSpurState(InterferenceMeasSetupData.SpurState.NO_SPUR);
                        } else if (spurState == 1) {
                            huntingData.setSpurState(InterferenceMeasSetupData.SpurState.DETECT_SPUR);
                        }

                        int spurType = bb.getInt();
                        if (spurType == 0) {
                            huntingData.setSpurType(InterferenceMeasSetupData.SpurType.SINGLE);
                        } else if (spurType == 1) {
                            huntingData.setSpurType(InterferenceMeasSetupData.SpurType.WIDE);
                        }

                        double power = (double) bb.getInt() / 100d;

                        if (huntingData.getSpurState() == InterferenceMeasSetupData.SpurState.DETECT_SPUR) {

                            huntingData.addTableData(new SpurTableData(InitActivity.getCurrentTime("yyyy.MM.dd HH:mm:ss"), huntingData.getSpurType(), power));

                            mActivity.runOnUiThread(huntingData::updateTableView);
                        }

                        //mActivity.runOnUiThread(() -> {

                        if (mFunctionHandler.getMainLineChart().isEnabledLimitMsg())
                            mFunctionHandler.getMainLineChart().setEnabledLimitMsg(false);
                        mFunctionHandler.getMainLineChart().updateSemBox(false);

                        binding.lineChartLayout.mainLineChart.getData().clearValuesNotChanging(0);
                        mFunctionHandler.getMainLineChart().addEntry(mFunctionHandler.getMainLineChart().getDataList(0), 0, mode, type, saConfigData);
                        // chart 데이터 모두 추가 후 갱신
                        mFunctionHandler.getMainLineChart().addEntryUpdate();

                        drawCount++;
                        //});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    isReceivingData = false;
                }

            }
            // SA - Transmit
            else if (mode == MeasureMode.MEASURE_MODE.SA && type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK && mode.getValue() == modeValue && type.getValue() == typeValue) {
                try {
                    if (D) Log.d("Data Connector", "Transmit On Off");

                    SaConfigData saConfigData = saDataHandler.getTransmitConfigData();

                    TRACE_TYPE traceType = saConfigData.getTraceData().getType(0);

                    if (traceType != TRACE_TYPE.VIEW) {

                        int count = (saConfigData.getTraceData().getDetector(0) == DETECTOR.NORMAL) ? SweepData.DATA_POINT.P2002.getDataPoint() : SweepData.DATA_POINT.P1001.getDataPoint();
                        ArrayList<Integer> dataList = mFunctionHandler.getMainLineChart().getDataList(0);

                        if (traceType == TRACE_TYPE.UPDATE) {
                            if (dataList.size() != count) {
                                dataList.clear();
                                for (int j = 0; j < count; j++) {
                                    dataList.add(bb.getInt());
                                }
                            } else {
                                for (int j = 0; j < count; j++) {
                                    dataList.set(j, bb.getInt());
                                }
                            }
                        } else {
                            // Blank
                            dataList.clear();
                            int pos = bb.position() + (count * 4);
                            bb.position(pos);
                        }

                        Float onPower = (float) bb.getInt() / 100f;
                        Float offPower = (float) bb.getInt() / 100f;
                        Float rampUpTime = (float) bb.getInt() / 100f;
                        Float rampDownTime = (float) bb.getInt() / 100f;
                        Integer offPowerPassFail = bb.getInt();
                        Integer rampUpTimePassFail = bb.getInt();
                        Integer rampDownTimePassFail = bb.getInt();

                        mActivity.runOnUiThread(() -> {
                            TransmitOnOffMeasSetupData transmitData = saDataHandler.getTransmitConfigData().getTransmitOnOffData();
                            transmitData.setOnPower(onPower);
                            transmitData.setOffPower(offPower);
                            transmitData.setRampUpTime(rampUpTime);
                            transmitData.setRampDownTime(rampDownTime);
                            transmitData.setOffPowerPass(offPowerPassFail);
                            transmitData.setRampUpTimePass(rampUpTimePassFail);
                            transmitData.setRampDownTimePass(rampDownTimePassFail);
                        });

                        //mActivity.runOnUiThread(() -> {
                        binding.lineChartLayout.mainLineChart.getData().clearValuesNotChanging(0);
                        binding.lineChartLayout.mainLineChart.getData().clearValuesNotChanging(mFunctionHandler.getMainLineChart().LIMIT_DATASET_INDEX);
                        mFunctionHandler.getMainLineChart().addEntry(mFunctionHandler.getMainLineChart().getDataList(0), 0, mode, type, saConfigData);
                        // chart 데이터 모두 추가 후 갱신
                        mFunctionHandler.getMainLineChart().addEntryUpdate();

                        drawCount++;
                        //});

                        //ViewHandler.getInstance().getContent().markerValueUpdate();
                        //ViewHandler.getInstance().getContent().markerTableUpdate();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    isReceivingData = false;
                }
            }
        }
        // 5G NR
        else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY
                && (type == MeasureType.MEASURE_TYPE.NR_5G || type == MeasureType.MEASURE_TYPE.TAE)
                && modeValue == MeasureMode.MEASURE_MODE.MOD_ACCURACY.getValue()
                && (typeValue == MeasureType.MEASURE_TYPE.NR_5G.getValue() || typeValue == MeasureType.MEASURE_TYPE.TAE.getValue())) {

            if (D) Log.d("Demodulation", "this is data connector");
            mFunctionHandler.getMainLineChart().setEnabledLimitMsg(false);

            for (int i = 0; i < mFunctionHandler.getScatterChart().getDataSetList().length; i++) {

                try {
                    if (inputStream.read(integer4ByteArr) == -1)
                        if (D) Log.d("I/Q Constellation", "data size error");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int dataSize = byteToInteger(integer4ByteArr, 4);
                mFunctionHandler.getScatterChart().getDataList(i).clear();

                Float[] x = new Float[dataSize];
                Float[] y = new Float[dataSize];

                for (int k = 0; k < dataSize; k++) {
                    try {
                        if (inputStream.read(integer4ByteArr) == -1)
                            if (D) Log.d("I/Q Constellation", "x value error(" + k + ")");
                        x[k] = (float) byteToInteger(integer4ByteArr, 4) / 10000f;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                for (int k = 0; k < dataSize; k++) {
                    try {
                        if (inputStream.read(integer4ByteArr) == -1)
                            if (D) Log.d("I/Q Constellation", "y value error(" + k + ")");
                        y[k] = (float) byteToInteger(integer4ByteArr, 4) / 10000f;

                        //add scatter data
                        mFunctionHandler.getScatterChart().getDataList(i).add(new Entry(x[k], y[k]));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            mFunctionHandler.getScatterChart().updateEntry();

            /* ============================== end scatter data (I/Q Constellation) ===========================*/

            /*
             * Start add beam index power data entry
             */
            for (int i = 0; i < mFunctionHandler.getCandleChartFunc().getDataSetList().size(); i++) {
                try {
                    if (inputStream.read(integer4ByteArr) == -1)
                        if (D) Log.d("Beam index power", "value error(" + i + ")");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int recvData = byteToInteger(integer4ByteArr, 4);

                float beamData = (float) ((double) recvData / 100);

                mFunctionHandler.getCandleChartFunc().getDataList(i).clear();
                mFunctionHandler.getCandleChartFunc().addData(beamData, i);
            }

            mFunctionHandler.getCandleChartFunc().updateEntry();

            /* ============================== end candle data (Beam Index Power) ===========================*/

            /*
             * receive cell information
             */

            Integer physicalCellId = null;
            Integer groupId = null;
            Integer sectorId = null;
            Integer subcarrierSpacing = null;
            Integer ssbFreq = null;

            /* end cell information
             *  start power information*/

            Integer ssrsrp = null;
            Integer ssrsrq = null;
            Integer ssSinr = null;
            Integer beamPower = null;
            Integer beamIndex = null;
            Integer exTxMaxPower = null;

            /* end power information
             *  start signal quality*/

            Integer freqOffsetThreshold = null;
            Integer freqOffsetHz = null;
            Integer freqOffsetResult = null;

            Integer pssEvm = null;
            Integer sssEvm = null;
            Integer pbchEvm = null;
            Integer pbchDmrsEvm = null;
            Integer pdcchEvm = null;
            Integer pdcchDmrsEvm = null;
            Integer pdschEvm = null;
            Integer pdschDmrsEvm = null;

            Integer pssPower = null;
            Integer sssPower = null;
            Integer pbchPower = null;
            Integer pbchDmrsPower = null;
            Integer pdcchPower = null;
            Integer pdcchDmrsPower = null;
            Integer pdschPower = null;
            Integer pdschDmrsPower = null;

            Integer pssModType = null;
            Integer sssModType = null;
            Integer pbchModType = null;
            Integer pbchDmrsModType = null;
            Integer pdcchModType = null;
            Integer pdcchDmrsModType = null;
            Integer pdschModType = null;
            Integer pdschDmrsModType = null;

            Integer pssNumRb = null;
            Integer sssNumRb = null;
            Integer pbchNumRb = null;
            Integer pbchDmrsNumRb = null;
            Integer pdcchNumRb = null;
            Integer pdcchDmrsNumRb = null;
            Integer pdschNumRb = null;
            Integer pdschDmrsNumRb = null;

            Integer timingOffset = null;
            Integer timeAlignmentError = null;
            Integer taePassFailResult = null;
            Integer taeCurMeasCount = null;

            try {

                /*cell information*/
                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("PhysicalCellId", "error");

                physicalCellId = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("GroupID", "error");

                groupId = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("SectorID", "error");

                sectorId = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("subcarrier", "error");

                subcarrierSpacing = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("ssbFreq", "error");

                ssbFreq = byteToInteger(integer4ByteArr, 4);

                /*power information*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("ssrsrp", "error");
                ssrsrp = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("ssrsrq", "error");
                ssrsrq = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("ssSinr", "error");
                ssSinr = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("beam power", "error");
                beamPower = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("beam index", "error");
                beamIndex = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("exTx", "error");
                exTxMaxPower = byteToInteger(integer4ByteArr, 4);

                /*Signal Quality chart*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset hold", "error");

                freqOffsetThreshold = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset HZ", "error");

                freqOffsetHz = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset Result", "error");

                freqOffsetResult = byteToInteger(integer4ByteArr, 4);

                /*evm*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pss evm", "error");

                pssEvm = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset sss evm", "error");

                sssEvm = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pbch evm", "error");

                pbchEvm = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pbch dmrs evm", "error");

                pbchDmrsEvm = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pdcch evm", "error");

                pdcchEvm = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pdcch dmrs evm", "error");

                pdcchDmrsEvm = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pdsch evm", "error");

                pdschEvm = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pdsch dmrs evm", "error");

                pdschDmrsEvm = byteToInteger(integer4ByteArr, 4);


                /*power*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pss power", "error");

                pssPower = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset sss power", "error");

                sssPower = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pbch power", "error");

                pbchPower = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pbch dmrs power", "error");

                pbchDmrsPower = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pbcch power", "error");

                pdcchPower = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pbcch dmrs power", "error");

                pdcchDmrsPower = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pdsch power", "error");

                pdschPower = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pdsch dmrs power", "error");

                pdschDmrsPower = byteToInteger(integer4ByteArr, 4);


                /*mode type*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pss mod", "error");

                pssModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset sss mod", "error");

                sssModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pbch mod", "error");

                pbchModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pbch dmrs mod", "error");

                pbchDmrsModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pdcch mod", "error");

                pdcchModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pdcch dmrs mod", "error");

                pdcchDmrsModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pdsch mod", "error");

                pdschModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pdsch dmrs mod", "error");

                pdschDmrsModType = byteToInteger(integer4ByteArr, 4);


                /*RB*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pss RB", "error");

                pssNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset sss RB", "error");

                sssNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pbch RB", "error");

                pbchNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pbch dmrs RB", "error");

                pbchDmrsNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pbcch RB", "error");

                pdcchNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pbcch dmrs RB", "error");

                pdcchDmrsNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pdsch RB", "error");

                pdschNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset pdsch dnrs RB", "error");

                pdschDmrsNumRb = byteToInteger(integer4ByteArr, 4);


                /*timing offset*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Timing Offset", "error");
                timingOffset = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Time Alignment Error", "error");
                timeAlignmentError = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("TAE Pass Fail Result", "error");
                taePassFailResult = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("TAE meas count", "error");
                taeCurMeasCount = byteToInteger(integer4ByteArr, 4);


                //Average EVM   x 0.01 값을 GUI에 표시. 값이 -999999 이면 --.-- 으로 표시
                String strEvmAvg = "--.--";
                if (inputStream.read(integer4ByteArr) == -1) {
                    if (D) Log.d("Average EVM", "error");
                } else {
                    int evmAvg = byteToInteger(integer4ByteArr, 4);
                    if (evmAvg != -999999)
                        strEvmAvg = ((float) evmAvg / 100) + "";
                }
                dataHandler.getNrData().getInfoData().setInformationText(
                        binding.demodulationLayout.tvEvmAvg, strEvmAvg
                );

                int evmResult = -1;
                if (inputStream.read(integer4ByteArr) == -1) {
                    if (D) Log.d("Average EVM Result", "error");
                } else {
                    evmResult = byteToInteger(integer4ByteArr, 4);
                }
                if (evmResult == 0) {
                    InitActivity.setInformationText(binding.demodulationLayout.tvEvmResult, "F", R.color.fail);
                } else if (evmResult == 1) {
                    InitActivity.setInformationText(binding.demodulationLayout.tvEvmResult, "P", R.color.pass);
                } else {
                    InitActivity.setInformationText(binding.demodulationLayout.tvEvmResult, "-", R.color.colorWhite);
                }


                /* effectivaly final */

                Integer finalPhysicalCellId = physicalCellId;
                Integer finalGroupId = groupId;
                Integer finalSectorId = sectorId;
                Integer finalSubcarrierSpacing = subcarrierSpacing;
                Integer finalSsbFreq = ssbFreq;


                Integer finalSsrsrp = ssrsrp;
                Integer finalSsrsrq = ssrsrq;
                Integer finalSsSinr = ssSinr;
                Integer finalBeamPower = beamPower;
                Integer finalBeamIndex = beamIndex;
                Integer finalExTxMaxPower = exTxMaxPower;
                Integer finalFreqOffsetThreshold = freqOffsetThreshold;
                Integer finalFreqOffsetHz = freqOffsetHz;
                Integer finalFreqOffsetResult = freqOffsetResult;
                Integer finalPssEvm = pssEvm;
                Integer finalSssEvm = sssEvm;
                Integer finalPbchEvm = pbchEvm;
                Integer finalPbchDmrsEvm = pbchDmrsEvm;
                Integer finalPdcchEvm = pdcchEvm;
                Integer finalPdcchDmrsEvm = pdcchDmrsEvm;
                Integer finalPdschEvm = pdschEvm;
                Integer finalPdschDmrsEvm = pdschDmrsEvm;
                Integer finalPssPower = pssPower;
                Integer finalSssPower = sssPower;
                Integer finalPbchPower = pbchPower;
                Integer finalPbchDmrsPower = pbchDmrsPower;
                Integer finalPdcchPower = pdcchPower;
                Integer finalPdcchDmrsPower = pdcchDmrsPower;
                Integer finalPdschPower = pdschPower;
                Integer finalPdschDmrsPower = pdschDmrsPower;
                Integer finalPssModeType = pssModType;
                Integer finalSssModeType = sssModType;
                Integer finalPbchModeType = pbchModType;
                Integer finalPbchDmrsModeType = pbchDmrsModType;
                Integer finalPdcchModeType = pdcchModType;
                Integer finalPdcchDmrsModeType = pdcchDmrsModType;
                Integer finalPdschModeType = pdschModType;
                Integer finalPdschDmrsModeType = pdschDmrsModType;
                Integer finalPssNumRb = pssNumRb;
                Integer finalSssNumRb = sssNumRb;
                Integer finalPbchNumRb = pbchNumRb;
                Integer finalPbchDmrsNumRb = pbchDmrsNumRb;
                Integer finalPdcchNumRb = pdcchNumRb;
                Integer finalPdcchDmrsNumRb = pdcchDmrsNumRb;
                Integer finalPdschNumRb = pdschNumRb;
                Integer finalPdschDmrsNumRb = pdschDmrsNumRb;

                Integer finalTimingOffset = timingOffset;
                Integer finalTimeAlignmentError = timeAlignmentError;
                Integer finalTaePassFailResult = taePassFailResult;
                Integer finalTaeCurMeasCount = taeCurMeasCount;

                mActivity.runOnUiThread(() -> {

                    dataHandler.getNrData().getInfoData().setPhysicalCellId(finalPhysicalCellId);
                    dataHandler.getNrData().getInfoData().setGroupId(finalGroupId);
                    dataHandler.getNrData().getInfoData().setSector(finalSectorId);
                    dataHandler.getNrData().getInfoData().setSubcarrierSpacing(finalSubcarrierSpacing);
                    dataHandler.getNrData().getInfoData().setSSBFreq((float) ((double) finalSsbFreq / 100));
                    dataHandler.getNrData().getInfoData().setSsRsrp((float) finalSsrsrp / 100f);
                    dataHandler.getNrData().getInfoData().setSsRsrq((float) finalSsrsrq / 100f);
                    dataHandler.getNrData().getInfoData().setSsSinr((float) finalSsSinr / 100f);
                    dataHandler.getNrData().getInfoData().setBeamPower((float) finalBeamPower / 100f, finalBeamIndex);
                    mFunctionHandler.getCandleChartFunc().selectChartData(finalBeamIndex);

                    dataHandler.getNrData().getInfoData().setExpectedTxMaxPower((float) finalExTxMaxPower / 100f);
                    dataHandler.getNrData().getInfoData().setThreshold((float) finalFreqOffsetThreshold / 100f);
                    dataHandler.getNrData().getInfoData().setHz((float) finalFreqOffsetHz / 100f);
                    dataHandler.getNrData().getInfoData().setOffsetResult(finalFreqOffsetResult);


                    /*start evm*/


                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvPssEvmValue, finalPssEvm / 100f
                    );

                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvSssEvmValue, finalSssEvm / 100f
                    );

                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvPbchEvmValue, finalPbchEvm / 100f
                    );

                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvPbchDmrsEvmValue, finalPbchDmrsEvm / 100f
                    );

//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdcchEvmValue, finalPdcchEvm / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdcchDmrsEvmValue, finalPdcchDmrsEvm / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdschEvmValue, finalPdschEvm / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdschDmrsEvmValue, finalPdschDmrsEvm / 100f
//                    );


                    /*end evm
                     * start power*/


                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvPssPwrValue, finalPssPower / 100f
                    );

                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvSssPwrValue, finalSssPower / 100f
                    );

                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvPbchPwrValue, finalPbchPower / 100f
                    );

                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvPbchDmrsPwrValue, finalPbchDmrsPower / 100f
                    );

//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdcchPwrValue, finalPdcchPower / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdcchDmrsPwrValue, finalPdcchDmrsPower / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdschPwrValue, finalPdschPower / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdschDmrsPwrValue, finalPdschDmrsPower / 100f
//                    );


                    /* end power
                     * start mode */

                    dataHandler.getNrData().getInfoData().setModeType(
                            binding.demodulationLayout.tvPssModeValue, finalPssModeType
                    );

                    dataHandler.getNrData().getInfoData().setModeType(
                            binding.demodulationLayout.tvSssModeValue, finalSssModeType
                    );

                    dataHandler.getNrData().getInfoData().setModeType(
                            binding.demodulationLayout.tvPbchModeValue, finalPbchModeType
                    );

                    dataHandler.getNrData().getInfoData().setModeType(
                            binding.demodulationLayout.tvPbchDmrsModeValue, finalPbchDmrsModeType
                    );

//                    dataHandler.getNrData().getInfoData().setModeType(
//                            binding.demodulationLayout.tvPdcchModeValue, finalPdcchModeType
//                    );
//
//                    dataHandler.getNrData().getInfoData().setModeType(
//                            binding.demodulationLayout.tvPdcchDmrsModeValue, finalPdcchDmrsModeType
//                    );
//
//                    dataHandler.getNrData().getInfoData().setModeType(
//                            binding.demodulationLayout.tvPdschModeValue, finalPdschModeType
//                    );
//
//                    dataHandler.getNrData().getInfoData().setModeType(
//                            binding.demodulationLayout.tvPdschDmrsModeValue, finalPdschDmrsModeType
//                    );

                    /*end mode type
                     * start RB */


                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvPssRbValue, finalPssNumRb + ""
                    );

                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvSssRbValue, finalSssNumRb + ""
                    );

                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvPbchRbValue, finalPbchNumRb + ""
                    );

                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvPbchDmrsRbValue, finalPbchDmrsNumRb + ""
                    );

//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdcchRbValue, finalPdcchNumRb, "-"
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdcchDmrsRbValue, finalPdcchDmrsNumRb, "-"
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdschRbValue, finalPdschNumRb, "-"
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdschDmrsRbValue, finalPdschDmrsNumRb, "-"
//                    );


                    /*end RB data
                     * start timing offset & time alignment error
                     * */

                    float timeOffset = finalTimingOffset / 100f;
                    String timeOffsetUnit = "";

                    if (Math.abs(timeOffset) < 1000f) {
                        timeOffsetUnit = " ns";

                    } else if (Math.abs(timeOffset) >= 1000f) {

                        timeOffset = timeOffset / 1000f;
                        timeOffsetUnit = " us";

                    } else if (Math.abs(timeOffset) > 1000000f) {
                        timeOffset = timeOffset / 1000000f;
                        timeOffsetUnit = " ms";
                    }

                    if (D)
                        Log.d("DataConnector", "time offset : " + timeOffset + " " + timeOffsetUnit);
                    if (D)
                        Log.d("DataConnector", "time alignment error : " + finalTimeAlignmentError + " " + timeOffsetUnit);
                    if (D)
                        Log.d("DataConnector", "time alignment error result : " + finalTaePassFailResult + " " + timeOffsetUnit);

                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvTimingOffsetValue, timeOffset, timeOffsetUnit
                    );

                    float taeValue = finalTimeAlignmentError / 100f;
                    String taeUnit = "";

                    if (Math.abs(taeValue) < 1000f) {
                        taeUnit = "Result [ns]";

                    } else if (Math.abs(taeValue) >= 1000f) {

                        taeValue = taeValue / 1000f;
                        taeUnit = "Result [us]";

                    } else if (Math.abs(taeValue) >= 1000000) {
                        taeValue = taeValue / 1000000f;
                        taeUnit = "Result [ms]";
                    }

                    dataHandler.getNrData().getInfoData().setInformationText(
                            binding.demodulationLayout.tvTaeUnit, taeUnit
                    );

                    if (finalTimeAlignmentError == -999999)
                        dataHandler.getNrData().getInfoData().setInformationText(
                                binding.demodulationLayout.tvTimeAlignmentErrorValue, "--.--"
                        );
                    else
                        dataHandler.getNrData().getInfoData().setInformationText(
                                binding.demodulationLayout.tvTimeAlignmentErrorValue, taeValue + ""
                        );

                    if (D) Log.d("DataConnector", "TaeValue : " + taeValue);

                    AutofitTextView taePassFailResultView = binding.demodulationLayout.tvTimeAlignmentErrorResult;

                    if (finalTaePassFailResult == 0) {
                        InitActivity.setInformationText(
                                taePassFailResultView, "F", R.color.fail
                        );
                    } else if (finalTaePassFailResult == 1) {
                        InitActivity.setInformationText(
                                taePassFailResultView, "P", R.color.pass
                        );
                    } else {
                        InitActivity.setInformationText(
                                taePassFailResultView, "-", R.color.colorWhite
                        );
                    }

                    NrData nrData = dataHandler.getNrData();
                    NrInterTaeData interTaeData = nrData.getInterTaeData();

                    if (D) Log.d("DataConnector", "MeasCount : " + finalTaeCurMeasCount);

                    if (taeData.getTeaMeasMode() == ON) {

                        if (finalTaeCurMeasCount >= 1 && finalTaeCurMeasCount < dataHandler.getNrData().getTaeInfoData().getMeasCount()) {
                            dismissProgressDialog();
                            taeData.setCurrentMeasCount(finalTaeCurMeasCount);
                            if (taeData.getTaeType() == NrTaeData.TAE_TYPE.INTRA) {
                                binding.tvTopCenterTitle.setText("Measuring RF port " + taeData.getCurrentPortIdx() + " timing... (" + taeData.getCurrentMeasCount() + "/" + taeData.getMeasCount() + ")");
                            } else {
                                binding.tvTopCenterTitle.setText("Measuring Carrier " + (interTaeData.getCurrentCarrierIdx() + 1) + " timing... (" + taeData.getCurrentMeasCount() + "/" + taeData.getMeasCount() + ")");
                            }

                            if (taeData.getScreenshotMode() == NrTaeData.SCREENSHOT_MODE.ON) {
                                String timeStamp = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss").format(new Date());
                                String fileName = type + timeStamp;
                                Screenshot.getInstance().capture(binding.linParent.getRootView(), fileName);
                            }

                            if (D)
                                Log.d("DataConnector", " measuring ... " + taeData.getCurrentMeasCount());
                            //original source
//                            sendCommand(dataHandler.getRequestMainDataCmd(), 2);
                            //original source

                            //Qos fix
                            sendCommand(dataHandler.getRequestMainDataCmd(), mQoS);
                            //Qos fix

                        } else if (taeData.getTaeType() == NrTaeData.TAE_TYPE.INTRA && finalTaeCurMeasCount == dataHandler.getNrData().getTaeInfoData().getMeasCount()) {

                            taeData.setCurrentMeasCount(finalTaeCurMeasCount);
                            binding.tvTopCenterTitle.setText("RF port " + taeData.getCurrentPortIdx() + " timing measurement is completed. (" + taeData.getCurrentMeasCount() + "/" + taeData.getMeasCount() + ")");

                            taeData.setRunning(false);
                            if (taeData.getCurrentPortIdx() >= taeData.getNumOfTxAnt().getValue()) {
                                mFunctionHandler.getTaeFunc().completeTaeMeasureDialog();
//                                taeData.setTeaMeasMode(NrTaeData.TAE_MEAS_MODE.OFF);
//                                sendCommand(dataHandler.getNrData().getNrCmd());

                            } else {
                                taeData.setCurrentPortIdx(taeData.getCurrentPortIdx() + 1);
                                mFunctionHandler.getTaeFunc().TaeMeasureDialog(taeData.getCurrentPortIdx());
                            }

                            if (taeData.getScreenshotMode() == NrTaeData.SCREENSHOT_MODE.ON) {
                                String timeStamp = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss").format(new Date());
                                String fileName = type + timeStamp;
                                Screenshot.getInstance().capture(binding.linParent.getRootView(), fileName);
                            }

                        } else if (taeData.getTaeType() == NrTaeData.TAE_TYPE.INTER && finalTaeCurMeasCount == dataHandler.getNrData().getTaeInfoData().getMeasCount()) {

                            taeData.setCurrentMeasCount(finalTaeCurMeasCount);
                            binding.tvTopCenterTitle.setText("Carrier " + (interTaeData.getCurrentCarrierIdx() + 1) + " timing measurement is completed. (" + taeData.getCurrentMeasCount() + "/" + taeData.getMeasCount() + ")");
                            taeData.setRunning(false);

                            if (interTaeData.getCurrentCarrierIdx() == interTaeData.getEndIdxCarrierOn()) {
                                mFunctionHandler.getTaeFunc().completeTaeMeasureDialog();
//                                sendCommand(dataHandler.getNrData().getNrCmd());
                            } else {
                                interTaeData.moveIdx();
                                mFunctionHandler.getTaeFunc().TaeMeasureDialog(interTaeData.getCurrentCarrierIdx() + 1);
                            }

                            if (taeData.getScreenshotMode() == NrTaeData.SCREENSHOT_MODE.ON) {
                                String timeStamp = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss").format(new Date());
                                String fileName = type + timeStamp;
                                Screenshot.getInstance().capture(binding.linParent.getRootView(), fileName);
                            }

                        }

                    }

                    if (D) Log.d("addChartValue", "Demoulation data complete");


                });

            } catch (IOException e) {
                e.printStackTrace();
            }

            //todo: add request data code

        }
        // LTE FDD
        else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && type == MeasureType.MEASURE_TYPE.LTE_FDD
                && modeValue == MeasureMode.MEASURE_MODE.MOD_ACCURACY.getValue() && typeValue == MeasureType.MEASURE_TYPE.LTE_FDD.getValue()) {

            if (D) Log.d("DataConnector", "LTE FDD");
            mFunctionHandler.getMainLineChart().setEnabledLimitMsg(false);

            for (int i = 0; i < mFunctionHandler.getScatterChart2().getDataSetList().length; i++) {

                try {
                    if (inputStream.read(integer4ByteArr) == -1)
                        if (D) Log.d("I/Q Constellation", "data size error");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int dataSize = byteToInteger(integer4ByteArr, 4);
                mFunctionHandler.getScatterChart2().getDataList(i).clear();

                Float[] x = new Float[dataSize];
                Float[] y = new Float[dataSize];

                for (int k = 0; k < dataSize; k++) {

                    try {

                        if (inputStream.read(integer4ByteArr) == -1)
                            if (D) Log.d("I/Q Constellation", "x value error(" + k + ")");
                        x[k] = (float) byteToInteger(integer4ByteArr, 4) / 10000f;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                for (int k = 0; k < dataSize; k++) {

                    try {

                        if (inputStream.read(integer4ByteArr) == -1)
                            if (D) Log.d("I/Q Constellation", "y value error(" + k + ")");
                        y[k] = (float) byteToInteger(integer4ByteArr, 4) / 10000f;

                        //add scatter data
                        mFunctionHandler.getScatterChart2().getDataList(i).add(new Entry(x[k], y[k]));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            mFunctionHandler.getScatterChart2().updateEntry();

            /* ============================== end scatter data (I/Q Constellation) ===========================*/


            /*
             * receive cell information
             */

            Integer physicalCellId = null;
            Integer groupId = null;
            Integer sectorId = null;

            /* end cell information
             *  start power information*/

            Float rsrp0 = null;
            Float rsrp1 = null;
            Float rsrp2 = null;
            Float rsrp3 = null;
            Float rsrq = null;
            Float expectedTxMaxPower = null;

            /* end power information
             *  start signal quality*/

            Float freqOffsetThreshold = null;
            Float freqOffsetHz = null;
            Integer freqOffsetResult = null;

            Float pssEvm = null;
            Float sssEvm = null;
            Float pbchEvm = null;
            Float pcfichEvm = null;
            Float phichEvm = null;
            Float pdcchEvm = null;
            Float crsEvm = null;
            Float pdschQpskEvm = null;
            Float pdsch16QamEvm = null;
            Float pdsch64QamEvm = null;
            Float pdsch256QamEvm = null;
            Integer crsResult = null;

            Float pssPower = null;
            Float sssPower = null;
            Float pbchPower = null;
            Float pcfichPower = null;
            Float phichPower = null;
            Float pdcchPower = null;
            Float crsPower = null;
            Float pdschQpskPower = null;
            Float pdsch16QamPower = null;
            Float pdsch64QamPower = null;
            Float pdsch256QamPower = null;

            Integer pssModType = null;
            Integer sssModType = null;
            Integer pbchModType = null;
            Integer pcfichModType = null;
            Integer phichModType = null;
            Integer pdcchModType = null;
            Integer crsModType = null;
            Integer pdschQpskModType = null;
            Integer pdsch16QamModType = null;
            Integer pdsch64QamModType = null;
            Integer pdsch256QamModType = null;

            Integer pssNumRb = null;
            Integer sssNumRb = null;
            Integer pbchNumRb = null;
            Integer pcfichNumRb = null;
            Integer phichNumRb = null;
            Integer pdcchNumRb = null;
            Integer crsNumRb = null;
            Integer pdschQpskNumRb = null;
            Integer pdsch16QamNumRb = null;
            Integer pdsch64QamNumRb = null;
            Integer pdsch256QamNumRb = null;

            Float timingOffset = null;
            Float timeAlignmentError = null;
            Integer timeAlignmentErrorResult = null;
            Integer antennaIndex = null;

            try {

                /*cell information*/
                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("PhysicalCellId", "error");
                physicalCellId = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("GroupID", "error");
                groupId = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("SectorID", "error");
                sectorId = byteToInteger(integer4ByteArr, 4);



                /*power information*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("rsrp0", "error");
                rsrp0 = (float) byteToInteger(integer4ByteArr, 4);// / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("rsrp1", "error");
                rsrp1 = (float) byteToInteger(integer4ByteArr, 4);// / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("rsrp2", "error");
                rsrp2 = (float) byteToInteger(integer4ByteArr, 4);// / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("rsrp3", "error");
                rsrp3 = (float) byteToInteger(integer4ByteArr, 4);// / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("rsrq", "error");
                rsrq = (float) byteToInteger(integer4ByteArr, 4);// / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("expected tx max power", "error");
                expectedTxMaxPower = (float) byteToInteger(integer4ByteArr, 4);// / 100f;



                /*Signal Quality chart*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Freq Offset hold", "error");
                freqOffsetThreshold = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1) if (D) Log.d("HZ", "error");
                freqOffsetHz = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Result", "error");
                freqOffsetResult = byteToInteger(integer4ByteArr, 4);

                /*evm*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pss evm", "error");
                pssEvm = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("sss evm", "error");
                sssEvm = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pbch evm", "error");
                pbchEvm = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pbch dmrs evm", "error");
                pcfichEvm = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdcch evm", "error");
                phichEvm = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdcch dmrs evm", "error");
                pdcchEvm = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("crs evm", "error");
                crsEvm = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch qpsk evm", "error");
                pdschQpskEvm = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch 16QAM evm", "error");
                pdsch16QamEvm = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch 64QAM evm", "error");
                pdsch64QamEvm = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch 256QAM evm", "error");
                pdsch256QamEvm = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch 256QAM evm", "error");
                crsResult = byteToInteger(integer4ByteArr, 4);

                /*power*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pss power", "error");
                pssPower = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("sss power", "error");
                sssPower = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pbch power", "error");
                pbchPower = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pcfich power", "error");
                pcfichPower = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("phich power", "error");
                phichPower = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdcch power", "error");
                pdcchPower = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("crs power", "error");
                crsPower = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch qpsk power", "error");
                pdschQpskPower = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch 16QAM Power", "error");
                pdsch16QamPower = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch 64QAM Power", "error");
                pdsch64QamPower = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch 256QAM Power", "error");
                pdsch256QamPower = (float) byteToInteger(integer4ByteArr, 4) / 100f;


                /*mode type*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pss mod", "error");
                pssModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("sss mod", "error");
                sssModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pbch mod", "error");
                pbchModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pcfich mod", "error");
                pcfichModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("phich mod", "error");
                phichModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdcch dmrs mod", "error");
                pdcchModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("crs mod", "error");
                crsModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch qpsk mod", "error");
                pdschQpskModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch 16qam mod", "error");
                pdsch16QamModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch 64qam mod", "error");
                pdsch64QamModType = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch 256qam mod", "error");
                pdsch256QamModType = byteToInteger(integer4ByteArr, 4);


                /*RB*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pss RB", "error");
                pssNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("sss RB", "error");
                sssNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pbch RB", "error");
                pbchNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pcfich RB", "error");
                pcfichNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("phich RB", "error");
                phichNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdcch RB", "error");
                pdcchNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("crs RB", "error");
                crsNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch qpsk RB", "error");
                pdschQpskNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch 16qam RB", "error");
                pdsch16QamNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch 64qam RB", "error");
                pdsch64QamNumRb = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("pdsch 256qam RB", "error");
                pdsch256QamNumRb = byteToInteger(integer4ByteArr, 4);


                /*timing offset*/

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Timing Offset", "error");
                timingOffset = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                String timingOffsetUnit = "";

                if (Math.abs(timingOffset) < 1000f) {
                    timingOffsetUnit = " ns";

                } else if (Math.abs(timingOffset) >= 1000f) {

                    timingOffset = timingOffset / 1000f;
                    timingOffsetUnit = " us";

                } else if (Math.abs(timingOffset) > 1000000f) {
                    timingOffset = timingOffset / 1000000f;
                    timingOffsetUnit = " ms";
                }

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Time Alignment Error", "error");
                timeAlignmentError = (float) byteToInteger(integer4ByteArr, 4) / 100f;

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Time Alignment Error Result", "error");
                timeAlignmentErrorResult = byteToInteger(integer4ByteArr, 4);

                if (inputStream.read(integer4ByteArr) == -1)
                    if (D) Log.d("Antenna Index", "error");
                antennaIndex = byteToInteger(integer4ByteArr, 4);


                InitActivity.setInformationText(binding.lteFddLayout.tvLtePhysicalCellIdValue, physicalCellId + "");
                InitActivity.setInformationText(binding.lteFddLayout.tvGroupIdValue, groupId + "");
                InitActivity.setInformationText(binding.lteFddLayout.tvSectorValue, sectorId + "");

                InitActivity.setInformationText(binding.lteFddLayout.tvRsrpRsValue0, rsrp0, 100, " dBm");
                InitActivity.setInformationText(binding.lteFddLayout.tvRsrpRsValue1, rsrp1, 100, " dBm");
                InitActivity.setInformationText(binding.lteFddLayout.tvRsrpRsValue2, rsrp2, 100, " dBm");
                InitActivity.setInformationText(binding.lteFddLayout.tvRsrpRsValue3, rsrp3, 100, " dBm");
                InitActivity.setInformationText(binding.lteFddLayout.tvRsrqValue, rsrq, 100, " dB");
                InitActivity.setInformationText(binding.lteFddLayout.tvExpectedTxMaxPowerValue, expectedTxMaxPower, 100, " dBm");

                InitActivity.setInformationText(binding.lteFddLayout.tvThresholdValue, freqOffsetThreshold);
                InitActivity.setInformationText(binding.lteFddLayout.tvHzValue, freqOffsetHz);

                if (freqOffsetResult == 0)
                    InitActivity.setInformationText(binding.lteFddLayout.tvFreqOffsetResult, "F", R.color.fail);
                else
                    InitActivity.setInformationText(binding.lteFddLayout.tvFreqOffsetResult, "P", R.color.pass);


                InitActivity.setInformationText(binding.lteFddLayout.tvPssEvmValue, pssEvm);
                InitActivity.setInformationText(binding.lteFddLayout.tvSssEvmValue, sssEvm);
                InitActivity.setInformationText(binding.lteFddLayout.tvPbchEvmValue, pbchEvm);
                InitActivity.setInformationText(binding.lteFddLayout.tvPcfichEvmValue, pcfichEvm);
                InitActivity.setInformationText(binding.lteFddLayout.tvPhichEvmValue, phichEvm);
                InitActivity.setInformationText(binding.lteFddLayout.tvPdcchEvmValue, pdcchEvm);
                InitActivity.setInformationText(binding.lteFddLayout.tvCrsEvmValue, crsEvm);
                InitActivity.setInformationText(binding.lteFddLayout.tvPdschQpskEvmValue, pdschQpskEvm);
                InitActivity.setInformationText(binding.lteFddLayout.tvPdsch16QamEvmValue, pdsch16QamEvm);
                InitActivity.setInformationText(binding.lteFddLayout.tvPdsch64QamEvmValue, pdsch64QamEvm);
                InitActivity.setInformationText(binding.lteFddLayout.tvPdsch256QamEvmValue, pdsch256QamEvm);

                if (crsResult == 0)
                    InitActivity.setInformationText(binding.lteFddLayout.tvCrsPassFail, "F", R.color.fail);
                else
                    InitActivity.setInformationText(binding.lteFddLayout.tvCrsPassFail, "P", R.color.pass);


                InitActivity.setInformationText(binding.lteFddLayout.tvPssPwrValue, pssPower);
                InitActivity.setInformationText(binding.lteFddLayout.tvSssPwrValue, sssPower);
                InitActivity.setInformationText(binding.lteFddLayout.tvPbchPwrValue, pbchPower);
                InitActivity.setInformationText(binding.lteFddLayout.tvPcfichPwrValue, pcfichPower);
                InitActivity.setInformationText(binding.lteFddLayout.tvPhichPwrValue, phichPower);
                InitActivity.setInformationText(binding.lteFddLayout.tvPdcchPwrValue, pdcchPower);
                InitActivity.setInformationText(binding.lteFddLayout.tvCrsPwrValue, crsPower);
                InitActivity.setInformationText(binding.lteFddLayout.tvPdschQpskPwrValue, pdschQpskPower);
                InitActivity.setInformationText(binding.lteFddLayout.tvPdsch16QamPwrValue, pdsch16QamPower);
                InitActivity.setInformationText(binding.lteFddLayout.tvPdsch64QamPwrValue, pdsch64QamPower);
                InitActivity.setInformationText(binding.lteFddLayout.tvPdsch256QamPwrValue, pdsch256QamPower);

                InitActivity.setInformationText(binding.lteFddLayout.tvPssModValue, dataHandler.getLteFddData().getModType(pssModType));
                InitActivity.setInformationText(binding.lteFddLayout.tvSssModValue, dataHandler.getLteFddData().getModType(sssModType));
                InitActivity.setInformationText(binding.lteFddLayout.tvPbchModValue, dataHandler.getLteFddData().getModType(pbchModType));
                InitActivity.setInformationText(binding.lteFddLayout.tvPcfichModValue, dataHandler.getLteFddData().getModType(pcfichModType));
                InitActivity.setInformationText(binding.lteFddLayout.tvPhichModValue, dataHandler.getLteFddData().getModType(phichModType));
                InitActivity.setInformationText(binding.lteFddLayout.tvPdcchModValue, dataHandler.getLteFddData().getModType(pdcchModType));
                InitActivity.setInformationText(binding.lteFddLayout.tvCrsModValue, dataHandler.getLteFddData().getModType(crsModType));
                InitActivity.setInformationText(binding.lteFddLayout.tvPdschQpskModValue, dataHandler.getLteFddData().getModType(pdschQpskModType));
                InitActivity.setInformationText(binding.lteFddLayout.tvPdsch16QamModValue, dataHandler.getLteFddData().getModType(pdsch16QamModType));
                InitActivity.setInformationText(binding.lteFddLayout.tvPdsch64QamModValue, dataHandler.getLteFddData().getModType(pdsch64QamModType));
                InitActivity.setInformationText(binding.lteFddLayout.tvPdsch256QamModValue, dataHandler.getLteFddData().getModType(pdsch256QamModType));

                InitActivity.setInformationText(binding.lteFddLayout.tvPssRbValue, pssNumRb + "");
                InitActivity.setInformationText(binding.lteFddLayout.tvSssRbValue, sssNumRb + "");
                InitActivity.setInformationText(binding.lteFddLayout.tvPbchRbValue, pbchNumRb + "");
                InitActivity.setInformationText(binding.lteFddLayout.tvPcfichRbValue, pcfichNumRb + "");
                InitActivity.setInformationText(binding.lteFddLayout.tvPhichRbValue, phichNumRb + "");
                InitActivity.setInformationText(binding.lteFddLayout.tvPdcchRbValue, pdcchNumRb + "");
                InitActivity.setInformationText(binding.lteFddLayout.tvCrsRbValue, crsNumRb + "");
                InitActivity.setInformationText(binding.lteFddLayout.tvPdschQpskRbValue, pdschQpskNumRb + "");
                InitActivity.setInformationText(binding.lteFddLayout.tvPdsch16QamRbValue, pdsch16QamNumRb + "");
                InitActivity.setInformationText(binding.lteFddLayout.tvPdsch64QamRbValue, pdsch64QamNumRb + "");
                InitActivity.setInformationText(binding.lteFddLayout.tvPdsch256QamRbValue, pdsch256QamNumRb + "");

                InitActivity.setInformationText(binding.lteFddLayout.timingOffsetValue, timingOffset, timingOffsetUnit);
                InitActivity.setInformationText(binding.lteFddLayout.timeAlignmentErrorValue, timeAlignmentError);

                if (timeAlignmentErrorResult == 0)
                    InitActivity.setInformationText(binding.lteFddLayout.timeAlignmentErrorResult, "F", R.color.fail);
                else
                    InitActivity.setInformationText(binding.lteFddLayout.timeAlignmentErrorResult, "P", R.color.pass);

                switch (antennaIndex) {
                    case 0:
                        mMainHandler.post(() -> {

                            binding.lteFddLayout.ivAntenna0.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_green));
                            binding.lteFddLayout.ivAntenna1.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));
                            binding.lteFddLayout.ivAntenna2.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));
                            binding.lteFddLayout.ivAntenna3.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));


                        });
                        break;

                    case 1:
                        mMainHandler.post(() -> {

                            binding.lteFddLayout.ivAntenna0.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));
                            binding.lteFddLayout.ivAntenna1.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_green));
                            binding.lteFddLayout.ivAntenna2.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));
                            binding.lteFddLayout.ivAntenna3.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));

                        });
                        break;

                    case 2:
                        mMainHandler.post(() -> {

                            binding.lteFddLayout.ivAntenna0.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));
                            binding.lteFddLayout.ivAntenna1.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));
                            binding.lteFddLayout.ivAntenna2.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_green));
                            binding.lteFddLayout.ivAntenna3.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));

                        });
                        break;

                    case 3:
                        mMainHandler.post(() -> {

                            binding.lteFddLayout.ivAntenna0.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));
                            binding.lteFddLayout.ivAntenna1.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));
                            binding.lteFddLayout.ivAntenna2.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));
                            binding.lteFddLayout.ivAntenna3.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_green));

                        });
                        break;

                    case 4:
                        mMainHandler.post(() -> {

                            binding.lteFddLayout.ivAntenna0.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_green));
                            binding.lteFddLayout.ivAntenna1.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_green));
                            binding.lteFddLayout.ivAntenna2.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));
                            binding.lteFddLayout.ivAntenna3.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_white));

                        });
                        break;

                    case 5:
                        mMainHandler.post(() -> {

                            binding.lteFddLayout.ivAntenna0.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_green));
                            binding.lteFddLayout.ivAntenna1.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_green));
                            binding.lteFddLayout.ivAntenna2.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_green));
                            binding.lteFddLayout.ivAntenna3.setImageDrawable(mActivity.getDrawable(R.drawable.lte_antenna_green));

                        });
                        break;
                }


//                    dataHandler.getNrData().getInfoData().setPhysicalCellId(finalPhysicalCellId);
//                    dataHandler.getNrData().getInfoData().setGroupId(finalGroupId);
//                    dataHandler.getNrData().getInfoData().setSector(finalSectorId);
//                    dataHandler.getNrData().getInfoData().setSubcarrierSpacing(finalSubcarrierSpacing);
//                    dataHandler.getNrData().getInfoData().setSSBFreq((float) ((double) finalSsbFreq / 100));
//
//                    dataHandler.getNrData().getInfoData().setSsRsrp((float) finalSsrsrp / 100f);
//                    dataHandler.getNrData().getInfoData().setSsRsrq((float) finalSsrsrq / 100f);
//                    dataHandler.getNrData().getInfoData().setBeamPower((float) finalBeamPower / 100f, finalBeamIndex);
//                    mFunctionHandler.getCandleChartFunc().selectChartData(finalBeamIndex);
//
//                    dataHandler.getNrData().getInfoData().setExpectedTxMaxPower((float) finalExTxMaxPower / 100f);
//
//                    dataHandler.getNrData().getInfoData().setThreshold((float) finalFreqOffsetThreshold / 100f);
//                    dataHandler.getNrData().getInfoData().setHz((float) finalFreqOffsetHz / 100f);
//                    dataHandler.getNrData().getInfoData().setOffsetResult(finalFreqOffsetResult);
//
//
//                    /*start evm*/
//
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPssEvmValue, finalPssEvm / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvSssEvmValue, finalSssEvm / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPbchEvmValue, finalPbchEvm / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPbchDmrsEvmValue, finalPbchDmrsEvm / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdcchEvmValue, finalPdcchEvm / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdcchDmrsEvmValue, finalPdcchDmrsEvm / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdschEvmValue, finalPdschEvm / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdschDmrsEvmValue, finalPdschDmrsEvm / 100f
//                    );
//
//
//                    /*end evm
//                     * start power*/
//
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPssPwrValue, finalPssPower / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvSssPwrValue, finalSssPower / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPbchPwrValue, finalPbchPower / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPbchDmrsPwrValue, finalPbchDmrsPower / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdcchPwrValue, finalPdcchPower / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdcchDmrsPwrValue, finalPdcchDmrsPower / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdschPwrValue, finalPdschPower / 100f
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdschDmrsPwrValue, finalPdschDmrsPower / 100f
//                    );
//
//
//                    /* end power
//                     * start mode */
//
//                    dataHandler.getNrData().getInfoData().setModeType(
//                            binding.demodulationLayout.tvPssModeValue, finalPssModeType
//                    );
//
//                    dataHandler.getNrData().getInfoData().setModeType(
//                            binding.demodulationLayout.tvSssModeValue, finalSssModeType
//                    );
//
//                    dataHandler.getNrData().getInfoData().setModeType(
//                            binding.demodulationLayout.tvPbchModeValue, finalPbchModeType
//                    );
//
//                    dataHandler.getNrData().getInfoData().setModeType(
//                            binding.demodulationLayout.tvPbchDmrsModeValue, finalPbchDmrsModeType
//                    );
//
//                    dataHandler.getNrData().getInfoData().setModeType(
//                            binding.demodulationLayout.tvPdcchModeValue, finalPdcchModeType
//                    );
//
//                    dataHandler.getNrData().getInfoData().setModeType(
//                            binding.demodulationLayout.tvPdcchDmrsModeValue, finalPdcchDmrsModeType
//                    );
//
//                    dataHandler.getNrData().getInfoData().setModeType(
//                            binding.demodulationLayout.tvPdschModeValue, finalPdschModeType
//                    );
//
//                    dataHandler.getNrData().getInfoData().setModeType(
//                            binding.demodulationLayout.tvPdschDmrsModeValue, finalPdschDmrsModeType
//                    );
//
//                    /*end mode type
//                     * start RB */
//
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPssRbValue, finalPssNumRb, "-"
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvSssRbValue, finalSssNumRb, "-"
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPbchRbValue, finalPbchNumRb, "-"
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPbchDmrsRbValue, finalPbchDmrsNumRb, "-"
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdcchRbValue, finalPdcchNumRb, "-"
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdcchDmrsRbValue, finalPdcchDmrsNumRb, "-"
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdschRbValue, finalPdschNumRb, "-"
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvPdschDmrsRbValue, finalPdschDmrsNumRb, "-"
//                    );
//
//
//                    /*end RB data
//                     * start timing offset & time alignment error
//                     * */
//
//                    float timeOffset;
//                    String unit = "";
//
//                    if (finalTimingOffset / 100f >= 1000f && finalTimingOffset / 100f < 1000000f) {
//                        timeOffset = finalTimingOffset / 1000f / 100f;
//                        unit = " us";
//                    } else if (finalTimingOffset / 100f >= 1000000) {
//
//                        timeOffset = finalTimingOffset / 1000000f / 100f;
//                        unit = " ms";
//
//                    } else {
//                        timeOffset = finalTimingOffset / 100f;
//                        unit = "";
//                    }
//
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvTimingOffsetValue, timeOffset, unit
//                    );
//
//                    dataHandler.getNrData().getInfoData().setInformationText(
//                            binding.demodulationLayout.tvTimeAlignmentErrorValue, finalTimeAlignmentError / 100f
//                    );

                if (D) Log.d("addChartValue", "Demoulation data complete");


            } catch (IOException e) {
                e.printStackTrace();
            }

            //todo: add request data code

        }
        // VSWR / DTF / CL
        else if ((mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL)
                && (modeValue == MeasureMode.MEASURE_MODE.VSWR.getValue()
                || modeValue == MeasureMode.MEASURE_MODE.DTF.getValue()
                || modeValue == MeasureMode.MEASURE_MODE.CL.getValue())) {

            try {

                VswrConfigData configData;
                if (modeValue == MeasureMode.MEASURE_MODE.VSWR.getValue())
                    configData = vswrDataHandler.getClConfigData();
                else if (modeValue == MeasureMode.MEASURE_MODE.DTF.getValue())
                    configData = vswrDataHandler.getDtfConfigData();
                else
                    configData = vswrDataHandler.getClConfigData();

                int count = configData.getSweepData().getDataPoint().getDataPoint();

                ArrayList<Integer> dataList = mFunctionHandler.getMainLineChart().getDataList(0);
                dataList.clear();

                for (int j = 0; j < count; j++) {
                    int data = bb.getInt();

                    switch (type) {
                        case VSWR:
                        case DTF_VSWR:
                            if (data > VswrAmplitudeData.DEFALUT_VSWR_Y_MAX * 1000)
                                data = (int) (VswrAmplitudeData.DEFALUT_VSWR_Y_MAX * 1000);
                            if (data < VswrAmplitudeData.DEFALUT_VSWR_Y_MIN * 1000)
                                data = (int) (VswrAmplitudeData.DEFALUT_VSWR_Y_MIN * 1000);
                            break;

                        case RL:
                        case DTF_RL:
                            if (data > VswrAmplitudeData.DEFALUT_RL_Y_MAX * 1000)
                                data = (int) (VswrAmplitudeData.DEFALUT_RL_Y_MAX * 1000);
                            if (data < VswrAmplitudeData.DEFALUT_RL_Y_MIN * 1000)
                                data = (int) (VswrAmplitudeData.DEFALUT_RL_Y_MIN * 1000);
                            break;
                    }

                    dataList.add(data);
                }

                if (modeValue == MeasureMode.MEASURE_MODE.CL.getValue()) {
                    final int finalData = bb.getInt();
                    mActivity.runOnUiThread(() -> {
                        if (mode == MeasureMode.MEASURE_MODE.CL) {
                            if (binding.lineChartLayout.tvChartInfo.getVisibility() != View.VISIBLE)
                                binding.lineChartLayout.tvChartInfo.setVisibility(View.VISIBLE);
                            float avg = (float) finalData / 1000f;
                            mFunctionHandler.getMainLineChart().setAverage(avg);
                            if (D) Log.d("CableLoss", "avg : " + avg);
                            binding.lineChartLayout.tvChartInfo.setText("Cable Loss : " + avg + " dB Avg");
                        } else {
                            binding.lineChartLayout.tvChartInfo.setText("");
                        }
                    });
                }

                //mActivity.runOnUiThread(() -> {
                binding.lineChartLayout.mainLineChart.getData().clearValuesNotChanging(0);
                binding.lineChartLayout.mainLineChart.getData().clearValuesNotChanging(mFunctionHandler.getMainLineChart().LIMIT_DATASET_INDEX);
                mFunctionHandler.getMainLineChart().addEntry(dataList, 0, mode, type, null);
                // chart 데이터 모두 추가 후 갱신
                mFunctionHandler.getMainLineChart().addEntryUpdate();

                drawCount++;
                //});

                if (!vswrDataHandler.getConfigData().getSweepData().isContinuous()) {
                    vswrDataHandler.getConfigData().getSweepData().setRun(false);
                    ViewHandler.getInstance().getSweepView().update();
                }

                if (!isAutoScale) {
                    if (D) Log.d("AutoScale", "in");
                    mFunctionHandler.getMainLineChart().autoScale();
                    isAutoScale = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                isReceivingData = false;
            }
        }
        // Gate
        else if (modeValue == dataHandler.getGateDataCmdValue()
                && type != MeasureType.MEASURE_TYPE.TRANSMIT_MASK
                && typeValue == type.getValue()) {

            if (D) Log.d("DataConnector", "in Gate");

            try {
                //TODO 차트 그리는 중 데이터가 수신되면 pass
                if (binding.lineChartLayout.gateLayout.gateLineChart.isDirty()) {
                    //if (D)
                    Log.e("addChartValue", "is drawing GateChart = true");
                    isReceivingData = false;
                    return;
                }

                int dataPoints;
                if (type == MeasureType.MEASURE_TYPE.SEM)
                    dataPoints = 1001;
                else
                    dataPoints = saDataHandler.getConfigData().getTraceData().getDetector(0).getDataPoints();

//            binding.lineChartLayout.gateLineChart.clearValues();
                //binding.lineChartLayout.gateLayout.gateLineChart.getLineData().clearValuesNotChanging(0);// .getDataSets().get(0).clear();
//            int size = binding.lineChartLayout.gateLineChart.getLineData().getDataSets().size();
//            if (D) Log.d("DataConnector", "Gate Chart Data size : " + size);

                ArrayList<Float> dataList = mFunctionHandler.getGateLineChart().getDataList();
                dataList.clear();

                for (int j = 0; j < dataPoints; j++) {
                    float data = (float) ((double) bb.getInt() / 100);
                    dataList.add(data);
                }

                inputStream.skip(dataPoints * 4);

                mFunctionHandler.getGateLineChart().addEntry();

            } catch (Exception e) {
                e.printStackTrace();
                isReceivingData = false;
            }
        }
        // 5G NR SCAN 데이터 수신
        else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && type == MeasureType.MEASURE_TYPE.NR_5G_SCAN
                && modeValue == MeasureMode.MEASURE_MODE.MOD_ACCURACY.getValue() && typeValue == MeasureType.MEASURE_TYPE.NR_5G_SCAN.getValue()) {
            //Log.d("KDK-", "5G NR SCAN 데이터 수신 size = " + payloadLength);

            // 수신 데이터 크기 체크
            if (payloadLength < NrScanRcvItem.NR_SCAN_RCV_PACKET_SIZE) {
                Log.e("KDK-", "5G NR SCAN data payloadLength < 36");
            } else {
                //ByteBuffer bb = ByteBuffer.wrap(message.getPayload());
                //bb.order(ByteOrder.LITTLE_ENDIAN);

                // 수신 데이터 저장
                NrScanData scanData = dataHandler.getNrScanData();

                int idx = -1;
                if (scanData.isStart()) {
                    idx = scanData.setRcvData(bb);
                    if (idx > -1) {
                        // 5G NR SCAN 화면 갱신
                        mFunctionHandler.getNrScanFunc().update(idx);
                        // chart
                        mFunctionHandler.getNrScanTrace1CharFunc().add(idx);
                        mFunctionHandler.getNrScanTrace2CharFunc().add(idx);
                    }
                }

                // TODO 5G NR SCAN 데이터 수신 CSV 저장

                isReceivingData = false;

                if (scanData.isStart() && scanData.isLast(idx)) {
                    // Start 이고, 마지막 수신 데이터 이면 데이터 새로 요청
                    sendCommand(dataHandler.getRequestMainDataCmd());
                }
            }

        }


        if (mode != MeasureMode.MEASURE_MODE.SA && mode != MeasureMode.MEASURE_MODE.MOD_ACCURACY
                && modeValue != dataHandler.getGateDataCmdValue()
                && !mFunctionHandler.getMainLineChart().

                isEnabledLimitMsg() && isFirstData) {

            mActivity.runOnUiThread(() -> {
                mFunctionHandler.getMainLineChart().setEnabledLimitMsg(true);
                isFirstData = false;
            });

        } else if ((mode == MeasureMode.MEASURE_MODE.SA || mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY)
                && mFunctionHandler.getMainLineChart().

                isEnabledLimitMsg()) {
            //@@ [22.01.27] 원전 Threshold
//            mFunctionHandler.getMainLineChart().setEnabledLimitMsg(false);
        }


        if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && type == MeasureType.MEASURE_TYPE.TAE && taeData.getTeaMeasMode() == ON) {
            isReceivingData = false;
            return;
        }


        /*
         * Add Request Data command below
         * */
//        if ((mode == MeasureMode.MEASURE_MODE.SA || mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) && type != MeasureType.MEASURE_TYPE.NR_5G) {
//            try {
//                inputStream.read(integer4ByteArr);
//                totalPartialCount = byteToInteger(integer4ByteArr, 4);
//
//                inputStream.read(integer4ByteArr);
//                currentPartialDataIndex = byteToInteger(integer4ByteArr, 4);
//            } catch (IOException e) {
//                e.printStackTrace();
//                isReceivingData = false;
//                return;
//            }
//        }

        Integer finalTotalPartialCount = 1;// totalPartialCount;
        Integer finalCurrentPartialDataIndex = 1;// currentPartialDataIndex;

        if (D)
            Log.d("addChartValue", "total count : " + finalTotalPartialCount + " partial count : " + finalCurrentPartialDataIndex);

        if (mode == MeasureMode.MEASURE_MODE.SA
                && type != MeasureType.MEASURE_TYPE.TRANSMIT_MASK
                && saDataHandler.getConfigData().

                getSweepTimeData().

                getGateData().

                getGateMode() == SaGateData.GATE_MODE.ON
                && modeValue == dataHandler.getGateDataCmdValue()
                && typeValue == type.getValue()
                && finalTotalPartialCount.equals(finalCurrentPartialDataIndex)
        ) {

            //Check auto test
            mActivity.runOnUiThread(() -> {
                if (MacroDialog.isAutoMeasure()) {
                    MacroDialog.getInstance().checkMacroStatus();
                }
            });

            //original source
//            sendCommand(dataHandler.getRequestMainDataCmd(), 2);
            //original source

            //Qos fix
            //sendCommand(dataHandler.getRequestMainDataCmd(), mQoS);
            //Qos fix

            if (D) Log.d("addChartValue", "send request main data => 0x11");
            calMqttTimeout();

        } else if (mode == MeasureMode.MEASURE_MODE.SA
                && type != MeasureType.MEASURE_TYPE.TRANSMIT_MASK
                && saDataHandler.getConfigData().

                getSweepTimeData().

                getGateData().

                getGateMode() == SaGateData.GATE_MODE.ON
                && modeValue != dataHandler.getGateDataCmdValue()
                && modeValue == mode.getValue()
                && typeValue == type.getValue()
                && finalTotalPartialCount.equals(finalCurrentPartialDataIndex)
        ) {

            //Check auto test
            mActivity.runOnUiThread(() -> {
                if (MacroDialog.isAutoMeasure()) {
                    MacroDialog.getInstance().checkMacroStatus();
                }
            });

            //sendCommand(dataHandler.getGateDataCmd());

            if (D) Log.d("addChartValue", "send request gate data => 0x23");

            calMqttTimeout();

        } else if (mode == MeasureMode.MEASURE_MODE.SA
                && mode.getValue() == modeValue
                && typeValue == type.getValue()
                && finalTotalPartialCount.equals(finalCurrentPartialDataIndex)) {

            //Check auto test
            mActivity.runOnUiThread(() -> {
                if (MacroDialog.isAutoMeasure()) {
                    MacroDialog.getInstance().checkMacroStatus();
                }
            });

            //original source
//            sendCommand(dataHandler.getRequestMainDataCmd(), 2);
            //original source

            //Qos fix
            //sendCommand(dataHandler.getRequestMainDataCmd(), mQoS);
            //Qos fix

            if (D) Log.d("addChartValue", "send request main data => 0x11");

        } else if (mode == MeasureMode.MEASURE_MODE.SA && mode.getValue() == modeValue && typeValue == type.getValue() && !finalTotalPartialCount.equals(finalCurrentPartialDataIndex)) {

            if (D) Log.d("addChartValue", "total count and partial count is different");

        } else if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL) {

            //Check auto test
            mActivity.runOnUiThread(() -> {
                if (MacroDialog.isAutoMeasure()) {
                    MacroDialog.getInstance().checkMacroStatus();
                }
            });
            //original source
//            sendCommand(dataHandler.getRequestMainDataCmd(), 2);
            //original source

            //Qos fix
            sendCommand(dataHandler.getRequestMainDataCmd(), mQoS);
            //Qos fix
            if (D) Log.d("addChartValue", "send request main data => 0x11 For VSWR");

        } else if (mode.getValue() == modeValue && typeValue == type.getValue() && finalTotalPartialCount != null && finalCurrentPartialDataIndex != null &&
                finalTotalPartialCount.equals(finalCurrentPartialDataIndex)) {

            //Check auto test
            mActivity.runOnUiThread(() -> {
                if (MacroDialog.isAutoMeasure()) {
                    MacroDialog.getInstance().checkMacroStatus();
                }
            });

            //original source
//            sendCommand(dataHandler.getRequestMainDataCmd(), 2);
            //original source

            //Qos fix
            sendCommand(dataHandler.getRequestMainDataCmd(), mQoS);
            //Qos fix
            if (D) Log.d("addChartValue", "send request main data => 0x11");

        } else if (modeValue == MeasureMode.MEASURE_MODE.MOD_ACCURACY.getValue() && typeValue == MeasureType.MEASURE_TYPE.NR_5G.getValue()) {

            //Check auto test
            mActivity.runOnUiThread(() -> {
                if (MacroDialog.isAutoMeasure()) {
                    MacroDialog.getInstance().checkMacroStatus();
                }
            });

            if (taeData.getTeaMeasMode() == OFF)
                //original source
//                sendCommand(dataHandler.getRequestMainDataCmd(), 2);
                //original source

                //Qos fix
                sendCommand(dataHandler.getRequestMainDataCmd(), mQoS);
            //Qos fix


//            if (D) Log.d("addChartValue", "send request demodulation data => 0x11");
//            if (D) Log.d("addChartValue", "============================What?=============================");
//            if (D) Log.d("addChartValue", "gete... else => mode : " + mode.getString());
//            if (D) Log.d("addChartValue", "gete... else => type : " + type.getFullName());
//            if (D) Log.d("addChartValue", "gete... else => mode value : " + modeValue);
//            if (D) Log.d("addChartValue", "gete... else => type value : " + typeValue);
//            if (D) Log.d("addChartValue", "gete... else => gate view value : " + saDataHandler.getConfigData().getSweepTimeData().getGateData().getGateView().getValue());
//            if (D) Log.d("addChartValue", "gete... else => gate mode value : " + saDataHandler.getConfigData().getSweepTimeData().getGateData().getGateMode().getValue());
//            if (D) Log.d("addChartValue", "=========================================================");
        } else if (modeValue == MeasureMode.MEASURE_MODE.MOD_ACCURACY.getValue() && typeValue == MeasureType.MEASURE_TYPE.LTE_FDD.getValue()) {

            //Check auto test
            mActivity.runOnUiThread(() -> {
                if (MacroDialog.isAutoMeasure()) {
                    MacroDialog.getInstance().checkMacroStatus();
                }
            });
            //original source
//            sendCommand(dataHandler.getRequestMainDataCmd(), 2);
            //original source

            //Qos fix
            sendCommand(dataHandler.getRequestMainDataCmd(), mQoS);
            //Qos fix

            if (D) Log.d("addChartValue", "send request demodulation data => 0x11");
//            if (D) Log.d("addChartValue", "============================What?=============================");
//            if (D) Log.d("addChartValue", "gete... else => mode : " + mode.getString());
//            if (D) Log.d("addChartValue", "gete... else => type : " + type.getFullName());
//            if (D) Log.d("addChartValue", "gete... else => mode value : " + modeValue);
//            if (D) Log.d("addChartValue", "gete... else => type value : " + typeValue);
//            if (D) Log.d("addChartValue", "gete... else => gate view value : " + saDataHandler.getConfigData().getSweepTimeData().getGateData().getGateView().getValue());
//            if (D) Log.d("addChartValue", "gete... else => gate mode value : " + saDataHandler.getConfigData().getSweepTimeData().getGateData().getGateMode().getValue());
//            if (D) Log.d("addChartValue", "=========================================================");
        }

        if (D) {
            new Thread(() -> {
                String log = "";
                while (true) {
                    try {
                        if (inputStream.read(integer4ByteArr) == -1) {
                            break;
                        }
                        Integer val = byteToInteger(integer4ByteArr, 4);
                        log += val + " ";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (D) Log.d("addChartValue", "Surplus data : " + log);
                if (D)
                    Log.d("addChartValue", "=========================================================");
            }).start();
        }

        isReceivingData = false;
    }

    public int byteToInteger(byte[] bytes, int byteSize) {

        ByteBuffer buffer = ByteBuffer.allocate(byteSize);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.put(bytes);
        buffer.flip();


        int value;

        if (byteSize == 4) value = buffer.getInt();
        else if (byteSize == 2) value = (int) buffer.getShort();
        else value = buffer.getInt();

        return value;
    }

    public void registerRes() throws NullPointerException {
//        try {
//            mMqttClient.registerResources(mActivity);
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        }
    }

    public void unregisterRes() throws NullPointerException {
//        try {
//            if (mMqttClient != null)
//                mMqttClient.unregisterResources();
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        }
    }

    public void disconnectMqtt() throws NullPointerException {
        if (D) Log.e(TAG, "disconnectMqtt()");
        try {
            if (mMqttClient != null && isConnected) {
                mMqttClient.disconnect();
                //mMqttClient = null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        isConnected = false;
        if (D) Log.e(TAG, "disconnectMqtt() - end");
    }

    public void destroyMqtt() {
        if (mMqttClient != null) {
            try {
                //mMqttClient.unregisterResources();
                mMqttClient.disconnect();
                mMqttClient = null;
                isConnected = false;

                isSetMqtt = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            if (mTopic1Thread != null) {
                mTopic1Thread.interrupt();
            }
            mTopic1Thread = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (mTopic2Thread != null) {
                mTopic2Thread.interrupt();
            }
            mTopic1Thread = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMqttTimeout() {
        return mMqttTimeout;
    }

    public int getInitMqttTimeout() {
        return mInitMqttTimeout;
    }

    public void setmInitMqttTimeout(int timeout) {
        mInitMqttTimeout = timeout;
    }

//original source
//    private void setMqttTimeout(int timeout) {
//
//        mMqttTimeout = timeout;
//    }
//original source

    public void setMqttTimeout(int timeout) {
        mMqttTimeout = timeout;
    }

    public void calMqttTimeout() {
        if (isBackgroundReset) {
            return;
        }

        MeasureMode modeData = mFunctionHandler.getMeasureMode();
        MeasureMode.MEASURE_MODE mode = modeData.getMode();
        MeasureMode.MEASURE_MODE prevMode = modeData.getPrevMode();
        MeasureType.MEASURE_TYPE type = mFunctionHandler.getMeasureType().getType();

        if ((mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL)) {

            switch (VswrDataHandler.getInstance().getConfigData().getSweepData().getDataPoint()) {
                case P129:
                    mMqttTimeout = 1000000;
                    break;
                case P257:
                    mMqttTimeout = 1500000;
                    break;
                case P513:
                    mMqttTimeout = 1800000;
                    break;
                case P1025:
                    mMqttTimeout = 2100000;
                    break;
                case P2049:
                    mMqttTimeout = 2400000;
                    break;
                case P2002:
                    mMqttTimeout = 1000000;
                    break;
            }

        } else if (mode == MeasureMode.MEASURE_MODE.SA) {

            SaConfigData configData = SaDataHandler.getInstance().getConfigData();

            if (configData.getSweepTimeData().getGateData().getGateMode() == SaGateData.GATE_MODE.ON
                    && configData.getSweepTimeData().getGateData().getGateSource() == SaGateData.GATE_SOURCE.SSB) {
                mMqttTimeout = 4000000;
            } else if (type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {
                mMqttTimeout = 4000000;
            } else if (type == MeasureType.MEASURE_TYPE.ACLR) {
                mMqttTimeout = 2000000;
            } else {
                mMqttTimeout = 1000000;
            }

        } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {

            if (DataHandler.getInstance().getNrData().getSsbInfoData().getConfigMode() == NrSSBInfoData.CONFIG_MODE.AUTO)
                mMqttTimeout = 4000000;
            else mMqttTimeout = 1500000;

        } else mMqttTimeout = 1500000;

//        if (D) Log.d("ready timeout", mMqttTimeout + "");
    }

    public boolean isMqttConnected() {
        if (mMqttClient != null && isConnected)
            return true;
        else return false;
    }

    public Boolean isReady() {
        return isReady;
    }

    public void setReady(Boolean ready) {
        isReady = ready;
    }

    public void setPrepare(boolean isPrepare) {
        this.isPrepare = isPrepare;
    }

    public boolean isPrepare() {
        return isPrepare;
    }

    public int getCurMeasMode() {
        MeasureMode.MEASURE_MODE mode = mFunctionHandler.getMeasureMode().getMode();
        MeasureType.MEASURE_TYPE type = mFunctionHandler.getMeasureType().getType();

        int curMeasMode = MeasureMode.MEASURE_MODE.NONE.getByte();

        if (mode == MeasureMode.MEASURE_MODE.VSWR || mode == MeasureMode.MEASURE_MODE.DTF || mode == MeasureMode.MEASURE_MODE.CL) {
            curMeasMode = 0;
        } else if (mode == MeasureMode.MEASURE_MODE.SA) {
            curMeasMode = 1;
        } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && (type == MeasureType.MEASURE_TYPE.NR_5G || type == MeasureType.MEASURE_TYPE.NR_5G_SCAN)) {
            curMeasMode = 2;
        } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY && type == MeasureType.MEASURE_TYPE.LTE_FDD) {
            curMeasMode = 3;
        }

        return curMeasMode;
    }

    //background timeout issue
    public void setBackgroundReset(boolean bool) {
        isBackgroundReset = bool;
    }

    public boolean getBackgroundReset() {
        return isBackgroundReset;
    }
//background timeout issue


    public void Test() {
        new TestThread().start();
    }

    class TestThread extends Thread {
        @Override
        public void run() {
            super.run();

            while (!interrupted()) {

                int count = SweepData.DATA_POINT.P2002.getDataPoint();

                ArrayList<Integer> dataList = mFunctionHandler.getMainLineChart().getDataList(0);
                dataList.clear();

                for (int j = 0; j < count; j++) {
                    dataList.add(-1500 - (int) (Math.random() * 300));
                }

                if (mFunctionHandler.getMainLineChart().isDrawingChart()) {
                    Log.e("TestThread", "isDrawingChart = true");
                } else {
                    // chart 데이터 추가
                    binding.lineChartLayout.mainLineChart.getData().clearValuesNotChanging(0);

                    MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                    MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
                    mFunctionHandler.getMainLineChart().addEntry(mFunctionHandler.getMainLineChart().getDataList(0), 0, mode, type, SaDataHandler.getInstance().getConfigData());
                    // chart 데이터 모두 추가 후 갱신
                    mFunctionHandler.getMainLineChart().addEntryUpdate();
                }

                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
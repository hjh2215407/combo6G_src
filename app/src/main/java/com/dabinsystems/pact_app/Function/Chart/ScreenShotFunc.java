package com.dabinsystems.pact_app.Function.Chart;


import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Recorder.VideoRecorder;
import com.dabinsystems.pact_app.Screenshot.Screenshot;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShotFunc {

    private InitActivity mActivity;
    private ActivityMainBinding binding;

    private Handler mLongPressTouchHandler;
    private Runnable mLongPressTouchRunnable;

    private long mTouchDownTime;
    private long mTouchUpTime;

    private int mLongPressTime;
    private Boolean isLongPress = false;


    public ScreenShotFunc(InitActivity activity, ActivityMainBinding binding) {
        super();

        mActivity = activity;
        this.binding = binding;
        mLongPressTime = 1000;

        mLongPressTouchHandler = new Handler();

    }

    public Handler getLongPressHandler() {

        return mLongPressTouchHandler;

    }

    public void removeLongPressCallback() {

        mLongPressTouchHandler.removeCallbacks(mLongPressTouchRunnable);

    }

    public void setLongPress(boolean on) {

        isLongPress = on;

    }

    public boolean isLongPress() {

        return isLongPress;

    }

    public void setTouchDown() {

        mTouchDownTime = System.currentTimeMillis();

    }

    public long getTouchDownTime() {

        return mTouchDownTime;
    }

    public void setTouchUp() {

        mTouchUpTime = System.currentTimeMillis();

    }

    public long getTouchUpTime() {

        return mTouchUpTime;

    }

    public int getLongPressTime() {

        return mLongPressTime;

    }

    public void setLongPressTime(int time) {

        mLongPressTime = time;

    }

    public void longPressTouchEvent() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if(!mActivity.getRecorder().isRecording()) {

                mLongPressTouchRunnable = () -> {

                    //Start Recording
                    InitActivity.logMsg("Recording Event", "recording event");
                    isLongPress = true;
                    VideoRecorder.setFileName(FunctionHandler.getInstance().getMeasureType().getType().toString());
                    mActivity.getRecorder().startShareScreen();

                };

                mLongPressTouchHandler.postDelayed(mLongPressTouchRunnable, mLongPressTime);
            } else {

                InitActivity.logMsg("Recorder", "Already Recording");

            }

        } else {

            Toast.makeText(mActivity, "This phone does not support video recording.", Toast.LENGTH_SHORT).show();

        }

    }

    public static void setFileName(String currentMode) {

        String timeStamp = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss").format(new Date());
        String fileName = currentMode + timeStamp;

        Screenshot.getInstance().setFileName(fileName);
    }

}

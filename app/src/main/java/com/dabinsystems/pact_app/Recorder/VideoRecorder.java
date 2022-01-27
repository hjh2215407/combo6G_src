package com.dabinsystems.pact_app.Recorder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.widget.Toast;

import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class VideoRecorder {

    private static final String TAG = "MainActivity";
    public static final int REQUEST_CODE = 1000;
    private int mScreenDensity;
    private MediaProjectionManager mProjectionManager;
    private static int DISPLAY_WIDTH = 1280;
    private static int DISPLAY_HEIGHT = 720;
    private final int mVideoFrameRate = 60;
    private int mVideoBitRate = 3500000;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjectionCallback mMediaProjectionCallback;
    private MediaRecorder mMediaRecorder;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    public static final int REQUEST_PERMISSIONS = 10;

    private static Boolean isRecording = false;

    private Activity mActivity;
    private ActivityMainBinding binding;
    private static String mFileName;
    private String mRecFileFullPath;

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    public VideoRecorder(Activity activity, ActivityMainBinding binding) {
        super();
        mActivity = activity;
        this.binding = binding;

        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        DISPLAY_WIDTH = size.x;
        DISPLAY_HEIGHT = size.y;
        mVideoBitRate = (int) (DISPLAY_WIDTH * DISPLAY_HEIGHT * 60 / 8);
    }

    public void onCreateMethod() {
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

        InitActivity.logMsg("DISPLAY_WIDTH", DISPLAY_WIDTH + "");
        InitActivity.logMsg("DISPLAY_HEIGHT", DISPLAY_HEIGHT + "");
        InitActivity.logMsg("DISPLAY_BITRATE", mVideoBitRate + "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProjectionManager = (MediaProjectionManager) mActivity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        }

        mMediaProjectionCallback = new MediaProjectionCallback();
    }

    public void addEvents(View v) {

//        InitActivity.logMsg("Video", "setUpEvents");
//
//        if (ContextCompat.checkSelfPermission(mActivity,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) +
//                ContextCompat.checkSelfPermission
//                        (mActivity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale
//                    (mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale
//                            (mActivity, Manifest.permission.RECORD_AUDIO)) {
//
//                Snackbar.make(mActivity.findViewById(android.R.id.content), R.string.label_permissions,
//                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ActivityCompat.requestPermissions(mActivity,
//                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
//                                        REQUEST_PERMISSIONS);
//                            }
//                        }).show();
//            } else {
//                ActivityCompat.requestPermissions(mActivity,
//                        new String[]{Manifest.permission
//                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
//                        REQUEST_PERMISSIONS);
//            }
//        }

    }

    public void onActivityResultMethod(final int requestCode, final int resultCode, final Intent data) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (requestCode != REQUEST_CODE) {
                Log.e(TAG, "Unknown request code: " + requestCode);
                binding.ivScreenshot.setImageResource(R.drawable.screenshot_icon);
                return;
            }

            if (resultCode != RESULT_OK) {
                isRecording = false;
                Toast.makeText(mActivity, "Screen Cast Permission Denied", Toast.LENGTH_SHORT).show();
                binding.ivScreenshot.setImageResource(R.drawable.screenshot_icon);
                return;
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mMediaProjection = mProjectionManager.getMediaProjection(resultCode, data);
                                mMediaProjection.registerCallback(mMediaProjectionCallback, null);

                                initRecorder();
                                mVirtualDisplay = createVirtualDisplay();
                                mMediaRecorder.start();

                                isRecording = true;

                                binding.ivScreenshot.post(() -> binding.ivScreenshot.setImageResource(R.drawable.rec_icon));

                                Toast.makeText(mActivity, "Video recording has started.", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();

                                Toast.makeText(mActivity, "Failed to start recording.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }
    }

    public void startShareScreen() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (!isRecording) {
//                isRecording = true;

                //original source
//                initRecorder();
//                shareScreen();
                //original source

                //210625 permission 관련 수정 start
                if (ContextCompat.checkSelfPermission(MainActivity.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //permission check, 권한 없을 시 권한 요청
                    Log.e("Permission", "Storage access permission requested");
                    ((MainActivity) MainActivity.getActivity()).requestPermission();
                } else {
                    //권한 있는 경우 videorecorder 관련 function 실행
                    //initRecorder();
                    shareScreen();
                }
                //210625 permission 관련 수정 end

            }

        }
    }

    public void stopShareScreen() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            try {
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                Log.v(TAG, "Stopping Recording");

                stopScreenSharing();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void onToggleScreenShare() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (!isRecording) {
//                isRecording = true;

                //original source
//                initRecorder();
//                shareScreen();
                //original source

                //210625 permission 관련 수정 start
                if (ContextCompat.checkSelfPermission(MainActivity.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //파일 access 관련 permission check, 권한 없을시 요청
                    Log.e("Permission", "Storage access permission requested");
                    ((MainActivity) MainActivity.getActivity()).requestPermission();
                } else {
                    //권한이 있는 경우 video recorder 관련 function 실행
                    initRecorder();
                    shareScreen();
                }
                //210625 permission 관련 수정 end
            } else {
//                isRecording = false;
                try {
                    mMediaRecorder.stop();
                    mMediaRecorder.reset();
                    Log.v(TAG, "Stopping Recording");
                    stopScreenSharing();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private void shareScreen() {

        if (isRecording) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            isRecording = true;
            InitActivity.logMsg("isRecording", isRecording + "");

            if (mMediaProjection == null) {
                mActivity.startActivityForResult(mProjectionManager.createScreenCaptureIntent(), REQUEST_CODE);
                return;
            }

//            mVirtualDisplay = createVirtualDisplay();

//            mMediaRecorder.start();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private VirtualDisplay createVirtualDisplay() {

        return mMediaProjection.createVirtualDisplay("MainActivity",
                DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mMediaRecorder.getSurface(), null /*Callbacks*/, null
                /*Handler*/);
    }

    private void initRecorder() {

        InitActivity.logMsg("InitRecorder", "in");

        try {
            mMediaRecorder = new MediaRecorder();

//            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//
//            File saveFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                    + File.separator + mActivity.getResources().getFullName(R.string.pact_logo));

//            if(!saveFolder.exists()) saveFolder.mkdir();

            mRecFileFullPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + mFileName + ".mp4";
            mMediaRecorder.setOutputFile(mRecFileFullPath);

//            CamcorderProfile cpHigh = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
//            mMediaRecorder.setProfile(cpHigh);

            mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setVideoEncodingBitRate(mVideoBitRate);
            mMediaRecorder.setVideoFrameRate(mVideoFrameRate);
            int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
            int orientation = ORIENTATIONS.get(rotation + 90);
            mMediaRecorder.setOrientationHint(orientation);
            mMediaRecorder.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            if (isRecording) {
                isRecording = false;
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                Log.v(TAG, "Recording Stopped");
            }
            mMediaProjection = null;
            stopScreenSharing();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void stopScreenSharing() {
        binding.ivScreenshot.setImageResource(R.drawable.screenshot_icon);
        isRecording = false;
        Toast.makeText(mActivity, "The video was recorded.", Toast.LENGTH_SHORT).show();

        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
        //mMediaRecorder_old.release(); //If used: mMediaRecorder_old object cannot
        // be reused again
        destroyMediaProjection();

        try {
            // 녹화 파일이 정상적으로 만들어 졌는지 확인
            File f = new File(mRecFileFullPath);
            if (f.exists()) {
                if (f.length() > 10) {
                    // 기본 앨범에서 볼 수 있도록
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.parse("file:" + f.getAbsolutePath());
                    intent.setData(uri);
                    mActivity.sendBroadcast(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void destroyMediaProjection() {
        if (mMediaProjection != null) {
            mMediaProjection.unregisterCallback(mMediaProjectionCallback);
            mMediaProjection.stop();
            mMediaProjection = null;
        }
        Log.i(TAG, "MediaProjection Stopped");
    }

//    public void onRequestPermissionResultMethod(int requestCode,
//                                                @NonNull int[] grantResults) {
//
//        InitActivity.logMsg("RequestPermission", "in");
//
//        switch (requestCode) {
//
//        }
//
//    }

    public static Boolean isRecording() {
        return isRecording;
    }

    public static void setIsRecording(Boolean isRecording) {
        VideoRecorder.isRecording = isRecording;
    }

    public static void setFileName(String currentMode) {

        String timeStamp = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss").format(new Date());
        String fileName = currentMode + timeStamp;

        mFileName = fileName;
    }

}

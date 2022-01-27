package com.dabinsystems.pact_app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;

import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

import com.dabinsystems.pact_app.Data.AppMode;
import com.dabinsystems.pact_app.Dialog.SelectNetworkDialog;
import com.dabinsystems.pact_app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.dabinsystems.pact_app.Data.AppMode.COMBO;
import static com.dabinsystems.pact_app.Data.AppMode.PACT;


public class SplashActivity extends AppCompatActivity {

    private static AppMode AppMode = COMBO;

    private ViewDataBinding binding;
    private Context mContext = null;

    private final String mDirectoryName = "PACT/Manual";
    private final File mPath = Environment.getExternalStorageDirectory();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set app mode : pact/combo
        /*
         * if set app is COMBO:
         *   default mode    => SA   / enable mode   => SA / 5GNR
         *
         * if set App is PACT:
         *   default mode    => VSWR / disable mode  => SA / 5GNR
         *
         * */
        setApp(COMBO);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        new Thread(() -> {
//            new Handler(getMainLooper()).post(() -> {
        if (getAppMode() == COMBO) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_combo);
        } else if (getAppMode() == com.dabinsystems.pact_app.Data.AppMode.PACT) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_pact);
        }
//            });
//        }).start();

        mContext = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                    startActivityForResult(intent, 2296);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityForResult(intent, 2296);
                }
            } else {
                start();
            }
        } else {
            start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2296) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (!Environment.isExternalStorageManager()) {
                    // TODO 알림?
                    //finish();
                }
                start();
            }
        }
    }

    private void start() {
        new Thread(() -> {
            File rootFile = new File(mPath, mDirectoryName);
            if (!rootFile.exists()) {
                rootFile.mkdir();
                copyManualFile();
            }

            new Handler(getMainLooper()).postDelayed(() -> {
                new SelectNetworkDialog(this).show();
            }, 1000);
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0); // Activity 종료 시 애니메이션 효과 제거
    }

    @Override
    public void onBackPressed() {

    }

    private void copyManualFile() {
        try {
            String path = mPath + File.separator + mDirectoryName + File.separator + "pact_manual_r0.pptx";
            File file = new File(path);

            if (file.exists())
                return;

            AssetManager aMgr = getResources().getAssets();
            InputStream is = aMgr.open("pact_manual_r0.pptx");
            int size = is.available();
            byte[] buf = new byte[size];

            FileOutputStream fos = new FileOutputStream(file);
            for (int c = is.read(buf); c != -1; c = is.read(buf)) {
                fos.write(buf, 0, c);
            }

            is.close();
            fos.close();
//            convertInputStreamToFile(is, mPath + File.separator + mDirectoryName + File.separator + "cable_list.ini");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setApp(AppMode mode) {
        AppMode = mode;
    }

    public static AppMode getAppMode() {
        return AppMode;
    }
}

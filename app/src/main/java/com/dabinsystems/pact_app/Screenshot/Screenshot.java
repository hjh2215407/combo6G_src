package com.dabinsystems.pact_app.Screenshot;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screenshot {

    public static Screenshot ourInstance;
    private static Context mContext;
    private String mImageName;

    private Bitmap mBitmap;

    public Screenshot(Context context) {
        mContext = context;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Screenshot getInstance(Context context) {
        mContext = context;
        if (ourInstance == null) ourInstance = new Screenshot(context);
        return ourInstance;
    }

    public static Screenshot getInstance() {

        if (ourInstance != null)
            return ourInstance;
        else if (mContext != null) {
            ourInstance = new Screenshot(mContext);
            return ourInstance;
        } else {
            ourInstance = new Screenshot(MainActivity.getContext());
            return ourInstance;
        }
    }

    /**
     * Take screen shot of root view.
     *
     * @param v the v
     * @return the mBitmap
     */
    public Bitmap takeScreenShotOfRootView(View v) {
//        v = v.getRootView();
        mBitmap = takeScreenShotOfView(v);
        return mBitmap;
    }

    public Bitmap getBitmap() {
        if (mBitmap == null) return null;
        return mBitmap;
    }

    /**
     * Take screen shot of the View with spaces as per constraints
     *
     * @param v the v
     * @return the mBitmap
     */
    public Bitmap takeScreenShotOfView(View v) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);

        // creates immutable clone
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false); // clear drawing cache
        return b;
    }

    /**
     * Take screen shot of texture view as mBitmap.
     *
     * @param v the TextureView
     * @return the mBitmap
     */
    public Bitmap takeScreenShotOfTextureView(TextureView v) {
        return v.getBitmap();
    }

    /**
     * Take screen shot of just the View without any constraints
     *
     * @param v the v
     * @return the mBitmap
     */
    public Bitmap takeScreenShotOfJustView(View v) {
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        return takeScreenShotOfView(v);
    }

    public void capture(View view, String fileName) {
        Screenshot.getInstance().takeScreenShotOfRootView(view);
        setFileName(fileName);

        if (getBitmap() != null)
            saveScreenshot(getBitmap());
    }

    /**
     * Save screenshot to pictures folder.
     *
     * @param context the context
     * @param image   the image
     * @return the mBitmap file object
     * @throws Exception the exception
     */
    public File saveScreenshotToPicturesFolder(Context context, Bitmap image)
            throws Exception {

        //210625 수정 부분 permission 관련 수정

        int wes = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        Log.e("permission", "wes : "+wes);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.e("Permission", "Storage access permission requested");
            ((MainActivity)MainActivity.getActivity()).requestPermission();
        }
        //210625 수정 부분 permission 관련 수정

        File bitmapFile = getOutputMediaFile();
        if (bitmapFile == null) {
            throw new NullPointerException("Error creating media file, check storage permissions!");
        }
        FileOutputStream fos = new FileOutputStream(bitmapFile);
        image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();

        // Initiate media scanning to make the image available in gallery apps
        MediaScannerConnection.scanFile(context, new String[]{bitmapFile.getPath()},
                new String[]{"image/jpeg"}, null);
        return bitmapFile;
    }

    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDirectory = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDirectory.exists()) {
            if (!mediaStorageDirectory.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        mediaFile = new File(mediaStorageDirectory.getPath() + File.separator + mImageName + ".png");
        return mediaFile;
    }

    public void setFileName(String fileName) {
        mImageName = fileName;
    }

    public String getFileName() {
        if (mImageName == null) return null;
        return mImageName;
    }

    public void saveScreenshot(Bitmap bitmap) {
        // Save the screenshot

        new Thread(() -> {

            try {
                File file = Screenshot.getInstance()
                        .saveScreenshotToPicturesFolder(MainActivity.getContext(), bitmap);
                // Display a toast
//            Toast.makeText(MainActivity.getContext(), "PNG Saved at " + file.getAbsolutePath(),
//                    Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

}

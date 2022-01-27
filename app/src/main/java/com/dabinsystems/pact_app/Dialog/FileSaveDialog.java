package com.dabinsystems.pact_app.Dialog;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.List.Adapter.CustomSpinnerAdapter;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Screenshot.Screenshot;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.FileSaveDialogBinding;

import java.io.File;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class FileSaveDialog extends CustomBaseDialog {

    private FileSaveDialogBinding mFileSaveBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    private String mFileName = Screenshot.getInstance().getFileName();
    private String mFileType = ".dat";
    private final static String mDirectoryName = "PACT/Save";
    private static String mPath = Environment.getExternalStorageState() + File.separator + mDirectoryName;


    public FileSaveDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    public FileSaveDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFileSaveBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.file_save_dialog, null, false);
        setContentView(mFileSaveBinding.getRoot());

        setValues();
        addEvents();

    }

    public void setValues() {

        viewSetting();

        mPath = Environment.getExternalStorageDirectory() + File.separator + mDirectoryName;

        InitActivity.logMsg("mPath", mPath);

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(mActivity, R.layout.spinner_item, mActivity.getResources().getStringArray(R.array.extension));
        mFileSaveBinding.spFileType.setAdapter(adapter);

    }

    public void addEvents() {

        mFileSaveBinding.spFileType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        mFileType = ".dat";
                        break;

                    case 1:
                        mFileType = ".stp";
                        break;

                    case 2:
                        mFileType = ".png";
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mFileSaveBinding.linMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mFileSaveBinding.linSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mFileSaveBinding.linScreenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mFileSaveBinding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FunctionHandler.getInstance().getSaveFunc().createSaveFile(
                        mFileSaveBinding.edFileName.getText().toString(), mFileType
                );
                dismiss();
            }
        });

        mFileSaveBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

//        mFileSaveBinding.linParent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });

    }

    private void viewSetting() {

        mFileSaveBinding.tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, MainActivity.getActivity().getApplicationContext()));

        mFileSaveBinding.tvFileName.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvFileName.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvFileName.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mFileSaveBinding.edFileName.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mFileSaveBinding.tvFileType.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvFileType.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvFileType.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mFileSaveBinding.tvSave.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvSave.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvSave.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mFileSaveBinding.tvCancel.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvCancel.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mFileSaveBinding.tvDat.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvDat.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvDat.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mFileSaveBinding.tvStp.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvStp.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvStp.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mFileSaveBinding.tvPng.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvPng.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mFileSaveBinding.tvPng.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

    }

    @Override
    public void show() {
        super.show();
        mFileName = Screenshot.getInstance().getFileName();
        mFileSaveBinding.edFileName.setText(mFileName);
    }

    public static String getSaveFilePath() {
        return mPath;
    }
}

package com.dabinsystems.pact_app.Dialog;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.List.Adapter.CustomSpinnerAdapter;
import com.dabinsystems.pact_app.List.Adapter.FileListAdapter;
import com.dabinsystems.pact_app.R;

import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.FileRecallDialogBinding;


import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class FileRecallDialog extends CustomBaseDialog {

    private FileRecallDialogBinding mRecallBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    private final String All = "All";
    private final String DAT = "DAT";
    private final String STP = "STP";
    private String mFileType = All;

    public FileRecallDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecallBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.file_recall_dialog, null, false);
        setContentView(mRecallBinding.getRoot());

        setValues();
        addEvents();

    }

    public void setValues() {

        viewSetting();
        showFileList(
                FunctionHandler.getInstance().getRecallFunc().getFileAdapter()
        );

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(mActivity, R.layout.spinner_item,
                mActivity.getResources().getStringArray(R.array.extension_recall));

        mRecallBinding.spFileType.setAdapter(adapter);

    }

    private void viewSetting() {

        mRecallBinding.tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, MainActivity.getActivity().getApplicationContext()));

        mRecallBinding.tvFileNameTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvFileNameTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvFileNameTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mRecallBinding.tvFileName.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvFileName.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvFileName.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mRecallBinding.tvFileType.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvFileType.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvFileType.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mRecallBinding.tvName.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvName.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mRecallBinding.tvType.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvType.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvType.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mRecallBinding.tvSize.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvSize.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvSize.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mRecallBinding.tvSize.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvSize.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvSize.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mRecallBinding.tvModified.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvModified.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvModified.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mRecallBinding.tvOK.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvOK.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvOK.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

        mRecallBinding.tvCancel.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvCancel.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        mRecallBinding.tvCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));


    }

    public void addEvents() {

        mRecallBinding.spFileType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        mFileType = All;
                        break;

                    case 1:
                        mFileType = DAT;
                        break;

                    case 2:
                        mFileType = STP;
                        break;

                }

                showFileList(FunctionHandler.getInstance().getRecallFunc().getFileAdapter());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRecallBinding.tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionHandler.getInstance().getRecallFunc().loadFile();
                dismiss();
            }
        });

        mRecallBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

//        mRecallBinding.linParent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });

    }

    public void showFileList(FileListAdapter adapter) {

        mRecallBinding.rvRecallList.setAdapter(adapter);
        mRecallBinding.rvRecallList.setLayoutManager(new LinearLayoutManager(mActivity));

    }

    public FileRecallDialogBinding getBinding() {

        return mRecallBinding;

    }

    @Override
    public void show() {
        super.show();
        showFileList(
                FunctionHandler.getInstance().getRecallFunc().getFileAdapter()
        );
    }
}

package com.dabinsystems.pact_app.Dialog;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.List.Adapter.ExcelListAdapter;
import com.dabinsystems.pact_app.List.Item.ExcelListItem;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Util.ExcelEditor;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.ExcelFileListBinding;

import java.io.File;
import java.util.ArrayList;

public class CallNameListDialog extends CustomBaseDialog {

    private ExcelFileListBinding mLayoutBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String PATH = Environment.getExternalStorageDirectory() + File.separator + "PACT/SaveData" + File.separator;

    public CallNameListDialog(MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mActivity = activity;
        mMainBinding = binding;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.excel_file_list, null, false);
        setContentView(mLayoutBinding.getRoot());

        setValues();
        addEvents();

    }

    private void setValues() {

        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        recyclerView = mLayoutBinding.recyclerView;

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);

        mActivity.runOnUiThread(() -> {
            /*mLayoutBinding.tvTitle.setText("호출명칭을 선택하세요.");*//**/
            mLayoutBinding.tvTitle.setText("Select Call Title");/**/
        });

    }

    public void addEvents() {

        mLayoutBinding.btnCancel.setOnClickListener(v -> {

            dismiss();

        });

    }


    @Override
    public void show() {
        super.show();
        LoadingMessageDialog loadingMessage = new LoadingMessageDialog(mActivity, mMainBinding);
        loadingMessage.setMessage("Loading...");

        new Thread(() -> {

            try {

                //get file list
                File path = new File(PATH);
                File[] fileList = path.listFiles();
                ArrayList<ExcelListItem> fileItems = new ArrayList<>();

                for (int i = 0; i < fileList.length; i++) {

                    ExcelEditor excelEditor = new ExcelEditor(fileList[i].getName(), Environment.getExternalStorageDirectory() + File.separator + "PACT/SaveData" + File.separator);
                    InitActivity.logMsg("ExcelListDialog", fileList[i].getName());
                    String cellData = excelEditor.getData(4, 11);
                    ExcelListItem item = new ExcelListItem(fileList[i].getName(), cellData);
                    fileItems.add(item);

                }

                new Handler(Looper.getMainLooper()).post(() -> {
                    // specify an adapter (see also next example)
                    mAdapter = new ExcelListAdapter(fileItems, this);
                    recyclerView.setAdapter(mAdapter);
                    loadingMessage.dismiss();
                });

            } catch (NullPointerException e) {
                e.printStackTrace();

                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(mActivity, "Can't find file...", Toast.LENGTH_SHORT).show();
                });
                loadingMessage.dismiss();
                dismiss();

            }

        }).start();

    }

}




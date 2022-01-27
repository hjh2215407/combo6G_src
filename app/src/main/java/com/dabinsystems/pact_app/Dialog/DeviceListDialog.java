package com.dabinsystems.pact_app.Dialog;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.List.Adapter.DeviceListAdapter;
import com.dabinsystems.pact_app.List.Adapter.ExcelListAdapter;
import com.dabinsystems.pact_app.List.Item.ExcelListItem;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Util.ExcelEditor;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;
import com.dabinsystems.pact_app.databinding.ExcelFileListBinding;

import java.io.File;
import java.util.ArrayList;

public class DeviceListDialog extends CustomBaseDialog {

    private String mFileName;
    private ExcelFileListBinding mLayoutBinding;
    private ActivityMainBinding mMainBinding;
    private MainActivity mActivity;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String PATH = Environment.getExternalStorageDirectory() + File.separator + "PACT/DeviceList" + File.separator;
    private String FILE_NAME = "DeviceList.xlsx";

    public DeviceListDialog(String fileName, MainActivity activity, ActivityMainBinding binding) {
        super(activity, R.style.AppFullScreenTheme);

        mFileName = fileName;
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
            /*mLayoutBinding.tvTitle.setText("측정장비를 선택하세요.");*/
            mLayoutBinding.tvTitle.setText("Select Measurement Device");
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

            //get file list
            ExcelEditor excelEditor = new ExcelEditor(FILE_NAME, PATH);
            String cellData = "-1";
            ArrayList<ExcelListItem> itemList = new ArrayList<>();
            int i=2;
            while(true) {
                cellData = excelEditor.getData(i, 0);
                if(cellData == null || cellData.equals("")) break;
                ExcelListItem item = new ExcelListItem("DeviceList.xlsx", cellData);
                itemList.add(item);
                i++;
            }

            new Handler(Looper.getMainLooper()).post(() -> {
                // specify an adapter (see also next example)
                mAdapter = new DeviceListAdapter(this, itemList, mFileName);
                recyclerView.setAdapter(mAdapter);
                loadingMessage.dismiss();
            });

        }).start();

    }

}




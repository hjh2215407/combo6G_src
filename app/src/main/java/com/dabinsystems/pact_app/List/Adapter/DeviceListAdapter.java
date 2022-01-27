package com.dabinsystems.pact_app.List.Adapter;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.CustomBaseDialog;
import com.dabinsystems.pact_app.Dialog.DeviceImageDialog;
import com.dabinsystems.pact_app.Dialog.MacroDialog;
import com.dabinsystems.pact_app.List.Item.ExcelListItem;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Util.ExcelEditor;
import com.dabinsystems.pact_app.databinding.ExcelFileListItemBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ExcelListHolder> {

    private List<ExcelListItem> mItemList;
    private String mFileName;
    private CustomBaseDialog mDialog;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    ExcelFileListItemBinding binding;

    public static class ExcelListHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ExcelFileListItemBinding binding;
        private TextView textView;
        private String FileName;

        private CustomBaseDialog mDialog;
        private String PATH = Environment.getExternalStorageDirectory() + File.separator + "PACT/SaveData" + File.separator;

        public ExcelListHolder(String fileNmae, CustomBaseDialog dialog, ExcelFileListItemBinding binding) {
            super(binding.getRoot());
            FileName = fileNmae;
            textView = binding.tvName;
            mDialog = dialog;
            this.binding = binding;
        }

        public void bind(ExcelListItem item) {

            String name = item.getTitle();

            MainActivity.getActivity().runOnUiThread(() -> {

                this.binding.tvName.setText(name);
                this.binding.tvName.setOnClickListener(v -> {

                    ExcelEditor editor = new ExcelEditor(FileName, PATH);
                    Toast.makeText(MainActivity.getContext(), "write " + item.getTitle() + "in " + FileName, Toast.LENGTH_SHORT).show();
                    editor.addExcelData(5, 11, item.getTitle());
                    MacroDialog.getInstance().setEditor(editor);
                    MacroDialog.getInstance().getEditor().removeSheet(1);
                    MacroDialog.getInstance().setDevice(MacroDialog.MEASURE_DEVICE.DEVICE1);
                    DeviceImageDialog imageDialog = new DeviceImageDialog((MainActivity) MainActivity.getActivity(), MainActivity.getBinding());
                    /*imageDialog.show("장치1에 연결 후 OK버튼을 누르세요.");*/
                    imageDialog.show("After connect to device 1, press ok button");
                    imageDialog.setImage(MainActivity.getActivity().getResources().getDrawable(R.drawable.device1));
//                    new MacroDialog(item.getName(), (MainActivity)MainActivity.getActivity(), MainActivity.getBinding()).show();
                    Toast.makeText(MainActivity.getContext(), name + " : " + item.getName(), Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                });
            });

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DeviceListAdapter(CustomBaseDialog dialog, ArrayList<ExcelListItem> items, String fileName) {
        mItemList = items;
        mFileName = fileName;
        mDialog = dialog;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public DeviceListAdapter.ExcelListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        binding = ExcelFileListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );

        // create a new view
//        TextView v = binding.tvFileName;

        ExcelListHolder vh = new ExcelListHolder(mFileName, mDialog, binding);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ExcelListHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ExcelListItem item = mItemList.get(position);
        holder.bind(item);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void setItem(List<ExcelListItem> items) {
        if (items != null) {
            this.mItemList = items;
            notifyDataSetChanged();
        }
    }

}
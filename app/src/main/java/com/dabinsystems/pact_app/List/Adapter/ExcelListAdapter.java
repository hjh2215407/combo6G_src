package com.dabinsystems.pact_app.List.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.CustomBaseDialog;
import com.dabinsystems.pact_app.Dialog.DeviceListDialog;
import com.dabinsystems.pact_app.List.Item.ExcelListItem;
import com.dabinsystems.pact_app.databinding.ExcelFileListItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ExcelListAdapter extends RecyclerView.Adapter<ExcelListAdapter.ExcelListHolder> {

    private List<ExcelListItem> mItemList;
    private CustomBaseDialog mDialog;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    ExcelFileListItemBinding binding;

    public static class ExcelListHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ExcelFileListItemBinding binding;

        private CustomBaseDialog mDialog;
        public TextView textView;

        public ExcelListHolder(ExcelFileListItemBinding binding, CustomBaseDialog dialog) {
            super(binding.getRoot());
            textView = binding.tvName;
            mDialog = dialog;
            this.binding = binding;
        }

        public void bind(ExcelListItem item) {

            String name = item.getTitle();

            MainActivity.getActivity().runOnUiThread(() -> {
                this.binding.tvName.setText(name);
                this.binding.tvName.setOnClickListener(v->{
                    mDialog.dismiss();
                    new DeviceListDialog(item.getName(), (MainActivity)MainActivity.getActivity(), MainActivity.getBinding()).show();
                    Toast.makeText(MainActivity.getContext(), name + " : " + item.getName(), Toast.LENGTH_SHORT).show();
                });
            });

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExcelListAdapter(ArrayList<ExcelListItem> items, CustomBaseDialog dialog) {
        mItemList = items;
        mDialog = dialog;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public ExcelListAdapter.ExcelListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        binding = ExcelFileListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );

        // create a new view
//        TextView v = binding.tvFileName;

        ExcelListHolder vh = new ExcelListHolder(binding, mDialog);
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
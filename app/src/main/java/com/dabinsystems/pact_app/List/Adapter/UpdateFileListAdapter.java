package com.dabinsystems.pact_app.List.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.List.Item.UpdateFileListItem;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.FwUpdateDialogBinding;
import com.dabinsystems.pact_app.databinding.UpdateFileListItemBinding;

import java.util.ArrayList;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;


public class UpdateFileListAdapter extends RecyclerView.Adapter {

    private ArrayList<UpdateFileListItem> mFileListInfoList;
    private ArrayList<UpdateFileListItemBinding> mHolderList;
    private UpdateFileListItem mSelectedFileInfo;
    private FwUpdateDialogBinding mUpdateFileBinding;

    private Context mContext;
    private RecyclerView.ViewHolder holder;
    private int mSelectedPos = -1;

    public UpdateFileListAdapter(ArrayList<UpdateFileListItem> fileListInfoList, FwUpdateDialogBinding binding, Context context) {
        this.mFileListInfoList = fileListInfoList;
        mContext = context;
        mHolderList = new ArrayList<UpdateFileListItemBinding>();
        mUpdateFileBinding = binding;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UpdateFileListItemBinding binding = UpdateFileListItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        holder = new UpdateFileHolder(binding);
        holder.setIsRecyclable(true);
        InitActivity.logMsg("onCreateViewHolder", "onCreateViewHolder");
//        holder.setIsRecyclable(false);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        InitActivity.logMsg("position", position + "");

        UpdateFileHolder updateFileHolder = (UpdateFileHolder) holder;

        final UpdateFileListItemBinding binding = updateFileHolder.binding;

        ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)convertDpToPixel(45, MainActivity.getActivity().getApplicationContext()));

        binding.parent.setLayoutParams(params);

        binding.tvName.setText(mFileListInfoList.get(position).getFileName());
        binding.tvName.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        binding.tvName.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        binding.tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));

//        binding.tvModified.setText(mFileListInfoList.get(position).getModified());
        binding.tvModified.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        binding.tvModified.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));
        binding.tvModified.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getActivity().getApplicationContext()));


        InitActivity.logMsg("FileName", mFileListInfoList.get(position).getFileName());

        binding.parent.setBackgroundResource(R.drawable.recall_list_view_unselected);
        binding.parent.setTag("none");

        if (!mHolderList.contains(updateFileHolder))
            mHolderList.add(updateFileHolder.binding);

        if (mSelectedPos != -1 && position == mSelectedPos) {

            binding.parent.setBackgroundResource(R.drawable.recall_list_view_selected);
            binding.parent.setTag("select");
            InitActivity.logMsg("mSelectedRelativeLayout", "in");

        }

        binding.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InitActivity.logMsg("FileList", "On Click " + position);
                if (binding.parent.getTag().equals("none")) {

                    noneSelect();

                    binding.parent.setBackgroundResource(R.drawable.recall_list_view_selected);
                    binding.parent.setTag("select");

                    mSelectedPos = position;
                    mSelectedFileInfo = mFileListInfoList.get(mSelectedPos);


                } else {

                    binding.parent.setBackgroundResource(R.drawable.recall_list_view_unselected);
                    binding.parent.setTag("none");
                    mSelectedPos = -1;

                }

            }
        });

    }

    public void noneSelect() {

        for (int i = 0; i < mHolderList.size(); i++) {

            mHolderList.get(i).parent.setBackgroundResource(R.drawable.recall_list_view_unselected);
            mHolderList.get(i).parent.setTag("none");
        }
        mSelectedPos = -1;

    }

    @Override
    public int getItemCount() {
        if(mFileListInfoList == null) return -1;
        return mFileListInfoList.size();
    }

    private class UpdateFileHolder extends RecyclerView.ViewHolder {
        UpdateFileListItemBinding binding;

        UpdateFileHolder(UpdateFileListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public RecyclerView.ViewHolder getHolder() {

        return holder;
    }

    public boolean isSelected() {

        if(mSelectedPos == -1 || mSelectedPos < 0) return false;
        else return true;

    }

    public UpdateFileListItem getSelectedItem() {
        return mSelectedFileInfo;
    }

//    public CableListItemBinding getBinding() {
//        return binding;
//    }

}
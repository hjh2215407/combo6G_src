package com.dabinsystems.pact_app.List.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.List.Item.CableListInfoItem;
import com.dabinsystems.pact_app.List.Item.CableLossInfoItem;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.CableListItemBinding;

import java.util.ArrayList;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;


public class CableListAdapter extends RecyclerView.Adapter {

    private ArrayList<CableListInfoItem> mCableInfoList;
    private ArrayList<CableListItemBinding> mHolderList;
    private ArrayList<CableLossInfoItem> lossList;
    private CableListInfoItem mSelectedCableInfo;

    private Context mContext;
    private RecyclerView.ViewHolder holder;
    private int mSelectedPos = 0;

    public CableListAdapter(Context context) {
        mCableInfoList = new ArrayList<>();
        mContext = context;
        mHolderList = new ArrayList<CableListItemBinding>();

    }

    public CableListAdapter(ArrayList<CableListInfoItem> CableInfoList, Context context) {
        mCableInfoList = CableInfoList;
        mContext = context;
        mHolderList = new ArrayList<CableListItemBinding>();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CableListItemBinding binding = CableListItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        holder = new CableListItemHolder(binding);
        holder.setIsRecyclable(true);
        InitActivity.logMsg("onCreateViewHolder", "onCreateViewHolder");
//        holder.setIsRecyclable(false);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        InitActivity.logMsg("position", position + "");

        CableListItemHolder cableListItemHolder = (CableListItemHolder) holder;

        final CableListItemBinding binding = cableListItemHolder.binding;

        binding.tvCableName.setText(mCableInfoList.get(position).getCableName());
        binding.tvCableName.setTag(binding.tvCableName.getText().toString());
        InitActivity.logMsg("CableName", mCableInfoList.get(position).getCableName());

        binding.parent.setBackgroundResource(R.drawable.cable_unseleted_button);
        binding.parent.setTag("none");

        if (!mHolderList.contains(cableListItemHolder))
            mHolderList.add(cableListItemHolder.binding);

        if (mSelectedPos != -1 && position == mSelectedPos) {

            binding.parent.setBackgroundResource(R.drawable.cable_seleted_button);
            binding.parent.setTag("select");
            updateLossInfo(lossList = mCableInfoList.get(position).getCablelossList());
            InitActivity.logMsg("mSelectedRelativeLayout", "in");

        }

        binding.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InitActivity.logMsg("CableList", "On Click " + position);
                if (binding.parent.getTag().equals("none")) {

                    noneSelect();

                    binding.parent.setBackgroundResource(R.drawable.cable_seleted_button);
                    binding.parent.setTag("select");

                    mSelectedPos = position;
                    mSelectedCableInfo = mCableInfoList.get(mSelectedPos);

                    InitActivity.getBinding().tvCableName.setText(mCableInfoList.get(position).getCableName());
                    InitActivity.getBinding().tvPropVelocity.setText(mCableInfoList.get(position).getPropVelocity() + "");

                    lossList = mCableInfoList.get(position).getCablelossList();

                    updateLossInfo(lossList);


                } else {

                    binding.parent.setBackgroundResource(R.drawable.cable_unseleted_button);
                    binding.parent.setTag("none");
                    resetLossText();

                    mSelectedPos = -1;

                }

            }
        });

    }

    public void noneSelect() {

        for (int i = 0; i < mHolderList.size(); i++) {

            mHolderList.get(i).parent.setBackgroundResource(R.drawable.cable_unseleted_button);
            mHolderList.get(i).parent.setTag("none");
        }
        mSelectedPos = -1;

        resetLossText();

    }

    private void updateLossInfo(ArrayList<CableLossInfoItem> lossList) {

        for (int i = 0; i < lossList.size(); i++) {

            switch (i) {
                case 0:
                    InitActivity.getBinding().tvCableFreq1.setText(lossList.get(0).getFreq() + "");
                    InitActivity.getBinding().tvCableLoss1.setText(lossList.get(0).getCableLoss() + "");
                    break;

                case 1:
                    InitActivity.getBinding().tvCableFreq2.setText(lossList.get(1).getFreq() + "");
                    InitActivity.getBinding().tvCableLoss2.setText(lossList.get(1).getCableLoss() + "");
                    break;

                case 2:
                    InitActivity.getBinding().tvCableFreq3.setText(lossList.get(2).getFreq() + "");
                    InitActivity.getBinding().tvCableLoss3.setText(lossList.get(2).getCableLoss() + "");
                    break;

                case 3:
                    InitActivity.getBinding().tvCableFreq4.setText(lossList.get(3).getFreq() + "");
                    InitActivity.getBinding().tvCableLoss4.setText(lossList.get(3).getCableLoss() + "");
                    break;

                case 4:
                    InitActivity.getBinding().tvCableFreq5.setText(lossList.get(4).getFreq() + "");
                    InitActivity.getBinding().tvCableLoss5.setText(lossList.get(4).getCableLoss() + "");
                    break;

                case 5:
                    InitActivity.getBinding().tvCableFreq6.setText(lossList.get(5).getFreq() + "");
                    InitActivity.getBinding().tvCableLoss6.setText(lossList.get(5).getCableLoss() + "");
                    break;
            }

        }

    }

    private void resetLossText() {

        InitActivity.getBinding().tvCableName.setText("");
        InitActivity.getBinding().tvPropVelocity.setText("");

        InitActivity.getBinding().tvCableFreq1.setText("");
        InitActivity.getBinding().tvCableLoss1.setText("");

        InitActivity.getBinding().tvCableFreq2.setText("");
        InitActivity.getBinding().tvCableLoss2.setText("");

        InitActivity.getBinding().tvCableFreq3.setText("");
        InitActivity.getBinding().tvCableLoss3.setText("");

        InitActivity.getBinding().tvCableFreq4.setText("");
        InitActivity.getBinding().tvCableLoss4.setText("");

        InitActivity.getBinding().tvCableFreq5.setText("");
        InitActivity.getBinding().tvCableLoss5.setText("");

        InitActivity.getBinding().tvCableFreq6.setText("");
        InitActivity.getBinding().tvCableLoss6.setText("");

    }

    @Override
    public int getItemCount() {
        if(mCableInfoList == null) return -1;
        return mCableInfoList.size();
    }

    private class CableListItemHolder extends RecyclerView.ViewHolder {
        CableListItemBinding binding;

        CableListItemHolder(CableListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) convertDpToPixel(60, MainActivity.getContext())
            );

            binding.parent.setLayoutParams(params);
            binding.tvCableName.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, MainActivity.getContext()));

        }
    }

    public RecyclerView.ViewHolder getHolder() {

        return holder;
    }

    public boolean isSelected() {

        if(mSelectedPos == -1 || mSelectedPos < 0) return false;
        else return true;

    }

    public CableListInfoItem getSelectedCable() {

        InitActivity.logMsg("getSelectedCable", mSelectedPos + "");

        if(mSelectedPos >= 0 && mCableInfoList.size() > 0)
            mSelectedCableInfo = mCableInfoList.get(mSelectedPos);

        else return null;

        return mSelectedCableInfo;

    }

    public ArrayList<CableListInfoItem> getCableInfoList() {
        return mCableInfoList;
    }

    public void setCableInfoList(ArrayList<CableListInfoItem> list) {

        mCableInfoList = list;
        notifyDataSetChanged();

    }
}
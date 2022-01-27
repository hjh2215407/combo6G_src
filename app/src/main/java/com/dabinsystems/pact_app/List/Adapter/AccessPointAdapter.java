package com.dabinsystems.pact_app.List.Adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.WiFiDialog;
import com.dabinsystems.pact_app.List.Item.AccessPointItem;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.WifiCardViewBinding;

import java.util.ArrayList;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;


public class AccessPointAdapter extends RecyclerView.Adapter {

    private ArrayList<AccessPointItem> mAccessPoints;
    private ArrayList<WifiCardViewBinding> mHolderList;
    private Context context;
    private WiFiDialog mWifiDialog;
    private int mSelectIdx = -1;

    public AccessPointAdapter(ArrayList<AccessPointItem> accessPoints, Context context, WiFiDialog dialog) {
        this.mAccessPoints = accessPoints;
        this.context = context;
        mHolderList = new ArrayList<WifiCardViewBinding>();
        mWifiDialog = dialog;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        WifiCardViewBinding binding = WifiCardViewBinding.inflate(LayoutInflater.from(context), parent, false);
        holder = new AccessPointHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        try {

            clearSelect();

            AccessPointHolder accessPointHolder = (AccessPointHolder) holder;
            final WifiCardViewBinding binding = accessPointHolder.binding;
            binding.cardView.setRadius(20.0f);

            if (!mHolderList.contains(binding)) mHolderList.add(binding);

            binding.cardParent.setBackgroundResource(R.color.colorDialogContent);
            binding.cardParent.setTag("none");
            binding.bssidTextView.setTextColor(Color.BLACK);
            binding.rssiLevelTextView.setTextColor(Color.BLACK);
            binding.ssidTextView.setTextColor(Color.BLACK);

            if (mSelectIdx != -1 && mSelectIdx == position) {
                binding.cardParent.setBackgroundColor(Color.BLACK);

                binding.rssiLevelTextView.setTextColor(Color.WHITE);
                binding.ssidTextView.setTextColor(Color.WHITE);
                binding.bssidTextView.setTextColor(Color.WHITE);

                binding.cardParent.setTag("select");
            }

            final String ssid = mAccessPoints.get(position).getSsid();
            String bSsid = "BSSID : " + mAccessPoints.get(position).getBssid();
            String rssi = "RSSI : " + mAccessPoints.get(position).getRssi();

            binding.ssidTextView.setText("SSID : " + ssid);
            binding.bssidTextView.setText(bSsid);
            binding.rssiLevelTextView.setText(rssi);

            binding.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (binding.cardParent.getTag().equals("none")) {

                        clearSelect();

                        mSelectIdx = position;
                        binding.cardParent.setBackgroundColor(Color.BLACK);

                        binding.rssiLevelTextView.setTextColor(Color.WHITE);
                        binding.ssidTextView.setTextColor(Color.WHITE);
                        binding.bssidTextView.setTextColor(Color.WHITE);

                        binding.cardParent.setTag("select");

                    } else {

                        clearSelect();

                        binding.cardParent.setBackgroundResource(R.color.colorDialogContent);
                        binding.rssiLevelTextView.setTextColor(Color.BLACK);
                        binding.ssidTextView.setTextColor(Color.BLACK);
                        binding.bssidTextView.setTextColor(Color.BLACK);

                        binding.cardParent.setTag("none");
                    }


                    InitActivity.logMsg("cardView", "On Click");

                }
            });
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    private void clearSelect() {

        for(WifiCardViewBinding bind : mHolderList) {
            bind.cardParent.setBackgroundResource(R.color.colorDialogContent);
            bind.rssiLevelTextView.setTextColor(Color.BLACK);
            bind.ssidTextView.setTextColor(Color.BLACK);
            bind.bssidTextView.setTextColor(Color.BLACK);
            bind.cardParent.setTag("none");
        }

    }

    @Override
    public int getItemCount() {
        return mAccessPoints.size();
    }

    private class AccessPointHolder extends RecyclerView.ViewHolder {
        WifiCardViewBinding binding;

        AccessPointHolder(WifiCardViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.bssidTextView.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, context));
            binding.bssidTextView.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, context));
            binding.bssidTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, context));

            binding.ssidTextView.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, context));
            binding.ssidTextView.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, context));
            binding.ssidTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, context));

            binding.rssiLevelTextView.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, context));
            binding.rssiLevelTextView.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, context));
            binding.rssiLevelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, context));

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int)convertDpToPixel(120, context)
            );

            binding.cardParent.setLayoutParams(params);

        }
    }

    public AccessPointItem getSelectedItem() {

        if(mSelectIdx != -1) return mAccessPoints.get(mSelectIdx);
        else return  null;

    }

}
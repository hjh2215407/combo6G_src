package com.dabinsystems.pact_app.List.Adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.databinding.SpinnerItemBinding;

import java.util.List;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private String[] mItems;

    private float mSubTextSize = 10f;
    private float mSubTextMaxSize = 11f;

    public CustomSpinnerAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    public CustomSpinnerAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        mContext = context;
    }

    public CustomSpinnerAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        mContext = context;
        mItems = objects;
    }

    public CustomSpinnerAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
    }

    public CustomSpinnerAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mContext = context;
    }

    public CustomSpinnerAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        mContext = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        SpinnerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.spinner_item, parent, false);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int)convertDpToPixel(35, MainActivity.getActivity().getApplicationContext()));

        binding.tvParent.setLayoutParams(params);
        binding.tvParent.setPadding(0, (int)convertDpToPixel(0, MainActivity.getActivity().getApplicationContext()), 0,
                (int)convertDpToPixel(0, MainActivity.getActivity().getApplicationContext()));

        binding.tvParent.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        binding.tvParent.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextMaxSize, MainActivity.getActivity().getApplicationContext()));
        binding.tvParent.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextSize, MainActivity.getActivity().getApplicationContext()));
        binding.tvParent.setTextColor(Color.BLACK);
        binding.tvParent.setText(mItems[position]);

        return binding.tvParent;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SpinnerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.spinner_item, parent, false);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        binding.tvParent.setLayoutParams(params);
        binding.tvParent.setPadding(0, (int)convertDpToPixel(5, MainActivity.getActivity().getApplicationContext()), 0,
                (int)convertDpToPixel(5, MainActivity.getActivity().getApplicationContext()));

        binding.tvParent.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, MainActivity.getActivity().getApplicationContext()));
        binding.tvParent.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextMaxSize, MainActivity.getActivity().getApplicationContext()));
        binding.tvParent.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(mSubTextSize, MainActivity.getActivity().getApplicationContext()));
        binding.tvParent.setTextColor(Color.BLACK);
        binding.tvParent.setText(mItems[position]);

        return binding.tvParent;

    }
}

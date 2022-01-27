package com.dabinsystems.pact_app.Function;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.PresetDialog;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

public class Preset {

    private ActivityMainBinding binding;
    private LinearLayout mParent;
    private MainActivity mActivity;
    private PresetDialog dialog;


    public Preset(Activity activity, ActivityMainBinding binding) {
        super();

        this.binding = binding;
        mActivity = (MainActivity)activity;
        dialog = new PresetDialog(mActivity);

    }

    public void addEvents() throws NullPointerException {

        binding.ivPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

    }



}

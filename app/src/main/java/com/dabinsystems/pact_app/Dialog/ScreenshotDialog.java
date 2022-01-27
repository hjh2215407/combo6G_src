package com.dabinsystems.pact_app.Dialog;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.Screenshot.Screenshot;
import com.dabinsystems.pact_app.databinding.ScreenshotDialogBinding;
import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class ScreenshotDialog extends CustomBaseDialog {

    private ScreenshotDialogBinding binding;
    private Context mContext;
    private Screenshot screenshot;
    private String mSelectedMenu;

    public ScreenshotDialog(Context context, String selectedMenu) {
        super(context, R.style.AppFullScreenTheme);
        mContext = context;
        mSelectedMenu = selectedMenu;
        screenshot = Screenshot.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.screenshot_dialog, null, false);
        setContentView(binding.getRoot());

        setValues();
        setUpEvents();

    }

    public void setValues() {

        binding.edFileName.setText(Screenshot.getInstance().getFileName());

        binding.tvTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mContext));
        binding.tvTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mContext));
        binding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mContext));

        binding.tvFileName.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mContext));
        binding.tvFileName.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mContext));
        binding.tvFileName.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mContext));

        binding.edFileName.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mContext));

        binding.tvPng.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mContext));
        binding.tvPng.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mContext));
        binding.tvPng.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mContext));

        binding.tvSave.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mContext));
        binding.tvSave.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mContext));
        binding.tvSave.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mContext));

        binding.tvCancel.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int)convertDpToPixel(1, mContext));
        binding.tvCancel.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mContext));
        binding.tvCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(14, mContext));

    }

    public void setUpEvents() {

        binding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(screenshot.getBitmap() != null) {
                    screenshot.setFileName(binding.edFileName.getText().toString());
                    screenshot.saveScreenshot(screenshot.getBitmap());
                }
                else Toast.makeText(mContext, "NULL", Toast.LENGTH_SHORT).show();

                dismiss();
            }
        });

        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


}

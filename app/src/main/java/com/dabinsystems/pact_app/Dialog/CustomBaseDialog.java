package com.dabinsystems.pact_app.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.dabinsystems.pact_app.R;

public class CustomBaseDialog extends Dialog {


    public CustomBaseDialog(Context context) {
        super(context, R.style.AppFullScreenTheme);
    }

    public CustomBaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.5f;

        getWindow().setAttributes(lpWindow);

    }
}

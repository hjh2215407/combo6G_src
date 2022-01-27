package com.dabinsystems.pact_app.View.DTF.FreqDist;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.List.Item.CableListInfoItem;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;


public class CableListView extends LayoutBase {

    private DynamicView dynamicView;

    private LinearLayout linNone, linSelect;
    //private AutofitTextView tvNone, tvSelect;

    public CableListView(MainActivity activity, ActivityMainBinding binding) throws NullPointerException {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        new Thread(() -> {

            initView();

            update();

            mActivity.runOnUiThread(() -> {

                FunctionHandler.getInstance().getCableInfoFunc().showCableList();

                binding.linRightMenu.removeAllViews();

                binding.linRightMenu.addView(linNone);
                binding.linRightMenu.addView(linSelect);

                binding.linCalibration.setVisibility(View.GONE);

                binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getDtfSetupView().addMenu());

            });
        }).start();
    }

    @Override
    public void initView() {
        super.initView();

        if (dynamicView != null) return;

        dynamicView = new DynamicView(mActivity);

        ArrayList<View> mNoneList = dynamicView.addRightMenuButton("None");
        ArrayList<View> mSelectList = dynamicView.addRightMenuButton("Select");

        linNone = (LinearLayout) mNoneList.get(0);
        linNone.setBackgroundResource(R.drawable.right_menu_layout_selector);

        linSelect = (LinearLayout) mSelectList.get(0);
        linSelect.setBackgroundResource(R.drawable.right_menu_layout_selector);

        setUpEvents();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();

        mActivity.runOnUiThread(() -> {

            binding.linCableList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //null
                }
            });

            linNone.setOnClickListener(v -> {
                if (!FunctionHandler.getInstance().getCableInfoFunc().getCableInfoAdapter().isSelected())
                    Toast.makeText(mActivity, "No items selected.", Toast.LENGTH_SHORT).show();
                else
                    FunctionHandler.getInstance().getCableInfoFunc().getCableInfoAdapter().noneSelect();
            });

            linSelect.setOnClickListener(v -> {

                if (FunctionHandler.getInstance().getCableInfoFunc().getCableInfoAdapter() != null && FunctionHandler.getInstance().getCableInfoFunc().getCableInfoAdapter().isSelected()) {

                    CableListInfoItem info = FunctionHandler.getInstance().getCableInfoFunc().getCableInfoAdapter().getSelectedCable();

                    FunctionHandler.getInstance().getCableInfoFunc().setCableName(info.getCableName());

                    FunctionHandler.getInstance().getCableInfoFunc().setLoss(info.getLoss());

                    FunctionHandler.getInstance().getCableInfoFunc().setVelocity(info.getPropVelocity());

                    VswrDataHandler.getInstance().getDtfConfigData().updateDistance();

                    FunctionHandler.getInstance().getDataConnector().sendCommand(
                            DataHandler.getInstance().getConfigCmd()
                    );

//                        setCableName(info.getCableName());
//                        setVelocity(info.getPropVelocity());
//                        calLoss();
//                        CommandData.getInstance().sendSettingValuesCmd();

                    Toast.makeText(mActivity, "Send Cable(" + info.getCableName() + ") Information to FW.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, "No items selected.", Toast.LENGTH_SHORT).show();
                }

                FunctionHandler.getInstance().getCableInfoFunc().updateCableText();
            });

            binding.tvBack.setOnClickListener(v -> ViewHandler.getInstance().getDtfSetupView().addMenu());
        });
    }
}

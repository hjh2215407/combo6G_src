package com.dabinsystems.pact_app.View.ModAccuracy.NR5GScan;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NR_SCAN_PROFILE;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanCarrierData;
import com.dabinsystems.pact_app.Data.SA.SaGateData;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.DynamicView;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import java.util.ArrayList;

import me.grantland.widget.AutofitTextView;

/**
 * [jigum] 2021-07-23
 * <p>
 * right menu 5G NR Scan -> Setup -> Carriers -> Profile
 * - 5 MHz
 * - ...
 * - 100 MHz
 */
public class NrScanSelProfileView extends LayoutBase implements View.OnClickListener {

    ArrayList<LinearLayout> layouts;
    ArrayList<AutofitTextView> tvs;
    boolean isInitView = false;

    private LinearLayout linMore;
    private AutofitTextView tvMore;

    int idxPage = 0;
    int totalPage = 2;

    public NrScanSelProfileView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);

        layouts = new ArrayList<>();
        tvs = new ArrayList<>();
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();
        new Thread(() -> {

            idxPage = 0;

            initView();
            update();
            mActivity.runOnUiThread(() -> {
                try {
                    binding.tvRightMenuTitle.setText(mActivity.getResources().getString(R.string.profile));
                    binding.linRightMenu.removeAllViews();

                    for (int i = 0; i < layouts.size(); ++i) {
                        binding.linRightMenu.addView(layouts.get(i));
                    }
                    ShowButton();

                    binding.linRightMenu.addView(linMore);

                    binding.tvBack.setOnClickListener(v -> {
                        ViewHandler.getInstance().getNrScanCarrierView().addMenu();
                    });

                } catch (NullPointerException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    @SuppressLint("SetTextI18n")
    public void update() {
        initView();
        mActivity.runOnUiThread(() -> {
            try {
                NrScanCarrierData data = DataHandler.getInstance().getNrScanData().getCarrierData();
                int selIdx = data.getSelectedCarrierIdx();
                NR_SCAN_PROFILE sp = data.getProfile(selIdx);

                // 기존 선택 항목 표시
                for (int i = 0; i < layouts.size(); ++i) {
                    NR_SCAN_PROFILE p = (NR_SCAN_PROFILE) layouts.get(i).getTag();
                    layouts.get(i).setSelected(sp == p);
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initView() {
        super.initView();

        if (isInitView)
            return;
        isInitView = true;

        DynamicView mDynamicView = new DynamicView(mActivity);

        for (NR_SCAN_PROFILE p : NR_SCAN_PROFILE.values()) {
            ArrayList<View> listView = mDynamicView.addRightMenuButton(p.getString());
            LinearLayout layout = (LinearLayout) listView.get(0);
            layout.setTag(p);
            layout.setOnClickListener(this);
            layouts.add(layout);
            tvs.add((AutofitTextView) listView.get(1));
        }

        ArrayList<View> listView = mDynamicView.addRightMenuButton("More", (idxPage + 1) + "/" + totalPage);
        linMore = (LinearLayout) listView.get(0);
        tvMore = (AutofitTextView) listView.get(2);
        linMore.setOnClickListener(v -> {
            // 다음 버튼 표시
            idxPage++;
            if (idxPage >= totalPage)
                idxPage = 0;
            tvMore.setText((idxPage + 1) + "/" + totalPage);

            ShowButton();
        });
    }

    @Override
    public void onClick(View v) {
        // 선택된 profile
        NR_SCAN_PROFILE p = (NR_SCAN_PROFILE) v.getTag();

        // 저장
        NrScanCarrierData data = DataHandler.getInstance().getNrScanData().getCarrierData();
        int selIdx = data.getSelectedCarrierIdx();
        data.setProfile(selIdx, p);

        // 화면 이동
        ViewHandler.getInstance().getNrScanCarrierView().addMenu();
    }

    private void ShowButton() {
        // 1/2
        if (idxPage == 0) {
            for (int i = 0; i < layouts.size(); ++i) {
                layouts.get(i).setVisibility(i < 7 ? View.VISIBLE : View.GONE);
            }
        } else {
            for (int i = 0; i < layouts.size(); ++i) {
                layouts.get(i).setVisibility(i < 7 ? View.GONE : View.VISIBLE);
            }
        }
    }

}

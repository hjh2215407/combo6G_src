package com.dabinsystems.pact_app.Function.nr5g;

import android.util.TypedValue;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanCarrierData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5GScan.NrScanRcvItem;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import me.grantland.widget.AutofitTextView;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

/**
 * [jigum] 2021-07-22
 *
 * 5G NR Scan 화면 표의 값 갱신
 *
 */
public class NrScanFunc {

    private InitActivity mActivity;
    private ActivityMainBinding binding;

    private AutofitTextView[] tvFreq, tvSSBFreq, tvPCI, tvSSBIndex, tvSS_RSRP, tvSS_RSRQ, tvSS_SINR, tvTimingOffset, tvTAE;

    public NrScanFunc(InitActivity activity, ActivityMainBinding binding) {
        super();
        mActivity = activity;
        this.binding = binding;
        init();
        initTextSize();
        updateFreq();
    }

    private void init() {
        tvFreq = new AutofitTextView[]{binding.nrScanLayout.tvFreq1, binding.nrScanLayout.tvFreq2, binding.nrScanLayout.tvFreq3, binding.nrScanLayout.tvFreq4};
        tvSSBFreq = new AutofitTextView[]{binding.nrScanLayout.tvSSBFreq1, binding.nrScanLayout.tvSSBFreq2, binding.nrScanLayout.tvSSBFreq3, binding.nrScanLayout.tvSSBFreq4};
        tvPCI = new AutofitTextView[]{binding.nrScanLayout.tvPCI1, binding.nrScanLayout.tvPCI2, binding.nrScanLayout.tvPCI3, binding.nrScanLayout.tvPCI4}; 
        tvSSBIndex = new AutofitTextView[]{binding.nrScanLayout.tvSSBIndex1, binding.nrScanLayout.tvSSBIndex2, binding.nrScanLayout.tvSSBIndex3, binding.nrScanLayout.tvSSBIndex4}; 
        tvSS_RSRP = new AutofitTextView[]{binding.nrScanLayout.tvSSRSRP1, binding.nrScanLayout.tvSSRSRP2, binding.nrScanLayout.tvSSRSRP3, binding.nrScanLayout.tvSSRSRP4}; 
        tvSS_RSRQ = new AutofitTextView[]{binding.nrScanLayout.tvSSRSRQ1, binding.nrScanLayout.tvSSRSRQ2, binding.nrScanLayout.tvSSRSRQ3, binding.nrScanLayout.tvSSRSRQ4}; 
        tvSS_SINR = new AutofitTextView[]{binding.nrScanLayout.tvSSSINR1, binding.nrScanLayout.tvSSSINR2, binding.nrScanLayout.tvSSSINR3, binding.nrScanLayout.tvSSSINR4}; 
        tvTimingOffset = new AutofitTextView[]{binding.nrScanLayout.tvTimingOffset1, binding.nrScanLayout.tvTimingOffset2, binding.nrScanLayout.tvTimingOffset3, binding.nrScanLayout.tvTimingOffset4};
        tvTAE = new AutofitTextView[]{binding.nrScanLayout.tvTAE1, binding.nrScanLayout.tvTAE2, binding.nrScanLayout.tvTAE3, binding.nrScanLayout.tvTAE4};
    }

    private void initTextSize() {
        TableLayout tl = binding.nrScanLayout.tableNrScan;
        for (int i = 0; i < tl.getChildCount(); ++i) {
            View child = tl.getChildAt(i);
            if (child instanceof TableRow) {
                for (int c = 0; c < ((TableRow) child).getChildCount(); ++c) {
                    View t = ((TableRow) child).getChildAt(c);
                    if (t instanceof AutofitTextView) {
                        ((AutofitTextView)t).setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mActivity));
                        ((AutofitTextView)t).setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(11, mActivity));
                        ((AutofitTextView)t).setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(9, mActivity));
                    }
                }
            }
        }
    }

    public void clear() {
        String def = "--.--";

        int count = tvSSBFreq.length;
        for (int idx = 0; idx < count; ++idx) {
            tvSSBFreq[idx].setText(def);
            tvPCI[idx].setText(def);
            tvSSBIndex[idx].setText(def);
            tvSS_RSRP[idx].setText(def);
            tvSS_RSRQ[idx].setText(def);
            tvSS_SINR[idx].setText(def);
            tvTimingOffset[idx].setText(def);
            tvTAE[idx].setText(def);
        }
    }

    public void updateFreq() {
        mActivity.runOnUiThread(() -> {
            NrScanCarrierData data = DataHandler.getInstance().getNrScanData().getCarrierData();
            //tvFreq[idx].setText(data.getFreqToMHzString(idx, false));

            for (int i = 0; i < tvFreq.length; ++i) {
                tvFreq[i].setText(data.getFreqToMHzString(i, false));
            }
        });
    }

    public void update(final int idx) {
        mActivity.runOnUiThread(() -> {
            NrScanRcvItem item = DataHandler.getInstance().getNrScanData().getRcvData().get(idx);

            tvSSBFreq[idx].setText(item.getSSB_FrequencyToViewText());
            tvPCI[idx].setText(item.getPCIToViewText());
            tvSSBIndex[idx].setText(item.getSSB_IndexToViewText());
            tvSS_RSRP[idx].setText(item.getSS_RSRPToViewText());
            tvSS_RSRQ[idx].setText(item.getSS_RSRQToViewText());
            tvSS_SINR[idx].setText(item.getSS_SINRToViewText());
            tvTimingOffset[idx].setText(item.getTimingOffsetToViewText());
            tvTAE[idx].setText(item.getTAEToViewText());
        });
    }
}
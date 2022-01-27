package com.dabinsystems.pact_app.databinding;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DemodulationLayoutBindingImpl extends DemodulationLayoutBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.scatterChartTitle, 1);
        sViewsWithIds.put(R.id.scatterChart, 2);
        sViewsWithIds.put(R.id.tvCellInformationTitle, 3);
        sViewsWithIds.put(R.id.tvChannelBandWidthTitle, 4);
        sViewsWithIds.put(R.id.tv_physical_cell_id_title, 5);
        sViewsWithIds.put(R.id.tvGroupIdTitle, 6);
        sViewsWithIds.put(R.id.tvSectorTitle, 7);
        sViewsWithIds.put(R.id.tvSubcarrierSpacingTitle, 8);
        sViewsWithIds.put(R.id.tvSsbFreqTitle, 9);
        sViewsWithIds.put(R.id.tvChannelBandwidthValue, 10);
        sViewsWithIds.put(R.id.tv_physical_cell_id_value, 11);
        sViewsWithIds.put(R.id.tvGroupIdValue, 12);
        sViewsWithIds.put(R.id.tvSectorValue, 13);
        sViewsWithIds.put(R.id.tvSubcarrierSpacingValue, 14);
        sViewsWithIds.put(R.id.tvSsbFreqValue, 15);
        sViewsWithIds.put(R.id.tvPowerInfoTitle, 16);
        sViewsWithIds.put(R.id.tvRsrpTitle, 17);
        sViewsWithIds.put(R.id.tvRsrqTitle, 18);
        sViewsWithIds.put(R.id.tvSinrTitle, 19);
        sViewsWithIds.put(R.id.tvBeamPowerTitle, 20);
        sViewsWithIds.put(R.id.tvExpectedTxMaxPowerTitle, 21);
        sViewsWithIds.put(R.id.tvRsrpValue, 22);
        sViewsWithIds.put(R.id.tvRsrqValue, 23);
        sViewsWithIds.put(R.id.tvSinrValue, 24);
        sViewsWithIds.put(R.id.tvBeamPowerValue, 25);
        sViewsWithIds.put(R.id.tvExpectedTxMaxPowerValue, 26);
        sViewsWithIds.put(R.id.tvCandleChartTitle, 27);
        sViewsWithIds.put(R.id.candleChart, 28);
        sViewsWithIds.put(R.id.tvSignalQualityTitle, 29);
        sViewsWithIds.put(R.id.tvFreqOffsetTitle, 30);
        sViewsWithIds.put(R.id.tvThresholdTitle, 31);
        sViewsWithIds.put(R.id.tvPpmTitle, 32);
        sViewsWithIds.put(R.id.tvHzTitle, 33);
        sViewsWithIds.put(R.id.tvThresholdValue, 34);
        sViewsWithIds.put(R.id.tvPpmValue, 35);
        sViewsWithIds.put(R.id.tvHzValue, 36);
        sViewsWithIds.put(R.id.tvFreqOffsetResult, 37);
        sViewsWithIds.put(R.id.tvEVM, 38);
        sViewsWithIds.put(R.id.tvEvmTitle1, 39);
        sViewsWithIds.put(R.id.tvEvmTitle2, 40);
        sViewsWithIds.put(R.id.tvEVMThreshold, 41);
        sViewsWithIds.put(R.id.tvEvmAvg, 42);
        sViewsWithIds.put(R.id.tvEvmResult, 43);
        sViewsWithIds.put(R.id.tvChannelTitle, 44);
        sViewsWithIds.put(R.id.tvEVMTitle, 45);
        sViewsWithIds.put(R.id.tvPWRTitle, 46);
        sViewsWithIds.put(R.id.tvModTitle, 47);
        sViewsWithIds.put(R.id.tvRBTitle, 48);
        sViewsWithIds.put(R.id.tvPssTitle, 49);
        sViewsWithIds.put(R.id.tvPssEvmValue, 50);
        sViewsWithIds.put(R.id.tvPssPwrValue, 51);
        sViewsWithIds.put(R.id.tvPssModeValue, 52);
        sViewsWithIds.put(R.id.tvPssRbValue, 53);
        sViewsWithIds.put(R.id.tvSssTitle, 54);
        sViewsWithIds.put(R.id.tvSssEvmValue, 55);
        sViewsWithIds.put(R.id.tvSssPwrValue, 56);
        sViewsWithIds.put(R.id.tvSssModeValue, 57);
        sViewsWithIds.put(R.id.tvSssRbValue, 58);
        sViewsWithIds.put(R.id.tvPbchTitle, 59);
        sViewsWithIds.put(R.id.tvPbchEvmValue, 60);
        sViewsWithIds.put(R.id.tvPbchPwrValue, 61);
        sViewsWithIds.put(R.id.tvPbchModeValue, 62);
        sViewsWithIds.put(R.id.tvPbchRbValue, 63);
        sViewsWithIds.put(R.id.tvPbchDmrsTitle, 64);
        sViewsWithIds.put(R.id.tvPbchDmrsEvmValue, 65);
        sViewsWithIds.put(R.id.tvPbchDmrsPwrValue, 66);
        sViewsWithIds.put(R.id.tvPbchDmrsModeValue, 67);
        sViewsWithIds.put(R.id.tvPbchDmrsRbValue, 68);
        sViewsWithIds.put(R.id.timeInformationTableParent, 69);
        sViewsWithIds.put(R.id.tvTimeInformationTitle, 70);
        sViewsWithIds.put(R.id.tvTimingOffsetTitle, 71);
        sViewsWithIds.put(R.id.tvTimingOffsetValue, 72);
        sViewsWithIds.put(R.id.tvTaeType, 73);
        sViewsWithIds.put(R.id.tvThrdTitle, 74);
        sViewsWithIds.put(R.id.tvTaeUnit, 75);
        sViewsWithIds.put(R.id.tvThrdValue, 76);
        sViewsWithIds.put(R.id.tvTimeAlignmentErrorValue, 77);
        sViewsWithIds.put(R.id.tvTimeAlignmentErrorResult, 78);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DemodulationLayoutBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 79, sIncludes, sViewsWithIds));
    }
    private DemodulationLayoutBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.github.mikephil.charting.charts.CandleStickChart) bindings[28]
            , (android.widget.LinearLayout) bindings[0]
            , (com.github.mikephil.charting.charts.ScatterChart) bindings[2]
            , (me.grantland.widget.AutofitTextView) bindings[1]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[69]
            , (me.grantland.widget.AutofitTextView) bindings[20]
            , (me.grantland.widget.AutofitTextView) bindings[25]
            , (me.grantland.widget.AutofitTextView) bindings[27]
            , (me.grantland.widget.AutofitTextView) bindings[3]
            , (me.grantland.widget.AutofitTextView) bindings[4]
            , (me.grantland.widget.AutofitTextView) bindings[10]
            , (me.grantland.widget.AutofitTextView) bindings[44]
            , (me.grantland.widget.AutofitTextView) bindings[38]
            , (me.grantland.widget.AutofitTextView) bindings[41]
            , (me.grantland.widget.AutofitTextView) bindings[45]
            , (me.grantland.widget.AutofitTextView) bindings[42]
            , (me.grantland.widget.AutofitTextView) bindings[43]
            , (me.grantland.widget.AutofitTextView) bindings[39]
            , (me.grantland.widget.AutofitTextView) bindings[40]
            , (me.grantland.widget.AutofitTextView) bindings[21]
            , (me.grantland.widget.AutofitTextView) bindings[26]
            , (me.grantland.widget.AutofitTextView) bindings[37]
            , (me.grantland.widget.AutofitTextView) bindings[30]
            , (me.grantland.widget.AutofitTextView) bindings[6]
            , (me.grantland.widget.AutofitTextView) bindings[12]
            , (me.grantland.widget.AutofitTextView) bindings[33]
            , (me.grantland.widget.AutofitTextView) bindings[36]
            , (me.grantland.widget.AutofitTextView) bindings[47]
            , (me.grantland.widget.AutofitTextView) bindings[46]
            , (me.grantland.widget.AutofitTextView) bindings[65]
            , (me.grantland.widget.AutofitTextView) bindings[67]
            , (me.grantland.widget.AutofitTextView) bindings[66]
            , (me.grantland.widget.AutofitTextView) bindings[68]
            , (me.grantland.widget.AutofitTextView) bindings[64]
            , (me.grantland.widget.AutofitTextView) bindings[60]
            , (me.grantland.widget.AutofitTextView) bindings[62]
            , (me.grantland.widget.AutofitTextView) bindings[61]
            , (me.grantland.widget.AutofitTextView) bindings[63]
            , (me.grantland.widget.AutofitTextView) bindings[59]
            , (me.grantland.widget.AutofitTextView) bindings[5]
            , (me.grantland.widget.AutofitTextView) bindings[11]
            , (me.grantland.widget.AutofitTextView) bindings[16]
            , (me.grantland.widget.AutofitTextView) bindings[32]
            , (me.grantland.widget.AutofitTextView) bindings[35]
            , (me.grantland.widget.AutofitTextView) bindings[50]
            , (me.grantland.widget.AutofitTextView) bindings[52]
            , (me.grantland.widget.AutofitTextView) bindings[51]
            , (me.grantland.widget.AutofitTextView) bindings[53]
            , (me.grantland.widget.AutofitTextView) bindings[49]
            , (me.grantland.widget.AutofitTextView) bindings[48]
            , (me.grantland.widget.AutofitTextView) bindings[17]
            , (me.grantland.widget.AutofitTextView) bindings[22]
            , (me.grantland.widget.AutofitTextView) bindings[18]
            , (me.grantland.widget.AutofitTextView) bindings[23]
            , (me.grantland.widget.AutofitTextView) bindings[7]
            , (me.grantland.widget.AutofitTextView) bindings[13]
            , (me.grantland.widget.AutofitTextView) bindings[29]
            , (me.grantland.widget.AutofitTextView) bindings[19]
            , (me.grantland.widget.AutofitTextView) bindings[24]
            , (me.grantland.widget.AutofitTextView) bindings[9]
            , (me.grantland.widget.AutofitTextView) bindings[15]
            , (me.grantland.widget.AutofitTextView) bindings[55]
            , (me.grantland.widget.AutofitTextView) bindings[57]
            , (me.grantland.widget.AutofitTextView) bindings[56]
            , (me.grantland.widget.AutofitTextView) bindings[58]
            , (me.grantland.widget.AutofitTextView) bindings[54]
            , (me.grantland.widget.AutofitTextView) bindings[8]
            , (me.grantland.widget.AutofitTextView) bindings[14]
            , (me.grantland.widget.AutofitTextView) bindings[73]
            , (me.grantland.widget.AutofitTextView) bindings[75]
            , (me.grantland.widget.AutofitTextView) bindings[74]
            , (me.grantland.widget.AutofitTextView) bindings[76]
            , (me.grantland.widget.AutofitTextView) bindings[31]
            , (me.grantland.widget.AutofitTextView) bindings[34]
            , (me.grantland.widget.AutofitTextView) bindings[78]
            , (me.grantland.widget.AutofitTextView) bindings[77]
            , (me.grantland.widget.AutofitTextView) bindings[70]
            , (me.grantland.widget.AutofitTextView) bindings[71]
            , (me.grantland.widget.AutofitTextView) bindings[72]
            );
        this.linDemodulation.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}
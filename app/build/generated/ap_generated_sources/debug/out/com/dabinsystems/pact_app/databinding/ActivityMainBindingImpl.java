package com.dabinsystems.pact_app.databinding;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMainBindingImpl extends ActivityMainBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(109);
        sIncludes.setIncludes(1, 
            new String[] {"line_chart_layout", "demodulation_layout", "layout_5g_nr_scan", "lte_fdd_layout", "limit_msg_layout", "recall_msg_layout", "loading_msg_layout"},
            new int[] {2, 3, 4, 5, 6, 7, 8},
            new int[] {com.dabinsystems.pact_app.R.layout.line_chart_layout,
                com.dabinsystems.pact_app.R.layout.demodulation_layout,
                com.dabinsystems.pact_app.R.layout.layout_5g_nr_scan,
                com.dabinsystems.pact_app.R.layout.lte_fdd_layout,
                com.dabinsystems.pact_app.R.layout.limit_msg_layout,
                com.dabinsystems.pact_app.R.layout.recall_msg_layout,
                com.dabinsystems.pact_app.R.layout.loading_msg_layout});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.layoutTopMenu, 9);
        sViewsWithIds.put(R.id.tvPactLogo, 10);
        sViewsWithIds.put(R.id.tvTimeText, 11);
        sViewsWithIds.put(R.id.linTopMenu, 12);
        sViewsWithIds.put(R.id.tvSa, 13);
        sViewsWithIds.put(R.id.linTopIconMenu, 14);
        sViewsWithIds.put(R.id.tvWifiName, 15);
        sViewsWithIds.put(R.id.ivWifiImage, 16);
        sViewsWithIds.put(R.id.ivMacroImage, 17);
        sViewsWithIds.put(R.id.iv_preset, 18);
        sViewsWithIds.put(R.id.tvAutoScale, 19);
        sViewsWithIds.put(R.id.ivAutoScale, 20);
        sViewsWithIds.put(R.id.ivGPS, 21);
        sViewsWithIds.put(R.id.ivScreenshot, 22);
        sViewsWithIds.put(R.id.linBatteryBg, 23);
        sViewsWithIds.put(R.id.tvBattery, 24);
        sViewsWithIds.put(R.id.linBattery, 25);
        sViewsWithIds.put(R.id.ivChargeIcon, 26);
        sViewsWithIds.put(R.id.linMainTopSub, 27);
        sViewsWithIds.put(R.id.linTopSubBtn, 28);
        sViewsWithIds.put(R.id.linMarkerBox, 29);
        sViewsWithIds.put(R.id.tvMarker1, 30);
        sViewsWithIds.put(R.id.tvMarker2, 31);
        sViewsWithIds.put(R.id.tvMarker3, 32);
        sViewsWithIds.put(R.id.tvMarker4, 33);
        sViewsWithIds.put(R.id.tvMarker5, 34);
        sViewsWithIds.put(R.id.linTraceBox, 35);
        sViewsWithIds.put(R.id.linTrace1, 36);
        sViewsWithIds.put(R.id.tvTrace1, 37);
        sViewsWithIds.put(R.id.tvTraceMode1, 38);
        sViewsWithIds.put(R.id.tvTraceDetector1, 39);
        sViewsWithIds.put(R.id.linTrace2, 40);
        sViewsWithIds.put(R.id.tvTrace2, 41);
        sViewsWithIds.put(R.id.tvTraceMode2, 42);
        sViewsWithIds.put(R.id.tvTraceDetector2, 43);
        sViewsWithIds.put(R.id.linTrace3, 44);
        sViewsWithIds.put(R.id.tvTrace3, 45);
        sViewsWithIds.put(R.id.tvTraceMode3, 46);
        sViewsWithIds.put(R.id.tvTraceDetector3, 47);
        sViewsWithIds.put(R.id.linTrace4, 48);
        sViewsWithIds.put(R.id.tvTrace4, 49);
        sViewsWithIds.put(R.id.tvTraceMode4, 50);
        sViewsWithIds.put(R.id.tvTraceDetector4, 51);
        sViewsWithIds.put(R.id.conMarkerInfo, 52);
        sViewsWithIds.put(R.id.tvMarkerValLabel, 53);
        sViewsWithIds.put(R.id.tv_marker_x, 54);
        sViewsWithIds.put(R.id.tv_marker_y, 55);
        sViewsWithIds.put(R.id.tvRecallOff, 56);
        sViewsWithIds.put(R.id.linNrFwStatus, 57);
        sViewsWithIds.put(R.id.tvNrOvf, 58);
        sViewsWithIds.put(R.id.tvNrExt, 59);
        sViewsWithIds.put(R.id.tvNrSync, 60);
        sViewsWithIds.put(R.id.tvNrPre, 61);
        sViewsWithIds.put(R.id.tvNrTrig, 62);
        sViewsWithIds.put(R.id.tvSmallVswr, 63);
        sViewsWithIds.put(R.id.tvMode, 64);
        sViewsWithIds.put(R.id.linSaFwStatus, 65);
        sViewsWithIds.put(R.id.tvOvf, 66);
        sViewsWithIds.put(R.id.tvExt, 67);
        sViewsWithIds.put(R.id.tvGate, 68);
        sViewsWithIds.put(R.id.tvPre, 69);
        sViewsWithIds.put(R.id.tvMeas, 70);
        sViewsWithIds.put(R.id.tvSmallReturnLoss, 71);
        sViewsWithIds.put(R.id.conTitleParent, 72);
        sViewsWithIds.put(R.id.tvTopCenterTitle, 73);
        sViewsWithIds.put(R.id.linChartParent, 74);
        sViewsWithIds.put(R.id.linCalibration, 75);
        sViewsWithIds.put(R.id.tvCaliDesc, 76);
        sViewsWithIds.put(R.id.ivCaliStep, 77);
        sViewsWithIds.put(R.id.linBottomMenu, 78);
        sViewsWithIds.put(R.id.tvRecallMessage, 79);
        sViewsWithIds.put(R.id.linCableList, 80);
        sViewsWithIds.put(R.id.rvCableList, 81);
        sViewsWithIds.put(R.id.tvCableNameTitle, 82);
        sViewsWithIds.put(R.id.tvCableName, 83);
        sViewsWithIds.put(R.id.tvPropVelocityTitle, 84);
        sViewsWithIds.put(R.id.tvPropVelocity, 85);
        sViewsWithIds.put(R.id.tvFreq1Title, 86);
        sViewsWithIds.put(R.id.tvCableFreq1, 87);
        sViewsWithIds.put(R.id.tvCableFreq2, 88);
        sViewsWithIds.put(R.id.tvCableFreq3, 89);
        sViewsWithIds.put(R.id.tvLoss1Title, 90);
        sViewsWithIds.put(R.id.tvCableLoss1, 91);
        sViewsWithIds.put(R.id.tvCableLoss2, 92);
        sViewsWithIds.put(R.id.tvCableLoss3, 93);
        sViewsWithIds.put(R.id.tvFreq2Title, 94);
        sViewsWithIds.put(R.id.tvCableFreq4, 95);
        sViewsWithIds.put(R.id.tvCableFreq5, 96);
        sViewsWithIds.put(R.id.tvCableFreq6, 97);
        sViewsWithIds.put(R.id.tvLoss2Title, 98);
        sViewsWithIds.put(R.id.tvCableLoss4, 99);
        sViewsWithIds.put(R.id.tvCableLoss5, 100);
        sViewsWithIds.put(R.id.tvCableLoss6, 101);
        sViewsWithIds.put(R.id.linMainRight, 102);
        sViewsWithIds.put(R.id.linRightMenuTitle, 103);
        sViewsWithIds.put(R.id.tvRightMenuTitle, 104);
        sViewsWithIds.put(R.id.linRightMenu, 105);
        sViewsWithIds.put(R.id.tvBack, 106);
        sViewsWithIds.put(R.id.linQuickMenu, 107);
        sViewsWithIds.put(R.id.tvMainQuickSA, 108);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMainBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 109, sIncludes, sViewsWithIds));
    }
    private ActivityMainBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 7
            , (android.widget.LinearLayout) bindings[52]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[72]
            , (com.dabinsystems.pact_app.databinding.DemodulationLayoutBinding) bindings[3]
            , (android.widget.FrameLayout) bindings[1]
            , (android.widget.ImageView) bindings[20]
            , (android.widget.ImageView) bindings[77]
            , (android.widget.ImageView) bindings[26]
            , (android.widget.ImageView) bindings[21]
            , (android.widget.ImageView) bindings[17]
            , (android.widget.ImageView) bindings[18]
            , (android.widget.ImageView) bindings[22]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.LinearLayout) bindings[9]
            , (com.dabinsystems.pact_app.databinding.LimitMsgLayoutBinding) bindings[6]
            , (android.widget.LinearLayout) bindings[25]
            , (android.widget.LinearLayout) bindings[23]
            , (android.widget.LinearLayout) bindings[78]
            , (android.widget.LinearLayout) bindings[80]
            , (android.widget.LinearLayout) bindings[75]
            , (android.widget.LinearLayout) bindings[74]
            , (android.widget.LinearLayout) bindings[102]
            , (android.widget.FrameLayout) bindings[27]
            , (android.widget.LinearLayout) bindings[29]
            , (android.widget.LinearLayout) bindings[57]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.LinearLayout) bindings[107]
            , (android.widget.LinearLayout) bindings[105]
            , (android.widget.LinearLayout) bindings[103]
            , (android.widget.LinearLayout) bindings[65]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[28]
            , (android.widget.LinearLayout) bindings[36]
            , (android.widget.LinearLayout) bindings[40]
            , (android.widget.LinearLayout) bindings[44]
            , (android.widget.LinearLayout) bindings[48]
            , (android.widget.LinearLayout) bindings[35]
            , (com.dabinsystems.pact_app.databinding.LineChartLayoutBinding) bindings[2]
            , (com.dabinsystems.pact_app.databinding.LoadingMsgLayoutBinding) bindings[8]
            , (com.dabinsystems.pact_app.databinding.LteFddLayoutBinding) bindings[5]
            , (com.dabinsystems.pact_app.databinding.Layout5gNrScanBinding) bindings[4]
            , (com.dabinsystems.pact_app.databinding.RecallMsgLayoutBinding) bindings[7]
            , (androidx.recyclerview.widget.RecyclerView) bindings[81]
            , (me.grantland.widget.AutofitTextView) bindings[19]
            , (me.grantland.widget.AutofitTextView) bindings[106]
            , (me.grantland.widget.AutofitTextView) bindings[24]
            , (me.grantland.widget.AutofitTextView) bindings[87]
            , (me.grantland.widget.AutofitTextView) bindings[88]
            , (me.grantland.widget.AutofitTextView) bindings[89]
            , (me.grantland.widget.AutofitTextView) bindings[95]
            , (me.grantland.widget.AutofitTextView) bindings[96]
            , (me.grantland.widget.AutofitTextView) bindings[97]
            , (me.grantland.widget.AutofitTextView) bindings[91]
            , (me.grantland.widget.AutofitTextView) bindings[92]
            , (me.grantland.widget.AutofitTextView) bindings[93]
            , (me.grantland.widget.AutofitTextView) bindings[99]
            , (me.grantland.widget.AutofitTextView) bindings[100]
            , (me.grantland.widget.AutofitTextView) bindings[101]
            , (me.grantland.widget.AutofitTextView) bindings[83]
            , (me.grantland.widget.AutofitTextView) bindings[82]
            , (me.grantland.widget.AutofitTextView) bindings[76]
            , (me.grantland.widget.AutofitTextView) bindings[67]
            , (me.grantland.widget.AutofitTextView) bindings[86]
            , (me.grantland.widget.AutofitTextView) bindings[94]
            , (me.grantland.widget.AutofitTextView) bindings[68]
            , (me.grantland.widget.AutofitTextView) bindings[90]
            , (me.grantland.widget.AutofitTextView) bindings[98]
            , (android.widget.TextView) bindings[108]
            , (me.grantland.widget.AutofitTextView) bindings[30]
            , (me.grantland.widget.AutofitTextView) bindings[31]
            , (me.grantland.widget.AutofitTextView) bindings[32]
            , (me.grantland.widget.AutofitTextView) bindings[33]
            , (me.grantland.widget.AutofitTextView) bindings[34]
            , (me.grantland.widget.AutofitTextView) bindings[53]
            , (me.grantland.widget.AutofitTextView) bindings[54]
            , (me.grantland.widget.AutofitTextView) bindings[55]
            , (me.grantland.widget.AutofitTextView) bindings[70]
            , (me.grantland.widget.AutofitTextView) bindings[64]
            , (me.grantland.widget.AutofitTextView) bindings[59]
            , (me.grantland.widget.AutofitTextView) bindings[58]
            , (me.grantland.widget.AutofitTextView) bindings[61]
            , (me.grantland.widget.AutofitTextView) bindings[60]
            , (me.grantland.widget.AutofitTextView) bindings[62]
            , (me.grantland.widget.AutofitTextView) bindings[66]
            , (me.grantland.widget.AutofitTextView) bindings[10]
            , (me.grantland.widget.AutofitTextView) bindings[69]
            , (me.grantland.widget.AutofitTextView) bindings[85]
            , (me.grantland.widget.AutofitTextView) bindings[84]
            , (me.grantland.widget.AutofitTextView) bindings[79]
            , (me.grantland.widget.AutofitTextView) bindings[56]
            , (me.grantland.widget.AutofitTextView) bindings[104]
            , (me.grantland.widget.AutofitTextView) bindings[13]
            , (me.grantland.widget.AutofitTextView) bindings[71]
            , (me.grantland.widget.AutofitTextView) bindings[63]
            , (me.grantland.widget.AutofitTextView) bindings[11]
            , (me.grantland.widget.AutofitTextView) bindings[73]
            , (me.grantland.widget.AutofitTextView) bindings[37]
            , (me.grantland.widget.AutofitTextView) bindings[41]
            , (me.grantland.widget.AutofitTextView) bindings[45]
            , (me.grantland.widget.AutofitTextView) bindings[49]
            , (me.grantland.widget.AutofitTextView) bindings[39]
            , (me.grantland.widget.AutofitTextView) bindings[43]
            , (me.grantland.widget.AutofitTextView) bindings[47]
            , (me.grantland.widget.AutofitTextView) bindings[51]
            , (me.grantland.widget.AutofitTextView) bindings[38]
            , (me.grantland.widget.AutofitTextView) bindings[42]
            , (me.grantland.widget.AutofitTextView) bindings[46]
            , (me.grantland.widget.AutofitTextView) bindings[50]
            , (me.grantland.widget.AutofitTextView) bindings[15]
            );
        setContainedBinding(this.demodulationLayout);
        this.frameChartParent.setTag(null);
        setContainedBinding(this.limitMessageLayout);
        this.linParent.setTag(null);
        setContainedBinding(this.lineChartLayout);
        setContainedBinding(this.loadingWindowLayout);
        setContainedBinding(this.lteFddLayout);
        setContainedBinding(this.nrScanLayout);
        setContainedBinding(this.recallMessageLayout);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x80L;
        }
        lineChartLayout.invalidateAll();
        demodulationLayout.invalidateAll();
        nrScanLayout.invalidateAll();
        lteFddLayout.invalidateAll();
        limitMessageLayout.invalidateAll();
        recallMessageLayout.invalidateAll();
        loadingWindowLayout.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (lineChartLayout.hasPendingBindings()) {
            return true;
        }
        if (demodulationLayout.hasPendingBindings()) {
            return true;
        }
        if (nrScanLayout.hasPendingBindings()) {
            return true;
        }
        if (lteFddLayout.hasPendingBindings()) {
            return true;
        }
        if (limitMessageLayout.hasPendingBindings()) {
            return true;
        }
        if (recallMessageLayout.hasPendingBindings()) {
            return true;
        }
        if (loadingWindowLayout.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        lineChartLayout.setLifecycleOwner(lifecycleOwner);
        demodulationLayout.setLifecycleOwner(lifecycleOwner);
        nrScanLayout.setLifecycleOwner(lifecycleOwner);
        lteFddLayout.setLifecycleOwner(lifecycleOwner);
        limitMessageLayout.setLifecycleOwner(lifecycleOwner);
        recallMessageLayout.setLifecycleOwner(lifecycleOwner);
        loadingWindowLayout.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeLimitMessageLayout((com.dabinsystems.pact_app.databinding.LimitMsgLayoutBinding) object, fieldId);
            case 1 :
                return onChangeLoadingWindowLayout((com.dabinsystems.pact_app.databinding.LoadingMsgLayoutBinding) object, fieldId);
            case 2 :
                return onChangeLteFddLayout((com.dabinsystems.pact_app.databinding.LteFddLayoutBinding) object, fieldId);
            case 3 :
                return onChangeNrScanLayout((com.dabinsystems.pact_app.databinding.Layout5gNrScanBinding) object, fieldId);
            case 4 :
                return onChangeRecallMessageLayout((com.dabinsystems.pact_app.databinding.RecallMsgLayoutBinding) object, fieldId);
            case 5 :
                return onChangeLineChartLayout((com.dabinsystems.pact_app.databinding.LineChartLayoutBinding) object, fieldId);
            case 6 :
                return onChangeDemodulationLayout((com.dabinsystems.pact_app.databinding.DemodulationLayoutBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeLimitMessageLayout(com.dabinsystems.pact_app.databinding.LimitMsgLayoutBinding LimitMessageLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLoadingWindowLayout(com.dabinsystems.pact_app.databinding.LoadingMsgLayoutBinding LoadingWindowLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLteFddLayout(com.dabinsystems.pact_app.databinding.LteFddLayoutBinding LteFddLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeNrScanLayout(com.dabinsystems.pact_app.databinding.Layout5gNrScanBinding NrScanLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeRecallMessageLayout(com.dabinsystems.pact_app.databinding.RecallMsgLayoutBinding RecallMessageLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLineChartLayout(com.dabinsystems.pact_app.databinding.LineChartLayoutBinding LineChartLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeDemodulationLayout(com.dabinsystems.pact_app.databinding.DemodulationLayoutBinding DemodulationLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
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
        executeBindingsOn(lineChartLayout);
        executeBindingsOn(demodulationLayout);
        executeBindingsOn(nrScanLayout);
        executeBindingsOn(lteFddLayout);
        executeBindingsOn(limitMessageLayout);
        executeBindingsOn(recallMessageLayout);
        executeBindingsOn(loadingWindowLayout);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): limitMessageLayout
        flag 1 (0x2L): loadingWindowLayout
        flag 2 (0x3L): lteFddLayout
        flag 3 (0x4L): nrScanLayout
        flag 4 (0x5L): recallMessageLayout
        flag 5 (0x6L): lineChartLayout
        flag 6 (0x7L): demodulationLayout
        flag 7 (0x8L): null
    flag mapping end*/
    //end
}
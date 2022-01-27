package com.dabinsystems.pact_app.databinding;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LineChartLayoutBindingImpl extends LineChartLayoutBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(25);
        sIncludes.setIncludes(1, 
            new String[] {"swept_sa"},
            new int[] {12},
            new int[] {com.dabinsystems.pact_app.R.layout.swept_sa});
        sIncludes.setIncludes(2, 
            new String[] {"aclr_layout"},
            new int[] {13},
            new int[] {com.dabinsystems.pact_app.R.layout.aclr_layout});
        sIncludes.setIncludes(4, 
            new String[] {"sem_layout"},
            new int[] {14},
            new int[] {com.dabinsystems.pact_app.R.layout.sem_layout});
        sIncludes.setIncludes(5, 
            new String[] {"ch_power_layout"},
            new int[] {15},
            new int[] {com.dabinsystems.pact_app.R.layout.ch_power_layout});
        sIncludes.setIncludes(6, 
            new String[] {"occ_bw_layout"},
            new int[] {16},
            new int[] {com.dabinsystems.pact_app.R.layout.occ_bw_layout});
        sIncludes.setIncludes(7, 
            new String[] {"transmit_on_off_layout"},
            new int[] {17},
            new int[] {com.dabinsystems.pact_app.R.layout.transmit_on_off_layout});
        sIncludes.setIncludes(8, 
            new String[] {"interference_hunting_layout"},
            new int[] {18},
            new int[] {com.dabinsystems.pact_app.R.layout.interference_hunting_layout});
        sIncludes.setIncludes(9, 
            new String[] {"gate_layout"},
            new int[] {19},
            new int[] {com.dabinsystems.pact_app.R.layout.gate_layout});
        sIncludes.setIncludes(10, 
            new String[] {"marker_table_layout"},
            new int[] {20},
            new int[] {com.dabinsystems.pact_app.R.layout.marker_table_layout});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.spuriousEmission, 11);
        sViewsWithIds.put(R.id.tvDBM, 21);
        sViewsWithIds.put(R.id.linChartParent, 22);
        sViewsWithIds.put(R.id.main_line_chart, 23);
        sViewsWithIds.put(R.id.tvChartInfo, 24);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LineChartLayoutBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 25, sIncludes, sViewsWithIds));
    }
    private LineChartLayoutBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 9
            , (com.dabinsystems.pact_app.databinding.AclrLayoutBinding) bindings[13]
            , (com.dabinsystems.pact_app.databinding.ChPowerLayoutBinding) bindings[15]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[5]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[1]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[8]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[10]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[6]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[3]
            , (com.dabinsystems.pact_app.databinding.GateLayoutBinding) bindings[19]
            , (com.dabinsystems.pact_app.databinding.InterferenceHuntingLayoutBinding) bindings[18]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[22]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.LinearLayout) bindings[7]
            , (com.github.mikephil.charting.charts.LineChart) bindings[23]
            , (com.dabinsystems.pact_app.databinding.MarkerTableLayoutBinding) bindings[20]
            , (com.dabinsystems.pact_app.databinding.OccBwLayoutBinding) bindings[16]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (com.dabinsystems.pact_app.databinding.SemLayoutBinding) bindings[14]
            , (android.view.View) bindings[11]
            , (com.dabinsystems.pact_app.databinding.SweptSaBinding) bindings[12]
            , (com.dabinsystems.pact_app.databinding.TransmitOnOffLayoutBinding) bindings[17]
            , (me.grantland.widget.AutofitTextView) bindings[24]
            , (me.grantland.widget.AutofitTextView) bindings[21]
            );
        setContainedBinding(this.aclrInfo);
        setContainedBinding(this.channelPowerLayout);
        this.conChannelPower.setTag(null);
        this.conChartInfo.setTag(null);
        this.conInterferenceInfo.setTag(null);
        this.conMarkerTableParent.setTag(null);
        this.conOccupiedBW.setTag(null);
        this.conSpuriousInfoParent.setTag(null);
        setContainedBinding(this.gateLayout);
        setContainedBinding(this.interferenceInfo);
        this.linAclrInfo.setTag(null);
        this.linGateView.setTag(null);
        this.linSemInfo.setTag(null);
        this.linTransmitOnOffInfo.setTag(null);
        setContainedBinding(this.markerTableLayout);
        setContainedBinding(this.occBwLayout);
        this.parent.setTag(null);
        setContainedBinding(this.semLayout);
        setContainedBinding(this.sweptSaInfo);
        setContainedBinding(this.transmitOnOffLayout);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x200L;
        }
        sweptSaInfo.invalidateAll();
        aclrInfo.invalidateAll();
        semLayout.invalidateAll();
        channelPowerLayout.invalidateAll();
        occBwLayout.invalidateAll();
        transmitOnOffLayout.invalidateAll();
        interferenceInfo.invalidateAll();
        gateLayout.invalidateAll();
        markerTableLayout.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (sweptSaInfo.hasPendingBindings()) {
            return true;
        }
        if (aclrInfo.hasPendingBindings()) {
            return true;
        }
        if (semLayout.hasPendingBindings()) {
            return true;
        }
        if (channelPowerLayout.hasPendingBindings()) {
            return true;
        }
        if (occBwLayout.hasPendingBindings()) {
            return true;
        }
        if (transmitOnOffLayout.hasPendingBindings()) {
            return true;
        }
        if (interferenceInfo.hasPendingBindings()) {
            return true;
        }
        if (gateLayout.hasPendingBindings()) {
            return true;
        }
        if (markerTableLayout.hasPendingBindings()) {
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
        sweptSaInfo.setLifecycleOwner(lifecycleOwner);
        aclrInfo.setLifecycleOwner(lifecycleOwner);
        semLayout.setLifecycleOwner(lifecycleOwner);
        channelPowerLayout.setLifecycleOwner(lifecycleOwner);
        occBwLayout.setLifecycleOwner(lifecycleOwner);
        transmitOnOffLayout.setLifecycleOwner(lifecycleOwner);
        interferenceInfo.setLifecycleOwner(lifecycleOwner);
        gateLayout.setLifecycleOwner(lifecycleOwner);
        markerTableLayout.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeSemLayout((com.dabinsystems.pact_app.databinding.SemLayoutBinding) object, fieldId);
            case 1 :
                return onChangeChannelPowerLayout((com.dabinsystems.pact_app.databinding.ChPowerLayoutBinding) object, fieldId);
            case 2 :
                return onChangeTransmitOnOffLayout((com.dabinsystems.pact_app.databinding.TransmitOnOffLayoutBinding) object, fieldId);
            case 3 :
                return onChangeAclrInfo((com.dabinsystems.pact_app.databinding.AclrLayoutBinding) object, fieldId);
            case 4 :
                return onChangeSweptSaInfo((com.dabinsystems.pact_app.databinding.SweptSaBinding) object, fieldId);
            case 5 :
                return onChangeOccBwLayout((com.dabinsystems.pact_app.databinding.OccBwLayoutBinding) object, fieldId);
            case 6 :
                return onChangeInterferenceInfo((com.dabinsystems.pact_app.databinding.InterferenceHuntingLayoutBinding) object, fieldId);
            case 7 :
                return onChangeGateLayout((com.dabinsystems.pact_app.databinding.GateLayoutBinding) object, fieldId);
            case 8 :
                return onChangeMarkerTableLayout((com.dabinsystems.pact_app.databinding.MarkerTableLayoutBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeSemLayout(com.dabinsystems.pact_app.databinding.SemLayoutBinding SemLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeChannelPowerLayout(com.dabinsystems.pact_app.databinding.ChPowerLayoutBinding ChannelPowerLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeTransmitOnOffLayout(com.dabinsystems.pact_app.databinding.TransmitOnOffLayoutBinding TransmitOnOffLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeAclrInfo(com.dabinsystems.pact_app.databinding.AclrLayoutBinding AclrInfo, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeSweptSaInfo(com.dabinsystems.pact_app.databinding.SweptSaBinding SweptSaInfo, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeOccBwLayout(com.dabinsystems.pact_app.databinding.OccBwLayoutBinding OccBwLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeInterferenceInfo(com.dabinsystems.pact_app.databinding.InterferenceHuntingLayoutBinding InterferenceInfo, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeGateLayout(com.dabinsystems.pact_app.databinding.GateLayoutBinding GateLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMarkerTableLayout(com.dabinsystems.pact_app.databinding.MarkerTableLayoutBinding MarkerTableLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
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
        executeBindingsOn(sweptSaInfo);
        executeBindingsOn(aclrInfo);
        executeBindingsOn(semLayout);
        executeBindingsOn(channelPowerLayout);
        executeBindingsOn(occBwLayout);
        executeBindingsOn(transmitOnOffLayout);
        executeBindingsOn(interferenceInfo);
        executeBindingsOn(gateLayout);
        executeBindingsOn(markerTableLayout);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): semLayout
        flag 1 (0x2L): channelPowerLayout
        flag 2 (0x3L): transmitOnOffLayout
        flag 3 (0x4L): aclrInfo
        flag 4 (0x5L): sweptSaInfo
        flag 5 (0x6L): occBwLayout
        flag 6 (0x7L): interferenceInfo
        flag 7 (0x8L): gateLayout
        flag 8 (0x9L): markerTableLayout
        flag 9 (0xaL): null
    flag mapping end*/
    //end
}
package com.dabinsystems.pact_app.databinding;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class GateLayoutBindingImpl extends GateLayoutBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.gateLineChart, 1);
        sViewsWithIds.put(R.id.gateLinkChart, 2);
        sViewsWithIds.put(R.id.tvGateXOffset, 3);
        sViewsWithIds.put(R.id.layerLinkChartLabel, 4);
        sViewsWithIds.put(R.id.ivDownlinkIcon, 5);
        sViewsWithIds.put(R.id.tvDownlink, 6);
        sViewsWithIds.put(R.id.ivUplinkIcon, 7);
        sViewsWithIds.put(R.id.tvUplink, 8);
        sViewsWithIds.put(R.id.tvTimeSpanVal, 9);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public GateLayoutBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private GateLayoutBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.github.mikephil.charting.charts.LineChart) bindings[1]
            , (com.github.mikephil.charting.charts.LineChart) bindings[2]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.LinearLayout) bindings[4]
            , (me.grantland.widget.AutofitTextView) bindings[6]
            , (me.grantland.widget.AutofitTextView) bindings[3]
            , (me.grantland.widget.AutofitTextView) bindings[9]
            , (me.grantland.widget.AutofitTextView) bindings[8]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
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
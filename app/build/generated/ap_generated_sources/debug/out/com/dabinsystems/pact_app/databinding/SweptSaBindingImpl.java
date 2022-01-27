package com.dabinsystems.pact_app.databinding;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class SweptSaBindingImpl extends SweptSaBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.gOffset, 1);
        sViewsWithIds.put(R.id.conRef, 2);
        sViewsWithIds.put(R.id.tvRef, 3);
        sViewsWithIds.put(R.id.tvRefVal, 4);
        sViewsWithIds.put(R.id.conOffset, 5);
        sViewsWithIds.put(R.id.tvOffset, 6);
        sViewsWithIds.put(R.id.tvOffsetVal, 7);
        sViewsWithIds.put(R.id.conAtten, 8);
        sViewsWithIds.put(R.id.tvAtten, 9);
        sViewsWithIds.put(R.id.tvAttenVal, 10);
        sViewsWithIds.put(R.id.gMarkerTable, 11);
        sViewsWithIds.put(R.id.gHorizonCenter, 12);
        sViewsWithIds.put(R.id.conVbw, 13);
        sViewsWithIds.put(R.id.tvVbwLabel, 14);
        sViewsWithIds.put(R.id.tvVbwVal, 15);
        sViewsWithIds.put(R.id.gLeftBottom, 16);
        sViewsWithIds.put(R.id.gRightBottom, 17);
        sViewsWithIds.put(R.id.gBottomHorizontal, 18);
        sViewsWithIds.put(R.id.conRbw, 19);
        sViewsWithIds.put(R.id.tvRbwLabel, 20);
        sViewsWithIds.put(R.id.tvRbwVal, 21);
        sViewsWithIds.put(R.id.conSpan, 22);
        sViewsWithIds.put(R.id.tvSpanLabel, 23);
        sViewsWithIds.put(R.id.tvSpanVal, 24);
        sViewsWithIds.put(R.id.conSweep, 25);
        sViewsWithIds.put(R.id.tvSweepLabel, 26);
        sViewsWithIds.put(R.id.tvSweepVal, 27);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public SweptSaBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 28, sIncludes, sViewsWithIds));
    }
    private SweptSaBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[8]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[5]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[19]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[2]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[22]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[25]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[13]
            , (androidx.constraintlayout.widget.Guideline) bindings[18]
            , (androidx.constraintlayout.widget.Guideline) bindings[12]
            , (androidx.constraintlayout.widget.Guideline) bindings[16]
            , (androidx.constraintlayout.widget.Guideline) bindings[11]
            , (androidx.constraintlayout.widget.Guideline) bindings[1]
            , (androidx.constraintlayout.widget.Guideline) bindings[17]
            , (me.grantland.widget.AutofitTextView) bindings[9]
            , (me.grantland.widget.AutofitTextView) bindings[10]
            , (me.grantland.widget.AutofitTextView) bindings[6]
            , (me.grantland.widget.AutofitTextView) bindings[7]
            , (me.grantland.widget.AutofitTextView) bindings[20]
            , (me.grantland.widget.AutofitTextView) bindings[21]
            , (me.grantland.widget.AutofitTextView) bindings[3]
            , (me.grantland.widget.AutofitTextView) bindings[4]
            , (me.grantland.widget.AutofitTextView) bindings[23]
            , (me.grantland.widget.AutofitTextView) bindings[24]
            , (me.grantland.widget.AutofitTextView) bindings[26]
            , (me.grantland.widget.AutofitTextView) bindings[27]
            , (me.grantland.widget.AutofitTextView) bindings[14]
            , (me.grantland.widget.AutofitTextView) bindings[15]
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
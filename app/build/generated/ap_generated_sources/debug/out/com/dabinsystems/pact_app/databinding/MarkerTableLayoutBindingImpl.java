package com.dabinsystems.pact_app.databinding;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class MarkerTableLayoutBindingImpl extends MarkerTableLayoutBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.conLeftMarkerTable, 1);
        sViewsWithIds.put(R.id.conMarkerTable1, 2);
        sViewsWithIds.put(R.id.gTrace1, 3);
        sViewsWithIds.put(R.id.gX1, 4);
        sViewsWithIds.put(R.id.gY1, 5);
        sViewsWithIds.put(R.id.tvTableMarkerName1, 6);
        sViewsWithIds.put(R.id.tvTableMarkerTrace1, 7);
        sViewsWithIds.put(R.id.tvTableMarkerX1, 8);
        sViewsWithIds.put(R.id.tvTableMarkerY1, 9);
        sViewsWithIds.put(R.id.conMarkerTable2, 10);
        sViewsWithIds.put(R.id.gTrace2, 11);
        sViewsWithIds.put(R.id.gX2, 12);
        sViewsWithIds.put(R.id.gY2, 13);
        sViewsWithIds.put(R.id.tvTableMarkerName2, 14);
        sViewsWithIds.put(R.id.tvTableMarkerTrace2, 15);
        sViewsWithIds.put(R.id.tvTableMarkerX2, 16);
        sViewsWithIds.put(R.id.tvTableMarkerY2, 17);
        sViewsWithIds.put(R.id.conMarkerTable3, 18);
        sViewsWithIds.put(R.id.gTrace3, 19);
        sViewsWithIds.put(R.id.gX3, 20);
        sViewsWithIds.put(R.id.gY3, 21);
        sViewsWithIds.put(R.id.tvTableMarkerName3, 22);
        sViewsWithIds.put(R.id.tvTableMarkerTrace3, 23);
        sViewsWithIds.put(R.id.tvTableMarkerX3, 24);
        sViewsWithIds.put(R.id.tvTableMarkerY3, 25);
        sViewsWithIds.put(R.id.conRightMarkerTable, 26);
        sViewsWithIds.put(R.id.conMarkerTable4, 27);
        sViewsWithIds.put(R.id.gTrace4, 28);
        sViewsWithIds.put(R.id.gX4, 29);
        sViewsWithIds.put(R.id.gY4, 30);
        sViewsWithIds.put(R.id.tvTableMarkerName4, 31);
        sViewsWithIds.put(R.id.tvTableMarkerTrace4, 32);
        sViewsWithIds.put(R.id.tvTableMarkerX4, 33);
        sViewsWithIds.put(R.id.tvTableMarkerY4, 34);
        sViewsWithIds.put(R.id.conMarkerTable5, 35);
        sViewsWithIds.put(R.id.gTrace5, 36);
        sViewsWithIds.put(R.id.gX5, 37);
        sViewsWithIds.put(R.id.gY5, 38);
        sViewsWithIds.put(R.id.tvTableMarkerName5, 39);
        sViewsWithIds.put(R.id.tvTableMarkerTrace5, 40);
        sViewsWithIds.put(R.id.tvTableMarkerX5, 41);
        sViewsWithIds.put(R.id.tvTableMarkerY5, 42);
        sViewsWithIds.put(R.id.conMarkerTable6, 43);
        sViewsWithIds.put(R.id.gTrace6, 44);
        sViewsWithIds.put(R.id.gX6, 45);
        sViewsWithIds.put(R.id.gY6, 46);
        sViewsWithIds.put(R.id.tvTableMarkerName6, 47);
        sViewsWithIds.put(R.id.tvTableMarkerTrace6, 48);
        sViewsWithIds.put(R.id.tvTableMarkerX6, 49);
        sViewsWithIds.put(R.id.tvTableMarkerY6, 50);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public MarkerTableLayoutBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 51, sIncludes, sViewsWithIds));
    }
    private MarkerTableLayoutBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[1]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[2]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[10]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[18]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[27]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[35]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[43]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[26]
            , (androidx.constraintlayout.widget.Guideline) bindings[3]
            , (androidx.constraintlayout.widget.Guideline) bindings[11]
            , (androidx.constraintlayout.widget.Guideline) bindings[19]
            , (androidx.constraintlayout.widget.Guideline) bindings[28]
            , (androidx.constraintlayout.widget.Guideline) bindings[36]
            , (androidx.constraintlayout.widget.Guideline) bindings[44]
            , (androidx.constraintlayout.widget.Guideline) bindings[4]
            , (androidx.constraintlayout.widget.Guideline) bindings[12]
            , (androidx.constraintlayout.widget.Guideline) bindings[20]
            , (androidx.constraintlayout.widget.Guideline) bindings[29]
            , (androidx.constraintlayout.widget.Guideline) bindings[37]
            , (androidx.constraintlayout.widget.Guideline) bindings[45]
            , (androidx.constraintlayout.widget.Guideline) bindings[5]
            , (androidx.constraintlayout.widget.Guideline) bindings[13]
            , (androidx.constraintlayout.widget.Guideline) bindings[21]
            , (androidx.constraintlayout.widget.Guideline) bindings[30]
            , (androidx.constraintlayout.widget.Guideline) bindings[38]
            , (androidx.constraintlayout.widget.Guideline) bindings[46]
            , (me.grantland.widget.AutofitTextView) bindings[6]
            , (me.grantland.widget.AutofitTextView) bindings[14]
            , (me.grantland.widget.AutofitTextView) bindings[22]
            , (me.grantland.widget.AutofitTextView) bindings[31]
            , (me.grantland.widget.AutofitTextView) bindings[39]
            , (me.grantland.widget.AutofitTextView) bindings[47]
            , (me.grantland.widget.AutofitTextView) bindings[7]
            , (me.grantland.widget.AutofitTextView) bindings[15]
            , (me.grantland.widget.AutofitTextView) bindings[23]
            , (me.grantland.widget.AutofitTextView) bindings[32]
            , (me.grantland.widget.AutofitTextView) bindings[40]
            , (me.grantland.widget.AutofitTextView) bindings[48]
            , (me.grantland.widget.AutofitTextView) bindings[8]
            , (me.grantland.widget.AutofitTextView) bindings[16]
            , (me.grantland.widget.AutofitTextView) bindings[24]
            , (me.grantland.widget.AutofitTextView) bindings[33]
            , (me.grantland.widget.AutofitTextView) bindings[41]
            , (me.grantland.widget.AutofitTextView) bindings[49]
            , (me.grantland.widget.AutofitTextView) bindings[9]
            , (me.grantland.widget.AutofitTextView) bindings[17]
            , (me.grantland.widget.AutofitTextView) bindings[25]
            , (me.grantland.widget.AutofitTextView) bindings[34]
            , (me.grantland.widget.AutofitTextView) bindings[42]
            , (me.grantland.widget.AutofitTextView) bindings[50]
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
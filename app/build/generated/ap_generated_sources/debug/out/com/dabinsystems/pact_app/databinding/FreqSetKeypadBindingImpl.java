package com.dabinsystems.pact_app.databinding;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FreqSetKeypadBindingImpl extends FreqSetKeypadBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linKeypadParent, 1);
        sViewsWithIds.put(R.id.tvStart, 2);
        sViewsWithIds.put(R.id.edStartFreq, 3);
        sViewsWithIds.put(R.id.tvStop, 4);
        sViewsWithIds.put(R.id.edStopFreq, 5);
        sViewsWithIds.put(R.id.linKeypad, 6);
        sViewsWithIds.put(R.id.tvGhz, 7);
        sViewsWithIds.put(R.id.tvMhz, 8);
        sViewsWithIds.put(R.id.tvKhz, 9);
        sViewsWithIds.put(R.id.tvHz, 10);
        sViewsWithIds.put(R.id.tvEnter, 11);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FreqSetKeypadBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private FreqSetKeypadBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatEditText) bindings[3]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[5]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[0]
            , (me.grantland.widget.AutofitTextView) bindings[11]
            , (me.grantland.widget.AutofitTextView) bindings[7]
            , (me.grantland.widget.AutofitTextView) bindings[10]
            , (me.grantland.widget.AutofitTextView) bindings[9]
            , (me.grantland.widget.AutofitTextView) bindings[8]
            , (me.grantland.widget.AutofitTextView) bindings[2]
            , (me.grantland.widget.AutofitTextView) bindings[4]
            );
        this.parent.setTag(null);
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
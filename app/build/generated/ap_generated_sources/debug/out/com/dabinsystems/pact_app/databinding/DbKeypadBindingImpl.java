package com.dabinsystems.pact_app.databinding;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DbKeypadBindingImpl extends DbKeypadBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linKeypadParent, 1);
        sViewsWithIds.put(R.id.tvView, 2);
        sViewsWithIds.put(R.id.linKeypad, 3);
        sViewsWithIds.put(R.id.tvValue1, 4);
        sViewsWithIds.put(R.id.tvValue2, 5);
        sViewsWithIds.put(R.id.tvValue3, 6);
        sViewsWithIds.put(R.id.tvValue4, 7);
        sViewsWithIds.put(R.id.tvValue5, 8);
        sViewsWithIds.put(R.id.tvValue6, 9);
        sViewsWithIds.put(R.id.tvValue7, 10);
        sViewsWithIds.put(R.id.tvValue8, 11);
        sViewsWithIds.put(R.id.tvValue9, 12);
        sViewsWithIds.put(R.id.tvPlusMinus, 13);
        sViewsWithIds.put(R.id.tvValue0, 14);
        sViewsWithIds.put(R.id.tvDot, 15);
        sViewsWithIds.put(R.id.ivBackspace, 16);
        sViewsWithIds.put(R.id.tvdB, 17);
        sViewsWithIds.put(R.id.tvEnter, 18);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DbKeypadBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private DbKeypadBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[16]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[0]
            , (me.grantland.widget.AutofitTextView) bindings[15]
            , (me.grantland.widget.AutofitTextView) bindings[18]
            , (me.grantland.widget.AutofitTextView) bindings[13]
            , (me.grantland.widget.AutofitTextView) bindings[14]
            , (me.grantland.widget.AutofitTextView) bindings[4]
            , (me.grantland.widget.AutofitTextView) bindings[5]
            , (me.grantland.widget.AutofitTextView) bindings[6]
            , (me.grantland.widget.AutofitTextView) bindings[7]
            , (me.grantland.widget.AutofitTextView) bindings[8]
            , (me.grantland.widget.AutofitTextView) bindings[9]
            , (me.grantland.widget.AutofitTextView) bindings[10]
            , (me.grantland.widget.AutofitTextView) bindings[11]
            , (me.grantland.widget.AutofitTextView) bindings[12]
            , (me.grantland.widget.AutofitTextView) bindings[2]
            , (me.grantland.widget.AutofitTextView) bindings[17]
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
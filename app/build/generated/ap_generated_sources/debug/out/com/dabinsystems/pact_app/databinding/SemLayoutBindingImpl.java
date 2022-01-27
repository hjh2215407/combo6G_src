package com.dabinsystems.pact_app.databinding;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class SemLayoutBindingImpl extends SemLayoutBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tvSemCenterFreqTitle, 1);
        sViewsWithIds.put(R.id.tvSemCenterFreqVal, 2);
        sViewsWithIds.put(R.id.tvAtten, 3);
        sViewsWithIds.put(R.id.tvAttenVal, 4);
        sViewsWithIds.put(R.id.tvOffset, 5);
        sViewsWithIds.put(R.id.tvOffsetVal, 6);
        sViewsWithIds.put(R.id.tvSemSpanFreqTitle, 7);
        sViewsWithIds.put(R.id.tvSemSpanFreqVal, 8);
        sViewsWithIds.put(R.id.tvSemChPwrTitle, 9);
        sViewsWithIds.put(R.id.tvSemChPwrVal, 10);
        sViewsWithIds.put(R.id.tvSemStartFreqTitle, 11);
        sViewsWithIds.put(R.id.tvSemStopFreqTitle, 12);
        sViewsWithIds.put(R.id.tvSemMaskRBW, 13);
        sViewsWithIds.put(R.id.tvSemStartFreq1, 14);
        sViewsWithIds.put(R.id.tvSemStopFreq1, 15);
        sViewsWithIds.put(R.id.tvSemMaskRBW1, 16);
        sViewsWithIds.put(R.id.tvSemStartFreq2, 17);
        sViewsWithIds.put(R.id.tvSemStopFreq2, 18);
        sViewsWithIds.put(R.id.tvSemMaskRBW2, 19);
        sViewsWithIds.put(R.id.tvSemStartFreq3, 20);
        sViewsWithIds.put(R.id.tvSemStopFreq3, 21);
        sViewsWithIds.put(R.id.tvSemMaskRBW3, 22);
        sViewsWithIds.put(R.id.tvSemStartFreq4, 23);
        sViewsWithIds.put(R.id.tvSemStopFreq4, 24);
        sViewsWithIds.put(R.id.tvSemMaskRBW4, 25);
        sViewsWithIds.put(R.id.tvSemDensityTitle, 26);
        sViewsWithIds.put(R.id.tvSemDensityVal, 27);
        sViewsWithIds.put(R.id.tvSemLowerTitle, 28);
        sViewsWithIds.put(R.id.tvSemLowerPkPwrTitle, 29);
        sViewsWithIds.put(R.id.tvSemLowerLimitTitle, 30);
        sViewsWithIds.put(R.id.tvSemLowerFreqTitle, 31);
        sViewsWithIds.put(R.id.tvSemLowerPkPwr1, 32);
        sViewsWithIds.put(R.id.tvSemLowerLimit1, 33);
        sViewsWithIds.put(R.id.tvSemLowerFreq1, 34);
        sViewsWithIds.put(R.id.tvSemLowerPkPwr2, 35);
        sViewsWithIds.put(R.id.tvSemLowerLimit2, 36);
        sViewsWithIds.put(R.id.tvSemLowerFreq2, 37);
        sViewsWithIds.put(R.id.tvSemLowerPkPwr3, 38);
        sViewsWithIds.put(R.id.tvSemLowerLimit3, 39);
        sViewsWithIds.put(R.id.tvSemLowerFreq3, 40);
        sViewsWithIds.put(R.id.tvSemLowerPkPwr4, 41);
        sViewsWithIds.put(R.id.tvSemLowerLimit4, 42);
        sViewsWithIds.put(R.id.tvSemLowerFreq4, 43);
        sViewsWithIds.put(R.id.tvSemPassTitle, 44);
        sViewsWithIds.put(R.id.tvSemUpperTitle, 45);
        sViewsWithIds.put(R.id.tvSemUpperPkPwrTitle, 46);
        sViewsWithIds.put(R.id.tvSemUpperLimitTitle, 47);
        sViewsWithIds.put(R.id.tvSemUpperFreqTitle, 48);
        sViewsWithIds.put(R.id.tvSemUpperPkPwr1, 49);
        sViewsWithIds.put(R.id.tvSemUpperLimit1, 50);
        sViewsWithIds.put(R.id.tvSemUpperFreq1, 51);
        sViewsWithIds.put(R.id.tvSemUpperPkPwr2, 52);
        sViewsWithIds.put(R.id.tvSemUpperLimit2, 53);
        sViewsWithIds.put(R.id.tvSemUpperFreq2, 54);
        sViewsWithIds.put(R.id.tvSemUpperPkPwr3, 55);
        sViewsWithIds.put(R.id.tvSemUpperLimit3, 56);
        sViewsWithIds.put(R.id.tvSemUpperFreq3, 57);
        sViewsWithIds.put(R.id.tvSemUpperPkPwr4, 58);
        sViewsWithIds.put(R.id.tvSemUpperLimit4, 59);
        sViewsWithIds.put(R.id.tvSemUpperFreq4, 60);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public SemLayoutBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 61, sIncludes, sViewsWithIds));
    }
    private SemLayoutBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (me.grantland.widget.AutofitTextView) bindings[3]
            , (me.grantland.widget.AutofitTextView) bindings[4]
            , (me.grantland.widget.AutofitTextView) bindings[5]
            , (me.grantland.widget.AutofitTextView) bindings[6]
            , (me.grantland.widget.AutofitTextView) bindings[1]
            , (me.grantland.widget.AutofitTextView) bindings[2]
            , (me.grantland.widget.AutofitTextView) bindings[9]
            , (me.grantland.widget.AutofitTextView) bindings[10]
            , (me.grantland.widget.AutofitTextView) bindings[26]
            , (me.grantland.widget.AutofitTextView) bindings[27]
            , (me.grantland.widget.AutofitTextView) bindings[34]
            , (me.grantland.widget.AutofitTextView) bindings[37]
            , (me.grantland.widget.AutofitTextView) bindings[40]
            , (me.grantland.widget.AutofitTextView) bindings[43]
            , (me.grantland.widget.AutofitTextView) bindings[31]
            , (me.grantland.widget.AutofitTextView) bindings[33]
            , (me.grantland.widget.AutofitTextView) bindings[36]
            , (me.grantland.widget.AutofitTextView) bindings[39]
            , (me.grantland.widget.AutofitTextView) bindings[42]
            , (me.grantland.widget.AutofitTextView) bindings[30]
            , (me.grantland.widget.AutofitTextView) bindings[32]
            , (me.grantland.widget.AutofitTextView) bindings[35]
            , (me.grantland.widget.AutofitTextView) bindings[38]
            , (me.grantland.widget.AutofitTextView) bindings[41]
            , (me.grantland.widget.AutofitTextView) bindings[29]
            , (me.grantland.widget.AutofitTextView) bindings[28]
            , (me.grantland.widget.AutofitTextView) bindings[13]
            , (me.grantland.widget.AutofitTextView) bindings[16]
            , (me.grantland.widget.AutofitTextView) bindings[19]
            , (me.grantland.widget.AutofitTextView) bindings[22]
            , (me.grantland.widget.AutofitTextView) bindings[25]
            , (me.grantland.widget.AutofitTextView) bindings[44]
            , (me.grantland.widget.AutofitTextView) bindings[7]
            , (me.grantland.widget.AutofitTextView) bindings[8]
            , (me.grantland.widget.AutofitTextView) bindings[14]
            , (me.grantland.widget.AutofitTextView) bindings[17]
            , (me.grantland.widget.AutofitTextView) bindings[20]
            , (me.grantland.widget.AutofitTextView) bindings[23]
            , (me.grantland.widget.AutofitTextView) bindings[11]
            , (me.grantland.widget.AutofitTextView) bindings[15]
            , (me.grantland.widget.AutofitTextView) bindings[18]
            , (me.grantland.widget.AutofitTextView) bindings[21]
            , (me.grantland.widget.AutofitTextView) bindings[24]
            , (me.grantland.widget.AutofitTextView) bindings[12]
            , (me.grantland.widget.AutofitTextView) bindings[51]
            , (me.grantland.widget.AutofitTextView) bindings[54]
            , (me.grantland.widget.AutofitTextView) bindings[57]
            , (me.grantland.widget.AutofitTextView) bindings[60]
            , (me.grantland.widget.AutofitTextView) bindings[48]
            , (me.grantland.widget.AutofitTextView) bindings[50]
            , (me.grantland.widget.AutofitTextView) bindings[53]
            , (me.grantland.widget.AutofitTextView) bindings[56]
            , (me.grantland.widget.AutofitTextView) bindings[59]
            , (me.grantland.widget.AutofitTextView) bindings[47]
            , (me.grantland.widget.AutofitTextView) bindings[49]
            , (me.grantland.widget.AutofitTextView) bindings[52]
            , (me.grantland.widget.AutofitTextView) bindings[55]
            , (me.grantland.widget.AutofitTextView) bindings[58]
            , (me.grantland.widget.AutofitTextView) bindings[46]
            , (me.grantland.widget.AutofitTextView) bindings[45]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
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
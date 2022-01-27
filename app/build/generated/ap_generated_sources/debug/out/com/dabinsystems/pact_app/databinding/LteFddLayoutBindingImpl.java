package com.dabinsystems.pact_app.databinding;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LteFddLayoutBindingImpl extends LteFddLayoutBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rightArea, 1);
        sViewsWithIds.put(R.id.signalQualityTitle, 2);
        sViewsWithIds.put(R.id.signalQualityContentParent, 3);
        sViewsWithIds.put(R.id.tableGuidLine, 4);
        sViewsWithIds.put(R.id.signalQualityHorizontalGuide1, 5);
        sViewsWithIds.put(R.id.signalQualityTableParent, 6);
        sViewsWithIds.put(R.id.tvFrequencyOffsetTitle, 7);
        sViewsWithIds.put(R.id.tvThresholdTitle, 8);
        sViewsWithIds.put(R.id.tvThresholdValue, 9);
        sViewsWithIds.put(R.id.tvPpmTitle, 10);
        sViewsWithIds.put(R.id.tvPpmValue, 11);
        sViewsWithIds.put(R.id.tvHzTitle, 12);
        sViewsWithIds.put(R.id.tvHzValue, 13);
        sViewsWithIds.put(R.id.tvFreqOffsetResult, 14);
        sViewsWithIds.put(R.id.linSignalInfoParent, 15);
        sViewsWithIds.put(R.id.linSignalInfoTitleParent, 16);
        sViewsWithIds.put(R.id.tvChannelTitle, 17);
        sViewsWithIds.put(R.id.tvEVMTitle, 18);
        sViewsWithIds.put(R.id.tvPWRTitle, 19);
        sViewsWithIds.put(R.id.tvModTitle, 20);
        sViewsWithIds.put(R.id.tvRBTitle, 21);
        sViewsWithIds.put(R.id.signalInfoDevider, 22);
        sViewsWithIds.put(R.id.linPssParent, 23);
        sViewsWithIds.put(R.id.tvPssTitle, 24);
        sViewsWithIds.put(R.id.tvPssEvmValue, 25);
        sViewsWithIds.put(R.id.tvPssPwrValue, 26);
        sViewsWithIds.put(R.id.tvPssModValue, 27);
        sViewsWithIds.put(R.id.tvPssRbValue, 28);
        sViewsWithIds.put(R.id.linSssParent, 29);
        sViewsWithIds.put(R.id.tvSssTitle, 30);
        sViewsWithIds.put(R.id.tvSssEvmValue, 31);
        sViewsWithIds.put(R.id.tvSssPwrValue, 32);
        sViewsWithIds.put(R.id.tvSssModValue, 33);
        sViewsWithIds.put(R.id.tvSssRbValue, 34);
        sViewsWithIds.put(R.id.linPbchParent, 35);
        sViewsWithIds.put(R.id.tvPbchTitle, 36);
        sViewsWithIds.put(R.id.tvPbchEvmValue, 37);
        sViewsWithIds.put(R.id.tvPbchPwrValue, 38);
        sViewsWithIds.put(R.id.tvPbchModValue, 39);
        sViewsWithIds.put(R.id.tvPbchRbValue, 40);
        sViewsWithIds.put(R.id.linPcfichParent, 41);
        sViewsWithIds.put(R.id.tvPcfichTitle, 42);
        sViewsWithIds.put(R.id.tvPcfichEvmValue, 43);
        sViewsWithIds.put(R.id.tvPcfichPwrValue, 44);
        sViewsWithIds.put(R.id.tvPcfichModValue, 45);
        sViewsWithIds.put(R.id.tvPcfichRbValue, 46);
        sViewsWithIds.put(R.id.linPhichParent, 47);
        sViewsWithIds.put(R.id.tvPhichTitle, 48);
        sViewsWithIds.put(R.id.tvPhichEvmValue, 49);
        sViewsWithIds.put(R.id.tvPhichPwrValue, 50);
        sViewsWithIds.put(R.id.tvPhichModValue, 51);
        sViewsWithIds.put(R.id.tvPhichRbValue, 52);
        sViewsWithIds.put(R.id.linPdcchParent, 53);
        sViewsWithIds.put(R.id.tvPdcchTitle, 54);
        sViewsWithIds.put(R.id.tvPdcchEvmValue, 55);
        sViewsWithIds.put(R.id.tvPdcchPwrValue, 56);
        sViewsWithIds.put(R.id.tvPdcchModValue, 57);
        sViewsWithIds.put(R.id.tvPdcchRbValue, 58);
        sViewsWithIds.put(R.id.linCrsParent, 59);
        sViewsWithIds.put(R.id.tvCrsTitle, 60);
        sViewsWithIds.put(R.id.tvCrsEvmValue, 61);
        sViewsWithIds.put(R.id.tvCrsPwrValue, 62);
        sViewsWithIds.put(R.id.tvCrsModValue, 63);
        sViewsWithIds.put(R.id.tvCrsRbValue, 64);
        sViewsWithIds.put(R.id.tvCrsPassFail, 65);
        sViewsWithIds.put(R.id.linPdschQpskParent, 66);
        sViewsWithIds.put(R.id.tvPdschQpskTitle, 67);
        sViewsWithIds.put(R.id.tvPdschQpskEvmValue, 68);
        sViewsWithIds.put(R.id.tvPdschQpskPwrValue, 69);
        sViewsWithIds.put(R.id.tvPdschQpskModValue, 70);
        sViewsWithIds.put(R.id.tvPdschQpskRbValue, 71);
        sViewsWithIds.put(R.id.linPdsch16qamParent, 72);
        sViewsWithIds.put(R.id.tvPdsch16QamTitle, 73);
        sViewsWithIds.put(R.id.tvPdsch16QamEvmValue, 74);
        sViewsWithIds.put(R.id.tvPdsch16QamPwrValue, 75);
        sViewsWithIds.put(R.id.tvPdsch16QamModValue, 76);
        sViewsWithIds.put(R.id.tvPdsch16QamRbValue, 77);
        sViewsWithIds.put(R.id.linPdsch64QamParent, 78);
        sViewsWithIds.put(R.id.tvPdsch64QamTitle, 79);
        sViewsWithIds.put(R.id.tvPdsch64QamEvmValue, 80);
        sViewsWithIds.put(R.id.tvPdsch64QamPwrValue, 81);
        sViewsWithIds.put(R.id.tvPdsch64QamModValue, 82);
        sViewsWithIds.put(R.id.tvPdsch64QamRbValue, 83);
        sViewsWithIds.put(R.id.linPdsch256qamParent, 84);
        sViewsWithIds.put(R.id.tvPdsch256QamTitle, 85);
        sViewsWithIds.put(R.id.tvPdsch256QamEvmValue, 86);
        sViewsWithIds.put(R.id.tvPdsch256QamPwrValue, 87);
        sViewsWithIds.put(R.id.tvPdsch256QamModValue, 88);
        sViewsWithIds.put(R.id.tvPdsch256QamRbValue, 89);
        sViewsWithIds.put(R.id.middleArea, 90);
        sViewsWithIds.put(R.id.cellInformationParent, 91);
        sViewsWithIds.put(R.id.cellInformationTitle, 92);
        sViewsWithIds.put(R.id.cellInformationContentParent, 93);
        sViewsWithIds.put(R.id.tvChBandWidthTitle, 94);
        sViewsWithIds.put(R.id.tvChBandWidthValue, 95);
        sViewsWithIds.put(R.id.tvLtePhysicalCellIdTitle, 96);
        sViewsWithIds.put(R.id.tvLtePhysicalCellIdValue, 97);
        sViewsWithIds.put(R.id.tvGroupIdTitle, 98);
        sViewsWithIds.put(R.id.tvGroupIdValue, 99);
        sViewsWithIds.put(R.id.tvSectorTitle, 100);
        sViewsWithIds.put(R.id.tvSectorValue, 101);
        sViewsWithIds.put(R.id.cellInfoVerticalGuide, 102);
        sViewsWithIds.put(R.id.powerInformationParent, 103);
        sViewsWithIds.put(R.id.powerInformationTitle, 104);
        sViewsWithIds.put(R.id.powerInformationContentParent, 105);
        sViewsWithIds.put(R.id.tvRsrpRsTitle0, 106);
        sViewsWithIds.put(R.id.tvRsrpRsValue0, 107);
        sViewsWithIds.put(R.id.tvRsrpRsTitle1, 108);
        sViewsWithIds.put(R.id.tvRsrpRsValue1, 109);
        sViewsWithIds.put(R.id.tvRsrpRsTitle2, 110);
        sViewsWithIds.put(R.id.tvRsrpRsValue2, 111);
        sViewsWithIds.put(R.id.tvRsrpRsTitle3, 112);
        sViewsWithIds.put(R.id.tvRsrpRsValue3, 113);
        sViewsWithIds.put(R.id.tvRsrqTitle, 114);
        sViewsWithIds.put(R.id.tvRsrqValue, 115);
        sViewsWithIds.put(R.id.tvExpectedTxMaxPowerTitle, 116);
        sViewsWithIds.put(R.id.tvExpectedTxMaxPowerValue, 117);
        sViewsWithIds.put(R.id.powerInfoVerticalGuide, 118);
        sViewsWithIds.put(R.id.leftArea, 119);
        sViewsWithIds.put(R.id.antennaInformationParent, 120);
        sViewsWithIds.put(R.id.antennaInformationTitle, 121);
        sViewsWithIds.put(R.id.antennaInfoContentParent, 122);
        sViewsWithIds.put(R.id.ivAntenna0, 123);
        sViewsWithIds.put(R.id.tvAnt0, 124);
        sViewsWithIds.put(R.id.ivAntenna1, 125);
        sViewsWithIds.put(R.id.tvAnt1, 126);
        sViewsWithIds.put(R.id.ivAntenna2, 127);
        sViewsWithIds.put(R.id.tvAnt2, 128);
        sViewsWithIds.put(R.id.ivAntenna3, 129);
        sViewsWithIds.put(R.id.tvAnt3, 130);
        sViewsWithIds.put(R.id.constellationParent, 131);
        sViewsWithIds.put(R.id.constellationTitle, 132);
        sViewsWithIds.put(R.id.scatterChart, 133);
        sViewsWithIds.put(R.id.timeInformationParent, 134);
        sViewsWithIds.put(R.id.timeInformationTitleParent, 135);
        sViewsWithIds.put(R.id.timeInformationTitle, 136);
        sViewsWithIds.put(R.id.timeInformationTableParent, 137);
        sViewsWithIds.put(R.id.horizontalGuide, 138);
        sViewsWithIds.put(R.id.verticalGuide1, 139);
        sViewsWithIds.put(R.id.verticalGuide2, 140);
        sViewsWithIds.put(R.id.verticalGuide3, 141);
        sViewsWithIds.put(R.id.verticalGuide4, 142);
        sViewsWithIds.put(R.id.verticalGuide5, 143);
        sViewsWithIds.put(R.id.verticalGuide6, 144);
        sViewsWithIds.put(R.id.timingOffsetTitle, 145);
        sViewsWithIds.put(R.id.timingOffsetValue, 146);
        sViewsWithIds.put(R.id.timeAlignmentErrorTitle, 147);
        sViewsWithIds.put(R.id.timeAlignmentErrorValue, 148);
        sViewsWithIds.put(R.id.thrdTitle, 149);
        sViewsWithIds.put(R.id.thrdValue, 150);
        sViewsWithIds.put(R.id.timeAlignmentErrorResult, 151);
        sViewsWithIds.put(R.id.signalQualityParent, 152);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LteFddLayoutBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 153, sIncludes, sViewsWithIds));
    }
    private LteFddLayoutBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[122]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[120]
            , (me.grantland.widget.AutofitTextView) bindings[121]
            , (androidx.constraintlayout.widget.Guideline) bindings[102]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[93]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[91]
            , (me.grantland.widget.AutofitTextView) bindings[92]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[131]
            , (me.grantland.widget.AutofitTextView) bindings[132]
            , (androidx.constraintlayout.widget.Guideline) bindings[138]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[123]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[125]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[127]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[129]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[119]
            , (android.widget.LinearLayout) bindings[59]
            , (android.widget.LinearLayout) bindings[35]
            , (android.widget.LinearLayout) bindings[41]
            , (android.widget.LinearLayout) bindings[53]
            , (android.widget.LinearLayout) bindings[72]
            , (android.widget.LinearLayout) bindings[84]
            , (android.widget.LinearLayout) bindings[78]
            , (android.widget.LinearLayout) bindings[66]
            , (android.widget.LinearLayout) bindings[47]
            , (android.widget.LinearLayout) bindings[23]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[15]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.LinearLayout) bindings[29]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[90]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (androidx.constraintlayout.widget.Guideline) bindings[118]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[105]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[103]
            , (me.grantland.widget.AutofitTextView) bindings[104]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[1]
            , (com.github.mikephil.charting.charts.ScatterChart) bindings[133]
            , (android.view.View) bindings[22]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[3]
            , (androidx.constraintlayout.widget.Guideline) bindings[5]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[152]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[6]
            , (me.grantland.widget.AutofitTextView) bindings[2]
            , (androidx.constraintlayout.widget.Guideline) bindings[4]
            , (me.grantland.widget.AutofitTextView) bindings[149]
            , (me.grantland.widget.AutofitTextView) bindings[150]
            , (me.grantland.widget.AutofitTextView) bindings[151]
            , (me.grantland.widget.AutofitTextView) bindings[147]
            , (me.grantland.widget.AutofitTextView) bindings[148]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[134]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[137]
            , (me.grantland.widget.AutofitTextView) bindings[136]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[135]
            , (me.grantland.widget.AutofitTextView) bindings[145]
            , (me.grantland.widget.AutofitTextView) bindings[146]
            , (me.grantland.widget.AutofitTextView) bindings[124]
            , (me.grantland.widget.AutofitTextView) bindings[126]
            , (me.grantland.widget.AutofitTextView) bindings[128]
            , (me.grantland.widget.AutofitTextView) bindings[130]
            , (me.grantland.widget.AutofitTextView) bindings[94]
            , (me.grantland.widget.AutofitTextView) bindings[95]
            , (me.grantland.widget.AutofitTextView) bindings[17]
            , (me.grantland.widget.AutofitTextView) bindings[61]
            , (me.grantland.widget.AutofitTextView) bindings[63]
            , (me.grantland.widget.AutofitTextView) bindings[65]
            , (me.grantland.widget.AutofitTextView) bindings[62]
            , (me.grantland.widget.AutofitTextView) bindings[64]
            , (me.grantland.widget.AutofitTextView) bindings[60]
            , (me.grantland.widget.AutofitTextView) bindings[18]
            , (me.grantland.widget.AutofitTextView) bindings[116]
            , (me.grantland.widget.AutofitTextView) bindings[117]
            , (me.grantland.widget.AutofitTextView) bindings[14]
            , (me.grantland.widget.AutofitTextView) bindings[7]
            , (me.grantland.widget.AutofitTextView) bindings[98]
            , (me.grantland.widget.AutofitTextView) bindings[99]
            , (me.grantland.widget.AutofitTextView) bindings[12]
            , (me.grantland.widget.AutofitTextView) bindings[13]
            , (me.grantland.widget.AutofitTextView) bindings[96]
            , (me.grantland.widget.AutofitTextView) bindings[97]
            , (me.grantland.widget.AutofitTextView) bindings[20]
            , (me.grantland.widget.AutofitTextView) bindings[19]
            , (me.grantland.widget.AutofitTextView) bindings[37]
            , (me.grantland.widget.AutofitTextView) bindings[39]
            , (me.grantland.widget.AutofitTextView) bindings[38]
            , (me.grantland.widget.AutofitTextView) bindings[40]
            , (me.grantland.widget.AutofitTextView) bindings[36]
            , (me.grantland.widget.AutofitTextView) bindings[43]
            , (me.grantland.widget.AutofitTextView) bindings[45]
            , (me.grantland.widget.AutofitTextView) bindings[44]
            , (me.grantland.widget.AutofitTextView) bindings[46]
            , (me.grantland.widget.AutofitTextView) bindings[42]
            , (me.grantland.widget.AutofitTextView) bindings[55]
            , (me.grantland.widget.AutofitTextView) bindings[57]
            , (me.grantland.widget.AutofitTextView) bindings[56]
            , (me.grantland.widget.AutofitTextView) bindings[58]
            , (me.grantland.widget.AutofitTextView) bindings[54]
            , (me.grantland.widget.AutofitTextView) bindings[74]
            , (me.grantland.widget.AutofitTextView) bindings[76]
            , (me.grantland.widget.AutofitTextView) bindings[75]
            , (me.grantland.widget.AutofitTextView) bindings[77]
            , (me.grantland.widget.AutofitTextView) bindings[73]
            , (me.grantland.widget.AutofitTextView) bindings[86]
            , (me.grantland.widget.AutofitTextView) bindings[88]
            , (me.grantland.widget.AutofitTextView) bindings[87]
            , (me.grantland.widget.AutofitTextView) bindings[89]
            , (me.grantland.widget.AutofitTextView) bindings[85]
            , (me.grantland.widget.AutofitTextView) bindings[80]
            , (me.grantland.widget.AutofitTextView) bindings[82]
            , (me.grantland.widget.AutofitTextView) bindings[81]
            , (me.grantland.widget.AutofitTextView) bindings[83]
            , (me.grantland.widget.AutofitTextView) bindings[79]
            , (me.grantland.widget.AutofitTextView) bindings[68]
            , (me.grantland.widget.AutofitTextView) bindings[70]
            , (me.grantland.widget.AutofitTextView) bindings[69]
            , (me.grantland.widget.AutofitTextView) bindings[71]
            , (me.grantland.widget.AutofitTextView) bindings[67]
            , (me.grantland.widget.AutofitTextView) bindings[49]
            , (me.grantland.widget.AutofitTextView) bindings[51]
            , (me.grantland.widget.AutofitTextView) bindings[50]
            , (me.grantland.widget.AutofitTextView) bindings[52]
            , (me.grantland.widget.AutofitTextView) bindings[48]
            , (me.grantland.widget.AutofitTextView) bindings[10]
            , (me.grantland.widget.AutofitTextView) bindings[11]
            , (me.grantland.widget.AutofitTextView) bindings[25]
            , (me.grantland.widget.AutofitTextView) bindings[27]
            , (me.grantland.widget.AutofitTextView) bindings[26]
            , (me.grantland.widget.AutofitTextView) bindings[28]
            , (me.grantland.widget.AutofitTextView) bindings[24]
            , (me.grantland.widget.AutofitTextView) bindings[21]
            , (me.grantland.widget.AutofitTextView) bindings[106]
            , (me.grantland.widget.AutofitTextView) bindings[108]
            , (me.grantland.widget.AutofitTextView) bindings[110]
            , (me.grantland.widget.AutofitTextView) bindings[112]
            , (me.grantland.widget.AutofitTextView) bindings[107]
            , (me.grantland.widget.AutofitTextView) bindings[109]
            , (me.grantland.widget.AutofitTextView) bindings[111]
            , (me.grantland.widget.AutofitTextView) bindings[113]
            , (me.grantland.widget.AutofitTextView) bindings[114]
            , (me.grantland.widget.AutofitTextView) bindings[115]
            , (me.grantland.widget.AutofitTextView) bindings[100]
            , (me.grantland.widget.AutofitTextView) bindings[101]
            , (me.grantland.widget.AutofitTextView) bindings[31]
            , (me.grantland.widget.AutofitTextView) bindings[33]
            , (me.grantland.widget.AutofitTextView) bindings[32]
            , (me.grantland.widget.AutofitTextView) bindings[34]
            , (me.grantland.widget.AutofitTextView) bindings[30]
            , (me.grantland.widget.AutofitTextView) bindings[8]
            , (me.grantland.widget.AutofitTextView) bindings[9]
            , (androidx.constraintlayout.widget.Guideline) bindings[139]
            , (androidx.constraintlayout.widget.Guideline) bindings[140]
            , (androidx.constraintlayout.widget.Guideline) bindings[141]
            , (androidx.constraintlayout.widget.Guideline) bindings[142]
            , (androidx.constraintlayout.widget.Guideline) bindings[143]
            , (androidx.constraintlayout.widget.Guideline) bindings[144]
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
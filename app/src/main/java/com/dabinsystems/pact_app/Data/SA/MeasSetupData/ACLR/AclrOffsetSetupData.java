package com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR;

import android.annotation.SuppressLint;
import android.provider.ContactsContract;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.FailSourceData.FAIL_SOURCE;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SideData;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.github.mikephil.charting.utils.Utils;

import java.util.Arrays;

import me.grantland.widget.AutofitTextView;

public class AclrOffsetSetupData {

    public final Integer MAX_ABS = 100;
    public final Integer MIN_ABS = -120;

    public final Integer MAX_REL = 100;
    public final Integer MIN_REL = -120;

    public final Integer MAX_NUM_OF_OFFSET = 5;
    public final Integer MIN_NUM_OF_OFFSET = 1;

    private Integer NumOfOffset = 2;
    private Integer SelectOffset = 0;
    private Double[] OffsetSpacing;
    private Double[] IntegBw;
    private SideData.SIDE_OPTION[] OffsetSide;
    private FAIL_SOURCE[] FailSource;
    private Float[] AbsLimit;
    private Float[] RelLimit;

    private final AutofitTextView[] OffsetIntegViews;
    private final AutofitTextView[] OffsetSpacingViews;

    public AclrOffsetSetupData() {

        OffsetSpacing = new Double[MAX_NUM_OF_OFFSET];
        IntegBw = new Double[MAX_NUM_OF_OFFSET];
        OffsetSide = new SideData.SIDE_OPTION[MAX_NUM_OF_OFFSET];
        FailSource = new FAIL_SOURCE[MAX_NUM_OF_OFFSET];
        AbsLimit = new Float[MAX_NUM_OF_OFFSET];
        RelLimit = new Float[MAX_NUM_OF_OFFSET];

        Arrays.fill(OffsetSpacing, 100d);
        OffsetSpacing[1] = 200d;
        Arrays.fill(IntegBw, 98.28d);
        Arrays.fill(OffsetSide, SideData.SIDE_OPTION.BOTH);
        Arrays.fill(FailSource, FAIL_SOURCE.RELATIVE);
        Arrays.fill(AbsLimit, -10f);
        Arrays.fill(RelLimit, -44.2f);

        OffsetSpacingViews = new AutofitTextView[]{

                MainActivity.getBinding().lineChartLayout.aclrInfo.tvAclrOffsetFreq1,
                MainActivity.getBinding().lineChartLayout.aclrInfo.tvAclrOffsetFreq2,
                MainActivity.getBinding().lineChartLayout.aclrInfo.tvAclrOffsetFreq3,
                MainActivity.getBinding().lineChartLayout.aclrInfo.tvAclrOffsetFreq4,
                MainActivity.getBinding().lineChartLayout.aclrInfo.tvAclrOffsetFreq5,
        };

        OffsetIntegViews = new AutofitTextView[]{

                MainActivity.getBinding().lineChartLayout.aclrInfo.tvAclrIntegBw1,
                MainActivity.getBinding().lineChartLayout.aclrInfo.tvAclrIntegBw2,
                MainActivity.getBinding().lineChartLayout.aclrInfo.tvAclrIntegBw3,
                MainActivity.getBinding().lineChartLayout.aclrInfo.tvAclrIntegBw4,
                MainActivity.getBinding().lineChartLayout.aclrInfo.tvAclrIntegBw5,

        };

    }

    public Integer getNumOfOffset() {
        return NumOfOffset;
    }

    public void setNumOfOffset(Integer numOfOffset) {

        if (numOfOffset >= MIN_NUM_OF_OFFSET && numOfOffset <= MAX_NUM_OF_OFFSET)
            NumOfOffset = numOfOffset;

        if (SelectOffset >= NumOfOffset) SelectOffset = NumOfOffset - 1;

        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
        FunctionHandler.getInstance().getMainLineChart().invalidate();

    }

    public Integer getSelectOffset() {
        return SelectOffset;
    }

    public void setSelectOffset(Integer selectOffset) {
        if (selectOffset >= 0 && selectOffset < NumOfOffset)
            SelectOffset = selectOffset;
    }

    public Double getOffsetSpacing() {
        return OffsetSpacing[SelectOffset];
    }

    public Long getOffsetSpacingToHz() {

        Long spacing = (long) (OffsetSpacing[SelectOffset] * 1000 * 1000);
        return spacing;
    }

    public Double getOffsetSpacing(int idx) {
        return OffsetSpacing[idx];
    }

    public Long getOffsetSpacingToHz(int idx) {

        Long spacing = (long) (OffsetSpacing[idx] * 1000 * 1000);
        return spacing;
    }

    @SuppressLint("SetTextI18n")
    public void setOffsetSpacing(Double offsetSpacing) {
        OffsetSpacing[SelectOffset] = offsetSpacing;
        MainActivity.getActivity().runOnUiThread(() -> {

            double spacing = Math.round(offsetSpacing * 100d) / 100d;
            OffsetSpacingViews[SelectOffset].setText(spacing + " MHz");

        });
        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
        FunctionHandler.getInstance().getMainLineChart().invalidate();
    }

    @SuppressLint("SetTextI18n")
    public void setOffsetSpacing(Double offsetSpacing, Integer idx) {
        OffsetSpacing[idx] = offsetSpacing;
        MainActivity.getActivity().runOnUiThread(() -> {
            double spacing = Math.round(offsetSpacing * 100d) /100d;
            OffsetSpacingViews[idx].setText(spacing + " MHz");
        });
        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
        FunctionHandler.getInstance().getMainLineChart().invalidate();
    }


    public Double getIntegBw() {
        return IntegBw[SelectOffset];
    }

    public Double getIntegBw(int idx) {
        return IntegBw[idx];
    }

    public Long getIntegBwToHz() {
        Long integ = (long) (IntegBw[SelectOffset] * 1000 * 1000);
        return integ;
    }

    public Long getIntegBwToHz(int idx) {
        Long integ = (long) (IntegBw[idx] * 1000 * 1000);
        return integ;
    }

    @SuppressLint("SetTextI18n")
    public void setIntegBw(Double integBw) {
        IntegBw[SelectOffset] = integBw;
        MainActivity.getActivity().runOnUiThread(() -> {
            double offsetInteg = Math.round(integBw * 100d)/100d;
            OffsetIntegViews[SelectOffset].setText(offsetInteg + " MHz");
        });
        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
        FunctionHandler.getInstance().getMainLineChart().invalidate();
    }

    @SuppressLint("SetTextI18n")
    public void setIntegBw(Double integBw, Integer idx) {
        IntegBw[idx] = integBw;
        MainActivity.getActivity().runOnUiThread(() -> {
            double offsetInteg =  Math.round(integBw * 100d) / 100d;
            OffsetIntegViews[idx].setText(offsetInteg + " MHz");
        });
        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
        FunctionHandler.getInstance().getMainLineChart().invalidate();
    }

    public SideData.SIDE_OPTION getOffsetSide() {
        return OffsetSide[SelectOffset];
    }

    public SideData.SIDE_OPTION getOffsetSide(Integer idx) {
        return OffsetSide[idx];
    }

    public void setOffsetSide(SideData.SIDE_OPTION offsetSide) {
        OffsetSide[SelectOffset] = offsetSide;
        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
        FunctionHandler.getInstance().getMainLineChart().invalidate();
    }

    public void setOffsetSide(SideData.SIDE_OPTION offsetSide, Integer idx) {
        OffsetSide[idx] = offsetSide;
        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
        FunctionHandler.getInstance().getMainLineChart().invalidate();
    }

    public FAIL_SOURCE getFailSource() {
        return FailSource[SelectOffset];
    }

    public FAIL_SOURCE getFailSource(Integer idx) {
        return FailSource[idx];
    }

    public void setFailSource(FAIL_SOURCE failSource) {
        FailSource[SelectOffset] = failSource;
        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
        FunctionHandler.getInstance().getMainLineChart().invalidate();
    }

    public void setFailSource(FAIL_SOURCE failSource, Integer idx) {
        FailSource[idx] = failSource;
        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
        FunctionHandler.getInstance().getMainLineChart().invalidate();
    }

    public Float getAbsLimit() {
        return AbsLimit[SelectOffset];
    }

    public Float getAbsLimit(Integer idx) {
        return AbsLimit[idx];
    }

    public void setAbsLimit(Float absLimit) {
        AbsLimit[SelectOffset] = absLimit;
        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
        FunctionHandler.getInstance().getMainLineChart().invalidate();
    }

    public void setAbsLimit(Float absLimit, Integer idx) {

        if (absLimit > MAX_ABS || absLimit < MIN_ABS) return;

        AbsLimit[idx] = absLimit;
        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
        FunctionHandler.getInstance().getMainLineChart().invalidate();
    }

    public Float getRelLimit() {
        return RelLimit[SelectOffset];
    }

    public Float getRelLimit(int idx) {
        return RelLimit[idx];
    }

    public void setRelLimit(Float relLimit) {

        if (relLimit > MAX_REL || relLimit < MIN_REL) return;

        RelLimit[SelectOffset] = relLimit;
        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
        FunctionHandler.getInstance().getMainLineChart().invalidate();

    }

    public void setRelLimit(Float relLimit, Integer idx) {

        if (relLimit > MAX_REL || relLimit < MIN_REL) return;

        RelLimit[idx] = relLimit;
        FunctionHandler.getInstance().getMainLineChart().updateAclrBox();
    }

}

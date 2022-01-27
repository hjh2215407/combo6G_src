package com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR;

import android.annotation.SuppressLint;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.github.mikephil.charting.utils.Utils;

import me.grantland.widget.AutofitTextView;

public class AclrCarrierSetupData {

    public final Integer MAX_CARRIERS = 2;
    public final Integer MIN_CARRIERS = 1;

    public final Integer MIN_REF_CARRIER = 1;

    private Integer Carriers = 1;
    private Integer RefCarrier = 1;
    private Double CarrierSpacing = 0d;
    private Double IntegBw = 98.28d;
    private AutofitTextView[] CarrierPowerViews;

    public AclrCarrierSetupData() {

        CarrierPowerViews = new AutofitTextView[]{

                MainActivity.getBinding().lineChartLayout.aclrInfo.tvAclrCarrierPower1,
                MainActivity.getBinding().lineChartLayout.aclrInfo.tvAclrCarrierPower2,

        };

    }

    public Integer getCarriers() {
        return Carriers;
    }

    public void setCarriers(Integer carriers) {
        if (carriers >= MIN_CARRIERS && carriers <= MAX_CARRIERS)
            Carriers = carriers;

        if (RefCarrier > Carriers) RefCarrier = Carriers;

        FunctionHandler.getInstance().getMainLineChart().calCarrierBox();

    }

    public Integer getRefCarrier() {
        return RefCarrier;
    }

    public void setRefCarrier(Integer refCarrier) {
        if (refCarrier >= MIN_REF_CARRIER && refCarrier <= Carriers)
            RefCarrier = refCarrier;
    }

    public Double getCarrierSpacing() {
        return CarrierSpacing;
    }

    public Long getCarrierSpacingToHz() {
        Long carrierSpacing = (long) (CarrierSpacing * 1000 * 1000);
        return carrierSpacing;
    }

    public void setCarrierSpacing(Double carrierSpacing) {
        CarrierSpacing = carrierSpacing;
        FunctionHandler.getInstance().getMainLineChart().calCarrierBox();

    }

    public Double getIntegBw() {
        return IntegBw;
    }

    public Long getIntegBwToHz() {

        Long integ = (long) (IntegBw * 1000 * 1000);
        return integ;
    }

    @SuppressLint("SetTextI18n")
    public void setIntegBw(Double integBw) {
        IntegBw = integBw;
        AclrMeasSetupData data = SaDataHandler.getInstance().getAclrConfigData().getAclrMeasSetupData();
        MainActivity.getActivity().runOnUiThread(() -> {

            for (int i = 0; i < Carriers; i++) {
                if (data.getCarrierPower(i) != null) {
                    CarrierPowerViews[i].setText(
                            i + ". " + Utils.formatNumber(data.getCarrierPower(i), 2, false) + " dBm / " + integBw + " MHz"
                    );
                }
            }

        });
        FunctionHandler.getInstance().getMainLineChart().calCarrierBox();
    }
}

package com.dabinsystems.pact_app.Handler;

import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SpuriousEmission.SpuriousEmissionMeasSetupData;
import com.dabinsystems.pact_app.Data.SA.SaConfigData;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;

public class SaDataHandler {

    private static SaDataHandler Instance = null;

    private SaConfigData SweptSaConfigData;
    private SaConfigData ChannelPowerConfigData;
    private SaConfigData OccupiedBwConfigData;
    private SaConfigData NrChannelPowerConfigData;
    private SaConfigData NrOccupiedBwConfigData;
    private SaConfigData AclrConfigData;
    private SaConfigData SemConfigData;
    private SaConfigData TransmitOnOffData;
    private SaConfigData SpuriousEmissionData;
    private SaConfigData InterferenceHuntingConfigData;

    public static SaDataHandler getInstance() {

        if (Instance == null) Instance = new SaDataHandler();

        return Instance;

    }

    public SaConfigData getSweptSaConfigData() {

        if (SweptSaConfigData == null) SweptSaConfigData = new SaConfigData(MeasureMode.MEASURE_MODE.SA, MeasureType.MEASURE_TYPE.SWEPT_SA);
        return SweptSaConfigData;

    }

    public SaConfigData getChannelPowerConfigData() {

        if (ChannelPowerConfigData == null) ChannelPowerConfigData = new SaConfigData(MeasureMode.MEASURE_MODE.SA, MeasureType.MEASURE_TYPE.CHANNEL_POWER);
        return ChannelPowerConfigData;

    }

    public SaConfigData getOccupiedBwConfigData() {

        if (OccupiedBwConfigData == null) OccupiedBwConfigData = new SaConfigData(MeasureMode.MEASURE_MODE.SA, MeasureType.MEASURE_TYPE.OCCUPIED_BW);
        return OccupiedBwConfigData;

    }

    public SaConfigData getNrChannelPowerConfigData() {

        if (NrChannelPowerConfigData == null) NrChannelPowerConfigData = new SaConfigData(MeasureMode.MEASURE_MODE.MOD_ACCURACY, MeasureType.MEASURE_TYPE.CHANNEL_POWER);
        return NrChannelPowerConfigData;

    }

    public SaConfigData getNrOccupiedBwConfigData() {

        if (NrOccupiedBwConfigData == null) NrOccupiedBwConfigData = new SaConfigData(MeasureMode.MEASURE_MODE.MOD_ACCURACY, MeasureType.MEASURE_TYPE.OCCUPIED_BW);
        return NrOccupiedBwConfigData;

    }

    public SaConfigData getAclrConfigData() {

        if(AclrConfigData == null) AclrConfigData = new SaConfigData(MeasureMode.MEASURE_MODE.SA, MeasureType.MEASURE_TYPE.ACLR);
        return AclrConfigData;

    }

    public SaConfigData getSemConfigData() {

        if(SemConfigData == null) SemConfigData = new SaConfigData(MeasureMode.MEASURE_MODE.SA, MeasureType.MEASURE_TYPE.SEM);
        return SemConfigData;

    }

    public SaConfigData getTransmitConfigData() {

        if(TransmitOnOffData == null) TransmitOnOffData = new SaConfigData(MeasureMode.MEASURE_MODE.SA, MeasureType.MEASURE_TYPE.TRANSMIT_MASK);
        return TransmitOnOffData;

    }

    public SaConfigData getSpuriousEmissionConfigData() {
        if(SpuriousEmissionData == null) SpuriousEmissionData = new SaConfigData(MeasureMode.MEASURE_MODE.SA, MeasureType.MEASURE_TYPE.SPURIOUS_EMISSION);
        return SpuriousEmissionData;
    }

    public SaConfigData getInterferenceHuntingConfigData() {
        if(InterferenceHuntingConfigData == null) InterferenceHuntingConfigData = new SaConfigData(MeasureMode.MEASURE_MODE.SA, MeasureType.MEASURE_TYPE.INTERFERENCE_HUNTING);
        return InterferenceHuntingConfigData;
    }

    /* set parameter */
    public SaConfigData getConfigData() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

            return getSweptSaConfigData();

        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

            return getChannelPowerConfigData();

        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

            return getOccupiedBwConfigData();

        } else if(type == MeasureType.MEASURE_TYPE.ACLR) {

            return getAclrConfigData();

        } else if(type == MeasureType.MEASURE_TYPE.SEM) {

            return getSemConfigData();

        } else if(type == MeasureType.MEASURE_TYPE.TRANSMIT_MASK) {

            return getTransmitConfigData();

        } else if(type == MeasureType.MEASURE_TYPE.INTERFERENCE_HUNTING) {

            return getInterferenceHuntingConfigData();

        }
        else return getSweptSaConfigData();

    }

}

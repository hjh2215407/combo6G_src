package com.dabinsystems.pact_app.Data.VSWR;

import android.util.Log;

import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;

public class VswrAmplitudeData {

    public static final float DEFALUT_CL_Y_MAX = 60;
    public static final float DEFALUT_CL_Y_MIN = 0;
    public static final float DEFALUT_RL_Y_MAX = 60;
    public static final float DEFALUT_RL_Y_MIN = 0;
    public static final float DEFALUT_VSWR_Y_MAX = 65;
    public static final float DEFALUT_VSWR_Y_MIN = 1;

    public static final float DEFAULT_RL_LIMIT = 13.98f;
    public static final float DEFAULT_VSWR_LIMIT = 1.5f;

    private Float AmpMaxForVswr = 65f;
    private Float AmpMinForVswr = 1f;

    private Float AmpMaxForRl = 60f;
    private Float AmpMinForRl = 0f;

    public Float getAmpMaxForVswr() {
        return AmpMaxForVswr;
    }

    public void setAmpMaxForVswr(Float ampMaxForVswr) {

            if (ampMaxForVswr <= DEFALUT_VSWR_Y_MAX)
            AmpMaxForVswr = ampMaxForVswr;
    }

    public void setAmpMax(Float amp) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

            setAmpMaxForVswr(amp);

        } else if (type == MeasureType.MEASURE_TYPE.RL || type == MeasureType.MEASURE_TYPE.DTF_RL || type == MeasureType.MEASURE_TYPE.CABLE_LOSS) {

            setAmpMaxForRl(amp);

        }

    }

    public Float getAmpMax() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

            return getAmpMaxForVswr();

        } else {

            return getAmpMaxForRl();

        }

    }

    public void setAmpMin(Float amp) {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

            setAmpMinForVswr(amp);

        } else if (type == MeasureType.MEASURE_TYPE.RL || type == MeasureType.MEASURE_TYPE.DTF_RL || type == MeasureType.MEASURE_TYPE.CABLE_LOSS) {

            setAmpMinForRl(amp);

        }

    }

    public Float getAmpMin() {

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (type == MeasureType.MEASURE_TYPE.VSWR || type == MeasureType.MEASURE_TYPE.DTF_VSWR) {

            return getAmpMinForVswr();

        } else {

            return getAmpMinForRl();

        }

    }

    public Float getAmpMinForVswr() {
        return AmpMinForVswr;
    }

    public void setAmpMinForVswr(Float ampMinForVswr) {

        if (ampMinForVswr >= DEFALUT_VSWR_Y_MIN)
            AmpMinForVswr = ampMinForVswr;

    }

    public Float getAmpMaxForRl() {
        return AmpMaxForRl;
    }

    public void setAmpMaxForRl(Float ampMaxForRl) {

        if (ampMaxForRl <= DEFALUT_RL_Y_MAX)
            AmpMaxForRl = ampMaxForRl;
    }

    public Float getAmpMinForRl() {
        return AmpMinForRl;
    }

    public void setAmpMinForRl(Float ampMinForRl) {
        if (ampMinForRl >= DEFALUT_RL_Y_MIN)
            AmpMinForRl = ampMinForRl;
    }

}

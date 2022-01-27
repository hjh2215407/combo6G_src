package com.dabinsystems.pact_app.Data.SA.MeasSetupData;

import android.util.Log;

import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrCarrierSetupData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ACLR.AclrOffsetSetupData;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.FunctionHandler;

public class MeasSetupData {

    public enum AVG_HOLD_MODE {

        OFF(0),
        ON(1);

        int val;

        AVG_HOLD_MODE(int num) {
            val = num;
        }

        public int getValue() {
            return val;
        }

    }
    
}

package com.dabinsystems.pact_app.List.Item;
import android.util.Log;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;

import java.util.ArrayList;

public class CableListInfoItem {

    private String CableName;
    private String PropVelocity = "0.5";
    private Float Loss = 0.5f;
    private ArrayList<CableLossInfoItem> CablelossList;

    public CableListInfoItem(String cableName, String propVelo, ArrayList<CableLossInfoItem> cablelossList) {
        super();

        CableName = cableName;
        PropVelocity = propVelo;
        CablelossList = cablelossList;

    }

    public String getCableName() {
        return CableName;
    }

    public void setCableName(String cableName) {
        CableName = cableName;
    }

    public Float getPropVelocity() {
        InitActivity.logMsg("CableListInfoItem", PropVelocity);
        return Float.parseFloat(PropVelocity);
    }

    public void setPropVelocity(float propVelocity) {

        PropVelocity = propVelocity + "";
    }

    public ArrayList<CableLossInfoItem> getCablelossList() {
        return CablelossList;
    }

    public void setCablelossList(ArrayList<CableLossInfoItem> cablelossList) {
        CablelossList = cablelossList;
    }

    public Float getLoss() {

        try {

            if (getCablelossList() != null && getCablelossList().size() > 0) {

                double freq = VswrDataHandler.getInstance().getConfigData().getFrequencyData().getStartFreq();

                if (getCablelossList().size() == 1) {

                    Loss = getCablelossList().get(0).getCableLoss();
                    return Loss;

                }

                for (int i = getCablelossList().size() - 1; i > 0; i--) {

                    float cableFreq1 = getCablelossList().get(i - 1).getFreq();
                    float cableFreq2 = getCablelossList().get(i).getFreq();
                    float cableLoss1 = getCablelossList().get(i - 1).getCableLoss();
                    float cableLoss2 = getCablelossList().get(i).getCableLoss();
                    float middleFreq = (cableFreq1 + cableFreq2) / 2f;

                    if (cableFreq1 < freq && freq < cableFreq2) {

                        if (freq > middleFreq) {

                            Loss = cableLoss2;

                        } else {

                            Loss = cableLoss1;

                        }

                    } else if ((i - 1) == 0 && freq < cableFreq1) {
//                        InitActivity.logMsg("getLoss1", cableLoss1 + " " + cableLoss2);
                        Loss = cableLoss1;

                    } else if ((i == (getCablelossList().size() - 1)) && freq > cableFreq2) {
//                        InitActivity.logMsg("getLoss2", cableLoss1 + " " + cableLoss2);
                        Loss = cableLoss2;

                    } else {
//                        InitActivity.logMsg("getLoss3", cableLoss1 + " " + cableLoss2);
                        Loss = cableLoss1;
                    }

                }
//            Toast.makeText(mActivity, "Send Cable(" + info.getCableName() + ") Information to FW.", Toast.LENGTH_SHORT).show();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return Loss;

    }

}

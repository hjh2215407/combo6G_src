package com.dabinsystems.pact_app.View.SA;

import android.view.View;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.View.LayoutBase;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

public class SaMeasSetupView extends LayoutBase {

    public SaMeasSetupView(MainActivity activity, ActivityMainBinding binding) {
        super(activity, binding);
    }

    @Override
    public void addMenu() throws NullPointerException {
        super.addMenu();

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        switch (type) {

            case SWEPT_SA:
                ViewHandler.getInstance().getMeasSetupSweptSa().addMenu();
                break;

            case CHANNEL_POWER:
                ViewHandler.getInstance().getMeasSetupChannelPower().addMenu();
                break;

            case OCCUPIED_BW:
                ViewHandler.getInstance().getMeasSetupOccupiedBW().addMenu();
                break;

            case ACLR:
                ViewHandler.getInstance().getMeasSetupAclrView().addMenu();
                break;

            case SEM:
                ViewHandler.getInstance().getMeasSetupSemView().addMenu();
                break;

            case SPURIOUS_EMISSION:
                ViewHandler.getInstance().getMeasSetupSpuriousView().addMenu();
                break;

            case TRANSMIT_MASK:
                ViewHandler.getInstance().getTransmitMeasSetupView().addMenu();
                break;

            case INTERFERENCE_HUNTING:
                ViewHandler.getInstance().getInterferenceHuntingView().addMenu();
                break;

            case NR_5G:
                ViewHandler.getInstance().getNrMeasSetupView().addMenu();
                break;

            case NR_5G_SCAN:
                ViewHandler.getInstance().getNrScanMeasSetupView().addMenu();
                break;

            default:
                break;
        }

    }

    @Override
    public void initView() {
        super.initView();

        setUpEvents();

    }

    @Override
    public void setUpEvents() {
        super.setUpEvents();


    }

    public void update() {


    }

}

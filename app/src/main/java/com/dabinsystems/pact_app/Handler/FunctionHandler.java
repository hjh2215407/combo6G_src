package com.dabinsystems.pact_app.Handler;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.MacroDialog;
import com.dabinsystems.pact_app.Function.BatteryFunc;
import com.dabinsystems.pact_app.Function.CableInfoControl;
import com.dabinsystems.pact_app.Function.CalibrationFunc;
import com.dabinsystems.pact_app.Function.Chart.CandleChartFunc;
import com.dabinsystems.pact_app.Function.Chart.NrScanTraceChartFunc;
import com.dabinsystems.pact_app.Function.Chart.ScatterChartFunc2;
import com.dabinsystems.pact_app.Function.DataConnector;
import com.dabinsystems.pact_app.Function.FWFunc;
import com.dabinsystems.pact_app.Function.Chart.GateLineChartFunc;
import com.dabinsystems.pact_app.Function.Chart.MainLineChartFunc;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Function.Recall;
import com.dabinsystems.pact_app.Function.Save;
import com.dabinsystems.pact_app.Function.Chart.ScatterChartFunc;
import com.dabinsystems.pact_app.Function.Chart.ScreenShotFunc;
import com.dabinsystems.pact_app.Function.WifiFunc;
import com.dabinsystems.pact_app.Function.nr5g.NrScanFunc;
import com.dabinsystems.pact_app.Function.nr5g.TAEFunc;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import static com.dabinsystems.pact_app.Function.DataConnector.CONNECTION_TYPE.MQTT;

public class FunctionHandler {

    private static FunctionHandler mHandler = null;

    private InitActivity mActivity;
    private ActivityMainBinding binding;

    private MainLineChartFunc mMainLineChartFunc;
    private GateLineChartFunc mGateLineChartFunc;
    private ScatterChartFunc mScatterChartFunc;
    private ScatterChartFunc2 mScatterChartFunc2;
    private CandleChartFunc mCandleChartFunc;

    private MeasureType mMeasureType;

    private MeasureMode mMeasureMode;
    private CalibrationFunc mCalibrationFunc;
    private CableInfoControl mCableInfoControl;
    private DataConnector mDataConnector;

    private BatteryFunc mBatteryFunc;
    private WifiFunc mWifiFunc;
    private FWFunc mFWFunc;

    private Save mSaveFunc;
    private Recall mRecallFunc;

    private ScreenShotFunc mScreenshotFunc;
    private MacroDialog MacroDialog;
    private TAEFunc TaeFunc;

    public FunctionHandler(InitActivity activity, ActivityMainBinding binding) {
        mActivity = activity;
        this.binding = binding;

    }

    public static FunctionHandler getInstance() {
//        if (mHandler == null)
//            mHandler = new FunctionHandler((MainActivity) MainActivity.getActivity(), MainActivity.getBinding());
        return mHandler;
    }

    public static FunctionHandler getInstance(InitActivity activity, ActivityMainBinding binding) {
        if (mHandler == null)
            mHandler = new FunctionHandler(activity, binding);
        return mHandler;
    }

    public MainLineChartFunc getMainLineChart() {

        if (mMainLineChartFunc == null) mMainLineChartFunc = new MainLineChartFunc(mActivity, binding);

        return mMainLineChartFunc;

    }

    public GateLineChartFunc getGateLineChart() {

        if (mGateLineChartFunc == null) mGateLineChartFunc = new GateLineChartFunc(mActivity, binding);

        return mGateLineChartFunc;

    }

    public ScatterChartFunc getScatterChart() {

        if(mScatterChartFunc == null) mScatterChartFunc = new ScatterChartFunc(mActivity, binding);

        return mScatterChartFunc;

    }

    public ScatterChartFunc2 getScatterChart2() {

        if(mScatterChartFunc2 == null) mScatterChartFunc2 = new ScatterChartFunc2(mActivity, binding);

        return mScatterChartFunc2;

    }


    public CandleChartFunc getCandleChartFunc() {

        if(mCandleChartFunc == null) mCandleChartFunc = new CandleChartFunc(mActivity, binding);

        return mCandleChartFunc;

    }

    public MeasureType getMeasureType() {

        if (mMeasureType == null) mMeasureType = new MeasureType();

        return mMeasureType;

    }

    public MeasureMode getMeasureMode() {

        if (mMeasureMode == null) mMeasureMode = new MeasureMode();
        return mMeasureMode;

    }

    public CalibrationFunc getCalibrationFunc() {

        if (mCalibrationFunc == null) mCalibrationFunc = new CalibrationFunc();
        return mCalibrationFunc;

    }

    public CableInfoControl getCableInfoFunc() {

        if (mCableInfoControl == null) mCableInfoControl = new CableInfoControl();
        return mCableInfoControl;

    }

    public DataConnector getDataConnector() {
        if (mDataConnector == null)
            mDataConnector = new DataConnector(MQTT, mActivity, binding, this);
        return mDataConnector;
    }

    public WifiFunc getWifiFunc() {

        if(mWifiFunc == null)
            mWifiFunc = new WifiFunc(mActivity, binding);

        return mWifiFunc;
    }

    public BatteryFunc getBatteryFunc(){

        if(mBatteryFunc == null) mBatteryFunc = new BatteryFunc(mActivity, binding);
        return mBatteryFunc;

    }

    public FWFunc getFwFunc() {

        if(mFWFunc == null) mFWFunc = new FWFunc(mActivity, binding);
        return mFWFunc;

    }

    public Save getSaveFunc() {

        if(mSaveFunc == null) mSaveFunc = new Save();
        return mSaveFunc;

    }

    public Recall getRecallFunc() {

        if(mRecallFunc == null) mRecallFunc = new Recall(mActivity, binding);
        return mRecallFunc;

    }

    public ScreenShotFunc getScreenshotFunc() {

        if(mScreenshotFunc == null) mScreenshotFunc = new ScreenShotFunc(mActivity, binding);
        return mScreenshotFunc;

    }

    public TAEFunc getTaeFunc() {

        if(TaeFunc == null) TaeFunc = new TAEFunc();
        return TaeFunc;

    }


    private NrScanFunc mNrScanFunc;
    private NrScanTraceChartFunc mNrScanTraceChart1Func, mNrScanTraceChart2Func;
    
    public NrScanFunc getNrScanFunc() {
        if (mNrScanFunc == null) mNrScanFunc = new NrScanFunc(mActivity, binding);
        return mNrScanFunc;
    }
    
    public NrScanTraceChartFunc getNrScanTrace1CharFunc() {
        if (mNrScanTraceChart1Func == null)
            mNrScanTraceChart1Func = new NrScanTraceChartFunc(mActivity, binding, 0);
        return mNrScanTraceChart1Func;
    }

    public NrScanTraceChartFunc getNrScanTrace2CharFunc() {
        if (mNrScanTraceChart2Func == null)
            mNrScanTraceChart2Func = new NrScanTraceChartFunc(mActivity, binding, 1);
        return mNrScanTraceChart2Func;
    }
}

package com.dabinsystems.pact_app.Handler;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Dialog.FileRecallDialog;
import com.dabinsystems.pact_app.Dialog.FileSaveDialog;
import com.dabinsystems.pact_app.View.AmplitudeView;
import com.dabinsystems.pact_app.View.CableLoss.CableLossView;
import com.dabinsystems.pact_app.View.ContentView;
import com.dabinsystems.pact_app.View.DTF.DtfView;
import com.dabinsystems.pact_app.View.DTF.FreqDist.CableListView;
import com.dabinsystems.pact_app.View.DTF.FreqDist.DistanceView;
import com.dabinsystems.pact_app.View.DTF.FreqDist.DtfSetupView;
import com.dabinsystems.pact_app.View.DTF.FreqDist.FreqDistView;
import com.dabinsystems.pact_app.View.DTF.FreqDist.WindowingView;
import com.dabinsystems.pact_app.View.DataPointsView;
import com.dabinsystems.pact_app.View.FrequencyView;
import com.dabinsystems.pact_app.View.LimitView;
import com.dabinsystems.pact_app.View.Marker.MarkerView;
import com.dabinsystems.pact_app.View.Marker.MarkerSetupView;
import com.dabinsystems.pact_app.View.Marker.MarkerSearchView;
import com.dabinsystems.pact_app.View.Marker.SelectMarkerView;
import com.dabinsystems.pact_app.View.MeasurementView;
import com.dabinsystems.pact_app.View.ModAccuracy.LteFdd.LteFddAmplitudeView;
import com.dabinsystems.pact_app.View.ModAccuracy.LteFdd.LteFddFrequencyView;
import com.dabinsystems.pact_app.View.ModAccuracy.LteFdd.LteFddLimitView;
import com.dabinsystems.pact_app.View.ModAccuracy.LteFdd.LteFddMeasSetupView;
import com.dabinsystems.pact_app.View.ModAccuracy.LteFdd.LteFddProfileView;
import com.dabinsystems.pact_app.View.ModAccuracy.LteFdd.LteSelectTriggerSourceView;
import com.dabinsystems.pact_app.View.ModAccuracy.ModAccuracyMeasView;
import com.dabinsystems.pact_app.View.ModAccuracy.ModAccuracyModeView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.NrMeasSetupView2;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.NrSelectTriggerSourceView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.tae.NrInterTaeView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.tae.SelectCarrierView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.tae.TaeProfileView1;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.tae.TaeProfileView2;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5GScan.NrScanCarrierView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5GScan.NrScanMeasSetupView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5GScan.NrScanSelCarrierView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5GScan.NrScanSelProfileView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5GScan.NrScanTraceSelectView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5GScan.NrScanTraceSetView;
import com.dabinsystems.pact_app.View.ModAccuracy.SelectTriggerSourceView;
import com.dabinsystems.pact_app.View.RecallView;
import com.dabinsystems.pact_app.View.SA.BW.SaBWView;
import com.dabinsystems.pact_app.View.SA.BW.SaRBWView1;
import com.dabinsystems.pact_app.View.SA.BW.SaRBWView2;
import com.dabinsystems.pact_app.View.SA.BW.SaVBWView1;
import com.dabinsystems.pact_app.View.SA.BW.SaVBWView2;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.NrAmplitudeView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.NrFrequencyView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.NrLimitView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.NrMeasSetupView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.NrProfileView1;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.NrProfileView2;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.NrSSBInfoView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.tae.NrTaeView;
import com.dabinsystems.pact_app.View.ModAccuracy.NR5G.tae.NumOfTxAntView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR.CarrierSetupView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR.CarriersView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR.AclrFailSourceView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR.MeasSetupAclrView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR.NumberOfOffsetView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR.OffsetSetupView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR.RefCarrierView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.ACLR.SelectOffsetView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.InterferenceHunting.MeasSetupInterferenceView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.InterferenceHunting.UplinkBandView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.EditMask.EditMaskIndexView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.EditMask.EditMaskLimitView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.EditMask.EditMaskRBWView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.EditMask.EditMaskVBWView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.EditMask.EditMaskView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.EditMask.SemFailSourceView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.MeasSetupSemView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.RefChannel.RefChannelRBWView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.RefChannel.RefChannelVBWView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.RefChannel.RefChannelView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SEM.SemMeasTypeView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SpuriousEmission.MeasSetupSpuriousView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SpuriousEmission.SelectFreqRangeView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SpuriousEmission.SpuriousBandSetupView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.SpuriousEmission.SpuriousFreqRangeTableView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.TransmitOnOff.TransmitOnOffLimitView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.TransmitOnOff.TransmitOnOffMeasSetupView;
import com.dabinsystems.pact_app.View.SA.SaMarker.SaMarkerTraceView;
import com.dabinsystems.pact_app.View.SA.SaAmplitudeView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.MeasSetupChannelPowerView;
import com.dabinsystems.pact_app.View.SA.SaMarker.SaMarkerView;
import com.dabinsystems.pact_app.View.SA.SaMarker.SaMarkerView2;
import com.dabinsystems.pact_app.View.SA.SaMarker.SaRelativeToView;
import com.dabinsystems.pact_app.View.SA.SaMarker.SaSelectMarkerView;
import com.dabinsystems.pact_app.View.SA.SaMeasView;
import com.dabinsystems.pact_app.View.SA.SaMeasSetupView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.MeasSetupOccupiedBWView;
import com.dabinsystems.pact_app.View.SA.MeasSetup.MeasSetupSweptSaView;
import com.dabinsystems.pact_app.View.SA.SaView;
import com.dabinsystems.pact_app.View.SA.SaFrequencyView;
import com.dabinsystems.pact_app.View.SA.SaModeView;
import com.dabinsystems.pact_app.View.SA.SaPeakView;
import com.dabinsystems.pact_app.View.SA.SaSpanView;
import com.dabinsystems.pact_app.View.SA.Standard.LteStandardView;
import com.dabinsystems.pact_app.View.SA.Standard.NrStandardView1;
import com.dabinsystems.pact_app.View.SA.Standard.NrStandardView2;
import com.dabinsystems.pact_app.View.SA.Standard.StandardView;
import com.dabinsystems.pact_app.View.SA.Sweep.GateNumOfGateView;
import com.dabinsystems.pact_app.View.SA.Sweep.GateSourceView;
import com.dabinsystems.pact_app.View.SA.Sweep.GateSweepTimeView;
import com.dabinsystems.pact_app.View.SA.Sweep.GateView;
import com.dabinsystems.pact_app.View.SA.Sweep.SaSweepTimeView;
import com.dabinsystems.pact_app.View.SA.Trace.ChannelDetectorView;
import com.dabinsystems.pact_app.View.SA.Trace.OffsetDetectorView;
import com.dabinsystems.pact_app.View.SA.Trace.SaDetectorView;
import com.dabinsystems.pact_app.View.SA.Trace.SaTraceView;
import com.dabinsystems.pact_app.View.SA.Trace.SaSelectTraceView;
import com.dabinsystems.pact_app.View.SA.Trace.SaTraceModeView;
import com.dabinsystems.pact_app.View.SA.Trace.SaTraceTypeView;
import com.dabinsystems.pact_app.View.SA.Trace.SemTraceView;
import com.dabinsystems.pact_app.View.SweepView;
import com.dabinsystems.pact_app.View.System.Connection.SelectConnectionView;
import com.dabinsystems.pact_app.View.System.FileView;
import com.dabinsystems.pact_app.View.System.HoldoverOptionView;
import com.dabinsystems.pact_app.View.System.RF.SelectRfView;
import com.dabinsystems.pact_app.View.System.SelectClockSourceView;
import com.dabinsystems.pact_app.View.System.SystemView;
import com.dabinsystems.pact_app.View.Calibration.CalibrationView;
import com.dabinsystems.pact_app.View.Calibration.StartCalibrationView;

import com.dabinsystems.pact_app.View.VSWR.VswrView;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

public class ViewHandler {

    private static ViewHandler mController;

    private MainActivity mActivity;
    private ActivityMainBinding binding;

    private float mChartWeight = 1f;

    private VswrView mVswr;
    private DtfView mDtfView;
    private CableLossView mClView;
    private SaView mSaView;

    private ContentView mContentView;

    private MeasurementView mVswrMeasurement;
    private FrequencyView mFrequencyView;
    private AmplitudeView mAmplitudeView;
    private MarkerView mMarkerView;
    private MarkerSetupView mMarkerSetupView;
    private SelectMarkerView mSelectMarkerView;
    private MarkerSearchView mMarkerSearchView;

    private FreqDistView mFreqDistView;
    private DistanceView mDistanceView;

    private CalibrationView mCalibrationView;
    private StartCalibrationView mStartCalibrationView;

    private LimitView mLimitView;

    private DtfSetupView mDtfSetupView;
    private CableListView mCableListView;

    private SaFrequencyView mSaFrequency;
    private SaAmplitudeView mSaAmplitudeView;
    private SaMarkerView mSaMarkerView;
    private SaMarkerView2 mSaMarkerView2;

    private SweepView mSweepView;
    private DataPointsView mDataPointsView;

    private MeasSetupSweptSaView mMeasSetupSweptSaView;
    private MeasSetupChannelPowerView mMeasSetupChannelPowerView;
    private MeasSetupOccupiedBWView mMeasSetupOccupiedBWView;
    private MeasSetupAclrView mMeasSetupAclrView;
    private MeasSetupSemView mMeasSetupSemView;
    private MeasSetupInterferenceView mMeasSetupInterferenceView;
    private UplinkBandView mUplinkBandView;

    //@@ [22.01.04]
    // Holdover Option view
    private HoldoverOptionView mHoldoverOptionView;
    //@@

    //@@ [22.01.27] 원자력 모니터링 관련
    private SelectRfView mSelectRfView;
    private SelectConnectionView mSelectConnectionView;
    //@@

    //ACLR Meas setup view
    private CarrierSetupView mCarrierSetupView;
    private OffsetSetupView mOffsetSetupView;
    private AclrFailSourceView mAclrFailSourceView;
    private NumberOfOffsetView mNumberOfOffsetView;
    private SelectOffsetView mSelectOffsetView;

    //SEM Meas setup view
    private EditMaskView mEditMaskView;
    private EditMaskIndexView mEditMaskIndexView;
    private RefChannelView mRefChannelView;
    private RefChannelRBWView mRefChannelRBWView;
    private RefChannelVBWView mRefChannelVBWView;
    private SemMeasTypeView mSemMeasTypeView;

    //Gate View
    private GateView mGateView;
    private GateSourceView mGateSourceView;
    private GateSweepTimeView mGateSweepTimeView;
    private GateNumOfGateView mGateNumOfGateView;

    private SaSpanView mSaSpan;
    private SaBWView mSaBWView;
    private SaTraceView mTrace;
    private SaRBWView1 mSaRBWView1;
    private SaRBWView2 mSaRBWView2;
    private SaVBWView1 mSaVBWView1;
    private SaVBWView2 mSaVBWView2;
    private SaPeakView mSaPeakView;
    private SaSelectTraceView mSaSelectTraceView;
    private SaTraceModeView mSaTraceModeView;
    private SaDetectorView mSASaDetectorView;
    private SaTraceTypeView mSaTraceTypeView;
    private SaSelectMarkerView mSaSelectMarker;
    private SaMarkerTraceView mSaMarkerTraceView;
    private SaRelativeToView mSaRelativeToView;
    private SaSweepTimeView mSaSweepTimeView;
    private SaModeView mSaModeView;
    private SaMeasView mSaMeasView;
    private SaMeasSetupView mSaMeasSetupView;

    //Standard
    private StandardView mStandardView;
    private LteStandardView mLteStandardView;
    private NrStandardView1 mNrStandardView1;
    private NrStandardView2 mNrStandardView2;

    //5G NR
    private NrMeasSetupView mNrMeasSetupView;
    private NrMeasSetupView2 mNrMeasSetupView2;

    private SystemView mSystemView;
    private FileView mFileView;
    private WindowingView mWindowingView;

    private RecallView mRecallView;
    private FileRecallDialog mFileRecallDialog;
    private FileSaveDialog mFileSaveDialog;

    private NrAmplitudeView mNrAmplitudeView;
    private NrProfileView1 mProfileView1;
    private NrProfileView2 mProfileView2;

    private TaeProfileView1 mTaeProfileView1;
    private TaeProfileView2 mTaeProfileView2;

    private NrSSBInfoView mSSBInfo;
    private NrLimitView mNrLimitView;
    private NrFrequencyView mNrFrequencyView;
    private CarriersView mCarriersView;
    private RefCarrierView mRefCarrierView;
    private SemFailSourceView mSemFailSourceView;
    private EditMaskRBWView mEditMaskRBWView;
    private EditMaskVBWView mEditMaskVBWView;
    private ChannelDetectorView mChannelDetectorView;
    private OffsetDetectorView mOffsetDetectorView;
    private TransmitOnOffMeasSetupView mMeasSetupTransmitView;
    private EditMaskLimitView mEditMaskLimitView;
    private SemTraceView SemTrace;
    private TransmitOnOffLimitView mTransmitLimitView;
    private NumOfTxAntView mNumOfTxAntView;
    private NrTaeView mTaeView;
    private NrInterTaeView InterTaeView;

    private SelectCarrierView mSelectCarrierView;

    private ModAccuracyMeasView mModAccuracyMeasView;
    private ModAccuracyModeView mModAccuracyModeView;

    private LteFddFrequencyView mLteFddFrequencyView;
    private LteFddAmplitudeView mLteFddAmpView;
    private LteFddLimitView mLteFddLimitView;
    private LteFddMeasSetupView mLteFddMeasSetupView;
    private LteFddProfileView mLteFddProfileView;

    private MeasSetupSpuriousView mMeasSetupSpuriousView;
    private SpuriousBandSetupView mBandSetupView;
    private SpuriousFreqRangeTableView mFreqRangeTableView;
    private SelectFreqRangeView mSelectFreqRangeView;

    private SelectClockSourceView mSelectClockSourceView;
//    private SelectTriggerSourceView mSelectTriggerSourceView;
    private NrSelectTriggerSourceView mNrSelectTriggerSourceView;
    private LteSelectTriggerSourceView mLteSelectTriggerSourceView;

    public ViewHandler(MainActivity activity, ActivityMainBinding binding) {
        mActivity = activity;
        this.binding = binding;
    }

    public static ViewHandler getInstance() {
        if (mController == null)
            mController = new ViewHandler((MainActivity) MainActivity.getActivity(), MainActivity.getBinding());
        return mController;
    }

    public static ViewHandler getInstance(MainActivity activity, ActivityMainBinding binding) {
        if (mController == null)
            mController = new ViewHandler(activity, binding);
        return mController;
    }

    public VswrView getVswrView() {

        if (mVswr == null)
            mVswr = new VswrView(mActivity, binding);

        return mVswr;
    }

    public MeasurementView getMeasurementView() {

        if (mVswrMeasurement == null) mVswrMeasurement = new MeasurementView(mActivity, binding);

        return mVswrMeasurement;

    }

    public AmplitudeView getAmplitudeView() {

        if (mAmplitudeView == null) mAmplitudeView = new AmplitudeView(mActivity, binding);

        return mAmplitudeView;

    }

    public MarkerView getMarkerView() {

        if (mMarkerView == null) mMarkerView = new MarkerView(mActivity, binding);

        return mMarkerView;

    }

    public MarkerSetupView getMarkerSetupView() {

        if (mMarkerSetupView == null) mMarkerSetupView = new MarkerSetupView(mActivity, binding);
        return mMarkerSetupView;

    }

    public SelectMarkerView getSelectMarkerView() {

        if (mSelectMarkerView == null) mSelectMarkerView = new SelectMarkerView(mActivity, binding);
        return mSelectMarkerView;

    }

    public MarkerSearchView getMarkerSearchView() {

        if (mMarkerSearchView == null) mMarkerSearchView = new MarkerSearchView(mActivity, binding);
        return mMarkerSearchView;

    }

    public SweepView getSweepView() {

        if (mSweepView == null) mSweepView = new SweepView(mActivity, binding);
        return mSweepView;
    }

    public DataPointsView getDataPointsView() {

        if (mDataPointsView == null) mDataPointsView = new DataPointsView(mActivity, binding);
        return mDataPointsView;
    }

    public LimitView getLimitView() {

        if (mLimitView == null) mLimitView = new LimitView(mActivity, binding);
        return mLimitView;

    }


    public DtfView getDtfView() {

        if (mDtfView == null) mDtfView = new DtfView(mActivity, binding);

        return mDtfView;
    }

    public CableLossView getCableLossView() {

        if (mClView == null) mClView = new CableLossView(mActivity, binding);

        return mClView;

    }

    public SaView getSAView() {
        if (mSaView == null)
            mSaView = new SaView(mActivity, binding);

        return mSaView;
    }

    public ContentView getContent() {

        if (mContentView == null)
            mContentView = new ContentView(mActivity, binding);

        return mContentView;

    }

    public SaModeView getSAMode() {

        if (mSaModeView == null)
            mSaModeView = new SaModeView(mActivity, binding);

        return mSaModeView;

    }

    public ModAccuracyModeView getModAccuracyMode() {

        if (mModAccuracyModeView == null)
            mModAccuracyModeView = new ModAccuracyModeView(mActivity, binding);

        return mModAccuracyModeView;

    }

    public MeasSetupSweptSaView getMeasSetupSweptSa() {

        if (mMeasSetupSweptSaView == null)
            mMeasSetupSweptSaView = new MeasSetupSweptSaView(mActivity, binding);

        return mMeasSetupSweptSaView;

    }

    public MeasSetupChannelPowerView getMeasSetupChannelPower() {

        if (mMeasSetupChannelPowerView == null)
            mMeasSetupChannelPowerView = new MeasSetupChannelPowerView(mActivity, binding);

        return mMeasSetupChannelPowerView;

    }

    public MeasSetupOccupiedBWView getMeasSetupOccupiedBW() {

        if (mMeasSetupOccupiedBWView == null)
            mMeasSetupOccupiedBWView = new MeasSetupOccupiedBWView(mActivity, binding);

        return mMeasSetupOccupiedBWView;

    }

    public MeasSetupAclrView getMeasSetupAclrView() {

        if (mMeasSetupAclrView == null)
            mMeasSetupAclrView = new MeasSetupAclrView(mActivity, binding);
        return mMeasSetupAclrView;

    }

    public MeasSetupSemView getMeasSetupSemView() {

        if (mMeasSetupSemView == null) mMeasSetupSemView = new MeasSetupSemView(mActivity, binding);
        return mMeasSetupSemView;

    }

    public TransmitOnOffMeasSetupView getTransmitMeasSetupView() {

        if (mMeasSetupTransmitView == null)
            mMeasSetupTransmitView = new TransmitOnOffMeasSetupView(mActivity, binding);
        return mMeasSetupTransmitView;

    }

    public TransmitOnOffLimitView getTransmitLimitView() {

        if (mTransmitLimitView == null)
            mTransmitLimitView = new TransmitOnOffLimitView(mActivity, binding);
        return mTransmitLimitView;

    }

    public SelectOffsetView getSelectOffsetView() {

        if (mSelectOffsetView == null) mSelectOffsetView = new SelectOffsetView(mActivity, binding);
        return mSelectOffsetView;

    }

    public CarriersView getCarriersView() {

        if (mCarriersView == null) mCarriersView = new CarriersView(mActivity, binding);
        return mCarriersView;

    }

    public RefCarrierView getRefCarrierView() {

        if (mRefCarrierView == null) mRefCarrierView = new RefCarrierView(mActivity, binding);
        return mRefCarrierView;

    }

    public SaMeasView getSaMeas() {

        if (mSaMeasView == null)
            mSaMeasView = new SaMeasView(mActivity, binding);

        return mSaMeasView;

    }

    public SaMeasSetupView getSaMeasSetup() {

        if (mSaMeasSetupView == null)
            mSaMeasSetupView = new SaMeasSetupView(mActivity, binding);

        return mSaMeasSetupView;

    }

    public ModAccuracyMeasView getModAccuracyMeas() {

        if (mModAccuracyMeasView == null)
            mModAccuracyMeasView = new ModAccuracyMeasView(mActivity, binding);

        return mModAccuracyMeasView;

    }

    public CarrierSetupView getCarrierSetupView() {

        if (mCarrierSetupView == null) mCarrierSetupView = new CarrierSetupView(mActivity, binding);
        return mCarrierSetupView;

    }

    public OffsetSetupView getOffsetSetupView() {

        if (mOffsetSetupView == null) mOffsetSetupView = new OffsetSetupView(mActivity, binding);
        return mOffsetSetupView;

    }

    public AclrFailSourceView getAclrFailSourceView() {

        if (mAclrFailSourceView == null)
            mAclrFailSourceView = new AclrFailSourceView(mActivity, binding);
        return mAclrFailSourceView;

    }

    public SemFailSourceView getSemFailSourceView() {

        if (mSemFailSourceView == null)
            mSemFailSourceView = new SemFailSourceView(mActivity, binding);
        return mSemFailSourceView;

    }

    public EditMaskView getEditMaskView() {

        if (mEditMaskView == null) mEditMaskView = new EditMaskView(mActivity, binding);
        return mEditMaskView;

    }

    public EditMaskIndexView getEditMaskIndexView() {

        if (mEditMaskIndexView == null)
            mEditMaskIndexView = new EditMaskIndexView(mActivity, binding);
        return mEditMaskIndexView;

    }

    public EditMaskLimitView getEditMaskLimitView() {

        if (mEditMaskLimitView == null)
            mEditMaskLimitView = new EditMaskLimitView(mActivity, binding);
        return mEditMaskLimitView;

    }

    public RefChannelView getRefChannelView() {

        if (mRefChannelView == null) mRefChannelView = new RefChannelView(mActivity, binding);
        return mRefChannelView;

    }

    public RefChannelRBWView getRefChannelRBWView() {

        if (mRefChannelRBWView == null)
            mRefChannelRBWView = new RefChannelRBWView(mActivity, binding);
        return mRefChannelRBWView;

    }

    public RefChannelVBWView getRefChannelVBWView() {

        if (mRefChannelVBWView == null)
            mRefChannelVBWView = new RefChannelVBWView(mActivity, binding);
        return mRefChannelVBWView;

    }

    public EditMaskRBWView getEditMaskRBWView() {

        if (mEditMaskRBWView == null) mEditMaskRBWView = new EditMaskRBWView(mActivity, binding);
        return mEditMaskRBWView;

    }

    public EditMaskVBWView getEditMaskVBWView() {

        if (mEditMaskVBWView == null) mEditMaskVBWView = new EditMaskVBWView(mActivity, binding);
        return mEditMaskVBWView;

    }

    public SemMeasTypeView getSemMeasTypeView() {

        if (mSemMeasTypeView == null) mSemMeasTypeView = new SemMeasTypeView(mActivity, binding);
        return mSemMeasTypeView;

    }

    public GateSourceView getGateSourceView() {

        if (mGateSourceView == null) mGateSourceView = new GateSourceView(mActivity, binding);
        return mGateSourceView;
    }

    public GateView getGateView() {
        if (mGateView == null) mGateView = new GateView(mActivity, binding);
        return mGateView;
    }

    public GateSweepTimeView getGateSweepTimeView() {
        if (mGateSweepTimeView == null) mGateSweepTimeView = new GateSweepTimeView(mActivity, binding);
        return mGateSweepTimeView;
    }

    public GateNumOfGateView getGateNumOfGateView() {
        if (mGateNumOfGateView == null) mGateNumOfGateView = new GateNumOfGateView(mActivity, binding);
        return mGateNumOfGateView;
    }

    public ChannelDetectorView getChannelDetectorView() {

        if (mChannelDetectorView == null)
            mChannelDetectorView = new ChannelDetectorView(mActivity, binding);
        return mChannelDetectorView;

    }

    public OffsetDetectorView getOffsetDetectorView() {

        if (mOffsetDetectorView == null)
            mOffsetDetectorView = new OffsetDetectorView(mActivity, binding);
        return mOffsetDetectorView;

    }

    public FrequencyView getFrequencyView() {

        if (mFrequencyView == null)
            mFrequencyView = new FrequencyView(mActivity, binding);

        return mFrequencyView;
    }

    public SaFrequencyView getSaFrequencyView() {
        if (mSaFrequency == null)
            mSaFrequency = new SaFrequencyView(mActivity, binding);
        return mSaFrequency;
    }

    public SaSpanView getSASpanView() {

        if (mSaSpan == null)
            mSaSpan = new SaSpanView(mActivity, binding);
        return mSaSpan;

    }

    public SaBWView getBW() {

        if (mSaBWView == null)
            mSaBWView = new SaBWView(mActivity, binding);
        return mSaBWView;

    }

    public SaRBWView1 getRBW1() {
        if (mSaRBWView1 == null)
            mSaRBWView1 = new SaRBWView1(mActivity, binding);
        return mSaRBWView1;
    }

    public SaRBWView2 getRBW2() {
        if (mSaRBWView2 == null)
            mSaRBWView2 = new SaRBWView2(mActivity, binding);
        return mSaRBWView2;
    }

    public SaVBWView1 getVBW1() {
        if (mSaVBWView1 == null)
            mSaVBWView1 = new SaVBWView1(mActivity, binding);
        return mSaVBWView1;
    }

    public SaVBWView2 getVBW2() {
        if (mSaVBWView2 == null)
            mSaVBWView2 = new SaVBWView2(mActivity, binding);
        return mSaVBWView2;
    }


    public SaAmplitudeView getSAAmplitudeView() {
        if (mSaAmplitudeView == null)
            mSaAmplitudeView = new SaAmplitudeView(mActivity, binding);
        return mSaAmplitudeView;
    }

    public NrAmplitudeView getDemodulationAmplitudeView() {
        if (mNrAmplitudeView == null)
            mNrAmplitudeView = new NrAmplitudeView(mActivity, binding);
        return mNrAmplitudeView;
    }

    public NrFrequencyView getDemodulationFrequencyView() {
        if (mNrFrequencyView == null)
            mNrFrequencyView = new NrFrequencyView(mActivity, binding);
        return mNrFrequencyView;
    }

    public SaMarkerView getSaMarkerView() {
        if (mSaMarkerView == null)
            mSaMarkerView = new SaMarkerView(mActivity, binding);

        return mSaMarkerView;
    }

    public SaMarkerView2 getSaMarkerView2() {
        if (mSaMarkerView2 == null)
            mSaMarkerView2 = new SaMarkerView2(mActivity, binding);

        return mSaMarkerView2;
    }

    public SaSelectMarkerView getSaSelectMarkerView() {
        if (mSaSelectMarker == null)
            mSaSelectMarker = new SaSelectMarkerView(mActivity, binding);

        return mSaSelectMarker;
    }

    public SaMarkerTraceView getMarkerTrace() {
        if (mSaMarkerTraceView == null)
            mSaMarkerTraceView = new SaMarkerTraceView(mActivity, binding);

        return mSaMarkerTraceView;
    }

    public SaRelativeToView getRelativeTo() {

        if (mSaRelativeToView == null)
            mSaRelativeToView = new SaRelativeToView(mActivity, binding);

        return mSaRelativeToView;
    }

    public SaTraceView getTrace() {

        if (mTrace == null)
            mTrace = new SaTraceView(mActivity, binding);

        return mTrace;

    }

    public SemTraceView getSemTraceView() {

        if (SemTrace == null)
            SemTrace = new SemTraceView(mActivity, binding);

        return SemTrace;

    }

    public SaSelectTraceView getSelectTrace() {

        if (mSaSelectTraceView == null)
            mSaSelectTraceView = new SaSelectTraceView(mActivity, binding);

        return mSaSelectTraceView;

    }

    public SaTraceModeView getTraceMode() {

        if (mSaTraceModeView == null)
            mSaTraceModeView = new SaTraceModeView(mActivity, binding);

        return mSaTraceModeView;

    }

    public SaTraceTypeView getTraceType() {

        if (mSaTraceTypeView == null)
            mSaTraceTypeView = new SaTraceTypeView(mActivity, binding);

        return mSaTraceTypeView;

    }

    public SaDetectorView getSADetector() {

        if (mSASaDetectorView == null)
            mSASaDetectorView = new SaDetectorView(mActivity, binding);

        return mSASaDetectorView;

    }

    public SaPeakView getSAPeakView() {
        if (mSaPeakView == null)
            mSaPeakView = new SaPeakView(mActivity, binding);
        return mSaPeakView;
    }

    public SaSweepTimeView getSaSweepView() {
        if (mSaSweepTimeView == null)
            mSaSweepTimeView = new SaSweepTimeView(mActivity, binding);
        return mSaSweepTimeView;
    }

    public NrMeasSetupView getNrMeasSetupView() {
        if (mNrMeasSetupView == null) {
            mNrMeasSetupView = new NrMeasSetupView(mActivity, binding);
        }
        return mNrMeasSetupView;
    }

    public NrMeasSetupView2 getNrMeasSetupView2() {
        if (mNrMeasSetupView2 == null) {
            mNrMeasSetupView2 = new NrMeasSetupView2(mActivity, binding);
        }
        return mNrMeasSetupView2;
    }

    public float getChartWeight() {
        return mChartWeight;
    }

    public void setChartWeight(float weight) {

        if (weight < 0 || weight > 1) return;

        mChartWeight = weight;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, mChartWeight);

        mActivity.runOnUiThread(() -> {
            binding.lineChartLayout.mainLineChart.setLayoutParams(params);

        });

    }

    public CalibrationView getCalibrationView() {

        if (mCalibrationView == null) mCalibrationView = new CalibrationView(mActivity, binding);

        return mCalibrationView;

    }

    public StartCalibrationView getStartCalibrationView() {

        if (mStartCalibrationView == null)
            mStartCalibrationView = new StartCalibrationView(mActivity, binding);

        return mStartCalibrationView;

    }

    public SystemView getSystemView() {

        if (mSystemView == null) mSystemView = new SystemView(mActivity, binding);
        return mSystemView;
    }

    public FileView getFileView() {

        if (mFileView == null) mFileView = new FileView(mActivity, binding);
        return mFileView;

    }

    public FreqDistView getFreqDistView() {

        if (mFreqDistView == null) mFreqDistView = new FreqDistView(mActivity, binding);
        return mFreqDistView;
    }

    public DistanceView getDistanceView() {

        if (mDistanceView == null) mDistanceView = new DistanceView(mActivity, binding);
        return mDistanceView;

    }

    public DtfSetupView getDtfSetupView() {

        if (mDtfSetupView == null) mDtfSetupView = new DtfSetupView(mActivity, binding);
        return mDtfSetupView;

    }

    public CableListView getCableListView() {

        if (mCableListView == null) mCableListView = new CableListView(mActivity, binding);
        return mCableListView;

    }

    public WindowingView getWindowingView() {

        if (mWindowingView == null) mWindowingView = new WindowingView(mActivity, binding);
        return mWindowingView;

    }

    public FileRecallDialog getFileRecallDialog() {

        if (mFileRecallDialog == null) mFileRecallDialog = new FileRecallDialog(mActivity, binding);
        return mFileRecallDialog;

    }

    public FileSaveDialog getFileSaveDialog() {

        if (mFileSaveDialog == null) mFileSaveDialog = new FileSaveDialog(mActivity, binding);
        return mFileSaveDialog;

    }

    public RecallView getRecallView() {

        if (mRecallView == null) mRecallView = new RecallView(mActivity, binding);
        return mRecallView;

    }

    public StandardView getStandardView() {

        if (mStandardView == null) mStandardView = new StandardView(mActivity, binding);

        return mStandardView;

    }

    public NumberOfOffsetView getNumberOfOffsetView() {

        if (mNumberOfOffsetView == null)
            mNumberOfOffsetView = new NumberOfOffsetView(mActivity, binding);
        return mNumberOfOffsetView;

    }

    public LteStandardView getLteStandardView() {

        if (mLteStandardView == null) mLteStandardView = new LteStandardView(mActivity, binding);

        return mLteStandardView;

    }

    public NrStandardView1 getNrStandardView1() {

        if (mNrStandardView1 == null) mNrStandardView1 = new NrStandardView1(mActivity, binding);

        return mNrStandardView1;

    }

    public NrStandardView2 getNrStandardView2() {

        if (mNrStandardView2 == null) mNrStandardView2 = new NrStandardView2(mActivity, binding);

        return mNrStandardView2;

    }

    public NrProfileView1 getNr5gProfileView1() {

        if (mProfileView1 == null) mProfileView1 = new NrProfileView1(mActivity, binding);

        return mProfileView1;

    }

    public NrProfileView2 getNr5gProfileView2() {

        if (mProfileView2 == null) mProfileView2 = new NrProfileView2(mActivity, binding);

        return mProfileView2;

    }

    public TaeProfileView1 getTaeProfileView1() {

        if (mTaeProfileView1 == null) mTaeProfileView1 = new TaeProfileView1(mActivity, binding);

        return mTaeProfileView1;

    }

    public TaeProfileView2 getTaeProfileView2() {

        if (mTaeProfileView2 == null) mTaeProfileView2 = new TaeProfileView2(mActivity, binding);

        return mTaeProfileView2;

    }

    public NrSSBInfoView getSSBInfoView() {

        if (mSSBInfo == null) mSSBInfo = new NrSSBInfoView(mActivity, binding);

        return mSSBInfo;

    }

    public NrLimitView getNrLimitView() {

        if (mNrLimitView == null) mNrLimitView = new NrLimitView(mActivity, binding);

        return mNrLimitView;

    }

    public NumOfTxAntView getNumOfTxAntView() {

        if (mNumOfTxAntView == null) mNumOfTxAntView = new NumOfTxAntView(mActivity, binding);

        return mNumOfTxAntView;
    }

    public NrTaeView getTaeView() {
        if (mTaeView == null) mTaeView = new NrTaeView(mActivity, binding);
        return mTaeView;
    }

    public LteFddFrequencyView getLteFddFrequencyView() {
        if (mLteFddFrequencyView == null)
            mLteFddFrequencyView = new LteFddFrequencyView(mActivity, binding);
        return mLteFddFrequencyView;
    }

    public LteFddAmplitudeView getLteFddAmpView() {
        if (mLteFddAmpView == null) mLteFddAmpView = new LteFddAmplitudeView(mActivity, binding);
        return mLteFddAmpView;
    }

    public LteFddLimitView getLteFddLimitView() {
        if (mLteFddLimitView == null) mLteFddLimitView = new LteFddLimitView(mActivity, binding);
        return mLteFddLimitView;
    }

    public LteFddMeasSetupView getLteFddMeasSetupView() {
        if (mLteFddMeasSetupView == null)
            mLteFddMeasSetupView = new LteFddMeasSetupView(mActivity, binding);
        return mLteFddMeasSetupView;
    }

    public LteFddProfileView getLteFddProfileView() {
        if (mLteFddProfileView == null)
            mLteFddProfileView = new LteFddProfileView(mActivity, binding);
        return mLteFddProfileView;
    }

    public NrInterTaeView getInterTaeView() {
        if (InterTaeView == null) InterTaeView = new NrInterTaeView(mActivity, binding);
        return InterTaeView;
    }

    public SelectCarrierView getSelectCarrierView() {
        if (mSelectCarrierView == null)
            mSelectCarrierView = new SelectCarrierView(mActivity, binding);
        return mSelectCarrierView;

    }

    public MeasSetupSpuriousView getMeasSetupSpuriousView() {
        if (mMeasSetupSpuriousView == null)
            mMeasSetupSpuriousView = new MeasSetupSpuriousView(mActivity, binding);
        return mMeasSetupSpuriousView;
    }

    public SpuriousBandSetupView getBandSetupView() {
        if (mBandSetupView == null) mBandSetupView = new SpuriousBandSetupView(mActivity, binding);
        return mBandSetupView;
    }

    public SpuriousFreqRangeTableView getFreqRangeTableView() {
        if (mFreqRangeTableView == null)
            mFreqRangeTableView = new SpuriousFreqRangeTableView(mActivity, binding);
        return mFreqRangeTableView;
    }

    public SelectFreqRangeView getSelectFreqRangeView() {
        if (mSelectFreqRangeView == null)
            mSelectFreqRangeView = new SelectFreqRangeView(mActivity, binding);
        return mSelectFreqRangeView;
    }

    public SelectClockSourceView getSelectClockSourceView() {
        if (mSelectClockSourceView == null)
            mSelectClockSourceView = new SelectClockSourceView(mActivity, binding);
        return mSelectClockSourceView;
    }

    //@@ [22.01.03] Holdover Option View
    public HoldoverOptionView getHoldoverOptView() {
        if (mHoldoverOptionView == null)
            mHoldoverOptionView = new HoldoverOptionView(mActivity, binding);
        return mHoldoverOptionView;
    }
    //@@

    //@@ [22.01.27] 원자력 모니터링 관련
    public SelectConnectionView getSelectConnectionView() {
        if (mSelectConnectionView == null)
            mSelectConnectionView = new SelectConnectionView(mActivity, binding);
        return mSelectConnectionView;
    }

    public SelectRfView getSelectRfView() {
        if (mSelectRfView == null)
            mSelectRfView = new SelectRfView(mActivity, binding);
        return mSelectRfView;
    }
    //@@

    /*public SelectTriggerSourceView getNrSelectTriggerSourceView() {
        if (mSelectTriggerSourceView == null)
            mSelectTriggerSourceView = new SelectTriggerSourceView(mActivity, binding);
        return mSelectTriggerSourceView;
    }*/

    public LteSelectTriggerSourceView getLteSelectTriggerSourceView() {
        if (mLteSelectTriggerSourceView == null)
            mLteSelectTriggerSourceView = new LteSelectTriggerSourceView(mActivity, binding);
        return mLteSelectTriggerSourceView;
    }

    public NrSelectTriggerSourceView getNrSelectTriggerSourceView() {
        if (mNrSelectTriggerSourceView == null)
            mNrSelectTriggerSourceView = new NrSelectTriggerSourceView(mActivity, binding);
        return mNrSelectTriggerSourceView;
    }

    public MeasSetupInterferenceView getInterferenceHuntingView() {
        if (mMeasSetupInterferenceView == null) {
            mMeasSetupInterferenceView = new MeasSetupInterferenceView(mActivity, binding);
        }
        return mMeasSetupInterferenceView;
    }

    public UplinkBandView getUplinkBandView() {
        if (mUplinkBandView == null) {
            mUplinkBandView = new UplinkBandView(mActivity, binding);
        }
        return mUplinkBandView;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // 5G NR Scan
    private NrScanMeasSetupView mNrScanMeasSetupView;
    private NrScanCarrierView mNrScanCarrierView;
    private NrScanSelCarrierView mNrScanSelCarrierView;
    private NrScanSelProfileView mNrScanSelProfileView;
    private NrScanTraceSetView mNrScanTraceSetView;
    private NrScanTraceSelectView mNrScanTraceSelectView;

    public NrScanMeasSetupView getNrScanMeasSetupView() {
        if (mNrScanMeasSetupView == null)
            mNrScanMeasSetupView = new NrScanMeasSetupView(mActivity, binding);
        return mNrScanMeasSetupView;
    }

    public NrScanCarrierView getNrScanCarrierView() {
        if (mNrScanCarrierView == null)
            mNrScanCarrierView = new NrScanCarrierView(mActivity, binding);
        return mNrScanCarrierView;
    }

    public NrScanSelCarrierView getNrScanSelCarrierView() {
        if (mNrScanSelCarrierView == null)
            mNrScanSelCarrierView = new NrScanSelCarrierView(mActivity, binding);
        return mNrScanSelCarrierView;
    }

    public NrScanSelProfileView getNrScanSelProfileView() {
        if (mNrScanSelProfileView == null)
            mNrScanSelProfileView = new NrScanSelProfileView(mActivity, binding);
        return mNrScanSelProfileView;
    }

    public NrScanTraceSetView getNrScanTraceSetView() {
        if (mNrScanTraceSetView == null)
            mNrScanTraceSetView = new NrScanTraceSetView(mActivity, binding);
        return mNrScanTraceSetView;
    }

    public NrScanTraceSelectView getNrScanTraceSelectView() {
        if (mNrScanTraceSelectView == null)
            mNrScanTraceSelectView = new NrScanTraceSelectView(mActivity, binding);
        return mNrScanTraceSelectView;
    }
}

package com.dabinsystems.pact_app.Util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;

import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.databinding.ActivityMainBinding;

import me.grantland.widget.AutofitTextView;

import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class FontUtil {

    Context mContext;
    ActivityMainBinding binding;

    private final int TITLE_FONT_SIZE = 18;
    private final int TITLE_FONT_MAX_SIZE = 20;

    private final int TOP_FONT_SIZE = 13;
    private final int TOP_FONT_MAX_SIZE = 15;

    private final int ICON_FONT_SIZE = 15;
    private final int ICON_FONT_MAX_SIZE = 17;

    private final int MKR_TABLE_FONT_SIZE = 8;
    private final int MKR_TABLE_FONT_MAX_SIZE = 9;

    private final int SMALL_FONT_SIZE = 9;
    private final int SMALL_FONT_MAX_SIZE = 11;

    private final int MARKER_INFO_FONT_SIZE = 9;
    private final int MARKER_INFO_FONT_MAX_SIZE = 11;

    private final int SUB_INFO_FONT_SIZE = 11;
    private final int SUB_INFO_FONT_MAX_SIZE = 13;

    private final int DEMODULATION_TITLE_FONT_SIZE = 9;
    private final int DEMODULATION_TITLE_MAX_FONT_SIZE = 11;

    private final int DEMODULATION_CONTENT_FONT_SIZE = 8;
    private final int DEMODULATION_CONTENT_MAX_FONT_SIZE = 9;

    private final int DEMODULATION_TABLE_FONT_SIZE = 8;
    private final int DEMODULATION_TABLE_MAX_FONT_SIZE = 9;

    private final int LTE_TITLE_FONT_SIZE = 9;
    private final int LTE_TITLE_MAX_FONT_SIZE = 11;

    private final int LTE_TABLE_FONT_SIZE = 8;
    private final int LTE_TABLE_MAX_FONT_SIZE = 9;

    private final int LTE_CONTENT_FONT_SIZE = 9;
    private final int LTE_CONTENT_MAX_FONT_SIZE = 11;

    //@@ ACLR FONT FIX
    //org
    // private final int ACLR_FONT_SIZE = 10;
    //org
    private final int ACLR_FONT_SIZE = 9;

    private final int ACLR_MAX_FONT_SIZE = 11;

    private final int SEM_FONT_SIZE = 10;
    private final int SEM_MAX_FONT_SIZE = 11;

    private final int TRANSMIT_FONT_SIZE = 11;
    private final int TRANSMIT_MAX_FONT_SIZE = 13;

    private final int GATE_FONT_SIZE = 9;
    private final int GATE_MAX_FONT_SIZE = 12;

    private final int FW_STATUS_FONT_SIZE = 8;
    private final int FW_STATUS_MAX_FONT_SIZE = 9;

    private final int INTERFERENCE_HUNTING_SIZE = 11;
    private final int INTERFERENCE_HUNTING_MAX_SIZE = 13;


    public FontUtil(Context context, ActivityMainBinding binding) {
        mContext = context;
        this.binding = binding;
    }


    public void initView() {

        new Thread(() -> {

            new Handler(Looper.getMainLooper()).post(() -> {

                float dp1 = convertDpToPixel(1, mContext);
                float dp8 = convertDpToPixel(8, mContext);
                float dp10 = convertDpToPixel(10, mContext);
                float dp14 = convertDpToPixel(14, mContext);
                float dp16 = convertDpToPixel(16, mContext);
                float dp13 = convertDpToPixel(13, mContext);
                float dp15 = convertDpToPixel(15, mContext);
                float dp17 = convertDpToPixel(17, mContext);
                float dp20 = convertDpToPixel(20, mContext);
                float dp25 = convertDpToPixel(25, mContext);
                float dpSubInfoMax = convertDpToPixel(SUB_INFO_FONT_MAX_SIZE, mContext);
                float dpSubInfoSize = convertDpToPixel(SUB_INFO_FONT_SIZE, mContext);
                float dpTopMax = convertDpToPixel(TOP_FONT_MAX_SIZE, mContext);
                float dpTopSize = convertDpToPixel(TOP_FONT_SIZE, mContext);
                float dpIconMax = convertDpToPixel(ICON_FONT_MAX_SIZE, mContext);
                float dpIconSize = convertDpToPixel(ICON_FONT_SIZE, mContext);
                float dpSmallMax = convertDpToPixel(ICON_FONT_MAX_SIZE, mContext);
                float dpSmallSize = convertDpToPixel(ICON_FONT_SIZE, mContext);
                float dpDemodulationTitleMax = convertDpToPixel(DEMODULATION_TITLE_MAX_FONT_SIZE, mContext);
                float dpDemodulationTitleSize = convertDpToPixel(DEMODULATION_TITLE_FONT_SIZE, mContext);
                float dpDemodulationContentMax = convertDpToPixel(DEMODULATION_CONTENT_MAX_FONT_SIZE, mContext);
                float dpDemodulationContentSize = convertDpToPixel(DEMODULATION_CONTENT_FONT_SIZE, mContext);
                float dpDemodulationTableMax = convertDpToPixel(DEMODULATION_TABLE_MAX_FONT_SIZE, mContext);
                float dpDemodulationTableSize = convertDpToPixel(DEMODULATION_TABLE_FONT_SIZE, mContext);
                float dpMarkerInfoMax = convertDpToPixel(MARKER_INFO_FONT_MAX_SIZE, mContext);
                float dpMarkerInfoSize = convertDpToPixel(MARKER_INFO_FONT_SIZE, mContext);
                float dpMkrTableMax = convertDpToPixel(MKR_TABLE_FONT_MAX_SIZE, mContext);
                float dpMkrTableSize = convertDpToPixel(MKR_TABLE_FONT_SIZE, mContext);
                float dpAclrMax = convertDpToPixel(ACLR_MAX_FONT_SIZE, mContext);
                float dpAclrSize = convertDpToPixel(ACLR_FONT_SIZE, mContext);

                binding.tvAutoScale.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvAutoScale.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp17);
                binding.tvAutoScale.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp17);

                binding.tvRightMenuTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvRightMenuTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp20);
                binding.tvRightMenuTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp20);
                binding.tvRightMenuTitle.setTypeface(Typeface.DEFAULT_BOLD);

                binding.tvTopCenterTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTopCenterTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TITLE_FONT_MAX_SIZE, mContext));
                binding.tvTopCenterTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TITLE_FONT_SIZE, mContext));

                binding.recallMessageLayout.tvWarningCal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.recallMessageLayout.tvWarningCal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp25);
                binding.recallMessageLayout.tvWarningCal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp25);

                binding.limitMessageLayout.tvPassFailBox.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.limitMessageLayout.tvPassFailBox.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp20);
                binding.limitMessageLayout.tvPassFailBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp17);

                binding.loadingWindowLayout.tvloadingWindow.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.loadingWindowLayout.tvloadingWindow.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.loadingWindowLayout.tvloadingWindow.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);

                binding.lineChartLayout.sweptSaInfo.tvRbwLabel.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvRbwLabel.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.sweptSaInfo.tvRbwLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.sweptSaInfo.tvRbwLabel.setTextColor(Color.WHITE);

                binding.lineChartLayout.sweptSaInfo.tvRbwVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvRbwVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.sweptSaInfo.tvRbwVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);

                binding.lineChartLayout.sweptSaInfo.tvSpanLabel.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvSpanLabel.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.sweptSaInfo.tvSpanLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.sweptSaInfo.tvSpanLabel.setTextColor(Color.WHITE);

                binding.lineChartLayout.sweptSaInfo.tvSpanVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvSpanVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.sweptSaInfo.tvSpanVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.sweptSaInfo.tvSpanVal.setTextColor(Color.WHITE);

                binding.lineChartLayout.sweptSaInfo.tvSweepLabel.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvSweepLabel.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.sweptSaInfo.tvSweepLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.sweptSaInfo.tvSweepLabel.setTextColor(Color.WHITE);

                binding.lineChartLayout.sweptSaInfo.tvSweepVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvSweepVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.sweptSaInfo.tvSweepVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.sweptSaInfo.tvSweepVal.setTextColor(Color.WHITE);

                binding.lineChartLayout.sweptSaInfo.tvVbwLabel.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvVbwLabel.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.sweptSaInfo.tvVbwLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.sweptSaInfo.tvVbwLabel.setTextColor(Color.WHITE);

                binding.lineChartLayout.sweptSaInfo.tvVbwVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvVbwVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.sweptSaInfo.tvVbwVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.sweptSaInfo.tvVbwVal.setTextColor(Color.WHITE);

                /* */

//                binding.lineChartLayout.channelPowerLayout.tvChannelPower.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//                binding.lineChartLayout.channelPowerLayout.tvChannelPower.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
//                binding.lineChartLayout.channelPowerLayout.tvChannelPower.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
//                binding.lineChartLayout.channelPowerLayout.tvChannelPower.setTextColor(Color.WHITE);

                binding.lineChartLayout.channelPowerLayout.tvChannelPowerVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.channelPowerLayout.tvChannelPowerVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.channelPowerLayout.tvChannelPowerVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.channelPowerLayout.tvChannelPowerVal.setTextColor(Color.WHITE);

//                binding.lineChartLayout.channelPowerLayout.tvDensity.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//                binding.lineChartLayout.channelPowerLayout.tvDensity.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
//                binding.lineChartLayout.channelPowerLayout.tvDensity.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
//                binding.lineChartLayout.channelPowerLayout.tvDensity.setTextColor(Color.WHITE);

                binding.lineChartLayout.channelPowerLayout.tvDensityVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.channelPowerLayout.tvDensityVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.channelPowerLayout.tvDensityVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.channelPowerLayout.tvDensityVal.setTextColor(Color.WHITE);

//                binding.lineChartLayout.occBwLayout.tvTotalPower.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//                binding.lineChartLayout.occBwLayout.tvTotalPower.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
//                binding.lineChartLayout.occBwLayout.tvTotalPower.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
//                binding.lineChartLayout.occBwLayout.tvTotalPower.setTextColor(Color.WHITE);

                binding.lineChartLayout.occBwLayout.tvTotalPowerVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.occBwLayout.tvTotalPowerVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.occBwLayout.tvTotalPowerVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.occBwLayout.tvTotalPowerVal.setTextColor(Color.WHITE);

//                binding.lineChartLayout.occBwLayout.tvOccupiedBW.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//                binding.lineChartLayout.occBwLayout.tvOccupiedBW.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
//                binding.lineChartLayout.occBwLayout.tvOccupiedBW.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
//                binding.lineChartLayout.occBwLayout.tvOccupiedBW.setTextColor(Color.WHITE);

                binding.lineChartLayout.occBwLayout.tvOccBWVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.occBwLayout.tvOccBWVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.occBwLayout.tvOccBWVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.occBwLayout.tvOccBWVal.setTextColor(Color.WHITE);

//                binding.lineChartLayout.occBwLayout.tvXDBBW.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//                binding.lineChartLayout.occBwLayout.tvXDBBW.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
//                binding.lineChartLayout.occBwLayout.tvXDBBW.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
//                binding.lineChartLayout.occBwLayout.tvXDBBW.setTextColor(Color.WHITE);

                binding.lineChartLayout.occBwLayout.tvXDBBWVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.occBwLayout.tvXDBBWVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.occBwLayout.tvXDBBWVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.occBwLayout.tvXDBBWVal.setTextColor(Color.WHITE);

//                binding.lineChartLayout.occBwLayout.tvXDB.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//                binding.lineChartLayout.occBwLayout.tvXDB.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
//                binding.lineChartLayout.occBwLayout.tvXDB.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
//                binding.lineChartLayout.occBwLayout.tvXDB.setTextColor(Color.WHITE);

                binding.lineChartLayout.occBwLayout.tvXDBVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.occBwLayout.tvXDBVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.occBwLayout.tvXDBVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.occBwLayout.tvXDBVal.setTextColor(Color.WHITE);


                // set demodulation layout text size
                binding.demodulationLayout.scatterChartTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.scatterChartTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTitleMax);
                binding.demodulationLayout.scatterChartTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTitleSize);

                binding.demodulationLayout.tvCandleChartTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvCandleChartTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTitleMax);
                binding.demodulationLayout.tvCandleChartTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTitleSize);

                binding.demodulationLayout.tvCellInformationTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvCellInformationTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTitleMax);
                binding.demodulationLayout.tvCellInformationTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTitleSize);

                binding.demodulationLayout.tvPowerInfoTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPowerInfoTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTitleMax);
                binding.demodulationLayout.tvPowerInfoTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTitleSize);

                binding.demodulationLayout.tvSignalQualityTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSignalQualityTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTitleMax);
                binding.demodulationLayout.tvSignalQualityTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTitleSize);

                binding.demodulationLayout.tvTimeInformationTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvTimeInformationTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTitleMax);
                binding.demodulationLayout.tvTimeInformationTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTitleSize);

                binding.demodulationLayout.tvSinrTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSinrTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvSinrTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvSinrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSinrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvSinrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvBeamPowerTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvBeamPowerTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvBeamPowerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvBeamPowerValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvBeamPowerValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvBeamPowerValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvChannelTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvChannelTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvChannelTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvChannelBandWidthTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvChannelBandWidthTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvChannelBandWidthTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvChannelBandwidthValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvChannelBandwidthValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvChannelBandwidthValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvPhysicalCellIdTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPhysicalCellIdTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvPhysicalCellIdTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvPhysicalCellIdValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPhysicalCellIdValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvPhysicalCellIdValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvGroupIdTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvGroupIdTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvGroupIdTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvGroupIdValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvGroupIdValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvGroupIdValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvSectorTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSectorTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvSectorTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvSectorValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSectorValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvSectorValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvSubcarrierSpacingTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSubcarrierSpacingTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvSubcarrierSpacingTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvSubcarrierSpacingValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSubcarrierSpacingValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvSubcarrierSpacingValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);



                /* Power Information Font*/

                binding.demodulationLayout.tvSsbFreqTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSsbFreqTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvSsbFreqTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvSsbFreqValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSsbFreqValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvSsbFreqValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvRsrpTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvRsrpTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvRsrpTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvRsrpValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvRsrpValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvRsrpValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvRsrqTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvRsrqTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvRsrqTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvRsrqValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvRsrqValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvRsrqValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvBeamPowerTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvBeamPowerTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvBeamPowerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvBeamPowerValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvBeamPowerValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvBeamPowerValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvExpectedTxMaxPowerTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvExpectedTxMaxPowerTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvExpectedTxMaxPowerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);

                binding.demodulationLayout.tvExpectedTxMaxPowerValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvExpectedTxMaxPowerValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
                binding.demodulationLayout.tvExpectedTxMaxPowerValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);


                /*Signal Quality
                 *
                 * Frequency Offset*/

                binding.demodulationLayout.tvFreqOffsetTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvFreqOffsetTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvFreqOffsetTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvThresholdTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvThresholdTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvThresholdTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvThresholdValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvThresholdValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvThresholdValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPpmTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPpmTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPpmTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPpmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPpmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPpmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvHzTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvHzTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvHzTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvHzValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvHzValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvHzValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvFreqOffsetResult.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvFreqOffsetResult.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvFreqOffsetResult.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);


                /*Signal Quality
                 *
                 * EVM*/

                binding.demodulationLayout.tvEVM.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvEVM.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvEVM.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvEvmTitle1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvEvmTitle1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvEvmTitle1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvEvmTitle2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvEvmTitle2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvEvmTitle2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvEVMThreshold.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvEVMThreshold.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvEVMThreshold.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvEvmAvg.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvEvmAvg.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvEvmAvg.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvEvmResult.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvEvmResult.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvEvmResult.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);


                /*Signal Quality Info*/

                binding.demodulationLayout.tvChannelTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvChannelTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvChannelTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvEVMTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvEVMTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvEVMTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPWRTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPWRTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPWRTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvModTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvModTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvModTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvRBTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvRBTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvRBTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPssTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPssTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPssTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPssEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPssEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPssEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPssPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPssPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPssPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPssModeValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPssModeValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPssModeValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPssRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPssRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPssRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvSssTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSssTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvSssTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvSssEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSssEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvSssEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvSssPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSssPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvSssPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvSssModeValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSssModeValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvSssModeValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvSssRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvSssRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvSssRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPbchTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPbchTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPbchTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPbchEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPbchEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPbchEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPbchPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPbchPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPbchPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPbchModeValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPbchModeValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPbchModeValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPbchRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPbchRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPbchRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPbchDmrsTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPbchDmrsTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPbchDmrsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPbchDmrsEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPbchDmrsEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPbchDmrsEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPbchDmrsPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPbchDmrsPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPbchDmrsPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPbchDmrsModeValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPbchDmrsModeValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPbchDmrsModeValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvPbchDmrsRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvPbchDmrsRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvPbchDmrsRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

//            binding.demodulationLayout.tvPdcchTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdcchTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdcchTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdcchEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdcchEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdcchEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdcchPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdcchPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdcchPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdcchModeValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdcchModeValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdcchModeValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdcchRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdcchRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdcchRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdcchDmrsTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdcchDmrsTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdcchDmrsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdcchDmrsEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdcchDmrsEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdcchDmrsEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdcchDmrsPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdcchDmrsPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdcchDmrsPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdcchDmrsModeValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdcchDmrsModeValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdcchDmrsModeValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdcchDmrsRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdcchDmrsRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdcchDmrsRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdschTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdschTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdschTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdschEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdschEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdschEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdschPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdschPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdschPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdschModeValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdschModeValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdschModeValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdschRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdschRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdschRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdschDmrsTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdschDmrsTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdschDmrsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdschDmrsEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdschDmrsEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdschDmrsEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdschDmrsPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdschDmrsPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdschDmrsPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdschDmrsModeValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdschDmrsModeValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdschDmrsModeValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvPdschDmrsRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvPdschDmrsRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvPdschDmrsRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);


                binding.demodulationLayout.tvTimingOffsetTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvTimingOffsetTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvTimingOffsetTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvTimingOffsetValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvTimingOffsetValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvTimingOffsetValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

//            binding.demodulationLayout.tvTimeAlignmentErrorTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvTimeAlignmentErrorTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
//            binding.demodulationLayout.tvTimeAlignmentErrorTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvTimeAlignmentErrorValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvTimeAlignmentErrorValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvTimeAlignmentErrorValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvTaeUnit.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvTaeUnit.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvTaeUnit.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvTaeType.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvTaeType.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvTaeType.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvThrdTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvThrdTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvThrdTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvThrdValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvThrdValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvThrdValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

                binding.demodulationLayout.tvTimeAlignmentErrorResult.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.demodulationLayout.tvTimeAlignmentErrorResult.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableMax);
                binding.demodulationLayout.tvTimeAlignmentErrorResult.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationTableSize);

//            binding.demodulationLayout.tvTim.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvTim.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvTim.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);
//
//            binding.demodulationLayout.tvTimeAlignmentErrorValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.demodulationLayout.tvTimeAlignmentErrorValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentMax);
//            binding.demodulationLayout.tvTimeAlignmentErrorValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpDemodulationContentSize);


//            end demodulation

//            binding.tvTemp1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.tvTemp1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
//            binding.tvTemp1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
//
//            binding.tvTemp2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//            binding.tvTemp2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
//            binding.tvTemp2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);

                binding.tvMarkerX.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvMarkerX.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMarkerInfoMax);
                binding.tvMarkerX.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMarkerInfoSize);

                binding.tvMarkerY.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvMarkerY.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMarkerInfoMax);
                binding.tvMarkerY.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMarkerInfoSize);
                binding.tvMarkerY.setTextColor(Color.WHITE);

                binding.tvMarkerValLabel.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvMarkerValLabel.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMarkerInfoMax);
                binding.tvMarkerValLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMarkerInfoSize);
                binding.tvMarkerValLabel.setTextColor(Color.WHITE);

                binding.lineChartLayout.tvDBM.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.tvDBM.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.tvDBM.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);

                binding.lineChartLayout.sweptSaInfo.tvRef.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvRef.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.sweptSaInfo.tvRef.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.sweptSaInfo.tvRef.setTextColor(Color.WHITE);

                binding.lineChartLayout.sweptSaInfo.tvRefVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvRefVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.sweptSaInfo.tvRefVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.sweptSaInfo.tvRefVal.setTextColor(Color.WHITE);

                binding.lineChartLayout.sweptSaInfo.tvAtten.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvAtten.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);

                binding.lineChartLayout.sweptSaInfo.tvAtten.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.sweptSaInfo.tvAtten.setTextColor(Color.WHITE);

                binding.lineChartLayout.sweptSaInfo.tvAttenVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvAttenVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.sweptSaInfo.tvAttenVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.sweptSaInfo.tvAttenVal.setTextColor(Color.WHITE);

                binding.lineChartLayout.sweptSaInfo.tvOffset.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvOffset.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.sweptSaInfo.tvOffset.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.sweptSaInfo.tvOffset.setTextColor(Color.WHITE);

                binding.lineChartLayout.sweptSaInfo.tvOffsetVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.sweptSaInfo.tvOffsetVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.lineChartLayout.sweptSaInfo.tvOffsetVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);
                binding.lineChartLayout.sweptSaInfo.tvOffsetVal.setTextColor(Color.WHITE);

                binding.tvRightMenuTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvRightMenuTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoMax);
                binding.tvRightMenuTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpSubInfoSize);

//                binding.tvVswr.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//                binding.tvVswr.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpTopMax);
//                binding.tvVswr.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpTopSize);

//                binding.tvDtf.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//                binding.tvDtf.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpTopMax);
//                binding.tvDtf.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpTopSize);

                binding.tvRecallMessage.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvRecallMessage.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp15);
                binding.tvRecallMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp13);

//                binding.tvCableLoss.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//                binding.tvCableLoss.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpTopMax);
//                binding.tvCableLoss.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpTopSize);

                binding.tvSa.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvSa.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpTopMax);
                binding.tvSa.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpTopSize);

//                binding.tvModAccuracy.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//                binding.tvModAccuracy.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpTopMax);
//                binding.tvModAccuracy.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpTopSize);

                binding.tvRecallOff.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvRecallOff.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvRecallOff.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.tvSmallVswr.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvSmallVswr.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvSmallVswr.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.tvSmallReturnLoss.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvSmallReturnLoss.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvSmallReturnLoss.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.tvMeas.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvMeas.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvMeas.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.tvMode.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvMode.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvMode.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.tvTrace1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTrace1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvTrace1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.tvTrace2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTrace2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvTrace2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.tvTrace3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTrace3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvTrace3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.tvTrace4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTrace4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvTrace4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.tvTraceDetector1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTraceDetector1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp10);
                binding.tvTraceDetector1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp8);

                binding.tvTraceDetector2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTraceDetector2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp10);
                binding.tvTraceDetector2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp8);

                binding.tvTraceDetector3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTraceDetector3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp10);
                binding.tvTraceDetector3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp8);

                binding.tvTraceDetector4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTraceDetector4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp10);
                binding.tvTraceDetector4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp8);

                binding.tvTraceMode1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTraceMode1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp10);
                binding.tvTraceMode1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp8);

                binding.tvTraceMode2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTraceMode2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp10);
                binding.tvTraceMode2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp8);

                binding.tvTraceMode3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTraceMode3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp10);
                binding.tvTraceMode3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp8);

                binding.tvTraceMode4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTraceMode4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp10);
                binding.tvTraceMode4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp8);

                binding.tvMarker1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvMarker1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvMarker1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.tvMarker2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvMarker2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvMarker2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.tvMarker3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvMarker3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvMarker3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.tvMarker4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvMarker4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvMarker4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.tvMarker5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvMarker5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconMax);
                binding.tvMarker5.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpIconSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerName1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerName1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerName1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerName2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerName2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerName2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerName3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerName3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerName3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerName4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerName4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerName4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerName5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerName5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerName5.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerTrace5.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerX1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerX1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerX1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerX2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerX2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerX2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerX3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerX3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerX3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerX4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerX4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerX4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerX5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerX5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerX5.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerY1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerY1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerY1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerY2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerY2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerY2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerY3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerY3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerY3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerY4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerY4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerY4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);

                binding.lineChartLayout.markerTableLayout.tvTableMarkerY5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerY5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableMax);
                binding.lineChartLayout.markerTableLayout.tvTableMarkerY5.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpMkrTableSize);


                binding.tvPactLogo.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvPactLogo.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp10);
                binding.tvPactLogo.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp8);

                binding.tvTimeText.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvTimeText.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp10);
                binding.tvTimeText.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp8);

                binding.tvBack.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvBack.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpTopMax);
                binding.tvBack.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpTopSize);
                binding.tvBack.setTypeface(Typeface.DEFAULT_BOLD);

                binding.tvWifiName.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvWifiName.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp16);
//        binding.tvWifiName.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp8);

//                binding.tvBattery.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//                binding.tvBattery.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp16);
//                binding.tvBattery.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp8);

                binding.tvCableNameTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableNameTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableNameTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvCableName.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableName.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableName.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvPropVelocityTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvPropVelocityTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvPropVelocityTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvPropVelocity.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvPropVelocity.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvPropVelocity.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvFreq1Title.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvFreq1Title.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvFreq1Title.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvCableFreq1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableFreq1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableFreq1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvCableFreq2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableFreq2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableFreq2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvCableFreq3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableFreq3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableFreq3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvLoss1Title.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvLoss1Title.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvLoss1Title.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvCableLoss1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableLoss1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableLoss1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvCableLoss2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableLoss2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableLoss2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvCableLoss3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableLoss3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableLoss3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvFreq2Title.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvFreq2Title.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvFreq2Title.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvCableFreq4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableFreq4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableFreq4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvCableFreq5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableFreq5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableFreq5.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvCableFreq6.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableFreq6.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableFreq6.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvLoss2Title.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvLoss2Title.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvLoss2Title.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvCableLoss4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableLoss4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableLoss4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvCableLoss5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableLoss5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableLoss5.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                binding.tvCableLoss6.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.tvCableLoss6.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);
                binding.tvCableLoss6.setTextSize(TypedValue.COMPLEX_UNIT_PX, dp14);

                /*Aclr sub info text*/

                binding.lineChartLayout.aclrInfo.tvAclrTotalCarrierPower.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrTotalCarrierPower.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrTotalCarrierPower.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrTotalCarrierPower.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrCarrierPowerTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrCarrierPowerTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrCarrierPowerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrCarrierPowerTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrTotalCarrierPowerVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrTotalCarrierPowerVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrTotalCarrierPowerVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrTotalCarrierPowerVal.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrCarrierPower1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrCarrierPower1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrCarrierPower1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);

                binding.lineChartLayout.aclrInfo.tvAclrCarrierPower2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrCarrierPower2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrCarrierPower2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);

                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreqTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreqTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreqTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreqTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrIntegBwTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBwTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBwTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBwTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrLower.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrLower.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrLower.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrLower.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrUpper.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrUpper.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrUpper.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrUpper.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrLowerDbcTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbcTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbcTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbcTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrLowerDbmTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbmTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbmTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbmTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrUpperDbcTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbcTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbcTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbcTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrUpperDbmTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbmTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbmTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbmTitle.setTextColor(Color.WHITE);

                /*
                 * aclr offset freq
                 * */

                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq1.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq2.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq3.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq4.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq5.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrOffsetFreq5.setTextColor(Color.WHITE);

                /* end offset freq
                 * start integ bw
                 * */

                binding.lineChartLayout.aclrInfo.tvAclrIntegBw1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw1.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrIntegBw2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw2.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrIntegBw3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw3.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrIntegBw4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw4.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrIntegBw5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw5.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrIntegBw5.setTextColor(Color.WHITE);

                /*end offset integ
                 * start aclr Lower dbc
                 */

                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc1.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc2.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc3.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc4.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc5.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbc5.setTextColor(Color.WHITE);

                /*end aclr lower dbc
                 * start lower dbm*/

                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm1.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm2.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm3.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm4.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm5.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrLowerDbm5.setTextColor(Color.WHITE);

                /*end aclr lower dbm
                 * start upper dbc*/

                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc1.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc2.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc3.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc4.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc5.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbc5.setTextColor(Color.WHITE);

                /*end upper dbc
                 * start upper dbm */

                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm1.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm1.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm2.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm2.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm3.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm3.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm4.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm4.setTextColor(Color.WHITE);

                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm5.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm5.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrMax);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm5.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpAclrSize);
                binding.lineChartLayout.aclrInfo.tvAclrUpperDbm5.setTextColor(Color.WHITE);

                /*
                 * Sem text
                 * */

                binding.lineChartLayout.semLayout.tvSemCenterFreqTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemCenterFreqTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemCenterFreqTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemCenterFreqTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemCenterFreqVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemCenterFreqVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemCenterFreqVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemCenterFreqVal.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemSpanFreqTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemSpanFreqTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemSpanFreqTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemSpanFreqTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemSpanFreqVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemSpanFreqVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemSpanFreqVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemSpanFreqVal.setTextColor(Color.WHITE);

                //Ch-Pwr
                binding.lineChartLayout.semLayout.tvSemChPwrTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemChPwrTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemChPwrTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemChPwrTitle.setTextColor(Color.WHITE);

                //Ch-Pwr Val
                binding.lineChartLayout.semLayout.tvSemChPwrVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemChPwrVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemChPwrVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemChPwrVal.setTextColor(Color.WHITE);

                //Start
                binding.lineChartLayout.semLayout.tvSemStartFreqTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemStartFreqTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStartFreqTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStartFreqTitle.setTextColor(Color.WHITE);

                //Stop
                binding.lineChartLayout.semLayout.tvSemStopFreqTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemStopFreqTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStopFreqTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStopFreqTitle.setTextColor(Color.WHITE);

                //Integ
                binding.lineChartLayout.semLayout.tvSemMaskRBW.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemMaskRBW.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemMaskRBW.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemMaskRBW.setTextColor(Color.WHITE);

                //Start
                binding.lineChartLayout.semLayout.tvSemStartFreq1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemStartFreq1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStartFreq1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStartFreq1.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemStartFreq2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemStartFreq2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStartFreq2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStartFreq2.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemStartFreq3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemStartFreq3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStartFreq3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStartFreq3.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemStartFreq4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemStartFreq4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStartFreq4.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStartFreq4.setTextColor(Color.WHITE);


                //Stop
                binding.lineChartLayout.semLayout.tvSemStopFreq1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemStopFreq1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStopFreq1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStopFreq1.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemStopFreq2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemStopFreq2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStopFreq2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStopFreq2.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemStopFreq3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemStopFreq3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStopFreq3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStopFreq3.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemStopFreq4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemStopFreq4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStopFreq4.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemStopFreq4.setTextColor(Color.WHITE);


                //Integ Val
                binding.lineChartLayout.semLayout.tvSemMaskRBW1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemMaskRBW1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemMaskRBW1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemMaskRBW1.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemMaskRBW2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemMaskRBW2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemMaskRBW2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemMaskRBW2.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemMaskRBW3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemMaskRBW3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemMaskRBW3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemMaskRBW3.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemMaskRBW4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemMaskRBW4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemMaskRBW4.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemMaskRBW4.setTextColor(Color.WHITE);


                /*
                 * Density Lower
                 * */

                binding.lineChartLayout.semLayout.tvSemDensityTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemDensityTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemDensityTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemDensityTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemDensityVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemDensityVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemDensityVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemDensityVal.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemLowerTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemLowerPkPwrTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerPkPwrTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerPkPwrTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerPkPwrTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemLowerLimitTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerLimitTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerLimitTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerLimitTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemLowerFreqTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerFreqTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerFreqTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerFreqTitle.setTextColor(Color.WHITE);

                //Pk Pwr
                binding.lineChartLayout.semLayout.tvSemLowerPkPwr1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerPkPwr1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerPkPwr1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerPkPwr1.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemLowerPkPwr2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerPkPwr2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerPkPwr2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerPkPwr2.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemLowerPkPwr3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerPkPwr3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerPkPwr3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerPkPwr3.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemLowerPkPwr4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerPkPwr4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerPkPwr4.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerPkPwr4.setTextColor(Color.WHITE);

                //Lower Limit
                binding.lineChartLayout.semLayout.tvSemLowerLimit1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerLimit1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerLimit1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerLimit1.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemLowerLimit2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerLimit2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerLimit2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerLimit2.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemLowerLimit3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerLimit3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerLimit3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerLimit3.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemLowerLimit4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerLimit4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerLimit4.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerLimit4.setTextColor(Color.WHITE);

                //Lower Freq
                binding.lineChartLayout.semLayout.tvSemLowerFreq1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerFreq1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerFreq1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerFreq1.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemLowerFreq2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerFreq2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerFreq2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerFreq2.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemLowerFreq3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerFreq3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerFreq3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerFreq3.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemLowerFreq4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemLowerFreq4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerFreq4.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemLowerFreq4.setTextColor(Color.WHITE);


                /*
                 * Density Upper
                 * */

                binding.lineChartLayout.semLayout.tvSemPassTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemPassTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemPassTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemPassTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemUpperTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemUpperPkPwrTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperPkPwrTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperPkPwrTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperPkPwrTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemUpperLimitTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperLimitTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperLimitTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperLimitTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemUpperFreqTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperFreqTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperFreqTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperFreqTitle.setTextColor(Color.WHITE);

                //Pk Pwr
                binding.lineChartLayout.semLayout.tvSemUpperPkPwr1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperPkPwr1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperPkPwr1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperPkPwr1.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemUpperPkPwr2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperPkPwr2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperPkPwr2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperPkPwr2.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemUpperPkPwr3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperPkPwr3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperPkPwr3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperPkPwr3.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemUpperPkPwr4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperPkPwr4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperPkPwr4.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperPkPwr4.setTextColor(Color.WHITE);


                //Upper Limit
                binding.lineChartLayout.semLayout.tvSemUpperLimit1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperLimit1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperLimit1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperLimit1.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemUpperLimit2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperLimit2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperLimit2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperLimit2.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemUpperLimit3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperLimit3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperLimit3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperLimit3.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemUpperLimit4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperLimit4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperLimit4.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperLimit4.setTextColor(Color.WHITE);


                //Upper Freq
                binding.lineChartLayout.semLayout.tvSemUpperFreq1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperFreq1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperFreq1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperFreq1.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemUpperFreq2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperFreq2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperFreq2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperFreq2.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemUpperFreq3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperFreq3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperFreq3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperFreq3.setTextColor(Color.WHITE);

                binding.lineChartLayout.semLayout.tvSemUpperFreq4.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.semLayout.tvSemUpperFreq4.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperFreq4.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(SEM_FONT_SIZE, mContext));
                binding.lineChartLayout.semLayout.tvSemUpperFreq4.setTextColor(Color.WHITE);

                /*
                 * Transmit On Off Power Font
                 * */

                binding.lineChartLayout.transmitOnOffLayout.tvTransmitInfoBwTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitInfoBwTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitInfoBwTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitInfoBwTitle.setTextColor(Color.WHITE);

                binding.lineChartLayout.transmitOnOffLayout.tvTransmitInfoBwVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitInfoBwVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitInfoBwVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_FONT_SIZE, mContext));

                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOnPowerTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOnPowerTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOnPowerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_FONT_SIZE, mContext));

                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOnPowerVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOnPowerVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOnPowerVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_FONT_SIZE, mContext));

                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOffPowerTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOffPowerTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOffPowerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_FONT_SIZE, mContext));

                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOffPowerVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOffPowerVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOffPowerVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_FONT_SIZE, mContext));

                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOffPowerPass.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOffPowerPass.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitOffPowerPass.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_FONT_SIZE, mContext));

                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampUpTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampUpTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampUpTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_FONT_SIZE, mContext));

                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampUpVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampUpVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampUpVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_FONT_SIZE, mContext));

                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampUpPass.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampUpPass.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampUpPass.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_FONT_SIZE, mContext));

                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampDownTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampDownTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampDownTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_FONT_SIZE, mContext));

                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampDownVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampDownVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampDownVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_FONT_SIZE, mContext));

                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampDownPass.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampDownPass.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.transmitOnOffLayout.tvTransmitRampDownPass.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(TRANSMIT_FONT_SIZE, mContext));


                //gate
//                binding.lineChartLayout.gateLayout.tvGateDelay.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
//                binding.lineChartLayout.gateLayout.tvGateDelay.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(GATE_MAX_FONT_SIZE, mContext));
//                binding.lineChartLayout.gateLayout.tvGateDelay.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(GATE_FONT_SIZE, mContext));
//
                binding.lineChartLayout.gateLayout.tvDownlink.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.gateLayout.tvDownlink.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(GATE_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.gateLayout.tvDownlink.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(GATE_FONT_SIZE, mContext));

                binding.lineChartLayout.gateLayout.tvUplink.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.gateLayout.tvUplink.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(GATE_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.gateLayout.tvUplink.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(GATE_FONT_SIZE, mContext));

                binding.lineChartLayout.gateLayout.tvGateXOffset.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.gateLayout.tvGateXOffset.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(GATE_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.gateLayout.tvGateXOffset.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(GATE_FONT_SIZE, mContext));

                binding.lineChartLayout.gateLayout.tvTimeSpanVal.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lineChartLayout.gateLayout.tvTimeSpanVal.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(GATE_MAX_FONT_SIZE, mContext));
                binding.lineChartLayout.gateLayout.tvTimeSpanVal.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(GATE_FONT_SIZE, mContext));

                //lte

                binding.lteFddLayout.constellationTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.constellationTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.constellationTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_FONT_SIZE, mContext));

                binding.lteFddLayout.timeInformationTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.timeInformationTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.timeInformationTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_FONT_SIZE, mContext));

                binding.lteFddLayout.cellInformationTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.cellInformationTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.cellInformationTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_FONT_SIZE, mContext));

                binding.lteFddLayout.powerInformationTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.powerInformationTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.powerInformationTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_FONT_SIZE, mContext));

                binding.lteFddLayout.timeInformationTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.timeInformationTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.timeInformationTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_FONT_SIZE, mContext));

                binding.lteFddLayout.signalQualityTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.signalQualityTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.signalQualityTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_FONT_SIZE, mContext));

                binding.lteFddLayout.antennaInformationTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.antennaInformationTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.antennaInformationTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TITLE_FONT_SIZE, mContext));


                binding.lteFddLayout.timingOffsetTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.timingOffsetTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.timingOffsetTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.timingOffsetValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.timingOffsetValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.timingOffsetValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.timeAlignmentErrorTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.timeAlignmentErrorTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.timeAlignmentErrorTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.timeAlignmentErrorValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.timeAlignmentErrorValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.timeAlignmentErrorValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.thrdTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.thrdTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.thrdTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.thrdValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.thrdValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.thrdValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.timeAlignmentErrorResult.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.timeAlignmentErrorResult.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.timeAlignmentErrorResult.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));


                //Antenna Information
                binding.lteFddLayout.tvAnt0.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvAnt0.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvAnt0.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvAnt1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvAnt1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvAnt1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvAnt2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvAnt2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvAnt2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvAnt3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvAnt3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvAnt3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                //Cell Information
                binding.lteFddLayout.tvChBandWidthTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvChBandWidthTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvChBandWidthTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvChBandWidthValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvChBandWidthValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvChBandWidthValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));


                binding.lteFddLayout.tvLtePhysicalCellIdTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvLtePhysicalCellIdTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvLtePhysicalCellIdTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvLtePhysicalCellIdValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvLtePhysicalCellIdValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvLtePhysicalCellIdValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvGroupIdTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvGroupIdTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvGroupIdTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvGroupIdValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvGroupIdValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvGroupIdValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));


                binding.lteFddLayout.tvSectorTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvSectorTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvSectorTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvSectorValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvSectorValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvSectorValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));


                binding.lteFddLayout.tvRsrpRsTitle0.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvRsrpRsTitle0.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvRsrpRsTitle0.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvRsrpRsValue0.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvRsrpRsValue0.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvRsrpRsValue0.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));


                binding.lteFddLayout.tvRsrpRsTitle1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvRsrpRsTitle1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvRsrpRsTitle1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvRsrpRsValue1.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvRsrpRsValue1.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvRsrpRsValue1.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));


                binding.lteFddLayout.tvRsrpRsTitle2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvRsrpRsTitle2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvRsrpRsTitle2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvRsrpRsValue2.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvRsrpRsValue2.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvRsrpRsValue2.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));


                binding.lteFddLayout.tvRsrpRsTitle3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvRsrpRsTitle3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvRsrpRsTitle3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvRsrpRsValue3.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvRsrpRsValue3.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvRsrpRsValue3.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));


                binding.lteFddLayout.tvRsrqTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvRsrqTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvRsrqTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvRsrqValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvRsrqValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvRsrqValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));


                binding.lteFddLayout.tvExpectedTxMaxPowerTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvExpectedTxMaxPowerTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvExpectedTxMaxPowerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvExpectedTxMaxPowerValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvExpectedTxMaxPowerValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvExpectedTxMaxPowerValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));


                //Signal Quality

                binding.lteFddLayout.tvFrequencyOffsetTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvFrequencyOffsetTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvFrequencyOffsetTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvThresholdTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvThresholdTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvThresholdTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvThresholdValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvThresholdValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvThresholdValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvPpmTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPpmTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPpmTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvPpmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPpmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPpmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvHzTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvHzTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvHzTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvHzValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvHzValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvHzValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvFreqOffsetResult.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvFreqOffsetResult.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvFreqOffsetResult.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvChannelTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvChannelTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvChannelTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvEVMTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvEVMTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvEVMTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvPWRTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPWRTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPWRTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvModTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvModTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvModTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));

                binding.lteFddLayout.tvRBTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvRBTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvRBTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_CONTENT_FONT_SIZE, mContext));


                //lte pss
                binding.lteFddLayout.tvPssTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPssTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPssTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvPssEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPssEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPssEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvPssPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPssPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPssPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvPssModValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPssModValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPssModValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvPssRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPssRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPssRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));


                //lte sss
                binding.lteFddLayout.tvSssTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvSssTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvSssTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvSssEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvSssEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvSssEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvSssPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvSssPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvSssPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvSssModValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvSssModValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvSssModValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvSssRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvSssRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvSssRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                //lte pbch
                binding.lteFddLayout.tvPbchTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPbchTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPbchTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvPbchEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPbchEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPbchEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvPbchPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPbchPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPbchPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvPbchModValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPbchModValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPbchModValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvPbchRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPbchRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPbchRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));


                //lte pcfich
                binding.lteFddLayout.tvPcfichTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPcfichTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPcfichTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPcfichEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPcfichEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPcfichEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPcfichPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPcfichPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPcfichPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPcfichModValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPcfichModValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPcfichModValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPcfichRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPcfichRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPcfichRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));


                //lte phich
                binding.lteFddLayout.tvPhichTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPhichTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPhichTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPhichEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPhichEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPhichEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPhichPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPhichPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPhichPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPhichModValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPhichModValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPhichModValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPhichRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPhichRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPhichRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));


                //lte pdcch
                binding.lteFddLayout.tvPdcchTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdcchTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdcchTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdcchEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdcchEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdcchEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdcchPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdcchPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdcchPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdcchModValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdcchModValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdcchModValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdcchRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdcchRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdcchRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));


                //lte crs
                binding.lteFddLayout.tvCrsTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvCrsTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvCrsTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvCrsEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvCrsEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvCrsEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvCrsPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvCrsPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvCrsPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvCrsModValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvCrsModValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvCrsModValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvCrsRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvCrsRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvCrsRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.lteFddLayout.tvCrsPassFail.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvCrsPassFail.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvCrsPassFail.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));


                //lte pdsch
                binding.lteFddLayout.tvPdschQpskTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdschQpskTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdschQpskTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdschQpskEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdschQpskEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdschQpskEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdschQpskPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdschQpskPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdschQpskPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdschQpskModValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdschQpskModValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdschQpskModValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdschQpskRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdschQpskRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdschQpskRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                //lte pdsch 16qam
                binding.lteFddLayout.tvPdsch16QamTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch16QamTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch16QamTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch16QamEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch16QamEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch16QamEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch16QamPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch16QamPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch16QamPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch16QamModValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch16QamModValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch16QamModValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch16QamRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch16QamRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch16QamRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                //lte pdsch 64
                binding.lteFddLayout.tvPdsch64QamTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch64QamTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch64QamTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch64QamEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch64QamEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch64QamEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch64QamPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch64QamPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch64QamPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch64QamModValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch64QamModValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch64QamModValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch64QamRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch64QamRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch64QamRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                //lte pdsch 256
                binding.lteFddLayout.tvPdsch256QamTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch256QamTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch256QamTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch256QamEvmValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch256QamEvmValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch256QamEvmValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch256QamPwrValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch256QamPwrValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch256QamPwrValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch256QamModValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch256QamModValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch256QamModValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch256QamRbValue.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, dp1);
                binding.lteFddLayout.tvPdsch256QamRbValue.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_MAX_FONT_SIZE, mContext));
                binding.lteFddLayout.tvPdsch256QamRbValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(LTE_TABLE_FONT_SIZE, mContext));

                binding.tvOvf.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_MAX_FONT_SIZE, mContext));
                binding.tvOvf.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_FONT_SIZE, mContext));

                binding.tvNrOvf.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_MAX_FONT_SIZE, mContext));
                binding.tvNrOvf.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_FONT_SIZE, mContext));

                binding.tvExt.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_MAX_FONT_SIZE, mContext));
                binding.tvExt.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_FONT_SIZE, mContext));

                binding.tvNrExt.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_MAX_FONT_SIZE, mContext));
                binding.tvNrExt.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_FONT_SIZE, mContext));

                binding.tvGate.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_MAX_FONT_SIZE, mContext));
                binding.tvGate.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_FONT_SIZE, mContext));

                binding.tvNrSync.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_MAX_FONT_SIZE, mContext));
                binding.tvNrSync.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_FONT_SIZE, mContext));

                binding.tvPre.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_MAX_FONT_SIZE, mContext));
                binding.tvPre.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_FONT_SIZE, mContext));

                binding.tvNrPre.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_MAX_FONT_SIZE, mContext));
                binding.tvNrPre.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_FONT_SIZE, mContext));

                binding.tvNrTrig.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_MAX_FONT_SIZE, mContext));
                binding.tvNrTrig.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(FW_STATUS_FONT_SIZE, mContext));


                AutofitTextView[] tvIndex = {
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex0,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex1,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex2,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex3,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex4,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex5,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex6,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex7,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex8,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex9,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex10,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex11,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex12,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex13,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex14,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex15,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex16,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex17,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex18,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvIndex19,
                };

                AutofitTextView[] tvPower = {
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower0,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower1,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower2,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower3,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower4,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower5,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower6,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower7,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower8,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower9,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower10,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower11,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower12,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower13,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower14,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower15,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower16,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower17,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower18,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvPower19,
                };

                AutofitTextView[] tvTime = {
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime0,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime1,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime2,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime3,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime4,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime5,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime6,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime7,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime8,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime9,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime10,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime11,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime12,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime13,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime14,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime15,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime16,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime17,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime18,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvTime19,
                };

                AutofitTextView[] tvType = {
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType0,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType1,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType2,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType3,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType4,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType5,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType6,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType7,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType8,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType9,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType10,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType11,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType12,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType13,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType14,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType15,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType16,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType17,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType18,
                        MainActivity.getBinding().lineChartLayout.interferenceInfo.tvType19,
                };

                for (int i = 0; i < 20; i++) {

                    tvIndex[i].setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(INTERFERENCE_HUNTING_MAX_SIZE, mContext));
                    tvIndex[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(INTERFERENCE_HUNTING_SIZE, mContext));
                    tvIndex[i].setSingleLine(true);

                    tvTime[i].setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(INTERFERENCE_HUNTING_MAX_SIZE, mContext));
                    tvTime[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(INTERFERENCE_HUNTING_SIZE, mContext));
                    tvTime[i].setSingleLine(true);

                    tvType[i].setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(INTERFERENCE_HUNTING_MAX_SIZE, mContext));
                    tvType[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(INTERFERENCE_HUNTING_SIZE, mContext));
                    tvType[i].setSingleLine(true);

                    tvPower[i].setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(INTERFERENCE_HUNTING_MAX_SIZE, mContext));
                    tvPower[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(INTERFERENCE_HUNTING_SIZE, mContext));
                    tvPower[i].setSingleLine(true);
                }

            });

        }).start();
    }

}

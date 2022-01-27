package com.dabinsystems.pact_app.Dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dabinsystems.pact_app.Activity.InitActivity;
import com.dabinsystems.pact_app.Activity.MainActivity;
import com.dabinsystems.pact_app.Data.ModAccuracy.LteFdd.LteFddData;
import com.dabinsystems.pact_app.Data.ModAccuracy.LteFdd.LteFddProfileData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrProfileData;
import com.dabinsystems.pact_app.Data.ModAccuracy.NR5G.NrSSBInfoData;
import com.dabinsystems.pact_app.Data.SA.BWData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.FailSourceData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SemMeasTypeData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.TraceEnumData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData;
import com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemRefChannelData;
import com.dabinsystems.pact_app.Data.SA.SAFrequencyData;
import com.dabinsystems.pact_app.Data.SA.SaConfigData;
import com.dabinsystems.pact_app.Data.SA.SaGateData;
import com.dabinsystems.pact_app.Data.SA.SaSweepTimeData;
import com.dabinsystems.pact_app.Function.MeasureType;
import com.dabinsystems.pact_app.Handler.DataHandler;
import com.dabinsystems.pact_app.Handler.FunctionHandler;
import com.dabinsystems.pact_app.Function.MeasureMode;
import com.dabinsystems.pact_app.Handler.SaDataHandler;
import com.dabinsystems.pact_app.Handler.ViewHandler;
import com.dabinsystems.pact_app.Handler.VswrDataHandler;
import com.dabinsystems.pact_app.R;
import com.dabinsystems.pact_app.View.SA.SaView;
import com.dabinsystems.pact_app.View.SA.Sweep.GateView;
import com.dabinsystems.pact_app.databinding.PresetDialogBinding;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitTextView;

import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.KHZ1;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.KHZ10;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.KHZ100;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.KHZ30;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.KHZ300;
import static com.dabinsystems.pact_app.Data.SA.BWData.BAND_WIDTH.MHZ1;
import static com.dabinsystems.pact_app.Data.SA.MeasSetupData.ENUM.SideData.SIDE_OPTION.BOTH;
import static com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData.MASK_ON_OFF.OFF;
import static com.dabinsystems.pact_app.Data.SA.MeasSetupData.SEM.SemEditMaskData.MASK_ON_OFF.ON;
import static com.dabinsystems.pact_app.View.DynamicView.convertDpToPixel;

public class PresetDialog extends CustomBaseDialog implements View.OnClickListener {

    final String PRESET_INI = "preset_list_3.ini";

    public enum UL_DL_STATUS {
        SINGLE,
        DL,
        UL
    }

    enum EnumStandard {
        NONE,
        Clock,
        IF,
        WCDMA,
        LTE,
        NR5G,
    }

    class PresetItem {
        String FreqStartEnd;
        String Freq;
        String Standard;
        String Profile;
        UL_DL_STATUS eDlStatus = UL_DL_STATUS.SINGLE;

        void set(String key, int idx, Ini.Section section, UL_DL_STATUS eDlStatus) {
            FreqStartEnd = section.get(key + idx);
            Freq = section.get(key + idx + "Freq");
            Standard = section.get(key + idx + "Standard");

            String p = section.get("profile");
            if (p != null) {
                String[] tokens = p.split(", ");
                if (tokens.length > idx)
                    Profile = tokens[idx];
            }

            this.eDlStatus = eDlStatus;
        }

        double[] getFreqStartEnd() {
            String[] tokens = FreqStartEnd.split(" ");
            double startFreq = Double.parseDouble(tokens[0]);
            double stopFreq = Double.parseDouble(tokens[1]);
            return new double[] { startFreq, stopFreq };
        }

        double getFreq() {
            return Double.parseDouble(Freq);
        }

        EnumStandard getStandard() {
            EnumStandard es = EnumStandard.NONE;

            String[] tokens = Standard.split(" ");
            switch (tokens[0]) {
                case "Clock":
                    es = EnumStandard.Clock;
                    break;
                case "IF":
                    es = EnumStandard.IF;
                    break;
                case "WCDMA":
                    es = EnumStandard.WCDMA;
                    break;
                case "LTE":
                    es = EnumStandard.LTE;
                    break;
                case "5GNR":
                    es = EnumStandard.NR5G;
                    break;
            }

            return es;
        }

        String getStandardMHz() {
            String[] tokens = Standard.split(" ");
            if (tokens.length > 1) {
                return tokens[1];
            }
            return "";
        }

        int getStandardMHzToInt() {
            int rtn = 0;
            String[] tokens = Standard.split(" ");
            if (tokens.length > 1) {
                rtn = Integer.parseInt(tokens[1]);
            }
            return rtn;
        }

        UL_DL_STATUS getDlStatus() {
            return eDlStatus;
        }
    }

    private PresetDialogBinding mPresetBinding;
    private SharedPreferences mAppInfoSp;
    private int mLoadPreset;
    private Context mContext;
    private Ini ini;
    private final String SP_PRESET_ID = "loadPreset";

    private final String mDirectoryName = "PACT/PresetList";
    private final File mPath = Environment.getExternalStorageDirectory();

    public PresetDialog(Context context) {
        super(context, R.style.AppFullScreenTheme);

        mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresetBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.preset_dialog, null, false);
        setContentView(mPresetBinding.getRoot());
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        mAppInfoSp = mContext.getSharedPreferences("AppInfo", Context.MODE_PRIVATE);
        mLoadPreset = mAppInfoSp.getInt(SP_PRESET_ID, 0);
        viewSetting();
        addEvents();

    }

    public void addEvents() {

        addPresetList();

        mPresetBinding.btnCancel.setOnClickListener(v -> {
            dismiss();
        });
    }

    public void viewSetting() {

//        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
//        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
//
//        InitActivity.logMsg("Preset Dialog", mode.getString() + " " + type.getFullName());
//
//        if(type != MeasureType.MEASURE_TYPE.SWEPT_SA) {
//            new Handler(Looper.getMainLooper()).post(()->{
//
//                mPresetBinding.titleClock.setVisibility(View.GONE);
//                mPresetBinding.btnClock10MHz.setVisibility(View.GONE);
//
//                InitActivity.logMsg("PresetDialog", "GONE");
//
//            });
//
//        } else if(mode == MeasureMode.MEASURE_MODE.SA) {
//            new Handler(Looper.getMainLooper()).post(()->{
//
//                mPresetBinding.titleClock.setVisibility(View.VISIBLE);
//                mPresetBinding.btnClock10MHz.setVisibility(View.VISIBLE);
//
//                InitActivity.logMsg("PresetDialog", "VISIBLE");
//
//            });
//
//        }

        mPresetBinding.tvPresetTitle.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, (int) convertDpToPixel(1, mContext));
        mPresetBinding.tvPresetTitle.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mContext));
        mPresetBinding.tvPresetTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(20, mContext));

//        mPresetBinding.titleClock.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(13, mContext));
//        mPresetBinding.btnClock10MHz.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(12, mContext));
        mPresetBinding.btnCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(10, mContext));

    }

    private void addPresetList() {

        InitActivity.logMsg("loadingFile", loadIniFileInAsset() + "");

        if (!loadIniFileInLocal()) {

            if (loadIniFileInAsset()) {
                copyPresetListFile();
                loadPresetList();

            }

            InitActivity.logMsg("addPresetList", "loadInFileInLocal : " + false);

        } else {

            loadPresetList();
            InitActivity.logMsg("addPresetList", "loadInFileInLocal : " + true);
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    private void loadPresetList() {

        try {
            //tag:prestAlpha 210616
            makeTextView2();
            //tag:prestAlpha 210616

            //original code
//            Ini.Section section = ini.get("Num of Preset");
//            int numOfPreset = Integer.parseInt(section.get("numOfPreset"));
//            int numOfValidPreset = numOfPreset;
//
//            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
//
//            for (int i = 0; i < numOfPreset; i++) {
//
//                section = ini.get(i + "");
//                String name = section.get("name");
//
//                if (type != MeasureType.MEASURE_TYPE.SWEPT_SA && (name == null || name.contains("Clock") || name.contains("IF")))
//                    numOfValidPreset--;
//            }
//            for (int i = 0; i < numOfPreset; i++) {
//
//                section = ini.get(i + "");
//                String name = section.get("name");
//
//                if(name == null) continue;
//                if(type != MeasureType.MEASURE_TYPE.SWEPT_SA && (name.contains("Clock") || name.contains("IF"))) continue;
//
//                if (i != 0)
//                    mPresetBinding.linPresetParent.addView(getDivisionLine());
//
//                LinearLayout linPreset = new LinearLayout(mContext);
//                LinearLayout.LayoutParams presetParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f / (float) numOfValidPreset - 0.06f);
//                linPreset.setLayoutParams(presetParams);
//                linPreset.setOrientation(LinearLayout.HORIZONTAL);
//                linPreset.setWeightSum(1);
//
//                AutofitTextView tvName = new AutofitTextView(mContext);
//                LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.18f);
//                tvName.setLayoutParams(nameParams);
//                tvName.setGravity(Gravity.CENTER);
//                tvName.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, 1);
//                tvName.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(16, mContext));
//                tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(16, mContext));
//                tvName.setTypeface(Typeface.DEFAULT_BOLD);
//                tvName.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
//                tvName.setBackgroundResource(R.drawable.preset_unselect_button_background);
//                tvName.setText(name);
//
//                linPreset.addView(tvName);
//                linPreset.addView(getDivisionButtonLine(0.036f));
//
//
//                int numOfList = Integer.parseInt(section.get("numOfList"));
//
//
//                //tag:prestAlpha 210616 presetDialog button 만드는 영역 original source
//                for (int k = 0; k < numOfList; k++) {
//
////                        InitActivity.logMsg(k + "", section.get("freq" + k) + " " + section.get("loss" + k));
//
//                    LinearLayout menuParent = getLinParent();
//
//                    String dlStr = section.get("dl" + k);
//                    if (dlStr != null) {
//                        String[] dlSplitStr = dlStr.split(" ");
//                        double startFreq = Double.parseDouble(dlSplitStr[0]);
//                        double stopFreq = Double.parseDouble(dlSplitStr[1]);
//                        double avgFreq = (startFreq + stopFreq) / 2d;
//
//                        AutofitTextView textView = getTextView(10, 0.47f);
//                        textView.setBackground(mContext.getResources().getDrawable(R.drawable.preset_selector));
//                        String title = "DL " + avgFreq + " MHz";
//                        textView.setText(title);
//                        textView.setOnTouchListener(new View.OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View v, MotionEvent event) {
//                                InitActivity.logMsg("PresetDialog", "setUpEvents :  DL -> " + title + " " + dlStr);
//                                setUpEvents(event, startFreq, stopFreq, textView, UL_DL_STATUS.DL);
//                                return true;
//
//                            }
//                        });
//
//                        menuParent.addView(textView);
//                        menuParent.addView(getDivisionViewLine(0.06f));
//
//                    }
//
//                    String ulStr = section.get("ul" + k);
//                    if (ulStr != null) {
//                        String[] ulSplitStr = ulStr.split(" ");
//                        double startFreq = Double.parseDouble(ulSplitStr[0]);
//                        double stopFreq = Double.parseDouble(ulSplitStr[1]);
//                        double avgFreq = (startFreq + stopFreq) / 2d;
//
//                        AutofitTextView textView = getTextView(10, 0.47f);
//                        textView.setBackground(mContext.getResources().getDrawable(R.drawable.preset_selector));
//                        String title = "UL " + avgFreq + " MHz";
//                        textView.setText(title);
//
//                        textView.setOnTouchListener(new View.OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View v, MotionEvent event) {
//
//                                InitActivity.logMsg("PresetDialog", "setUpEvents :  UL - > " + title + " " +  ulStr);
//                                setUpEvents(event, startFreq, stopFreq, textView, UL_DL_STATUS.UL);
//                                return true;
//                            }
//                        });
//
//                        menuParent.addView(textView);
//
//                    }
//
//                    String singleStr = section.get("single" + k);
//                    if (singleStr != null) {
//                        String[] singleSplitStr = singleStr.split(" ");
//                        float startFreq = Float.parseFloat(singleSplitStr[0]);
//                        float stopFreq = Float.parseFloat(singleSplitStr[1]);
//                        float avgFreq = (startFreq + stopFreq) / 2f;
//
//                        AutofitTextView textView = getTextView(12, 1f);
//                        textView.setBackground(mContext.getResources().getDrawable(R.drawable.preset_selector));
//                        textView.setText(avgFreq + " MHz");
//
//                        textView.setOnTouchListener(new View.OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View v, MotionEvent event) {
//
//                                setUpEvents(event, startFreq, stopFreq, textView, UL_DL_STATUS.SINGLE);
//
//                                return true;
//                            }
//                        });
//
//                        menuParent.addView(textView);
//
//                    }
//
//                    linPreset.addView(menuParent);
//                    linPreset.addView(getDivisionButtonLine(0.018f));
//
//                }
//
//                mPresetBinding.linPresetParent.addView(linPreset);
//
//            }
//            //original code

            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = mAppInfoSp.edit();
            editor.putInt(SP_PRESET_ID, 1);
            editor.apply();
            mLoadPreset = mAppInfoSp.getInt(SP_PRESET_ID, 0);
            InitActivity.logMsg("PresetDialog", "Load Preset : " + mLoadPreset);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        PresetItem item = (PresetItem) v.getTag();
        if (item == null)
            return;

        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();

        if (mode == MeasureMode.MEASURE_MODE.SA) {
            String strFreq = item.Freq;
            double dFreq = item.getFreq();

            SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
            SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(dFreq);
            SaDataHandler.getInstance().getConfigData().getFrequencyData().checkFreqRange();

            switch (item.getStandard()) {
                case Clock: {
                    DataHandler.getInstance().getStandardLoadData().loadClockData(dFreq);
                    ViewHandler.getInstance().getSAView().update();
                    break;
                }
                case IF: {
                    DataHandler.getInstance().getStandardLoadData().loadIFData(dFreq);
                    ViewHandler.getInstance().getSAView().update();
                    break;
                }
                case WCDMA: {
                    DataHandler.getInstance().getStandardLoadData().loadWcdmaData();
                    break;
                }
                case LTE: {
                    switch (item.getStandardMHz()) {
                        case "1.4":
                            DataHandler.getInstance().getStandardLoadData().load1d4MHzDataForLte();
                            break;
                        case "3":
                            DataHandler.getInstance().getStandardLoadData().load3MHzDataForLte();
                            break;
                        case "5":
                            DataHandler.getInstance().getStandardLoadData().load5MHzDataForLte();
                            break;
                        case "10":
                            DataHandler.getInstance().getStandardLoadData().load10MHzDataForLte();
                            break;
                        case "15":
                            DataHandler.getInstance().getStandardLoadData().load15MHzDataForLte();
                            break;
                        case "20":
                            DataHandler.getInstance().getStandardLoadData().load20MHzDataForLte();
                            break;
                    }
                    break;
                }
                case NR5G: {
                    switch (item.getStandardMHz()) {
                        case "5":
                            DataHandler.getInstance().getStandardLoadData().load5MHzDataFor5G(item.getDlStatus());
                            break;
                        case "10":
                            DataHandler.getInstance().getStandardLoadData().load10MHzDataFor5G(item.getDlStatus());
                            break;
                        case "15":
                            DataHandler.getInstance().getStandardLoadData().load15MHzDataFor5G(item.getDlStatus());
                            break;
                        case "20":
                            DataHandler.getInstance().getStandardLoadData().load20MHzDataFor5G(item.getDlStatus());
                            break;
                        case "25":
                            DataHandler.getInstance().getStandardLoadData().load25MHzDataFor5G(item.getDlStatus());
                            break;
                        case "30":
                            DataHandler.getInstance().getStandardLoadData().load30MHzDataFor5G(item.getDlStatus());
                            break;
                        case "40":
                            DataHandler.getInstance().getStandardLoadData().load40MHzDataFor5G(item.getDlStatus());
                            break;
                        case "50":
                            DataHandler.getInstance().getStandardLoadData().load50MHzDataFor5G(item.getDlStatus());
                            break;
                        case "60":
                            DataHandler.getInstance().getStandardLoadData().load60MHzDataFor5G(item.getDlStatus());
                            break;
                        case "70":
                            DataHandler.getInstance().getStandardLoadData().load70MHzDataFor5G(item.getDlStatus());
                            break;
                        case "80":
                            DataHandler.getInstance().getStandardLoadData().load80MHzDataFor5G(item.getDlStatus());
                            break;
                        case "90":
                            DataHandler.getInstance().getStandardLoadData().load90MHzDataFor5G(item.getDlStatus());
                            break;
                        case "100":
                            DataHandler.getInstance().getStandardLoadData().load100MHzDataFor5G(item.getDlStatus());
                            break;
                    }
                    break;
                }
            }
        }
        else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {
            String profile = String.valueOf(item.Profile);

            if (type == MeasureType.MEASURE_TYPE.LTE_FDD) {
                LteFddData data = DataHandler.getInstance().getLteFddData();
                data.setCenterFreq(item.getFreq());

                try {
                    LteFddProfileData.PROFILE profileData = LteFddProfileData.PROFILE.valueOf(profile.toUpperCase());
                    data.getProfileData().setProfile(profileData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (type == MeasureType.MEASURE_TYPE.NR_5G) {
                NrData nrData = DataHandler.getInstance().getNrData();
                nrData.getFreqData().setCenterFreq(item.getFreq());

                try {
                    NrProfileData.PROFILE profileData = NrProfileData.PROFILE.valueOf(profile.toUpperCase());
                    nrData.getProfileData().setProfile(profileData);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                DataHandler.getInstance().getNrData().getSsbInfoData().setConfigMode(NrSSBInfoData.CONFIG_MODE.AUTO);
                ViewHandler.getInstance().getNrMeasSetupView().update();
            }
        }
        else { // if (mode == MeasureMode.MEASURE_MODE.CL) {
            double[] freqStartEnd = item.getFreqStartEnd();

            VswrDataHandler.getInstance().getConfigData().getFrequencyData().setFreq(freqStartEnd[0], freqStartEnd[1]);
            VswrDataHandler.getInstance().getConfigData().getFrequencyData().checkFreqRange();
            ViewHandler.getInstance().getFreqDistView().update();
        }

        ViewHandler.getInstance().getContent().update();

        FunctionHandler.getInstance().getDataConnector().sendCommand(
                DataHandler.getInstance().getConfigCmd()
        );

        dismiss();
    }

    private void makeTextView2() {
        Ini.Section section = ini.get("Num of Preset");
        int numOfPreset = Integer.parseInt(section.get("numOfPreset"));
        boolean isModeVisible;
        boolean isTypeVisible;

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

        Log.e("Current Measure", "Mode : " + mode + " Type : " + type);

        String[] modeVisibleStr;
        String[] typeVisibleStr;

        for (int i = 0; i < numOfPreset; i++) {
            section = ini.get(i + "");
            String name = section.get("name");
            if (name == null) continue;

            // 표출 여부 확인
            modeVisibleStr = section.get("modeVisible").split(", ");
            typeVisibleStr = section.get("typeVisible").split(", ");
            MeasureType.MEASURE_TYPE typeE;
            MeasureMode.MEASURE_MODE modeE;
            isModeVisible = false;
            isTypeVisible = false;

            for (int j = 0; j < modeVisibleStr.length; j++) {
                modeE = MeasureMode.MEASURE_MODE.valueOf(modeVisibleStr[j].toUpperCase());
                Log.e("Current modeE", "ModeE : " + modeE);
                if (modeE.equals(mode)) {
                    isModeVisible = true;
                    break;
                }
            }
            for (int j = 0; j < typeVisibleStr.length; j++) {
                typeE = MeasureType.MEASURE_TYPE.valueOf(typeVisibleStr[j].toUpperCase());
                Log.e("Current typeE", "typeE : " + typeE);
                if (typeE.equals(type)) {
                    isTypeVisible = true;
                    break;
                }
            }

            if (!(isModeVisible & isTypeVisible)) {
                continue;
            }


            if (i != 0) mPresetBinding.linPresetParent.addView(getDivisionLine());

            LinearLayout linPreset = new LinearLayout(mContext);
            LinearLayout.LayoutParams presetParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);// 1f / (float) numOfValidPreset - 0.06f);
            linPreset.setLayoutParams(presetParams);
            linPreset.setOrientation(LinearLayout.HORIZONTAL);
            linPreset.setWeightSum(6.16f);

            AutofitTextView tvName = new AutofitTextView(mContext);
            LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
            tvName.setLayoutParams(nameParams);
            tvName.setGravity(Gravity.CENTER);
            tvName.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, 1);
            tvName.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(16, mContext));
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(16, mContext));
            tvName.setTypeface(Typeface.DEFAULT_BOLD);
            tvName.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            tvName.setBackgroundResource(R.drawable.preset_unselect_button_background);
            tvName.setText(name);

            linPreset.addView(tvName);

            linPreset.addView(getDivisionButtonLine(0.06f));

            int numOfList = Integer.parseInt(section.get("numOfList"));

            String strWidths = section.get("width");
            String[] aryWidths = null;
            if (strWidths != null) {
                aryWidths = strWidths.split(" ");
            }

            //test start
            for (int k = 0; k < numOfList; k++) {

                linPreset.addView(getDivisionButtonLine(0.02f));

                float fW = 1f;
                if (aryWidths != null && aryWidths.length > k)
                    fW = Float.parseFloat(aryWidths[k]);

                LinearLayout menuParent = getLinParent(fW);

                String dlStr = section.get("dl" + k);
                String ulStr = section.get("ul" + k);
                String singleStr = section.get("single" + k);

                if (dlStr != null) {
                    String key = "dl";
                    PresetItem item = new PresetItem();
                    item.set(key, k, section, UL_DL_STATUS.DL);

                    String title = section.get(key + k + "Title");

                    AutofitTextView textView = getTextView(10, 0.47f);
                    //textView.setBackground(mContext.getResources().getDrawable(R.drawable.preset_selector));
                    textView.setText(title);
                    textView.setTag(item);
                    textView.setOnClickListener(this);

                    menuParent.addView(textView);

                    menuParent.addView(getDivisionViewLine(0.06f));
                }

                if (ulStr != null) {
                    String key = "ul";
                    PresetItem item = new PresetItem();
                    item.set(key, k, section, UL_DL_STATUS.UL);

                    String title = section.get(key + k + "Title");

                    AutofitTextView textView = getTextView(10, 0.47f);
                    //textView.setBackground(mContext.getResources().getDrawable(R.drawable.preset_selector));
                    textView.setText(title);
                    textView.setTag(item);
                    textView.setOnClickListener(this);
                    menuParent.addView(textView);
                }

                if (singleStr != null) {
                    String key = "single";
                    PresetItem item = new PresetItem();
                    item.set(key, k, section, UL_DL_STATUS.SINGLE);

                    String title = section.get(key + k + "Title");

                    AutofitTextView textView = getTextView(12, 1f);
                    //textView.setBackground(mContext.getResources().getDrawable(R.drawable.preset_selector));
                    textView.setText(title);
                    textView.setTag(item);
                    textView.setOnClickListener(this);
                    menuParent.addView(textView);
                }

                linPreset.addView(menuParent);
            }

            mPresetBinding.linPresetParent.addView(linPreset);
        }
    }

    //tag:prestAlpha 210617 start
    private int makeTextView() {
        Ini.Section section = ini.get("Num of Preset");
        int numOfPreset = Integer.parseInt(section.get("numOfPreset"));
        int numOfValidPreset = numOfPreset;
        int textViewId = 0;
        boolean isModeVisible;
        boolean isTypeVisible;

        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();

        Log.e("Current Measure", "Mode : " + mode + " Type : " + type);

        String[] modeVisibleStr;
        String[] typeVisibleStr;


        for (int i = 0; i < numOfPreset; i++) {

            section = ini.get(i + "");
            String name = section.get("name");

            modeVisibleStr = section.get("modeVisible").split(", ");
            typeVisibleStr = section.get("typeVisible").split(", ");

            MeasureType.MEASURE_TYPE typeE;
            MeasureMode.MEASURE_MODE modeE;
            isModeVisible = false;
            isTypeVisible = false;

            for (int j = 0; j < modeVisibleStr.length; j++) {
                modeE = MeasureMode.MEASURE_MODE.valueOf(modeVisibleStr[j].toUpperCase());

                if (modeE.equals(mode)) {
                    isModeVisible = true;
                    break;
                } else {
                    isModeVisible = false;
                }
            }
            for (int j = 0; j < typeVisibleStr.length; j++) {
                typeE = MeasureType.MEASURE_TYPE.valueOf(typeVisibleStr[j].toUpperCase());

                if (typeE.equals(type)) {
                    isTypeVisible = true;
                    break;
                } else {
                    isTypeVisible = false;
                }
            }

            if (!(isModeVisible & isTypeVisible)) {
                numOfValidPreset--;
            }
//            if (type != MeasureType.MEASURE_TYPE.SWEPT_SA && (name == null || name.contains("Clock") || name.contains("IF")))
//                numOfValidPreset--;
        }

        //Log.d("KDK-", "numOfValidPreset = " + numOfValidPreset);
        if (numOfValidPreset < 1)
            return numOfValidPreset;

        for (int i = 0; i < numOfPreset; i++) {
            section = ini.get(i + "");
            String name = section.get("name");

            modeVisibleStr = section.get("modeVisible").split(", ");
            typeVisibleStr = section.get("typeVisible").split(", ");

            MeasureType.MEASURE_TYPE typeE;
            MeasureMode.MEASURE_MODE modeE;
            isModeVisible = false;
            isTypeVisible = false;

            //tag:prestAlpha 210616
            String startF = section.get("startFreq");
            String endF = section.get("endFreq");
            String centerF = section.get("centerFreq");
            String span = section.get("span");
            String rbw = section.get("rbw");
            String vbw = section.get("vbw");
            String traceMode = section.get("traceMode");
            String traceType = section.get("traceType");
            String detector = section.get("detector");
            String sweepTime = section.get("sweepTime");
            String gate = section.get("gate");
            String gateView = section.get("gateView");
            String gateViewSweepTime = section.get("gateViewSweepTime");
            String gateDelay = section.get("gateDelay");
            String gateLength = section.get("gateLength");
            String gateSource = section.get("gateSource");
            String integBW = section.get("integBW");
            String obwPower = section.get("obwPower");
            String aclrIntegBW = section.get("aclrIntegBW");
            String offsetFreq = section.get("offsetFreq");
            String profile = section.get("profile");
            String modeVisible = section.get("modeVisible");
            String typeVisible = section.get("typeVisible");

            //preset init 파일에서 데이터 읽어오는지 체크
//            Log.e("traceMode", i+"");
//            Log.e("traceMode", "startFreq : "+startF);
//            Log.e("traceMode", "endFreq : "+endF);
//            Log.e("traceMode", "centerF : "+centerF);
//            Log.e("traceMode", "span : "+span);
//            Log.e("traceMode", "rbw : "+rbw);
//            Log.e("traceMode", "vbw : "+vbw);
//            Log.e("traceMode", "traceMode : "+traceMode);
//            Log.e("traceMode", "trcaeType : "+traceType);
//            Log.e("traceMode", "detector : "+detector);
//            Log.e("traceMode", "sweepTime : "+sweepTime);
//            Log.e("traceMode", "gateView : "+gateView);
//            Log.e("traceMode", "gateViewSweepTime : "+gateViewSweepTime);
//            Log.e("traceMode", "gateDelay : "+gateDelay);
//            Log.e("traceMode", "gateLength : "+gateLength);
//            Log.e("traceMode", "gateSource : "+gateSource);
            //tag:prestAlpha 210616

            if (name == null) continue;
            //if(type != MeasureType.MEASURE_TYPE.SWEPT_SA && (name.contains("Clock") || name.contains("IF"))) continue;
            for (int j = 0; j < modeVisibleStr.length; j++) {
                modeE = MeasureMode.MEASURE_MODE.valueOf(modeVisibleStr[j].toUpperCase());
                Log.e("Current modeE", "ModeE : " + modeE);
//                if (!modeVisibleStr[j].contains(mode.toString()))
//                    continue Loop1;
                if (modeE.equals(mode)) {
                    isModeVisible = true;
                    break;
                } else {
                    isModeVisible = false;
                }
            }
            for (int j = 0; j < typeVisibleStr.length; j++) {
                typeE = MeasureType.MEASURE_TYPE.valueOf(typeVisibleStr[j].toUpperCase());
                Log.e("Current typeE", "typeE : " + typeE);

                if (typeE.equals(type)) {
                    isTypeVisible = true;
                    break;
                } else {
                    isTypeVisible = false;
                }
            }


//            Log.e("Current isModeVisible", ""+isModeVisible);
//            Log.e("Current isTypeVisible", ""+isTypeVisible);

            if (!(isModeVisible & isTypeVisible)) {
                continue;
            }
            if (i != 0) mPresetBinding.linPresetParent.addView(getDivisionLine());

            LinearLayout linPreset = new LinearLayout(mContext);
            LinearLayout.LayoutParams presetParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);// 1f / (float) numOfValidPreset - 0.06f);
            linPreset.setLayoutParams(presetParams);
            linPreset.setOrientation(LinearLayout.HORIZONTAL);
            linPreset.setWeightSum(1);

            AutofitTextView tvName = new AutofitTextView(mContext);
            LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.18f);
            tvName.setLayoutParams(nameParams);
            tvName.setGravity(Gravity.CENTER);
            tvName.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, 1);
            tvName.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(16, mContext));
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(16, mContext));
            tvName.setTypeface(Typeface.DEFAULT_BOLD);
            tvName.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            tvName.setBackgroundResource(R.drawable.preset_unselect_button_background);
            tvName.setText(name);

            linPreset.addView(tvName);
            linPreset.addView(getDivisionButtonLine(0.036f));


            int numOfList = Integer.parseInt(section.get("numOfList"));


            //test start
            for (int k = 0; k < numOfList; k++) {

//                        InitActivity.logMsg(k + "", section.get("freq" + k) + " " + section.get("loss" + k));

                LinearLayout menuParent = getLinParent(0.14f);

                String dlStr = section.get("dl" + k);


                if (dlStr != null) {
                    String[] dlSplitStr = dlStr.split(" ");
                    double startFreq = Double.parseDouble(dlSplitStr[0]);
                    double stopFreq = Double.parseDouble(dlSplitStr[1]);
                    double avgFreq = (startFreq + stopFreq) / 2d;

                    String[] spanStr = section.get("span" + k).split(", ");


                    String[] rbwStr;
                    String[] vbwStr;
                    String[] traceModeStr;
                    String[] detectorStr;
                    String[] integBWStr;
                    String[] aclrIntegBWStr;
                    String[] offsetFreqStr;
                    String[] profileStr;


                    traceModeStr = section.get("traceMode").split(", ");
                    detectorStr = section.get("detector").split(", ");
                    integBWStr = section.get("integBW").split(", ");
                    aclrIntegBWStr = section.get("aclrIntegBW").split(", ");
                    offsetFreqStr = section.get("offsetFreq").split(", ");

                    integBW = integBWStr[k];
                    aclrIntegBW = aclrIntegBWStr[k];
                    offsetFreq = offsetFreqStr[k];


                    //Log.e("offset", "offsetFreq is : "+offsetFreq);
                    if (name.equals("3G WCDMA")) {
                        //Log.e("what name ", "name is :" +name);

                        rbwStr = section.get("rbw").split(", ");
                        vbwStr = section.get("vbw").split(", ");


                        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {
                            rbw = rbwStr[0];
                            vbw = vbwStr[0];
                            traceMode = traceModeStr[0];
                            detector = detectorStr[0];
                            span = spanStr[0];
                        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {
                            rbw = rbwStr[1];
                            vbw = vbwStr[1];
                            traceMode = traceModeStr[1];
                            detector = detectorStr[1];
                            span = spanStr[1];
                        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {
                            rbw = rbwStr[2];
                            vbw = vbwStr[2];
                            traceMode = traceModeStr[2];
                            detector = detectorStr[2];
                            span = spanStr[2];
                        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {
                            rbw = rbwStr[3];
                            vbw = vbwStr[3];
                            traceMode = traceModeStr[3];
                            detector = detectorStr[3];
                            span = spanStr[3];
                        } else {
                            rbw = rbwStr[0];
                            vbw = vbwStr[0];
                            traceMode = traceModeStr[0];
                            detector = detectorStr[0];
                            span = spanStr[0];
                        }
                    } else if (name.equals("4G LTE")) {
                        //Log.e("what name ", "name is :" +name);

                        rbwStr = section.get("rbw" + k).split(", ");
                        vbwStr = section.get("vbw" + k).split(", ");
                        profileStr = section.get("profile").split(", ");
                        profile = profileStr[k];

                        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {
                            rbw = rbwStr[0];
                            vbw = vbwStr[0];
                            traceMode = traceModeStr[0];
                            detector = detectorStr[0];
                            span = spanStr[0];
                        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {
                            rbw = rbwStr[1];
                            vbw = vbwStr[1];
                            traceMode = traceModeStr[1];
                            detector = detectorStr[1];
                            span = spanStr[1];
                        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {
                            rbw = rbwStr[2];
                            vbw = vbwStr[2];
                            traceMode = traceModeStr[2];
                            detector = detectorStr[2];
                            span = spanStr[2];
                        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {
                            rbw = rbwStr[3];
                            vbw = vbwStr[3];
                            traceMode = traceModeStr[3];
                            detector = detectorStr[3];
                            span = spanStr[3];
                        } else {
                            rbw = rbwStr[0];
                            vbw = vbwStr[0];
                            traceMode = traceModeStr[0];
                            detector = detectorStr[0];
                            span = spanStr[0];
                        }
                    } else if (name.equals("5G NR")) {
                        //dl, ul delay, length, source
                        String[] gateDelayStr = section.get("gateDelay").split(" ");
                        String[] gateLengthStr = section.get("gateLength").split(" ");
                        profileStr = section.get("profile").split(", ");

                        gateDelay = gateDelayStr[0];
                        gateLength = gateLengthStr[0];
                        profile = profileStr[k];

                        rbwStr = section.get("rbw" + k).split(", ");
                        vbwStr = section.get("vbw" + k).split(", ");

                        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {
                            rbw = rbwStr[0];
                            vbw = vbwStr[0];
                            traceMode = traceModeStr[0];
                            detector = detectorStr[0];
                            span = spanStr[0];
                        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {
                            rbw = rbwStr[1];
                            vbw = vbwStr[1];
                            traceMode = traceModeStr[1];
                            detector = detectorStr[1];
                            span = spanStr[1];
                        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {
                            rbw = rbwStr[2];
                            vbw = vbwStr[2];
                            traceMode = traceModeStr[2];
                            detector = detectorStr[2];
                            span = spanStr[2];
                        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {
                            rbw = rbwStr[3];
                            vbw = vbwStr[3];
                            traceMode = traceModeStr[3];
                            detector = detectorStr[3];
                            span = spanStr[3];
                        } else {
                            rbw = rbwStr[0];
                            vbw = vbwStr[0];
                            traceMode = traceModeStr[0];
                            detector = detectorStr[0];
                            span = spanStr[0];
                        }
                    }

                    AutofitTextView textView = getTextView(10, 0.47f);
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.preset_selector));
                    String title = section.get("dl" + k + "Title");
                    if (title == null) {
                        title = "DL " + avgFreq + " MHz";
                    }
                    textView.setText(title);

                    textView.setId(textViewId);
                    int num = textViewId;

                    ArrayList iniData = new ArrayList<>();

                    iniData.add(name);
                    iniData.add(startFreq);
                    iniData.add(stopFreq);
                    iniData.add(avgFreq);
                    iniData.add(span);
                    iniData.add(rbw);
                    iniData.add(vbw);
                    iniData.add(traceMode);
                    iniData.add(traceType);
                    iniData.add(detector);
                    iniData.add(sweepTime);
                    iniData.add(gate);
                    iniData.add(gateView);
                    iniData.add(gateViewSweepTime);
                    iniData.add(gateDelay);
                    iniData.add(gateLength);
                    iniData.add(gateSource);
                    iniData.add(integBW);
                    iniData.add(obwPower);
                    iniData.add(aclrIntegBW);
                    iniData.add(offsetFreq);
                    iniData.add(profile);

                    Log.e("Button Info", "This Button's info : " + iniData);

                    textView.setOnTouchListener(new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {


                            //tag:prestAlpha 210616
                            Log.e("PresetDialog", "this view ID : " + num);

                            //tag:prestAlpha 210617
                            applyPreset(iniData, UL_DL_STATUS.DL);
                            //tag:prestAlpha 210617

                            InitActivity.logMsg("PresetDialog", "setUpEvents :  DL -> " + ((AutofitTextView) v).getText() + " " + dlStr);
                            //setUpEvents(event, startFreq, stopFreq, textView, UL_DL_STATUS.DL);
                            return true;

                        }
                    });

                    menuParent.addView(textView);
                    menuParent.addView(getDivisionViewLine(0.06f));
                    textViewId++;
                }

                String ulStr = section.get("ul" + k);
                if (ulStr != null) {
                    String[] ulSplitStr = ulStr.split(" ");
                    double startFreq = Double.parseDouble(ulSplitStr[0]);
                    double stopFreq = Double.parseDouble(ulSplitStr[1]);
                    double avgFreq = (startFreq + stopFreq) / 2d;

                    String[] rbwStr;
                    String[] vbwStr;
                    String[] traceModeStr;
                    String[] detectorStr;
                    String[] integBWStr;
                    String[] aclrIntegBWStr;
                    String[] profileStr;

                    String[] spanStr = section.get("span" + k).split(", ");
                    traceModeStr = section.get("traceMode").split(", ");
                    detectorStr = section.get("detector").split(", ");
                    integBWStr = section.get("integBW").split(", ");
                    aclrIntegBWStr = section.get("aclrIntegBW").split(", ");

                    integBW = integBWStr[k];
                    aclrIntegBW = aclrIntegBWStr[k];

                    if (name.equals("3G WCDMA")) {
                        //Log.e("what name ", "name is :" +name);

                        rbwStr = section.get("rbw").split(", ");
                        vbwStr = section.get("vbw").split(", ");

                        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {
                            rbw = rbwStr[0];
                            vbw = vbwStr[0];
                            traceMode = traceModeStr[0];
                            detector = detectorStr[0];
                            span = spanStr[0];
                        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {
                            rbw = rbwStr[1];
                            vbw = vbwStr[1];
                            traceMode = traceModeStr[1];
                            detector = detectorStr[1];
                            span = spanStr[1];
                        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {
                            rbw = rbwStr[2];
                            vbw = vbwStr[2];
                            traceMode = traceModeStr[2];
                            detector = detectorStr[2];
                            span = spanStr[2];
                        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {
                            rbw = rbwStr[3];
                            vbw = vbwStr[3];
                            traceMode = traceModeStr[3];
                            detector = detectorStr[3];
                            span = spanStr[3];
                        } else {
                            rbw = rbwStr[0];
                            vbw = vbwStr[0];
                            traceMode = traceModeStr[0];
                            detector = detectorStr[0];
                            span = spanStr[0];
                        }
                    } else if (name.equals("4G LTE")) {
                        //Log.e("what name ", "name is :" +name);

                        rbwStr = section.get("rbw" + k).split(", ");
                        vbwStr = section.get("vbw" + k).split(", ");
                        profileStr = section.get("profile").split(", ");
                        profile = profileStr[k];

                        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {
                            rbw = rbwStr[0];
                            vbw = vbwStr[0];
                            traceMode = traceModeStr[0];
                            detector = detectorStr[0];
                            span = spanStr[0];
                        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {
                            rbw = rbwStr[1];
                            vbw = vbwStr[1];
                            traceMode = traceModeStr[1];
                            detector = detectorStr[1];
                            span = spanStr[1];
                        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {
                            rbw = rbwStr[2];
                            vbw = vbwStr[2];
                            traceMode = traceModeStr[2];
                            detector = detectorStr[2];
                            span = spanStr[2];
                        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {
                            rbw = rbwStr[3];
                            vbw = vbwStr[3];
                            traceMode = traceModeStr[3];
                            detector = detectorStr[3];
                            span = spanStr[3];
                        } else {
                            rbw = rbwStr[0];
                            vbw = vbwStr[0];
                            traceMode = traceModeStr[0];
                            detector = detectorStr[0];
                            span = spanStr[0];
                        }
                    } else if (name.equals("5G NR")) {
                        //dl, ul delay, length
                        String[] gateDelayStr = section.get("gateDelay").split(" ");
                        String[] gateLengthStr = section.get("gateLength").split(" ");

                        gateDelay = gateDelayStr[1];
                        gateLength = gateLengthStr[1];

                        rbwStr = section.get("rbw" + k).split(", ");
                        vbwStr = section.get("vbw" + k).split(", ");
                        profileStr = section.get("profile").split(", ");
                        profile = profileStr[k];

                        if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {
                            rbw = rbwStr[0];
                            vbw = vbwStr[0];
                            traceMode = traceModeStr[0];
                            detector = detectorStr[0];
                            span = spanStr[0];
                        } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {
                            rbw = rbwStr[1];
                            vbw = vbwStr[1];
                            traceMode = traceModeStr[1];
                            detector = detectorStr[1];
                            span = spanStr[1];
                        } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {
                            rbw = rbwStr[2];
                            vbw = vbwStr[2];
                            traceMode = traceModeStr[2];
                            detector = detectorStr[2];
                            span = spanStr[2];
                        } else if (type == MeasureType.MEASURE_TYPE.ACLR) {
                            rbw = rbwStr[3];
                            vbw = vbwStr[3];
                            traceMode = traceModeStr[3];
                            detector = detectorStr[3];
                            span = spanStr[3];
                        } else {
                            rbw = rbwStr[0];
                            vbw = vbwStr[0];
                            traceMode = traceModeStr[0];
                            detector = detectorStr[0];
                            span = spanStr[0];
                        }
                        Log.e("5G", "rbw : " + rbw + "vbw" + vbw);
                    }

                    AutofitTextView textView = getTextView(10, 0.47f);
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.preset_selector));
                    String title = section.get("ul" + k + "Title");
                    if (title == null) {
                        title = "UL " + avgFreq + " MHz";
                    }
                    textView.setText(title);

                    textView.setId(textViewId);
                    int num = textViewId;

                    ArrayList iniData = new ArrayList<>();

                    iniData.add(name);
                    iniData.add(startFreq);//start
                    iniData.add(stopFreq);//end
                    iniData.add(avgFreq);//center
                    iniData.add(span);
                    iniData.add(rbw);
                    iniData.add(vbw);
                    iniData.add(traceMode);
                    iniData.add(traceType);
                    iniData.add(detector);
                    iniData.add(sweepTime);
                    iniData.add(gate);
                    iniData.add(gateView);
                    iniData.add(gateViewSweepTime);
                    iniData.add(gateDelay);
                    iniData.add(gateLength);
                    iniData.add(gateSource);
                    iniData.add(integBW);
                    iniData.add(obwPower);
                    iniData.add(aclrIntegBW);
                    iniData.add(offsetFreq);
                    iniData.add(profile);

                    //Log.e("Button Info", "This Button's info : "+iniData);


                    textView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {

                            //tag:prestAlpha 210616
                            //Log.e("PresetDialog", "this view ID : " +num);
                            //tag:prestAlpha 210616

                            //tag:prestAlpha 210617
                            applyPreset(iniData, UL_DL_STATUS.UL);
                            //tag:prestAlpha 210617

                            InitActivity.logMsg("PresetDialog", "setUpEvents :  UL - > " + ((AutofitTextView) v).getText() + " " + ulStr);
                            //setUpEvents(event, startFreq, stopFreq, textView, UL_DL_STATUS.UL);
                            return true;
                        }
                    });

                    menuParent.addView(textView);
                    textViewId++;
                }

                String singleStr = section.get("single" + k);
                if (singleStr != null) {
                    String[] singleSplitStr = singleStr.split(" ");
                    float startFreq = Float.parseFloat(singleSplitStr[0]);
                    float stopFreq = Float.parseFloat(singleSplitStr[1]);
                    float avgFreq = (startFreq + stopFreq) / 2f;

                    span = section.get("span" + k);

                    AutofitTextView textView = getTextView(12, 1f);
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.preset_selector));

                    String title = section.get("single" + k + "Title");
                    if (title == null) {
                        title = avgFreq + " MHz";
                    }
                    textView.setText(title);

                    textView.setId(textViewId);
                    int num = textViewId;

                    ArrayList iniData = new ArrayList<>();

                    iniData.add(name);
                    iniData.add(startFreq);//start
                    iniData.add(stopFreq);//end
                    iniData.add(avgFreq);//center
                    iniData.add(span);
                    iniData.add(rbw);
                    iniData.add(vbw);
                    iniData.add(traceMode);
                    iniData.add(traceType);
                    iniData.add(detector);
                    iniData.add(sweepTime);
                    iniData.add(gate);
                    iniData.add(gateView);
                    iniData.add(gateViewSweepTime);
                    iniData.add(gateDelay);
                    iniData.add(gateLength);
                    iniData.add(gateSource);

                    //Log.e("Button Info", "This Button's info : "+iniData);


                    textView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {

                            //tag:prestAlpha 210616
                            //Log.e("PresetDialog", "this view ID : " +num);
                            //tag:prestAlpha 210616

                            //tag:prestAlpha 210617
                            applyPreset(iniData, UL_DL_STATUS.SINGLE);
                            //tag:prestAlpha 210617

                            //setUpEvents(event, startFreq, stopFreq, textView, UL_DL_STATUS.SINGLE);


                            return true;
                        }
                    });

                    menuParent.addView(textView);
                    textViewId++;
                }

                linPreset.addView(menuParent);
                linPreset.addView(getDivisionButtonLine(0.018f));

            }
            mPresetBinding.linPresetParent.addView(linPreset);
        }

        return numOfValidPreset;
    }
    //tag:prestAlpha 210617 end

    //tag:presetAlpha 210617 start
    private void applyPreset(ArrayList arrayList, UL_DL_STATUS status) {
        //apply preset function
        //textView onTouched call this function
        //get iniData ArrayList
        //define data from arrayList

        String name = String.valueOf(arrayList.get(0));
        double startFreq = Double.parseDouble(String.valueOf(arrayList.get(1)));
        double endFreq = Double.parseDouble(String.valueOf(arrayList.get(2)));
        double centerFreq = Double.parseDouble(String.valueOf(arrayList.get(3)));
        double span = Double.parseDouble(String.valueOf(arrayList.get(4)));
        int rbw = Integer.parseInt(String.valueOf(arrayList.get(5)));
        int vbw = Integer.parseInt(String.valueOf(arrayList.get(6)));
        String traceMode = String.valueOf(arrayList.get(7));//trace mode from ini
        String traceType = String.valueOf(arrayList.get(8));//trace type from ini
        String detectorStr = String.valueOf(arrayList.get(9));//detector from ini
        String sweepTimeStr = String.valueOf(arrayList.get(10));//sweep time from ini
        String gateStr = String.valueOf(arrayList.get(11));//gate from ini
        String gateViewStr = String.valueOf(arrayList.get(12));//gate view from ini
        double gateViewSweepTime = Double.parseDouble(String.valueOf(arrayList.get(13)));
        double gateDelay = Double.parseDouble(String.valueOf(arrayList.get(14)));
        double gateLength = Double.parseDouble(String.valueOf(arrayList.get(15)));
        String gateSourceStr = String.valueOf(arrayList.get(16));//gate source from ini


        Log.e("ini data ", "detector form ini : " + detectorStr);


        TraceEnumData.TRACE_MODE trace_mode = TraceEnumData.TRACE_MODE.valueOf(traceMode.toUpperCase());//trace mode value
        TraceEnumData.TRACE_TYPE trace_type = TraceEnumData.TRACE_TYPE.valueOf(traceType.toUpperCase());//trace type value
        TraceEnumData.DETECTOR detector = TraceEnumData.DETECTOR.valueOf(detectorStr.toUpperCase());//detector value
        SaSweepTimeData.SWEEP_TIME_MODE sweep_time_mode = SaSweepTimeData.SWEEP_TIME_MODE.valueOf(sweepTimeStr.toUpperCase());//sweep time value
        SaGateData.GATE_MODE gate = SaGateData.GATE_MODE.valueOf(gateStr.toUpperCase());
        SaGateData.GATE_MODE gate_view = SaGateData.GATE_MODE.valueOf(gateViewStr.toUpperCase());
        SaGateData.GATE_SOURCE gate_source = SaGateData.GATE_SOURCE.valueOf(gateSourceStr.toUpperCase());

        Log.e("ApplyPreset",
                "Name : " + name +
                        ", Start Freq : " + startFreq +
                        ", End Freq : " + endFreq +
                        ", Center Freq :" + centerFreq +
                        ", Span : " + span + ", Rbw : " + rbw +
                        ", Vbw : " + vbw +
                        ", Sweep Time : " + gateViewSweepTime +
                        ", Gate Length : " + gateLength);
        //apply - settings, view, cmd ....

        //get current measure mode
        MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
        BWData bwData = new BWData();

        if (mode == MeasureMode.MEASURE_MODE.SA) {
            //sa prev freq load, set centerFreq, check range of frequency
            SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
            SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(centerFreq);
            SaDataHandler.getInstance().getConfigData().getFrequencyData().checkFreqRange();

            bwData.setRBW(rbw);
            bwData.setVBW(vbw);
            Log.e("RBW check", "RBW : " + bwData.getRBW().getString());
            Log.e("VBW check", "VBW : " + bwData.getVBW().getString());


            Log.e("name check", "this is " + name);

            //clock
            if ((name).equals("Clock")) {


                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
                SaConfigData data = SaDataHandler.getInstance().getConfigData();

                if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {
                    //data set
                    data.getFrequencyData().setSpan(span);//span
                    data.getFrequencyData().setCenterFreq(centerFreq);//center

                    Log.e("RBW check", "In clock RBW : " + bwData.getRBW().getString());
                    Log.e("VBW check", "In clock VBW : " + bwData.getVBW().getString());

                    data.getBwData().setRBW(bwData.getRBW());//RBW
                    data.getBwData().setVBW(bwData.getVBW());//VBW
                    //210622 수정
                    data.getTraceData().setAllMode(trace_mode);//traceMode
                    data.getTraceData().setType(trace_type, 0);//traceType
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);
                    //

                    data.getTraceData().setAllDetector(detector);//detector

                    data.getSweepTimeData().setMode(sweep_time_mode);//sweepTime
                    data.getSweepTimeData().getGateData().setGateMode(gate);//gate
                    data.getSweepTimeData().getGateData().setGateView(gate_view);//gateView
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);//gateViewSweepTime
                    data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);//gateDelay
                    data.getSweepTimeData().getGateData().setGateLength((int) gateLength * 1000);//gateLength
                    data.getSweepTimeData().getGateData().setGateSource(gate_source);//gateSource

                }
                data.loadData();
                ViewHandler.getInstance().getSAView().update();

            } else if ((name).equals("IF")) {

                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
                SaConfigData data = SaDataHandler.getInstance().getConfigData();

                if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {
                    //data set
                    data.getFrequencyData().setSpan(span);//span
                    data.getFrequencyData().setCenterFreq(centerFreq);//center
                    data.getBwData().setRBW(bwData.getRBW());//RBW
                    data.getBwData().setVBW(bwData.getVBW());//VBW
                    data.getTraceData().setAllMode(trace_mode);//traceMode

                    data.getTraceData().setType(trace_type, 0);//traceType
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

                    data.getTraceData().setAllDetector(detector);//detector

                    data.getSweepTimeData().setMode(sweep_time_mode);//sweepTime
                    data.getSweepTimeData().getGateData().setGateMode(gate);//gate
                    data.getSweepTimeData().getGateData().setGateView(gate_view);//gateView
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);//gateViewSweepTime
                    data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);//gateDelay
                    data.getSweepTimeData().getGateData().setGateLength((int) gateLength * 1000);//gateLength
                    data.getSweepTimeData().getGateData().setGateSource(gate_source);//gateSource

                }
                data.loadData();
                ViewHandler.getInstance().getSAView().update();
            }
            else if ((name).equals("3G WCDMA")) {

                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
                SaConfigData data = SaDataHandler.getInstance().getConfigData();
                double integBW = Double.parseDouble(String.valueOf(arrayList.get(17)));
                float obwPower = Float.parseFloat(String.valueOf(arrayList.get(18)));
                double aclrIntegBW = Double.parseDouble(String.valueOf(arrayList.get(19)));
                double offsetFreq = Double.parseDouble(String.valueOf(arrayList.get(20)));

                //수정 필 하드코딩 존재
                if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {
                    //data set
                    data.getFrequencyData().setSpan(span);//span
                    data.getBwData().setRBW(bwData.getRBW());//RBW
                    data.getBwData().setVBW(bwData.getVBW());//VBW
                    data.getTraceData().setAllMode(trace_mode);//traceMode

                    data.getTraceData().setType(trace_type, 0);//traceType
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

                    data.getTraceData().setAllDetector(detector);//detector

                    data.getSweepTimeData().setMode(sweep_time_mode);//sweepTime
                    data.getSweepTimeData().getGateData().setGateMode(gate);//gate
                    data.getSweepTimeData().getGateData().setGateView(gate_view);//gateView
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);//gateViewSweepTime
                    data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);//gateDelay
                    data.getSweepTimeData().getGateData().setGateLength((int) gateLength * 1000);//gateLength
                    data.getSweepTimeData().getGateData().setGateSource(gate_source);//gateSource

                    //5 라는 숫자는 IntegBW 값을 의미 하는 것으로 보임 Channel Power Meas setting
                    data.getChannelPowerMeasSetupData().setIngegBW((long) (integBW * 1000 * 1000));
                } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

                    data.getFrequencyData().setSpan(span);//span
                    data.getBwData().setRBW(bwData.getRBW());//RBW
                    data.getBwData().setVBW(bwData.getVBW());//VBW
                    data.getTraceData().setAllMode(trace_mode);//traceMode

                    data.getTraceData().setAllDetector(detector);//detector

                    data.getSweepTimeData().setMode(sweep_time_mode);//sweepTime
                    data.getSweepTimeData().getGateData().setGateMode(gate);//gate
                    data.getSweepTimeData().getGateData().setGateView(gate_view);//gateView
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);//gateViewSweepTime
                    data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);//gateDelay
                    data.getSweepTimeData().getGateData().setGateLength((int) gateLength * 1000);//gateLength
                    data.getSweepTimeData().getGateData().setGateSource(gate_source);//gateSource

                    data.getChannelPowerMeasSetupData().setIngegBW((long) (integBW * 1000 * 1000));

                } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

                    data.getFrequencyData().setSpan(span);//span
                    data.getBwData().setRBW(bwData.getRBW());//RBW
                    data.getBwData().setVBW(bwData.getVBW());//VBW
                    data.getTraceData().setAllMode(trace_mode);//traceMode
                    data.getTraceData().setAllDetector(detector);//detector
                    data.getOccupiedBwMeasSetupData().setOBWPower(obwPower);

                    data.getSweepTimeData().setMode(sweep_time_mode);//sweepTime
                    data.getSweepTimeData().getGateData().setGateMode(gate);//gate
                    data.getSweepTimeData().getGateData().setGateView(gate_view);//gateView
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);//gateViewSweepTime
                    data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);//gateDelay
                    data.getSweepTimeData().getGateData().setGateLength((int) gateLength * 1000);//gateLength
                    data.getSweepTimeData().getGateData().setGateSource(gate_source);//gateSource
                } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

                    data.getFrequencyData().setSpan(span);//span
                    data.getBwData().setRBW(bwData.getRBW());//RBW
                    data.getBwData().setVBW(bwData.getVBW());//VBW
                    data.getTraceData().setAllMode(trace_mode);//traceMode
                    data.getTraceData().setAllDetector(detector);//detector

                    data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);//carrier set
                    data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
                    data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
                    data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(aclrIntegBW);

                    data.getSweepTimeData().setMode(sweep_time_mode);//sweepTime
                    data.getSweepTimeData().getGateData().setGateMode(gate);//gate
                    data.getSweepTimeData().getGateData().setGateView(gate_view);//gateView
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);//gateViewSweepTime
                    data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);//gateDelay
                    data.getSweepTimeData().getGateData().setGateLength((int) gateLength * 1000);//gateLength
                    data.getSweepTimeData().getGateData().setGateSource(gate_source);//gateSource

                    data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);


                    for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                        data.getAclrMeasSetupData()
                                .getOffsetSetupData()
                                .setOffsetSpacing(offsetFreq * (i + 1), i);

                        data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(aclrIntegBW, i);
                        data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                        data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.RELATIVE, i);

                        data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(50f, i);

                    }

                    data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, 0);
                    data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-49.2f, 1);

                } else if (type == MeasureType.MEASURE_TYPE.SEM) {

                    data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

                    SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
                    refChannelData.setSpan(5d);
                    refChannelData.setIntegBw(3.84f);
                    refChannelData.getBwData().setRBW(KHZ30);
                    refChannelData.getBwData().setVBW(KHZ30);

                    SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

                    /*
                     * mask index 0(1)
                     * */

                    editMaskData.setMaskOnOff(ON, 0);

                    editMaskData.setStartFreq(0.015d, 0);
                    editMaskData.setStopFreq(0.215d, 0);

                    editMaskData.setMaskSide(BOTH, 0);

                    editMaskData.getBwData(0).setRBW(KHZ30);
                    editMaskData.getBwData(0).setVBW(KHZ300);

                    editMaskData.setAbsStartLimit(-12.5f, 0);
                    editMaskData.setAbsStopLimit(-12.5f, 0);

                    editMaskData.setRelStartLimit(-30f, 0);
                    editMaskData.setRelStopLimit(-30f, 0);

                    editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 0);

                    /*
                     * mask index 1(2)
                     * */

                    editMaskData.setMaskOnOff(ON, 1);

                    editMaskData.setStartFreq(0.215d, 1);
                    editMaskData.setStopFreq(1.015d, 1);

                    editMaskData.setMaskSide(BOTH, 1);

                    editMaskData.getBwData(1).setRBW(KHZ30);
                    editMaskData.getBwData(1).setVBW(KHZ300);

                    editMaskData.setAbsStartLimit(-12.5f, 1);
                    editMaskData.setAbsStopLimit(-24.5f, 1);

                    editMaskData.setRelStartLimit(-30f, 1);
                    editMaskData.setRelStopLimit(-30f, 1);

                    editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 1);

                    /*
                     * mask index 2(3)
                     * */

                    editMaskData.setMaskOnOff(ON, 2);

                    editMaskData.setStartFreq(1.015d, 2);
                    editMaskData.setStopFreq(1.5d, 2);

                    editMaskData.setMaskSide(BOTH, 2);

                    editMaskData.getBwData(2).setRBW(KHZ30);
                    editMaskData.getBwData(2).setVBW(KHZ300);

                    editMaskData.setAbsStartLimit(-24.5f, 2);
                    editMaskData.setAbsStopLimit(-24.5f, 2);

                    editMaskData.setRelStartLimit(-30f, 2);
                    editMaskData.setRelStopLimit(-30f, 2);

                    editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 2);

                    /*
                     * mask index 3(4)
                     * */

                    editMaskData.setMaskOnOff(ON, 3);

                    editMaskData.setStartFreq(1.5d, 3);
                    editMaskData.setStopFreq(5.5d, 3);

                    editMaskData.setMaskSide(BOTH, 3);

                    editMaskData.getBwData(3).setRBW(MHZ1);
                    editMaskData.getBwData(3).setVBW(KHZ10);

                    editMaskData.setAbsStartLimit(-11.5f, 3);
                    editMaskData.setAbsStopLimit(-11.5f, 3);

                    editMaskData.setRelStartLimit(-30f, 3);
                    editMaskData.setRelStopLimit(-30f, 3);

                    editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

                }

                data.loadData();
            }
            else if ((name).equals("4G LTE")) {
                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
                SaConfigData data = SaDataHandler.getInstance().getConfigData();
                double integBW = Double.parseDouble(String.valueOf(arrayList.get(17)));
                float obwPower = Float.parseFloat(String.valueOf(arrayList.get(18)));
                double aclrIntegBW = Double.parseDouble(String.valueOf(arrayList.get(19)));
                double offsetFreq = Double.parseDouble(String.valueOf(arrayList.get(20)));

                if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {
                    data.getFrequencyData().setSpan(span);//span
                    data.getBwData().setRBW(bwData.getRBW());//rbw
                    data.getBwData().setVBW(bwData.getVBW());//vbw
                    data.getTraceData().setAllMode(trace_mode);//traceMode

                    data.getTraceData().setType(trace_type, 0);//traceType
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

                    data.getTraceData().setAllDetector(detector);//detector

                    data.getSweepTimeData().setMode(sweep_time_mode);//sweep time
                    data.getSweepTimeData().getGateData().setGateMode(gate);
                    data.getSweepTimeData().getGateData().setGateView(gate_view);
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);
                    data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);
                    data.getSweepTimeData().getGateData().setGateLength((int) gateLength * 1000);
                    data.getSweepTimeData().getGateData().setGateSource(gate_source);

                    data.getChannelPowerMeasSetupData().setIngegBW((long) (integBW * 1000 * 1000));
                } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

                    data.getFrequencyData().setSpan(span);
                    data.getBwData().setRBW(bwData.getRBW());
                    data.getBwData().setVBW(bwData.getVBW());
                    data.getTraceData().setAllMode(trace_mode);
                    data.getTraceData().setAllDetector(detector);
                    data.getChannelPowerMeasSetupData().setIngegBW((long) (integBW * 1000 * 1000));

                    data.getSweepTimeData().setMode(sweep_time_mode);
                    data.getSweepTimeData().getGateData().setGateMode(gate);
                    data.getSweepTimeData().getGateData().setGateView(gate_view);
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);
                    data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);
                    data.getSweepTimeData().getGateData().setGateLength((int) gateLength * 1000);
                    data.getSweepTimeData().getGateData().setGateSource(gate_source);

                } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

                    data.getFrequencyData().setSpan(span);
                    data.getBwData().setRBW(bwData.getRBW());
                    data.getBwData().setVBW(bwData.getVBW());
                    data.getTraceData().setAllMode(trace_mode);
                    data.getTraceData().setAllDetector(detector);
                    data.getOccupiedBwMeasSetupData().setOBWPower(obwPower);

                    data.getSweepTimeData().setMode(sweep_time_mode);
                    data.getSweepTimeData().getGateData().setGateMode(gate);
                    data.getSweepTimeData().getGateData().setGateView(gate_view);
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);
                    data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);
                    data.getSweepTimeData().getGateData().setGateLength((int) gateLength * 1000);
                    data.getSweepTimeData().getGateData().setGateSource(gate_source);

                } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

                    data.getFrequencyData().setSpan(span);
                    data.getBwData().setRBW(bwData.getRBW());
                    data.getBwData().setVBW(bwData.getVBW());
                    data.getTraceData().setAllMode(trace_mode);
                    data.getTraceData().setAllDetector(detector);

                    data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
                    data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
                    data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
                    data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(aclrIntegBW);

                    data.getSweepTimeData().setMode(sweep_time_mode);
                    data.getSweepTimeData().getGateData().setGateMode(gate);
                    data.getSweepTimeData().getGateData().setGateView(gate_view);
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);
                    data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);
                    data.getSweepTimeData().getGateData().setGateLength((int) gateLength * 1000);
                    data.getSweepTimeData().getGateData().setGateSource(gate_source);

                    data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

                    for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                        data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);
                        data.getAclrMeasSetupData()
                                .getOffsetSetupData()
                                .setOffsetSpacing(offsetFreq * (i + 1), i);

                        data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(aclrIntegBW, i);
                        data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                        data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                        data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-14.61f, i);
                        data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, i);

                    }


                } else if (type == MeasureType.MEASURE_TYPE.SEM) {

                    data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

                    SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
                    refChannelData.setSpan(3d);
                    refChannelData.setIntegBw(2.715f);
                    refChannelData.getBwData().setRBW(KHZ100);
                    refChannelData.getBwData().setVBW(KHZ100);

                    SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

                    /*
                     * mask index 0(1)
                     * */

                    editMaskData.setMaskOnOff(ON, 0);

                    editMaskData.setStartFreq(0.05d, 0);
                    editMaskData.setStopFreq(3.05d, 0);

                    editMaskData.setMaskSide(BOTH, 0);

                    editMaskData.getBwData(0).setRBW(KHZ100);
                    editMaskData.getBwData(0).setVBW(KHZ1);

                    editMaskData.setAbsStartLimit(-3.5f, 0);
                    editMaskData.setAbsStopLimit(-13.5f, 0);

                    editMaskData.setRelStartLimit(-30f, 0);
                    editMaskData.setRelStopLimit(-30f, 0);

                    editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 0);

                    /*
                     * mask index 1(2)
                     * */

                    editMaskData.setMaskOnOff(ON, 1);

                    editMaskData.setStartFreq(3.05d, 1);
                    editMaskData.setStopFreq(6.05d, 1);

                    editMaskData.setMaskSide(BOTH, 1);

                    editMaskData.getBwData(1).setRBW(KHZ100);
                    editMaskData.getBwData(1).setVBW(KHZ1);

                    editMaskData.setAbsStartLimit(-13.5f, 1);
                    editMaskData.setAbsStopLimit(-13.5f, 1);

                    editMaskData.setRelStartLimit(-30f, 1);
                    editMaskData.setRelStopLimit(-30f, 1);

                    editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 1);

                    /*
                     * mask index 2(3)
                     * */

                    editMaskData.setMaskOnOff(ON, 2);

                    editMaskData.setStartFreq(3.05d, 2);
                    editMaskData.setStopFreq(6.05d, 2);

                    editMaskData.setMaskSide(BOTH, 2);

                    editMaskData.getBwData(2).setRBW(MHZ1);
                    editMaskData.getBwData(2).setVBW(KHZ10);

                    editMaskData.setAbsStartLimit(-15f, 2);
                    editMaskData.setAbsStopLimit(-15f, 2);

                    editMaskData.setRelStartLimit(-30f, 2);
                    editMaskData.setRelStopLimit(-30f, 2);

                    editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 2);

                    /*
                     * mask index 3(4)
                     * */

                    editMaskData.setMaskOnOff(OFF, 3);

                    editMaskData.setStartFreq(6.5d, 3);
                    editMaskData.setStopFreq(15d, 3);

                    editMaskData.setMaskSide(BOTH, 3);

                    editMaskData.getBwData(3).setRBW(MHZ1);
                    editMaskData.getBwData(3).setVBW(KHZ10);

                    editMaskData.setAbsStartLimit(-15f, 3);
                    editMaskData.setAbsStopLimit(-15f, 3);

                    editMaskData.setRelStartLimit(-30f, 3);
                    editMaskData.setRelStopLimit(-30f, 3);

                    editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);

                }
                data.loadData();
            } else if ((name).equals("5G NR")) {

                MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
                SaConfigData data = SaDataHandler.getInstance().getConfigData();
                double integBW = Double.parseDouble(String.valueOf(arrayList.get(17)));
                float obwPower = Float.parseFloat(String.valueOf(arrayList.get(18)));
                double aclrIntegBW = Double.parseDouble(String.valueOf(arrayList.get(19)));
                double offsetFreq = Double.parseDouble(String.valueOf(arrayList.get(20)));

                if (type == MeasureType.MEASURE_TYPE.SWEPT_SA) {

                    data.getFrequencyData().setSpan(span);
                    data.getBwData().setRBW(bwData.getRBW());
                    data.getBwData().setVBW(bwData.getVBW());
                    data.getTraceData().setAllMode(trace_mode);

                    data.getTraceData().setType(trace_type, 0);
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 1);
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 2);
                    data.getTraceData().setType(TraceEnumData.TRACE_TYPE.BLANK, 3);

                    data.getTraceData().setAllDetector(detector);

                    data.getSweepTimeData().setMode(sweep_time_mode);
                    data.getSweepTimeData().getGateData().setGateMode(gate);
                    data.getSweepTimeData().getGateData().setGateView(gate_view);
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);

                    if (status == PresetDialog.UL_DL_STATUS.DL) {

                        data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);
                        data.getSweepTimeData().getGateData().setGateLength((int) (gateLength * 1000));

                    } else if (status == PresetDialog.UL_DL_STATUS.UL) {

                        data.getSweepTimeData().getGateData().setGateDelay((int) (gateDelay * 1000));
                        data.getSweepTimeData().getGateData().setGateLength((int) (gateLength * 1000));
                    }

                    data.getSweepTimeData().getGateData().setGateSource(gate_source);
                    data.getChannelPowerMeasSetupData().setIngegBW((long) (integBW * 1000 * 1000));

                } else if (type == MeasureType.MEASURE_TYPE.CHANNEL_POWER) {

                    data.getFrequencyData().setSpan(span);
                    data.getBwData().setRBW(bwData.getRBW());
                    data.getBwData().setVBW(bwData.getVBW());
                    data.getTraceData().setAllMode(trace_mode);
                    data.getTraceData().setAllDetector(detector);
                    data.getChannelPowerMeasSetupData().setIngegBW((long) (integBW * 1000 * 1000));

                    data.getSweepTimeData().setMode(sweep_time_mode);
                    data.getSweepTimeData().getGateData().setGateMode(gate);
                    data.getSweepTimeData().getGateData().setGateView(gate_view);
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);
                    if (status == PresetDialog.UL_DL_STATUS.DL) {

                        data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);
                        data.getSweepTimeData().getGateData().setGateLength((int) (gateLength * 1000));

                    } else if (status == PresetDialog.UL_DL_STATUS.UL) {

                        data.getSweepTimeData().getGateData().setGateDelay((int) (gateDelay * 1000));
                        data.getSweepTimeData().getGateData().setGateLength((int) (gateLength * 1000));
                    }
                    data.getSweepTimeData().getGateData().setGateSource(gate_source);


                } else if (type == MeasureType.MEASURE_TYPE.OCCUPIED_BW) {

                    data.getFrequencyData().setSpan(span);
                    data.getBwData().setRBW(bwData.getRBW());
                    data.getBwData().setVBW(bwData.getVBW());
                    data.getTraceData().setAllMode(trace_mode);
                    data.getTraceData().setAllDetector(detector);
                    data.getOccupiedBwMeasSetupData().setOBWPower(obwPower);

                    data.getSweepTimeData().setMode(sweep_time_mode);
                    data.getSweepTimeData().getGateData().setGateMode(gate);
                    data.getSweepTimeData().getGateData().setGateView(gate_view);
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);
                    if (status == PresetDialog.UL_DL_STATUS.DL) {

                        data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);
                        data.getSweepTimeData().getGateData().setGateLength((int) (gateLength * 1000));

                    } else if (status == PresetDialog.UL_DL_STATUS.UL) {

                        data.getSweepTimeData().getGateData().setGateDelay((int) (gateDelay * 1000));
                        data.getSweepTimeData().getGateData().setGateLength((int) (gateLength * 1000));
                    }
                    data.getSweepTimeData().getGateData().setGateSource(gate_source);

                } else if (type == MeasureType.MEASURE_TYPE.ACLR) {

                    data.getFrequencyData().setSpan(span);
                    data.getBwData().setRBW(bwData.getRBW());
                    data.getBwData().setVBW(bwData.getVBW());
                    data.getTraceData().setAllMode(trace_mode);
                    data.getTraceData().setAllDetector(detector);

                    data.getAclrMeasSetupData().getCarrierSetupData().setCarriers(1);
                    data.getAclrMeasSetupData().getCarrierSetupData().setRefCarrier(1);
                    data.getAclrMeasSetupData().getCarrierSetupData().setCarrierSpacing(0d);
                    data.getAclrMeasSetupData().getCarrierSetupData().setIntegBw(aclrIntegBW);

                    data.getSweepTimeData().setMode(sweep_time_mode);
                    data.getSweepTimeData().getGateData().setGateMode(gate);
                    data.getSweepTimeData().getGateData().setGateView(gate_view);
                    data.getSweepTimeData().getGateData().setGateViewSweepTime((int) gateViewSweepTime * 1000);
                    if (status == PresetDialog.UL_DL_STATUS.DL) {

                        data.getSweepTimeData().getGateData().setGateDelay((int) gateDelay);
                        data.getSweepTimeData().getGateData().setGateLength((int) (gateLength * 1000));

                    } else if (status == PresetDialog.UL_DL_STATUS.UL) {

                        data.getSweepTimeData().getGateData().setGateDelay((int) (gateDelay * 1000));
                        data.getSweepTimeData().getGateData().setGateLength((int) (gateLength * 1000));
                    }
                    data.getSweepTimeData().getGateData().setGateSource(gate_source);

                    data.getAclrMeasSetupData().getOffsetSetupData().setNumOfOffset(2);

                    for (int i = 0; i < data.getAclrMeasSetupData().getOffsetSetupData().getNumOfOffset(); i++) {

                        data.getAclrMeasSetupData()
                                .getOffsetSetupData()
                                .setOffsetSpacing(offsetFreq * (i + 1), i);

                        data.getAclrMeasSetupData().getOffsetSetupData().setIntegBw(aclrIntegBW, i);
                        data.getAclrMeasSetupData().getOffsetSetupData().setOffsetSide(BOTH, i);
                        data.getAclrMeasSetupData().getOffsetSetupData().setFailSource(FailSourceData.FAIL_SOURCE.ALL, i);

                        data.getAclrMeasSetupData().getOffsetSetupData().setAbsLimit(-8.47f, i);
                        data.getAclrMeasSetupData().getOffsetSetupData().setRelLimit(-44.2f, i);

                    }


                } else if (type == MeasureType.MEASURE_TYPE.SEM) {

                    data.getSemMeasSetupData().setType(SemMeasTypeData.SEM_MEASURE_TYPE.TOTAL_POWER);

                    SemRefChannelData refChannelData = data.getSemMeasSetupData().getRefChannelData();
                    refChannelData.setSpan(5d);
                    refChannelData.setIntegBw(4.5f);

                    refChannelData.getBwData().setRBW(KHZ100);
                    refChannelData.getBwData().setVBW(KHZ100);

                    SemEditMaskData editMaskData = data.getSemMeasSetupData().getEditMaskData();

                    /*
                     * mask index 0(1)
                     * */

                    editMaskData.setMaskOnOff(ON, 0);
                    editMaskData.setStartFreq(0.05d, 0);
                    editMaskData.setStopFreq(5.05d, 0);
                    editMaskData.setMaskSide(BOTH, 0);

                    editMaskData.getBwData(0).setRBW(KHZ100);
                    editMaskData.getBwData(0).setVBW(KHZ1);

                    editMaskData.setAbsStartLimit(-5.2f, 0);
                    editMaskData.setAbsStopLimit(-12.2f, 0);

                    editMaskData.setRelStartLimit(-30f, 0);
                    editMaskData.setRelStopLimit(-30f, 0);

                    editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 0);

                    /*
                     * mask index 1(2)
                     * */

                    editMaskData.setMaskOnOff(ON, 1);
                    editMaskData.setStartFreq(5.05d, 1);
                    editMaskData.setStopFreq(10.05d, 1);
                    editMaskData.setMaskSide(BOTH, 1);

                    editMaskData.getBwData(1).setRBW(KHZ100);
                    editMaskData.getBwData(1).setVBW(KHZ1);

                    editMaskData.setAbsStartLimit(-12.2f, 1);
                    editMaskData.setAbsStopLimit(-12.2f, 1);

                    editMaskData.setRelStartLimit(-30f, 1);
                    editMaskData.setRelStopLimit(-30f, 1);

                    editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 1);

                    /*
                     * mask index 2(3)
                     * */

                    editMaskData.setMaskOnOff(ON, 2);
                    editMaskData.setStartFreq(10.5d, 2);
                    editMaskData.setStopFreq(40d, 2);
                    editMaskData.setMaskSide(BOTH, 2);

                    editMaskData.getBwData(2).setRBW(MHZ1);
                    editMaskData.getBwData(2).setVBW(KHZ10);

                    editMaskData.setAbsStartLimit(-15f, 2);
                    editMaskData.setAbsStopLimit(-15f, 2);

                    editMaskData.setRelStartLimit(-30f, 2);
                    editMaskData.setRelStopLimit(-30f, 2);

                    editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 2);

                    /*
                     * mask index 3(4)
                     * */

                    editMaskData.setMaskOnOff(OFF, 3);

                    editMaskData.setStartFreq(40d, 3);
                    editMaskData.setStopFreq(50d, 3);
                    editMaskData.setMaskSide(BOTH, 3);

                    editMaskData.getBwData(3).setRBW(MHZ1);
                    editMaskData.getBwData(3).setVBW(KHZ10);

                    editMaskData.setAbsStartLimit(-15f, 3);
                    editMaskData.setAbsStopLimit(-15f, 3);

                    editMaskData.setRelStartLimit(-30f, 3);
                    editMaskData.setRelStopLimit(-30f, 3);

                    editMaskData.setFailSource(FailSourceData.FAIL_SOURCE.ABSOLUTE, 3);


                }
                data.loadData();
            }

            //view update
            ViewHandler.getInstance().getContent().subInfoUpdate();
            ViewHandler.getInstance().getSaFrequencyView().updateFreq();
            ViewHandler.getInstance().getSASpanView().updateSpan();
            //mqtt refresh, send
            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    SaDataHandler.getInstance().getConfigData().getSaParameter()
            );
        } else if (mode == MeasureMode.MEASURE_MODE.CL) {
            for (int i = 0; i < arrayList.size(); i++) {
                Log.e("CL", "" + arrayList.get(i) + "\n");
            }


            VswrDataHandler.getInstance().getConfigData().getFrequencyData().setFreq(startFreq, endFreq);
            VswrDataHandler.getInstance().getConfigData().getFrequencyData().checkFreqRange();
            ViewHandler.getInstance().getFreqDistView().update();
            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    VswrDataHandler.getInstance().getVswrParameter()
            );

        } else if (mode == MeasureMode.MEASURE_MODE.MOD_ACCURACY) {
            MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
            String profile = String.valueOf(arrayList.get(21));

            if (type == MeasureType.MEASURE_TYPE.LTE_FDD) {
                LteFddProfileData.PROFILE profileData = LteFddProfileData.PROFILE.valueOf(profile.toUpperCase());

                LteFddData data = DataHandler.getInstance().getLteFddData();
                data.setCenterFreq(centerFreq);
                data.getProfileData().setProfile(profileData);
            } else if (type == MeasureType.MEASURE_TYPE.NR_5G) {
                NrProfileData.PROFILE profileData = NrProfileData.PROFILE.valueOf(profile.toUpperCase());

                NrData nrData = DataHandler.getInstance().getNrData();
                nrData.getFreqData().setCenterFreq(centerFreq);
                nrData.getProfileData().setProfile(profileData);
                ViewHandler.getInstance().getNrMeasSetupView().update();
            }

            ViewHandler.getInstance().getContent().subInfoUpdate();
            ViewHandler.getInstance().getDemodulationFrequencyView().update();

            FunctionHandler.getInstance().getDataConnector().sendCommand(
                    DataHandler.getInstance().getConfigCmd()
            );

        }
        ViewHandler.getInstance().getSAAmplitudeView().update();
        dismiss();
    }

    //tag:prestAlpha 210617 end

    private void setUpEvents(MotionEvent event, double startFreq,
                             double stopFreq, AutofitTextView textView, UL_DL_STATUS status) {

        InitActivity.logMsg("PresetDialog", "setUpEvents : " + status.name());

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                textView.setBackgroundColor(Color.BLACK);
                textView.setTextColor(Color.WHITE);
                break;

            case MotionEvent.ACTION_UP:

                MeasureMode.MEASURE_MODE mode = FunctionHandler.getInstance().getMeasureMode().getMode();
                double centerFreq;

                switch (mode) {

                    case VSWR:
                    case DTF:
                    case CL:
                        VswrDataHandler.getInstance().getConfigData().getFrequencyData().setFreq(startFreq, stopFreq);
                        VswrDataHandler.getInstance().getConfigData().getFrequencyData().checkFreqRange();
                        ViewHandler.getInstance().getFreqDistView().update();
                        FunctionHandler.getInstance().getDataConnector().sendCommand(
                                VswrDataHandler.getInstance().getVswrParameter()
                        );
                        break;

                    case SA:
                        centerFreq = (startFreq + stopFreq) / 2;
                        SaDataHandler.getInstance().getConfigData().getFrequencyData().updatePrevFreq();
                        SaDataHandler.getInstance().getConfigData().getFrequencyData().setCenterFreq(centerFreq);
                        SaDataHandler.getInstance().getConfigData().getFrequencyData().checkFreqRange();
//                        FunctionHandler.getInstance().getMainLineChart().setCenterFreq(centerFreq);

                        //Clock
                        if (centerFreq == 10d) {
                            DataHandler.getInstance().getStandardLoadData().loadClockData();
                            ViewHandler.getInstance().getSAView().update();

                        }

                        //IF
                        else if (centerFreq == 75d) {

                            DataHandler.getInstance().getStandardLoadData().loadIF75MHzData();
                            ViewHandler.getInstance().getSAView().update();


                        } else if (centerFreq == 130d) {

                            DataHandler.getInstance().getStandardLoadData().loadIF130MHzData();
                            ViewHandler.getInstance().getSAView().update();


                        }

                        //WCDMA
                        else if (centerFreq == 2142.6d) {
                            DataHandler.getInstance().getStandardLoadData().loadWcdmaData();
                        } else if (centerFreq == 1952.6d) {
                            DataHandler.getInstance().getStandardLoadData().loadWcdmaData();
                        }

                        //LTE
                        else if (centerFreq == 879d || centerFreq == 834d) {
                            DataHandler.getInstance().getStandardLoadData().load10MHzDataForLte();
                        } else if (centerFreq == 1820d || centerFreq == 1725d) {
                            DataHandler.getInstance().getStandardLoadData().load20MHzDataForLte();
                        } else if (centerFreq == 2135d || centerFreq == 1945d) {
                            DataHandler.getInstance().getStandardLoadData().load10MHzDataForLte();
                        } else if (centerFreq == 2630d || centerFreq == 2510d) {
                            DataHandler.getInstance().getStandardLoadData().load20MHzDataForLte();
                        } else if (centerFreq == 2665d || centerFreq == 2545d) {
                            DataHandler.getInstance().getStandardLoadData().load10MHzDataForLte();
                        } else if (centerFreq == 3650.01d) {
                            if (status == UL_DL_STATUS.DL) {
                                DataHandler.getInstance().getStandardLoadData().load100MHzDataFor5G(UL_DL_STATUS.DL);
                                InitActivity.logMsg("5G100MHz", "DL-");
                            } else if (status == UL_DL_STATUS.UL) {
                                DataHandler.getInstance().getStandardLoadData().load100MHzDataFor5G(UL_DL_STATUS.UL);
                                InitActivity.logMsg("5G100MHz", "UL-");
                            }
                        }
                        ViewHandler.getInstance().getContent().subInfoUpdate();
                        ViewHandler.getInstance().getSaFrequencyView().updateFreq();
                        ViewHandler.getInstance().getSASpanView().updateSpan();

                        FunctionHandler.getInstance().getDataConnector().sendCommand(
                                SaDataHandler.getInstance().getConfigData().getSaParameter()
                        );

                        break;

                    case MOD_ACCURACY:

                        centerFreq = (startFreq + stopFreq) / 2;

                        MeasureType.MEASURE_TYPE type = FunctionHandler.getInstance().getMeasureType().getType();
                        LteFddData data = DataHandler.getInstance().getLteFddData();

                        if (type == MeasureType.MEASURE_TYPE.LTE_FDD) {
                            //LTE

                            if (centerFreq == 879d || centerFreq == 834d) {
                                data.setCenterFreq(centerFreq);
                                data.getProfileData().setProfile(LteFddProfileData.PROFILE.MHZ10);
                            } else if (centerFreq == 1820d || centerFreq == 1725d) {
                                data.setCenterFreq(centerFreq);
                                data.getProfileData().setProfile(LteFddProfileData.PROFILE.MHZ20);
                            } else if (centerFreq == 2135d || centerFreq == 1945d) {
                                data.setCenterFreq(centerFreq);
                                data.getProfileData().setProfile(LteFddProfileData.PROFILE.MHZ10);
                            } else if (centerFreq == 2630d || centerFreq == 2510d) {
                                data.setCenterFreq(centerFreq);
                                data.getProfileData().setProfile(LteFddProfileData.PROFILE.MHZ20);
                            } else if (centerFreq == 2665d || centerFreq == 2545d) {
                                data.setCenterFreq(centerFreq);
                                data.getProfileData().setProfile(LteFddProfileData.PROFILE.MHZ10);
                            }
                        } else if (type == MeasureType.MEASURE_TYPE.NR_5G) {
                            if (centerFreq == 3650.01d) {
                                DataHandler.getInstance().getNrData().getFreqData().setCenterFreq(centerFreq);
                                DataHandler.getInstance().getNrData().getProfileData().setProfile(NrProfileData.PROFILE.MHZ100);
                                DataHandler.getInstance().getNrData().getSsbInfoData().setConfigMode(NrSSBInfoData.CONFIG_MODE.AUTO);
                                ViewHandler.getInstance().getNrMeasSetupView().update();
                            }

                        }

                        ViewHandler.getInstance().getContent().subInfoUpdate();
                        ViewHandler.getInstance().getDemodulationFrequencyView().update();

                        FunctionHandler.getInstance().getDataConnector().sendCommand(
                                DataHandler.getInstance().getConfigCmd()
                        );

                        break;
                }

                textView.setTextColor(Color.BLACK);
                ViewHandler.getInstance().getSAAmplitudeView().update();
                dismiss();


                break;

        }

    }


    private LinearLayout getDivisionLine() {

        LinearLayout linDivision = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0.1f);// 0.06f);
        linDivision.setLayoutParams(params);
        linDivision.setGravity(Gravity.CENTER);

        View line = new View(mContext);
//        line.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) convertDpToPixel(2, mContext));
        line.setLayoutParams(lineParams);

        linDivision.addView(line);

        return linDivision;

    }

    private View getDivisionViewLine(float weight) {

        View line = new View(mContext);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, weight);
        line.setLayoutParams(lineParams);
        return line;
    }

    private View getDivisionButtonLine(float weight) {

        View line = new View(mContext);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight);
        line.setLayoutParams(lineParams);
        return line;
    }

    private AutofitTextView getTextView(int textSize, float weight) {

        AutofitTextView tvName = new AutofitTextView(mContext);
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, weight);
        tvName.setLayoutParams(nameParams);
        tvName.setGravity(Gravity.CENTER);
        tvName.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, 1);
        tvName.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(textSize, mContext));
        tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, convertDpToPixel(textSize, mContext));
        tvName.setTypeface(Typeface.DEFAULT_BOLD);
        tvName.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        tvName.setBackgroundResource(R.drawable.preset_selector);
        tvName.setMaxLines(2);
        return tvName;
    }

    private LinearLayout getLinParent(float weight) {

        LinearLayout linParent = new LinearLayout(mContext);
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight);
        linParent.setOrientation(LinearLayout.VERTICAL);
        linParent.setLayoutParams(nameParams);
        linParent.setWeightSum(1);

        return linParent;
    }

    private boolean loadIniFileInAsset() {
        AssetManager aMgr = MainActivity.getActivity().getResources().getAssets();
        InputStream is = null;
        try {
            is = aMgr.open(PRESET_INI);

            ini = new Ini(is);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean loadIniFileInLocal() {

        File mRootFolder = new File(mPath, mDirectoryName);
        if (!mRootFolder.exists()) {
            mRootFolder.mkdirs();
        }

        if (mRootFolder == null || mRootFolder.listFiles() == null || mLoadPreset == 0)
            return false;
        for (File file : mRootFolder.listFiles()) {

            if (file.getName().equals(PRESET_INI)) {

                try {
                    InputStream is = new FileInputStream(file);
                    ini = new Ini(is);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (InvalidFileFormatException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;

            }

        }

        return false;

    }

    private void copyPresetListFile() {

        AssetManager aMgr = MainActivity.getActivity().getResources().getAssets();
        InputStream is = null;
        FileOutputStream fos = null;
        String path = mPath + File.separator + mDirectoryName + File.separator + PRESET_INI;

        try {
            is = aMgr.open(PRESET_INI);
            int size = is.available();
            byte[] buf = new byte[size];
            File file = new File(path);
            fos = new FileOutputStream(file);
            for (int c = is.read(buf); c != -1; c = is.read(buf)) {
                fos.write(buf, 0, c);
            }

            is.close();
            fos.close();
//            convertInputStreamToFile(is, mPath + File.separator + mDirectoryName + File.separator + "cable_list.ini");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        dismiss();
    }

    public boolean selectBtn(AutofitTextView parent) {

        if (parent.getTag().equals("select")) return false;

        parent.setTag("select");
        parent.setTextColor(Color.WHITE);
        parent.setBackgroundResource(R.drawable.preset_select_button_background);

        return true;

    }

    public PresetDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}

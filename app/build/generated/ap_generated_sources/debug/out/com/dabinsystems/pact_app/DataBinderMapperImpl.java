package com.dabinsystems.pact_app;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.dabinsystems.pact_app.databinding.AclrLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.AclrLayoutOrgBindingImpl;
import com.dabinsystems.pact_app.databinding.ActivityMainBindingImpl;
import com.dabinsystems.pact_app.databinding.ActivitySplashComboBindingImpl;
import com.dabinsystems.pact_app.databinding.ActivitySplashPactBindingImpl;
import com.dabinsystems.pact_app.databinding.AttenKeypadBindingImpl;
import com.dabinsystems.pact_app.databinding.AutoMeasuringLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.AvgHoldKeypadBindingImpl;
import com.dabinsystems.pact_app.databinding.BatteryWarningDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.CableListItemBindingImpl;
import com.dabinsystems.pact_app.databinding.CableLossInputDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.CablePropVelocityDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.CalibrationDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.ChPowerLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.ChangeIpDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.CustomContinuousMarkerBindingImpl;
import com.dabinsystems.pact_app.databinding.CustomLeftArrowBindingImpl;
import com.dabinsystems.pact_app.databinding.CustomRightArrowBindingImpl;
import com.dabinsystems.pact_app.databinding.DbKeypadBindingImpl;
import com.dabinsystems.pact_app.databinding.DefaultInputKeypadBindingImpl;
import com.dabinsystems.pact_app.databinding.DemodulationLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.DistanceKeypadBindingImpl;
import com.dabinsystems.pact_app.databinding.ExcelFileListBindingImpl;
import com.dabinsystems.pact_app.databinding.ExcelFileListItemBindingImpl;
import com.dabinsystems.pact_app.databinding.FileRecallDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.FileSaveDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.FreqKeypadBindingImpl;
import com.dabinsystems.pact_app.databinding.FreqSetKeypadBindingImpl;
import com.dabinsystems.pact_app.databinding.FwUpdateDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.GateLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.GpsInfoDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.ImageViewBindingImpl;
import com.dabinsystems.pact_app.databinding.IntegerInputKeypadBindingImpl;
import com.dabinsystems.pact_app.databinding.InterTaeLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.InterferenceHuntingLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.IntraTaeLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.Layout5gNrScanBindingImpl;
import com.dabinsystems.pact_app.databinding.LayoutLoadingDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.LimitMsgLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.LineChartLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.LoadingDefaultBindingImpl;
import com.dabinsystems.pact_app.databinding.LoadingMsgLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.LoadingTrackingHoldoverBindingImpl;
import com.dabinsystems.pact_app.databinding.LteFddLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.MacroDialogLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.MacroLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.MarkerTableLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.ObwKeypadBindingImpl;
import com.dabinsystems.pact_app.databinding.OccBwLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.PresetDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.RecallMsgLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.SaveFileListItemBindingImpl;
import com.dabinsystems.pact_app.databinding.ScreenshotDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.SelectNetworkLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.SemLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.SpinnerItemBindingImpl;
import com.dabinsystems.pact_app.databinding.SweepTimeKeypadBindingImpl;
import com.dabinsystems.pact_app.databinding.SweptSaBindingImpl;
import com.dabinsystems.pact_app.databinding.SystemVersionDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.TransmitOnOffLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.UpdateDialogLayoutBindingImpl;
import com.dabinsystems.pact_app.databinding.UpdateFileListItemBindingImpl;
import com.dabinsystems.pact_app.databinding.WarningDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.WifiCardViewBindingImpl;
import com.dabinsystems.pact_app.databinding.WifiDialogBindingImpl;
import com.dabinsystems.pact_app.databinding.XdbKeypadBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACLRLAYOUT = 1;

  private static final int LAYOUT_ACLRLAYOUTORG = 2;

  private static final int LAYOUT_ACTIVITYMAIN = 3;

  private static final int LAYOUT_ACTIVITYSPLASHCOMBO = 4;

  private static final int LAYOUT_ACTIVITYSPLASHPACT = 5;

  private static final int LAYOUT_ATTENKEYPAD = 6;

  private static final int LAYOUT_AUTOMEASURINGLAYOUT = 7;

  private static final int LAYOUT_AVGHOLDKEYPAD = 8;

  private static final int LAYOUT_BATTERYWARNINGDIALOG = 9;

  private static final int LAYOUT_CABLELISTITEM = 10;

  private static final int LAYOUT_CABLELOSSINPUTDIALOG = 11;

  private static final int LAYOUT_CABLEPROPVELOCITYDIALOG = 12;

  private static final int LAYOUT_CALIBRATIONDIALOG = 13;

  private static final int LAYOUT_CHPOWERLAYOUT = 14;

  private static final int LAYOUT_CHANGEIPDIALOG = 15;

  private static final int LAYOUT_CUSTOMCONTINUOUSMARKER = 16;

  private static final int LAYOUT_CUSTOMLEFTARROW = 17;

  private static final int LAYOUT_CUSTOMRIGHTARROW = 18;

  private static final int LAYOUT_DBKEYPAD = 19;

  private static final int LAYOUT_DEFAULTINPUTKEYPAD = 20;

  private static final int LAYOUT_DEMODULATIONLAYOUT = 21;

  private static final int LAYOUT_DISTANCEKEYPAD = 22;

  private static final int LAYOUT_EXCELFILELIST = 23;

  private static final int LAYOUT_EXCELFILELISTITEM = 24;

  private static final int LAYOUT_FILERECALLDIALOG = 25;

  private static final int LAYOUT_FILESAVEDIALOG = 26;

  private static final int LAYOUT_FREQKEYPAD = 27;

  private static final int LAYOUT_FREQSETKEYPAD = 28;

  private static final int LAYOUT_FWUPDATEDIALOG = 29;

  private static final int LAYOUT_GATELAYOUT = 30;

  private static final int LAYOUT_GPSINFODIALOG = 31;

  private static final int LAYOUT_IMAGEVIEW = 32;

  private static final int LAYOUT_INTEGERINPUTKEYPAD = 33;

  private static final int LAYOUT_INTERTAELAYOUT = 34;

  private static final int LAYOUT_INTERFERENCEHUNTINGLAYOUT = 35;

  private static final int LAYOUT_INTRATAELAYOUT = 36;

  private static final int LAYOUT_LAYOUT5GNRSCAN = 37;

  private static final int LAYOUT_LAYOUTLOADINGDIALOG = 38;

  private static final int LAYOUT_LIMITMSGLAYOUT = 39;

  private static final int LAYOUT_LINECHARTLAYOUT = 40;

  private static final int LAYOUT_LOADINGDEFAULT = 41;

  private static final int LAYOUT_LOADINGMSGLAYOUT = 42;

  private static final int LAYOUT_LOADINGTRACKINGHOLDOVER = 43;

  private static final int LAYOUT_LTEFDDLAYOUT = 44;

  private static final int LAYOUT_MACRODIALOGLAYOUT = 45;

  private static final int LAYOUT_MACROLAYOUT = 46;

  private static final int LAYOUT_MARKERTABLELAYOUT = 47;

  private static final int LAYOUT_OBWKEYPAD = 48;

  private static final int LAYOUT_OCCBWLAYOUT = 49;

  private static final int LAYOUT_PRESETDIALOG = 50;

  private static final int LAYOUT_RECALLMSGLAYOUT = 51;

  private static final int LAYOUT_SAVEFILELISTITEM = 52;

  private static final int LAYOUT_SCREENSHOTDIALOG = 53;

  private static final int LAYOUT_SELECTNETWORKLAYOUT = 54;

  private static final int LAYOUT_SEMLAYOUT = 55;

  private static final int LAYOUT_SPINNERITEM = 56;

  private static final int LAYOUT_SWEEPTIMEKEYPAD = 57;

  private static final int LAYOUT_SWEPTSA = 58;

  private static final int LAYOUT_SYSTEMVERSIONDIALOG = 59;

  private static final int LAYOUT_TRANSMITONOFFLAYOUT = 60;

  private static final int LAYOUT_UPDATEDIALOGLAYOUT = 61;

  private static final int LAYOUT_UPDATEFILELISTITEM = 62;

  private static final int LAYOUT_WARNINGDIALOG = 63;

  private static final int LAYOUT_WIFICARDVIEW = 64;

  private static final int LAYOUT_WIFIDIALOG = 65;

  private static final int LAYOUT_XDBKEYPAD = 66;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(66);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.aclr_layout, LAYOUT_ACLRLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.aclr_layout_org, LAYOUT_ACLRLAYOUTORG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.activity_splash_combo, LAYOUT_ACTIVITYSPLASHCOMBO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.activity_splash_pact, LAYOUT_ACTIVITYSPLASHPACT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.atten_keypad, LAYOUT_ATTENKEYPAD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.auto_measuring_layout, LAYOUT_AUTOMEASURINGLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.avg_hold_keypad, LAYOUT_AVGHOLDKEYPAD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.battery_warning_dialog, LAYOUT_BATTERYWARNINGDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.cable_list_item, LAYOUT_CABLELISTITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.cable_loss_input_dialog, LAYOUT_CABLELOSSINPUTDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.cable_prop_velocity_dialog, LAYOUT_CABLEPROPVELOCITYDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.calibration_dialog, LAYOUT_CALIBRATIONDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.ch_power_layout, LAYOUT_CHPOWERLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.change_ip_dialog, LAYOUT_CHANGEIPDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.custom_continuous_marker, LAYOUT_CUSTOMCONTINUOUSMARKER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.custom_left_arrow, LAYOUT_CUSTOMLEFTARROW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.custom_right_arrow, LAYOUT_CUSTOMRIGHTARROW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.db_keypad, LAYOUT_DBKEYPAD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.default_input_keypad, LAYOUT_DEFAULTINPUTKEYPAD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.demodulation_layout, LAYOUT_DEMODULATIONLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.distance_keypad, LAYOUT_DISTANCEKEYPAD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.excel_file_list, LAYOUT_EXCELFILELIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.excel_file_list_item, LAYOUT_EXCELFILELISTITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.file_recall_dialog, LAYOUT_FILERECALLDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.file_save_dialog, LAYOUT_FILESAVEDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.freq_keypad, LAYOUT_FREQKEYPAD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.freq_set_keypad, LAYOUT_FREQSETKEYPAD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.fw_update_dialog, LAYOUT_FWUPDATEDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.gate_layout, LAYOUT_GATELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.gps_info_dialog, LAYOUT_GPSINFODIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.image_view, LAYOUT_IMAGEVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.integer_input_keypad, LAYOUT_INTEGERINPUTKEYPAD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.inter_tae_layout, LAYOUT_INTERTAELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.interference_hunting_layout, LAYOUT_INTERFERENCEHUNTINGLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.intra_tae_layout, LAYOUT_INTRATAELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.layout_5g_nr_scan, LAYOUT_LAYOUT5GNRSCAN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.layout_loading_dialog, LAYOUT_LAYOUTLOADINGDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.limit_msg_layout, LAYOUT_LIMITMSGLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.line_chart_layout, LAYOUT_LINECHARTLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.loading_default, LAYOUT_LOADINGDEFAULT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.loading_msg_layout, LAYOUT_LOADINGMSGLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.loading_tracking_holdover, LAYOUT_LOADINGTRACKINGHOLDOVER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.lte_fdd_layout, LAYOUT_LTEFDDLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.macro_dialog_layout, LAYOUT_MACRODIALOGLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.macro_layout, LAYOUT_MACROLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.marker_table_layout, LAYOUT_MARKERTABLELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.obw_keypad, LAYOUT_OBWKEYPAD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.occ_bw_layout, LAYOUT_OCCBWLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.preset_dialog, LAYOUT_PRESETDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.recall_msg_layout, LAYOUT_RECALLMSGLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.save_file_list_item, LAYOUT_SAVEFILELISTITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.screenshot_dialog, LAYOUT_SCREENSHOTDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.select_network_layout, LAYOUT_SELECTNETWORKLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.sem_layout, LAYOUT_SEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.spinner_item, LAYOUT_SPINNERITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.sweep_time_keypad, LAYOUT_SWEEPTIMEKEYPAD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.swept_sa, LAYOUT_SWEPTSA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.system_version_dialog, LAYOUT_SYSTEMVERSIONDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.transmit_on_off_layout, LAYOUT_TRANSMITONOFFLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.update_dialog_layout, LAYOUT_UPDATEDIALOGLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.update_file_list_item, LAYOUT_UPDATEFILELISTITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.warning_dialog, LAYOUT_WARNINGDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.wifi_card_view, LAYOUT_WIFICARDVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.wifi_dialog, LAYOUT_WIFIDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.dabinsystems.pact_app.R.layout.xdb_keypad, LAYOUT_XDBKEYPAD);
  }

  private final ViewDataBinding internalGetViewDataBinding0(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_ACLRLAYOUT: {
        if ("layout/aclr_layout_0".equals(tag)) {
          return new AclrLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for aclr_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_ACLRLAYOUTORG: {
        if ("layout/aclr_layout_org_0".equals(tag)) {
          return new AclrLayoutOrgBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for aclr_layout_org is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYMAIN: {
        if ("layout/activity_main_0".equals(tag)) {
          return new ActivityMainBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSPLASHCOMBO: {
        if ("layout/activity_splash_combo_0".equals(tag)) {
          return new ActivitySplashComboBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_splash_combo is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSPLASHPACT: {
        if ("layout/activity_splash_pact_0".equals(tag)) {
          return new ActivitySplashPactBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_splash_pact is invalid. Received: " + tag);
      }
      case  LAYOUT_ATTENKEYPAD: {
        if ("layout/atten_keypad_0".equals(tag)) {
          return new AttenKeypadBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for atten_keypad is invalid. Received: " + tag);
      }
      case  LAYOUT_AUTOMEASURINGLAYOUT: {
        if ("layout/auto_measuring_layout_0".equals(tag)) {
          return new AutoMeasuringLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for auto_measuring_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_AVGHOLDKEYPAD: {
        if ("layout/avg_hold_keypad_0".equals(tag)) {
          return new AvgHoldKeypadBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for avg_hold_keypad is invalid. Received: " + tag);
      }
      case  LAYOUT_BATTERYWARNINGDIALOG: {
        if ("layout/battery_warning_dialog_0".equals(tag)) {
          return new BatteryWarningDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for battery_warning_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_CABLELISTITEM: {
        if ("layout/cable_list_item_0".equals(tag)) {
          return new CableListItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for cable_list_item is invalid. Received: " + tag);
      }
      case  LAYOUT_CABLELOSSINPUTDIALOG: {
        if ("layout/cable_loss_input_dialog_0".equals(tag)) {
          return new CableLossInputDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for cable_loss_input_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_CABLEPROPVELOCITYDIALOG: {
        if ("layout/cable_prop_velocity_dialog_0".equals(tag)) {
          return new CablePropVelocityDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for cable_prop_velocity_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_CALIBRATIONDIALOG: {
        if ("layout/calibration_dialog_0".equals(tag)) {
          return new CalibrationDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for calibration_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_CHPOWERLAYOUT: {
        if ("layout/ch_power_layout_0".equals(tag)) {
          return new ChPowerLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for ch_power_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_CHANGEIPDIALOG: {
        if ("layout/change_ip_dialog_0".equals(tag)) {
          return new ChangeIpDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for change_ip_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_CUSTOMCONTINUOUSMARKER: {
        if ("layout/custom_continuous_marker_0".equals(tag)) {
          return new CustomContinuousMarkerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for custom_continuous_marker is invalid. Received: " + tag);
      }
      case  LAYOUT_CUSTOMLEFTARROW: {
        if ("layout/custom_left_arrow_0".equals(tag)) {
          return new CustomLeftArrowBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for custom_left_arrow is invalid. Received: " + tag);
      }
      case  LAYOUT_CUSTOMRIGHTARROW: {
        if ("layout/custom_right_arrow_0".equals(tag)) {
          return new CustomRightArrowBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for custom_right_arrow is invalid. Received: " + tag);
      }
      case  LAYOUT_DBKEYPAD: {
        if ("layout/db_keypad_0".equals(tag)) {
          return new DbKeypadBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for db_keypad is invalid. Received: " + tag);
      }
      case  LAYOUT_DEFAULTINPUTKEYPAD: {
        if ("layout/default_input_keypad_0".equals(tag)) {
          return new DefaultInputKeypadBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for default_input_keypad is invalid. Received: " + tag);
      }
      case  LAYOUT_DEMODULATIONLAYOUT: {
        if ("layout/demodulation_layout_0".equals(tag)) {
          return new DemodulationLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for demodulation_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_DISTANCEKEYPAD: {
        if ("layout/distance_keypad_0".equals(tag)) {
          return new DistanceKeypadBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for distance_keypad is invalid. Received: " + tag);
      }
      case  LAYOUT_EXCELFILELIST: {
        if ("layout/excel_file_list_0".equals(tag)) {
          return new ExcelFileListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for excel_file_list is invalid. Received: " + tag);
      }
      case  LAYOUT_EXCELFILELISTITEM: {
        if ("layout/excel_file_list_item_0".equals(tag)) {
          return new ExcelFileListItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for excel_file_list_item is invalid. Received: " + tag);
      }
      case  LAYOUT_FILERECALLDIALOG: {
        if ("layout/file_recall_dialog_0".equals(tag)) {
          return new FileRecallDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for file_recall_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_FILESAVEDIALOG: {
        if ("layout/file_save_dialog_0".equals(tag)) {
          return new FileSaveDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for file_save_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_FREQKEYPAD: {
        if ("layout/freq_keypad_0".equals(tag)) {
          return new FreqKeypadBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for freq_keypad is invalid. Received: " + tag);
      }
      case  LAYOUT_FREQSETKEYPAD: {
        if ("layout/freq_set_keypad_0".equals(tag)) {
          return new FreqSetKeypadBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for freq_set_keypad is invalid. Received: " + tag);
      }
      case  LAYOUT_FWUPDATEDIALOG: {
        if ("layout/fw_update_dialog_0".equals(tag)) {
          return new FwUpdateDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fw_update_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_GATELAYOUT: {
        if ("layout/gate_layout_0".equals(tag)) {
          return new GateLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for gate_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_GPSINFODIALOG: {
        if ("layout/gps_info_dialog_0".equals(tag)) {
          return new GpsInfoDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for gps_info_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_IMAGEVIEW: {
        if ("layout/image_view_0".equals(tag)) {
          return new ImageViewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for image_view is invalid. Received: " + tag);
      }
      case  LAYOUT_INTEGERINPUTKEYPAD: {
        if ("layout/integer_input_keypad_0".equals(tag)) {
          return new IntegerInputKeypadBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for integer_input_keypad is invalid. Received: " + tag);
      }
      case  LAYOUT_INTERTAELAYOUT: {
        if ("layout/inter_tae_layout_0".equals(tag)) {
          return new InterTaeLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for inter_tae_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_INTERFERENCEHUNTINGLAYOUT: {
        if ("layout/interference_hunting_layout_0".equals(tag)) {
          return new InterferenceHuntingLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for interference_hunting_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_INTRATAELAYOUT: {
        if ("layout/intra_tae_layout_0".equals(tag)) {
          return new IntraTaeLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for intra_tae_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUT5GNRSCAN: {
        if ("layout/layout_5g_nr_scan_0".equals(tag)) {
          return new Layout5gNrScanBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_5g_nr_scan is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTLOADINGDIALOG: {
        if ("layout/layout_loading_dialog_0".equals(tag)) {
          return new LayoutLoadingDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_loading_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_LIMITMSGLAYOUT: {
        if ("layout/limit_msg_layout_0".equals(tag)) {
          return new LimitMsgLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for limit_msg_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_LINECHARTLAYOUT: {
        if ("layout/line_chart_layout_0".equals(tag)) {
          return new LineChartLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for line_chart_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_LOADINGDEFAULT: {
        if ("layout/loading_default_0".equals(tag)) {
          return new LoadingDefaultBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for loading_default is invalid. Received: " + tag);
      }
      case  LAYOUT_LOADINGMSGLAYOUT: {
        if ("layout/loading_msg_layout_0".equals(tag)) {
          return new LoadingMsgLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for loading_msg_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_LOADINGTRACKINGHOLDOVER: {
        if ("layout/loading_tracking_holdover_0".equals(tag)) {
          return new LoadingTrackingHoldoverBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for loading_tracking_holdover is invalid. Received: " + tag);
      }
      case  LAYOUT_LTEFDDLAYOUT: {
        if ("layout/lte_fdd_layout_0".equals(tag)) {
          return new LteFddLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for lte_fdd_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_MACRODIALOGLAYOUT: {
        if ("layout/macro_dialog_layout_0".equals(tag)) {
          return new MacroDialogLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for macro_dialog_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_MACROLAYOUT: {
        if ("layout/macro_layout_0".equals(tag)) {
          return new MacroLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for macro_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_MARKERTABLELAYOUT: {
        if ("layout/marker_table_layout_0".equals(tag)) {
          return new MarkerTableLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for marker_table_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_OBWKEYPAD: {
        if ("layout/obw_keypad_0".equals(tag)) {
          return new ObwKeypadBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for obw_keypad is invalid. Received: " + tag);
      }
      case  LAYOUT_OCCBWLAYOUT: {
        if ("layout/occ_bw_layout_0".equals(tag)) {
          return new OccBwLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for occ_bw_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_PRESETDIALOG: {
        if ("layout/preset_dialog_0".equals(tag)) {
          return new PresetDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for preset_dialog is invalid. Received: " + tag);
      }
    }
    return null;
  }

  private final ViewDataBinding internalGetViewDataBinding1(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_RECALLMSGLAYOUT: {
        if ("layout/recall_msg_layout_0".equals(tag)) {
          return new RecallMsgLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for recall_msg_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_SAVEFILELISTITEM: {
        if ("layout/save_file_list_item_0".equals(tag)) {
          return new SaveFileListItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for save_file_list_item is invalid. Received: " + tag);
      }
      case  LAYOUT_SCREENSHOTDIALOG: {
        if ("layout/screenshot_dialog_0".equals(tag)) {
          return new ScreenshotDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for screenshot_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_SELECTNETWORKLAYOUT: {
        if ("layout/select_network_layout_0".equals(tag)) {
          return new SelectNetworkLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for select_network_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_SEMLAYOUT: {
        if ("layout/sem_layout_0".equals(tag)) {
          return new SemLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for sem_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_SPINNERITEM: {
        if ("layout/spinner_item_0".equals(tag)) {
          return new SpinnerItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for spinner_item is invalid. Received: " + tag);
      }
      case  LAYOUT_SWEEPTIMEKEYPAD: {
        if ("layout/sweep_time_keypad_0".equals(tag)) {
          return new SweepTimeKeypadBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for sweep_time_keypad is invalid. Received: " + tag);
      }
      case  LAYOUT_SWEPTSA: {
        if ("layout/swept_sa_0".equals(tag)) {
          return new SweptSaBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for swept_sa is invalid. Received: " + tag);
      }
      case  LAYOUT_SYSTEMVERSIONDIALOG: {
        if ("layout/system_version_dialog_0".equals(tag)) {
          return new SystemVersionDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for system_version_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_TRANSMITONOFFLAYOUT: {
        if ("layout/transmit_on_off_layout_0".equals(tag)) {
          return new TransmitOnOffLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for transmit_on_off_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_UPDATEDIALOGLAYOUT: {
        if ("layout/update_dialog_layout_0".equals(tag)) {
          return new UpdateDialogLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for update_dialog_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_UPDATEFILELISTITEM: {
        if ("layout/update_file_list_item_0".equals(tag)) {
          return new UpdateFileListItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for update_file_list_item is invalid. Received: " + tag);
      }
      case  LAYOUT_WARNINGDIALOG: {
        if ("layout/warning_dialog_0".equals(tag)) {
          return new WarningDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for warning_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_WIFICARDVIEW: {
        if ("layout/wifi_card_view_0".equals(tag)) {
          return new WifiCardViewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for wifi_card_view is invalid. Received: " + tag);
      }
      case  LAYOUT_WIFIDIALOG: {
        if ("layout/wifi_dialog_0".equals(tag)) {
          return new WifiDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for wifi_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_XDBKEYPAD: {
        if ("layout/xdb_keypad_0".equals(tag)) {
          return new XdbKeypadBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for xdb_keypad is invalid. Received: " + tag);
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      // find which method will have it. -1 is necessary becausefirst id starts with 1;
      int methodIndex = (localizedLayoutId - 1) / 50;
      switch(methodIndex) {
        case 0: {
          return internalGetViewDataBinding0(component, view, localizedLayoutId, tag);
        }
        case 1: {
          return internalGetViewDataBinding1(component, view, localizedLayoutId, tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(66);

    static {
      sKeys.put("layout/aclr_layout_0", com.dabinsystems.pact_app.R.layout.aclr_layout);
      sKeys.put("layout/aclr_layout_org_0", com.dabinsystems.pact_app.R.layout.aclr_layout_org);
      sKeys.put("layout/activity_main_0", com.dabinsystems.pact_app.R.layout.activity_main);
      sKeys.put("layout/activity_splash_combo_0", com.dabinsystems.pact_app.R.layout.activity_splash_combo);
      sKeys.put("layout/activity_splash_pact_0", com.dabinsystems.pact_app.R.layout.activity_splash_pact);
      sKeys.put("layout/atten_keypad_0", com.dabinsystems.pact_app.R.layout.atten_keypad);
      sKeys.put("layout/auto_measuring_layout_0", com.dabinsystems.pact_app.R.layout.auto_measuring_layout);
      sKeys.put("layout/avg_hold_keypad_0", com.dabinsystems.pact_app.R.layout.avg_hold_keypad);
      sKeys.put("layout/battery_warning_dialog_0", com.dabinsystems.pact_app.R.layout.battery_warning_dialog);
      sKeys.put("layout/cable_list_item_0", com.dabinsystems.pact_app.R.layout.cable_list_item);
      sKeys.put("layout/cable_loss_input_dialog_0", com.dabinsystems.pact_app.R.layout.cable_loss_input_dialog);
      sKeys.put("layout/cable_prop_velocity_dialog_0", com.dabinsystems.pact_app.R.layout.cable_prop_velocity_dialog);
      sKeys.put("layout/calibration_dialog_0", com.dabinsystems.pact_app.R.layout.calibration_dialog);
      sKeys.put("layout/ch_power_layout_0", com.dabinsystems.pact_app.R.layout.ch_power_layout);
      sKeys.put("layout/change_ip_dialog_0", com.dabinsystems.pact_app.R.layout.change_ip_dialog);
      sKeys.put("layout/custom_continuous_marker_0", com.dabinsystems.pact_app.R.layout.custom_continuous_marker);
      sKeys.put("layout/custom_left_arrow_0", com.dabinsystems.pact_app.R.layout.custom_left_arrow);
      sKeys.put("layout/custom_right_arrow_0", com.dabinsystems.pact_app.R.layout.custom_right_arrow);
      sKeys.put("layout/db_keypad_0", com.dabinsystems.pact_app.R.layout.db_keypad);
      sKeys.put("layout/default_input_keypad_0", com.dabinsystems.pact_app.R.layout.default_input_keypad);
      sKeys.put("layout/demodulation_layout_0", com.dabinsystems.pact_app.R.layout.demodulation_layout);
      sKeys.put("layout/distance_keypad_0", com.dabinsystems.pact_app.R.layout.distance_keypad);
      sKeys.put("layout/excel_file_list_0", com.dabinsystems.pact_app.R.layout.excel_file_list);
      sKeys.put("layout/excel_file_list_item_0", com.dabinsystems.pact_app.R.layout.excel_file_list_item);
      sKeys.put("layout/file_recall_dialog_0", com.dabinsystems.pact_app.R.layout.file_recall_dialog);
      sKeys.put("layout/file_save_dialog_0", com.dabinsystems.pact_app.R.layout.file_save_dialog);
      sKeys.put("layout/freq_keypad_0", com.dabinsystems.pact_app.R.layout.freq_keypad);
      sKeys.put("layout/freq_set_keypad_0", com.dabinsystems.pact_app.R.layout.freq_set_keypad);
      sKeys.put("layout/fw_update_dialog_0", com.dabinsystems.pact_app.R.layout.fw_update_dialog);
      sKeys.put("layout/gate_layout_0", com.dabinsystems.pact_app.R.layout.gate_layout);
      sKeys.put("layout/gps_info_dialog_0", com.dabinsystems.pact_app.R.layout.gps_info_dialog);
      sKeys.put("layout/image_view_0", com.dabinsystems.pact_app.R.layout.image_view);
      sKeys.put("layout/integer_input_keypad_0", com.dabinsystems.pact_app.R.layout.integer_input_keypad);
      sKeys.put("layout/inter_tae_layout_0", com.dabinsystems.pact_app.R.layout.inter_tae_layout);
      sKeys.put("layout/interference_hunting_layout_0", com.dabinsystems.pact_app.R.layout.interference_hunting_layout);
      sKeys.put("layout/intra_tae_layout_0", com.dabinsystems.pact_app.R.layout.intra_tae_layout);
      sKeys.put("layout/layout_5g_nr_scan_0", com.dabinsystems.pact_app.R.layout.layout_5g_nr_scan);
      sKeys.put("layout/layout_loading_dialog_0", com.dabinsystems.pact_app.R.layout.layout_loading_dialog);
      sKeys.put("layout/limit_msg_layout_0", com.dabinsystems.pact_app.R.layout.limit_msg_layout);
      sKeys.put("layout/line_chart_layout_0", com.dabinsystems.pact_app.R.layout.line_chart_layout);
      sKeys.put("layout/loading_default_0", com.dabinsystems.pact_app.R.layout.loading_default);
      sKeys.put("layout/loading_msg_layout_0", com.dabinsystems.pact_app.R.layout.loading_msg_layout);
      sKeys.put("layout/loading_tracking_holdover_0", com.dabinsystems.pact_app.R.layout.loading_tracking_holdover);
      sKeys.put("layout/lte_fdd_layout_0", com.dabinsystems.pact_app.R.layout.lte_fdd_layout);
      sKeys.put("layout/macro_dialog_layout_0", com.dabinsystems.pact_app.R.layout.macro_dialog_layout);
      sKeys.put("layout/macro_layout_0", com.dabinsystems.pact_app.R.layout.macro_layout);
      sKeys.put("layout/marker_table_layout_0", com.dabinsystems.pact_app.R.layout.marker_table_layout);
      sKeys.put("layout/obw_keypad_0", com.dabinsystems.pact_app.R.layout.obw_keypad);
      sKeys.put("layout/occ_bw_layout_0", com.dabinsystems.pact_app.R.layout.occ_bw_layout);
      sKeys.put("layout/preset_dialog_0", com.dabinsystems.pact_app.R.layout.preset_dialog);
      sKeys.put("layout/recall_msg_layout_0", com.dabinsystems.pact_app.R.layout.recall_msg_layout);
      sKeys.put("layout/save_file_list_item_0", com.dabinsystems.pact_app.R.layout.save_file_list_item);
      sKeys.put("layout/screenshot_dialog_0", com.dabinsystems.pact_app.R.layout.screenshot_dialog);
      sKeys.put("layout/select_network_layout_0", com.dabinsystems.pact_app.R.layout.select_network_layout);
      sKeys.put("layout/sem_layout_0", com.dabinsystems.pact_app.R.layout.sem_layout);
      sKeys.put("layout/spinner_item_0", com.dabinsystems.pact_app.R.layout.spinner_item);
      sKeys.put("layout/sweep_time_keypad_0", com.dabinsystems.pact_app.R.layout.sweep_time_keypad);
      sKeys.put("layout/swept_sa_0", com.dabinsystems.pact_app.R.layout.swept_sa);
      sKeys.put("layout/system_version_dialog_0", com.dabinsystems.pact_app.R.layout.system_version_dialog);
      sKeys.put("layout/transmit_on_off_layout_0", com.dabinsystems.pact_app.R.layout.transmit_on_off_layout);
      sKeys.put("layout/update_dialog_layout_0", com.dabinsystems.pact_app.R.layout.update_dialog_layout);
      sKeys.put("layout/update_file_list_item_0", com.dabinsystems.pact_app.R.layout.update_file_list_item);
      sKeys.put("layout/warning_dialog_0", com.dabinsystems.pact_app.R.layout.warning_dialog);
      sKeys.put("layout/wifi_card_view_0", com.dabinsystems.pact_app.R.layout.wifi_card_view);
      sKeys.put("layout/wifi_dialog_0", com.dabinsystems.pact_app.R.layout.wifi_dialog);
      sKeys.put("layout/xdb_keypad_0", com.dabinsystems.pact_app.R.layout.xdb_keypad);
    }
  }
}

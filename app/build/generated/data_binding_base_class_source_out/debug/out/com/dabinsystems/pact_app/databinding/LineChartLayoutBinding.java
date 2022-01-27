// Generated by data binding compiler. Do not edit!
package com.dabinsystems.pact_app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.dabinsystems.pact_app.R;
import com.github.mikephil.charting.charts.LineChart;
import java.lang.Deprecated;
import java.lang.Object;
import me.grantland.widget.AutofitTextView;

public abstract class LineChartLayoutBinding extends ViewDataBinding {
  @NonNull
  public final AclrLayoutBinding aclrInfo;

  @NonNull
  public final ChPowerLayoutBinding channelPowerLayout;

  @NonNull
  public final ConstraintLayout conChannelPower;

  @NonNull
  public final ConstraintLayout conChartInfo;

  @NonNull
  public final ConstraintLayout conInterferenceInfo;

  @NonNull
  public final ConstraintLayout conMarkerTableParent;

  @NonNull
  public final ConstraintLayout conOccupiedBW;

  @NonNull
  public final ConstraintLayout conSpuriousInfoParent;

  @NonNull
  public final GateLayoutBinding gateLayout;

  @NonNull
  public final InterferenceHuntingLayoutBinding interferenceInfo;

  @NonNull
  public final LinearLayout linAclrInfo;

  @NonNull
  public final LinearLayout linChartParent;

  @NonNull
  public final LinearLayout linGateView;

  @NonNull
  public final LinearLayout linSemInfo;

  @NonNull
  public final LinearLayout linTransmitOnOffInfo;

  @NonNull
  public final LineChart mainLineChart;

  @NonNull
  public final MarkerTableLayoutBinding markerTableLayout;

  @NonNull
  public final OccBwLayoutBinding occBwLayout;

  @NonNull
  public final ConstraintLayout parent;

  @NonNull
  public final SemLayoutBinding semLayout;

  @NonNull
  public final View spuriousEmission;

  @NonNull
  public final SweptSaBinding sweptSaInfo;

  @NonNull
  public final TransmitOnOffLayoutBinding transmitOnOffLayout;

  @NonNull
  public final AutofitTextView tvChartInfo;

  @NonNull
  public final AutofitTextView tvDBM;

  protected LineChartLayoutBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AclrLayoutBinding aclrInfo, ChPowerLayoutBinding channelPowerLayout,
      ConstraintLayout conChannelPower, ConstraintLayout conChartInfo,
      ConstraintLayout conInterferenceInfo, ConstraintLayout conMarkerTableParent,
      ConstraintLayout conOccupiedBW, ConstraintLayout conSpuriousInfoParent,
      GateLayoutBinding gateLayout, InterferenceHuntingLayoutBinding interferenceInfo,
      LinearLayout linAclrInfo, LinearLayout linChartParent, LinearLayout linGateView,
      LinearLayout linSemInfo, LinearLayout linTransmitOnOffInfo, LineChart mainLineChart,
      MarkerTableLayoutBinding markerTableLayout, OccBwLayoutBinding occBwLayout,
      ConstraintLayout parent, SemLayoutBinding semLayout, View spuriousEmission,
      SweptSaBinding sweptSaInfo, TransmitOnOffLayoutBinding transmitOnOffLayout,
      AutofitTextView tvChartInfo, AutofitTextView tvDBM) {
    super(_bindingComponent, _root, _localFieldCount);
    this.aclrInfo = aclrInfo;
    this.channelPowerLayout = channelPowerLayout;
    this.conChannelPower = conChannelPower;
    this.conChartInfo = conChartInfo;
    this.conInterferenceInfo = conInterferenceInfo;
    this.conMarkerTableParent = conMarkerTableParent;
    this.conOccupiedBW = conOccupiedBW;
    this.conSpuriousInfoParent = conSpuriousInfoParent;
    this.gateLayout = gateLayout;
    this.interferenceInfo = interferenceInfo;
    this.linAclrInfo = linAclrInfo;
    this.linChartParent = linChartParent;
    this.linGateView = linGateView;
    this.linSemInfo = linSemInfo;
    this.linTransmitOnOffInfo = linTransmitOnOffInfo;
    this.mainLineChart = mainLineChart;
    this.markerTableLayout = markerTableLayout;
    this.occBwLayout = occBwLayout;
    this.parent = parent;
    this.semLayout = semLayout;
    this.spuriousEmission = spuriousEmission;
    this.sweptSaInfo = sweptSaInfo;
    this.transmitOnOffLayout = transmitOnOffLayout;
    this.tvChartInfo = tvChartInfo;
    this.tvDBM = tvDBM;
  }

  @NonNull
  public static LineChartLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.line_chart_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LineChartLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LineChartLayoutBinding>inflateInternal(inflater, R.layout.line_chart_layout, root, attachToRoot, component);
  }

  @NonNull
  public static LineChartLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.line_chart_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LineChartLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LineChartLayoutBinding>inflateInternal(inflater, R.layout.line_chart_layout, null, false, component);
  }

  public static LineChartLayoutBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static LineChartLayoutBinding bind(@NonNull View view, @Nullable Object component) {
    return (LineChartLayoutBinding)bind(component, view, R.layout.line_chart_layout);
  }
}

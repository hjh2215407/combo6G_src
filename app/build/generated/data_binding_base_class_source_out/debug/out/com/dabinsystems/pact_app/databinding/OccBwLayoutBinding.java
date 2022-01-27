// Generated by data binding compiler. Do not edit!
package com.dabinsystems.pact_app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.dabinsystems.pact_app.R;
import java.lang.Deprecated;
import java.lang.Object;
import me.grantland.widget.AutofitTextView;

public abstract class OccBwLayoutBinding extends ViewDataBinding {
  @NonNull
  public final AutofitTextView tvOccBWVal;

  @NonNull
  public final AutofitTextView tvTotalPowerVal;

  @NonNull
  public final AutofitTextView tvXDBBWVal;

  @NonNull
  public final AutofitTextView tvXDBVal;

  protected OccBwLayoutBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AutofitTextView tvOccBWVal, AutofitTextView tvTotalPowerVal, AutofitTextView tvXDBBWVal,
      AutofitTextView tvXDBVal) {
    super(_bindingComponent, _root, _localFieldCount);
    this.tvOccBWVal = tvOccBWVal;
    this.tvTotalPowerVal = tvTotalPowerVal;
    this.tvXDBBWVal = tvXDBBWVal;
    this.tvXDBVal = tvXDBVal;
  }

  @NonNull
  public static OccBwLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.occ_bw_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static OccBwLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<OccBwLayoutBinding>inflateInternal(inflater, R.layout.occ_bw_layout, root, attachToRoot, component);
  }

  @NonNull
  public static OccBwLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.occ_bw_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static OccBwLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<OccBwLayoutBinding>inflateInternal(inflater, R.layout.occ_bw_layout, null, false, component);
  }

  public static OccBwLayoutBinding bind(@NonNull View view) {
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
  public static OccBwLayoutBinding bind(@NonNull View view, @Nullable Object component) {
    return (OccBwLayoutBinding)bind(component, view, R.layout.occ_bw_layout);
  }
}
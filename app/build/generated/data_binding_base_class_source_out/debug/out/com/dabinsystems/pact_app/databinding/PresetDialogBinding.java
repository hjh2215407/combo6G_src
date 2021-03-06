// Generated by data binding compiler. Do not edit!
package com.dabinsystems.pact_app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.dabinsystems.pact_app.R;
import java.lang.Deprecated;
import java.lang.Object;
import me.grantland.widget.AutofitTextView;

public abstract class PresetDialogBinding extends ViewDataBinding {
  @NonNull
  public final Button btnCancel;

  @NonNull
  public final LinearLayout linPresetParent;

  @NonNull
  public final LinearLayout ll;

  @NonNull
  public final ConstraintLayout parent;

  @NonNull
  public final LinearLayout presetParent;

  @NonNull
  public final AutofitTextView tvPresetTitle;

  protected PresetDialogBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnCancel, LinearLayout linPresetParent, LinearLayout ll, ConstraintLayout parent,
      LinearLayout presetParent, AutofitTextView tvPresetTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnCancel = btnCancel;
    this.linPresetParent = linPresetParent;
    this.ll = ll;
    this.parent = parent;
    this.presetParent = presetParent;
    this.tvPresetTitle = tvPresetTitle;
  }

  @NonNull
  public static PresetDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.preset_dialog, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static PresetDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<PresetDialogBinding>inflateInternal(inflater, R.layout.preset_dialog, root, attachToRoot, component);
  }

  @NonNull
  public static PresetDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.preset_dialog, null, false, component)
   */
  @NonNull
  @Deprecated
  public static PresetDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<PresetDialogBinding>inflateInternal(inflater, R.layout.preset_dialog, null, false, component);
  }

  public static PresetDialogBinding bind(@NonNull View view) {
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
  public static PresetDialogBinding bind(@NonNull View view, @Nullable Object component) {
    return (PresetDialogBinding)bind(component, view, R.layout.preset_dialog);
  }
}

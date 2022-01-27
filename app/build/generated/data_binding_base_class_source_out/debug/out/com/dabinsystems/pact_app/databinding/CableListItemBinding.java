// Generated by data binding compiler. Do not edit!
package com.dabinsystems.pact_app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.dabinsystems.pact_app.R;
import java.lang.Deprecated;
import java.lang.Object;
import me.grantland.widget.AutofitTextView;

public abstract class CableListItemBinding extends ViewDataBinding {
  @NonNull
  public final RelativeLayout parent;

  @NonNull
  public final AutofitTextView tvCableName;

  protected CableListItemBinding(Object _bindingComponent, View _root, int _localFieldCount,
      RelativeLayout parent, AutofitTextView tvCableName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.parent = parent;
    this.tvCableName = tvCableName;
  }

  @NonNull
  public static CableListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.cable_list_item, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static CableListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<CableListItemBinding>inflateInternal(inflater, R.layout.cable_list_item, root, attachToRoot, component);
  }

  @NonNull
  public static CableListItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.cable_list_item, null, false, component)
   */
  @NonNull
  @Deprecated
  public static CableListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<CableListItemBinding>inflateInternal(inflater, R.layout.cable_list_item, null, false, component);
  }

  public static CableListItemBinding bind(@NonNull View view) {
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
  public static CableListItemBinding bind(@NonNull View view, @Nullable Object component) {
    return (CableListItemBinding)bind(component, view, R.layout.cable_list_item);
  }
}
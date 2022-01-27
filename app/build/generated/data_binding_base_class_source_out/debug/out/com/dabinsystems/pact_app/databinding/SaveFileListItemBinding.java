// Generated by data binding compiler. Do not edit!
package com.dabinsystems.pact_app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.dabinsystems.pact_app.R;
import java.lang.Deprecated;
import java.lang.Object;
import me.grantland.widget.AutofitTextView;

public abstract class SaveFileListItemBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout parent;

  @NonNull
  public final AutofitTextView tvModified;

  @NonNull
  public final AutofitTextView tvName;

  @NonNull
  public final AutofitTextView tvSize;

  @NonNull
  public final AutofitTextView tvType;

  protected SaveFileListItemBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout parent, AutofitTextView tvModified, AutofitTextView tvName,
      AutofitTextView tvSize, AutofitTextView tvType) {
    super(_bindingComponent, _root, _localFieldCount);
    this.parent = parent;
    this.tvModified = tvModified;
    this.tvName = tvName;
    this.tvSize = tvSize;
    this.tvType = tvType;
  }

  @NonNull
  public static SaveFileListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.save_file_list_item, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static SaveFileListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<SaveFileListItemBinding>inflateInternal(inflater, R.layout.save_file_list_item, root, attachToRoot, component);
  }

  @NonNull
  public static SaveFileListItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.save_file_list_item, null, false, component)
   */
  @NonNull
  @Deprecated
  public static SaveFileListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<SaveFileListItemBinding>inflateInternal(inflater, R.layout.save_file_list_item, null, false, component);
  }

  public static SaveFileListItemBinding bind(@NonNull View view) {
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
  public static SaveFileListItemBinding bind(@NonNull View view, @Nullable Object component) {
    return (SaveFileListItemBinding)bind(component, view, R.layout.save_file_list_item);
  }
}
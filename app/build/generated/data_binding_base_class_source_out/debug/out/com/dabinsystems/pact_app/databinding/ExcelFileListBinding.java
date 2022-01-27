// Generated by data binding compiler. Do not edit!
package com.dabinsystems.pact_app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.dabinsystems.pact_app.R;
import java.lang.Deprecated;
import java.lang.Object;
import me.grantland.widget.AutofitTextView;

public abstract class ExcelFileListBinding extends ViewDataBinding {
  @NonNull
  public final Button btnCancel;

  @NonNull
  public final ConstraintLayout conMain;

  @NonNull
  public final RelativeLayout linParent;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final AutofitTextView tvTitle;

  protected ExcelFileListBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnCancel, ConstraintLayout conMain, RelativeLayout linParent,
      RecyclerView recyclerView, AutofitTextView tvTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnCancel = btnCancel;
    this.conMain = conMain;
    this.linParent = linParent;
    this.recyclerView = recyclerView;
    this.tvTitle = tvTitle;
  }

  @NonNull
  public static ExcelFileListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.excel_file_list, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ExcelFileListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ExcelFileListBinding>inflateInternal(inflater, R.layout.excel_file_list, root, attachToRoot, component);
  }

  @NonNull
  public static ExcelFileListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.excel_file_list, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ExcelFileListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ExcelFileListBinding>inflateInternal(inflater, R.layout.excel_file_list, null, false, component);
  }

  public static ExcelFileListBinding bind(@NonNull View view) {
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
  public static ExcelFileListBinding bind(@NonNull View view, @Nullable Object component) {
    return (ExcelFileListBinding)bind(component, view, R.layout.excel_file_list);
  }
}
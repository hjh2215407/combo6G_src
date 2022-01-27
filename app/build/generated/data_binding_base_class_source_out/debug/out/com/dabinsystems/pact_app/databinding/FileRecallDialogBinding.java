// Generated by data binding compiler. Do not edit!
package com.dabinsystems.pact_app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.dabinsystems.pact_app.R;
import java.lang.Deprecated;
import java.lang.Object;
import me.grantland.widget.AutofitTextView;

public abstract class FileRecallDialogBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout linParent;

  @NonNull
  public final RecyclerView rvRecallList;

  @NonNull
  public final Spinner spFileType;

  @NonNull
  public final AutofitTextView tvCancel;

  @NonNull
  public final AutofitTextView tvFileName;

  @NonNull
  public final AutofitTextView tvFileNameTitle;

  @NonNull
  public final AutofitTextView tvFileType;

  @NonNull
  public final AutofitTextView tvModified;

  @NonNull
  public final AutofitTextView tvName;

  @NonNull
  public final AutofitTextView tvOK;

  @NonNull
  public final AutofitTextView tvSize;

  @NonNull
  public final AutofitTextView tvTitle;

  @NonNull
  public final AutofitTextView tvType;

  protected FileRecallDialogBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout linParent, RecyclerView rvRecallList, Spinner spFileType,
      AutofitTextView tvCancel, AutofitTextView tvFileName, AutofitTextView tvFileNameTitle,
      AutofitTextView tvFileType, AutofitTextView tvModified, AutofitTextView tvName,
      AutofitTextView tvOK, AutofitTextView tvSize, AutofitTextView tvTitle,
      AutofitTextView tvType) {
    super(_bindingComponent, _root, _localFieldCount);
    this.linParent = linParent;
    this.rvRecallList = rvRecallList;
    this.spFileType = spFileType;
    this.tvCancel = tvCancel;
    this.tvFileName = tvFileName;
    this.tvFileNameTitle = tvFileNameTitle;
    this.tvFileType = tvFileType;
    this.tvModified = tvModified;
    this.tvName = tvName;
    this.tvOK = tvOK;
    this.tvSize = tvSize;
    this.tvTitle = tvTitle;
    this.tvType = tvType;
  }

  @NonNull
  public static FileRecallDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.file_recall_dialog, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FileRecallDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FileRecallDialogBinding>inflateInternal(inflater, R.layout.file_recall_dialog, root, attachToRoot, component);
  }

  @NonNull
  public static FileRecallDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.file_recall_dialog, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FileRecallDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FileRecallDialogBinding>inflateInternal(inflater, R.layout.file_recall_dialog, null, false, component);
  }

  public static FileRecallDialogBinding bind(@NonNull View view) {
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
  public static FileRecallDialogBinding bind(@NonNull View view, @Nullable Object component) {
    return (FileRecallDialogBinding)bind(component, view, R.layout.file_recall_dialog);
  }
}
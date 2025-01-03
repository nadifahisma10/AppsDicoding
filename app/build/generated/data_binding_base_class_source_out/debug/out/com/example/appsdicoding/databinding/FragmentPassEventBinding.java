// Generated by view binder compiler. Do not edit!
package com.example.appsdicoding.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.appsdicoding.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPassEventBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ProgressBar progressBarPast;

  @NonNull
  public final RecyclerView recyclerViewPast;

  @NonNull
  public final SearchView searchView;

  private FragmentPassEventBinding(@NonNull LinearLayout rootView,
      @NonNull ProgressBar progressBarPast, @NonNull RecyclerView recyclerViewPast,
      @NonNull SearchView searchView) {
    this.rootView = rootView;
    this.progressBarPast = progressBarPast;
    this.recyclerViewPast = recyclerViewPast;
    this.searchView = searchView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPassEventBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPassEventBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_pass_event, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPassEventBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.progressBarPast;
      ProgressBar progressBarPast = ViewBindings.findChildViewById(rootView, id);
      if (progressBarPast == null) {
        break missingId;
      }

      id = R.id.recyclerViewPast;
      RecyclerView recyclerViewPast = ViewBindings.findChildViewById(rootView, id);
      if (recyclerViewPast == null) {
        break missingId;
      }

      id = R.id.searchView;
      SearchView searchView = ViewBindings.findChildViewById(rootView, id);
      if (searchView == null) {
        break missingId;
      }

      return new FragmentPassEventBinding((LinearLayout) rootView, progressBarPast,
          recyclerViewPast, searchView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

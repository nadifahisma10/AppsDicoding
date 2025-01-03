// Generated by view binder compiler. Do not edit!
package com.example.appsdicoding.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.appsdicoding.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityEventDetailBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final TextView beginTime;

  @NonNull
  public final TextView category;

  @NonNull
  public final TextView cityName;

  @NonNull
  public final TextView description;

  @NonNull
  public final TextView endTime;

  @NonNull
  public final TextView id;

  @NonNull
  public final ImageView imageLogo;

  @NonNull
  public final Button link;

  @NonNull
  public final TextView mediaCover;

  @NonNull
  public final TextView name;

  @NonNull
  public final TextView ownerName;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final TextView quota;

  @NonNull
  public final TextView registrants;

  @NonNull
  public final TextView summary;

  private ActivityEventDetailBinding(@NonNull ScrollView rootView, @NonNull TextView beginTime,
      @NonNull TextView category, @NonNull TextView cityName, @NonNull TextView description,
      @NonNull TextView endTime, @NonNull TextView id, @NonNull ImageView imageLogo,
      @NonNull Button link, @NonNull TextView mediaCover, @NonNull TextView name,
      @NonNull TextView ownerName, @NonNull ProgressBar progressBar, @NonNull TextView quota,
      @NonNull TextView registrants, @NonNull TextView summary) {
    this.rootView = rootView;
    this.beginTime = beginTime;
    this.category = category;
    this.cityName = cityName;
    this.description = description;
    this.endTime = endTime;
    this.id = id;
    this.imageLogo = imageLogo;
    this.link = link;
    this.mediaCover = mediaCover;
    this.name = name;
    this.ownerName = ownerName;
    this.progressBar = progressBar;
    this.quota = quota;
    this.registrants = registrants;
    this.summary = summary;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityEventDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityEventDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_event_detail, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityEventDetailBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.beginTime;
      TextView beginTime = ViewBindings.findChildViewById(rootView, id);
      if (beginTime == null) {
        break missingId;
      }

      id = R.id.category;
      TextView category = ViewBindings.findChildViewById(rootView, id);
      if (category == null) {
        break missingId;
      }

      id = R.id.cityName;
      TextView cityName = ViewBindings.findChildViewById(rootView, id);
      if (cityName == null) {
        break missingId;
      }

      id = R.id.description;
      TextView description = ViewBindings.findChildViewById(rootView, id);
      if (description == null) {
        break missingId;
      }

      id = R.id.endTime;
      TextView endTime = ViewBindings.findChildViewById(rootView, id);
      if (endTime == null) {
        break missingId;
      }

      id = R.id.id;
      TextView id_ = ViewBindings.findChildViewById(rootView, id);
      if (id_ == null) {
        break missingId;
      }

      id = R.id.imageLogo;
      ImageView imageLogo = ViewBindings.findChildViewById(rootView, id);
      if (imageLogo == null) {
        break missingId;
      }

      id = R.id.link;
      Button link = ViewBindings.findChildViewById(rootView, id);
      if (link == null) {
        break missingId;
      }

      id = R.id.mediaCover;
      TextView mediaCover = ViewBindings.findChildViewById(rootView, id);
      if (mediaCover == null) {
        break missingId;
      }

      id = R.id.name;
      TextView name = ViewBindings.findChildViewById(rootView, id);
      if (name == null) {
        break missingId;
      }

      id = R.id.ownerName;
      TextView ownerName = ViewBindings.findChildViewById(rootView, id);
      if (ownerName == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.quota;
      TextView quota = ViewBindings.findChildViewById(rootView, id);
      if (quota == null) {
        break missingId;
      }

      id = R.id.registrants;
      TextView registrants = ViewBindings.findChildViewById(rootView, id);
      if (registrants == null) {
        break missingId;
      }

      id = R.id.summary;
      TextView summary = ViewBindings.findChildViewById(rootView, id);
      if (summary == null) {
        break missingId;
      }

      return new ActivityEventDetailBinding((ScrollView) rootView, beginTime, category, cityName,
          description, endTime, id_, imageLogo, link, mediaCover, name, ownerName, progressBar,
          quota, registrants, summary);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

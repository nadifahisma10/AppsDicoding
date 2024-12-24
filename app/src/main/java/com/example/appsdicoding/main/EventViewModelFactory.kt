package com.example.appsdicoding.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsdicoding.api.ApiService
import com.example.appsdicoding.ui.settings.SettingPreferences

class EventViewModelFactory(private val apiService: ApiService, private val pref: SettingPreferences) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return EventViewModel(apiService, pref) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
  }
}

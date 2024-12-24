package com.example.appsdicoding.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsdicoding.api.ApiService

class EventDetailViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(EventDetailViewModel::class.java)) {
      return EventDetailViewModel(apiService) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}

package com.example.appsdicoding.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsdicoding.main.EventResponse
import com.example.appsdicoding.api.ApiService
import com.example.appsdicoding.ui.detail.DetailEventResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val apiService: ApiService) : ViewModel() {

  private val _activeEvents = MutableLiveData<List<DetailEventResponse>>()
  private val _pastEvents = MutableLiveData<List<DetailEventResponse>>()
  private val _error = MutableLiveData<String?>()
  val error: LiveData<String?> get() = _error

  fun loadEvents() {
    viewModelScope.launch {
      try {
        // Load active events
        val activeResponse: Response<EventResponse> = apiService.getEvents()
        if (activeResponse.isSuccessful && activeResponse.body() != null) {
          val activeList = activeResponse.body()!!.listEvents?.filterIsInstance<DetailEventResponse>() ?: emptyList()
          _activeEvents.value = activeList
        } else {
          _error.value = "Error loading active events: ${activeResponse.message()}"
        }

        // Load past events
        val pastResponse: Response<EventResponse> = apiService.getEvents()
        if (pastResponse.isSuccessful && pastResponse.body() != null) {
          val pastList = pastResponse.body()!!.listEvents?.filterIsInstance<DetailEventResponse>()?.take(5) ?: emptyList()
          _pastEvents.value = pastList
        } else {
          _error.value = "Error loading past events: ${pastResponse.message()}"
        }
      } catch (e: Exception) {
        _error.value = "Failed to load events: ${e.localizedMessage}"
      }
    }
  }
}

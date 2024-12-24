package com.example.appsdicoding.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.appsdicoding.api.ApiService
import com.example.appsdicoding.ui.settings.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Response

class EventViewModel(private val apiService: ApiService, private val pref: SettingPreferences) : ViewModel() {

    private val _isLoadingActive = MutableLiveData<Boolean>()
    val isLoadingActive: LiveData<Boolean> get() = _isLoadingActive

    private val _isLoadingPast = MutableLiveData<Boolean>()
    val isLoadingPast: LiveData<Boolean> get() = _isLoadingPast

    private val _isLoadingHome = MutableLiveData<Boolean>()
    val isLoadingHome: LiveData<Boolean> get() = _isLoadingHome

    private val _activeEvents = MutableLiveData<List<Event>>()
    val activeEvents: LiveData<List<Event>> get() = _activeEvents

    private val _pastEvents = MutableLiveData<List<Event>>()
    val pastEvents: LiveData<List<Event>> get() = _pastEvents

    private val _homeEvents = MutableLiveData<List<Event>>()

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun observeThemeSettings(
        onThemeChanged: (Boolean) -> Unit
    ) {
        getThemeSettings().observeForever { isDarkModeActive ->
            onThemeChanged(isDarkModeActive)
        }
    }

    fun toggleTheme(isChecked: Boolean) {
        saveThemeSetting(isChecked)
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun loadActiveEvents() {
        _isLoadingActive.value = true
        viewModelScope.launch {
            try {
                val response: Response<EventResponse> = apiService.getEvents(active = 1)
                if (response.isSuccessful) {
                    _activeEvents.value = response.body()?.listEvents // Asumsi EventResponse memiliki properti 'events' yang berisi List<Event>
                } else {
                    _error.value = "Failed to load active events: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoadingActive.value = false
            }
        }
    }

    fun loadPastEvents() {
        _isLoadingPast.value = true
        viewModelScope.launch {
            try {
                val response: Response<EventResponse> = apiService.getEvents(active = 0)
                if (response.isSuccessful) {
                    _pastEvents.value = response.body()?.listEvents // Sama seperti di atas
                } else {
                    _error.value = "Failed to load past events: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoadingPast.value = false
            }
        }
    }

    fun loadHomeEvents() {
        _isLoadingHome.value = true
        viewModelScope.launch {
            try {
                val response: Response<EventResponse> = apiService.getEvents() // Menggunakan tipe EventResponse
                if (response.isSuccessful) {
                    _homeEvents.value = response.body()?.listEvents // Sama seperti di atas
                } else {
                    _error.value = "Failed to load home events: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoadingHome.value = false
            }
        }
    }
}

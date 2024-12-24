package com.example.appsdicoding.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appsdicoding.main.EventDetail
import com.example.appsdicoding.main.EventDetailResponse
import com.example.appsdicoding.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventDetailViewModel(private val apiService: ApiService) : ViewModel() {

  // Menggunakan objek default DetailEventResponse
  private val _eventDetail = MutableLiveData<EventDetail>().apply {
    value = EventDetail() // Inisialisasi dengan objek default
  }
  val eventDetail: LiveData<EventDetail> get() = _eventDetail

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  // Mengambil detail event berdasarkan ID
  fun fetchEventDetail(eventId: Int) {
    _isLoading.value = true
    apiService.getDetailEvent(eventId).enqueue(object : Callback<EventDetailResponse> {
      override fun onResponse(
        p0: Call<EventDetailResponse>,
        response: Response<EventDetailResponse>
      ) {
        _isLoading.value = false
        if (response.isSuccessful && response.body() != null) {
          _eventDetail.value = response.body()?.event
          Log.d("EventDetail", "Data berhasil didapatkan: ${response.body()}")
        } else {
          _eventDetail.value = EventDetail() // Gunakan objek default
          Log.e("EventDetail", "Data kosong atau tidak ditemukan")
        }
      }

      override fun onFailure(call: Call<EventDetailResponse>, t: Throwable) {
        _isLoading.value = false
        _eventDetail.value = EventDetail() // Gunakan objek default saat gagal
      }
    })
  }
}

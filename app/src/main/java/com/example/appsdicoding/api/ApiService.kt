package com.example.appsdicoding.api

import com.example.appsdicoding.main.Event
import com.example.appsdicoding.main.EventDetailResponse
import com.example.appsdicoding.main.EventResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Call

interface ApiService {

    /**
     * Fetches a list of events. If 'active' is provided, filters the events based on their active status.
     *
     * @param active Integer that filters events (1 for active, 0 for inactive). Default is null.
     * @return Response containing a list of events.
     */
    @GET("events")
    suspend fun getEvents(@Query("active") active: Int? = null): Response<EventResponse>

    /**
     * Fetches detailed information about a specific event by its ID.
     *
     * @param eventId The ID of the event to retrieve.
     * @return Response containing details of the requested event.
     */
    @GET("events/{id}")
    fun getDetailEvent(@Path("id") eventId: Int): Call<EventDetailResponse>

    @GET("events/search")
    suspend fun searchEvents(@Query("keyword") keyword: String): Response<List<Event>>
}

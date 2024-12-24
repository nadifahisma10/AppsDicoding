package com.example.appsdicoding.ui.search

import com.example.appsdicoding.main.Event

class EventSearchHelper {

    /**
     * Fungsi untuk melakukan pencarian event berdasarkan keyword.
     * @param events Daftar event yang akan dicari.
     * @param keyword Kata kunci pencarian.
     * @return Daftar event yang sesuai dengan keyword.
     */
    fun searchEvents(events: List<Event>, keyword: String): List<Event> {
        if (keyword.isEmpty()) return events

        return events.filter { event ->
            val title = event.name ?: ""
            val description = event.description ?: ""
            title.contains(keyword, ignoreCase = true) || description.contains(keyword, ignoreCase = true)
        }
    }
}

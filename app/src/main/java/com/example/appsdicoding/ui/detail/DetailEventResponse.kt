package com.example.appsdicoding.ui.detail

data class DetailEventResponse(
    val id: Int,
    val name: String,
    val summary: String,
    val description: String,
    val imageLogo: String,
    val mediaCover: String,
    val category: String,
    val ownerName: String,
    val cityName: String,
    val quota: Int,
    val registrants: Int,
    val beginTime: String,
    val endTime: String,
    val link: String,
    val isActive: Int
)  {
    companion object {
        @Suppress("unused")
        val Empty = DetailEventResponse (
            id = 0,
            name = "",
            summary = "",
            description = "",
            imageLogo = "",
            mediaCover = "",
            category = "",
            ownerName = "",
            cityName = "",
            quota = 0,
            registrants = 0,
            beginTime = "",
            endTime = "",
            link = "",
            isActive = 0
        )
    }
}

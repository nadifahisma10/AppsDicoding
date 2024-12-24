package com.example.appsdicoding.ui.favorite

import android.content.Context
import android.util.Log

class FavoriteEventManager(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("favorite_events", Context.MODE_PRIVATE)

    fun isFavorite(eventId: Int): Boolean {
        val isFavorite = getFavoriteList().contains(eventId.toString())
        Log.d("FavoriteEventManager", "isFavorite($eventId): $isFavorite")
        return isFavorite
    }

    fun addFavorite(eventId: Int) {
        val favorites = getFavoriteList().toMutableSet()
        favorites.add(eventId.toString())
        sharedPreferences.edit().putStringSet("favorite_list", favorites).apply()
        Log.d("FavoriteEventManager", "Added event $eventId to favorites")
    }

    fun removeFavorite(eventId: Int) {
        val favorites = getFavoriteList().toMutableSet()
        favorites.remove(eventId.toString())
        sharedPreferences.edit().putStringSet("favorite_list", favorites).apply()
        Log.d("FavoriteEventManager", "Removed event $eventId from favorites")
    }

    fun getFavoriteList(): Set<String> {
        return sharedPreferences.getStringSet("favorite_list", emptySet()) ?: emptySet()
    }
}

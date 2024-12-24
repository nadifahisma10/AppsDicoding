package com.example.appsdicoding.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsdicoding.R
import com.example.appsdicoding.ui.favorite.FavoriteEventManager

class EventAdapter(
    context: Context
) : ListAdapter<Event, EventAdapter.EventViewHolder>(EventDiffCallback()) {

    private val favoriteManager = FavoriteEventManager(context)

    private var listener: OnItemClickCallback? = null

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageLogo: ImageView = view.findViewById(R.id.imageLogo)
        private val name: TextView = view.findViewById(R.id.name)
        private val tvEventOrganizer: TextView = view.findViewById(R.id.organizer)
        private val btnFavorite: ImageView = view.findViewById(R.id.btn_favorite)

        fun bind(event: Event) {
            name.text = event.name
            tvEventOrganizer.text = event.ownerName
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(imageLogo)

            // Set ikon favorit berdasarkan status event
            val isFavorite = favoriteManager.isFavorite(event.id?.toInt() ?: 0)
            updateFavoriteIcon(isFavorite)

            itemView.setOnClickListener {
                listener?.onEventClick(event)
            }

            btnFavorite.setOnClickListener {
                val eventId = event.id?.toInt() ?: return@setOnClickListener
                if (isFavorite) {
                    favoriteManager.removeFavorite(eventId)
                } else {
                    favoriteManager.addFavorite(eventId)
                }
                updateFavoriteIcon(!isFavorite)
                itemView.context.sendBroadcast(Intent("UPDATE_FAVORITE_LIST"))
            }
        }

        private fun updateFavoriteIcon(isFavorite: Boolean) {
            btnFavorite.setImageResource(
                if (isFavorite) R.drawable.baseline_favorite_24
                else R.drawable.baseline_favorite_border_24
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setListener(listener: OnItemClickCallback?) {
        this.listener = listener
    }

    class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickCallback {
        fun onEventClick(event: Event)
    }
}

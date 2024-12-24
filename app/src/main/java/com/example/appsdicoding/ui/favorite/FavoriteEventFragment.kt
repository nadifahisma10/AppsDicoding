package com.example.appsdicoding.ui.favorite

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsdicoding.databinding.FragmentFavoriteEventBinding
import com.example.appsdicoding.main.EventAdapter
import com.example.appsdicoding.main.Event
import com.example.appsdicoding.ui.detail.EventDetailActivity

class FavoriteEventFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteEventBinding
    private lateinit var favoriteManager: FavoriteEventManager
    private lateinit var adapter: EventAdapter

    private val favoriteUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            loadFavoriteEvents() // Memuat ulang event favorit saat ada perubahan
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteManager = FavoriteEventManager(requireContext())
        setupRecyclerView()
        loadFavoriteEvents()

        // Mendaftarkan broadcast receiver
        val intentFilter = IntentFilter("UPDATE_FAVORITE_LIST")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireContext().registerReceiver(
                favoriteUpdateReceiver,
                intentFilter,
                Context.RECEIVER_NOT_EXPORTED // Flag untuk API 33 ke atas
            )
        } else {
            requireContext().registerReceiver(
                favoriteUpdateReceiver,
                intentFilter, Context.RECEIVER_NOT_EXPORTED // Tanpa flag untuk API di bawah 33
            )
        }
    }

    private fun setupRecyclerView() {
        adapter = EventAdapter(requireContext()) // Menggunakan Context sebagai parameter
        adapter.setListener(object : EventAdapter.OnItemClickCallback {
            override fun onEventClick(event: Event) {
                // Navigasi ke halaman detail event
                val intent = Intent(requireContext(), EventDetailActivity::class.java)
                intent.putExtra("EVENT_ID", event.id)
                startActivity(intent)
            }
        })
        binding.recyclerViewFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavorite.adapter = adapter
    }

    private fun loadFavoriteEvents() {
        val favoriteIds = favoriteManager.getFavoriteList().map { it.toIntOrNull() }.filterNotNull()
        val favoriteEvents = getEventsByIds(favoriteIds)

        if (favoriteEvents.isNotEmpty()) {
            binding.recyclerViewFavorite.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
            adapter.submitList(favoriteEvents)
        } else {
            binding.recyclerViewFavorite.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        }
    }

    private fun getEventsByIds(ids: List<Int>): List<Event> {
        // Periksa nullability sebelum mencocokkan
        return ids.mapNotNull { id ->
            dummyEvents.find { it.id == id }
        }
    }

    private val dummyEvents = listOf(
        Event("1", "Festival Musik Jakarta", "Musik", "LogoURL1", "Owner1"),
        Event("2", "Pameran Buku Surabaya", "Pameran", "LogoURL2", "Owner2"),
        Event("3", "Workshop Fotografi Bali", "Workshop", "LogoURL3", "Owner3")
    )

    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().unregisterReceiver(favoriteUpdateReceiver)
    }
}

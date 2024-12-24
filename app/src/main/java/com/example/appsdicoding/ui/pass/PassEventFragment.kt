package com.example.appsdicoding.ui.pass

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsdicoding.main.Event
import com.example.appsdicoding.main.EventAdapter
import com.example.appsdicoding.api.APIConfig
import com.example.appsdicoding.main.EventViewModel
import com.example.appsdicoding.main.EventViewModelFactory
import com.example.appsdicoding.databinding.FragmentPassEventBinding
import com.example.appsdicoding.ui.detail.EventDetailActivity
import com.example.appsdicoding.ui.search.EventSearchHelper
import com.example.appsdicoding.ui.settings.SettingPreferences
import com.example.appsdicoding.ui.settings.dataStore

class PassEventFragment : Fragment(), EventAdapter.OnItemClickCallback {

    private var _binding: FragmentPassEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var allEvents: List<Event>
    private lateinit var eventSearchHelper: EventSearchHelper
    private lateinit var filteredEvents: List<Event>
    private lateinit var eventViewModel: EventViewModel

    private val pastAdapter by lazy {
        EventAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPassEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi SettingPreferences
        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val apiService = APIConfig.apiService
        val factory = EventViewModelFactory(apiService, pref)
        eventViewModel = ViewModelProvider(this, factory)[EventViewModel::class.java]

        setupSearchView()
        eventSearchHelper = EventSearchHelper()

        // Contoh daftar event
        allEvents = listOf(
            Event("1", "Music Concert", "An exciting music concert", true.toString()),
            Event("2", "Art Exhibition", "An amazing art exhibition", false.toString())
        )

        // Mencari event berdasarkan keyword
        val keyword = "music" // Contoh keyword
        filteredEvents = eventSearchHelper.searchEvents(allEvents, keyword)

        // Menampilkan hasil pencarian
        displayEvents(filteredEvents)

        pastAdapter.setListener(this)
        binding.recyclerViewPast.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pastAdapter
        }

        // Observe past events only
        eventViewModel.pastEvents.observe(viewLifecycleOwner) { events ->
            pastAdapter.submitList(events)
        }

        // Observe loading and error states
        eventViewModel.isLoadingPast.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarPast.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        eventViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        // Load past events
        eventViewModel.loadPastEvents()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    filterEvents(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    filterEvents(it)
                }
                return true
            }
        })
    }

    // Your filter function
    private fun filterEvents(keyword: String) {
        filteredEvents = EventSearchHelper().searchEvents(allEvents, keyword)
        pastAdapter.submitList(filteredEvents) // Set the filtered events to the adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onEventClick(event: Event) {
        val intent = Intent(context, EventDetailActivity::class.java).apply {
            putExtra("eventId", event.id)  // Mengirimkan ID event
        }
        startActivity(intent)
    }

    private fun displayEvents(events: List<Event>) {
        events.forEach { event ->
            Log.d("PassEventFragment", "Event: ${event.name}")
        }
    }
}

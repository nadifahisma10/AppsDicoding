package com.example.appsdicoding.ui.home

import android.content.Intent
import android.os.Bundle
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
import com.example.appsdicoding.databinding.FragmentHomeEventBinding
import com.example.appsdicoding.ui.detail.EventDetailActivity
import com.example.appsdicoding.ui.favorite.FavoriteEventManager
import com.example.appsdicoding.ui.search.EventSearchHelper
import com.example.appsdicoding.ui.settings.SettingPreferences
import com.example.appsdicoding.ui.settings.dataStore

class HomeEventFragment : Fragment(), EventAdapter.OnItemClickCallback {

    private var _binding: FragmentHomeEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteManager: FavoriteEventManager
    private lateinit var eventSearchHelper: EventSearchHelper
    private lateinit var allEvents: List<Event>
    private lateinit var filteredEvents: List<Event>
    private lateinit var activeAdapter: EventAdapter
    private lateinit var pastAdapter: EventAdapter

    private lateinit var eventViewModel: EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi awal dengan daftar kosong untuk menghindari UninitializedPropertyAccessException
        allEvents = emptyList()

        // Inisialisasi FavoriteEventManager
        favoriteManager = FavoriteEventManager(requireContext())

        // Inisialisasi SettingPreferences
        val pref = SettingPreferences.getInstance(requireContext().dataStore)

        // Initialize ViewModel with the API service
        val apiService = APIConfig.apiService
        val factory = EventViewModelFactory(apiService, pref)
        eventViewModel = ViewModelProvider(this, factory)[EventViewModel::class.java]

        // Initialize RecyclerViews and Adapters for active and past events
        activeAdapter = EventAdapter(requireContext())
        pastAdapter = EventAdapter(requireContext())
        activeAdapter.setListener(this)
        pastAdapter.setListener(this)

        eventSearchHelper = EventSearchHelper()

        // Amati data dari ViewModel
        observeViewModel()

        // Setup RecyclerView untuk event aktif
        binding.recyclerViewActive.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = activeAdapter
        }

        // Setup RecyclerView untuk event tidak aktif
        binding.recyclerViewPast.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pastAdapter
        }

        // Load active and past events
        eventViewModel.loadActiveEvents()
        eventViewModel.loadPastEvents()
        eventViewModel.loadHomeEvents()

        // Setup SearchView untuk pencarian
        setupSearchView()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (::allEvents.isInitialized) { // Pastikan allEvents sudah diinisialisasi
                        filterEvents(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (::allEvents.isInitialized) { // Pastikan allEvents sudah diinisialisasi
                        filterEvents(it)
                    }
                }
                return true
            }
        })
    }

    private fun filterEvents(keyword: String) {
        filteredEvents = eventSearchHelper.searchEvents(allEvents, keyword)
        activeAdapter.submitList(filteredEvents) // Tampilkan hasil pencarian pada RecyclerView aktif
    }

    private fun observeViewModel() {
        // Observe active events dan set ke activeAdapter
        eventViewModel.activeEvents.observe(viewLifecycleOwner) { activeEvents ->
            activeAdapter.submitList(activeEvents.take(5)) // Menampilkan maksimal 5 event aktif
        }

        // Observe past events dan set ke pastAdapter
        eventViewModel.pastEvents.observe(viewLifecycleOwner) { pastEvents ->
            pastAdapter.submitList(pastEvents.take(5)) // Menampilkan maksimal 5 event tidak aktif
        }

        // Observe loading state untuk menampilkan progress bar
        eventViewModel.isLoadingHome.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarHome.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observe error message dan tampilkan sebagai Toast
        eventViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onEventClick(event: Event) {
        val intent = Intent(context, EventDetailActivity::class.java).apply {
            putExtra("eventId", event.id) // Mengirimkan ID event
        }
        startActivity(intent)
    }
}

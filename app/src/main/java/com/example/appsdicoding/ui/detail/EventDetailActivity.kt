package com.example.appsdicoding.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.appsdicoding.R
import com.example.appsdicoding.api.ApiServiceFactory
import com.example.appsdicoding.databinding.ActivityEventDetailBinding
import com.example.appsdicoding.main.EventViewModel
import com.example.appsdicoding.main.EventViewModelFactory
import com.example.appsdicoding.ui.favorite.FavoriteEventManager
import com.example.appsdicoding.ui.settings.SettingPreferences
import com.example.appsdicoding.ui.settings.dataStore
import com.google.android.material.snackbar.Snackbar

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding
    private lateinit var eventViewModel: EventViewModel
    private lateinit var favoriteManager: FavoriteEventManager

    // Inisialisasi ViewModel dan ApiService
    private val viewModel: EventDetailViewModel by viewModels {
        EventDetailViewModelFactory(ApiServiceFactory.create())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi binding
        val pref = SettingPreferences.getInstance(applicationContext.dataStore)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi EventViewModel
        val apiService = ApiServiceFactory.create()
        val factory = EventViewModelFactory(apiService, pref)
        eventViewModel = ViewModelProvider(this, factory)[EventViewModel::class.java]

        // Initialize favoriteManager
        favoriteManager = FavoriteEventManager(this)

        // Ambil ID event dari intent
        val eventId = intent.getIntExtra("eventId", 0)

        if (eventId != 0) {
            viewModel.fetchEventDetail(eventId)
            observeViewModel()
        } else {
            Snackbar.make(binding.root, "Event tidak ditemukan", Snackbar.LENGTH_SHORT).show()
            finish()
        }

        // Atur link pendaftaran
        binding.link.setOnClickListener {
            val eventLink = viewModel.eventDetail.value?.link
            if (!eventLink.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(eventLink))
                startActivity(intent)
            } else {
                Snackbar.make(binding.root, "Link pendaftaran tidak tersedia", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        viewModel.eventDetail.observe(this) { event ->
            with(binding) {
                progressBar.visibility = View.GONE

                id.text = getString(R.string.id, event.id)
                name.text = event.name
                summary.text = event.summary
                description.text = HtmlCompat.fromHtml(event.description.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
                mediaCover.text = getString(R.string.media_cover, event.mediaCover)
                category.text = getString(R.string.category, event.category)
                ownerName.text = getString(R.string.owner_name, event.ownerName)
                cityName.text = getString(R.string.city, event.cityName)
                quota.text = getString(R.string.quota_left, (event.quota ?: 0) - (event.registrants ?: 0))
                registrants.text = getString(R.string.registrants, event.registrants)
                beginTime.text = getString(R.string.begin_time, event.beginTime)
                endTime.text = getString(R.string.end_time, event.endTime)

                Log.d("EventDetail", "ImageLogo URL: ${event.imageLogo}, MediaCover URL: ${event.mediaCover}")

                val imageUrl = event.imageLogo?.takeIf { it.isNotEmpty() } ?: event.mediaCover
                Glide.with(this@EventDetailActivity)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(binding.imageLogo)
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}

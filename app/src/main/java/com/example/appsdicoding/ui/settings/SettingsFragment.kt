package com.example.appsdicoding.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.appsdicoding.api.ApiServiceFactory
import com.example.appsdicoding.databinding.FragmentSettingsBinding
import com.example.appsdicoding.main.EventViewModel
import com.example.appsdicoding.main.EventViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : AppCompatActivity() {

    private lateinit var switchTheme: SwitchMaterial
    private lateinit var eventViewModel: EventViewModel
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        switchTheme = binding.switchTheme

        // Inisialisasi EventViewModel dengan Factory
        val pref = SettingPreferences.getInstance(applicationContext.dataStore)
        val apiService = ApiServiceFactory.create()
        val factory = EventViewModelFactory(apiService, pref)
        eventViewModel = ViewModelProvider(this, factory)[EventViewModel::class.java]

        // Observe theme setting and apply the mode
        eventViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        // Handle switch change
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            eventViewModel.saveThemeSetting(isChecked)
        }
    }

    // Handle back button in the toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}

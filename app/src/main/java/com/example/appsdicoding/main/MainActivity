package com.example.appsdicoding.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.appsdicoding.R
import com.example.appsdicoding.api.APIConfig
import com.example.appsdicoding.databinding.ActivityMainBinding
import com.example.appsdicoding.ui.settings.SettingPreferences
import com.example.appsdicoding.ui.settings.SettingsFragment
import com.example.appsdicoding.ui.settings.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var eventViewModel: EventViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        // Initialize ViewModel
        val pref = SettingPreferences.getInstance(applicationContext.dataStore)
        val apiService = APIConfig.apiService
        val factory = EventViewModelFactory(apiService, pref)
        eventViewModel = ViewModelProvider(this, factory)[EventViewModel::class.java]

        // Observe pengaturan tema
        eventViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Toolbar as ActionBar
        setSupportActionBar(binding.toolbar)
        // Setup Bottom Navigation
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }

    // Inflate menu in the toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Handle menu item selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsFragment::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

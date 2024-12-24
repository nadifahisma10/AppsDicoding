package com.example.appsdicoding.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appsdicoding.databinding.AboutActivityMainBinding
import com.example.appsdicoding.R

class AboutActivity : Fragment() {

  // Binding variable for AboutActivityMainBinding
  private var _binding: AboutActivityMainBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout using AboutActivityMainBinding
    _binding = AboutActivityMainBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Setup TextViews using the binding
    binding.aboutText.text = getString(R.string.nadifah_isma_aulia)
    binding.aboutDescription.text = getString(R.string.ismanadifah574_gmail_com)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    // Clear the binding when view is destroyed to avoid memory leaks
    _binding = null
  }
}

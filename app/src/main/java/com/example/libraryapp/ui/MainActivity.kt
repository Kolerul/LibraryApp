package com.example.libraryapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.libraryapp.R
import com.example.libraryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.apply {
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.compilation_screen_button -> {
                        findNavController(R.id.nav_host_controller).navigate(R.id.action_global_compilationFragment)
                        true
                    }

                    R.id.search_screen_button -> {
                        findNavController(R.id.nav_host_controller).navigate(R.id.action_global_searchFragment)
                        true
                    }

                    R.id.library_screen_button -> {
                        findNavController(R.id.nav_host_controller).navigate(R.id.action_global_bookshelfFragment)
                        true
                    }

                    else -> false
                }

            }
        }
    }
}
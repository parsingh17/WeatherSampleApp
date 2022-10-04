package com.parul.imdbapplication.presentation.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.parul.imdbapplication.databinding.ActivityMainBinding
import com.parul.imdbapplication.presentation.viewModel.MainViewModel
import com.parul.imdbapplication.presentation.viewModel.MainViewModel.Companion.CMD_LOAD_MAIN

class MainActivity : BaseActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        subscribeToEventCommands()
        viewModel.onCreate()

    }

    private fun setUpNavigation() {

    }

    private fun subscribeToEventCommands() {
        viewModel.eventCommand.observe(this) { event ->
            when (event) {
                CMD_LOAD_MAIN -> {
                    setSupportActionBar(binding.toolbar)
                    setUpNavigation()
                }
            }
        }
    }

}
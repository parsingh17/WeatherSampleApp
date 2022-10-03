package com.parul.imdbapplication.presentation.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.parul.imdbapplication.R
import com.parul.imdbapplication.presentation.viewModel.MainViewModel
import com.parul.imdbapplication.presentation.viewModel.MainViewModel.Companion.CMD_LOAD_MAIN
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
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
                    setContentView(R.layout.activity_main)
                    setUpNavigation()
                }
            }
        }
    }

}
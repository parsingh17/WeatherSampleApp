package com.parul.imdbapplication.presentation.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.parul.imdbapplication.R
import com.parul.imdbapplication.presentation.viewModel.MainViewModel
import com.parul.imdbapplication.presentation.viewModel.MainViewModel.Companion.CMD_LOAD_MAIN
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity() {
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        subscribeToEventCommands()
        viewModel.onCreate()
    }

    private fun setUpNavigation() {
        val navHostFragment = nav_host_fragment as NavHostFragment
    }

    private fun subscribeToEventCommands() {
        viewModel.eventCommand.observe(this, Observer { event ->
            when (event) {
                CMD_LOAD_MAIN -> {
                    setContentView(R.layout.activity_main)
                    setUpNavigation()
                }
            }
        })
    }

}
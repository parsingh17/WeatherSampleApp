package com.parul.imdbapplication.presentation.activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import javax.inject.Inject


open class BaseActivity : AppCompatActivity(){
    private val handler = Handler()
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("WEATHER", "onCreate");

        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        //EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        //EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}



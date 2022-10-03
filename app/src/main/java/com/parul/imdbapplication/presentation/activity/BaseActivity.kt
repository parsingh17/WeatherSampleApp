package com.parul.imdbapplication.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import javax.inject.Inject


open class BaseActivity : AppCompatActivity(){
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("WEATHER", "onCreate")

        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }

}



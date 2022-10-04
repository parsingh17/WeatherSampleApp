package com.parul.imdbapplication

import android.app.Application
import android.content.Context
import com.parul.imdbapplication.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class MainApplication : Application(), HasAndroidInjector {
    var androidInjector: DispatchingAndroidInjector<Any>? = null
        @Inject set

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any>? = androidInjector


    companion object {
        var instance = MainApplication()
        fun getContext(): Context {
            return instance.applicationContext
        }
    }
}

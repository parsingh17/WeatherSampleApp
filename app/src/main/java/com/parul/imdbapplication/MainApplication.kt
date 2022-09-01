package com.parul.imdbapplication

import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import com.parul.imdbapplication.dagger.ApiModule
import com.parul.imdbapplication.dagger.AppComponent
import com.parul.imdbapplication.dagger.DaggerAppComponent
import com.parul.imdbapplication.listener.AppLifeCycleListener
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class MainApplication : Application(), HasAndroidInjector {
    var androidInjector: DispatchingAndroidInjector<Any>? = null
        @Inject set
    private var mAppComponent: AppComponent? = null

    private val lifecycleListener: AppLifeCycleListener by lazy {
        AppLifeCycleListener()
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

/*        mAppComponent = DaggerAppComponent.builder()
            // list of modules that are part of this component need to be created here too
            .application(this) // This also corresponds to the name of your module: %component_name%Module
            .build()*/

        setupLifeCycleListener()

    }

    //fun getAppComponent(): AppComponent? = mAppComponent

    override fun androidInjector(): AndroidInjector<Any>? = androidInjector

    private fun setupLifeCycleListener() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleListener)
    }

    companion object {
        var instance = MainApplication()
        fun getContext(): Context {
            return instance.applicationContext
        }
    }
}

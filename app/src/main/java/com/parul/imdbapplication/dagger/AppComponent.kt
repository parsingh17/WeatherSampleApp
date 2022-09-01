package com.parul.imdbapplication.dagger

import android.app.Application
import com.parul.imdbapplication.MainApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, ActivityBuilder::class, ApplicationModule::class,
        ViewModelModule::class, FragmentModule::class, ApiModule::class]
)
interface AppComponent {

    fun inject(app: MainApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        //fun apiModule(url: String): Builder
        fun build(): AppComponent
    }
}
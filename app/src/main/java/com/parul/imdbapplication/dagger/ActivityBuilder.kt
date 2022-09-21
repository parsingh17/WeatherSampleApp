package com.parul.imdbapplication.dagger

import com.parul.imdbapplication.presentation.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

}
package com.parul.imdbapplication.dagger

import com.parul.imdbapplication.presentation.fragment.CurrentLocationFragment
import com.parul.imdbapplication.presentation.fragment.WeatherDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun provideFirstFragment(): CurrentLocationFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun provideWeatherDetailsFragment(): WeatherDetailsFragment

}
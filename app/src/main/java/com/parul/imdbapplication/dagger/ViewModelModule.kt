package com.parul.imdbapplication.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.parul.imdbapplication.presentation.viewModel.CurrentLocationFragmentViewModel
import com.parul.imdbapplication.presentation.viewModel.MainViewModel
import com.parul.imdbapplication.presentation.viewModel.WeatherDetailsFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrentLocationFragmentViewModel::class)
    abstract fun bindFirstFragmentViewModel(viewModel: CurrentLocationFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherDetailsFragmentViewModel::class)
    abstract fun bindWeatherDetailsFragmentViewModel(viewModel: WeatherDetailsFragmentViewModel): ViewModel
}
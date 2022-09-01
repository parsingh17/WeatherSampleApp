package com.parul.imdbapplication.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.parul.imdbapplication.dagger.ViewModelFactory
import com.parul.imdbapplication.viewModel.FirstFragmentViewModel
import com.parul.imdbapplication.viewModel.MainViewModel
import com.parul.imdbapplication.viewModel.SecondFragmentViewModel
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
    @ViewModelKey(FirstFragmentViewModel::class)
    abstract fun bindFirstFragmentViewModel(viewModel: FirstFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SecondFragmentViewModel::class)
    abstract fun bindSecondFragmentViewModel(viewModel: SecondFragmentViewModel): ViewModel
}
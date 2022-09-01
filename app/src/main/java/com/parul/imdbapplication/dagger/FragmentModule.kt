package com.parul.imdbapplication.dagger

import com.parul.imdbapplication.fragment.FirstFragment
import com.parul.imdbapplication.fragment.SecondFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun provideFirstFragment(): FirstFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun provideSecondFragment(): SecondFragment

}
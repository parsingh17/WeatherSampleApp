package com.parul.imdbapplication.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel

import com.parul.imdbapplication.common.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val app: Application
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    var eventCommand = SingleLiveEvent<Int>()

    fun onCreate() {
            eventCommand.value = CMD_LOAD_MAIN
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


    companion object {
        const val CMD_LOAD_MAIN = 1
    }
}
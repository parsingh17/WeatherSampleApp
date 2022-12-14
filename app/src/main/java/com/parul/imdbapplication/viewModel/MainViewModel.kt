package com.parul.imdbapplication.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import com.parul.imdbapplication.repository.WeatherRepository
import com.parul.imdbapplication.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val app: Application
) : AndroidViewModel(app) {
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
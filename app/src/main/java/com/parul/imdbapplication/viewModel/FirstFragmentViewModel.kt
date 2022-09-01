package com.parul.imdbapplication.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.parul.imdbapplication.repository.WeatherRepository
import com.parul.imdbapplication.utils.SingleLiveEvent
import javax.inject.Inject

class FirstFragmentViewModel @Inject constructor(
    val app: Application,
    val repository: WeatherRepository
) : AndroidViewModel(app){

    var navigationCommand = SingleLiveEvent<Int>()


    fun onNextButtonClicked() {
        navigationCommand.value = NAV_NEXT
    }

    fun onBackPressed() {
        navigationCommand.value = NAV_BACK
    }

    companion object {
        const val NAV_NEXT = 0
        const val NAV_TO_TASK_DETAILS_PAGE = 1
        const val NAV_BACK = 2
    }

}
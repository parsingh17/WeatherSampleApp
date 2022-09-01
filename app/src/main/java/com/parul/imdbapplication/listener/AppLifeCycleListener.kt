package com.parul.imdbapplication.listener

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.parul.imdbapplication.event.AppMovedToForegroundEvent
import org.greenrobot.eventbus.EventBus


class AppLifeCycleListener : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        Log.d( "AppLifeCycleListener","AppLifeCycleListener: app moved to foreground")
        EventBus.getDefault().post(AppMovedToForegroundEvent())
    }
}
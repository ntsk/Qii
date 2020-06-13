package com.qii.ntsk.qii

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QiiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: QiiApp
            private set
    }
}
package com.qii.ntsk.qii

import android.app.Application

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
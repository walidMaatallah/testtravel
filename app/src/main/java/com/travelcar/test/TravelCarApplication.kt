package com.travelcar.test

import android.app.Application
import com.facebook.stetho.Stetho

class TravelCarApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        lateinit var appContext: TravelCarApplication
    }

}
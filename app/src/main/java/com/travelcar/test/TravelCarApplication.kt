package com.travelcar.test

import android.app.Application

class TravelCarApplication : Application() {



    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        fun geInstance(): TravelCarApplication {
            return TravelCarApplication()
        }

        lateinit var appContext: TravelCarApplication

        fun get(): TravelCarApplication {
            return appContext
        }
    }

}
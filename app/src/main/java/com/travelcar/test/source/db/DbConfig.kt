package com.travelcar.test.source.db

import android.app.Application
import androidx.room.Room

class DbConfig {

    companion object {
        private lateinit var INSTANCE: TravelCarDatabase
        fun getInstance(application: Application): TravelCarDatabase {
            synchronized(TravelCarDatabase::class) {
                INSTANCE = Room
                    .databaseBuilder(
                        application,
                        TravelCarDatabase::class.java,
                        TravelCarDatabase.DB_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }
}
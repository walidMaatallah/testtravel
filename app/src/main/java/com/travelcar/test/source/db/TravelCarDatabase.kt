package com.travelcar.test.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.travelcar.test.source.db.converters.EquipementsConverter
import com.travelcar.test.source.db.converters.TimeStampConverter
import com.travelcar.test.source.db.dao.CarDao
import com.travelcar.test.source.db.dao.UserDao
import com.travelcar.test.source.db.tables.CarEntity
import com.travelcar.test.source.db.tables.UserEntity

@Database(
    entities = [UserEntity::class, CarEntity::class],
    version = TravelCarDatabase.VERSION, exportSchema = true
)
@TypeConverters(TimeStampConverter::class, EquipementsConverter::class)
abstract class TravelCarDatabase : RoomDatabase() {

    abstract fun carDao(): CarDao
    abstract fun userDao(): UserDao

    companion object {
        const val DB_NAME = "travelcar.db"
        const val VERSION = 9
    }
}
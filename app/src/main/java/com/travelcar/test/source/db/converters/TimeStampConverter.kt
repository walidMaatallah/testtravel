package com.travelcar.test.source.db.converters

import androidx.room.TypeConverter
import java.util.*

class TimeStampConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
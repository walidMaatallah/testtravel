package com.travelcar.test.source.db.tables


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.travelcar.test.source.db.converters.TimeStampConverter
import java.util.*


@Entity
data class UserEntity(
    @PrimaryKey
    var id: Int,
    var firstName: String,
    var lastName: String,
    @TypeConverters(TimeStampConverter::class)
    var birthDay: Date?,
    var address: String
)
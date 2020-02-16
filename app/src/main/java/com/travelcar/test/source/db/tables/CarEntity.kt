package com.travelcar.test.source.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.travelcar.test.source.db.converters.EquipementsConverter

@Entity
data class CarEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var make: String,
    var model: String,
    var year: Int,
    var picture: String,
    @TypeConverters(EquipementsConverter::class)
    var equipement: List<String>?
)
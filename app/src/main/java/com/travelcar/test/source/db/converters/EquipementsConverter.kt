package com.travelcar.test.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

class EquipementsConverter {

    @TypeConverter
    fun stringToListCars(value: String?): List<String>? {
        return value?.let {
            val objects = Gson().fromJson(it, Array<String>::class.java)
            objects.toList()
        }
    }

    @TypeConverter
    fun listCarsToString(equipements: List<String>?): String? {
        return equipements?.let { Gson().toJson(it) }
    }
}
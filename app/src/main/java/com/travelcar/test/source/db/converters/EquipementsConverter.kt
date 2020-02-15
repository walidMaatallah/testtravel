package com.travelcar.test.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

class EquipementsConverter {

    @TypeConverter
    fun stringToListCars(value: String?): List<String>? {
        val objects = Gson().fromJson(value, Array<String>::class.java) as Array<String>
        return objects.toList()
    }

    @TypeConverter
    fun listCarsToString(equipements :  List<String>?): String? {
        return Gson().toJson(equipements)
    }
}
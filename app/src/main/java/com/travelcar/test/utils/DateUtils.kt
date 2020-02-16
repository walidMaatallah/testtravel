package com.travelcar.test.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {


    fun convertDateToString(date: Date?): String {
        date ?: return ""
        val format = SimpleDateFormat("dd/MM/yyy", Locale.getDefault())
        return format.format(date)
    }
}
package com.travelcar.test.ui.main.search

import com.travelcar.test.model.Car

interface CarListClickEventListener {
    fun onCarItemClick(car: Car)
}
package com.travelcar.test.ui.main.car.list

import androidx.appcompat.widget.AppCompatImageView
import com.travelcar.test.model.Car

interface CarListClickEventListener {
    fun onCarItemClick(car: Car, sharedElementView: AppCompatImageView)
}
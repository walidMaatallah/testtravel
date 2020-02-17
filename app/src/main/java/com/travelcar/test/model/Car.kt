package com.travelcar.test.model

import java.io.Serializable

data class Car(
    var make: String,
    var model: String,
    var year: Int,
    var picture: String,
    var equipments: List<String>?
) : Serializable
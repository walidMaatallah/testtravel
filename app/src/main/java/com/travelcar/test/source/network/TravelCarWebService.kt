package com.travelcar.test.source.network

import com.travelcar.test.model.Car
import retrofit2.http.GET

interface TravelCarWebService {

    @GET("/ncltg/6a74a0143a8202a5597ef3451bde0d5a/raw/8fa93591ad4c3415c9e666f888e549fb8f945eb7/tc-test-ios.json")
    suspend fun getAllCarsAsync() : List<Car>
}
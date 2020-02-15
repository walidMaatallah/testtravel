package com.travelcar.test.repository

import com.travelcar.test.model.Car
import com.travelcar.test.source.network.TravelCarWebService

class RemoteCarsRepository(private val travelCarWebService: TravelCarWebService) :
    BaseRepository() {

    //get latest news using safe api call
    suspend fun getRemoteCars(): MutableList<Car>? {
        return safeApiCall(
            //await the result of deferred type
            call = { travelCarWebService.getAllCarsAsync() },
            error = "Error fetching news"
            //convert to mutable list
        )?.toMutableList()
    }
}
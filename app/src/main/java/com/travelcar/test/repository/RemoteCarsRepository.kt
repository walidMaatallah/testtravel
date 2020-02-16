package com.travelcar.test.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.travelcar.test.mapper.CarMapper
import com.travelcar.test.model.Car
import com.travelcar.test.source.db.dao.CarDao
import com.travelcar.test.source.db.tables.CarEntity
import com.travelcar.test.source.network.TravelCarWebService
import java.io.IOException

class RemoteCarsRepository(
    private val travelCarWebService: TravelCarWebService,
    private val carDao: CarDao
) :
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


    fun getCar(): LiveData<List<Car>> = liveData {
        val disposable = emitSource(
            carDao.loadAllCars().map { lis -> CarMapper.mapCarEntityToCarDomain(lis.toList()) }
        )

        try {
            val cars = travelCarWebService.getAllCarsAsync().body()
            disposable.dispose()
            carDao.insertCars(CarMapper.mapCarDomainToCarEntity(cars!!))
            emitSource(
                carDao.loadAllCars().map { lis -> CarMapper.mapCarEntityToCarDomain(lis.toList()) }
            )
        } catch (exception: IOException) {
            emitSource(
                carDao.loadAllCars().map { listOf<Car>() }
            )
        }
    }
}
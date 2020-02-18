package com.travelcar.test.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.travelcar.test.mapper.CarMapper
import com.travelcar.test.model.Car
import com.travelcar.test.repository.base.BaseRepository
import com.travelcar.test.source.db.dao.CarDao
import com.travelcar.test.source.network.TravelCarWebService
import java.io.IOException

class RemoteCarsRepository(
    private val travelCarWebService: TravelCarWebService,
    private val carDao: CarDao
) : BaseRepository() {

    fun getCar(): LiveData<List<Car>> = liveData {
        val disposable = emitSource(
            carDao.loadAllCars().map { list -> CarMapper.mapCarEntityToCarDomain(list.toList()) }
        )
        try {
            val cars = travelCarWebService.getAllCarsAsync()
            // Stop the previous emission to avoid dispatching the updated user
            // as `loading`.
            disposable.dispose()
            // delete from db because there are no ids form ws
            carDao.deleteCars()
            // Update the database.
            carDao.insertCars(CarMapper.mapCarDomainToCarEntity(cars))
            emitSource(
                carDao.loadAllCars().map { lis -> CarMapper.mapCarEntityToCarDomain(lis.toList()) }
            )
        } catch (exception: IOException) {
            // Any call to `emit` disposes the previous one automatically so we don't
            // need to dispose it here as we didn't get an updated value.
            emitSource(
                carDao.loadAllCars().map { list -> CarMapper.mapCarEntityToCarDomain(list.toList()) })
        }
    }
}
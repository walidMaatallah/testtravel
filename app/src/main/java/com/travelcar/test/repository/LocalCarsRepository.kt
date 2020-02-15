package com.travelcar.test.repository

import com.travelcar.test.source.db.dao.CarDao
import com.travelcar.test.source.db.tables.UserEntity
import com.travelcar.test.source.db.dao.UserDao
import com.travelcar.test.source.db.tables.CarEntity

class LocalCarsRepository(private val dao: CarDao) :
    BaseRepository() {

    //get latest news using safe api call
    suspend fun getLocalCars(): Array<CarEntity>? {
        return dao.loadAllCars()
    }
}
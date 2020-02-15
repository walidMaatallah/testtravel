package com.travelcar.test.source.db.dao

import androidx.room.*
import com.travelcar.test.source.db.tables.CarEntity


@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCars(vararg cars: CarEntity)

    @Update
    suspend fun updateCars(vararg cars: CarEntity)

    @Delete
    suspend fun deleteCars(vararg cars: CarEntity)

    @Query("SELECT * FROM CarEntity")
    suspend fun loadAllCars(): Array<CarEntity>

    @Query("SELECT * FROM CarEntity WHERE id = :id")
    suspend fun selectCar(id: Int) : CarEntity


    @Query("DELETE FROM CarEntity")
    abstract fun deleteCar(car: CarEntity)

    @Insert
    abstract suspend fun insertCar(car: CarEntity)

}
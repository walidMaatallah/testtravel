package com.travelcar.test.source.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.travelcar.test.source.db.tables.CarEntity


@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCars(vararg cars: CarEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCars( cars: List<CarEntity>)

    @Update
    suspend fun updateCars(vararg cars: CarEntity)

    @Delete
    suspend fun deleteCars(vararg cars: CarEntity)

    @Query("SELECT * FROM CarEntity")
    fun loadAllCars(): LiveData<Array<CarEntity>>

}
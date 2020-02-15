package com.travelcar.test.mapper

import com.travelcar.test.model.Car
import com.travelcar.test.source.db.tables.CarEntity

object CarMapper {

    fun mapCarEntityToCarDomain(carEntity: CarEntity) : Car{
        return Car(carEntity.make, carEntity.model, carEntity.year, carEntity.picture, carEntity.equipement)
    }

    fun mapCarEntityToCarDomain(carEntitys: List<CarEntity>) : List<Car> {
        val listCars = ArrayList<Car>()
        carEntitys.forEach{
            listCars.add(mapCarEntityToCarDomain(it))
        }
        return listCars
    }
}
package com.travelcar.test.mapper

import com.travelcar.test.model.Car
import com.travelcar.test.source.db.tables.CarEntity

object CarMapper {

    private fun mapCarEntityToCarDomain(carEntity: CarEntity): Car {
        return Car(
            carEntity.make,
            carEntity.model,
            carEntity.year,
            carEntity.picture,
            carEntity.equipement
        )
    }

    fun mapCarEntityToCarDomain(carEntitys: List<CarEntity>): List<Car> {
        val listCars = ArrayList<Car>()
        carEntitys.forEach {
            listCars.add(mapCarEntityToCarDomain(it))
        }
        return listCars
    }


    private fun mapCarDomainToCarEntity(car: Car): CarEntity {
        return CarEntity(make =  car.make, model = car.model, year = car.year, picture = car.picture, equipement = car.equipement)
    }

    fun mapCarDomainToCarEntity(cars: List<Car>): List<CarEntity> {
        val listCars = ArrayList<CarEntity>()
        cars.forEach {
            listCars.add(mapCarDomainToCarEntity(it))
        }
        return listCars
    }
}
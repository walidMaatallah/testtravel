package com.travelcar.test.ui.main.car.list

import androidx.lifecycle.ViewModel
import com.travelcar.test.TravelCarApplication
import com.travelcar.test.repository.RemoteCarsRepository
import com.travelcar.test.source.db.DbConfig
import com.travelcar.test.source.network.WsConfig

class CarsListViewModel : ViewModel() {

    private val remoteCarsRepository: RemoteCarsRepository = RemoteCarsRepository(
        WsConfig.createService(),
        DbConfig.getInstance(TravelCarApplication.appContext).carDao()
    )

    fun getLatestNews() = remoteCarsRepository.getCar()
}

package com.travelcar.test.ui.main.car

import androidx.lifecycle.ViewModel
import com.travelcar.test.TravelCarApplication
import com.travelcar.test.repository.RemoteCarsRepository
import com.travelcar.test.source.network.DbConfig
import com.travelcar.test.source.network.WsConfig

class CarsListViewModel : ViewModel() {


    //initialize car repo
    private val remoteCarsRepository: RemoteCarsRepository =
        RemoteCarsRepository(
            WsConfig.createService(),
            DbConfig.getInstance(TravelCarApplication.appContext).carDao()
        )


    fun getLatestNews() = remoteCarsRepository.getCar()
}

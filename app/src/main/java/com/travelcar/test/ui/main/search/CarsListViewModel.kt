package com.travelcar.test.ui.main.search

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.travelcar.test.model.Car
import com.travelcar.test.repository.LocalCarsRepository
import com.travelcar.test.repository.RemoteCarsRepository
import com.travelcar.test.source.network.DbConfig
import com.travelcar.test.source.network.WsConfig
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CarsListViewModel : ViewModel() {

    //create a new Job
    private val parentJob = Job()
    //create a coroutine context with the job and the dispatcher
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    //create a coroutine scope with the coroutine context
    private val scope = CoroutineScope(coroutineContext)
    //initialize car repo
    private val remoteCarsRepository: RemoteCarsRepository =
        RemoteCarsRepository(WsConfig.createService())
    //initialize car local repo
    private val localCarsRepository: LocalCarsRepository =
        LocalCarsRepository(DbConfig.getInstance(Application()).carDao())
    //live data that will be populated as cars list
    val carsLiveData = MutableLiveData<MutableList<Car>>()



    fun getLatestNews() {
        ///launch the coroutine scope
        scope.launch {
            //get  cars from car repo
            val carsList = remoteCarsRepository.getRemoteCars()
            //post the value inside live data
            carsLiveData.postValue(carsList)
        }
    }



    fun cancelRequests() = coroutineContext.cancel()
}

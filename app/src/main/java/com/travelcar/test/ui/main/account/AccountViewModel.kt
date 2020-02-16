package com.travelcar.test.ui.main.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travelcar.test.TravelCarApplication
import com.travelcar.test.model.User
import com.travelcar.test.repository.UserRepository
import com.travelcar.test.source.network.DbConfig
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    private val userRepository by lazy {
        UserRepository(DbConfig.getInstance(TravelCarApplication.appContext).userDao())
    }

    fun getUser(): LiveData<User?> = userRepository.loadUser()

    fun saveUser(user: User) = viewModelScope.launch {
        userRepository.saveUser(user)
    }
}

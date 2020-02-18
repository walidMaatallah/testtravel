package com.travelcar.test.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.travelcar.test.mapper.UserMapper
import com.travelcar.test.model.User
import com.travelcar.test.source.db.dao.UserDao

class UserRepository(private val userDao: UserDao) {

    companion object {
        private const val USER_ID =
            1 // we choose to assign default id to user because there is no data loaded from remote for user
    }

    fun loadUser(): LiveData<User?> = liveData {
        val data = userDao.loadUserById(USER_ID)
            .map { userEntity -> UserMapper.mapUserEntityToUserDomain(userEntity) }
        emitSource(data)
    }

    suspend fun saveUser(user: User) =
        userDao.insertUser(UserMapper.mapUserDomainToUserEntity(user))

}
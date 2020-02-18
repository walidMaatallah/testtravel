package com.travelcar.test.source.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.travelcar.test.source.db.tables.UserEntity


@Dao
interface UserDao {

    @Query("SELECT * FROM UserEntity WHERE id =:id")
    fun loadUserById(id: Int): LiveData<UserEntity>

    @Update
    suspend fun updateUsers(vararg users: UserEntity)

    @Delete
    suspend fun deleteUsers(vararg users: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

}
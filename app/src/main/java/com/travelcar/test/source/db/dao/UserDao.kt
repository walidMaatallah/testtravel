package com.travelcar.test.source.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.travelcar.test.source.db.tables.UserEntity


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(vararg users: UserEntity)

    @Query("SELECT * FROM UserEntity WHERE id =:id")
    fun loadUser(id: Int): LiveData<UserEntity>

    @Update
    suspend fun updateUsers(vararg users: UserEntity)

    @Delete
    suspend fun deleteUsers(vararg users: UserEntity)

    @Insert
    suspend fun insertUser(user: UserEntity)

}
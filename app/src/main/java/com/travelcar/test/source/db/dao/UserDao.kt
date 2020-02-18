package com.travelcar.test.source.db.dao

import androidx.room.*
import com.travelcar.test.source.db.tables.UserEntity


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM UserEntity WHERE id =:id")
    suspend fun loadUserById(id: Int): UserEntity

    @Update
    suspend fun updateUsers(vararg users: UserEntity)

    @Delete
    suspend fun deleteUsers(vararg users: UserEntity)
}
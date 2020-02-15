package com.travelcar.test.source.db.dao

import androidx.room.*
import com.travelcar.test.source.db.tables.UserEntity


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(vararg users: UserEntity)

    @Update
    suspend fun updateUsers(vararg users: UserEntity)

    @Delete
    suspend fun deleteUsers(vararg users: UserEntity)

    @Query("SELECT * FROM UserEntity")
    suspend fun loadAllUsers(): Array<UserEntity>

    @Transaction
    open suspend fun setLoggedInUser(loggedInUser: UserEntity) {
        deleteUser(loggedInUser)
        insertUser(loggedInUser)
    }


    @Query("SELECT * FROM UserEntity WHERE id = :id")
    suspend fun selectUser(id: Int) : UserEntity


    @Query("DELETE FROM UserEntity")
    abstract fun deleteUser(user: UserEntity)

    @Insert
    abstract suspend fun insertUser(user: UserEntity)

}
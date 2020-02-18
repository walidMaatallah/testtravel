package com.travelcar.test.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.travelcar.test.MainCoroutineRule
import com.travelcar.test.source.db.TravelCarDatabase
import com.travelcar.test.source.db.tables.UserEntity
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {

    private lateinit var database: TravelCarDatabase

    //set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    //Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        //using an in-memory database because the information stored here disappears when
        //the process is killed
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            TravelCarDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun testInsertUserAndGetById() = runBlockingTest {
        //given
        val user = UserEntity(
            id = 1,
            firstName = "Slim",
            lastName = "Tonnech",
            birthDay = Date(),
            address = "10 av x 12033"
        )
        database.userDao().insertUser(user)

        //when
        val loaded = database.userDao().loadUserById(user.id)

        //then
        assertThat(loaded, notNullValue())
        assertThat(loaded.id, `is`(user.id))
        assertThat(loaded.firstName, `is`(user.firstName))
        assertThat(loaded.lastName, `is`(user.lastName))
        assertThat(loaded.birthDay, `is`(user.birthDay))
        assertThat(loaded.address, `is`(user.address))
    }

    @Test
    fun testInsertUserReplacesOnConflict() = runBlockingTest {
        //given
        val user = UserEntity(
            id = 1,
            firstName = "firstName1",
            lastName = "lastName1",
            birthDay = Date(),
            address = "Address1"
        )
        database.userDao().insertUser(user)

        //when a user with same id is inserted
        val newUser = UserEntity(
            id = user.id,
            firstName = "firstName2",
            lastName = "lastName2",
            birthDay = Date(),
            address = "Address2"
        )
        database.userDao().insertUser(newUser)

        //then - the loaded data contains the expected values
        val loaded = database.userDao().loadUserById(user.id)
        assertThat(loaded.id, `is`(user.id))
        assertThat(loaded.firstName, `is`("firstName2"))
        assertThat(loaded.lastName, `is`("lastName2"))
        assertThat(loaded.birthDay, `is`(newUser.birthDay))
        assertThat(loaded.address, `is`("Address2"))
    }

    @Test
    fun testUpdateUserAndGetById() = runBlockingTest {
        //given
        val originalUser = UserEntity(
            id = 1,
            firstName = "firstName",
            lastName = "lastName",
            birthDay = Date(),
            address = "Address"
        )
        database.userDao().insertUser(originalUser)

        //when user updated
        val updatedUser = UserEntity(
            id = originalUser.id,
            firstName = "new firstName",
            lastName = "new lastName",
            birthDay = Date(),
            address = "new Address"
        )
        database.userDao().updateUsers(updatedUser)

        //then
        val loaded = database.userDao().loadUserById(originalUser.id)
        assertThat(loaded.id, `is`(originalUser.id))
        assertThat(loaded.firstName, `is`(updatedUser.firstName))
        assertThat(loaded.lastName, `is`(updatedUser.lastName))
        assertThat(loaded.birthDay, `is`(updatedUser.birthDay))
        assertThat(loaded.address, `is`(updatedUser.address))
    }

    @Test
    fun testDeleteUserAndGettingUser() = runBlockingTest {
        //given
        //given
        val user = UserEntity(
            id = 1,
            firstName = "Slim",
            lastName = "Tonnech",
            birthDay = Date(),
            address = "10 av x 12033"
        )
        database.userDao().insertUser(user)

        //when deleting user
        database.userDao().deleteUsers(user)

        //then - user should be empty
        val loaded = database.userDao().loadUserById(user.id)
        assertEquals(loaded, null)
    }

}
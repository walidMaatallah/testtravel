package com.travelcar.test

import android.content.Context
import androidx.room.Room
import com.travelcar.test.mapper.UserMapper
import com.travelcar.test.model.User
import com.travelcar.test.source.db.TravelCarDatabase
import com.travelcar.test.source.db.dao.UserDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.*

@RunWith(MockitoJUnitRunner::class)

class DatabaseUnitTest {

    private lateinit var userDao: UserDao
    private lateinit var db: TravelCarDatabase
    lateinit var instrumentationContext: Context
    val context = mock(Context::class.java)

    val userTest = User(1, "TOTO", "LAST", Date(), "10 Allee louis aragon, Noisy le grand")

    @Before
    fun createDb() {

        db = Room.inMemoryDatabaseBuilder(context, TravelCarDatabase::class.java).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() = runBlocking {
        val user: User = userTest.apply {
            firstName = "slim"
        }
        userDao.insertUser(UserMapper.mapUserDomainToUserEntity(user))
        val byId = userDao.loadUserById(1)
        assertThat(UserMapper.mapUserDomainToUserEntity(userTest), equalTo(UserMapper.mapUserDomainToUserEntity(user)))
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

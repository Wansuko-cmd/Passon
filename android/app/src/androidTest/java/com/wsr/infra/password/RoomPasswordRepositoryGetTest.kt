@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.password

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wsr.infra.PassonDatabase
import com.wsr.password.Password
import com.wsr.utils.UniqueId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RoomPasswordRepositoryGetTest {
    private lateinit var passwordEntityDao: PasswordEntityDao
    private lateinit var db: PassonDatabase
    private lateinit var target: RoomPasswordRepositoryImpl

    @BeforeTest
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PassonDatabase::class.java).build()
        passwordEntityDao = db.passwordEntityDao()

        target = RoomPasswordRepositoryImpl(passwordEntityDao)
    }

    @AfterTest
    fun closeDB() {
        db.close()
    }

    /*** getAllByPasswordGroupId関数 ***/
    @Test
    fun passwordGroupIdを渡すと所属する全てのPasswordGroupを返す() = runTest {
        val mockedPasswordGroupId = UniqueId.of("mockedPasswordGroupId")
        val mockedPasswords = List(5) { index ->
            Password.of(
                id = UniqueId.of("mockedPasswordId$index"),
                passwordGroupId = mockedPasswordGroupId,
                name = "mockedName$index",
                password = "mockedPassword$index"
            )
        }

        mockedPasswords.forEach { target.upsert(it) }

        val actual = target.getAllByPasswordGroupId(mockedPasswordGroupId)
        assertThat(actual).isEqualTo(mockedPasswords)
    }
}

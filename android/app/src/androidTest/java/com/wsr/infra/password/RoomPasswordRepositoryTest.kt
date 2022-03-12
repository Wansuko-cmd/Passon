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
class RoomPasswordRepositoryTest {
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


    @Test
    fun upsertのinsert機能とgetAllByPasswordGroupIdの動作確認() = runTest {

        val mockedPassword = Password(
            id = UniqueId("mockedPasswordId"),
            passwordGroupId = UniqueId("mockedPasswordGroupId"),
            name = "mockedName",
            password = "mockedPassword"
        )
        target.upsert(mockedPassword)

        val actual = target.getAllByPasswordGroupId(mockedPassword.passwordGroupId)
        assertThat(actual).contains(mockedPassword)
    }


    @Test
    fun upsertのupdate機能とgetAllByPasswordGroupIdの動作確認() = runTest {

        val mockedPasswordGroupId = UniqueId("mockedPasswordGroupId")

        val mockedPassword = Password(
            id = UniqueId("mockedPasswordId"),
            passwordGroupId = mockedPasswordGroupId,
            name = "mockedName",
            password = "mockedPassword"
        )
        target.upsert(mockedPassword)

        val updatedMockedPassword = mockedPassword.copy(
            name = "updatedMockedName",
            password = "updatedMockedPassword",
        )
        target.upsert(updatedMockedPassword)

        val actual = target.getAllByPasswordGroupId(mockedPasswordGroupId)
        assertThat(actual).contains(updatedMockedPassword)
    }

    @Test
    fun deleteとgetAllByPasswordGroupIdの動作確認() = runTest {

        val mockedPasswordId = UniqueId("mockedPasswordId")
        val mockedPasswordGroupId = UniqueId("mockedPasswordGroupId")
        val mockedPassword = Password(
            id = mockedPasswordId,
            passwordGroupId = mockedPasswordGroupId,
            name = "mockedName",
            password = "mockedPassword"
        )
        target.upsert(mockedPassword)

        target.delete(mockedPasswordId)

        val actual = target.getAllByPasswordGroupId(mockedPasswordGroupId)
        assertThat(actual).isEmpty()
    }
}
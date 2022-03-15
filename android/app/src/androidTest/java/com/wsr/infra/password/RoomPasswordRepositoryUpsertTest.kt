@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.password

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.wsr.infra.PassonDatabase
import com.wsr.password.Password
import com.wsr.utils.UniqueId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RoomPasswordRepositoryUpsertTest {
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

    /*** upsert関数 ***/
    @Test
    fun 存在しないpasswordIdを持つ新しいPasswordGroupの情報を渡せば新規作成する() = runTest {

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
    fun 存在するpasswordIdを持つPasswordGroupの情報を渡せば更新を行う() = runTest {

        val mockedPasswordId = UniqueId("mockedPasswordId")
        val mockedPasswordGroupId = UniqueId("mockedPasswordGroupId")

        val mockedPassword = Password(
            id = mockedPasswordId,
            passwordGroupId = mockedPasswordGroupId,
            name = "mockedName",
            password = "mockedPassword"
        )
        target.upsert(mockedPassword)

        val updatedMockedPassword = Password(
            id = mockedPasswordId,
            passwordGroupId = mockedPasswordGroupId,
            name = "updatedMockedName",
            password = "updatedMockedPassword",
        )
        target.upsert(updatedMockedPassword)

        val actual = target.getAllByPasswordGroupId(mockedPasswordGroupId)
        assertThat(actual).contains(updatedMockedPassword)
    }
}

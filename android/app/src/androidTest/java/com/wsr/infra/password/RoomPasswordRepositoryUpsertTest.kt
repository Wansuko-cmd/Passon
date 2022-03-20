@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.password

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.wsr.infra.PassonDatabase
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordpair.Name
import com.wsr.passwordpair.Password
import com.wsr.passwordpair.PasswordPair
import com.wsr.passwordpair.PasswordPairId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RoomPasswordRepositoryUpsertTest {
    private lateinit var passwordEntityDao: PasswordPairEntityDao
    private lateinit var db: PassonDatabase
    private lateinit var target: RoomPasswordPairRepositoryImpl

    @BeforeTest
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PassonDatabase::class.java).build()
        passwordEntityDao = db.passwordEntityDao()

        target = RoomPasswordPairRepositoryImpl(passwordEntityDao)
    }

    @AfterTest
    fun closeDB() {
        db.close()
    }

    /*** upsert関数 ***/
    @Test
    fun 存在しないpasswordIdを持つ新しいPasswordGroupの情報を渡せば新規作成する() = runTest {

        val mockedPassword = PasswordPair(
            id = PasswordPairId("mockedPasswordId"),
            passwordGroupId = PasswordGroupId("mockedPasswordGroupId"),
            name = Name("mockedName"),
            password = Password("mockedPassword"),
        )
        target.upsert(mockedPassword)

        val actual = target.getAllByPasswordGroupId(mockedPassword.passwordGroupId)
        assertThat(actual).contains(mockedPassword)
    }

    @Test
    fun 存在するpasswordIdを持つPasswordGroupの情報を渡せば更新を行う() = runTest {

        val mockedPasswordId = PasswordPairId("mockedPasswordId")
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")

        val mockedPassword = PasswordPair(
            id = mockedPasswordId,
            passwordGroupId = mockedPasswordGroupId,
            name = Name("mockedName"),
            password = Password("mockedPassword"),
        )
        target.upsert(mockedPassword)

        val updatedMockedPassword = PasswordPair(
            id = mockedPasswordId,
            passwordGroupId = mockedPasswordGroupId,
            name = Name("updatedMockedName"),
            password = Password("updatedMockedPassword"),
        )
        target.upsert(updatedMockedPassword)

        val actual = target.getAllByPasswordGroupId(mockedPasswordGroupId)
        assertThat(actual).contains(updatedMockedPassword)
    }
}

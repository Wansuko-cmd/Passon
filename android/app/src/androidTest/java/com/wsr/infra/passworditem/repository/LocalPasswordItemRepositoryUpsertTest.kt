@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.passworditem.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.wsr.infra.PassonDatabase
import com.wsr.infra.passworditem.LocalPasswordItemRepositoryImpl
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.Name
import com.wsr.passworditem.Password
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocalPasswordItemRepositoryUpsertTest {
    private lateinit var passwordEntityDao: PasswordItemEntityDao
    private lateinit var db: PassonDatabase
    private lateinit var target: LocalPasswordItemRepositoryImpl

    @BeforeTest
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PassonDatabase::class.java).build()
        passwordEntityDao = db.passwordEntityDao()

        target = LocalPasswordItemRepositoryImpl(passwordEntityDao)
    }

    @AfterTest
    fun closeDB() {
        db.close()
    }

    /*** upsert関数 ***/
    @Test
    fun 存在しないpasswordItemIdを持つ新しいPasswordGroupの情報を渡せば新規作成する() = runTest {

        val mockedPassword = PasswordItem(
            id = PasswordItemId("mockedpasswordItemId"),
            passwordGroupId = PasswordGroupId("mockedPasswordGroupId"),
            name = Name("mockedName"),
            password = Password("mockedPassword"),
        )
        target.upsert(mockedPassword)

        val actual = passwordEntityDao.getAllByPasswordGroupId(mockedPassword.passwordGroupId.value)
        assertThat(actual).contains(mockedPassword)
    }

    @Test
    fun 存在するpasswordItemIdを持つPasswordGroupの情報を渡せば更新を行う() = runTest {

        val mockedPasswordItemId = PasswordItemId("mockedpasswordItemId")
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")

        val mockedPassword = PasswordItem(
            id = mockedPasswordItemId,
            passwordGroupId = mockedPasswordGroupId,
            name = Name("mockedName"),
            password = Password("mockedPassword"),
        )
        target.upsert(mockedPassword)

        val updatedMockedPassword = PasswordItem(
            id = mockedPasswordItemId,
            passwordGroupId = mockedPasswordGroupId,
            name = Name("updatedMockedName"),
            password = Password("updatedMockedPassword"),
        )
        target.upsert(updatedMockedPassword)

        val actual = passwordEntityDao.getAllByPasswordGroupId(mockedPasswordGroupId.value)
        assertThat(actual).contains(updatedMockedPassword)
    }
}

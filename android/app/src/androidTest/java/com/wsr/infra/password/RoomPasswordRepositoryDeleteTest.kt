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

class RoomPasswordRepositoryDeleteTest {
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


    /*** delete関数 ***/
    @Test
    fun passwordIdを渡すと対応するPasswordを削除する() = runTest {

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
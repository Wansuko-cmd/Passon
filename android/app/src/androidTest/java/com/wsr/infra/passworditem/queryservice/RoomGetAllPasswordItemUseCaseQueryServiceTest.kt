@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.passworditem.queryservice

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wsr.infra.PassonDatabase
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.infra.passworditem.toEntity
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.Name
import com.wsr.passworditem.Password
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RoomGetAllPasswordItemUseCaseQueryServiceTest {
    private lateinit var passwordEntityDao: PasswordItemEntityDao
    private lateinit var db: PassonDatabase
    private lateinit var target: LocalGetAllPasswordItemUseCaseQueryServiceImpl

    @BeforeTest
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PassonDatabase::class.java).build()
        passwordEntityDao = db.passwordEntityDao()

        target = LocalGetAllPasswordItemUseCaseQueryServiceImpl(passwordEntityDao)
    }

    @AfterTest
    fun closeDB() {
        db.close()
    }

    /*** getAllByPasswordGroupId関数 ***/
    @Test
    fun passwordGroupIdを渡すと所属する全てのPasswordGroupを返す() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedPasswordItems = List(5) { index ->
            PasswordItem(
                id = PasswordItemId("mockedpasswordItemId$index"),
                passwordGroupId = mockedPasswordGroupId,
                name = Name("mockedName$index"),
                password = Password("mockedPassword$index"),
            )
        }

        mockedPasswordItems.forEach { passwordEntityDao.upsert(it.toEntity()) }

        val actual = target.getAllByPasswordGroupId(mockedPasswordGroupId)
        assertThat(actual).isEqualTo(mockedPasswordItems)
    }
}

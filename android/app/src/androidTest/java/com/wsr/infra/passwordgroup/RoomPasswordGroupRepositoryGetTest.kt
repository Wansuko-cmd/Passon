@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.passwordgroup

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wsr.infra.PassonDatabase
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.user.Email
import com.wsr.utils.UniqueId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RoomPasswordGroupRepositoryGetTest {
    private lateinit var passwordGroupEntityDao: PasswordGroupEntityDao
    private lateinit var db: PassonDatabase
    private lateinit var target: RoomPasswordGroupRepositoryImpl

    @BeforeTest
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PassonDatabase::class.java).build()
        passwordGroupEntityDao = db.passwordGroupEntityDao()

        target = RoomPasswordGroupRepositoryImpl(passwordGroupEntityDao)
    }

    @AfterTest
    fun closeDB() {
        db.close()
    }

    /*** getAllByEmail関数 ***/
    @Test
    fun Emailを渡せば所属するPasswordGroupIdを返す() = runTest {
        val mockedEmail = Email("mockedEmail")
        val mockedPasswordGroups = List(5) { index ->
            PasswordGroup(
                id = UniqueId("mockedPasswordGroupId$index"),
                email = mockedEmail,
                title = "mockedTitle$index",
                remark = "mockedRemark$index",
            )
        }
        mockedPasswordGroups.forEach { target.create(it) }

        val actual = target.getAllByEmail(mockedEmail)
        assertThat(actual).isEqualTo(mockedPasswordGroups)
    }

    /*** getById関数 ***/
    @Test
    fun passwordGroupIdを渡せば対応するPasswordGroupを返す() = runTest {
        val mockedPasswordGroupId = UniqueId("mockedPasswordGroupId")
        val mockedPasswordGroup = PasswordGroup(
            id = mockedPasswordGroupId,
            email = Email("mockedEmail"),
            title = "mockedTitle",
            remark = "mockedRemark",
        )
        target.create(mockedPasswordGroup)

        val actual = target.getById(mockedPasswordGroupId)
        assertThat(actual).isEqualTo(mockedPasswordGroup)
    }
}
@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.passwordgroup

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wsr.email.Email
import com.wsr.exceptions.GetDataFailedException
import com.wsr.infra.PassonDatabase
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.utils.UniqueId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

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
        val mockedEmail = Email.from("mockedEmail")
        val mockedPasswordGroups = List(5) { index ->
            PasswordGroup.of(
                id = UniqueId.from("mockedPasswordGroupId$index"),
                email = mockedEmail,
                title = "mockedTitle$index",
                remark = "mockedRemark$index",
            )
        }
        mockedPasswordGroups.forEach { target.create(it) }
        val notTargetMockedPasswordGroups = List(5) { index ->
            PasswordGroup.of(
                id = UniqueId.from("notTargetMockedPasswordGroupId$index"),
                email = Email.from("notTargetMockedEmail$index"),
                title = "notTargetMockedTitle$index",
                remark = "notTargetMockedRemark$index",
            )
        }
        notTargetMockedPasswordGroups.forEach { target.create(it) }

        val actual = target.getAllByEmail(mockedEmail)
        assertThat(actual).isEqualTo(mockedPasswordGroups)
    }

    /*** getById関数 ***/
    @Test
    fun passwordGroupIdを渡せば対応するPasswordGroupを返す() = runTest {
        val mockedPasswordGroupId = UniqueId.from("mockedPasswordGroupId")
        val mockedPasswordGroup = PasswordGroup.of(
            id = mockedPasswordGroupId,
            email = Email.from("mockedEmail"),
            title = "mockedTitle",
            remark = "mockedRemark",
        )
        target.create(mockedPasswordGroup)

        val notTargetMockedPasswordGroup = PasswordGroup.of(
            id = UniqueId.from("notTargetPasswordGroupId"),
            email = Email.from("notTargetMockedEmail"),
            title = "notTargetMockedTitle",
            remark = "notTargetMockedRemark",
        )
        target.create(notTargetMockedPasswordGroup)

        val actual = target.getById(mockedPasswordGroupId)
        assertThat(actual).isEqualTo(mockedPasswordGroup)
    }

    @Test
    fun 存在しないpasswordGroupIdを渡せばNoSuchElementExceptionが投げられる() = runTest {
        val mockedPasswordGroupId = UniqueId.from("mockedPasswordGroupId")

        assertFailsWith<GetDataFailedException.NoSuchElementException> {
            target.getById(mockedPasswordGroupId)
        }
    }
}

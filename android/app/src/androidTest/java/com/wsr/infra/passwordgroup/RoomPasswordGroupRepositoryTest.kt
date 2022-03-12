@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.passwordgroup

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wsr.exceptions.GetDataFailedException
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
import kotlin.test.assertFailsWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RoomPasswordGroupRepositoryTest {
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


    @Test
    fun createとgetAllByEmailの動作確認() = runTest {
        val mockedEmail = Email("mockedEmail")
        val mockedPasswordGroup = PasswordGroup(
            id = UniqueId("mockedPasswordGroupId"),
            email = mockedEmail,
            title = "mockedTitle",
            remark = "mockedRemark",
        )
        target.create(mockedPasswordGroup)

        val actual = target.getAllByEmail(mockedEmail)
        assertThat(actual).contains(mockedPasswordGroup)
    }

    @Test
    fun updateとgetByIdの動作確認() = runTest {
        val mockedPasswordGroupId = UniqueId("mockedPasswordGroupId")
        val mockedPasswordGroup = PasswordGroup(
            id = mockedPasswordGroupId,
            email = Email("mockedEmail"),
            title = "mockedTitle",
            remark = "mockedRemark",
        )
        target.create(mockedPasswordGroup)

        val updatedMockedPasswordGroup = mockedPasswordGroup.copy(
            title = "updatedMockedTitle",
            remark = "updatedMockedRemark",
        )
        target.update(
            id = updatedMockedPasswordGroup.id,
            title = updatedMockedPasswordGroup.title,
            remark = updatedMockedPasswordGroup.remark,
        )

        val actual = target.getById(mockedPasswordGroupId)
        assertThat(actual).isEqualTo(updatedMockedPasswordGroup)
    }

    @Test
    fun deleteとgetByIdの動作確認() = runTest {
        val mockedPasswordGroupId = UniqueId("mockedPasswordGroupId")
        val mockedPasswordGroup = PasswordGroup(
            id = mockedPasswordGroupId,
            email = Email("mockedEmail"),
            title = "mockedTitle",
            remark = "mockedRemark",
        )
        target.create(mockedPasswordGroup)

        target.delete(mockedPasswordGroupId)

        assertFailsWith<GetDataFailedException.NoSuchElementException> {
            target.getById(
                mockedPasswordGroupId
            )
        }
    }
}
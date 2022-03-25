@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.passwordgroup.queryservice

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.wsr.email.Email
import com.wsr.exceptions.GetDataFailedException
import com.wsr.infra.PassonDatabase
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passwordgroup.toEntity
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.Remark
import com.wsr.passwordgroup.Title
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RoomUpdatePasswordGroupUseCaseQueryServiceTest {

    private lateinit var passwordGroupEntityDao: PasswordGroupEntityDao
    private lateinit var db: PassonDatabase
    private lateinit var target: RoomUpdatePasswordGroupUseCaseQueryServiceImpl

    @BeforeTest
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PassonDatabase::class.java).build()
        passwordGroupEntityDao = db.passwordGroupEntityDao()

        target = RoomUpdatePasswordGroupUseCaseQueryServiceImpl(passwordGroupEntityDao)
    }

    @AfterTest
    fun closeDB() {
        db.close()
    }

    /*** getById関数 ***/
    @Test
    fun passwordGroupIdを渡せば対応するPasswordGroupを返す() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedPasswordGroup = PasswordGroup(
            id = mockedPasswordGroupId,
            email = Email("mockedEmail"),
            title = Title("mockedTitle"),
            remark = Remark("mockedRemark"),
        )
        passwordGroupEntityDao.insert(mockedPasswordGroup.toEntity())

        val notTargetMockedPasswordGroup = PasswordGroup(
            id = PasswordGroupId("notTargetPasswordGroupId"),
            email = Email("notTargetMockedEmail"),
            title = Title("notTargetMockedTitle"),
            remark = Remark("notTargetMockedRemark"),
        )
        passwordGroupEntityDao.insert(notTargetMockedPasswordGroup.toEntity())

        val actual = target.getById(mockedPasswordGroupId)
        Truth.assertThat(actual).isEqualTo(mockedPasswordGroup)
    }

    @Test
    fun 存在しないpasswordGroupIdを渡せばNoSuchElementExceptionが投げられる() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")

        assertFailsWith<GetDataFailedException.NoSuchElementException> {
            target.getById(mockedPasswordGroupId)
        }
    }
}

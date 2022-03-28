@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.queryservice.fetch

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wsr.email.Email
import com.wsr.infra.PassonDatabase
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passwordgroup.toEntity
import com.wsr.infra.queryservice.LocalFetchAllPasswordGroupUseCaseQueryServiceImpl
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.Remark
import com.wsr.passwordgroup.Title
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class LocalFetchAllPasswordGroupUseCaseQueryServiceTest {
    private lateinit var passwordGroupEntityDao: PasswordGroupEntityDao
    private lateinit var db: PassonDatabase
    private lateinit var target: LocalFetchAllPasswordGroupUseCaseQueryServiceImpl

    @BeforeTest
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PassonDatabase::class.java).build()
        passwordGroupEntityDao = db.passwordGroupEntityDao()

        target = LocalFetchAllPasswordGroupUseCaseQueryServiceImpl(passwordGroupEntityDao)
    }

    @AfterTest
    fun closeDB() {
        db.close()
    }

    /*** getAllPasswordGroup関数 ***/
    @Test
    fun Emailを渡せば所属するPasswordGroupを返す() = runTest {
        val mockedEmail = Email("mockedEmail")
        val mockedPasswordGroups = List(5) { index ->
            PasswordGroup(
                id = PasswordGroupId("mockedPasswordGroupId$index"),
                email = mockedEmail,
                title = Title("mockedTitle$index"),
                remark = Remark("mockedRemark$index"),
            )
        }
        mockedPasswordGroups.forEach { passwordGroupEntityDao.insert(it.toEntity()) }
        val notTargetMockedPasswordGroups = List(5) { index ->
            PasswordGroup(
                id = PasswordGroupId("notTargetMockedPasswordGroupId$index"),
                email = Email("notTargetMockedEmail$index"),
                title = Title("notTargetMockedTitle$index"),
                remark = Remark("notTargetMockedRemark$index"),
            )
        }
        notTargetMockedPasswordGroups.forEach { passwordGroupEntityDao.insert(it.toEntity()) }

        val actual = target.getAllPasswordGroup(mockedEmail)
        assertThat(actual).isEqualTo(mockedPasswordGroups)
    }
}

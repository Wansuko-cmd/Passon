@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.queryservice.fetch

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wsr.PasswordPairUseCaseModel
import com.wsr.infra.PassonDatabase
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passwordgroup.toEntity
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.infra.passworditem.toEntity
import com.wsr.infra.queryservice.LocalFetchPasswordPairUseCaseQueryServiceImpl
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.Remark
import com.wsr.passwordgroup.Title
import com.wsr.passworditem.Name
import com.wsr.passworditem.Password
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId
import com.wsr.state.State
import com.wsr.toUseCaseModel
import com.wsr.user.Email
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class LocalFetchPasswordPairUseCaseQueryServiceImplTest {
    private lateinit var passwordGroupEntityDao: PasswordGroupEntityDao
    private lateinit var passwordItemEntityDao: PasswordItemEntityDao
    private lateinit var db: PassonDatabase
    private lateinit var target: LocalFetchPasswordPairUseCaseQueryServiceImpl

    @BeforeTest
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PassonDatabase::class.java).build()
        passwordGroupEntityDao = db.passwordGroupEntityDao()
        passwordItemEntityDao = db.passwordItemEntityDao()

        target = LocalFetchPasswordPairUseCaseQueryServiceImpl(passwordGroupEntityDao, passwordItemEntityDao)
    }

    @AfterTest
    fun closeDB() {
        db.close()
    }

    /*** getPasswordSet関数 ***/
    @Test
    fun passwordGroupIdを渡せば対応するPasswordGroupと所属するPasswordItemを返す() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedPasswordGroup = PasswordGroup(
            id = mockedPasswordGroupId,
            email = Email("mockedEmail"),
            title = Title("mockedTitle"),
            remark = Remark("mockedRemark"),
        )
        passwordGroupEntityDao.insert(mockedPasswordGroup.toEntity())
        val notTargetMockedPasswordGroups = List(5) { index ->
            PasswordGroup(
                id = PasswordGroupId("notTargetMockedPasswordGroupId$index"),
                email = Email("notTargetMockedEmail$index"),
                title = Title("notTargetMockedTitle$index"),
                remark = Remark("notTargetMockedRemark$index"),
            )
        }
        notTargetMockedPasswordGroups.forEach { passwordGroupEntityDao.insert(it.toEntity()) }

        val mockedPasswordItems = List(5) { index ->
            PasswordItem(
                id = PasswordItemId("mockedPasswordItemId$index"),
                passwordGroupId = mockedPasswordGroupId,
                name = Name("mockedName$index"),
                password = Password("mockedPassword$index"),
            )
        }
        mockedPasswordItems.forEach { passwordItemEntityDao.upsert(it.toEntity()) }
        val notTargetMockedPasswordItems = List(5) { index ->
            PasswordItem(
                id = PasswordItemId("notMockedPasswordItemId$index"),
                passwordGroupId = mockedPasswordGroupId,
                name = Name("notMockedName$index"),
                password = Password("notMockedPassword$index"),
            )
        }
        notTargetMockedPasswordItems.forEach { passwordItemEntityDao.upsert(it.toEntity()) }

        val actual = target.getPasswordPair(mockedPasswordGroupId)
        val expected = PasswordPairUseCaseModel(
            passwordGroup = mockedPasswordGroup.toUseCaseModel(),
            passwordItems = mockedPasswordItems.map { it.toUseCaseModel() },
        )

        assertThat(actual).isEqualTo(State.Success(expected))
    }
}

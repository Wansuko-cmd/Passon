@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.passwordgroup

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wsr.email.Email
import com.wsr.infra.PassonDatabase
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
class LocalPasswordGroupRepositoryUpdateTest {
    private lateinit var passwordGroupEntityDao: PasswordGroupEntityDao
    private lateinit var db: PassonDatabase
    private lateinit var target: LocalPasswordGroupRepositoryImpl

    @BeforeTest
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PassonDatabase::class.java).build()
        passwordGroupEntityDao = db.passwordGroupEntityDao()

        target = LocalPasswordGroupRepositoryImpl(passwordGroupEntityDao)
    }

    @AfterTest
    fun closeDB() {
        db.close()
    }

    /*** update関数 ***/
    @Test
    fun 新しいPasswordGroupの情報を渡すと指定されたPasswordGroupの更新を行う() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedPasswordGroup = PasswordGroup(
            id = mockedPasswordGroupId,
            email = Email("mockedEmail"),
            title = Title("mockedTitle"),
            remark = Remark("mockedRemark"),
        )
        target.create(mockedPasswordGroup)

        val updatedMockedPasswordGroup = mockedPasswordGroup.copy(
            title = Title("updatedMockedTitle"),
            remark = Remark("updatedMockedRemark"),
        )
        target.update(
            id = updatedMockedPasswordGroup.id,
            title = updatedMockedPasswordGroup.title.value,
            remark = updatedMockedPasswordGroup.remark.value,
        )

        val actual = passwordGroupEntityDao.getById(mockedPasswordGroupId.value)
        assertThat(actual).isEqualTo(updatedMockedPasswordGroup)
    }
}

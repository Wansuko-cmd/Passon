@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.passwordgroup

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wsr.user.Email
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
class LocalPasswordGroupRepositoryDeleteTest {
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

    /*** delete関数 ***/
    @Test
    fun passwordGroupIdを渡すと対応するPasswordGroupを削除する() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedPasswordGroup = PasswordGroup(
            id = mockedPasswordGroupId,
            email = Email("mockedEmail"),
            title = Title("mockedTitle"),
            remark = Remark("mockedRemark"),
        )
        target.create(mockedPasswordGroup)

        target.delete(mockedPasswordGroupId)

        assertThat(passwordGroupEntityDao.getById(mockedPasswordGroupId.value)).isNull()
    }
}

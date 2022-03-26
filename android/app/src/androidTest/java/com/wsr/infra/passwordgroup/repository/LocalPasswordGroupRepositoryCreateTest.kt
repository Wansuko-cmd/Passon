@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.passwordgroup.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wsr.email.Email
import com.wsr.infra.PassonDatabase
import com.wsr.infra.passwordgroup.LocalPasswordGroupRepositoryImpl
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
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
class LocalPasswordGroupRepositoryCreateTest {
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

    /*** create関数 ***/
    @Test
    fun 新しいPasswordGroupの情報を渡せば登録する() = runTest {
        val mockedEmail = Email("mockedEmail")
        val mockedPasswordGroup = PasswordGroup(
            id = PasswordGroupId("mockedPasswordGroupId"),
            email = mockedEmail,
            title = Title("mockedTitle"),
            remark = Remark("mockedRemark"),
        )
        target.create(mockedPasswordGroup)

        val actual = passwordGroupEntityDao.getAllByEmail(mockedEmail.value)
        assertThat(actual).contains(mockedPasswordGroup)
    }
}
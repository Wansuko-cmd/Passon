@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.passworditem

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wsr.infra.PassonDatabase
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.Name
import com.wsr.passworditem.Password
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId
import com.wsr.state.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)

class LocalPasswordItemRepositoryDeleteTest {
    private lateinit var passwordEntityDao: PasswordItemEntityDao
    private lateinit var db: PassonDatabase
    private lateinit var target: LocalPasswordItemRepositoryImpl

    @BeforeTest
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PassonDatabase::class.java).build()
        passwordEntityDao = db.passwordItemEntityDao()

        target = LocalPasswordItemRepositoryImpl(passwordEntityDao)
    }

    @AfterTest
    fun closeDB() {
        db.close()
    }

    /*** delete関数 ***/
    @Test
    fun passwordItemIdを渡すと対応するPasswordを削除する() = runTest {

        val mockedPasswordItemId = PasswordItemId("mockedpasswordItemId")
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedPasswordItem = PasswordItem(
            id = mockedPasswordItemId,
            passwordGroupId = mockedPasswordGroupId,
            name = Name("mockedName"),
            password = Password("mockedPassword"),
        )
        target.upsert(mockedPasswordItem)

        target.delete(mockedPasswordItemId).also { assertThat(it).isEqualTo(State.Success(Unit)) }

        val actual = passwordEntityDao.getAllByPasswordGroupId(mockedPasswordGroupId.value)
        assertThat(actual).isEmpty()
    }
}

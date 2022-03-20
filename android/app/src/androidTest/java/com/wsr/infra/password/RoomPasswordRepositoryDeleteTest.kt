@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.password

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wsr.infra.PassonDatabase
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordpair.Name
import com.wsr.passwordpair.Password
import com.wsr.passwordpair.PasswordPair
import com.wsr.passwordpair.PasswordPairId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)

class RoomPasswordRepositoryDeleteTest {
    private lateinit var passwordEntityDao: PasswordPairEntityDao
    private lateinit var db: PassonDatabase
    private lateinit var target: RoomPasswordPairRepositoryImpl

    @BeforeTest
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PassonDatabase::class.java).build()
        passwordEntityDao = db.passwordEntityDao()

        target = RoomPasswordPairRepositoryImpl(passwordEntityDao)
    }

    @AfterTest
    fun closeDB() {
        db.close()
    }

    /*** delete関数 ***/
    @Test
    fun passwordPairIdを渡すと対応するPasswordを削除する() = runTest {

        val mockedPasswordPairId = PasswordPairId("mockedpasswordPairId")
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedPasswordPair = PasswordPair(
            id = mockedPasswordPairId,
            passwordGroupId = mockedPasswordGroupId,
            name = Name("mockedName"),
            password = Password("mockedPassword"),
        )
        target.upsert(mockedPasswordPair)

        target.delete(mockedPasswordPairId)

        val actual = target.getAllByPasswordGroupId(mockedPasswordGroupId)
        assertThat(actual).isEmpty()
    }
}

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
class RoomPasswordRepositoryGetTest {
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

    /*** getAllByPasswordGroupId関数 ***/
    @Test
    fun passwordGroupIdを渡すと所属する全てのPasswordGroupを返す() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedPasswordPairs = List(5) { index ->
            PasswordPair(
                id = PasswordPairId("mockedpasswordPairId$index"),
                passwordGroupId = mockedPasswordGroupId,
                name = Name("mockedName$index"),
                password = Password("mockedPassword$index"),
            )
        }

        mockedPasswordPairs.forEach { target.upsert(it) }

        val actual = target.getAllByPasswordGroupId(mockedPasswordGroupId)
        assertThat(actual).isEqualTo(mockedPasswordPairs)
    }
}

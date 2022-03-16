@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.infra.passwordgroup

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wsr.email.Email
import com.wsr.exceptions.GetDataFailedException
import com.wsr.infra.PassonDatabase
import com.wsr.passwordgroup.PasswordGroup
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
class RoomPasswordGroupRepositoryDeleteTest {
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

    /*** delete関数 ***/
    @Test
    fun passwordGroupIdを渡すと対応するPasswordGroupを削除する() = runTest {
        val mockedPasswordGroupId = UniqueId.of("mockedPasswordGroupId")
        val mockedPasswordGroup = PasswordGroup.of(
            id = mockedPasswordGroupId,
            email = Email.of("mockedEmail"),
            title = "mockedTitle",
            remark = "mockedRemark",
        )
        target.create(mockedPasswordGroup)

        target.delete(mockedPasswordGroupId)

        assertFailsWith<GetDataFailedException.NoSuchElementException> {
            target.getById(mockedPasswordGroupId)
        }
    }
}

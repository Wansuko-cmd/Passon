@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.password.upsert

import com.wsr.password.Password
import com.wsr.password.PasswordRepository
import com.wsr.password.PasswordUseCaseModel
import com.wsr.state.State
import com.wsr.utils.UniqueId
import kotlinx.coroutines.runBlocking
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.Test
import kotlin.test.assertEquals

class UpsertPasswordUseCaseImplTest {

    private val testPassword = Password(UniqueId(), UniqueId(), "", "")

    private val passwordRepository: PasswordRepository = mock {
        onBlocking { upsert(testPassword) } doReturn Unit
    }

    private val upsertPasswordUseCaseImpl = UpsertPasswordUseCaseImpl(passwordRepository)

    @Test
    fun upsertでPasswordの登録or更新を行う(): Unit = runBlocking {

        val result = upsertPasswordUseCaseImpl.upsert(
            testPassword.id.value,
            "newPasswordGroupId",
            "newName",
            "newPassword",
        )
        assertEquals(
            expected = State.Success(
                PasswordUseCaseModel(
                    testPassword.id.value,
                    "newPasswordGroupId",
                    "newName",
                    "newPassword",
                )
            ),
            actual = result,
        )
    }
}
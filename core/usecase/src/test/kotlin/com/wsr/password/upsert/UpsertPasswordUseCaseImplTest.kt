@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.password.upsert

import com.wsr.password.Password
import com.wsr.password.PasswordRepository
import com.wsr.password.PasswordUseCaseModel
import com.wsr.state.State
import com.wsr.utils.UniqueId
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UpsertPasswordUseCaseImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun upsertでPasswordの登録or更新を行う(): Unit = runTest {

        val testPassword =
            Password(UniqueId(), UniqueId("newPasswordGroupId"), "newName", "newPassword")
        val passwordRepository = mockk<PasswordRepository>()
        coEvery { passwordRepository.upsert(testPassword) } returns Unit
        val upsertPasswordUseCaseImpl = UpsertPasswordUseCaseImpl(passwordRepository)

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
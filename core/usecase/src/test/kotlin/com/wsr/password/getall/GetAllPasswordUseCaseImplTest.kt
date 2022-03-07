@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.password.getall

import app.cash.turbine.test
import com.wsr.password.Password
import com.wsr.password.PasswordRepository
import com.wsr.password.toUseCaseModel
import com.wsr.state.State
import com.wsr.utils.UniqueId
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAllPasswordUseCaseImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllByPasswordGroupIdにPasswordGroupIdを渡すことでPasswordデータを取得() = runTest {

        val passwordGroupId = "PasswordGroupId"
        val testPassword = Password(UniqueId(), UniqueId(passwordGroupId), "", "")

        val passwordRepository = mockk<PasswordRepository>()
        coEvery { passwordRepository.getAllByPasswordGroupId(UniqueId(passwordGroupId)) } returns listOf(
            testPassword
        )

        val getAllPasswordUseCaseImpl = GetAllPasswordUseCaseImpl(passwordRepository)

        getAllPasswordUseCaseImpl.data.test {
            getAllPasswordUseCaseImpl.getAllByPasswordGroupId(passwordGroupId)
            assertEquals(State.Loading, awaitItem())
            assertEquals(State.Success(listOf(testPassword.toUseCaseModel())), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordgroup.getall

import app.cash.turbine.test
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State
import com.wsr.user.Email
import com.wsr.utils.UniqueId
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAllPasswordGroupUseCaseImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllByEmailで特定のEmailの全てのPasswordGroupを取得() = runTest {

        val testPasswordGroup = PasswordGroup(UniqueId(), Email("example@gmail.com"), "", "")
        val passwordGroupRepository = mockk<PasswordGroupRepository>()
        coEvery { passwordGroupRepository.getAllByEmail(testPasswordGroup.email) } returns listOf(
            testPasswordGroup
        )
        val getAllPasswordGroupUseCaseImpl = GetAllPasswordGroupUseCaseImpl(passwordGroupRepository)


        getAllPasswordGroupUseCaseImpl.data.test {
            getAllPasswordGroupUseCaseImpl.getAllByEmail(testPasswordGroup.email.value)
            assertEquals(State.Loading, awaitItem())
            assertEquals(State.Success(listOf(testPasswordGroup.toUseCaseModel())), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
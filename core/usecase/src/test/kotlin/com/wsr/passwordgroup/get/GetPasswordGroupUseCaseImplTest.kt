@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordgroup.get

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

class GetPasswordGroupUseCaseImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getByIdで特定のPasswordGroupを取得() = runTest {

        val testPasswordGroup = PasswordGroup(UniqueId(), Email("example@gmail.com"), "", "")
        val passwordGroupRepository = mockk<PasswordGroupRepository>()
        coEvery { passwordGroupRepository.getById(testPasswordGroup.id) } returns testPasswordGroup
        val getPasswordGroupUseCaseImpl = GetPasswordGroupUseCaseImpl(passwordGroupRepository)

        getPasswordGroupUseCaseImpl.data.test {
            getPasswordGroupUseCaseImpl.getById(testPasswordGroup.id.value)
            assertEquals(State.Loading, awaitItem())
            assertEquals(State.Success(testPasswordGroup.toUseCaseModel()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
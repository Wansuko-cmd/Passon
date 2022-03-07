@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordgroup.get

import app.cash.turbine.test
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State
import com.wsr.user.Email
import com.wsr.utils.UniqueId
import kotlinx.coroutines.runBlocking
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.Test
import kotlin.test.assertEquals

class GetPasswordGroupUseCaseImplTest {
    private val testPasswordGroup = PasswordGroup(UniqueId(), Email("example@gmail.com"), "", "")

    private val passwordGroupRepository: PasswordGroupRepository = mock {
        onBlocking { getById(testPasswordGroup.id) } doReturn testPasswordGroup
    }

    private val getPasswordGroupUseCaseImpl = GetPasswordGroupUseCaseImpl(passwordGroupRepository)

    @Test
    fun getByIdで特定のPasswordGroupを取得() = runBlocking {
        getPasswordGroupUseCaseImpl.data.test {
            getPasswordGroupUseCaseImpl.getById(testPasswordGroup.id.value)
            assertEquals(State.Loading, awaitItem())
            assertEquals(State.Success(testPasswordGroup.toUseCaseModel()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
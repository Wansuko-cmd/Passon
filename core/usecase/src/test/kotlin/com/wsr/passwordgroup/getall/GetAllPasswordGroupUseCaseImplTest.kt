@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordgroup.getall

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

class GetAllPasswordGroupUseCaseImplTest {
    private val testPasswordGroup = PasswordGroup(UniqueId(), Email("example@gmail.com"), "", "")

    private val passwordGroupRepository: PasswordGroupRepository = mock {
        onBlocking { getAllByEmail(testPasswordGroup.email) } doReturn listOf(testPasswordGroup)
    }

    private val getAllPasswordGroupUseCaseImpl = GetAllPasswordGroupUseCaseImpl(passwordGroupRepository)

    @Test
    fun getAllByEmailで特定のEmailの全てのPasswordGroupを取得() = runBlocking {
        getAllPasswordGroupUseCaseImpl.data.test {
            getAllPasswordGroupUseCaseImpl.getAllByEmail(testPasswordGroup.email.value)
            assertEquals(State.Loading, awaitItem())
            assertEquals(State.Success(listOf(testPasswordGroup.toUseCaseModel())), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.password.getall

import app.cash.turbine.test
import com.wsr.password.Password
import com.wsr.password.PasswordRepository
import com.wsr.password.toUseCaseModel
import com.wsr.state.State
import com.wsr.utils.UniqueId
import kotlinx.coroutines.runBlocking
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAllPasswordUseCaseImplTest {

    private val passwordGroupId = "PasswordGroupId"

    private val testPassword = Password(UniqueId(), UniqueId(passwordGroupId), "", "")

    private val passwordRepository: PasswordRepository = mock {
        onBlocking { getAllByPasswordGroupId(UniqueId(passwordGroupId)) } doReturn listOf(
            testPassword
        )
    }

    private val getAllPasswordUseCaseImpl = GetAllPasswordUseCaseImpl(passwordRepository)

    @Test
    fun getAllByPasswordGroupIdにパスワードを渡すことでPasswordデータを取得() = runBlocking {
        getAllPasswordUseCaseImpl.data.test {
            getAllPasswordUseCaseImpl.getAllByPasswordGroupId(passwordGroupId)
            assertEquals(State.Loading, awaitItem())
            assertEquals(State.Success(listOf(testPassword.toUseCaseModel())), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
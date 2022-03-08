@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.password.getall

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.wsr.password.Password
import com.wsr.password.PasswordRepository
import com.wsr.password.toUseCaseModel
import com.wsr.state.State
import com.wsr.utils.UniqueId
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllPasswordUseCaseImplTest {

    @MockK
    private lateinit var passwordRepository: PasswordRepository
    private lateinit var target: GetAllPasswordUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = GetAllPasswordUseCaseImpl(passwordRepository)
    }

    @Test
    fun getAllByPasswordGroupIdにPasswordGroupIdを渡すことでPasswordデータを取得() = runTest {

        val mockedPasswordGroupId = UniqueId("mockedPasswordGroupId")
        val mockedPasswords = List(5) { index ->
            Password(
                id = UniqueId(value = "UniqueId$index"),
                passwordGroupId = mockedPasswordGroupId,
                name = "name$index",
                password = "password$index"
            )
        }

        coEvery { passwordRepository.getAllByPasswordGroupId(mockedPasswordGroupId) } returns mockedPasswords

        target.data.test {
            target.getAllByPasswordGroupId(mockedPasswordGroupId.value)

            assertThat(awaitItem()).isEqualTo(State.Loading)
            assertThat(awaitItem()).isEqualTo(State.Success(mockedPasswords.map { it.toUseCaseModel() }))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { passwordRepository.getAllByPasswordGroupId(mockedPasswordGroupId) }
        confirmVerified(passwordRepository)
    }
}
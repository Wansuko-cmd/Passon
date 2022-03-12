@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.password.getall

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.password.Password
import com.wsr.password.PasswordRepository
import com.wsr.password.toUseCaseModel
import com.wsr.state.State
import com.wsr.utils.UniqueId
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

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

    /*** getAllByPasswordGroupId関数 ***/
    @Test
    fun PasswordGroupIdを渡すと所属する全てのPasswordを返す() = runTest {
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

            val expected = mockedPasswords.map { it.toUseCaseModel() }
            assertThat(awaitItem()).isEqualTo(State.Success(expected))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { passwordRepository.getAllByPasswordGroupId(mockedPasswordGroupId) }
        confirmVerified(passwordRepository)
    }

    @Test
    fun 取得するときにエラーが起きればその内容を返す() = runTest {
        val mockedPasswordGroupId = UniqueId("mockedPasswordGroupId")
        coEvery { passwordRepository.getAllByPasswordGroupId(mockedPasswordGroupId) } throws GetAllDataFailedException.DatabaseException()

        target.data.test {
            target.getAllByPasswordGroupId(mockedPasswordGroupId.value)

            assertThat(awaitItem()).isEqualTo(State.Loading)

            val expected = GetAllDataFailedException.DatabaseException()
            assertThat(awaitItem()).isEqualTo(State.Failure(expected))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { passwordRepository.getAllByPasswordGroupId(mockedPasswordGroupId) }
        confirmVerified(passwordRepository)
    }
}
@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordpair.getall

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordpair.Name
import com.wsr.passwordpair.Password
import com.wsr.passwordpair.PasswordPair
import com.wsr.passwordpair.PasswordPairId
import com.wsr.passwordpair.PasswordPairRepository
import com.wsr.passwordpair.toUseCaseModel
import com.wsr.state.State
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
    private lateinit var passwordRepository: PasswordPairRepository
    private lateinit var target: GetAllPasswordPairUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = GetAllPasswordPairUseCaseImpl(passwordRepository)
    }

    /*** getAllByPasswordGroupId関数 ***/
    @Test
    fun PasswordGroupIdを渡すと所属する全てのPasswordを返す() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedPasswordPairs = List(5) { index ->
            PasswordPair(
                id = PasswordPairId(value = "UniqueId$index"),
                passwordGroupId = mockedPasswordGroupId,
                name = Name("name$index"),
                password = Password("password$index"),
            )
        }
        coEvery { passwordRepository.getAllByPasswordGroupId(mockedPasswordGroupId) } returns mockedPasswordPairs

        target.data.test {
            target.getAllByPasswordGroupId(mockedPasswordGroupId.value)

            assertThat(awaitItem()).isEqualTo(State.Loading)

            val expected = mockedPasswordPairs.map { it.toUseCaseModel() }
            assertThat(awaitItem()).isEqualTo(State.Success(expected))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { passwordRepository.getAllByPasswordGroupId(mockedPasswordGroupId) }
        confirmVerified(passwordRepository)
    }

    @Test
    fun 取得するときにエラーが起きればその内容を返す() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
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

@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passworditem.getall

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.Name
import com.wsr.passworditem.Password
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId
import com.wsr.passworditem.PasswordItemRepository
import com.wsr.passworditem.toUseCaseModel
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
class GetAllPasswordItemUseCaseImplTest {

    @MockK
    private lateinit var passwordRepository: PasswordItemRepository
    private lateinit var target: GetAllPasswordItemUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = GetAllPasswordItemUseCaseImpl(passwordRepository)
    }

    /*** getAllByPasswordGroupId関数 ***/
    @Test
    fun PasswordGroupIdを渡すと所属する全てのPasswordを返す() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedPasswordItems = List(5) { index ->
            PasswordItem(
                id = PasswordItemId(value = "UniqueId$index"),
                passwordGroupId = mockedPasswordGroupId,
                name = Name("name$index"),
                password = Password("password$index"),
            )
        }
        coEvery { passwordRepository.getAllByPasswordGroupId(mockedPasswordGroupId) } returns mockedPasswordItems

        target.data.test {
            target.getAllByPasswordGroupId(mockedPasswordGroupId.value)

            assertThat(awaitItem()).isEqualTo(State.Loading)

            val expected = mockedPasswordItems.map { it.toUseCaseModel() }
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

@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.password.upsert

import com.google.common.truth.Truth.assertThat
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
class UpsertPasswordUseCaseImplTest {

    @MockK
    private lateinit var passwordRepository: PasswordRepository
    private lateinit var target: UpsertPasswordUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = UpsertPasswordUseCaseImpl(passwordRepository)
    }

    /*** upsert関数 ***/
    @Test
    fun 新しいPasswordの情報を渡すとPasswordの登録or更新を行い返す(): Unit = runTest {
        val mockedPasswordId = UniqueId("mockedPasswordId")
        val mockedPasswordGroupId = UniqueId("mockedPasswordGroupId")
        val mockedName = "mockedName"
        val mockedPassword = "mockedPassword"
        val expectedPassword =
            Password(mockedPasswordId, mockedPasswordGroupId, mockedName, mockedPassword)

        coEvery { passwordRepository.upsert(any()) } returns Unit


        val actual = target.upsert(
            id = mockedPasswordId.value,
            passwordGroupId = mockedPasswordGroupId.value,
            name = mockedName,
            password = mockedPassword,
        )
        val expected = State.Success(expectedPassword.toUseCaseModel())

        assertThat(actual).isEqualTo(expected)

        coVerify(exactly = 1) { passwordRepository.upsert(expectedPassword) }
        confirmVerified(passwordRepository)
    }
}
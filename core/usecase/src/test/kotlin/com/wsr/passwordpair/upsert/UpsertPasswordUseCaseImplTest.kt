@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordpair.upsert

import com.google.common.truth.Truth.assertThat
import com.wsr.exceptions.UpsertDataFailedException
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
class UpsertPasswordUseCaseImplTest {

    @MockK
    private lateinit var passwordRepository: PasswordPairRepository
    private lateinit var target: UpsertPasswordPairUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = UpsertPasswordPairUseCaseImpl(passwordRepository)
    }

    /*** upsert関数 ***/
    @Test
    fun 新しいPasswordの情報を渡すとPasswordの登録or更新を行い返す(): Unit = runTest {
        val mockedPasswordPairId = PasswordPairId("mockedPasswordId")
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedName = Name("mockedName")
        val mockedPassword = Password("mockedPassword")
        val expectedPasswordPair =
            PasswordPair(mockedPasswordPairId, mockedPasswordGroupId, mockedName, mockedPassword)

        coEvery { passwordRepository.upsert(expectedPasswordPair) } returns expectedPasswordPair

        val actual = target.upsert(
            id = mockedPasswordPairId.value,
            passwordGroupId = mockedPasswordGroupId.value,
            name = mockedName.value,
            password = mockedPassword.value,
        )
        val expected = State.Success(expectedPasswordPair.toUseCaseModel())

        assertThat(actual).isEqualTo(expected)

        coVerify(exactly = 1) { passwordRepository.upsert(expectedPasswordPair) }
        confirmVerified(passwordRepository)
    }

    @Test
    fun 登録or更新するときにエラーが起きればその内容を返す() = runTest {
        val mockedPasswordPairId = PasswordPairId("mockedPasswordId")
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedName = "mockedName"
        val mockedPassword = "mockedPassword"

        coEvery { passwordRepository.upsert(any()) } throws UpsertDataFailedException.DatabaseException()

        val actual = target.upsert(
            id = mockedPasswordPairId.value,
            passwordGroupId = mockedPasswordGroupId.value,
            name = mockedName,
            password = mockedPassword,
        )
        val expected = State.Failure(UpsertDataFailedException.DatabaseException())

        assertThat(actual).isEqualTo(expected)

        coVerify(exactly = 1) { passwordRepository.upsert(any()) }
        confirmVerified(passwordRepository)
    }
}

@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passworditem.upsert

import com.google.common.truth.Truth.assertThat
import com.wsr.exceptions.UpsertDataFailedException
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
class UpsertPasswordItemUseCaseImplTest {

    @MockK
    private lateinit var passwordRepository: PasswordItemRepository
    private lateinit var target: UpsertPasswordItemUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = UpsertPasswordItemUseCaseImpl(passwordRepository)
    }

    /*** upsert関数 ***/
    @Test
    fun 新しいPasswordの情報を渡すとPasswordの登録or更新を行い返す(): Unit = runTest {
        val mockedPasswordItemId = PasswordItemId("mockedPasswordItemId")
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedName = Name("mockedName")
        val mockedPassword = Password("mockedPassword")
        val expectedPasswordItem =
            PasswordItem(mockedPasswordItemId, mockedPasswordGroupId, mockedName, mockedPassword)

        coEvery { passwordRepository.upsert(expectedPasswordItem) } returns expectedPasswordItem

        val actual = target.upsert(
            id = mockedPasswordItemId.value,
            passwordGroupId = mockedPasswordGroupId.value,
            name = mockedName.value,
            password = mockedPassword.value,
        )
        val expected = State.Success(expectedPasswordItem.toUseCaseModel())

        assertThat(actual).isEqualTo(expected)

        coVerify(exactly = 1) { passwordRepository.upsert(expectedPasswordItem) }
        confirmVerified(passwordRepository)
    }

    @Test
    fun 登録or更新するときにエラーが起きればその内容を返す() = runTest {
        val mockedPasswordItemId = PasswordItemId("mockedPasswordItemId")
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedName = "mockedName"
        val mockedPassword = "mockedPassword"

        coEvery { passwordRepository.upsert(any()) } throws UpsertDataFailedException.DatabaseException()

        val actual = target.upsert(
            id = mockedPasswordItemId.value,
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

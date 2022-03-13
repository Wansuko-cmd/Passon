@file:Suppress("NonAsciiCharacters", "TestFunctionName")


package com.wsr.passwordgroup.update

import com.google.common.truth.Truth.assertThat
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.passwordgroup.PasswordGroupRepository
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
class UpdatePasswordGroupUseCaseImplTest {

    @MockK
    private lateinit var passwordGroupRepository: PasswordGroupRepository
    private lateinit var target: UpdatePasswordGroupUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = UpdatePasswordGroupUseCaseImpl(passwordGroupRepository)
    }

    /*** update関数 ***/
    @Test
    fun 新しいPasswordGroupの情報を渡すと指定されたPasswordGroupの更新を行う() = runTest {
        val mockedPasswordGroupId = UniqueId("mockedPasswordGroupId")
        val mockedTitle = "mockedTitle"
        val mockedRemark = "mockedRemark"

        coEvery { passwordGroupRepository.update(any(), any(), any()) } returns Unit

        val actual = target.update(
            id = mockedPasswordGroupId.value,
            title = mockedTitle,
            remark = mockedRemark,
        )
        val expected = State.Success(Unit)

        assertThat(actual).isEqualTo(expected)

        coVerify(exactly = 1) { passwordGroupRepository.update(any(), any(), any()) }
        confirmVerified(passwordGroupRepository)
    }

    @Test
    fun 更新するときにエラーが起きればその内容を返す() = runTest {
        val mockedPasswordGroupId = UniqueId("mockedPasswordGroupId")
        val mockedTitle = "mockedTitle"
        val mockedRemark = "mockedRemark"

        coEvery {
            passwordGroupRepository.update(
                any(),
                any(),
                any()
            )
        } throws UpdateDataFailedException.DatabaseException()

        val actual = target.update(
            id = mockedPasswordGroupId.value,
            title = mockedTitle,
            remark = mockedRemark,
        )
        val expected = State.Failure(UpdateDataFailedException.DatabaseException())

        assertThat(actual).isEqualTo(expected)

        coVerify(exactly = 1) { passwordGroupRepository.update(any(), any(), any()) }
        confirmVerified(passwordGroupRepository)
    }
}
@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.delete

import com.google.common.truth.Truth.assertThat
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.state.State
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeletePasswordGroupUsecaseImplTest {

    @MockK
    private lateinit var passwordGroupRepository: PasswordGroupRepository
    private lateinit var target: DeletePasswordGroupUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = DeletePasswordGroupUseCaseImpl(passwordGroupRepository)
    }

    /*** delete関数 ***/
    @Test
    fun passwordGroupIdを渡せば対応するPasswordGroupを削除する() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")

        coEvery { passwordGroupRepository.delete(mockedPasswordGroupId) } returns State.Success(Unit)

        val actual = target.delete(mockedPasswordGroupId.value)
        assertThat(actual).isEqualTo(State.Success(Unit))
    }

    @Test
    fun 削除するときにエラーが起きればその内容を返す() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")

        coEvery { passwordGroupRepository.delete(mockedPasswordGroupId) } returns State.Failure(DeleteDataFailedException.NoSuchElementException())

        val actual = target.delete(mockedPasswordGroupId.value)
        val expected = State.Failure(DeletePasswordGroupUseCaseException.NoSuchPasswordGroupException(""))
        assertThat(actual).isEqualTo(expected)
    }
}

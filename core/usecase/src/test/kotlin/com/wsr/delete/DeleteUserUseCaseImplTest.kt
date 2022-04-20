@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.delete

import com.google.common.truth.Truth.assertThat
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.user.UserId
import com.wsr.user.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteUserUseCaseImplTest {

    @MockK
    private lateinit var userRepository: UserRepository
    private lateinit var target: DeleteUserUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = DeleteUserUseCaseImpl(userRepository)
    }

    /*** delete関数 ***/
    @Test
    fun userIdを渡せば対応するUserを削除する() = runTest {
        val mockedUserId = UserId("mockedUserId")

        coEvery { userRepository.delete(mockedUserId) } returns Maybe.Success(Unit)

        val actual = target.delete(mockedUserId.value)
        assertThat(actual).isEqualTo(Maybe.Success(Unit))
    }

    @Test
    fun 削除するときにエラーが起きればその内容を渡す() = runTest {
        val mockedUserId = UserId("mockedUserId")

        coEvery { userRepository.delete(mockedUserId) } returns Maybe.Failure(DeleteDataFailedException.NoSuchElementException())

        val actual = target.delete(mockedUserId.value)
        val expected = Maybe.Failure(DeleteUserUseCaseException.NoSuchUserUseCaseException(""))

        assertThat(actual).isEqualTo(expected)
    }
}
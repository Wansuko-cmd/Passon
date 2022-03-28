@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.fetch

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.wsr.email.Email
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.Remark
import com.wsr.passwordgroup.Title
import com.wsr.state.State
import com.wsr.toUseCaseModel
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
class FetchAllPasswordGroupUseCaseImplTest {

    @MockK
    private lateinit var queryService: FetchAllPasswordGroupUseCaseQueryService
    private lateinit var target: FetchAllPasswordGroupUseCase

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = FetchAllPasswordGroupUseCaseImpl(queryService)
    }

    /*** fetch関数 ***/
    @Test
    fun emailを渡すと所属する全てのPasswordGroupを返す() = runTest {
        val mockedEmail = Email("mockedEmail")
        val mockedPasswordGroups = List(5) { index ->
            PasswordGroup(
                id = PasswordGroupId("mockedPasswordGroupId$index"),
                email = mockedEmail,
                title = Title("mockedTitle$index"),
                remark = Remark("mockedRemark$index"),
            )
        }

        coEvery { queryService.getAllPasswordGroup(mockedEmail) } returns mockedPasswordGroups

        target.data.test {
            target.fetch(mockedEmail.value)

            assertThat(awaitItem()).isEqualTo(State.Loading)

            val expected = mockedPasswordGroups.map { it.toUseCaseModel() }
            assertThat(awaitItem()).isEqualTo(State.Success(expected))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { queryService.getAllPasswordGroup(mockedEmail) }
        confirmVerified(queryService)
    }

    @Test
    fun 取得するときにエラーが起きればその内容を返す() = runTest {
        val mockedEmail = Email("mockedEmail")

        coEvery { queryService.getAllPasswordGroup(mockedEmail) } throws GetAllDataFailedException.DatabaseException()

        target.data.test {
            target.fetch(mockedEmail.value)

            assertThat(awaitItem()).isEqualTo(State.Loading)

            val expected = GetAllDataFailedException.DatabaseException()
            assertThat(awaitItem()).isEqualTo(State.Failure(expected))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { queryService.getAllPasswordGroup(mockedEmail) }
        confirmVerified(queryService)
    }
}

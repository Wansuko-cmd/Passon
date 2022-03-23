@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordgroup.getall

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.wsr.email.Email
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.Remark
import com.wsr.passwordgroup.Title
import com.wsr.passwordgroup.toUseCaseModel
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
class GetAllPasswordGroupUseCaseImplTest {

    @MockK
    private lateinit var getAllPasswordGroupQueryService: GetAllPasswordGroupQueryService
    private lateinit var target: GetAllPasswordGroupUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = GetAllPasswordGroupUseCaseImpl(getAllPasswordGroupQueryService)
    }

    /*** getAllByEmail関数 ***/
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

        coEvery { getAllPasswordGroupQueryService.getAllByEmail(mockedEmail) } returns mockedPasswordGroups

        target.data.test {
            target.getAllByEmail(mockedEmail.value)

            assertThat(awaitItem()).isEqualTo(State.Loading)

            val expected = mockedPasswordGroups.map { it.toUseCaseModel() }
            assertThat(awaitItem()).isEqualTo(State.Success(expected))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { getAllPasswordGroupQueryService.getAllByEmail(mockedEmail) }
        confirmVerified(getAllPasswordGroupQueryService)
    }

    @Test
    fun 取得するときにエラーが起きればその内容を返す() = runTest {
        val mockedEmail = Email("mockedEmail")

        coEvery { getAllPasswordGroupQueryService.getAllByEmail(mockedEmail) } throws GetAllDataFailedException.DatabaseException()

        target.data.test {
            target.getAllByEmail(mockedEmail.value)

            assertThat(awaitItem()).isEqualTo(State.Loading)

            val expected = GetAllDataFailedException.DatabaseException()
            assertThat(awaitItem()).isEqualTo(State.Failure(expected))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { getAllPasswordGroupQueryService.getAllByEmail(mockedEmail) }
        confirmVerified(getAllPasswordGroupQueryService)
    }
}

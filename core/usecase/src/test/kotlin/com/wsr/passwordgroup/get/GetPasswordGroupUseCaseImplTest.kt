@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordgroup.get

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.wsr.email.Email
import com.wsr.exceptions.GetDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
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
class GetPasswordGroupUseCaseImplTest {

    @MockK
    private lateinit var getPasswordGroupQueryService: GetPasswordGroupQueryService
    private lateinit var target: GetPasswordGroupUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = GetPasswordGroupUseCaseImpl(getPasswordGroupQueryService)
    }

    /*** getById関数 ***/
    @Test
    fun PasswordGroupIdを渡すと対応するPasswordGroupを返す() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedEmail = Email("mockedEmail")
        val mockedTitle = Title("mockedTitle")
        val mockedRemark = Remark("mockedRemark")
        val mockedPasswordGroup = PasswordGroup(
            id = mockedPasswordGroupId,
            email = mockedEmail,
            title = mockedTitle,
            remark = mockedRemark,
        )

        coEvery { getPasswordGroupQueryService.getById(mockedPasswordGroupId) } returns mockedPasswordGroup

        target.data.test {
            target.getById(mockedPasswordGroupId.value)

            assertThat(awaitItem()).isEqualTo(State.Loading)

            val expected = mockedPasswordGroup.toUseCaseModel()
            assertThat(awaitItem()).isEqualTo(State.Success(expected))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { getPasswordGroupQueryService.getById(mockedPasswordGroupId) }
        confirmVerified(getPasswordGroupQueryService)
    }

    @Test
    fun 取得するときにエラーが起きればその内容を返す() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")

        coEvery { getPasswordGroupQueryService.getById(mockedPasswordGroupId) } throws GetDataFailedException.DatabaseException()

        target.data.test {
            target.getById(mockedPasswordGroupId.value)

            assertThat(awaitItem()).isEqualTo(State.Loading)

            val expected = GetDataFailedException.DatabaseException()
            assertThat(awaitItem()).isEqualTo(State.Failure(expected))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { getPasswordGroupQueryService.getById(mockedPasswordGroupId) }
        confirmVerified(getPasswordGroupQueryService)
    }
}

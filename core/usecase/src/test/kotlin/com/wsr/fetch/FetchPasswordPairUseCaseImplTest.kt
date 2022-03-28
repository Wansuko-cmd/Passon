@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.fetch

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.wsr.PasswordPairUseCaseModel
import com.wsr.email.Email
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.Remark
import com.wsr.passwordgroup.Title
import com.wsr.passworditem.Name
import com.wsr.passworditem.Password
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId
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
class FetchPasswordPairUseCaseImplTest {

    @MockK
    private lateinit var queryService: FetchPasswordPairUseCaseQueryService
    private lateinit var target: FetchPasswordPairUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = FetchPasswordPairUseCaseImpl(queryService)
    }

    /*** fetch関数 ***/
    @Test
    fun PasswordGroupIdを渡すと対応するPasswordGroupと所属するPasswordItemsを返す() = runTest {
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

        val mockedPasswordItems = List(5) { index ->
            PasswordItem(
                id = PasswordItemId(value = "mockedPasswordItemId$index"),
                passwordGroupId = mockedPasswordGroupId,
                name = Name("mockedName$index"),
                password = Password("mockedPassword$index"),
            )
        }

        coEvery {
            queryService.getPasswordPair(mockedPasswordGroupId)
        } returns PasswordPairUseCaseModel(
            passwordGroup = mockedPasswordGroup.toUseCaseModel(),
            passwordItems = mockedPasswordItems.map { it.toUseCaseModel() },
        )

        target.data.test {
            target.fetch(mockedPasswordGroupId.value)

            assertThat(awaitItem()).isEqualTo(State.Loading)

            val expected = PasswordPairUseCaseModel(
                passwordGroup = mockedPasswordGroup.toUseCaseModel(),
                passwordItems = mockedPasswordItems.map { it.toUseCaseModel() },
            )
            assertThat(awaitItem()).isEqualTo(State.Success(expected))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { queryService.getPasswordPair(mockedPasswordGroupId) }
        confirmVerified(queryService)
    }

    @Test
    fun 取得するときにエラーが起きればその内容を返す() = runTest { }
}

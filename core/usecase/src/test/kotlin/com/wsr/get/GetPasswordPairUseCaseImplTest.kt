@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.get

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPasswordPairUseCaseImplTest {

    @MockK
    private lateinit var queryService: FetchPasswordPairUseCaseQueryService
    private lateinit var target: GetPasswordPairUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = GetPasswordPairUseCaseImpl(queryService)
    }

    /*** fetch関数 ***/
    @Test
    fun PasswordGroupIdを渡すと対応するPasswordGroupと所属するPasswordItemsを返す() = runTest {
//        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
//        val mockedEmail = Email("mockedEmail")
//        val mockedTitle = Title("mockedTitle")
//        val mockedRemark = Remark("mockedRemark")
//        val mockedPasswordGroup = PasswordGroup(
//            id = mockedPasswordGroupId,
//            userId = mockedEmail,
//            title = mockedTitle,
//            remark = mockedRemark,
//        )
//
//        val mockedPasswordItems = List(5) { index ->
//            PasswordItem(
//                id = PasswordItemId(value = "mockedPasswordItemId$index"),
//                passwordGroupId = mockedPasswordGroupId,
//                name = Name("mockedName$index"),
//                password = Password("mockedPassword$index"),
//            )
//        }
//
//        coEvery {
//            queryService.getPasswordPair(mockedPasswordGroupId)
//        } returns PasswordPairUseCaseModel(
//            passwordGroup = mockedPasswordGroup.toUseCaseModel(),
//            passwordItems = mockedPasswordItems.map { it.toUseCaseModel() },
//        ).let { State.Success(it) }
//
//        target.data.test {
//            target.fetch(mockedPasswordGroupId.value)
//
//            assertThat(awaitItem()).isEqualTo(State.Loading)
//
//            val expected = PasswordPairUseCaseModel(
//                passwordGroup = mockedPasswordGroup.toUseCaseModel(),
//                passwordItems = mockedPasswordItems.map { it.toUseCaseModel() },
//            )
//            assertThat(awaitItem()).isEqualTo(State.Success(expected))
//
//            cancelAndIgnoreRemainingEvents()
//        }
//
//        coVerify(exactly = 1) { queryService.getPasswordPair(mockedPasswordGroupId) }
//        confirmVerified(queryService)
    }

    @Test
    fun 取得するときにエラーが起きればその内容を返す() = runTest {
//        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
//
//        coEvery {
//            queryService.getPasswordPair(mockedPasswordGroupId)
//        } throws Exception()
//
//        target.data.test {
//            target.fetch(mockedPasswordGroupId.value)
//
//            assertThat(awaitItem()).isEqualTo(State.Loading)
//
//            val expected = State.Failure(FetchPasswordPairUseCaseException.SystemError("", Exception()))
//            assertThat(awaitItem()).isEqualTo(expected)
//
//            cancelAndIgnoreRemainingEvents()
//        }
//
//        coVerify(exactly = 1) { queryService.getPasswordPair(mockedPasswordGroupId) }
//        confirmVerified(queryService)
    }
}

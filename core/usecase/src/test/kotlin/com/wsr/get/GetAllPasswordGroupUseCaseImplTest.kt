@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.get

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllPasswordGroupUseCaseImplTest {

    @MockK
    private lateinit var queryService: FetchAllPasswordGroupUseCaseQueryService
    private lateinit var target: GetAllPasswordGroupUseCase

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = GetAllPasswordGroupUseCaseImpl(queryService)
    }

    /*** fetch関数 ***/
    @Test
    fun userIdを渡すと所属する全てのPasswordGroupを返す() = runTest {
//        val mockedEmail = Email("mockedEmail")
//        val mockedPasswordGroups = List(5) { index ->
//            PasswordGroup(
//                id = PasswordGroupId("mockedPasswordGroupId$index"),
//                userId = mockedEmail,
//                title = Title("mockedTitle$index"),
//                remark = Remark("mockedRemark$index"),
//            )
//        }
//
//        coEvery { queryService.getAllPasswordGroup(mockedEmail) } returns mockedPasswordGroups.map { it.toUseCaseModel() }.map { Maybe.Success(it) }.sequence()
//
//        target.data.test {
//            target.fetch(mockedEmail.value)
//
//            assertThat(awaitItem()).isEqualTo(Maybe.Loading)
//
//            val expected = mockedPasswordGroups.map { it.toUseCaseModel() }
//            assertThat(awaitItem()).isEqualTo(Maybe.Success(expected))
//
//            cancelAndIgnoreRemainingEvents()
//        }
//
//        coVerify(exactly = 1) { queryService.getAllPasswordGroup(mockedEmail) }
//        confirmVerified(queryService)
    }

    @Test
    fun 取得するときにエラーが起きればその内容を返す() = runTest {
//        val mockedEmail = Email("mockedEmail")
//
//        coEvery { queryService.getAllPasswordGroup(mockedEmail) } throws Exception()
//
//        target.data.test {
//            target.fetch(mockedEmail.value)
//
//            assertThat(awaitItem()).isEqualTo(Maybe.Loading)
//
//            val expected = FetchAllPasswordGroupUseCaseException.SystemError("", Exception())
//            assertThat(awaitItem()).isEqualTo(Maybe.Failure(expected))
//
//            cancelAndIgnoreRemainingEvents()
//        }
//
//        coVerify(exactly = 1) { queryService.getAllPasswordGroup(mockedEmail) }
//        confirmVerified(queryService)
    }
}

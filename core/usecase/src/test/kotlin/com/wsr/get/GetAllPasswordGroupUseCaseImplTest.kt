@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.get

import com.google.common.truth.Truth.assertThat
import com.wsr.maybe.Maybe
import com.wsr.maybe.sequence
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.Remark
import com.wsr.passwordgroup.Title
import com.wsr.queryservice.PasswordGroupQueryService
import com.wsr.queryservice.PasswordGroupsQueryServiceException
import com.wsr.toUseCaseModel
import com.wsr.user.UserId
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllPasswordGroupUseCaseImplTest {

    @MockK
    private lateinit var queryService: PasswordGroupQueryService
    private lateinit var target: GetAllPasswordGroupUseCase

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = GetAllPasswordGroupUseCaseImpl(queryService)
    }

    /*** get関数 ***/
    @Test
    fun userIdを渡すと所属する全てのPasswordGroupを返す() = runTest {
        val mockedUserId = UserId("mockedUserId")
        val mockedPasswordGroups = List(5) { index ->
            PasswordGroup(
                id = PasswordGroupId("mockedPasswordGroupId$index"),
                userId = mockedUserId,
                title = Title("mockedTitle$index"),
                remark = Remark("mockedRemark$index"),
            )
        }

        coEvery {
            queryService.getAll(mockedUserId)
        } returns mockedPasswordGroups.map { Maybe.Success(it) }.sequence()

        val actual = target.get(mockedUserId.value)
        val expected = Maybe.Success(mockedPasswordGroups.map { it.toUseCaseModel() })

        assertThat(actual).isEqualTo(expected)

        coVerify(exactly = 1) { queryService.getAll(mockedUserId) }
        confirmVerified(queryService)
    }

    @Test
    fun 取得するときにエラーが起きればその内容を返す() = runTest {
        val mockedUserId = UserId("mockedUserId")

        coEvery {
            queryService.getAll(mockedUserId)
        } returns Maybe.Failure(PasswordGroupsQueryServiceException.SystemError("", Exception()))

        assertFailsWith<PasswordGroupsQueryServiceException> { target.get(mockedUserId.value) }

        coVerify(exactly = 1) { queryService.getAll(mockedUserId) }
        confirmVerified(queryService)
    }
}

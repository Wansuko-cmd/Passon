@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.create

import com.google.common.truth.Truth.assertThat
import com.wsr.exceptions.CreateDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.Remark
import com.wsr.passwordgroup.Title
import com.wsr.toUseCaseModel
import com.wsr.user.UserId
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import java.util.UUID
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class CreatePasswordGroupUseCaseImplTest {

    @MockK
    private lateinit var passwordGroupRepository: PasswordGroupRepository
    private lateinit var target: CreatePasswordGroupUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        mockkStatic(UUID::class)
        target = CreatePasswordGroupUseCaseImpl(passwordGroupRepository)
    }

    /*** create関数 ***/
    @Test
    fun 新しいPasswordGroupの情報を渡すと新しいPasswordGroupを作成して登録し返す(): Unit = runTest {
        val uuid = "5af48f3b-468b-4ae0-a065-7d7ac70b37a8"
        every { UUID.randomUUID().toString() } returns uuid

        val mockedPasswordGroupId = PasswordGroupId(uuid)
        val mockedEmail = UserId("mockedEmail")
        val mockedTitle = Title("mockedTitle")
        val mockedRemark = Remark("")
        val mockedPasswordGroup = PasswordGroup(
            id = mockedPasswordGroupId,
            userId = mockedEmail,
            title = mockedTitle,
            remark = mockedRemark,
        )

        coEvery { passwordGroupRepository.create(any()) } returns Maybe.Success(Unit)

        val actual = target.create(
            userId = mockedEmail.value,
            title = mockedTitle.value,
        )
        val expected = Maybe.Success(mockedPasswordGroup.toUseCaseModel())

        assertThat(actual).isEqualTo(expected)

        coVerify(exactly = 1) { passwordGroupRepository.create(any()) }
        confirmVerified(passwordGroupRepository)
    }

    @Test
    fun 作成するときにエラーが起きればその内容を返す() = runTest {
        val mockedEmail = UserId("mockedEmail")
        val mockedTitle = "mockTitle"

        coEvery { passwordGroupRepository.create(any()) } returns Maybe.Failure(
            CreateDataFailedException.SystemError()
        )

        assertFailsWith<CreateDataFailedException> {
            target.create(
                userId = mockedEmail.value,
                title = mockedTitle,
            )
        }

        coVerify(exactly = 1) { passwordGroupRepository.create(any()) }
        confirmVerified(passwordGroupRepository)
    }
}

@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordgroup.update

import com.google.common.truth.Truth.assertThat
import com.wsr.email.Email
import com.wsr.exceptions.UpdateDataFailedException
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
class UpdatePasswordGroupUseCaseImplTest {

    @MockK
    private lateinit var passwordGroupRepository: PasswordGroupRepository
    @MockK
    private lateinit var updatePasswordGroupQueryService: UpdatePasswordGroupQueryService
    private lateinit var target: UpdatePasswordGroupUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = UpdatePasswordGroupUseCaseImpl(passwordGroupRepository, updatePasswordGroupQueryService)
    }

    /*** update関数 ***/
    @Test
    fun 新しいPasswordGroupの情報を渡すと指定されたPasswordGroupの更新を行う() = runTest {
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

        coEvery {
            passwordGroupRepository.update(
                id = mockedPasswordGroup.id,
                title = mockedPasswordGroup.title.value,
                remark = mockedPasswordGroup.remark.value,
            )
        } returns Unit
        coEvery { updatePasswordGroupQueryService.getById(mockedPasswordGroupId) } returns mockedPasswordGroup

        val actual = target.update(
            id = mockedPasswordGroup.id.value,
            title = mockedPasswordGroup.title.value,
            remark = mockedPasswordGroup.remark.value,
        )
        val expected = State.Success(mockedPasswordGroup.toUseCaseModel())

        assertThat(actual).isEqualTo(expected)

        coVerify(exactly = 1) {
            passwordGroupRepository.update(
                id = mockedPasswordGroup.id,
                title = mockedPasswordGroup.title.value,
                remark = mockedPasswordGroup.remark.value,
            )
            updatePasswordGroupQueryService.getById(mockedPasswordGroupId)
        }
        confirmVerified(passwordGroupRepository, updatePasswordGroupQueryService)
    }

    @Test
    fun 更新するときにエラーが起きればその内容を返す() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedTitle = "mockedTitle"
        val mockedRemark = "mockedRemark"

        coEvery {
            passwordGroupRepository.update(
                mockedPasswordGroupId,
                mockedTitle,
                mockedRemark,
            )
        } throws UpdateDataFailedException.DatabaseException()

        val actual = target.update(
            id = mockedPasswordGroupId.value,
            title = mockedTitle,
            remark = mockedRemark,
        )
        val expected = State.Failure(UpdateDataFailedException.DatabaseException())

        assertThat(actual).isEqualTo(expected)

        coVerify(exactly = 1) {
            passwordGroupRepository.update(
                mockedPasswordGroupId,
                mockedTitle,
                mockedRemark,
            )
        }
        confirmVerified(passwordGroupRepository)
    }
}

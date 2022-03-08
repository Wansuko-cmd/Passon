@file:Suppress("NonAsciiCharacters", "TestFunctionName")


package com.wsr.passwordgroup.update

import com.google.common.truth.Truth.assertThat
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State
import com.wsr.user.Email
import com.wsr.utils.UniqueId
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UpdatePasswordGroupUseCaseImplTest {

    @MockK
    private lateinit var passwordGroupRepository: PasswordGroupRepository
    private lateinit var target: UpdatePasswordGroupUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = UpdatePasswordGroupUseCaseImpl(passwordGroupRepository)
    }

    @Test
    fun updateで特定のPasswordGroupの更新をする() = runTest {

        val mockedPasswordGroupId = UniqueId("mockedPasswordGroupId")
        val mockedTitle = "mockedTitle"
        val mockedRemark = "mockedRemark"

        coEvery { passwordGroupRepository.update(any(), any(), any()) } returns Unit

        val actual = target.update(
            id = mockedPasswordGroupId.value,
            title = mockedTitle,
            remark = mockedRemark,
        )
        val expected = State.Success(Unit)

        assertThat(actual).isEqualTo(expected)


        coVerify(exactly = 1) { passwordGroupRepository.update(any(), any(), any()) }
        confirmVerified(passwordGroupRepository)
    }
}
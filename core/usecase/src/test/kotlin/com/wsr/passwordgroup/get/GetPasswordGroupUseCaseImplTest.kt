@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordgroup.get

import app.cash.turbine.test
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
class GetPasswordGroupUseCaseImplTest {

    @MockK
    private lateinit var passwordGroupRepository: PasswordGroupRepository
    private lateinit var target: GetPasswordGroupUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = GetPasswordGroupUseCaseImpl(passwordGroupRepository)
    }

    @Test
    fun getByIdで特定のPasswordGroupを取得() = runTest {

        val mockedPasswordGroupId = UniqueId("mockedPasswordGroupId")
        val mockedEmail = Email("mockedEmail")
        val mockedTitle = "mockedTitle"
        val mockedRemark = "mockedRemark"
        val mockedPasswordGroup = PasswordGroup(mockedPasswordGroupId, mockedEmail, mockedTitle, mockedRemark)

        coEvery { passwordGroupRepository.getById(mockedPasswordGroupId) } returns mockedPasswordGroup

        target.data.test {
            target.getById(mockedPasswordGroupId.value)

            assertThat(awaitItem()).isEqualTo(State.Loading)
            assertThat(awaitItem()).isEqualTo(State.Success(mockedPasswordGroup.toUseCaseModel()))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { passwordGroupRepository.getById(mockedPasswordGroupId) }
        confirmVerified(passwordGroupRepository)
    }
}
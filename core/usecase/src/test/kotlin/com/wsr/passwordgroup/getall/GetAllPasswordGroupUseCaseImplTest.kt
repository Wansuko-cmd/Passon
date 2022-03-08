@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordgroup.getall

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
class GetAllPasswordGroupUseCaseImplTest {

    @MockK
    private lateinit var passwordGroupRepository: PasswordGroupRepository
    private lateinit var target: GetAllPasswordGroupUseCaseImpl

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = GetAllPasswordGroupUseCaseImpl(passwordGroupRepository)
    }

    @Test
    fun getAllByEmailで特定のEmailの全てのPasswordGroupを取得() = runTest {

        val mockedEmail = Email("mockedEmail")
        val mockedPasswordGroups = List(5) { index ->
            PasswordGroup(
                id = UniqueId("mockedPasswordGroupId$index"),
                email = mockedEmail,
                title = "mockedTitle$index",
                remark = "mockedRemark$index"
            )
        }


        coEvery { passwordGroupRepository.getAllByEmail(mockedEmail) } returns mockedPasswordGroups


        target.data.test {
            target.getAllByEmail(mockedEmail.value)

            assertThat(awaitItem()).isEqualTo(State.Loading)
            assertThat(awaitItem()).isEqualTo(State.Success(mockedPasswordGroups.map { it.toUseCaseModel() }))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { passwordGroupRepository.getAllByEmail(mockedEmail) }
        confirmVerified(passwordGroupRepository)
    }
}
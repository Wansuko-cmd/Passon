@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordgroup.create

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
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test

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

        val mockedEmail = Email("mockedEmail")
        val mockedTitle = "mockTitle"

        coEvery { passwordGroupRepository.create(any()) } returns Unit


        val actual = target.create(
            email = mockedEmail.value,
            title = mockedTitle,
        )
        val expected = State.Success(
            PasswordGroup(
                id = UniqueId(uuid),
                email = mockedEmail,
                title = mockedTitle,
                remark = "",
            ).toUseCaseModel()
        )

        assertThat(actual).isEqualTo(expected)

        coVerify(exactly = 1) { passwordGroupRepository.create(any()) }
        confirmVerified(passwordGroupRepository)
    }
}
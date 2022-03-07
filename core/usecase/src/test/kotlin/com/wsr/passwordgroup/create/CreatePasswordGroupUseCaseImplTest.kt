@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordgroup.create

import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State
import com.wsr.user.Email
import com.wsr.utils.UniqueId
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CreatePasswordGroupUseCaseImplTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createでPasswordGroupを新規登録(): Unit = runTest {
        val uuid = "5af48f3b-468b-4ae0-a065-7d7ac70b37a8"
        mockkStatic(UUID::class)
        every { UUID.randomUUID().toString() } returns uuid

        val testPasswordGroup = PasswordGroup(UniqueId(uuid), Email("example@gmail.com"), "", "")

        val passwordGroupRepository = mockk<PasswordGroupRepository>()
        coEvery { passwordGroupRepository.create(testPasswordGroup) } returns Unit

        val createPasswordGroupUseCaseImpl = CreatePasswordGroupUseCaseImpl(passwordGroupRepository)

        val newPasswordGroup = createPasswordGroupUseCaseImpl.create(
            testPasswordGroup.email.value,
            testPasswordGroup.title
        )
        assertEquals(
            expected = State.Success(testPasswordGroup.toUseCaseModel()),
            actual = newPasswordGroup,
        )
    }
}
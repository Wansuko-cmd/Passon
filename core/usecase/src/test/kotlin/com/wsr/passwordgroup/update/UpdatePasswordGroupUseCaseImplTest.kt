@file:Suppress("NonAsciiCharacters", "TestFunctionName")


package com.wsr.passwordgroup.update

import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.state.State
import com.wsr.user.Email
import com.wsr.utils.UniqueId
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdatePasswordGroupUseCaseImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateで特定のPasswordGroupの更新をする() = runTest {

        val testPasswordGroup = PasswordGroup(UniqueId(), Email("example@gmail.com"), "", "")

        val passwordGroupRepository = mockk<PasswordGroupRepository>()
        coEvery {
            passwordGroupRepository.update(
                testPasswordGroup.id,
                "newTitle",
                "newRemark"
            )
        } returns Unit
        val updatePasswordGroupUseCaseImpl = UpdatePasswordGroupUseCaseImpl(passwordGroupRepository)

        val result = updatePasswordGroupUseCaseImpl.update(
            testPasswordGroup.id.value,
            "newTitle",
            "newRemark"
        )
        assertEquals(State.Success(Unit), result)
    }
}
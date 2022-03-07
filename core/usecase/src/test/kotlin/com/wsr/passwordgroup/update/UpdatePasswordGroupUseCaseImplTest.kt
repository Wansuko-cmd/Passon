@file:Suppress("NonAsciiCharacters", "TestFunctionName")


package com.wsr.passwordgroup.update

import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.state.State
import com.wsr.user.Email
import com.wsr.utils.UniqueId
import kotlinx.coroutines.runBlocking
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdatePasswordGroupUseCaseImplTest {

    private val testPasswordGroup = PasswordGroup(UniqueId(), Email("example@gmail.com"), "", "")

    private val passwordGroupRepository: PasswordGroupRepository = mock {
        onBlocking { update(testPasswordGroup.id, title = "newTitle", remark = "newRemark") } doReturn Unit
    }

    private val updatePasswordGroupUseCaseImpl = UpdatePasswordGroupUseCaseImpl(passwordGroupRepository)

    @Test
    fun updateで特定のPasswordGroupの更新をする() = runBlocking {
        val result = updatePasswordGroupUseCaseImpl.update(testPasswordGroup.id.value, "newTitle", "newRemark")
        assertEquals(State.Success(Unit), result)
    }
}
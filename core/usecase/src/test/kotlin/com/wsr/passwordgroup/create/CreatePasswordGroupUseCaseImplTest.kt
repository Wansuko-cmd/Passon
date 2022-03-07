@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordgroup.create

import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State
import com.wsr.user.Email
import com.wsr.utils.UniqueId
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CreatePasswordGroupUseCaseImplTest {

    private val uuid: UUID = UUID.fromString("5af48f3b-468b-4ae0-a065-7d7ac70b37a8")

    init {
        val uuidMock = Mockito.mockStatic(UUID::class.java)

        uuidMock.`when`<UUID>(UUID::randomUUID).thenReturn(uuid)
    }

    private val testPasswordGroup = PasswordGroup(UniqueId(), Email("example@gmail.com"), "", "")

    private val passwordGroupRepository: PasswordGroupRepository = mock {
        onBlocking { create(testPasswordGroup) } doReturn Unit
    }

    private val createPasswordGroupUseCaseImpl = CreatePasswordGroupUseCaseImpl(passwordGroupRepository)

    @Test
    fun createでPasswordGroupを新規登録(): Unit = runBlocking {
        val newPasswordGroup = createPasswordGroupUseCaseImpl.create(testPasswordGroup.email.value, testPasswordGroup.title)
        assertEquals(State.Success(testPasswordGroup.toUseCaseModel()), newPasswordGroup)
    }
}
@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.password.create

import com.wsr.password.PasswordUseCaseModel
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.*
import java.util.UUID.randomUUID
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CreatePasswordUseCaseImplTest {
    private val createPasswordUseCase = CreatePasswordUseCaseImpl()

    private val uuid: UUID = UUID.fromString("5af48f3b-468b-4ae0-a065-7d7ac70b37a8")

    @BeforeTest
    fun setup() {

        val uuidMock = Mockito.mockStatic(UUID::class.java)

        uuidMock.`when`<UUID>(UUID::randomUUID).thenReturn(uuid)
    }

    @Test
    fun passwordGroupIdを渡すことでPasswordUseCaseModelを新規作成() {

        val passwordGroupId = "PasswordGroupId"
        assertEquals(
            expected = PasswordUseCaseModel(uuid.toString(), passwordGroupId, "", ""),
            actual = createPasswordUseCase.createInstance(passwordGroupId)
        )
    }
}
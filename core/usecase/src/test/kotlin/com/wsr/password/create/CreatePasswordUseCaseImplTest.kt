@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.password.create

import com.wsr.password.PasswordUseCaseModel
import io.mockk.every
import io.mockk.mockkStatic
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CreatePasswordUseCaseImplTest {
    private val createPasswordUseCase = CreatePasswordUseCaseImpl()


    /*** createInstance関数 ***/
    @Test
    fun passwordGroupIdを渡すとPasswordUseCaseModelのインスタンスを作成して返す() {

        val uuid = "5af48f3b-468b-4ae0-a065-7d7ac70b37a8"
        mockkStatic(UUID::class)
        every { UUID.randomUUID().toString() } returns uuid

        val passwordGroupId = "PasswordGroupId"
        assertEquals(
            expected = PasswordUseCaseModel(uuid, passwordGroupId, "", ""),
            actual = createPasswordUseCase.createInstance(passwordGroupId)
        )
    }
}
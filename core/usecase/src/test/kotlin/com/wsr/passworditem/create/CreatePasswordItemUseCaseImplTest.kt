@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passworditem.create

import com.google.common.truth.Truth.assertThat
import com.wsr.create.CreatePasswordItemUseCaseImpl
import com.wsr.passworditem.PasswordItemUseCaseModel
import io.mockk.every
import io.mockk.mockkStatic
import java.util.UUID
import kotlin.test.BeforeTest
import kotlin.test.Test

class CreatePasswordItemUseCaseImplTest {

    private lateinit var target: CreatePasswordItemUseCaseImpl

    @BeforeTest
    fun setup() {
        mockkStatic(UUID::class)
        target = CreatePasswordItemUseCaseImpl()
    }

    /*** createInstance関数 ***/
    @Test
    fun passwordGroupIdを渡すと新しいPasswordUseCaseModelを作成して返す() {
        val uuid = "5af48f3b-468b-4ae0-a065-7d7ac70b37a8"
        every { UUID.randomUUID().toString() } returns uuid

        val mockedPasswordGroupId = "mockedPasswordGroupId"

        val actual = target.createPasswordInstance(mockedPasswordGroupId)
        val expected = PasswordItemUseCaseModel(uuid, mockedPasswordGroupId, "", "")

        assertThat(actual).isEqualTo(expected)
    }
}

@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordpair.create

import com.google.common.truth.Truth.assertThat
import com.wsr.passwordpair.PasswordPairUseCaseModel
import io.mockk.every
import io.mockk.mockkStatic
import java.util.UUID
import kotlin.test.BeforeTest
import kotlin.test.Test

class CreatePasswordUseCaseImplTest {

    private lateinit var target: CreatePasswordPairUseCaseImpl

    @BeforeTest
    fun setup() {
        mockkStatic(UUID::class)
        target = CreatePasswordPairUseCaseImpl()
    }

    /*** createInstance関数 ***/
    @Test
    fun passwordGroupIdを渡すと新しいPasswordUseCaseModelを作成して返す() {
        val uuid = "5af48f3b-468b-4ae0-a065-7d7ac70b37a8"
        every { UUID.randomUUID().toString() } returns uuid

        val mockedPasswordGroupId = "mockedPasswordGroupId"

        val actual = target.createPasswordInstance(mockedPasswordGroupId)
        val expected = PasswordPairUseCaseModel(uuid, mockedPasswordGroupId, "", "")

        assertThat(actual).isEqualTo(expected)
    }
}
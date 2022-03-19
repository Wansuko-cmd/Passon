@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.password

import com.google.common.truth.Truth.assertThat
import com.wsr.utils.UniqueId
import kotlin.test.Test

class PasswordUseCaseModelTest {

    /*** toUseCaseModel ***/
    @Test
    fun Passwordから実行すれば対応するUseCaseModelに変換して返す() {
        val mockedPasswordId = UniqueId.from("mockedPasswordId")
        val mockedPasswordGroupId = UniqueId.from("mockedPasswordId")
        val mockedName = "mockedName"
        val mockedPassword = "mockedPassword"

        val actual = Password.of(
            id = mockedPasswordId,
            passwordGroupId = mockedPasswordGroupId,
            name = mockedName,
            password = mockedPassword,
        ).toUseCaseModel()
        val expected = PasswordUseCaseModel(
            id = mockedPasswordId.value,
            passwordGroupId = mockedPasswordGroupId.value,
            name = mockedName,
            password = mockedPassword,
        )
        assertThat(actual).isEqualTo(expected)
    }
}

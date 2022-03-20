@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordpair

import com.google.common.truth.Truth.assertThat
import com.wsr.passwordgroup.PasswordGroupId
import kotlin.test.Test

class PasswordUseCaseModelTest {

    /*** toUseCaseModel ***/
    @Test
    fun Passwordから実行すれば対応するUseCaseModelに変換して返す() {
        val mockedPasswordPairId = PasswordPairId("mockedPasswordId")
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordId")
        val mockedName = Name("mockedName")
        val mockedPassword = Password("mockedPassword")

        val actual = PasswordPair(
            id = mockedPasswordPairId,
            passwordGroupId = mockedPasswordGroupId,
            name = mockedName,
            password = mockedPassword,
        ).toUseCaseModel()
        val expected = PasswordPairUseCaseModel(
            id = mockedPasswordPairId.value,
            passwordGroupId = mockedPasswordGroupId.value,
            name = mockedName.value,
            password = mockedPassword.value,
        )
        assertThat(actual).isEqualTo(expected)
    }
}

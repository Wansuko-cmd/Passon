@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr

import com.google.common.truth.Truth.assertThat
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.Name
import com.wsr.passworditem.Password
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId
import kotlin.test.Test

class PasswordItemUseCaseModelTest {

    /*** toUseCaseModel ***/
    @Test
    fun Passwordから実行すれば対応するUseCaseModelに変換して返す() {
        val mockedPasswordItemId = PasswordItemId("mockedPasswordItemId")
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordItemId")
        val mockedName = Name("mockedName")
        val mockedPassword = Password("mockedPassword")

        val actual = PasswordItem(
            id = mockedPasswordItemId,
            passwordGroupId = mockedPasswordGroupId,
            name = mockedName,
            password = mockedPassword,
        ).toUseCaseModel()
        val expected = PasswordItemUseCaseModel(
            id = mockedPasswordItemId.value,
            passwordGroupId = mockedPasswordGroupId.value,
            name = mockedName.value,
            password = mockedPassword.value,
        )
        assertThat(actual).isEqualTo(expected)
    }
}

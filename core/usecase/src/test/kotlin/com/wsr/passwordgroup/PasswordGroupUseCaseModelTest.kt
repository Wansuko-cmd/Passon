@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.passwordgroup

import com.google.common.truth.Truth.assertThat
import com.wsr.email.Email
import com.wsr.utils.UniqueId
import kotlin.test.Test

class PasswordGroupUseCaseModelTest {

    /*** toUseCaseModel ***/
    @Test
    fun PasswordGroupから実行すれば対応するUseCaseModelに変換して返す() {
        val mockedPasswordGroupId = UniqueId.from("mockedPasswordGroupId")
        val mockedEmail = Email.from("mockedEmail")
        val mockedTitle = "mockedTitle"
        val mockedRemark = "mockedRemark"

        val actual = PasswordGroup.of(
            id = mockedPasswordGroupId,
            email = mockedEmail,
            title = mockedTitle,
            remark = mockedRemark,
        ).toUseCaseModel()
        val expected = PasswordGroupUseCaseModel(
            id = mockedPasswordGroupId.value,
            email = mockedEmail.value,
            title = mockedTitle,
            remark = mockedRemark,
        )
        assertThat(actual).isEqualTo(expected)
    }
}

@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr

import com.google.common.truth.Truth.assertThat
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.Remark
import com.wsr.passwordgroup.Title
import com.wsr.user.UserId
import kotlin.test.Test

class PasswordGroupUseCaseModelTest {

    /*** toUseCaseModel ***/
    @Test
    fun PasswordGroupから実行すれば対応するUseCaseModelに変換して返す() {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedEmail = UserId("mockedEmail")
        val mockedTitle = Title("mockedTitle")
        val mockedRemark = Remark("mockedRemark")

        val actual = PasswordGroup(
            id = mockedPasswordGroupId,
            email = mockedEmail,
            title = mockedTitle,
            remark = mockedRemark,
        ).toUseCaseModel()
        val expected = PasswordGroupUseCaseModel(
            id = mockedPasswordGroupId.value,
            email = mockedEmail.value,
            title = mockedTitle.value,
            remark = mockedRemark.value,
        )
        assertThat(actual).isEqualTo(expected)
    }
}

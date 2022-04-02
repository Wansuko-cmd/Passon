package com.wsr.passwordgroup

import com.wsr.user.UserId
import java.util.UUID

class PasswordGroupFactory {
    fun create(
        email: UserId,
        title: Title = Title(""),
        remark: Remark = Remark(""),
    ): PasswordGroup {
        val passwordGroupId = PasswordGroupId(UUID.randomUUID().toString())
        return PasswordGroup(passwordGroupId, email, title, remark)
    }
}

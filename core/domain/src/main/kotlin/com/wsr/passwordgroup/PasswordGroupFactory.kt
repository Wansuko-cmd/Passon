package com.wsr.passwordgroup

import com.wsr.email.Email
import java.util.UUID

class PasswordGroupFactory {
    fun create(
        email: Email,
        title: Title = Title(""),
        remark: Remark = Remark(""),
    ): PasswordGroup {
        val id = PasswordGroupId(UUID.randomUUID().toString())
        return PasswordGroup(id, email, title, remark)
    }
}

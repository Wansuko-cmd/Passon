package com.wsr.passworditem

import com.wsr.passwordgroup.PasswordGroupId
import java.util.UUID

class PasswordItemFactory {
    fun create(
        passwordGroupId: PasswordGroupId,
        name: Name = Name(""),
        password: Password = Password(""),
    ): PasswordItem {
        val id = PasswordItemId(UUID.randomUUID().toString())
        return PasswordItem(id, passwordGroupId, name, password)
    }

    fun create(
        passwordItemId: PasswordItemId,
        passwordGroupId: PasswordGroupId,
        name: Name,
        password: Password,
    ): PasswordItem = PasswordItem(passwordItemId, passwordGroupId, name, password)
}

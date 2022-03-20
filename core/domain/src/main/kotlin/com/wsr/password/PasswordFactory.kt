package com.wsr.password

import com.wsr.passwordgroup.PasswordGroupId
import java.util.UUID

class PasswordFactory {
    fun create(passwordGroupId: PasswordGroupId, name: String = "", password: String = ""): Password {
        val id = PasswordId(UUID.randomUUID().toString())
        return Password(id, passwordGroupId, name, password)
    }

    fun create(
        passwordId: PasswordId,
        passwordGroupId: PasswordGroupId,
        name: String,
        password: String,
    ): Password = Password(passwordId, passwordGroupId, name, password)
}

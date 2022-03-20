package com.wsr.passwordpair

import com.wsr.passwordgroup.PasswordGroupId
import java.util.UUID

class PasswordPairFactory {
    fun create(passwordGroupId: PasswordGroupId, name: String = "", password: String = ""): PasswordPair {
        val id = PasswordPairId(UUID.randomUUID().toString())
        return PasswordPair(id, passwordGroupId, Name(name), Password(password))
    }

    fun create(
        passwordId: PasswordPairId,
        passwordGroupId: PasswordGroupId,
        name: String,
        password: String,
    ): PasswordPair = PasswordPair(passwordId, passwordGroupId, Name(name), Password(password))
}

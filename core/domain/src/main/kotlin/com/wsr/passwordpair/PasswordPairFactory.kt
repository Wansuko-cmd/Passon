package com.wsr.passwordpair

import com.wsr.passwordgroup.PasswordGroupId
import java.util.UUID

class PasswordPairFactory {
    fun create(
        passwordGroupId: PasswordGroupId,
        name: Name = Name(""),
        password: Password = Password(""),
    ): PasswordPair {
        val id = PasswordPairId(UUID.randomUUID().toString())
        return PasswordPair(id, passwordGroupId, name, password)
    }

    fun create(
        passwordId: PasswordPairId,
        passwordGroupId: PasswordGroupId,
        name: Name,
        password: Password,
    ): PasswordPair = PasswordPair(passwordId, passwordGroupId, name, password)
}

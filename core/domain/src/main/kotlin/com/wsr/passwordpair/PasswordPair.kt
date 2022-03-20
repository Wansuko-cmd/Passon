package com.wsr.passwordpair

import com.wsr.passwordgroup.PasswordGroupId

data class PasswordPair(
    val id: PasswordPairId,
    val passwordGroupId: PasswordGroupId,
    val name: Name,
    val password: Password,
)

data class PasswordPairId(val value: String)

@JvmInline
value class Name(val value: String)

@JvmInline
value class Password(val value: String)

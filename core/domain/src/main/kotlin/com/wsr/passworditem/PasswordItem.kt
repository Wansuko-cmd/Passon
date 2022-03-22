package com.wsr.passworditem

import com.wsr.passwordgroup.PasswordGroupId

data class PasswordItem(
    val id: PasswordItemId,
    val passwordGroupId: PasswordGroupId,
    val name: Name,
    val password: Password,
)

@JvmInline
value class PasswordItemId(val value: String)

@JvmInline
value class Name(val value: String)

@JvmInline
value class Password(val value: String)

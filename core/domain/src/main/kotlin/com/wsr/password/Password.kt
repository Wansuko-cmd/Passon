package com.wsr.password

import com.wsr.passwordgroup.PasswordGroupId

data class Password(
    val id: PasswordId,
    val passwordGroupId: PasswordGroupId,
    val name: String,
    val password: String,
)

data class PasswordId(val value: String)

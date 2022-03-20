package com.wsr.passwordgroup

import com.wsr.email.Email

data class PasswordGroup(
    val id: PasswordGroupId,
    val email: Email,
    val title: Title,
    val remark: Remark,
)

data class PasswordGroupId(val value: String)

@JvmInline
value class Title(val value: String)

@JvmInline
value class Remark(val value: String)

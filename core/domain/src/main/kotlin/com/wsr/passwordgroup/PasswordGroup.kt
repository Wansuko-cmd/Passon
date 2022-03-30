package com.wsr.passwordgroup

import com.wsr.user.Email

data class PasswordGroup(
    val id: PasswordGroupId,
    val email: Email,
    val title: Title,
    val remark: Remark,
)

@JvmInline
value class PasswordGroupId(val value: String)

@JvmInline
value class Title(val value: String)

@JvmInline
value class Remark(val value: String)

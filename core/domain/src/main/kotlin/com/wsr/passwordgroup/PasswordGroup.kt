package com.wsr.passwordgroup

import com.wsr.email.Email

data class PasswordGroup(
    val id: PasswordGroupId,
    val email: Email,
    val title: String,
    val remark: String,
)

data class PasswordGroupId(val value: String)

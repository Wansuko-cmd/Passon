package com.wsr.passwordgroup

import com.wsr.user.Email
import com.wsr.ext.UniqueId


data class PasswordGroup(
    val id: UniqueId,
    val email: Email,
    val title: String,
    val remark: String,
)

package com.wsr.password_group

import com.wsr.user.Email
import com.wsr.utils.UniqueId


data class PasswordGroup(
    val id: UniqueId,
    val email: Email,
    val title: String,
    val remark: String,
)

package com.wsr.passwordgroup

import com.wsr.email.Email
import com.wsr.utils.UniqueId

data class PasswordGroup private constructor(
    val id: UniqueId,
    val email: Email,
    val title: String,
    val remark: String,
) {
    companion object {
        fun of(
            id: UniqueId = UniqueId.of(),
            email: Email,
            title: String,
            remark: String = "",
        ) = PasswordGroup(id, email, title, remark)
    }
}

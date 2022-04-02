package com.wsr.passwordgroup

import com.wsr.user.UserId

data class PasswordGroup(
    val id: PasswordGroupId,
    val userId: UserId,
    val title: Title,
    val remark: Remark,
)

@JvmInline
value class PasswordGroupId(val value: String)

@JvmInline
value class Title(val value: String)

@JvmInline
value class Remark(val value: String)

package com.wsr.password

import com.wsr.ext.UniqueId

data class Password(
    val id: UniqueId,
    val passwordGroupId: UniqueId,
    val name: String,
    val password: String,
)

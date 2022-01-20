package com.wsr.password

import com.wsr.utils.UniqueId

data class Password(
    val id: UniqueId,
    val titleId: UniqueId,
    val name: String,
    val password: String,
)

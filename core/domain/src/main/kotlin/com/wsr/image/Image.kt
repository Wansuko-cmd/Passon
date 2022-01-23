package com.wsr.image

import com.wsr.utils.UniqueId

data class Image(
    val id: UniqueId,
    val content: Base64,
)

data class Base64(val value: String)
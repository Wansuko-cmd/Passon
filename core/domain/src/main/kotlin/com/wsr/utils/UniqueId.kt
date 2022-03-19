package com.wsr.utils

import java.util.UUID

@JvmInline
value class UniqueId private constructor(val value: String) {
    companion object {
        fun from(value: String = UUID.randomUUID().toString()) = UniqueId(value)
    }
}

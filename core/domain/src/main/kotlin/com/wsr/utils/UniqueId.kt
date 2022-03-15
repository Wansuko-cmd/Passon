package com.wsr.utils

import java.util.*

@JvmInline
value class UniqueId(val value: String) {
    companion object {
        fun of(value: String = UUID.randomUUID().toString()) = UniqueId(value)
    }
}

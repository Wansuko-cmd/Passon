package com.wsr.email

@JvmInline
value class Email private constructor(val value: String) {
    companion object {
        fun of(value: String) = Email(value)
    }
}

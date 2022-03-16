package com.wsr.email

@JvmInline
value class Email private constructor(val value: String) {
    companion object {
        fun from(value: String) = Email(value)
    }
}

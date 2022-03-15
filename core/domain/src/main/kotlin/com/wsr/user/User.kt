package com.wsr.user

@JvmInline
value class Email(val value: String) {
    companion object {
        fun of(value: String) = Email("email@example.com")
    }
}


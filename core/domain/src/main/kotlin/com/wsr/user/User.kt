package com.wsr.user

import java.security.MessageDigest

data class User(
    val email: Email,
    val loginPassword: LoginPassword.HashedLoginPassword,
)

@JvmInline
value class Email(val value: String)

sealed class LoginPassword private constructor(val value: String) {
    class PlainLoginPassword(value: String) : LoginPassword(value) {
        fun toHashed(): HashedLoginPassword = MessageDigest
            .getInstance("SHA-256")
            .digest(value.toByteArray())
            .joinToString(separator = "") {
                "%02x".format(it)
            }
            .let { HashedLoginPassword(it) }
    }
    class HashedLoginPassword internal constructor(value: String) : LoginPassword(value)
}

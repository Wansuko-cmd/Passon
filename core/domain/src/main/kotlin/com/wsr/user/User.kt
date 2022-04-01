package com.wsr.user

import java.security.MessageDigest

data class User(
    val email: Email,
    val loginPassword: LoginPassword.HashedLoginPassword,
) {
    fun shouldPass(password: LoginPassword) = when (password) {
        is LoginPassword.PlainLoginPassword -> password.toHashed().value == loginPassword.value
        is LoginPassword.HashedLoginPassword -> password.value == loginPassword.value
    }

    fun copyWithLoginPassword(loginPassword: LoginPassword.PlainLoginPassword) =
        this.copy(loginPassword = loginPassword.toHashed())
}

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

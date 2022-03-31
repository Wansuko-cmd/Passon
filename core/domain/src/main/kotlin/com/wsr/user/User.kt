package com.wsr.user

data class User(
    val email: Email,
    val loginPassword: LoginPassword,
)

@JvmInline
value class Email(val value: String)

data class LoginPassword(val value: String)

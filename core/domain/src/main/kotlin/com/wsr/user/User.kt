package com.wsr.user

data class User(
    val email: Email,
    val displayName: DisplayName,
    val authImage: Image,
)

data class Email(val value: String)

data class DisplayName(val value: String)

data class Image(val value: String)

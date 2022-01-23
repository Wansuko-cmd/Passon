package com.wsr.user

import com.wsr.image.Image

data class User(
    val email: Email,
    val displayName: DisplayName,
    val authImage: Image,
)

data class Email(val value: String)

data class DisplayName(val value: String)

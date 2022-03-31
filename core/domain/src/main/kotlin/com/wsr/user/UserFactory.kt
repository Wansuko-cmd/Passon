package com.wsr.user

class UserFactory {
    fun create(
        email: Email,
        loginPassword: LoginPassword,
    ): User = User(email, loginPassword)
}

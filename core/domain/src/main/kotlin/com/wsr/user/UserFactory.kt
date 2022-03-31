package com.wsr.user

class UserFactory {
    fun create(
        email: Email,
        loginPassword: LoginPassword.PlainLoginPassword,
    ): User = User(email, loginPassword.toHashed())
}

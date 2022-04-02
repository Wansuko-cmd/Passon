package com.wsr.user

class UserFactory {
    fun create(
        email: UserId,
        loginPassword: LoginPassword.PlainLoginPassword,
    ): User = User(email, loginPassword.toHashed())
}

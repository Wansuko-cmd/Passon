package com.wsr.user

class UserFactory {
    fun create(
        email: UserId,
        databasePath: DatabasePath,
        loginPassword: LoginPassword.PlainLoginPassword,
    ): User = User(email, databasePath, loginPassword.toHashed())
}

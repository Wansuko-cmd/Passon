package com.wsr.user

class UserFactory {
    fun create(
        userId: UserId,
        displayName: DisplayName,
        databasePath: DatabasePath,
        loginPassword: LoginPassword.PlainLoginPassword,
    ): User = User(
        userId = userId,
        displayName = displayName,
        databasePath = databasePath,
        loginPassword = loginPassword.toHashed(),
    )
}

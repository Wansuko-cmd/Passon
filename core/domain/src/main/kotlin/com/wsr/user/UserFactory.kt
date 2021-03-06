package com.wsr.user

import java.util.UUID

class UserFactory {
    fun create(
        userId: UserId,
        displayName: DisplayName,
        databasePath: DatabasePath,
        loginPassword: LoginPassword.PlainLoginPassword,
    ): User = User(
        id = userId,
        displayName = displayName,
        databasePath = databasePath,
        loginPassword = loginPassword.toHashed(),
    )

    fun create(
        displayName: DisplayName,
        databasePath: DatabasePath,
        loginPassword: LoginPassword.PlainLoginPassword,
    ): User {
        val userId = UserId(UUID.randomUUID().toString())
        return User(
            id = userId,
            displayName = displayName,
            databasePath = databasePath,
            loginPassword = loginPassword.toHashed(),
        )
    }
}

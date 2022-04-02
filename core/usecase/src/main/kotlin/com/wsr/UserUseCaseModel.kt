package com.wsr

import com.wsr.user.User

data class UserUseCaseModel(
    val id: String,
    val displayName: String,
    val databasePath: String,
)

fun User.toUseCaseModel() = UserUseCaseModel(
    id = this.userId.value,
    displayName = this.displayName.value,
    databasePath = this.databasePath.value
)
package com.wsr.infra.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsr.user.DatabasePath
import com.wsr.user.LoginPassword
import com.wsr.user.User
import com.wsr.user.UserId

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val email: String,
    @ColumnInfo(name = "database_path") val databasePath: String,
    @ColumnInfo(name = "login_password") val loginPassword: String,
) {

    fun toUser() = User(
        userId = UserId(email),
        databasePath = DatabasePath(databasePath),
        loginPassword = LoginPassword.HashedLoginPassword(loginPassword)
    )
}

fun User.toEntity() = UserEntity(
    email = userId.value,
    databasePath = databasePath.value,
    loginPassword = loginPassword.value,
)

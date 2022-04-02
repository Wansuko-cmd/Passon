package com.wsr.infra.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsr.user.UserId
import com.wsr.user.LoginPassword
import com.wsr.user.User

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val email: String,
    @ColumnInfo(name = "login_password") val loginPassword: String,
) {

    fun toUser() = User(
        userId = UserId(email),
        loginPassword = LoginPassword.HashedLoginPassword(loginPassword)
    )
}

fun User.toEntity() = UserEntity(
    email = userId.value,
    loginPassword = loginPassword.value,
)
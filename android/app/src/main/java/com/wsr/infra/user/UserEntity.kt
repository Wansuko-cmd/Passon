package com.wsr.infra.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsr.user.DatabasePath
import com.wsr.user.DisplayName
import com.wsr.user.LoginPassword
import com.wsr.user.User
import com.wsr.user.UserId

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "display_name") val displayName: String,
    @ColumnInfo(name = "database_path") val databasePath: String,
    @ColumnInfo(name = "login_password") val loginPassword: String,
) {

    fun toUser() = User(
        id = UserId(id),
        displayName = DisplayName(displayName),
        databasePath = DatabasePath(databasePath),
        loginPassword = LoginPassword.HashedLoginPassword(loginPassword)
    )
}

fun User.toEntity() = UserEntity(
    id = id.value,
    displayName = displayName.value,
    databasePath = databasePath.value,
    loginPassword = loginPassword.value,
)

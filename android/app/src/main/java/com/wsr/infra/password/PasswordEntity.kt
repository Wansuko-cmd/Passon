package com.wsr.infra.password

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsr.password.Password
import com.wsr.utils.UniqueId

@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "password_group_id") val passwordGroupId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "password") val password: String,
) {

    fun toPassword() = Password.of(
        id = UniqueId.from(id),
        passwordGroupId = UniqueId.from(passwordGroupId),
        name = name,
        password = password,
    )
}

fun Password.toEntity() = PasswordEntity(
    id = id.value,
    passwordGroupId = passwordGroupId.value,
    name = name,
    password = password,
)

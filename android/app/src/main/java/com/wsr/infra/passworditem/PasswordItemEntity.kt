package com.wsr.infra.passworditem

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.Name
import com.wsr.passworditem.Password
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId

@Entity(tableName = "password_items")
data class PasswordItemEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "password_group_id") val passwordGroupId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "password") val password: String,
) {

    fun toPassword() = PasswordItem(
        id = PasswordItemId(id),
        passwordGroupId = PasswordGroupId(passwordGroupId),
        name = Name(name),
        password = Password(password),
    )
}

fun PasswordItem.toEntity() = PasswordItemEntity(
    id = id.value,
    passwordGroupId = passwordGroupId.value,
    name = name.value,
    password = password.value,
)

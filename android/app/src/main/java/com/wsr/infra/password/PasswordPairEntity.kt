package com.wsr.infra.password

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordpair.Name
import com.wsr.passwordpair.Password
import com.wsr.passwordpair.PasswordPair
import com.wsr.passwordpair.PasswordPairId

@Entity(tableName = "password_pairs")
data class PasswordPairEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "password_group_id") val passwordGroupId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "password") val password: String,
) {

    fun toPassword() = PasswordPair(
        id = PasswordPairId(id),
        passwordGroupId = PasswordGroupId(passwordGroupId),
        name = Name(name),
        password = Password(password),
    )
}

fun PasswordPair.toEntity() = PasswordPairEntity(
    id = id.value,
    passwordGroupId = passwordGroupId.value,
    name = name.value,
    password = password.value,
)

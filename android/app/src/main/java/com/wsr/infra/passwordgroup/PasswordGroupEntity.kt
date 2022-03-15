package com.wsr.infra.passwordgroup

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.user.Email
import com.wsr.utils.UniqueId

@Entity(tableName = "password_groups")
data class PasswordGroupEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "remark") val remark: String,
) {
    fun toPasswordGroup() = PasswordGroup.of(
        id = UniqueId.of(id),
        email = Email.of(email),
        title = title,
        remark = remark,
    )
}

fun PasswordGroup.toEntity() = PasswordGroupEntity(
    id = id.value,
    email = email.value,
    title = title,
    remark = remark,
)
package com.wsr.infra.passwordgroup

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsr.email.Email
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId

@Entity(tableName = "password_groups")
data class PasswordGroupEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "remark") val remark: String,
) {
    fun copyWithTitle(title: String) = this.copy(title = title)
    fun copyWithRemark(remark: String) = this.copy(remark = remark)

    fun toPasswordGroup() = PasswordGroup(
        id = PasswordGroupId(id),
        email = Email(email),
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

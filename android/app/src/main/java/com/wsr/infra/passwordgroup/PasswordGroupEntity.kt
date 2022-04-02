package com.wsr.infra.passwordgroup

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.Remark
import com.wsr.passwordgroup.Title
import com.wsr.user.UserId

@Entity(tableName = "password_groups")
data class PasswordGroupEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "remark") val remark: String,
) {
    fun copyWithTitle(title: String) = this.copy(title = title)
    fun copyWithRemark(remark: String) = this.copy(remark = remark)

    fun toPasswordGroup() = PasswordGroup(
        id = PasswordGroupId(id),
        userId = UserId(userId),
        title = Title(title),
        remark = Remark(remark),
    )
}

fun PasswordGroup.toEntity() = PasswordGroupEntity(
    id = id.value,
    userId = userId.value,
    title = title.value,
    remark = remark.value,
)

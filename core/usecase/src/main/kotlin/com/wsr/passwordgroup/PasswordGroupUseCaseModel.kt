package com.wsr.passwordgroup

import com.wsr.user.Email
import com.wsr.utils.UniqueId

data class PasswordGroupUseCaseModel(
    val id: String,
    val email: String,
    val title: String,
    val remark: String,
)

fun PasswordGroup.toUseCaseModel() = PasswordGroupUseCaseModel(
    this.id.value,
    this.email.value,
    this.title,
    this.remark,
)

fun PasswordGroupUseCaseModel.toPasswordGroup() = PasswordGroup(
    UniqueId(id),
    Email(email),
    title,
    remark,
)
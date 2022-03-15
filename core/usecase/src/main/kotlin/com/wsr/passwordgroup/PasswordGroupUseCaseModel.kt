package com.wsr.passwordgroup

data class PasswordGroupUseCaseModel(
    val id: String,
    val email: String,
    val title: String,
    val remark: String,
)

fun PasswordGroup.toUseCaseModel() = PasswordGroupUseCaseModel(
    id = this.id.value,
    email = this.email.value,
    title = this.title,
    remark = this.remark,
)

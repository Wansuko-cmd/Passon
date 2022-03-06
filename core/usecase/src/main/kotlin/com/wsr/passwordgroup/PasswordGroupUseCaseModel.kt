package com.wsr.passwordgroup

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

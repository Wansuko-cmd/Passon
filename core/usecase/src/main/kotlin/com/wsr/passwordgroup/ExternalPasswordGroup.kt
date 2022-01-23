package com.wsr.passwordgroup

data class ExternalPasswordGroup(
    val id: String,
    val email: String,
    val title: String,
    val remark: String,
)

fun PasswordGroup.toExternal() = ExternalPasswordGroup(
    this.id.value,
    this.email.value,
    this.title,
    this.remark,
)

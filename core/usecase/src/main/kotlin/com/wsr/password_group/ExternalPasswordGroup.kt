package com.wsr.password_group

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

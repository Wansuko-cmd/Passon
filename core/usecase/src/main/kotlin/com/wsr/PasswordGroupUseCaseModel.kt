package com.wsr

import com.wsr.passwordgroup.PasswordGroup

data class PasswordGroupUseCaseModel(
    val id: String,
    val email: String,
    val title: String,
    val remark: String,
)

fun PasswordGroup.toUseCaseModel() = PasswordGroupUseCaseModel(
    id = this.id.value,
    email = this.email.value,
    title = this.title.value,
    remark = this.remark.value,
)

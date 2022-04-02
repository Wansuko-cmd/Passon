package com.wsr

import com.wsr.passwordgroup.PasswordGroup

data class PasswordGroupUseCaseModel(
    val id: String,
    val userId: String,
    val title: String,
    val remark: String,
)

fun PasswordGroup.toUseCaseModel() = PasswordGroupUseCaseModel(
    id = this.id.value,
    userId = this.userId.value,
    title = this.title.value,
    remark = this.remark.value,
)

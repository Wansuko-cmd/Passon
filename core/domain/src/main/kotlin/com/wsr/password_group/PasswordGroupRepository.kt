package com.wsr.password_group

import com.wsr.user.Email
import com.wsr.utils.UniqueId

interface PasswordGroupRepository {
    fun getAllByEmail(email: Email): List<PasswordGroup>
    fun getById(id: UniqueId): PasswordGroup
    fun create(passwordGroup: PasswordGroup)
    fun update(id: UniqueId, title: String, remark: String)
    fun delete(id: UniqueId)
}

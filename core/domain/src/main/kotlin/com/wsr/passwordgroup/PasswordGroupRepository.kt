package com.wsr.passwordgroup

import com.wsr.user.Email
import com.wsr.utils.UniqueId

interface PasswordGroupRepository {
    suspend fun getAllByEmail(email: Email): List<PasswordGroup>
    suspend fun getById(id: UniqueId): PasswordGroup
    suspend fun create(passwordGroup: PasswordGroup)
    suspend fun update(id: UniqueId, title: String, remark: String)
    suspend fun delete(id: UniqueId)
}

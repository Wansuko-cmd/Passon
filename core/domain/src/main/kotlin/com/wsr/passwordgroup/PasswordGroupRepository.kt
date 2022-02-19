package com.wsr.passwordgroup

import com.wsr.user.Email
import com.wsr.ext.UniqueId

interface PasswordGroupRepository {
    suspend fun getAllByEmail(email: Email): List<PasswordGroup>
    suspend fun getById(id: UniqueId): PasswordGroup
    suspend fun create(passwordGroup: PasswordGroup)
    suspend fun update(id: UniqueId, title: String? = null, remark: String? = null)
    suspend fun delete(id: UniqueId)
}

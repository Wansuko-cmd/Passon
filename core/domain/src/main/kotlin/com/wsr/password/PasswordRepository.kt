package com.wsr.password

import com.wsr.utils.UniqueId

interface PasswordRepository {
    suspend fun getAllByPasswordGroupId(passwordGroupId: UniqueId): List<Password>
    suspend fun create(password: Password)
    suspend fun update(password: Password)
    suspend fun delete(id: UniqueId)
}
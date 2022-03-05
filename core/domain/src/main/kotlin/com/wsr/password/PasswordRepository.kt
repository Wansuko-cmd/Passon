package com.wsr.password

import com.wsr.utils.UniqueId

interface PasswordRepository {
    suspend fun getAllByPasswordGroupId(passwordGroupId: UniqueId): List<Password>
    suspend fun upsert(password: Password)
    suspend fun delete(id: UniqueId)
}
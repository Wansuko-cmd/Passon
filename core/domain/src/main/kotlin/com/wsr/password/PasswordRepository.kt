package com.wsr.password

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.passwordgroup.PasswordGroupId

interface PasswordRepository {
    @Throws(GetAllDataFailedException::class)
    suspend fun getAllByPasswordGroupId(passwordGroupId: PasswordGroupId): List<Password>

    @Throws(UpsertDataFailedException::class)
    suspend fun upsert(password: Password): Password

    @Throws(DeleteDataFailedException::class)
    suspend fun delete(id: PasswordId)
}

package com.wsr.passwordgroup

import com.wsr.exceptions.*
import com.wsr.user.Email
import com.wsr.utils.UniqueId

interface PasswordGroupRepository {
    @Throws(GetAllDataFailedException::class)
    suspend fun getAllByEmail(email: Email): List<PasswordGroup>

    @Throws(GetDataFailedException::class)
    suspend fun getById(id: UniqueId): PasswordGroup

    @Throws(CreateDataFailedException::class)
    suspend fun create(passwordGroup: PasswordGroup)

    @Throws(UpdateDataFailedException::class)
    suspend fun update(id: UniqueId, title: String, remark: String)

    @Throws(DeleteDataFailedException::class)
    suspend fun delete(id: UniqueId)
}

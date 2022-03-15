package com.wsr.passwordgroup

import com.wsr.email.Email
import com.wsr.exceptions.CreateDataFailedException
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.exceptions.GetDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
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

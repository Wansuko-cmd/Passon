package com.wsr.passwordgroup

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpdateDataFailedException

interface PasswordGroupRepository {
    @Throws(CreateDataFailedException::class)
    suspend fun create(passwordGroup: PasswordGroup)

    @Throws(UpdateDataFailedException::class)
    suspend fun update(id: PasswordGroupId, title: String, remark: String)

    @Throws(DeleteDataFailedException::class)
    suspend fun delete(id: PasswordGroupId)
}

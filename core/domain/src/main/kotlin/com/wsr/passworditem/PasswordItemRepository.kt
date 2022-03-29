package com.wsr.passworditem

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.passwordgroup.PasswordGroupId

interface PasswordItemRepository {

    @Throws(UpsertDataFailedException::class)
    suspend fun upsert(passwordItem: PasswordItem)

    @Throws(DeleteDataFailedException::class)
    suspend fun delete(id: PasswordItemId)

    @Throws(DeleteDataFailedException::class)
    suspend fun deleteAll(passwordGroupId: PasswordGroupId)
}

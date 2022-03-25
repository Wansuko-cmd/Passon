package com.wsr.passworditem

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpsertDataFailedException

interface PasswordItemRepository {

    @Throws(UpsertDataFailedException::class)
    suspend fun upsert(passwordItem: PasswordItem)

    @Throws(DeleteDataFailedException::class)
    suspend fun delete(id: PasswordItemId)
}

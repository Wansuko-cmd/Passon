package com.wsr.passworditem

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.passwordgroup.PasswordGroupId

interface PasswordItemRepository {
    @Throws(GetAllDataFailedException::class)
    suspend fun getAllByPasswordGroupId(passwordGroupId: PasswordGroupId): List<PasswordItem>

    @Throws(UpsertDataFailedException::class)
    suspend fun upsert(passwordItem: PasswordItem)

    @Throws(DeleteDataFailedException::class)
    suspend fun delete(id: PasswordItemId)
}

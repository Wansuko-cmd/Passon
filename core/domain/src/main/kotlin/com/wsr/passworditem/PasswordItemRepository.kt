package com.wsr.passworditem

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.state.State

interface PasswordItemRepository {

    suspend fun upsert(passwordItem: PasswordItem): State<Unit, UpsertDataFailedException>

    suspend fun delete(id: PasswordItemId): State<Unit, DeleteDataFailedException>

    suspend fun deleteAll(passwordGroupId: PasswordGroupId): State<Unit, DeleteDataFailedException>
}

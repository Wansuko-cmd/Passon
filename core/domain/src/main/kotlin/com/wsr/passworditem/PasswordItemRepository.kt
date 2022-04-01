package com.wsr.passworditem

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroupId

interface PasswordItemRepository {

    suspend fun upsert(passwordItem: PasswordItem): Maybe<Unit, UpsertDataFailedException>

    suspend fun delete(id: PasswordItemId): Maybe<Unit, DeleteDataFailedException>

    suspend fun deleteAll(passwordGroupId: PasswordGroupId): Maybe<Unit, DeleteDataFailedException>
}

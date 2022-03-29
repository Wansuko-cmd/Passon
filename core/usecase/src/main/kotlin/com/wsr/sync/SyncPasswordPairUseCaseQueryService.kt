package com.wsr.sync

import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItemId

interface SyncPasswordPairUseCaseQueryService {
    suspend fun getAllPasswordItemId(passwordGroupId: PasswordGroupId): List<PasswordItemId>
}

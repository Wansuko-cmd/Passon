package com.wsr.sync

import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItemId

interface SyncPasswordSetUseCaseQueryService {
    suspend fun getAllPasswordItemId(passwordGroupId: PasswordGroupId): List<PasswordItemId>
}

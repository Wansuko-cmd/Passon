package com.wsr.infra.queryservice

import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItemId
import com.wsr.sync.SyncPasswordPairUseCaseQueryService

class LocalSyncPasswordPairUseCaseQueryServiceImpl(
    private val passwordItemEntityDao: PasswordItemEntityDao,
) : SyncPasswordPairUseCaseQueryService {
    override suspend fun getAllPasswordItemId(
        passwordGroupId: PasswordGroupId,
    ): List<PasswordItemId> = passwordItemEntityDao
        .getAllByPasswordGroupId(passwordGroupId.value)
        .map { PasswordItemId(it.id) }
}

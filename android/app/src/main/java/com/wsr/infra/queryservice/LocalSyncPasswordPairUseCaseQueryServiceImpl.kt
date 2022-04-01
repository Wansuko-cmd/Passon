package com.wsr.infra.queryservice

import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.maybe.Maybe
import com.wsr.maybe.sequence
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItemId
import com.wsr.sync.SyncPasswordPairUseCaseException
import com.wsr.sync.SyncPasswordPairUseCaseQueryService

class LocalSyncPasswordPairUseCaseQueryServiceImpl(
    private val passwordItemEntityDao: PasswordItemEntityDao,
) : SyncPasswordPairUseCaseQueryService {
    override suspend fun getAllPasswordItemId(
        passwordGroupId: PasswordGroupId,
    ): Maybe<List<PasswordItemId>, SyncPasswordPairUseCaseException> = try {
        passwordItemEntityDao
            .getAllByPasswordGroupId(passwordGroupId.value)
            .map { PasswordItemId(it.id) }
            .map { Maybe.Success(it) }
            .sequence()
    } catch (e: Exception) {
        throw e
    }
}

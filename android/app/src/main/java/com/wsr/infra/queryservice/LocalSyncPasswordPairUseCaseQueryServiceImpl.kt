package com.wsr.infra.queryservice

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItemId
import com.wsr.sync.SyncPasswordPairUseCaseQueryService
import kotlin.jvm.Throws

class LocalSyncPasswordPairUseCaseQueryServiceImpl(
    private val passwordItemEntityDao: PasswordItemEntityDao,
) : SyncPasswordPairUseCaseQueryService {
    @Throws(GetAllDataFailedException::class)
    override suspend fun getAllPasswordItemId(
        passwordGroupId: PasswordGroupId,
    ): List<PasswordItemId> = try {
        passwordItemEntityDao
            .getAllByPasswordGroupId(passwordGroupId.value)
            .map { PasswordItemId(it.id) }
    } catch (e: Exception) {
        throw GetAllDataFailedException.DatabaseException()
    }
}

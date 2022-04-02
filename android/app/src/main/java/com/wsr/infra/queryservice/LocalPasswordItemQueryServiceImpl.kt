package com.wsr.infra.queryservice

import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.maybe.Maybe
import com.wsr.maybe.sequence
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem
import com.wsr.queryservice.PasswordItemQueryService
import com.wsr.queryservice.PasswordItemQueryServiceException

class LocalPasswordItemQueryServiceImpl(
    private val passwordItemEntityDao: PasswordItemEntityDao,
) : PasswordItemQueryService {
    override suspend fun getAll(
        passwordGroupId: PasswordGroupId,
    ): Maybe<List<PasswordItem>, PasswordItemQueryServiceException> = try {
        passwordItemEntityDao
            .getAllByPasswordGroupId(passwordGroupId.value)
            .map { Maybe.Success(it.toPassword()) }
            .sequence()
    } catch (e: Exception) {
        throw e
    }
}

package com.wsr.infra.queryservice

import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem
import com.wsr.queryservice.PasswordItemsQueryService
import com.wsr.queryservice.PasswordItemsQueryServiceException

class LocalPasswordItemQueryServiceImpl(
    private val passwordItemEntityDao: PasswordItemEntityDao,
) : PasswordItemsQueryService {
    override suspend fun getAll(
        passwordGroupId: PasswordGroupId,
    ): Maybe<List<PasswordItem>, PasswordItemsQueryServiceException> = try {
        passwordItemEntityDao
            .getAllByPasswordGroupId(passwordGroupId.value)
            .map { it.toPassword() }
            .let { Maybe.Success(it) }
    } catch (e: Exception) {
        Maybe.Failure(PasswordItemsQueryServiceException.SystemError(e.message.orEmpty()))
    }
}

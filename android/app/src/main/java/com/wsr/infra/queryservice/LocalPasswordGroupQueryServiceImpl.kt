package com.wsr.infra.queryservice

import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.queryservice.PasswordGroupQueryService
import com.wsr.queryservice.PasswordGroupQueryServiceException
import com.wsr.user.UserId

class LocalPasswordGroupQueryServiceImpl(private val passwordGroupEntityDao: PasswordGroupEntityDao) : PasswordGroupQueryService {
    override suspend fun getAll(userId: UserId): Maybe<List<PasswordGroup>, PasswordGroupQueryServiceException> = try {
        passwordGroupEntityDao
            .getAllByUserId(userId.value)
            .map { it.toPasswordGroup() }
            .let { Maybe.Success(it) }
    } catch (e: Exception) {
        Maybe.Failure(PasswordGroupQueryServiceException.DatabaseError(e.message.orEmpty()))
    }
}

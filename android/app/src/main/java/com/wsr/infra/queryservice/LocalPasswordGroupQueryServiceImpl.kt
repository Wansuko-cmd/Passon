package com.wsr.infra.queryservice

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.maybe.Maybe
import com.wsr.maybe.sequence
import com.wsr.queryservice.PasswordGroupQueryService
import com.wsr.queryservice.PasswordGroupQueryServiceException
import com.wsr.toUseCaseModel
import com.wsr.user.UserId

class LocalPasswordGroupQueryServiceImpl(private val passwordGroupEntityDao: PasswordGroupEntityDao) : PasswordGroupQueryService {
    override suspend fun getAll(email: UserId): Maybe<List<PasswordGroupUseCaseModel>, PasswordGroupQueryServiceException> = try {
        passwordGroupEntityDao
            .getAllByEmail(email.value)
            .map { it.toPasswordGroup() }
            .map { it.toUseCaseModel() }
            .map { Maybe.Success(it) }
            .sequence()
    } catch (e: Exception) {
        Maybe.Failure(PasswordGroupQueryServiceException.DatabaseError(e.message.orEmpty()))
    }
}

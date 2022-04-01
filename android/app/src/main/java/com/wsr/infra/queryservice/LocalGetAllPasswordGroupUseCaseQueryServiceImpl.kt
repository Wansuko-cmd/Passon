package com.wsr.infra.queryservice

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.get.FetchAllPasswordGroupUseCaseQueryService
import com.wsr.get.FetchAllPasswordGroupUseCaseQueryServiceException
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.maybe.Maybe
import com.wsr.maybe.sequence
import com.wsr.toUseCaseModel
import com.wsr.user.Email

class LocalGetAllPasswordGroupUseCaseQueryServiceImpl(
    private val passwordGroupEntityDao: PasswordGroupEntityDao,
) : FetchAllPasswordGroupUseCaseQueryService {
    override suspend fun getAllPasswordGroup(email: Email): Maybe<List<PasswordGroupUseCaseModel>, FetchAllPasswordGroupUseCaseQueryServiceException> = try {
        passwordGroupEntityDao
            .getAllByEmail(email.value)
            .map { it.toPasswordGroup() }
            .map { it.toUseCaseModel() }
            .map { Maybe.Success(it) }
            .sequence()
    } catch (e: Exception) {
        Maybe.Failure(FetchAllPasswordGroupUseCaseQueryServiceException.DatabaseError(e.message.orEmpty()))
    }
}

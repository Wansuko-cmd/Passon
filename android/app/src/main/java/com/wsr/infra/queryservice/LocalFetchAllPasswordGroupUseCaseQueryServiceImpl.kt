package com.wsr.infra.queryservice

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.fetch.FetchAllPasswordGroupUseCaseQueryService
import com.wsr.fetch.FetchAllPasswordGroupUseCaseQueryServiceException
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.state.State
import com.wsr.state.sequence
import com.wsr.toUseCaseModel
import com.wsr.user.Email

class LocalFetchAllPasswordGroupUseCaseQueryServiceImpl(
    private val passwordGroupEntityDao: PasswordGroupEntityDao,
) : FetchAllPasswordGroupUseCaseQueryService {
    override suspend fun getAllPasswordGroup(email: Email): State<List<PasswordGroupUseCaseModel>, FetchAllPasswordGroupUseCaseQueryServiceException> = try {
        passwordGroupEntityDao
            .getAllByEmail(email.value)
            .map { it.toPasswordGroup() }
            .map { it.toUseCaseModel() }
            .map { State.Success(it) }
            .sequence()
    } catch (e: Exception) {
        State.Failure(FetchAllPasswordGroupUseCaseQueryServiceException.DatabaseError(e.message.orEmpty()))
    }
}

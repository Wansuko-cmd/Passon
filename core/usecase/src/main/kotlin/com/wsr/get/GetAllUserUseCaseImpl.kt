package com.wsr.get

import com.wsr.UserUseCaseModel
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapBoth
import com.wsr.queryservice.UsersQueryService
import com.wsr.queryservice.UsersQueryServiceException
import com.wsr.toUseCaseModel

class GetAllUserUseCaseImpl(private val userQueryService: UsersQueryService) : GetAllUserUseCase {
    override suspend fun getAll(): Maybe<List<UserUseCaseModel>, GetAllUserUseCaseException> =
        userQueryService.getAll().mapBoth(
            success = { users -> users.map { it.toUseCaseModel() } },
            failure = { it.toGetAllUserUseCaseException() }
        )

    private fun UsersQueryServiceException.toGetAllUserUseCaseException(): GetAllUserUseCaseException = when (this) {
        is UsersQueryServiceException.SystemError ->
            throw this
    }
}

package com.wsr.get

import com.wsr.UserUseCaseModel
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapBoth
import com.wsr.queryservice.UserQueryService
import com.wsr.queryservice.UserQueryServiceException
import com.wsr.toUseCaseModel

class GetAllUserUseCaseImpl(private val userQueryService: UserQueryService) : GetAllUserUseCase {
    override suspend fun getAll(): Maybe<List<UserUseCaseModel>, GetAllUserUseCaseException> =
        userQueryService.getAll().mapBoth(
            success = { users -> users.map { it.toUseCaseModel() } },
            failure = { it.toGetAllUserUseCaseException() }
        )

    private fun UserQueryServiceException.toGetAllUserUseCaseException(): GetAllUserUseCaseException = when (this) {
        is UserQueryServiceException.NoSuchUserException ->
            throw this
        is UserQueryServiceException.SystemError ->
            throw this
    }
}

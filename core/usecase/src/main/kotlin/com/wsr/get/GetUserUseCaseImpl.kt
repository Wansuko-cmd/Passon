package com.wsr.get

import com.wsr.UserUseCaseModel
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapBoth
import com.wsr.queryservice.UserQueryService
import com.wsr.queryservice.UserQueryServiceException
import com.wsr.toUseCaseModel
import com.wsr.user.UserId

class GetUserUseCaseImpl(private val userQueryService: UserQueryService) : GetUserUseCase {
    override suspend fun get(userId: String): Maybe<UserUseCaseModel, GetUserUseCaseException> =
        userQueryService.get(UserId(userId)).mapBoth(
            success = { it.toUseCaseModel() },
            failure = { it.toGetUserUseCaseException() }
        )

    private fun UserQueryServiceException.toGetUserUseCaseException() = when (this) {
        is UserQueryServiceException.NoSuchUserException ->
            GetUserUseCaseException.NoSuchUserException("")
        is UserQueryServiceException.SystemError ->
            GetUserUseCaseException.SystemError(
                message = this.message,
                cause = this,
            )
    }
}

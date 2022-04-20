package com.wsr.get

import com.wsr.maybe.mapBoth
import com.wsr.queryservice.PasswordGroupQueryService
import com.wsr.queryservice.PasswordGroupsQueryServiceException
import com.wsr.toUseCaseModel
import com.wsr.user.UserId

class GetAllPasswordGroupUseCaseImpl(
    private val passwordGroupQueryService: PasswordGroupQueryService,
) : GetAllPasswordGroupUseCase {

    override suspend fun get(userId: String) =
        passwordGroupQueryService
            .getAll(UserId(userId))
            .mapBoth(
                success = { users -> users.map { it.toUseCaseModel() } },
                failure = { it.toGetAllPasswordGroupUseCaseException() },
            )

    private fun PasswordGroupsQueryServiceException.toGetAllPasswordGroupUseCaseException(): GetAllPasswordGroupUseCaseException = when (this) {
        is PasswordGroupsQueryServiceException.NoSuchUserException ->
            GetAllPasswordGroupUseCaseException.NoSuchUserException("")
        is PasswordGroupsQueryServiceException.SystemError ->
            throw this
    }
}

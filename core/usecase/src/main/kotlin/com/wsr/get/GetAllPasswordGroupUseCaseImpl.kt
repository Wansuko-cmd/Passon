package com.wsr.get

import com.wsr.maybe.mapFailure
import com.wsr.queryservice.PasswordGroupQueryService
import com.wsr.queryservice.PasswordGroupQueryServiceException
import com.wsr.user.UserId

class GetAllPasswordGroupUseCaseImpl(
    private val passwordGroupQueryService: PasswordGroupQueryService,
) : GetAllPasswordGroupUseCase {

    override suspend fun get(email: String) =
        passwordGroupQueryService
            .getAll(UserId(email))
            .mapFailure { it.toGetAllPasswordGroupUseCaseException() }

    private fun PasswordGroupQueryServiceException.toGetAllPasswordGroupUseCaseException() = when (this) {
        is PasswordGroupQueryServiceException.DatabaseError ->
            GetAllPasswordGroupUseCaseException.SystemError(
                message = this.message,
                cause = this,
            )
    }
}

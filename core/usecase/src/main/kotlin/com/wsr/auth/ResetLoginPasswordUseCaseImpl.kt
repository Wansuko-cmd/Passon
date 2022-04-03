package com.wsr.auth

import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapFailure
import com.wsr.queryservice.UserQueryService
import com.wsr.queryservice.UserQueryServiceException
import com.wsr.user.LoginPassword
import com.wsr.user.User
import com.wsr.user.UserId
import com.wsr.user.UserRepository

class ResetLoginPasswordUseCaseImpl(
    private val userRepository: UserRepository,
    private val userQueryService: UserQueryService,
) : ResetLoginPasswordUseCase {

    override suspend fun reset(
        userId: String,
        newPassword: String,
    ): Maybe<Unit, ResetLoginPasswordUseCaseException> = userQueryService.get(UserId(userId))
        .mapFailure { it.toResetLoginPasswordUseCaseException() }
        .updatePassword(newPassword)

    private suspend fun Maybe<User, ResetLoginPasswordUseCaseException>.updatePassword(
        newPassword: String,
    ): Maybe<Unit, ResetLoginPasswordUseCaseException> = when (this) {
        is Maybe.Success ->
            userRepository
                .update(value.copyWithLoginPassword(LoginPassword.PlainLoginPassword(newPassword)))
                .mapFailure { it.toResetLoginPasswordUseCaseException() }
        is Maybe.Failure -> this
    }

    private fun UserQueryServiceException.toResetLoginPasswordUseCaseException() = when (this) {
        is UserQueryServiceException.NoSuchUserException ->
            ResetLoginPasswordUseCaseException.NoSuchUserException(this.message)
        is UserQueryServiceException.DatabaseError ->
            ResetLoginPasswordUseCaseException.SystemError(
                message = this.message,
                cause = this,
            )
    }

    private fun UpdateDataFailedException.toResetLoginPasswordUseCaseException() = when (this) {
        is UpdateDataFailedException.NoSuchElementException ->
            ResetLoginPasswordUseCaseException.NoSuchUserException("")
        is UpdateDataFailedException.DatabaseError ->
            ResetLoginPasswordUseCaseException.SystemError(
                message = this.message,
                cause = this,
            )
    }
}

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

class ResetUseCaseImpl(
    private val userRepository: UserRepository,
    private val userQueryService: UserQueryService,
) : ResetUseCase {

    override suspend fun reset(
        userId: String,
        newPassword: String,
    ): Maybe<Unit, ResetUseCaseException> = userQueryService.get(UserId(userId))
        .mapFailure { it.toResetUseCaseException() }
        .updatePassword(newPassword)

    private suspend fun Maybe<User, ResetUseCaseException>.updatePassword(
        newPassword: String,
    ): Maybe<Unit, ResetUseCaseException> = when (this) {
        is Maybe.Success ->
            userRepository
                .update(value.copyWithLoginPassword(LoginPassword.PlainLoginPassword(newPassword)))
                .mapFailure { it.toResetUseCaseException() }
        is Maybe.Failure -> this
    }

    private fun UserQueryServiceException.toResetUseCaseException() = when (this) {
        is UserQueryServiceException.NoSuchUserException ->
            ResetUseCaseException.NoSuchUserException(this.message)
        is UserQueryServiceException.DatabaseError ->
            ResetUseCaseException.SystemError(
                message = this.message,
                cause = this,
            )
    }

    private fun UpdateDataFailedException.toResetUseCaseException() = when (this) {
        is UpdateDataFailedException.NoSuchElementException ->
            ResetUseCaseException.NoSuchUserException("")
        is UpdateDataFailedException.DatabaseError ->
            ResetUseCaseException.SystemError(
                message = this.message,
                cause = this,
            )
    }
}

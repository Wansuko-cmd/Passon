package com.wsr.auth

import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapFailure
import com.wsr.user.LoginPassword
import com.wsr.user.UserId
import com.wsr.user.UserRepository

class ResetLoginPasswordUseCaseImpl(
    private val userRepository: UserRepository,
) : ResetLoginPasswordUseCase {

    override suspend fun reset(
        userId: String,
        newPassword: String,
    ): Maybe<Unit, ResetLoginPasswordUseCaseException> = userRepository.update(
        userId = UserId(userId),
        loginPassword = LoginPassword.PlainLoginPassword(newPassword).toHashed(),
    ).mapFailure { it.toResetLoginPasswordUseCaseException() }

    private fun UpdateDataFailedException.toResetLoginPasswordUseCaseException() = when (this) {
        is UpdateDataFailedException.NoSuchElementException ->
            ResetLoginPasswordUseCaseException.NoSuchUserException("")
        is UpdateDataFailedException.SystemError ->
            ResetLoginPasswordUseCaseException.SystemError(
                message = this.message,
                cause = this,
            )
    }
}

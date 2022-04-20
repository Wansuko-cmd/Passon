package com.wsr.update

import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapFailure
import com.wsr.user.DisplayName
import com.wsr.user.UserId
import com.wsr.user.UserRepository

class UpdateUserUseCaseImpl(private val userRepository: UserRepository) : UpdateUserUseCase {
    override suspend fun update(
        userId: String,
        displayName: String,
    ): Maybe<Unit, UpdateUserUseCaseException> =
        userRepository
            .update(UserId(userId), DisplayName(displayName))
            .mapFailure { it.toUpdateUserUseCaseException() }

    private fun UpdateDataFailedException.toUpdateUserUseCaseException() = when (this) {
        is UpdateDataFailedException.NoSuchElementException ->
            UpdateUserUseCaseException.NoSuchUserException(this.message)
        is UpdateDataFailedException.SystemError ->
            throw this
    }
}

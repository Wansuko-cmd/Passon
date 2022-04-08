package com.wsr.delete

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapFailure
import com.wsr.user.UserId
import com.wsr.user.UserRepository

class DeleteUserUseCaseImpl(private val userRepository: UserRepository) : DeleteUserUseCase {
    override suspend fun delete(id: String): Maybe<Unit, DeleteUserUseCaseException> =
        userRepository
            .delete(UserId(id))
            .mapFailure { it.toDeleteUserUseCaseException() }

    private fun DeleteDataFailedException.toDeleteUserUseCaseException() = when(this) {
        is DeleteDataFailedException.NoSuchElementException ->
            DeleteUserUseCaseException.NoSuchPasswordGroupException("")
        is DeleteDataFailedException.DatabaseError ->
            DeleteUserUseCaseException.SystemError(
                message = this.message,
                cause = this,
            )
    }
}
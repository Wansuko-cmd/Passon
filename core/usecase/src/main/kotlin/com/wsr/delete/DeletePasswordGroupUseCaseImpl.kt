package com.wsr.delete

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapFailure
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository

class DeletePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
) : DeletePasswordGroupUseCase {
    override suspend fun delete(id: String): Maybe<Unit, DeletePasswordGroupUseCaseException> =
        passwordGroupRepository
            .delete(PasswordGroupId(id))
            .mapFailure { it.toDeletePasswordGroupUseCaseException() }

    private fun DeleteDataFailedException.toDeletePasswordGroupUseCaseException() = when(this) {
        is DeleteDataFailedException.NoSuchElementException ->
            DeletePasswordGroupUseCaseException.NoSuchPasswordGroupException("")
        is DeleteDataFailedException.DatabaseError ->
            DeletePasswordGroupUseCaseException.SystemError(
                message = this.message,
                cause = this,
            )
    }
}

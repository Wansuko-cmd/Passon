package com.wsr.delete

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.state.State
import com.wsr.state.mapBoth

class DeletePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
) : DeletePasswordGroupUseCase {
    override suspend fun delete(id: String): State<Unit, DeletePasswordGroupUseCaseException> =
        passwordGroupRepository
            .delete(PasswordGroupId(id))
            .mapBoth(
                success = { },
                failure = { exception ->
                    when (exception) {
                        is DeleteDataFailedException.NoSuchElementException ->
                            DeletePasswordGroupUseCaseException.NoSuchPasswordGroupException(exception.message)

                        is DeleteDataFailedException.DatabaseException ->
                            DeletePasswordGroupUseCaseException.SystemError(
                                message = exception.message,
                                cause = exception,
                            )
                    }
                },
            )
}

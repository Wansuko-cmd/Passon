package com.wsr.delete

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapBoth
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository

class DeletePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
) : DeletePasswordGroupUseCase {
    override suspend fun delete(id: String): Maybe<Unit, DeletePasswordGroupUseCaseException> =
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

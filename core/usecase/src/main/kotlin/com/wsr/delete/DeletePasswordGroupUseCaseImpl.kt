package com.wsr.delete

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.state.State

class DeletePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
) : DeletePasswordGroupUseCase {
    override suspend fun delete(id: String): State<Unit, DeletePasswordGroupUseCaseException> = try {
        passwordGroupRepository.delete(PasswordGroupId(id))
        State.Success(Unit)
    } catch (e: DeleteDataFailedException.NoSuchElementException) {
        State.Failure(DeletePasswordGroupUseCaseException.NoSuchPasswordGroupException(e.message))
    } catch (e: Exception) {
        State.Failure(DeletePasswordGroupUseCaseException.SystemError(e.message.orEmpty(), e))
    }
}

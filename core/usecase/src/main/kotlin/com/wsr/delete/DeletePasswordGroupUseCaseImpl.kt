package com.wsr.delete

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.GetDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.state.State

class DeletePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
) : DeletePasswordGroupUseCase {
    override suspend fun delete(id: String): State<Unit, DeleteDataFailedException> = try {
        passwordGroupRepository.delete(PasswordGroupId(id))
        State.Success(Unit)
    } catch (e: GetDataFailedException) {
        when (e) {
            is GetDataFailedException.NoSuchElementException -> State.Failure(DeleteDataFailedException.NoSuchElementException())
            is GetDataFailedException.DatabaseException -> State.Failure(DeleteDataFailedException.DatabaseException())
        }
    } catch (e: DeleteDataFailedException) {
        State.Failure(e)
    }
}

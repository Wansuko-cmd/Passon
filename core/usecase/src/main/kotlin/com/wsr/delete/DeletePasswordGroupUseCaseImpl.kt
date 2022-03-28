package com.wsr.delete

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.GetDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.state.State
import com.wsr.toUseCaseModel

class DeletePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
    private val passwordGroupUseCaseQueryService: DeletePasswordGroupUseCaseQueryService,
) : DeletePasswordGroupUseCase {
    override suspend fun delete(id: String): State<PasswordGroupUseCaseModel, DeleteDataFailedException> = try {
        val passwordGroup = passwordGroupUseCaseQueryService.getPasswordGroup(passwordGroupId = PasswordGroupId(id))
        passwordGroupRepository.delete(PasswordGroupId(id))
        State.Success(passwordGroup.toUseCaseModel())
    } catch (e: GetDataFailedException) {
        when (e) {
            is GetDataFailedException.NoSuchElementException -> State.Failure(DeleteDataFailedException.NoSuchElementException())
            is GetDataFailedException.DatabaseException -> State.Failure(DeleteDataFailedException.DatabaseException())
        }
    } catch (e: DeleteDataFailedException) {
        State.Failure(e)
    }
}

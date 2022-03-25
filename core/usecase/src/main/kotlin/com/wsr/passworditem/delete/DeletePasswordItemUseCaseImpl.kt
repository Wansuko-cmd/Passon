package com.wsr.passworditem.delete

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.exceptions.GetDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItemId
import com.wsr.passworditem.PasswordItemRepository
import com.wsr.passworditem.PasswordItemUseCaseModel
import com.wsr.passworditem.toUseCaseModel
import com.wsr.state.State

class DeletePasswordItemUseCaseImpl(
    private val passwordItemRepository: PasswordItemRepository,
    private val deletePasswordItemUseCaseQueryService: DeletePasswordItemUseCaseQueryService,
) : DeletePasswordItemUseCase {
    override suspend fun deleteAll(id: String): State<List<PasswordItemUseCaseModel>, DeleteDataFailedException> =
        try {
            val passwordItems =
                deletePasswordItemUseCaseQueryService.getAllByPasswordGroupId(PasswordGroupId(id))
            passwordItemRepository.deleteByPasswordGroupId(PasswordGroupId(id))
            State.Success(passwordItems.map { it.toUseCaseModel() })
        } catch (e: GetAllDataFailedException) {
            State.Failure(DeleteDataFailedException.DatabaseException(e.message ?: ""))
        } catch (e: DeleteDataFailedException) {
            State.Failure(e)
        }

    override suspend fun delete(id: String): State<PasswordItemUseCaseModel, DeleteDataFailedException> =
        try {
            val passwordItem = deletePasswordItemUseCaseQueryService.getById(PasswordItemId(id))
            passwordItemRepository.delete(PasswordItemId(id))
            State.Success(passwordItem.toUseCaseModel())
        } catch (e: GetDataFailedException) {
            when (e) {
                is GetDataFailedException.NoSuchElementException -> State.Failure(
                    DeleteDataFailedException.NoSuchElementException()
                )
                is GetDataFailedException.DatabaseException -> State.Failure(
                    DeleteDataFailedException.DatabaseException()
                )
            }
        } catch (e: DeleteDataFailedException) {
            State.Failure(e)
        }
}

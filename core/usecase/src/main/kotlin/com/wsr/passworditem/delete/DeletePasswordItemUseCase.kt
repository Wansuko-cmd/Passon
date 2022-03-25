package com.wsr.passworditem.delete

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.passworditem.PasswordItemUseCaseModel
import com.wsr.state.State

interface DeletePasswordItemUseCase {
    suspend fun deleteAll(id: String): State<List<PasswordItemUseCaseModel>, DeleteDataFailedException>
    suspend fun delete(id: String): State<PasswordItemUseCaseModel, DeleteDataFailedException>
}

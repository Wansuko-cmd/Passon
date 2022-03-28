package com.wsr.delete

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.state.State

interface DeletePasswordGroupUseCase {
    suspend fun delete(id: String): State<PasswordGroupUseCaseModel, DeleteDataFailedException>
}

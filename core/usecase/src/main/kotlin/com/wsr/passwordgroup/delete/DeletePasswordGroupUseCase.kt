package com.wsr.passwordgroup.delete

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.state.State

interface DeletePasswordGroupUseCase {
    suspend fun delete(id: String): State<PasswordGroupUseCaseModel, DeleteDataFailedException>
}

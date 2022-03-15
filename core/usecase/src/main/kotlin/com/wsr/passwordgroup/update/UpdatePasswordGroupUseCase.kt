package com.wsr.passwordgroup.update

import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.state.State

interface UpdatePasswordGroupUseCase {
    suspend fun update(
        id: String,
        title: String,
        remark: String,
    ): State<PasswordGroupUseCaseModel, UpdateDataFailedException>
}

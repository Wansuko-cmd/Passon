package com.wsr.password.create

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.password.PasswordUseCaseModel
import com.wsr.state.State

interface CreatePasswordUseCase {
    suspend fun create(passwordGroupId: String): State<PasswordUseCaseModel, CreateDataFailedException>
}
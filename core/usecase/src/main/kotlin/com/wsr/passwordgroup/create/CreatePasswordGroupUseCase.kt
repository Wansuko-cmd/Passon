package com.wsr.passwordgroup.create

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.state.State

interface CreatePasswordGroupUseCase {
    suspend fun create(email: String, title: String): State<PasswordGroupUseCaseModel, CreateDataFailedException>
}
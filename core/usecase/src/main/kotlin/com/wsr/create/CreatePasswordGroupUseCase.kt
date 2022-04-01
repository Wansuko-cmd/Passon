package com.wsr.create

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.state.State

interface CreatePasswordGroupUseCase {
    suspend fun create(
        email: String,
        title: String,
    ): State<PasswordGroupUseCaseModel, CreatePasswordGroupUseCaseException>
}

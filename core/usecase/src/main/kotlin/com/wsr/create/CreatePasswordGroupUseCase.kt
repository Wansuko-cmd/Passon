package com.wsr.create

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.state.State

interface CreatePasswordGroupUseCase {
    fun create(
        email: String,
        title: String,
    ): State<PasswordGroupUseCaseModel, CreateDataFailedException>
}

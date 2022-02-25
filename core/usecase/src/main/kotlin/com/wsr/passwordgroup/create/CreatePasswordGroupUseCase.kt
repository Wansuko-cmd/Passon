package com.wsr.passwordgroup.create

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.StateFlow

interface CreatePasswordGroupUseCase {
    val data: StateFlow<State<PasswordGroupUseCaseModel, CreateDataFailedException>>

    suspend fun create(email: String, title: String)
}
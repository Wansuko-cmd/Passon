package com.wsr.passwordgroup.get

import com.wsr.exceptions.GetDataFailedException
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.StateFlow

interface GetPasswordGroupUseCase {
    val data: StateFlow<State<PasswordGroupUseCaseModel, GetDataFailedException>>

    suspend fun getById(id: String)
}